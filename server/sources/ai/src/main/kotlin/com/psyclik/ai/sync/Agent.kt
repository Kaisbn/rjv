package com.psyclik.ai.sync

import com.psyclik.ai.data.*
import com.psyclik.ai.duration
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClient
import io.vertx.core.json.Json
import kotlinx.coroutines.experimental.Here
import kotlinx.coroutines.experimental.launch
import org.apache.logging.log4j.LogManager
import java.nio.charset.StandardCharsets
import kotlin.coroutines.suspendCoroutine

open class Agent(val id: String, var x: Int, var y: Int, var z: Int) : AbstractVerticle() {

    val log = LogManager.getLogger(Agent::class.java)
    var available: Boolean = true
    lateinit var auth: Auth
    lateinit var client: HttpClient

    override fun start(startFuture: Future<Void>?) {
        super.start(startFuture)
        client = vertx.createHttpClient()
    }

    override fun stop() {
        client.close()
    }

    fun point() = Point(x, y, z)

    suspend fun post(url: String, vars: Map<String, Any>): String = suspendCoroutine { context ->
        val req = client.post(auth.port, auth.url, url) {
            it.exceptionHandler { context.resumeWithException(it) }
            it.bodyHandler { body -> context.resume(body.toString(StandardCharsets.UTF_8)) }
        }
        val content: String = Json.encode(vars)
        available = false
        req.end(content)
    }

    suspend fun get(url: String): String = suspendCoroutine { context ->
        client.getNow(auth.port, auth.url, url) {
            it.exceptionHandler { ex -> context.resumeWithException(ex) }
            it.bodyHandler { body -> context.resume(body.toString(StandardCharsets.UTF_8)) }
        }
    }

    suspend fun get(port: Int = 80, domain: String, uri: String): String = suspendCoroutine { context ->
        client.getNow(port, domain, uri) {
            it.exceptionHandler { ex -> context.resumeWithException(ex) }
            it.bodyHandler { body -> context.resume(body.toString(StandardCharsets.UTF_8)) }
        }
    }

    suspend fun delay(time: Long): Unit = suspendCoroutine { cont ->
        vertx.setTimer(time) { cont.resume(Unit) }
    }

    suspend fun command(opcode: String, vars: Map<String, Any> = mapOf()): CommandResult {
        val uri = "/command/${auth.login}/$id/${opcode}"
        val rep = Json.decodeValue(post(uri, vars), ActionResponse::class.java)
        if (rep.opcode == "nomoney") {
            return CommandResult(rep.opcode, -1, rep.reportId)
        } else if (rep.reportId == "") {
            delay(1000)
            return command(opcode, vars)
        } else {
            return CommandResult(rep.opcode, DURATIONS[opcode]!!.first + DURATIONS[opcode]!!.second, rep.reportId)
        }
    }

    suspend fun report(reportId: String): Report {
        val uri = "/report/$reportId"

        log.trace("${id} fetching report ${reportId}")

        var report = Report(reportId).fromJson(get(uri))
        while (report.opcode == "noreport") {
            log("delayed", reportId)
            delay(1000)
            report = Report(reportId).fromJson(get(uri))
        }

        if (report.opcode == "dead") {
            log.error("${id} is dead, reportId: ${reportId}")
            throw RuntimeException("$id is dead")
        } else {
            log.trace("${id} received report for repcode ${report.opcode} and reportId: ${reportId}")
            available = true
            return report
        }

    }

    suspend fun execute(opcode: String,
                        vars: Map<String, Any> = mapOf()) = execute(opcode, vars, true)

    suspend fun execute(opcode: String,
                        vars: Map<String, Any> = mapOf(),
                        doLog: Boolean = true): Report {
        val cmdres = command(opcode, vars)
        delay(100 + duration(opcode))
        val report = report(cmdres.reportId)
        if (doLog) {
            log(opcode, this.x, this.y, this.z)
        }
        return report
    }

    suspend fun release() {
        val report = execute("release")
        log("release", "M: ${report.minerals}", "B: ${report.biomass}")
        vertx.undeploy(deploymentID())
    }

    suspend fun playerstatus(): Report = execute("playerstatus")


    suspend fun waitForMoney(costBiomass: Int,
                             costMinerals: Int) {
        do {
            val report = playerstatus()
            log("playerstatus", "M: ${report.minerals}", "B: ${report.biomass}")
        } while (report.minerals!! < costMinerals || report.biomass!! < costBiomass)
    }

    fun error(err: Any) {
        log.error(err)
    }

    fun death(id: String) {
        log.error("agent $id died")
    }

    fun log(opcode: String, vararg vars: Any) {
        val computed = vars.reduce { left, right -> "$left, $right" }
        log.info("$id\t${opcode.padEnd(16, ' ')}\t$computed")
    }

    suspend fun status(): Report = execute("status")

    suspend fun repeat(condition: () -> Boolean = { true }, runnable: suspend () -> Unit) {
        while (condition.invoke()) {
            runnable.invoke()
        }
    }

    suspend fun repeat(times: Int, runnable: suspend () -> Unit) {
        if (times > 0) {
            for (i in 0..times - 1) {
                runnable.invoke()
            }
        }
    }

    fun deploy(vertx: Vertx, onDeploy: suspend () -> Unit) {
        vertx.deployVerticle(this) {
            launch(Here) {
                onDeploy.invoke()
            }
        }
    }
}
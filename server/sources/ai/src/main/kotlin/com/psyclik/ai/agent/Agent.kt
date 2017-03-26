package com.psyclik.ai.agent

import com.psyclik.ai.data.*
import com.psyclik.ai.ticksToMs
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpClient
import io.vertx.core.json.Json
import org.apache.logging.log4j.LogManager
import java.nio.charset.StandardCharsets

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

    fun command(opcode: String,
                vars: Map<String, Any> = mapOf(),
                onError: (String) -> Unit = { error(it) },
                onReport: (CommandResult) -> Unit = {}) {

        val uri = "/command/${auth.login}/$id/${opcode}"
        val req = client.post(auth.port, auth.url, uri) {
            it.exceptionHandler { err -> onError(err.message ?: "") }
            it.bodyHandler { body ->
                val rep = Json.decodeValue(body.toString(StandardCharsets.UTF_8), ActionResponse::class.java)
                if (rep.opcode == "nomoney") {
                    onError.invoke(rep.opcode)
                } else if (rep.reportId == "") {
                    if (rep.opcode == "noagent") {

                    } else {
                        vertx.setTimer(1000, { command(opcode, vars, onError, onReport) })
                    }
                } else {
                    onReport.invoke(CommandResult(rep.opcode, DURATIONS[opcode]!!.first + DURATIONS[opcode]!!.second,
                            rep.reportId))
                }
            }
        }
        val content: String = Json.encode(vars)
        req.end(content)
        available = false
    }

    fun report(reportId: String,
               onError: (String) -> Unit = { error(it) },
               onDeath: (String) -> Unit = { death(it) },
               onReport: (Report) -> Unit) {
        val uri = "/report/$reportId"

        log.trace("${id} fetching report ${reportId}")

        client.getNow(auth.port, auth.url, uri) {
            it.exceptionHandler { onError.invoke("$uri -> ${it.message}") }
            it.bodyHandler { body ->
                var report = Report(reportId).fromJson(body.toString(StandardCharsets.UTF_8))
                val repcode = report.opcode

                if (repcode != "noreport") {
                    if (repcode == "dead") {
                        log.error("${id} is dead, reportId: ${reportId}")
                        onError.invoke("dead")
                    } else {
                        log.trace("${id} received report for repcode ${repcode} and reportId: ${reportId}")
                        available = true
                        onReport.invoke(report)
                    }
                } else {
                    log("delayed", reportId)
                    vertx.setTimer(1000, { report(reportId, onError, onDeath, onReport) })
                }
            }
        }
    }

    fun execute(opcode: String,
                vars: Map<String, Any> = mapOf(),
                onError: (String) -> Unit = { error(it) },
                onDeath: (String) -> Unit = { this.death(it) },
                onReport: (Report) -> Unit) = execute(opcode, vars, true, onError, onDeath, onReport)

    fun execute(opcode: String,
                vars: Map<String, Any> = mapOf(),
                doLog: Boolean = true,
                onError: (String) -> Unit = { error(it) },
                onDeath: (String) -> Unit = { this.death(it) },
                onReport: (Report) -> Unit) {
        command(opcode, vars, onError) { result ->
            vertx.setTimer((100 + ticksToMs(DURATIONS[opcode]!!.first + DURATIONS[opcode]!!.second)).toLong()) {
                report(result.reportId, onError, onDeath) { report ->
                    if (doLog) {
                        log(opcode, this.x, this.y, this.z)
                    }
                    onReport.invoke(report)
                }
            }
        }
    }

    fun release(onError: (String) -> Unit = { error(it) },
                onDeath: (String) -> Unit = { this.death(it) },
                onReport: (Report) -> Unit = {}) {
        execute("release", mapOf(), false, onError, onDeath) { report ->
            log("release", "M: ${report.minerals}", "B: ${report.biomass}")
            onReport.invoke(report)
            vertx.undeploy(deploymentID())
        }
    }

    fun waitForMoney(costBiomass: Int,
                     costMinerals: Int,
                     onError: (String) -> Unit = { error(it) },
                     onDeath: (String) -> Unit = { this.death(it) },
                     onReport: (Report) -> Unit = {}) {
        execute("playerstatus") { report ->
            log("playerstatus", "M: ${report.minerals}", "B: ${report.biomass}")
            if (report.minerals!! >= costMinerals && report.biomass!! >= costBiomass) {
                onReport.invoke(report)
            } else {
                waitForMoney(costBiomass, costMinerals, onError, onDeath, onReport)
            }
        }
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

    fun status(onError: (String) -> Unit = { error(it) },
               onDeath: (String) -> Unit = { this.death(it) },
               onReport: (Report) -> Unit = {}) {
        execute("status", mapOf(), true, onError, onDeath) {
            onReport.invoke(it)
        }
    }

    fun playerstatus(onError: (String) -> Unit = { error(it) },
                     onDeath: (String) -> Unit = { this.death(it) },
                     onReport: (Report) -> Unit = {}) {
        execute("playerstatus", mapOf(), false, onError, onDeath) {
            onReport.invoke(it)
        }
    }

    fun repeat(condition: () -> Boolean = { true }, runnable: (() -> Unit) -> Unit) {
        if (condition.invoke()) {
            runnable.invoke { repeat(condition, runnable) }
        }
    }

    fun repeat(times: Int, runnable: (() -> Unit) -> Unit) {
        if (times > 0) {
            runnable.invoke { repeat(times - 1, runnable) }
        }
    }
}
package com.psyclik.ai

import com.psyclik.ai.agent.Agent
import com.psyclik.ai.data.*
import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClient
import io.vertx.core.json.Json
import kotlinx.coroutines.experimental.Here
import kotlinx.coroutines.experimental.launch
import org.apache.logging.log4j.LogManager
import java.nio.charset.StandardCharsets
import java.util.*

open class AI(val port: Int, val host: String) : AbstractVerticle() {

    val log = LogManager.getLogger(AI::class.java)
    lateinit var client: HttpClient
    lateinit var auth: Auth
    val blocks = HashMap<Point, Location>()

    override fun start() {
        super.start()
        vertx.eventBus().registerDefaultCodec(Command::class.java, JsonCodec(Command::class.java))
        vertx.eventBus().registerDefaultCodec(Report::class.java, JsonCodec(Report::class.java))
        vertx.eventBus().registerDefaultCodec(Auth::class.java, JsonCodec(Auth::class.java))
        vertx.eventBus().registerDefaultCodec(Point::class.java, JsonCodec(Point::class.java))
        vertx.eventBus().registerDefaultCodec(Location::class.java, JsonCodec(Location::class.java))
        vertx.eventBus().registerDefaultCodec(ArrayList::class.java, JsonCodec(ArrayList::class.java))
        vertx.eventBus().registerDefaultCodec(LinkedHashMap::class.java, JsonCodec(LinkedHashMap::class.java))
        vertx.eventBus().registerDefaultCodec(HashMap::class.java, JsonCodec(HashMap::class.java))

        vertx.eventBus().consumer<Location>("/blocks/update") {
            blocks[it.body().point()] = it.body()
        }

        vertx.eventBus().consumer<List<Point>>("/blocks/search") {
            val points = it.body()
            it.reply(points.map { it.to(blocks[it]) }.toMap(HashMap()))
        }

        client = vertx.createHttpClient()
    }

    fun connect(login: String,
                onLoginNotAvailable: (String) -> String = { "${it}_" },
                onError: (String) -> Unit = { error(it) },
                onConnect: (InitResponse) -> Unit = {}) {
        client?.getNow(port, host, "/init/$login") {
            it.exceptionHandler { AI::error }
            it.bodyHandler { body ->
                val resp: InitResponse = Json.decodeValue(body.toString(StandardCharsets.UTF_8), InitResponse::class.java)
                if (resp.error == null) {
                    log.info("logged in with login: $login")
                    auth = Auth(host, port, login)
                    onConnect.invoke(resp)
                } else {
                    if (resp.error == "login not available.") {
                        val nl = onLoginNotAvailable.invoke(login)
                        log.info("login not available, retrying with: $nl")
                        connect(nl, onLoginNotAvailable, onError, onConnect)
                    } else {
                        onError.invoke(resp.error!!)
                    }
                }
            }
        } ?: error("Client unavailable")
    }

    fun status(onStatus: (StatusResponse) -> Unit) {
        client?.getNow(port, host, "/status") {
            it.exceptionHandler { error(it) }
            it.bodyHandler { body ->
                val resp = Json.decodeValue(body.toString(StandardCharsets.UTF_8), StatusResponse::class.java)
                onStatus.invoke(resp)
            }
        } ?: error("Client unavailable")
    }

    fun deploy(agent: Agent, onDeploy: (Agent) -> Unit = {}) {
        agent.auth = auth
        vertx.deployVerticle(agent) {
            onDeploy.invoke(agent)
        }
    }

    fun deploy(agent: com.psyclik.ai.sync.Agent, onDeploy: suspend (com.psyclik.ai.sync.Agent) -> Unit = {}) {
        agent.auth = auth
        vertx.deployVerticle(agent) {
            launch(Here) {
                onDeploy.invoke(agent)
            }
        }
    }


    fun error(err: Any) {
        log.error(err)
    }

    fun connect(login: String,
                vertx: Vertx,
                onLoginNotAvailable: (String) -> String = { "${it}_" },
                onError: (String) -> Unit = { error(it) },
                onConnect: (InitResponse) -> Unit = {}) {
        vertx.deployVerticle(this) {
            connect(login, onLoginNotAvailable, onError) { iresp ->
                onConnect.invoke(iresp)
            }
        }
    }
}
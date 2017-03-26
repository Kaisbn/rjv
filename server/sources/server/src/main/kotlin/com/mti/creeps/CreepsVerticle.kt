package com.mti.creeps

import com.mti.creeps.response.InitResponse
import com.mti.creeps.response.Response
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

class CreepsVerticle(val plugin: CreepsPlugin) : AbstractVerticle() {


    fun listen(): Unit {
        val http = vertx.createHttpServer()
        val router = Router.router(vertx)

        router.route().handler(BodyHandler.create())
        router.get("/status").handler { status(it) }
        router.get("/status/game").handler { gameStatus(it) }
        router.get("/init/:login").handler { init(it) }
        router.get("/report/:reportId").handler { report(it) }
        router.post("/command/:login/:agentId/:opcode").handler { command(it) }

        http.requestHandler { router.accept(it) }
        http.listen(1664)
        creepsInstance = this.plugin
    }

    override fun start() {
        super.start()
        listen()
    }

    fun init(ctx: RoutingContext): Unit {
        val login: String = ctx.request().getParam("login")
        val rsp: HttpServerResponse = ctx.response().putHeader(
                "content-type", "application/json; charset=utf-8")
        val resp: InitResponse = plugin.game.spawn(login)
        if (resp.error == null) {
            rsp.setStatusCode(200).end(Json.encodePrettily(resp))
        } else {
            rsp.setStatusCode(401).end(Json.encodePrettily(resp))
        }
    }

    fun status(ctx: RoutingContext): Unit {
        ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(200)
                .end(Json.encodePrettily(plugin.game.status()))
    }

    fun gameStatus(ctx: RoutingContext): Unit {
        ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(200)
                .end(Json.encodePrettily(plugin.game.gameStatus()))
    }

    fun report(ctx: RoutingContext): Unit {
        val reportId: String = ctx.request().getParam("reportId")
        ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(200)
                .end(Json.encodePrettily(plugin.game.report(reportId)))
    }

    fun command(ctx: RoutingContext): Unit {
        val login = ctx.request().getParam("login")
        val agentId = ctx.request().getParam("agentId")
        val opcode = ctx.request().getParam("opcode")

        val command = ctx.bodyAsJson

        val response: Response = plugin.game.command(login, agentId, opcode, command)

        ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(200)
                .end(Json.encodePrettily(response))
    }
}
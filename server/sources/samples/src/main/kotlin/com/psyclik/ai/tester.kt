package com.psyclik.ai

import com.psyclik.ai.agent.Probe
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main(args: Array<String>) {
    val login = "psx"
    val ai = AI(1337, "127.0.0.1")
    val vertx = Vertx.vertx(VertxOptions().setBlockedThreadCheckInterval(1000 * 60))

    vertx.deployVerticle(ai) {
        ai.connect(login) { resp ->
            val probe = Probe(resp.probeId!!, resp.startX!!, 1 + resp.startY!!, resp.startZ!!)
            ai.deploy(probe) {
                probe.moveDown { probe.release() }


            }
        }
    }
}
package com.psyclik.ai

import com.psyclik.ai.pylons.Pylon
import com.psyclik.ai.pylons.Settler
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main(args: Array<String>) {
    val login = "psx"
    val ai = AI(1337, "127.0.0.1")
    val vertx = Vertx.vertx(VertxOptions().setBlockedThreadCheckInterval(1000 * 60))

    vertx.deployVerticle(ai) {
        ai.connect(login) { resp ->
            val probe = Settler(ai, resp.probeId!!, resp.startX!!, 1 + resp.startY!!, resp.startZ!!)
            ai.deploy(probe) {
                probe.path("...") {
                    probe.drill()
                }
            }

            val nexus = Pylon(ai, resp.baseId!!, resp.startX!!, resp.startY!!, resp.startZ!!)
                    .pushStrategy { probe -> probe.fundNorthNexus() }
                    .pushStrategy { probe -> probe.fundNorthWestNexus() }
                    .pushStrategy { probe -> probe.fundWestNexus() }
                    .pushStrategy { probe -> probe.fundSouthWestNexus() }
                    .pushStrategy { probe -> probe.fundSouthNexus() }
                    .pushStrategy { probe -> probe.fundSouthEastNexus() }
                    .pushStrategy { probe -> probe.fundEastNexus() }
                    .pushStrategy { probe -> probe.fundNorthEastNexus() }



            ai.deploy(nexus) {
                nexus.spawn()
            }
        }
    }
}
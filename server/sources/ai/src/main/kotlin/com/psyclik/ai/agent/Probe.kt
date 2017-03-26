package com.psyclik.ai.agent

import com.psyclik.ai.data.Report

open class Probe(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {

    fun mine(onError: (String) -> Unit = { error(it) },
             onDeath: (String) -> Unit = { death(it) },
             onReport: (Report) -> Unit = {}) {
        execute("mine", mapOf(), true, onError, onDeath) {
            update(it.location!!)
            onReport.invoke(it)
        }
    }

    fun safeToMine(terrain: String): Boolean {
        return terrain != "LAVA" &&
                terrain != "STATIONARY_WATER" &&
                terrain != "STATIONARY_LAVA" &&
                terrain != "TNT" &&
                terrain != "AIR" &&
                terrain != "BEDROCK"
    }

    fun safeToConvert(terrain: String): Boolean {
        return terrain != "LAVA" &&
                terrain != "STATIONARY_LAVA" &&
                terrain != "TNT"
    }

    fun checkedmine(onError: (String) -> Unit = { error(it) },
                    onDeath: (String) -> Unit = { death(it) },
                    onReport: () -> Unit = {}) {
        block(point()) { point ->
            if (safeToMine(point[point()]!!.type.toString())) {
                mine(onError, onDeath) { onReport.invoke() }
            } else if (safeToConvert(point[point()]!!.type.toString())) {
                convert(onError, onDeath) { onReport.invoke() }
            } else {
                onReport.invoke()
            }
        }

    }

    fun spawnNexus(onError: (String) -> Unit = { error(it) },
                   onDeath: (String) -> Unit = { death(it) },
                   onReport: (Report) -> Unit = {}) {
        execute("spawn:nexus", mapOf(), true, onError, onDeath) {
            onReport.invoke(it)
        }
    }


    fun drill(limit: Int = 10,
              onError: (String) -> Unit = { error(it) },
              onDeath: (String) -> Unit = { death(it) },
              onReach: () -> Unit = {}) {
        if (y > limit) {
            scan3 {
                flatsquare({ continuation -> checkedmine { continuation.invoke() } }) {
                    moveDown { drill(limit, onError, onDeath, onReach) }
                }
            }
        } else {
            onReach.invoke()
        }
    }

    fun spire(limit: Int = 255,
              onError: (String) -> Unit = { error(it) },
              onDeath: (String) -> Unit = { death(it) },
              onReach: () -> Unit = {}) {
        if (y < limit) {
            flatsquare({ continuation -> convert { continuation.invoke() } }) { moveUp { spire(limit, onError, onDeath, onReach) } }
        } else {
            onReach.invoke()
        }
    }
}
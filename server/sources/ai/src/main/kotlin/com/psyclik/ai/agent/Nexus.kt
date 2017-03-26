package com.psyclik.ai.agent

open class Nexus(id: String, x: Int, y: Int, z: Int) : Agent(id, x, y, z) {

    fun spawnProbe(onError: (String) -> Unit = { error(it) },
                   onDeath: (String) -> Unit = { death(it) },
                   onReport: (Probe) -> Unit = {}) {
        execute("spawn:probe", mapOf(), true, onError, onDeath) {

            onReport.invoke(Probe(it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!))
        }
    }

    fun spawnBeacon(onError: (String) -> Unit = { error(it) },
                    onDeath: (String) -> Unit = { death(it) },
                    onReport: (Beacon) -> Unit = {}) {
        execute("spawn:beacon", mapOf(), true, onError, onDeath) {
            onReport.invoke(Beacon(it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!))
        }
    }
}
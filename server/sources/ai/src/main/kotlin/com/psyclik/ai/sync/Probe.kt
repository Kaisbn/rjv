package com.psyclik.ai.sync

import com.psyclik.ai.data.Report

open class Probe(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {

    suspend fun mine(): Report {
        val report = execute("mine")
        update(report.location!!)
        return report
    }

    fun safeToMine(terrain: String): Boolean = terrain != "LAVA" &&
            terrain != "STATIONARY_WATER" &&
            terrain != "STATIONARY_LAVA" &&
            terrain != "TNT" &&
            terrain != "AIR" &&
            terrain != "BEDROCK"

    fun safeToConvert(terrain: String): Boolean = terrain != "LAVA" &&
            terrain != "STATIONARY_LAVA" &&
            terrain != "TNT"


    suspend fun checkedmine(): Report {
        val block = block(point())
        if (safeToMine(block[point()]!!.type.toString())) {
            return mine()
        } else {
            return convert()
        }
    }

    suspend fun spawnNexus() = execute("spawn:nexus")


    suspend fun drill(limit: Int = 10) {
        while (y > limit) {
            scan3()
            flatsquare { checkedmine() }
            moveDown()
        }
    }

    suspend fun spire(limit: Int = 255) {
        while (y < limit) {
            scan3()
            flatsquare { checkedmine() }
            moveUp()
        }
    }
}
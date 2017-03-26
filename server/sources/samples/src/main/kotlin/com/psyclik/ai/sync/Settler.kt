package com.psyclik.ai.sync

import com.psyclik.ai.AI
import com.psyclik.ai.data.Report

class Settler(val ai: AI, id: String, x: Int, y: Int, z: Int) : Probe(id, x, y, z) {

    constructor(ai: AI, probe: Probe) : this(ai, probe.id, probe.x, probe.y, probe.z)


    suspend fun fund(dx: Int, dy: Int, dz: Int, ctor: (Report) -> Pylon) {
        moveTo(x + dx, y + dy, z + dz)
        moveToGround()
        waitForMoney(300, 300)
        val nexus = ctor.invoke(spawnNexus())
        ai.deploy(nexus) { nexus.spawn() }
        moveDown()
        drill(10)
        release()
    }

    suspend fun fundNorthNexus() = fund(0, 0, -12, { Pylon.NorthPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })
    suspend fun fundSouthNexus() = fund(0, 0, 12, { Pylon.SouthPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })
    suspend fun fundEastNexus() = fund(12, 0, 0, { Pylon.EastPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })
    suspend fun fundWestNexus() = fund(-12, 0, 0, { Pylon.WestPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })
    suspend fun fundNorthWestNexus() = fund(-12, 0, -12, { Pylon.NorthWestPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })
    suspend fun fundSouthWestNexus() = fund(-12, 0, 12, { Pylon.SouthWestPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })
    suspend fun fundNorthEastNexus() = fund(12, 0, -12, { Pylon.NorthEastPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })
    suspend fun fundSouthEastNexus() = fund(12, 0, 12, { Pylon.SouthEastPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) })

}
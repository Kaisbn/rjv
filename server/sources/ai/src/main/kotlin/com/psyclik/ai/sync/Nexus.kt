package com.psyclik.ai.sync

import com.psyclik.ai.data.Report

open class Nexus(id: String, x: Int, y: Int, z: Int) : Agent(id, x, y, z) {

    suspend fun spawn(type: String): Report = execute("spawn:$type")

    suspend fun probe() = spawn("probe").let { Probe(it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) }

    suspend fun beacon() = spawn("beacon").let { Beacon(it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) }

    suspend fun scout() = spawn("scout").let { Scout(it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) }

    suspend fun templar() = spawn("templar").let { Templar(it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!) }
}
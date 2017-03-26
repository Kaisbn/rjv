package com.psyclik.ai.sync

open class Beacon(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {

    suspend fun ion() = execute("ion")

    suspend fun laser() = execute("laser")

}
package com.psyclik.ai.sync

open class Scout(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {

    suspend fun scan5() = execute("scan5")
    suspend fun scan9() = execute("scan9")
}
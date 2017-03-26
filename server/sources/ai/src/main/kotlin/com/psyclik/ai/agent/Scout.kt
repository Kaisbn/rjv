package com.psyclik.ai.agent

open class Scout(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {

    fun scan5(onError: (String) -> Unit = { error(it) },
              onDeath: (String) -> Unit = { death(it) },
              onScan: () -> Unit = {}) {
        execute("scan5", mapOf(), onError) {
            it.scan?.forEach { update(it.value) }
            onScan.invoke()
        }
    }

    fun scan9(onError: (String) -> Unit = { error(it) },
              onDeath: (String) -> Unit = { death(it) },
              onScan: () -> Unit = {}) {
        execute("scan9", mapOf(), onError) {
            it.scan?.forEach { update(it.value) }
            onScan.invoke()
        }
    }
}
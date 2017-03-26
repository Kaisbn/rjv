package com.psyclik.ai.agent

import com.psyclik.ai.data.Report

open class Beacon(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {

    fun ion(onError: (String) -> Unit = { error(it) },
            onDeath: (String) -> Unit = { death(it) },
            onReport: (Report) -> Unit = {}) {
        execute("ion", mapOf(), true, onError, onDeath, onReport)
    }

    fun laser(onError: (String) -> Unit = { error(it) },
              onDeath: (String) -> Unit = { death(it) },
              onReport: (Report) -> Unit = {}) {
        execute("laser", mapOf(), true, onError, onDeath, onReport)
    }

}
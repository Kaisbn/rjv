package com.psyclik.ai.agent

import com.psyclik.ai.data.Report
import com.psyclik.ai.data.Sphere

open class Templar(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {

    fun sphere(material: Sphere,
               onError: (String) -> Unit = { error(it) },
               onDeath: (String) -> Unit = { death(it) },
               onReport: (Report) -> Unit = {}) {
        execute("sphere", mapOf("material" to material.string), onError, onDeath, onReport)
    }

    fun lava(onError: (String) -> Unit = { error(it) },
             onDeath: (String) -> Unit = { death(it) },
             onReport: (Report) -> Unit = {}) = sphere(Sphere.LAVA, onError, onDeath, onReport)

    fun sand(onError: (String) -> Unit = { error(it) },
             onDeath: (String) -> Unit = { death(it) },
             onReport: (Report) -> Unit = {}) = sphere(Sphere.SAND, onError, onDeath, onReport)

    fun water(onError: (String) -> Unit = { error(it) },
              onDeath: (String) -> Unit = { death(it) },
              onReport: (Report) -> Unit = {}) = sphere(Sphere.WATER, onError, onDeath, onReport)


}
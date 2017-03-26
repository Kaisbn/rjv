package com.psyclik.ai.sync

import com.psyclik.ai.data.Sphere

open class Templar(id: String, x: Int, y: Int, z: Int) : ScannerAgent(id, x, y, z) {


    suspend fun sphere(material: Sphere) = execute("sphere", mapOf("material" to material.string))

    suspend fun lava() = sphere(Sphere.LAVA)

    suspend fun sand() = sphere(Sphere.SAND)

    suspend fun water() = sphere(Sphere.WATER)


}
package com.psyclik.ai.data

data class Point(val x: Int, val y: Int, val z: Int) {
    constructor(it: Location) : this(it.x!!, it.y!!, it.z!!)
}
package com.psyclik.ai.data

data class Location(
        var type: String? = null,
        var player: String? = null,
        var x: Int? = null,
        var y: Int? = null,
        var z: Int? = null) {
    fun point() = Point(x!!, y!!, z!!)
}
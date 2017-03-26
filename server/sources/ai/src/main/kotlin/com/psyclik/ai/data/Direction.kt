package com.psyclik.ai.data

enum class Direction(val str: String) {
    UP("up"),
    DOWN("down"),
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west");

    fun opcode() = "move$str"
}
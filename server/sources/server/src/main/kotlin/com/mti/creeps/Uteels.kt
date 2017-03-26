package com.mti.creeps

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import java.math.BigInteger
import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */

val random: Random = Random()

fun randomId(): String = BigInteger(130, random).toString(16).substring(0..8)
fun highest(x: Int, z: Int, world: World): Block {
    var y: Int = world.maxHeight
    var block = world.getBlockAt(x, y, z)
    while (block.type == Material.AIR) {
        y -= 1
        block = world.getBlockAt(x, y, z)
    }
    return block
}

fun locationEquals(l1: Location, l2: Location): Boolean = l1.blockX == l2.blockX && l1.blockY == l2.blockY && l1
        .blockZ == l2.blockZ

fun ticksToMs(ticks: Int): Int = 1000 / TICKRATE * ticks
fun material(mat:Material) : String = mat.name.toUpperCase()

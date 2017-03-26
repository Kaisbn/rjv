package com.mti.creeps.action

import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.block.Block

class Beacon(val server: Server, val x: Int, val y: Int, val z: Int) {

    val world = server.worlds[0]

    val blocks: Array<Block?> = Array<Block?>(10, { i -> null })

    fun create(): Unit = execute()
    fun delete(material: Material = Material.AIR): Unit {
        blocks.forEach { block -> block?.setType(material) }
    }

    fun execute(): Unit {
        val beaconBlock = world.getBlockAt(x, y, z)
        blocks[0] = beaconBlock
        beaconBlock.type = Material.BEACON

        val yy = beaconBlock.y - 1
        var ii = 1
        for (i: Int in -1..1) {
            for (j: Int in -1..1) {
                val block = world.getBlockAt(x + i, yy, z + j)
                block.type = Material.DIAMOND_BLOCK
                blocks[ii] = block
                ii++
            }
        }
    }
}
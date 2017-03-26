package com.mti.creeps.action

import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.TNTPrimed

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class IonCannon(val server: Server, val x: Int, val z: Int, val depth: Int, val setFire: Boolean = false) {


    fun execute(): Unit {
        val world: World = server.worlds.get(0)
        var block: Block = world.getHighestBlockAt(x, z)
        val blocks: Array<Block?> = Array(10) { i -> null }

        val beaconBlock = world.getBlockAt(x, block.y, z)
        blocks[0] = beaconBlock
        beaconBlock.type = Material.BEACON

        val yy = block.y - 1
        var ii = 1
        for (i: Int in -1..1) {
            for (j: Int in -1..1) {
                block = world.getBlockAt(x + i, yy, z + j)
                block.type = Material.DIAMOND_BLOCK
                blocks[ii] = block
                ii++
            }
        }

        for (i: Int in 1..depth + 1) {

            block = world.getBlockAt(x, block.y - i, z)
            block.type = Material.AIR
            val tnt: TNTPrimed = world.spawn(block.location, TNTPrimed::class.java)

            tnt.fuseTicks = 50 + (5 * i)
        }

        server.scheduler.runTaskLater(server.pluginManager.getPlugin("creeps"), {
            for (b in blocks) {
                b?.type = Material.AIR
            }
            world.createExplosion(beaconBlock.location, 6f, setFire)
        }, 50)

    }
}
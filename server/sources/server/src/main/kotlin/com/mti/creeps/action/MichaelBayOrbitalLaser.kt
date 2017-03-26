package com.mti.creeps.action

import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.block.Block

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class MichaelBayOrbitalLaser(val server: Server, val x: Int, val z: Int, val duration: Int = 300) {


    fun execute(): Unit {
        val depth = (if (duration > 0) duration * 20 else 300 * 20) / 10
        val world: World = server.worlds.get(0)
        val root: Block = world.getHighestBlockAt(x, z)
        val blocks = Array<Block?>(10) { i -> null }

        val nbExplosions = if (depth > root.y - 3) root.y - 3 else depth
        val start = root.y
        val end = root.y - nbExplosions

        val beaconBlock = world.getBlockAt(x, end, z)
        blocks[0] = beaconBlock

        val beacon = Beacon(server, beaconBlock.x, beaconBlock.y, beaconBlock.z)
        beacon.create()

        var nb: Int = 1
        for (i in 0..nbExplosions + 2) {
            val block = world.getBlockAt(x, start + 3 - i, z)
            block.type = Material.AIR

            server.scheduler.runTaskLater(server.pluginManager.getPlugin("creeps"), {
                world.createExplosion(block.location, 4F, false)
            }, (nb * 5).toLong())
            nb++
        }
        server.scheduler.runTaskLater(server.pluginManager.getPlugin("creeps"), {
            beacon.delete()
        }, (nb * 5).toLong())


    }
}
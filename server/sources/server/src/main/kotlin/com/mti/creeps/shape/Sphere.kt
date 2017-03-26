package com.mti.creeps.shape

import org.bukkit.Location
import org.bukkit.block.Block
import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Sphere(override val origin: Location, val radius: Int) : Shape {
    override fun blocks(): Set<Block> {
        val res = HashSet<Block>()

        val bx = origin.blockX
        val by = origin.blockY
        val bz = origin.blockZ

        for (x in (bx - radius)..(bx + radius)) {
            for (y in (by - radius)..(by + radius)) {
                for (z in (bz - radius)..(bz + radius)) {
                    val dist: Long = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y))).toLong()
                    if (dist < (radius * radius)) {
                        res.add(origin.world.getBlockAt(x, y, z))
                    }
                }
            }
        }

        return res
    }
}
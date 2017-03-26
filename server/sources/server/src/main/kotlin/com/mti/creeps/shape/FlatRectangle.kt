package com.mti.creeps.shape

import org.bukkit.Location
import org.bukkit.block.Block
import java.util.*

/**
 * You may want to add a meaningful comment here.
 * x+ -> east
 * +z -> south
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
open class FlatRectangle(override val origin: Location, val nsSide: Int, val
ewSide: Int) : Shape {

    override fun blocks(): Set<Block> {
        val res: MutableSet<Block> = HashSet()
        val world = origin.world

        for (i: Int in 0..(nsSide * ewSide - 1)) {
            res.add(world.getBlockAt(origin.x.toInt() + (i % ewSide),
                    origin.y.toInt(),
                    origin.z.toInt() + (i / ewSide)))
        }


        return res
    }
}
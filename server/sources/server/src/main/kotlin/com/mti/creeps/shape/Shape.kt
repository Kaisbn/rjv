package com.mti.creeps.shape

import org.bukkit.Location
import org.bukkit.block.Block

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
interface Shape {
    val origin: Location

    fun blocks(): Set<Block>
}
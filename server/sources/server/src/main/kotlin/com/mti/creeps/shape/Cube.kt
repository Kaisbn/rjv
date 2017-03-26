package com.mti.creeps.shape

import org.bukkit.Location

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Cube(origin: Location, side: Int) : Slab(origin, side, side, side)
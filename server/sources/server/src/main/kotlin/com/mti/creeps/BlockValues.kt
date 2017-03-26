package com.mti.creeps

import org.bukkit.Material

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */

data class BlockValues(val biomass: Int, val minerals: Int)

val blockValues: Map<Material, BlockValues> = mapOf(
        Material.SAND to BlockValues(3, 5),
        Material.COBBLESTONE to BlockValues(0, 2),
        Material.STONE to BlockValues(10, 20),
        Material.COAL_ORE to BlockValues(100, 50),
        Material.IRON_ORE to BlockValues(0, 100),
        Material.GOLD_ORE to BlockValues(0, 150),
        Material.LAPIS_ORE to BlockValues(0, 200),
        Material.REDSTONE_ORE to BlockValues(25, 150),
        Material.DIAMOND_ORE to BlockValues(0, 500),
        Material.OBSIDIAN to BlockValues(250, 250),
        Material.BEDROCK to BlockValues(-100000, -100000),
        Material.WOOL to BlockValues(100, 100),

        Material.DIRT to BlockValues(10, 0),
        Material.GRASS to BlockValues(20, 0),
        Material.GRASS_PATH to BlockValues(20, 0),
        Material.LONG_GRASS to BlockValues(30, 0),
        Material.LEAVES to BlockValues(50, 0),
        Material.LEAVES_2 to BlockValues(50, 0),
        Material.WOOD to BlockValues(100, 0),
        Material.CACTUS to BlockValues(100, 0),
        Material.DEAD_BUSH to BlockValues(10, 0),
        Material.RED_ROSE to BlockValues(100, 0),
        Material.YELLOW_FLOWER to BlockValues(100, 0),
        Material.CHORUS_FLOWER to BlockValues(100, 0),
        Material.LOG to BlockValues(100, 0),
        Material.LOG_2 to BlockValues(100, 0),
        Material.PUMPKIN to BlockValues(100, 0),

        Material.WATER to BlockValues(-1, -1),
        Material.STATIONARY_WATER to BlockValues(-100, -100),
        Material.STATIONARY_LAVA to BlockValues(-500, -500),
        Material.LAVA to BlockValues(-500, -500),
        Material.TNT to BlockValues(-500, -500),
        Material.AIR to BlockValues(0, 0))
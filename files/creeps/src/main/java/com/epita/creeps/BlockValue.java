package com.epita.creeps;

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
public enum BlockValue {

    SAND(3, 5),
    COBBLESTONE(0, 2),
    STONE(10, 20),
    COAL_ORE(25, 50),
    IRON_ORE(0, 100),
    GOLD_ORE(0, 150),
    LAPIS_ORE(0, 200),
    REDSTONE_ORE(25, 150),
    DIAMOND_ORE(0, 500),
    OBSIDIAN(250, 250),
    BEDROCK(-1000, -1000),
    WOOL(100, 100),

    DIRT(10, 0),
    GRASS(20, 0),
    LEAVES(50, 0),
    LEAVES_2(50, 0),
    WOOD(100, 0),

    WATER(-1, -1),
    STATIONARY_WATER(-100, -100),
    STATIONARY_LAVA(-500, -500),
    LAVA(-500, -500),
    TNT(-500, -500),
    AIR(-1, -1);

    private Integer biomass;

    private Integer minerals;

    BlockValue(Integer biomass, Integer minerals) {
        this.biomass = biomass;
        this.minerals = minerals;
    }

    public Integer getBiomass() {
        return biomass;
    }

    public Integer getMinerals() {
        return minerals;
    }
}

    

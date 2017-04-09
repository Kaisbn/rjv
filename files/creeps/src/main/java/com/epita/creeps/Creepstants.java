package com.epita.creeps;

/**
 * Here you will find all the necessary constants to run a proper IA of creeps. <p>All durations are given in ticks.</p>
 * ticks.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
public class Creepstants {

    /**
     * Ticks per second.
     */
    public static Integer TICKRATE = 20;

    public static Integer PLAYER_START_BIOMASS = 300;

    public static Integer PLAYER_START_MINERALS = 300;


    // Spawns -------------------------------------------------------------------------------------------------------------

    public static Integer PROBE_MINERAL = 50;

    public static Integer PROBE_BIOMASS = 50;

    public static Integer PROBE_SPAWNTIME = TICKRATE * 15;

    public static Integer SCOUT_MINERAL = 150;

    public static Integer SCOUT_BIOMASS = 0;

    public static Integer SCOUT_SPAWNTIME = TICKRATE * 5;

    public static Integer TEMPLAR_MINERAL = 100;

    public static Integer TEMPLAR_BIOMASS = 300;

    public static Integer TEMPLAR_SPAWNTIME = TICKRATE * 30;

    public static Integer NEXUS_MINERAL = 300;

    public static Integer NEXUS_BIOMASS = 300;

    public static Integer NEXUS_SPAWNTIME = TICKRATE * 15;


    // Actions ------------------------------------------------------------------------------------------------------------

    public static Integer NOOP = TICKRATE * 1;

    public static Integer DEFAULT_CONVERT = TICKRATE / 2;

    public static Integer DEFAULT_MINE = TICKRATE * 1;

    public static Integer DEFAULT_MOVE = TICKRATE * 1;

    public static Integer DEFAULT_PLAYERSTATUS = TICKRATE / 1;

    public static Integer DEFAULT_SCAN3 = TICKRATE / 1;

    public static Integer DEFAULT_SCAN5 = TICKRATE * 2;

    public static Integer DEFAULT_SCAN9 = TICKRATE * 4;

    public static Integer DEFAULT_STATUS = TICKRATE / 2;

    public static Integer DEFAULT_SPHERE = TICKRATE * 10;

    public static Integer DEFAULT_IONDISCHARGE = TICKRATE * 10;

    public static Integer DEFAULT_ORBITALLASER = TICKRATE * 20;
}

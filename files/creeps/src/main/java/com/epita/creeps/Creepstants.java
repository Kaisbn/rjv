package com.epita.creeps;

/**
 * Here you will find all the necessary constants to run a proper IA of creeps.
 * All durations are given in ticks.
 *
 * To convert those constants into milliseconds, the formula is `(1000 * CONSTANT) / TICKRATE`
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
public class Creepstants {

// Conf ---------------------------------------------------------------------------------------------------------------

    public static Integer TICKRATE = 20;
    public static Integer TIMEOUT = 30 * 1000;
    public static Integer ALLOWED_MISSES = 100;
    public static Integer PLAYER_START_BIOMASS = 200;
    public static Integer PLAYER_START_MINERALS = 200;

// Spawns -------------------------------------------------------------------------------------------------------------

    public static Integer PROBE_MINERAL = 50;
    public static Integer PROBE_BIOMASS = 5;
    public static Integer PROBE_SPAWNTIME = TICKRATE * 15;

    public static Integer SCOUT_MINERAL = 150;
    public static Integer SCOUT_BIOMASS = 0;
    public static Integer SCOUT_SPAWNTIME = TICKRATE * 5;

    public static Integer TEMPLAR_MINERAL = 100;
    public static Integer TEMPLAR_BIOMASS = 300;
    public static Integer TEMPLAR_SPAWNTIME = TICKRATE * 30;

    public static Integer BEACON_MINERAL = 200;
    public static Integer BEACON_BIOMASS = 500;
    public static Integer BEACON_SPAWNTIME = TICKRATE * 30;

    public static Integer NEXUS_MINERAL = 300;
    public static Integer NEXUS_BIOMASS = 300;
    public static Integer NEXUS_SPAWNTIME = TICKRATE * 15;

// Actions ------------------------------------------------------------------------------------------------------------

    public static Integer NOOP_TIME = TICKRATE * 1;
    public static Integer RELEASE_TIME = TICKRATE * 1;
    public static Integer DEFAULT_CONVERT_TIME = TICKRATE / 2;
    public static Integer DEFAULT_MINE_TIME = TICKRATE * 1;
    public static Integer DEFAULT_MOVE_TIME = TICKRATE / 2;
    public static Integer DEFAULT_PLAYERSTATUS_TIME = TICKRATE * 1;
    public static Integer DEFAULT_SCAN3_TIME = TICKRATE * 1;
    public static Integer DEFAULT_SCAN5_TIME = TICKRATE * 2;
    public static Integer DEFAULT_SCAN9_TIME = TICKRATE * 4;
    public static Integer DEFAULT_STATUS_TIME = TICKRATE / 2;
    public static Integer DEFAULT_SPHERE_TIME = TICKRATE * 10;
    public static Integer DEFAULT_IONDISCHARGE_TIME = TICKRATE * 10;
    public static Integer DEFAULT_ORBITALLASER_TIME = TICKRATE * 20;
}

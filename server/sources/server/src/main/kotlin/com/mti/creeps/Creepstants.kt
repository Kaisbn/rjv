package com.mti.creeps

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */

// Conf ---------------------------------------------------------------------------------------------------------------

val TICKRATE: Int = 20;
val TIMEOUT: Int = 30 * 1000;
val ALLOWED_MISSES: Int = 100
val PLAYER_START_BIOMASS: Int = 200
val PLAYER_START_MINERALS: Int = 200


// Spawns -------------------------------------------------------------------------------------------------------------

val PROBE_MINERAL: Int = 50;
val PROBE_BIOMASS: Int = 5;
val PROBE_SPAWNTIME: Int = TICKRATE * 15;
val PROBE_SPAWNTIME_READYUP: Int = TICKRATE * 0;

val SCOUT_MINERAL: Int = 150;
val SCOUT_BIOMASS: Int = 0;
val SCOUT_SPAWNTIME: Int = TICKRATE * 5;
val SCOUT_SPAWNTIME_READYUP: Int = TICKRATE * 0;

val TEMPLAR_MINERAL: Int = 100;
val TEMPLAR_BIOMASS: Int = 300;
val TEMPLAR_SPAWNTIME: Int = TICKRATE * 30;
val TEMPLAR_SPAWNTIME_READYUP: Int = TICKRATE * 0;

val BEACON_MINERAL: Int = 200;
val BEACON_BIOMASS: Int = 500;
val BEACON_SPAWNTIME: Int = TICKRATE * 30;
val BEACON_SPAWNTIME_READYUP: Int = TICKRATE * 0;

val NEXUS_MINERAL: Int = 300;
val NEXUS_BIOMASS: Int = 300;
val NEXUS_SPAWNTIME: Int = TICKRATE * 15;
val NEXUS_SPAWNTIME_READYUP: Int = TICKRATE * 0;

val PYLON_MINERAL: Int = 100;
val PYLON_BIOMASS: Int = 50;
val PYLON_SPAWNTIME: Int = TICKRATE * 8;
val PYLON_SPAWNTIME_READYUP: Int = TICKRATE * 0;


// Actions ------------------------------------------------------------------------------------------------------------

val NOOP_POSTTIME: Int = TICKRATE * 0;
val NOOP_PRETIME: Int = TICKRATE * 1;

val RELEASE_POSTTIME: Int = TICKRATE * 0;
val RELEASE_PRETIME: Int = TICKRATE * 1;

val DEFAULT_CONVERT_POSTTIME: Int = TICKRATE * 0;
val DEFAULT_CONVERT_PRETIME: Int = TICKRATE / 2;

val DEFAULT_MINE_POSTTIME: Int = TICKRATE * 0;
val DEFAULT_MINE_PRETIME: Int = TICKRATE * 1;

val DEFAULT_MOVE_POSTTIME: Int = TICKRATE * 0;
val DEFAULT_MOVE_PRETIME: Int = TICKRATE / 2;

val DEFAULT_PLAYERSTATUS_POSTTIME: Int = TICKRATE / 2;
val DEFAULT_PLAYERSTATUS_PRETIME: Int = TICKRATE / 2;

val DEFAULT_SCAN3_POSTTIME: Int = TICKRATE / 2;
val DEFAULT_SCAN3_PRETIME: Int = TICKRATE / 2;

val DEFAULT_SCAN5_POSTTIME: Int = TICKRATE;
val DEFAULT_SCAN5_PRETIME: Int = TICKRATE;

val DEFAULT_SCAN9_POSTTIME: Int = TICKRATE * 2;
val DEFAULT_SCAN9_PRETIME: Int = TICKRATE * 2;

val DEFAULT_STATUS_POSTTIME: Int = TICKRATE / 4;
val DEFAULT_STATUS_PRETIME: Int = TICKRATE / 4;

val DEFAULT_SPHERE_POSTTIME: Int = TICKRATE * 5;
val DEFAULT_SPHERE_PRETIME: Int = TICKRATE * 5;

val DEFAULT_IONDISCHARGE_POSTTIME: Int = TICKRATE * 5;
val DEFAULT_IONDISCHARGE_PRETIME: Int = TICKRATE * 5;

val DEFAULT_ORBITALLASER_POSTTIME: Int = TICKRATE * 10;
val DEFAULT_ORBITALLASER_PRETIME: Int = TICKRATE * 10;
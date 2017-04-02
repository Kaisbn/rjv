package com.psyclik.ai.data

import com.mti.creeps.*

val DURATIONS = mapOf(
        "status" to Pair(DEFAULT_STATUS_PRETIME, DEFAULT_STATUS_POSTTIME),
        "playerstatus" to Pair(DEFAULT_PLAYERSTATUS_PRETIME, DEFAULT_PLAYERSTATUS_POSTTIME),
        "movenorth" to Pair(DEFAULT_MOVE_PRETIME, DEFAULT_MOVE_POSTTIME),
        "movesouth" to Pair(DEFAULT_MOVE_PRETIME, DEFAULT_MOVE_POSTTIME),
        "moveeast" to Pair(DEFAULT_MOVE_PRETIME, DEFAULT_MOVE_POSTTIME),
        "movewest" to Pair(DEFAULT_MOVE_PRETIME, DEFAULT_MOVE_POSTTIME),
        "moveup" to Pair(DEFAULT_MOVE_PRETIME, DEFAULT_MOVE_POSTTIME),
        "movedown" to Pair(DEFAULT_MOVE_PRETIME, DEFAULT_MOVE_POSTTIME),
        "mine" to Pair(DEFAULT_MINE_PRETIME, DEFAULT_MINE_POSTTIME),
        "convert" to Pair(DEFAULT_CONVERT_PRETIME, DEFAULT_CONVERT_POSTTIME),
        "scan" to Pair(DEFAULT_SCAN3_PRETIME, DEFAULT_SCAN3_POSTTIME),
        "scan5" to Pair(DEFAULT_SCAN5_PRETIME, DEFAULT_SCAN5_POSTTIME),
        "scan9" to Pair(DEFAULT_SCAN9_PRETIME, DEFAULT_SCAN9_POSTTIME),
        "noop" to Pair(NOOP_PRETIME, NOOP_POSTTIME),
        "sphere" to Pair(DEFAULT_SPHERE_PRETIME, DEFAULT_SPHERE_POSTTIME),
        "ion" to Pair(DEFAULT_IONDISCHARGE_PRETIME, DEFAULT_IONDISCHARGE_POSTTIME),
        "laser" to Pair(DEFAULT_ORBITALLASER_PRETIME, DEFAULT_ORBITALLASER_POSTTIME),
        "release" to Pair(RELEASE_PRETIME, RELEASE_POSTTIME),
        "spawn:probe" to Pair(PROBE_SPAWNTIME, PROBE_SPAWNTIME_READYUP),
        "spawn:scout" to Pair(SCOUT_SPAWNTIME, SCOUT_SPAWNTIME_READYUP),
        "spawn:templar" to Pair(TEMPLAR_SPAWNTIME, TEMPLAR_SPAWNTIME_READYUP),
        "spawn:beacon" to Pair(BEACON_SPAWNTIME, BEACON_SPAWNTIME_READYUP),
        "spawn:nexus" to Pair(NEXUS_SPAWNTIME, NEXUS_SPAWNTIME_READYUP))
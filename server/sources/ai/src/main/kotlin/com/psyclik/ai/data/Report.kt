package com.psyclik.ai.data

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import kotlin.reflect.KMutableProperty

data class Report(
        var reportId: String = "",                  // Report ID
        var login: String? = null,                  // Player ID
        var id: String? = null,                     // Agent ID
        var opcode: String? = null,                 // Opcode
        var biomass: Int? = null,                   // Global Biomass
        var minerals: Int? = null,                  // Global Minerals
        var location: Location? = null,             // Action Location
        var type: String? = null,                   // Agent type
        var status: String? = null,                 // Agent status
        var mineralsEarned: Int? = null,            // Minerals earned by the command
        var biomassEarned: Int? = null,             // Biomass earned by the command
        var scan: Map<String, Location>? = null,    // Scan result
        var biome:String? = null,                   // Current biome
        var causeOfDeath: String? = null,           // Cause of agent's death
        var error: String? = null) {                // Error code

    fun fromJson(raw: String): Report = Json.decodeValue(raw, Report::class.java)

}


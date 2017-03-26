package com.mti.creeps.response

import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
data class GameStatusResponse(var pluginRunning: Boolean, var gameRunning: Boolean, var scores: Map<String, Map<String, Int>>) :
        Response {
    constructor() : this(true, false, Collections.emptyMap<String, Map<String, Int>>())
}
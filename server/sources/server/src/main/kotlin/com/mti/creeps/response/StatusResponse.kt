package com.mti.creeps.response

import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
data class StatusResponse(
        var pluginRunning: Boolean,
        var gameRunning: Boolean,
        var scores: Map<String, Int>
) : Response
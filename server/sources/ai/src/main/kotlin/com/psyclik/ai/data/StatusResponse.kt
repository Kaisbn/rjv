package com.psyclik.ai.data

import java.util.*

data class StatusResponse(var pluginRunning: Boolean, var gameRunning: Boolean, var scores: Map<String, Int>) : Response {
    constructor() : this(true, false, Collections.emptyMap<String, Int>())
}
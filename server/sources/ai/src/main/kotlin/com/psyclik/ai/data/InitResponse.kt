package com.psyclik.ai.data

data class InitResponse(
        var error: String?,
        var login: String?,
        var color: String?,
        var startX: Int?,
        var startY: Int?,
        var startZ: Int?,
        var baseId: String?,
        var probeId: String?) : Response {
    constructor(error: String) : this(error, null, null, null, null, null, null, null)

    constructor() : this(null, null, null, null, null, null, null, null)

    constructor(login: String,
                color: String,
                startX: Int,
                startY: Int,
                startZ: Int,
                baseId: String,
                probeId: String) : this(null, login, color, startX, startY, startZ, baseId, probeId)
}

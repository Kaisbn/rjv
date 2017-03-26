package com.psyclik.ai.data

interface Response
data class NoRespomse(val repcode: String = "noresponse") : Response
data class ActionResponse(
        var opcode: String = "",
        var reportId: String = "",
        var error: String = "",
        var login: String = "",
        var id: String = "",
        var misses: Int = 0) : Response {
    constructor() : this("", "", "")
    constructor(repcode: String, reportId: String) : this(repcode, reportId, "")
}
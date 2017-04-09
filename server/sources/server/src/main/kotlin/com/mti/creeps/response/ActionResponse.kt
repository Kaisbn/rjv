package com.mti.creeps.response

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
data class ActionResponse(
        var opcode: String = "",
        var reportId: String = "",
        var error: String?,
        var login: String = "",
        var id: String = "",
        var misses: Int = 0) : Response {

    constructor(opcode: String, reportId: String) : this(opcode, reportId, null)
}
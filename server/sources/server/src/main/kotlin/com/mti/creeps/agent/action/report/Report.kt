package com.mti.creeps.agent.action.report

import com.mti.creeps.response.Response

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
open class Report(
        var opcode: String,
        var reportId: String,
        var id: String,
        var login: String) : Response {
    constructor() : this("", "", "", "")
}
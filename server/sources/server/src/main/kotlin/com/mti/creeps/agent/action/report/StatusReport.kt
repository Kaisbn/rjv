package com.mti.creeps.agent.action.report

import com.mti.creeps.agent.BlockStatus

class StatusReport(
        reportId: String,
        id: String,
        login: String,
        var status: String,
        var causeOfDeath: String?,
        var location: BlockStatus?) :
        Report("status", reportId, id, login) {
    constructor() : this("", "", "", "", null, null)
}
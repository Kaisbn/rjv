package com.mti.creeps.agent.action.report

import com.mti.creeps.agent.BlockStatus

class ConvertReport(
        reportId: String,
        id: String,
        login: String,
        var status: String?,
        val causeOfDeath: String?,
        var location: BlockStatus?) : Report("convert", reportId, id, login) {
    constructor() : this("", "", "", "", "", null)
}
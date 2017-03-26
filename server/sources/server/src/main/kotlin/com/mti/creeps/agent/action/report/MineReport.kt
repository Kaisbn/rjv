package com.mti.creeps.agent.action.report

import com.mti.creeps.agent.BlockStatus

class MineReport(reportId: String,
                 id: String,
                 login: String,
                 val mineralsEarned: Int,
                 val biomassEarned: Int,
                 var status: String?,
                 val causeOfDeath: String?,
                 var location: BlockStatus?) : Report("mine", reportId, id, login) {
    constructor() : this("", "", "", 0, 0, "", "", null)
}
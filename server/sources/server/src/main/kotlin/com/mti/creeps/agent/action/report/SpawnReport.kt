package com.mti.creeps.agent.action.report

import com.mti.creeps.agent.BlockStatus

class SpawnReport(reportId: String,
                  id: String,
                  login: String,
                  var type: String,
                  var location: BlockStatus?,
                  val error: String? = "") :
        Report("spawn", reportId, id, login) {
    constructor() : this("", "", "", "", null, "")
}
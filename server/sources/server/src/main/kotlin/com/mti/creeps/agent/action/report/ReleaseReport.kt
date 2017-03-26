package com.mti.creeps.agent.action.report

class ReleaseReport(
        reportId: String,
        id: String,
        login: String,
        var biomass: Int,
        var minerals: Int) : Report("release", reportId, id, login) {
    constructor() : this("", "", "", 0, 0)
}
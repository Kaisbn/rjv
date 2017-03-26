package com.mti.creeps.agent.action.report

class PlayerStatusReport(
        reportId: String,
        id: String,
        login: String,
        var biomass: Int,
        var minerals: Int) :
        Report("playerstatus", reportId, id, login) {
    constructor() : this("", "", "", -1, -1)
}
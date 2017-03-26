package com.mti.creeps.agent.action.report


class LaserReport(reportId: String, id: String, login: String) : Report("laser", reportId, id, login) {
    constructor() : this("", "", "")
}
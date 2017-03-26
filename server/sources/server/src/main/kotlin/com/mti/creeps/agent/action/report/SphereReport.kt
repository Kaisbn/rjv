package com.mti.creeps.agent.action.report


class SphereReport(
        reportId: String,
        id: String,
        login: String) :
        Report("sphere", reportId, id, login) {
    constructor() : this("", "", "")
}
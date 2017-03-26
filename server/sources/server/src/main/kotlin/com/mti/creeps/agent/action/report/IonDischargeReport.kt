package com.mti.creeps.agent.action.report

class IonDischargeReport(reportId: String, id: String, login: String) : Report("ion", reportId, id,
        login) {
    constructor() : this("", "", "")
}
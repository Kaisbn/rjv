package com.mti.creeps.agent.action.report

class TransferReport(
        reportId: String,
        id: String,
        login: String) : Report("transfer", reportId, id, login) {
    constructor() : this("", "", "")
}
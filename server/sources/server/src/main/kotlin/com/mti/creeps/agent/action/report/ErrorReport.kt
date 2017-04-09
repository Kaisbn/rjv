package com.mti.creeps.agent.action.report

class ErrorReport(
        error: String?,
        opcode: String = "",
        reportId: String = "",
        id: String = "",
        login: String = "") : Report(opcode, reportId, id, login)
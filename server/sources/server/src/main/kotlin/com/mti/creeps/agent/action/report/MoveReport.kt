package com.mti.creeps.agent.action.report

import com.mti.creeps.agent.BlockStatus

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class MoveReport(
        reportId: String,
        id: String,
        login: String,
        var location: BlockStatus?) :
        Report("move", reportId, id, login) {
    constructor() : this("", "", "", null)
}
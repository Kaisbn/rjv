package com.mti.creeps.agent.action.report

import com.mti.creeps.agent.BlockStatus
import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class ScanReport(
        reportId: String,
        id: String,
        login: String,
        var biome: String,
        var scan: Map<String, BlockStatus>) : Report("scan", reportId, id, login) {
    constructor() : this("", "", "", "", Collections.emptyMap())
}
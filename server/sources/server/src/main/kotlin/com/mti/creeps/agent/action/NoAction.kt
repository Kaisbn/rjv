package com.mti.creeps.agent.action

import com.mti.creeps.NOOP_POSTTIME
import com.mti.creeps.NOOP_PRETIME
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.Report
import com.mti.creeps.randomId
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class NoAction() : Action<NoAction.NoopReport> {
    override val opcode: String = "noop"
    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = NOOP_POSTTIME
    override val preExecuteTime: Int = NOOP_PRETIME

    override fun perform(agent: Agent, server: Server, world: World): NoAction.NoopReport {
        server.logger.info("${agent.id} here! I did nothing, but I did it well.")
        return NoopReport(reportId, agent.id, agent.player.login)
    }

    class NoopReport(reportId: String, id: String, login: String) : Report("noop", reportId, id, login) {
        constructor() : this("", "", "")
    }

}
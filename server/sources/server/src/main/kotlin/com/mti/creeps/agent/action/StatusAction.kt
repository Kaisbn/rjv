package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_STATUS_POSTTIME
import com.mti.creeps.DEFAULT_STATUS_PRETIME
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.StatusReport
import com.mti.creeps.agent.status
import com.mti.creeps.randomId
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class StatusAction : Action<StatusReport> {
    override val opcode: String = "status"
    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_STATUS_POSTTIME
    override val preExecuteTime: Int = DEFAULT_STATUS_PRETIME

    override fun perform(agent: Agent, server: Server, world: World): StatusReport {
        return StatusReport(reportId, agent.id, agent.player.login, if (agent.causeOfDeath == null) "alive" else "dead",
                agent.causeOfDeath, status(agent.location.block))
    }
}
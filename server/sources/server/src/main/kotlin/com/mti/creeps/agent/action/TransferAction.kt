package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_SPHERE_POSTTIME
import com.mti.creeps.DEFAULT_SPHERE_PRETIME
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.Pylon
import com.mti.creeps.agent.action.report.TransferReport
import com.mti.creeps.locationEquals
import com.mti.creeps.randomId
import io.vertx.core.json.JsonObject
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class TransferAction : Action<TransferReport> {
    override val opcode: String = "transfer"

    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_SPHERE_POSTTIME
    override val preExecuteTime: Int = DEFAULT_SPHERE_PRETIME
    var targetId: String? = null
    var agentId: String? = null

    override fun initialize(json: JsonObject): Boolean {
        targetId = json.getString("targetId")
        agentId = json.getString("agentId")
        return super.initialize(json)
    }

    override fun perform(agent: Agent, server: Server, world: World): TransferReport {
        if (agent.player.agents[targetId] is Pylon && agent.player.agents[agentId] != null) {
            val ag: Agent = agent.player.agents[agentId]!!;
            if (locationEquals(ag.location, agent.location)) {
                ag.location = agent.player.agents[targetId]!!.location
                return TransferReport(reportId, agentId!!, agent.player.login)
            }
            return TransferReport(reportId, agentId!!, agent.player.login)
        }
        return TransferReport(reportId, agentId!!, agent.player.login)
    }

}
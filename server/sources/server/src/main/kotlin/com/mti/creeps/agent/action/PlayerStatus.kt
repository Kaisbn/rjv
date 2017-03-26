package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_PLAYERSTATUS_POSTTIME
import com.mti.creeps.DEFAULT_PLAYERSTATUS_PRETIME
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.PlayerStatusReport
import com.mti.creeps.randomId
import org.bukkit.Particle
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class PlayerStatus() : Action<PlayerStatusReport> {
    override val reportId: String = randomId()
    override val opcode: String = "playerstatus"
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_PLAYERSTATUS_POSTTIME
    override val preExecuteTime: Int = DEFAULT_PLAYERSTATUS_PRETIME

    override fun perform(agent: Agent, server: Server, world: World): PlayerStatusReport {
        world.spawnParticle(Particle.END_ROD, agent.location, 20)
        return PlayerStatusReport(reportId, agent.id, agent.player.login, agent.player.biomass, agent.player.minerals)
    }

}
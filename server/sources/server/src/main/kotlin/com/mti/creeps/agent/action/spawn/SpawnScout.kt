package com.mti.creeps.agent.action.spawn

import com.mti.creeps.*
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.Scout
import com.mti.creeps.agent.action.Action
import com.mti.creeps.agent.action.report.SpawnReport
import com.mti.creeps.agent.status
import org.bukkit.Particle
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class SpawnScout() : Action<SpawnReport> {
    override val costBiomass: Int = SCOUT_BIOMASS
    override val costMineral: Int = SCOUT_MINERAL
    override val postExecuteTime: Int = SCOUT_SPAWNTIME_READYUP
    override val preExecuteTime: Int = SCOUT_SPAWNTIME
    override val reportId: String = randomId()
    override val opcode: String = "spawn:scout"


    override fun perform(agent: Agent, server: Server, world: World): SpawnReport {
        val agent: Scout = Scout(agent.player, randomId(), agent.location.clone())
        agent.player.agents[agent.id] = agent
        world.spawnParticle(Particle.HEART, agent.location, 20)
        return SpawnReport(reportId, agent.id, agent.player.login, agent.codename(), status(agent.location.block))
    }
}
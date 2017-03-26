package com.mti.creeps.agent.action.spawn

import com.mti.creeps.*
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.Probe
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
class SpawnProbe() : Action<SpawnReport> {
    override val costBiomass: Int = PROBE_BIOMASS
    override val costMineral: Int = PROBE_MINERAL
    override val postExecuteTime: Int = PROBE_SPAWNTIME_READYUP
    override val preExecuteTime: Int = PROBE_SPAWNTIME
    override val reportId: String = randomId()
    override val opcode: String = "spawn:probe"


    override fun perform(agent: Agent, server: Server, world: World): SpawnReport {
        val probe: Probe = Probe(agent.player, randomId(), agent.location.clone())
        agent.player.agents[probe.id] = probe
        world.spawnParticle(Particle.HEART, agent.location, 20)
        return SpawnReport(reportId, probe.id, agent.player.login, probe.codename(), status(agent.location.block))
    }
}
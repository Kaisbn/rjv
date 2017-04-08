package com.mti.creeps.agent.action

import com.mti.creeps.RELEASE_POSTTIME
import com.mti.creeps.RELEASE_PRETIME
import com.mti.creeps.agent.*
import com.mti.creeps.agent.action.report.ReleaseReport
import com.mti.creeps.randomId
import org.bukkit.Particle
import org.bukkit.Server
import org.bukkit.World

class ReleaseAction() : Action<ReleaseReport> {
    override val opcode: String = "release"
    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = RELEASE_POSTTIME
    override val preExecuteTime: Int = RELEASE_PRETIME

    override fun perform(agent: Agent, server: Server, world: World): ReleaseReport {
        agent.player.deleteAgent(agent, "release")

        when (agent) {
            is Probe -> agent.player.updateResources(5, 20)
            is Scout -> agent.player.updateResources(0, 50)
            is Nexus -> agent.player.updateResources(50, 50)
            is Templar -> agent.player.updateResources(10, 30)
            is Beacon -> agent.player.updateResources(10, 25)
        }

        world.spawnParticle(Particle.CRIT_MAGIC, agent.location, 20)
        return ReleaseReport(reportId, agent.id, agent.player.login, agent.player.biomass, agent.player.minerals)
    }


}
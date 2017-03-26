package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_ORBITALLASER_POSTTIME
import com.mti.creeps.DEFAULT_ORBITALLASER_PRETIME
import com.mti.creeps.action.MichaelBayOrbitalLaser
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.LaserReport
import com.mti.creeps.randomId
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class OrbitalLaser : Action<LaserReport> {

    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_ORBITALLASER_POSTTIME
    override val preExecuteTime: Int = DEFAULT_ORBITALLASER_PRETIME
    override val opcode: String = "laser"


    override fun perform(agent: Agent, server: Server, world: World): LaserReport {
        val e = MichaelBayOrbitalLaser(server, agent.location.blockX, agent.location.blockZ)
        e.execute()
        return LaserReport(reportId, agent.id, agent.player.login)
    }

}
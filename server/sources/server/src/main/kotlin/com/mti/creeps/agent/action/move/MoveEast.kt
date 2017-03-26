package com.mti.creeps.agent.action.move

import com.mti.creeps.DEFAULT_MOVE_POSTTIME
import com.mti.creeps.DEFAULT_MOVE_PRETIME
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.Action
import com.mti.creeps.agent.action.report.MoveReport
import com.mti.creeps.agent.status
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
class MoveEast : Action<MoveReport> {
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_MOVE_POSTTIME
    override val preExecuteTime: Int = DEFAULT_MOVE_PRETIME
    override val reportId: String = randomId()
    override val opcode: String = "moveeast"

    override fun perform(agent: Agent, server: Server, world: World): MoveReport {
        agent.location.x += 1
        world.spawnParticle(Particle.VILLAGER_HAPPY, agent.location, 20)
        return MoveReport(reportId, agent.id, agent.player.login, status(world.getBlockAt(agent.location)))
    }
}
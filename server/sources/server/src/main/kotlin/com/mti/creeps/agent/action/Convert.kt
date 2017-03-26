package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_CONVERT_POSTTIME
import com.mti.creeps.DEFAULT_CONVERT_PRETIME
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.ConvertReport
import com.mti.creeps.agent.status
import com.mti.creeps.randomId
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Convert() : Action<ConvertReport> {
    override val opcode: String = "convert"

    override val postExecuteTime: Int = DEFAULT_CONVERT_POSTTIME
    override val preExecuteTime: Int = DEFAULT_CONVERT_PRETIME
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val reportId: String = randomId()

    override fun perform(agent: Agent, server: Server, world: World): ConvertReport {
        val block = world.getBlockAt(agent.location)

        when (block.type) {
            Material.TNT -> {
                world.createExplosion(agent.location, 8f)
                agent.player.deleteAgent(agent, "Agent tried to convert TNT")
                return ConvertReport(reportId, agent.id, agent.player.login, "dead", "tnt", status(block))
            }

            Material.LAVA, Material.STATIONARY_LAVA -> {
                agent.player.deleteAgent(agent, "Agent tried to convert lava")
                return ConvertReport(reportId, agent.id, agent.player.login, "dead", "lava", status(block))
            }
        }

        block.type = Material.WOOL
        block.data = agent.player.color.dyeData
        agent.player.converted.add(block.location)
        world.spawnParticle(Particle.CLOUD, agent.location, 20)
        return ConvertReport(reportId, agent.id, agent.player.login, "alive", null, status(block))

    }
}
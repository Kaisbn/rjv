package com.mti.creeps.agent.action

import com.mti.creeps.*
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.MineReport
import com.mti.creeps.agent.status
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
class Mine : Action<MineReport> {
    override val opcode: String = "mine"

    override val postExecuteTime: Int = DEFAULT_MINE_POSTTIME
    override val preExecuteTime: Int = DEFAULT_MINE_PRETIME
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val reportId: String = randomId()

    override fun perform(agent: Agent, server: Server, world: World): MineReport {
        val block = world.getBlockAt(agent.location)
        val material = block.type
        block.type = Material.WOOL
        block.data = agent.player.color.dyeData


        val values: BlockValues = if (agent.player.converted.contains(block.location)) BlockValues(-100, -100) else
            blockValues[material] ?: BlockValues(0, 0)

        agent.player.converted.add(block.location)
        agent.player.updateResources(values.biomass, values.minerals)

        var status: String = "alive"
        var deathReason: String? = null

        when (material) {
            Material.TNT -> {
                world.createExplosion(agent.location, 8f)
                agent.player.deleteAgent(agent, "tnt")
                status = "dead"
                deathReason = "tnt"
            }

            Material.LAVA, Material.STATIONARY_LAVA -> {
                agent.player.deleteAgent(agent, "lava")
                status = "dead"
                deathReason = "lava"
            }
        }

        world.spawnParticle(Particle.CLOUD, agent.location, 20)
        return MineReport(reportId, agent.id, agent.player.login, values.minerals, values.biomass, status, deathReason, status(block))
    }
}
package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_SCAN3_POSTTIME
import com.mti.creeps.DEFAULT_SCAN3_PRETIME
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.BlockStatus
import com.mti.creeps.agent.action.report.ScanReport
import com.mti.creeps.agent.status
import com.mti.creeps.randomId
import com.mti.creeps.shape.Cube
import org.bukkit.Particle
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.block.Block
import java.util.*

class Scan() : Action<ScanReport> {
    override val opcode: String = "scan"
    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_SCAN3_POSTTIME
    override val preExecuteTime: Int = DEFAULT_SCAN3_PRETIME

    override fun perform(agent: Agent, server: Server, world: World): ScanReport {
        val blocks: Set<Block> = Cube(agent.location.clone().add(-1.0, -1.0, -1.0), 3).blocks()
        val biome = agent.location.block.biome.name
        val res: MutableMap<String, BlockStatus> = HashMap()

        blocks.forEach {
            res.put("${it.location.x.toInt()},${it.location.y.toInt()},${it.location.z.toInt()}", status(it))
        }

        world.spawnParticle(Particle.DRAGON_BREATH, agent.location, 20)
        return ScanReport(reportId, agent.id, agent.player.login, biome, res)
    }


}
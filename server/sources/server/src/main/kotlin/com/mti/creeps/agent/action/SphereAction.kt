package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_SPHERE_POSTTIME
import com.mti.creeps.DEFAULT_SPHERE_PRETIME
import com.mti.creeps.action.CreateSphereAction
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.SphereReport
import com.mti.creeps.randomId
import io.vertx.core.json.JsonObject
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class SphereAction : Action<SphereReport> {
    override val opcode: String = "sphere"

    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_SPHERE_POSTTIME
    override val preExecuteTime: Int = DEFAULT_SPHERE_PRETIME

    var material: Material = Material.AIR

    override fun initialize(json: JsonObject): Boolean {
        when (json.getString("material").toLowerCase()) {
            "sand" -> material = Material.SAND
            "water" -> material = Material.WATER
            "lava" -> material = Material.LAVA
            "tnt" -> material = Material.TNT
        }
        return super.initialize(json)
    }

    override fun body(): String = "{\"material\":\"${material.name.toLowerCase()}\"}"

    override fun perform(agent: Agent, server: Server, world: World): SphereReport {
        CreateSphereAction(agent.location.clone(), 10, material).execute()
        return SphereReport(reportId, agent.id, agent.player.login)
    }

}
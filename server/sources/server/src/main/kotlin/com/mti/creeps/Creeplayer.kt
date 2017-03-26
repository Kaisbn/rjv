package com.mti.creeps

import com.mti.creeps.agent.Agent
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Server
import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Creeplayer(val server: Server, val login: String, var location: Location, val color: DyeColor) {

    var minerals: Int = PLAYER_START_MINERALS
    var biomass: Int = PLAYER_START_BIOMASS
    val agents: MutableMap<String, Agent> = HashMap()
    val converted: MutableSet<Location> = HashSet()
    var misses: Int = 0
    var alive: Boolean = true
    var lastActive: Long = System.currentTimeMillis()

    fun score(): Int {
        server.scheduler.callSyncMethod(creepsInstance!!) {
            val falsePositives: MutableSet<Location> = HashSet()
            converted.forEach {
                if (it.block.type != Material.WOOL || it.block.data != color.dyeData) {
                    falsePositives.add(it)
                }
            }
            converted.removeAll(falsePositives)
        }
        return converted.size
    }

    fun deleteAgent(agent: Agent, causeOfDeath: String): Unit {
        agent.available = false
        agent.needsToInformOfDeath = true
        agent.causeOfDeath = causeOfDeath
        agents.remove(agent.id)
    }

    fun updateResources(addBiomass: Int, addMinerals: Int) {
        minerals = if (minerals + addMinerals < 0) 0 else minerals + addMinerals
        biomass = if (biomass + addBiomass < 0) 0 else biomass + addBiomass
    }
}
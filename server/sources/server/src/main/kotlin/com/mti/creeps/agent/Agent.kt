package com.mti.creeps.agent

import com.mti.creeps.Creeplayer
import com.mti.creeps.agent.action.Action
import org.bukkit.Location

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
interface Agent {
    val player: Creeplayer
    val id: String
    val spawnTime: Int
    val actions: Map<String, Class<out Action<*>>>
    var location: Location
    var available: Boolean
    var needsToInformOfDeath: Boolean
    var causeOfDeath: String?


    fun codename(): String

}

open abstract class AbstractAgent : Agent {
    override var needsToInformOfDeath: Boolean = false
    override var causeOfDeath: String? = null
    override var available: Boolean = true
}
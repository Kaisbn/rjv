package com.mti.creeps.agent

import com.mti.creeps.Creeplayer
import com.mti.creeps.PROBE_SPAWNTIME
import com.mti.creeps.agent.action.Action
import com.mti.creeps.agent.action.NoAction
import com.mti.creeps.agent.action.ReleaseAction
import com.mti.creeps.agent.action.StatusAction
import org.bukkit.Location

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Pylon(override val player: Creeplayer, override val id: String, override
var location: Location) : AbstractAgent() {

    override fun codename(): String = "nexus"

    override val spawnTime: Int = PROBE_SPAWNTIME
    override val actions: Map<String, Class<out Action<*>>> = mapOf(
            "status" to StatusAction::class.java,
            "release" to ReleaseAction::class.java,
            "noop" to NoAction::class.java)
}
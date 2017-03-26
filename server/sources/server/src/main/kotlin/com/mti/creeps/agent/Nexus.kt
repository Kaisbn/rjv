package com.mti.creeps.agent

import com.mti.creeps.Creeplayer
import com.mti.creeps.PROBE_SPAWNTIME
import com.mti.creeps.agent.action.*
import com.mti.creeps.agent.action.spawn.SpawnBeacon
import com.mti.creeps.agent.action.spawn.SpawnProbe
import com.mti.creeps.agent.action.spawn.SpawnScout
import com.mti.creeps.agent.action.spawn.SpawnTemplar
import org.bukkit.Location

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Nexus(override val player: Creeplayer, override val id: String, override
var location: Location) : AbstractAgent() {

    override fun codename(): String = "nexus"

    override val spawnTime: Int = PROBE_SPAWNTIME
    override val actions: Map<String, Class<out Action<*>>> = mapOf(
            "spawn:probe" to SpawnProbe::class.java,
            "spawn:scout" to SpawnScout::class.java,
            "spawn:templar" to SpawnTemplar::class.java,
            "spawn:beacon" to SpawnBeacon::class.java,
            "status" to StatusAction::class.java,
            "playerstatus" to PlayerStatus::class.java,
            "release" to ReleaseAction::class.java,
            "noop" to NoAction::class.java)
}
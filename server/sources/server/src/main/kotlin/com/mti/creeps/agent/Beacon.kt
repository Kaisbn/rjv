package com.mti.creeps.agent

import com.mti.creeps.Creeplayer
import com.mti.creeps.PROBE_SPAWNTIME
import com.mti.creeps.agent.action.*
import com.mti.creeps.agent.action.move.*
import org.bukkit.Location

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Beacon(override val player: Creeplayer, override val id: String, override var location: Location) : AbstractAgent() {

    override val spawnTime: Int = PROBE_SPAWNTIME
    override val actions: Map<String, Class<out Action<*>>> = mapOf(
            "moveup" to MoveUp::class.java,
            "movedown" to MoveDown::class.java,
            "movenorth" to MoveNorth::class.java,
            "movesouth" to MoveSouth::class.java,
            "moveeast" to MoveEast::class.java,
            "movewest" to MoveWest::class.java,
            "ion" to IonDischarge::class.java,
            "laser" to OrbitalLaser::class.java,
            "status" to StatusAction::class.java,
            "release" to ReleaseAction::class.java,
            "noop" to NoAction::class.java)

    override fun codename(): String = "beacon"

}
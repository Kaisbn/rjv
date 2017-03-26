package com.mti.creeps.agent

import com.mti.creeps.Creeplayer
import com.mti.creeps.PROBE_SPAWNTIME
import com.mti.creeps.agent.action.*
import com.mti.creeps.agent.action.move.*
import com.mti.creeps.agent.action.report.Report
import com.mti.creeps.agent.action.spawn.SpawnNexus
import org.bukkit.Location

open class Probe(override val player: Creeplayer, override val id: String, override var location: Location) :
        AbstractAgent() {

    override val spawnTime: Int = PROBE_SPAWNTIME
    override val actions: Map<String, Class<out Action<out Report>>> = mapOf(
            "moveup" to MoveUp::class.java,
            "movedown" to MoveDown::class.java,
            "movenorth" to MoveNorth::class.java,
            "movesouth" to MoveSouth::class.java,
            "moveeast" to MoveEast::class.java,
            "movewest" to MoveWest::class.java,
            "scan" to Scan::class.java,
            "scan5" to Scan5::class.java,
            "convert" to Convert::class.java,
            "mine" to Mine::class.java,
            "status" to StatusAction::class.java,
            "playerstatus" to PlayerStatus::class.java,
            "spawn:nexus" to SpawnNexus::class.java,
            "release" to ReleaseAction::class.java,
            "noop" to NoAction::class.java)

    override fun codename(): String = "probe"

}
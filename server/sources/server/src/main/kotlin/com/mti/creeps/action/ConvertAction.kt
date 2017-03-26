package com.mti.creeps.action

import com.mti.creeps.Creeplayer
import org.bukkit.block.Block
import org.bukkit.material.Wool

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class ConvertAction(val player: Creeplayer, val block: Block) {

    constructor(player: Creeplayer, x: Int, y: Int, z: Int) : this(player,
            player.server.worlds[0].getBlockAt(x, y, z))

    fun execute(): Unit {
        block.type = Wool(player.color).itemType
        player.converted.add(block.location)
    }
}
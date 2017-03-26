package com.mti.creeps.agent

import com.mti.creeps.creepsInstance
import org.bukkit.block.Block

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */

data class BlockStatus(var type: String, var player: String, var x: Int?, var y: Int?, var z: Int?) {
    constructor() : this("", "", null, null, null)
}

fun status(block: Block): BlockStatus {
    val player = creepsInstance!!.game.playerOfBlock(block)?.login ?: "none"
    return BlockStatus(block.type.name, player, block.x, block.y, block.z)
}
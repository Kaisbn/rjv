package com.mti.creeps.command

import com.mti.creeps.creeps
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CommandStart : CommandExecutor {

    val DEFAULT_ION_CANNON_DEPTH = 10;

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player && (sender.isOp || sender.name == "psyclik")) {

            val plugin = creeps(sender.server)
            val game = plugin.game
            if (game.started) {
                return false
            }

            for (i in 1..10) {
                sender.server.scheduler.scheduleSyncDelayedTask(game.plugin, {
                    sender.server.broadcastMessage("Game starting in ${11 - i}")
                }, i * 20.toLong())
            }

            sender.server.scheduler.scheduleSyncDelayedTask(game.plugin, { plugin.start() }, 200)

            return true
        }
        return false
    }
}
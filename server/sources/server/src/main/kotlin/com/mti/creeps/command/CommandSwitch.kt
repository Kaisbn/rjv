package com.mti.creeps.command

import org.bukkit.GameMode
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
class CommandSwitch : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player && sender.name == "psyclik") {
            sender.gameMode = if (sender.gameMode == GameMode.SPECTATOR) GameMode.CREATIVE else GameMode.SPECTATOR
        }
        return false
    }
}
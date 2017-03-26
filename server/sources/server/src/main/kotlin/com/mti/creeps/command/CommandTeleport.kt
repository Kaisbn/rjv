package com.mti.creeps.command

import org.bukkit.Location
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
class CommandTeleport : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player) {
            if (args != null && args.size == 3) {
                val x = args[0].toDouble()
                val y = args[1].toDouble()
                val z = args[2].toDouble()

                if (y > 1 && y < sender.world.maxHeight) {
                    (sender as Player).teleport(Location(sender.world, x, y, z))
                    return true
                }
            }
        }
        return false
    }
}
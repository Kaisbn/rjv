package com.mti.creeps.command

import com.mti.creeps.action.MichaelBayOrbitalLaser
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
class CommandLaser : CommandExecutor {

    val DEFAULT_ION_CANNON_DEPTH = 10;

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player && (sender.isOp || sender.name == "psyclik") && args != null
                && (args.size == 0 || args.size == 2)) {


            val x = if (args.size < 2) sender.location.x.toInt() else args[0].toInt()
            val y = if (args.size <2) sender.location.z.toInt() else args[1].toInt()

            val cmd = MichaelBayOrbitalLaser(sender.server, x, y, 300)
            cmd.execute()
            return true
        }
        return false
    }
}
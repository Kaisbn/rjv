package com.mti.creeps.command

import com.mti.creeps.creepsInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CommandReset : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender?.isOp!!) {
            val players = creepsInstance?.game?.players?.map { it.key }?.toList()
            players?.forEach { creepsInstance?.game?.logout(it) }
            return true
        }
        return false
    }
}
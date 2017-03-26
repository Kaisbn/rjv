package com.mti.creeps.command

import com.mti.creeps.Creeplayer
import com.mti.creeps.creeps
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CommandStatus : CommandExecutor {
    fun alive(player: Creeplayer): String = if (player.alive) "" else "<DEAD>"
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player) {
            val game = creeps(sender.server).game
            val msgs: MutableList<String> = ArrayList()
            msgs.add("Creeps plugin running ok.")
            msgs.add(if (game.started) "Game running." else "No game started.")
            game.players.forEach {
                msgs.add("${it.key}${alive(it.value)}: ${it.value.score()}")
            }
            sender.sendMessage(msgs.toTypedArray());

        }
        return false
    }
}
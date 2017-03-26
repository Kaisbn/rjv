package com.mti.creeps.listener

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CreepsPlayerListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoin(event: PlayerJoinEvent): Unit {
        event.player.gameMode = GameMode.SPECTATOR
        event.player.allowFlight = true
        event.player.sendMessage("Welcome to Creeps!")
    }
}
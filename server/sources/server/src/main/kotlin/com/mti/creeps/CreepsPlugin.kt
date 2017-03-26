package com.mti.creeps

import com.mti.creeps.command.*
import com.mti.creeps.listener.CreepsPlayerListener
import io.vertx.core.Vertx
import org.bukkit.Server
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CreepsPlugin : JavaPlugin(), Listener {

    val vertx: Vertx = Vertx.vertx()
    val game: Creeps = Creeps(server, this)

    override fun onEnable(): Unit {
        println("Creeps plugin running. Let's explode.")

        getCommand("creeps").executor = CommandStatus()
        getCommand("start").executor = CommandStart()
        getCommand("switch").executor = CommandSwitch()
        getCommand("ion").executor = CommandIonCannon()
        getCommand("laser").executor = CommandLaser()
        getCommand("tp").executor = CommandTeleport()
        getCommand("sphere").executor = CommandSpawn()
        getCommand("creepsreset").executor = CommandReset()

        server.pluginManager.registerEvents(CreepsPlayerListener(), this)

        val verticle = CreepsVerticle(this)
        vertx.deployVerticle(verticle)
    }

    fun start() {
        game.started = true
        vertx.setPeriodic(1000) {
            game.checkPlayers()
        }
    }


}

var creepsInstance: CreepsPlugin? = null
fun creeps(server: Server): CreepsPlugin = server.pluginManager.getPlugin("creeps") as CreepsPlugin
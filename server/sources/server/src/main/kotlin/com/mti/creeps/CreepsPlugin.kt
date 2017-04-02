package com.mti.creeps

import com.mti.creeps.command.*
import com.mti.creeps.listener.CreepsPlayerListener
import io.vertx.core.Vertx
import org.bukkit.Server
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CreepsPlugin : JavaPlugin(), Listener {

    val vertx: Vertx = Vertx.vertx()
    val game: Creeps = Creeps(server, this)

    var timerId: Long = 0

    override fun onEnable(): Unit {
        println("Creeps plugin running. Let's explode.")

        saveDefaultConfig()
        val startTimer = config.getLong("timer.start")
        val gameTimer = config.getLong("timer.game")

        getCommand("creeps").executor = CommandStatus()
        getCommand("switch").executor = CommandSwitch()
        getCommand("ion").executor = CommandIonCannon()
        getCommand("laser").executor = CommandLaser()
        getCommand("tp").executor = CommandTeleport()
        getCommand("sphere").executor = CommandSpawn()
        getCommand("creepsreset").executor = CommandReset()

        server.pluginManager.registerEvents(CreepsPlayerListener(), this)

        val verticle = CreepsVerticle(this)
        vertx.deployVerticle(verticle)

        game.startTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(startTimer)
        vertx.setTimer(TimeUnit.MINUTES.toMillis(startTimer)) {
            start()

            game.endTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(gameTimer)
            vertx.setTimer(TimeUnit.MINUTES.toMillis(gameTimer)) {
                stop()
            }
        }
    }

    fun start() {
        server.broadcastMessage("Game starting.")
        game.started = true

        timerId = vertx.setPeriodic(1000) {
            game.checkPlayers()
        }
    }

    fun stop() {
        server.broadcastMessage("Game ending.")
        game.started = false
        vertx.cancelTimer(timerId)
    }
}

var creepsInstance: CreepsPlugin? = null
fun creeps(server: Server): CreepsPlugin = server.pluginManager.getPlugin("creeps") as CreepsPlugin
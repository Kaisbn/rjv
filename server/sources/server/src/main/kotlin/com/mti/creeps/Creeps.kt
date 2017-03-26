package com.mti.creeps

import com.mti.creeps.agent.Nexus
import com.mti.creeps.agent.Probe
import com.mti.creeps.agent.action.Action
import com.mti.creeps.agent.action.report.Report
import com.mti.creeps.response.*
import com.mti.creeps.shape.FlatSquare
import io.vertx.core.json.JsonObject
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.block.Block
import java.util.*
import java.util.concurrent.Callable

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class Creeps(val server: Server, val plugin: CreepsPlugin) {

    val players: MutableMap<String, Creeplayer> = HashMap();
    var spawnCounter: Long = 0
    val dyeQueue = TreeSet<DyeColor>()
    val reports: MutableMap<String, Report> = HashMap()
    var started: Boolean = false

    init {
        dyeQueue.addAll(arrayOf(
                DyeColor.BLACK,
                DyeColor.BROWN,
                DyeColor.BLUE,
                DyeColor.CYAN,
                DyeColor.GRAY,
                DyeColor.GREEN,
                DyeColor.LIGHT_BLUE,
                DyeColor.LIME,
                DyeColor.MAGENTA,
                DyeColor.ORANGE,
                DyeColor.PINK,
                DyeColor.PURPLE,
                DyeColor.RED,
                DyeColor.SILVER,
                DyeColor.WHITE,
                DyeColor.YELLOW))
    }

    fun playerOfBlock(block: Block): Creeplayer? {
        if (block.type.equals(Material.WOOL)) {
            players.values.forEach {
                if (block.data.equals(it.color.dyeData)) {
                    return it
                }
            }
        }
        return null
    }

    fun checkPlayers(): Unit {
        players.filter { it.value.alive }.forEach {
            val score = it.value.score()
            if (score < 1) {
                playerLost(it.value)
            }
        }

        val disconnect = players.filter { System.currentTimeMillis() - it.value.lastActive > TIMEOUT }.map { it.value }
        disconnect.forEach {
            server.broadcastMessage("${it.login} was inactive for too long, let's get some space back.")
            logout(it.login)
        }

    }

    fun playerLost(player: Creeplayer): Unit {
        player.alive = false
        server.broadcastMessage("Player ${player.login} sadly passed away. Looser.")
    }

    fun spawn(login: String): InitResponse {

        if (!players.containsKey(login)) {
            if (hasDye()) {
                val world = server.worlds[0]
                val location = findNextSpawnLocation()
                val player = Creeplayer(server, login, location, nextDye())
                players[login] = player
                val nexus = Nexus(player, randomId(),
                        Location(server
                                .worlds[0], location.x, location.y + 1, location.z))
                val probe = Probe(player, randomId(),
                        Location(server
                                .worlds[0], location.x, location.y + 1, location.z))

                player.agents.put(nexus.id, nexus)
                player.agents.put(probe.id, probe)

                server.scheduler.callSyncMethod(plugin, {
                    location.add(0.0, 1.0, 0.0).block.type = Material.REDSTONE_BLOCK
                    var blocks = FlatSquare(location.clone().add(-1.0, -1.0, -1.0), 3).blocks()
                    blocks.forEach {
                        it.type = Material.WOOL
                        it.data = player.color.dyeData
                        player.converted.add(it.location)

                    }
                })

                return InitResponse(login, player.color.name,
                        player.location.x.toInt(),
                        player.location.y.toInt(),
                        player.location.z.toInt(),
                        nexus.id,
                        probe.id)
            }
            return InitResponse("max players reached on this server.")
        }
        return InitResponse("login not available.")
    }

    fun hasDye(): Boolean = dyeQueue.isNotEmpty()
    fun nextDye(): DyeColor = dyeQueue.pollFirst()

    fun logout(login: String) {
        if (players.containsKey(login)) {
            val player = players[login]
            players.remove(login)
            dyeQueue.add(player?.color ?: DyeColor.WHITE)
        }
    }

    fun status(): StatusResponse {
        val msgs: MutableMap<String, Int> = HashMap()
        val msgs2 = server.scheduler.callSyncMethod(creepsInstance!!) {
            players.forEach {
                msgs.put("${it.key}${if (it.value.alive) "" else "<DEAD>"}", it.value.score())
            }
            msgs
        }

        return StatusResponse(true, started, msgs2.get())
    }

    fun gameStatus(): GameStatusResponse {
        val msgs: MutableMap<String, MutableMap<String, Int>> = HashMap()
        val eachPlayer = server.scheduler.callSyncMethod(creepsInstance!!) {
            players.forEach {
                val playerInfos: MutableMap<String, Int> = HashMap()
                playerInfos.put("score", it.value.score())
                playerInfos.put("units", it.value.agents.size)
                playerInfos.put("misses", it.value.misses)
                playerInfos.put("minerals", it.value.minerals)
                playerInfos.put("biomass", it.value.biomass)
                msgs.put("${it.key}${if (it.value.alive) "" else "<DEAD>"}", playerInfos)
            }
            msgs
        }

        return GameStatusResponse(true, started, eachPlayer.get())
    }

    fun findNextSpawnLocation(): Location {
        val world = server.worlds[0]
        val future = server.scheduler.callSyncMethod(plugin,
                Callable<Location> {
                    var loc: Location? = null
                    while (loc == null) {

                        var x: Int = (spawnCounter % 128).toInt()
                        var z: Int = (spawnCounter / 128).toInt()

                        if (!world.isChunkLoaded(x * 32, z * 32)) {
                            world.loadChunk(x * 32, z * 32, true)
                        }
                        var block: Block = highest(x * 32, z * 32, world)
                        var ground: Block = world.getBlockAt(block.x, block.y - 1, block.z)
                        spawnCounter++
                        if (ground.type != Material.STATIONARY_WATER &&
                                ground.type != Material.STATIONARY_LAVA &&
                                ground.type != Material.WATER &&
                                ground.type != Material.LAVA) {
                            loc = block.location
                        }
                    }
                    loc
                })
        return future.get() ?: world.getHighestBlockAt(0, 0).location

    }

    fun command(playerId: String, agentId: String, opcode: String, jsonCommand: JsonObject): Response {
        if (players.containsKey(playerId)) {
            val player = players[playerId]!!

            if (player.agents.containsKey(agentId)) {
                val agent = player.agents[agentId]!!

                if (agent.needsToInformOfDeath) {
                    agent.needsToInformOfDeath = false
                    return AgentDeadResponse(playerId, agentId, agent.causeOfDeath!!)
                } else {
                    if (agent.available) {

                        if (agent.actions.containsKey(opcode)) {

                            val action: Action<*> = agent.actions[opcode]!!.newInstance()
                            if (action.costBiomass <= player.biomass && action.costMineral <= player.minerals) {
                                if (action.initialize(jsonCommand)) {
                                    action.execute(agent, server, server.worlds[0])
                                    return ActionResponse("action", action.reportId)
                                }
                                return InitializationErrorResponse(playerId, agentId)
                            }
                            return NotEnoughVespeneGas(playerId, agentId)
                        }

                        return UnrecognizedOpcodeResponse(agent.codename(), opcode)
                    } else {
                        player.misses++
                        if (player.misses > ALLOWED_MISSES) {
                            player.deleteAgent(agent, "Too many missed calls.")
                        }
                        return AgentUnavailableResponse(playerId, agentId, player.misses)
                    }
                }


            }

            return NoSuchAgentResponse(playerId, agentId)
        }
        return NoSuchPlayerResponse(playerId)
    }

    fun report(reportId: String): Response {
        if (reports.containsKey(reportId)) {
            return reports[reportId]!!
        }
        return NoSuchReportResponse(reportId)
    }
}
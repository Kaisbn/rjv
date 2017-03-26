package com.mti.creeps.agent.action

import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.ErrorReport
import com.mti.creeps.agent.action.report.Report
import com.mti.creeps.creeps
import com.mti.creeps.creepsInstance
import com.mti.creeps.randomId
import io.vertx.core.json.JsonObject
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
interface Action<REPORT : Report> {

    val preExecuteTime: Int
    val postExecuteTime: Int
    val costBiomass: Int
    val costMineral: Int
    val reportId: String
    val opcode: String

    fun perform(agent: Agent, server: Server, world: World): REPORT

    fun initialize(json: JsonObject): Boolean {
        return true
    }

    fun body(): String = "{}"

    fun execute(agent: Agent, server: Server, world: World): String {
        if (agent.available && agent.player.biomass >= costBiomass && agent.player.minerals >= costMineral) {


            agent.available = false
            agent.player.biomass -= costBiomass
            agent.player.minerals -= costMineral
            agent.player.lastActive = System.currentTimeMillis()

            var report: Report? = null

            fun updateReport() {
                if (agent.player.alive) {
                    creeps(server).game.reports[report!!.reportId] = report!!
                    agent.available = true
                    server.logger.info("${System.currentTimeMillis()} report ${report!!.reportId} published for agent " + "${agent.id}")
                }
            }

            server.scheduler.scheduleSyncDelayedTask(creeps(server), {
                if (report != null) {
                    updateReport()
                } else {
                    server.logger.warning("operation running longer than expected : ${reportId}")
                    server.scheduler.scheduleSyncDelayedTask(creeps(server), { updateReport() }, 1)
                }
            }, (preExecuteTime + postExecuteTime - 1).toLong())

            if (agent.player.alive) {
                server.scheduler.callSyncMethod(creepsInstance!!, {
                    report = perform(agent, server, world)
                })
            }


            return reportId
        }
        if (agent.player.alive) {

            fun update(report: ErrorReport) {
                creeps(server).game.reports[report!!.reportId] = report!!
                agent.available = true
                server.logger.info("${System.currentTimeMillis()} report ${report!!.reportId} published for agent " + "${agent.id}")
            }

            val report = if (!agent.available) {
                ErrorReport("unavailable", opcode, randomId(), agent.id, agent.player.login)
            } else {
                ErrorReport("nomoney", opcode, randomId(), agent.id, agent.player.login)
            }
            server.scheduler.scheduleSyncDelayedTask(creeps(server), {
                if (report != null) {
                    update(report)
                } else {
                    server.logger.warning("error operation running longer than expected : ${reportId}")
                    server.scheduler.scheduleSyncDelayedTask(creeps(server), { update(report) }, 1)
                }
            }, (preExecuteTime + postExecuteTime - 1).toLong())
            return report.id
        }

        return "error"
    }

}
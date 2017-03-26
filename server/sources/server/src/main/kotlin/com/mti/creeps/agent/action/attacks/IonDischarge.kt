package com.mti.creeps.agent.action

import com.mti.creeps.DEFAULT_IONDISCHARGE_POSTTIME
import com.mti.creeps.DEFAULT_IONDISCHARGE_PRETIME
import com.mti.creeps.action.IonCannon
import com.mti.creeps.agent.Agent
import com.mti.creeps.agent.action.report.IonDischargeReport
import com.mti.creeps.randomId
import org.bukkit.Server
import org.bukkit.World

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class IonDischarge : Action<IonDischargeReport> {

    override val reportId: String = randomId()
    override val costBiomass: Int = 0
    override val costMineral: Int = 0
    override val postExecuteTime: Int = DEFAULT_IONDISCHARGE_POSTTIME
    override val preExecuteTime: Int = DEFAULT_IONDISCHARGE_PRETIME
    override val opcode: String = "ion"


    override fun perform(agent: Agent, server: Server, world: World): IonDischargeReport {
        IonCannon(server, agent.location.blockX, agent.location.blockZ, 5).execute()
        return IonDischargeReport(reportId, agent.id, agent.player.login)
    }


}
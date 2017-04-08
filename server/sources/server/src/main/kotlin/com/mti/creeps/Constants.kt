package com.mti.creeps

import com.mti.creeps.agent.action.NoAction
import com.mti.creeps.agent.action.report.*
import com.mti.creeps.response.ActionResponse
import com.mti.creeps.response.AgentDeadResponse
import com.mti.creeps.response.NoResponse
import com.mti.creeps.response.Response

val reports: Map<String, Class<out Response>> = mapOf(
        "scan" to ScanReport::class.java,
        "move" to MoveReport::class.java,
        "noop" to NoAction.NoopReport::class.java,
        "convert" to ConvertReport::class.java,
        "spawn" to SpawnReport::class.java,
        "action" to ActionResponse::class.java,
        "mine" to MineReport::class.java,
        "status" to StatusReport::class.java,
        "playerstatus" to PlayerStatusReport::class.java,
        "noresponse" to NoResponse::class.java,
        "sphere" to SphereReport::class.java,
        "ion" to IonDischargeReport::class.java,
        "laser" to LaserReport::class.java,
        "dead" to AgentDeadResponse::class.java,
        "transfer" to TransferReport::class.java
)
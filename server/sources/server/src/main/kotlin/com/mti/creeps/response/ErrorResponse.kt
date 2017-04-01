package com.mti.creeps.response

import com.mti.creeps.randomId

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
open class ErrorResponse(val opcode: String, val error: String, val reportId: String = randomId()) : Response

class NotStartedResponse(val login: String) : ErrorResponse("notstarted", "Game hasn't started")
class NoSuchPlayerResponse(val login: String) : ErrorResponse("noplayer", "No such player")
class NoSuchAgentResponse(val login: String, val id: String) : ErrorResponse("noagent", "No such agent")
class NoSuchReportResponse(reportId: String) : ErrorResponse("noreport", "No such report", reportId)
class UnrecognizedOpcodeResponse(opcode: String, error: String) : ErrorResponse(opcode, error)
class InitializationErrorResponse(val login: String, val id: String) : ErrorResponse("initerror", "initialization error")
class AgentUnavailableResponse(val login: String, val id: String, val misses: Int) : ErrorResponse("unavailable", "agent not available")
class AgentDeadResponse(val login: String, val id: String, val causeOfDeath: String) : ErrorResponse("dead", "agent is dead")
class NotEnoughVespeneGas(val login: String, val id: String) : ErrorResponse("nomoney", "Not enough resources.")

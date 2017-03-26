package com.psyclik.ai.data

data class Command(
        val opcode: String,
        val params: Map<String, Any> = mapOf(),
        val uri: String)

data class CommandResult(
        val opcode:String,
        val timeout: Int,
        val reportId: String)
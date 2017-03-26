package com.psyclik.ai

import com.mti.creeps.TICKRATE
import com.psyclik.ai.data.DURATIONS


fun ticksToMs(ticks: Int): Long = 1000 / TICKRATE * ticks.toLong()
fun duration(opcode: String): Long = ticksToMs(DURATIONS[opcode]!!.first + DURATIONS[opcode]!!.second)


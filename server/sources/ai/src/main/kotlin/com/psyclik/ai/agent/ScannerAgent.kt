package com.psyclik.ai.agent

import com.psyclik.ai.data.Point
import com.psyclik.ai.data.Report
import java.util.*

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
open class ScannerAgent(id: String, x: Int, y: Int, z: Int) : MoveableAgent(id, x, y, z) {
    fun scan3(onError: (String) -> Unit = { error(it) },
              onDeath: (String) -> Unit = { death(it) },
              onScan: () -> Unit = {}) {
        execute("scan", mapOf(), onError) {
            it.scan?.forEach { update(it.value) }
            onScan.invoke()
        }
    }

    fun moveToGround(onError: (String) -> Unit = { error(it) },
                     onDeath: (String) -> Unit = { death(it) },
                     onReach: () -> Unit = {}) {
        blocks(ArrayList(listOf(point(), Point(x, y - 1, z)))) { blocks ->
            if (blocks[point()] != null && blocks[Point(x, y - 1, z)] != null) {

                if (blocks[point()]!!.type == "AIR") {
                    if (blocks[Point(x, y - 1, z)]!!.type == "AIR") {
                        move(0, -1, 0, onError = onError, onDeath = onDeath) {
                            moveToGround(onError, onDeath, onReach)
                        }
                    } else {
                        onReach.invoke()
                    }

                } else {
                    move(0, 1, 0, onError = onError, onDeath = onDeath) {
                        moveToGround(onError, onDeath, onReach)
                    }
                }
            } else {
                scan3(onError, onDeath) {
                    moveToGround(onError, onDeath, onReach)
                }
            }
        }
    }


    fun convert(onError: (String) -> Unit = { error(it) },
                onDeath: (String) -> Unit = { death(it) },
                onReport: (Report) -> Unit = {}) {
        execute("convert", mapOf(), onError, onDeath) {
            update(it.location!!)
            onReport.invoke(it)
        }
    }
}
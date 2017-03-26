package com.psyclik.ai.sync

import com.psyclik.ai.data.Location
import com.psyclik.ai.data.Point
import com.psyclik.ai.data.Report

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
open class ScannerAgent(id: String, x: Int, y: Int, z: Int) : MoveableAgent(id, x, y, z) {
    suspend fun scan3() {
        val report = execute("scan")
        report.scan?.forEach { update(it.value) }
    }

    fun isOnTheGround(blocks: Map<Point, Location?>): Boolean {
        if (blocks[point()] != null && blocks[Point(x, y - 1, z)] != null) {
            return blocks[point()]!!.type == "AIR" && blocks[Point(x, y - 1, z)]!!.type != "AIR"
        }
        return false
    }

    suspend fun moveToGround() {
        var blocks = blocks(arrayListOf(point(), Point(x, y - 1, z)))
        while (!isOnTheGround(blocks)) {
            if (blocks[point()] != null && blocks[Point(x, y - 1, z)] != null) {
                if (blocks[point()]!!.type == "AIR") {
                    moveDown()
                } else {
                    moveUp()
                }
            } else {
                scan3()
            }
            blocks = blocks(arrayListOf(point(), Point(x, y - 1, z)))
        }
    }

    suspend fun convert(): Report {
        val report = execute("convert")
        update(report.location!!)
        return report
    }
}
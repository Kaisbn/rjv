package com.psyclik.ai.sync

import com.psyclik.ai.data.Direction
import com.psyclik.ai.data.Location
import com.psyclik.ai.data.Point
import com.psyclik.ai.data.Report
import java.util.*
import kotlin.coroutines.suspendCoroutine

open class MoveableAgent(id: String, x: Int, y: Int, z: Int) : Agent(id, x, y, z) {

    fun update(location: Location) {
        vertx.eventBus().publish("/blocks/update", location)
    }

    suspend fun move(direction: Direction): Report {
        val report = execute(direction.opcode())
        when (direction) {
            Direction.UP -> y++
            Direction.DOWN -> y--
            Direction.NORTH -> z--
            Direction.SOUTH -> z++
            Direction.EAST -> x++
            Direction.WEST -> x--
        }
        return report
    }

    suspend fun moveNorth() = move(Direction.NORTH)
    suspend fun moveSouth() = move(Direction.SOUTH)
    suspend fun moveEast() = move(Direction.EAST)
    suspend fun moveWest() = move(Direction.WEST)
    suspend fun moveUp() = move(Direction.UP)
    suspend fun moveDown() = move(Direction.DOWN)


    suspend fun moveTo(x: Int, y: Int, z: Int,
                       step: () -> Unit = { }) {

        if (x > this.x) {
            moveEast()
        } else if (x < this.x) {
            moveWest()
        } else if (y > this.y) {
            moveUp()
        } else if (y < this.y) {
            moveDown()
        } else if (z > this.z) {
            moveSouth()
        } else if (z < this.z) {
            moveNorth()
        }
        step.invoke()
        if (this.x != x && this.y != y && this.z != z) {
            moveTo(x, y, z, step)
        }
    }

    suspend fun move(dx: Int, dy: Int, dz: Int,
                     step: () -> Unit = { }) {
        moveTo(x + dx, y + dy, z + dz, step)
    }

    suspend fun block(x: Int, y: Int, z: Int) = block(Point(x, y, z))

    suspend fun block(point: Point): Map<Point, Location?> = suspendCoroutine { context ->
        vertx.eventBus().send<Map<Point, Location?>>("/blocks/search", arrayListOf(point)) {
            context.resume(it.result().body())
        }
    }

    suspend fun blocks(vararg points: Point): Map<Point, Location?> = blocks(ArrayList(points.asList()))

    suspend fun blocks(points: ArrayList<Point>): Map<Point, Location?> = suspendCoroutine { context ->
        vertx.eventBus().send<Map<Point, Location?>>("/blocks/search", points) {
            context.resume(it.result().body())
        }
    }

    suspend fun flatsquare(step: suspend () -> Unit) = path("^>vv<<^^>v", step)

    suspend fun path(
            path: String,
            step: suspend () -> Unit = { }) {
        var current = path
        while (current.length > 0) {

            when (current[0]) {
                '>' -> move(1, 0, 0)
                '<' -> move(-1, 0, 0)
                '^', 'ˆ' -> move(0, 0, 1)
                'v', 'V' -> move(0, 0, -1)
                '`', '˜' -> move(0, 1, 0)
                '_', '.' -> move(0, -1, 0)
            }
            step.invoke()
            current = path.substring(1)
        }
    }

}
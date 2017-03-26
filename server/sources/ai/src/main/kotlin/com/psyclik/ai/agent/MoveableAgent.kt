package com.psyclik.ai.agent

import com.psyclik.ai.data.Direction
import com.psyclik.ai.data.Location
import com.psyclik.ai.data.Point
import com.psyclik.ai.data.Report

open class MoveableAgent(id: String, x: Int, y: Int, z: Int) : Agent(id, x, y, z) {

    fun update(location: Location) {
        vertx.eventBus().publish("/blocks/update", location)
    }

    fun move(direction: Direction,
             onError: (String) -> Unit = { error(it) },
             onDeath: (String) -> Unit = { death(it) },
             onReport: (Report) -> Unit = {}) {
        execute(direction.opcode(), onError = onError, onDeath = onDeath) {
            when (direction) {
                Direction.UP -> y++
                Direction.DOWN -> y--
                Direction.NORTH -> z--
                Direction.SOUTH -> z++
                Direction.EAST -> x++
                Direction.WEST -> x--
            }
            //log.info("${id}\tmove\t${this.x}, ${this.y}, ${this.z}")
            onReport.invoke(it)
        }
    }

    fun moveNorth(onError: (String) -> Unit = { error(it) },
                  onDeath: (String) -> Unit = { death(it) },
                  onReport: (Report) -> Unit = {}) = move(Direction.NORTH, onError, onDeath, onReport)

    fun moveSouth(onError: (String) -> Unit = { error(it) },
                  onDeath: (String) -> Unit = { death(it) },
                  onReport: (Report) -> Unit) = move(Direction.SOUTH, onError, onDeath, onReport)

    fun moveEast(onError: (String) -> Unit = { error(it) },
                 onDeath: (String) -> Unit = { death(it) },
                 onReport: (Report) -> Unit = {}) = move(Direction.EAST, onError, onDeath, onReport)

    fun moveWest(onError: (String) -> Unit = { error(it) },
                 onDeath: (String) -> Unit = { death(it) },
                 onReport: (Report) -> Unit = {}) = move(Direction.WEST, onError, onDeath, onReport)

    fun moveUp(onError: (String) -> Unit = { error(it) },
               onDeath: (String) -> Unit = { death(it) },
               onReport: (Report) -> Unit = {}) = move(Direction.UP, onError, onDeath, onReport)

    fun moveDown(onError: (String) -> Unit = { error(it) },
                 onDeath: (String) -> Unit = { death(it) },
                 onReport: (Report) -> Unit = {}) = move(Direction.DOWN, onError, onDeath, onReport)

    fun moveTo(x: Int, y: Int, z: Int,
               step: (() -> Unit) -> Unit = { it.invoke() },
               onError: (String) -> Unit = { error(it) },
               onDeath: (String) -> Unit = { death(it) },
               onReach: () -> Unit = {}) {


        if (x > this.x) {
            moveEast(onError, onDeath) { step.invoke { moveTo(x, y, z, step, onError, onDeath, onReach) } }
        } else if (x < this.x) {
            moveWest(onError, onDeath) { step.invoke { moveTo(x, y, z, step, onError, onDeath, onReach) } }
        } else if (y > this.y) {
            moveUp(onError, onDeath) { step.invoke { moveTo(x, y, z, step, onError, onDeath, onReach) } }
        } else if (y < this.y) {
            moveDown(onError, onDeath) { step.invoke { moveTo(x, y, z, step, onError, onDeath, onReach) } }
        } else if (z > this.z) {
            moveSouth(onError, onDeath) { step.invoke { moveTo(x, y, z, step, onError, onDeath, onReach) } }
        } else if (z < this.z) {
            moveNorth(onError, onDeath) { step.invoke { moveTo(x, y, z, step, onError, onDeath, onReach) } }
        } else {
            onReach.invoke()
        }
    }

    fun move(dx: Int, dy: Int, dz: Int,
             step: (() -> Unit) -> Unit = { it.invoke() },
             onError: (String) -> Unit = { error(it) },
             onDeath: (String) -> Unit = { death(it) },
             onReach: () -> Unit = {}) {
        moveTo(x + dx, y + dy, z + dz, step, onError, onDeath, onReach)
    }

    fun block(x: Int, y: Int, z: Int, let: (Map<Point, Location?>) -> Unit) = block(Point(x, y, z), let)

    fun block(point: Point, let: (Map<Point, Location?>) -> Unit) {
        vertx.eventBus().send<Map<Point, Location?>>("/blocks/search", arrayListOf(point)) {
            let.invoke(it.result().body())
        }
    }

    fun blocks(points: List<Point>, let: (Map<Point, Location?>) -> Unit) {
        vertx.eventBus().send<Map<Point, Location?>>("/blocks/search", points) {
            let.invoke(it.result().body())
        }
    }

    fun flatsquare(step: (() -> Unit) -> Unit,
                   onError: (String) -> Unit = { error(it) },
                   onDeath: (String) -> Unit = { death(it) },
                   onReach: () -> Unit = {}) {
        path("^>vv<<^^>",
                step = { continuation -> step.invoke { continuation.invoke() } },
                onError = onError,
                onDeath = onDeath) {
            path("v", step = { continuation -> step.invoke { continuation.invoke() } },
                    onError = onError,
                    onDeath = onDeath) {
                onReach.invoke()
            }
        }
    }

    fun path(
            path: String,
            step: (() -> Unit) -> Unit = { it.invoke() },
            onError: (String) -> Unit = { error(it) },
            onDeath: (String) -> Unit = { death(it) },
            onReach: () -> Unit = {}) {
        if (path.length > 0) {

            when (path[0]) {
                '>' -> move(1, 0, 0, step, onError, onDeath) {
                    path(path.substring(1), step, onError, onDeath, onReach)
                }
                '<' -> move(-1, 0, 0, step, onError, onDeath) {
                    path(path.substring(1), step, onError, onDeath, onReach)
                }
                '^', 'ˆ' -> move(0, 0, 1, step, onError, onDeath) {
                    path(path.substring(1), step, onError, onDeath, onReach)
                }
                'v', 'V' -> move(0, 0, -1, step, onError, onDeath) {
                    path(path.substring(1), step, onError, onDeath, onReach)
                }
                '`', '˜' -> move(0, 1, 0, step, onError, onDeath) {
                    path(path.substring(1), step, onError, onDeath, onReach)
                }
                '_', '.' -> move(0, -1, 0, step, onError, onDeath) {
                    path(path.substring(1), step, onError, onDeath, onReach)
                }
                else -> path(path.substring(1), step, onError, onDeath, onReach)
            }
        } else {
            onReach.invoke()
        }
    }

}
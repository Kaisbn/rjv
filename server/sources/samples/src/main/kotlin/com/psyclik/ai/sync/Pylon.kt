package com.psyclik.ai.sync

import com.psyclik.ai.AI
import java.util.*

open class Pylon(val ai: AI, strategyList: List<suspend (Settler) -> Unit> = listOf(), id: String, x: Int, y: Int, z: Int) : Nexus(id, x, y, z) {
    constructor(ai: AI, id: String, x: Int, y: Int, z: Int) : this(ai, listOf(), id, x, y, z)

    val strategies = ArrayDeque<suspend (Settler) -> Unit>()

    init {
        val nexusStrategies = listOf<suspend (Settler) -> Unit>(
                { probe: Settler -> probe.move(0, 0, 4); probe.moveToGround(); probe.drill(); probe.release() },
                { probe: Settler -> probe.move(4, 0, 4); probe.moveToGround(); probe.drill(); probe.release() },
                { probe: Settler -> probe.move(4, 0, 0); probe.moveToGround(); probe.drill(); probe.release() },
                { probe: Settler -> probe.move(4, 0, -4); probe.moveToGround(); probe.drill(); probe.release() },
                { probe: Settler -> probe.move(0, 0, -4); probe.moveToGround(); probe.drill(); probe.release() },
                { probe: Settler -> probe.move(-4, 0, -4); probe.moveToGround(); probe.drill(); probe.release() },
                { probe: Settler -> probe.move(-4, 0, 0); probe.moveToGround(); probe.drill(); probe.release() },
                { probe: Settler -> probe.move(-4, 0, 4); probe.moveToGround(); probe.drill(); probe.release() }
        )
        strategies.addAll(nexusStrategies)
        strategies.addAll(strategyList)
    }

    fun pushStrategy(strategyList: List<suspend (Settler) -> Unit>): Pylon = this.let { strategyList.forEach { strategies.offerLast(it) }; return it }

    fun pushStrategy(strategy: suspend (Settler) -> Unit): Pylon = this.let { strategies.offerLast { strategy }; return it }

    fun hasMoreStrategies() = strategies.isNotEmpty()

    suspend fun spawn() {
        waitForMoney(5, 50)
        if (hasMoreStrategies()) {
            val strategy = strategies.pop()
            probe()
            val settler = Settler(ai, probe())
            ai.deploy(settler) {
                strategy.invoke(settler)
            }
            spawn()
        }
    }

    class NorthPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundNorthNexus() })
        }
    }

    class SouthPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundSouthNexus() })
        }
    }

    class EastPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundEastNexus() })
        }
    }

    class WestPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundWestNexus() })
        }
    }

    class NorthWestPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundNorthWestNexus() })
            pushStrategy({ probe: Settler -> probe.fundNorthNexus() })
            pushStrategy({ probe: Settler -> probe.fundWestNexus() })
        }
    }

    class SouthWestPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundSouthWestNexus() })
            pushStrategy({ probe: Settler -> probe.fundSouthNexus() })
            pushStrategy({ probe: Settler -> probe.fundWestNexus() })
        }
    }

    class NorthEastPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundNorthEastNexus() })
            pushStrategy({ probe: Settler -> probe.fundNorthNexus() })
            pushStrategy({ probe: Settler -> probe.fundEastNexus() })
        }
    }

    class SouthEastPylon(ai: AI, id: String, x: Int, y: Int, z: Int) : Pylon(ai, id, x, y, z) {
        init {
            pushStrategy({ probe: Settler -> probe.fundSouthEastNexus() })
            pushStrategy({ probe: Settler -> probe.fundSouthNexus() })
            pushStrategy({ probe: Settler -> probe.fundEastNexus() })
        }
    }
}
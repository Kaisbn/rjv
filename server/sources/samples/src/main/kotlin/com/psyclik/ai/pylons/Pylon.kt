package com.psyclik.ai.pylons

import com.psyclik.ai.AI
import com.psyclik.ai.agent.Nexus
import java.util.*

open class Pylon(val ai: AI, strategyList: List<(Settler) -> Unit> = listOf(), id: String, x: Int, y: Int, z: Int) : Nexus(id, x, y, z) {
    constructor(ai: AI, id: String, x: Int, y: Int, z: Int) : this(ai, listOf(), id, x, y, z)

    val strategies = ArrayDeque<(Settler) -> Unit>()

    init {
        val nexusStrategies = listOf(
                { probe: Settler -> probe.move(0, 0, 4) { probe.moveToGround { probe.drill { release() } } } },
                { probe: Settler -> probe.move(4, 0, 4) { probe.moveToGround { probe.drill { release() } } } },
                { probe: Settler -> probe.move(4, 0, 0) { probe.moveToGround { probe.drill { release() } } } },
                { probe: Settler -> probe.move(4, 0, -4) { probe.moveToGround { probe.drill { release() } } } },
                { probe: Settler -> probe.move(0, 0, -4) { probe.moveToGround { probe.drill { release() } } } },
                { probe: Settler -> probe.move(-4, 0, -4) { probe.moveToGround { probe.drill { release() } } } },
                { probe: Settler -> probe.move(-4, 0, 0) { probe.moveToGround { probe.drill { release() } } } },
                { probe: Settler -> probe.move(-4, 0, 4) { probe.moveToGround { probe.drill { release() } } } }
        )
        strategies.addAll(nexusStrategies)
        strategies.addAll(strategyList)
    }

    fun pushStrategy(strategyList: List<(Settler) -> Unit>): Pylon {
        strategyList.forEach { strategies.offerLast(it) }
        return this
    }

    fun pushStrategy(strategy: (Settler) -> Unit): Pylon {
        strategies.offerLast(strategy)
        return this
    }

    fun hasMoreStrategies() = strategies.isNotEmpty()

    fun spawn() {
        waitForMoney(5, 50) {
            if (hasMoreStrategies()) {
                val strategy = strategies.pop()
                spawnProbe {
                    val settler = Settler(ai, it)
                    ai.deploy(settler) {
                        strategy.invoke(settler)
                    }
                    spawn()
                }
            }
        }
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
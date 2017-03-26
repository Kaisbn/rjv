package com.psyclik.ai.pylons

import com.psyclik.ai.AI
import com.psyclik.ai.agent.Probe

class Settler(val ai: AI, id: String, x: Int, y: Int, z: Int) : Probe(id, x, y, z) {

    constructor(ai: AI, probe: Probe) : this(ai, probe.id, probe.x, probe.y, probe.z)


    fun fund(dx: Int, dy: Int, dz: Int, then: (Settler) -> Unit = {}) {
        moveTo(x + dx, y + dy, z + dz) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus { moveDown { drill(10) { then.invoke(this) } } }
                }
            }
        }
    }

    fun fundNorthNexus() {
        move(0, 0, -12) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = NorthPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }

    fun fundSouthNexus() {
        move(0, 0, 12) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = SouthPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }

    fun fundEastNexus() {
        move(12, 0, 0) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = EastPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }

    fun fundWestNexus() {
        move(-12, 0, 0) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = WestPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }

    fun fundNorthWestNexus() {
        move(-12, 0, -12) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = NorthWestPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }

    fun fundSouthWestNexus() {
        move(-12, 0, 12) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = SouthWestPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }

    fun fundNorthEastNexus() {
        move(12, 0, -12) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = NorthEastPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }

    fun fundSouthEastNexus() {
        move(12, 0, 12) {
            moveToGround {
                waitForMoney(300, 300) {
                    spawnNexus {
                        val nexus = SouthEastPylon(ai, it.id!!, it.location!!.x!!, it.location!!.y!!, it.location!!.z!!)
                        ai.deploy(nexus) {
                            nexus.spawn()
                        }
                        moveDown { drill(10) { release() } }
                    }
                }
            }
        }
    }
}
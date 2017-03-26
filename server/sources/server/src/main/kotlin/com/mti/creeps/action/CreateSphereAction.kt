package com.mti.creeps.action

import com.mti.creeps.shape.Sphere
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.TNTPrimed

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CreateSphereAction(val origin: Location, val radius: Int, val material: Material) {

    fun execute(): Unit {
        val blocks = Sphere(origin, radius).blocks()
        var i: Int = 0
        blocks.forEach {
            if (it.type == Material.AIR) {
                if (material == Material.TNT) {
                    val tnt = origin.world.spawn(it.location, TNTPrimed::class.java)
                    tnt.fuseTicks = 10 + i
                    i++
                } else {
                    it.setType(material, true)
                }
            }
        }
    }
}
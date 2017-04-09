package com.mti.creeps.command

import com.mti.creeps.action.CreateSphereAction
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class CommandSpawn : CommandExecutor {

    val authorized = listOf("lava", "water", "sand", "tnt")

    val radius = 5

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player && sender.isOp) {
            if (args != null && args.size == 4) {
                val x = args[0].toDouble()
                val y = args[1].toDouble()
                val z = args[2].toDouble()
                val mat: String = args[3]

                if (authorized.contains(mat.toLowerCase())) {
                    if (y > 1 && (y + radius) < sender.world.maxHeight) {
                        when (mat.toLowerCase()) {
                            "lava" -> CreateSphereAction(Location(sender.world, x, y, z), radius, Material.LAVA)
                                    .execute()
                            "water" -> CreateSphereAction(Location(sender.world, x, y, z), radius, Material.WATER)
                                    .execute()
                            "sand" -> CreateSphereAction(Location(sender.world, x, y, z), radius, Material.SAND)
                                    .execute()
                            "tnt" -> CreateSphereAction(Location(sender.world, x, y, z), radius, Material.TNT)
                                    .execute()
                        }
                        return true
                    }
                }


            }
        }
        return false
    }
}
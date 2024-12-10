//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.elilroy.angleguard.events;

import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishmentManager {
    public PunishmentManager() {
    }

    public static void handlePunishment(AngleGuard plugin, Player player, String check, Integer flags, Integer maxflags, Boolean getBroadcast, Boolean getPunishment, String broadcastConfigPath, String punishmentConfigPath, String description) {
        boolean broadcastEnable = getBroadcast;
        boolean punishmentEnable = getPunishment;
        if (plugin != null) {
            if (broadcastEnable) {
                for(String message : plugin.getConfig().getStringList(broadcastConfigPath)) {
                    message = ColorUtils.translate(message).replace("%player%", player.getName()).replace("%check%", check).replace("%vl%", String.valueOf(flags)).replace("%max_vl%", String.valueOf(maxflags));
                    Bukkit.broadcastMessage(message);
                }
            }

            if (punishmentEnable) {
                for(String command : plugin.getConfig().getStringList(punishmentConfigPath)) {
                    command = command.replace("%player%", player.getName()).replace("%check%", check).replace("%vl%", String.valueOf(flags)).replace("%max_vl%", String.valueOf(maxflags));
                    String finalCommand = command;
                    Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand));
                }
            }

            AlertManager.sendAlert(plugin, player, check, flags, maxflags, description);
        }
    }
}

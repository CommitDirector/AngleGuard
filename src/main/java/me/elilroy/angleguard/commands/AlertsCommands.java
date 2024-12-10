package me.elilroy.angleguard.commands;

import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommands implements CommandExecutor {
    private final AngleGuard plugin;

    public AlertsCommands(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtils.translate("&cOnly players can use this commands!"));
            return true;
        } else if (!player.hasPermission("angleguard.alerts")) {
            player.sendMessage("You don't have permission to use this commands!");
            return true;
        } else {
            this.plugin.toggleAlerts(player);
            boolean Toggled = this.plugin.isAlertsToggled(player);
            if (Toggled) {
                player.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.toggle-alerts", "&8[&eAG&8] &7AngleGuard Alerts %result%")).replace("%result%", "Enable"));
                return true;
            } else {
                player.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.toggle-alerts", "&8[&eAG&8] &7AngleGuard Alerts %result%")).replace("%result%", "Disable"));
                return true;
            }
        }
    }
}

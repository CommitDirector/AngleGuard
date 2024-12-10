package me.elilroy.angleguard.commands;

import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DisableCheckCommands implements CommandExecutor {
    private final AngleGuard plugin;

    public DisableCheckCommands(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("angleguard.disablecheck")) {
            sender.sendMessage(ColorUtils.translate("&e&lAngleGuard &7v" + this.plugin.getDescription().getVersion()) + " &eMade by &bL4oySt0rm&7, &bmF0X_");
            return false;
        } else if (args.length > 1) {
            String checkName = args[1].toLowerCase();
            if (checkName.startsWith("autototem")) {
                String type = checkName.substring(9);
                if (this.plugin.getConfig().isConfigurationSection("check.auto-totem." + type)) {
                    this.plugin.getConfig().set("check.auto-totem." + type + ".enable", false);
                    this.plugin.saveConfig();
                    sender.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.disable-check", "&8[&eAG&8] &7Check &e%check% &7has been disabled!").replace("%check%", checkName)));
                    return true;
                } else {
                    sender.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.check-not-found", "&8[&eAG&8] &7Check &c%check% &7is not found!").replace("%check%", checkName)));
                    return true;
                }
            } else if (this.plugin.getConfig().isConfigurationSection("check." + checkName)) {
                this.plugin.getConfig().set("check." + checkName + ".enable", false);
                this.plugin.saveConfig();
                sender.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.disable-check", "&8[&eAG&8] &7Check &e%check% &7has been disabled!").replace("%check%", checkName)));
                return true;
            } else {
                sender.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.check-not-found", "&8[&eAG&8] &7Check &c%check% &7is not found!").replace("%check%", checkName)));
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /angleguard disablecheck (check)");
            return true;
        }
    }
}

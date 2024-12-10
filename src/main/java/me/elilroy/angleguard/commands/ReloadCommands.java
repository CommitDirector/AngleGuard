package me.elilroy.angleguard.commands;

import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommands implements CommandExecutor {
    private final AngleGuard plugin;

    public ReloadCommands(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("angleguard.reload")) {
            sender.sendMessage(ColorUtils.translate("&e&lAngleGuard &7v" + this.plugin.getDescription().getVersion()) + " &eMade by &bL4oySt0rm&7, &bmF0X_");
            return false;
        } else {
            this.plugin.reloadConfig();
            sender.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.reload", "&8[&eAG&8] &7AngleGuard config has been reloaded!")));
            return true;
        }
    }
}

package me.elilroy.angleguard.commands;

import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestCommands implements CommandExecutor {
    private final AngleGuard plugin;

    public TestCommands(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("angleguard.testautototem")) {
            sender.sendMessage(ColorUtils.translate("&e&lAngleGuard &7v" + this.plugin.getDescription().getVersion()) + " &eMade by &bL4oySt0rm&7, &bmF0X_");
            return false;
        } else {
            if (args.length > 1) {
                Player target = Bukkit.getPlayer(args[1]);
                Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                    target.getInventory().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                    target.damage(target.getHealth());
                }, 20L);
            }

            return true;
        }
    }
}

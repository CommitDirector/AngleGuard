package me.elilroy.angleguard.commands;

import me.elilroy.angleguard.AngleGuard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AngleGuardCommands implements CommandExecutor {
    private final AngleGuard plugin;
    private final HelpCommands helpCommands;
    private final ReloadCommands reloadCommands;
    private final DisableCheckCommands disableCheckCommands;
    private final EnableCheckCommands enableCheckCommands;
    private final AlertsCommands alertsCommands;
    private final TestCommands testCommands;

    public AngleGuardCommands(AngleGuard plugin) {
        this.plugin = plugin;
        this.helpCommands = new HelpCommands(plugin);
        this.reloadCommands = new ReloadCommands(plugin);
        this.disableCheckCommands = new DisableCheckCommands(plugin);
        this.enableCheckCommands = new EnableCheckCommands(plugin);
        this.alertsCommands = new AlertsCommands(plugin);
        this.testCommands = new TestCommands(plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        } else if (args.length != 0 && !args[0].equalsIgnoreCase("help")) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    return this.reloadCommands.onCommand(sender, command, label, args);
                case "disablecheck":
                    return this.disableCheckCommands.onCommand(sender, command, label, args);
                case "enablecheck":
                    return this.enableCheckCommands.onCommand(sender, command, label, args);
                case "alerts":
                    return this.alertsCommands.onCommand(sender, command, label, args);
                case "test":
                    return this.testCommands.onCommand(sender, command, label, args);
                default:
                    this.helpCommands.onCommand(sender, command, label, args);
                    return true;
            }
        } else {
            this.helpCommands.onCommand(sender, command, label, args);
            return true;
        }
    }
}

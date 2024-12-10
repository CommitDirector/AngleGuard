package me.elilroy.angleguard.commands;

import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommands implements CommandExecutor {
    private final AngleGuard plugin;

    public HelpCommands(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("angleguard.use")) {
            sender.sendMessage(ColorUtils.translate("&e&lAngleGuard &7v" + this.plugin.getDescription().getVersion()) + " &eMade by &bL4oySt0rm&7, &bmF0X_");
            return false;
        } else {
            sender.sendMessage(ColorUtils.translate("#ACB6E5=#ABB9E5=#A9BBE5=#A8BEE5=#A6C1E5=#A5C3E6=#A4C6E6=#A2C8E6=#A1CBE6=#9FCEE6=#9ED0E6[#9DD3E6 #9BD6E6&lH#9AD8E6&lE#98DBE7&lL#97DDE7&lP#95E0E7 #94E3E7]#93E5E7=#91E8E7=#90EBE7=#8EEDE7=#8DF0E7=#8CF2E8=#8AF5E8=#89F8E8=#87FAE8=#86FDE8="));
            sender.sendMessage("");
            sender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A8BEE5a#A4C6E6g#9FCEE6 #9BD6E6r#97DDE7e#93E5E7l#8EEDE7o#8AF5E8a#86FDE8d &7# to reload the configuration."));
            sender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A9BBE5a#A7BFE5g#A4C4E6 #A2C9E6d#9FCEE6i#9DD2E6s#9AD7E6a#98DCE7b#95E1E7l#93E5E7e#90EAE7c#8EEFE7h#8BF4E8e#89F8E8c#86FDE8k &7# to disable plugin check."));
            sender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A9BBE5a#A7C0E5g#A4C5E6 #A1CAE6e#9ECFE6n#9CD4E6a#99DAE7b#96DFE7l#94E4E7e#91E9E7c#8EEEE7h#8BF3E8e#89F8E8c#86FDE8k &7# to enable plugin check."));
            sender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A7C0E5a#A1CAE6g#9CD4E6 #96DFE7h#91E9E7e#8BF3E8l#86FDE8p &7# to get help with commands."));
            sender.sendMessage(ColorUtils.translate("  &7-> #86FDE8/#8AF5E8a#8EEDE7g#93E5E7 #97DDE7a#9BD6E6l#9FCEE6e#A4C6E6r#A8BEE5t#ACB6E5s &7# to toggle alerts."));
            sender.sendMessage("");
            sender.sendMessage(ColorUtils.translate("#ACB6E5=#ABB9E5=#A9BBE5=#A8BEE5=#A6C1E5=#A5C3E6=#A4C6E6=#A2C8E6=#A1CBE6=#9FCEE6=#9ED0E6[#9DD3E6 #9BD6E6&lH#9AD8E6&lE#98DBE7&lL#97DDE7&lP#95E0E7 #94E3E7]#93E5E7=#91E8E7=#90EBE7=#8EEDE7=#8DF0E7=#8CF2E8=#8AF5E8=#89F8E8=#87FAE8=#86FDE8="));
            sender.sendMessage("");
            return false;
        }
    }
}

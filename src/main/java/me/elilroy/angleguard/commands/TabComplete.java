package me.elilroy.angleguard.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
    public TabComplete() {
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList();
        if (command.getName().equalsIgnoreCase("angleguard") && args.length == 1) {
            if (sender.hasPermission("angleguard.reload")) {
                completions.add("reload");
            }

            if (sender.hasPermission("angleguard.togglecheck")) {
                completions.add("disablecheck");
                completions.add("enablecheck");
            }

            if (sender.hasPermission("angleguard.alerts")) {
                completions.add("alerts");
            }

            if (sender.hasPermission("angleguard.testautototem")) {
                completions.add("test");
            }

            completions.add("help");
        }

        return this.filterTabCompletion(completions, args);
    }

    private List<String> filterTabCompletion(List<String> completions, String[] args) {
        List<String> filteredCompletions = new ArrayList();
        if (args.length > 0) {
            String currentArg = args[args.length - 1].toLowerCase();

            for(String completion : completions) {
                if (completion.toLowerCase().startsWith(currentArg)) {
                    filteredCompletions.add(completion);
                }
            }
        } else {
            filteredCompletions.addAll(completions);
        }

        return filteredCompletions;
    }
}

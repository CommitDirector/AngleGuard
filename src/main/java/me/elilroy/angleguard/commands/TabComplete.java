package me.elilroy.angleguard.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
    public List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
        ArrayList<String> arrayList = new ArrayList();
        if (paramCommand.getName().equalsIgnoreCase("angleguard") && paramArrayOfString.length == 1) {
            if (paramCommandSender.hasPermission("angleguard.reload"))
                arrayList.add("reload");
            if (paramCommandSender.hasPermission("angleguard.togglecheck")) {
                arrayList.add("disablecheck");
                arrayList.add("enablecheck");
            }
            if (paramCommandSender.hasPermission("angleguard.alerts"))
                arrayList.add("alerts");
            arrayList.add("help");
        }
        return filterTabCompletion(arrayList, paramArrayOfString);
    }

    static {

    }

    private List<String> filterTabCompletion(List<String> paramList, String[] paramArrayOfString) {
        ArrayList<String> arrayList = new ArrayList();
        if (paramArrayOfString.length > 0) {
            String str = paramArrayOfString[paramArrayOfString.length - 1].toLowerCase();
            for (String str1 : paramList) {
                if (str1.toLowerCase().startsWith(str))
                    arrayList.add(str1);
            }
        } else {
            arrayList.addAll(paramList);
        }
        return arrayList;
    }
}

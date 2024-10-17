package me.elilroy.angleguard.utils;

import java.util.List;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishmentUtils {
    static {

    }

    public static void handlePunishment(AngleGuard paramAngleGuard, Player paramPlayer, String paramString1, Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean1, Boolean paramBoolean2, String paramString2, String paramString3, String paramString4) {
        boolean bool1 = paramBoolean1.booleanValue();
        boolean bool2 = paramBoolean2.booleanValue();
        if (paramAngleGuard == null)
            return;
        if (bool1) {
            List<String> list = paramAngleGuard.getConfig().getStringList(paramString2);
            for (String str : list) {
                str = ColorUtils.translate(str).replace("%player%", paramPlayer.getName()).replace("%check%", paramString1).replace("%vl%", String.valueOf(paramInteger1)).replace("%max_vl%", String.valueOf(paramInteger2));
                Bukkit.broadcastMessage(str);
            }
        }
        if (bool2) {
            List<String> list = paramAngleGuard.getConfig().getStringList(paramString3);
            for (String str : list) {
                str = str.replace("%player%", paramPlayer.getName()).replace("%check%", paramString1).replace("%vl%", String.valueOf(paramInteger1)).replace("%max_vl%", String.valueOf(paramInteger2));
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), str);
            }
        }
        AlertUtils.sendAlert(paramAngleGuard, paramPlayer, paramString1, paramInteger1, paramInteger2, paramString4);
    }
}

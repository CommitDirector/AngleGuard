package me.elilroy.angleguard.utils;

import java.util.List;
import me.elilroy.angleguard.AngleGuard;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AlertUtils {
    static {

    }

    public static void sendAlert(AngleGuard paramAngleGuard, Player paramPlayer, String paramString1, Integer paramInteger1, Integer paramInteger2, String paramString2) {
        String str = paramAngleGuard.getConfig().getString("messages.alert", "");
        str = ColorUtils.translate(str);
        boolean bool = paramAngleGuard.isAlertsToggled(paramPlayer);
        if (str != null)
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("angleguard.alerts") && bool) {
                    String str1 = paramAngleGuard.getConfig().getString("messages.alert-console", "[AG] %player% failed %check% VL[%vl%/%max_vl%]").replace("%player%", (paramPlayer.getName() != null) ? paramPlayer.getName() : "Unknown Player").replace("%check%", (paramString1 != null) ? paramString1 : "Unknown Check").replace("%vl%", String.valueOf((paramInteger1 != null) ? paramInteger1.intValue() : 0)).replace("%max_vl%", String.valueOf((paramInteger2 != null) ? paramInteger2.intValue() : 0));
                    Boolean bool1 = Boolean.valueOf(paramAngleGuard.getConfig().getBoolean("general.console-alert", true));
                    TextComponent textComponent = new TextComponent(str.replace("%player%", (paramPlayer.getName() != null) ? paramPlayer.getName() : "Unknown Player").replace("%check%", (paramString1 != null) ? paramString1 : "Unknown Check").replace("%vl%", String.valueOf((paramInteger1 != null) ? paramInteger1.intValue() : 0)).replace("%max_vl%", String.valueOf((paramInteger2 != null) ? paramInteger2.intValue() : 0)));
                    List<String> list = paramAngleGuard.getConfig().getStringList("messages.alerts-hover");
                    List<String> list1 = paramAngleGuard.getConfig().getStringList("messages.command-hover-click");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String str3 : list) {
                        if (str3 != null)
                            stringBuilder.append(ColorUtils.translate(str3).replace("%tps%", String.format("%.0f", new Object[] { Double.valueOf(TpsUtils.getTPS()) })).replace("%client_version%", (paramAngleGuard.getVersion(paramPlayer) != null) ? paramAngleGuard.getVersion(paramPlayer) : "Unknown").replace("%client_brand%", ((new ClientBrandUtils(paramAngleGuard)).getClientBrand(paramPlayer) != null) ? (new ClientBrandUtils(paramAngleGuard)).getClientBrand(paramPlayer) : "Unknown").replace("%player%", (paramPlayer.getName() != null) ? paramPlayer.getName() : "Unknown Player").replace("%ping%", String.valueOf(paramPlayer.getPing())).replace("%check%", (paramString1 != null) ? paramString1 : "Unknown Check").replace("%description%", (paramString2 != null) ? paramString2 : "").replace("%player%", (paramPlayer.getName() != null) ? paramPlayer.getName() : "Unknown Player")).append("\n");
                    }
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Content[] { (Content)new Text(String.valueOf(stringBuilder)) });
                    textComponent.setHoverEvent(hoverEvent);
                    if (!list1.isEmpty() && list1.get(0) != null) {
                        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, ((String)list1.get(0)).replace("%player%", paramPlayer.getName()));
                        textComponent.setClickEvent(clickEvent);
                    }
                    player.spigot().sendMessage((BaseComponent)textComponent);
                    if (bool1.booleanValue())
                        paramAngleGuard.getLogger().info(str1);
                    Boolean bool2 = Boolean.valueOf(paramAngleGuard.getConfig().getBoolean("general.sound-alert.enable", true));
                    String str2 = paramAngleGuard.getConfig().getString("general.sound-alert.sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
                    if (bool2.booleanValue() && str2 != null)
                        try {
                            Sound sound = Sound.valueOf(str2);
                            player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
                        } catch (IllegalArgumentException illegalArgumentException) {
                            paramAngleGuard.getLogger().warning(String.valueOf((new StringBuilder()).append("Invalid sound type: ").append(str2)));
                        }
                }
            }
        if (paramAngleGuard.getConfig().getBoolean("discord-webhook.alerts.enabled"))
            DiscordUtils.sendWebhookAlert(paramPlayer, paramString1, paramInteger1.intValue(), paramInteger2.intValue());
    }
}

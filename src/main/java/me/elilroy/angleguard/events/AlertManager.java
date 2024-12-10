package me.elilroy.angleguard.events;

import java.util.List;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ClientBrandUtils;
import me.elilroy.angleguard.utils.ColorUtils;
import me.elilroy.angleguard.utils.DiscordUtils;
import me.elilroy.angleguard.utils.TpsUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AlertManager {
    public AlertManager() {
    }

    public static void sendAlert(AngleGuard plugin, Player player, String check, Integer flags, Integer maxflags, String description) {
        String unmessage = ColorUtils.translate(plugin.getConfig().getString("messages.alert", ""));
        boolean Toggled = plugin.isAlertsToggled(player);
        if (unmessage != null) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("angleguard.alerts") && Toggled) {
                    String console = plugin.getConfig().getString("messages.alert-console", "[AG] %player% failed %check% VL[%vl%/%max_vl%]").replace("%player%", player.getName() != null ? player.getName() : "Unknown Player").replace("%check%", check != null ? check : "Unknown Check").replace("%vl%", String.valueOf(flags != null ? flags : 0)).replace("%max_vl%", String.valueOf(maxflags != null ? maxflags : 0));
                    Boolean enableConsole = plugin.getConfig().getBoolean("general.console-alert", true);
                    TextComponent message = new TextComponent(unmessage.replace("%player%", player.getName() != null ? player.getName() : "Unknown Player").replace("%check%", check != null ? check : "Unknown Check").replace("%vl%", String.valueOf(flags != null ? flags : 0)).replace("%max_vl%", String.valueOf(maxflags != null ? maxflags : 0)));
                    List<String> hoverMessages = plugin.getConfig().getStringList("messages.alerts-hover");
                    List<String> clickCommands = plugin.getConfig().getStringList("messages.command-hover-click");
                    StringBuilder hoverText = new StringBuilder();

                    for(int i = 0; i < hoverMessages.size(); ++i) {
                        String hoverMessage = hoverMessages.get(i);
                        if (hoverMessage != null) {
                            String messageLine = ColorUtils.translate(hoverMessage).replace("%tps%", String.format("%.0f", TpsUtils.getTPS())).replace("%client_version%", plugin.getVersion(player) != null ? plugin.getVersion(player) : "Unknown").replace("%client_brand%", (new ClientBrandUtils(plugin)).getClientBrand(player) != null ? (new ClientBrandUtils(plugin)).getClientBrand(player) : "Unknown").replace("%player%", player.getName() != null ? player.getName() : "Unknown Player").replace("%ping%", String.valueOf(player.getPing())).replace("%check%", check != null ? check : "Unknown Check").replace("%description%", description != null ? description : "").replace("%player%", player.getName() != null ? player.getName() : "Unknown Player");
                            hoverText.append(messageLine);
                            if (i < hoverMessages.size() - 1) {
                                hoverText.append("\n");
                            }
                        }
                    }

                    HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, new Content[]{new Text(hoverText.toString())});
                    message.setHoverEvent(hoverEvent);
                    if (!clickCommands.isEmpty() && clickCommands.get(0) != null) {
                        String clickCommand = clickCommands.get(0).replace("%player%", player.getName());
                        ClickEvent clickEvent = new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, clickCommand);
                        message.setClickEvent(clickEvent);
                    }

                    p.spigot().sendMessage(message);
                    if (enableConsole) {
                        plugin.getLogger().info(console);
                    }

                    Boolean soundsEnabled = plugin.getConfig().getBoolean("general.sound-alert.enable", true);
                    String soundTypeString = plugin.getConfig().getString("general.sound-alert.sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
                    if (soundsEnabled && soundTypeString != null) {
                        try {
                            Sound soundType = Sound.valueOf(soundTypeString);
                            p.playSound(p.getLocation(), soundType, 1.0F, 1.0F);
                        } catch (IllegalArgumentException var20) {
                            plugin.getLogger().warning("Invalid sound type: " + soundTypeString);
                        }
                    }
                }
            }
        }

        if (plugin.getConfig().getBoolean("discord-webhook.alerts.enabled")) {
            DiscordUtils.sendWebhookAlert(player, check, flags, maxflags);
        }

    }
}

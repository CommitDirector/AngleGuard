package me.elilroy.angleguard.utils;

import java.awt.Color;
import java.io.IOException;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DiscordUtils {
    private static AngleGuard plugin;

    public DiscordUtils(AngleGuard paramAngleGuard) {
        plugin = paramAngleGuard;
    }

    public static void sendWebhookAlert(Player paramPlayer, String paramString, int paramInt1, int paramInt2) {
        if (!plugin.getConfig().getBoolean("discord-webhook.alerts.enabled"))
            return;
        String str1 = plugin.getConfig().getString("discord-webhook.alerts.url");
        if (str1 == null || str1.equals("insert-webhook-url-here")) {
            Bukkit.getLogger().warning("Webhook URL is not set! Please configure it in config.yml.");
            return;
        }
        DiscordWebhook discordWebhook = new DiscordWebhook(str1);
        discordWebhook.setAvatarUrl(plugin.getConfig().getString("discord-webhook.alerts.avatar"));
        discordWebhook.setUsername(plugin.getConfig().getString("discord-webhook.alerts.username"));
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
        int i = plugin.getConfig().getInt("discord-webhook.alerts.color-r", 252);
        int j = plugin.getConfig().getInt("discord-webhook.alerts.color-g", 140);
        int k = plugin.getConfig().getInt("discord-webhook.alerts.color-b", 3);
        embedObject.setColor(new Color(i, j, k));
        String str2 = plugin.getConfig().getString("discord-webhook.alerts.title", "Alert");
        String str3 = plugin.getConfig().getString("discord-webhook.alerts.description", "").replace("%player%", paramPlayer.getName()).replace("%check%", paramString).replace("%max_vl%", String.valueOf(paramInt2)).replace("%vl%", String.valueOf(paramInt1));
        embedObject.setTitle(str2);
        embedObject.setDescription(str3);
        String str4 = plugin.getConfig().getString("discord-webhook.alerts.thumbnail", "").replace("%player%", paramPlayer.getName()).replace("%check%", paramString).replace("%vl%", String.valueOf(paramInt1)).replace("%max-vl%", String.valueOf(paramInt2));
        if (!str4.isEmpty())
            embedObject.setThumbnail(str4);
        if (plugin.getConfig().getBoolean("discord-webhook.alerts.server-name-field", false))
            embedObject.addField("Server", ServerNameUtils.Name(), true);
        if (plugin.getConfig().getBoolean("discord-webhook.alerts.location-field", false)) {
            Location location = paramPlayer.getLocation();
            String str5 = location.getWorld().getName();
            String str6 = String.format("%.2f", new Object[] { Double.valueOf(location.getX()) });
            String str7 = String.format("%.2f", new Object[] { Double.valueOf(location.getY()) });
            String str8 = String.format("%.2f", new Object[] { Double.valueOf(location.getZ()) });
            String str9 = String.valueOf((new StringBuilder()).append(str5).append(" **").append(str6).append(", ").append(str7).append(", ").append(str8).append("**"));
            embedObject.addField("Location", str9, true);
        }
        if (plugin.getConfig().getBoolean("discord-webhook.alerts.ping-tps-field", false)) {
            embedObject.addField("Ping", String.valueOf(paramPlayer.getPing()), true);
            embedObject.addField("TPS", String.format("%.0f", new Object[] { Double.valueOf(TpsUtils.getTPS()) }), true);
        }
        discordWebhook.addEmbed(embedObject);
        try {
            discordWebhook.execute();
        } catch (IOException iOException) {
            iOException.printStackTrace();
            Bukkit.getLogger().warning("Failed to send webhook alert!");
        }
    }
}

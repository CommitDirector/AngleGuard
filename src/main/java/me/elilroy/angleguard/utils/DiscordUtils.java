package me.elilroy.angleguard.utils;

import java.awt.Color;
import java.io.IOException;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DiscordUtils {
    private static AngleGuard plugin;

    public DiscordUtils(AngleGuard plugin) {
        DiscordUtils.plugin = plugin;
    }

    public static void sendWebhookAlert(Player player, String check, int vl, int maxVl) {
        if (plugin.getConfig().getBoolean("discord-webhook.alerts.enabled")) {
            String url = plugin.getConfig().getString("discord-webhook.alerts.url");
            if (url != null && !url.equals("insert-webhook-url-here")) {
                DiscordWebhook webhook = new DiscordWebhook(url);
                webhook.setAvatarUrl(plugin.getConfig().getString("discord-webhook.alerts.avatar"));
                webhook.setUsername(plugin.getConfig().getString("discord-webhook.alerts.username"));
                DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                int r = plugin.getConfig().getInt("discord-webhook.alerts.color-r", 252);
                int g = plugin.getConfig().getInt("discord-webhook.alerts.color-g", 140);
                int b = plugin.getConfig().getInt("discord-webhook.alerts.color-b", 3);
                embed.setColor(new Color(r, g, b));
                String title = plugin.getConfig().getString("discord-webhook.alerts.title", "Alert");
                String description = plugin.getConfig().getString("discord-webhook.alerts.description", "").replace("%player%", player.getName()).replace("%check%", check).replace("%max_vl%", String.valueOf(maxVl)).replace("%vl%", String.valueOf(vl));
                embed.setTitle(title);
                embed.setDescription(description);
                String thumbnail = plugin.getConfig().getString("discord-webhook.alerts.thumbnail", "").replace("%player%", player.getName()).replace("%check%", check).replace("%vl%", String.valueOf(vl)).replace("%max-vl%", String.valueOf(maxVl));
                if (!thumbnail.isEmpty()) {
                    embed.setThumbnail(thumbnail);
                }

                if (plugin.getConfig().getBoolean("discord-webhook.alerts.server-name-field", false)) {
                    embed.addField("Server", ServerNameUtils.Name(), true);
                }

                if (plugin.getConfig().getBoolean("discord-webhook.alerts.location-field", false)) {
                    Location Loc = player.getLocation();
                    String World = Loc.getWorld().getName();
                    String X = String.format("%.2f", Loc.getX());
                    String Y = String.format("%.2f", Loc.getY());
                    String Z = String.format("%.2f", Loc.getZ());
                    String Location = World + " **" + X + ", " + Y + ", " + Z + "**";
                    embed.addField("Location", Location, true);
                }

                if (plugin.getConfig().getBoolean("discord-webhook.alerts.ping-tps-field", false)) {
                    embed.addField("Ping", String.valueOf(player.getPing()), true);
                    embed.addField("TPS", String.format("%.0f", TpsUtils.getTPS()), true);
                }

                webhook.addEmbed(embed);

                try {
                    webhook.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    Bukkit.getLogger().warning("Failed to send webhook alert!");
                }

            } else {
                Bukkit.getLogger().warning("Webhook URL is not set! Please configure it in config.yml.");
            }
        }
    }
}

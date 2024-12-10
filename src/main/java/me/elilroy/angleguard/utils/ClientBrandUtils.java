package me.elilroy.angleguard.utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ClientBrandUtils implements Listener {
    public static final String CHANNEL_NAME = "minecraft:brand";
    private boolean registered = false;
    private final Map<UUID, String> playerBrands = new HashMap();
    private final AngleGuard plugin;

    public ClientBrandUtils(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public boolean register() {
        if (this.registered) {
            return false;
        } else {
            Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, "minecraft:brand", new ClientBrandMessageListener());
            this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
            return this.registered = true;
        }
    }

    public String getClientBrand(Player player) {
        UUID uuid = player.getUniqueId();
        return (String)this.playerBrands.getOrDefault(uuid, "UNKNOWN");
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        this.playerBrands.remove(uuid);
    }

    private class ClientBrandMessageListener implements PluginMessageListener {
        private ClientBrandMessageListener() {
        }

        public void onPluginMessageReceived(String channel, Player player, byte[] message) {
            if (channel.equals("minecraft:brand")) {
                UUID uuid = player.getUniqueId();
                String brand = new String(message, StandardCharsets.UTF_8);
                brand = brand.substring(1);
                ClientBrandUtils.this.playerBrands.put(uuid, brand);
            }
        }
    }
}

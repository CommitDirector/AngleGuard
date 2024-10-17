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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ClientBrandUtils implements Listener {
    private final AngleGuard plugin;
    private final Map<UUID, String> playerBrands = new HashMap<>();
    private boolean registered = false;

    public ClientBrandUtils(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
    }

    public String getClientBrand(Player paramPlayer) {
        UUID uUID = paramPlayer.getUniqueId();
        return this.playerBrands.getOrDefault(uUID, "UNKNOWN");
    }

    public boolean register() {
        if (this.registered)
            return false;
        Bukkit.getMessenger().registerIncomingPluginChannel((Plugin)this.plugin, "minecraft:brand", new ClientBrandMessageListener());
        this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
        return this.registered = true;
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent paramPlayerQuitEvent) {
        Player player = paramPlayerQuitEvent.getPlayer();
        UUID uUID = player.getUniqueId();
        this.playerBrands.remove(uUID);
    }

    private class ClientBrandMessageListener implements PluginMessageListener {
        private ClientBrandMessageListener() {}

        public void onPluginMessageReceived(String param1String, Player param1Player, byte[] param1ArrayOfbyte) {
            if (!param1String.equals("minecraft:brand"))
                return;
            UUID uUID = param1Player.getUniqueId();
            String str = new String(param1ArrayOfbyte, StandardCharsets.UTF_8);
            str = str.substring(1);
            ClientBrandUtils.this.playerBrands.put(uUID, str);
        }
    }
}

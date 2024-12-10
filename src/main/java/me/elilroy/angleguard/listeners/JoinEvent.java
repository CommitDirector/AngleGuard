package me.elilroy.angleguard.listeners;

import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    private final AngleGuard plugin;

    public JoinEvent(AngleGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.plugin.getCW().ClearFlags(player);
        this.plugin.getAutoTotemA().ClearFlags(player);
        this.plugin.getAutoTotemB().ClearFlags(player);
        this.plugin.getAutoTotemC().ClearFlags(player);
        this.plugin.getAutoTotemD().ClearFlags(player);
        boolean Toggled = this.plugin.isAlertsToggled(player);
        if (Toggled) {
            player.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.toggle-alerts", "&8[&eAG&8] &7AngleGuard Alerts %result%")).replace("%result%", "Enable"));
        } else {
            player.sendMessage(ColorUtils.translate(this.plugin.getConfig().getString("messages.toggle-alerts", "&8[&eAG&8] &7AngleGuard Alerts %result%")).replace("%result%", "Disable"));
        }

    }
}

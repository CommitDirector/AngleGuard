package me.elilroy.angleguard.listeners;

import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.HashMap;
import java.util.UUID;

public class SwapHandListener implements Listener {
    private final AngleGuard plugin;

    public SwapHandListener(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
    }

    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent paramPlayerSwapHandItemsEvent) {
        Material material = (paramPlayerSwapHandItemsEvent.getOffHandItem() == null) ? Material.AIR : paramPlayerSwapHandItemsEvent.getOffHandItem().getType();
        Player player = paramPlayerSwapHandItemsEvent.getPlayer();
        int i = this.plugin.getConfig().getInt("check.auto-totem.b.delay") / 2;
        if (material != Material.TOTEM_OF_UNDYING)
            return;
        if (!this.plugin.lastTotem.containsKey(player.getUniqueId()))
            return;
        long l = ((Long)this.plugin.lastTotem.get(player.getUniqueId())).longValue();
        if (this.plugin.getTicks() - l > (i + 10))
            return;
        Integer integer1 = (Integer)(this.plugin.getAutoTotemB()).totemsFlagged.get(player.getUniqueId());
        Integer integer2 = (Integer)(this.plugin.getAutoTotem()).totemsPopped.get(player.getUniqueId());
        if ((((integer1 == null) ? 1 : 0) | ((integer2 == null) ? 1 : 0)) != 0) {
            this.plugin.getAutoTotemB().punishAndRegisterFlag((HumanEntity)player);
        } else if (integer1.intValue() > integer2.intValue() / 3) {
            if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
                return;
            if (this.plugin.getConfig().getBoolean("check.auto-totem.b.setback"))
                paramPlayerSwapHandItemsEvent.setCancelled(true);
            this.plugin.getAutoTotemB().punishAndRegisterFlag((HumanEntity)player);
        } else {
            if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
                return;
            if (this.plugin.getConfig().getBoolean("check.auto-totem.d.setback"))
                paramPlayerSwapHandItemsEvent.setCancelled(true);
            this.plugin.getAutoTotemD().punishAndRegisterFlag((HumanEntity)player);
        }
    }
}

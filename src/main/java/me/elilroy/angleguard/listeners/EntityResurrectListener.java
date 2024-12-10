package me.elilroy.angleguard.listeners;

import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;

public class EntityResurrectListener implements Listener {
    private final AngleGuard plugin;

    public EntityResurrectListener(AngleGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event) {
        if (!event.isCancelled() && event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player)event.getEntity();
            UUID playerUUID = player.getUniqueId();
            if (this.plugin.getAutoTotem() != null && event.getEntity() instanceof Player) {
                long currentTicks = this.plugin.getTicks();
                Long lastTotem = (Long)this.plugin.lastTotem.get(playerUUID);
                if (lastTotem == null || currentTicks - lastTotem > 100L) {
                    this.plugin.lastTotem.put(playerUUID, currentTicks);
                    this.plugin.getAutoTotem().addTotem(player);
                }
            }
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onUseTotem(EntityResurrectEvent event) {
        LivingEntity var3 = event.getEntity();
        if (var3 instanceof Player player) {
            if (player.getInventory().getItemInMainHand().getType() != Material.TOTEM_OF_UNDYING) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.TOTEM_OF_UNDYING), 2)) {
                    this.plugin.getAutoTotemA().UseTotem.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }
    }
}

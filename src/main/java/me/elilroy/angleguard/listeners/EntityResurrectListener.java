package me.elilroy.angleguard.listeners;

import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class EntityResurrectListener implements Listener {
    private final AngleGuard plugin;

    public EntityResurrectListener(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent paramEntityResurrectEvent) {
        if (!paramEntityResurrectEvent.isCancelled() && paramEntityResurrectEvent.getEntityType() == EntityType.PLAYER) {
            Player player = (Player)paramEntityResurrectEvent.getEntity();
            UUID uUID = player.getUniqueId();
            if (this.plugin.getAutoTotem() != null && paramEntityResurrectEvent.getEntity() instanceof Player) {
                long l = this.plugin.getTicks();
                Long long_ = (Long)this.plugin.lastTotem.get(uUID);
                if (long_ == null || l - long_.longValue() > 100L) {
                    this.plugin.lastTotem.put(uUID, Long.valueOf(l));
                    this.plugin.getAutoTotem().addTotem(player);
                }
            }
        }
    }
}

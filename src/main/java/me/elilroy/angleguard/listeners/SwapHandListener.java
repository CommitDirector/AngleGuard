package me.elilroy.angleguard.listeners;

import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SwapHandListener implements Listener {
    private final AngleGuard plugin;

    public SwapHandListener(AngleGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        Material item = event.getOffHandItem() == null ? Material.AIR : event.getOffHandItem().getType();
        Player player = event.getPlayer();
        int ticks = this.plugin.getConfig().getInt("check.auto-totem.b.delay") / 2;
        if (item == Material.TOTEM_OF_UNDYING) {
            if (this.plugin.lastTotem.containsKey(player.getUniqueId())) {
                long lastUse = (Long)this.plugin.lastTotem.get(player.getUniqueId());
                if (this.plugin.getTicks() - lastUse <= (long)ticks) {
                    Integer totemsFlagged = (Integer)this.plugin.getAutoTotemB().totemsFlagged.get(player.getUniqueId());
                    Integer totemsPopped = (Integer)this.plugin.getAutoTotem().totemsPopped.get(player.getUniqueId());
                    if (totemsFlagged != null && totemsPopped != null) {
                        if (totemsFlagged > totemsPopped / 3) {
                            if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock"))) {
                                return;
                            }

                            if (this.plugin.getConfig().getBoolean("check.auto-totem.b.setback")) {
                                event.setCancelled(true);
                            }

                            this.plugin.getAutoTotemB().punishAndRegisterFlag(player);
                        }
                    } else {
                        this.plugin.getAutoTotemB().totemsFlagged.put(player.getUniqueId(), 0);
                        this.plugin.getAutoTotem().totemsPopped.put(player.getUniqueId(), 0);
                    }

                }
            }
        }
    }
}

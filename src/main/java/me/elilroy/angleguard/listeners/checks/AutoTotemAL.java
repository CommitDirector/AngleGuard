package me.elilroy.angleguard.listeners.check;

import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AutoTotemAL implements Listener {
    private final AngleGuard plugin;

    public AutoTotemAL(AngleGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    private void AutoTotemA(InventoryClickEvent event) {
        HumanEntity var3 = event.getWhoClicked();
        if (var3 instanceof Player player) {
            if (event.getRawSlot() == 45 && event.getCursor().getType() == Material.TOTEM_OF_UNDYING) {
                this.plugin.getAutoTotemA().timeClick.computeIfPresent(player.getUniqueId(), (uuid, clickTime) -> {
                    this.TotemCheck(player, clickTime);
                    return clickTime;
                });
            } else {
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.TOTEM_OF_UNDYING) {
                    this.plugin.getAutoTotemA().timeClick.put(player.getUniqueId(), System.currentTimeMillis());
                }

            }
        }
    }

    private void TotemCheck(Player player, Long TimeClicked) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            Long TimeUse = this.plugin.getAutoTotemA().UseTotem.remove(player.getUniqueId());
            if (TimeUse != null) {
                long currentTime = System.currentTimeMillis();
                long timeDifference = Math.abs(currentTime - TimeUse);
                long clickTimeDifference = Math.abs(currentTime - TimeClicked);
                int ticks = this.plugin.getConfig().getInt("check.auto-totem.a.delay");
                int MS = this.plugin.ticksToMs(ticks, 7.5F);
                if (clickTimeDifference <= (long)MS && timeDifference <= 1500L) {
                    this.plugin.getAutoTotemA().punishAndRegisterFlag(player);
                }

            }
        });
    }
}

package me.elilroy.angleguard.listeners.check;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AutoTotemACDL implements Listener {
    private final AngleGuard plugin;
    private final HashMap<UUID, Long> lastClick = new HashMap();

    public AutoTotemACDL(AngleGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!this.AutoTotemSetBackTypeA(event) && !this.quickItemMoveChecks(event) && this.plugin.getConfig().getBoolean("check.auto-totem.binds")) {
            this.bindTotemChecks(event);
        }

        Player player = (Player)event.getWhoClicked();
        this.lastClick.put(player.getUniqueId(), this.plugin.getTicks());
    }

    private boolean quickItemMoveChecks(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        int ticks = this.plugin.getConfig().getInt("check.auto-totem.c.delay") / 2;
        if (currentItem == null) {
            return false;
        } else {
            Material item = currentItem.getType();
            if (item != Material.TOTEM_OF_UNDYING) {
                return false;
            } else if (event.getSlot() != 40) {
                return false;
            } else if (!this.plugin.lastTotem.containsKey(player.getUniqueId())) {
                return false;
            } else {
                long lastUse = this.plugin.lastTotem.get(player.getUniqueId());
                if (this.plugin.getTicks() - lastUse > (long)ticks) {
                    return false;
                } else {
                    this.plugin.getAutoTotemC().punishAndRegisterFlag(player);
                    return true;
                }
            }
        }
    }

    private boolean bindTotemChecks(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock"))) {
            return false;
        } else if (!this.plugin.getConfig().getBoolean("check.auto-totem.d.binds")) {
            return false;
        } else if (event.getClick() != ClickType.UNKNOWN) {
            return false;
        } else if (event.getSlot() != 40) {
            return false;
        } else {
            if (this.plugin.getConfig().getBoolean("check.auto-totem.d.setback")) {
                event.setCancelled(true);
            }

            this.plugin.getAutoTotemD().punishAndRegisterFlag(player);
            return true;
        }
    }

    private boolean AutoTotemSetBackTypeA(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        ItemStack cursorItem = event.getCursor();
        if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock"))) {
            return false;
        } else if (cursorItem == null) {
            return false;
        } else if (cursorItem.getType() != Material.TOTEM_OF_UNDYING) {
            return false;
        } else if (event.getSlot() != 40) {
            return false;
        } else if (!this.plugin.lastTotem.containsKey(player.getUniqueId())) {
            return false;
        } else {
            int ticks = this.plugin.getConfig().getInt("check.auto-totem.a.delay");
            long lastUse = (Long)this.plugin.lastTotem.get(player.getUniqueId());
            long currentTick = this.plugin.getTicks();
            long getResult = currentTick - lastUse;
            if (getResult >= (long)ticks) {
                return false;
            } else {
                if (this.plugin.getConfig().getBoolean("check.auto-totem.a.setback")) {
                    event.setCancelled(true);
                }

                return true;
            }
        }
    }
}

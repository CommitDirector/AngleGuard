package me.elilroy.angleguard.listeners;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final AngleGuard plugin;
    private final HashMap<UUID, Long> lastClick = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent paramInventoryClickEvent) {
        if (!(paramInventoryClickEvent.getWhoClicked() instanceof Player))
            return;
        Player player = (Player)paramInventoryClickEvent.getWhoClicked();
        if (!autoTotemChecks(paramInventoryClickEvent) && !quickItemMoveChecks(paramInventoryClickEvent) && this.plugin.getConfig().getBoolean("check.auto-totem.d.binds"))
            bindTotemChecks(paramInventoryClickEvent);
        this.lastClick.put(player.getUniqueId(), Long.valueOf(this.plugin.getTicks()));
    }

    private boolean autoTotemChecks(InventoryClickEvent paramInventoryClickEvent) {
        Player player = (Player)paramInventoryClickEvent.getWhoClicked();
        ItemStack itemStack = paramInventoryClickEvent.getCursor();
        if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
            return false;
        if (itemStack == null || itemStack.getType() != Material.TOTEM_OF_UNDYING || paramInventoryClickEvent.getSlot() != 40)
            return false;
        int i = this.plugin.getConfig().getInt("check.auto-totem.a.delay");
        if (!this.plugin.lastTotem.containsKey(player.getUniqueId()))
            return false;
        long l = ((Long)this.plugin.lastTotem.get(player.getUniqueId())).longValue();
        if (this.plugin.getTicks() - l > i)
            return false;
        if (this.plugin.getConfig().getBoolean("check.auto-totem.a.setback"))
            paramInventoryClickEvent.setCancelled(true);
        this.plugin.getAutoTotemA().punishAndRegisterFlag((HumanEntity)player);
        return true;
    }

    private boolean bindTotemChecks(InventoryClickEvent paramInventoryClickEvent) {
        Player player = (Player)paramInventoryClickEvent.getWhoClicked();
        if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
            return false;
        ItemStack itemStack = player.getInventory().getItemInOffHand();
        if (itemStack == null || itemStack.getType() != Material.TOTEM_OF_UNDYING)
            return false;
        if (paramInventoryClickEvent.getClick() == ClickType.NUMBER_KEY || paramInventoryClickEvent.getClick() == ClickType.SWAP_OFFHAND)
            return true;
        if (this.plugin.getConfig().getBoolean("check.auto-totem.d.setback")) {
            paramInventoryClickEvent.setCancelled(true);
            this.plugin.getAutoTotemD().punishAndRegisterFlag((HumanEntity)player);
        }
        return true;
    }

    private boolean quickItemMoveChecks(InventoryClickEvent paramInventoryClickEvent) {
        Player player = (Player)paramInventoryClickEvent.getWhoClicked();
        ItemStack itemStack = paramInventoryClickEvent.getCurrentItem();
        if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
            return false;
        if (itemStack == null || itemStack.getType() != Material.TOTEM_OF_UNDYING)
            return false;
        if (paramInventoryClickEvent.getSlot() != 40)
            return false;
        int i = this.plugin.getConfig().getInt("check.auto-totem.c.delay") / 2;
        if (!this.plugin.lastTotem.containsKey(player.getUniqueId()))
            return false;
        long l = ((Long)this.plugin.lastTotem.get(player.getUniqueId())).longValue();
        if (this.plugin.getTicks() - l > i)
            return false;
        if (this.plugin.getConfig().getBoolean("check.auto-totem.c.setback"))
            paramInventoryClickEvent.setCancelled(true);
        this.plugin.getAutoTotemC().punishAndRegisterFlag((HumanEntity)player);
        return true;
    }

    public InventoryClickListener(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
    }
}

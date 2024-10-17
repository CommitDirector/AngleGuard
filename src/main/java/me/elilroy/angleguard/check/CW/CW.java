package me.elilroy.angleguard.check.CW;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.AlertUtils;
import me.elilroy.angleguard.utils.PunishmentUtils;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CW implements Listener {
    private final AngleGuard plugin;
    private final Map<UUID, Integer> crystalPlacements;
    private final Map<UUID, Integer> crystalWarnings;
    private final Map<UUID, Integer> rightCps;
    private final Map<UUID, Location> lastInteractLocation;
    private final Map<UUID, Long> lastInteractTime;

    public CW(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
        this.crystalPlacements = new HashMap<>();
        this.crystalWarnings = new HashMap<>();
        this.rightCps = new HashMap<>();
        this.lastInteractLocation = new HashMap<>();
        this.lastInteractTime = new HashMap<>();
        startPlacementResetTask();
        startWarningsResetTask();
    }

    private void startPlacementResetTask() {
        (new BukkitRunnable() {
            public void run() {
                for (UUID uUID : CW.this.crystalPlacements.keySet())
                    CW.this.crystalPlacements.put(uUID, Integer.valueOf(0));
                for (UUID uUID : CW.this.rightCps.keySet())
                    CW.this.rightCps.put(uUID, Integer.valueOf(0));
            }
        }).runTaskTimer((Plugin)this.plugin, 20L, 20L);
    }

    private void startWarningsResetTask() {
        int i = this.plugin.getConfig().getInt("general.reset-violet", 10);
        long l = (i * 60 * 1000);
        (new BukkitRunnable() {
            public void run() {
                for (UUID uUID : CW.this.crystalWarnings.keySet())
                    CW.this.crystalWarnings.put(uUID, Integer.valueOf(0));
            }
        }).runTaskTimer((Plugin)this.plugin, l, l);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent paramPlayerInteractEvent) {
        Player player = paramPlayerInteractEvent.getPlayer();
        UUID uUID = player.getUniqueId();
        boolean bool = this.plugin.getConfig().getBoolean("check.cw.enable", true);
        if (bool) {
            if (TpsUtils.getTPS() <= this.plugin.getConfig().getInt("check.cw.minimum-tps"))
                return;
            if (paramPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK && player.getInventory().getItemInMainHand().getType() == Material.END_CRYSTAL && paramPlayerInteractEvent.getClickedBlock() != null && paramPlayerInteractEvent.getClickedBlock().getType() == Material.OBSIDIAN) {
                this.lastInteractLocation.put(uUID, paramPlayerInteractEvent.getClickedBlock().getLocation());
                this.lastInteractTime.put(uUID, Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent paramEntitySpawnEvent) {
        if (paramEntitySpawnEvent.getEntity() instanceof org.bukkit.entity.EnderCrystal) {
            Location location = paramEntitySpawnEvent.getLocation();
            long l = System.currentTimeMillis();
            Player player = null;
            double d = Double.MAX_VALUE;
            for (Map.Entry<UUID, Location> entry : this.lastInteractLocation.entrySet()) {
                UUID uUID = (UUID)entry.getKey();
                Location location1 = (Location)entry.getValue();
                long l1 = ((Long)this.lastInteractTime.getOrDefault(uUID, Long.valueOf(0L))).longValue();
                if (location1 != null && l - l1 < 1000L && location1.getWorld().equals(location.getWorld())) {
                    double d1 = location1.distance(location);
                    if (d1 < d) {
                        d = d1;
                        player = Bukkit.getPlayer(uUID);
                    }
                }
            }
            if (player != null) {
                UUID uUID = player.getUniqueId();
                int i = ((Integer)this.crystalPlacements.getOrDefault(uUID, Integer.valueOf(0))).intValue() + 1;
                this.crystalPlacements.put(uUID, Integer.valueOf(i));
                int j = this.plugin.getConfig().getInt("check.cw.max-cps", 6);
                if (i > j) {
                    if (TpsUtils.getTPS() <= this.plugin.getConfig().getInt("check.cw.minimum-tps"))
                        return;
                    int k = ((Integer)this.crystalWarnings.get(uUID)).intValue() + 1;
                    this.crystalWarnings.put(uUID, Integer.valueOf(k));
                    this.crystalPlacements.put(uUID, Integer.valueOf(0));
                    Boolean bool1 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.cw.broadcast", true));
                    Boolean bool2 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.cw.punishment", true));
                    String str1 = "check.cw.broadcast-message";
                    String str2 = "check.cw.punishment-commands";
                    int m = this.plugin.getConfig().getInt("check.cw.max-violet", 3);
                    if (k >= m) {
                        PunishmentUtils.handlePunishment(this.plugin, player, "CW", Integer.valueOf(k), Integer.valueOf(m), bool1, bool2, str1, str2, "Crystal Per Seconds");
                    } else {
                        AlertUtils.sendAlert(this.plugin, player, "CW", Integer.valueOf(k), Integer.valueOf(m), "Crystal Per Seconds");
                    }
                }
            }
        }
    }

    public void ClearFlags() {
        this.crystalWarnings.clear();
    }
}

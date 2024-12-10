package me.elilroy.angleguard.check.CW;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.events.AlertManager;
import me.elilroy.angleguard.events.PunishmentManager;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CW implements Listener {
    public HashMap<UUID, Integer> crystalPlacements;
    public HashMap<UUID, Integer> crystalWarnings;
    public HashMap<UUID, Integer> rightCps;
    public HashMap<UUID, Location> lastInteractLocation;
    public HashMap<UUID, Long> lastInteractTime;
    private final AngleGuard plugin;

    public CW(AngleGuard plugin) {
        this.plugin = plugin;
        this.crystalPlacements = new HashMap();
        this.crystalWarnings = new HashMap();
        this.rightCps = new HashMap();
        this.lastInteractLocation = new HashMap();
        this.lastInteractTime = new HashMap();
        this.startPlacementResetTask();
        this.startWarningsResetTask();
    }

    public void ClearFlags(Player player) {
        this.crystalWarnings.put(player.getUniqueId(), 0);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof EnderCrystal) {
            Location spawnLocation = event.getLocation();
            long currentTime = System.currentTimeMillis();
            Player nearestPlayer = null;
            double nearestDistance = Double.MAX_VALUE;

            for(Map.Entry<UUID, Location> entry : this.lastInteractLocation.entrySet()) {
                UUID playerUUID = (UUID)entry.getKey();
                Location interactLocation = (Location)entry.getValue();
                long interactTime = (Long)this.lastInteractTime.getOrDefault(playerUUID, 0L);
                if (interactLocation != null && currentTime - interactTime < 1000L && interactLocation.getWorld().equals(spawnLocation.getWorld())) {
                    double distance = interactLocation.distance(spawnLocation);
                    if (distance < nearestDistance) {
                        nearestDistance = distance;
                        nearestPlayer = Bukkit.getPlayer(playerUUID);
                    }
                }
            }

            if (nearestPlayer != null) {
                UUID playerUUID = nearestPlayer.getUniqueId();
                int placements = (Integer)this.crystalPlacements.getOrDefault(playerUUID, 0) + 1;
                this.crystalPlacements.put(playerUUID, placements);
                int maxCPS = this.plugin.getConfig().getInt("check.cw.max-cps", 6);
                if (placements > maxCPS) {
                    if (TpsUtils.getTPS() <= (double)this.plugin.getConfig().getInt("check.cw.minimum-tps")) {
                        return;
                    }

                    int warnings = (Integer)this.crystalWarnings.get(playerUUID) + 1;
                    this.crystalWarnings.put(playerUUID, warnings);
                    this.crystalPlacements.put(playerUUID, 0);
                    Boolean Broadcast = this.plugin.getConfig().getBoolean("check.cw.broadcast", true);
                    Boolean Punishment = this.plugin.getConfig().getBoolean("check.cw.punishment", true);
                    String BroadcastMSG = "check.cw.broadcast-message";
                    String PunishmentCMD = "check.cw.punishment-commands";
                    int maxViolet = this.plugin.getConfig().getInt("check.cw.max-violet", 3);
                    if (warnings >= maxViolet) {
                        PunishmentManager.handlePunishment(this.plugin, nearestPlayer, "CW", warnings, maxViolet, Broadcast, Punishment, BroadcastMSG, PunishmentCMD, "Crystal Per Seconds");
                    } else {
                        AlertManager.sendAlert(this.plugin, nearestPlayer, "CW", warnings, maxViolet, "Crystal Per Seconds");
                    }
                }
            }
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        boolean enableCheck = this.plugin.getConfig().getBoolean("check.cw.enable", true);
        if (enableCheck) {
            if (TpsUtils.getTPS() <= (double)this.plugin.getConfig().getInt("check.cw.minimum-tps")) {
                return;
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getInventory().getItemInMainHand().getType() == Material.END_CRYSTAL && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.OBSIDIAN) {
                this.lastInteractLocation.put(playerUUID, event.getClickedBlock().getLocation());
                this.lastInteractTime.put(playerUUID, System.currentTimeMillis());
            }
        }

    }

    private void startPlacementResetTask() {
        (new BukkitRunnable() {
            public void run() {
                for(UUID playerUUID : CW.this.crystalPlacements.keySet()) {
                    CW.this.crystalPlacements.put(playerUUID, 0);
                }

                for(UUID playerUUID : CW.this.rightCps.keySet()) {
                    CW.this.rightCps.put(playerUUID, 0);
                }

            }
        }).runTaskTimer(this.plugin, 20L, 20L);
    }

    private void startWarningsResetTask() {
        int TimeConfig = this.plugin.getConfig().getInt("general.reset-violet", 10);
        long Times = (long)(TimeConfig * 60 * 1000);
        (new BukkitRunnable() {
            public void run() {
                for(UUID playerUUID : CW.this.crystalWarnings.keySet()) {
                    CW.this.crystalWarnings.put(playerUUID, 0);
                }

            }
        }).runTaskTimer(this.plugin, Times, Times);
    }
}

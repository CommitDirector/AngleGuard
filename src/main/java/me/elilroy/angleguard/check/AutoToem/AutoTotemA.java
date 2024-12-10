package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.events.AlertManager;
import me.elilroy.angleguard.events.PunishmentManager;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class AutoTotemA {
    private final AngleGuard plugin;
    public HashMap<UUID, Integer> totemsFlagged = new HashMap();
    private final HashMap<UUID, Integer> flagsA = new HashMap();
    public HashMap<UUID, Long> timeClick = new HashMap();
    public HashMap<UUID, Long> UseTotem = new HashMap();

    public AutoTotemA(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public void ClearFlags(Player player) {
        this.flagsA.put(player.getUniqueId(), 0);
    }

    public void punishAndRegisterFlag(HumanEntity player) {
        Boolean Enable = this.plugin.getConfig().getBoolean("check.auto-totem.a.enable", true);
        if (this.plugin != null && Enable) {
            if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock"))) {
                return;
            }

            if (TpsUtils.getTPS() <= (double)this.plugin.getConfig().getInt("check.auto-totem.a.minimum-tps")) {
                return;
            }

            Boolean Broadcast = this.plugin.getConfig().getBoolean("check.auto-totem.a.broadcast", true);
            Boolean Punishment = this.plugin.getConfig().getBoolean("check.auto-totem.a.punishment", true);
            String BroadcastMSG = "check.auto-totem.a.broadcast-message";
            String PunishmentCMD = "check.auto-totem.a.punishment-commands";
            Integer maxFlags = this.plugin.getConfig().getInt("check.auto-totem.a.max-violet", 5);
            Integer Flags = (Integer)this.flagsA.getOrDefault(player.getUniqueId(), 0) + 1;
            this.totemsFlagged.put(player.getUniqueId(), (Integer)this.totemsFlagged.getOrDefault(player.getUniqueId(), 0) + 1);
            this.flagsA.put(player.getUniqueId(), Flags);
            if (Flags >= maxFlags) {
                PunishmentManager.handlePunishment(this.plugin, (Player)player, "AutoTotemA", Flags, maxFlags, Broadcast, Punishment, BroadcastMSG, PunishmentCMD, "Fast Totem Switch");
            } else {
                AlertManager.sendAlert(this.plugin, (Player)player, "AutoTotemA", Flags, maxFlags, "Fast Totem Switch");
            }
        }

    }
}

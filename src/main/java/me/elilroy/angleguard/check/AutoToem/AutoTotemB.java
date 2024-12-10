package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.events.AlertManager;
import me.elilroy.angleguard.events.PunishmentManager;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class AutoTotemB {
    private final AngleGuard plugin;
    public HashMap<UUID, Integer> totemsFlagged = new HashMap();
    private final HashMap<UUID, Integer> flagsB = new HashMap();

    public AutoTotemB(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public void ClearFlags(Player player) {
        this.flagsB.put(player.getUniqueId(), 0);
    }

    public void punishAndRegisterFlag(HumanEntity player) {
        Boolean Enable = this.plugin.getConfig().getBoolean("check.auto-totem.b.enable", true);
        if (this.plugin != null && Enable) {
            if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock"))) {
                return;
            }

            if (TpsUtils.getTPS() <= (double)this.plugin.getConfig().getInt("check.auto-totem.b.minimum-tps")) {
                return;
            }

            Boolean Broadcast = this.plugin.getConfig().getBoolean("check.auto-totem.b.broadcast", true);
            Boolean Punishment = this.plugin.getConfig().getBoolean("check.auto-totem.b.punishment", true);
            String BroadcastMSG = "check.auto-totem.b.broadcast-message";
            String PunishmentCMD = "check.auto-totem.b.punishment-commands";
            Integer maxFlags = this.plugin.getConfig().getInt("check.auto-totem.b.max-violet", 5);
            Integer Flags = (Integer)this.flagsB.getOrDefault(player.getUniqueId(), 0) + 1;
            Integer Flagged = (Integer)this.totemsFlagged.getOrDefault(player.getUniqueId(), 0) + 1;
            this.totemsFlagged.put(player.getUniqueId(), Flagged);
            this.flagsB.put(player.getUniqueId(), Flags);
            if (Flags >= maxFlags) {
                PunishmentManager.handlePunishment(this.plugin, (Player)player, "AutoTotemB", Flags, maxFlags, Broadcast, Punishment, BroadcastMSG, PunishmentCMD, "Totem Swap Exploit");
            } else {
                AlertManager.sendAlert(this.plugin, (Player)player, "AutoTotemB", Flags, maxFlags, "Totem Swap Exploit");
            }
        }

    }
}

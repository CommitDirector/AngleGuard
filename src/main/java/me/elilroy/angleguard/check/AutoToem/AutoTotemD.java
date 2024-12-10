package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.events.AlertManager;
import me.elilroy.angleguard.events.PunishmentManager;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class AutoTotemD {
    private final AngleGuard plugin;
    public HashMap<UUID, Integer> totemsFlagged = new HashMap();
    private final HashMap<UUID, Integer> flagsD = new HashMap();

    public AutoTotemD(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public void ClearFlags(Player player) {
        this.flagsD.put(player.getUniqueId(), 0);
    }

    public void punishAndRegisterFlag(HumanEntity player) {
        Boolean Enable = this.plugin.getConfig().getBoolean("check.auto-totem.d.enable", true);
        if (this.plugin != null && Enable) {
            if (player.getName().contains(this.plugin.getConfig().getString("general.bedrock"))) {
                return;
            }

            if (TpsUtils.getTPS() <= (double)this.plugin.getConfig().getInt("check.auto-totem.d.minimum-tps")) {
                return;
            }

            Boolean Broadcast = this.plugin.getConfig().getBoolean("check.auto-totem.d.broadcast", true);
            Boolean Punishment = this.plugin.getConfig().getBoolean("check.auto-totem.d.punishment", true);
            String BroadcastMSG = "check.auto-totem.d.broadcast-message";
            String PunishmentCMD = "check.auto-totem.d.punishment-commands";
            Integer maxFlags = this.plugin.getConfig().getInt("check.auto-totem.d.max-violet", 5);
            Integer Flags = (Integer)this.flagsD.getOrDefault(player.getUniqueId(), 0) + 1;
            this.totemsFlagged.put(player.getUniqueId(), (Integer)this.totemsFlagged.getOrDefault(player.getUniqueId(), 0) + 1);
            this.flagsD.put(player.getUniqueId(), Flags);
            if (Flags >= maxFlags) {
                PunishmentManager.handlePunishment(this.plugin, (Player)player, "AutoTotemD", Flags, maxFlags, Broadcast, Punishment, BroadcastMSG, PunishmentCMD, "Manual Totem Detection");
            } else {
                AlertManager.sendAlert(this.plugin, (Player)player, "AutoTotemD", Flags, maxFlags, "Invalid Totem Bind Detection");
            }
        }

    }
}
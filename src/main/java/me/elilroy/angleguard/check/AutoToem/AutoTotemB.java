package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.AlertUtils;
import me.elilroy.angleguard.utils.PunishmentUtils;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class AutoTotemB {
    private final AngleGuard plugin;
    private final HashMap<UUID, Integer> flagsB = new HashMap<>();
    public final HashMap<UUID, Integer> totemsFlagged = new HashMap<>();

    public AutoTotemB(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
    }

    public void punishAndRegisterFlag(HumanEntity paramHumanEntity) {
        Boolean bool = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.b.enable", true));
        if (this.plugin != null && bool.booleanValue()) {
            if (paramHumanEntity.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
                return;
            if (TpsUtils.getTPS() <= this.plugin.getConfig().getInt("check.auto-totem.b.minimum-tps"))
                return;
            Boolean bool1 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.b.broadcast", true));
            Boolean bool2 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.b.punishment", true));
            String str1 = "check.auto-totem.b.broadcast-message";
            String str2 = "check.auto-totem.b.punishment-commands";
            Integer integer1 = Integer.valueOf(this.plugin.getConfig().getInt("check.auto-totem.b.max-violet", 5));
            Integer integer2 = Integer.valueOf(((Integer)this.flagsB.getOrDefault(paramHumanEntity.getUniqueId(), Integer.valueOf(0))).intValue() + 1);
            Integer integer3 = Integer.valueOf(((Integer)this.totemsFlagged.getOrDefault(paramHumanEntity.getUniqueId(), Integer.valueOf(0))).intValue() + 1);
            this.totemsFlagged.put(paramHumanEntity.getUniqueId(), integer3);
            this.flagsB.put(paramHumanEntity.getUniqueId(), integer2);
            if (integer2.intValue() >= integer1.intValue()) {
                PunishmentUtils.handlePunishment(this.plugin, (Player)paramHumanEntity, "AutoTotemB", integer2, integer1, bool1, bool2, str1, str2, "Totem Swap Exploit");
            } else {
                AlertUtils.sendAlert(this.plugin, (Player)paramHumanEntity, "AutoTotemB", integer2, integer1, "Totem Swap Exploit");
            }
        }
    }

    public void ClearFlags() {
        this.flagsB.clear();
    }
}

package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.AlertUtils;
import me.elilroy.angleguard.utils.PunishmentUtils;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class AutoTotemA {
    private final AngleGuard plugin;
    private final HashMap<UUID, Integer> flagsA = new HashMap<>();
    private final HashMap<UUID, Integer> totemsFlagged = new HashMap<>();

    public void ClearFlags() {
        this.flagsA.clear();
    }

    public void punishAndRegisterFlag(HumanEntity paramHumanEntity) {
        Boolean bool = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.a.enable", true));
        if (this.plugin != null && bool.booleanValue()) {
            if (paramHumanEntity.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
                return;
            if (TpsUtils.getTPS() <= this.plugin.getConfig().getInt("check.auto-totem.a.minimum-tps"))
                return;
            Boolean bool1 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.a.broadcast", true));
            Boolean bool2 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.a.punishment", true));
            String str1 = "check.auto-totem.a.broadcast-message";
            String str2 = "check.auto-totem.a.punishment-commands";
            Integer integer1 = Integer.valueOf(this.plugin.getConfig().getInt("check.auto-totem.a.max-violet", 5));
            Integer integer2 = Integer.valueOf(((Integer)this.flagsA.getOrDefault(paramHumanEntity.getUniqueId(), Integer.valueOf(0))).intValue() + 1);
            this.totemsFlagged.put(paramHumanEntity.getUniqueId(), Integer.valueOf(((Integer)this.totemsFlagged.getOrDefault(paramHumanEntity.getUniqueId(), Integer.valueOf(0))).intValue() + 1));
            this.flagsA.put(paramHumanEntity.getUniqueId(), integer2);
            if (integer2.intValue() >= integer1.intValue()) {
                PunishmentUtils.handlePunishment(this.plugin, (Player)paramHumanEntity, "AutoTotemA", integer2, integer1, bool1, bool2, str1, str2, "Fast Totem Switch");
            } else {
                AlertUtils.sendAlert(this.plugin, (Player)paramHumanEntity, "AutoTotemA", integer2, integer1, "Fast Totem Switch");
            }
        }
    }

    public AutoTotemA(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
    }
}

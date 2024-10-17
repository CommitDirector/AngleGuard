package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import me.elilroy.angleguard.utils.AlertUtils;
import me.elilroy.angleguard.utils.PunishmentUtils;
import me.elilroy.angleguard.utils.TpsUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class AutoTotemC {
    private final AngleGuard plugin;

    private final HashMap<UUID, Integer> flagsC = new HashMap<>();
    private final HashMap<UUID, Integer> totemsFlagged = new HashMap<>();

    public void punishAndRegisterFlag(HumanEntity paramHumanEntity) {
        Boolean bool = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.c.enable", true));
        if (this.plugin != null && bool.booleanValue()) {
            if (paramHumanEntity.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
                return;
            if (TpsUtils.getTPS() <= this.plugin.getConfig().getInt("check.auto-totem.c.minimum-tps"))
                return;
            Boolean bool1 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.c.broadcast", true));
            Boolean bool2 = Boolean.valueOf(this.plugin.getConfig().getBoolean("check.auto-totem.c.punishment", true));
            String str1 = "check.auto-totem.c.broadcast-message";
            String str2 = "check.auto-totem.c.punishment-commands";
            Integer integer1 = Integer.valueOf(this.plugin.getConfig().getInt("check.auto-totem.c.max-violet", 5));
            Integer integer2 = Integer.valueOf(((Integer)this.flagsC.getOrDefault(paramHumanEntity.getUniqueId(), Integer.valueOf(0))).intValue() + 1);
            this.totemsFlagged.put(paramHumanEntity.getUniqueId(), Integer.valueOf(((Integer)this.totemsFlagged.getOrDefault(paramHumanEntity.getUniqueId(), Integer.valueOf(0))).intValue() + 1));
            this.flagsC.put(paramHumanEntity.getUniqueId(), integer2);
            if (integer2.intValue() >= integer1.intValue()) {
                PunishmentUtils.handlePunishment(this.plugin, (Player)paramHumanEntity, "AutoTotemC", integer2, integer1, bool1, bool2, str1, str2, "Quick Item Move");
            } else {
                AlertUtils.sendAlert(this.plugin, (Player)paramHumanEntity, "AutoTotemC", integer2, integer1, "Quick Item Move");
            }
        }
    }

    public void ClearFlags() {
        this.flagsC.clear();
    }

    public AutoTotemC(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
    }
}

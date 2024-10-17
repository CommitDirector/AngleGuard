package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoTotem {
    private final AngleGuard plugin;
    public final HashMap<UUID, Integer> totemsPopped = new HashMap<>();

    public AutoTotem(AngleGuard paramAngleGuard) {
        this.plugin = paramAngleGuard;
        startWarningsResetTask();
    }

    private void startWarningsResetTask() {
        int i = this.plugin.getConfig().getInt("general.reset-violet", 10);
        long l = (i * 60) * 20L;
        (new BukkitRunnable() {
            public void run() {
                AutoTotemA autoTotemA = AutoTotem.this.plugin.getAutoTotemA();
                if (autoTotemA != null)
                    autoTotemA.ClearFlags();
                AutoTotemB autoTotemB = AutoTotem.this.plugin.getAutoTotemB();
                if (autoTotemB != null)
                    autoTotemB.ClearFlags();
                AutoTotemC autoTotemC = AutoTotem.this.plugin.getAutoTotemC();
                if (autoTotemC != null)
                    autoTotemC.ClearFlags();
                AutoTotemD autoTotemD = AutoTotem.this.plugin.getAutoTotemD();
                if (autoTotemD != null)
                    autoTotemD.ClearFlags();
            }
        }).runTaskTimer((Plugin)this.plugin, l, l);
    }

    public void addTotem(Player paramPlayer) {
        if (paramPlayer.getName().contains(this.plugin.getConfig().getString("general.bedrock")))
            return;
        int i = ((Integer)this.totemsPopped.getOrDefault(paramPlayer.getUniqueId(), Integer.valueOf(0))).intValue();
        this.totemsPopped.put(paramPlayer.getUniqueId(), Integer.valueOf(i + 1));
    }
}

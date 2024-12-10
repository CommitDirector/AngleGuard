package me.elilroy.angleguard.check.AutoToem;

import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.AngleGuard;
import org.bukkit.entity.Player;

public class AutoTotem {
    private final AngleGuard plugin;
    public HashMap<UUID, Integer> totemsPopped = new HashMap();

    public AutoTotem(AngleGuard plugin) {
        this.plugin = plugin;
    }

    public void addTotem(Player player) {
        if (!player.getName().contains(this.plugin.getConfig().getString("general.bedrock"))) {
            int totem = this.totemsPopped.getOrDefault(player.getUniqueId(), 0);
            this.totemsPopped.put(player.getUniqueId(), totem + 1);
        }
    }
}

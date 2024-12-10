package me.elilroy.angleguard;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.check.AutoToem.AutoTotem;
import me.elilroy.angleguard.check.AutoToem.AutoTotemA;
import me.elilroy.angleguard.check.AutoToem.AutoTotemB;
import me.elilroy.angleguard.check.AutoToem.AutoTotemC;
import me.elilroy.angleguard.check.AutoToem.AutoTotemD;
import me.elilroy.angleguard.check.AutoToem.AutoTotemE;
import me.elilroy.angleguard.check.CW.CW;
import me.elilroy.angleguard.commands.AlertsCommands;
import me.elilroy.angleguard.commands.AngleGuardCommands;
import me.elilroy.angleguard.commands.TabComplete;
import me.elilroy.angleguard.listeners.EntityResurrectListener;
import me.elilroy.angleguard.listeners.JoinEvent;
import me.elilroy.angleguard.listeners.SwapHandListener;
import me.elilroy.angleguard.listeners.check.AutoTotemACDL;
import me.elilroy.angleguard.listeners.check.AutoTotemAL;
import me.elilroy.angleguard.utils.ClientBrandUtils;
import me.elilroy.angleguard.utils.ColorUtils;
import me.elilroy.angleguard.utils.DiscordUtils;
import me.elilroy.angleguard.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class AngleGuard extends JavaPlugin implements Listener {
    private AutoTotem autoTotem;
    private AutoTotemA autoTotemA;
    private AutoTotemB autoTotemB;
    private AutoTotemC autoTotemC;
    private AutoTotemD autoTotemD;
    private AutoTotemE autoTotemE;
    private CW cw;
    public long ticks;
    private DiscordUtils discordUtils;
    private static HashMap<UUID, Boolean> alertToggles = new HashMap();
    public HashMap<UUID, Long> lastTotem = new HashMap();

    public AngleGuard() {
    }

    public void onEnable() {
        this.loadConfiguration();
        this.countTicks();
        this.discordUtils = new DiscordUtils(this);
        this.startWarningsResetTask();
        TabCompleter tabCompleter = new TabComplete();
        ClientBrandUtils clientBrandUtils = new ClientBrandUtils(this);
        clientBrandUtils.register();
        this.getServer().getConsoleSender().sendMessage("");
        this.getServer().getConsoleSender().sendMessage(ColorUtils.translate("#ACB6E5=#ABB8E5=#AABAE5=#A9BCE5=#A8BEE5=#A7C0E5=#A6C2E5=#A5C3E6=#A4C5E6=#A3C7E6=#A2C9E6=#A1CBE6=#A0CDE6[#9FCFE6 &l#9ED1E6A#9DD3E6n#9CD5E6g#9BD7E6l#9AD9E6e#98DAE7G#97DCE7u#96DEE7a#95E0E7r#94E2E7d#93E4E7&r #92E6E7]#91E8E7=#90EAE7=#8FECE7=#8EEEE7=#8DF0E7=#8CF1E8=#8BF3E8=#8AF5E8=#89F7E8=#88F9E8=#87FBE8=#86FDE8="));
        this.getServer().getConsoleSender().sendMessage("");
        this.getServer().getConsoleSender().sendMessage(ColorUtils.translate("  &7-> #ACB6E5A#A4C4E6u#9DD2E6t#95E1E7h#8EEFE7o#86FDE8r: &r" + this.getDescription().getAuthors()));
        this.getServer().getConsoleSender().sendMessage(ColorUtils.translate("  &7-> #ACB6E5V#A6C2E6e#9FCEE6r#99DAE7s#93E5E7i#8CF1E8o#86FDE8n: &rv" + this.getDescription().getVersion()));
        this.getServer().getConsoleSender().sendMessage(ColorUtils.translate("  &7-> #ACB6E5S#A6C2E6u#9FCEE6p#99DAE7p#93E5E7o#8CF1E8r#86FDE8t: &r1.16x - 1.21x"));
        this.getServer().getConsoleSender().sendMessage(ColorUtils.translate("  &7-> #ACB6E5T#9FCEE6e#93E5E7a#86FDE8m: &rVerleX Development"));
        this.getServer().getConsoleSender().sendMessage("");
        this.getServer().getConsoleSender().sendMessage(ColorUtils.translate("#ACB6E5=#ABB8E5=#AABAE5=#A9BCE5=#A8BEE5=#A7C0E5=#A6C2E5=#A5C3E6=#A4C5E6=#A3C7E6=#A2C9E6=#A1CBE6=#A0CDE6[#9FCFE6 &l#9ED1E6A#9DD3E6n#9CD5E6g#9BD7E6l#9AD9E6e#98DAE7G#97DCE7u#96DEE7a#95E0E7r#94E2E7d#93E4E7&r #92E6E7]#91E8E7=#90EAE7=#8FECE7=#8EEEE7=#8DF0E7=#8CF1E8=#8BF3E8=#8AF5E8=#89F7E8=#88F9E8=#87FBE8=#86FDE8="));
        this.getServer().getConsoleSender().sendMessage("");
        if (this.getConfig().getInt("config-version") != 3) {
            this.getServer().getConsoleSender().sendMessage(ColorUtils.translate("&4[WARNING] &cYour config has been reset to Original because 'config-version' is not '2'"));
            File configFile = new File(this.getDataFolder(), "config.yml");
            configFile.delete();
            this.saveDefaultConfig();
            this.reloadConfig();
        }

        this.autoTotem = new AutoTotem(this);
        this.autoTotemA = new AutoTotemA(this);
        this.autoTotemB = new AutoTotemB(this);
        this.autoTotemC = new AutoTotemC(this);
        this.autoTotemD = new AutoTotemD(this);
        this.autoTotemE = new AutoTotemE(this);
        this.cw = new CW(this);
        this.getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new CW(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityResurrectListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AutoTotemACDL(this), this);
        this.getServer().getPluginManager().registerEvents(new AutoTotemAL(this), this);
        this.getServer().getPluginManager().registerEvents(new SwapHandListener(this), this);
        this.getCommand("angleguard").setTabCompleter(tabCompleter);
        this.getCommand("angleguard").setExecutor(new AngleGuardCommands(this));
        this.getCommand("alerts").setExecutor(new AlertsCommands(this));
    }

    private void countTicks() {
        (new BukkitRunnable() {
            public void run() {
                ++AngleGuard.this.ticks;
                if (AngleGuard.this.ticks > 72000L) {
                    AngleGuard.this.ticks = 0L;
                }

            }
        }).runTaskTimerAsynchronously(this, 0L, 1L);
    }

    public int ticksToMs(int ticks, double msPerTick) {
        return (int)((double)ticks * msPerTick);
    }

    public void onDisable() {
        this.getLogger().info("Server orn klg nas brer plugin pin ng sos error! ??");
        this.getLogger().info("AngleGuard is disabled!");
    }

    public AutoTotem getAutoTotem() {
        return this.autoTotem;
    }

    public AutoTotemA getAutoTotemA() {
        return this.autoTotemA;
    }

    public AutoTotemB getAutoTotemB() {
        return this.autoTotemB;
    }

    public AutoTotemC getAutoTotemC() {
        return this.autoTotemC;
    }

    public AutoTotemD getAutoTotemD() {
        return this.autoTotemD;
    }

    public AutoTotemE getAutoTotemE() {
        return this.autoTotemE;
    }

    public CW getCW() {
        return this.cw;
    }

    public long getTicks() {
        return this.ticks;
    }

    public String getVersion(Player player) {
        return this.getServer().getPluginManager().getPlugin("ProtocolLib") != null ? VersionUtils.getPlayerVersion(player) : "&cError ProtocolLib";
    }

    private void loadConfiguration() {
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        } else {
            this.reloadConfig();
        }

    }

    public boolean isAlertsToggled(Player player) {
        return alertToggles.getOrDefault(player.getUniqueId(), true);
    }

    public void toggleAlerts(Player player) {
        boolean currentState = alertToggles.getOrDefault(player.getUniqueId(), true);
        alertToggles.put(player.getUniqueId(), !currentState);
    }

    private void startWarningsResetTask() {
        int resetTimeInMinutes = this.getConfig().getInt("general.reset-violet", 10);
        long resetTimeTicks = (long)(resetTimeInMinutes * 60) * 20L;
        (new BukkitRunnable() {
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if (AngleGuard.this.autoTotemA != null) {
                        AngleGuard.this.autoTotemA.ClearFlags(p);
                    }

                    if (AngleGuard.this.autoTotemB != null) {
                        AngleGuard.this.autoTotemB.ClearFlags(p);
                    }

                    if (AngleGuard.this.autoTotemC != null) {
                        AngleGuard.this.autoTotemC.ClearFlags(p);
                    }

                    if (AngleGuard.this.autoTotemD != null) {
                        AngleGuard.this.autoTotemD.ClearFlags(p);
                    }

                    if (AngleGuard.this.autoTotemE != null) {
                        AngleGuard.this.autoTotemE.ClearFlags(p);
                    }

                    if (AngleGuard.this.cw != null) {
                        AngleGuard.this.cw.ClearFlags(p);
                    }
                }

            }
        }).runTaskTimer(this, resetTimeTicks, resetTimeTicks);
    }
}

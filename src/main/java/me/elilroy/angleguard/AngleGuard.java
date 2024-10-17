package me.elilroy.angleguard;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import me.elilroy.angleguard.check.AutoToem.AutoTotem;
import me.elilroy.angleguard.check.AutoToem.AutoTotemA;
import me.elilroy.angleguard.check.AutoToem.AutoTotemB;
import me.elilroy.angleguard.check.AutoToem.AutoTotemC;
import me.elilroy.angleguard.check.AutoToem.AutoTotemD;
import me.elilroy.angleguard.check.CW.CW;
import me.elilroy.angleguard.commands.TabComplete;
import me.elilroy.angleguard.listeners.EntityResurrectListener;
import me.elilroy.angleguard.listeners.InventoryClickListener;
import me.elilroy.angleguard.listeners.SwapHandListener;
import me.elilroy.angleguard.utils.ClientBrandUtils;
import me.elilroy.angleguard.utils.ColorUtils;
import me.elilroy.angleguard.utils.DiscordUtils;
import me.elilroy.angleguard.utils.VersionUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class AngleGuard extends JavaPlugin implements Listener {

    public final HashMap<UUID, Long> lastTotem = new HashMap<>();

    private DiscordUtils discordUtils;

    private AutoTotem autoTotem;
    private AutoTotemA autoTotemA;
    private AutoTotemB autoTotemB;
    private AutoTotemC autoTotemC;
    private AutoTotemD autoTotemD;
    private CW cw;

    private final HashMap<UUID, Boolean> alertToggles = new HashMap<>();

    private long ticks = 0L;


    public void onDisable() {
        getLogger().info("Server orn klg nas brer plugin pin ng sos error! ??");
        getLogger().info("AngleGuard is disabled!");
    }

    public AutoTotemB getAutoTotemB() {
        return this.autoTotemB;
    }

    public long getTicks() {
        return this.ticks;
    }

    public AutoTotem getAutoTotem() {
        return this.autoTotem;
    }

    private void countTicks() {
        (new BukkitRunnable() {
            public void run() {
                AngleGuard.this.ticks++;
                if (AngleGuard.this.ticks > 72000L)
                    AngleGuard.this.ticks = 0L;
            }
        }).runTaskTimerAsynchronously((Plugin)this, 0L, 1L);
    }

    public AutoTotemA getAutoTotemA() {
        return this.autoTotemA;
    }

    public AutoTotemC getAutoTotemC() {
        return this.autoTotemC;
    }

    public CW getCW() {
        return this.cw;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent paramPlayerJoinEvent) {
        getCW().ClearFlags();
        getAutoTotemA().ClearFlags();
        getAutoTotemB().ClearFlags();
        getAutoTotemC().ClearFlags();
        getAutoTotemD().ClearFlags();
    }

    public void toggleAlerts(Player paramPlayer) {
        boolean bool = ((Boolean)alertToggles.getOrDefault(paramPlayer.getUniqueId(), Boolean.valueOf(true))).booleanValue();
        alertToggles.put(paramPlayer.getUniqueId(), Boolean.valueOf(!bool));
    }

    public AutoTotemD getAutoTotemD() {
        return this.autoTotemD;
    }

    public void onEnable() {
        loadConfiguration();
        countTicks();
        this.discordUtils = new DiscordUtils(this);
        TabComplete tabComplete = new TabComplete();
        ClientBrandUtils clientBrandUtils = new ClientBrandUtils(this);
        clientBrandUtils.register();
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ColorUtils.translate("#ACB6E5=#ABB8E5=#AABAE5=#A9BCE5=#A8BEE5=#A7C0E5=#A6C2E5=#A5C3E6=#A4C5E6=#A3C7E6=#A2C9E6=#A1CBE6=#A0CDE6[#9FCFE6 &l#9ED1E6A#9DD3E6n#9CD5E6g#9BD7E6l#9AD9E6e#98DAE7G#97DCE7u#96DEE7a#95E0E7r#94E2E7d#93E4E7&r #92E6E7]#91E8E7=#90EAE7=#8FECE7=#8EEEE7=#8DF0E7=#8CF1E8=#8BF3E8=#8AF5E8=#89F7E8=#88F9E8=#87FBE8=#86FDE8="));
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ColorUtils.translate(String.valueOf(new StringBuilder().append("  &7-> #ACB6E5A#A4C4E6u#9DD2E6t#95E1E7h#8EEFE7o#86FDE8r: &r").append(getDescription().getAuthors()))));
        getServer().getConsoleSender().sendMessage(ColorUtils.translate(String.valueOf(new StringBuilder().append("  &7-> #ACB6E5V#A6C2E6e#9FCEE6r#99DAE7s#93E5E7i#8CF1E8o#86FDE8n: &r").append(getDescription().getVersion()))));
        getServer().getConsoleSender().sendMessage(ColorUtils.translate("  &7-> #ACB6E5S#A6C2E6u#9FCEE6p#99DAE7p#93E5E7o#8CF1E8r#86FDE8t: &r1.13x - 1.21x"));
        getServer().getConsoleSender().sendMessage(ColorUtils.translate("  &7-> #ACB6E5T#9FCEE6e#93E5E7a#86FDE8m: &rVerleX Development"));
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ColorUtils.translate("#ACB6E5=#ABB8E5=#AABAE5=#A9BCE5=#A8BEE5=#A7C0E5=#A6C2E5=#A5C3E6=#A4C5E6=#A3C7E6=#A2C9E6=#A1CBE6=#A0CDE6[#9FCFE6 &l#9ED1E6A#9DD3E6n#9CD5E6g#9BD7E6l#9AD9E6e#98DAE7G#97DCE7u#96DEE7a#95E0E7r#94E2E7d#93E4E7&r #92E6E7]#91E8E7=#90EAE7=#8FECE7=#8EEEE7=#8DF0E7=#8CF1E8=#8BF3E8=#8AF5E8=#89F7E8=#88F9E8=#87FBE8=#86FDE8="));
        getServer().getConsoleSender().sendMessage("");
        if (getConfig().getInt("config-version") != 2) {
            getServer().getConsoleSender().sendMessage(ColorUtils.translate("&4[WARNING] &cYour config has been reset to Original because 'config-version' is not '2'"));
            File file = new File(getDataFolder(), "config.yml");
            file.delete();
            saveDefaultConfig();
            reloadConfig();
        }
        this.autoTotem = new AutoTotem(this);
        this.autoTotemA = new AutoTotemA(this);
        this.autoTotemB = new AutoTotemB(this);
        this.autoTotemC = new AutoTotemC(this);
        this.autoTotemD = new AutoTotemD(this);
        this.cw = new CW(this);
        getServer().getPluginManager().registerEvents(this, (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new CW(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new EntityResurrectListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new InventoryClickListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new SwapHandListener(this), (Plugin)this);
        getCommand("angleguard").setTabCompleter((TabCompleter)tabComplete);
    }

    public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
        if (paramCommand.getName().equalsIgnoreCase("angleguard")) {
            if (paramArrayOfString.length <= 0) {
                paramCommandSender.sendMessage(ColorUtils.translate("#ACB6E5=#ABB9E5=#A9BBE5=#A8BEE5=#A6C1E5=#A5C3E6=#A4C6E6=#A2C8E6=#A1CBE6=#9FCEE6=#9ED0E6[#9DD3E6 #9BD6E6&lH#9AD8E6&lE#98DBE7&lL#97DDE7&lP#95E0E7 #94E3E7]#93E5E7=#91E8E7=#90EBE7=#8EEDE7=#8DF0E7=#8CF2E8=#8AF5E8=#89F8E8=#87FAE8=#86FDE8="));
                paramCommandSender.sendMessage("");
                paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A8BEE5a#A4C6E6g#9FCEE6 #9BD6E6r#97DDE7e#93E5E7l#8EEDE7o#8AF5E8a#86FDE8d &7# to reload the configuration."));
                paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A9BBE5a#A7BFE5g#A4C4E6 #A2C9E6d#9FCEE6i#9DD2E6s#9AD7E6a#98DCE7b#95E1E7l#93E5E7e#90EAE7c#8EEFE7h#8BF4E8e#89F8E8c#86FDE8k &7# to disable plugin check."));
                paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A9BBE5a#A7C0E5g#A4C5E6 #A1CAE6e#9ECFE6n#9CD4E6a#99DAE7b#96DFE7l#94E4E7e#91E9E7c#8EEEE7h#8BF3E8e#89F8E8c#86FDE8k &7# to enable plugin check."));
                paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A7C0E5a#A1CAE6g#9CD4E6 #96DFE7h#91E9E7e#8BF3E8l#86FDE8p &7# to get help with commands."));
                paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #86FDE8/#8AF5E8a#8EEDE7g#93E5E7 #97DDE7a#9BD6E6l#9FCEE6e#A4C6E6r#A8BEE5t#ACB6E5s &7# to toggle alerts."));
                paramCommandSender.sendMessage("");
                paramCommandSender.sendMessage(ColorUtils.translate("#ACB6E5=#ABB9E5=#A9BBE5=#A8BEE5=#A6C1E5=#A5C3E6=#A4C6E6=#A2C8E6=#A1CBE6=#9FCEE6=#9ED0E6[#9DD3E6 #9BD6E6&lH#9AD8E6&lE#98DBE7&lL#97DDE7&lP#95E0E7 #94E3E7]#93E5E7=#91E8E7=#90EBE7=#8EEDE7=#8DF0E7=#8CF2E8=#8AF5E8=#89F8E8=#87FAE8=#86FDE8="));
                paramCommandSender.sendMessage("");
                return true;
            }
            if (paramArrayOfString.length > 0) {
                if (paramArrayOfString[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.reload", "&8[&eAG&8] &7AngleGuard config has been reloaded!"))));
                    return true;
                }
                if (paramArrayOfString[0].equalsIgnoreCase("disablecheck")) {
                    if (paramArrayOfString.length > 1) {
                        String str = paramArrayOfString[1].toLowerCase();
                        if (str.startsWith("autototem")) {
                            String str1 = str.substring(9);
                            if (getConfig().isConfigurationSection(String.valueOf((new StringBuilder()).append("check.auto-totem.").append(str1)))) {
                                getConfig().set(String.valueOf((new StringBuilder()).append("check.auto-totem.").append(str1).append(".enable")), Boolean.valueOf(false));
                                saveConfig();
                                paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.disable-check", "&8[&eAG&8] &7Check &e%check% &7has been disabled!").replace("%check%", str))));
                                return true;
                            }
                            paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.check-not-found", "&8[&eAG&8] &7Check &c%check% &7is not found!").replace("%check%", str))));
                            return true;
                        }
                        if (getConfig().isConfigurationSection(String.valueOf((new StringBuilder()).append("check.").append(str)))) {
                            getConfig().set(String.valueOf((new StringBuilder()).append("check.").append(str).append(".enable")), Boolean.valueOf(false));
                            saveConfig();
                            paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.disable-check", "&8[&eAG&8] &7Check &e%check% &7has been disabled!").replace("%check%", str))));
                            return true;
                        }
                        paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.check-not-found", "&8[&eAG&8] &7Check &c%check% &7is not found!").replace("%check%", str))));
                        return true;
                    }
                    paramCommandSender.sendMessage(String.valueOf((new StringBuilder()).append(ChatColor.RED).append("Usage: /angleguard disablecheck (check)")));
                    return true;
                }
                if (paramArrayOfString[0].equalsIgnoreCase("enablecheck")) {
                    if (paramArrayOfString.length > 1) {
                        String str = paramArrayOfString[1].toLowerCase();
                        if (str.startsWith("autototem")) {
                            String str1 = str.substring(9);
                            if (getConfig().isConfigurationSection(String.valueOf((new StringBuilder()).append("check.auto-totem.").append(str1)))) {
                                getConfig().set(String.valueOf((new StringBuilder()).append("check.auto-totem.").append(str1).append(".enable")), Boolean.valueOf(true));
                                saveConfig();
                                paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.enable-check", "&8[&eAG&8] &7Check &e%check% &7has been enabled!").replace("%check%", str))));
                                return true;
                            }
                            paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.check-not-found", "&8[&eAG&8] &7Check &c%check% &7is not found!").replace("%check%", str))));
                            return true;
                        }
                        if (getConfig().isConfigurationSection(String.valueOf((new StringBuilder()).append("check.").append(str)))) {
                            getConfig().set(String.valueOf((new StringBuilder()).append("check.").append(str).append(".enable")), Boolean.valueOf(true));
                            saveConfig();
                            paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.enable-check", "&8[&eAG&8] &7Check &e%check% &7has been enabled!").replace("%check%", str))));
                            return true;
                        }
                        paramCommandSender.sendMessage(ColorUtils.translate(String.valueOf(getConfig().getString("messages.check-not-found", "&8[&eAG&8] &7Check &c%check% &7is not found!").replace("%check%", str))));
                        return true;
                    }
                    paramCommandSender.sendMessage(String.valueOf((new StringBuilder()).append(ChatColor.RED).append("Usage: /angleguard enablecheck (check)")));
                    return true;
                }
                if (paramArrayOfString[0].equalsIgnoreCase("help")) {
                    paramCommandSender.sendMessage(ColorUtils.translate("#ACB6E5=#ABB9E5=#A9BBE5=#A8BEE5=#A6C1E5=#A5C3E6=#A4C6E6=#A2C8E6=#A1CBE6=#9FCEE6=#9ED0E6[#9DD3E6 #9BD6E6&lH#9AD8E6&lE#98DBE7&lL#97DDE7&lP#95E0E7 #94E3E7]#93E5E7=#91E8E7=#90EBE7=#8EEDE7=#8DF0E7=#8CF2E8=#8AF5E8=#89F8E8=#87FAE8=#86FDE8="));
                    paramCommandSender.sendMessage("");
                    paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A8BEE5a#A4C6E6g#9FCEE6 #9BD6E6r#97DDE7e#93E5E7l#8EEDE7o#8AF5E8a#86FDE8d &7# to reload the configuration."));
                    paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A9BBE5a#A7BFE5g#A4C4E6 #A2C9E6d#9FCEE6i#9DD2E6s#9AD7E6a#98DCE7b#95E1E7l#93E5E7e#90EAE7c#8EEFE7h#8BF4E8e#89F8E8c#86FDE8k &7# to disable plugin check."));
                    paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A9BBE5a#A7C0E5g#A4C5E6 #A1CAE6e#9ECFE6n#9CD4E6a#99DAE7b#96DFE7l#94E4E7e#91E9E7c#8EEEE7h#8BF3E8e#89F8E8c#86FDE8k &7# to enable plugin check."));
                    paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #ACB6E5/#A7C0E5a#A1CAE6g#9CD4E6 #96DFE7h#91E9E7e#8BF3E8l#86FDE8p &7# to get help with commands."));
                    paramCommandSender.sendMessage(ColorUtils.translate("  &7-> #86FDE8/#8AF5E8a#8EEDE7g#93E5E7 #97DDE7a#9BD6E6l#9FCEE6e#A4C6E6r#A8BEE5t#ACB6E5s &7# to toggle alerts."));
                    paramCommandSender.sendMessage("");
                    paramCommandSender.sendMessage(ColorUtils.translate("#ACB6E5=#ABB9E5=#A9BBE5=#A8BEE5=#A6C1E5=#A5C3E6=#A4C6E6=#A2C8E6=#A1CBE6=#9FCEE6=#9ED0E6[#9DD3E6 #9BD6E6&lH#9AD8E6&lE#98DBE7&lL#97DDE7&lP#95E0E7 #94E3E7]#93E5E7=#91E8E7=#90EBE7=#8EEDE7=#8DF0E7=#8CF2E8=#8AF5E8=#89F8E8=#87FAE8=#86FDE8="));
                    paramCommandSender.sendMessage("");
                    return true;
                }
            }
            if (!(paramCommandSender instanceof Player)) {
                paramCommandSender.sendMessage(ColorUtils.translate("&cOnly players can use this commands!"));
                return true;
            }
            Player player = (Player)paramCommandSender;
            if (paramArrayOfString[0].equalsIgnoreCase("alerts")) {
                if (!player.hasPermission("angleguard.alerts")) {
                    player.sendMessage("You don't have permission to use this commands!");
                    return true;
                }
                toggleAlerts(player);
                boolean bool = isAlertsToggled(player);
                if (bool) {
                    player.sendMessage(ColorUtils.translate(getConfig().getString("messages.toggle-alerts", "&8[&eAG&8] &7AngleGuard Alerts %result%")).replace("%result%", "Enable"));
                    return true;
                }
                player.sendMessage(ColorUtils.translate(getConfig().getString("messages.toggle-alerts", "&8[&eAG&8] &7AngleGuard Alerts %result%")).replace("%result%", "Disable"));
                return true;
            }
        }
        return false;
    }

    private void loadConfiguration() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        } else {
            reloadConfig();
        }
    }

    public boolean isAlertsToggled(Player paramPlayer) {
        return ((Boolean)alertToggles.getOrDefault(paramPlayer.getUniqueId(), Boolean.valueOf(true))).booleanValue();
    }

    public String getVersion(Player paramPlayer) {
        return (getServer().getPluginManager().getPlugin("ProtocolLib") != null) ? VersionUtils.getPlayerVersion(paramPlayer) : "&cError ProtocolLib";
    }
}

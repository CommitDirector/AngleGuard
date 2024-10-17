package me.elilroy.angleguard.utils;

import me.elilroy.angleguard.AngleGuard;

public class ServerNameUtils {

    private static AngleGuard plugin;

    public ServerNameUtils(AngleGuard paramAngleGuard) {
        plugin = paramAngleGuard;
    }

    public static String Name() {
        return String.valueOf(plugin.getConfig().getString("general.server-name"));
    }
}

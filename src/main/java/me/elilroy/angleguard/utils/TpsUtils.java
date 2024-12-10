package me.elilroy.angleguard.utils;

import org.bukkit.Bukkit;

public class TpsUtils {
    public TpsUtils() {
    }

    private static double[] getServerTPS() {
        try {
            Object minecraftServer = Bukkit.getServer().getClass().getMethod("getServer").invoke(Bukkit.getServer());
            return (double[])minecraftServer.getClass().getField("recentTps").get(minecraftServer);
        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{(double)20.0F, (double)20.0F, (double)20.0F};
        }
    }

    public static double getTPS() {
        return getServerTPS()[0];
    }
}

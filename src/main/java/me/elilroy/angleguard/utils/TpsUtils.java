package me.elilroy.angleguard.utils;

import org.bukkit.Bukkit;

public class TpsUtils {
    static {

    }

    public static double getTPS() {
        return getServerTPS()[0];
    }

    private static double[] getServerTPS() {
        try {
            Object object = Bukkit.getServer().getClass().getMethod("getServer", new Class[0]).invoke(Bukkit.getServer(), new Object[0]);
            return (double[])object.getClass().getField("recentTps").get(object);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new double[] { 20.0D, 20.0D, 20.0D };
        }
    }
}

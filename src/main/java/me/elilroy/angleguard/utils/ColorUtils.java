package me.elilroy.angleguard.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class ColorUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
    private static final Pattern AMPERSAND_HEX_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");

    public ColorUtils() {
    }

    public static String translate(String message) {
        if (isModernVersion()) {
            message = translateHexColors(message, HEX_PATTERN);
            message = translateHexColors(message, AMPERSAND_HEX_PATTERN);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String translateHexColors(String message, Pattern pattern) {
        Matcher matcher = pattern.matcher(message);

        while(matcher.find()) {
            String colorCode = matcher.group();
            String parsedColor = colorCode.startsWith("&") ? colorCode.substring(1) : colorCode;

            try {
                message = message.replace(colorCode, "" + ChatColor.of(parsedColor));
            } catch (IllegalArgumentException var6) {
                Bukkit.getLogger().warning("Invalid hex color: " + parsedColor);
            }
        }

        return message;
    }

    private static boolean isModernVersion() {
        String version = Bukkit.getVersion();
        return version.contains("1.16") || version.contains("1.17") || version.contains("1.18") || version.contains("1.19") || version.contains("1.20") || version.contains("1.21");
    }
}

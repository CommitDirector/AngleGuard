package me.elilroy.angleguard.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class ColorUtils {
    private final static Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
    private final static Pattern AMPERSAND_HEX_PATTERN;

    private static boolean isModernVersion() {
        String str = Bukkit.getVersion();
        return (str.contains("1.16") || str.contains("1.17") || str.contains("1.18") || str.contains("1.19") || str.contains("1.20") || str.contains("1.21"));
    }

    static {
        AMPERSAND_HEX_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");
    }

    private static String translateHexColors(String paramString, Pattern paramPattern) {
        Matcher matcher = paramPattern.matcher(paramString);
        while (matcher.find()) {
            String str1 = matcher.group();
            String str2 = str1.startsWith("&") ? str1.substring(1) : str1;
            try {
                paramString = paramString.replace(str1, String.valueOf((new StringBuilder()).append(ChatColor.of(str2)).append("")));
            } catch (IllegalArgumentException illegalArgumentException) {
                Bukkit.getLogger().warning(String.valueOf((new StringBuilder()).append("Invalid hex color: ").append(str2)));
            }
        }
        return paramString;
    }

    public static String translate(String paramString) {
        if (isModernVersion()) {
            paramString = translateHexColors(paramString, HEX_PATTERN);
            paramString = translateHexColors(paramString, AMPERSAND_HEX_PATTERN);
        }
        return ChatColor.translateAlternateColorCodes('&', paramString);
    }
}

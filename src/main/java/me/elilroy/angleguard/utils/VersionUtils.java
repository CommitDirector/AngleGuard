package me.elilroy.angleguard.utils;

import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.entity.Player;

public class VersionUtils {
    public VersionUtils() {
    }

    private static String getMinecraftVersion(int protocolVersion) {
        switch (protocolVersion) {
            case 5:
                return "1.7.2";
            case 47:
                return "1.8.x";
            case 107:
                return "1.9";
            case 108:
                return "1.9.1";
            case 109:
                return "1.9.2";
            case 110:
                return "1.9.4";
            case 210:
                return "1.10.x";
            case 315:
            case 316:
                return "1.11.x";
            case 335:
                return "1.12";
            case 340:
                return "1.12.2";
            case 393:
            case 401:
            case 404:
                return "1.13.x";
            case 477:
            case 485:
            case 490:
                return "1.14.x";
            case 498:
                return "1.14.4";
            case 573:
            case 575:
            case 578:
                return "1.15.x";
            case 735:
                return "1.16";
            case 736:
                return "1.16.1";
            case 751:
                return "1.16.2";
            case 753:
                return "1.16.3";
            case 754:
                return "1.16.4";
            case 755:
                return "1.17";
            case 756:
                return "1.17.1";
            case 757:
                return "1.18";
            case 758:
                return "1.18.2";
            case 759:
                return "1.19";
            case 760:
                return "1.19.1";
            case 761:
                return "1.19.3";
            case 762:
                return "1.19.4";
            case 763:
                return "1.20";
            case 764:
                return "1.20.2";
            case 765:
                return "1.20.4";
            case 766:
                return "1.20.6";
            case 767:
                return "1.21";
            default:
                return "UNKNOWN";
        }
    }

    public static String getPlayerVersion(Player player) {
        int protocolVersion = ProtocolLibrary.getProtocolManager().getProtocolVersion(player);
        return getMinecraftVersion(protocolVersion);
    }
}

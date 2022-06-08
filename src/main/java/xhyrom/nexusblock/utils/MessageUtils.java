package xhyrom.nexusblock.utils;

import org.bukkit.ChatColor;
import xhyrom.nexusblock.NexusBlock;

public class MessageUtils {
    public static String message(String text) {
        return message(text, true);
    }

    public static String message(String text, boolean prefix) {
        return NexusBlock.getInstance().config.getString("prefix") + translateColorCodes(text);
    }

    private static String translateColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}

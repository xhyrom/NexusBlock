package hyro.lib.utils;

import cn.nukkit.Player;
import hyro.lib.Main;

public class Message {
    public static void sendConsole(String message) {
        String prefix = Main.fileConfig.getString("prefix").replaceAll("&","§");
        Main.instance.getServer().getConsoleSender().sendMessage(prefix + message.replaceAll("&","§"));
    }

    public static void sendMessage(Player p, String message) {
        String prefix = Main.fileConfig.getString("prefix").replaceAll("&","§");
        p.sendMessage(prefix + message.replaceAll("&","§"));
    }

    public static void sendMessageNoPrefix(Player p, String message) {
        p.sendMessage(message.replaceAll("&","§"));
    }
}

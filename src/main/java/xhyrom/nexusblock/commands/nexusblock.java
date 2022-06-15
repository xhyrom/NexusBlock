package xhyrom.nexusblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xhyrom.nexusblock.NexusBlock;

import static xhyrom.nexusblock.utils.MessageUtils.message;

public class nexusblock implements CommandExecutor {
    private String infoMessage = message("Plugin by &cxHyroM#2851 &7available on github &c&nhttps://github.com/xHyroM/NexusBlock");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusblock.admin")) {
            sender.sendMessage(infoMessage);
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(message(infoMessage + "\n&cAdmin Commands:\n\n&c/nexusblock reload &8- &7Reload plugin", false));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(message("Please restart server."));
            // NexusBlock.getInstance().reloadConfig();
            // NexusBlock.getInstance().onReload();
            return true;
        }

        return true;
    }
}

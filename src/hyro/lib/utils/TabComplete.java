package hyro.lib.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("nexusblock") || cmd.getName().equalsIgnoreCase("blocknexus"))
        {
            List<String> argList = new ArrayList<>();

            if(args.length == 1) argList.add("info");

            if(sender.hasPermission("nexusblock.admin")) {
                if(args.length == 1) {
                    argList.add("reload");
                    argList.add("create");
                    argList.add("remove");
                }

                if(args[0].equalsIgnoreCase("create")) {
                    argList.add("<material>");
                    argList.add("<x>");
                    argList.add("<y>");
                    argList.add("<z>");

                    if(args.length >= 3) argList.remove("<material>");
                    if(args.length >= 4) argList.remove("<x>");
                    if(args.length >= 5) argList.remove("<y>");
                    if(args.length >= 6) argList.remove("<z>");
                }

                if(args[0].equalsIgnoreCase("remove")) {
                    argList.add("<name material>");
                    if(args.length >= 3) argList.remove("<name material>");
                }
            }

            return argList;
        }

        return null;
    }
}

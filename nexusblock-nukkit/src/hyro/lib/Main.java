package hyro.lib;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import hyro.lib.commands.nexusblock;
import hyro.lib.listeners.BlockDestroy;
import hyro.lib.structures.Nexus;
import hyro.lib.utils.Holograms.*;
import hyro.lib.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hyro.lib.utils.Utils.getLocation;

public class Main extends PluginBase {
    public static File file;
    public static Plugin instance;
    public static Config fileConfig;
    public static GlobalInterface HologramManager;
    public static HashMap<String, Nexus> nexuses = new HashMap<String, Nexus>();

    @Override
    public void onEnable() {
        instance = this;

        // TODO: HOLOGRAMS
        /*String dependency = Utils.getDependency();
        if(dependency == null) {
            getLogger().warning("§cRequire Holograms by Creeperface!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }*/

        //if(dependency.equalsIgnoreCase("cfhd")) HologramManager = new CFHolograms();

        saveDefaultConfig();
        fileConfig = getConfig();

        getLogger().info("§cNexusBlock by Hyro has been §aenabled!");

        loadBlocks();
        loadCommands();
        getServer().getPluginManager().registerEvents(new BlockDestroy(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("§cNexusBlock by Hyro has been disabled!");

        nexuses.forEach((material, nexus) -> {
            Main.HologramManager.deleteHologram(nexus.hologram);
            try {
                fileConfig = getConfig();
                fileConfig.set("nexuses."+material+".health", nexus.health);
                fileConfig.save();
                fileConfig = getConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Loaders
     */
    private void loadCommands() {
        getServer().getCommandMap().register("nexusblock", new nexusblock("nexusblock"));
    }

    private static void loadBlocks() {
        ConfigSection section = fileConfig.getSection("nexuses");
        section.getKeys(false).forEach(block -> {
            String path = "nexuses."+block;

            Integer rawBlockId = fileConfig.getInt(path+".blockId");
            BlockID blockId = Block.get(rawBlockId);
            if(blockId == null) {
                Main.instance.getLogger().warning("[NexusBlock] Bad block id in " + block);
                return;
            }

            String name = fileConfig.getString(path+".name").replaceAll("&","§");
            String info = fileConfig.getString(path+".info").replaceAll("&","§");
            String respawn = fileConfig.getString(path+".respawn");

            String maxHealth = fileConfig.getString(path+".maxHealth");
            String healthBar = fileConfig.getString(path+".healthBar").replaceAll("&","§");
            Integer health = fileConfig.getInt(path+".health");

            Location location = getLocation(path+".location");
            List<String> rewards = fileConfig.getStringList(path+".rewards");

            List<String> rewardsEveryBreak = new ArrayList<>();
            if(fileConfig.get(path+".rewardsEveryBreak") != null) rewardsEveryBreak = fileConfig.getStringList(path+".rewardsEveryBreak");

            if(rawBlockId == Block.BEDROCK) location.getLevel().setBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ(), rawBlockId);

            Main.instance.getLogger().info("[NexusBlock] Loaded " + block + " nexus!");
            nexuses.put(block, new Nexus(
                    rawBlockId, name, info, maxHealth, healthBar, health, respawn, location, rewards, rewardsEveryBreak
            ));
        });
    }

    public static void reloadPlugin() {
        nexuses.forEach((material, nexus) -> {
            nexuses.remove(material);
            Main.HologramManager.deleteHologram(nexus.hologram);
            try {
                fileConfig = Main.instance.getConfig();
                fileConfig.set("nexuses."+material+".health", nexus.health);
                fileConfig.save();
                fileConfig = Main.instance.getConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loadBlocks();
    }
}

package xhyrom.nexusblock;

import com.google.gson.Gson;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xhyrom.nexusblock.commands.nexusblock;
import xhyrom.nexusblock.events.BlockDestroy;
import xhyrom.nexusblock.structures.Nexus;
import xhyrom.nexusblock.structures.database.JSONDatabase;
import xhyrom.nexusblock.structures.holograms.HologramInterface;
import xhyrom.nexusblock.utils.Loader;
import xhyrom.nexusblock.utils.Metrics;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class NexusBlock extends JavaPlugin {
    private static NexusBlock Instance;
    public final Gson gson = new Gson();
    public List<Nexus> nexuses = new ArrayList<>();
    public HologramInterface hologram;
    public FileConfiguration config = getConfig();
    public JSONDatabase jsonDatabase;

    @Override
    public void onEnable() {
        Instance = this;

        this.saveDefaultConfig();
        config.options().copyDefaults(true);

        if (config.getBoolean("send-metrics"))
            new Metrics(this, 13145);

        hologram = Loader.loadHologram();
        nexuses = Loader.loadBlocks();
        getCommand("nexusblock").setExecutor(new nexusblock());
        getServer().getPluginManager().registerEvents(new BlockDestroy(), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, this::saveData, 1L, (long) 300 * 20);
    }

    @Override
    public void onDisable() {
        this.saveData();
    }

    @Override
    public void saveDefaultConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        File databaseFile = new File(getDataFolder(), "database.json");

        if (!configFile.exists()) {
            super.saveResource("config.yml", false);
        }

        if (!databaseFile.exists()) {
            super.saveResource("database.json", false);
        }

        try {
            this.jsonDatabase = gson.fromJson(new FileReader(databaseFile), JSONDatabase.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onReload() {
        for (Nexus nexus : this.nexuses) {
            this.nexuses.remove(nexus);
            this.hologram.deleteHologram(nexus.hologramInterface);
        }

        nexuses = Loader.loadBlocks();
    }

    private void saveData() {
        JSONDatabase tempJsonDatabase = new JSONDatabase();

        for (Nexus nexus : this.nexuses) {
            tempJsonDatabase.addNexus(nexus.id, nexus.getDestroyers(), nexus.getDestroys(), nexus.healths.damaged);
        }

        tempJsonDatabase.toString(getDataFolder() + "/database.json");
        this.jsonDatabase = tempJsonDatabase;
    }

    public static NexusBlock getInstance() {
        return Instance;
    }
}

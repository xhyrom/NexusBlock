package xhyrom.nexusblock;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xhyrom.nexusblock.events.BlockDestroy;
import xhyrom.nexusblock.structures.Nexus;
import xhyrom.nexusblock.structures.holograms.HologramInterface;
import xhyrom.nexusblock.utils.Loader;
import xhyrom.nexusblock.utils.Metrics;

import java.util.ArrayList;
import java.util.List;

public final class NexusBlock extends JavaPlugin {
    private static NexusBlock Instance;
    public List<Nexus> nexuses = new ArrayList<>();
    public HologramInterface hologram;
    public FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        Instance = this;

        this.saveDefaultConfig();
        if (config.getBoolean("send-metrics"))
            new Metrics(this, 13145);

        hologram = Loader.loadHologram();
        nexuses = Loader.loadBlocks();
        getServer().getPluginManager().registerEvents(new BlockDestroy(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void onReload() {
        for (Nexus nexus : this.nexuses) {
            this.nexuses.remove(nexus);
            this.hologram.deleteHologram(nexus.hologramInterface);
        }

        nexuses = Loader.loadBlocks();
    }

    public static NexusBlock getInstance() {
        return Instance;
    }
}

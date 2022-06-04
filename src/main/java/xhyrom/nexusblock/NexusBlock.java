package xhyrom.nexusblock;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xhyrom.nexusblock.events.BlockDestroy;
import xhyrom.nexusblock.structures.Nexus;
import xhyrom.nexusblock.structures.holograms.HologramInterface;
import xhyrom.nexusblock.utils.Loader;

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

        hologram = Loader.loadHologram();
        nexuses = Loader.loadBlocks();
        getServer().getPluginManager().registerEvents(new BlockDestroy(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static NexusBlock getInstance() {
        return Instance;
    }
}

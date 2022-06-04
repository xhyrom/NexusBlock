package xhyrom.nexusblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import xhyrom.nexusblock.NexusBlock;
import xhyrom.nexusblock.structures.Nexus;
import xhyrom.nexusblock.structures.nexusConfig.NexusConfig;
import xhyrom.nexusblock.structures.holograms.DecentHolograms;
import xhyrom.nexusblock.structures.holograms.HologramInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Loader {
    public static List<Nexus> loadBlocks() {
        List<Nexus> nexuses = new ArrayList<>();

        for (final Object nexusObject : NexusBlock.getInstance().config.getList("nexuses")) {
            HashMap<String, Object> nexusHashmap = read((HashMap) nexusObject);
            NexusConfig nexusConfig = new NexusConfig(nexusHashmap);

            Material material = Material.getMaterial(nexusConfig.material);
            if (material == null) {
                NexusBlock.getInstance().getLogger().warning("Invalid material in nexus " + nexusConfig.id);
                continue;
            }

            nexuses.add(new Nexus(nexusConfig.id, material, nexusConfig.hologram, nexusConfig.location, nexusConfig.respawn, nexusConfig.healths, nexusConfig.hologramLocation, nexusConfig.rewards));
        }

        return nexuses;
    }

    public static HologramInterface loadHologram() {
        if (checkDependency("DecentHolograms")) return new DecentHolograms();
        else return null;
    }

    private static boolean checkDependency(String name) {
        return Bukkit.getPluginManager().getPlugin(name) != null;
    }

    private static HashMap<String, Object> read(final HashMap nexusObject) {
        HashMap<String, Object> map = new HashMap<>();

        for (Object key : nexusObject.keySet()) {
            Object value = nexusObject.get(key.toString());
            if (value instanceof HashMap) {
                map.put(key.toString(), value);

                continue;
            }

            map.put(key.toString(), value.toString());
        }

        return map;
    }
}

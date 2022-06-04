package xhyrom.nexusblock.structures;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xhyrom.nexusblock.NexusBlock;
import xhyrom.nexusblock.structures.holograms.HologramInterface;
import xhyrom.nexusblock.structures.nexusConfig.NexusConfigHealths;
import xhyrom.nexusblock.structures.nexusConfig.NexusConfigHologram;
import xhyrom.nexusblock.structures.nexusConfig.NexusConfigLocation;
import xhyrom.nexusblock.structures.nexusConfig.NexusConfigRewards;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Nexus {
    public int id;
    private Material material;
    private World world;
    public Location location;
    private NexusConfigHologram hologram;
    private Object hologramInterface;
    private Long respawn;
    private NexusConfigHealths healths;
    private NexusConfigRewards rewards;
    private CopyOnWriteArrayList<String> destroyers = new CopyOnWriteArrayList<>();
    private static HashMap<String, Integer> destroys = new HashMap<>();

    public Nexus(int id, Material material, NexusConfigHologram hologram, NexusConfigLocation location, long respawn, NexusConfigHealths healths, double hologramLocation, NexusConfigRewards rewards) {
        this.id = id;
        this.material = material;
        this.world = Bukkit.getWorld(location.world);
        this.location = new Location(world, location.x, location.y, location.z, 0, 0);
        this.hologram = hologram;
        this.hologramInterface = NexusBlock.getInstance().hologram.createHologram(this.location, this.id, hologramLocation);
        this.respawn = respawn;
        this.healths = healths;
        this.rewards = rewards;

        if (this.location.getBlock().getType() == Material.BEDROCK) this.location.getBlock().setType(this.material);

        updateHologram(true);
    }

    public void onHit(Player player) {
        this.healths.health++;

        destroys.merge(player.getName(), 1, Integer::sum);

        if (!destroyers.contains(player.getName())) {
            destroyers.add(player.getName());
        }

        Collections.sort(destroyers, new ModuleComparator());
        if (this.destroyers.size() > this.hologram.positionsHologramPositions.size())
            this.destroyers.remove(this.hologram.positionsHologramPositions.size());

        updateHologram(false);
        if (this.healths.health >= this.healths.maximumHealth)
            onDestroy(player);
    }

    public static class ModuleComparator implements Comparator<String> {
        @Override
        public int compare(String arg0, String arg1) {
            int destroys1 = Nexus.destroys.get(arg0);
            int destroys2 = Nexus.destroys.get(arg1);
            if (destroys1 < destroys2 ) {
                return 1;
            }
            if (destroys1  > destroys2 ) {
                return -1;
            }
            return 0;
        }
    }

    private void updateHologram(boolean setup) {
        HologramInterface hologramInterfaceNexusBlock = NexusBlock.getInstance().hologram;

        if (setup) {
            int i = 0;
            for (String line : this.hologram.main) {
                if (line.contains("{health}") || line.contains("{maximumHealth}")) {
                    this.hologram.healthVariablesPositions.put(i, line);
                }

                line = line
                        .replaceAll("\\{health}", String.valueOf(this.healths.health))
                        .replaceAll("\\{maximumHealth}", String.valueOf(this.healths.maximumHealth));

                if (line.contains("\n")) {
                    for (String l : line.split("\n")) {
                        hologramInterfaceNexusBlock.insertTextLine(
                                this.hologramInterface,
                                i,
                                l
                                        .replaceAll("\\{playerName}", "-")
                                        .replaceAll("\\{count}", "0")
                        );
                        this.hologram.positionsHologramPositions.put(i, l);
                        i++;
                    }

                    continue;
                }

                if (line.equals("{BLOCK:MATERIAL}")) NexusBlock.getInstance().hologram.insertItemLine(this.hologramInterface, i, new ItemStack(this.material));
                else NexusBlock.getInstance().hologram.insertTextLine(this.hologramInterface, i, line);

                i++;
            }

            return;
        }

        updateHologramHealthPositions();
        updateHologramPositions(false);
    }

    private void updateHologramHealthPositions() {
        HologramInterface hologramInterfaceNexusBlock = NexusBlock.getInstance().hologram;

        this.hologram.healthVariablesPositions.forEach((i, line) -> {
            hologramInterfaceNexusBlock.editTextLine(
                    this.hologramInterface,
                    i,
                    line
                            .replaceAll("\\{health}", String.valueOf(this.healths.health))
                            .replaceAll("\\{maximumHealth}", String.valueOf(this.healths.maximumHealth)),
                    false
            );
        });
    }

    private void updateHologramPositions(boolean reset) {
        HologramInterface hologramInterfaceNexusBlock = NexusBlock.getInstance().hologram;

        int i = 0;
        for (Map.Entry<Integer, String> line : this.hologram.positionsHologramPositions.entrySet()) {
            if (reset) {
                hologramInterfaceNexusBlock.editTextLine(
                        this.hologramInterface,
                        line.getKey(),
                        line.getValue()
                                .replaceAll("\\{playerName}", "-")
                                .replaceAll("\\{count}", "0"),
                        false
                );

                continue;
            }

            if (this.destroyers.size() <= i) break;

            String playerName = this.destroyers.get(i);
            if (playerName == null) continue;

            hologramInterfaceNexusBlock.editTextLine(
                    this.hologramInterface,
                    line.getKey(),
                    line.getValue()
                            .replaceAll("\\{playerName}", playerName)
                            .replaceAll("\\{count}", String.valueOf(Nexus.destroys.get(playerName))),
                    false
            );

            i++;
        }
    }

    private void onDestroy(Player player) {
        this.world.strikeLightningEffect(location);

        Block block = this.location.getBlock();
        block.setType(Material.BEDROCK);

        for (int i = 0; i <= this.hologram.positionsHologramPositions.size(); i++) {
            if (this.destroyers.size() <= i) break;

            String playerName = this.destroyers.get(i);
            if (playerName == null) continue;

            giveRewards(i, playerName);
        }

        giveRewards(-2, player.getName());

        Bukkit.getScheduler().runTaskLater(NexusBlock.getInstance(), new Runnable() {
            @Override
            public void run() {
                Block block = location.getBlock();
                block.setType(material);

                healths.health = 0;
                updateHologramHealthPositions();

                destroyers.clear();
                destroys.clear();
                updateHologramPositions(true);
            }
        }, this.respawn * 20L);
    }

    private void giveRewards(int i, String playerName) {
        if (!this.rewards.rewards.containsKey(i)) return;

        ArrayList<String> rewards = this.rewards.rewards.get(i);
        rewards.forEach(reward -> {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    reward
                            .replaceAll("\\{playerName}", playerName)
                            .replaceAll("\\{destroys}", String.valueOf(Nexus.destroys.get(playerName)))
            );
        });
    }
}

package hyro.lib.structures;

import hyro.lib.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Nexus {
    public Material type;
    public String name;
    public String info;
    public String maxHealth;
    public String healthBar;
    public Integer health;
    public String respawn;
    public Location location;
    public List<String> rewards;
    public Object hologram;

    public Nexus(Material type, String name, String info, String maxHealth, String healthBar, Integer health, String respawn, Location location, List<String> rewards) {
        this.type = type;
        this.name = name;
        this.info = info;
        this.maxHealth = maxHealth;
        this.healthBar = healthBar;
        this.respawn = respawn;
        this.location = location;
        this.rewards = rewards;
        this.hologram = Main.HologramManager.createHologram(this.location.getWorld().getBlockAt(location).getLocation().add(0.5, 3.0, 0.5));
        this.health = health;

        updateHologram(true);
    }

    public void onHit(Player player) {
        health++;
        updateHologram(false);

        if(health >= Integer.parseInt(maxHealth)) onDestroy(player);
    }

    private void updateHologram(Boolean first) {
        if(first) {
            Main.HologramManager.insertItemLine(hologram, 0, new ItemStack(type));
            Main.HologramManager.insertTextLine(hologram, 1, name);
            Main.HologramManager.insertTextLine(hologram, 2, healthBar.replace("{health}", health.toString()).replace("{maxHealth}", maxHealth));
            Main.HologramManager.insertTextLine(hologram, 3, info);
        } else {
            Main.HologramManager.editTextLine(hologram, 2, healthBar.replace("{health}", health.toString()).replace("{maxHealth}", maxHealth), false);
        }
    }

    private void onDestroy(Player player) {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf('.') + 1);

        if(version.contains("v1_17")) {
            location.getWorld().createExplosion(location, 1F, false, false);
        } else {
            Bukkit.getLogger().warning("[NexusBlock] Can't create explosion.");
        }

        location.getWorld().strikeLightningEffect(location);

        Block block = location.getBlock();
        block.setType(Material.BEDROCK);
        health = 0;

        rewards.forEach((reward) -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), reward.replace("{player}", player.getName()));
        });

        Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable() {
            @Override
            public void run() {
                Block block = location.getBlock();
                block.setType(type);
                updateHologram(false);
            }
        }, (Long.parseLong(respawn) * 20L));
    }
}

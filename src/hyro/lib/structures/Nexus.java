package hyro.lib.structures;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
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
    public Hologram hologram;

    public Nexus(Material type, String name, String info, String maxHealth, String healthBar, Integer health, String respawn, Location location, List<String> rewards) {
        this.type = type;
        this.name = name;
        this.info = info;
        this.maxHealth = maxHealth;
        this.healthBar = healthBar;
        this.respawn = respawn;
        this.location = location;
        this.rewards = rewards;
        this.hologram = HologramsAPI.createHologram(Main.instance, this.location.getWorld().getBlockAt(location).getLocation().add(0.5, 3.0, 0.5));
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
            hologram.insertItemLine(0, new ItemStack(type));
            hologram.insertTextLine(1, name);
            hologram.insertTextLine(2, healthBar.replace("{health}", health.toString()).replace("{maxHealth}", maxHealth));
            hologram.insertTextLine(3, info);
        } else {
            hologram.removeLine(2);
            hologram.insertTextLine(2, healthBar.replace("{health}", health.toString()).replace("{maxHealth}", maxHealth));
        }
    }

    private void onDestroy(Player player) {
        location.getWorld().createExplosion(location, 1F, false, false);
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

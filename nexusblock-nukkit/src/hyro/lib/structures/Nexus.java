package hyro.lib.structures;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Location;
import hyro.lib.Main;
import hyro.lib.utils.Utils;

import java.util.List;

public class Nexus {
    public Integer type;
    public String name;
    public String info;
    public String maxHealth;
    public String healthBar;
    public Integer health;
    public String respawn;
    public Location location;
    public List<String> rewards;
    public String[] rewardsEveryBreak;
    public Object hologram;

    public Nexus(Integer type, String name, String info, String maxHealth, String healthBar, Integer health, String respawn, Location location, List<String> rewards, List<String> rewardsEveryBreak) {
        this.type = type;
        this.name = name;
        this.info = info;
        this.maxHealth = maxHealth;
        this.healthBar = healthBar;
        this.respawn = respawn;
        this.location = location;
        this.rewards = rewards;
        this.rewardsEveryBreak = Utils.percentageItems(rewardsEveryBreak);
        this.hologram = Main.HologramManager.createHologram(this.location.getLevel().getBlock(location).getLocation());
        this.health = health;

        updateHologram(true);
    }

    public void onHit(Player player) {
        health++;
        updateHologram(false);

        if(health >= Integer.parseInt(maxHealth)) onDestroy(player);
        else if(rewardsEveryBreak.length != 0) {
            String command = Utils.randomItem(rewardsEveryBreak);
            if(!command.equalsIgnoreCase("none")) {
                Main.instance.getServer().dispatchCommand(Main.instance.getServer().getConsoleSender(), command.replace("{player}", player.getName()));
            }
        }
    }

    private void updateHologram(Boolean first) {
        if(first) {
            //Main.HologramManager.insertItemLine(hologram, 0, new ItemStack(type));
            Main.HologramManager.insertTextLine(hologram, 0, name);
            Main.HologramManager.insertTextLine(hologram, 1, healthBar.replace("{health}", health.toString()).replace("{maxHealth}", maxHealth));
            Main.HologramManager.insertTextLine(hologram, 2, info);
        } else {
            //Main.HologramManager.editTextLine(hologram, 1, healthBar.replace("{health}", health.toString()).replace("{maxHealth}", maxHealth), false);
        }
    }

    private void onDestroy(Player player) {
        location.getLevel().setBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ(), 7);
        health = 0;

        rewards.forEach((reward) -> {
            Main.instance.getServer().dispatchCommand(Main.instance.getServer().getConsoleSender(), reward.replace("{player}", player.getName()));
        });

        Main.instance.getServer().getScheduler().scheduleDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                location.getLevel().setBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ(), type);
                updateHologram(false);
            }
        }, (int) (Long.parseLong(respawn) * 20L));
    }
}

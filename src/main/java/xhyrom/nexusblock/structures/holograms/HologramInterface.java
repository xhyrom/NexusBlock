package xhyrom.nexusblock.structures.holograms;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface HologramInterface {
    Object createHologram(Location location, int id, double hologramLocation);
    void insertTextLine(Object hologram, Integer line, String desc);
    void insertItemLine(Object hologram, Integer line, ItemStack item);
    void editTextLine(Object hologram, Integer line, String desc, Boolean save);
    void editItemLine(Object hologram, Integer line, ItemStack item, Boolean save);
    void deleteHologram(Object hologram);
}

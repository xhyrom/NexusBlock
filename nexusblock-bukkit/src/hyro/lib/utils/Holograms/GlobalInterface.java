package hyro.lib.utils.Holograms;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface GlobalInterface {
    Object createHologram(Location location);
    void insertTextLine(Object hologram, Integer line, String desc);
    void insertItemLine(Object hologram, Integer line, ItemStack item);
    void editTextLine(Object hologram, Integer line, String desc, Boolean save);
    void editItemLine(Object hologram, Integer line, ItemStack item, Boolean save);
    void deleteHologram(Object hologram);
}

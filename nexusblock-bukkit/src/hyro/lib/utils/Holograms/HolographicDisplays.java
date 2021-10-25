package hyro.lib.utils.Holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import hyro.lib.Main;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class HolographicDisplays implements GlobalInterface {
    public Object createHologram(Location location) {
        Hologram hd = HologramsAPI.createHologram(Main.instance, location);
        return hd;
    }

    public void insertTextLine(Object hd, Integer line, String desc) {
        ((Hologram) hd).insertTextLine(line, desc);
        return;
    }

    public void insertItemLine(Object hd, Integer line, ItemStack item) {
        ((Hologram) hd).insertItemLine(line, item);
        return;
    }

    public void editTextLine(Object hd, Integer line, String desc, Boolean save) {
        ((Hologram) hd).removeLine(line);
        insertTextLine(hd, line, desc);
        return;
    }

    public void editItemLine(Object hd, Integer line, ItemStack item, Boolean save) {
        ((Hologram) hd).removeLine(line);
        insertItemLine(hd, line, item);
        return;
    }

    public void deleteHologram(Object hd) {
        ((Hologram) hd).delete();
        return;
    }
}

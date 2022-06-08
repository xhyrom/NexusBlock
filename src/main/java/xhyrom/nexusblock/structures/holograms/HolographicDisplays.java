package xhyrom.nexusblock.structures.holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import xhyrom.nexusblock.NexusBlock;

public class HolographicDisplays implements HologramInterface {
    public Object createHologram(Location location, int id, double hologramLocation) {
        Location hdLocation = location.clone().add(0.5, 0, 0.5);
        hdLocation.setY(hologramLocation);

        Hologram hd = HologramsAPI.createHologram(NexusBlock.getInstance(), hdLocation);
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

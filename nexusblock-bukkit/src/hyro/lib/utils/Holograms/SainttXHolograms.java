package hyro.lib.utils.Holograms;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.api.line.TextLine;
import hyro.lib.utils.Utils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SainttXHolograms implements GlobalInterface {
    private static HologramManager hologramManager;

    public SainttXHolograms() {
        hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
    }

    public Object createHologram(Location location) {
        location = location.add(0.5, 3.0, 0.5);

        Hologram hd = new Hologram(Utils.getRandomString(5), location);
        hd.spawn();
        hologramManager.addActiveHologram(hd);

        return hd;
    }

    public void insertTextLine(Object hd, Integer line, String desc) {
        HologramLine hdLine = new TextLine((Hologram) hd, desc);
        ((Hologram) hd).addLine(hdLine, line);
        hologramManager.saveHologram((Hologram) hd);

        return;
    }

    public void insertItemLine(Object hd, Integer line, ItemStack item) {
        HologramLine hdLine = new ItemLine((Hologram) hd, item);
        ((Hologram) hd).addLine(hdLine, line);
        hologramManager.saveHologram((Hologram) hd);

        return;
    }

    public void editTextLine(Object hd, Integer line, String desc, Boolean save) {
        HologramLine hdLine = ((Hologram) hd).getLine(line);
        ((Hologram) hd).removeLine(hdLine);

        insertTextLine(hd, line, desc);
        return;
    }

    public void editItemLine(Object hd, Integer line, ItemStack item, Boolean save) {
        HologramLine hdLine = ((Hologram) hd).getLine(line);
        ((Hologram) hd).removeLine(hdLine);

        insertItemLine(hd, line, item);
        return;
    }

    public void deleteHologram(Object hd) {
        hologramManager.removeActiveHologram((Hologram) hd);
        hologramManager.deleteHologram((Hologram) hd);
        return;
    }
}

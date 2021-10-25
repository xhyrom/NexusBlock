package hyro.lib.utils.Holograms;

import com.Zrips.CMI.Containers.CMILocation;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import hyro.lib.utils.Utils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CMIHolograms implements GlobalInterface {
    public Object createHologram(Location location) {
        CMILocation cmiloc = new CMILocation(location);
        CMIHologram hologram = new CMIHologram(Utils.getRandomString(5), cmiloc);
        return hologram;
    }

    public void insertTextLine(Object hd, Integer line, String desc) {
        ((CMIHologram) hd).setLine(line, desc);
        return;
    }

    public void insertItemLine(Object hd, Integer line, ItemStack item) {
        ((CMIHologram) hd).setLine(line, "ICON:"+item.getType().name());
        return;
    }

    public void editTextLine(Object hd, Integer line, String desc, Boolean save) {
        ((CMIHologram) hd).setLine(line, desc);
        return;
    }

    public void editItemLine(Object hd, Integer line, ItemStack item, Boolean save) {
        ((CMIHologram) hd).setLine(line, "ICON:"+item.getType().name());
        return;
    }

    public void deleteHologram(Object hd) {
        ((CMIHologram) hd).remove();
        return;
    }
}

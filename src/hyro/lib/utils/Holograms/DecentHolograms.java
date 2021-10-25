package hyro.lib.utils.Holograms;

import eu.decentsoftware.holograms.api.DecentHologramsProvider;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;;
import eu.decentsoftware.holograms.core.holograms.DefaultHologram;
import eu.decentsoftware.holograms.core.holograms.DefaultHologramLine;
import hyro.lib.utils.Utils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.Charset;
import java.util.Random;

public class DecentHolograms implements GlobalInterface {
    private static eu.decentsoftware.holograms.api.DecentHolograms dh;

    public DecentHolograms() {
        dh = DecentHologramsProvider.getDecentHolograms();
    }

    public Object createHologram(Location location) {
        Hologram hd = new DefaultHologram(Utils.getRandomString(5), location);
        hd.show();
        hd.save();

        dh.getHologramManager().registerHologram(hd);
        return hd;
    }

    public void insertTextLine(Object hd, Integer line, String desc) {
        HologramLine hdline = new DefaultHologramLine(((Hologram) hd).getNextLineLocation(), desc);
        ((Hologram) hd).addLine(hdline);
        ((Hologram) hd).save();
        return;
    }

    public void insertItemLine(Object hd, Integer line, ItemStack item) {
        HologramLine hdline = new DefaultHologramLine(((Hologram) hd).getNextLineLocation(), "#ICON: "+item.getType().name());
        ((Hologram) hd).addLine(hdline);
        ((Hologram) hd).save();
        return;
    }

    public void editTextLine(Object hd, Integer line, String desc, Boolean save) {
        HologramLine hdline = ((Hologram) hd).getLine(line);
        hdline.setContent(desc);
        if(save) ((Hologram) hd).save();
        return;
    }

    public void editItemLine(Object hd, Integer line, ItemStack item, Boolean save) {
        HologramLine hdline = ((Hologram) hd).getLine(line);
        hdline.setContent("#ICON: "+item.getType().name());
        if(save) ((Hologram) hd).save();
        return;
    }

    public void deleteHologram(Object hd) {
        String name = ((Hologram) hd).getName();
        ((Hologram) hd).delete();

        dh.getHologramManager().removeHologram(name);
        return;
    }
}

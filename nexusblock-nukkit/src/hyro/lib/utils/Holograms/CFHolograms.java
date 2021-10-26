package hyro.lib.utils.Holograms;

import cn.nukkit.level.Location;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import gt.creeperface.holograms.Hologram;
import gt.creeperface.holograms.HologramTranslation;
import gt.creeperface.holograms.api.HologramAPI;
import gt.creeperface.holograms.entity.HologramEntity;
import hyro.lib.Main;
import hyro.lib.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CFHolograms implements GlobalInterface {
    public static HologramAPI manager;

    public CFHolograms() {
        manager = HologramAPI.getInstance();
    }

    public Object createHologram(Location location) {
        location = location.add(0.5, 3.0, 0.5);

        String hologramId = Utils.getRandomString(5);
        Hologram hd = new Hologram(hologramId, new ArrayList<>(), new Hologram.GridSettings());

        return hd;
    }

    public void insertTextLine(Object hd, Integer line, String desc) {
        /*List<String> hdLines = new ArrayList<>();
        hdLines.add(desc);

        HologramTranslation hdTranslations = new HologramTranslation(hdLines);
        ((Hologram) hd).getTranslations().add(hdTranslations);*/
        return;
    }

    public void editTextLine(Object hd, Integer line, String desc, Boolean save) {
        /*Object[] hdTranslations = ((Hologram) hd).getTranslations().toArray();
        ((Hologram) hd).getTranslations().remove(hdTranslations[line]);
        insertTextLine(hd, line, desc);*/
        return;
    }

    public void deleteHologram(Object hd) {
        //((Hologram) hd).
        return;
    }
}

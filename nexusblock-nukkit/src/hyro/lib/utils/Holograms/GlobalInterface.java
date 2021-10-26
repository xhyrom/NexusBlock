package hyro.lib.utils.Holograms;

import cn.nukkit.level.Location;

public interface GlobalInterface {
    Object createHologram(Location location);
    void insertTextLine(Object hologram, Integer line, String desc);
    void editTextLine(Object hologram, Integer line, String desc, Boolean save);
    void deleteHologram(Object hologram);
}

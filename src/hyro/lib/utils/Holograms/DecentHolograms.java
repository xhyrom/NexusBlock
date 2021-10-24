package hyro.lib.utils.Holograms;

import eu.decentsoftware.holograms.api.DecentHologramsProvider;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;;
import eu.decentsoftware.holograms.core.holograms.DefaultHologram;
import eu.decentsoftware.holograms.core.holograms.DefaultHologramLine;
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
        Hologram hd = new DefaultHologram(getRandomString(5), location);
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

    private String getRandomString(int i)
    {

        // bind the length
        byte[] bytearray;
        bytearray = new byte[256];
        String mystring;
        StringBuffer thebuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray);

        mystring
                = new String(bytearray, Charset.forName("UTF-8"));

        thebuffer = new StringBuffer();

        theAlphaNumericS
                = mystring
                .replaceAll("[^A-Z0-9]", "");

        //random selection
        for (int m = 0; m < theAlphaNumericS.length(); m++) {

            if (Character.isLetter(theAlphaNumericS.charAt(m))
                    && (i > 0)
                    || Character.isDigit(theAlphaNumericS.charAt(m))
                    && (i > 0)) {

                thebuffer.append(theAlphaNumericS.charAt(m));
                i--;
            }
        }

        // the resulting string
        return thebuffer.toString();
    }
}

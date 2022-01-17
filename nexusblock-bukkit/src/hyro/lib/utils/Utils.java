package hyro.lib.utils;

import hyro.lib.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {
    public static Location getLocation(String path) {
        World world = Bukkit.getServer().getWorld(Main.fileConfig.getString(path+".world"));

        Double x = Main.fileConfig.getDouble(path+".x");
        Double y = Main.fileConfig.getDouble(path+".y");
        Double z = Main.fileConfig.getDouble(path+".z");
        Float yaw = Float.parseFloat(Main.fileConfig.getString(path+".yaw"));
        Float pitch = Float.parseFloat(Main.fileConfig.getString(path+".pitch"));

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String getRandomString(int i)
    {
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

        for (int m = 0; m < theAlphaNumericS.length(); m++) {

            if (Character.isLetter(theAlphaNumericS.charAt(m))
                    && (i > 0)
                    || Character.isDigit(theAlphaNumericS.charAt(m))
                    && (i > 0)) {

                thebuffer.append(theAlphaNumericS.charAt(m));
                i--;
            }
        }

        return thebuffer.toString();
    }

    public static String getDependency() {
        if(Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
            return "hd";
        } else if(Bukkit.getServer().getPluginManager().getPlugin("DecentHolograms") != null) {
            return "dh";
        } else if(Bukkit.getServer().getPluginManager().getPlugin("Holograms") != null) {
            return "saintxhd";
        } else if(Bukkit.getServer().getPluginManager().getPlugin("CMI") != null) {
            return "cmihd";
        }

        return null;
    }

    public static String[] percentageItems(List<String> list) {
        List<String> items = new ArrayList<>();

        for(String item : list) {
            for(int i = 0; i < Integer.parseInt(item.split(";")[1].replaceAll("%","")); i++) {
                items.add(item);
            }
        }

        Collections.shuffle(items);
        return items.stream().toArray(String[]::new);
    }

    public static String randomItem(String[] items) {
        Random random = new Random();
        String randomItem = items[random.nextInt(items.length)].split(";")[0];

        return randomItem;
    }
}

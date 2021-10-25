package hyro.lib.utils;

import hyro.lib.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.nio.charset.Charset;
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
}

package hyro.lib.utils;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import hyro.lib.Main;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {
    public static Location getLocation(String path) {
        Level world = Main.instance.getServer().getLevelByName(Main.fileConfig.getString(path+".world"));

        Integer x = Main.fileConfig.getInt(path+".x");
        Integer y = Main.fileConfig.getInt(path+".y");
        Integer z = Main.fileConfig.getInt(path+".z");
        Float yaw = Float.parseFloat(Main.fileConfig.getString(path+".yaw"));
        Float pitch = Float.parseFloat(Main.fileConfig.getString(path+".pitch"));

        return new Location(x, y, z, yaw, pitch, world);
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
        if(Main.instance.getServer().getPluginManager().getPlugin("Holograms") != null) {
            return "cfhd";
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

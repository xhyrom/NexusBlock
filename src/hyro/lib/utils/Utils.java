package hyro.lib.utils;

import hyro.lib.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

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
}

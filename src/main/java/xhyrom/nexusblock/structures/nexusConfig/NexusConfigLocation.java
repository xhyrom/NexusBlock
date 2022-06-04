package xhyrom.nexusblock.structures.nexusConfig;

import java.util.HashMap;

public class NexusConfigLocation {
    public Double x;
    public Double y;
    public Double z;
    public String world;

    public NexusConfigLocation(HashMap<String, Object> other) {
        if (!(other.get("location") instanceof HashMap)) return;

        this.x = Double.valueOf(
                ((HashMap<?, ?>) other.get("location")).get("x").toString()
        );
        this.y = Double.valueOf(
                ((HashMap<?, ?>) other.get("location")).get("y").toString()
        );
        this.z = Double.valueOf(
                ((HashMap<?, ?>) other.get("location")).get("z").toString()
        );
        this.world = ((HashMap<?, ?>) other.get("location")).get("world").toString();
    }
}

package xhyrom.nexusblock.structures.nexusConfig;

import java.util.HashMap;
import java.util.List;

public class NexusConfig {
    public int id;
    public long respawn;
    public String material;
    public Double hologramLocation;
    public NexusConfigHologram hologram;
    public NexusConfigLocation location;
    public NexusConfigHealths healths;
    public NexusConfigRewards rewards;

    public NexusConfig(HashMap<String, Object> other) {
        this.id = Integer.parseInt(other.get("id").toString());
        this.respawn = Long.parseLong(other.get("respawn").toString());
        this.material = other.get("material").toString();
        this.hologramLocation = Double.parseDouble(other.get("hologramLocation").toString());
        this.hologram = new NexusConfigHologram(other);
        this.location = new NexusConfigLocation(other);
        this.healths = new NexusConfigHealths(other);
        this.rewards = new NexusConfigRewards(other);
    }
}


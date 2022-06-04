package xhyrom.nexusblock.structures.nexusConfig;

import java.util.HashMap;

public class NexusConfigHealths {
    public int health;
    public int maximumHealth;

    public NexusConfigHealths(HashMap<String, Object> other) {
        if (!(other.get("healths") instanceof HashMap)) return;

        this.health = Integer.parseInt(((HashMap<?, ?>) other.get("healths")).get("health").toString());
        this.maximumHealth = Integer.parseInt(((HashMap<?, ?>) other.get("healths")).get("maximumHealth").toString());
    }
}

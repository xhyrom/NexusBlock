package xhyrom.nexusblock.structures.nexusConfig;

import java.util.HashMap;

public class NexusConfigHealths {
    public int damaged;
    public int maximumHealth;

    public NexusConfigHealths(HashMap<String, Object> other) {
        Object healths = other.get("healths");
        if (healths instanceof HashMap) {
            healths = Integer.parseInt(((HashMap<?, ?>) other.get("healths")).get("maximumHealth").toString());
        }

        this.damaged = 0;
        this.maximumHealth = Integer.parseInt(healths.toString());
    }
}

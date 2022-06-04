package xhyrom.nexusblock.structures.nexusConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NexusConfigRewards {
    public HashMap<Integer, ArrayList<String>> rewards = new HashMap<>();

    public NexusConfigRewards(HashMap<String, Object> other) {
        if (!(other.get("rewards") instanceof HashMap)) return;

        for (Map.Entry<?, ?> entry : ((HashMap<?, ?>) other.get("rewards")).entrySet()) {
            this.rewards.put(Integer.parseInt(entry.getKey().toString()) - 1, (ArrayList<String>) entry.getValue());
        }
    }
}

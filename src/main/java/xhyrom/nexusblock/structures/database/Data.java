package xhyrom.nexusblock.structures.database;

import xhyrom.nexusblock.NexusBlock;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data {
    public CopyOnWriteArrayList<String> destroyers = new CopyOnWriteArrayList<>();
    public HashMap<String, Integer> destroys = new HashMap<>();
    public int damaged = 0;

    public Data(CopyOnWriteArrayList<String> destroyers, HashMap<String, Integer> destroys, int damaged) {
        this.destroyers = destroyers;
        this.destroys = destroys;
        this.damaged = damaged;
    }

    @Override
    public String toString() {
        return NexusBlock.getInstance().gson.toJson(this);
    }
}

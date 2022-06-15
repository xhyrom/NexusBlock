package xhyrom.nexusblock.structures.database;

import xhyrom.nexusblock.NexusBlock;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class JSONDatabase {
    public HashMap<Integer, Data> data = new HashMap<>();

    public void addNexus(Integer nexusId, CopyOnWriteArrayList<String> destroyers, HashMap<String, Integer> destroys, int damaged) {
        data.put(nexusId, new Data(
                destroyers, destroys, damaged
        ));
    }

    public void toString(String filePath) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(NexusBlock.getInstance().gson.toJson(this));
            myWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}


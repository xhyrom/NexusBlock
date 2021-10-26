package hyro.lib.listeners;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.level.Location;
import hyro.lib.Main;
import hyro.lib.structures.Nexus;

public class BlockDestroy implements Listener {
    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        Block eventBlock = event.getBlock();
        Location eventLocation = eventBlock.getLocation();
        Nexus nexus = Main.nexuses.get(eventBlock.getName().toUpperCase().replaceAll(" ","_"));

        if(nexus != null) {
            if(eventLocation.getX() == nexus.location.getX() && eventLocation.getY() == nexus.location.getY() && eventLocation.getZ() == nexus.location.getZ() && eventLocation.getLevel().getName() == nexus.location.getLevel().getName()) {
                event.setCancelled(true);
                nexus.onHit(event.getPlayer());
            }
        }
    }
}

package hyro.lib.listeners;

import hyro.lib.Main;
import hyro.lib.structures.Nexus;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockDestroy implements Listener {
    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        Block eventBlock = event.getBlock();
        Location eventLocation = eventBlock.getLocation();
        Nexus nexus = Main.nexuses.get(eventBlock.getType().name());

        if(nexus != null) {
            if(eventLocation.getBlockX() == nexus.location.getBlockX() && eventLocation.getBlockY() == nexus.location.getBlockY() && eventLocation.getBlockZ() == nexus.location.getBlockZ() && eventLocation.getWorld().getName() == nexus.location.getWorld().getName() && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
                nexus.onHit(event.getPlayer());
            }
        }
    }
}

package xhyrom.nexusblock.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import xhyrom.nexusblock.NexusBlock;
import xhyrom.nexusblock.structures.Nexus;

public class BlockDestroy implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDestroy(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();
        Location blockLocation = block.getLocation();
        Nexus nexusBlock = NexusBlock.getInstance().nexuses.stream()
                .filter(nexus -> nexus.location.equals(blockLocation))
                .findAny()
                .orElse(null);

        if (nexusBlock != null) {
            event.setCancelled(true);

            if (block.getType() != Material.BEDROCK)
                nexusBlock.onHit(player);
        }
    }
}

package xhyrom.nexusblock.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import xhyrom.nexusblock.structures.Nexus;

public class PlayerDestroyNexus extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Nexus nexus;
    private boolean isCancelled;

    public PlayerDestroyNexus(Player player, Nexus nexus) {
        super(player);
        this.nexus = nexus;
    }

    public Nexus getNexus() {
        return this.nexus;
    }

    @Override
    public String toString() {
        return "PlayerDestroyNexus{"
                + "player=" + player
                + ", nexus='" + nexus.toString() + '\''
                + '}';
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}

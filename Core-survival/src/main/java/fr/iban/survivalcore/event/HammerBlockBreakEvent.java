package fr.iban.survivalcore.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HammerBlockBreakEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Block block;
    private final int expToDrop;

    public HammerBlockBreakEvent(Player player, Block block, int expToDrop) {
        this.player = player;
        this.block = block;
        this.expToDrop = expToDrop;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Block getBlock() {
        return block;
    }

    public Player getPlayer() {
        return player;
    }

    public int getExpToDrop() {
        return expToDrop;
    }
}

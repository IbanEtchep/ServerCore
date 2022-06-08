package fr.iban.survivalcore.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UseReplantHoeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Block block;

    public UseReplantHoeEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
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
}

package fr.iban.survivalcore.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemRepairEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final ItemStack item;
    private boolean cancel;

    public ItemRepairEvent(ItemStack item) {
        this.item = item;
        cancel = false;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}

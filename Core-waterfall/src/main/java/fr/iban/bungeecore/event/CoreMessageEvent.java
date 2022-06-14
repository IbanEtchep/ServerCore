package fr.iban.bungeecore.event;

import fr.iban.common.messaging.Message;
import net.md_5.bungee.api.plugin.Event;

public class CoreMessageEvent extends Event {

    private final Message message;

    public CoreMessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}

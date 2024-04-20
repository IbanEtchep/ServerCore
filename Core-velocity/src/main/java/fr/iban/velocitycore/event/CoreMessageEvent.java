package fr.iban.velocitycore.event;

import fr.iban.common.messaging.Message;

public class CoreMessageEvent {

    private final Message message;

    public CoreMessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}

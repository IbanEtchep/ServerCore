package fr.iban.common.messaging;

import java.util.function.Consumer;

public abstract class AbstractMessenger {

    protected Consumer<Message> messageConsumer;

    public abstract void sendMessage(Message message);

    public void setOnMessageListener(Consumer<Message> message) {
        this.messageConsumer = message;
    }

}

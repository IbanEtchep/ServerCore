package fr.iban.common.messaging;

import java.util.function.Consumer;

public abstract class AbstractMessenger {

    protected boolean debugMode = false;
    protected Consumer<Message> messageConsumer;

    public void setOnMessageListener(Consumer<Message> message) {
        this.messageConsumer = message;
    }

    public abstract void init();
    public abstract void close();

    public abstract void sendMessage(Message message);

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}

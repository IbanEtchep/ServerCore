package fr.iban.common.messaging;

import com.google.gson.Gson;

public abstract class AbstractMessagingManager {

    protected AbstractMessenger messenger;
    private final Gson gson = new Gson();

    /**
     * Init an implementation of AbstractMessenger
     */
    public abstract void init();

    public abstract void close();

    protected abstract String getServerName();

    public void sendMessage(String channel, String jsonMsg) {
        messenger.sendMessage(new Message(channel, getServerName(), jsonMsg));
    }

    public <T> void sendMessage(String channel, T message) {
        sendMessage(channel, gson.toJson(message));
    }

    public AbstractMessenger getMessenger() {
        return messenger;
    }
}

package fr.iban.common.messaging;

public abstract class MessagingManager {

    protected final SqlMessenger sqlMessenger;

    public MessagingManager() {
        this.sqlMessenger = new SqlMessenger();
        sqlMessenger.init();
        startPollTask();
        startCleanUpTask();
    }

    public void sendMessage(Message message) {
        this.sqlMessenger.sendMessage(message);
    }

    public abstract void startPollTask();

    public abstract void startCleanUpTask();

}

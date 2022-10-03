package fr.iban.common.messaging.message;

import java.util.UUID;
import java.util.function.Consumer;

public class ApprovalRequest {

    private final UUID requestID;
    private final PlayerInfo playerInfo;
    private final String requestMessage;
    private final long createdAt = System.currentTimeMillis();
    private final transient Consumer<Boolean> resultConsumer;


    public ApprovalRequest(UUID requestID, PlayerInfo playerInfo, String requestMessage, Consumer<Boolean> resultConsumer) {
        this.requestID = requestID;
        this.playerInfo = playerInfo;
        this.requestMessage = requestMessage;
        this.resultConsumer = resultConsumer;
    }

    public UUID getRequestID() {
        return requestID;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void consumeResult(boolean result) {
        resultConsumer.accept(result);
    }

    public long getCreatedAt() {
        return createdAt;
    }
}

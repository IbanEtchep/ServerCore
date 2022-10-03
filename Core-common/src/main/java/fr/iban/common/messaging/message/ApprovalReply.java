package fr.iban.common.messaging.message;

import java.util.UUID;

public class ApprovalReply {

    private UUID requestID;
    private boolean result;
    private boolean trustUser;

    public ApprovalReply(UUID requestID, boolean result, boolean trustUser) {
        this.requestID = requestID;
        this.result = result;
        this.trustUser = trustUser;
    }

    public UUID getRequestID() {
        return requestID;
    }

    public void setRequestID(UUID requestID) {
        this.requestID = requestID;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isTrustUser() {
        return trustUser;
    }

    public void setTrustUser(boolean trustUser) {
        this.trustUser = trustUser;
    }
}

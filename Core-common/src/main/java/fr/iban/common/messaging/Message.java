package fr.iban.common.messaging;

public class Message {

    private String channel; //ECONOMY_USER_BALANCE_UPDATE, LAND_UPDATE, WARP_UPDATE
    private String serverFrom;
    private String message;

    public Message(String channel, String serverFrom, String message) {
        this.channel = channel;
        this.serverFrom = serverFrom;
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public String getServerFrom() {
        return serverFrom;
    }

    public String getMessage() {
        return message;
    }
}

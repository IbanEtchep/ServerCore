package fr.iban.bungeecore.teleport;

import java.util.UUID;

public class TpRequest {
	
	private UUID playerID;
	private RequestType requestType;
	
	public TpRequest() {}
	
	public TpRequest(UUID playerID, RequestType requestType) {
		this.playerID = playerID;
		this.requestType = requestType;
	}
	
	
	public RequestType getRequestType() {
		return requestType;
	}
	
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	
	public UUID getPlayerID() {
		return playerID;
	}
	public void setPlayerID(UUID playerID) {
		this.playerID = playerID;
	}
	

}

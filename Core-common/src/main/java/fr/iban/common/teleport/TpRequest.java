package fr.iban.common.teleport;

import java.util.UUID;

public class TpRequest {
	
	private UUID playerFrom;
	private UUID playerTo;
	private RequestType requestType;
	
	public TpRequest() {}

	public TpRequest(UUID playerFrom, UUID playerTo, RequestType requestType) {
		this.playerFrom = playerFrom;
		this.playerTo = playerTo;
		this.requestType = requestType;
	}

	public RequestType getRequestType() {
		return requestType;
	}
	
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public UUID getPlayerFrom() {
		return playerFrom;
	}

	public UUID getPlayerTo() {
		return playerTo;
	}
}

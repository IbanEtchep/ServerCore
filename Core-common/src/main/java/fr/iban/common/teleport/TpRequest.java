package fr.iban.common.teleport;

import java.util.Objects;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TpRequest request = (TpRequest) o;
		return Objects.equals(playerFrom, request.playerFrom) && Objects.equals(playerTo, request.playerTo) && requestType == request.requestType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerFrom, playerTo, requestType);
	}
}

package fr.iban.common.messaging.message;

import fr.iban.common.teleport.SLocation;

import java.util.UUID;

public class PlayerSLocationMessage {

	private UUID uuid;
	private SLocation location;

	public PlayerSLocationMessage(UUID uuid, SLocation location) {
		this.uuid = uuid;
		this.location = location;
	}


	public UUID getUuid() {
		return uuid;
	}


	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}


	public SLocation getLocation() {
		return location;
	}


	public void setLocation(SLocation location) {
		this.location = location;
	}

}

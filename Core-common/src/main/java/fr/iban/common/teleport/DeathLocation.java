package fr.iban.common.teleport;

import java.util.UUID;

public class DeathLocation {
	
	private UUID uuid;
	private SLocation location;
	
	public DeathLocation() {}
	
	
	public DeathLocation(UUID uuid, SLocation location) {
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

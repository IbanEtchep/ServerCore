package fr.iban.common.teleport;

import java.util.UUID;

public class TeleportToLocation {
	
	private UUID uuid;
	private SLocation location;
	private int delay = 0;
	
	public TeleportToLocation() {}
	
	public TeleportToLocation(UUID uuid, SLocation location) {
		this.uuid = uuid;
		this.location = location;
	}
	
	public TeleportToLocation(UUID uuid, SLocation location, int delay) {
		this(uuid, location);
		this.delay = delay;
		
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
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
}

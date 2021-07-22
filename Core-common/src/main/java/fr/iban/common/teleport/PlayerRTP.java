package fr.iban.common.teleport;

import java.util.UUID;

public class PlayerRTP {
	
	private UUID uuid;
	private String world;
	
	public PlayerRTP(UUID uuid, String world) {
		this.uuid = uuid;
		this.world = world;
	}
	
	public PlayerRTP() {}
	
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}
	
	
	

}

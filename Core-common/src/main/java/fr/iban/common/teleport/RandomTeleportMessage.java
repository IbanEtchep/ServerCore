package fr.iban.common.teleport;

import java.util.UUID;

public class RandomTeleportMessage {

	private UUID uuid;
	private String targetServer;
	private String world;

	public RandomTeleportMessage(UUID uuid, String targetServer, String world) {
		this.uuid = uuid;
		this.targetServer = targetServer;
		this.world = world;
	}

	public RandomTeleportMessage() {}
	
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

	public String getTargetServer() {
		return targetServer;
	}
}

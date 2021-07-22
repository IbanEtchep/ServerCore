package fr.iban.common.teleport;

import java.util.UUID;

public class TeleportToPlayer {
	
	private UUID uuid;
	private UUID target;
	
	public TeleportToPlayer() {}
	
	public TeleportToPlayer(UUID uuid, UUID target) {
		this.uuid = uuid;
		this.setTarget(target);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getTargetId() {
		return target;
	}

	public void setTarget(UUID target) {
		this.target = target;
	}
	
	
}

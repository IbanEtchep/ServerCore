package fr.iban.common.teleport;

import java.util.UUID;

public class TeleportToPlayer {
	
	private UUID uuid;
	private UUID target;
	private int delay;
	
	public TeleportToPlayer() {}

	public TeleportToPlayer(UUID uuid, UUID target, int delay) {
		this.uuid = uuid;
		this.setTarget(target);
		this.delay = delay;
	}
	
	public TeleportToPlayer(UUID uuid, UUID target) {
		this(uuid, target, 0);
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

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
}

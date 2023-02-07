package fr.iban.common.messaging.message;

import fr.iban.common.teleport.SLocation;

public class EventAnnounce {
	
	private String name;
	private String desc;
	private SLocation location;
	private String hostName;
	private String arena;
	
	public EventAnnounce() {}
	
	public EventAnnounce(String name, String arena, String desc, SLocation location, String hostName) {
		this.arena = arena;
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.hostName = hostName;
	}
	
	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public SLocation getLocation() {
		return location;
	}
	
	public void setLocation(SLocation location) {
		this.location = location;
	}

	public String getHostName() {
		return hostName;
	}
	
	public String getArena() {
		return arena;
	}
	
	public void setArena(String arena) {
		this.arena = arena;
	}

}

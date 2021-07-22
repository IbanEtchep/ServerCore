package fr.iban.common.teleport;

public class EventAnnouce {
	
	private String name;
	private String desc;
	private SLocation location;
	private String hostName;
	private String arena;
	
	public EventAnnouce() {}
	
	public EventAnnouce(String name, String arena, String desc, SLocation location, String hostName) {
		this.arena = arena;
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.hostName = hostName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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

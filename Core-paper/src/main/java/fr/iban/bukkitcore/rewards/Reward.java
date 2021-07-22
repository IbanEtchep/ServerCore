package fr.iban.bukkitcore.rewards;

public class Reward {
		
	private int id;
	private String name;
	private String server;
	private String command;
	
	
	public Reward(int id, String name, String server, String command) {
		this.id = id;
		this.name = name;
		this.server = server;
		this.command = command;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCommand() {
		return command;
	}

	public String getServer() {
		return server;
	}

}

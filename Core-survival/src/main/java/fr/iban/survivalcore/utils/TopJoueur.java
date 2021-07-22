package fr.iban.survivalcore.utils;

public class TopJoueur {

	private String name;
	private int level;
	
	
	public TopJoueur(String name, int level) {
		this.name = name;
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
}

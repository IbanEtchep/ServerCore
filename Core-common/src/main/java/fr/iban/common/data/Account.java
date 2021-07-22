package fr.iban.common.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Account {


	/*
	 * PLAYERDATA
	 * 
	 * Contient toutes les informations et méthodes relatives à un joueur.
	 * 
	 */

	private UUID uuid;
	private String name;
	private short maxClaims = 1;
	private long lastSeen = 0;
	private boolean bypass = false;
	private Set<Integer> blackListedAnnounces = new HashSet<>();
	private Set<UUID> ignoredPlayers = new HashSet<>();
	private Map<Option, Boolean> options = new HashMap<>();
	private String ip;
	private List<Boost> boosts;

	public Account(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Account() {}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public long getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(long lastseen) {
		this.lastSeen = lastseen;
	}

	public UUID getUUID() {
		return uuid;
	}

	public boolean isBypass() {
		return bypass;
	}

	public void setBypass(boolean bypass) {
		this.bypass = bypass;
	}

	public short getMaxClaims() {
		return maxClaims;
	}

	public void setMaxClaims(short maxClaims) {
		this.maxClaims = maxClaims;
	}

	public void addMaxClaims(short nombre) {
		maxClaims += nombre;
	}

	public void removeMaxClaims(short nombre) {
		maxClaims -= nombre;
		if(maxClaims < 0) maxClaims = 0;
	}


	public Set<Integer> getBlackListedAnnounces() {
		if(blackListedAnnounces == null) {
			blackListedAnnounces = new HashSet<>();
		}
		return blackListedAnnounces;
	}
	
	public Set<UUID> getIgnoredPlayers() {
		if(ignoredPlayers == null) {
			ignoredPlayers = new HashSet<>();
		}
		return ignoredPlayers;
	}
	
	public Map<Option, Boolean> getOptions() {
		if(options == null) {
			options = new HashMap<>();
		}
		return options;
	}
	
	
	public void setBlackListedAnnounces(Set<Integer> blackListedAnnounces) {
		this.blackListedAnnounces = blackListedAnnounces;
	}
	
	public void setIgnoredPlayers(Set<UUID> ignoredPlayers) {
		this.ignoredPlayers = ignoredPlayers;
	}
	
	public void setOptions(Map<Option, Boolean> options) {
		this.options = options;
	}
	
	public void setOption(Option option, boolean value) {
		if(!options.containsKey(option)) {
			options.put(option, true);
		}
		this.options.put(option, value);
	}
	

	public void toggleOption(Option option) {
		if(options.containsKey(option) && !options.get(option)) {
			options.put(option, true);
		} else {
			options.put(option, false);
		}
	}
	
	public boolean getOption(Option option) {
		return options.getOrDefault(option, option.getDefaultValue());
	}
		
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getLastId() {
		int valeur = 0;
		Iterator<Boost> it = getBoosts().iterator();
		while(it.hasNext()){
		     Boost boost = it.next();
		     valeur = boost.getId();
		}
		return valeur;
	}
	
	public long getLastEnd() {
		long end = 0;
		Iterator<Boost> it = getBoosts().iterator();
		while(it.hasNext()){
		     Boost boost = it.next();
		     end = boost.getEnd();
		}
		return end;
	}

	public List<Boost> getBoosts() {
		if(boosts == null) {
			boosts = new ArrayList<>();
		}
		return boosts;
	}
	
	public void setBoosts(List<Boost> boosts) {
		this.boosts = boosts;
	}
}

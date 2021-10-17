package fr.iban.common.data;

public enum Option {

	PVP(false),
	JOIN_MESSAGE(true),
	LEAVE_MESSAGE(true),
	DEATH_MESSAGE(true),
	TP(true),
	TCHAT(true),
	MENTION(true),
	MSG(true);
	
	private boolean defaultvalue;
	
	private Option(boolean defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public boolean getDefaultValue() {
		return defaultvalue;
	}

}

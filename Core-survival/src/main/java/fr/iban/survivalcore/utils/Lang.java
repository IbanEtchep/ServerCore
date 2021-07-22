package fr.iban.survivalcore.utils;

public enum Lang {
    
    NOPERM("§cVous n'avez pas la permissions de faire ça."),
    ERROR("§4Une erreur est survenue, veuillez contacter un administrateur."),
	PLAYER_OFFLINE("§cCe joueur n'est pas en ligne.");
    
    private String string;

    private Lang(String string) {
	this.string = string;
    }

    public String get() {
	return string;
    }

}

package fr.iban.survivalcore.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;

import fr.iban.survivalcore.utils.ArrayUtils;

public class ServerListPingListener implements Listener {

    private String[] messages = {
	    "Ici, on arrive à monter en bas et à descendre en haut.",
	    "Danette se lève pour nous.",
	    "Nous sommes contre les radars automatiques : ça éblouit lorsqu'on fait du vélo...",
	    "Quand on lance une pièce elle fait toujours pile ; personne ne nous fait face.",
	    "Ici on sait étrangler quelqu'un avec un téléphone sans fil.",
	    "Ici on ne dort pas. On attend.",
	    "Ici on ne coupe ni de pommes ni de poires... avec nous, pas de quartier.",
	    "Ici on sait diviser par zéro.",
	    "Ici on sait compter jusqu'à l'infini.",
	    "La seule chose qui nous arrive à la cheville ce sont nos chaussettes.",
	    "Ici, on sait faire pleurer les oignons quand on les coupe.",
	    "Un jour au restaurant, nos joueurs ont commandé un steak, et le steak a obéi.",
	    "Si un spartiate te rate, c'est qu'il visait quelqu'un d'autre.",
	    "Quand la nuit tombe, on la ramasse.",
	    "Le mot \"impossible\" désigne une action que seul ici on peut réussir.",
	    "Ici on sait faire des loopings avec une montgolfière.",
	    "Ici, on sait faire des ricochets avec des parpaings.",
	    "La foudre est tombée à 1 mètre de nous... Elle a eu chaud.",
	    "On a trouvé le chemin qui ne mène pas à Rome.",
	    "Quand on lui dit qu'il est gros, Obélix ferme sa gueule.",
	    "On a planté la Lune sous le drapeau américain.",
	    "On ne croit pas en Dieu, Dieu croit en nous."
    };

    @EventHandler
    public void onPing(PaperServerListPingEvent e) {
	e.setMaxPlayers(100);
	//e.setMotd("§6✷ §cSpΛrta§6Cube §c✷ §eOuverture à 15h ! \n §7site: spartacube.fr");

	e.setMotd("§6✷ §cSpΛrta§6Cube §c✷ §e" + ArrayUtils.getRandomFromArray(messages));
    }

}

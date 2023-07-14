package fr.iban.bungeecore.commands;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import revxrsal.commands.annotation.Command;

public class MiscellaneousCommands {

    @Command("offhand")
    public void offHand(ProxiedPlayer player) {
        if(player.getName().startsWith(".")) {
            player.chat("/geyser offhand");
        }else {
            player.sendMessage("§cCommande réservée aux utilisateurs de la version bedrock.");
        }
    }

}

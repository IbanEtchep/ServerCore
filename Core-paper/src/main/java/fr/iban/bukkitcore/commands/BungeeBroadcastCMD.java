package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.utils.HexColor;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BungeeBroadcastCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length >= 1){
            StringBuilder bc = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                bc.append(args[i] + " ");
            }
            String message = HexColor.translateColorCodes(bc.toString());
            PluginMessageHelper.broadcastMessage(message);
        }


        return false;
    }
}

package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.utils.HexColor;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BungeeBroadcastCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if(args.length >= 1){
            StringBuilder bc = new StringBuilder();
            for (String arg : args) {
                bc.append(arg).append(" ");
            }
            String message = HexColor.translateColorCodes(bc.toString());
            PluginMessageHelper.broadcastMessage(message);
        }


        return false;
    }
}

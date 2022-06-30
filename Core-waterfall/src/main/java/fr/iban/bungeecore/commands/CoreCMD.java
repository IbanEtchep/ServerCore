package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.messaging.AbstractMessenger;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CoreCMD extends Command {

    private final CoreBungeePlugin plugin;

    public CoreCMD(String name, String permission, CoreBungeePlugin plugin) {
        super(name, permission);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.loadConfig();
                sender.sendMessage(TextComponent.fromLegacyText("§aLa configuration a été rechargée avec succès."));
            } else if (args[0].equalsIgnoreCase("debug")) {
                AbstractMessenger messenger = plugin.getMessagingManager().getMessenger();
                messenger.setDebugMode(!messenger.isDebugMode());
                sender.sendMessage("debug mode : " + messenger.isDebugMode());
            }
        }
    }
}

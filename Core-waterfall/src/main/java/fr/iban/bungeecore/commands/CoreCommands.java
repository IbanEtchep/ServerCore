package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.utils.HexColor;
import net.md_5.bungee.api.chat.TextComponent;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bungee.BungeeCommandActor;
import revxrsal.commands.command.CommandActor;

import java.util.Objects;

public class CoreCommands {

    private final CoreBungeePlugin plugin;

    public CoreCommands(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Command("site")
    public void site(BungeeCommandActor actor) {
        if (actor.isPlayer()) {
            Objects.requireNonNull(actor.asPlayer()).sendMessage(TextComponent.fromLegacyText(
                    HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.site"))
            ));
        } else {
            actor.reply(HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.site")));
        }
    }

    @Command("discord")
    public void discord(BungeeCommandActor actor) {
        if (actor.isPlayer()) {
            Objects.requireNonNull(actor.asPlayer()).sendMessage(TextComponent.fromLegacyText(
                    HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.discord"))
            ));
        } else {
            actor.reply(HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.discord")));
        }
    }

    @Command("vote")
    public void vote(BungeeCommandActor actor) {
        if (actor.isPlayer()) {
            Objects.requireNonNull(actor.asPlayer()).sendMessage(TextComponent.fromLegacyText(
                    HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.vote"))
            ));
        } else {
            actor.reply(HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.vote")));
        }
    }

    @Command("grades")
    public void grades(BungeeCommandActor actor) {
        if (actor.isPlayer()) {
            Objects.requireNonNull(actor.asPlayer()).sendMessage(TextComponent.fromLegacyText(
                    HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.grades"))
            ));
        } else {
            actor.reply(HexColor.translateColorCodes(plugin.getConfiguration().getString("messages.grades")));
        }
    }

}

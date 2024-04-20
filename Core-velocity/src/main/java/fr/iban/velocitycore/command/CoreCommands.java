package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import de.themoep.minedown.adventure.MineDown;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.velocity.VelocityCommandActor;
import revxrsal.commands.velocity.VelocityCommandHandler;

public class CoreCommands {

    private final CoreVelocityPlugin plugin;

    public CoreCommands(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command("site")
    public void siteCommand(VelocityCommandActor actor) {
        sendMessage(actor, "messages.site");
    }

    @Command("discord")
    public void discordCommand(VelocityCommandActor actor) {
        sendMessage(actor, "messages.discord");
    }

    @Command("vote")
    public void voteCommand(VelocityCommandActor actor) {
        sendMessage(actor, "messages.vote");
    }

    @Command("grades")
    public void gradesCommand(VelocityCommandActor actor) {
        sendMessage(actor, "messages.grades");
    }

    @Command("wiki")
    public void wikiCommand(VelocityCommandActor actor) {
        sendMessage(actor, "messages.wiki");
    }

    private void sendMessage(VelocityCommandActor actor, String configPath) {
        if (actor instanceof Player player) {
            player.sendMessage(MineDown.parse(plugin.getConfig().getString(configPath)));
        } else {
            actor.reply(MineDown.parse(plugin.getConfig().getString(configPath)));
        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}

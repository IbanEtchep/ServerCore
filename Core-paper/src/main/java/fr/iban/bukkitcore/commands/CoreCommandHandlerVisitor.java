package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.commands.annotation.Context;
import fr.iban.bukkitcore.commands.annotation.Online;
import fr.iban.bukkitcore.commands.annotation.SenderType;
import fr.iban.bukkitcore.commands.annotation.SurvivalServer;
import fr.iban.bukkitcore.manager.BukkitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.CommandHandlerVisitor;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.autocomplete.SuggestionProviderFactory;
import revxrsal.commands.exception.CommandErrorException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;

public class CoreCommandHandlerVisitor implements CommandHandlerVisitor {

    private final CoreBukkitPlugin plugin;

    public CoreCommandHandlerVisitor(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void visit(@NotNull CommandHandler handler) {
        BukkitPlayerManager playerManager = plugin.getPlayerManager();
        Collection<String> playerNames = playerManager.getOnlinePlayers().values();

        handler.setLocale(Locale.FRENCH);

        handler.getAutoCompleter().registerSuggestionFactory(0,
                SuggestionProviderFactory.forType(OfflinePlayer.class, SuggestionProvider.of(playerNames)));
        handler.getAutoCompleter().registerSuggestionFactory(0,
                SuggestionProviderFactory.forType(UUID.class, SuggestionProvider.of(playerNames)));

        //OfflinePlayer
        handler.registerValueResolver(0, OfflinePlayer.class, context -> {
            String value = context.arguments().pop();
            if (!playerManager.getOfflinePlayers().containsKey(value)) {
                throw new CommandErrorException("Le joueur " + value + " n''a jamais joué sur le serveur.");
            }
            return Bukkit.getOfflinePlayer(playerManager.getOfflinePlayerUUID(value));
        });

        handler.getAutoCompleter().registerSuggestionFactory(parameter -> {
            if (parameter.hasAnnotation(Context.class)) {
                return (args, sender, command) -> Arrays.asList("global", "bukkit", "proxy");
            }

            if (parameter.hasAnnotation(SenderType.class)) {
                return (args, sender, command) -> Arrays.asList("player", "staff", "console");
            }

            if (parameter.hasAnnotation(SurvivalServer.class)) {
                return (args, sender, command) -> plugin.getServerManager().getSurvivalServers();
            }

            return null;
        });

        handler.registerParameterValidator(OfflinePlayer.class, (value, parameter, actor) -> {
            if (parameter.hasAnnotation(Online.class)) {
                if (!playerManager.getOnlinePlayers().containsKey(value.getUniqueId()))
                    throw new CommandErrorException("Ce joueur n''est pas en ligne.");
            }
        });


        //UUID
        handler.registerValueResolver(0, UUID.class, context -> {
            String value = context.arguments().pop();
            if (!playerManager.getOfflinePlayers().containsKey(value)) {
                throw new CommandErrorException("Le joueur " + value + " n''a jamais joué sur le serveur.");
            }
            return playerManager.getOfflinePlayerUUID(value);
        });

        handler.registerParameterValidator(UUID.class, (value, parameter, actor) -> {
            if (parameter.hasAnnotation(Online.class)) {
                if (!playerManager.getOnlinePlayers().containsKey(value))
                    throw new CommandErrorException("Ce joueur n''est pas en ligne.");
            }
        });
    }

}

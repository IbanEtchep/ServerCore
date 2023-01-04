package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.commands.annotation.Online;
import fr.iban.bukkitcore.manager.RessourcesWorldManager;
import fr.iban.bukkitcore.manager.TeleportManager;
import fr.iban.bukkitcore.menu.RessourceMenu;
import fr.iban.bukkitcore.menu.ServeurMenu;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.common.teleport.RequestType;
import fr.iban.common.teleport.TpRequest;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class ServerSwitchCommands {

    private final CoreBukkitPlugin plugin;
    private final TeleportManager teleportManager;

    public ServerSwitchCommands(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
        this.teleportManager = plugin.getTeleportManager();
    }

    @Command("survie")
    public void survie(Player sender) {
        teleportManager.teleportToSurvivalServer(sender);
    }

    @Command("serveur")
    public void serveurMenu(Player sender) {
        new ServeurMenu(sender).open();
    }

    @Command("ressources")
    @AutoComplete("world|nether|end")
    public void ressources(Player sender, @Optional String world) {
        if(world == null){
            new RessourceMenu(sender).open();
        }else {
            final RessourcesWorldManager ressourcesWorldManager = CoreBukkitPlugin.getInstance().getRessourcesWorldManager();
            switch (world) {
                case "world" -> ressourcesWorldManager.sendToRessourceWorld(sender, "resource_world");
                case "nether" -> ressourcesWorldManager.sendToRessourceWorld(sender, "resource_nether");
                case "end" -> ressourcesWorldManager.sendToRessourceWorld(sender, "resource_end");
                default -> sender.sendMessage("§cCe type de monde n'existe pas.");
            }
        }
    }

    @Command("survivalrtp")
    @Cooldown(value = 2, unit = TimeUnit.MINUTES)
    public void survivalRandomTP(Player player) {
        plugin.getTeleportManager().randomTeleportToSurvivalServer(player);
    }

}

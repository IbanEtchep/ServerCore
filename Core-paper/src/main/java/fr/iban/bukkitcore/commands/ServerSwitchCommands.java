package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.manager.RessourcesWorldManager;
import fr.iban.bukkitcore.manager.TeleportManager;
import fr.iban.bukkitcore.menu.RessourceMenu;
import fr.iban.bukkitcore.menu.ServeurMenu;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.*;

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
    @AutoComplete("world|nether|end|lastpos")
    public void ressources(Player sender, @Optional String world) {
        if(world == null){
            new RessourceMenu(sender).open();
        }else {
            final RessourcesWorldManager ressourcesWorldManager = CoreBukkitPlugin.getInstance().getRessourcesWorldManager();
            switch (world) {
                case "world" -> ressourcesWorldManager.randomTpResourceWorld(sender, "resource_world");
                case "nether" -> ressourcesWorldManager.randomTpResourceWorld(sender, "resource_nether");
                case "end" -> ressourcesWorldManager.randomTpResourceWorld(sender, "resource_end");
                case "lastpos" -> PluginMessageHelper.sendPlayerToServer(sender, ressourcesWorldManager.getResourceServerName());
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

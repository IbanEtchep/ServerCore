package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.VelocityCommandActor;
import revxrsal.commands.velocity.annotation.CommandPermission;
import revxrsal.commands.velocity.exception.SenderNotPlayerException;

import java.io.IOException;
import java.util.List;

public class TabCompleteCMD {

    private final CoreVelocityPlugin plugin;

    public TabCompleteCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command("addtabcomplete")
    @CommandPermission("servercore.addtabcomplete")
    @Description("Ajoute une commande à l'auto-complétion des groupes spécifiés.")
    @AutoComplete("@completions")
    @Usage("/addtabcomplete <global/moderation> <commandeSansSlash>")
    public void execute(@NotSender Player sender, String[] args) throws SenderNotPlayerException, IOException {
        if (args.length <= 1) {
            sender.sendMessage(Component.text("/addtabcomplete global/moderation commandeSansSlash", NamedTextColor.RED));
        } else if (args.length == 2) {
            switch (args[0]) {
                case "global" -> addCommandToGroup(sender, "global", args[1]);
                case "moderation" -> addCommandToGroup(sender, "moderation", args[1]);
                default -> sender.sendMessage(Component.text("Commande inconnue.", NamedTextColor.RED));
            }
        }
    }

    private void addCommandToGroup(Player sender, String group, String command) throws IOException {
        String path = "tabcomplete." + group;
        List<String> list = plugin.getConfig().getStringList(path);
        list.add(command);
        plugin.getConfig().set(path, list);
        sender.sendMessage(Component.text(command + " ajouté au groupe " + group, NamedTextColor.GREEN));
        plugin.getConfig().save();
    }
}

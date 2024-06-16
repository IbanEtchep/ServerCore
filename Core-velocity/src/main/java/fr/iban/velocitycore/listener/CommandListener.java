package fr.iban.velocitycore.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import fr.iban.velocitycore.CoreVelocityPlugin;

public class CommandListener {

    private final CoreVelocityPlugin plugin;

    public CommandListener(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onChat(CommandExecuteEvent event) {
        if(event.getCommandSource().equals(plugin.getServer().getConsoleCommandSource())) {
            return;
        }

        String message = event.getCommand().toLowerCase();
        if (message.startsWith("lpv") || message.startsWith("luckpermsvelocity")) {
            event.setResult(CommandExecuteEvent.CommandResult.denied());
        }
    }

}

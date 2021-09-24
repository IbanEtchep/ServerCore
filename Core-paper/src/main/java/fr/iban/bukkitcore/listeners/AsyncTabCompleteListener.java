package fr.iban.bukkitcore.listeners;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import fr.iban.bukkitcore.CoreBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AsyncTabCompleteListener implements Listener {

    CoreBukkitPlugin plugin;

    public AsyncTabCompleteListener(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTabComplete(AsyncTabCompleteEvent e){
        String buffer = e.getBuffer();
        if(e.getSender() instanceof Player){
            Player player = (Player) e.getSender();
            if(e.isCommand()){
                if(buffer.startsWith("/tp") || buffer.startsWith("/tpa") || buffer.startsWith("/s") || buffer.startsWith("/tphere") || buffer.startsWith("/tpahere")|| buffer.startsWith("/tpyes") || buffer.startsWith("/tpno")){
                    String[] args = buffer.split(" ");

                    String start = "";
                    if(args.length == 2){
                        start = args[1];
                    }

                    if(args.length >= 1){
                        List<String> playerNames = new ArrayList<>();
                        for(String name: plugin.getProxyPlayers().keySet()) {
                            if (!player.getName().equals(name) && name.toLowerCase().startsWith(start.toLowerCase())) {
                                playerNames.add(name);
                            }
                        }
                        e.setCompletions(playerNames);
                    }
                }
            }
        }
    }
}

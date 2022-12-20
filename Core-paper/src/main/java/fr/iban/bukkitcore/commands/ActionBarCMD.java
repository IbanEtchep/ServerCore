package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ActionBarCMD implements CommandExecutor {
	
	private final List<String> queue = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length >= 1) {
			StringBuilder bc = new StringBuilder();
			for (String arg : args) {
				bc.append(arg + " ");
			}
			String message = bc.toString();
			
			if(queue.isEmpty()) {
				sendActionBar(message);
			}
			queue.add(message);
		}
		return false;
	}
	
	private void sendActionBar(String message) {
		new BukkitRunnable() {
			int time = 0;
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(p -> p.sendActionBar(ChatUtils.translateColors(message)));
				time++;
				if(time == 4) {
					cancel();
					queue.remove(0);
					if(!queue.isEmpty()) {
						sendActionBar(queue.get(0));
					}
				}
			}
		}.runTaskTimer(CoreBukkitPlugin.getInstance(), 0, 20L);
	}



}
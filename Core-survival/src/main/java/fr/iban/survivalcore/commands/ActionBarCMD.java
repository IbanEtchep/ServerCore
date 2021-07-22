package fr.iban.survivalcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import fr.iban.survivalcore.SurvivalCorePlugin;
import fr.iban.survivalcore.utils.ChatUtils;

public class ActionBarCMD implements CommandExecutor {
	
	private List<String> queue = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("spartacube.actionbar") && args.length >= 1) {
			StringBuilder bc = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				bc.append(args[i] + " ");
			}
			String message = bc.toString();
			
			if(queue.isEmpty()) {
				sendActionBar(message);
				//System.out.println("isempty");
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
				//System.out.println(time);
				if(time == 4) {
					cancel();
					queue.remove(0);
					if(!queue.isEmpty()) {
						//System.out.println("!isempty");
						sendActionBar(queue.get(0));
					}
				}
			}
		}.runTaskTimer(SurvivalCorePlugin.getInstance(), 0, 20L);
	}



}
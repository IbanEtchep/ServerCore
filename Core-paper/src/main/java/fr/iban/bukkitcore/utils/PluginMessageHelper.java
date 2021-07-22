package fr.iban.bukkitcore.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.listeners.PluginMessageReceivedListener;

public class PluginMessageHelper {
	
	private static final String BUNGEECORD_CHANNEL = "BungeeCord";

	public static void registerChannels(Plugin plugin) {
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, BUNGEECORD_CHANNEL, new PluginMessageReceivedListener());
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, BUNGEECORD_CHANNEL);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "proxy:chat");
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "proxy:annonce");
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "proxy:send");
	}
	
	public static void receivePluginMessage(String channel, byte[] bytes) {
		if(!channel.equalsIgnoreCase(PluginMessageHelper.BUNGEECORD_CHANNEL)) {
			return;
		}
		
		final ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
		final String sub = in.readUTF();

        if(sub.equals("GetServer")){
            String name = in.readUTF();
            CoreBukkitPlugin.getInstance().setServerName(name);
        }
	}
	
	public static void sendPlayerToServer(Player player , String serveur) {
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("ConnectOther");
	    out.writeUTF(player.getName());
	    out.writeUTF(serveur);
	    player.sendPluginMessage(CoreBukkitPlugin.getInstance(), BUNGEECORD_CHANNEL, out.toByteArray());
	}
	
	public static void sendGlobalMessage(Player player , String message) {
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("Global");
	    out.writeUTF(player.getUniqueId().toString());
	    out.writeUTF(message);
	    player.sendPluginMessage(CoreBukkitPlugin.getInstance(), "proxy:chat", out.toByteArray());
	}
	
	public static void sendServer(Player player) {
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("Server");
	    out.writeUTF(player.getUniqueId().toString());
	    player.sendPluginMessage(CoreBukkitPlugin.getInstance(), "proxy:send", out.toByteArray());
	}
	
	public static void sendAnnonce(Player player , String annonce) {
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("Annonce");
	    out.writeUTF(player.getUniqueId().toString());
	    out.writeUTF(annonce);
	    player.sendPluginMessage(CoreBukkitPlugin.getInstance(), "proxy:annonce", out.toByteArray());
	}
	
	public static void askServerName(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        player.sendPluginMessage(CoreBukkitPlugin.getInstance(), BUNGEECORD_CHANNEL, out.toByteArray());
	}
}

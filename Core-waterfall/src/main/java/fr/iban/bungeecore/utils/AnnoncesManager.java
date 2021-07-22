package fr.iban.bungeecore.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.utils.ArrayUtils;
import fr.iban.spartacube.data.Account;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AnnoncesManager {
	
	private Map<Integer, BaseComponent[]> annonces = new HashMap<>();

	public AnnoncesManager() {
		addAnnounces();
		startAnnouncetask();
	}
	
	public Map<Integer, BaseComponent[]> getAnnonces() {
		return annonces;
	}
	
	private BaseComponent[] getMessage(int id, String msg, String command) {
		String croix = " §7[§c✕§7]";
		BaseComponent[] announceDisable = new ComponentBuilder(croix).event(ChatUtils.getShowTextHoverEvent("§cClic pour ne plus afficher cette annnonce")).event(ChatUtils.getCommandClickEvent("/announce disable " + id)).create();
		ComponentBuilder announceBuilder = new ComponentBuilder(msg);
		if(command != null) {
			announceBuilder.event(ChatUtils.getShowTextHoverEvent("§aClic pour lancer la commande §5"+command));
			announceBuilder.event(ChatUtils.getCommandClickEvent(command));
		}
		return announceBuilder.append(announceDisable).create();
	}
	
	private void addAnnounces() {
		annonces.put(0, getMessage(0, "§5≫ §dRejoignez-nous sur discord pour suivre toute l'actualité du serveur ! §5§l/discord§d !", "/discord"));
		annonces.put(1, getMessage(1, "§5≫ §dPensez à voter régulièrement pour soutenir le serveur et gagner des récompenses ! §5§l/vote§d !", "/vote"));
		annonces.put(2, getMessage(2, "§5≫ §dPensez à visiter notre site. Vous y trouverez le réglement, la boutique, la page de votes,...! §5§l/site§d !", "/site"));
		annonces.put(3, getMessage(3, "§5≫ §dUne dynmap du serveur est disponible ! §5§l/map§d !", "/map"));
		annonces.put(4, getMessage(4, "§5≫ §dVous pouvez nous aider à financer le serveur en faisant des achats sur la boutique ! §5§l/boutique§d !", "/boutique"));
	}
		
	private void startAnnouncetask() {
		ProxyServer.getInstance().getScheduler().schedule(CoreBungeePlugin.getInstance(), () -> {
			int id = (int) ArrayUtils.getRandomFromArray(annonces.keySet().toArray());
			BaseComponent[] annonce = annonces.get(id);
			for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				AccountProvider ap = new AccountProvider(player.getUniqueId());
				Account account = ap.getAccount();
				if(!account.getBlackListedAnnounces().contains(id)) {
					player.sendMessage(annonce);
				}
			}
		}, 0, 5, TimeUnit.MINUTES);
	}

}

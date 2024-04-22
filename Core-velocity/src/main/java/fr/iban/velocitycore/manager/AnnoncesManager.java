package fr.iban.velocitycore.manager;

import com.velocitypowered.api.scheduler.ScheduledTask;
import de.themoep.minedown.adventure.MineDown;
import fr.iban.common.utils.ArrayUtils;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AnnoncesManager {

	private final CoreVelocityPlugin plugin;
	private final Map<Integer, Component> annonces = new HashMap<>();

	public AnnoncesManager(CoreVelocityPlugin plugin) {
		this.plugin = plugin;
		addAnnounces();
		startAnnouncetask();
	}

	public Map<Integer, Component> getAnnonces() {
		return annonces;
	}

	private Component getMessage(int id, String msg, String command) {
		Component croix = Component.text(" [")
				.append(Component.text("✕", NamedTextColor.RED))
				.append(Component.text("]", NamedTextColor.GRAY))
				.hoverEvent(HoverEvent.showText(Component.text("Clic pour ne plus afficher cette annonce", NamedTextColor.RED)))
				.clickEvent(ClickEvent.runCommand("/announce disable " + id));

		Component messageComponent = MineDown.parse(msg);

		if (command != null) {
			messageComponent = messageComponent
					.hoverEvent(HoverEvent.showText(Component.text("Clic pour lancer la commande " + command, NamedTextColor.GREEN)))
					.clickEvent(ClickEvent.runCommand(command));
		}

		return messageComponent.append(croix);
	}

	private void addAnnounces() {
		annonces.put(0, getMessage(0, "&5≫ &dRejoignez-nous sur discord pour suivre toute l'actualité du serveur ! &5&l/discord&d !", "/discord"));
		annonces.put(1, getMessage(1, "&5≫ &dPensez à voter régulièrement pour soutenir le serveur et gagner des récompenses ! &5&l/vote&d !", "/vote"));
		annonces.put(2, getMessage(2, "&5≫ &dPensez à visiter notre site. Vous y trouverez le réglement, la boutique, la page de votes,...! &5&l/site&d !", "/site"));
		annonces.put(3, getMessage(3, "&5≫ &dVous pouvez nous aider à financer le serveur en faisant des achats sur la boutique ! &5&l/boutique&d !", "/boutique"));
	}

	private void startAnnouncetask() {
        plugin.getServer().getScheduler().buildTask(plugin, () -> {
            int id = (int) ArrayUtils.getRandomFromArray(annonces.keySet().toArray());
            Component annonce = annonces.get(id);

            plugin.getServer().getAllPlayers().stream()
                    .filter(player -> !plugin.getAccountManager().getAccount(player.getUniqueId()).getBlackListedAnnounces().contains(id))
                    .forEach(player -> player.sendMessage(annonce));
        }).repeat(5, TimeUnit.MINUTES).schedule();
    }

}
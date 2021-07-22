package fr.iban.survivalcore.utils;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import fr.iban.survivalcore.SurvivalCorePlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PermissionNode;

public class LuckPermsUtils {

	private static LuckPerms luckapi = LuckPermsProvider.get();
	
//	public void registerEvents() {
//        EventBus eventBus = luckapi.getEventBus();
//        eventBus.subscribe(UserPromoteEvent.class, this::onUserPromote);
//	}
	
//    private void onUserPromote(UserPromoteEvent event) {
//    	if(event.getTrack().getName().equalsIgnoreCase("joueurs")) {
//            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
//                Bukkit.broadcastMessage(HexColor.FLAT_PINK.getColor() + Bukkit.getPlayer(event.getUser().getUniqueId()).getName() + " a été promu " + event.getGroupTo().get() + "!");
//            });
//    	}
//    }

	public void addPermission(Player player, String perm) {
		User user = loadUser(player);
		PermissionNode node = PermissionNode.builder(perm).build();
		user.getNodes().add(node);
		luckapi.getUserManager().saveUser(user);
	}

	public static void addHomes(Player player, int amount) {
		CompletableFuture<User> userFuture = luckapi.getUserManager().loadUser(player.getUniqueId());
		userFuture.thenAcceptAsync(user -> {
			int homes = 1;
			for (PermissionNode permNode : user.getNodes(NodeType.PERMISSION)) {
				if(permNode.getKey().startsWith("essentials.sethome.multiple.")) {
					homes = Integer.parseInt(StringUtils.removeStart(permNode.getKey(), "essentials.sethome.multiple."));
					user.data().remove(permNode);
				}
			}
			SurvivalCorePlugin.getInstance().getLogger().log(Level.INFO, "Résidences de " + player.getName() + " " + homes + " -> "+(homes+amount));
			user.data().add(Node.builder("essentials.sethome.multiple."+(homes+amount)).build());
			luckapi.getUserManager().saveUser(user);
		});
	}

	public static User loadUser(Player player) {
		if (!player.isOnline())
			throw new IllegalStateException("Player is offline!"); 
		return luckapi.getUserManager().getUser(player.getUniqueId());
	}

}

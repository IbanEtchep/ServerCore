package fr.iban.survivalcore.utils;

import fr.iban.bukkitcore.utils.HexColor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;

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

	public static void showGroups(Player player) {
		for (Group group : luckapi.getGroupManager().getLoadedGroups()) {
			CachedMetaData metaData = group.getCachedData().getMetaData();
			player.sendMessage(HexColor.translateColorCodes(metaData.getSuffix() + metaData.getPrefix() + " " + player.getName()));
		}
	}

	public static User loadUser(Player player) {
		if (!player.isOnline())
			throw new IllegalStateException("Player is offline!"); 
		return luckapi.getUserManager().getUser(player.getUniqueId());
	}

}

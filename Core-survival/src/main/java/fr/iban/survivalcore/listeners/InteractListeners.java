package fr.iban.survivalcore.listeners;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.iban.survivalcore.SurvivalCorePlugin;
import fr.iban.survivalcore.tools.SpecialTools;


public class InteractListeners implements Listener {
	
	private SurvivalCorePlugin plugin;
	
	private final Set<Material> cropList = EnumSet.of(
			Material.WHEAT, Material.POTATOES, Material.CARROTS,
			Material.BEETROOTS, Material.NETHER_WART );
	
	public InteractListeners(SurvivalCorePlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		BlockFace bf = e.getBlockFace();
		if(bf != null) {
			SpecialTools.faces.put(e.getPlayer().getUniqueId(), bf);
		}
		
		ItemStack item = e.getItem();
		
		if(e.getItem() != null) {
			if(e.getItem().getType() == Material.NETHERITE_HOE && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
				if(SpecialTools.isReplantHoue(e.getItem())) {
					Block block = e.getClickedBlock();
					Material material = block.getType();
					if(cropList.contains(material)) {
						BlockData bd = block.getBlockData();
						Ageable age = (Ageable) bd;

						if(age.getAge() == age.getMaximumAge()) {
							
							block.breakNaturally();
							ItemMeta meta = item.getItemMeta();
							Damageable itemDmg = (Damageable) meta;
							
							double rand = Math.random() * 100;
							double breakChance = (100/(meta.getEnchantLevel(Enchantment.DURABILITY)+1));
							if(rand <= breakChance) {
								
								itemDmg.setDamage(itemDmg.getDamage() + 1);

								item.setItemMeta(meta);
							}
							
//							e.getPlayer().sendMessage(rand+"");
//							e.getPlayer().sendMessage(breakChance+"");

							new BukkitRunnable() {

								@Override
								public void run() {

									block.setType(material);
								}
							}.runTaskLater(plugin, 1L);
						}
					}
					
				}
			}
		}

	}

}
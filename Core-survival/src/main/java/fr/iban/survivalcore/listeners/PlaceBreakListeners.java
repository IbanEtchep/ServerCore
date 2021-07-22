package fr.iban.survivalcore.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import fr.iban.lands.LandManager;
import fr.iban.lands.LandsPlugin;
import fr.iban.lands.enums.Action;
import fr.iban.lands.objects.Land;
import fr.iban.survivalcore.SurvivalCorePlugin;
import fr.iban.survivalcore.tools.SpecialTools;

public class PlaceBreakListeners implements Listener {
	

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		LandManager landManager = LandsPlugin.getInstance().getLandManager();
		Player player = e.getPlayer();
		Block block = e.getBlock();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		Location loc = block.getLocation();

		if (player.isSneaking()) return;

		Chunk chunk = block.getChunk();
		Land land = landManager.getLandAt(chunk);

		if(!landManager.isWilderness(land) && !land.isBypassing(player, Action.BLOCK_BREAK)) return;

		//Pioche 3x3
		if(SpecialTools.is3x3Pickaxe(itemInHand)) {
			//Bukkit.broadcastMessage("pioche");
			for(Block b : SpecialTools.getSurroundingBlocksPickaxe(player, block)){
				Chunk c = b.getChunk();
				Land l = landManager.getLandAt(c);
				if(!landManager.isWilderness(l) && !l.isBypassing(player, Action.BLOCK_BREAK)) 
					continue;

				b.breakNaturally(itemInHand);
			}
		}

		//Pelle 3x3
		if(SpecialTools.is3x3Shovel(itemInHand)) {
			//Bukkit.broadcastMessage("pelle");
			for(Block b : SpecialTools.getSurroundingBlocksShovel(player, block)){
				Chunk c = b.getChunk();
				Land l = landManager.getLandAt(c);
				if(!landManager.isWilderness(l) && !l.isBypassing(player, Action.BLOCK_BREAK)) 
					continue;

				b.breakNaturally(itemInHand);
			}
		}

		//Hache bûcheron
		if(SpecialTools.isLumberjackAxe(itemInHand)) {
			//Bukkit.broadcastMessage("hache");
			if (!isLog(block.getType())) {
				return;
			}
			dropTree(block, itemInHand);
		}



		//Pioche Hades
		if(SpecialTools.isCutCleanPickaxe(itemInHand)) {
			switch (block.getType()) {
			case GOLD_ORE:
			case DEEPSLATE_GOLD_ORE:
				drop(e, Material.GOLD_INGOT, 1, loc, true);
				break;
			case IRON_ORE:
			case DEEPSLATE_IRON_ORE:
				drop(e, Material.IRON_INGOT, 0.7, loc, true);
				break;
			case ANCIENT_DEBRIS:
				drop(e, Material.NETHERITE_SCRAP, 2, loc, false);
				break;
			case NETHER_GOLD_ORE:
				drop(e, Material.GOLD_INGOT, 1, loc, false);
				break;
			case COPPER_ORE:
			case DEEPSLATE_COPPER_ORE:
				drop(e, Material.COPPER_INGOT, 0.7, loc, true);
				break;
			default:
				break;
			}
		}
	}


	private void drop(BlockBreakEvent e, Material newDrop, double xp, Location loc, boolean fortuneMultiply) {
		drop(e, newDrop, xp, loc, fortuneMultiply, 1);
	}
	
	private void drop(BlockBreakEvent e, Material newDrop, double xp, Location loc, boolean fortuneMultiply, int amountToDrop) {
		Player player = e.getPlayer();
		ItemStack toDrop = new ItemStack(newDrop);
		int expToDrop = 0;

		//Centre du bloc : 
		loc.add(0.5, 0.5, 0.5);

		if(fortuneMultiply) {
			int fortuneLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
			amountToDrop = getMultiplier(fortuneLevel);
			toDrop.setAmount(amountToDrop);
		}

		for (int i = 0; i < amountToDrop ; i++) {
			if(xp >= 1) {
				expToDrop += xp;
			}else {
				if(Math.random() <= xp) {
					expToDrop++;
				}
			}
		}

		e.setDropItems(false);
		e.setExpToDrop(expToDrop);
		player.getWorld().dropItem(loc, toDrop);
		player.getWorld().spawnParticle(Particle.FLAME, loc, 10 ,0.3, 0.3, 0.3, 0.02);
	}

	private int getMultiplier(int fortunelevel) {
		Random rand = new Random();
		int alz2 = rand.nextInt(100 + 1);
		switch(fortunelevel) {

		case(3):
			if(alz2 <= 20 && alz2 >= 1) {
				return 2;
			}else if(alz2 <= 40 && alz2 > 20) {
				return 3;
			}else if(alz2 <= 60 && alz2 > 40) {
				return 4;
			}else {
				return 1;
			}
		case(2):
			if(alz2 <= 25 && alz2 >= 1) {
				return 2;
			}else if(alz2 <= 50 && alz2 > 25) {
				return 3;
			}else {
				return 1;
			}
		case(1):
			if(alz2 <= 33 && alz2 >= 1) {
				return 2;
			}else {
				return 1;
			}
		}

		return 1;
	}


	private void dropTree(final Block block, final ItemStack item) {
		List<Block> blocks = new ArrayList<>();
		//List<Block> leaves = new ArrayList<>();

		for (Block _block = block; !_block.isEmpty(); _block = _block.getRelative(BlockFace.UP)) {

			for (int k = -1; k <= 1; k++) {
				for (int j = -1; j <= 1; j++) {
					for (int l = -1; l <= 1; l++) {
						final Block relativeBlock = _block.getRelative(j, k, l);

						if (isLog(relativeBlock.getType()))
							blocks.add(relativeBlock);
					}
				}
			}
		}
		
		ItemMeta meta = item.getItemMeta();
		Damageable itemDmg = (Damageable) meta;

		int count = 0;
		for (final Block b : blocks) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalCorePlugin.getInstance(), new Runnable() {
				@Override
				public void run() {
					b.breakNaturally(item);
				}
			}, ++count);
		}
		
		
		int damage = blocks.size() / (meta.getEnchantLevel(Enchantment.DURABILITY)+1);
				
		itemDmg.setDamage(itemDmg.getDamage() + damage);

		item.setItemMeta(meta);
	}

//	private static boolean isLeaf(Material material) {
//		switch (material) {
//		case ACACIA_LEAVES:
//		case BIRCH_LEAVES:
//		case DARK_OAK_LEAVES:
//		case JUNGLE_LEAVES:
//		case OAK_LEAVES:
//		case SPRUCE_LEAVES:
//			return true;
//		default:
//			return false;
//		}
//	}

	private static boolean isLog(Material material) {
		switch (material) {
		case ACACIA_LOG:
		case BIRCH_LOG:
		case DARK_OAK_LOG:
		case JUNGLE_LOG:
		case OAK_LOG:
		case SPRUCE_LOG:
		case WARPED_STEM:
		case CRIMSON_STEM:
			return true;
		default:
			return false;
		}
	}


}

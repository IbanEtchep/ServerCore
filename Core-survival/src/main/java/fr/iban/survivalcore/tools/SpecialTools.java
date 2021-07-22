package fr.iban.survivalcore.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpecialTools {

	public static final Set<Material> canBreakWith3x3Pickaxe = EnumSet.of(
			Material.COBBLESTONE, Material.STONE, Material.DIRT, Material.NETHERRACK, Material.GRASS_BLOCK, Material.DIORITE,
			Material.ANDESITE, Material.GRANITE, Material.IRON_ORE, Material.COAL_ORE, Material.GOLD_ORE, Material.LAPIS_ORE,
			Material.REDSTONE_ORE, Material.GRAVEL, Material.BASALT, Material.BLACKSTONE, Material.END_STONE, Material.DIRT,
			Material.BASALT, Material.MAGMA_BLOCK, Material.DEEPSLATE, Material.DEEPSLATE_COAL_ORE, Material.COBBLED_DEEPSLATE,
			Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_LAPIS_ORE,
			Material.DEEPSLATE_REDSTONE_ORE, Material.COPPER_ORE, Material.TUFF
			);
	
	public static final Set<Material> canBreakWith3x3Shovel = EnumSet.of(
			Material.GRASS_BLOCK, Material.DIRT, Material.SAND, Material.RED_SAND, Material.GRAVEL
			);

	public static Map<UUID, BlockFace> faces = new HashMap<>();


	public static ItemStack get3x3Pickaxe() {
		ItemStack hades = new ItemStack(Material.NETHERITE_PICKAXE);

		ItemMeta cus = hades.getItemMeta();
		cus.setDisplayName("§9§lMarteau du Dieu");
		cus.setLore(Arrays.asList("§3|| §bMine du §93x3§3 ||","§c§l[ITEM LEGENDAIRE]"));
		hades.setItemMeta(cus);
		return hades;
	}

	public static ItemStack getCutCleanPickaxe() {
		ItemStack hades = new ItemStack(Material.NETHERITE_PICKAXE);

		ItemMeta cus = hades.getItemMeta();
		cus.setDisplayName("§e§lPioche d'Hades");
		cus.setLore(Arrays.asList("§e§l-------------","§e§lCette pioche fait fondre","§e§lles minerais que vous minez.","§e§l-------------","§c§l[ITEM LEGENDAIRE]"));
		hades.setItemMeta(cus);
		return hades;	
	}
	
	public static ItemStack get3x3Shovel() {
		ItemStack hades = new ItemStack(Material.NETHERITE_SHOVEL);

		ItemMeta cus = hades.getItemMeta();
		cus.setDisplayName("§e§lPelle du Dieu");
		cus.setLore(Arrays.asList("§e§l-------------","§e§lCette pelle permet de","§e§lminer une zone de 3x3.","§e§l-------------","§c§l[ITEM LEGENDAIRE]"));
		hades.setItemMeta(cus);
		return hades;
	}

	public static ItemStack getLumberjackAxe() {
		ItemStack hades = new ItemStack(Material.NETHERITE_AXE);

		ItemMeta cus = hades.getItemMeta();
		cus.setDisplayName("§e§lHache du Bûcheron");
		cus.setLore(Arrays.asList("§e§l-------------","§e§lCette hache détruit entièrement l'arbre","§e§lque vous cassez.","§e§l-------------","§c§l[ITEM LEGENDAIRE]"));
		hades.setItemMeta(cus);
		return hades;	
	}
	
	public static ItemStack getFarmerHoe() {
		ItemStack hades = new ItemStack(Material.NETHERITE_HOE);

		ItemMeta cus = hades.getItemMeta();
		cus.setDisplayName("§e§lHoue de Déméter");
		cus.setLore(Arrays.asList("§e§l-------------","§e§lCette houe permet de replanter","§e§lautomatiquement vos récoltes.","§e§l-------------","§c§l[ITEM LEGENDAIRE]"));
		hades.setItemMeta(cus);
		return hades;	
	}
	
	public static ItemStack getXpSword() {
		ItemStack xpsword = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta cus = xpsword.getItemMeta();
		cus.setDisplayName("§e§lEpée d'Héphaïstos");
		cus.setLore(Arrays.asList("§e§l-------------","§e§lCette épée, forgée par Héphaïstos en personne","§e§lpermet de récolter §c§l50%§e§l d'XP supplémentaire.","§e§l-------------","§c§l[ITEM LEGENDAIRE]"));
		xpsword.setItemMeta(cus);
		return xpsword;	
	}
	
	public static boolean isLumberjackAxe(ItemStack item) {
		return item.getType() == Material.NETHERITE_AXE && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§e§lCette hache détruit entièrement l'arbre");
	}

	public static boolean is3x3Shovel(ItemStack item) {
		return item.getType() == Material.NETHERITE_SHOVEL && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§e§lCette pelle permet de");
	}

	public static boolean isCutCleanPickaxe(ItemStack item) {
		return item.getType() == Material.NETHERITE_PICKAXE && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§e§lCette pioche fait fondre");
	}

	public static boolean isReplantHoue(ItemStack item) {
		return item.getType() == Material.NETHERITE_HOE && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§e§lCette houe permet de replanter");
	}
	
	public static boolean is3x3Pickaxe(ItemStack item) {
		return item.getType() == Material.NETHERITE_PICKAXE && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§3|| §bMine du §93x3§3 ||");
	}
	
	public static boolean isXpBoostSword(ItemStack item) {
		return item.getType() == Material.NETHERITE_SWORD && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§e§lpermet de récolter §c§l50%§e§l d'XP supplémentaire.");
	}


	public static List<Block> getSurroundingBlocksShovel(Player player, Block targetBlock) {
		return getSurroundingBlocks(faces.get(player.getUniqueId()), targetBlock).stream().filter(block -> canBreakWith3x3Shovel.contains(block.getType())).collect(Collectors.toList());
	}
	
	public static List<Block> getSurroundingBlocksPickaxe(Player player, Block targetBlock) {
		return getSurroundingBlocks(faces.get(player.getUniqueId()), targetBlock).stream().filter(block -> canBreakWith3x3Pickaxe.contains(block.getType())).collect(Collectors.toList());
	}

	private static List<Block> getSurroundingBlocks(BlockFace blockFace, Block targetBlock) {
		ArrayList<Block> blocks = new ArrayList<>();
		World world = targetBlock.getWorld();

		int x, y, z;
		x = targetBlock.getX();
		y = targetBlock.getY();
		z = targetBlock.getZ();

		switch(blockFace) {
		case UP:
		case DOWN:
			blocks.add(world.getBlockAt(x+1, y, z));
			blocks.add(world.getBlockAt(x-1, y, z));
			blocks.add(world.getBlockAt(x, y, z+1));
			blocks.add(world.getBlockAt(x, y, z-1));
			blocks.add(world.getBlockAt(x+1, y, z+1));
			blocks.add(world.getBlockAt(x-1, y, z-1));
			blocks.add(world.getBlockAt(x+1, y, z-1));
			blocks.add(world.getBlockAt(x-1, y, z+1));
			break;
		case EAST:
		case WEST:
			blocks.add(world.getBlockAt(x, y, z+1));
			blocks.add(world.getBlockAt(x, y, z-1));
			blocks.add(world.getBlockAt(x, y+1, z));
			blocks.add(world.getBlockAt(x, y-1, z));
			blocks.add(world.getBlockAt(x, y+1, z+1));
			blocks.add(world.getBlockAt(x, y-1, z-1));
			blocks.add(world.getBlockAt(x, y-1, z+1));
			blocks.add(world.getBlockAt(x, y+1, z-1));
			break;
		case NORTH:
		case SOUTH:
			blocks.add(world.getBlockAt(x+1, y, z));
			blocks.add(world.getBlockAt(x-1, y, z));
			blocks.add(world.getBlockAt(x, y+1, z));
			blocks.add(world.getBlockAt(x, y-1, z));
			blocks.add(world.getBlockAt(x+1, y+1, z));
			blocks.add(world.getBlockAt(x-1, y-1, z));
			blocks.add(world.getBlockAt(x+1, y-1, z));
			blocks.add(world.getBlockAt(x-1, y+1, z));
			break;
		default:
			break;
		}
		
		blocks.removeAll(Collections.singleton(null));
		return blocks;
	}

}
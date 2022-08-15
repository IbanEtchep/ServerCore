package fr.iban.survivalcore.listeners;

import java.util.EnumSet;
import java.util.Set;

import fr.iban.lands.LandManager;
import fr.iban.lands.LandsPlugin;
import fr.iban.lands.enums.Flag;
import fr.iban.lands.objects.Land;
import fr.iban.survivalcore.event.UseReplantHoeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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

    private final SurvivalCorePlugin plugin;

    private final Set<Material> cropList = EnumSet.of(
            Material.WHEAT, Material.POTATOES, Material.CARROTS,
            Material.BEETROOTS, Material.NETHER_WART);

    public InteractListeners(SurvivalCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        BlockFace bf = e.getBlockFace();
        if (bf == BlockFace.SELF) return;
        SpecialTools.faces.put(e.getPlayer().getUniqueId(), bf);
        LandManager landManager = LandsPlugin.getInstance().getLandManager();

        ItemStack item = e.getItem();

        if (e.getItem() != null && e.getClickedBlock() != null) {
            if (e.getItem().getType() == Material.NETHERITE_HOE && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
                if (SpecialTools.is3x3ReplantHoue(e.getItem())) {
                    Block block = e.getClickedBlock();
					handleBlockReplant(item, block, player, landManager);
					for(Block b : SpecialTools.getSurroundingBlocks(BlockFace.UP, block)) {
						handleBlockReplant(item, b, player, landManager);
					}
                    return;
                }
                if (SpecialTools.isReplantHoue(e.getItem())) {
                    Block block = e.getClickedBlock();
                    handleBlockReplant(item, block, player, landManager);
                }
            }

        }
    }

	private void handleBlockReplant(ItemStack houe,Block block, Player player, LandManager landManager) {
		Material material = block.getType();
		if (cropList.contains(material)) {
			BlockData bd = block.getBlockData();
			Ageable age = (Ageable) bd;

			if (age.getAge() == age.getMaximumAge()) {
				Land land = landManager.getLandAt(block.getChunk());
				if (!land.isBypassing(player, fr.iban.lands.enums.Action.BLOCK_BREAK) && !land.hasFlag(Flag.AUTO_REPLANT)) {
					return;
				}

				Bukkit.getPluginManager().callEvent(new UseReplantHoeEvent(player, block));
				block.breakNaturally();
				ItemMeta meta = houe.getItemMeta();
				Damageable itemDmg = (Damageable) meta;

				double rand = Math.random() * 100;
				double breakChance = (100.0 / (meta.getEnchantLevel(Enchantment.DURABILITY) + 1));
				if (rand <= breakChance) {

					itemDmg.setDamage(itemDmg.getDamage() + 1);

					houe.setItemMeta(meta);
				}

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
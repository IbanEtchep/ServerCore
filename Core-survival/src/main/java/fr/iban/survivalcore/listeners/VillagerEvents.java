package fr.iban.survivalcore.listeners;

import com.google.common.collect.Lists;
import fr.iban.bukkitcore.utils.ItemBuilder;
import fr.iban.survivalcore.SurvivalCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class VillagerEvents implements Listener {

	private SurvivalCorePlugin plugin;

	public VillagerEvents(SurvivalCorePlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onVillagerInteract(final PlayerInteractAtEntityEvent e){
		if (!(e.getRightClicked() instanceof Villager)) return;

		Villager villager = (Villager) e.getRightClicked();

		List<MerchantRecipe> recipes = Lists.newArrayList(villager.getRecipes());

		Iterator<MerchantRecipe> recipeIterator = recipes.iterator();
		while(recipeIterator.hasNext()) {

			MerchantRecipe recipe = recipeIterator.next();

			if (recipe.getResult().getType().equals(Material.ENCHANTED_BOOK)) {
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) recipe.getResult().getItemMeta();

				if (meta.hasStoredEnchant(Enchantment.MENDING)) {
					recipe.setMaxUses(1);
					int initialPrice = plugin.getConfig().getInt("mending-trade-price");
					ItemStack diams = new ItemStack(Material.valueOf(plugin.getConfig().getString("mending-trade-material")), initialPrice);
					recipe.setIngredients(Arrays.asList(diams));
					recipe.setIgnoreDiscounts(true);
				}
			}
		}

		villager.setRecipes(recipes);
	}

}

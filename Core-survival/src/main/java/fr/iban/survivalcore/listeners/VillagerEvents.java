package fr.iban.survivalcore.listeners;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import com.google.common.collect.Lists;

public class VillagerEvents implements Listener {


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
					recipe.setIngredients(Arrays.asList(new ItemStack(Material.EMERALD, 64)));
				}
			}
		}

		villager.setRecipes(recipes);
	}

}

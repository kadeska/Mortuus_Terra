/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mortuusterra.MortuusTerraCore;

public class RecipeManager {
	
	private MortuusTerraCore main;
	public RecipeManager(MortuusTerraCore main) {
		this.main = main;
		setGeneratorRecipe();
	}
	
	private final Plugin plugin = JavaPlugin.getPlugin(MortuusTerraCore.class);
	
	// Added this to remove deprecated code.
	//private final NamespacedKey cellTowerKey = new NamespacedKey(main, "celltower");
	private final NamespacedKey genKey = new NamespacedKey(plugin, "generator");

	// To prevent repition :)
	public static final String GENERATOR_NAME = ChatColor.RED + "" + ChatColor.BOLD  + "Generator";
	
	private static ItemStack generator;
	
	
	// Returns the generator item DO NOT MODIFY THIS
	public static ItemStack getGenerator() {
		return generator;
	}

	// Generator
	private void setGeneratorRecipe() {
		generator = new ItemStack(Material.FURNACE);

		ItemMeta generatorMeta = generator.getItemMeta();
		generatorMeta.setDisplayName(GENERATOR_NAME);
		generator.setItemMeta(generatorMeta);

		ShapedRecipe generatorRecipe = new ShapedRecipe(genKey, generator);


		generatorRecipe.shape("iii", "ici", "iii");
		generatorRecipe.setIngredient('i', Material.IRON_INGOT);
		generatorRecipe.setIngredient('c', Material.COAL);

		Bukkit.getServer().addRecipe(generatorRecipe);
	}
}

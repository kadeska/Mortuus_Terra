//TODO not having custom zombies anymore so delete this
package com.mortuusterra.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mortuusterra.util.NMSUtil;

public class CustomZombieEgg {

	private String name = "The Flash";
	
	private static ItemStack egg;
	
	public CustomZombieEgg() {
		egg = new ItemStack(Material.MONSTER_EGG, 1, (short) 54);
		ItemMeta eggMeta = egg.getItemMeta();
		eggMeta.setDisplayName(name);
		egg.setItemMeta(eggMeta);
		NMSUtil.addGlow(egg);
	}
	
	public static ItemStack getItem() {
		return egg;
	}
	
}

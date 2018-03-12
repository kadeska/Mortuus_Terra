/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.objects;

import org.bukkit.Material;

public class SupplyDropContent {
	
	private Material itemMaterial;
	private int itemChance;
	private int itemAmount;
	
	public SupplyDropContent(Material itemMaterial, int itemChance, int itemAmount) {
		this.itemAmount = itemAmount;
		this.itemChance = itemChance;
		this.itemMaterial = itemMaterial;
	}
	
	public Material getItemMaterial() {
		return itemMaterial;
	}
	
	public int getItemAmount() {
		return itemAmount;
	}
	
	public int getItemChance() {
		return itemChance;
	}
	

}

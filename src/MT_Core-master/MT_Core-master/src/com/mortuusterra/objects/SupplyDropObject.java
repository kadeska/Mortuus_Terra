/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.objects;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.mortuusterra.MortuusTerraCore;

public class SupplyDropObject {

	private final MortuusTerraCore main = JavaPlugin.getPlugin(MortuusTerraCore.class);

	private Location dropLocation;
	private Inventory dropInventory;
	private boolean isLooted;
	private Chest dropChest;

	public SupplyDropObject(Location dropLocation, Chest dropChest, Inventory dropInventory) {
		this.isLooted = false;
		this.dropChest = dropChest;
		this.dropInventory = dropInventory;
		this.dropLocation = dropLocation;

		if (dropInventory != null) {
			if (main.getSupplyDropManager().isEmpty(dropLocation))
				fillChest();
		}

	}

	public void fillChest() {
		main.getSupplyDropManager().fillSupplyDropContent(dropInventory);
	}

	public Inventory getDropInventory() {
		return dropInventory;
	}

	public void setDropChest(Chest dropChest) {
		this.dropChest = dropChest;
	}

	public Chest getDropChest() {
		return dropChest;
	}

	public Location getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(Location supplyDropLocation) {
		this.dropLocation = supplyDropLocation;
	}

	public boolean isLooted() {
		return isLooted;
	}

	public void setLooted(boolean isLooted) {
		this.isLooted = isLooted;
	}

}

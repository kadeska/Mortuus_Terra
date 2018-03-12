package com.mortuusterra.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.objects.SupplyDrop;

public class SupplyDropListener implements Listener {

	private MortuusTerraCore core;

	public SupplyDropListener(MortuusTerraCore core) {
		this.core = core;
	}

	@EventHandler
	public void onSupplyDropBreak(BlockBreakEvent e) {
		SupplyDrop sd = core.getSupplyDropManager().getSupplyDropByLocation(e.getBlock().getLocation());

		if (sd == null)
			return;

		sd.setLooted(true);
		core.getSupplyDropManager().removeSupplyDrop(e.getBlock().getLocation());
	}

	@EventHandler
	public void onSupplyDropInventoryClose(InventoryCloseEvent e) {
		Location loc = e.getInventory().getLocation();
		Block b = loc.getBlock();

		if (b.getType() == Material.CHEST) {

			if (core.getSupplyDropManager().isSupplyDrop(b.getLocation())) {
				if (core.getSupplyDropManager().isEmpty(loc)) {
					core.getSupplyDropManager().removeSupplyDrop(loc);
					
					b.setType(Material.AIR);
					loc.getWorld().playSound(loc, Sound.BLOCK_WOOD_BREAK, 1, 1);
				}

			}
		}
	}
}

package com.mortuusterra.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mortuusterra.MortuusTerraMain;

public class MTGeneratorListener implements Listener {

	private MortuusTerraMain main;

	public MTGeneratorListener(MortuusTerraMain m) {
		this.main = m;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		// e.getPlayer().sendMessage("test");
		Block b = e.getBlock();
		if (main.getGenBuild().isStage_one()) {
			if (b.getType().equals(Material.SMOOTH_BRICK)) {
				
			}

		} else if (main.getGenBuild().isStage_two()) {
			if(b.getType().equals(Material.IRON_FENCE)) {
				
			}

		} else if (main.getGenBuild().isStage_three()) {
			if(b.getType().equals(Material.STONE_SLAB2)) {
				
			}

		} else if (main.getGenBuild().isStage_four()) {
			if(b.getType().equals(Material.FURNACE)) {
				
			}

		} else if (main.getGenBuild().isStage_five()) {
			if(b.getType().equals(Material.REDSTONE_LAMP_OFF)) {
				
			}

		}
	}

}

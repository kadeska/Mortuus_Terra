package com.mortuusterra.listeners;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.managers.GeneratorManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class GeneratorListener implements Listener {

	private MortuusTerraMain main;

	public GeneratorListener(MortuusTerraMain m) {
		this.main = m;
	}
//TODO change these so they aren't depreciated before release even if it means we have to change the format of generators
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType().equals(Material.LEGACY_REDSTONE_LAMP_OFF)) {
			Block lamp = e.getBlock();
			if (lamp.getRelative(BlockFace.DOWN).getType().equals(Material.FURNACE)) {
				Block furnace = lamp.getRelative(BlockFace.DOWN);
				if (furnace.getRelative(BlockFace.DOWN).getType().equals(Material.LEGACY_SMOOTH_BRICK)) {
					Block centerGround = furnace.getRelative(BlockFace.DOWN);
					if (lamp.getRelative(BlockFace.UP).getType().equals(Material.STONE_BRICK_SLAB)) {
						if (main.getRad().containsGenerator(e.getBlock().getRelative(BlockFace.DOWN).getLocation())) {
							e.getPlayer().sendMessage("There is already a generator at this location!");
							return;
						}
						if (main.getRad().isInGeneratorRange(furnace)) {
							e.getPlayer().sendMessage(ChatColor.RED + "!WARNING! " + ChatColor.BLUE
									+ "You are building this generator in range of another generator.");
							e.getPlayer().sendMessage(ChatColor.BLUE
									+ "It is recommended to build a new generator out of range of another generator");
						}
						Block centerTop = lamp.getRelative(BlockFace.UP);
						BlockFace[] squareFaces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH,
								BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
								BlockFace.SOUTH_WEST };
						BlockFace[] ironFenceFaces = { BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
								BlockFace.SOUTH_WEST };
						for (BlockFace f : squareFaces) {
							if (!centerGround.getRelative(f).getType().equals(Material.LEGACY_SMOOTH_BRICK)
									|| !centerTop.getRelative(f).getType().equals(Material.STONE_BRICK_SLAB)) {
								e.getPlayer().sendMessage("Generator is not built correctly!");
								return;
							}
						}
						for (BlockFace f : ironFenceFaces) {
							if (!furnace.getRelative(f).getType().equals(Material.IRON_BARS)
									|| !lamp.getRelative(f).getType().equals(Material.IRON_BARS)) {
								e.getPlayer().sendMessage("Generator is not built correctly!");
								return;
							}
						}
						// generator must be built correctly
						e.getPlayer().sendMessage("Generator is built correctly");
						e.getPlayer().sendMessage("Generator is starting ...");
						
						GeneratorManager gen = new GeneratorManager(e.getPlayer(), furnace.getLocation(), main);
						main.getRad().addGenerator(gen);
						gen.startGenerator();
					}
				}
			}
		}
	}

	@EventHandler
	public void furnaceBreak(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.FURNACE)) {
			if (main.getRad().containsGenerator(e.getBlock().getLocation())) {
				if (main.getRad().getGenerator(e.getBlock().getLocation()).busy) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("This generator is busy, please wait!");
					return;
				}
				main.getRad().getGenerator(e.getBlock().getLocation()).stopGenerator();
			}
		}
	}

}

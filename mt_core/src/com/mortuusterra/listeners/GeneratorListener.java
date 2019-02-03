package com.mortuusterra.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.managers.GeneratorManager;

public class GeneratorListener implements Listener {

	private MortuusTerraMain main;

	public GeneratorListener(MortuusTerraMain m) {
		this.main = m;
	}

	/**
	// this is just for testing the supplydrops
	@SuppressWarnings("deprecation")
	@EventHandler
	public void forceSupplyDrop(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) {
			return;
		}
		if (e.getPlayer().getItemInHand().getType().equals(Material.STICK)) {
			new MTSupplyDrop(main.getServer().getWorld("world"), main);
		}

	}
	**/

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType().equals(Material.REDSTONE_LAMP_OFF)) {
			Block lamp = e.getBlock();
			if (lamp.getRelative(BlockFace.DOWN).getType().equals(Material.FURNACE)) {
				Block furnace = lamp.getRelative(BlockFace.DOWN);
				if (furnace.getRelative(BlockFace.DOWN).getType().equals(Material.SMOOTH_BRICK)) {
					Block centerGround = furnace.getRelative(BlockFace.DOWN);
					if (lamp.getRelative(BlockFace.UP).getType().equals(Material.STEP)) {
						if (main.getRad().containsGenerator(e.getBlock().getRelative(BlockFace.DOWN).getLocation())) {
							e.getPlayer().sendMessage("There is already a generator at this location!");
							return;
						}
						if (main.getRad().isInGeneratorRange(furnace)) {
							e.getPlayer().sendMessage(ChatColor.RED + "!WARNING! " + ChatColor.BLUE
									+ "You are building this generator in range of another generator.");
							e.getPlayer().sendMessage(ChatColor.BLUE
									+ "It is recomended to build a new generator out of range of another generator");
						}
						Block centerTop = lamp.getRelative(BlockFace.UP);
						BlockFace[] squareFaces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH,
								BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
								BlockFace.SOUTH_WEST };
						BlockFace[] ironFenceFaces = { BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
								BlockFace.SOUTH_WEST };
						for (BlockFace f : squareFaces) {
							if (!centerGround.getRelative(f).getType().equals(Material.SMOOTH_BRICK)
									|| !centerTop.getRelative(f).getType().equals(Material.STEP)) {
								e.getPlayer().sendMessage("Generator is not built corectly!");
								return;
							}
						}
						for (BlockFace f : ironFenceFaces) {
							if (!furnace.getRelative(f).getType().equals(Material.IRON_FENCE)
									|| !lamp.getRelative(f).getType().equals(Material.IRON_FENCE)) {
								e.getPlayer().sendMessage("Generator is not built corectly!");
								return;
							}
						}
						// generator must be built correctly
						e.getPlayer().sendMessage("Generator is built corectly");
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
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) {
			return;
		}
		if (!e.getClickedBlock().getType().equals(Material.FURNACE)) {
			return;
		}
		if (main.getRad().containsGenerator(e.getClickedBlock().getLocation())) {
			if (!main.getRad().canPlayerInteractGenerator(main.getRad().getGenerator(e.getClickedBlock().getLocation()),
					e.getPlayer())) {
				e.getPlayer().sendMessage("This is not your generator, you can not interact with it.");
			}
		}
	}

	@EventHandler
	public void furnacBreak(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.FURNACE)) {
			if (main.getRad().containsGenerator(e.getBlock().getLocation())) {
				if (main.getRad().getGenerator(e.getBlock().getLocation()).isBusy()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("This generator is busy, please wait!");
					return;
				}
				main.getRad().getGenerator(e.getBlock().getLocation()).stopGenerator();
			}
		}
	}

	@EventHandler
	public void lampBreak(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.REDSTONE_LAMP_ON)
				|| e.getBlock().getType().equals(Material.REDSTONE_LAMP_OFF)) {
			if (main.getRad().containsGenerator(e.getBlock().getRelative(BlockFace.DOWN).getLocation())) {
				if (main.getRad().getGenerator(e.getBlock().getRelative(BlockFace.DOWN).getLocation()).isBusy()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("This generator is busy, please wait!");
					return;
				}
				main.getRad().getGenerator(e.getBlock().getRelative(BlockFace.DOWN).getLocation()).stopGenerator();
			}
		}
	}
}

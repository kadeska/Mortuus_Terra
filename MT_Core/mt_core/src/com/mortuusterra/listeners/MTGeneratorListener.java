package com.mortuusterra.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.managers.MTGenerator;

public class MTGeneratorListener implements Listener {

	private MortuusTerraMain main;

	public MTGeneratorListener(MortuusTerraMain m) {
		this.main = m;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType().equals(Material.REDSTONE_LAMP_OFF)) {
			Block lamp = e.getBlock();
			if (lamp.getRelative(BlockFace.DOWN).getType().equals(Material.FURNACE)) {
				Block funace = lamp.getRelative(BlockFace.DOWN);
				if (funace.getRelative(BlockFace.DOWN).getType().equals(Material.SMOOTH_BRICK)) {
					Block centerGround = funace.getRelative(BlockFace.DOWN);
					if (lamp.getRelative(BlockFace.UP).getType().equals(Material.STONE_SLAB2)) {
						Block centerTop = lamp.getRelative(BlockFace.UP);
						BlockFace[] squareFaces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH,
								BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
								BlockFace.SOUTH_WEST };
						BlockFace[] ironFenceFaces = { BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
								BlockFace.SOUTH_WEST };
						for (BlockFace f : squareFaces) {
							if (!centerGround.getRelative(f).getType().equals(Material.SMOOTH_BRICK)
									|| !centerTop.getRelative(f).getType().equals(Material.STONE_SLAB2)) {
								e.getPlayer().sendMessage("Generator is not built corectly!");
								return;
							}
								main.getGenBuild().addBlock(centerGround.getRelative(f));
								main.getGenBuild().addBlock(centerTop.getRelative(f));
							
						}
						for (BlockFace f : ironFenceFaces) {
							if (!funace.getRelative(f).getType().equals(Material.IRON_FENCE)
									|| !lamp.getRelative(f).getType().equals(Material.IRON_FENCE)) {
								e.getPlayer().sendMessage("Generator is not built corectly!");
								return;
							}
							main.getGenBuild().addBlock(funace.getRelative(f));
							main.getGenBuild().addBlock(lamp.getRelative(f));
						}
						//generator must be built correctly
						main.getGenBuild().addBlock(lamp);
						main.getGenBuild().addBlock(funace);
						e.getPlayer().sendMessage("Generator is built corectly");
						e.getPlayer().sendMessage("Generator is starting ...");
						MTGenerator gen = new MTGenerator(lamp.getLocation(), main.getGenBuild().getBlocks());
						main.getRad().addGenerator(gen);
						main.getGenBuild().startGenerator(e.getPlayer(), gen);
					}
				}
			}
		}
	}

}

/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.misc;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.utils.others.WorldUtils;

public class FalloutShelter {

	private MortuusTerraCore core;
	private Location shelterLocation;

	public FalloutShelter(MortuusTerraCore core, Location shelterLocation) {
		this.core = core;
		this.shelterLocation = shelterLocation;
	}

	public Location getShelterLocation() {
		return shelterLocation;
	}

	public void setShelterLocation(Location shelterLocation) {
		this.shelterLocation = shelterLocation;
	}

	@SuppressWarnings("deprecation")
	public void generateFalloutShelter() {
		// Ground level.
		Block center = shelterLocation.getBlock();

		// 1 Block above ground.
		Block center1 = center.getRelative(BlockFace.UP);
		Block hay = center.getRelative(BlockFace.EAST);
		Block hay1 = center.getRelative(BlockFace.NORTH_EAST);
		Block carpet = center1.getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.NORTH);
		Block sepWall11 = center1.getRelative(BlockFace.SOUTH_WEST);
		Block sepWall12 = sepWall11.getRelative(BlockFace.WEST);
		Block bottomDoor = sepWall12.getRelative(BlockFace.SOUTH_WEST);

		Block chestBlock = center1.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST);
		Block furnace = chestBlock.getRelative(BlockFace.NORTH);
		Block shelf1 = furnace.getRelative(BlockFace.NORTH);

		// 2 Blocks above ground.
		Block center2 = center1.getRelative(BlockFace.UP);
		Block sepWall21 = sepWall11.getRelative(BlockFace.UP);
		Block sepWall22 = sepWall12.getRelative(BlockFace.UP);
		Block enderChest = center2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST);
		Block shelf2 = shelf1.getRelative(BlockFace.UP);

		// 3 Blocks above ground.
		Block center3 = center2.getRelative(BlockFace.UP);
		Block sepWall31 = sepWall21.getRelative(BlockFace.UP);
		Block sepWall32 = sepWall22.getRelative(BlockFace.UP);

		List<Block> groundBlocks = WorldUtils.getNearbyBlocks2D(center, 3);
		List<Block> walls1 = WorldUtils.getNearbyBlocks2D(center1, 3);
		List<Block> walls2 = WorldUtils.getNearbyBlocks2D(center2, 3);
		List<Block> walls3 = WorldUtils.getNearbyBlocks2D(center3, 3);
		List<Block> roofBlocks = WorldUtils.getNearbyBlocks2D(center3.getRelative(BlockFace.UP), 3);

		setBedrock(groundBlocks);
		setBedrock(walls1);
		setBedrock(walls2);
		setBedrock(walls3);
		setBedrock(roofBlocks);

		for (Block b : WorldUtils.getNearbyBlocks2D(center1, 2))
			b.setType(Material.AIR);

		for (Block b : WorldUtils.getNearbyBlocks2D(center2, 2))
			b.setType(Material.AIR);

		for (Block b : WorldUtils.getNearbyBlocks2D(center3, 2))
			b.setType(Material.AIR);
		
		sepWall11.setType(Material.BEDROCK);
		sepWall12.setType(Material.BEDROCK);
		sepWall21.setType(Material.BEDROCK);
		sepWall22.setType(Material.BEDROCK);
		sepWall31.setType(Material.BEDROCK);
		sepWall32.setType(Material.BEDROCK);
		
		bottomDoor.setTypeIdAndData(71, (byte) 0, false);
		bottomDoor.getRelative(BlockFace.UP).setTypeIdAndData(71, (byte) 8, true);
		carpet.setType(Material.CARPET);
		hay.setType(Material.HAY_BLOCK);
		hay1.setType(Material.HAY_BLOCK);
		enderChest.setType(Material.ENDER_CHEST);
		chestBlock.setType(Material.CHEST);
		furnace.setType(Material.FURNACE);
		shelf1.setType(Material.BOOKSHELF);
		shelf2.setType(Material.BOOKSHELF);
		
		chestBlock.setData((byte) 5);
		enderChest.setData((byte) 5);
		furnace.setData((byte) 5);
		
		Chest chest = (Chest) chestBlock.getState();
		core.getSupplyDropManager().fillSupplyDropContent(chest.getBlockInventory());
		
		
	}

	private void setBedrock(List<Block> blocks) {
		for (Block b : blocks)
			b.setType(Material.BEDROCK);
	}

}

package com.mortuusterra.managers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.mortuusterra.MortuusTerraCore;

public class GeneratorManager {
	private MortuusTerraCore main;
	public GeneratorManager(MortuusTerraCore main) {
		this.main = main;
	}

	public boolean isGeneratorBuildCorrect(Block furnace) {
		BlockFace[] squareFaces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH,
				BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST };
		BlockFace[] ironFenceFaces = { BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
				BlockFace.SOUTH_WEST };
		Block lamp = furnace.getRelative(BlockFace.UP);


		for (BlockFace face : squareFaces) {
			if (furnace.getRelative(BlockFace.DOWN).getRelative(face).getType() != Material.SMOOTH_BRICK
					|| lamp.getRelative(BlockFace.UP).getRelative(face).getType() != Material.STEP)
				return false;
		}

		for (BlockFace face : ironFenceFaces) {
			if (furnace.getRelative(face).getType() != Material.IRON_FENCE
					|| lamp.getRelative(face).getType() != Material.IRON_FENCE)
				return false;
		}

		return lamp.getType() == Material.REDSTONE_LAMP_OFF;
	}

}

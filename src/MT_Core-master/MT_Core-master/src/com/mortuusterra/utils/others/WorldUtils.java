/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.utils.others;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class WorldUtils {

	/**
	 * Util method to return all blocks in a cubic area around the block
	 * 
	 * @param generator
	 *            the block in the center.
	 * @param radius
	 *            The size from a corner to the center
	 * @return List of blocks around the gen
	 */
	public static List<Block> getNearbyBlocks(Block generator, int radius) {

		List<Block> blocks = new ArrayList<>();

		for (int x = -(radius); x <= radius; x++) {
			for (int y = -(radius); y <= radius; y++) {
				for (int z = -(radius); z <= radius; z++) {
					blocks.add(generator.getRelative(x, y, z));
				}
			}
		}
		return blocks;
	}

	/**
	 * Method to return all blocks in a 2-dimensional space (x,z)
	 * 
	 * @param center
	 *            The center block.
	 * @param radius
	 *            The radius.
	 * @return A list containing all blocks.
	 */
	public static List<Block> getNearbyBlocks2D(Block center, int radius) {

		List<Block> blocks = new ArrayList<>();

		for (int x = center.getX() - radius; x <= center.getX() + radius; x++) {
			for (int z = center.getZ() - radius; z <= center.getZ() + radius; z++) {
				Location loc = new Location(center.getWorld(), x, center.getY(), z);

				blocks.add(loc.getBlock());
			}
		}
		return blocks;
	}
}

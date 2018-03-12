/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.managers;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.objects.Geck;
import com.mortuusterra.objects.MTPlayer;

public class GeckManager {
	private MortuusTerraCore main;

	public GeckManager(MortuusTerraCore main) {
		this.main = main;
	}

	/**
	 * @param player
	 *            The player in question
	 * @return <CODE>True </CODE> If the player is inside geck range and geck is
	 *         powered and built correctly.
	 *         <p>
	 *         <CODE>False </CODE> If the player is not in range. Also if the player
	 *         is in range but geck is not powered or incorrect.
	 */
	public boolean isPlayerInGeckRange(Player player) {
		for (Geck geckObject : main.getGeckObjectManager().getGecklocationMap().values()) {
			Location geckLocation = geckObject.getGeckLocation();
			Location playerLocation = player.getLocation();
			if (!geckLocation.getWorld().equals(playerLocation.getWorld())) {
				continue;
			}
			double distance = geckLocation.distanceSquared(playerLocation);
			// if the distance is less than or = to x and the GECK is correct
			// and powered then you are in range of the geck
			// this is the squared range of blocks. the block range is 15
			// blocks.
			int x = 225;
			UUID uuid = player.getUniqueId();
			// If the player is not inside the radiationMap
			if (!main.getPlayerManager().containsMortuusPlayer(uuid)) {
				return false;
			}

			MTPlayer mortuusPlayer = main.getPlayerManager().getMortuusPlayer(uuid);

			if (distance <= x) {
				if (geckObject.isCorrect() && geckObject.isPowered()) {
					// Player inside radius and geck is powered + correct
					mortuusPlayer.setPlayerInRangeOfGeck(true);
					return true;
				} else {
					// Player is inside but either geck not powered or not
					// correct or both
					mortuusPlayer.setPlayerInRangeOfGeck(false);
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the geck structure is correct.
	 * 
	 * @param center
	 *            The center block.
	 * @return true if the build is correct, false otherwise.
	 */
	public boolean isGeckBuildCorrect(Block center) {
		BlockFace[] faces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH };
		for (BlockFace f : faces) {
			if (center.getRelative(f).getType() != Material.PISTON_BASE)
				return false;
		}
		return true;
	}
}

/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.listeners;

import java.util.Arrays;
import java.util.List;

import com.mortuusterra.MortuusTerraCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class GeckPowerListener implements Listener {
	private MortuusTerraCore main;
	public GeckPowerListener(MortuusTerraCore main) {
		this.main = main;
	}


	// Every time a player interacts with a block
	// Added disabled message. 9/3/17

	/*
	 * Redid the listener for a cleaner look. @Shyos
	 */

	@EventHandler
	public void onPlayerLeaverInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block clicked = e.getClickedBlock();

		if (clicked == null)
			return;

		Block underneath = clicked.getRelative(BlockFace.DOWN);
		Location underneathLoc = underneath.getLocation();

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getHand() == EquipmentSlot.OFF_HAND)
			return;

		if (clicked.getType() != Material.LEVER || underneath == null)
			return;

		String chunk = clicked.getLocation().getChunk().getX() + ";" + clicked.getLocation().getChunk().getZ();

		if (underneath.getType() == Material.SPONGE && !underneath.isBlockPowered()) {
			if (!main.getGeckManager().isGeckBuildCorrect(underneath)) {
				p.sendMessage(ChatColor.RED + "You must build the GECK correctly!");

				// Check if the geck is inside the powerables list
			} else if (main.getGenListener().getPowerable().get(clicked.getWorld().getName()).getList(chunk)
					.contains(underneathLoc)) {
				main.getGeckObjectManager().addGeckLocation(underneathLoc);
				main.getGeckObjectManager().getGeckObject(underneathLoc).setCorrect(true);
				main.getGeckObjectManager().getGeckObject(underneathLoc).setPowered(true);
				p.sendMessage(ChatColor.GREEN + "GECK Enabled!");
				return;
			}

		} else if (main.getGeckObjectManager().getGeckObject(underneathLoc) != null && underneath.isBlockPowered()) {
			main.getGeckObjectManager().getGeckObject(underneathLoc).setPowered(false);
			main.getGeckObjectManager().removeGeckLocation(underneathLoc);
			p.sendMessage(ChatColor.RED + "GECK Disabled!");
		}
	}

	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block broken = event.getBlock();
		Location geckLocation = broken.getRelative(0, -1, 0).getLocation();

		if (broken.getType() == Material.LEVER) {
			if (main.getGeckObjectManager().getGeckObject(geckLocation) != null) {
				main.getGeckObjectManager().removeGeckLocation(geckLocation);
				player.sendMessage(ChatColor.RED + "GECK disabled.");
			}

		} else if (broken.getType() == Material.SPONGE
				&& main.getGeckObjectManager().getGeckObject(broken.getLocation()) != null) {
			main.getGeckObjectManager().removeGeckLocation(broken.getLocation());
			player.sendMessage(ChatColor.RED + "GECK disabled.");

		} else if (broken.getType() == Material.PISTON_BASE) {
			List<BlockFace> sides = Arrays.asList(BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH);
			sides.forEach(side -> {
				Location bloc = broken.getRelative(side).getLocation();

				if (main.getGeckObjectManager().getGeckObject(bloc) != null) {
					main.getGeckObjectManager().removeGeckLocation(bloc);
					player.sendMessage(ChatColor.RED + "GECK disabled.");
				}
			});
		}
	}
}

/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mortuusterra.MortuusTerraCore;

public class RadiationManager {
	private MortuusTerraCore main;

	public RadiationManager(MortuusTerraCore main) {
		this.main = main;
	}

	public void startPlayerRadiationDamage() {
		new BukkitRunnable() {

			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (isPlayerInRad(p))
						p.damage(calculateRadiationDamage(p));
				}
			}
		}.runTaskTimer(main, 0L, main.getDataManager().getRadiationDamageDelay() * 20L);
	}

	/**
	 * Method to calculate the radiation damage based on conditions.
	 * 
	 * @param player
	 *            The player whose damage should be calculated
	 * @return The calculated damage
	 */
	private double calculateRadiationDamage(Player player) {
		double receivedDamage = main.getDataManager().getDefaultRadiationDamage();
		double stormDamage = main.getDataManager().getStormRadiationDamage();
		double waterDamage = main.getDataManager().getWaterRadiationDamage();

		if (isPlayerInBuilding(player)) {
			return 0D;
		}

		if (player.getWorld().hasStorm()) {
			receivedDamage += stormDamage;
		}

		if (player.getLocation().getBlock().getType() == Material.WATER
				|| player.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
			receivedDamage += waterDamage;
		}

		return receivedDamage;
	}

	private boolean isPlayerInBuilding(Player p) {
		Location playerLocation = p.getLocation();
		int highestY = playerLocation.getWorld().getHighestBlockYAt(playerLocation) - 1;

		int x = playerLocation.getBlockX();
		int z = playerLocation.getBlockZ();
		List<Block> blocksAbovePlayer = new ArrayList<>();

		// Add blocks above player to a list
		for (int i = playerLocation.getBlockY() + 2; i < highestY; i++) {
			blocksAbovePlayer.add(playerLocation.getWorld().getBlockAt(x, i, z));
		}

		Block highestBlock = playerLocation.getWorld().getBlockAt(x, highestY, z);

		// Check if the highest block is a leaf
		// If its a leaf but theres a non-leaf block below player is covered
		// If theres only air or leaves above player, hes not covered
		if (highestBlock.getType().name().contains("LEAVES")) {
			for (Block b : blocksAbovePlayer) {

				if (b.getType() != Material.AIR && !b.getType().name().contains("LEAVES"))
					return true;
			}
			return false;
		}

		return (playerLocation.getBlockY() < highestY - 1);
	}

	public boolean isPlayerInRad(Player player) {
		// Returns true if player is in building and in range of geck
		return (!isPlayerInBuilding(player) && !main.getGeckManager().isPlayerInGeckRange(player));

	}

}

package com.mortuusterra.managers;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.tasks.RadiationTask;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RadiationManager {

// Radiation damage should be a system that works as follows:
// Rads int as a value applied to the player
//Rads number depends on weather and if indoors/in water
// Deal player damage based on rads int
// GECKS remove all rads for players in range
// no rads in nether
// Armor lowers rad level by certain amount, OR rad damage is modified by armor level value in spigot
// Careful here, if we treat this as normal damage that armor protects against we must take durability loss into consideration, is this something we want or should rads not damage armor

	private MortuusTerraMain main;
	private final int geckRange = 10, generatorRange = 25;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<GeckManager> MTGeckList = new ArrayList<GeckManager>();
	private ArrayList<GeneratorManager> MTGeneratorList = new ArrayList<GeneratorManager>();

	private RadiationTask mtradtask;
	
	// private MTRadiationDamageEvent event;

	public RadiationManager(MortuusTerraMain m) {
		this.main = m;
		mtradtask = new RadiationTask(main, playerList);
	}

	public void addPlayer(Player p) {
		playerList.add(p);
		mtradtask.updateList(playerList);
	}

	public void removePlayer(Player p) {
		playerList.remove(p);
		mtradtask.updateList(playerList);
	}

	// check if the player can be damaged by radiation
	/**
	 * 
	 * @param player
	 *            Player to be checked
	 * @return False if player can not be damaged by radiation. True if player can
	 *         be damaged by radiation
	 */
	public boolean playerCheck(Player player) {
		if (isPlayerInRange(player)) {
			return false;
		}
		return true;
	}

	public void addGeck(GeckManager geck) {
		if (MTGeckList.contains(geck)) {
			return;
		}
		MTGeckList.add(geck);
		return;
	}

	public void removeGeck(GeckManager geck) {
		if (!MTGeckList.contains(geck)) {
			return;
		}
		MTGeckList.remove(geck);
		return;
	}

	public boolean containsGeck(GeckManager geck) {
		if (MTGeckList.contains(geck)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param geckLocation
	 *            Location that is being checked
	 * @return MTGeck object of the given location. If there is no MTGeck object at
	 *         the given location the NULL is returned.
	 */
	public GeckManager getGeck(Location geckLocation) {
		for (GeckManager geck : MTGeckList) {
			if (geck.getGeckLocation().distance(geckLocation) == 0) {
				return geck;
			}
		}
		return null;
	}

	public void addGenerator(GeneratorManager mtgenerator) {
		MTGeneratorList.add(mtgenerator);
	}

	public void removeGenerator(GeneratorManager mtgenerator) {
		MTGeneratorList.remove(mtgenerator);
	}

	/**
	 * 
	 * @param mtgeneratorLocation
	 *            The location that is being checked
	 * @return MTGenerator object of the given location. If there is no generator
	 *         attached with the given location the NULL is returned.
	 */
	public GeneratorManager getGenerator(Location mtgeneratorLocation) {
		for (GeneratorManager gen : MTGeneratorList) {
			if (gen.getGeneratorLocation().distance(mtgeneratorLocation) == 0) {
				return gen;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param mtgenerator
	 *            The generator to check
	 * @return True if the generator is already in the list
	 */
	public boolean containsGenerator(GeneratorManager mtgenerator) {
		if (MTGeneratorList.contains(mtgenerator)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param location
	 *            The location to check
	 * @return True if the given location is part of a generator. False if the given
	 *         location is not part of a generator.
	 */
	public boolean containsGenerator(Location location) {
		for (GeneratorManager gen : MTGeneratorList) {
			if (gen.getGeneratorLocation().distance(location) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param block
	 *            The block to check
	 * @return True if the given block is in range of a generator. False if the
	 *         given block is not in range of a generator.
	 */
	public boolean isInGeneratorRange(Block block) {
		for (GeneratorManager gen : MTGeneratorList) {
			if (block.getLocation().distance(gen.getGeneratorLocation()) <= generatorRange) {
				if (!gen.isValid()) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param gen
	 *            The Generator object to check
	 * @param player
	 *            The player to check
	 * @return True if the given player can interact with the given generator. False
	 *         if the given player can not interact with the given generator. Only
	 *         the owner of the generator or and members of the generator can
	 *         interact with the generator.
	 *
	 */
	 //TODO we dont need this, faction land will take care of permission to use
	public boolean canPlayerInteractGenerator(GeneratorManager gen, Player player) {
		if (gen.getOwner().getUniqueId().equals(player.getUniqueId()) || gen.getAllowedPlayers().contains(player)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 *            to check if in range of GECK
	 * @return True if in range of GECK. False if not in range of GECK.
	 */
	public boolean isPlayerInRange(Player player) {
		for (GeckManager mtgeck : MTGeckList) {
			if (player.getLocation().distance(mtgeck.getGeckLocation()) <= geckRange && mtgeck.getPowered()
					&& mtgeck.isValid()) {
				player.sendMessage("You are in range of a GECK.");
				return true;
			}
		}
		player.sendMessage("You are not in range of a GECK.");
		return false;
	}

}

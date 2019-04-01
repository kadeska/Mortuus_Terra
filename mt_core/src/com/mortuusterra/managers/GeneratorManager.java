package com.mortuusterra.managers;

import com.mortuusterra.MortuusTerraMain;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
// TODO: Code furnace to use existing bukkit integration instead of a custom removing materials and ispowered method
public class GeneratorManager {
	private GeneratorManager gen = this;
	private MortuusTerraMain main;
	private int u = 0, d = 10;
	private Boolean powered = false;
	private boolean valid = false;
	private Location generatorLocation;
	private Block furnace;
	private Block lamp;
	private World world;
	private Player owner;
	private ArrayList<Player> allowedPlayers = new ArrayList<Player>();
	private BukkitTask run;
	public boolean busy;

	public GeneratorManager(Player owner, Location furnacLocation, MortuusTerraMain m) {
		this.main = m;
		this.generatorLocation = furnacLocation;
		this.furnace = furnacLocation.getBlock();
		this.lamp = furnace.getRelative(BlockFace.UP);
		this.owner = owner;
	}
	public Boolean getPowered() {
		return powered;
	}

	public void setPowered(Boolean powered) {
		this.powered = powered;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Location getGeneratorLocation() {
		return generatorLocation;
	}

	public ArrayList<Player> getAllowedPlayers() {
		return allowedPlayers;
		// Factions will integrate this natively, remove all allowed player functionality
	}

	public Player getOwner() {
		return owner;
	}

	private Inventory getFurnaceInventory() {
		InventoryHolder ih = (InventoryHolder) furnace.getState();
		return ih.getInventory();
	}

	public Block getFurnace() {
		return furnace;
	}

	public void startWaitForCoal() {
		this.run = new BukkitRunnable() {
			@Override
			public void run() {
				if (isBroken()) {
					owner.sendMessage(
							"Your Generator is broken, it is not generating power anymore until you fix it!!");
					stopGenerator();
					return;
				}
				useCoal();
			}
		}.runTaskTimer(main, 10, 600);
	}

	public boolean isBroken() {
		if (scan()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return True if generator is still fully built. False if generator is not
	 *         still fully built.
	 */


	//TODO() Change these so they aren't depreciated before release
	public boolean scan() {
		this.busy = true;
		new BukkitRunnable() {
			@Override
			public void run() {
				Block furnace = gen.getFurnace();
				if (furnace.getRelative(BlockFace.UP).getType().equals(Material.LEGACY_REDSTONE_LAMP_OFF)) {
					Block lamp = furnace.getRelative(BlockFace.UP);
					if (furnace.getRelative(BlockFace.DOWN).getType().equals(Material.LEGACY_SMOOTH_BRICK)) {
						Block centerGround = furnace.getRelative(BlockFace.DOWN);
						if (lamp.getRelative(BlockFace.UP).getType().equals(Material.LEGACY_STONE_SLAB2)) {
							Block centerTop = lamp.getRelative(BlockFace.UP);
							BlockFace[] squareFaces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
									BlockFace.SOUTH, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
									BlockFace.SOUTH_WEST };
							BlockFace[] ironFenceFaces = { BlockFace.NORTH_EAST, BlockFace.NORTH_WEST,
									BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST };
							for (BlockFace f : squareFaces) {
								if (!centerGround.getRelative(f).getType().equals(Material.LEGACY_SMOOTH_BRICK)
										|| !centerTop.getRelative(f).getType().equals(Material.STONE_SLAB)) {
									gen.getOwner().sendMessage("Generator is not built correctly!");
									stopGenerator();
									gen.setValid(false);
									// return valid;
								}
							}
							for (BlockFace f : ironFenceFaces) {
								if (!furnace.getRelative(f).getType().equals(Material.IRON_BARS)
										|| !lamp.getRelative(f).getType().equals(Material.IRON_BARS)) {
									gen.getOwner().sendMessage("Generator is not built correctly!");
									stopGenerator();
									gen.setValid(false);
									// return valid;
								}
							}
						}
					}
				}
				// generator must be built correctly still, so set valid true and update lamp if
				// it is off
				gen.setValid(true);
			}
		}.runTask(main);
		this.busy = false;
		return valid;
	}

	private void useCoal() {
		// TODO: are we just removing the coal blocks and replacing them with 8 coal? We
		// should be burning coal blocks directly
		if (getFurnaceInventory().contains(Material.COAL)) {
			getFurnaceInventory().removeItem(new ItemStack(Material.COAL, 1));
		} else if (getFurnaceInventory().contains(Material.COAL_BLOCK)) {
			getFurnaceInventory().removeItem(new ItemStack(Material.COAL_BLOCK, 1));
			getFurnaceInventory().addItem(new ItemStack(Material.COAL, 8));
		} else {
			owner.sendMessage("Generator needs coal, shutting down!");
			stopGenerator();
		}

	}

	/*@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e) {

		Furnace furnace = (Furnace) e.getBlock().getState();
		furnace.setCookTime((short) 100);

	}*/

	public void startGenerator() {
		this.busy = true;
		gen.setValid(true);
		gen.getOwner().sendMessage(ChatColor.BLUE + "Generator is now completely powered up, and awaiting coal!");
		gen.startWaitForCoal();
		this.busy = false;
	}

	public void stopGenerator() {
		this.busy = true;
		gen.getOwner().sendMessage(
				ChatColor.RED + "!!WARNING!! " + ChatColor.BLUE + "Generator is now completely powered down!");
		gen.setValid(false);
		main.getRad().removeGenerator(gen);
		this.busy = false;
	}

}

package com.mortuusterra.managers;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.mortuusterra.MortuusTerraMain;

public class MTGenerator {
	private Boolean powered = false;
	private boolean valid = false;
	private Location generatorLocation;
	private World world;
	
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private BukkitTask run;

	public MTGenerator(Location location, ArrayList<Block> mtgeneratorBlocks) {
		this.generatorLocation = location;
		this.blocks = mtgeneratorBlocks;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
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
	
	private FurnaceInventory getFurnace() {
		for(Block b : blocks) {
			if(b.getType().equals(Material.FURNACE)) {
				return (FurnaceInventory) b;
			}
		}
		return null;
	}
	
	public void startWaitForCoal(MortuusTerraMain main, Player p) {
		this.run = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(getFurnace().getFuel().getAmount() != 0) {
					if(getFurnace().getFuel().getType().equals(Material.COAL)) {
						getFurnace().getFuel().setAmount(getFurnace().getFuel().getAmount() - 1);
					}
				}else {
					p.sendMessage("Generator needs coal!");
				}
				
			}
		}.runTaskTimerAsynchronously(main, 20, 40);
	}

}

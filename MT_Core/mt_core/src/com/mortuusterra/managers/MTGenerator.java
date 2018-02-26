package com.mortuusterra.managers;

import org.bukkit.Location;
import org.bukkit.World;

public class MTGenerator {
	private Boolean powered = false;
	private boolean valid = false;
	private Location generatorLocation;
	private World world;

	public MTGenerator(Location location) {
		this.generatorLocation = location;
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

}

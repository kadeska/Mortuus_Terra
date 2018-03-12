/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.objects;

import org.bukkit.Location;

public class Generator {

	private Location genLoc;
	private boolean toggle;
	private int fuelAmount;
	
	public Generator(Location genLoc) {
		this.setGenLoc(genLoc);
	}

	public int getFuelAmount() {
		return fuelAmount;
	}

	public void setFuelAmount(int fuelAmount) {
		this.fuelAmount = fuelAmount;
	}

	public boolean isToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	public Location getGenLoc() {
		return genLoc;
	}

	public void setGenLoc(Location genLoc) {
		this.genLoc = genLoc;
	}
	
	
}

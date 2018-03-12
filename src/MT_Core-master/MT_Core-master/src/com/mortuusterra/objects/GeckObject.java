/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.objects;

import org.bukkit.Location;

public class GeckObject {

	private Boolean ispowered = false;
	private boolean isCorrect = false;
	private Location geckLocation;

	public GeckObject(Location geckLocation) {
		this.geckLocation = geckLocation;
	}
	public Boolean isPowered() {
		return ispowered;
	}

	public void setPowered(Boolean ispowered) {
		this.ispowered = ispowered;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Location getGeckLocation() {
		return geckLocation;
	}

	public void setGeckLocation(Location geckLocation) {
		this.geckLocation = geckLocation;
	}
}

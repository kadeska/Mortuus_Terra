/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.misc;

import org.bukkit.ChatColor;

public enum PKStates {
	
	NEUTRAL(ChatColor.WHITE, 0),
	ORANGE(ChatColor.GOLD, 5),
	RED(ChatColor.RED, 15);
	
	private ChatColor color;
	private int requiredKills;
	
	PKStates(ChatColor color, int requiredKills) {
		this.color = color;
		this.requiredKills = requiredKills;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public int getRequiredKills() {
		return requiredKills;
	}
	
	public static PKStates getStateByString(String state) {
		for (PKStates st : PKStates.values()) {
			if (st.name().equalsIgnoreCase(state))
				return st;
		}
		
		return null;
	}
	
	
	
}

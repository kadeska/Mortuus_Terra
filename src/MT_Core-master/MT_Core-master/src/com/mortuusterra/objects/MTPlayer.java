/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.mortuusterra.misc.PKStates;

public class MTPlayer {

	//private MortuusTerraCore main = JavaPlugin.getPlugin(MortuusTerraCore.class);

	private UUID uuid;
	private PKStates pkState;
	private long lastPlayerKillTime;
	private int playerKills;
	private boolean playerInRangeOfGeck;

	private String currentIngameName;
	private Location playerLocation;

	public MTPlayer(UUID uniqueId) {
		this.lastPlayerKillTime = 0;
		this.pkState = PKStates.NEUTRAL;
		this.uuid = uniqueId;
		this.playerKills = 0;
		this.playerInRangeOfGeck = false;
		this.currentIngameName = Bukkit.getOfflinePlayer(uniqueId).getName();
	}

	public UUID getUuid() {
		return uuid;
	}
	
	public long getLastPlayerKillTime() {
		return lastPlayerKillTime;
	}
	
	public void setLastPlayerKillTime(long lastPlayerKillTime) {
		this.lastPlayerKillTime = lastPlayerKillTime;
	}

	public PKStates getPkState() {
		return pkState;
	}
	
	public void setPkState(PKStates pkState) {
		this.pkState = pkState;
	}
	
	public String getCurrentIngameName() {
		return currentIngameName;
	}

	public int getPlayerKills() {
		return playerKills;
	}

	public void addPlayerKills(int amount) {
		this.playerKills += amount;
	}

	public void setPlayerKills(int playerKills) {
		this.playerKills = playerKills;
	}

	public void setPlayerInRangeOfGeck(boolean bool) {
		this.playerInRangeOfGeck = bool;
	}

	public boolean isPlayerInRangeOfGeck() {
		return playerInRangeOfGeck;
	}

	public Location getPlayerLocation() {
		return playerLocation;
	}

	public void setPlayerLocation(Location playerLocation) {
		this.playerLocation = playerLocation;
	}

}

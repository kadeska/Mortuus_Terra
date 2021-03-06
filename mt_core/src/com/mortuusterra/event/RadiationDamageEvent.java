package com.mortuusterra.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RadiationDamageEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private Player damagedPlayer;

	private boolean cancelled;

	public RadiationDamageEvent(Player player) {
		this.damagedPlayer = player;
	}

	public Player getDamagedPlayer() {
		return damagedPlayer;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;

	}

}

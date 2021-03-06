package com.mortuusterra.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InfectionRemoveEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
	private Player player;

	public InfectionRemoveEvent(Player player) {
		this.player = player;
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

	public Player getDisinfectedPlayer() {
		return player;
	}
	

}

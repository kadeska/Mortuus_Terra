package com.mortuusterra.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mortuusterra.managers.SupplyDropManager;

public class SupplyDropLandEvent extends Event implements Cancellable{
	private static final HandlerList handlers = new HandlerList();

	private SupplyDropManager supplydrop;

	private boolean cancelled;

	public SupplyDropLandEvent(SupplyDropManager drop) {
		this.supplydrop = drop;
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

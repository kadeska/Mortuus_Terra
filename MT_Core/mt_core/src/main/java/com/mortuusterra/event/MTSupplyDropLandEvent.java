package main.java.com.mortuusterra.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import main.java.com.mortuusterra.manager.MTSupplyDrop;

public class MTSupplyDropLandEvent extends Event implements Cancellable{
	private static final HandlerList handlers = new HandlerList();

	private MTSupplyDrop supplydrop;

	private boolean cancelled;

	public MTSupplyDropLandEvent(MTSupplyDrop drop) {
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

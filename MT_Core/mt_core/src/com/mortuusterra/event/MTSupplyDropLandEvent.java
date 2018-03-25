package com.mortuusterra.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

<<<<<<< HEAD:MT_Core/mt_core/src/com/mortuusterra/event/MTSupplyDropLandEvent.java
import com.mortuusterra.managers.MTSupplyDrop;
=======
import main.java.com.mortuusterra.manager.MTSupplyDrop;
>>>>>>> 700e479bc563f8ebc254cfa1657461bd95b5c450:MT_Core/mt_core/src/main/java/com/mortuusterra/event/MTSupplyDropLandEvent.java

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

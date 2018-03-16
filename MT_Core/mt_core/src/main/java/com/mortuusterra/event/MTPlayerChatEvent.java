package main.java.com.mortuusterra.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MTPlayerChatEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();

	private Player player;

	private boolean cancelled;

	public MTPlayerChatEvent(Player player) {
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

}

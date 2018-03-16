package main.java.com.mortuusterra.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import main.java.com.mortuusterra.MortuusTerraMain;

public class MTPlayerListener implements Listener {
	
	private MortuusTerraMain main;

	public MTPlayerListener(MortuusTerraMain m) {
		this.main = m;
	}
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		main.getRad().addPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		main.getRad().removePlayer(e.getPlayer());
	}

}

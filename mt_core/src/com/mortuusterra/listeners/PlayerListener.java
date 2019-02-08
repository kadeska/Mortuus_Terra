package com.mortuusterra.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.items.CustomZombieEgg;

public class PlayerListener implements Listener {
	
	private MortuusTerraMain main;

	public PlayerListener(MortuusTerraMain m) {
		this.main = m;
	}
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		main.getRad().addPlayer(e.getPlayer());
		//e.getPlayer().getInventory().addItem(CustomZombieEgg.getItem());
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		main.getRad().removePlayer(e.getPlayer());
	}

}

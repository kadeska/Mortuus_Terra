package com.mortuusterra.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.util.ChatChannels;

import net.md_5.bungee.api.ChatColor;

public class CommunicationListener implements Listener {

	private MortuusTerraMain main;

	public CommunicationListener(MortuusTerraMain m) {
		this.main = m;
	}

	@EventHandler
	public void PlayerChatEvent(AsyncPlayerChatEvent e) {
		
		e.setCancelled(true);
		if ((main.getCommunicationChannels().getChannel(e.getPlayer()) == ChatChannels.NULL)) {
			e.getPlayer().sendMessage(ChatColor.RED + "You must choose a channel before you can chat.");
			e.getPlayer().sendMessage(ChatColor.RED + "Use [/channel list] to see a list of all the channels that are available to you.");
			e.getPlayer().sendMessage(ChatColor.RED + "Use [/channel join <channel>] to join the chosen channel");
			return;
		}
		main.getCommunicationChannels().sendMessage(main.getCommunicationChannels().getChannel(e.getPlayer()),
				e.getPlayer(), e.getMessage());

	}

}

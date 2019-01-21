package com.mortuusterra.managers;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.util.ChatChannels;

import net.md_5.bungee.api.ChatColor;

public class CommunicationChannelsManager {

	private HashMap<Player, ChatChannels> MTMap = new HashMap<Player, ChatChannels>();

	private MortuusTerraMain main;

	public CommunicationChannelsManager(MortuusTerraMain m) {
		this.main = m;
	}

	public void addPlayer(Player p) {
		if (main.isDebugMode()) {
			if (!MTMap.containsKey(p)) {
				main.getLogger().log(Level.INFO, "Player " + p.getName() + " is not in the map. Adding now");
				MTMap.put(p, ChatChannels.NULL);
				main.getLogger().log(Level.INFO,
						"Player " + p.getName() + " is now in the map, and is in the channel " + MTMap.get(p).name());
				return;
			} else {
				main.getLogger().log(Level.INFO, "Player " + p.getName() + " is already in the map");
				main.getLogger().log(Level.INFO, "Player " + p.getName() + " is in channel " + MTMap.get(p).name());
				return;
			}
		} else if (MTMap.containsKey(p)) {
			return;
		}
		MTMap.put(p, ChatChannels.NULL);
	}

	public void setChannel(Player p, ChatChannels channel) {
		if (main.isDebugMode()) {
			if (!MTMap.containsKey(p)) {
				main.getLogger().log(Level.INFO, "Player " + p.getName() + " is not in the map. Adding now");
				MTMap.put(p, channel);
				main.getLogger().log(Level.INFO,
						"Player " + p.getName() + " is now in the map, and is in the channel " + channel.name());
				p.sendMessage(ChatColor.BLUE + "You are not connected to the chat channel: " + ChatColor.GRAY
						+ channel.name());
				p.sendMessage(ChatColor.BLUE + "You now can send and receive messages on this channel");
				p.sendMessage(ChatColor.BLUE + "If you wish to change channels at any time then use " + ChatColor.WHITE
						+ "[" + ChatColor.GREEN + "/channel join " + ChatColor.GRAY + "<" + ChatColor.GREEN + "channel"
						+ ChatColor.GRAY + ">" + ChatColor.WHITE + "]");
				return;
			} else {
				main.getLogger().log(Level.INFO,
						"Player " + p.getName() + " is already in the map. setting channel to " + channel.name());
				MTMap.put(p, channel);
				main.getLogger().log(Level.INFO, "Player " + p.getName() + " is in channel " + MTMap.get(p).name());
				p.sendMessage(ChatColor.BLUE + "You are not connected to the chat channel: " + ChatColor.GRAY
						+ channel.name());
				p.sendMessage(ChatColor.BLUE + "You now can send and receive messages on this channel");
				p.sendMessage(ChatColor.BLUE + "If you wish to change channels at any time then use " + ChatColor.WHITE
						+ "[" + ChatColor.GREEN + "/channel join " + ChatColor.GRAY + "<" + ChatColor.GREEN + "channel"
						+ ChatColor.GRAY + ">" + ChatColor.WHITE + "]");
				return;
			}
		} else if (!MTMap.containsKey(p)) {
			MTMap.put(p, channel);
			p.sendMessage(
					ChatColor.BLUE + "You are not connected to the chat channel: " + ChatColor.GRAY + channel.name());
			p.sendMessage(ChatColor.BLUE + "You now can send and receive messages on this channel");
			p.sendMessage(ChatColor.BLUE + "If you wish to change channels at any time then use " + ChatColor.WHITE
					+ "[" + ChatColor.GREEN + "/channel join " + ChatColor.GRAY + "<" + ChatColor.GREEN + "channel"
					+ ChatColor.GRAY + ">" + ChatColor.WHITE + "]");
			return;
		}
		MTMap.put(p, channel);
		p.sendMessage(ChatColor.BLUE + "You are not connected to the chat channel: " + ChatColor.GRAY + channel.name());
		p.sendMessage(ChatColor.BLUE + "You now can send and receive messages on this channel");
		p.sendMessage(ChatColor.BLUE + "If you wish to change channels at any time then use " + ChatColor.WHITE + "["
				+ ChatColor.GREEN + "/channel join " + ChatColor.GRAY + "<" + ChatColor.GREEN + "channel"
				+ ChatColor.GRAY + ">" + ChatColor.WHITE + "]");
		return;
	}

	public ChatChannels getChannel(Player p) {
		if (main.isDebugMode()) {
			if (!MTMap.containsKey(p)) {
				main.getLogger().log(Level.INFO, "Player " + p.getName() + " is not in the map. Adding now");
				MTMap.put(p, ChatChannels.NULL);
				main.getLogger().log(Level.INFO,
						"Player " + p.getName() + " is now in the map, and is in the channel " + MTMap.get(p).name());
				return MTMap.get(p);
			} else {
				main.getLogger().log(Level.INFO,
						"Player " + p.getName() + " is in the map. Returning " + MTMap.get(p).name());
				main.getLogger().log(Level.INFO, "Player " + p.getName() + " is in channel " + MTMap.get(p).name());
				return MTMap.get(p);
			}
		} else if (!MTMap.containsKey(p)) {
			MTMap.put(p, ChatChannels.NULL);
			return MTMap.get(p);
		}
		return MTMap.get(p);
	}

	public void sendMessage(ChatChannels channel, Player sender, String message) {
		if (sender.hasPermission("mortuusterra.chat.overide")) {
			for (Player p : MTMap.keySet()) {
				p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GRAY + "Channel: " + ChatColor.GREEN
						+ MTMap.get(sender).name() + ChatColor.WHITE + "] " + ChatColor.WHITE + "<" + ChatColor.GREEN
						+ sender.getName() + ChatColor.WHITE + "> " + ChatColor.WHITE + message);
			}
		} else {
			for (Player p : MTMap.keySet()) {
				if (MTMap.get(p) == MTMap.get(sender)) {
					p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GRAY + "Channel: " + ChatColor.GREEN
							+ MTMap.get(sender).name() + ChatColor.WHITE + "] " + ChatColor.WHITE + "<"
							+ ChatColor.GREEN + sender.getName() + ChatColor.WHITE + "> " + ChatColor.WHITE + message);
				}
			}
		}
	}

}

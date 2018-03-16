package main.java.com.mortuusterra.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.com.mortuusterra.MortuusTerraMain;
import main.java.com.mortuusterra.communication.MTChatChannel;


public class MTcommands implements CommandExecutor {

	private MortuusTerraMain main;

	public MTcommands(MortuusTerraMain m) {
		this.main = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Commands are only available from ingame!");
			return false;
		}

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("mortuusterra")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					// Send player custom help message
					p.sendMessage("test help");
				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("channel")) {
			if (args.length == 0) {
				p.sendMessage(ChatColor.GRAY + "This is the Base command for sellecting a chat channel.");
				p.sendMessage(ChatColor.BLUE + "You can do " + ChatColor.WHITE + "[" + ChatColor.GREEN + "/channel list"
						+ ChatColor.WHITE + "] To see a list of all of the channels that you can join.");
				p.sendMessage("You can do " + ChatColor.WHITE + "[" + ChatColor.GREEN + "/channel join "
						+ ChatColor.GRAY + "<" + ChatColor.GREEN + "channel" + ChatColor.GRAY + ">" + ChatColor.WHITE
						+ "] To join a channel");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					// check what channels the player can join based off of permissions
					String s = "";
					if (p.hasPermission("mortuusterra.channel.alpha")) {
						s = s + ChatColor.YELLOW + " Alpha,";
					}
					if (p.hasPermission("mortuusterra.channel.bravo")) {
						s = s + ChatColor.YELLOW + " Bravo,";
					}
					if (p.hasPermission("mortuusterra.channel.charlie")) {
						s = s + ChatColor.YELLOW + " Charlie,";
					}
					if (p.hasPermission("mortuusterra.channel.delta")) {
						s = s + ChatColor.YELLOW + " Delta,";
					}
					if (p.hasPermission("mortuusterra.channel.echo")) {
						s = s + ChatColor.YELLOW + " Echo,";
					}
					if (p.hasPermission("mortuusterra.channel.admin")) {
						s = s + ChatColor.RED + " Admin,";
					}
					if (p.hasPermission("mortuusterra.channel.custom")) {
						s = s + ChatColor.GOLD + " Custom";
					}
					// Send player list of available channels
					p.sendMessage(ChatColor.BLUE + "You can join these chat channels:" + s);
				} else {
					p.sendMessage(ChatColor.RED
							+ "Unknown command. Did you mean [/channel list] or [/channel join <channel>]");
					return true;
				}
			}

			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("join")) {
					if (args[1].equalsIgnoreCase("alpha")) {
						if (p.hasPermission("mortuusterra.channel.alpha")) {
							main.getCommunicationChannels().setChannel(p, MTChatChannel.APLHA);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have permission to join this channel!");
						}
						return true;
					}
					if (args[1].equalsIgnoreCase("bravo")) {
						if (p.hasPermission("mortuusterra.channel.bravo")) {
							main.getCommunicationChannels().setChannel(p, MTChatChannel.BRAVO);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have permission to join this channel!");
						}
						return true;
					}
					if (args[1].equalsIgnoreCase("charlie")) {
						if (p.hasPermission("mortuusterra.channel.charlie")) {
							main.getCommunicationChannels().setChannel(p, MTChatChannel.Charlie);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have permission to join this channel!");
						}
						return true;
					}
					if (args[1].equalsIgnoreCase("delta")) {
						if (p.hasPermission("mortuusterra.channel.delta")) {
							main.getCommunicationChannels().setChannel(p, MTChatChannel.Delta);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have permission to join this channel!");
						}
						return true;
					}
					if (args[1].equalsIgnoreCase("echo")) {
						if (p.hasPermission("mortuusterra.channel.echo")) {
							main.getCommunicationChannels().setChannel(p, MTChatChannel.ECHO);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have permission to join this channel!");
						}
						return true;
					}
					if (args[1].equalsIgnoreCase("admin")) {
						if (p.hasPermission("mortuusterra.channel.admin")) {
							main.getCommunicationChannels().setChannel(p, MTChatChannel.ADMIN);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have permission to join this channel!");
						}
						return true;
					}
					if (args[1].equalsIgnoreCase("custom")) {
						if (p.hasPermission("mortuusterra.channel.custom")) {
							main.getCommunicationChannels().setChannel(p, MTChatChannel.CUSTOM);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have permission to join this channel!");
						}
						return true;
					}
					p.sendMessage(ChatColor.RED
							+ "Unknown command. Did you mean [/channel list] or [/channel join <channel>]");
				}
				p.sendMessage(
						ChatColor.RED + "Unknown command. Did you mean [/channel list] or [/channel join <channel>]");
			}
		}
		return true;
	}

}

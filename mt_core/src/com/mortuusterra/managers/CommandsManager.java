package com.mortuusterra.managers;
// TODO: remove all chat commands because we will redo that
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.mortuusterra.MortuusTerraMain;


public class CommandsManager implements CommandExecutor {

	private MortuusTerraMain main;

	public CommandsManager(MortuusTerraMain m) {
		this.main = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}

}

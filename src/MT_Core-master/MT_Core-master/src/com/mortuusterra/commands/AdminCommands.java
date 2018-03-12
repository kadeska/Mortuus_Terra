/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.utils.others.StringUtilities;

public class AdminCommands implements CommandExecutor {

	private MortuusTerraCore main;
	private final String[] helpPage = StringUtilities.helpPage("supplydrop", "drop", "Forces a supplydrop");

	public AdminCommands(MortuusTerraCore main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Admin commands are currently only available from ingame.");
			return true;
		}

		Player p = (Player) sender;

		if (!p.isOp())
			return true;

		if (args.length == 0) {
			p.sendMessage(helpPage);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("supplydrop")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("drop")) {
					main.getSupplyDropManager().deliverSupplyDrop(p.getWorld());

				}
			}
		}

		return true;
	}

}

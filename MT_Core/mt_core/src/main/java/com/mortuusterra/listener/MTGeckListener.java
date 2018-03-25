package main.java.com.mortuusterra.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import main.java.com.mortuusterra.MortuusTerraMain;
import main.java.com.mortuusterra.manager.MTGeck;
import net.md_5.bungee.api.ChatColor;

public class MTGeckListener implements Listener {

	private MortuusTerraMain main;

	public MTGeckListener(MortuusTerraMain m) {
		this.main = m;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		// e.getPlayer().sendMessage("test");
		if (e.getClickedBlock() == null) {
			return;
		}
		// is the clicked block a lever
		if (e.getClickedBlock().getType().equals(Material.LEVER)) {
			Block lever = e.getClickedBlock();
			// is the block below the lever a sponge
			if (lever.getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE)) {
				Block sponge = lever.getRelative(BlockFace.DOWN);
				BlockFace[] faces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH };
				for (BlockFace f : faces) {
					// are there pistons on the 4 horizontal sides of the sponge
					if (!sponge.getRelative(f).getType().equals(Material.PISTON_BASE)) {
						e.getPlayer().sendMessage("You must create the GECK correctly!");
						return;
					}
				}

				// is there a generator in range.
				if(!main.getRad().isInGeneratorRange(sponge)) {
					e.getPlayer().sendMessage("There is no generator in range");
					return;
				}
				// if the sponge/geck is already in the MTGeckList then check the power state of
				// the sponge and do appropriate actions, power off/on
				if (main.getRad().containsGeck(main.getRad().getGeck(sponge.getLocation()))) {
					// if the sponge is already powered then the player must be turning the GECK
					// off, else the player must be turning the GECK on.
					if (sponge.isBlockPowered()) {
						e.getPlayer().sendMessage(ChatColor.RED + "!!WARNING!! " + ChatColor.BLUE
								+ "The GECK now will charge down and the air around it will become radiated.");
						main.getRad().getGeck(sponge.getLocation()).chargeDown();
					} else {
						e.getPlayer().sendMessage(
								ChatColor.BLUE + "The GECK now will charge up and deradiate the air around it.");
						e.getPlayer()
								.sendMessage(ChatColor.BLUE + "Once finished there will be a " + ChatColor.GOLD
										+ " radiation free zone" + ChatColor.BLUE + "that is about " + ChatColor.GOLD
										+ " 10 blocks " + ChatColor.BLUE + "in every direction around the GECK");
						e.getPlayer().sendMessage(ChatColor.BLUE + "GECK is now charging. Please wait!");
						main.getRad().getGeck(sponge.getLocation()).chargeUp();
					}
				} else {
					// else add the sponge/geck to the MTGeckList
					main.getRad().addGeck(new MTGeck(sponge.getLocation(), e.getPlayer(), main));

					// Inform player that the new GECK has been build and now it needs to charge and
					// deradiate the air around it
					e.getPlayer()
							.sendMessage(ChatColor.BLUE + "You have succesfuly created a GECK" + ChatColor.WHITE + "("
									+ ChatColor.GRAY + "Garden of Eden Creation Kit" + ChatColor.WHITE + ")"
									+ ChatColor.BLUE + "!");
					e.getPlayer().sendMessage(
							ChatColor.BLUE + "The GECK now will charge up and deradiate the air around it.");
					e.getPlayer()
							.sendMessage(ChatColor.BLUE + "Once finished there will be a " + ChatColor.GOLD
									+ " radiation free zone" + ChatColor.BLUE + "that is about " + ChatColor.GOLD
									+ " 10 blocks " + ChatColor.BLUE + "in every direction around the GECK");
					e.getPlayer().sendMessage(ChatColor.BLUE + "GECK is now charging. Please wait!");
					main.getRad().getGeck(sponge.getLocation()).setValid(true);
					main.getRad().getGeck(sponge.getLocation()).chargeUp();
				}
			}
		}
		return;
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.SPONGE)) {
			if (main.getRad().containsGeck(main.getRad().getGeck(e.getBlock().getLocation()))) {
				main.getRad().removeGeck(main.getRad().getGeck(e.getBlock().getLocation()));
			}
		}
	}

}

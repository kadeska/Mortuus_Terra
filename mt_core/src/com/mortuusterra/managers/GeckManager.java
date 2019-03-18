package com.mortuusterra.managers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.tasks.TimerTask;

public class GeckManager {
	private MortuusTerraMain main;
	private Boolean powered;
	private boolean valid;
	private Location geckLocation; // sponge
	private World world;
	private Player owner;
	private int u = 0, d = 10;

	public GeckManager(Location l, Player owner, MortuusTerraMain m) {
		this.main = m;
		this.owner = owner;
		this.geckLocation = l;
		this.world = l.getWorld();
	}

	public World getWorld() {
		return world;
	}

	public Player getOwner() {
		return owner;
	}

	public Boolean getPowered() {
		return powered;
	}

	public void setPowered(Boolean powered) {
		this.powered = powered;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Location getGeckLocation() {
		return geckLocation;
	}

	public boolean scan() {
		new TimerTask(main, true, 10, 40) {
			@Override
			public void run() {
		if (!main.getRad().containsGeck(GeckManager.this)) {
			stop();
		}
		Block sponge = geckLocation.getBlock();
		// is there a generator in range.
		if (!main.getRad().isInGeneratorRange(sponge)) {
			owner.sendMessage("There is no generator in range, shutting down!");
			setValid(false);
			chargeDown();
			stop();
		}
		BlockFace[] faces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH };
		for (BlockFace f : faces) {
			// are there pistons on the 4 horizontal sides of the sponge
			if (!sponge.getRelative(f).getType().equals(Material.LEGACY_PISTON_BASE)) {
				owner.sendMessage("The GECK is broken, shutting down!");
				setValid(false);
				chargeDown();
				stop();
			}
		}
		setValid(true);
		
			}
		};
		
		return valid;
	}

	public void chargeUp() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (u < 0 || u > 11) {
					// u = 0;
					//cancel();
					return;
				}
				if (u == 0) {
					owner.sendMessage(ChatColor.BLUE + "GECK power: " + ChatColor.YELLOW + "0 " + ChatColor.GOLD + "%");
				} else {
					owner.sendMessage(
							ChatColor.BLUE + "GECK power: " + ChatColor.YELLOW + u + "0 " + ChatColor.GOLD + "%");
				}
				u++;
				if (u == 10) {
					owner.sendMessage(
							ChatColor.BLUE + "GECK power: " + ChatColor.YELLOW + "100 " + ChatColor.GOLD + "%");
					owner.sendMessage(ChatColor.BLUE + "GECK is now compleatly pwered up!");
					setPowered(true);
					//cancel();
					return;
				}
			}

		}.runTaskTimer(main, 0, 30);
		u = 0;
	}
	//TODO: these messages/delays are useless, should they even exist?
	public void chargeDown() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (d < 0 || d > 11) {
					// d = 10;
					//cancel();
					return;
				}
				if (d == 10) {

					owner.sendMessage(
							ChatColor.BLUE + "GECK power: " + ChatColor.YELLOW + "100 " + ChatColor.GOLD + "%");
				} else {
					owner.sendMessage(
							ChatColor.BLUE + "GECK power: " + ChatColor.YELLOW + d + "0 " + ChatColor.GOLD + "%");
				}
				d--;
				if (d <= 0) {
					owner.sendMessage(ChatColor.BLUE + "GECK power: " + ChatColor.YELLOW + "0 " + ChatColor.GOLD + "%");
					owner.sendMessage(
							ChatColor.RED + "!!WARNING!! " + ChatColor.BLUE + "GECK is now compleatly powered down!");
					setPowered(false);
					//cancel();
					return;
				}
			}

		}.runTaskTimer(main, 0, 30);
		d = 10;
		main.getRad().removeGeck(this);
	}

}

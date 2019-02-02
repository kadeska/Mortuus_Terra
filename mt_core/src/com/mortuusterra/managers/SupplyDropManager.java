package com.mortuusterra.managers;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mortuusterra.MortuusTerraMain;

public class SupplyDropManager {
	private MortuusTerraMain main;
	private World world;
	private Chest chest;
	private Block chestBlock;
	private Location l;

	public SupplyDropManager() {
		
	}
	
	public void drop(World world, MortuusTerraMain m) {
		this.main = m;
		this.world = world;
		l = makeLocation();
		//location is only null if there are no players online. 
		if(l == null) return;
		start(l);
	}

	public Chest getChest() {
		return chest;
	}

	/**
	 * 
	 * @return The average location of all online players. if there is only one
	 *         player online then shift the location away from the player
	 */
	private Location makeLocation() {
		if(main.getServer().getOnlinePlayers().size() == 0) return null;
		ArrayList<Integer> Xlist = new ArrayList<Integer>();
		ArrayList<Integer> Zlist = new ArrayList<Integer>();
		int aX = 0;
		int aZ = 0;
		for (Player p : main.getServer().getOnlinePlayers()) {
			Xlist.add((int) p.getLocation().getX());
			Zlist.add((int) p.getLocation().getZ());
		}
		for (int xx : Xlist) {
			aX = (aX + xx) / Xlist.size();
		}
		for (int zz : Zlist) {
			aZ = (aZ + zz) / Zlist.size();
		}
		int y = world.getHighestBlockYAt(aX, aZ);
		if (main.getServer().getOnlinePlayers().size() == 1) {
			aX = aX + 10;
			aZ = aZ + 10;
		}
		return new Location(world, aX, y, aZ);
	}

	private void spawnChestAt(Location l) {
		l.getBlock().setType(Material.CHEST);
		/*chestBlock = l.getBlock(); 
		chest = (Chest) chestBlock;
		chest.setCustomName("Supply Drop");
		chest.update();
		chest.getInventory().addItem(new ItemStack(Material.APPLE, 1));
		for (Player p : main.getServer().getOnlinePlayers()) {
			p.sendMessage(ChatColor.RED + "!!ANOUNCMENT!!"
					+ (ChatColor.BLUE + " SupplyDrop has been dropped at: " + ChatColor.GOLD + "X" + ChatColor.GRAY
							+ " = " + ChatColor.YELLOW + (int) l.getX() + ChatColor.GRAY + ", " + ChatColor.GOLD + "Y"
							+ ChatColor.GRAY + " = " + ChatColor.YELLOW + (int) l.getY() + ChatColor.GRAY + ", "
							+ ChatColor.GOLD + "Z" + ChatColor.GRAY + " = " + ChatColor.YELLOW + (int) l.getZ()));
		}*/
		return;
	}

	private void start(Location location) {
		for (int i = 0; i <= 3; i++) {
			world.strikeLightningEffect(location);
			world.createExplosion(location, 1); //problem line
		}
		Location l = new Location(world, location.getX(), world.getHighestBlockYAt(location), location.getZ());
		BlockFace[] faces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP,
				BlockFace.DOWN };
		for (BlockFace f : faces) {
			l.getBlock().getRelative(f).setType(Material.MOSSY_COBBLESTONE);
		}
		spawnChestAt(l);
		return;
	}

}

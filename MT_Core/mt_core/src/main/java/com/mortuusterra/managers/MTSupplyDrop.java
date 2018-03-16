package main.java.com.mortuusterra.managers;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.com.mortuusterra.MortuusTerraMain;

public class MTSupplyDrop {
	private MortuusTerraMain main;
	private World world;
	private Chest chest;
	private Location l;

	public MTSupplyDrop(World world, MortuusTerraMain m) {
		this.main = m;
		this.world = world;
		l = makeLocation();
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
		chest = (Chest) l.getBlock().getState();
		chest.setCustomName("Supply Drop");
		chest.update();
		chest.getInventory().addItem(new ItemStack(Material.APPLE, 1));
		for (Player p : main.getServer().getOnlinePlayers()) {
			p.sendMessage(ChatColor.RED + "!!ANOUNCMENT!!"
					+ (ChatColor.BLUE + " SupplyDrop has been dropped at: " + ChatColor.GOLD + "X" + ChatColor.GRAY
							+ " = " + ChatColor.YELLOW + (int) l.getX() + ChatColor.GRAY + ", " + ChatColor.GOLD + "Y"
							+ ChatColor.GRAY + " = " + ChatColor.YELLOW + (int) l.getY() + ChatColor.GRAY + ", "
							+ ChatColor.GOLD + "Z" + ChatColor.GRAY + " = " + ChatColor.YELLOW + (int) l.getZ()));
		}
		return;
	}

	private void start(Location location) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (int i = 0; i <= 3; i++) {
					world.strikeLightningEffect(location);
					world.createExplosion(location, 1);
				}
				Location l = new Location(world, location.getX(), world.getHighestBlockYAt(location), location.getZ());
				BlockFace[] faces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP,
						BlockFace.DOWN };
				for (BlockFace f : faces) {
					l.getBlock().getRelative(f).setType(Material.MOSSY_COBBLESTONE);
				}
				spawnChestAt(l);
			}
		}.runTask(main);
		return;
	}

}

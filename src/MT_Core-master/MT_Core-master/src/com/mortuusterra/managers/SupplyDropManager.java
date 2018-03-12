/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.misc.SupplyDropContent;
import com.mortuusterra.objects.SupplyDrop;
import com.mortuusterra.utils.files.FileType;
import com.mortuusterra.utils.files.PluginFile;
import com.mortuusterra.utils.others.StringUtilities;

public class SupplyDropManager {

	private MortuusTerraCore main;
	private PluginFile file;

	private List<SupplyDrop> supplyDrops;
	private List<SupplyDropContent> supplyContent;

	public SupplyDropManager(MortuusTerraCore main) {
		this.main = main;
		supplyDrops = new ArrayList<>();
		supplyContent = new ArrayList<>();
	}

	public void deliverSupplyDrop(World world) {
		Random r = new Random();

		// Supply drops between -500, + 500
		double x = r.nextInt(1000) - 500;
		double y = 0.0D;
		double z = r.nextInt(1000) - 500;

		Location dropLocation = new Location(world, x, y, z);

		// Get the highest block at that location.
		dropLocation.setY(world.getHighestBlockYAt(dropLocation));

		dropLocation.getBlock().setType(Material.CHEST);
		Chest dropChest = (Chest) dropLocation.getBlock().getState();

		SupplyDrop supplyDrop = new SupplyDrop(dropLocation, dropChest, dropChest.getInventory());
		addSupplyDrop(supplyDrop);

		for (Player p : world.getPlayers()) {
			p.sendMessage(MortuusTerraCore.ALERT_PREFIX + StringUtilities
					.color("&eSupply Drop spotted at: &6" + x + ", " + dropLocation.getY() + ", " + z + "&e!"));
		}
	}

	public SupplyDrop getSupplyDropByLocation(Location location) {
		for (SupplyDrop sd : supplyDrops) {
			if (sd.getDropLocation().equals(location)) {
				return sd;
			}
		}
		return null;
	}

	public boolean isSupplyDrop(Location location) {
		for (SupplyDrop sd : supplyDrops) {
			if (sd.getDropLocation().equals(location)) {
				return true;
			}
		}
		return false;
	}

	public void addSupplyDrop(SupplyDrop sd) {
		supplyDrops.add(sd);
	}

	public void removeSupplyDrop(Location location) {
		supplyDrops.remove(getSupplyDropByLocation(location));
	}

	public boolean isEmpty(Location location) {
		SupplyDrop sd = getSupplyDropByLocation(location);
		if (sd == null)
			return true;

		for (int i = 0; i < sd.getDropInventory().getSize(); i++) {
			if (sd.getDropInventory().getContents()[i] != null)
				return false;
		}
		return true;
	}

	public Inventory fillSupplyDropContent(Inventory inventory) {
		
		Random r = new Random();
		for (SupplyDropContent content : supplyContent) {
			if (r.nextInt(100) < content.getItemChance()) {

				int slot = r.nextInt(inventory.getSize());
				// Add the items to the inventory on random slots.
				while (inventory.getContents()[slot] != null)
					slot = r.nextInt(inventory.getSize());
				inventory.setItem(slot, new ItemStack(content.getItemMaterial(), content.getItemAmount()));
			}
		}
		
		// A 5 % chance to add a Generator item
		if (r.nextInt(100) < 5)
			inventory.addItem(RecipeManager.getGenerator());

		return inventory;
	}

	public void loadSupplyData() {
		file = new PluginFile(main, "supplyDrops", FileType.YAML);
		YamlConfiguration config = file.returnYaml();

		// Supply content
		// If no content for supply drops is set in config, set a default one to
		// avoid a NPE.
		if (config.getConfigurationSection("supply-drops.items") == null) {
			main.getLogger().info("No Supplydrop content found. Setting defaults.");
			String path = "supply-drops.items.0";
			config.set(path + ".material", "STONE");
			config.set(path + ".chance", 75);
			config.set(path + ".amount", 32);
			file.save(config);
		}

		for (String key : config.getConfigurationSection("supply-drops.items").getKeys(false)) {

			Material itemMaterial = Material
					.valueOf(config.getString("supply-drops.items." + key + ".material").toUpperCase());
			int itemChance = config.getInt("supply-drops.items." + key + ".chance");
			int itemAmount = config.getInt("supply-drops.items." + key + ".amount");

			supplyContent.add(new SupplyDropContent(itemMaterial, itemChance, itemAmount));
		}

		// Supply drops
		for (String s : config.getStringList("supply-drops.objects")) {
			Location loc = StringUtilities.stringToLocation(s);

			loc.getBlock().setType(Material.CHEST);
			Chest chest = (Chest) loc.getBlock().getState();
			supplyDrops.add(new SupplyDrop(loc, chest, chest.getInventory()));
		}
	}

	public void saveSupplyData() {
		YamlConfiguration config = file.returnYaml();

		List<String> locString = new ArrayList<>();
		for (SupplyDrop sd : supplyDrops) {
			locString.add(StringUtilities.locationToString(sd.getDropLocation()));
		}

		config.set("supply-drops.objects", locString);
		file.save(config);
	}

	public List<SupplyDrop> getSupplyDrops() {
		return supplyDrops;
	}

	public List<SupplyDropContent> getSupplyContent() {
		return supplyContent;
	}

}

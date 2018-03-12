/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.listeners;

import static com.mortuusterra.utils.others.WorldUtils.getNearbyBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Lever;
import org.bukkit.scheduler.BukkitRunnable;

import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.managers.RecipeManager;
import com.mortuusterra.utils.files.FileType;
import com.mortuusterra.utils.files.PluginFile;
import com.mortuusterra.utils.others.ListUtils;
import com.mortuusterra.utils.others.ManyMap;
import com.mortuusterra.utils.others.StringUtilities;
import com.mortuusterra.utils.others.WorldUtils;

/**
 * Listens and manages generators. 9/22/17
 * 
 * @author Horsey
 *
 */
public class GeneratorListener implements Listener {
	private MortuusTerraCore main;
	public GeneratorListener(MortuusTerraCore main) {
		this.main = main;
	}

	// Variables :D
	private PluginFile file;

	// A special hierarchical map, in the order of World -> Chunk -> Location to
	// make it as fast as possible
	private Map<String, ManyMap<String, Location>> powerable = new HashMap<>();
	private Map<String, ManyMap<String, Location>> generators = new HashMap<>();
	private List<Location> inUse = new ArrayList<>();

	// Initailize file; lists from config. Called when server is starting
	public void loadFile() { // Something in here is throwing a NPE 
		file = new PluginFile(main, "generators", FileType.YAML);
		for (World w : Bukkit.getWorlds()) {
			powerable.put(w.getName(), new ManyMap<>());
			generators.put(w.getName(), new ManyMap<>());
		}

		YamlConfiguration config = file.returnYaml();

		for (String locString : config.getStringList("gens")) {
			Location loc = StringUtilities.stringToLocation(locString);

			ManyMap<String, Location> mm = generators.get(loc.getWorld().getName());
			mm.addValue(loc.getChunk().getX() + ";" + loc.getChunk().getZ(), loc);
			generators.put(loc.getWorld().getName(), mm);

			for (Block bloc : getNearbyBlocks(loc.getBlock(), 15)) {
				Location ploc = bloc.getLocation();
				ManyMap<String, Location> pmm = powerable.get(ploc.getWorld().getName());
				pmm.addValue(ploc.getChunk().getX() + ";" + ploc.getChunk().getZ(), ploc);
				powerable.put(ploc.getWorld().getName(), pmm);
			}
		}

	}

	// Called when the server is shutting down to save the file
	public void saveFile() {
		YamlConfiguration config = file.returnYaml();
		List<String> toSave = new ArrayList<>();
		for (ManyMap<String, Location> mm : generators.values()) {
			for (List<Location> vals : mm.values()) {
				for (Location loc : vals) {
					toSave.add(StringUtilities.locationToString(loc));
				}
			}
		}
		config.set("gens", toSave);
		file.save(config);
	}

	// Called when a player tries to interact with a generator while it's
	// turning on/off
	@EventHandler
	public void onInteractBusy(PlayerInteractEvent event) {
		if (event.getClickedBlock() == null)
			return;
		if (!inUse.contains(event.getClickedBlock().getLocation()))
			return;

		event.setCancelled(true);

		if (event.getHand() == EquipmentSlot.HAND)
			event.getPlayer().sendMessage(ChatColor.RED + "That generator is busy right now!");
	}

	// Called when a player tries to break a generator while it's turning on/off
	@EventHandler
	public void onBreakBusy(BlockBreakEvent event) {
		if (!inUse.contains(event.getBlock().getLocation()))
			return;

		event.setCancelled(true);

		event.getPlayer().sendMessage(ChatColor.RED + "That generator is busy right now!");
	}

	// Called when a player places a generator
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block placedBlock = event.getBlock();
		Player player = event.getPlayer();
		ItemStack inHand = event.getItemInHand();

		String chunk = placedBlock.getLocation().getChunk().getX() + ";" + placedBlock.getLocation().getChunk().getZ();

		if (placedBlock.getType() == Material.FURNACE) {
			if (main.getGeneratorManager().isGeneratorBuildCorrect(placedBlock)) {

				if (!inHand.hasItemMeta())
					return;

				if (!inHand.getItemMeta().getDisplayName().equalsIgnoreCase(RecipeManager.GENERATOR_NAME))
					return;

				// A generator was placed
				inUse.add(placedBlock.getLocation());

				player.sendMessage(MortuusTerraCore.NOTI_PREFIX + ChatColor.GREEN + " Powering up generator...");

				// Run everything async; creates a dramatic delay + saves
				// performance
				new BukkitRunnable() {

					@Override
					public void run() {
						List<Block> blocks = getNearbyBlocks(placedBlock, 15);

						for (Block block : blocks) {
							String chunk = block.getLocation().getChunk().getX() + ";"
									+ block.getLocation().getChunk().getZ();

							ManyMap<String, Location> mm = powerable.get(block.getLocation().getWorld().getName());
							mm.addValue(chunk, block.getLocation());
							powerable.put(block.getLocation().getWorld().getName(), mm);

						}

						new BukkitRunnable() {
							@Override
							public void run() {
								player.sendMessage(
										MortuusTerraCore.NOTI_PREFIX + ChatColor.GREEN + " Generator Ready!");
								inUse.remove(placedBlock.getLocation());
							}
						}.runTask(main);

					}
				}.runTaskLaterAsynchronously(main, 10L); // It's a little too
															// fast.
				for (Block block : getNearbyBlocks(placedBlock, 15)) {
					// Power redstone
					if (block.getType() == Material.REDSTONE_TORCH_OFF)
						block.setType(Material.REDSTONE_TORCH_ON);

					// Tries to update the wire. This doesn't work all
					// the time.
					// Sometimes a wire won't turn on eventhough it is
					// powered by a torch for example.
					if (block.getType() == Material.REDSTONE_WIRE) {
						block.setData((byte) 1, true);
						block.setData((byte) 0, true);
					}
				}

				Location generatorLoc = placedBlock.getLocation();

				// add the generator
				ManyMap<String, Location> mm = generators.get(generatorLoc.getWorld().getName());
				mm.addValue(generatorLoc.getChunk().getX() + ";" + generatorLoc.getChunk().getZ(), generatorLoc);
				generators.put(generatorLoc.getWorld().getName(), mm);
			}

			// Disable torches
		} else if (inHand.getType() == Material.REDSTONE_TORCH_ON && !generators.get(placedBlock.getWorld().getName())
				.getList(chunk).contains(placedBlock.getLocation())) {
			placedBlock.setType(Material.REDSTONE_TORCH_OFF);
		}
	}

	// Called when a player breaks a generator
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onGeneratorBreak(BlockBreakEvent event) {
		Block brokenBlock = event.getBlock();
		Player player = event.getPlayer();

		String chunk = brokenBlock.getLocation().getChunk().getX() + ";" + brokenBlock.getLocation().getChunk().getZ();

		if (!generators.containsKey(brokenBlock.getWorld().getName()))
			return;

		if (!generators.get(brokenBlock.getWorld().getName()).getList(chunk).contains(brokenBlock.getLocation()))
			return;

		player.sendMessage(MortuusTerraCore.NOTI_PREFIX + ChatColor.RED + " Un-powering generator");

		inUse.add(brokenBlock.getLocation());
		event.setCancelled(true);

		// Split the ~28000 blocks into 300 blocks to scan every tick; sounds
		// like a lot, but it's fast enough.
		int i = 0;
		List<List<Block>> sublists = ListUtils.subList(WorldUtils.getNearbyBlocks(brokenBlock, 15), 300);
		for (List<Block> blocks : sublists) {
			new BukkitRunnable() {

				@Override
				public void run() {
					for (Block block : blocks) {
						// Unregister the block
						ManyMap<String, Location> mm = powerable.get(block.getWorld().getName());
						String chunks = block.getLocation().getChunk().getX() + ";"
								+ block.getLocation().getChunk().getZ();

						mm.removeValue(chunks, block.getLocation());

						// Check if there is a geck in range and remove it
						main.getGeckObjectManager().removeGeckLocation(block.getLocation());

						// revert the block to un-powered (if it can be)
						if (block.getType() == Material.REDSTONE_WIRE)
							block.setData((byte) 0, true);

						else if (block.getType() == Material.REDSTONE_TORCH_ON)
							block.setType(Material.REDSTONE_TORCH_OFF);

						else if (block.getType() == Material.LEVER) {
							BlockState state = block.getState();
							Lever lever = (Lever) state.getData();
							lever.setPowered(false);
							state.setData(lever);
							state.update();
						}

						powerable.put(block.getWorld().getName(), mm);
					}
				}
			}.runTaskLater(main, ++i);
		}
		// Later we drop the generator
		new BukkitRunnable() {

			@Override
			public void run() {
				player.sendMessage(MortuusTerraCore.NOTI_PREFIX + ChatColor.RED + " Generator deactivated!");
				brokenBlock.setType(Material.AIR);
				brokenBlock.getWorld().dropItemNaturally(brokenBlock.getLocation(),
						new ItemStack(RecipeManager.getGenerator()));
				inUse.remove(brokenBlock.getLocation());
			}

		}.runTaskLater(main, i);

		// We unregister the generator
		ManyMap<String, Location> mm = generators.get(brokenBlock.getWorld().getName());

		mm.removeValue(chunk, brokenBlock.getLocation());

		generators.put(brokenBlock.getWorld().getName(), mm);
	}

	// Called when a player tries to activate a lever or a button
	@EventHandler
	public void onLeverOrButton(PlayerInteractEvent event) {
		Block clickedBlock = event.getClickedBlock();
		Player player = event.getPlayer();

		if (clickedBlock == null)
			return;

		String chunk = clickedBlock.getLocation().getChunk().getX() + ";"
				+ clickedBlock.getLocation().getChunk().getZ();
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (!powerable.containsKey(clickedBlock.getWorld().getName()))
			return;
		if (powerable.get(clickedBlock.getWorld().getName()).getList(chunk).contains(clickedBlock.getLocation()))
			return;

		// We cancel; send smoke particles for button, and we just turn off the
		// lever (if it was, for some reason, on).
		if (clickedBlock.getType() == Material.STONE_BUTTON || clickedBlock.getType() == Material.WOOD_BUTTON) {

			clickedBlock.getWorld().spawnParticle(Particle.SMOKE_NORMAL, clickedBlock.getLocation().add(0.5, 1, 0.5), 7,
					0, 0.2, 0, 0.03);
			player.sendMessage(MortuusTerraCore.NOTI_PREFIX + ChatColor.RED + " There is no generator in range!");

		} else if (clickedBlock.getType() == Material.LEVER) {
			BlockState state = clickedBlock.getState();
			Lever lever = (Lever) state.getData();

			lever.setPowered(false);
			state.setData(lever);
			state.update();
			clickedBlock.getWorld().spawnParticle(Particle.SMOKE_NORMAL, clickedBlock.getLocation().add(0.5, 1, 0.5), 7,
					0, 0.2, 0, 0.03);
			player.sendMessage(MortuusTerraCore.NOTI_PREFIX + ChatColor.RED + " There is no generator in range!");
		}

	}

	// Called when a redstone current moves thru a block
	@EventHandler
	public void onPower(BlockRedstoneEvent event) {
		Block block = event.getBlock();
		String chunk = block.getLocation().getChunk().getX() + ";" + block.getLocation().getChunk().getZ();

		if (!powerable.get(block.getWorld().getName()).getList(chunk).contains(block.getLocation())) {
			block.getWorld().spawnParticle(Particle.SMOKE_NORMAL, block.getLocation().add(0.5, 1, 0.5), 7, 0, 0.2, 0,
					0.03);
			event.setNewCurrent(0);
		}
	}

	public Map<String, ManyMap<String, Location>> getPowerable() {
		return powerable;
	}

	public Map<String, ManyMap<String, Location>> getGenerators() {
		return generators;
	}

	public List<Location> getInUse() {
		return inUse;
	}

}

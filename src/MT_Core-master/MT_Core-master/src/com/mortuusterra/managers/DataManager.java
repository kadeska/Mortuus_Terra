package com.mortuusterra.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.utils.files.FileType;
import com.mortuusterra.utils.files.PluginFile;

public class DataManager {
	
	private PluginFile file;
	private MortuusTerraCore main;
	
	private List<World> supplyDropWorlds;
	
	private double defaultRadiationDamage;
	private double stormRadiationDamage;
	private double waterRadiationDamage;
	private int radiationDamageDelay;

	public DataManager(MortuusTerraCore core) {
		this.main = core;
		supplyDropWorlds = new ArrayList<>();
	}
	
	public void loadData() {
		file = new PluginFile(main, "data", FileType.YAML);
		YamlConfiguration config = file.returnYaml();
		
		checkDefaults();
		
		// Supplydrops related.
		for (String worldString : config.getStringList("supply-drops.enabled-worlds")) {
			supplyDropWorlds.add(Bukkit.getWorld(worldString));
		}
		
		// Radiation related.
		this.defaultRadiationDamage = config.getDouble("radiation.default-damage");
		this.stormRadiationDamage = config.getDouble("radiation.storm-damage");
		this.waterRadiationDamage = config.getDouble("radiation.water-damage");
		this.radiationDamageDelay = config.getInt("radiation.seconds-delay");
	}
	
	/**
	 * Checks if any configuration sections is null and sets a default
	 * to avoid a NPE.
	 */
	private void checkDefaults() {
		YamlConfiguration config = file.returnYaml();
		main.getLogger().info("Checking for defaults inside data.yml");
		
		if (config.getStringList("supply-drops.enabled-worlds") == null || config.getStringList("supply-drops.enabled-worlds").isEmpty()) {
			config.set("supply-drops.enabled-worlds", Arrays.asList("world"));
		}
		
		if (config.get("radiation.default-damage") == null)
			config.set("radiation.default-damage", 1D);
		
		if (config.get("radiation.storm-damage") == null)
			config.set("radiation.storm-damage", 1D);
		
		if (config.get("radiation.water-damage") == null)
			config.set("radiation.water-damage", 2D);
		
		if (config.get("radiation.seconds-delay") == null)
			config.set("radiation.seconds-delay", 3);
		
		file.save(config);
		main.getLogger().info("Saved defaults.");
	}
	
	public List<World> getSupplyDropWorlds() {
		return supplyDropWorlds;
	}
	
	public double getDefaultRadiationDamage() {
		return defaultRadiationDamage;
	}
	
	public double getStormRadiationDamage() {
		return stormRadiationDamage;
	}
	
	public double getWaterRadiationDamage() {
		return waterRadiationDamage;
	}

	public int getRadiationDamageDelay() {
		return radiationDamageDelay;
	}
}

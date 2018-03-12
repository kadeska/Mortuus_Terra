/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */

package com.mortuusterra;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mortuusterra.commands.AdminCommands;
import com.mortuusterra.listeners.GeckPowerListener;
import com.mortuusterra.listeners.GeneratorListener;
import com.mortuusterra.listeners.MobListener;
import com.mortuusterra.listeners.PlayerListener;
import com.mortuusterra.listeners.SupplyDropListener;
import com.mortuusterra.listeners.WorldListener;
import com.mortuusterra.managers.DataManager;
import com.mortuusterra.managers.GeckManager;
import com.mortuusterra.managers.GeckObjectManager;
import com.mortuusterra.managers.GeneratorManager;
import com.mortuusterra.managers.MobManager;
import com.mortuusterra.managers.PlayerManager;
import com.mortuusterra.managers.RadiationManager;
import com.mortuusterra.managers.RecipeManager;
import com.mortuusterra.managers.SupplyDropManager;
import com.mortuusterra.misc.CustomScoreboards;
import com.mortuusterra.utils.files.FileManager;
import com.mortuusterra.utils.nmsentities.CustomEntityType;
import com.mortuusterra.utils.others.StringUtilities;
import com.mortuusterra.utils.others.SupplyDropTimer;

public class MortuusTerraCore extends JavaPlugin {
	/*
	 * List of contributors Kadeska23 Shyos Horsey Andrewbow159
	 */

	private final MortuusTerraCore core = this;

	/*
	 * These are all of the managers
	 **/
	private CustomScoreboards scoreboards;
	private FileManager fileManager;
	private PlayerManager playerMan;
	private RadiationManager radMan;
	private GeckObjectManager geckObjectManager;
	private GeckManager geckManager;
	private MobManager mobManager;
	private RecipeManager recipeManager;
	private SupplyDropManager supplyDropManager;
	private DataManager dataManager;
	private SupplyDropTimer supplyDropTimer;
	private GeneratorManager generatorManager;

	/*
	 * These are all of the listeners
	 **/
	private GeneratorListener genListener;

	/*
	 * These are all of the prefixes
	 **/
	// looks like "[MTCore]"
	public static final String MTC_PREFIX = StringUtilities.color("&7&l[&b&lMTCore&7&l]");
	// looks like "[!]"
	public static final String NOTI_PREFIX = StringUtilities.color("&7&l[&b&l!&7&l] ");
	// looks like "[Alert!]"
	public static final String ALERT_PREFIX = StringUtilities.color("&7&l[&c&lAlert!&7&l] ");

	@Override
	public void onEnable() {
		// Console sender
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "|----------|");
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Starting Mortuus Terra.");
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "This plugin is in BETA");

		// register/initiate listeners
		registerListeners();

		// register/initiate managers
		initiateManagers();

		// register/initiate recipes
		registerRecipes();

		// register custom Zombie
		CustomEntityType.DAY_ZOMBIE.registerEntity();

		// register/initiate Commands
		getCommand("supplydrop").setExecutor(new AdminCommands(this));

		// Load files
		fileManager = new FileManager(this);
		getFileManager().loadFiles();

		// start radiation
		getRadiationManager().startPlayerRadiationDamage();

		// Start supplydrops for enabled worlds
		for (World world : getDataManager().getSupplyDropWorlds()) {
			getSupplyDropTimer().startSupplyDropTimer(world);
		}

		// Console sender
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Mortuus Terra ready.");
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "|----------|");
	}

	@Override
	public void onDisable() {
		// Console sender
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "|----------|");
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Stoping Mortuus Terra.");

		// Save every file to disk.
		getFileManager().saveFiles();

		// Console sender
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Mortuus Terra stopped.");
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "|----------|");
	}

	private void registerRecipes() {
		recipeManager = new RecipeManager(this);
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();

		manager.registerEvents(new PlayerListener(this), this);
		manager.registerEvents(new MobListener(this), this);
		manager.registerEvents(new WorldListener(this), this);
		manager.registerEvents(new GeneratorListener(this), this);
		manager.registerEvents(new GeckPowerListener(this), this);
		manager.registerEvents(new SupplyDropListener(this), this);
	}

	private void initiateManagers() {
		dataManager = new DataManager(this);
		playerMan = new PlayerManager(this);
		mobManager = new MobManager(this);
		radMan = new RadiationManager(this);
		geckObjectManager = new GeckObjectManager(this);
		geckManager = new GeckManager(this);
		generatorManager = new GeneratorManager(this);
		
		scoreboards = new CustomScoreboards(this);
		
		supplyDropManager = new SupplyDropManager(this);
		supplyDropTimer = new SupplyDropTimer(this);
	}

	public MortuusTerraCore getCore() {
		return core;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public GeneratorManager getGeneratorManager() {
		return generatorManager;
	}

	public SupplyDropTimer getSupplyDropTimer() {
		return supplyDropTimer;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public CustomScoreboards getScoreboards() {
		return scoreboards;
	}

	public GeneratorListener getGenListener() {
		return genListener;
	}

	public MobManager getMobManager() {
		return mobManager;
	}

	public PlayerManager getPlayerManager() {
		return playerMan;
	}

	public RadiationManager getRadiationManager() {
		return radMan;
	}

	public RecipeManager getCellTowerRecipe() {
		return recipeManager;
	}

	public GeckObjectManager getGeckObjectManager() {
		return geckObjectManager;
	}

	public GeckManager getGeckManager() {
		return geckManager;
	}

	public SupplyDropManager getSupplyDropManager() {
		return supplyDropManager;
	}
}
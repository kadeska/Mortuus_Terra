package com.mortuusterra;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import com.mortuusterra.listeners.ChunkListener;
import com.mortuusterra.listeners.CommunicationListener;
import com.mortuusterra.listeners.GeckListener;
import com.mortuusterra.listeners.GeneratorListener;
import com.mortuusterra.listeners.MobListener;
import com.mortuusterra.listeners.PlayerListener;
import com.mortuusterra.listeners.PowerListener;
import com.mortuusterra.managers.CommunicationChannelsManager;
import com.mortuusterra.managers.FalloutShelterManager;
import com.mortuusterra.managers.InfectManager;
import com.mortuusterra.managers.RadiationManager;
import com.mortuusterra.managers.SupplyDropManager;
import com.mortuusterra.managers.CommandsManager;
import com.mortuusterra.tasks.InfectTask;
import com.mortuusterra.tasks.RadiationTask;
import com.mortuusterra.tasks.ScoreboardTask;
import com.mortuusterra.tasks.TimerTask;
import com.mortuusterra.util.Data;

public class MortuusTerraMain extends JavaPlugin {

	private static boolean debugMode = false;

	// private FileManager fm;
	private CommandsManager cmd;
	private CommunicationChannelsManager communicationChannels;
	private RadiationManager rad;
	private TimerTask mttimer;
	private FalloutShelterManager shelterManager;

	private PlayerListener pl;
	private CommunicationListener communicationListener;
	private GeckListener geck;
	private GeneratorListener genListener;
	private PowerListener power;
	private ChunkListener chunkListener;
	private MobListener mobListener;
	private InfectTask infectTask;
	private InfectManager infect;
	private Data fm;
	private RadiationTask mtradtask;

	@Override
	public void onEnable() {
		setFileManager(new Data(this));
		cmd = new CommandsManager(this);
		getCommand("channel").setExecutor(cmd);
		getCommand("mortuusterra").setExecutor(cmd);
		communicationChannels = new CommunicationChannelsManager(this);
		rad = new RadiationManager(this);
		//shelterManager = new MTFalloutShelter();

		power = new PowerListener(this);
		getServer().getPluginManager().registerEvents(power, this);

		pl = new PlayerListener(this);
		getServer().getPluginManager().registerEvents(pl, this);

		geck = new GeckListener(this);
		getServer().getPluginManager().registerEvents(geck, this);

		genListener = new GeneratorListener(this);
		getServer().getPluginManager().registerEvents(genListener, this);

		communicationListener = new CommunicationListener(this);
		getServer().getPluginManager().registerEvents(communicationListener, this);

		chunkListener = new ChunkListener(this);
		getServer().getPluginManager().registerEvents(chunkListener, this);

		mobListener = new MobListener(this);
		getServer().getPluginManager().registerEvents(mobListener, this);

		setInfectTask(new InfectTask(this));

		infect = new InfectManager();
		getServer().getPluginManager().registerEvents(infect, this);
		

		ScoreboardTask scTask = new ScoreboardTask(this);
		getServer().getPluginManager().registerEvents(scTask, this);

		// load this last
		startSupplydrops();
	}

	@Override
	public void onDisable() {
		/*
		 * for (Player p : getServer().getOnlinePlayers()) {
		 * p.sendMessage(ChatColor.DARK_RED +
		 * "Server is restarting. You will be kicked from the server!");
		 * p.kickPlayer("Server restart, come back soon!"); }
		 */

		// ToDo: Need to save all online players to a file and then load in the players
		// from file onEnable. Or check when the server is reloaded save the players
		// then reload the players in the MT Hashmaps
		stopSupplyDrops();
	}

	private void startSupplydrops() {
		// start server anouncment
		mttimer = new TimerTask(getCore(), false, 0, 1) {
			double timePassed = 0.0;
			// 3 1/2 hours (252000 ticks)
			int time = 252000;

			@Override
			public void run() {
				// Message every hour
				if (timePassed % 72000 == 0) {
					announceServer();
				}
				// Message the last 30, 10, 5 , 1 minutes
				if (timePassed % 72000 == 0.5 || timePassed % 72000 == (1 / 6) || timePassed % 72000 == (1 / 12)
						|| timePassed % 72000 == (1 / 60)) {
					announceServer();
				}
				// if the time has passed then deliver the supply drop
				if (timePassed >= time) {
					// mtsupplydrop = null;
					new SupplyDropManager(getCore().getServer().getWorld("world"), getCore());
				}
				timePassed++;
			}

			private void announceServer() {
				int seconds = (int) ((time - timePassed) / 20);
				int minutes = (int) (seconds / 60);
				int hours = (int) (minutes / 60);
				// seconds = seconds % 60;
				for (Player p : getCore().getServer().getOnlinePlayers()) {
					p.sendMessage(ChatColor.RED + "!!ANOUNCMENT!!"
							+ (ChatColor.BLUE + " Next Supply Drop in: " + ChatColor.GOLD + hours + ChatColor.YELLOW
									+ " Hours, " + ChatColor.GOLD + minutes + ChatColor.YELLOW + " Minutes, "
									+ ChatColor.GOLD + seconds + ChatColor.YELLOW + " Seconds."));
				}
			}
		};
	}

	private void stopSupplyDrops() {
		if (mttimer == null) {
			return;
		}
		mttimer.stop();
	}

	public void callEvent(Event event) {
		Bukkit.getServer().getPluginManager().callEvent(event);
	}

	public void notifyConsole(String text) {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + text);
	}

	public final MortuusTerraMain getCore() {
		return this;
	}

	public FalloutShelterManager getShelterManager() {
		return shelterManager;
	}

	public CommandsManager getCommands() {
		return cmd;
	}
	/*
	 * public FileManager getFileManager() { return fm; }
	 */

	public TimerTask getMttimer() {
		return mttimer;
	}

	public CommunicationChannelsManager getCommunicationChannels() {
		return communicationChannels;
	}

	public RadiationManager getRad() {
		return rad;
	}

	public PlayerListener getPl() {
		return pl;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		MortuusTerraMain.debugMode = debugMode;
	}

	public InfectTask getInfectTask() {
		return infectTask;
	}

	public void setInfectTask(InfectTask infectTask) {
		this.infectTask = infectTask;
	}

	public Data getFileManager() {
		return fm;
	}

	public void setFileManager(Data fm) {
		this.fm = fm;
	}
}

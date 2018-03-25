package com.mortuusterra;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

<<<<<<< HEAD:MT_Core/mt_core/src/com/mortuusterra/MortuusTerraMain.java
import com.mortuusterra.commands.MTcommands;
import com.mortuusterra.listeners.MTChunkListener;
import com.mortuusterra.listeners.MTCommunication;
import com.mortuusterra.listeners.MTGeckListener;
import com.mortuusterra.listeners.MTGeneratorListener;
import com.mortuusterra.listeners.MTMobListener;
import com.mortuusterra.listeners.MTPlayerListener;
import com.mortuusterra.listeners.MTPower;
import com.mortuusterra.managers.MTCommunicationChannels;
import com.mortuusterra.managers.MTFalloutShelter;
import com.mortuusterra.managers.MTInfect;
import com.mortuusterra.managers.MTRadiation;
import com.mortuusterra.managers.MTSupplyDrop;
import com.mortuusterra.tasks.MTInfectTask;
import com.mortuusterra.tasks.MTTimer;
import com.mortuusterra.util.MTfile;
=======
import main.java.com.mortuusterra.command.MTcommands;
import main.java.com.mortuusterra.event.MTRadiationDamageEvent;
import main.java.com.mortuusterra.listener.MTChunkListener;
import main.java.com.mortuusterra.listener.MTCommunication;
import main.java.com.mortuusterra.listener.MTGeckListener;
import main.java.com.mortuusterra.listener.MTGeneratorListener;
import main.java.com.mortuusterra.listener.MTMobListener;
import main.java.com.mortuusterra.listener.MTPlayerListener;
import main.java.com.mortuusterra.listener.MTPower;
import main.java.com.mortuusterra.manager.MTCommunicationChannels;
import main.java.com.mortuusterra.manager.MTFalloutShelter;
import main.java.com.mortuusterra.manager.MTRadiation;
import main.java.com.mortuusterra.manager.MTSupplyDrop;
import main.java.com.mortuusterra.manager.MTTimer;
>>>>>>> 700e479bc563f8ebc254cfa1657461bd95b5c450:MT_Core/mt_core/src/main/java/com/mortuusterra/MortuusTerraMain.java

public class MortuusTerraMain extends JavaPlugin {

	private static boolean debugMode = false;

	// private FileManager fm;
	private MTcommands cmd;
	private MTCommunicationChannels communicationChannels;
	private MTRadiation rad;
	private MTTimer mttimer;
	private MTFalloutShelter shelterManager;

	private MTPlayerListener pl;
	private MTCommunication communicationListener;
	private MTGeckListener geck;
	private MTGeneratorListener genListener;
	private MTPower power;
	private MTChunkListener chunkListener;
	private MTMobListener mobListener;
	private MTInfectTask infectTask;
	private MTInfect infect;
	private MTfile fm;

	@Override
	public void onEnable() {
		setFileManager(new MTfile(this));
		cmd = new MTcommands(this);
		getCommand("channel").setExecutor(cmd);
		getCommand("mortuusterra").setExecutor(cmd);
		communicationChannels = new MTCommunicationChannels(this);
		rad = new MTRadiation(this);
		shelterManager = new MTFalloutShelter();

		power = new MTPower(this);
		getServer().getPluginManager().registerEvents(power, this);

		pl = new MTPlayerListener(this);
		getServer().getPluginManager().registerEvents(pl, this);

		geck = new MTGeckListener(this);
		getServer().getPluginManager().registerEvents(geck, this);

		genListener = new MTGeneratorListener(this);
		getServer().getPluginManager().registerEvents(genListener, this);

		communicationListener = new MTCommunication(this);
		getServer().getPluginManager().registerEvents(communicationListener, this);

		chunkListener = new MTChunkListener(this);
		getServer().getPluginManager().registerEvents(chunkListener, this);

		mobListener = new MTMobListener(this);
		getServer().getPluginManager().registerEvents(mobListener, this);
		
		setInfectTask(new MTInfectTask(this));
		
		infect = new MTInfect();
		getServer().getPluginManager().registerEvents(infect, this);

		// load this last
		startSupplydrops();
	}

	@Override
	public void onDisable() {
		// Removed the kick because it gets called on /reload and is annoying. Bukkit kicks players anyways...
		/* for (Player p : getServer().getOnlinePlayers()) {
			p.sendMessage(ChatColor.DARK_RED + "Server is restarting. You will be kicked from the server!");
			p.kickPlayer("Server restart, come back soon!");
		}*/
		stopSupplyDrops();
	}

	private void startSupplydrops() {
		// start server anouncment
		mttimer = new MTTimer(getCore(), true, 0, 1) {
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
					new MTSupplyDrop(getCore().getServer().getWorld("world"), getCore());
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

	public MTFalloutShelter getShelterManager() {
		return shelterManager;
	}

	public MTcommands getCommands() {
		return cmd;
	}
	/*
	 * public FileManager getFileManager() { return fm; }
	 */

	public MTTimer getMttimer() {
		return mttimer;
	}

	public MTCommunicationChannels getCommunicationChannels() {
		return communicationChannels;
	}

	public MTRadiation getRad() {
		return rad;
	}

	public MTPlayerListener getPl() {
		return pl;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		MortuusTerraMain.debugMode = debugMode;
	}

	public MTInfectTask getInfectTask() {
		return infectTask;
	}

	public void setInfectTask(MTInfectTask infectTask) {
		this.infectTask = infectTask;
	}

	public MTfile getFileManager() {
		return fm;
	}

	public void setFileManager(MTfile fm) {
		this.fm = fm;
	}
}

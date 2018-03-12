package com.mortuusterra.utils.others;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mortuusterra.MortuusTerraCore;

public class SupplyDropTimer {

	private MortuusTerraCore core;

	public SupplyDropTimer(MortuusTerraCore core) {
		this.core = core;
	}

	public void startSupplyDropTimer(World world) {

		new BukkitRunnable() {
			int timePassed = 0;

			@Override
			public void run() {
				// 3 1/2 hours (252000 ticks)
				int time = 252000;
				
				// Message every hour
				if (timePassed % 72000 == 0)
					messagePlayers(time - timePassed, world);

				// Message the last 20, 10, 5 , < 1 minutes
				if (timePassed == 228000 || timePassed == 240000 || timePassed == 246000 || timePassed > 250800)
					messagePlayers(time - timePassed, world);
				
				// If time has passed, deliver.
				if (timePassed >= time) {
					core.getSupplyDropManager().deliverSupplyDrop(world);
					timePassed = 0;
				}

				timePassed++;
			}

		}.runTaskTimer(core, 0L, 1L);
	}

	private void messagePlayers(int ticksLeft, World world) {
		int seconds = ticksLeft / 20;
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = seconds % 60;

		for (Player p : world.getPlayers()) {
			p.sendMessage(MortuusTerraCore.ALERT_PREFIX + StringUtilities
					.color("&eNext Supply Drop in: &6" + hours + "h, " + minutes + "m, " + seconds + "s."));
		}
	}
}

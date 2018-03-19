package com.mortuusterra.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.mortuusterra.MortuusTerraMain;

public abstract class MTTimer {
	private MortuusTerraMain main;
	private BukkitTask timer;

	public MTTimer(MortuusTerraMain m, boolean isAsync, int delay, int period) {
		this.main = m;
		if (isAsync) {
			timer = new BukkitRunnable() {
				@Override
				public void run() {
					run();
				}
			}.runTaskTimerAsynchronously(main, delay, period);
		} else {
			timer = new BukkitRunnable() {
				@Override
				public void run() {
					run();
				}
			}.runTaskTimer(main, delay, period);
		}
	}

	public void stop() {
		if (timer.isCancelled()) {
			return;
		}
		timer.cancel();
	}

	public abstract void run();

}

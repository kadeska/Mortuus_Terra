package com.mortuusterra.managers;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.mortuusterra.MortuusTerraMain;

public abstract class MTTimer {
	private MTTimer mttimmer = this;
	private MortuusTerraMain main;
	private BukkitTask timmer;

	public MTTimer(MortuusTerraMain m, boolean isAsync, int delay, int period) {
		this.main = m;
		if (isAsync) {
			timmer = new BukkitRunnable() {
				@Override
				public void run() {
					mttimmer.run();
				}
			}.runTaskTimerAsynchronously(main, delay, period);
		} else {
			timmer = new BukkitRunnable() {
				@Override
				public void run() {
					mttimmer.run();
				}
			}.runTaskTimer(main, delay, period);
		}
	}

	public void stop() {
		if (timmer.isCancelled()) {
			return;
		}
		timmer.cancel();
	}

	public abstract void run();

}

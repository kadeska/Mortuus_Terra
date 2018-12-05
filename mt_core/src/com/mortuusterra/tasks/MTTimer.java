package com.mortuusterra.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.mortuusterra.MortuusTerraMain;

public abstract class MTTimer {
	private MTTimer mtTimer = this;
	private MortuusTerraMain main;
	private BukkitTask timer;

	public MTTimer(MortuusTerraMain m, boolean isAsync, int delay, int period) {
		this.main = m;
		if (isAsync) {
			timer = new BukkitRunnable() {
				@Override
				public void run() {
					mtTimer.run();
				}
			}.runTaskTimerAsynchronously(main, delay, period);
		} else {
			timer = new BukkitRunnable() {
				@Override
				public void run() {
					mtTimer.run();
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

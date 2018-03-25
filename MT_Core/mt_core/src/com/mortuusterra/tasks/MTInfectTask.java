package com.mortuusterra.tasks;

import org.bukkit.Bukkit;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.managers.MTInfect;

public class MTInfectTask extends MTTimer {

	public MTInfectTask(MortuusTerraMain m) {
		super(m, false, 0, 20);
	}

	@Override
	public void run() {
		
		Bukkit.getOnlinePlayers()
			  .stream()
			  .filter(MTInfect::isInfected)
			  .filter(p -> MTInfect.getInfectionEndTime(p) < System.currentTimeMillis())
			  .forEach(MTInfect::removeInfect);
	}

}

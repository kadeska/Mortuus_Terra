package com.mortuusterra.tasks;

import org.bukkit.Bukkit;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.managers.InfectManager;

public class InfectTask extends TimerTask {

	public InfectTask(MortuusTerraMain m) {
		super(m, false, 0, 20);
	}

	@Override
	public void run() {
		
		Bukkit.getOnlinePlayers()
			  .stream()
			  .filter(InfectManager::isInfected)
			  .filter(p -> InfectManager.getInfectionEndTime(p) < System.currentTimeMillis())
			  .forEach(InfectManager::removeInfect);
	}

}

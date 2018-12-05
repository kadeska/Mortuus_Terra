package com.mortuusterra.listeners;

import org.bukkit.Effect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.mortuusterra.MortuusTerraMain;

public class MTPower implements Listener {
	
	private MortuusTerraMain main;

	public MTPower(MortuusTerraMain m) {
		this.main = m;
	}
	
	@EventHandler
	public void onPower(BlockRedstoneEvent e) {
		if(!main.getRad().isInGeneratorRange(e.getBlock())) {
			e.setNewCurrent(0);
			e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.SMOKE, 0);
			return;
		}
		return;
	}

}

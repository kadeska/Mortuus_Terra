package com.mortuusterra.tasks;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.mortuusterra.MortuusTerraMain;

public class MTRadiationTask extends MTTimer {

	private MortuusTerraMain main;
	private ArrayList<Player> list;
	private int listMarker = 0;

	public MTRadiationTask(MortuusTerraMain m, ArrayList<Player> l) {
		super(m, false, 20, 600);
		list = l;
		main = m;
	}

	@Override
	public void run() {
		if (list.isEmpty() == false && listMarker <= list.size() - 1) {
			Player p = list.get(listMarker);
			if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
				return;
			}
			// check if in range of GECK
			if (main.getRad().playerCheck(p) == false) {
				return;
			}
			// check weather
			if (p.getWorld().isThundering()) {
				p.damage(2.1);
				return;
			}
			if (p.getWorld().hasStorm()) {
				p.damage(2.1);
				return;
			}
			// check if player is in water
			if (p.getLocation().getBlock().getType().equals(Material.WATER)) {
				p.damage(3.5);
				return;
			}
			// check armor for full gold and enchanted with protection 5
			// if non of the above apply then do normal damage
			// trigger radiation damage event
			p.damage(0.5);
			listMarker++;
		} else if (listMarker >= list.size()) {
			listMarker = 0;
		}
	}
	
	public void updateList(ArrayList<Player> list) {
		this.list = list;
	}

}

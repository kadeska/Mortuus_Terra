package com.mortuusterra.listeners;

import java.io.IOException;
import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.mortuusterra.MortuusTerraMain;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.world.DataException;

public class MTChunkListener implements Listener {

	private MortuusTerraMain main;

	public MTChunkListener(MortuusTerraMain m) {
		main = m;
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (e.isNewChunk()) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					/**
					//for testing always load a shelter
					try {
						main.getShelterManager().loadSchematic(e);
					} catch (MaxChangedBlocksException | DataException | IOException e1) {
						e1.printStackTrace();
					}
					*/

					// 0.2% chance of spawning shelter on generating new chunks.
					if (new Random().nextInt(1000) < 2) {
						try {
							main.getShelterManager().loadSchematic(e);
						} catch (MaxChangedBlocksException | DataException | IOException e1) {
							e1.printStackTrace();
						}
					}
					
				}
			}.runTaskLater(main, 40);
			
		}
		return;
	}

}

/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.listeners;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.misc.FalloutShelter;

public class WorldListener implements Listener {

	private MortuusTerraCore core;

	public WorldListener(MortuusTerraCore core) {
		this.core = core;
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (!e.isNewChunk())
			return;

		Chunk chunk = e.getChunk();
		int x = chunk.getX() * 16;
		int z = chunk.getZ() * 16;
		int y = chunk.getWorld().getHighestBlockYAt(x + 8, z + 2);

		Location loc = new Location(e.getWorld(), x + 8, y, z + 2);
		Random r = new Random();

		// 0.2% chance of spawning shelter on generating new chunks
		if (r.nextInt(1000) < 2) {
			FalloutShelter s = new FalloutShelter(core, loc.clone());
			s.generateFalloutShelter();
		}
	}

}

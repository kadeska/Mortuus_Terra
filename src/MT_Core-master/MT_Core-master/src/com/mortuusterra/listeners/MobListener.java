/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.listeners;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;

import com.mortuusterra.MortuusTerraCore;


public class MobListener implements Listener {
	private MortuusTerraCore main;
	public MobListener(MortuusTerraCore main) {
		this.main = main;
	}

	
	private Random rand = new Random();

	// Changed to CreatureSpawnEvent 9/2/17
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent e) {
		if (e.getEntityType().isAlive() && e.getEntityType() != EntityType.PLAYER
                && e.getEntityType() != EntityType.ZOMBIE && e.getEntityType() != EntityType.ENDERMAN
                && e.getEntityType() != EntityType.PIG && e.getEntityType() != EntityType.COW
                && e.getEntityType() != EntityType.SHEEP && e.getEntityType() != EntityType.HORSE
                && e.getEntityType() != EntityType.SQUID) {
		    e.setCancelled(true);
		    e.getLocation().getWorld().spawn(e.getLocation(), Zombie.class);
		}

	}
	
	/*
	 * Added chance of dropping sponge
	 */
	@EventHandler
	public void onZombieDie(EntityDeathEvent event) {
		if (event.getEntityType() != EntityType.ZOMBIE) return;
		
		if (rand.nextInt(100) == 0) {
			Location loc = event.getEntity().getLocation();
			loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.SPONGE));
		}
	}

	@EventHandler
	public void onBurn(EntityCombustEvent e) {
		if (e.getEntityType() == EntityType.ZOMBIE) {
			long time = e.getEntity().getWorld().getTime();
			if (e.getEntity().getLocation().getBlock().getLightFromSky() == 15 && time > 0 && time < 12000) {
				e.getEntity().setFireTicks(0);
				e.setDuration(0);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		main.getMobManager().clearUnwantedMobs(event.getChunk());
	}
}
package com.mortuusterra.managers;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mortuusterra.event.MTInfectEvent;
import com.mortuusterra.event.MTInfectionRemoveEvent;

/**
 * This class will manage infections.	
 * @author Horsey
 */
public class MTInfect implements Listener{
	public static final long INFECTION_TIME = 600000; // Currently 10 minutes. 
	private static Map<Player, Long> infections = new WeakHashMap<>();
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.getDamager().getType() != EntityType.ZOMBIE || event.getDamager().getType() != EntityType.ZOMBIE_VILLAGER) return;
		if (event.getEntityType() != EntityType.PLAYER) return;
		
		Player player = (Player)event.getDamager();
		if (infections.containsKey(player)) return;
		
		// Not infected Player attacked by Zombie.
		
		ThreadLocalRandom random = ThreadLocalRandom.current();
		if (random.nextInt(1) == 0) { // TODO: tweak as required.
			infectPlayer(player);
		}
	}
	
	/**
	 * This method adds the infected state to a player
	 * PLEASE NOTE: This method DOES NOT perform any logic!! It is the caller's duty to ensure the Player is not
	 *              already infected! 
	 * @param player The player to be infected
	 * @return boolean Whether the task was completed successfully.
	 */
	public static boolean infectPlayer(Player player) {
		MTInfectEvent event = new MTInfectEvent(player);
		Bukkit.getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			infections.put(player, INFECTION_TIME);
			return true;
		}
		return false;
	}
	
	/**
	 * Forcefully remove a player before their infection time is up. 
	 * @param player The player to remove infection from.
	 * @return boolean whether or not the action was successful.
	 */
	public static boolean removeInfect(Player player) {
		MTInfectionRemoveEvent event = new MTInfectionRemoveEvent(player);
		Bukkit.getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			infections.remove(player);
			return true;
		}
		return false;
	}
	
	public static boolean isInfected(Player player) {
		return infections.containsKey(player);
	}
	
	public static long getInfectionEndTime(Player player) {
		return infections.get(player);
	}
	
}

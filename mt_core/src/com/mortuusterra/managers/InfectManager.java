package com.mortuusterra.managers;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.reflect.TypeToken;
import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.event.InfectEvent;
import com.mortuusterra.event.InfectionRemoveEvent;

/**
 * This class will manage infections.
 * @author Horsey
 */
public class InfectManager implements Listener{
	public static final long INFECTION_TIME = 600000; // Currently 10 minutes. TODO this should be 30 mins
	private static Map<Player, Long> infections = new WeakHashMap<>();
	private MortuusTerraMain main = JavaPlugin.getPlugin(MortuusTerraMain.class);

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.getDamager().getType() != EntityType.ZOMBIE && event.getDamager().getType() != EntityType.ZOMBIE_VILLAGER) return;
		if (event.getEntityType() != EntityType.PLAYER) return;

		Player player = (Player)event.getEntity();
		if (infections.containsKey(player)) return;

		// Not infected Player attacked by Zombie.

		ThreadLocalRandom random = ThreadLocalRandom.current();
		int r = random.nextInt(5);  // 20% == 1/5 chance.
		if (r == 0) {
			infectPlayer(player);
		}
	}

	/**
	 * This method adds the infected state to a player
	 * PLEASE NOTE: This method DOES NOT perform any logic!! It is the caller's duty to ensure the Player is not
	 *              already infected! 
	 * @param player The player to be infected
	 * @param time The time for which the player should be infected
	 * @return boolean Whether the task was completed successfully.
	 */
	public static boolean infectPlayer(Player player, long time) {
		InfectEvent event = new InfectEvent(player);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			infections.put(player, time + System.currentTimeMillis());
			return true;
		}
		return false;
	}
	
	/**
	 * @see infectPlayer(Player player, long time)
	 * 
	 *	Calls infectPlayer(Player player, long time) with INFECTION_TIME as the time.
	 */
	public static boolean infectPlayer(Player player) {
		return infectPlayer(player, INFECTION_TIME);
	}

	/**
	 * Forcefully remove infection state from a player.
	 * @param player The player to remove infection from.
	 * @return boolean whether or not the action was successful.
	 */
	public static boolean removeInfect(Player player) {
		InfectionRemoveEvent event = new InfectionRemoveEvent(player);
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

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (!infections.containsKey(e.getPlayer())) return;

		/// Do this async!
		//new BukkitRunnable() {
			//@Override
			//public void run() {
				main.getFileManager().createJsonFile("infections");
				Type type = new TypeToken<Map<UUID, Long>>(){}.getType();
				Map<UUID, Long> map = main.getFileManager().getParsedJsonFile("infections", type);
				
				if (map.containsKey(e.getPlayer().getUniqueId())) return;
				
				map.put(e.getPlayer().getUniqueId(), infections.get(e.getPlayer()) - System.currentTimeMillis());

				main.getFileManager().writeJsonToFile("infections", map);
			//}
		//}.runTaskAsynchronously(main);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		main.getFileManager().createJsonFile("infections");
		//new BukkitRunnable() {
			//@Override
			//public void run() {
				Type type = new TypeToken<Map<Player, Long>>(){}.getType();
				Map<Player, Long> map = main.getFileManager().getParsedJsonFile("infections", type);
				
				if (!map.containsKey(e.getPlayer())) return; 
				long remaining = map.get(e.getPlayer()).longValue();
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(main, 
						() -> infections.put(e.getPlayer(), System.currentTimeMillis() + remaining));
				
			//}
		//}.runTaskAsynchronously(main);
	}

	@EventHandler
	public void onDrinkMilk(PlayerItemConsumeEvent e) {
		if (e.getItem().getType() == Material.MILK_BUCKET) {
			removeInfect(e.getPlayer());
			// e.getPlayer().setBonesStrong(true); 
		}
	}
	
}

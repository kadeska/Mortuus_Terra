package com.mortuusterra.tasks;

import static org.bukkit.ChatColor.*;
//TODO: lets see what we can do to improve on this 
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.mortuusterra.MortuusTerraMain;

/**
 * 
 * @author Horsey
 * This class manages the scoreboard. Most of the code should already make sense. 
 */
public class ScoreboardTask extends TimerTask implements Listener {

	private Scoreboard sc;
	private Objective obj;
	private List<String> titles = Lists.newArrayList();
	private int currentIndex = 0;
	private MortuusTerraMain m;
	private Map<Player, Integer> scores = new WeakHashMap<>();
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		//new BukkitRunnable(){
			//@Override
			//public void run() {
				m.getFileManager().createJsonFile("kills");
				Type type = new TypeToken<Map<UUID, Integer>>(){}.getType();
				Map<UUID, Integer> map = m.getFileManager().getParsedJsonFile("kills", type);
				
				int kills = scores.getOrDefault(event.getPlayer().getUniqueId(), 0);
				map.put(event.getPlayer().getUniqueId(), kills);
				
				m.getFileManager().writeJsonToFile("kills", map);
				scores.remove(event.getPlayer());
			//}
		//}.runTaskAsynchronously(m);
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (killer == null) return;
		
		int kills = scores.getOrDefault(killer, 0);
		scores.put(killer, ++kills);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		//new BukkitRunnable(){
			//@Override
			//public void run() {
				m.getFileManager().createJsonFile("kills");
				Type type =  new TypeToken<Map<Player, Integer>>(){}.getType();
				Map<Player, Integer> map = m.getFileManager().getParsedJsonFile("kills", type);
				if (!map.containsKey(event.getPlayer())) return;
				
				int score = map.get(event.getPlayer());
				scores.put(event.getPlayer(), score);
		//	}
		//}.runTaskAsynchronously(m);
		event.getPlayer().setScoreboard(sc);
	}
	
	public ScoreboardTask(MortuusTerraMain m) {
		super(m, false, 0, 4);
		this.m = m;
	
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		sc = manager.getNewScoreboard();
		
		// We aren't using the minecraft playerKillCount objective so that we can have more control over everything.
		obj = sc.registerNewObjective("playersKilled", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		// Okay, so basically, we need many different titles that change every few ticks
		// so first we have M, Mo, Mor ... Mortuus Terra
		for (int i = 0; i < "Mortuus Terra".length(); i++) {
			String substr = "Mortuus Terra".substring(0, i);
			substr = StringUtils.center(substr, 30);
			titles.add(GREEN + substr);
		}
		
		// Then we have a red "Mortuus Terra"
		for (int i = 0; i < 3; i++)
			titles.add(RED + StringUtils.center("Mortuus Terra", 30));
		
		// Followed by "- Land of the dead -"
		for (int i = 0; i < 5; i++)
			titles.add(RED + StringUtils.center("- Land of the dead-", 30));
		
		// And a red "Mortuus Terra" again
		for (int i = 0; i < 3; i++)
			titles.add(RED + StringUtils.center("Mortuus Terra", 30));
		
		// Now the reverse of the first one, so it goes like:
		// MortuusTerra, Mortuus Terr, ... Mo, M
		for (int i = "Mortuus Terra".length(); i > 0 ; i--) {
			String substr = "Mortuus Terra".substring(0, i);
			substr = StringUtils.center(substr, 30);
			titles.add(GREEN + substr);
		}
		
		obj.setDisplayName(titles.get(0));
	}

	@Override
	public void run() {
		// Switch to the next title
		if (currentIndex >= titles.size()) currentIndex = 0;
		obj.setDisplayName(titles.get(currentIndex++));
		
		// TODO: Check the performance of the following and determine if it should occur less often.
		
		// First we clear all the scores. We do this in order to ensure that it updates on name changes/nick changes
		sc.getEntries().forEach(sc::resetScores);
		
		// Now we re-add the players to the scoreboard
		scores.forEach((s, i) -> obj.getScore(BLUE + s.getDisplayName()).setScore(i));
		
		// Calculate the highest number of kills
		int max = sc.getEntries().stream()
				.filter(s -> !s.equals(GOLD + "  > Kills Leaderboard <  "))
				.map(obj::getScore)
				.map(Score::getScore)
				.max(Comparator.naturalOrder()).orElse(-1);
		
		// Set the score of " > Kills Leaderboard < " to be 1 higher than the highest score.
		obj.getScore(GOLD + "  > Kills Leaderboard <  ").setScore(++max);
		
	}

}

/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.mortuusterra.MortuusTerraCore;

public class CustomScoreboards {
	private MortuusTerraCore main;

	private Scoreboard pkTeamsBoard;

	public CustomScoreboards(MortuusTerraCore main) {
		this.main = main;
		ScoreboardManager manager = Bukkit.getScoreboardManager();

		pkTeamsBoard = manager.getNewScoreboard();

		pkTeamsBoard.registerNewTeam("NEUTRAL");
		pkTeamsBoard.registerNewTeam("ORANGE");
		pkTeamsBoard.registerNewTeam("RED");

		pkTeamsBoard.getTeam("NEUTRAL").setPrefix(PKStates.NEUTRAL.getColor() + "");
		pkTeamsBoard.getTeam("ORANGE").setPrefix(PKStates.ORANGE.getColor() + "");
		pkTeamsBoard.getTeam("RED").setPrefix(PKStates.RED.getColor() + "");

		for (Team team : pkTeamsBoard.getTeams()) {
			team.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
		}
	}
	
	public boolean isHostile(Player p) {
		if (pkTeamsBoard.getTeam("ORANGE").hasEntry(p.getName()) ||
			pkTeamsBoard.getTeam("RED").hasEntry(p.getName())) 
			return true;
		return false;
	}

	public void switchTeam(Player p, String newTeam) {
		for (Team team : pkTeamsBoard.getTeams()) {
			if (team.hasEntry(p.getName()))
				team.removeEntry(p.getName());
		}
		addPlayer(p, newTeam);
	}

	public void addPlayer(Player p, String team) {
		if (!pkTeamsBoard.getTeam(team).hasEntry(p.getName())) {
			pkTeamsBoard.getTeam(team).addEntry(p.getName());
		}
		updateScoreboard();
	}

	public void removePlayer(Player p, String team) {
		if (!pkTeamsBoard.getTeam(team).hasEntry(p.getName())) {
			pkTeamsBoard.getTeam(team).removeEntry(p.getName());
		}
		updateScoreboard();
	}
	
	public void updateScoreboard() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(pkTeamsBoard);
		}
	}

	public Scoreboard getPkTeamsBoard() {
		return pkTeamsBoard;
	}

}

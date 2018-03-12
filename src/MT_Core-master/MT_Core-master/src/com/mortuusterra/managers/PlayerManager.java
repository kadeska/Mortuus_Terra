/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.misc.PKStates;
import com.mortuusterra.objects.MTPlayer;
import com.mortuusterra.utils.files.FileType;
import com.mortuusterra.utils.files.PluginFile;

public class PlayerManager {
	private MortuusTerraCore main;
	public PlayerManager(MortuusTerraCore main) {
		this.main = main;
	}

	private PluginFile file;

	/*
	 * Map of all PlayerObject of players who are online. I figured we did not
	 * need a Map for players in radiation since we always damage the players.
	 * But if they are not in radiation they'll get damaged for 0 dmg
	 */
	private Map<UUID, MTPlayer> mtPlayers = new HashMap<>();

	/**
	 * Adds an MT-Player -if not already present - to the Map.
	 * 
	 * @param p
	 *            The Player to be added.
	 */
	public void addMortuusPlayer(Player p) {
		if (!mtPlayers.containsKey(p.getUniqueId()))
			mtPlayers.put(p.getUniqueId(), new MTPlayer(p.getUniqueId()));
	}

	public void removeMortuusPlayer(Player p) {
		if (mtPlayers.containsKey(p.getUniqueId()))
			mtPlayers.remove(p.getUniqueId());
	}

	public MTPlayer getMortuusPlayer(UUID uuid) {
		if (mtPlayers.containsKey(uuid))
			return mtPlayers.get(uuid);
		return null;
	}

	public boolean containsMortuusPlayer(UUID uuid) {
		if (mtPlayers.containsKey(uuid))
			return true;
		return false;
	}

	public Map<UUID, MTPlayer> getMtPlayers() {
		return mtPlayers;
	}

	public void savePlayersToDisk() {
		YamlConfiguration config = file.returnYaml();
		if (mtPlayers.isEmpty())
			return;

		for (MTPlayer p : mtPlayers.values()) {
			String uuid = p.getUuid().toString();
			
			config.set(uuid + ".pk-state", p.getPkState().name());
			config.set(uuid + ".kills", p.getPlayerKills());
			config.set(uuid + ".ingame-name", p.getCurrentIngameName());
			config.set(uuid + ".in-geck-range", p.isPlayerInRangeOfGeck());
			config.set(uuid + ".last-player-kill", p.getLastPlayerKillTime());

			// if (config.get(uuid + ".first-join-time") == null)
			// config.set(uuid + ".first-join-time", p.getJoinTime());
		}
		mtPlayers.clear();
		file.save(config);
	}

	public void loadPlayersFromDisk() {
		file = new PluginFile(main, "players", FileType.YAML);
		YamlConfiguration config = file.returnYaml();

		for (String key : config.getConfigurationSection("").getKeys(false)) {

			// Only convert online players to PlayerObject and add to Map.
			// for (Player online : Bukkit.getOnlinePlayers()) {
			UUID uuid = UUID.fromString(key);

			// if (!online.getUniqueId().equals(uuid))
			// continue;

			MTPlayer p = new MTPlayer(uuid);
			PKStates state = PKStates.getStateByString(config.getString(key + ".pk-state"));
			boolean inGeckRange = config.getBoolean(key + ".in-geck-range");
			long lastPlayerKill = config.getLong(key + ".last-player-kill");
			int playerKills = config.getInt(key + ".kills");
			
			p.setPkState(state);
			p.setPlayerKills(playerKills);
			p.setPlayerInRangeOfGeck(inGeckRange);
			p.setLastPlayerKillTime(lastPlayerKill);

			mtPlayers.put(uuid, p);
			// }
		}

	}
}

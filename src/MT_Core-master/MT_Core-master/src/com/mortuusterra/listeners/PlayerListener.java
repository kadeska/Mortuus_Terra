/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import com.mortuusterra.MortuusTerraCore;
import com.mortuusterra.misc.PKStates;
import com.mortuusterra.objects.MTPlayer;

public class PlayerListener implements Listener {
	private MortuusTerraCore main;
	public PlayerListener(MortuusTerraCore main) {
		this.main = main;
	}

	// Disguise disguise = DisguiseAPI.constructDisguise(zombie);

	@EventHandler
	private void onPlayerJoinEvent(PlayerJoinEvent e) {
		main.getPlayerManager().addMortuusPlayer(e.getPlayer());

		if (!main.getScoreboards().isHostile(e.getPlayer()))
			main.getScoreboards().addPlayer(e.getPlayer(), "NEUTRAL");
	}

	@EventHandler
	public void onPlayerKillsPlayer(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player))
			return;

		Player killer = (Player) e.getDamager();
		Player target = (Player) e.getEntity();

		// Check if the target is dead
		if (target.getHealth() - e.getDamage() <= 0) {
			MTPlayer killerObject = main.getPlayerManager().getMortuusPlayer(killer.getUniqueId());

			// Sets the time in SECONDS.
			killerObject.setLastPlayerKillTime(System.currentTimeMillis() / 1000);
			killerObject.addPlayerKills(1);

			for (PKStates state : PKStates.values()) {
				if (killerObject.getPlayerKills() == state.getRequiredKills()) {
					killerObject.setPkState(state);
					main.getScoreboards().switchTeam(killer, state.name());
					break;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		MTPlayer pObject = main.getPlayerManager().getMortuusPlayer(p.getUniqueId());

		// if 25 minutes have passed since the last kill set to neutral.
		// The time is in SECONDS.
		if (pObject.getLastPlayerKillTime() != 0) {
			if (pObject.getLastPlayerKillTime() + 1500 < (System.currentTimeMillis() / 1000)
					&& pObject.getPkState() != PKStates.NEUTRAL) {

				main.getScoreboards().switchTeam(p, "NEUTRAL");
				pObject.setPkState(PKStates.NEUTRAL);
				pObject.setPlayerKills(0);
			}
		}
	}

//	@EventHandler
//	public void test(PlayerInteractEvent e) {
//		if (e.getHand() == EquipmentSlot.OFF_HAND)
//			return;
//
//		if (e.getAction() == Action.LEFT_CLICK_BLOCK)
//			main.getPlayerManager().getMortuusPlayer(e.getPlayer().getUniqueId()).setPkState(PKStates.ORANGE);
//		;
//
//		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
//
//			if (e.getItem() == null)
//				return;
//
//			if (e.getItem().getType() != Material.DIAMOND_HOE)
//				return;
//			FalloutShelter s = new FalloutShelter(e.getClickedBlock().getLocation().clone());
//			s.generateFalloutShelter();
//		}
//	}

}
package com.mortuusterra.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.mortuusterra.event.MTInfectEvent;
import com.mortuusterra.event.MTPlayerChatEvent;
import com.mortuusterra.event.MTRadiationDamageEvent;
import com.mortuusterra.event.MTSupplyDropLandEvent;

public class MTEventListener implements Listener {

	public MTEventListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This listens to when the MTRadtiationDamageEvent is fired. This method can be
	 * used to add additional features to radiation or anything related to the
	 * MTRadiationDamage system.
	 * 
	 * @param event
	 *            MTRadiationDamageEvent
	 */
	@EventHandler
	public void onMTRadiationDamage(MTRadiationDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
	}

	/**
	 * This listens to when the MTPlayerChatEvent is fired. This method could be
	 * used to add additional features to the MTChat system or anything else
	 * relating to the MTChat system.
	 * 
	 * @param event
	 *            MTPlayerChatEvent
	 */
	@EventHandler
	public void onMTPlayerChat(MTPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}
	}

	/**
	 * This listens to when the MTInfectEvent is fired. This method could be used to
	 * add additional features to the MTInfect system or anything else related to
	 * the MTInfect system.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onMTInfect(MTInfectEvent event) {
		if (event.isCancelled()) {
			return;
		}
	}

	/**
	 * This listens to when the MTSupplyDropLandEvent is fired. This method could be
	 * used to add additional features to the MTSupplyDropLand system, or anything
	 * related to the MTSupplyDropLand system.
	 * 
	 * @param event
	 *            MTSupplyDropLandEvent
	 */
	@EventHandler
	public void onMTSupplyDropLand(MTSupplyDropLandEvent event) {
		if (event.isCancelled()) {
			return;
		}
	}

}

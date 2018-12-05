package com.mortuusterra.util;

import java.lang.reflect.Type;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * This class is for GSON to know how to deal with players.
 * @author Horsey
 *
 */
public class PlayerTypeAdaptor implements JsonDeserializer<Player>, JsonSerializer<Player> {

	/**
	 * Takes a player, gets their UUID and saves that.
	 */
	@Override
	public JsonElement serialize(Player player, Type type, JsonSerializationContext ctx) {
		return new JsonPrimitive(player.getUniqueId().toString());
	}

	/**
	 * Takes a UUID, and gets a player from it.
	 * 
	 * @returns the player OR null if player is offline!!
	 */
	@Override
	public Player deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		UUID uuid = UUID.fromString(el.getAsString());
		return Bukkit.getPlayer(uuid);
	}

}

/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.utils.others;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class StringUtilities {
	
	public static String[] helpPage(String command, String... args) {
		String title = color("&8---------- &6" + command + " Helppage &8----------");
		if (args.length % 2 != 0)
			return new String[] { title };

		String[] out = new String[args.length / 2 + 1];
		out[0] = title;
		for (int i = 0; i < args.length / 2; i++) {
			out[i + 1] = color("&e/" + command + " &7" + args[2 * i] + "&e  -  " + args[2 * i + 1]);
		}
		return out;
	}
	
	/**
	 * Util method to convert a String with the right format (see example) to a
	 * Location. This method can be useful when working with config files.
	 * 
	 * @param stringLocation
	 *            The location in string format. It can either contain yaw and
	 *            pitch or not.
	 *            <p>
	 *            Example: "world,406.0,4.0,-313.0"
	 * @return new Location according to the string input.
	 */
	public static Location stringToLocation(String stringLocation) {
		String[] split = stringLocation.split(",");
		World world = Bukkit.getWorld(split[0]);
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);

		if (split.length == 6) {
			float yaw = Float.parseFloat(split[4]);
			float pitch = Float.parseFloat(split[5]);
			return new Location(world, x, y, z, yaw, pitch);
		}
		return new Location(world, x, y, z);
	}

	/**
	 * Util method to convert a Location to a String. This method can be useful
	 * when working with config files.
	 * 
	 * @param location
	 *            The location to convert into string. It can either contain yaw
	 *            and pitch or not.
	 *            <p>
	 * @return The input Location in string format.
	 */
	public static String locationToString(Location location) {
		String w = location.getWorld().getName();
		double x = location.getBlockX();
		double y = location.getBlockY();
		double z = location.getBlockZ();

		if ((location.getYaw() == 0.0F) && (location.getPitch() == 0.0F))
			return w + "," + x + "," + y + "," + z;

		float yaw = location.getYaw();
		float pitch = location.getPitch();
		return w + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
	}

	/** Util method to color Strings using the <b>'&'</b> character.
	 * <p><b>Example:</b> "&cRed" would be the same as <code>ChatColor.RED + "Red"</code>
	 * @param text The String that needs to be colored.
	 * @return The appropriately colored String.
	 */
	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}

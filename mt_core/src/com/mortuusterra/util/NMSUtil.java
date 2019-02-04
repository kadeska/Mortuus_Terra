package com.mortuusterra.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;

public class NMSUtil {

	public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass,
			Class<? extends EntityInsentient> customClass) {
		try {
			List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
			for (Field f : EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMap.add((Map<?, ?>) f.get(null));
				}
			}

			if (dataMap.get(0).containsKey(id)) {
				dataMap.get(0).remove(name);
				dataMap.get(0).remove(id);
			}

			Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			method.setAccessible(true);
			method.invoke(null, customClass, name, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if(!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if(tag == null) tag = nmsStack.getTag();
		
		NBTTagList enchantment = new NBTTagList();
		tag.set("ench", enchantment);
		nmsStack.setTag(tag);
		
		return CraftItemStack.asCraftMirror(nmsStack);
	}
	
}

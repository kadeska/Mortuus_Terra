package com.mortuusterra.entity;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.Items;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.World;

public class CustomEntityZombie extends EntityZombie {

	public CustomEntityZombie(World world) {
		super(world);
	}
	
	protected void initAttributes() {
		super.initAttributes();
		
		this.getAttributeInstance(GenericAttributes.e).setValue(10.0D);
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(1000.0D);
		this.setCustomName(ChatColor.RED + "The Flash");
		this.setCustomNameVisible(true);
		this.setBaby(false);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(10.00D);
		
		//this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		//this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityZombie>(this, EntityZombie.class, true));
	}
	
	protected Item getLoot() {
		return Items.DIAMOND;
	}
	
	public static Zombie spawn(Location location) {
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final CustomEntityZombie customEntity = new CustomEntityZombie(mcWorld);
		
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		//(CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		
		return (Zombie) customEntity.getBukkitEntity();
	}

}

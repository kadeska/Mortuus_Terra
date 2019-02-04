package com.mortuusterra.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.mortuusterra.MortuusTerraMain;
import com.mortuusterra.items.CustomZombieEgg;

public class CustomZombieEggSpawnListener implements Listener {
	
	private MortuusTerraMain main;
	
	public CustomZombieEggSpawnListener(MortuusTerraMain main) {
		this.main = main;
	}

	
	@EventHandler
	public void onPlayerSpanBoss(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand() == CustomZombieEgg.getItem() && e.getPlayer().getItemInHand().getItemMeta().equals(CustomZombieEgg.getItem().getItemMeta())) {
				
			}
		}
	}
	
	@EventHandler
	public void ZombieDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Zombie) {
			if(e.getEntity().getCustomName() == null) return; 
			if(e.getEntity().getCustomName().equals(CustomZombieEgg.getItem().getItemMeta().getDisplayName())) {
				for(int d = 0; d < 64; d++) {
					Item diamond = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.DIAMOND, 1));
					Item gold = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
					
					//float x = (float) (Math.random() + 1);
					//float y = (float) (Math.random() + 1);
					//float z = (float) (Math.random() + 1);
					//diamond.setVelocity(new Vector(x, y, z));
					//gold.setVelocity(new Vector(x, y, z));
				}
				
				ItemStack flashSword = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta fsMeta = flashSword.getItemMeta();
				
				fsMeta.setDisplayName(ChatColor.AQUA + "Flashes sword");
				flashSword.setItemMeta(fsMeta);
				
				flashSword.addEnchantment(Enchantment.DAMAGE_ALL, 4);
				flashSword.addEnchantment(Enchantment.DURABILITY, 3);
				flashSword.addEnchantment(Enchantment.KNOCKBACK, 1);
				
				e.getEntity().getWorld().dropItem(e.getEntity().getLocation().add(0, 1, 0), flashSword);
				
			}
		}
	}
	
	@EventHandler
	public void BossSpawn(EntitySpawnEvent e) {
		if(e.getEntity().getCustomName() == null) return;
		if(e.getEntity().getCustomName().equalsIgnoreCase(ChatColor.RED + "The Flash")) {
			//ItemStack flashSword = new ItemStack(Material.DIAMOND_SWORD);
			//flashSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
			
			//net.minecraft.server.v1_12_R1.ItemStack nmsSword = CraftItemStack.asNMSCopy(flashSword);
			
			main.getServer().getWorld(e.getEntity().getWorld().getName()).strikeLightning(e.getEntity().getLocation());
			
			
			
		}
	}
	
	
}

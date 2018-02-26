package com.mortuusterra.managers;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mortuusterra.MortuusTerraMain;

public class MTGeneratorBuildProcess {

	private MortuusTerraMain main;

	private boolean stage_one = false; // bottom 9 stone slabs
	private boolean stage_two = false; // 8 iron fences
	private boolean stage_three = false; // top 9 stone slabs
	private boolean stage_four = false; // Furnace
	private boolean stage_five = false; // lamp

	public MTGeneratorBuildProcess(MortuusTerraMain m) {
		this.main = m;
	}

	public void start(Player p) {
		p.sendMessage("Checking your inventory it you have all the required items ...");
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			p.sendMessage("You are in creative mode. You have all the items that you need");
		}
		if (!p.getInventory().containsAtLeast(new ItemStack(Material.SMOOTH_BRICK), 9)) {
			p.sendMessage("You dont have at least 9 SMOOTH_BRICK");
			p.sendMessage("Canceling the build process for the generator!");
			return;
		}
		if (!p.getInventory().containsAtLeast(new ItemStack(Material.STONE_SLAB2), 9)) {
			p.sendMessage("You dont have at least 9 STONE_SLAB");
			p.sendMessage("Canceling the build process for the generator!");
			return;
		}
		if (!p.getInventory().containsAtLeast(new ItemStack(Material.IRON_FENCE), 8)) {
			p.sendMessage("You dont have at least 8 IRON_FENCE");
			p.sendMessage("Canceling the build process for the generator!");
			return;
		}
		if (!p.getInventory().containsAtLeast(new ItemStack(Material.REDSTONE_LAMP_OFF), 1)) {
			p.sendMessage("You dont have at least 1 REDSTONE_LAMP");
			p.sendMessage("Canceling the build process for the generator!");
			return;
		}
		if (!p.getInventory().containsAtLeast(new ItemStack(Material.FURNACE), 1)) {
			p.sendMessage("You dont have at least 1 FURNACE");
			p.sendMessage("Canceling the build process for the generator!");
			return;
		}
		
		p.sendMessage("Starting stage one of build process");
		p.sendMessage("Please place the center ground smooth_brick block");

	}

	public boolean isStage_one() {
		return stage_one;
	}

	public boolean isStage_two() {
		return stage_two;
	}

	public boolean isStage_three() {
		return stage_three;
	}

	public boolean isStage_four() {
		return stage_four;
	}

	public boolean isStage_five() {
		return stage_five;
	}

	public void setStage_one(boolean stage_one) {
		this.stage_one = stage_one;
	}

	public void setStage_two(boolean stage_two) {
		this.stage_two = stage_two;
	}

	public void setStage_three(boolean stage_three) {
		this.stage_three = stage_three;
	}

	public void setStage_four(boolean stage_four) {
		this.stage_four = stage_four;
	}

	public void setStage_five(boolean stage_five) {
		this.stage_five = stage_five;
	}

}

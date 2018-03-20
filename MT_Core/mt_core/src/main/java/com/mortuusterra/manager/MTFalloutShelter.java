package main.java.com.mortuusterra.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.world.ChunkLoadEvent;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.DataException;

@SuppressWarnings("deprecation")
public class MTFalloutShelter {

	public MTFalloutShelter() {
	}

	public void loadSchematic(ChunkLoadEvent e) throws DataException, IOException, MaxChangedBlocksException {
		Chunk chunk = e.getChunk();
		// File file = new File("main/resources/schematics/shelter1-basic.schematic");
		File file = new File("plugins/WorldEdit/schematics/shelter1.schematic");
        EditSession es = new EditSession(new BukkitWorld(chunk.getWorld()), 999999);
        CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
        Block start = chunk.getBlock(0, 0, 0);
        int highest = start.getWorld().getHighestBlockYAt(start.getLocation());
        cc.paste(es, new Vector(start.getX(), highest, start.getZ()), false);
        Bukkit.getServer().getConsoleSender().sendMessage(start.getX() + "  " + highest + "  " + start.getZ());
	}

}

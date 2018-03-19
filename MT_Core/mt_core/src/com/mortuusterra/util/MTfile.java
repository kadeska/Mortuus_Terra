package com.mortuusterra.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sk89q.worldedit.bags.OutOfBlocksException;

import com.mortuusterra.MortuusTerraMain;

public class MTfile {
	private MortuusTerraMain core;
	private JSONParser parser = new JSONParser();

	public MTfile(MortuusTerraMain core) {
		this.core = core;
	}

	public void createYmlFile(String name) {
		if (!core.getDataFolder().exists()) {
			core.getDataFolder().mkdir();
		}
		File file = new File(core.getDataFolder(), name + "yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				core.notifyConsole("The " + name + ".yml file has been created");
			} catch (IOException e) {
				core.notifyConsole("Could not create the " + name + ".yml file");
				e.printStackTrace();
			}
		}
	}

	public void createJsonFile(String name) {
		if (!core.getDataFolder().exists()) {
			core.getDataFolder().mkdir();
		}
		File jsonFile = new File(name + ".json");
		try {
			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
			}
		} catch (IOException e) {
			core.notifyConsole("Could not create the " + name + ".json file");
			e.printStackTrace();
		}
	}

	public void createTextFile(String name) {
		if (!core.getDataFolder().exists()) {
			core.getDataFolder().mkdir();
		}
		File textFile = new File(name + ".txt");
		try {

			if (!textFile.exists()) {
				textFile.createNewFile();
			}

		} catch (IOException e) {
			core.notifyConsole("Could not create the " + name + ".txt file");
			e.printStackTrace();
		}
	}
	
	public JSONObject getParsedJsonFile(String name) {
		File file = new File(core.getDataFolder(), name + ".json");
		try {
			return (JSONObject) parser.parse(new FileReader(file));
		} catch (IOException | ParseException e) {
			throw new RuntimeException(String.format("Failure parsing file %s", name + ".json"), e);
		}
	}
	
	
	public void saveFiles() {
		
	}
	
	public void loadFiles() {
		
	}
}

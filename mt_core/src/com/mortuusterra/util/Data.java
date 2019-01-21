package com.mortuusterra.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.mortuusterra.MortuusTerraMain;

public class Data {
	private MortuusTerraMain core;
	private Gson gson;

	public Data(MortuusTerraMain core) {
		this.core = core;
		// Create GSON.
		gson = new GsonBuilder().registerTypeHierarchyAdapter(Player.class, new PlayerTypeAdaptor())
		       .setPrettyPrinting()
		       .enableComplexMapKeySerialization()
		       .create();
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
		File jsonFile = new File(core.getDataFolder(), name + ".json");
		try {
			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
				FileWriter fw = new FileWriter(jsonFile);
				fw.write("{}");
				fw.close();
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
		File textFile = new File(core.getDataFolder(), name + ".txt");
		try {

			if (!textFile.exists()) {
				textFile.createNewFile();
			}

		} catch (IOException e) {
			core.notifyConsole("Could not create the " + name + ".txt file");
			e.printStackTrace();
		}
	}
	
	public <T> T getParsedJsonFile(String name, Type type) {
		File file = new File(core.getDataFolder(), name + ".json");
		try (FileReader fileReader = new FileReader(file)){
			return gson.fromJson(fileReader, type);
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			throw new RuntimeException(String.format("An error occured while reading %s.json! %n Caused by: ", name), e);
		}
	}
	
	public void writeJsonToFile(String name, Object json) {
		String jsonString = gson.toJson(json);
		File file = new File(core.getDataFolder(), name + ".json");
		
		try (FileWriter writer = new FileWriter(file, false)){
			writer.write(jsonString);
		} catch (IOException e) {
			throw new RuntimeException(String.format("An error occured while writing to %s.json! %n Caused by: ", name), e);
		}
	}
	
	public void saveFiles() {
		
	}
	
	public void loadFiles() {
		
	}
}

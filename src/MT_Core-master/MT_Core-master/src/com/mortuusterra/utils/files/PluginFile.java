/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.utils.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.simple.JSONObject;

import com.mortuusterra.MortuusTerraCore;

public class PluginFile {

	// Unsure how this was meant to be, changed so that it doesn't throw StackOverflows
    private MortuusTerraCore main;

    private String name;
    private File file;

    /**
     * Constructor
     * @param core Core plugin
     * @param name Name of the file
     * @param type Type of the file
     */
    public PluginFile(MortuusTerraCore core, String name, FileType type) {
    	this.main = core;
        file = main.getFileManager().createPluginFile(name, type);
        this.name = name;

    }

    /**
     * returns the file bound to this object
     * @return file
     */
    public File getFile() {
        return file;
    }

    /** 
     * Sets the file bound to this object
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Save Method (Only for YAML)
     *
     */

    public void save(YamlConfiguration yamlConfiguration) {
        try {
            yamlConfiguration.save(file);
            main.getServer().getConsoleSender().sendMessage(MortuusTerraCore.MTC_PREFIX + ChatColor.GREEN + "Successfully saved file " + name + "!");
        } catch (IOException e) {
            e.printStackTrace();
            main.getServer().getConsoleSender().sendMessage(MortuusTerraCore.MTC_PREFIX + ChatColor.RED + "Failed to save file " + name + "!");
        }
    }

    /**
     * Write Method for JSON files
     *
     * @param jsonObject The JSONObject to write
     */

    public void write(JSONObject jsonObject) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "File could not be written to!");
        }
    }

    /**
     * Write Method for Text files
     *
     * @param text The text to write
     */
    public void write(String text) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(text);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "File could not be written to!");
        }
    }

    /**
     * Returns the file as a YAML
     *
     * @return the file as yaml
     */
    public YamlConfiguration returnYaml(){
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Gets the name of the file
     * @return name of the file
     */
	public String getName() {
		return name;
	}

	/**
	 * Deletes the file bound to this object; clears all variables. 
	 */
	public void dispose() {
		file.delete();
		file = null;
		main = null;
		name = null;
	}
}
package me.dylanmullen.bingo.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager
{

	private static ConfigManager instance;

	private List<Config> configs;

	/**
	 * @return Returns an instance of the Config Manager
	 */
	public static ConfigManager getInstance()
	{
		if (instance == null)
			instance = new ConfigManager();

		return instance;
	}

	/**
	 * Manages the configs of the server
	 */
	public ConfigManager()
	{
		this.configs = new ArrayList<Config>();
		load();
	}

	/**
	 * Loads all config names
	 */
	private void load()
	{
		configs.add(new Config("mysql", new File("res/mysql.json")));
	}

	/**
	 * @param name Config File Name
	 * @return Returns the config file identified by the name
	 */
	public Config getConfig(String name)
	{
		for (Config cfg : configs)
			if (cfg.getName().equals(name))
				return cfg;
		return null;
	}
}

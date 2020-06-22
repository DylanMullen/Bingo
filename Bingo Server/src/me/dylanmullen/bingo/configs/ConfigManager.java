package me.dylanmullen.bingo.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.configs.Config.ConfigType;

public class ConfigManager
{

	private static ConfigManager instance;

	private List<Config> configs;
	private List<Config> bingoConfigs;

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
		this.bingoConfigs = new ArrayList<Config>();
		load();
	}

	/**
	 * Loads all config names
	 */
	private void load()
	{
		configs.add(new Config("mysql.json", ConfigType.CONFIG,
				getClass().getClassLoader().getResourceAsStream("mysql.json")));

	}

	public void loadBingoFiles()
	{
		for (File file : IOController.getController().getBingoFolder().listFiles())
		{
			if (!file.getName().endsWith(".json"))
				continue;

			bingoConfigs.add(new Config(file.getName(), ConfigType.BINGO, file));
		}
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

	public Config getBingoConfig(int i)
	{
		return bingoConfigs.get(i);
	}
	
	public List<Config> getBingoConfigs()
	{
		return bingoConfigs;
	}

}

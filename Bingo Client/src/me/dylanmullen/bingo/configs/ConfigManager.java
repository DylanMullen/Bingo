package me.dylanmullen.bingo.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager
{

	private static ConfigManager instance;

	private List<Config> uiConfigs;

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
		this.uiConfigs = new ArrayList<Config>();
		load();
	}

	/**
	 * Loads all config names
	 */
	private void load()
	{
		loadUIFiles();
	}

	public void loadUIFiles()
	{
		for (File file : IOController.getController().getUIConfigFolder().listFiles())
		{
			if (!file.getName().endsWith(".csv"))
				return;
			uiConfigs.add(new Config(file.getName(), file));
		}
	}

	/**
	 * @param name Config File Name
	 * @return Returns the config file identified by the name
	 */
	public Config getUIConfig(String name)
	{
		System.out.println(name);
		for (Config cfg : uiConfigs)
			if (cfg.getName().equals(name))
				return cfg;
		return null;
	}
	
	public List<Config> getUiConfigs()
	{
		return uiConfigs;
	}

}

package me.dylanmullen.bingo.gfx.ui.colour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.dylanmullen.bingo.configs.Config;
import me.dylanmullen.bingo.configs.ConfigManager;

public class ColourManager
{

	private List<UIColourSet> sets;

	public ColourManager()
	{
		this.sets = new ArrayList<UIColourSet>();
	}

	public void load()
	{
		BufferedReader reader = null;
		Scanner scanner = null;
		try
		{
			for (Config config : ConfigManager.getInstance().getUiConfigs())
			{
				reader = new BufferedReader(new FileReader(config.getFile()));
				String line = null;
				UIColourSet set = new UIColourSet(config.getName());
				while ((line = reader.readLine()) != null)
				{
					scanner = new Scanner(line);
					scanner.useDelimiter(",");
					UIColour colour = new UIColour(scanner.next(), scanner.nextInt(), scanner.nextInt(),
							scanner.nextInt());
					set.addColour(colour);
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
				try
				{
					reader.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			if (scanner != null)
				scanner.close();
		}
	}

	public UIColourSet getSet(String name)
	{
		for (UIColourSet set : sets)
		{
			if (set.getName().equalsIgnoreCase(name))
				return set;
		}
		return null;
	}

}

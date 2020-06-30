package me.dylanmullen.bingo.gfx.ui.colour;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import me.dylanmullen.bingo.configs.IOController;

public class ColourManager
{

	private List<UIColourSet> sets;

	public ColourManager()
	{
		this.sets = new ArrayList<UIColourSet>();
	}

	public UIColourSet load(String name)
	{
		BufferedReader reader = null;
		Scanner scanner = null;
		try
		{
			reader = new BufferedReader(new FileReader(
					new File(getClass().getClassLoader().getResource("ui" + IOController.SEPERATOR + name).toURI())));
			String line = null;
			UIColourSet set = new UIColourSet(name);
			while ((line = reader.readLine()) != null)
			{
				if (line.startsWith("#"))
					continue;
				try
				{
					scanner = new Scanner(line);
					scanner.useDelimiter(",");
					UIColour colour = new UIColour(scanner.next(), scanner.nextInt(), scanner.nextInt(),
							scanner.nextInt());
					set.addColour(colour);
				} catch (Exception e)
				{
					continue;
				}
			}
			sets.add(set);
			return set;
		} catch (IOException | URISyntaxException e)
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
		return null;
	}

	public UIColourSet getSet(String name)
	{
		UIColourSet set = null;
		try
		{
			set = sets.stream().filter(e -> e.getName().startsWith(name)).findFirst().get();
		} catch (NoSuchElementException e)
		{
			set = load(name + ".csv");
		}
		return set;
	}

}

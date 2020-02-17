package me.dylanmullen.bingo.configs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Config
{

	private InputStream is;

	private HashMap<String, String> contents = new HashMap<>();

	public Config(InputStream is)
	{
		this.is = is;
		load();
	}

	private void load()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			String len;
			while ((len = reader.readLine()) != null)
			{
				String[] line = len.split(":", 2);
				contents.put(line[0], line[1]);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getValue(String key)
	{
		return contents.get(key);
	}

}

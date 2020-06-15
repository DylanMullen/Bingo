package me.dylanmullen.bingo.configs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config
{

	public enum ConfigType
	{
		CONFIG, BINGO;
	}

	private String name;
	private ConfigType type;
	private File file;
	private InputStream stream;
	private boolean loaded;

	private Map<String, JSONObject> objects;

	/**
	 * Config file parsed from a JSON file
	 * 
	 * @param name Config name
	 * @param type The type of Config
	 * @param file JSON file
	 */
	public Config(String name, ConfigType type, InputStream stream)
	{
		this.name = name;
		this.type = type;
		this.stream = stream;
		this.objects = new HashMap<String, JSONObject>();
		this.loaded = load();
	}

	public Config(String name, ConfigType type, File file)
	{
		this.name = name;
		this.type = type;
		this.file = file;
		this.objects = new HashMap<String, JSONObject>();
		this.loaded = load();
	}

	/**
	 * Copies the resource from inside the JAR file to the local directory
	 */
	public void copyResource()
	{
		File temp = null;

		switch (type)
		{
			case BINGO:
				temp = new File(
						IOController.getController().getBingoFolder().getPath() + IOController.SEPERATOR + name);
				break;
			case CONFIG:
				temp = new File(
						IOController.getController().getConfigFolder().getPath() + IOController.SEPERATOR + name);
				break;
			default:
				temp = new File(
						IOController.getController().getConfigFolder().getPath() + IOController.SEPERATOR + name);
				break;
		}

		if (!temp.exists())
		{
			InputStream input = null;
			FileOutputStream output = null;
			try
			{
				temp.createNewFile();

				input = stream;

				int bytes;
				byte[] buffer = new byte[4096];
				output = new FileOutputStream(temp);
				while ((bytes = input.read(buffer)) > 0)
					output.write(buffer, 0, bytes);
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					if (input != null)
						input.close();
					if (output != null)
						output.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		this.file = temp;
	}

	/**
	 * Loads the JSON file contents
	 * 
	 * @return Returns true if the load was successful
	 */
	private boolean load()
	{
		if (file == null)
			copyResource();

		JSONParser parser = new JSONParser();
		FileReader fileReader = null;
		try
		{
			fileReader = new FileReader(file);
			JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

			for (Object obj : jsonObject.keySet())
			{
				objects.put((String) obj, (JSONObject) jsonObject.get(obj));
				System.out.println(obj);
				JSONObject j = (JSONObject) jsonObject.get(obj);
				storeObject(obj, j);
			}
			return true;
		} catch (IOException | ParseException e)
		{
			e.printStackTrace();
			return false;
		} finally
		{
			try
			{
				if (fileReader != null)
					fileReader.close();
			} catch (IOException e)
			{
				System.err.println("Failed to close File Reader.");
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Stores the JSONObject to the list as long as it is a parent to another
	 * JSONObject. Recursive method
	 * 
	 * @param parentKey Parent Key to the current object
	 * @param object    The current JSONObject to check
	 */
	public void storeObject(Object parentKey, JSONObject object)
	{
		if (!(parentKey instanceof String))
		{
			return;
		}
		for (Object key : object.keySet())
		{
			if (!(key instanceof String))
				continue;
			Object jo = object.get(key);
			if (jo instanceof JSONObject)
			{
				objects.put(parentKey + "." + (String) key, (JSONObject) jo);
				storeObject(parentKey + "." + (String) key, (JSONObject) jo);
			}
		}
	}

	public JSONObject getJSONObject(String key)
	{
		return objects.get(key);
	}

	/**
	 * Returns JSONObject value based on parameters
	 * 
	 * @param objectKey The key of the object
	 * @param valueKey  The value key for the Object
	 * @return Returns an object from the JSONObject
	 */
	public Object getValue(String objectKey, String valueKey)
	{
		JSONObject obj = getJSONObject(objectKey);
		if (obj == null)
			return null;
		return obj.get(valueKey);
	}

	@SuppressWarnings("unchecked")
	public void write(String objectKey, String valueKey, Object object)
	{
		JSONObject json = getJSONObject(objectKey);
		if (json == null)
		{
			return;
		}
		json.put(valueKey, object);
		try (FileWriter writer = new FileWriter(file))
		{
			writer.append(json.toJSONString());
			writer.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isLoaded()
	{
		return loaded;
	}

	public String getName()
	{
		return name;
	}
}

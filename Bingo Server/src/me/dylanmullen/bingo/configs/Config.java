package me.dylanmullen.bingo.configs;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config
{

	private String name;
	private File sourceFile;
	private boolean loaded;

	private Map<String, JSONObject> objects;

	/**
	 * Config file parsed from a JSON file
	 * 
	 * @param name Config name
	 * @param file JSON file
	 */
	public Config(String name, File file)
	{
		this.name = name;
		this.sourceFile = file;
		this.objects = new HashMap<String, JSONObject>();
		this.loaded = load();
	}

	/**
	 * Loads the JSON file contents
	 * 
	 * @return Returns true if the load was successful
	 */
	private boolean load()
	{
		JSONParser parser = new JSONParser();
		FileReader fileReader = null;
		try
		{
			fileReader = new FileReader(sourceFile);
			JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

			for (Object obj : jsonObject.keySet())
			{
				objects.put((String) obj, (JSONObject) jsonObject.get(obj));
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
		for (Object key : object.keySet())
		{
			Object jo = object.get(key);

			if (jo instanceof JSONObject)
			{
				objects.put(parentKey + "." + (String) key, (JSONObject) jo);
				storeObject(jo, (JSONObject) jo);
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

	public boolean isLoaded()
	{
		return loaded;
	}

	public String getName()
	{
		return name;
	}
}

package me.dylanmullen.bingo.configs;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import me.dylanmullen.bingo.core.Main;

public class IOController
{

	public static final String SEPERATOR = System.getProperty("file.separator");

	private static IOController instance;

	private File parentFolder;
	private File uiConfigFolder;

	public static IOController getController()
	{
		if (instance == null)
			instance = new IOController();

		return instance;
	}

	/**
	 * Sets up the folder file paths
	 * 
	 * @return Returns true if the folders needed to be generated
	 */
	public boolean setup()
	{
		try
		{
			this.parentFolder = new File(getJARPath() + SEPERATOR + "resources" + SEPERATOR);
			this.uiConfigFolder = new File(parentFolder.getPath() + SEPERATOR + "ui-configs" + SEPERATOR);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return false;
		}
		return createFolders();
	}

	/**
	 * Creates any folders that needs to be created
	 * 
	 * @return Returns true if any folder needed to be generated
	 */
	private boolean createFolders()
	{
		try
		{
			boolean status = false;
			if (!this.parentFolder.exists())
			{
				status = true;
				this.parentFolder.mkdir();
			}
			if (!this.uiConfigFolder.exists())
			{
				status = true;
				this.uiConfigFolder.mkdir();
			}
			return status;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private String getJARPath() throws UnsupportedEncodingException
	{
		String temp = URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
				"UTF-8");
		return new File(temp).getParentFile().getPath();
	}

	public File getUIConfigFolder()
	{
		return uiConfigFolder;
	}
}

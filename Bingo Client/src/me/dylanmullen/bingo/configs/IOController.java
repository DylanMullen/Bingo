package me.dylanmullen.bingo.configs;

import java.io.File;
import java.net.URISyntaxException;

public class IOController
{

	public static final String SEPERATOR = System.getProperty("file.separator");

	private static IOController instance;

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
			this.uiConfigFolder = new File(getClass().getClassLoader().getResource("ui").toURI());
		} catch (URISyntaxException e)
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

	public File getUIConfigFolder()
	{
		return uiConfigFolder;
	}
}

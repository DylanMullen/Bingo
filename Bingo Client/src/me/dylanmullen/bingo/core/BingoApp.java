package me.dylanmullen.bingo.core;

import java.awt.EventQueue;

import me.dylanmullen.bingo.configs.ConfigManager;
import me.dylanmullen.bingo.configs.IOController;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;
import me.dylanmullen.bingo.gfx.image.AtlasManager;
import me.dylanmullen.bingo.gfx.ui.colour.ColourManager;
import me.dylanmullen.bingo.net.handlers.ClientHandler;
import me.dylanmullen.bingo.window.bingo.BingoWindow;
import me.dylanmullen.bingo.window.login.LoginWindow;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class BingoApp
{

	private static BingoApp instance;

	private ColourManager colours;
	private AtlasManager images;

	private ClientHandler clientHandler;

	/* Windows */
	private LoginWindow loginWindow;
	private BingoWindow bingoWindow;

	/**
	 * Class constructor for Bingo Application.<br>
	 * This is main class for the application and contains the Window objects for
	 * both the Login and Bingo windows. This class also contains the networking
	 * aspect of the application.
	 */
	public BingoApp()
	{
		if (instance == null)
			instance = this;
		init();
		openLoginWindow();
//		openBingoWindow(null);
	}

	/**
	 * Returns the instance of this class.
	 * 
	 * @return instance
	 */
	public static BingoApp getInstance()
	{
		return BingoApp.instance;
	}

	/**
	 * Initialises the required dependencies of the application such as networking.
	 */
	public void init()
	{
		IOController.getController().setup();
		ConfigManager.getInstance();
		this.clientHandler = new ClientHandler("localhost", 4585);
		this.colours = new ColourManager();
		this.images = new AtlasManager();
	}

	/**
	 * Opens the Login Window as well as sets the loginWindow object.
	 */
	public void openLoginWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				loginWindow = new LoginWindow();
				loginWindow.setVisible(true);
			}
		});
	}

	/**
	 * Opens the Bingo Window.<br>
	 * If the Login Window has been created and is open, it will close it.
	 * 
	 * @param ui The User Information retrieved from the server.
	 */
	public void openBingoWindow(UserInformationChangeEvent event)
	{
		if (this.loginWindow != null)
			this.loginWindow.dispose();

		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				bingoWindow = new BingoWindow(event);
				bingoWindow.setVisible(true);
			}
		});
	}

	/**
	 * Returns the Login Window object.
	 * 
	 * @return loginWindow
	 */
	public LoginWindow getLoginWindow()
	{
		return this.loginWindow;
	}

	/**
	 * Returns the Bingo Window object.
	 * 
	 * @return bingoWindow
	 */
	public BingoWindow getBingoWindow()
	{
		return this.bingoWindow;
	}

	/**
	 * Returns the Client Handler networking object.
	 * 
	 * @return clientHandlers
	 */
	public ClientHandler getClientHandler()
	{
		return this.clientHandler;
	}

	public ColourManager getColourManager()
	{
		return colours;
	}

	public AtlasManager getAtlastManager()
	{
		return images;
	}
}

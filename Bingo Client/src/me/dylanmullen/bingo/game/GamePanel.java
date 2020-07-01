package me.dylanmullen.bingo.game;

import java.util.UUID;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.chat.ChatPanel;
import me.dylanmullen.bingo.game.components.GameComponent;
import me.dylanmullen.bingo.game.components.HeaderPanel;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

/**
 * 
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class GamePanel extends UIPanel
{

	private static final long serialVersionUID = 3683450078479151314L;
	private UUID dropletUUID;

	private HeaderPanel headerComponent;
	private ChatPanel chatComponent;
	private GameComponent gameComponent;

	/**
	 * The Game Panel of the Bingo Application.<br>
	 * This panel is the container for all the components to visually show the Bingo
	 * Game.
	 * 
	 * @param x      X-Position of the Panel.
	 * @param y      Y-Position of the Panel.
	 * @param width  The width of the Panel.
	 * @param height The height of the Panel.
	 */
	public GamePanel(UUID dropletUUID, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.dropletUUID = dropletUUID;
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("content").toColour());

		this.headerComponent = new HeaderPanel(0, 0, getWidth(), getHeight() / 6);
		getHeaderComponent().setup();
		getHeaderComponent().create();

		this.gameComponent = new GameComponent(0, headerComponent.getHeight(), (int) ((getWidth() / 3) * 2.15),
				getHeight() - headerComponent.getHeight());
		getGameComponent().setup();
		getGameComponent().create();

		this.chatComponent = new ChatPanel(dropletUUID, gameComponent.getWidth(), headerComponent.getHeight(),
				getWidth() - gameComponent.getWidth(), getHeight() - headerComponent.getHeight());
		getChatComponent().setup();
		getChatComponent().create();
	}

	@Override
	public void create()
	{
		add(getHeaderComponent());
		add(getGameComponent());
		add(getChatComponent());
	}

	/**
	 * Returns the Game Component of the Panel.
	 * 
	 * @return {@link #gameComponent}
	 */
	public GameComponent getGameComponent()
	{
		return this.gameComponent;
	}

	/**
	 * Returns the Header Component of the Panel.
	 * 
	 * @return {@link #headerComponent}
	 */
	public HeaderPanel getHeaderComponent()
	{
		return this.headerComponent;
	}

	/**
	 * Returns the Chat Component of the Panel
	 * 
	 * @return {@link #chatComponent}
	 */
	public ChatPanel getChatComponent()
	{
		return this.chatComponent;
	}

}

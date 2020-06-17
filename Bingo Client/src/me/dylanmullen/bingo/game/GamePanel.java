package me.dylanmullen.bingo.game;

import me.dylanmullen.bingo.game.components.ChatComponent;
import me.dylanmullen.bingo.game.components.GameComponent;
import me.dylanmullen.bingo.game.components.HeaderPanel;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

/**
 * 
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class GamePanel extends Panel
{

	private static final long serialVersionUID = 3683450078479151314L;

	private HeaderPanel headerComponent;
	private ChatComponent chatComponent;
	private GameComponent gameComponent;

	/**
	 * The Game Panel of the Bingo Application.<br>
	 * This panel is the container for all the components to visually show the Bingo
	 * Game.
	 * 
	 * @param x X-Position of the Panel.
	 * @param y Y-Position of the Panel.
	 * @param width The width of the Panel.
	 * @param height The height of the Panel.
	 */
	public GamePanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG.toColor());

		this.headerComponent = new HeaderPanel(0, 0, getWidth(), getHeight() / 6);
		getHeaderComponent().setup();
		getHeaderComponent().create();

		this.gameComponent = new GameComponent(0, headerComponent.getHeight(), (int) (getWidth() / 3) * 2,
				getHeight() - headerComponent.getHeight());
		getGameComponent().setup();
		getGameComponent().create();

		this.chatComponent = new ChatComponent(gameComponent.getWidth(), headerComponent.getHeight(),
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
	 * @return {@link #gameComponent}
	 */
	public GameComponent getGameComponent()
	{
		return this.gameComponent;
	}
	
	/**
	 * Returns the Header Component of the Panel.
	 * @return {@link #headerComponent}
	 */
	public HeaderPanel getHeaderComponent()
	{
		return this.headerComponent;
	}
	
	/**
	 * Returns the Chat Component of the Panel
	 * @return {@link #chatComponent}
	 */
	public ChatComponent getChatComponent()
	{
		return this.chatComponent;
	}

}

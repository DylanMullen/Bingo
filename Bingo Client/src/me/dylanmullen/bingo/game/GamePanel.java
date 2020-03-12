package me.dylanmullen.bingo.game;

import me.dylanmullen.bingo.game.components.ChatComponent;
import me.dylanmullen.bingo.game.components.GameComponent;
import me.dylanmullen.bingo.game.components.HeaderPanel;
import me.dylanmullen.bingo.game.components.NumbersCalledComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class GamePanel extends Panel
{

	private static final long serialVersionUID = 3683450078479151314L;

	private HeaderPanel header;
	private ChatComponent chat;
	private GameComponent game;

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
		
		this.header=new HeaderPanel(0, 0, getWidth(), getHeight()/6);
		header.setup();
		header.create();
		
		this.game = new GameComponent(0, header.getHeight(), (int) (getWidth() / 3) * 2,
				getHeight() - header.getHeight());
		game.setup();
		game.create();
		
		this.chat= new ChatComponent(game.getWidth(), header.getHeight(), getWidth()-game.getWidth(), getHeight()-header.getHeight());
		chat.setup();
		chat.create();
	}

	@Override
	public void create()
	{
		add(header);
		add(game);
		add(chat);
	}
	
	public void repaint()
	{
		super.repaint();
	}
	
	public GameComponent getGameComponent()
	{
		return game;
	}
	
	public NumbersCalledComponent getNumbersComp()
	{
		return header.getNumbersComp();
	}
	
	public HeaderPanel getHeader()
	{
		return header;
	}

}

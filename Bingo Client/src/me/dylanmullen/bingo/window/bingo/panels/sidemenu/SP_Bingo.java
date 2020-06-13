package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import me.dylanmullen.bingo.window.bingo.ui.buttons.ButtonContainer;
import me.dylanmullen.bingo.window.bingo.ui.buttons.SidePanelButton;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class SP_Bingo extends Panel
{
	
	private static final long serialVersionUID = 7856700131153385897L;

	private static SP_Bingo instance;
	
	private ProfilePanel panel;
	
	public SP_Bingo(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		if(instance ==null)
			instance = this;
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG_TOP.toColor());
	}

	@Override
	public void create()
	{
		panel = new ProfilePanel(30, 30, getWidth()-60, (int)(getHeight()/2.75));
		panel.setup();
		add(panel);
		createButtons();
	}
	
	private boolean debug = false;

	private void createButtons()
	{
		ButtonContainer buttons = new ButtonContainer(0, getHeight() / 2, getWidth(), getHeight() / 2)
				.setButtonHeight(60);
		buttons.addButton(new SidePanelButton("Home", new Dimension(1, 0)));
		buttons.addButton(new SidePanelButton("Play",new Dimension(2, 0)).setActive(true));
		buttons.addButton(new SidePanelButton("Settings",new Dimension(3, 0)));

		buttons.populate();
		buttons.setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		add(buttons);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(debug)
		{
			g.setColor(Color.white);
			g.drawLine(12, 0, 12, getHeight());
			g.drawLine(getWidth()-12, 0, getWidth()-12, getHeight());
			g.drawLine(0, 12, getWidth(), 12);
			g.drawLine(0,(getHeight()/2)-12,getWidth(),(getHeight()/2)-12);
		}
	}
	public ProfilePanel getPanel()
	{
		return panel;
	}
	public static SP_Bingo getInstance()
	{
		return instance;
	}

}

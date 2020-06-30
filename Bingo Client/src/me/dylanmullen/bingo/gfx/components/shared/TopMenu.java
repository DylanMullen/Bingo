package me.dylanmullen.bingo.gfx.components.shared;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.ImageButton;
import me.dylanmullen.bingo.gfx.ui.buttons.ImageButton.ImageButtonType;
import me.dylanmullen.bingo.gfx.ui.buttons.UIButton;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.util.Vector2I;

public class TopMenu extends UIPanel
{

	private static final long serialVersionUID = 284079253028974329L;
	private TopMenuListener listener;
	private UIColourSet set;

	public TopMenu(JFrame frame, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		listener = new TopMenuListener(frame);
		this.set = BingoApp.getInstance().getColourManager().getSet("frame");
	}

	private UIButton close;

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(set.getColour("top-menu").toColour());
		UIColour colour = BingoApp.getInstance().getColourManager().getSet("buttons").getColour("close");
		close = new ImageButton(
				BingoApp.getInstance().getAtlastManager().getAtlas("uiAtlas", 42).getImage(0, 1, Color.BLACK,
						colour.toColour()),
				ImageButtonType.CENTER, new ButtonInformation(new Vector2I(getWidth() - (getWidth() / 8), 0),
						new Vector2I(getWidth() / 8, getHeight()), () ->
						{
							System.exit(0);
						}));
		close.updateColours(colour.darken(0.15), colour.darken(0.25));
		ServerInformationComponent si = new ServerInformationComponent(
				new Vector2I((close.getX() - getHeight() - 5), 0), new Vector2I(getHeight(), getHeight()));
		si.create();
		add(si);
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	@Override
	public void create()
	{
		add(close);
	}

	class TopMenuListener extends MouseAdapter implements MouseMotionListener
	{
		private Point initialClick;
		private JFrame frame;

		public TopMenuListener(JFrame frame)
		{
			this.frame = frame;
		}

		public void mouseDragged(MouseEvent e)
		{
			int thisX = frame.getLocation().x;
			int thisY = frame.getLocation().y;

			// Determine how much the mouse moved since the initial click
			int xMoved = e.getX() - initialClick.x;
			int yMoved = e.getY() - initialClick.y;

			// Move window to this position
			int X = thisX + xMoved;
			int Y = thisY + yMoved;

			frame.setLocation(clamp(X), clamp(Y));
		}

		public void mousePressed(MouseEvent e)
		{
			initialClick = e.getPoint();
			getComponentAt(initialClick);
		}

		private int clamp(int i)
		{
//			if(i < 0)
//				return 0;
//			else
			return i;
		}
	}

}

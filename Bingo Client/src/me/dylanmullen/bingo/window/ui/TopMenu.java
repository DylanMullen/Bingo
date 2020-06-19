package me.dylanmullen.bingo.window.ui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import me.dylanmullen.bingo.gfx.ImageAtlas;
import me.dylanmullen.bingo.window.login.comp.ServerInformationComponent;

public class TopMenu extends Panel
{

	private TopMenuListener listener;
	private final ImageAtlas ATLAS = new ImageAtlas("uiAtlas.png", 42);

	public TopMenu(JFrame frame, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		listener = new TopMenuListener(frame);
	}

	private UIButton close;

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());

		close = new Button("", null, getWidth() - 100, 0, 100, getHeight(), UIColour.BTN_FAILURE);
		ImageComponent icon = new ImageComponent((close.getWidth() / 2) - 21, (close.getHeight() / 2) - 21, 42, 42);
		icon.setImage(ATLAS.getImage(3, 1, UIColour.BTN_BINGO_ACTIVE.toColor()));
		close.create();
		close.add(icon);

		close.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});

		ServerInformationComponent si = new ServerInformationComponent((close.getX() - 10) - (width / 8), 10, width / 8, height - 20);
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

package me.dylanmullen.bingo.window.bingo.panels;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import me.dylanmullen.bingo.window.bingo.ui.buttons.CloseButton;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class TM_Bingo extends Panel
{

	private TopMenuListener listener;
	private UIButton close;

	public TM_Bingo(JFrame frame, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		listener = new TopMenuListener(frame);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		addMouseMotionListener(listener);
		addMouseListener(listener);
	}

	@Override
	public void create()
	{
		close = new CloseButton("", getWidth() - 100, 0, 100, getHeight());
		close.create();
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

package me.dylanmullen.bingo.gfx.ui.panel;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;

public class UIScrollBar extends BasicScrollBarUI
{

	private UIColourSet set;
	private int width;

	public UIScrollBar(JScrollBar verticalScrollBar, int width)
	{
		this.width = width;
		this.set = BingoApp.getInstance().getColourManager().getSet("frame");
		verticalScrollBar.setPreferredSize(new Dimension(width, verticalScrollBar.getHeight()));
	}

	@Override
	protected JButton createIncreaseButton(int orientation)
	{
		return createButton();
	}

	@Override
	protected JButton createDecreaseButton(int orientation)
	{
		return createButton();
	}

	private JButton createButton()
	{
		JButton button = new JButton()
		{
			private static final long serialVersionUID = -1118230155211055578L;

			@Override
			protected void paintComponent(Graphics g)
			{
				g.setColor(set.getColour("scroll").darken(0.5).toColour());
				g.fillRect(0, 0, getWidth(), getHeight());
			}

			@Override
			public Dimension getPreferredSize()
			{
				return new Dimension(width, width);
			}
		};
		button.setBorder(null);
		return button;
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(set.getColour("scroll").toColour());
		g2.fillRect(r.x, r.y, r.width, r.height);
		g2.setColor(set.getColour("scroll").darken(0.15).toColour());
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r)
	{
		g.setColor(set.getColour("scroll-thumb").toColour());
		g.fillRect(r.x, r.y, r.width, r.height);
	}

}

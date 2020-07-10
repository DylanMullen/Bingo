package me.dylanmullen.bingo.game.chat;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;

public class ChatInputField extends JTextArea
{

	private static final long serialVersionUID = 2170803220795570917L;

	private Polygon shape;

	public ChatInputField(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		UIColour colour = BingoApp.getInstance().getColourManager().getSet("frame").getColour("side-primary")
				.lighten(0.30);
		setBackground(colour.toColour());
		setForeground(colour.getTextColour());
		setCaretColor(getForeground());
		setup();
	}

	private void setup()
	{
		setBorder(new EmptyBorder(5, 5, 5, 10));
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(false);

		this.shape = new Polygon();
		shape.addPoint(0, 0);
		shape.addPoint(getWidth(), 0);
		shape.addPoint(getWidth() - 5, getHeight());
		shape.addPoint(0, getHeight());
	}

	public int getTextAngle()
	{
		if (getText().length() == 0)
		{
			return 0;
		} else
		{
			return (int) (360 * ((double) getText().length() / 128));
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillPolygon(shape);
		super.paintComponent(g2);
	}

}

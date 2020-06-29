package me.dylanmullen.bingo.gfx.components.login;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.panel.RoundedPanel;
import me.dylanmullen.bingo.util.FontUtil;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class WarningInfoComponent extends RoundedPanel
{

	private static final long serialVersionUID = 5423352332179650770L;
	private final int OFFSET = 10;

	private List<String> lines;

	/**
	 * Creates a Warning Information rounded panel which displays information to the
	 * Player.
	 * 
	 * @param x      X-Position of the Component.
	 * @param y      Y-Position of the Component.
	 * @param width  The width of the Component.
	 * @param height The height of the Component.
	 */
	public WarningInfoComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	/**
	 * Creates a Warning Information rounded panel which displays information to the
	 * Player. <br>
	 * This uses default values to construct a dynamically sized component.
	 * <ul>
	 * <li>{@link #getX()}=0</li>
	 * <li>{@link #getY()}=0</li>
	 * <li>{@link #getWidth()}=0</li>
	 * <li>{@link #getHeight()}=0</li>
	 * </ul>
	 */
	public WarningInfoComponent()
	{
		this(0, 0, 0, 0);
	}

	@Override
	public void setup()
	{
		this.lines = new ArrayList<>();
		setFont(new Font("Calibri", Font.PLAIN, 25));
		updateBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("information"));
		construct("Please Login/Register");
	}

	@Override
	public void create()
	{
		setup();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(getForeground());
		Dimension prev = null;
		int indentY = -1;
		int height = -1;
		for (int i = 0; i < lines.size(); i++)
		{
			String text = lines.get(i);
			prev = FontUtil.getFontSize(getFontMetrics(getFont()), text, 0, 0);
			if (height == -1)
			{
				height = prev.height * lines.size();
				indentY = (getHeight() / 2 + ((getHeight() - height))) / 2;
			}

			g2.drawString(text, getWidth() / 2 - (prev.width / 2), indentY);
			indentY += prev.height;
		}
	}

	public void updateText(String text)
	{
		construct(text);
	}

	private void construct(String message)
	{
		lines.clear();
		FontMetrics metrics = getFontMetrics(getFont());
		StringBuilder lineBuilder = new StringBuilder();
		int currentWidth = 0;

		for (int i = 0; i < message.length(); i++)
		{
			currentWidth = FontUtil.getFontSize(metrics, lineBuilder.toString(), 0, 0).width;
			if (currentWidth >= getWidth() - (OFFSET * 3) || message.charAt(i) == '\n')
			{
				currentWidth = 0;
				lines.add(lineBuilder.toString());
				lineBuilder = new StringBuilder();
			}
			lineBuilder.append(message.charAt(i));

			if (message.length() - 1 == i)
				lines.add(lineBuilder.toString());
		}
		repaint();
	}

	/**
	 * Updates the background colour of the Component.
	 * 
	 * @param colour The colour to change to.
	 */
	public void updateBackground(UIColour colour)
	{
		setBackground(colour.toColour());
		setForeground(colour.getTextColour());
		repaint();
	}
}

package me.dylanmullen.bingo.window.bingo.ui.buttons;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation.TextPosition;
import me.dylanmullen.bingo.gfx.ui.buttons.UIButton;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class SidePanelButton extends UIButton
{
	// TODO

	private static final long serialVersionUID = -1495099977168142089L;

	private UIColourSet colours;
	private BufferedImage image;

	public SidePanelButton(String text, BufferedImage icon, ButtonInformation information)
	{
		super(text, information);
		this.image = icon;
		this.colours = BingoApp.getInstance().getColourManager().getSet("buttons");
		setup();
	}

	protected void setup()
	{
		updateColours(colours.getColour("sidepanel-bg"), colours.getColour("sidepanel-hover"));
		setFont(new Font("Calibri", Font.PLAIN, 25));
		getInformation().setTextPosition(TextPosition.LEFT);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawBody(g2);
		drawContent(g2);
	}

	private void drawBody(Graphics2D g2)
	{
		g2.setColor((isActive() ? getForeground() : getBackground()));
		g2.fillRect(0, 0, getWidth(), getHeight());

		if (isActive())
		{
			g2.setColor(colours.getColour("sidepanel-active").toColour());
			g2.fillRect(0, 0, getWidth() / 20, getHeight());
		}
	}

	private void drawContent(Graphics2D g2)
	{
		if (image != null)
			g2.drawImage(image, (getWidth() / 20) + 5, 5 + getHeight() / 2 - (getHeight() / 2), getHeight() - 10,
					getHeight() - 10, null);
		g2.setColor(colours.getColour("sidepanel-active").toColour());
		drawText(g2, (getWidth() / 20) + 5 + getHeight());
	}

}

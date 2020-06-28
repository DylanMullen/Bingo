package me.dylanmullen.bingo.window.bingo.ui.buttons;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		this.colours = BingoApp.getInstance().getColours().getSet("buttons");
	}

	protected void setup()
	{
		setBackground(
				(isActive() ? colours.getColour("sidepanel-active") : colours.getColour("sidepanel-bg")).toColour());
		setFont(new Font("Calibri", Font.PLAIN, 25));
		getInformation().setTextPosition(TextPosition.LEFT);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		drawBody(g2);
		drawContent(g2);
	}

	private void drawBody(Graphics2D g2)
	{
		g2.setColor(colours.getColour("sidepanel-bg").toColour());
		g2.drawRect(0, 0, getWidth(), getHeight());

		if (isActive())
		{
			g2.setColor(colours.getColour("sidepanel-active").toColour());
			g2.drawRect(0, 0, getWidth() / 20, getHeight());
		}
	}

	private void drawContent(Graphics2D g2)
	{
		if(image !=null)
			g2.drawImage(image, (getWidth() / 20) + 5, getHeight() / 2 - (getHeight() / 4), getHeight(), getHeight(), null);
		g2.setColor(colours.getColour("sidepanel-active").toColour());
		drawText(g2, (getWidth() / 20) + 5 + getHeight());
	}

}

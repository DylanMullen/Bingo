package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation.TextPosition;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class SidePanelButton extends UIButton
{

	private static final long serialVersionUID = -1495099977168142089L;

	private UIColourSet colours;
	private BufferedImage image;

	public SidePanelButton(String text, BufferedImage icon, ButtonInformation information)
	{
		super(text, information);
		this.image = icon;
		this.colours = BingoApp.getInstance().getColourManager().getSet("buttons");
//		setup();
	}

	public void setup()
	{
		updateColours(BingoApp.getInstance().getColourManager().getSet("frame").getColour("side-primary").lighten(0.35),
				BingoApp.getInstance().getColourManager().getSet("frame").getColour("side-primary").lighten(0.25));
		getInformation().setFont(FontUtil.getFont(getText(), this,
				new Vector2I(getWidth() - ((getWidth() / 20) + 5 + getHeight()), getHeight()-30)));
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
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawImage(image, (getWidth() / 20) + 5, 5 + getHeight() / 2 - (getHeight() / 2), getHeight() - 10,
					getHeight() - 10, null);
		}
		drawText(g2, (getWidth() / 20) + 5 + getHeight(), colours.getColour("sidepanel-active").toColour());
	}

}

package me.dylanmullen.bingo.game.cards;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.ImageButton;
import me.dylanmullen.bingo.gfx.ui.buttons.ImageButton.ImageButtonType;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

public class CardPagnationPanel extends UIPanel
{

	private static final long serialVersionUID = 7043937322050274571L;

	private Dimension textDimension;
	private int page;

	public CardPagnationPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		UIColour colour = BingoApp.getInstance().getColourManager().getSet("frame").getColour("content").darken(0.25);
		setBackground(colour.toColour());
		setForeground(colour.getTextColour());
		setFont(new Font("Calibri", Font.PLAIN, 20));
		updatePage(1);
		setup();
	}

	@Override
	public void setup()
	{

		ImageButton left = new ImageButton(
				BingoApp.getInstance().getAtlastManager().getAtlas("uiAtlas", 64).getSubImage(0,1),
				ImageButtonType.CENTER, new ButtonInformation(new Vector2I(10, getHeight() / 2 - (getHeight() - 15) / 2),
						new Vector2I(getHeight() - 15, getHeight() - 15), () ->
						{
							
						}));

		ImageButton right = new ImageButton(
				BingoApp.getInstance().getAtlastManager().getAtlas("uiAtlas", 64).getSubImage(1, 1),
				ImageButtonType.CENTER,
				new ButtonInformation(
						new Vector2I(getWidth() - ((getHeight() - 15) + 10),
								getHeight() / 2 - (getHeight() - 15) / 2),
						new Vector2I(getHeight() - 15, getHeight() - 15), () ->
						{

						}));
		left.updateColours(BingoApp.getInstance().getColourManager().getSet("buttons").getColour("send"),
				BingoApp.getInstance().getColourManager().getSet("buttons").getColour("send"));
		right.updateColours(BingoApp.getInstance().getColourManager().getSet("buttons").getColour("send"),
				BingoApp.getInstance().getColourManager().getSet("buttons").getColour("send"));
		add(left);
		add(right);
	}

	@Override
	public void create()
	{

	}

	private void updatePage(int page)
	{
		this.page = page;
		this.textDimension = FontUtil.getFontSize(getFontMetrics(getFont()), "Page " + page, 0, 0);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		drawPageString(g2);
	}

	private void drawPageString(Graphics2D g2)
	{
		g2.setColor(getForeground());
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("Page " + page, getWidth() / 2 - (textDimension.width / 2),
				getHeight() / 2 + (textDimension.height / 4));
	}

}

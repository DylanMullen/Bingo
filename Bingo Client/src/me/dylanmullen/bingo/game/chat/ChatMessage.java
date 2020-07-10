package me.dylanmullen.bingo.game.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.util.FontUtil;

public class ChatMessage extends JComponent
{

	private static final long serialVersionUID = -6059262950079715491L;

	private final int OFFSET = 5;
	private final int HEADER_HEIGHT = 35;

	private String username;
	private String userGroup;
	private String message;
	private List<String> lines;

	private Font headerFont;
	private Dimension userGroupDimension;

	private UIColourSet set;

	public ChatMessage(int x, int y, int width, UIColour colour)
	{
		this.lines = new ArrayList<>();
		this.set = BingoApp.getInstance().getColourManager().getSet("chat");
		setBackground(colour.toColour());
		setForeground(colour.darken(0.15).toColour());
		setBounds(x, y, width, 0);
	}

	public void setup()
	{
		construct(message);
		setBounds(getX(), getY(), getWidth(), getHeightOfLines());
		this.headerFont = new Font("Calibri", Font.PLAIN, 18);
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
			if (currentWidth >= getWidth() - (OFFSET * 3))
			{
				currentWidth = 0;
				lines.add(lineBuilder.toString());
				lineBuilder = new StringBuilder();
			}
			lineBuilder.append(message.charAt(i));

			if (message.length() - 1 == i)
				lines.add(lineBuilder.toString());
		}
	}

	private void reconstruct(int width)
	{
		lines.clear();
		FontMetrics metrics = getFontMetrics(getFont());
		StringBuilder lineBuilder = new StringBuilder();
		int currentWidth = 0;

		for (int i = 0; i < message.length(); i++)
		{
			currentWidth = FontUtil.getFontSize(metrics, lineBuilder.toString(), 0, 0).width;
			if (currentWidth >= width - (OFFSET * 3))
			{
				currentWidth = 0;
				lines.add(lineBuilder.toString());
				lineBuilder = new StringBuilder();
			}
			lineBuilder.append(message.charAt(i));

			if (message.length() - 1 == i)
				lines.add(lineBuilder.toString());
		}
	}

	public void resizeMessage(int width)
	{
		reconstruct(width);
		setBounds(getX(), getY(), width, getHeightOfLines());
		repaint();
	}

	private int getHeightOfLines()
	{
		int height = HEADER_HEIGHT + 5;
		for (String s : lines)
		{
			Dimension dim = FontUtil.getFontSize(getFontMetrics(getFont()), s, 0, 5);
			height += dim.height / 2 + 5;
		}
		height += 10;
		return height;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getForeground());
		g2.fillRect(0, 0, getWidth(), getHeight());
		drawHeader(g2);
		g2.setFont(getFont());
		drawMessages(g2);
	}

	private void drawHeader(Graphics2D g2)
	{
		if (userGroupDimension == null)
			userGroupDimension = FontUtil.getFontSize(getFontMetrics(headerFont), userGroup, 0, 0);

		g2.setFont(headerFont);
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), HEADER_HEIGHT);
		drawUserPill(g2);
		g2.setColor(set.getColour("text").toColour());
		int indent = HEADER_HEIGHT / 2
				+ FontUtil.getFontSize(getFontMetrics(getFont()), getHeaderString(), 0, 0).height / 4;
		g2.drawString(getHeaderString(), OFFSET + (userGroupDimension.width + 10) + 5, indent);
	}

	private void drawUserPill(Graphics2D g2)
	{
		g2.setColor(Color.black);
		g2.fillRoundRect(OFFSET, HEADER_HEIGHT / 2 - (HEADER_HEIGHT - 10) / 2, userGroupDimension.width + 10,
				HEADER_HEIGHT - 10, 15, 15);
		g2.setColor(set.getColour("text").toColour());
		g2.drawString(userGroup, OFFSET + (userGroupDimension.width) / 2 - userGroupDimension.width / 2 + 5,
				HEADER_HEIGHT / 2 + userGroupDimension.height / 4);
	}

	private void drawMessages(Graphics2D g2)
	{
		g2.setColor(Color.white);
		Dimension prev = null;
		int indentY = HEADER_HEIGHT + 10;
		for (String string : lines)
		{
			prev = FontUtil.getFontSize(getFontMetrics(getFont()), string, 0, 0);

			indentY += prev.height / 2;
			g2.drawString(string, OFFSET, indentY);
			indentY += 5;
		}
	}

	private String getHeaderString()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setUserGroup(String userGroup)
	{
		this.userGroup = userGroup;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}

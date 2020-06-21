package me.dylanmullen.bingo.game.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ChatMessage extends JComponent
{

	private static final long serialVersionUID = -6059262950079715491L;

	private final int OFFSET = 5;
	private final int HEADER_HEIGHT = 30;

	private String username;
	private String userGroup;
	private String message;

	private List<String> lines;

	public ChatMessage(int x, int y, int width)
	{
		this.lines = new ArrayList<>();

		setBounds(x, y, width, 0);
	}

	public void setup()
	{
		construct(message);
		setBounds(getX(), getY(), getWidth(), getHeightOfLines());
	}

	private void construct(String message)
	{
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

	private int getHeightOfLines()
	{
		int height = HEADER_HEIGHT + 5;
		for (String s : lines)
		{
			Dimension dim = FontUtil.getFontSize(getFontMetrics(getFont()), s, 0, 5);
			height += dim.height / 2 + 5;
		}
		height -= 5;
		return height;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.fillRect(0, 0, getWidth(), getHeight());
		drawHeader(g2);
		g2.setFont(getFont());
		drawMessages(g2);
	}

	private void drawHeader(Graphics2D g2)
	{
		g2.setFont(new Font("Calibri", Font.PLAIN, 18));
		g2.setColor(UIColour.BINGO_BALL_3.toColor());
		g2.fillRect(0, 0, getWidth(), HEADER_HEIGHT);
		g2.setColor(Color.white);
		int indent = HEADER_HEIGHT / 2
				+ FontUtil.getFontSize(getFontMetrics(getFont()), "[" + userGroup + "] " + username, 0, 0).height / 4;
		g2.drawString("[" + userGroup + "] " + username, OFFSET, indent);
	}

	private void drawMessages(Graphics2D g2)
	{
		g2.setColor(Color.white);
		Dimension prev = null;
		int indentY = HEADER_HEIGHT + 5;
		for (String string : lines)
		{
			prev = FontUtil.getFontSize(getFontMetrics(getFont()), string, 0, 0);

			indentY += prev.height / 2;
			g2.drawString(string, OFFSET, indentY);
			indentY += 5;
		}
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

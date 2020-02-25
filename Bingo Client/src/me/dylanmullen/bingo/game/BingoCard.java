package me.dylanmullen.bingo.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.UUID;

import javax.swing.JComponent;

import me.dylanmullen.bingo.game.components.BingoSquare;
import me.dylanmullen.bingo.window.ui.UIColour;

public class BingoCard extends JComponent
{

	private BingoSquare[] squares;
	private  final int BUFFER = 35;

	private UUID uuid;

	public BingoCard(int x, int y, int w, int h)
	{
		setBackground(UIColour.FRAME_BINGO_BG_TOP.toColor());
		setOpaque(true);
		int widthSize = w / 9;
		int heightSize = h / 3;

		if ((double) (w / 9) > 0)
			w = widthSize * 9;
		if ((double) (h / 3) > 0)
			h = heightSize * 3;

		h += BUFFER;
		setBounds(x, y, w, h);
		squares = new BingoSquare[9 * 3];

		int indentX = 0;
		int indentY = BUFFER;

		for (int i = 0; i < squares.length; i++)
		{
			squares[i] = new BingoSquare(indentX, indentY, widthSize, heightSize);
			indentX += widthSize;
			if ((i + 1) % 9 == 0 && i != 0)
			{
				indentY += heightSize;
				indentX = 0;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		// font anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int fontSize = 25; // font for everything else
		Font font = new Font("Calibri", Font.PLAIN, fontSize);
		g2.setFont(font);
		g2.setColor(UIColour.BTN_BINGO_ACTIVE.toColor());
		g2.fillRect(0, 0, getWidth(), getHeight());
		paintGrid(g2);
		paintTop(g2);

	}

	private void paintGrid(Graphics2D g2)
	{
		for (int i = 0; i < squares.length; i++)
		{
			BingoSquare square = squares[i];

			g2.setColor(Color.BLACK);
			g2.fill(square);
			g2.setColor(Color.WHITE);
			g2.draw(square);

			int xp = (int) ((int) square.getX() + square.getWidth() - (square.getWidth() / 1.5));
			int yp = (int) ((int) square.getY() + (square.getHeight() / 1.5));

			if (!square.isEmpty())
				g2.drawString(square.getNumber() + "", xp, yp);
		}
	}

	private void paintTop(Graphics2D g2)
	{
		if (uuid == null)
			return;
		Font font = new Font("Calibri", Font.PLAIN,20);
		g2.setFont(font);
		g2.drawString(uuid.toString(), 0, 32);
	}

	public void setCardNumbers(String s, UUID uuid)
	{
		this.uuid = uuid;
		System.out.println(uuid == null);
		String[] temp = s.split("/");
		for (int i = 0; i < temp.length; i++)
		{
			String[] numbers = temp[i].split("\\.");
			setNumber(Integer.parseInt(numbers[0]));	
			setNumber(Integer.parseInt(numbers[1]));
			setNumber(Integer.parseInt(numbers[2]));
			setNumber(Integer.parseInt(numbers[3]));
			setNumber(Integer.parseInt(numbers[4]));
			row++;
		}
	}

	private int row = 0;

	private void setNumber(int x)
	{
		int index = (x / 10 == 9 ? 8 : x / 10);
		squares[index + row * 9].setNumber(x);
	}

}
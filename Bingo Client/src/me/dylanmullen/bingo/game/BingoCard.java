package me.dylanmullen.bingo.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.UUID;

import javax.swing.JComponent;

import me.dylanmullen.bingo.game.components.BingoSquare;
import me.dylanmullen.bingo.game.components.overlays.PurchaseOverlay;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.UIColour;

public class BingoCard extends JComponent
{

	private static final long serialVersionUID = -1646554212523678637L;

	private PurchaseOverlay po;

	private BingoSquare[] squares;
	public final int BUFFER = 36;

	private boolean purchased = false;
	private boolean selected = false;

	private UUID uuid;

	private int x, y;

	public BingoCard(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		createSquares(w, h);
	}

	private void createSquares(int w, int h)
	{
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
		setBackground(
				(purchased ? UIColour.CARD_PURCHASED : (selected ? UIColour.CARD_SELECTED : UIColour.CARD_DEFAULT))
						.toColor());

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		int fontSize = 25;
		Font font = new Font("Calibri", Font.PLAIN, fontSize);
		g2.setFont(font);
		paintGrid(g2);
		paintTop(g2);

	}

	private void paintGrid(Graphics2D g2)
	{
		for (int i = 0; i < squares.length; i++)
		{
			BingoSquare square = squares[i];

			g2.setColor((square.isCalled() ? UIColour.SQUARE_MARKED.toColor() : getColour(i)));
			g2.fill(square);

			Dimension dim = FontUtil.getFontSize(getFontMetrics(g2.getFont()), g2.getFont(), square.getNumber() + "", 0,
					0);
			int xp = (int) ((int) square.getX() + square.getWidth() / 2 - (dim.width / 2));
			int yp = (int) ((int) square.getY() + square.getHeight() / 2 + (dim.height / 4));

			g2.setColor(Color.WHITE);
			if (!square.isEmpty())
				g2.drawString(square.getNumber() + "", xp, yp);

//			g2.setColor(Color.red);
//			g2.drawLine(square.x, square.y+square.height/2, square.x+square.width, square.y+square.height/2);
//			g2.drawLine(square.x+square.width/2, square.y, square.x+square.width/2, square.y+square.height);

			int radius = (square.height - 5);

			Color col = UIColour.FRAME_BINGO_BG_TOP.toColor();
			g2.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 95));
			if (square.isCalled())
				g2.fillOval((int) (square.x + square.width / 2 - radius / 2),
						(int) (square.y + square.height / 2 - radius / 2), radius, radius);
		}
	}

	private Color getColour(int n)
	{
		return (n % 2 == 0 ? UIColour.FRAME_BINGO_BG_SIDE.toColor() : UIColour.FRAME_BINGO_BG_TOP.toColor());
	}

	private void paintTop(Graphics2D g2)
	{
		if (uuid == null)
			return;
		Font font = new Font("Calibri", Font.PLAIN, 20);
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		Dimension dim = FontUtil.getFontSize(getFontMetrics(font), font, uuid.toString(), 0, 0);
		g2.drawString(uuid.toString(), getWidth() / 2 - (dim.width / 2), BUFFER / 2 + dim.height / 4);
	}

	public void setCardNumbers(String s, UUID uuid)
	{
		this.uuid = uuid;
		String[] temp = s.split("/");
		row = 0;

		setPurchased(false);

		for (int i = 0; i < squares.length; i++)
		{
			BingoSquare square = squares[i];
			square.setNumber(-1);
			square.setCalled(false);
		}

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

	public void setCard(BingoCard card)
	{
		this.uuid = card.getUUID();
		this.squares = card.getSquares();
	}

	public UUID getUUID()
	{
		return uuid;
	}

	private int row = 0;

	private void setNumber(int x)
	{
		int index = (x / 10 == 9 ? 8 : x / 10);
		squares[index + row * 9].setNumber(x);
	}

	public void markNumber(int num)
	{
		for (BingoSquare square : squares)
			if (square.getNumber() == num)
			{
				square.setCalled(true);
				repaint();
			}
	}

	public void setPurchased(boolean val)
	{
		this.purchased = val;
	}

	public boolean isPurchased()
	{
		return purchased;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
	}

	public BingoSquare[] getSquares()
	{
		return squares;
	}

	public void setY(int y)
	{
		setBounds(x, y, getWidth(), getHeight());
		repaint();
	}

}

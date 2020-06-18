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

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class BingoCard extends JComponent
{

	private static final long serialVersionUID = -1646554212523678637L;

	private PurchaseOverlay purchaseOverlay;

	private BingoSquare[] squares;
	public final int BUFFER = 36;

	private boolean purchased = false;
	private boolean selected = false;

	private UUID uuid;

	private int x, y;

	/**
	 * This is the Bingo Card for the game. <br>
	 * This handles the numbers that is on the card.
	 * 
	 * @param x      X-Position to be drawn at.
	 * @param y      Y-Position to be drawn at.
	 * @param width  The width of the Bingo Card.
	 * @param height The height of the Bingo Card.
	 */
	public BingoCard(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		createSquares(width, height);
	}

	/**
	 * Creates the Bingo Squares for the card. <br>
	 * The method calculates the height and width of each square based on the width
	 * and height of the Bingo Card.
	 * 
	 * @param width  The width of the Bingo Card
	 * @param height The height of the Bingo Cards
	 */
	private void createSquares(int width, int height)
	{
		int widthSize = width / 9;
		int heightSize = height / 3;

		if ((double) (width / 9) > 0)
			width = widthSize * 9;
		if ((double) (height / 3) > 0)
			height = heightSize * 3;

		height += this.BUFFER;

		setBounds(x, y, width, height);
		this.squares = new BingoSquare[9 * 3];

		int indentX = 0;
		int indentY = this.BUFFER;

		for (int i = 0; i < squares.length; i++)
		{
			this.squares[i] = new BingoSquare(indentX, indentY, widthSize, heightSize);
			indentX += widthSize;
			if ((i + 1) % 9 == 0 && i != 0)
			{
				indentY += heightSize;
				indentX = 0;
			}
		}
	}

	/**
	 * Updates the numbers and the UUID of the Bingo Card.<br>
	 * This data is retrieved from the server and has to decoded.
	 * 
	 * @param numbersData Number data from the Packet
	 * @param uuid        UUID of the Bingo Card.
	 */
	public void updateCardInformation(String numbersData, UUID uuid)
	{
		String[] temp = numbersData.split("/");

		setUUID(uuid);
		setPurchased(false);

		for (int i = 0; i < this.squares.length; i++)
		{
			BingoSquare square = this.squares[i];
			square.setNumber(-1);
			square.setCalled(false);
		}

		for (int i = 0; i < temp.length; i++)
		{
			String[] numbers = temp[i].split("\\.");
			setNumber(i, Integer.parseInt(numbers[0]));
			setNumber(i, Integer.parseInt(numbers[1]));
			setNumber(i, Integer.parseInt(numbers[2]));
			setNumber(i, Integer.parseInt(numbers[3]));
			setNumber(i, Integer.parseInt(numbers[4]));
		}
	}

	/**
	 * Marks a number on the Bingo Card.
	 * 
	 * @param number Number to mark.
	 */
	public void markNumber(int number)
	{
//		for (BingoSquare square : squares)
//			if (square.getNumber() == number)
//			{
//				square.setCalled(true);
//				repaint();
//			}
		int column = getColumnOfNumber(number);
		for (int row = 0; row < 3; row++)
		{
			BingoSquare square = this.squares[column + row * 9];
			if (square.getNumber() == number)
			{
				square.setCalled(true);
				repaint();
			}
		}
	}

	/**
	 * Shows the Purchase Overlay as long as the card is not already selected.<br>
	 * If the Purchase Overlay does not exist, then it will be created. <br>
	 * This will set the card as selected.
	 */
	public void showPurchaseOverlay()
	{
		if (!this.selected)
		{
			if (this.purchaseOverlay == null)
			{
				this.purchaseOverlay = new PurchaseOverlay(this, UIColour.FRAME_BINGO_BG_TOP.toColor(), 155, 0,
						this.BUFFER, getWidth(), getHeight() - this.BUFFER);
				this.purchaseOverlay.setup();
			}

			this.purchaseOverlay.setVisible(true);
			this.selected = true;

			add(this.purchaseOverlay);
		}
	}

	/**
	 * Hides the Purchase Overlay as long as the card is currently selected.<br>
	 * This will set the card as unselected.
	 */
	public void hidePurchaseOverlay()
	{
		if (this.selected)
		{
			this.purchaseOverlay.setVisible(false);
			this.selected = false;

			remove(this.purchaseOverlay);
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		setBackground((isPurchased() ? UIColour.CARD_PURCHASED
				: (isSelected() ? UIColour.CARD_SELECTED : UIColour.CARD_DEFAULT)).toColor());

		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		// TODO Store the font in a class variable. More efficent than creating a
		// variable every draw call.
		Font font = new Font("Calibri", Font.PLAIN, 25);
		g2.setFont(font);

		paintTop(g2);
		paintGrid(g2);
	}

	/**
	 * Paints the top of the Bingo Card. This section contains the UUID of the
	 * ticket.
	 * 
	 * @param g2 Graphics2D object from {@link #paintComponent(Graphics)}.
	 */
	private void paintTop(Graphics2D g2)
	{
		if (getUUID() == null)
			return;

		// TODO Store the font in a class variable. More efficent than creating a
		// variable every draw call.

		Font font = new Font("Calibri", Font.PLAIN, 20);
		g2.setFont(font);
		g2.setColor(Color.WHITE);

		Dimension dim = FontUtil.getFontSize(getFontMetrics(font), getUUID().toString(), 0, 0);
		g2.drawString(getUUID().toString(), getWidth() / 2 - (dim.width / 2), this.BUFFER / 2 + dim.height / 4);
	}

	/**
	 * Paints the Bingo Square grid onto the Bingo Card. <br>
	 * If the Bingo Card is marked the square will be coloured in with the
	 * {@link UIColour#SQUARE_MARKED} colour, otherwise it will be coloured with the
	 * colour retrieved from {@link #getColour(int)}.
	 * 
	 * @param g2 Graphics2D object from {@link #paintComponent(Graphics)}.
	 */
	private void paintGrid(Graphics2D g2)
	{
		for (int i = 0; i < this.squares.length; i++)
		{
			BingoSquare square = this.squares[i];

			g2.setColor((square.isCalled() ? UIColour.SQUARE_MARKED.toColor() : getColour(i)));
			g2.fill(square);

			Dimension dim = FontUtil.getFontSize(getFontMetrics(g2.getFont()), square.getNumber() + "", 0,
					0);
			int xp = (int) ((int) square.getX() + square.getWidth() / 2 - (dim.width / 2));
			int yp = (int) ((int) square.getY() + square.getHeight() / 2 + (dim.height / 4));

			g2.setColor(Color.WHITE);
			if (!square.isEmpty())
				g2.drawString(square.getNumber() + "", xp, yp);

			int radius = (square.height - 5);

			Color col = UIColour.FRAME_BINGO_BG_TOP.toColor();
			g2.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 95));
			
			if (square.isCalled())
				g2.fillOval((int) (square.x + square.width / 2 - radius / 2),
						(int) (square.y + square.height / 2 - radius / 2), radius, radius);
		}
	}

	/**
	 * Sets a Bingo Square number.
	 * 
	 * @param row    Row of the square
	 * @param number Number to set to.
	 */
	private void setNumber(int row, int number)
	{
		int column = getColumnOfNumber(number);
		this.squares[column + row * 9].setNumber(number);
	}

	/**
	 * Returns the column of a number.<br>
	 * This method gets the column by dividing the number by 10 to get the tenth
	 * digit. If this number is 90 then it is set to the 80-90 column instead of a
	 * 9th column.
	 * 
	 * @param number Number to retrieve the column
	 * @return column of Number.
	 */
	private int getColumnOfNumber(int number)
	{
		return (number / 10 == 9 ? 8 : number / 10);
	}

	/**
	 * Returns a colour to colour the square. <br>
	 * This is dependent on if the integer passed in is even. <br>
	 * 
	 * @param n The number to check.
	 * @return Colour (Even = {@link UIColour#FRAME_BINGO_BG_SIDE}, Odd =
	 *         {@link UIColour#FRAME_BINGO_BG_TOP}.)
	 */
	private Color getColour(int n)
	{
		return (n % 2 == 0 ? UIColour.FRAME_BINGO_BG_SIDE.toColor() : UIColour.FRAME_BINGO_BG_TOP.toColor());
	}

	/**
	 * Returns the Bingo Squares of the Bingo Card.
	 * 
	 * @return {@link #squares}
	 */
	public BingoSquare[] getSquares()
	{
		return this.squares;
	}

	/**
	 * Returns if the Bingo Card is purchased.
	 * 
	 * @return {@link #purchased}
	 */
	public boolean isPurchased()
	{
		return this.purchased;
	}

	/**
	 * Returns if the Bingo Card is seleted.
	 * 
	 * @return {@link #selected}
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * Sets the Bingo Card as purchased
	 * 
	 * @param purchased
	 */
	public void setPurchased(boolean purchased)
	{
		this.purchased = purchased;
	}

	/**
	 * Sets the Bingo Card as selected.
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	/**
	 * Sets the UUID of the Bingo Card.
	 * 
	 * @param uuid The UUID of the Card.
	 */
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * Sets the y-position of the Bingo Card. This will repaint the card.
	 * 
	 * @param y The new y-position.
	 */
	public void setY(int y)
	{
		setBounds(x, y, getWidth(), getHeight());
		repaint();
	}

	/**
	 * Returns the Bingo Cards UUID
	 * 
	 * @return {@link #uuid}
	 */
	public UUID getUUID()
	{
		return this.uuid;
	}

}

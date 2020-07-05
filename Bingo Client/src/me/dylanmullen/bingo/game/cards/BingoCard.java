package me.dylanmullen.bingo.game.cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.UUID;

import javax.swing.JComponent;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.components.BingoSquare;
import me.dylanmullen.bingo.game.components.overlays.PurchaseOverlay;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class BingoCard extends JComponent
{

	private static final long serialVersionUID = -1646554212523678637L;

	private UUID dropletUUID;
	private PurchaseOverlay purchaseOverlay;

	private BingoSquare[] squares;
	public static final int BUFFER = 36;

	private UIColourSet set;
	private CardInformation cardInfo;

	private Font headerFont;
	private Font gridFont;
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
	public BingoCard(UUID dropletUUID, int x, int y, int width, int height, CardInformation info)
	{
		this.dropletUUID = dropletUUID;
		this.x = x;
		this.y = y;
		this.cardInfo = info;
		this.set = BingoApp.getInstance().getColourManager().getSet("bingo-card");
		createSquares(width, height);
		updateCardInformation(cardInfo);
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
		int heightSize = widthSize;

		height = heightSize * 3;
		height += BingoCard.BUFFER;

		setBounds(x, y, width, height);
		this.squares = new BingoSquare[9 * 3];

		int indentX = 0;
		int indentY = BingoCard.BUFFER;

		for (int i = 0; i < squares.length; i++)
		{
			this.squares[i] = new BingoSquare(indentX, indentY, widthSize, widthSize);
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
	public void updateCardInformation(CardInformation info)
	{
		this.cardInfo = info;

		for (int i = 0; i < this.squares.length; i++)
		{
			BingoSquare square = this.squares[i];
			square.setNumber(-1);
			square.setCalled(false);
		}

		for (int i = 0; i < info.getNumbers().length; i++)
		{
			int temp = i + 1;
			int row = temp / 5;
			if (temp % 5 == 0 && temp != 0)
				row--;
			setNumber(row, info.getNumbers()[i]);
		}

		this.headerFont = FontUtil.getFont(info.getUUID().toString(), this,
				new Vector2I((getWidth() - 10) - getWidth() / 5, BUFFER));
		this.gridFont = FontUtil.getFont("90", this, new Vector2I((getWidth() / 9) - 15, (getWidth() / 9) - 15));
	}

	/**
	 * Marks a number on the Bingo Card.
	 * 
	 * @param number Number to mark.
	 */
	public void markNumber(int number)
	{
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
		if (!this.cardInfo.isSelected())
		{
			if (this.purchaseOverlay == null)
			{
				this.purchaseOverlay = new PurchaseOverlay(this, set.getColour("selected").applyTransparency(150), 0,
						BingoCard.BUFFER, getWidth(), getHeight() - BingoCard.BUFFER);
				this.setForeground(set.getColour("selected").getTextColour());
				this.purchaseOverlay.setup();
			}

			this.purchaseOverlay.setVisible(true);
			this.cardInfo.setSelected(true);

			add(this.purchaseOverlay);
		}
	}

	/**
	 * Hides the Purchase Overlay as long as the card is currently selected.<br>
	 * This will set the card as unselected.
	 */
	public void hidePurchaseOverlay()
	{
		if (this.cardInfo.isSelected())
		{
			this.purchaseOverlay.setVisible(false);
			this.cardInfo.setSelected(false);

			remove(this.purchaseOverlay);
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		setBackground(getCurrentColour().toColour());

		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		paintTop(g2);
		paintGrid(g2);
		super.paintComponent(g);
	}

	private UIColour getCurrentColour()
	{
		return (isPurchased() ? set.getColour("purchased")
				: (isSelected() ? set.getColour("selected") : set.getColour("header")));
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

//		g2.setColor(getCurrentColour().darken(0.25).toColour());
//		g2.setStroke(new BasicStroke(4));
//		g2.drawLine(0, BUFFER-2, getWidth(), BUFFER-2);
		g2.setFont(headerFont);
		drawUUID(g2);
		drawInfoPill(g2);
	}

	private void drawUUID(Graphics2D g2)
	{
		g2.setColor(Color.WHITE);
		Dimension dim = FontUtil.getFontSize(getFontMetrics(getFont()), getUUID().toString(), 0, 0);
		int xPos = ((getWidth()) - (getWidth() / 5)) / 2 - (dim.width / 2) - 10;
		g2.drawString(getUUID().toString(), xPos, BingoCard.BUFFER / 2 + dim.height / 4);
	}

	private void drawInfoPill(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(getWidth() - getWidth() / 5 - 5, BingoCard.BUFFER / 2 - (BingoCard.BUFFER - 8) / 2,
				getWidth() / 5, BingoCard.BUFFER - 8, 10, 10);
		Dimension textDimension = FontUtil.getFontSize(getFontMetrics(getFont()), "WTG", 0, 0);
		g2.setColor(Color.white);
		g2.drawString("WTG", (getWidth() - 5) - ((getWidth() / 5) / 2) - (textDimension.width / 2),
				BingoCard.BUFFER / 2 + textDimension.height / 4);
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
		g2.setFont(gridFont);
		for (int i = 0; i < this.squares.length; i++)
		{
			BingoSquare square = this.squares[i];

			g2.setColor((square.isCalled() ? set.getColour("marked").toColour() : getColour(i)));
			g2.fill(square);

			Dimension dim = FontUtil.getFontSize(getFontMetrics(g2.getFont()), square.getNumber() + "", 0, 0);
			int xp = (int) ((int) square.getX() + square.getWidth() / 2 - (dim.width / 2));
			int yp = (int) ((int) square.getY() + square.getHeight() / 2 + (dim.height / 4));

			g2.setColor(Color.WHITE);
			if (!square.isEmpty())
				g2.drawString(square.getNumber() + "", xp, yp);

			int radius = (square.height - 5);

			g2.setColor(set.getColour("marker").applyTransparency(95));

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
		return (n % 2 == 0 ? set.getColour("primary").darken(0.15) : set.getColour("primary")).toColour();
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
		return this.cardInfo.isPurchased();
	}

	/**
	 * Returns if the Bingo Card is seleted.
	 * 
	 * @return {@link #selected}
	 */
	public boolean isSelected()
	{
		return this.cardInfo.isSelected();
	}

	/**
	 * Sets the Bingo Card as purchased
	 * 
	 * @param purchased
	 */
	public void setPurchased(boolean purchased)
	{
		this.cardInfo.setPurchased(purchased);
	}

	/**
	 * Sets the Bingo Card as selected.
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected)
	{
		this.cardInfo.setSelected(selected);
	}

	/**
	 * Sets the UUID of the Bingo Card.
	 * 
	 * @param uuid The UUID of the Card.
	 */
	public void setUUID(UUID uuid)
	{
		this.cardInfo.setUUID(uuid);
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
		return this.cardInfo.getUUID();
	}

	public UUID getDropletUUID()
	{
		return dropletUUID;
	}
}

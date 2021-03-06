package me.dylanmullen.bingo.game.chat;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class ChatMessagesComponent extends JComponent implements Scrollable, MouseMotionListener
{

	private static final long serialVersionUID = 6717389248920350598L;

	private JScrollPane scrollPanel;
	private List<ChatMessage> chatMessages;

	private boolean updateScrollbar;
	private UIColourSet set;

	private Font font = new Font("Calibri", Font.PLAIN, 15);
	private int maxUnitIncrement = 20;
	private int indentY;

	public ChatMessagesComponent(JScrollPane scroll, int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		this.scrollPanel = scroll;
		this.chatMessages = new ArrayList<ChatMessage>();
		this.updateScrollbar = false;
		this.set = BingoApp.getInstance().getColourManager().getSet("chat");
	}

	public void addMessage(String displayName, String userGroup, String mes)
	{
		UIColour colour = null;
		if (chatMessages.size() % 2 == 0)
			colour = set.getColour("secondary");
		else
			colour = set.getColour("primary");
		ChatMessage message = new ChatMessage(0, indentY,
				getWidth() - getScrollPanel().getVerticalScrollBar().getWidth(), colour);
		message.setMessage(mes);
		message.setUsername(displayName);
		message.setUserGroup(userGroup);
		message.setFont(font);
		message.setup();
		indentY += message.getHeight();
		chatMessages.add(message);
		add(message);
		if (indentY > getHeight())
		{
			resizeMessages();
			repaint();
		} else
			repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		drawMessages(g2);
		handleSizingChanges();
	}

	private void drawMessages(Graphics2D g2)
	{
		g2.setColor(BingoApp.getInstance().getColourManager().getSet("frame").getColour("side-primary").darken(0.25)
				.toColour());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

	private void handleSizingChanges()
	{
		int height = getItemHeights();

		if (getHeight() < height)
		{
			setBounds(getX(), getY(), getWidth(), height);
			this.updateScrollbar = true;
			return;
		}
		if (shouldUpdateScrollbar())
		{
			getScrollPanel().getVerticalScrollBar().setValue(height);
			this.updateScrollbar = false;
		}
	}

	private void resizeMessages()
	{
		chatMessages.parallelStream().forEach(e ->
		{
			if (e.getWidth() != getWidth() - 15)
				e.resizeMessage(getWidth() - 15);
		});
	}

	private int getItemHeights()
	{
		int height = 0;
		for (ChatMessage message : getChatMessages())
			height += message.getHeight();
		return height;
	}

	public List<ChatMessage> getChatMessages()
	{
		return this.chatMessages;
	}

	public JScrollPane getScrollPanel()
	{
		return this.scrollPanel;
	}

	private boolean shouldUpdateScrollbar()
	{
		return this.updateScrollbar;
	}

	// Scrollable
	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
		scrollRectToVisible(r);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(getWidth(), getHeight());
	}

	public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		int currentPosition = 0;
		if (orientation == SwingConstants.HORIZONTAL)
		{
			currentPosition = visibleRect.x;
		} else
		{
			currentPosition = visibleRect.y;
		}
		if (direction < 0)
		{
			int newPosition = currentPosition - (currentPosition / this.maxUnitIncrement) * this.maxUnitIncrement;
			return (newPosition == 0) ? this.maxUnitIncrement : newPosition;
		} else
		{
			return ((currentPosition / this.maxUnitIncrement) + 1) * this.maxUnitIncrement - currentPosition;
		}
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		if (orientation == SwingConstants.HORIZONTAL)
		{
			return visibleRect.width - this.maxUnitIncrement;
		} else
		{
			return visibleRect.height - this.maxUnitIncrement;
		}
	}

	public boolean getScrollableTracksViewportWidth()
	{
		return false;
	}

	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}

	public void setMaxUnitIncrement(int pixels)
	{
		this.maxUnitIncrement = pixels;
	}

}

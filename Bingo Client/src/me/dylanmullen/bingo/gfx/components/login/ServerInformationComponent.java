package me.dylanmullen.bingo.gfx.components.login;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.events.ServerStatusChangeEvent;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.net.ServerStatusManager;
import me.dylanmullen.bingo.net.ServerStatusManager.ServerStatus;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class ServerInformationComponent extends JComponent implements EventListener
{

	private static final long serialVersionUID = 1188076056138803597L;

	private String statusText;
	private UIColourSet set;

	public ServerInformationComponent(Vector2I pos, Vector2I dim)
	{
		setBounds(pos.getX(), pos.getY(), dim.getX(), dim.getY());
		this.set = BingoApp.getInstance().getColours().getSet("serverStatus");
		EventHandler.getHandler().registerListener(ServerStatusChangeEvent.class, this);
	}

	/**
	 * Creates new Server Information Component. <br>
	 * This class displays to the Player the current status of the Bingo Server. The
	 * text is updated every time the {@link ServerStatusChangeEvent} has been
	 * fired.<br>
	 * Default values are set to 0:
	 * <ul>
	 * <li>{@link #getX()}=0</li>
	 * <li>{@link #getY()}=0</li>
	 * <li>{@link #getWidth()}=0</li>
	 * <li>{@link #getHeight()}=0</li>
	 * </ul>
	 */
	public ServerInformationComponent()
	{
		setBounds(0, 0, 0, 0);
		EventHandler.getHandler().registerListener(ServerStatusChangeEvent.class, this);
	}

	@SuppressWarnings("deprecation")
	public void setup()
	{
		setOpaque(false);
		setBackground(set.getColour("undefined").getColour());

		this.statusText = "Ping";
		updateStatus(ServerStatusManager.getManager().getStatus());
		setFont(FontUtil.getFont(this, statusText, 0, 0));
		setForeground(set.getColour("undefined").getTextColour());
	}

	public void create()
	{
		setup();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());

		// Ensures that if the height is too small, the component just displays a circle
		// indicator.
		if (getHeight() > 50)
		{
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
			drawText(g2);
			return;
		} else
		{
			g2.fillOval(getWidth() / 4, 0, getHeight(), getHeight());
		}
	}

	private void drawText(Graphics2D g2)
	{
		g2.setColor(getForeground());
		g2.setFont(getFont());
		Dimension dim = FontUtil.getFontSize(getFontMetrics(getFont()), statusText, 0, 0);
		g2.drawString(statusText, getWidth() / 2 - (dim.width / 2), getHeight() / 2 - dim.height / 4);
	}

	/**
	 * Updates the text of the component based on the new status of the Bingo
	 * Server.<br>
	 * <h1>Deprecated</h1><br>
	 * This method contains an intensive search for a font size. Needs to be
	 * replaced.
	 * 
	 * @param status
	 */
	@SuppressWarnings("deprecation")
	public void updateStatus(ServerStatus status)
	{
		if (status == null)
		{
			statusText = "Please wait...";
			return;
		}

		statusText = status.getMesssage();
		setFont(FontUtil.getFont(this, statusText, 0, 0));

		switch (status)
		{
			case UNDEFINED:
				setBackground(set.getColour("undefined").getColour());
				setForeground(set.getColour("undefined").getTextColour());
				break;
			case CONNECTED:
				setBackground(set.getColour("connected").getColour());
				setForeground(set.getColour("connected").getTextColour());
				break;
			case DISCONNECTED:
				setBackground(set.getColour("disconnected").getColour());
				setForeground(set.getColour("disconnected").getTextColour());
				break;
		}
	}

	@Override
	public void receive(Event e)
	{
		if (e instanceof ServerStatusChangeEvent)
		{
			updateStatus(((ServerStatusChangeEvent) e).getStatus());
		}
	}
}

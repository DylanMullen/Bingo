package me.dylanmullen.bingo.window.login.comp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.events.ServerStatusChangeEvent;
import me.dylanmullen.bingo.net.ServerStatusManager.ServerStatus;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class ServerInformationComponent extends Panel implements EventListener
{

	private static final long serialVersionUID = 1188076056138803597L;

	private JLabel statusText;

	/**
	 * Creates new Server Information Component. <br>
	 * This class displays to the Player the current status of the Bingo Server. The
	 * text is updated every time the {@link ServerStatusChangeEvent} has been
	 * fired.
	 * 
	 * @param x      X-Position of the Component.
	 * @param y      Y-Position of the Component.
	 * @param width  The width of the Component.
	 * @param height The height of the Component.
	 */
	public ServerInformationComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
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
		super(0, 0, 0, 0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setup()
	{
		setLayout(null);
		setOpaque(false);
		EventHandler.registerListener(this);

		setBackground(UIColour.STATUS_UNDEFINED.toColor());

		this.statusText = new JLabel();
		getStatusText().setText("Please wait...");
		getStatusText().setBounds(5, 0, getWidth() - 10, getHeight());
		getStatusText().setFont(FontUtil.getFont(getStatusText(), getStatusText().getText(), 0, 0));
		getStatusText().setForeground(UIColour.STATUS_UNDEFINED.getTextColour());
		getStatusText().setHorizontalAlignment(SwingConstants.CENTER);
		getStatusText().setOpaque(false);
	}

	@Override
	public void create()
	{
		setup();
		add(getStatusText());
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
			super.paintComponent(g);
			return;
		} else
		{
			if (getStatusText().isVisible())
				getStatusText().setVisible(false);
			g2.fillOval(getWidth() / 4, 0, getHeight(), getHeight());
			/* TODO secondary inner colour */
//			g2.setColor(Color.BLACK);
//			g2.fillOval(width/4+5, 5, height-10, height-10);
		}
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
		getStatusText().setText(status.getMesssage());
		getStatusText().setFont(FontUtil.getFont(getStatusText(), getStatusText().getText(), 10, 0));

		switch (status)
		{
			case UNDEFINED:
				setBackground(UIColour.STATUS_UNDEFINED.toColor());
				getStatusText().setForeground(UIColour.STATUS_UNDEFINED.getTextColour());
				break;
			case CONNECTED:
				setBackground(UIColour.STATUS_CONNECTED.toColor());
				getStatusText().setForeground(UIColour.STATUS_CONNECTED.getTextColour());
				break;
			case DISCONNECTED:
				setBackground(UIColour.STATUS_DISCONNECTED.toColor());
				getStatusText().setForeground(UIColour.STATUS_DISCONNECTED.getTextColour());
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

	/**
	 * Returns the JLabel of the Status Text of the Component.
	 * 
	 * @return {@link #statusText}
	 */
	public JLabel getStatusText()
	{
		return this.statusText;
	}

}

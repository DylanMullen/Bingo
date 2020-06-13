package me.dylanmullen.bingo.window.login.comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.ServerStatusChangeEvent;
import me.dylanmullen.bingo.net.ServerStatusManager.ServerStatus;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ServerInfoComponent extends Panel implements EventListener
{

	private static final long serialVersionUID = 1188076056138803597L;

	private JLabel statusText;

	public ServerInfoComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	public ServerInfoComponent()
	{
		super(0, 0, 0, 0);
	}

	@Override
	public void setup()
	{
		setLayout(null);
		setOpaque(false);
		EventHandler.registerListener(this);

		setBackground(UIColour.STATUS_UNDEFINED.toColor());

		statusText = new JLabel();
		statusText.setText("Please wait...");
		statusText.setBounds(5, 0, width - 10, height);
		statusText.setFont(FontUtil.getFont(statusText, statusText.getText(), 0, 0));
		statusText.setForeground(UIColour.STATUS_UNDEFINED.getTextColour());
		statusText.setHorizontalAlignment(SwingConstants.CENTER);
		statusText.setOpaque(false);
	}

	@Override
	public void create()
	{
		setup();
		add(statusText);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());

		if(height > 50)
		{
			g2.fillRoundRect(0, 0, width, height, 15, 15);
			super.paintComponent(g);
			return;
		}
		else
		{
			if(statusText.isVisible())
				statusText.setVisible(false);
			g2.fillOval(width/4, 0, height, height);
			/* TODO secondary inner colour */
//			g2.setColor(Color.BLACK);
//			g2.fillOval(width/4+5, 5, height-10, height-10);
		}
	}

	public void setStatus(ServerStatus status)
	{
		statusText.setText(status.getMesssage());
		statusText.setFont(FontUtil.getFont(statusText, statusText.getText(), 10, 0));
		switch (status)
		{
			case UNDEFINED:
				setBackground(UIColour.STATUS_UNDEFINED.toColor());
				statusText.setForeground(UIColour.STATUS_UNDEFINED.getTextColour());
				break;
			case CONNECTED:
				setBackground(UIColour.STATUS_CONNECTED.toColor());
				statusText.setForeground(UIColour.STATUS_CONNECTED.getTextColour());
				break;
			case DISCONNECTED:
				setBackground(UIColour.STATUS_DISCONNECTED.toColor());
				statusText.setForeground(UIColour.STATUS_DISCONNECTED.getTextColour());
				break;
		}
	}

	@Override
	public void fire(Event e)
	{
		if (e instanceof ServerStatusChangeEvent)
		{
			setStatus(((ServerStatusChangeEvent) e).getStatus());
		}
	}

}

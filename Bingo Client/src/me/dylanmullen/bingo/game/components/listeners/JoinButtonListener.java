package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.dylanmullen.bingo.game.callbacks.JoinCallback;
import me.dylanmullen.bingo.net.PacketHandler;

public class JoinButtonListener implements MouseListener
{

	private boolean clicked;

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (clicked)
			return;
		PacketHandler.sendPacket(PacketHandler.createPacket(006, ""), new JoinCallback());
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{

	}

}

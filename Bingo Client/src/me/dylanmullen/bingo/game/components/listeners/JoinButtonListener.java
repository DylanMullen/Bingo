package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import me.dylanmullen.bingo.game.callbacks.JoinCallback;
import me.dylanmullen.bingo.game.home.GameSelector;
import me.dylanmullen.bingo.game.home.HomePanel;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.window.bingo.BingoWindow;

public class JoinButtonListener extends MouseAdapter
{

	private boolean clicked;

	private JComponent component;

	public JoinButtonListener(JComponent component)
	{
		this.component = component;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (clicked)
			return;

		if (component.getParent() instanceof GameSelector)
		{
			HomePanel.getInstance().getWindow().showBingoPanel();
			PacketHandler.sendPacket(PacketHandler.createPacket(006, ""), new JoinCallback());
		}
		else
			PacketHandler.sendPacket(PacketHandler.createPacket(006, ""), new JoinCallback());
	}

}

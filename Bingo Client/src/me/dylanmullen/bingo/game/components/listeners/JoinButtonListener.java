package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.callbacks.JoinCallback;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;

public class JoinButtonListener extends MouseAdapter
{

	private boolean clicked;
	private Packet packet;

	public JoinButtonListener(UUID bingoCloudUUID)
	{
		JSONObject message = new JSONObject();
		message.put("cloudUUID", bingoCloudUUID.toString());
		packet = PacketHandler.createPacket(6, message);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (clicked)
			return;

		PacketHandler.sendPacket(packet, new JoinCallback());
	}

}

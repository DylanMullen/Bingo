package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.window.bingo.BingoWindow;

public class JoinCloudListener extends MouseAdapter
{

	private boolean clicked;
	private Packet packet;
	private UUID bingoCloudUUID;

	public JoinCloudListener(UUID bingoCloudUUID)
	{
		this.bingoCloudUUID = bingoCloudUUID;
		JSONObject message = new JSONObject();
		message.put("cloudUUID", bingoCloudUUID.toString());
		packet = PacketHandler.createPacket(18, message);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (clicked)
			return;

		PacketHandler.sendPacket(packet, new PacketCallback()
		{
			@Override
			public boolean callback()
			{
				BingoWindow.getWindow().showBingoCloud(bingoCloudUUID, getMessage());
				return false;
			}
		});
	}

}

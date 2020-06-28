package me.dylanmullen.bingo.game.chat;

import java.util.UUID;

import javax.swing.JScrollPane;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;

public class ChatPanel extends UIPanel
{

	private static final long serialVersionUID = -6660881977261292475L;

	private UUID dropletUUID;

	public ChatPanel(UUID dropletUUID, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.dropletUUID = dropletUUID;
	}

	private ChatMessagesComponent chatMessagesComponent;
	private ChatInputComponent chatInputComponent;

	@Override
	public void setup()
	{
		setBackground(BingoApp.getInstance().getColours().getSet("frame").getColour("side-menu").toColour());

		JScrollPane scroll = new JScrollPane();
		this.chatMessagesComponent = new ChatMessagesComponent(scroll, 15, 15, getWidth() - 30, getHeight() - 45 - 50);
		scroll.setViewportView(getChatMessagesComponent());
		scroll.setBounds(15, 15, getWidth() - 30, getHeight() - 45 - 50);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(null);

		this.chatInputComponent = new ChatInputComponent(this, 15, getHeight() - 15 - 50, getWidth() - 30, 50);
		getChatInputComponent().create();
	}

	@Override
	public void create()
	{
		add(getChatMessagesComponent().getScrollPanel());
		add(getChatInputComponent());
	}

	public void recieveMessage(long timestamp, String username, String usergroup, String message)
	{
		getChatMessagesComponent().addMessage(username, usergroup, message);
	}

	public void sendMessage(String message)
	{
		if (message.length() > 128 || message.length() == 0)
			return;
		PacketHandler.sendPacket(constructPacket(message), null);
	}

	private Packet constructPacket(String mes)
	{
		JSONObject message = new JSONObject();
		message.put("dropletUUID", dropletUUID.toString());
		message.put("chatMessage", mes);
		return PacketHandler.createPacket(16, message);
	}

	public ChatMessagesComponent getChatMessagesComponent()
	{
		return this.chatMessagesComponent;
	}

	public ChatInputComponent getChatInputComponent()
	{
		return this.chatInputComponent;
	}

}

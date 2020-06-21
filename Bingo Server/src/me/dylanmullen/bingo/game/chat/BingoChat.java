package me.dylanmullen.bingo.game.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_Generic;

public class BingoChat
{

	private List<ChatMessage> chatMessages;

	public BingoChat()
	{
		this.chatMessages = new ArrayList<>();
	}

	public void sendMessage(Set<User> users, ChatMessage message)
	{
		for (User user : users)
		{
			Packet_Generic packet = new Packet_Generic(16, user.getClient(), message.toString());
			PacketHandler.sendPacket(packet, null);
		}
	}

	public ChatMessage submitMessage(User user, String rawMessage)
	{
		ChatMessage message = new ChatMessage(user, rawMessage);
		getChatMessages().add(message);
		return message;
	}

	public List<ChatMessage> getChatMessages()
	{
		return this.chatMessages;
	}
}

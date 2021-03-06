package me.dylanmullen.bingo.game.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class BingoChat
{

	private UUID dropletUUID;
	private List<ChatMessage> chatMessages;

	public BingoChat(UUID uuid)
	{
		this.dropletUUID = uuid;
		this.chatMessages = new ArrayList<>();
	}

	public void sendMessage(Set<User> users, ChatMessage message)
	{
		for (User user : users)
		{
			Packet packet = createChatPacket(message);
			packet.setClient(user.getClient());
			PacketHandler.sendPacket(packet);
		}
	}

	@SuppressWarnings("unchecked")
	private Packet createChatPacket(ChatMessage message)
	{
		Packet packet = PacketHandler.createPacket(null, 16, null);
		JSONObject messageData = new JSONObject();
		messageData.put("dropletUUID", dropletUUID.toString());
		messageData.put("displayName", message.getUser().getUserInformation().getDisplayName());
		messageData.put("userGroup", "User");
		messageData.put("message", message.getMessage());
		messageData.put("timestamp", message.getTimeProduced());
		packet.setMessageSection(messageData);

		return packet;
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

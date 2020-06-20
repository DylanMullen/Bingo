package me.dylanmullen.bingo.game.chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class ChatComponent extends JTextArea
{

	private static final long serialVersionUID = 2277345570147389600L;

	private List<String> chatMessages;

	public ChatComponent(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		this.chatMessages = new ArrayList<String>();
		setup();
	}

	private void setup()
	{
		setEditable(false);
	}

	public void addMessage(String message)
	{
		if (getChatMessages().size() > 500)
			getChatMessages().remove(0);

		getChatMessages().add(message);
		updateTextArea(message);
	}

	private void updateTextArea(String data)
	{
		append(data + "\n");
	}

	public List<String> getChatMessages()
	{
		return this.chatMessages;
	}

}

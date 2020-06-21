package me.dylanmullen.bingo.game.chat;

import javax.swing.JScrollPane;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ChatPanel extends Panel
{

	private static final long serialVersionUID = -6660881977261292475L;

	public ChatPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	private ChatMessagesComponent chatMessagesComponent;
	private ChatInputComponent chatInputComponent;

	@Override
	public void setup()
	{
		setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());

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

	public void addMessage()
	{

	}

	public void sendMessage(String message)
	{
		if (message.length() > 128)
			return;
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

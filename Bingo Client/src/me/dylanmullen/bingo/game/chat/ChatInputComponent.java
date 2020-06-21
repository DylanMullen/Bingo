package me.dylanmullen.bingo.game.chat;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.window.ui.Button;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ChatInputComponent extends Panel
{

	private static final long serialVersionUID = -1228467449669717505L;

	private ChatPanel chatPanel;
	private ChatInputField textArea;
	private Button submitButton;

	public ChatInputComponent(ChatPanel panel, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.chatPanel = panel;
	}

	@Override
	public void setup()
	{
		this.textArea = new ChatInputField(0, 0, (int) (getWidth() / 4 * 3), getHeight());

		this.submitButton = new Button("Send!", new Font("Calibri", Font.PLAIN, 25), getTextArea().getWidth(), 0,
				getWidth() - getTextArea().getWidth(), getHeight(), UIColour.BINGO_BALL_0);
		getSubmitButton().create();
		getSubmitButton().addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
//				getChatPanel().getChatMessagesComponent().addMessage("rpfoviblhpuxdmsavuiwbjzkpzjyklheyqhbvyjnfqzafnkvsijlizxrvnlfbxbhwgifspzmkzkyymhrjkrstzorviwlmojokaznrfzpnykqvxsjyurqijkkofmsxjyo");
				getChatPanel().sendMessage(getTextArea().getText());
			}
		});
	}

	@Override
	public void create()
	{
		setup();
		add(getTextArea());
		add(getSubmitButton());
	}

	public ChatInputField getTextArea()
	{
		return textArea;
	}

	public Button getSubmitButton()
	{
		return submitButton;
	}

	public ChatPanel getChatPanel()
	{
		return chatPanel;
	}
}

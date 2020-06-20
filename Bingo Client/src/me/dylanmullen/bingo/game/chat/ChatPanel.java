package me.dylanmullen.bingo.game.chat;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ChatPanel extends Panel
{

	private static final long serialVersionUID = -6660881977261292475L;

	public ChatPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		ChatComponent chat = new ChatComponent(15, 15, getWidth() - 30, getHeight() - 30);
		chat.setWrapStyleWord(true);
		add(chat);

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				String message = "(" + System.currentTimeMillis() + ") Test > sdfssdfsdfsfsfsfdfsfsdfsfdfsfsffdfsfdfsdffs";
				chat.addMessage(message);
			}
		});
	}

	@Override
	public void create()
	{

	}

}

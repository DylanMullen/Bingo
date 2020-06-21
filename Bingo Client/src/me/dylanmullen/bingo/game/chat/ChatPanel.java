package me.dylanmullen.bingo.game.chat;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import me.dylanmullen.bingo.window.ui.Button;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ChatPanel extends Panel
{

	private static final long serialVersionUID = -6660881977261292475L;

	public ChatPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	JTextArea input = new JTextArea();

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());

		JScrollPane scroll = new JScrollPane();
		ChatComponent chat = new ChatComponent(scroll,15, 15, getWidth() - 30, getHeight() - 45 - 50);
		scroll.setViewportView(chat);
		
		scroll.setBounds(15, 15, getWidth() - 30, getHeight() - 45 - 50);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(null);
		add(scroll);

		Button button = new Button("Send", null, getWidth() - 15 - 100, getHeight() - 15 - 50, 100, 50,
				UIColour.BTN_BINGO_ACTIVE);
		button.create();

		add(button);
		input.setBounds(15, getHeight() - 15 - 50, getWidth() - 30 - 100, 50);
		input.setWrapStyleWord(false);
		input.setLineWrap(true);

		add(input);

		button.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				String message = "(" + System.currentTimeMillis()
						+ ") Test > sdfssdfsdfsfsfsfdfsfsdfsfdfsfsffdfsfdfsdffs";
				chat.addMessage(scroll, message);
			}
		});
	}

	private void update()
	{

	}

	@Override
	public void create()
	{

	}

}

package me.dylanmullen.bingo.game.chat;

import java.awt.Polygon;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.buttons.Button;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.util.Vector2I;

public class ChatInputComponent extends UIPanel
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
		this.submitButton = new Button("Send", new ButtonInformation(new Vector2I(getTextArea().getWidth() - 5, 0),
				new Vector2I(getWidth() - getTextArea().getWidth()+5, getHeight()), () ->
				{
					getChatPanel().sendMessage(getTextArea().getText());
				}));
		submitButton.setCustomShape(getShape());
		this.submitButton.updateColours(BingoApp.getInstance().getColourManager().getSet("buttons").getColour("send"),
				BingoApp.getInstance().getColourManager().getSet("buttons").getColour("send").darken(0.05));
	}

	private Polygon getShape()
	{
		Polygon p = new Polygon();
		p.addPoint(4, 0);
		p.addPoint(submitButton.getWidth(), 0);
		p.addPoint(submitButton.getWidth(), submitButton.getHeight());
		p.addPoint(0, submitButton.getHeight());

		return p;
	}

	@Override
	public void create()
	{
		setup();
		add(getSubmitButton());
		add(getTextArea());
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

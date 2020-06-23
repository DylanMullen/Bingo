package me.dylanmullen.bingo.game.components.overlays;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.window.ui.TransparentLabel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class WinnerOverlay extends Overlay
{

	private static final long serialVersionUID = 5162085351393194993L;
	private TransparentLabel header;
	private JLabel winners;

	public WinnerOverlay(int x, int y, int width, int height)
	{
		super(UIColour.FRAME_BINGO_BG_TOP.toColor(), 200, x, y, width, height);
		setup();
	}

	public void setup()
	{
		Font font = new Font("Calibri", Font.PLAIN, 30);

		this.header = new TransparentLabel(240);
		header.setText("WE HAVE A WINNER");
		header.setBounds(0, 0, getWidth(), getHeight() / 5);
		header.setFont(font);
		header.setForeground(Color.WHITE);
		header.setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		header.setHorizontalAlignment(SwingConstants.CENTER);

		font = new Font("Calibri", Font.PLAIN, 25);
		this.winners = new JLabel();
		winners.setBounds(0, header.getHeight() + 12, getWidth(), getHeight() - (header.getHeight() + 24));
		winners.setHorizontalAlignment(SwingConstants.CENTER);
		winners.setVerticalAlignment(SwingConstants.TOP);
		winners.setFont(font);
		winners.setForeground(Color.white);

		add(header);
		add(winners);
	}

	public void setWinners(List<String> winners)
	{
		if (winners == null)
		{
			setText("");
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < winners.size(); i++)
		{
			sb.append(winners.get(i) + (winners.size() - 1 == i ? "" : "\n"));
		}
		setText(sb.toString());
		setVisible(true);
		repaint();

	}

	public void setText(String text)
	{
		winners.setText(text);
	}
}

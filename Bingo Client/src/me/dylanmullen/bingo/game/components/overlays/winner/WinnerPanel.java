package me.dylanmullen.bingo.game.components.overlays.winner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.TransparentLabel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class WinnerPanel extends Panel
{
	private static final long serialVersionUID = -5054359118554243790L;

	private TransparentLabel header;
	private JLabel winners;

	public WinnerPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBackground(UIColour.FRAME_BINGO_BG_TOP.toColor());
		setOpaque(false);

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
	}

	@Override
	public void create()
	{
		setup();

		add(header);
		add(winners);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		Color col = UIColour.FRAME_BINGO_BG_TOP.toColor();
		g2.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 200));
		g2.fillRect(0, 0, width, height);
	}

	public void setText(String text)
	{
		winners.setText(text);
	}

}

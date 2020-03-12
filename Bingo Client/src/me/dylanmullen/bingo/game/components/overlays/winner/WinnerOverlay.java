package me.dylanmullen.bingo.game.components.overlays.winner;

import javax.swing.JComponent;

public class WinnerOverlay extends JComponent
{

	private static final long serialVersionUID = 5162085351393194993L;

	private WinnerPanel panel;

	public WinnerOverlay(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		setup();
	}

	private void setup()
	{
		panel = new WinnerPanel(20, 20, getWidth() - 40, getHeight() - 40);
		panel.create();
		add(panel);
	}

	public void setWinners(String[] winners)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < winners.length; i++)
			sb.append(winners[i] + (winners.length - 1 == i ? "" : "\n"));
		panel.setText(sb.toString());
		panel.repaint();
	}

}

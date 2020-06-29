package me.dylanmullen.bingo.game.components;

import javax.swing.JLabel;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class NumbersCalledComponent extends UIPanel
{

	private static final long serialVersionUID = 4164051545883107133L;

	private boolean displaying = false;

	public NumbersCalledComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	private NumberGrid[] numbers;
	private JLabel label;

	private int gridSize;
	private int calls;

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("content").toColour());
		this.numbers = new NumberGrid[5];
		this.gridSize = (getHeight() - 24);
	}

	@Override
	public void create()
	{
		int indentY = 12;
		int indentX = (getWidth() - ((gridSize + 12) * numbers.length)) / 2;
		for (int i = 0; i < numbers.length; i++)
		{
			numbers[i] = new NumberGrid(indentX, indentY, gridSize, gridSize);
			numbers[i].setup();
			indentX += gridSize + 12;
			add(numbers[i]);
		}
		numbers[0].setFirst(true);

		label = new JLabel();
		label.setBounds(getWidth() - (getWidth() / 4), getHeight() - (getHeight() / 5), getWidth() / 4,
				getHeight() / 5);
		label.setOpaque(true);
		add(label);
	}

	public void update(int num)
	{
		int temp = numbers[0].getCurrentNumber();
		numbers[0].updateCurrentNumber(num);
		numbers[0].repaint();
		if (temp != -1)
		{
			for (int i = 1; i < numbers.length; i++)
			{
				if (temp == -1)
					break;
				temp = numbers[i].updateCurrentNumber(temp);
				numbers[i].repaint();
			}
		}
		updateCalls();
		repaint();
	}

	public void restart()
	{
		calls = 0;
		label.setText("");
		for (NumberGrid grid : numbers)
		{
			grid.updateCurrentNumber(-1);
			grid.repaint();
		}

	}

	private void updateCalls()
	{
		calls++;
		label.setText("Call: " + calls);
		label.repaint();
	}

	public void setDisplaying(boolean displaying)
	{
		this.displaying = displaying;
	}

	public boolean isDisplaying()
	{
		return displaying;
	}

}

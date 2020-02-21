package me.dylanmullen.bingo.window.bingo.ui.buttons;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.dylanmullen.bingo.gfx.ImageAtlas;
import me.dylanmullen.bingo.window.ui.ImageComponent;
import me.dylanmullen.bingo.window.ui.UIButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class CloseButton extends UIButton
{

	private static final long serialVersionUID = 4134576125389759700L;

	private final ImageAtlas ATLAS = new ImageAtlas("uiAtlas.png", 42);

	private ImageComponent icon;

	public CloseButton(String text, int x, int y, int w, int h)
	{
		super(text, x, y, w, h);
	}

	@Override
	protected void setup()
	{
		init();
		setLayout(null);
		setBackground(UIColour.BTN_FAILURE.toColor());
		System.out.println("ests");
		icon = new ImageComponent((getWidth() / 2) - 21, (getHeight() / 2) - 21, 42, 42);
		icon.setImage(ATLAS.getImage(3, 1, UIColour.BTN_BINGO_ACTIVE.toColor()));
		add(icon);

		addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				System.exit(0);
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public UIButton create()
	{
		setup();
		return this;
	}

}

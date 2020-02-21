package me.dylanmullen.bingo.window.bingo.ui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.dylanmullen.bingo.window.bingo.ui.buttons.SidePanelButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class SP_BTN_Listener implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent e)
	{
		SidePanelButton spb = (SidePanelButton) e.getComponent();
		spb.setActive();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		SidePanelButton spb = (SidePanelButton) e.getComponent();
		if(spb.isActive())
			return;
		spb.setBackground(UIColour.BTN_BINGO_SIDE_HOVER.toColor());
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		SidePanelButton spb = (SidePanelButton) e.getComponent();
		if(spb.isActive())
			return;
		
		spb.setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

}

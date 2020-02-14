package me.dylanmullen.bingo.window.login.comps;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LoginTopMenu extends JPanel
{

	/**
	 * Create the panel.
	 */
	public LoginTopMenu()
	{
		super();
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Close");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(595, 0, 155, 50);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0x3c6382));
		lblNewLabel.addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent e)
					{
						System.exit(0);
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
					public void mousePressed(MouseEvent e)
					{
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e)
					{
						// TODO Auto-generated method stub
						
					}
		
				});
		add(lblNewLabel);
	}
	
	
}

package me.dylanmullen.bingo.window.bingo;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import me.dylanmullen.bingo.window.ui.Panel;

public class Container extends Panel
{

	private static final long serialVersionUID = -2373878660466121716L;

	private JScrollPane scrollHomePanel;
	private JComponent current;

	public Container(int x, int y, int width, int height)
	{
		super(x, y, width, height);

		this.scrollHomePanel = new JScrollPane();
		getScrollHomePanel().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getScrollHomePanel().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getScrollHomePanel().setPreferredSize(new Dimension(getWidth(), getHeight()));
		getScrollHomePanel().setBounds(0, 0, getWidth(), getHeight());
		getScrollHomePanel().setBorder(null);
	}

	public void removeCurrent()
	{
		remove(current);
	}

	public void setCurrentPanel(JPanel component)
	{
		if (current != null)
			removeCurrent();
		add(component);
		this.current = component;
		repaint();
	}

	public void setScrollCurrentPanel(JPanel panel)
	{
		if (current != null)
			removeCurrent();
		this.scrollHomePanel.setViewportView(panel);
		add(getScrollHomePanel());
		this.current = getScrollHomePanel();
		repaint();
	}

	@Override
	public void setup()
	{
	}

	@Override
	public void create()
	{

	}

	public JScrollPane getScrollHomePanel()
	{
		return scrollHomePanel;
	}

}

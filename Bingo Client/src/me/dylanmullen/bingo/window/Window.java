package me.dylanmullen.bingo.window;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public abstract class Window extends JFrame
{

	private static final long serialVersionUID = 1L;

	private int width, height;
	private boolean setup;

	private JPanel contentPanel;

	/**
	 * Creates a new JFrame window with a title and dimensions.
	 * 
	 * @param title  The title of the Window.
	 * @param width  The width of the Window.
	 * @param height The height of the Window.
	 */
	public Window(String title, int width, int height)
	{
		super(title);
		this.width = width;
		this.height = height;
		setup();
	}

	/**
	 * Creates a new JFrame window with the title and the default width and height
	 * of 1280x720.
	 * 
	 * @param title The title of the Window.
	 */
	public Window(String title)
	{
		this(title, 1280, 720);
	}

	/**
	 * This method setups the Window with default values.
	 */
	public void setup()
	{
		if (this.setup)
			return;

		setSize(this.width, this.height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		this.contentPanel = new JPanel();
		getContentPanel().setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPanel().setLayout(null);
		setContentPane(getContentPanel());

		this.setup = true;
	}

	/**
	 * @return Returns the middle point of the JFrame.
	 */
	protected Dimension getMidPoint()
	{
		return new Dimension(getX() / 2, getY() / 2);
	}

	/**
	 * @return Returns the Content Panel
	 */
	public JPanel getContentPanel()
	{
		return this.contentPanel;
	}
}

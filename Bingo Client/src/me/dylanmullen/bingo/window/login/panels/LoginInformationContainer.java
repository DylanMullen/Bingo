package me.dylanmullen.bingo.window.login.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.LoginHandler;
import me.dylanmullen.bingo.gfx.ui.buttons.Button;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.RoundedButton;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.input.InputGroup;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;
import me.dylanmullen.bingo.window.login.LoginWindow;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class LoginInformationContainer extends UIPanel
{

	private static final long serialVersionUID = -6138313483836658937L;

	private LoginContainer panel;

	private Grid grid;
	private InputGroup username, password;
	private List<RoundedButton> buttons;
	private UIColourSet buttonSet;
	private UIColourSet frameSet;

	private List<String> lines;

	/**
	 * Creates the Login Information panel used for logging in users to the Bingo
	 * Server. <br>
	 * This class is commonly used in the {@link LoginWindow} of the Application.
	 * 
	 * @param loginPanel The login panel which houses this Component.
	 * @param x          X-Position of the Component.
	 * @param y          Y-Position of the Component.
	 * @param width      The width of the Component.
	 * @param height     The height of the Component.
	 */
	public LoginInformationContainer(LoginContainer loginPanel, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.panel = loginPanel;

	}

	@Override
	public void setup()
	{
		this.lines = new ArrayList<>();
		this.buttons = new ArrayList<RoundedButton>();
		this.buttonSet = BingoApp.getInstance().getColourManager().getSet("buttons");
		this.frameSet = BingoApp.getInstance().getColourManager().getSet("frame");
		setBackground(frameSet.getColour("side-primary").toColour());
		setForeground(frameSet.getColour("side-primary").lighten(0.25).toColour());
		showInput();
		constructMessage("Login");
		setFont(new Font("Calibri", Font.PLAIN, 24));
	}

	@Override
	public void create()
	{
		setup();
	}

	public void showInput()
	{
		grid = new Grid(new GridSettings(getWidth() - 20, getHeight() / 3, 2, 1, 5), 10,
				(int) (getHeight() / 5 * 2) + 35);
		grid.getGridSettings().setFixedRowHeight(48);
		username = createInput("Username", false);
		password = createInput("Password", true);
		grid.addGridItem(new GridItem(username, 1, 1), 0);
		grid.addGridItem(new GridItem(password, 1, 1), 1);
		grid.getItems().stream().forEach(e ->
		{
			InputGroup group = (InputGroup) e.getComponent();
			group.setupShapes();
			add(group);
		});
		createButtons();
	}

	private void createButtons()
	{
		int width = getWidth() / 2;
		int yPos = grid.getY() + grid.getTotalHeight();

		Button login = new Button("Login",
				new ButtonInformation(new Vector2I(10, yPos), new Vector2I(width - 10, getHeight() / 10), () ->
				{
					LoginHandler.getHandlerInstance().handleLoginRequest(this);
				}));
		Button register = new Button("Register",
				new ButtonInformation(new Vector2I(width - 10, yPos), new Vector2I(width, getHeight() / 10), () ->
				{
					LoginHandler.getHandlerInstance().handleRegisterRequest(this);
				}));
		login.updateColours(buttonSet.getColour("login-bg"), buttonSet.getColour("login-bg").darken(0.15));
		register.updateColours(buttonSet.getColour("register-bg"), buttonSet.getColour("register-bg").darken(0.15));

		setShapes(login, register);
		add(login);
		add(register);
	}

	private void setShapes(Button login, Button register)
	{
		Polygon loginShape = new Polygon();
		loginShape.addPoint(0, 0);
		loginShape.addPoint(login.getWidth(), 0);
		loginShape.addPoint(login.getWidth() - 10, login.getHeight());
		loginShape.addPoint(0, login.getHeight());

		login.setCustomShape(loginShape);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getForeground());
		g2.setFont(getFont());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillRoundRect(15, 15, getWidth() - 30, (int) (getHeight() / 5 * 2), 15, 15);
		drawMessage(g2);
	}

	private void drawMessage(Graphics2D g2)
	{
		g2.setColor(frameSet.getColour("side-primary").getTextColour());
		Dimension prev = null;
		int indentY = 15 + (int) (getHeight() / 5 * 2) / 2;
		for (String string : lines)
		{
			prev = FontUtil.getFontSize(getFontMetrics(getFont()), string, 0, 0);

			indentY += prev.height / 4;
			g2.drawString(string, getWidth() / 2 - (prev.width / 2), indentY);
			indentY += 5;
		}
	}

	private InputGroup createInput(String span, boolean hidden)
	{
		return new InputGroup(span, null, null,
				BingoApp.getInstance().getColourManager().getSet("loginInput").getColour("primary"), hidden);
	}

	public void constructMessage(String message)
	{
		lines.clear();
		FontMetrics metrics = getFontMetrics(getFont());
		StringBuilder lineBuilder = new StringBuilder();
		int currentWidth = 0;

		for (String line : message.split("\n"))
		{
			currentWidth = FontUtil.getFontSize(metrics, line, 0, 0).width;
			if (currentWidth > getWidth() - 30)
			{
				currentWidth = 0;
				for (int i = 0; i < line.split(" ").length; i++)
				{
					String word = line.split(" ")[i];
					currentWidth += FontUtil.getFontSize(metrics, word + " ", 0, 0).width;
					if (currentWidth >= getWidth() - 30)
					{
						lines.add(lineBuilder.toString());
						currentWidth = 0;
						lineBuilder = new StringBuilder();
						lineBuilder.append(word + " ");
					} else
						lineBuilder.append(word + " ");

				}
			} else
			{
				lineBuilder.append(line);
				lines.add(lineBuilder.toString());
				lineBuilder = new StringBuilder();
			}
		}
		lines.add(lineBuilder.toString());
		repaint();
	}

	public InputGroup getPassword()
	{
		return password;
	}

	public InputGroup getUsername()
	{
		return username;
	}

	/**
	 * Returns the Login Panel that this Component belongs to.
	 * 
	 * @return {@link #panel}
	 */
	public LoginContainer getLoginPanel()
	{
		return panel;
	}

	/**
	 * Returns the buttons being used in this Component.
	 * 
	 * @return {@link #buttons}
	 */
	public List<RoundedButton> getButtons()
	{
		return buttons;
	}
}

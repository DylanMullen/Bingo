package me.dylanmullen.bingo.window.login.comp;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.RoundedPanel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class WarningInfoComponent extends RoundedPanel
{

	private static final long serialVersionUID = 5423352332179650770L;

	private JLabel information;

	public WarningInfoComponent()
	{
		super(0, 0, 0, 0, 15);
	}

	@Override
	public void setup()
	{
		updateBackground(UIColour.STATUS_UNDEFINED);

		information = new JLabel();
		information.setBounds(0, 0, getWidth(), getHeight());
		System.out.println(getWidth());
		information.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		updateText("Please Login/Register");
		information.setHorizontalAlignment(SwingConstants.CENTER);
		information.setVerticalTextPosition(SwingConstants.CENTER);
		information.setForeground(getForeground());
	}

	@Override
	public void create()
	{
		setup();
		add(information);
	}

	public void updateText(String text)
	{
		if(text.contains("\n"))
		{
			StringBuilder sb = new StringBuilder();
			String[] split = text.split("\n");
			sb.append("<html>");
			for(int i =0; i < split.length;i++)
				sb.append(split[i]+ (split.length-1==i?"":"<br>"));
			sb.append("</html>");
			text = sb.toString();
		}
		
		information.setText(text);
		information.setFont(FontUtil.getFont(information, text, 30, 30));
	}

	public void updateBackground(UIColour colour)
	{
		setBackground(colour.toColor());
		setForeground(colour.getTextColour());
	}
}

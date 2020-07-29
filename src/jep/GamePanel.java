package jep;

import javax.swing.*;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel{

	/**
     * 2 default value
     */
    public static int numDailyDoubles = 2;
    /**
     * 6 default value
     */
    public static int numTeams =  6;
	private static final long serialVersionUID = 983369563163169425L;
	protected Color moneyColor, categoryColor, questionColor, buttonColor, categoryBackColor, buttonBackColor;
	protected static File file;
	protected Map<TextAttribute, Integer> fontAttributes; 
    protected Font boldUnderline;
    public static String defaultPath = ""; 
	public GamePanel()
	{
		super();
		setLayout(new GridBagLayout());
		fontAttributes = new HashMap<TextAttribute, Integer>();
	    fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	    boldUnderline = new Font("New Times Roman",Font.BOLD, 28).deriveFont(fontAttributes);
		moneyColor = new Color(244, 197, 79);
	       	//new Color(246, 204, 117));
		categoryColor = Color.RED;
				//Color.WHITE; 
				//new Color(210, 45, 45);
		questionColor = Color.WHITE;
		buttonColor = new Color(21, 43, 141);
		buttonBackColor = new Color(71, 119, 190);
		categoryBackColor = new Color(252, 255, 175);
				//new Color(21, 43, 141);
				//new Color(47, 66, 147);
	    setOpaque(false);
	}
	protected void removeBackground(JButton button)
	{
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
	}
	protected String getValue(String str)
	{
		switch(str)
		{
		case "num_daily_doubles":
			return(Integer.toString(numDailyDoubles));
		case "default_path":
			return(defaultPath);
		case "num_teams":
			return(Integer.toString(numTeams));
		}
		return "FAILED TO FIND VALUE!";
	}
	protected void setValue(String str, String value) throws Exception
	{
		switch(str)
		{
		case "num_daily_doubles":
			int doubles = Integer.parseInt(value);
			if(doubles>25||doubles<0)
			{
				throw new IllegalArgumentException("Value entered is not within the range 0-25");
			}
			else
			{
				numDailyDoubles = doubles;
			}
			break;
		case "default_path":
			defaultPath=value;
			break;
		case "num_teams":
			int teams = Integer.parseInt(value);
			if(teams>10||teams<0)
			{
				throw new IllegalArgumentException("Value entered is not within the range 0-10");
			}
			else
			{
				numTeams = teams;
			}
			break;
		}	
	}
	protected void adjustMenuButton(JButton button)
	{
        button.setPreferredSize(new Dimension(200,100));
        button.setFont(new Font("Times New Roman", Font.BOLD, 25));
        button.setForeground(buttonColor);
        button.setBackground(buttonBackColor);
        button.setMargin(new Insets(0, 10, 0, 10));
        
	}
	protected void adjustMenuLabel(JLabel label, int size)
	{
		label.setFont(new Font("Times New Roman", Font.BOLD, size));
        label.setForeground(moneyColor);
	}
	protected JButton createMenuBackButton(String panelName)
	{
		JButton backButton = new JButton("Back");
        adjustMenuButton(backButton);
        backButton.addActionListener(new Listener(panelName));
        return backButton;
	}
    protected void setUpCategory(JComponent component)
    {
        component.setForeground(Color.RED);
        component.setBackground(categoryBackColor);
        component.setFont(new Font("New Times Roman", Font.BOLD, 18));
        component.setOpaque(true);
    }
    protected class Listener implements ActionListener
    {
    	String panel;
    	public Listener(String panelName)
    	{
    		panel=panelName;
    	}
    	public void actionPerformed(ActionEvent e)
    	{
    		DefaultPanel.getManager().toPanel(panel);
    	}
    }
    
}

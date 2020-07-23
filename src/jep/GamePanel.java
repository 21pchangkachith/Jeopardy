package jep;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GamePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 983369563163169425L;
	protected Color moneyColor, categoryColor, questionColor;
	protected File file;
	public GamePanel()
	{
		super();
		moneyColor = new Color(244, 197, 79);
		categoryColor = new Color(210, 45, 45);
		questionColor = new Color(21, 43, 141);
				//new Color(47, 66, 147);
	    file = null;
	}
	protected void handleException(Exception ex, String message)
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
        JOptionPane.showMessageDialog(new JFrame(), message + sw.toString());
	}
	protected void handleException(Exception ex)
	{
		handleException(ex, "Whoops, something went wrong. Record this log below, and someone will know what to do with it. \n\n");
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
    		Driver.switchPanels(panel);
    	}
    }
}

package jep;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.*;

import jep.utilities.ExceptionHandler;
import jep.utilities.UIChooser;

public class SettingsPanel extends AuxiliaryPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3971966470559219178L;
	private JButton[] settingsButton;
	private final int numSettings = 3;
	private JSONObject settings;
	private InputStream stream;
	public SettingsPanel() {
		super();
		try {

			stream = new FileInputStream(new File("./settings.json"));
			JSONTokener tokener = new JSONTokener(stream);
			settings = (JSONObject) tokener.nextValue();
			numDailyDoubles = ((JSONObject)settings.get("settings")).getInt("num_daily_doubles");
			numTeams = ((JSONObject)settings.get("settings")).getInt("num_teams");
			defaultPath = (String)((JSONObject)settings.get("settings")).get("default_path");
			
		}
		catch(FileNotFoundException e)
		{
			try
			{
				stream = getClass().getResourceAsStream("/jep/GameFiles/resources/Settings.json");
				JSONTokener tokener = new JSONTokener(stream);
				settings = (JSONObject) tokener.nextValue();
				numDailyDoubles = ((JSONObject)settings.get("settings")).getInt("num_daily_doubles");
				numTeams = ((JSONObject)settings.get("settings")).getInt("num_teams");
				defaultPath = (String)((JSONObject)settings.get("settings")).get("default_path");
			}
			catch(Exception ex)
			{
				ExceptionHandler.getHandler().handleException(ex);
			}
		}
		catch(Exception e)
		{
			ExceptionHandler.getHandler().handleException(e);
		}
        settingsButton = new JButton[numSettings];
		fillButtons();
        populatePanel(contentPanel);
        
        c.gridx++;
        c.gridy++;
        c.weightx=1.0;
        c.weighty=1.0;
        add(contentPanel, c);
	}
	private void populatePanel(JPanel contentPanel) {
		for(int i = 0; i<settingsButton.length; i++)
        {
            formatButton(i);
            contentPanel.add(settingsButton[i]);
        }
	}
	private void formatButton(int i) {
		adjustMenuButton(settingsButton[i]);
		settingsButton[i].setPreferredSize(new Dimension(400, 100));
		settingsButton[i].setHorizontalAlignment(JButton.CENTER);
	}
	private void fillButtons() {
		settingsButton[0] = new JButton("Change # Daily Doubles");
		settingsButton[0].addActionListener(new SettingsListener("num_daily_doubles"));
        settingsButton[1] = new JButton("Change default path");
        settingsButton[1].addActionListener(new SettingsListener("default_path"));
        settingsButton[2] = new JButton("Change # Teams");
        settingsButton[2].addActionListener(new SettingsListener("num_teams"));
	}
	private class SettingsListener implements ActionListener
	{
		String settingName;
		public SettingsListener(String setting)
		{
			super();
			settingName = setting;
		}
		public void actionPerformed(ActionEvent e)
		{
			String userInput = null;
			if(settingName.equals("default_path"))
			{
				userInput = UIChooser.getUIChooser().getDirectoryViaUI();
			}
			else
			{
				userInput = JOptionPane.showInputDialog(new JFrame(), "Current " + settingName.replaceAll("_", " ") + ": "+ getValue(settingName) + "\nEnter new value:");
			}
			
			try
			{
				if(userInput==null)
				{
					return;
				}
				for(String setting: settings.keySet())
				{
					JSONObject settingType = (JSONObject)settings.get(setting);
					if(settingType.keySet().contains(settingName))
					{
						settingType.putOpt(settingName, userInput);
					}
				}
			    OutputStream out = new FileOutputStream(new File("./settings.json"));
				stream.transferTo(out);
				OutputStreamWriter writer = new OutputStreamWriter(out);
				settings.write(writer);
				writer.close();
				out.close();
				setValue(settingName, userInput);
				JOptionPane.showMessageDialog(new JFrame(), "Changes saved. Restart to see effects");
			}
			catch(IllegalArgumentException ex)
			{
				JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
			}
			catch(FileNotFoundException ex)
			{
				try
				{
					String path = getClass().getResource("/jep/GameFiles/resources/Settings.json").toURI().getPath();
					File file =  new File(path);
					OutputStream out = new FileOutputStream(file);
					OutputStreamWriter writer = new OutputStreamWriter(out);
					settings.write(writer);
					writer.close();
					out.close();
					setValue(settingName, userInput);
					JOptionPane.showMessageDialog(new JFrame(), "Changes saved. Restart to see effects");
				}
				catch(Exception exc)
				{
					ExceptionHandler.getHandler().handleException(ex);
					ex.printStackTrace();
				}
			}
			catch(Exception ex)
			{
				if(userInput.isBlank())
				{
					JOptionPane.showMessageDialog(new JFrame(), "Please do not attempt to save blank fields.");
				}
				else
				{
					ExceptionHandler.getHandler().handleException(ex);
					ex.printStackTrace();
				}
			}
		}
	}

}

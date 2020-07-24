package jep;


import java.awt.Dimension;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.*;

public class SettingsPanel extends AuxiliaryPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3971966470559219178L;
	private JButton[] settingsButton;
	private JSONObject settings;
	private InputStream stream;
	public SettingsPanel() {
		super();
		try {

			stream = getClass().getResourceAsStream("/jep/GameFiles/resources/Settings.json");
			JSONTokener tokener = new JSONTokener(stream);
			settings = (JSONObject) tokener.nextValue();
			numDailyDoubles = ((JSONObject)settings.get("settings")).getInt("num_daily_doubles");
			defaultPath = (String)((JSONObject)settings.get("settings")).get("default_path");
			
		}
		catch(Exception e)
		{
			handleException(e, "Background missing, please replace /GameFiles/resources/images/BackButtonPicture.PNG");
		}
        settingsButton = new JButton[5];
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
        settingsButton[2] = new JButton("Change # Daily Doubles");
        settingsButton[3] = new JButton("Change # Daily Doubles");
        settingsButton[4] = new JButton("Change # Daily Doubles");
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
			if(settingName=="default_path")
			{
				userInput = setDirectoryViaUI();
			}
			else
			{
				userInput = JOptionPane.showInputDialog(new JFrame(), "Current value:" + getValue(settingName) + "\nEnter new value:");
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
			catch(Exception ex)
			{
				if(userInput.isBlank())
				{
					JOptionPane.showMessageDialog(new JFrame(), "Please do not attempt to save blank fields.");
				}
				handleException(ex);
				ex.printStackTrace();
			}
		}
		private String setDirectoryViaUI() {
			String path = null;
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			try
			{
			    fc.setCurrentDirectory(new File(defaultPath));
			}
			catch(Exception ex)
			{
				handleException(ex);
			}
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
			    File directory = fc.getSelectedFile();
			    path = directory.getPath();
			}
			return path;
		}
	}

}

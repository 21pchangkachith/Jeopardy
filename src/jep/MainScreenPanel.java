package jep;
import javax.swing.*;
import java.awt.*;
/**
 * Class MainScreenPanel displays 4 buttons and the title of the app at the top
 * 
 * @author Phoenix Changkachith
 * @version 2/10/2020
 * @period 2
 * @teacher Coglianese
 */
public class MainScreenPanel extends GamePanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2984058000721393156L;


    public MainScreenPanel()
    /**
     * @author Phoenix Changkachith
     * @version 2/10/2020
     * @period 2
     * @teacher Coglianese
     * constructor for the class. 
     */
    {
    	super();
        GridBagConstraints c = new GridBagConstraints();

        JButton playButton = new JButton("Play");
        JButton creditsButton = new JButton("Credits");
        JButton settingsButton = new JButton("Settings");
        
        playButton.addActionListener(new Listener("LoadPanel"));     
        creditsButton.addActionListener(new Listener("CreditsPanel"));
        settingsButton.addActionListener(new Listener("SettingsPanel"));
        adjustMenuButton(playButton);
        adjustMenuButton(creditsButton);
        adjustMenuButton(settingsButton);

        JLabel title = new JLabel("Jeopardy");
        adjustMenuLabel(title, 70);
        
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx=0;
        c.gridy=0;
        c.weightx=1.0;
        c.weighty=1.0;
        add(title, c);
        
        c.gridy++;
        add(playButton, c);
        c.gridy++;
        add(creditsButton, c);
        c.gridy++;
        add(settingsButton, c);

        

    }
}
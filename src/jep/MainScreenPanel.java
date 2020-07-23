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

    /**
     * an array of JButtons
     */
    private JButton buttons[];

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
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton playButton = new JButton("Play");
        JButton creditsButton = new JButton("Credits");
        buttons = new JButton[]{playButton, creditsButton};
        
        playButton.addActionListener(new Listener("LoadPanel"));     
        creditsButton.addActionListener(new Listener("CreditsPanel"));
        
        
        for(int i=0; i<buttons.length; i++)
        {
            adjustMenuButton(buttons[i]);
        }

        JLabel title = new JLabel("Jeopardy");
        title.setFont(new Font("Times New Roman", Font.BOLD, 70));
        title.setForeground(moneyColor);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        c.gridx=0;
        c.gridy=0;
        c.weightx=1.0;
        c.weighty=1.0;
        add(title, c);
        
        c.gridy++;
        add(playButton, c);
        c.gridy++;
        add(creditsButton, c);
        

        

    }
}
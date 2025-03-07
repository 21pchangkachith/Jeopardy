package jep;
import javax.swing.*;
/**
 * Class CreditsPanel displays the credits
 * 
 * @author Phoenix Changkachith
 * @author Justin Son
 * @version 2/10/2020
 * @period 2
 * @teacher Coglianese
 */
public class CreditsPanel extends AuxiliaryPanel
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5723321311075463726L;
	JLabel[] creatorLabels;
    /**
     * Constructor for objects of class CreditsPanel
     * 
     * @author Phoenix Changkachith
     * @author Justin Son
     * @version 2/10/2020
     * @period 2
     * @teacher Coglianese
     */
    public CreditsPanel()
    {
    	super();
    	creatorLabels = new JLabel[5];
        fillLabels();
        populatePanel(contentPanel);
        
        c.gridx++;
        c.gridy++;
        c.weightx=1.0;
        c.weighty=1.0;
        add(contentPanel, c);
        
    }
	private void populatePanel(JPanel contentPanel) {
		for(int i = 0; i<creatorLabels.length; i++)
        {
            formatLabel(i);
            contentPanel.add(creatorLabels[i]);
        }
	}
	private void formatLabel(int i) {
		adjustMenuLabel(creatorLabels[i], 25);
		creatorLabels[i].setHorizontalAlignment(JLabel.CENTER);
	}
	
	//If you are a dev working from this project, update my tag to second version.
	private void fillLabels() {
		creatorLabels[0] = new JLabel("Latest Version: Phoenix Changkachith");
        creatorLabels[1] = new JLabel("First Version: Siddhant Gupta");
        creatorLabels[2] = new JLabel("First Version: Justin Son");
        creatorLabels[3] = new JLabel("First Version: Ethan Shan");
        creatorLabels[4] = new JLabel("First Version: Bao Nguyen");
	}
}

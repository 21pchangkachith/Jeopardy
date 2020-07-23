package jep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Class CreditsPanel displays the credits
 * 
 * @author Phoenix Changkachith
 * @author Justin Son
 * @version 2/10/2020
 * @period 2
 * @teacher Coglianese
 */
public class CreditsPanel extends JPanel
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5723321311075463726L;
	JLabel[] array = new JLabel[5];
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

        
        setLayout(new BorderLayout());
        
        //add labels here 
        JPanel labelPanel = new JPanel();
        labelPanel.setOpaque(false);
        labelPanel.setLayout(new GridLayout(5,1));
        
        
        //help reformat button when added to GridLayout
        JPanel buttonList  = new JPanel();
        buttonList.setLayout(new GridLayout(1,5));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        backButton.addActionListener(new Listener());
        //reformats the backButton so it would be smaller and to the left. 
        buttonList.add(backButton);
        for(int i = 0; i<4; i++){
            JButton button = new JButton("");
            buttonList. add(button);
            button.setVisible(false);
        }
        buttonList.setOpaque(false);
        add(buttonList, BorderLayout.NORTH);
        //initializes array of JLabel
        array[0] = new JLabel("Phoenix Changkachith");
        array[1] = new JLabel("Siddhant Gupta");
        array[2] = new JLabel("Justin Son");
        array[3] = new JLabel("Ethan Shan");
        array[4] = new JLabel("Bao Nguyen");

        for(int i = 0; i<array.length; i++)
        {
            array[i].setFont(new Font("Times New Roman", Font.BOLD, 25));
            array[i].setForeground(Color.WHITE);
            array[i].setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add(array[i]);
        }
        add(labelPanel, BorderLayout.CENTER);
        setOpaque(false);
        
    }
    private class Listener implements ActionListener
    /**
     * @author Justin Son
     * @period 2
     * @teacher Coglianese
     * @version 3-2-20
     * 
     * main point of this class is to establish behavior to the backButton
     */
    {
        public void actionPerformed(ActionEvent e)
        {
            Driver.switchPanels("MainScreenPanel");
        }
    }

}

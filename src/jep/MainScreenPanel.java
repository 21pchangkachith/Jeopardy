package jep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Class MainScreenPanel displays 4 buttons and the title of the app at the top
 * 
 * @author Phoenix Changkachith
 * @version 2/10/2020
 * @period 2
 * @teacher Coglianese
 */
public class MainScreenPanel extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2984058000721393156L;

	/**
     * JPanel buttonHolder is a JPanel and this is for adding buttons and reformatting them to size. 
     */
    private JPanel buttonHolder;

    /**
     * JPanel sizeFixer is a JPanel and this is for adding buttons and reformatting them to size. 
     */
    private JPanel sizeFixer;

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
        //sets initial variables
        setLayout(new BorderLayout());
        buttonHolder = new JPanel();
        buttonHolder.setLayout(new GridLayout(5,1));
        buttonHolder.setOpaque(false);
        ((GridLayout)buttonHolder.getLayout()).setVgap(20);

        //initializes buttons and arraylist
         JButton playButton = new JButton("Play");
        JButton creditsButton = new JButton("Credits");
        JButton helpButton = new JButton("Removed");
        buttons = new JButton[]{playButton, creditsButton, helpButton};
        
        playButton.addActionListener(new LoadListener());     
        
        
        
        
        creditsButton.addActionListener(new Listener());
        
        
        helpButton.setVisible(false);
        helpButton.setEnabled(false);

        //for loop iterating through array. It makes sure that the buttons have the right size and they are added correctly. 
        for(int i=0; i<buttons.length; i++)
        {
            JButton currentButton = buttons[i];
            buttonHolder.add(currentButton);
            currentButton.setPreferredSize(new Dimension(200,100));
            currentButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        }

        //title for the play screen. It displays "Jeopardy"
        JLabel title = new JLabel("Jeopardy");
        title.setFont(new Font("Times New Roman", Font.BOLD, 70));
        title.setForeground(Color.YELLOW);
        title.setHorizontalAlignment(SwingConstants.CENTER);
       // title.setBackground(Color.BLUE.darker());
        add(title, BorderLayout.NORTH);
        
        //Size Fixer fixes the size of the buttons in Buttonholder. 

        sizeFixer = new JPanel();
        sizeFixer.setLayout(new FlowLayout());
        sizeFixer.add(buttonHolder);
        sizeFixer.setOpaque(false);
        add(sizeFixer, BorderLayout.SOUTH);
        
        //even more steps to initialize

        setOpaque(false);
        //buttonHolder.setBackground(Color.BLUE.darker());
        //sizeFixer.setBackground(Color.BLUE.darker());
    }
    private class Listener implements ActionListener
    /**
     * @author Phoenix Changkachith
     * @period 2
     * @version 2/10/2020
     * @teacher Coglianese
     * 
     * main purpose of this class is to establish behavior for buttons. 
     */
    {
        public void actionPerformed(ActionEvent e)
        {
            Driver.switchPanels(( (JButton) e.getSource()).getText().replaceAll(" ","")+"Panel");
        }
    }
    private class LoadListener implements ActionListener
    /**
     * @author Phoenix Changkachith
     * @period 2
     * @version 2/10/2020
     * @teacher Coglianese
     * 
     * main purpose of this class is to establish behavior for buttons. 
     */
    {
        public void actionPerformed(ActionEvent e)
        {
            Driver.switchPanels("LoadPanel");
        }
    }
}
package jep;
import javax.swing.JFrame;
import java.io.*;
/**
 * Class Driver is the driver
 * 
 * @author Phoenix Changkachith
 * @version 3/2/2020
 * @period 2
 * @teacher Coglianese
 */
public class Driver
{
    /**
     * JFrame frame is the frame
     */
    public static JFrame frame;
    /**
     * Method main starts the program
     *
     * @param args unused arguments
     */
    public static void main(String[] args)
    {
        DefaultPanel cards = new DefaultPanel();
        frame = new JFrame("Jeopardy");
        frame.setSize(800, 800);
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(cards);
        frame.setVisible(true);
        
    	
    }
    

    /**
     * Method switchPanels switches the panels plainly
     *
     * @param panel the panel
     */
    public static void switchPanels(String panel)
    {
        ( (DefaultPanel) frame.getContentPane() ).toPanel(panel);
    }

    /**
     * Method play upon call, locks the game in the provided edit mode 
     *
     * @param panel the panel to be switched to
     * @param file the game set
     */
    public static void play(String panel, File file, boolean edit)
    {
        ( (DefaultPanel) frame.getContentPane() ).toPanel(panel, file, edit);
    }


    /**
     * Method switchQuestions switches the questions and gives a question to do so (usually switching between questionpanel and answerpanel)
     *
     * @param panel the panel to be switched to
     * @param q the current question object
     */
    public static void switchQuestions(String panel, Question q)
    {
        ( (DefaultPanel) frame.getContentPane() ).toPanel(panel, q);
    }
}
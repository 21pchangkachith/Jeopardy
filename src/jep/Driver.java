package jep;
import javax.swing.JFrame;
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
     * Method main starts the program
     *
     * @param args unused arguments
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Jeopardy");
        frame.setSize(800, 800);
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(DefaultPanel.getManager());
        frame.setVisible(true);
        
    	
    }
}
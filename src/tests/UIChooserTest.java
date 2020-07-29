package tests;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jep.utilities.UIChooser;

public class UIChooserTest {

	public UIChooserTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		UIChooser chooser = UIChooser.getUIChooser();
		JFrame frame = new JFrame("Jeopardy");
        frame.setSize(800, 800);
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new JPanel());
        frame.setVisible(true);
		String thing = chooser.getDirectoryViaUI();
		System.out.println(thing);
		
	}
}

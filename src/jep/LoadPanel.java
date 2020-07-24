package jep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
/**
 * Class LoadPanel the panel used to play/edit from files
 *
 * @author Phoenix Changkachith
 * @version 3/2/2020
 */
public class LoadPanel extends GamePanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7502054283100731489L;
    /**
     * JFileChooser fc the filechooser that allows the user to open a GUI and pick which file they want to load
     */
    private JFileChooser fc;
    /**
     * JButton playButton the button that the user clicks to play the game
     */
    private JButton playButton;
    /**
     * JButton editButton the button that the user clicks to edit the game
     */
    private JButton editButton;
    /**
     * JButton[] buttons the array of all buttons used in formatting the panel
     */
    private JButton[] buttons;
    /**
     * Constructor for objects of class LoadPanel
     */
    public LoadPanel()
    {
    	super();
        GridBagConstraints c = new GridBagConstraints();
        
        fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        fc.setFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        try
        {
            fc.setCurrentDirectory(new File(Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "jep" + System.getProperty("file.separator")+ "GameFiles" + System.getProperty("file.separator")+ "GameSets" + System.getProperty("file.separator")));
        }
        catch(Exception ex)
        {
            handleException(ex);
        }
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(new SelectListener());
        
        JButton parseButton = new JButton("Create new set");
        parseButton.addActionListener(new Listener("ParsePanel"));
        
        playButton = makeLoadButton("Play", false);
        editButton = makeLoadButton("Edit", true);
        
        buttons = new JButton[]{selectButton, parseButton, playButton, editButton};
        JPanel buttonHolder = new JPanel();
        buttonHolder.setOpaque(false);
        buttonHolder.setLayout(new GridLayout(5,1));
        ((GridLayout)buttonHolder.getLayout()).setVgap(20);
        for(int i=0; i<buttons.length; i++)
        {
            JButton currentButton = buttons[i];
            adjustMenuButton(currentButton);	
            buttonHolder.add(currentButton);
        }
        
        c.gridx=0;
        c.gridy=1;
        c.weightx=1.0;
        c.weighty=1.0;
        //c.fill= GridBagConstraints.HORIZONTAL;
        add(buttonHolder, c);
        
        JButton backButton = new JButton("Back");
        adjustMenuButton(backButton);	
        backButton.addActionListener(new Listener("MainScreenPanel"));
        c.gridy--;
        c.anchor= GridBagConstraints.FIRST_LINE_START;
        add(backButton, c);

        
    }
	private JButton makeLoadButton(String text, boolean edit) {
		JButton button = new JButton(text);
        button.addActionListener(new LoadListener(edit));
        button.setEnabled(false);
        
        return button;
	}


    /**
     * Class SelectListener the listener that is used to select the file to be loaded
     */
    private class SelectListener implements ActionListener
    {
        /**
         * Method actionPerformed allows the user to choose the file
         *
         * @param e unused actionevent
         */
        public void actionPerformed(ActionEvent e)
        {
            
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                file = fc.getSelectedFile();
                playButton.setEnabled(true);
                editButton.setEnabled(true);
            }
            else
            {
            	playButton.setEnabled(false);
                editButton.setEnabled(false);
            }
            
        }
    }
    /**
     * Class PlayListener the listener that takes the user to play the game
     */
    private class LoadListener implements ActionListener
    {
    	boolean edit;
        /**
         * Method actionPerformed takes the user to the panel
         *
         * @param e unused action event
         */
    	public LoadListener(boolean e) {
    		edit=e;
    	}
        public void actionPerformed(ActionEvent e)
        {
            Driver.play("QuestionListPanel", file, edit);
        }
    }
}

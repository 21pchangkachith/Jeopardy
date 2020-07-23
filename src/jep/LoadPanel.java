package jep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;
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
        setLayout(new BorderLayout());
        
        fc = new JFileChooser();
        javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
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
        
        JButton parseButton = new JButton("Create new game set");
        parseButton.addActionListener(new Listener("ParsePanel"));
        
        playButton = new JButton("Play");
        playButton.addActionListener(new LoadListener(false));
        playButton.setEnabled(false);
        
        editButton = new JButton("Edit");
        editButton.addActionListener(new LoadListener(true));
        editButton.setEnabled(false);
        
        buttons = new JButton[]{selectButton, parseButton, playButton, editButton};
        JPanel buttonHolder = new JPanel();
        buttonHolder.setOpaque(false);
        buttonHolder.setLayout(new GridLayout(5,1));
        ((GridLayout)buttonHolder.getLayout()).setVgap(20);
        for(int i=0; i<buttons.length; i++)
        {
            JButton currentButton = buttons[i];
            buttonHolder.add(currentButton);
            currentButton.setPreferredSize(new Dimension(200,100));
            currentButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        }
        JButton placeHolder = new JButton();
        placeHolder.setVisible(false);
        placeHolder.setPreferredSize(new Dimension(200,100));
        placeHolder.setOpaque(false);
        buttonHolder.add(placeHolder);
        
        JPanel sizeFixer = new JPanel();
        sizeFixer.setOpaque(false);
        sizeFixer.setLayout(new FlowLayout());
        sizeFixer.add(buttonHolder);
        add(sizeFixer, BorderLayout.SOUTH);
        
         JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new GridLayout(1,5));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200,100));
        backButton.addActionListener(new Listener("MainScreenPanel"));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

        header.add(backButton);
        for(int i=0; i<4; i++)
        {
            JButton filler= new JButton();
            header.add(filler);
            filler.setVisible(false);
        }
        add(header, BorderLayout.NORTH);
        
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

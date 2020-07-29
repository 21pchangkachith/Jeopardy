package jep;
import javax.imageio.ImageIO;
import javax.swing.*;

import jep.utilities.ExceptionHandler;

import java.awt.*;
import java.io.*;
/**
 * Class DefaultPanel manages which panel is shown at which time and manages shared variables
 * 
 * @author Phoenix Changkachith
 * @author Siddhant Gupta
 * @version 3/2/2020
 * @period 2
 * @teacher Coglianese
 */
public class DefaultPanel extends GamePanel
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5693779735124158262L;
	static ImageIcon backButtonIcon;

    /**
     * QuestionListPanel qListP the QuestionListPanel, the reference is used as a shortcut because time is wasted looking through the array and casting otherwise
     */
    private static QuestionListPanel qListP;
    /**
     * QuestionPanel qP the QuestionPanel, the reference is used as a shortcut because time is wasted looking through the array and casting otherwise
     */
    private QuestionPanel qP;
    /**
     * QuestionListPanel qListP the QuestionListPanel, the reference is used as a shortcut because time is wasted looking through the array and casting otherwise
     */
    private AnswerPanel aP;
    /**
     * CardLayout cards the CardLayout which is the layout used to manage which panel is displayede
     */
    private CardLayout cards;
     /**
     * 'PANELS' is of JPanel type, and it holds all the panels for the main screen layout of the app, essentially all Panels are static and could therefore be serialized if we wanted.
     */
    private boolean editing;
    
    private String previousPanel;
    public static final String categorySeparator = "#";
    private static JPanel[] PANELS;
    private static final DefaultPanel defaultPanel = new DefaultPanel();
    /**
     * 'DefaultPanel' class constructor, instantiates CardLayout object 'cards' and sets up the panels
     * 
     */
    private DefaultPanel()
    {
    	ImageIcon icon = null;
    	try {
		    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		 } catch (Exception e) {
		            e.printStackTrace();
		 }
		try {

			InputStream stream = getClass().getResourceAsStream("/jep/GameFiles/resources/images/BackButtonPicture.PNG");
			icon= new ImageIcon(ImageIO.read(stream));
			
		}
		catch(Exception e)
		{
			ExceptionHandler.getHandler().handleException(e, "Background missing, please replace /GameFiles/resources/images/BackButtonPicture.PNG");
		}
		backButtonIcon = icon;

		SettingsPanel settings = new SettingsPanel();
        previousPanel = null;
        editing = false;
        PANELS = new JPanel[]{new MainScreenPanel(), new CreditsPanel(), new LoadPanel(), new ParsePanel(), null, null, null, null, settings};
        cards = new CardLayout();
        setLayout(cards);
        setUpMenuPanels();
        cards.show(this, PANELS[0].getClass().getSimpleName());
        
    }
    public static DefaultPanel getManager()
    {
    	return defaultPanel;
    }
    @Override
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	ImageIcon icon = null;
    	
		try {

			InputStream stream = getClass().getResourceAsStream("/jep/GameFiles/resources/images/background.png");
			icon= new ImageIcon(ImageIO.read(stream));
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame(), "Background missing, please replace /GameFiles/resources/images/background.png");
		}
    	g.drawImage(icon.getImage(), 0, 0, null);
    }

    /**
     *  Method setUpPanels adds all Panels to the cards layout 
     * 
     */
    public void setUpMenuPanels()
    {

        for(int i=0; i<PANELS.length; i++)
        {
        	if(i<4||i>7)
        	{
        		add(PANELS[i]);
        		cards.addLayoutComponent(PANELS[i], PANELS[i].getClass().getSimpleName());
        	}
        }
    }
    public void setUpGamePanels()
    {

        for(int i=0; i<PANELS.length; i++)
        {
        	if(i>=4&&i<=7)
        	{
        		add(PANELS[i]);
        		cards.addLayoutComponent(PANELS[i], PANELS[i].getClass().getSimpleName());
        	}
        }
    }
    public int[] getScores()
    {
    	return qListP.getScores();
    }
    public int getScore(int index)
    {
    	return qListP.getScore(index);
    }

	
	/**
	 * Method setScore changes a team's score and updates the display
	 *
	 * @param newValue the new value of the team's score
	 * @param teamNum the number the team belongs to
	 */
	public static void setScore(int newValue, int teamNum)
	{
	    qListP.setScore(newValue, teamNum);
	}
    public static void loadSavedGame(File f)
    {
    	try
    	{
    		
    		FileInputStream fis = new FileInputStream(f);
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		qListP = (QuestionListPanel) ois.readObject();
    		PANELS[4]= qListP;
    		numDailyDoubles = ois.readInt();
    		numTeams = ois.readInt();
    		
    		ois.close();
    		fis.close();
    		file =f;
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }

    /**
     * Method toPanel switches the panels, and does a messy but necessary update to scores and remaining points values 
     * 
     * @param panel the panel to be switched to
     */
    public void toPanel(String panel)
    {
    	boolean success = true;
        switch(panel)
        {
        	case "AnswerPanel":
        		aP.updateScores();
        		if(editing)
        		{
        			success = qP.doEdit();
        		}
        		break;
        	case "QuestionPanel":
        		qP.updateScores();
        	    qP.resetTeamButtons();
        		if(editing && previousPanel.equals("AnswerPanel"))
        		{
        			success = aP.doEdit();
        		}
        		break;
        	case "QuestionListPanel":
        		qListP.refreshButtons();
        		if(previousPanel.equals("AnswerPanel")&&!editing)
        		{
        			try
        			{
        				File temp = new File(file.getPath()+".sav");
        				FileOutputStream fos = new FileOutputStream(temp);
        				ObjectOutputStream oos = new ObjectOutputStream(fos);
        				oos.reset();
        				oos.writeObject(qListP);
        				oos.writeInt(numDailyDoubles);
        				oos.writeInt(numTeams);
        				oos.close();
        				fos.close();
        			}
        			catch(Exception ex)
        			{
        				ExceptionHandler.getHandler().handleException(ex);
        			}
        		}
        		break;
        }
        if(success)
        {
        	cards.show(this, panel);
        	previousPanel = panel;
        }
    }

    /**
     * Method toPanel switches the panels and provides a file
     *
     * @param panel the panel to be switched to
     * @param file the file to be provided
     */
    public void toPanel(String panel, File f, boolean edit)
    {
    	
    	qP = new QuestionPanel();
    	PANELS[5] = qP;
    	aP = new AnswerPanel();
    	PANELS[6] = aP;
    	PANELS[7] = new SpecialPanel();
    	if(f.getPath().substring(f.getPath().lastIndexOf(".")).equals(".txt"))
    	{
    		qListP = new QuestionListPanel();
    		PANELS[4]= qListP;
    		declareFile(f);
    		declareEdit(edit);
    	}
    	setUpGamePanels();
        
    	
    	cards.show(this, panel);
    }
    private void declareFile(File f)
    {

    		qListP.setFile(f);
    		aP.setFile(f);
    		qP.setFile(f);
    	
    }
    private void declareEdit(boolean edit)
    {
        qListP.setEdit(edit);
        qP.setEdit(edit);
        aP.setEdit(edit);
        editing=edit;
    }

    /**
     * Method toPanel switches the panels to play the game
     *
     * @param panel the panel to be switched to
     * @param q the question object being reviewed
     */
    public void toPanel(String panel, Question q)
    {
        qP.setQuestion(q);
        aP.setQuestion(q);
        if(panel.equals("SpecialPanel"))
        {
        	((SpecialPanel) PANELS[7]).setQuestion(q);
        }
        toPanel(panel);
    }

}

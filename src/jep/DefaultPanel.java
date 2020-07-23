package jep;
import javax.swing.*;
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
public class DefaultPanel extends JPanel
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5693779735124158262L;
	static final String backButtonPath;
	static {
		String temp = null;
		try {
			temp = Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "jep" + System.getProperty("file.separator") + "GameFiles" + System.getProperty("file.separator")+ "images" + System.getProperty("file.separator") + "BackButtonPicture.PNG";
		}
		catch(Exception e)
		{
			System.out.println("Back Button missing, please replace /GameFiles/images/BackButtonPicture.PNG");
		}
		backButtonPath=temp;
	}
	/**
     * int numDailyDoubles the number of daily doubles, easily accessible in case changes are required during maintenance
     */
    public static int numDailyDoubles=2;
    /**
     * QuestionListPanel qListP the QuestionListPanel, the reference is used as a shortcut because time is wasted looking through the array and casting otherwise
     */
    private QuestionListPanel qListP;
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
    public final JPanel[] PANELS;
    /**
     * 'DefaultPanel' class constructor, instantiates CardLayout object 'cards' and sets up the panels
     * 
     */
    public DefaultPanel()
    {
    	qListP= new QuestionListPanel();
        qP= new QuestionPanel();
        aP= new AnswerPanel();
        previousPanel = null;
        editing = false;
        PANELS = new JPanel[]{new MainScreenPanel(), new CreditsPanel(), new LoadPanel(), new ParsePanel(), qListP, qP, aP, new SpecialPanel()};
        cards = new CardLayout();
        setLayout(cards);
        setUpPanels();
        cards.show(this, PANELS[0].getClass().getSimpleName());
        
    }
    @Override
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	String temp = null;
		try {
			temp = Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "jep" + System.getProperty("file.separator") + "GameFiles" + System.getProperty("file.separator")+ "images" + System.getProperty("file.separator") + "background.png";
		}
		catch(Exception e)
		{
			System.out.println("Back Button missing, please replace /GameFiles/images/BackButtonPicture.PNG");
		}
    	g.drawImage(new ImageIcon(temp).getImage(), 0, 0, null);
    }

    /**
     *  Method setUpPanels adds all Panels to the cards layout 
     * 
     */
    public void setUpPanels()
    {

        for(int i=0; i<PANELS.length; i++)
        {
            add(PANELS[i]);
            cards.addLayoutComponent(PANELS[i], PANELS[i].getClass().getSimpleName());
            PANELS[i].setOpaque(false);
        }
    }

    /**
     * Method toPanel switches the panels, and does a messy but necessary update to scores and remaining points values 
     * 
     * @param panel the panel to be switched to
     */
    public void toPanel(String panel)
    {
        
        switch(panel)
        {
        	case "AnswerPanel":
        		aP.updateScores();
        		if(editing)
        		{
        			qP.editQuestion();
        		}
        		break;
        	case "QuestionPanel":
        		qP.updateScores();
        	    qP.resetTeamButtons();
        		if(editing && previousPanel.equals("AnswerPanel"))
        		{
        			aP.changeAnswer();
        		}
        		break;
        	case "QuestionListPanel":
        		qListP.refreshButtons();
        		break;
        }
        cards.show(this, panel);
        previousPanel = panel;
    }

    /**
     * Method toPanel switches the panels and provides a file
     *
     * @param panel the panel to be switched to
     * @param file the file to be provided
     */
    public void toPanel(String panel, File file, boolean edit)
    {
        declareFile(file);
        declareEdit(edit);
        cards.show(this, panel);
    }
    private void declareFile(File file)
    {
    	qListP.setFile(file);
        aP.setFile(file);
        qP.setFile(file);
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

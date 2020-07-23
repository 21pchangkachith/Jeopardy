package jep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.*;
/**
 * Sets up questions based on the Jeopardy set
 *
 * @author Siddhant Gupta
 * @author Phoenix Changkachith
 * @version 2/23/2020
 * @teacher Mr. Coglianese
 * @period 2
 */
public class QuestionListPanel extends GamePanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7858456204393013516L;
	/**
     * JPanel questionHolder the JPanel that holds all the question buttons
     */
    JPanel questionHolder;
    /**
     * Category[] categoryList the array of 5 categories in the game
     */
    Category[] categoryList; 
    /**
     * Question finalJeopardy the final Jeopardy question
     */
    Question finalJeopardy;
    /**
     * int[] scores the array that stores the points of each team
     */
    private static int[] scores = new int[6];
    /**
     * JLabel[] scoreLabels the array that stores the labels which display the point values of each team
     */
    private static JLabel[] scoreLabels;
    /**
     * JLabel[] categoryLabels the array of JLabels that stores the category names
     */
    JLabel[] categoryLabels;
    /**
     * JTextField[] categoryEdits the array of JTextFields that are used when edit mode is enabled
     */
    JTextField[] categoryEdits;
    /**
     * JButton[] buttonArrangementChanger the array of buttons on the question list screen
     */
    JButton[] buttonArrangementChanger;
     /**
     * File file the txt file containing an acceptable format that can be read into Question objects and Category names
     */
    File file;
    /**
     * boolean edit the boolean that decides whether or not the file is in edit mode
     */
    boolean edit;
    int[] indexOfDailyDoubles;
    /**
     * Constructor for objects of class QuestionListPanel initializes all values, but is never fully set up until a valid file is selected in parsepanel.
     */
    
    public QuestionListPanel()
    {
    	super();
    	 Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
         fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
         Font boldUnderline = new Font("New Times Roman",Font.BOLD, 28).deriveFont(fontAttributes);
         
    	setOpaque(false);
        edit=false;
        // initializes int 'Question' array
        setLayout(new BorderLayout());
        categoryList = new Category[5];
        categoryLabels = new JLabel[5];
        categoryEdits = new JTextField[5];
        for(int i=0; i<categoryLabels.length; i++)
        {
            categoryLabels[i]= new JLabel("");
            categoryLabels[i].setBackground(new Color(252, 255, 175));
            categoryLabels[i].setFont(new Font("New Times Roman", Font.BOLD, 18));
            categoryLabels[i].setForeground(Color.RED);
            categoryLabels[i].setOpaque(true);
            categoryEdits[i] = new JTextField("Category");
            categoryEdits[i].setVisible(false);
            categoryEdits[i].setEnabled(false);
            categoryEdits[i].setForeground(Color.RED);
            categoryEdits[i].setBackground(new Color(252, 255, 175));
            categoryEdits[i].addActionListener(new EditListener(i));
            categoryEdits[i].setFont(new Font("New Times Roman", Font.BOLD, 18));
        }
        file = null;
        finalJeopardy = new Question();
        //adding point displays
        scoreLabels = new JLabel[6];
        JPanel scoreHolder = new JPanel();
        scoreHolder.setLayout(new GridLayout(1,5));
        for(int i=0; i<scoreLabels.length; i++)
        {
            scores[i]=-1;
            scores[i]++;
            scoreLabels[i] = new JLabel("Team "+(i+1)+": "+scores[i]);
            scoreLabels[i].setFont(new Font("New Times Roman", Font.BOLD, 24));
            scoreLabels[i].setForeground(new Color(246, 204, 117));
            scoreHolder.add(scoreLabels[i]);
        }
        scoreHolder.setOpaque(false);
        add(scoreHolder, BorderLayout.SOUTH);
        
        //yes i could use a 2d array, but i think a traditional array works nicer even if its less readable, and its easier
        buttonArrangementChanger = new JButton[25];
        indexOfDailyDoubles= new int[DefaultPanel.numDailyDoubles];
	        for(int i=0; i<indexOfDailyDoubles.length; i++)
	        {
	            int index= (int)(25*Math.random());
	            indexOfDailyDoubles[i]=index;
	            
	            
	            for(int repI=i-1; repI>=0; repI--)
	            {
	                while(indexOfDailyDoubles[i]==indexOfDailyDoubles[repI])
	                {
	                    index= (int)(25*Math.random());
	                    indexOfDailyDoubles[i]=index;
	                }
	            }
	            System.out.println("INDEX" + index);
	        }

        for(int i=0; i<25; i++)
        {
            JButton button = new JButton( "$"+ (((i%5)+1)*100) );
            button.setForeground(new Color(71, 119, 190));
            button.setFont(new Font("New Times Roman", Font.BOLD, 24).deriveFont(fontAttributes));
            button.addActionListener(new Listener(i));
            button.setContentAreaFilled(false);
            button.setOpaque(false);
            button.setBorderPainted(false);
            buttonArrangementChanger[i] = button;

        }
        
        questionHolder = new JPanel();
        questionHolder.setLayout(new GridLayout(7,5));

        for(int i=0; i<categoryList.length; i++)
        {
            questionHolder.add(categoryLabels[i]);
        }
        for(int i=0; i<categoryList.length; i++)
        {
            questionHolder.add(categoryEdits[i]);
        }
        for(int outer=0; outer<5; outer++)
        {
            for(int inner=0; inner<5; inner++)
            {
                questionHolder.add(buttonArrangementChanger[outer+inner*5]);
            }
        }
        
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new GridLayout(1,2));
        
        JLabel jepLabel = new JLabel("Jeopardy");
        jepLabel.setFont(new Font("New Times Roman", Font.BOLD, 80));
        jepLabel.setForeground(Color.YELLOW);
        topPanel.add(jepLabel);
        
        JButton button = new JButton("Final Jeopardy");
        button.addActionListener(new Listener(25));
       
        button.setFont(boldUnderline);
        button.setForeground(Color.YELLOW);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        topPanel.add(button);
        
        ((GridLayout)questionHolder.getLayout()).setVgap(20);
        ((GridLayout)questionHolder.getLayout()).setHgap(2);
        add(topPanel, BorderLayout.NORTH);
        questionHolder.setOpaque(false);

        add(questionHolder, BorderLayout.CENTER);

        
    }
    /**
     * Method refreshButtons refreshes the values on the buttons to reflect the current values of the questions they refer to
     */
    public void refreshButtons()
    {

        for(int i=0; i<25; i++)
        {
            buttonArrangementChanger[i].setText("$"+findQuestion(i).getCurrentValue());
        }
    }
    /**
     * EditListener the listener that is used when editing the question list (for categories)
     * 
     * @version 3/2/2020
     * @author Phoenix
     */
    private class EditListener implements ActionListener
    {
        /**
         * int index the index of the category
         */
        private int index;
        /**
         * EditListener constructor initializes index
         * 
         * @param i the index of the category
         */
        public EditListener(int i)
        {
            index=i;
        }
        /**
         * Method actionPerformed changes the category names when enter is pressed on a textfield
         *
         * @param e unused action event
         */
        public void actionPerformed(ActionEvent e)
        {
            try{
                    FileReader fileReader = new FileReader(file);
                    Scanner reader = new Scanner(fileReader);

                    String line = null;
                    String store = "";
                    String condition= "Categories:";
                    do
                    {
                        line = reader.nextLine();
                        store = store + line + System.lineSeparator();
                    }
                    while(!line.contains(condition));
                    //arrived at categories list
                    line = reader.nextLine();
                    String editedLine = line;
                    String currName= categoryList[index].getName();
                    editedLine= line.substring(0, line.indexOf(currName)); 
                    editedLine= editedLine + categoryEdits[index].getText();
                    editedLine= editedLine + line.substring(line.indexOf(currName)+currName.length());
                    categoryList[index].setName(categoryEdits[index].getText());
                    store = store + editedLine + System.lineSeparator();
                    while(reader.hasNext())
                    {
                        line=reader.nextLine();
                        store = store + line + System.lineSeparator();
                    }
                    fileReader.close();
                    reader.close();
                    FileWriter fw = new FileWriter(file);
                    fw.write(store);
                    fw.close();
                    JOptionPane.showMessageDialog(new JFrame(), "Successfully updated categories");
                }
                catch(Exception ex)
                {
                	handleException(ex);
                }
        }
    }
    /**
     * Method setEdit prepares the panel for editing mode if it is to be edited
     * 
     * @param e whether or not the panel is to be edited
     */
    public void setEdit(boolean e)
    {
        edit = e;
        if(edit)
        {
            for(int i=0; i<5; i++)
            {
                categoryEdits[i].setText(categoryLabels[i].getText());
                categoryEdits[i].setVisible(true);
                categoryEdits[i].setEnabled(true);
                categoryLabels[i].setVisible(false);
            }
            for(int i=0; i<scoreLabels.length; i++)
            {
            	scoreLabels[i].setVisible(false);
            }
        }
        
    }

    /**
     * Method checkDailyDouble checks if the index of a button matches the index of a daily double
     *
     * @param index index of the button
     * @param dailyDoubleIndexes all indexes of daily doubles
     * @return whether or not the index was found in the array of daily double indexes
     */
    public boolean checkDailyDouble(int index)
    {
        for(int i=0; i<indexOfDailyDoubles.length; i++)
        {
            if(indexOfDailyDoubles[i]==index)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Method setScore changes a team's score and updates the display
     *
     * @param newValue the new value of the team's score
     * @param teamNum the number the team belongs to
     */
    public static void setScore(int newValue, int teamNum)
    {
        scores[teamNum]= newValue;
        scoreLabels[teamNum].setText("Team "+(teamNum+1)+": "+newValue);
    }
    
    /**
     * Method getScore returns the score of a team
     * 
     * @param teamNum the team number of a team
     * @return the score of the team
     */
    public static int getScore(int teamNum)
    {
        return scores[teamNum];
    }
    
    /**
     * Class Listener the listener used to take the user to the questionpanel of their selected question
     * 
     * @version 3/2/2020
     * @author Phoenix
     */
    private class Listener implements ActionListener
    {
        /**
         * int index the index of the question
         */
        int index;
        
        /**
         * Listener Constructor initializes values
         *
         * @param i the index of the question
         * @param d whether or not the question is a daily double
         */
        public Listener(int i)
        {
            index=i;
        }

        /**
         * Method actionPerformed takes the user to their question
         *
         * @param e unused actionevent
         */
        public void actionPerformed(ActionEvent e)
        {
            Question q= findQuestion(index);
            if(!edit&&q.isSpecial())
            {
                Driver.switchQuestions("SpecialPanel", q);
            }
            else
            {
                Driver.switchQuestions("QuestionPanel", q);
            }
        }
        
        

    }
    /**
     * Method findQuestion returns the question this listener refers to
     *
     * @return the question
     */
    public Question findQuestion(int index)
    {
    	Question target;
    	if(index==25)
    	{
    		target = finalJeopardy;
    	}
    	else
    	{
    		Category type = categoryList[index/5];
        	target= type.getQuestion(index%5);
    	}
        return target;
    }
    /**
     * Method setFile sets the file and processes it into useable question and categories for every other panel
     *
     * @param f the file
     */
    public void setFile(File f)
    {
        file = f;
        processFile();
    }
    /**
     * Method processFile processes the file and sets the values of all the question objects when the correct values are found
     */
    private void processFile()
    {
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            //Skip to categories
            do
            {
                line = reader.readLine();
            }
            while(!line.equals("Categories:"));
            line = reader.readLine();
            processCategories(line, 0);
            //Skip to Questions
            while(!line.equals("Questions:"))
            {
                line = reader.readLine();
            }
            line = reader.readLine();
            //Process the questions
            int categoryIndex = 1;
            while(!line.equals("End Questions"))
            {
                int innerIndex = 0;
                boolean isQuestion = true;
                while(!isIndicator(line = reader.readLine()))
                {
                    if(isQuestion) 
                    {
                        categoryList[categoryIndex-1].getQuestion(innerIndex).setQuestion(line);
                        isQuestion = false;
                    }
                    else
                    {
                        categoryList[categoryIndex-1].getQuestion(innerIndex).setAnswer(line);
                        categoryList[categoryIndex-1].getQuestion(innerIndex).setOriginalValue((innerIndex+1)*100);
                        categoryList[categoryIndex-1].getQuestion(innerIndex).setCurrentValue((innerIndex+1)*100);
                        isQuestion = true;
                        innerIndex++;
                    }
                }
                categoryIndex++;
            }
            //set daily doubles
            for(int i=0; i<25; i++)
            {
            	findQuestion(i).setDailyDouble(checkDailyDouble(i));
            }
            //Process the final jeopardy
            line=reader.readLine();
            finalJeopardy.setQuestion(line=reader.readLine());
            finalJeopardy.setAnswer(line=reader.readLine());
            finalJeopardy.setFinalJeopardy(true);
            //Reader is finished
            reader.close();
        }
        catch(Exception ex)
        {
        	handleException(ex);
        }
        fixCategoryLabels();
    }
    /**
     * Method fixCategoryLabels adds the values of categorylabels late, because the names are unknown until the user chooses a game file
     */
    private void fixCategoryLabels()
    {
        for(int i=0; i<categoryLabels.length; i++)
        {
            categoryLabels[i].setText(categoryList[i].getName());
        }
    }

    /**
     * Method isIndicator checks whether or not a string is an indicator of a new segment
     *
     * @param s the string
     * @return whether or not the string is an "indicator"
     */
    private boolean isIndicator(String s)
    {
        for(int i=1; i<=5; i++)
        {
            String tempIndic= "C"+i+"QA";
            if(tempIndic.equals(s))
            {
                return true;
            }
        }
        if(s.isEmpty()||s.equals("End Questions")) return true;
        return false;
    }

    /**
     * Method processCategories stepwise refined method to process the categories in a string
     *
     * @param s the string
     * @param index used for recursion
     */
    public void processCategories(String s, int index)
    {
            String name = s.substring(0, s.indexOf(","));
            categoryList[index] = new Category(name);
            int next = s.indexOf(",")+2;
            if(s.length()>next)
            {
                String remainder = s.substring(next);
                processCategories(remainder, index+1);
            }
    }
    /**
     * Method getScores returns the points of the teams
     *
     * @return the points
     */
    public static int[] getScores()
    {
        return scores;
    }
}

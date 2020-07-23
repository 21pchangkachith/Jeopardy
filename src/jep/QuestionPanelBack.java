package jep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
/**
 * Write a description of class QuestionPanel here.
 *
 * @author Phoenix Changkachith
 * @version 3/2/2020
 * 
 */
public class QuestionPanelBack extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7476901316775349781L;
	/**
     * JPanel buttonHolder the panel that holds all the buttons that take the user to question screens
     */
    private JPanel buttonHolder;
    /**
     * JPanel added the points gui used to give and take points
     */
    JPanel added;
    /**
     * Question currQues the question that is being viewd
     */
    private Question currQues;
    /**
     * JLabel question the label storing the question
     */
    private JLabel question;
    /**
     * boolean edit the boolean that determines whether or not the question is being edited or viewed
     */
    private boolean edit;
    /**
     * File file the file that the question was loaded from
     */
    private File file;
    /**
     * JLabel[] pointsLabel an array of JLabels that display the current points
     */
    private JLabel[] pointLabels;
    /**
     * JButton[] pointButtons an array of JButtons that allow the user to choose which team gets points
     */
    private JButton[] pointButtons;
    /**
     * JTextField editQuestion the JTextField that can be editted when edit is enabled to edit the question
     */
    private JTextField editQuestion;
    /**
     * boolean addedButton the boolean that is used to control the number of buttons that appear on the screen when the edit function is enabled 
     */
    private boolean addedButton;
    /**
     * String[] order the array of strings that are appended to the buttons and are used as a makeshift-queue
     */
    private final String[] order = new String[]{"First", "Second", "Third", "Fourth", "Fifth", "Sixth"};
    /**
     * String[] images the array of images that stores the names of the images used for point distribution
     */
    private final String[] images = new String[]{"pie_ul.jpg", "pie_ur.jpg", "pie_bl.jpg", "pie_br.jpg"};
    /**
     * int orderIndex the int storing the index of the appended queue from order
     */
    private int orderIndex;
    /**
     * JLabel money the label displaying the money
     */
    private JLabel money;
    /**
     * JLabel category the label displaying the category
     */
    private JLabel category;
    private JPanel questionHolder;
    /**
     * Constructor for objects of class QuestionPanel
     */
    public QuestionPanelBack()
    {
        orderIndex=0;
        addedButton = false;
        file = null;
        editQuestion = new JTextField("");
        editQuestion.setFont(new Font("New Times Roman", Font.BOLD, 40));
        edit=false;
        setLayout(new BorderLayout());
        currQues=null;

        questionHolder = new JPanel();
        questionHolder.setLayout(new BorderLayout());
        questionHolder.setBackground(Color.BLUE.darker());

        JPanel topHolder = new JPanel();
        topHolder.setBackground(Color.BLUE.darker());

        money = new JLabel("");
        money.setFont(new Font("New Times Roman", Font.BOLD, 40));
        money.setForeground(new Color(246, 204, 117));
        topHolder.add(money);

        category = new JLabel("");
        category.setFont(new Font("New Times Roman", Font.BOLD, 40));
        category.setForeground(Color.RED);
        topHolder.add(category);

        questionHolder.add(topHolder, BorderLayout.NORTH);

        question = new JLabel("");
        question.setFont(new Font("New Times Roman", Font.BOLD, 40));
        question.setForeground(Color.WHITE);
        questionHolder.add(question, BorderLayout.CENTER);

        add(questionHolder, BorderLayout.CENTER);

        buttonHolder = new JPanel();
        buttonHolder.setLayout(new GridLayout(2,6));
        ((GridLayout)buttonHolder.getLayout()).setVgap(20);
        int[] scores = QuestionListPanel.getScores();

        pointLabels= new JLabel[6];
        for(int i=0; i<6; i++)
        {
            pointLabels[i]= new JLabel("Team " +(i+1)+": "+scores[i]);
            pointLabels[i].setFont(new Font("New Times Roman", Font.BOLD, 20));
            buttonHolder.add(pointLabels[i]);
        }
        pointButtons = new JButton[6];
        for(int i=1; i<=6; i++)
        {
            pointButtons[i-1] = new JButton("Team "+i);
            pointButtons[i-1].addActionListener(new PointsListener(i));
            pointButtons[i-1].setFont(new Font("New Times Roman", Font.BOLD, 20));
            buttonHolder.add(pointButtons[i-1]);
        }

        add(buttonHolder, BorderLayout.SOUTH);

        JPanel header = new JPanel();
        header.setBackground(Color.BLUE.darker());
        header.setLayout(new GridLayout(1,5));

        JButton backButton = new JButton("");
        backButton.setPreferredSize(new Dimension(107, 84));
        backButton.setHorizontalTextPosition(JButton.CENTER);
        backButton.setVerticalTextPosition(JButton.CENTER);
        backButton.setMargin(new Insets(0,0,0,0));
        String path = "";
        try
        {
            path = Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "jep" + System.getProperty("file.separator")+"GameFiles" + System.getProperty("file.separator")+ "images" + System.getProperty("file.separator") + "BackButtonPicture.PNG";
        }
        catch(Exception ex)
        {
            System.out.println("Uh oh, the back button image is missing!");
        }
        backButton.setIcon(new ImageIcon(path));
        backButton.addActionListener(new Listener());
        backButton.setVerticalAlignment(SwingConstants.TOP);

        JPanel excessRemover = new JPanel(new FlowLayout());
        excessRemover.setBackground(Color.BLUE.darker());

        excessRemover.add(backButton);
        add(excessRemover, BorderLayout.WEST);

        setOpaque(true);
    }

    /**
     * Method updateScores updates the GUI scores of each team
     *
     */
    public void updateScores()
    {
        int[] scores= QuestionListPanel.getScores();
        for(int i=0; i<6; i++)
        {
            pointLabels[i].setText("Team " + (i+1) + ": " + scores[i]);
        }
    }

    /**
     * Method setFile records the file for questionpanel
     *
     * @param f the file
     */
    public void setFile(File f)
    {
        file=f;
    }
    /**
     * Class PointsListener the listener that controls all functions related to giving and taking points
     * 
     * @author Phoenix
     * @version 3/2/2020
     */
    private class PointsListener implements ActionListener
    {
        /**
         * int index the index of the team that this listener points to
         */
        int index;
        /**
         * boolean queued the boolean that controls whether or not a team has been queued to answer the question next
         */
        boolean queued;


        /**
         * PointsListener Constructor creates a points listener with basically default values
         *
         * @param i index of the team
         */
        public PointsListener(int i)
        {
            index=i;
            queued=false;
        }
        public boolean getQueued()
        {
        	return queued;
        }
        /**
         * Method setQueued changes the queue status 
         *
         * @param q whether or not a team is queued
         */
        public void setQueued(boolean q)
        {
            queued=q;
        }

        /**
         * Method actionPerformed triggered whenever the team points buttons are called and will always handle distribution of points/ask for the points to be risked/edit the file
         *
         * @param e unused action event
         */
        public void actionPerformed(ActionEvent e)
        {
            if(edit)
            {
                try{
                    FileReader fileReader = new FileReader(file);
                    Scanner reader = new Scanner(fileReader);

                    String line = null;
                    String store = "";
                    String condition= question.getText();
                    do
                    {
                        line = reader.nextLine();
                        store = store + line + System.lineSeparator();
                    }
                    while(!line.contains(condition));
                    store = store.substring(0, store.indexOf(condition));
                    String added = editQuestion.getText();
                    question.setText(added);
                    currQues.setQuestion(added);
                    store = store + added + System.lineSeparator();
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
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                Driver.switchPanels("AnswerPanel");
            }
            //if not editting
            else
            {
            	//if is a regular question
                if(!currQues.isFinalJeopardy())
                {
                	
                    added = new JPanel(new GridLayout(2,2));
                    //if queued
                    if(queued)
                    {
                    	System.out.println("test");
                        for(int i=0; i<4; i++)
                        {
                            JButton plain = new JButton("");
                            switch(i)
                            {
                                case 0:
                                plain.setText("100%");
                                break;
                                case 1:
                                plain.setText("50%");
                                break;
                                case 2:
                                plain.setText("-50%");
                                break;
                                case 3:
                                plain.setText("-100%");
                                break;
                            }
                            plain.setHorizontalTextPosition(JButton.CENTER);
                            plain.setVerticalTextPosition(JButton.CENTER);
                            plain.setMargin(new Insets(0,0,0,0));
                            plain.setFont(new Font("Times New Roman", Font.BOLD, 20));
                            String path = "";
                            try
                            {
                                path = Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "jep" + System.getProperty("file.separator")+"GameFiles" + System.getProperty("file.separator")+ "images" + System.getProperty("file.separator") + images[i];
                            }
                            catch(Exception ex)
                            {
                                System.out.println("Uh oh, an image is missing!");
                            }
                            plain.setIcon(new ImageIcon(path));
                            plain.addActionListener(new DistributionListener(plain.getText()));
                            added.add(plain);
                            
                        }
                        add(added, BorderLayout.EAST);
                            for(int i=0; i<pointButtons.length; i++)
                            {
                            	PointsListener targetListener = ((PointsListener)pointButtons[i].getActionListeners()[0]);
                            	if(targetListener.getQueued())
                            	{
                            		if(orderHolder[i]==-1)
                            		{
                            			targetListener.setQueued(false);
                            			pointButtons[i].setText(pointButtons[i].getText().substring(0, 6));
                            			pointButtons[i].setEnabled(false);
                            		}
                            		else
                            		{
                            			pointButtons[i].setText(pointButtons[i].getText().substring(0, 6)+ " "+order[orderHolder[i]]);
                            			if(orderHolder[i]==0)
                            			{
                            				pointButtons[i].setEnabled(true);
                            			}
                            		}
                            			
                            	}
                            }
                        
                         
                    }
                    //if not queued then queue up
                    else
                    {
                        pointButtons[index-1].setText(pointButtons[index-1].getText()+ " " + order[orderIndex]);
                        orderHolder[index-1] = orderIndex;
                        orderIndex++;
                        queued=true;
                        remove(added);
                    }
                }
                //
                else
                {
                    JButton button = pointButtons[index-1];
                    String text = button.getText();
                    if(text.contains("Correct"))
                    {
                        pointButtons[index-1].setText("Team " + index + " Incorrect");
                    }
                    else
                    {
                        pointButtons[index-1].setText("Team " + index + " Correct");
                    }
                    boolean allCorrected = true;
                    for(int i=0; i<pointButtons.length; i++)
                    {
                        if(!(pointButtons[i].getText().contains("Correct")|| pointButtons[i].getText().contains("Incorrect")))
                        {
                            allCorrected = false;
                        }
                    }
                    if(allCorrected && !addedButton)
                    {
                        addedButton= true;
                        JButton goToAnswer = new JButton("Go to answer");
                        goToAnswer.addActionListener(new FinalListener());
                        buttonHolder.add(goToAnswer);
                    }
                }
            }

        }
        /**
         * Class DistributionListener listener for the points gui
         * 
         * @version 3/2/2020
         * @author Phoenix
         */
        private class DistributionListener implements ActionListener
        {
            /**
             * String value the value that this listener carries
             */
            String value;
            /**
             * DistributionListener Constructor
             *
             * @param v the value in percent
             */
            public DistributionListener(String v)
            {
                value=v;
            }

            /**
             * Method actionPerformed called every time the points gui is clicked and handles points distribution
             *
             * @param e unused action event
             */
            public void actionPerformed(ActionEvent e)
            {
                Driver.getQuestionListPanel().setScore(calculateScore() + findExistingScore(), index-1);
                if(calculateScore()>0) currQues.setCurrentValue(currQues.getCurrentValue()-calculateScore());
                remove(added);
                int[] scores = QuestionListPanel.getScores();
                for(int i=0; i<6; i++)
                {
                    pointLabels[i].setText("Team " +(i+1)+": "+scores[i]);
                }
                if(currQues.getCurrentValue()==0) 
                {
                    Driver.switchPanels("AnswerPanel");
                }

            }

            /**
             * Method calculateScore returns the score that should be given based on the percent of the remaining value of teh question
             *
             * @return the value to be gained or lost
             */
            private int calculateScore()
            {
                double percent = Double.parseDouble(value.substring(0,value.indexOf("%")));
                if(currQues.isDailyDouble()&& percent>0) currQues.setCurrentValue(currQues.getCurrentValue()*2);
                int currValue= currQues.getCurrentValue();
                return (int) (percent/100.0*((double)currValue));
            }
        }
        /**
         * Class FinalListener the listener used to handle final jeopardy functions
         * 
         * @author Phoenix
         * @version 3/2/2020
         */
        private class FinalListener implements ActionListener
        {
            /**
             * Method actionPerformed triggered after the "bets" are locked in
             *
             * @param e unused actionevent
             */
            public void actionPerformed(ActionEvent e)
            {
                for(int i=0; i<6; i++)
                {
                    Driver.getQuestionListPanel().setScore(calculateScore(i), i);
                }
                Driver.switchPanels("AnswerPanel");
            }

            /**
             * Method calculateScore calculates the score of a team based on their bet
             *
             * @param i the team index of the team
             * @return the team's new score
             */
            private int calculateScore(int i)
            {
                int newValue = findExistingScore(i);
                if(pointButtons[i].getText().contains("Incorrect"))
                {
                    newValue-=currQues.getFinalJeopardyValue(i);
                }
                else
                {
                    newValue+=currQues.getFinalJeopardyValue(i);
                }
                return newValue;
            }

            /**
             * Method findExistingScore finds the existing score of a team
             *
             * @param i the index of the team
             * @return the team's existing score
             */
            private int findExistingScore(int i)
            {
                return Driver.getQuestionListPanel().getScore(i);
            }
        }
        /**
         * Method calculateScore returns the new score
         *
         * @return new score
         */
        private int calculateScore()
        {
            return findExistingScore()+currQues.getCurrentValue()*(((Boolean)currQues.isDailyDouble()).compareTo(false)+1);
        }

        /**
         * Method findExistingScore returns the existing score of a team
         *
         * @return the existing score
         */
        private int findExistingScore()
        {
            return Driver.getQuestionListPanel().getScore(index-1);
        }
    }
    /**
     * Method setEdit prepares the questionpanel to be edited if the user has chosen to go into edit mode
     *
     * @param e boolean deciding whether or not the question is to be edited
     */
    public void setEdit(boolean e)
    {
        edit=e;

        if(edit)
        {
            remove(buttonHolder);
            buttonHolder.setVisible(false);
            remove(question);
            question.setVisible(false);
            remove(questionHolder);
            


            JButton next = new JButton("Answer");
            next.addActionListener(new PointsListener(0));
            next.setPreferredSize(new Dimension(800,200));
            add(next, BorderLayout.SOUTH);

            editQuestion.setText(currQues.getQuestion());

            JPanel layoutFixer = new JPanel();
            layoutFixer.setVisible(true);
            layoutFixer.setBackground(Color.BLUE.darker());
            layoutFixer.setLayout(new GridLayout(5,1));
            for(int i=0; i<5; i++)
            {
                if(i==2) layoutFixer.add(editQuestion);
                else
                {
                    JButton placeHolder = new JButton();
                    placeHolder.setVisible(false);
                    layoutFixer.add(placeHolder);
                }
            }

            add(layoutFixer, BorderLayout.CENTER);

        }

    }

    /**
     * Method setQuestion stores the new question and updates labels to reflect it
     * 
     * @param q the new question
     */
    public void setQuestion(Question q)
    {
        currQues=q;
        if(!currQues.isSpecial()&&!edit)
        {
            money.setText("$"+q.getOriginalValue());
            category.setText(q.getSubject());
            question.setText(q.getQuestion());
        }
        else 
        {
            question.setText(q.getQuestion());
        }

    }
    /**
     * Class Listener basic listener that takes the user back to the panel they were on before
     * 
     * @version 3/2/2020
     * @author Phoenix
     */
    private class Listener implements ActionListener
    {
        /**
         * Method actionPerformed triggered upon back button being clicked and takes the user back to the questionlist
         *
         * @param e unused actionevent
         */
        public void actionPerformed(ActionEvent e)
        {
        	orderIndex=0;
        	for(int i=0; i<pointButtons.length; i++)
            {
            	PointsListener targetListener = ((PointsListener)pointButtons[i].getActionListeners()[0]);
            		targetListener.setQueued(false);
            		pointButtons[i].setText(pointButtons[i].getText().substring(0, 6));
            		pointButtons[i].setEnabled(true);
            }
        	remove(added);
            Driver.switchPanels("QuestionListPanel");
        }
    }
}

package jep;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

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
public class QuestionPanel extends QAPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7476901316775349781L;
    /**
     * JButton[] pointButtons an array of JButtons that allow the user to choose which team gets points
     */
    private JButton[] pointButtons;
    /**
     * boolean addedButton the boolean that is used to control the number of buttons that appear on the screen when the edit function is enabled 
     */
    private boolean addedButton;
    /**
     * String[] order the array of strings that are appended to the buttons and are used as a makeshift-queue
     */
    private final String[] order = new String[]{"First", "Second", "Third", "Fourth", "Fifth", "Sixth"};
    /**
     * int orderIndex the int storing the index of the appended queue from order
     */
    private int orderIndex;
    /**
     * Constructor for objects of class QuestionPanel
     */
    public QuestionPanel()
    {
    	super();
    	backButton.addActionListener(new Listener("QuestionListPanel"));
        orderIndex=0;
        addedButton = false;
        pointButtons = new JButton[6];
        for(int i=0; i<6; i++)
        {
            pointButtons[i] = new JButton("Team "+(i+1)) {
            	
				@Override
            	protected void paintComponent(Graphics g)
            	{
					Graphics newGraphic = g.create();
					newGraphic.setColor(questionColor.brighter());
            		super.paintComponent(newGraphic);
            	}
            };
            pointButtons[i].addActionListener(new PointsListener(i+1));
            pointButtons[i].setFont(new Font("New Times Roman", Font.BOLD, 20));
            pointButtons[i].setForeground(categoryColor);
            pointButtons[i].setBackground(questionColor);
            pointButtons[i].setOpaque(true);
            //CompoundBorder currentBorder = (CompoundBorder) pointButtons[i].getBorder();
            //LineBorder outsideBorder = new LineBorder (questionColor, 5);
            //CompoundBorder border = new CompoundBorder(currentBorder.getInsideBorder(), outsideBorder);
            //pointButtons[i].setBorder(outsideBorder);
            //pointButtons[i].setBorderPainted(false);
            footerHolder.add(pointButtons[i]);
        }

    }
    
    
    @Override
    public void setQuestion(Question q)
    {
    	super.setQuestion(q);
    	editArea.setText(q.getQuestion());
    	content.setText(q.getQuestion());
    }
    public void editQuestion() {
    	try{
        	String line = null;
            String store = "";
            String condition= content.getText();
        	if(editArea.getText().isEmpty())
            {
            	JOptionPane.showMessageDialog(new JFrame(), "Please do not attempt to save blank questions, it may make the file unreadable");
            	return;
            }
            FileReader fileReader = new FileReader(file);
            Scanner reader = new Scanner(fileReader);
            do
            {
                line = reader.nextLine();
                store = store + line + System.lineSeparator();
            }
            while(!line.contains(condition));
            store = store.substring(0, store.indexOf(condition));
            String newQuestion = editArea.getText();
            content.setText(newQuestion);
            currQues.setQuestion(newQuestion);
            store = store + newQuestion + System.lineSeparator();
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
            JOptionPane.showMessageDialog(new JFrame(), "Successfully updated question");
        }
        catch(Exception ex)
        {
        	handleException(ex);
        }  
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
        /**
         * Method setQueued changes the queue status 
         *
         * @param q whether or not a team is queued
         */
        public void setQueued(boolean q)
        {
            queued=q;
        }
        
        public void handleQueuedQuestion() {
        	int points= 0;
            String input = null;
            boolean valueFound = false;
            do
            {
         	   try {
         		  input = JOptionPane.showInputDialog("Enter points to award/remove (use negative to remove)");
         		  points = Integer.parseInt(input);
         	 	 if(points<=currQues.getCurrentValue())
         	 	 {
         	 		 valueFound=true;
         	 	 }
         	 	 else
         	 	 {
         	 		 JOptionPane.showMessageDialog(new JFrame(), "Please enter a number less than or equal to the available points");
         	 	 }
         	   }
         	   catch(NumberFormatException exception)
         	   {
         		   if(input==null)
         		   {
         			   break;
         		   }
         		   else
         		   {
         	        JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid number");
         		   }
         		}
            }
            while(!valueFound);
            if(input==null)
            {
         	   return;
            }
            QuestionListPanel.setScore(calculateScore(points), index-1);
            if(points>0) currQues.setCurrentValue(currQues.getCurrentValue()-points);
            money.setText("$"+currQues.getCurrentValue());
            int[] scores = QuestionListPanel.getScores();
            for(int i=0; i<6; i++)
            {
                pointLabels[i].setText("Team " +(i+1)+": "+scores[i]);
            }
            if(currQues.getCurrentValue()==0) 
            {
                Driver.switchPanels("AnswerPanel");
                resetTeamButtons();
            }
        }
        public void handleFinalJeopardy() {
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
                
                goToAnswer.setFont(new Font("New Times Roman", Font.BOLD, 24).deriveFont(fontAttributes));
                goToAnswer.setOpaque(false);
                goToAnswer.setForeground(Color.YELLOW);
                goToAnswer.setBorderPainted(false);
                Dimension dim = new Dimension(Integer.MAX_VALUE, 50);
                goToAnswer.setPreferredSize(dim);
                goToAnswer.setMinimumSize(dim);
                goToAnswer.addActionListener(new FinalListener());
                GridBagConstraints c = new GridBagConstraints();
                
                c.gridx=0;
                c.gridy=4;
                c.gridwidth=4;
                c.fill= GridBagConstraints.HORIZONTAL;
                c.weightx=1.0;
                c.weighty=1.0;
                add(goToAnswer, c);
            }
                
        }
        /**
         * Method actionPerformed triggered whenever the team points buttons are called and will always handle distribution of points/ask for the points to be risked/edit the file
         *
         * @param e unused action event
         */
        public void actionPerformed(ActionEvent e)
        {

                if(!currQues.isFinalJeopardy())
                {
                    if(queued)
                    {
                       handleQueuedQuestion();

                    }
                    else
                    {
                        pointButtons[index-1].setText(pointButtons[index-1].getText()+ " " + order[orderIndex]);
                        orderIndex++;
                        queued=true;
                    }
                }
                else
                {
                    handleFinalJeopardy();
                }
            

        }
        /**
		 * Method calculateScore returns the new score
		 *
		 * @return new score
		 */
		private int calculateScore(int points)
		{
			int doubleController = (((Boolean)currQues.isDailyDouble()).compareTo(false)+1);
		    return findExistingScore()+points*doubleController;
		}
		/**
		 * Method findExistingScore returns the existing score of a team
		 *
		 * @return the existing score
		 */
		private int findExistingScore()
		{
		    return QuestionListPanel.getScore(index-1);
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
                    QuestionListPanel.setScore(calculateScore(i), i);
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
                return QuestionListPanel.getScore(i);
            }
        }
    }
    /**
     * Method setEdit prepares the questionpanel to be edited if the user has chosen to go into edit mode
     *
     * @param e boolean deciding whether or not the question is to be edited
     */
    @Override
    public void setEdit(boolean e)
    {
    	super.setEdit(e);
        if(edit)
        {
        	GridBagConstraints c = new GridBagConstraints();
            JButton next = new JButton("Save & Go to Answer");
            next.addActionListener(new Listener("AnswerPanel"));
            next.setOpaque(false);
            next.setBorderPainted(false);
            next.setForeground(Color.RED);
            next.setFont(new Font("New Times Roman", Font.BOLD, 24).deriveFont(fontAttributes));
            
            c.gridx = 0;
            c.gridy= 2; 
            c.gridwidth=3;
            c.weightx= 1.0;
            c.weighty= 1.0;
            c.fill= GridBagConstraints.HORIZONTAL;
            c.anchor= GridBagConstraints.LINE_END;
            c.insets = new Insets(0, 30, 0, 30);
            add(next, c);

        }

    }
    public void resetTeamButtons()
    {
    	orderIndex=0;
    	for(int i=0; i<pointButtons.length; i++)
        {
        	PointsListener targetListener = ((PointsListener)pointButtons[i].getActionListeners()[0]);
        		targetListener.setQueued(false);
        		pointButtons[i].setText(pointButtons[i].getText().substring(0, 6));
        }
    }
}

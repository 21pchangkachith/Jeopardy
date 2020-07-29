package jep;
import javax.swing.*;

import jep.utilities.ExceptionHandler;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.*;
import java.io.*;

import javax.sound.sampled.*;
/**
 * Class SpecialPanel counts for daily doubles and things like that
 * 
 * @author Phoenix Changkachith
 * @author Justin Son
 * @version 2/23/2020
 * @period 2
 * @teacher Coglianese
 */
public class SpecialPanel extends GamePanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1759315044194611683L;
	private JLabel specialLabel;
    private Question question;
    private JPanel inputBox;
    private JPanel inputInfo;
    private ArrayList<JTextField> amounts;
    private JLabel[] scoreLabels;
    /**
     * @author Phoenix Changkachith
     * @version 2/23/2020
     * @period 2
     * @teacher Coglianese
     * 
     * formats the panel correctly. Should be a list of whitespace and the label (Daily Double) 
     */
    public SpecialPanel()
    {
    	super();
    	GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());
        question=null;
        

        amounts = new ArrayList<JTextField>();

        JButton backButton = new JButton("");
        backButton.setIcon(DefaultPanel.backButtonIcon);
        removeBackground(backButton);
        backButton.addActionListener(new Listener("QuestionListPanel"));
        
        c.gridy=0;
        c.gridx=0;
        c.anchor= GridBagConstraints.FIRST_LINE_START;
        c.weightx= 1.0;
        c.weighty= 1.0;
        c.insets = new Insets(10, 10, 0, 0);
        add(backButton, c);

        
        specialLabel = new JLabel("");
        specialLabel.setFont(new Font("New Times Roman", Font.BOLD, 50));
        specialLabel.setForeground(moneyColor);
        c.gridx++;
        c.insets = new Insets(10, 0, 0, 30);
        add(specialLabel, c);

        
        
        inputInfo = new JPanel();
        inputInfo.setLayout(new GridLayout(0, 1));
        ((GridLayout)inputInfo.getLayout()).setVgap(50);
        ((GridLayout)inputInfo.getLayout()).setHgap(50);
        inputInfo.setOpaque(false);
        
        
        
        inputBox = new JPanel();
        inputBox.setLayout(new GridLayout(0, 1));
        
        Dimension test = new Dimension(100, 300);
        inputBox.setPreferredSize(test);
        inputBox.setMaximumSize(test);
        inputBox.setMinimumSize(test);
        inputBox.setSize(test);
        inputBox.setOpaque(false);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0,2));
        centerPanel.add(inputInfo);
        centerPanel.add(inputBox);
        centerPanel.setOpaque(false);
        
        
        
        c.gridx = 0;
        c.gridy=1; 
        c.gridwidth=2;
        c.fill= GridBagConstraints.HORIZONTAL;
        c.anchor= GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 30, 0, 30);
        add(centerPanel, c);

        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        
        JButton button = new JButton("Go to question");
        button.setPreferredSize(new Dimension(800,100));
        button.setFont(new Font("New Times Roman", Font.BOLD, 25).deriveFont(fontAttributes));
        button.setForeground(questionColor);
        removeBackground(button);
        button.addActionListener(new SpecialListener());
        
        c.gridy=2;
        c.anchor= GridBagConstraints.LAST_LINE_START;
        c.fill= GridBagConstraints.HORIZONTAL;
        add(button, c);
        
        scoreLabels = new JLabel[numTeams];
        JPanel scoreHolder = new JPanel();
        scoreHolder.setLayout(new GridLayout(1,5));
        for(int i=0; i<scoreLabels.length; i++)
        {
        	scoreLabels[i] = new JLabel("Team "+(i+1)+": "+"0");
            scoreLabels[i].setFont(new Font("New Times Roman", Font.BOLD, 24));
            scoreLabels[i].setForeground(moneyColor);
            scoreLabels[i].setOpaque(false);
            scoreHolder.add(scoreLabels[i]);
        }
        scoreHolder.setOpaque(false);
        
        c.gridy=3;
        c.anchor= GridBagConstraints.LAST_LINE_START;
        c.fill= GridBagConstraints.HORIZONTAL;
        add(scoreHolder, c);
    }

    public void setQuestion(Question q)
    {
        question=q;
        if(q.isSpecial())
        {
        	setDailyDouble(q.isDailyDouble());
        }
        updateScores();
    }
    public void updateScores()
    {
        int[] scores= DefaultPanel.getManager().getScores();
        for(int i=0; i<numTeams; i++)
        {
            scoreLabels[i].setText("Team " + (i+1) + ": " + scores[i]);
        }
    }
    /**
     * sets the question to be a daily Double
     */
    public void setDailyDouble(boolean isDailyDouble)
    {
        inputBox.removeAll();
        inputInfo.removeAll();
        ArrayList<String> info= new ArrayList<String>();
        amounts = new ArrayList<JTextField>();
        if(isDailyDouble) 
        {   
            specialLabel.setText("Daily Double!");
            info.add("      Bet Amount: "); 
        }
        else if(question.isFinalJeopardy())
        {
            specialLabel.setText("Final Jeopardy!");
            playJingle();
            for(int i=0; i<numTeams; i++)
            {
                info.add("       Team "+(i+1)+" Bets: ");
            }
        }


        for(int i=0; i<info.size(); i++)
        {
            JLabel amountInfo = new JLabel(info.get(i));
            amountInfo.setFont(new Font("New Times Roman", Font.BOLD, 25));
            amountInfo.setVisible(true);
            amountInfo.setForeground(buttonBackColor);
            
            JPanel flow = new JPanel();
            flow.setLayout(new GridBagLayout());
            flow.setOpaque(false);
            JTextField amount = createBetField();
            flow.add(amount);
            inputBox.add(flow);

            inputInfo.add(amountInfo);
            amounts.add(amount);
        }
        
    }

	private JTextField createBetField() {
		JTextField amount = new JTextField("");
		amount.setFont(new Font("New Times Roman", Font.BOLD, 25));
		Dimension dim = new Dimension(300, 50);
		amount.setSize(dim);
		amount.setPreferredSize(dim);
		amount.setMaximumSize(dim);
		amount.setVisible(true);
		amount.setBackground(buttonBackColor);
		amount.setForeground(buttonColor);
		return amount;
	}

	private void playJingle() {
		try {
			InputStream stream = getClass().getResourceAsStream("/jep/GameFiles/resources/music/Jeopardy.wav");
			InputStream bufferedStream = new BufferedInputStream(stream);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
		    Clip clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		} catch(Exception ex) {
		    ExceptionHandler.getHandler().handleException(ex, "Error with playing sound. \n\n");
		    ex.printStackTrace();
		}
	}
    private class SpecialListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try{
                if(question.isDailyDouble())
                {
                    int value = Integer.parseInt(amounts.get(0).getText());
                    if(question.getCurrentValue()!=0)
                    {
                        question.setCurrentValue(value);
                        question.setOriginalValue(value);
                    }
                }
                else
                {
                    int[] values = new int[numTeams];
                    for(int i=0; i<values.length; i++)
                    {
                        values[i]= Math.abs(Integer.parseInt(amounts.get(i).getText()));
                    }
                    question.setFinalJeopardyValues(values);
                }
                DefaultPanel.getManager().toPanel("QuestionPanel");
            }
            catch(NumberFormatException empty)
            {
            	JOptionPane.showMessageDialog(new JFrame(), "Please enter only valid numbers");
            }
        }
    }

}

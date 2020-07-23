package jep;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.*;

public class QAPanel extends GamePanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7224801135062401392L;
	
	protected Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
	/**
     * JPanel footerHolder the panel that holds all the buttons that take the user to question screens
     */
    protected JPanel footerHolder;
    /**
     * Question currQues the question that is being viewd
     */
    protected Question currQues;
    /**
     * JLabel question the label storing the question
     */
    protected JTextArea content, editArea;
    /**
     * boolean edit the boolean that determines whether or not the question is being edited or viewed
     */
    protected boolean edit;
    /**
     * File file the file that the question was loaded from
     */
    protected File file;
    /**
     * JLabel[] pointsLabel an array of JLabels that display the current points
     */
    protected JLabel[] pointLabels;
    /**
     * JLabels displaying category and money
     */
    protected JLabel category, money;
    protected JButton backButton; 
    /**
     * Constructor for objects of class QuestionPanel
     */
    
    public QAPanel()
    {
    	super();
    	setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	setOpaque(false);
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    	
        file = null;
        edit=false;
        currQues=null;



        editArea = makeQuestionArea();
        editArea.setOpaque(true);
        editArea.setForeground(Color.RED);
        
        content = makeQuestionArea();
        content.setEditable(false);

        money = makeInformationLabel(moneyColor, 40);
        category = makeInformationLabel(categoryColor, 44);
       
        backButton = new JButton("");
        backButton.setOpaque(false);
        Dimension dim = new Dimension(107, 84);
        backButton.setMaximumSize(dim);
        backButton.setPreferredSize(dim);
        backButton.setMargin(new Insets(0,0,0,0));
        backButton.setBorderPainted(false);
        backButton.setIcon(new ImageIcon(DefaultPanel.backButtonPath));
        

        
        c.gridy=0;
        c.gridx=0;
        c.anchor= GridBagConstraints.FIRST_LINE_START;
        c.weightx= 0.5;
        c.weighty= 1.0;
        c.insets = new Insets(10, 10, 0, 0);
        add(backButton, c);
        
        c.gridx++;
        c.anchor= GridBagConstraints.FIRST_LINE_END;
        c.insets = new Insets(10, 0, 0, 30);
        add(money, c);
        
        c.gridx++;
        c.weightx= 1.0;
        c.anchor= GridBagConstraints.FIRST_LINE_START;
        add(category, c);
        
        c.gridx = 0;
        c.gridy=1; 
        c.gridwidth=3;
        c.fill= GridBagConstraints.HORIZONTAL;
        c.anchor= GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 30, 0, 30);
        add(content, c);

        
        footerHolder = new JPanel();
        footerHolder.setLayout(new GridLayout(0,6));
        ((GridLayout)footerHolder.getLayout()).setVgap(20);
        int[] scores = QuestionListPanel.getScores();

        pointLabels= new JLabel[6];
        for(int i=0; i<6; i++)
        {
            pointLabels[i]= new JLabel("Team " +(i+1)+": "+scores[i]);
            pointLabels[i].setFont(new Font("New Times Roman", Font.BOLD, 24));
            pointLabels[i].setForeground(moneyColor);
            footerHolder.add(pointLabels[i]);
        }
        footerHolder.setOpaque(false);
        
        c.gridy=2;
        c.anchor= GridBagConstraints.LAST_LINE_START;
        c.fill= GridBagConstraints.HORIZONTAL;
        add(footerHolder, c);
        

        

    }
    
    private JLabel makeInformationLabel(Color cl, int fontSize)
    {
    	JLabel temp = new JLabel("");
        temp.setFont(new Font("New Times Roman", Font.BOLD, fontSize));
        temp.setForeground(cl);
        temp.setOpaque(false);
    	return temp;
    }
    /**
     * Method setQuestion stores the new question and updates labels to reflect it
     * 
     * @param q the new question
     */
    public void setQuestion(Question q)
    {
        currQues=q;
        category.setText(q.getSubject());
        if(!q.isFinalJeopardy())
        {
        	money.setText("$"+q.getOriginalValue());
        }
        else
        {
        	money.setText("");
        	category.setText("Final Jeopardy!");
        }
        
        //override will set content text and editArea text

    }
    public void setEdit(boolean e)
    {
    	edit = e;
        if(edit)
        {

            remove(footerHolder);
            remove(content);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx=0;
            c.gridy=1;
            c.gridwidth=3;
            c.weightx=1.0;
            c.weighty=1.0;
            c.fill= GridBagConstraints.HORIZONTAL;
            c.anchor= GridBagConstraints.LINE_END;
            
            
            c.insets= new Insets(0, 20, 0, 20);
            
            
            add(editArea, c);
        }
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
        if(!currQues.isFinalJeopardy())
        {
        	money.setText("$"+currQues.getCurrentValue());
        }
    }
    
    public JTextArea makeQuestionArea()
    {
    	JTextArea area = new JTextArea("");
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setFont(new Font("New Times Roman", Font.BOLD, 36));
        area.setForeground(questionColor);
        return area;
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
	



}

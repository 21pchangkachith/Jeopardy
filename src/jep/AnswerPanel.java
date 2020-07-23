package jep;
import javax.swing.*;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.*;
import java.util.*;
/**
 * Class AnswerPanel the panel that displays the answer to a given question object
 *
 * @author Phoenix
 * @version 3/2/2020
 */
public class AnswerPanel extends QAPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1741857724532193636L;
    
    /**
     * Constructor for objects of AnswerPanel, initializes everything
     */
    public AnswerPanel()
    {
    	super();
    	backButton.addActionListener(new Listener("QuestionListPanel"));
    }
    
    @Override
    public void setQuestion(Question q)
    {
    	super.setQuestion(q);
    	editArea.setText(q.getAnswer());
    	content.setText(q.getAnswer());
    }
    @Override
    public void setEdit(boolean e)
    {
    	super.setEdit(e);
    	if(edit)
    	{
    		GridBagConstraints c = new GridBagConstraints();
    		JButton back = new JButton("Save & Back to Question");
            back.addActionListener(new Listener("QuestionPanel"));
            back.setOpaque(false);
            back.setBorderPainted(false);
            back.setForeground(Color.RED);
            back.setFont(new Font("New Times Roman", Font.BOLD, 24).deriveFont(fontAttributes));
            
            c.gridx = 0;
            c.gridy= 2; 
            c.gridwidth=3;
            c.weightx= 1.0;
            c.weighty= 1.0;
            c.fill= GridBagConstraints.HORIZONTAL;
            c.anchor= GridBagConstraints.LINE_END;
            c.insets = new Insets(0, 30, 0, 30);
            add(back, c);
    	}
    }
    public void changeAnswer()
    {
    	if(edit)
        {
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
                while(!line.equals(condition));
                store = store.substring(0, store.indexOf(condition));
                String added = editArea.getText();
                content.setText(added);
                currQues.setAnswer(added);
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
                JOptionPane.showMessageDialog(new JFrame(), "Successfully updated answer");
            }
            catch(Exception ex)
            {
            	handleException(ex);
            }
        }
    }
  
}

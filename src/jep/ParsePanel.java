package jep;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import tests.InvalidFormatException;



import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.extractor.SlideShowExtractor;

import jep.utilities.ExceptionHandler;
import jep.utilities.UIChooser;

import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;

/**
 * Class ParsePanel the panel used to create new sets from scratch
 *
 * @author Phoenix
 * @version 3/2/2020
 */
public class ParsePanel extends GamePanel
{
	private final String header =   "If you're seeing these lines of text, then this text file was produced by the Jeopardy Review Game written in Java by juniors at Troy High School." + System.lineSeparator()
 + "These lines are intended to discourage you from editing this file" + System.lineSeparator()
 + "If you are interested in directly modifying the particular set of questions in this text file, please refrain from modifying the formatting." + System.lineSeparator()
+ "However, it is recommended that you edit this set of questions directly through the program." + System.lineSeparator()
+ "Categories:" + System.lineSeparator();
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 2459571825670427920L;
	/**
     * JTextField newName the name of the file desired
     */
    private JTextField newName;
    /**
     * Constructor for objects of class ParsePanel initializes all values
     */
    public ParsePanel()
    {
    	super();
        GridBagConstraints c = new GridBagConstraints();
        

        
        JButton backButton = createButton("Back", new Listener());
        JButton newButton = createButton("Create new set: ", new CreateListener());
        JButton parseButton = createButton("Import set", new ParseListener());
           
        
        newName = new JTextField("example_name.txt");
        newName.setPreferredSize(newButton.getPreferredSize());
        setUpCategory(newName);
        

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weighty=0.5;
        add(backButton, c);
        c.gridx++;
        c.gridy++;
        c.weightx=0.5;
        c.weighty=0.0;
        c.fill= GridBagConstraints.HORIZONTAL;
        
        
        add(newButton, c);
        c.gridx++;
        
        add(newName, c);
        c.weighty=1.0;
        
        c.gridx--;
        c.gridy++;
        c.gridwidth = 2;
        c.fill= GridBagConstraints.HORIZONTAL;
        add(parseButton, c);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        c.weightx=1.0;
        c.gridx = 3;
        c.gridwidth=1;
        add(panel, c);
        
    }
    private JButton createButton(String text, ActionListener listen)
    {
        JButton button = new JButton(text);
        button.addActionListener(listen);
        adjustMenuButton(button);
        return button;
    }
    private class ParseListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		File powerpoint = null;
    		JFileChooser fc = new JFileChooser();
            javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("PPT file", "ppt");
            fc.setFileFilter(filter);
            fc.setAcceptAllFileFilterUsed(false);
            try
            {
                fc.setCurrentDirectory(new File(defaultPath));
            }
            catch(Exception ex)
            {
            	ExceptionHandler.getHandler().handleException(ex);
            }
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                powerpoint = fc.getSelectedFile();
                try
                {
                	HSLFSlideShow ppt = new HSLFSlideShow(new FileInputStream(powerpoint));
                	SlideShowExtractor<HSLFShape, HSLFTextParagraph> extractor = new SlideShowExtractor<>(ppt);
                	String pptContents = extractor.getText();
                	extractor.close();
                	
                	try
                	{
	                    File parsedFile = new File(powerpoint.getPath().substring(0, powerpoint.getPath().lastIndexOf("."))+".txt");
	                    if(parsedFile.exists())
	                    {
	                    	int overwrite = JOptionPane.showConfirmDialog(new JFrame(), "This gameset seems to already exists. Overwrite?");
	                    	if(overwrite != JOptionPane.YES_OPTION)
	                    	{
	                    		return;
	                    	}
	                    }
	                    FileWriter fw = new FileWriter(parsedFile);
	                    
	                    String lines = createSetFromPPT(pptContents);
	                    fw.write(lines);
	                    fw.close();
	                    JOptionPane.showMessageDialog(new JFrame(), "Reading from powerpoint successful. \n\nFind the converted file in the same folder as the powerpoint");
	                }
                	catch(Exception innerEx)
                	{
                		ExceptionHandler.getHandler().handleException(innerEx, "Whoops, something went wrong processing the ppt. Record this log below, and someone will know what to do with it. \n\n");
                	}
                	
                }
                catch(Exception ex)
                {
                	ExceptionHandler.getHandler().handleException(ex, "Whoops, something went wrong reading the ppt. Record this log below, and someone will know what to do with it. \n\n");
                }
            }
            
            
            
    	}
    }
    public String createSetFromPPT(String pptContents)
    {
    	String set = header;
    	set+= processCategories(pptContents)+System.lineSeparator();
    	set+="Questions:" + System.lineSeparator();
    	set+= processQuestions(pptContents);
    	
    	
    	return set;
    }
    public String processQuestions(String pptContents)
    {
    	String[] lines = pptContents.split("\\r?\\n");
    	String qna = "";
    	int matchCounter=0;
    	if(lines.length<52)
    	{
    		ExceptionHandler.getHandler().handleException(new InvalidFormatException("Too few lines to be turned into a valid question set"));
    	}
    	boolean standard = false;
    	int countedStandard = 0;
    	for(int i=0; i<lines.length; i++)
    	{
    		String currentLine = lines[i].trim();
    		if(currentLine.matches("(Q|R):.*"))
    		{
    			countedStandard++;
    		}
    	}
    	if(countedStandard>=50)
    	{
    		standard= true;
    	}
    	for(int i=0; i<lines.length; i++)
    	{
    		String currentLine = lines[i].trim();
    		if(matchCounter<50&&(currentLine.matches("(Q|R):.*")||!standard))
    		{
    			if(matchCounter%10==0)
    			{
    				qna+= "C"+((matchCounter/10)+1)+"QA" + System.lineSeparator();
    			}
    			if(currentLine.length()<5)
    			{
    				do
    				{
    					i++;
    				}
    				while(lines[i].trim().length()<5);
    				qna+= lines[i].trim() + System.lineSeparator();
    			}
    			else
    			{
    				if(standard)
    				{
    					qna+= currentLine.substring(3).trim() + System.lineSeparator();
    				}
    				else
    				{
    					qna+= currentLine.trim()+System.lineSeparator();
    				}
    			}
    			matchCounter++;
    			if(matchCounter>=50)
	    		{
	    			qna+= "End Questions" + System.lineSeparator();
	    	    	qna+= System.lineSeparator();
	    		}
    		}
    		else if(matchCounter>=50)
    		{
    			if(lines[i].trim().contains("Final Jeopardy")&&standard)
    			{
    				//grab ques
    				do
    				{
    					i++;
    				}
    				while(lines[i].length()<4);
    				qna+= lines[i].trim() + System.lineSeparator();
    				//ques grabbed
    				do
    				{
    					i++;
    				}
    				while(!lines[i].trim().contains("Final Jeopardy"));
    				//shift to ans
    				do
    				{
    					i++;
    				}
    				while(lines[i].trim().length()<4);
    				//seek ans
    				qna+= lines[i].trim() + System.lineSeparator();
    				//ans grabbed
    			}
    			else
    			{
    				while(lines[i].length()<4)
    				{
    					i++;
    				}
    				qna+= lines[i].trim() + System.lineSeparator();
    				do {
    					i++;
    				}
    				while(lines[i].length()<4);

    				qna+= lines[i].trim() + System.lineSeparator();
    			}
    		}
    		
    	
    	}
    	return qna;
    }
    //it is not certain that there will only be 5 categories as seen in sem. final
    //therefore i must weigh how many times each potential category appears and submit the most likely
    //candidates as the categories.
    public String processCategories(String pptContents)
    {
    	ArrayList<String> slicedLines = new ArrayList<String>();
    	ArrayList<Integer> weight = new ArrayList<Integer>();
    	String[] lines = pptContents.split("\\r?\\n");
    	for(int i=0; i<lines.length; i++)
    	{
    		int currWeight = 1;
    		String potentialCat;
    		if(lines[i].length()>5)
    		{
    			if(lines[i].matches(".*\\$[1-5]{1}00.*"))
    			{
    				currWeight = 50;
    				potentialCat = lines[i].substring(lines[i].indexOf("$")+5).trim();
    			}
    			else
        		{
        			potentialCat = lines[i];
        		}
    			if(slicedLines.contains(potentialCat))
    			{
    				int indexCat = slicedLines.indexOf(potentialCat);
    				weight.set(indexCat, weight.get(indexCat)+currWeight);
    			}
    			else
    			{
    				slicedLines.add(potentialCat);
    				weight.add(currWeight);
    			}
    		}
    	}
        
    	
    	Integer[] sortedWeights = weight.toArray(new Integer[0]);
    	Arrays.sort(sortedWeights);
    	String[] confirmedCats = new String[5];
    	for(int i=sortedWeights.length-1; i>=sortedWeights.length-5; i--)
    	{
    		for(int inner = 0; inner<weight.size(); inner++)
    		{
    			if(sortedWeights[i]==weight.get(inner))
    			{
    				weight.set(inner, -1);
    				confirmedCats[sortedWeights.length-i-1]= slicedLines.get(inner);
    				break;
    			}
    		}
    	}
    	
    	String categories = "";
    	for(int i=0; i<confirmedCats.length; i++)
    	{
    		categories+=confirmedCats[i]+DefaultPanel.categorySeparator+" ";
    	}
    	if(categories.contains("null"))
    	{
    		ExceptionHandler.getHandler().handleException(new InvalidFormatException("Could not calculate the most likely categories"));
    	}
    	return categories;
    	
    }
    /**
     * Class CreateListener the listener that is used to create new files
     * 
     * @version 3/2/2020
     * @author Phoenix
     */
    private class CreateListener implements ActionListener
    {
        /**
         * Method actionPerformed the method called to write the new file
         *
         * @param e unused action event
         */
        public void actionPerformed(ActionEvent e)
        {
        	
            String x = newName.getText();
            if(x.isEmpty())
            {
            	JOptionPane.showMessageDialog(new JFrame(), "Please enter new game set name", "Failed to create set", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            if(!x.contains(".txt")) x = x + ".txt";
            String path = UIChooser.getUIChooser().getDirectoryViaUI();
            if(path==null)
            {
            	return;
            }
            path+= System.getProperty("file.separator") + x;
            
                try
                {
                    File files = new File(path);
                    if(files.exists())
                    {
                    	int overwrite = JOptionPane.showConfirmDialog(new JFrame(), "This gameset seems to already exists. Overwrite?");
                    	if(overwrite != JOptionPane.YES_OPTION)
                    	{
                    		return;
                    	}
                    	else
                    	{
                    		int confirm = JOptionPane.showConfirmDialog(new JFrame(), "Continuing anyways will delete the previous file. \n Are you ABSOLUTELY sure you want to continue?");
                    		if(confirm != JOptionPane.YES_OPTION)
                        	{
                        		return;
                        	}
                    	}
                    }
                    FileWriter fw = new FileWriter(files);
                    
                    String lines = createTemplate();
                    fw.write(lines);
                    fw.close();
                    newName.setText("");
                    JOptionPane.showMessageDialog(new JFrame(), "The gameset named "+x+" has been successfully created. \n\n Find it at "+ path);
                }
                catch(Exception ex)
                {
                	ExceptionHandler.getHandler().handleException(ex);
                }
            

        }
        /**
         * Method createTemplate stepwise refined method to find the string to be written to the file
         *
         * @return the needed string
         */
        private String createTemplate()
        {
            String template = "";
            template = header;
            template+= "Category1" + DefaultPanel.categorySeparator;
            template+= " Category2" +DefaultPanel.categorySeparator;
            template+= " Category3" + DefaultPanel.categorySeparator;
            template+= " Category4" + DefaultPanel.categorySeparator;
            template+= " Category5" + DefaultPanel.categorySeparator + System.lineSeparator();
            template = template + "Questions:" + System.lineSeparator();
            for(int category=1; category<=5; category++)
            {
                template = template + "C" + category + "QA" + System.lineSeparator();
                for(int qa=1; qa<=10; qa++)
                {
                    if(qa%2==1)
                    {
                        template = template + "Question"+(5*(category-1)+(qa+1)/2) + System.lineSeparator();
                    }
                    else
                    {
                        template = template + "Answer"+(5*(category-1)+qa/2) + System.lineSeparator();
                    }
                }
            }
            template = template + "End Questions" + System.lineSeparator();
            template = template + System.lineSeparator();
            template = template + "Final Jeopardy Question" + System.lineSeparator();
            template = template + "Final Jeopardy Answer";
            
            return template;
        }
    }
    /**
     * Class Listener the listener that takes the user back to the previous panel
     */
    private class Listener implements ActionListener
    {
        
        public void actionPerformed(ActionEvent e)
        {
            DefaultPanel.getManager().toPanel("LoadPanel");
        }
    }
}

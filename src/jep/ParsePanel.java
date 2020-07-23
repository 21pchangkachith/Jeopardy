package jep;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.extractor.SlideShowExtractor;
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
	private static String setPath; 
	static{
		try {
			setPath = Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "jep" +  System.getProperty("file.separator")+"GameFiles" + System.getProperty("file.separator")+ "GameSets" + System.getProperty("file.separator");
			}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(new JFrame(), "Something went wrong.");
		}
		}
		
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
        setLayout(new BorderLayout());
        
        

        
        JButton backButton = createButton("Back", new Listener());
        JButton newButton = createButton("Create new set: ", new CreateListener());
        JButton parseButton = createButton("Import set", new ParseListener());
        parseButton.setPreferredSize(new Dimension(400, 100));
        
        JPanel buttonList  = new JPanel();
        buttonList.setOpaque(false);
        buttonList.setLayout(new GridLayout(1,5));
        buttonList.add(backButton);
        for(int i = 0; i<4; i++){
            JButton button = new JButton("");
            buttonList.add(button);
            button.setVisible(false);
        }
        add(buttonList, BorderLayout.NORTH);
        

        
        
        
        newName = new JTextField("example_name.txt");
        newName.setPreferredSize(new Dimension(200,50));
        
        JPanel createHolder = new JPanel(new GridBagLayout());
        createHolder.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        createHolder.add(newButton, c);
        
        c.gridx= GridBagConstraints.RELATIVE;
        createHolder.add(newName, c);
        c.gridy= 1;
        c.gridwidth = 2;
        c.gridx= 0;
        createHolder.add(parseButton, c);
        
        add(createHolder, BorderLayout.CENTER);
        
        
        

        setOpaque(false);
    }
    public JButton createButton(String text, ActionListener listen)
    {
        JButton button = new JButton(text);
        button.addActionListener(listen);
        button.setPreferredSize(new Dimension(200,100));
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
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
                fc.setCurrentDirectory(new File(Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "jep" + System.getProperty("file.separator")+ "GameFiles" + System.getProperty("file.separator")+ "Powerpoints" + System.getProperty("file.separator")));
            }
            catch(Exception ex)
            {
            	StringWriter sw = new StringWriter();
            	PrintWriter pw = new PrintWriter(sw);
            	ex.printStackTrace(pw);
                JOptionPane.showMessageDialog(new JFrame(), "Whoops, something went wrong. Record this log below, and someone will know what to do with it. \n\n"+ sw.toString(), "Failed to import set", JOptionPane.ERROR_MESSAGE);
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
                	String pptName = powerpoint.getName();
                	pptName = pptName.substring(0, pptName.lastIndexOf("."))+".txt";
                	extractor.close();
                	
                	try
                	{
	                    File parsedFile = new File(powerpoint.getParent()+pptName);
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
	                    JOptionPane.showMessageDialog(new JFrame(), "Reading from powerpoint successful.");
	                }
                	catch(Exception innerEx)
                	{
                		StringWriter sw = new StringWriter();
                    	PrintWriter pw = new PrintWriter(sw);
                    	innerEx.printStackTrace(pw);
                        JOptionPane.showMessageDialog(new JFrame(), "Whoops, something went wrong processing the ppt. Record this log below, and someone will know what to do with it. \n\n"+ sw.toString());
                	}
                	
                }
                catch(Exception ex)
                {
                	StringWriter sw = new StringWriter();
                	PrintWriter pw = new PrintWriter(sw);
                	ex.printStackTrace(pw);
                	ex.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(), "Whoops, something went wrong reading the ppt. Record this log below, and someone will know what to do with it. \n\n"+ sw.toString());
                }
            }
            
            
            
    	}
    }
    private String createSetFromPPT(String pptContents)
    {
    	String set = header;
    	set+= processCategories(pptContents)+System.lineSeparator();
    	set+="Questions:" + System.lineSeparator();
    	set+= processQuestions(pptContents);
    	
    	
    	return set;
    }
    private String processQuestions(String pptContents)
    {
    	String[] lines = pptContents.split("\\r?\\n");
    	String qna = "";
    	int matchCounter=0;
    	
    	for(int i=0; i<lines.length; i++)
    	{
    		String currentLine = lines[i].trim();
    		if(matchCounter<50&&currentLine.matches("(Q|R):.*"))
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
    				qna+= currentLine.substring(3).trim() + System.lineSeparator();
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
    			if(lines[i].trim().contains("Final Jeopardy"))
    			{
    				do
    				{
    					i++;
    				}
    				while(lines[i].length()<4);
    				qna+= lines[i].trim() + System.lineSeparator();
    				do
    				{
    					i++;
    				}
    				while(!lines[i].trim().contains("Final Jeopardy"));
    				do
    				{
    					i++;
    				}
    				while(lines[i].trim().length()<4);
    				qna+= lines[i].trim() + System.lineSeparator();
    			}
    		}
    	
    	}
    	return qna;
    }
    //it is not certain that there will only be 5 categories as seen in sem. final
    //therefore i must weigh how many times each potential category appears and submit the most likely
    //candidates as the categories.
    private String processCategories(String pptContents)
    {
    	ArrayList<String> slicedLines = new ArrayList<String>();
    	ArrayList<Integer> weight = new ArrayList<Integer>();
    	String[] lines = pptContents.split("\\r?\\n");
    	for(int i=0; i<lines.length; i++)
    	{
    		if(lines[i].length()>7&&lines[i].matches(".*\\$[1-5]{1}00.*"))
    		{
    			String potentialCat = lines[i].substring(lines[i].indexOf("$")+5).trim();
    			
    			if(slicedLines.contains(potentialCat))
    			{
    				int indexCat = slicedLines.indexOf(potentialCat);
    				weight.set(indexCat, weight.get(indexCat)+1);
    			}
    			else
    			{
    				slicedLines.add(potentialCat);
    				weight.add(1);
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
    		categories+=confirmedCats[i]+", ";
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
        	
    		JFileChooser fc = new JFileChooser();
    		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            try
            {
                //fc.setCurrentDirectory();
            }
            catch(Exception ex)
            {
            	handleException(ex);
            }
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                File directory = fc.getSelectedFile();
                try
                {
                	
                    String x = newName.getText();
                    if(x.isEmpty())
                    {
                    	JOptionPane.showMessageDialog(new JFrame(), "Please enter new game set name", "Failed to create set", JOptionPane.ERROR_MESSAGE);
                    	return;
                    }
                    if(!x.contains(".txt")) x = x + ".txt";
                    String path = directory.toPath() + System.getProperty("file.separator") + x;
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
                    JOptionPane.showMessageDialog(new JFrame(), "The gameset named "+x+" has been successfully created. \n\n Find it in "+ directory.toPath());
                }
                catch(Exception ex)
                {
                	handleException(ex);
                }
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
            template = template + "Categories:" + System.lineSeparator();
            template = template + "Category1, Category2, Category3, Category4, Category5," + System.lineSeparator();
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
            Driver.switchPanels("LoadPanel");
        }
    }
}

package jep;
import java.io.*;
/**
 * Class Category sets the five categories of the Jeopardy Game
 * @author Phoenix Changkachith
 * @author Siddhant Gupta
 * @version 3/2/2020
 * @period 2
 * @teacher Coglianese
 */
public class Category implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6543264476756608064L;
	/**
     * instance variables - Question object array 'questionSet' contains all
     * the category names. The String variable, 'name' sets the name of each
     * question in 'questionSet'
     */
    private Question[] questionSet;
    private String name;
    /**
     * Sets the value of 'name', and the length of 'questionSet', then the
     * default category subjects, will be the value of name, which is '???'
     * 
     * param n/a
     */
    public Category()
    {
        name = "???";
        questionSet = new Question[5];
        for(int i=0; i<5; i++)
        {
            questionSet[i]= new Question();
            questionSet[i].setSubject(name);
        }
    }
    /**
     * Sets the value of 'name', and the length of 'questionSet', then the
     * default category subjects, will be the value of name, which is '???'
     * 
     * param s - It is a string that is used to set the subject for
     * 'questionSet'
     */
    public Category(String s)
    {
        this();
        name=s;
        for(int i=0; i<5; i++)
        {
            questionSet[i].setSubject(name);
        }
    }
    /**
     * Sets the value of 'name', based on the parameter, 'n'
     * 
     * param n - It is a string that is used to set the value of 'name'
     * 
     * return n/a
     */
    public void setName(String n)
    {
        name=n;
    }
    /**
     * Returns the specific question object from the array 'questionSet'
     * based on the index given as the parameter.
     * 
     * param index - It is a string that is used to set the value of 'name'
     * 
     * return Question object from the 'questionSet' array
     */
    public Question getQuestion(int index)
    {
        return questionSet[index];
    }
    /**
     * Returns the String variable 'name'
     * 
     * param n/a
     * 
     * return String 'name'
     */
    public String getName()
    {
        return name;
    }

}

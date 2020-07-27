package jep;
import java.io.*;
/**
 * Class Question contains everything a question has for the Jeopardy Game.
 *
 * @author Siddhant Gupta
 * @author Phoenix Changkachith
 * @version 3/2/2020
 * @period 2
 * @teacher Mr. Coglianese
 */
public class Question implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7657057718013993071L;
	/**
     * These instance variables are components related to the questions and the type of
     * questions that are part of the Jeopardy Game.
     * 
     */
    private boolean isFinalJeopardy, isDailyDouble;
    private String ques, ans, subject;
    private int originalValue, currentValue;
    private int[] finalJeopardyValues;

    /**
     * Sets the values for the instance variables, with String 'subject' as
     * null and int values 'originalValue' and 'currentValue' as 500.
     * 
     */
    public Question()
    {
        subject= "null";
        isFinalJeopardy = false;
        isDailyDouble = false;
        ques= "Question";
        ans= "Answer";
        originalValue = 500;
        currentValue = 500;
        finalJeopardyValues = new int[6];
    }
    /**
     * Sets the values for the instance variables, with String 'subject' as
     * the parameter, String 'sub', and int values 'originalValue' and 
     * 'currentValue' as parameter int 'val'.
     * 
     * param String sub, int val - used to set values for String 'subject', 
     * int 'originalValue' and 'currentValue'
     * 
     */
    public Question(String sub, int val)
    {
        subject = sub;
        isFinalJeopardy = false;
        isDailyDouble = false;
        ques = "Question";
        ans = "Answer";
        originalValue = val;
        currentValue = val;
        finalJeopardyValues = new int[6];
    }
    
    /**
     * Sets the values for the int array 'finalJeopardyValues' with the
     * parameter, int array
     * 
     * param int[] newValues - used to set values for int array 
     * 'finalJeopardyValues'
     * 
     * return n/a
     * 
     */
    public void setFinalJeopardyValues(int[] newValues)
    {
        finalJeopardyValues=newValues;
    }
    /**
     * Using the parameter int 'index', it returns the specific value of int 
     * array 'finalJeopardyValues' 
     * 
     * param int[] newValues - used to get specific value of int array 
     * 'finalJeopardyValues'
     * 
     * return specific value of finalJeopardyValues based on the parameter,
     * int 'index'
     * 
     */
    public int getFinalJeopardyValue(int index)
    {
        return finalJeopardyValues[index];
    }
    
    /**
     * Returns String 'subject'
     * 
     * param n/a
     * 
     * return String 'subject'
     * 
     */
    public String getSubject()
    {
        return subject;
    }
    /**
     * Parameter String 's' sets the value of String 'subject'
     * 
     * param String 's' - sets the value of String 'subject'
     * 
     * return n/a
     * 
     */
    public void setSubject(String s)
    {
        subject=s;
    }
    /**
     * Returns value of String 'ques'
     * 
     * param n/a
     * 
     * return String 'ques'
     * 
     */
    public String getQuestion()
    {
        return ques;
    }
    
    /**
     * Returns value of String 'ans'
     * 
     * param n/a
     * 
     * return String 'ans'
     * 
     */
    public String getAnswer()
    {
        return ans;
    }
    /**
     * Returns value of int 'originalValue'
     * 
     * param n/a
     * 
     * return int 'originalValue'
     * 
     */
    public int getOriginalValue()
    {
        return originalValue;
    }
    /**
     * Returns value of int 'currentValue'
     * 
     * param n/a
     * 
     * return int 'currentValue'
     * 
     */
    public int getCurrentValue()
    {
        return currentValue;
    }
    /**
     * Parameter String 'q' sets value of String 'ques'
     * 
     * param String q - sets value of String 'ques'
     * 
     * return n/a
     * 
     */
    public void setQuestion(String q)
    {
        ques= q;
    }
    /**
     * Parameter String 'a' sets value of String 'ans'
     * 
     * param String a - sets value of String 'ans'
     * 
     * return n/a
     * 
     */
    public void setAnswer(String a)
    {
        ans = a;
    }
    /**
     * Parameter int 'val' sets value of int 'currentValue'
     * 
     * param int val - sets value of int 'currentValue'
     * 
     * return n/a
     * 
     */
    public void setCurrentValue(int val)
    {
        currentValue= val;
    }
    /**
     * Parameter int 'val' sets value of int 'originalValue'
     * 
     * param int val - sets value of int 'originalValue'
     * 
     * return n/a
     * 
     */
    public void setOriginalValue(int val)
    {
        originalValue = val;
    }
    /**
     * Parameter boolean 'b' sets value of boolean 'isDailyDouble'
     * 
     * param boolean b - sets value of String 'isDailyDouble'
     * 
     * return n/a
     * 
     */
    public void setDailyDouble(boolean b)
    {
        isDailyDouble = b;
    }
    /**
     * Returns boolean 'isDailyDouble'
     * 
     * param n/a
     * 
     * return boolean 'isDailyDouble'
     * 
     */
    public boolean isDailyDouble()
    {
        return isDailyDouble;
    }
    /**
     * Returns boolean 'isFinalJeopardy'
     * 
     * param n/a
     * 
     * return boolean 'isFinalJeopardy'
     * 
     */
    public boolean isFinalJeopardy()
    {
        return isFinalJeopardy;
    }
    /**
     * Parameter boolean 'b' sets value of boolean 'isFinalJeopardy'
     * 
     * param boolean b - sets value of String 'isFinalJeopardy'
     * 
     * return n/a
     * 
     */
    public void setFinalJeopardy(boolean b)
    {
        isFinalJeopardy = b;
    }
    /**
     * Returns true if either 'isDailyDouble' or 'isFinalJeopardy' is true,
     * or if their both true. Otherwise, it returns false.
     * 
     * param n/a
     * 
     * return true if one (or both) of the boolean objects is true, else it
     * returns false
     * 
     */
    public boolean isSpecial()
    {
        return isDailyDouble||isFinalJeopardy;
    }
    public String toString()
    {
    	return ques;
    }
}

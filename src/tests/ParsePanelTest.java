package tests;

import jep.ParsePanel;

public class ParsePanelTest {

	public ParsePanelTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		testCategories(args);
		testQuestions(args);
		
	}
	public static void testCategories(String[] args) {
		ParsePanel panel = new ParsePanel();
		String text = panel.processCategories("Hello world\n test \n test1 \n test2 \ntest3");
		System.out.println(text);
		
	}
	public static void testQuestions(String[] args) {
		ParsePanel panel = new ParsePanel();
		String text = "";
		for(int i=0; i<52; i++)
		{
			text+="test"+i+"\n";
		}
		text = panel.processQuestions(text);
		System.out.print(text+"DA");
	}

}

package jep.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class ExceptionHandler {
	private static final ExceptionHandler handler = new ExceptionHandler();
	private ExceptionHandler() {
		// TODO Auto-generated constructor stub
	}
	public static ExceptionHandler getHandler()
	{
		return handler;
	}
	public void handleException(Exception ex, String message)
	{
		ex.printStackTrace();
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
        JOptionPane.showMessageDialog(new JFrame(), message + sw.toString());
	}
	public void handleException(Exception ex)
	{
		handleException(ex, "Whoops, something went wrong. Record this log below, and someone will know what to do with it. \n\n");
	}

}

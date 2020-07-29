package jep.utilities;

import java.io.File;

import javax.swing.JFileChooser;

import jep.GamePanel;

public final class UIChooser {
	private static final UIChooser uIChooser = new UIChooser();
	private UIChooser() {
		// TODO Auto-generated constructor stub
	}
	public static UIChooser getUIChooser()
	{
		return uIChooser;
	}
	public String getDirectoryViaUI() {
		String path = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		try
		{
		    fc.setCurrentDirectory(new File(GamePanel.defaultPath));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			ExceptionHandler.getHandler().handleException(ex);
		}
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
		    File directory = fc.getSelectedFile();
		    path = directory.getPath();
		}
		return path;
	}
}


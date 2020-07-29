package jep.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class Editor{
	private static Editor editor= new Editor();
	private Editor() {}
	public static Editor getEditor()
	{
		return editor;
	}
	public void replaceValue(String value, String newValue, File file)
	{
    	try {
			String line = null;
			String store = "";
			
			
			FileReader fileReader = new FileReader(file);
			Scanner reader = new Scanner(fileReader);
			
			
			do {
				line = reader.nextLine();
				store = store + line + System.lineSeparator();
			} 
			while (!line.contains(value));
			
			store = store.substring(0, store.indexOf(value));
			store = store + newValue;
			store += line.substring(line.indexOf(value)+value.length()) + System.lineSeparator();
			
			while (reader.hasNext()) {
				line = reader.nextLine();
				store = store + line + System.lineSeparator();
			}
			fileReader.close();
			reader.close();
			FileWriter fw = new FileWriter(file);
			fw.write(store);
			fw.close();
			JOptionPane.showMessageDialog(new JFrame(), "Update successful");
		} catch (Exception ex) {
			ExceptionHandler.getHandler().handleException(ex);
		}
	}
}

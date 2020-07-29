package tests;

import java.io.File;

import jep.utilities.Editor;

public class EditorTest {

	public EditorTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args)
	{
		Editor edit = Editor.getEditor();
		edit.replaceValue("hello", "hellow", new File("/users/phoenixchangkachith/testfile.txt"));
	}

}

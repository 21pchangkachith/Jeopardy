package jep;

public class ParallelThread extends Thread {
	Object settingvalue;
	public ParallelThread() {
		settingvalue = null;
		// TODO Auto-generated constructor stub
	}
	public void run()
	{
		
	}
	public void setValue(Object obj)
	{
		if(obj instanceof String)
		{
			GamePanel.defaultPath = (String) obj;
		}
		else if(obj instanceof Integer)
		{
			
		}
	}

}

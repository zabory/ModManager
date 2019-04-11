package modManager;

public class modManager extends Thread{

	final static String CONFIG_FILE_PATH = "\\config\\modManager.cfg";
	
	modPanel panel;
	
	String message;

	public modManager(modPanel p) {
		panel = p;
		message = "";
	}
	
	public void run() {
		while(!message.equals("exit")) {
			
		}
	}
	
	public void updateModList() {
		
	}
	
	public void setMessage(String m) {
		message = m;
	}
	
	
	
}

package modManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class modManager extends Thread{

	final static String CONFIG_FILE_PATH = "config\\modManager\\modManager.cfg";
	final static String MOD_FILE_FOLDER = "mods";
	
	ArrayList<mod> mods;
	
	modPanel panel;
	
	String message;

	public modManager(modPanel p) {
		panel = p;
		message = "";
		
		mods = new ArrayList<mod>();
		
		File configFile = new File(CONFIG_FILE_PATH);
		File modFolder = new File(MOD_FILE_FOLDER);
		
		if(!modFolder.exists()) {
			panel.addToLog("Creating mods folder");
			modFolder.mkdir();
		}
		
		
		
		if(!configFile.exists()) {
			panel.addToLog("Creating config file");
			configFile.getParentFile().mkdirs();
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public void update() {
		for(mod x : mods) {
			panel.setModStatus(panel.getComponent(x.getName()), x.getName());
		}
	}
	
	public void run() {
		while(!message.equals("exit")) {
			if(message.contentEquals("updateModList")) {
				updateModList();
				message = "";
			}
		}
	}
	
	
	
	public void updateModList() {
		panel.addToLog("Updating mod list");
		
		File modsFile = new File(MOD_FILE_FOLDER);
		
		mods = new ArrayList<mod>();
		
		for(File x : modsFile.listFiles()) {
			mod current = new mod(x.getName());
			current.newMod();
			mods.add(current);
			panel.addModToList(x.getName());
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	public void setMessage(String m) {
		message = m;
	}
	
	
	
}

package modManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class modManager extends Thread{

	final static String CONFIG_FILE_PATH = "config\\modManager\\modManager.cfg";
	final static String MOD_FILE_NAME_PATH = "config\\modManager\\modNames.cfg";
	final static String MOD_FILE_URL_PATH = "config\\modManager\\modURLs.cfg";
	final static String MOD_FILE_FOLDER = "mods";
	
	ArrayList<mod> mods;
	
	modPanel panel;
	
	String message;

	public modManager(modPanel p) {
		panel = p;
		message = "";
		
		mods = new ArrayList<mod>();
		
		File modFolder = new File(MOD_FILE_FOLDER);
		File[] dataFiles = new File[3];
		
		dataFiles[0] = new File(CONFIG_FILE_PATH);
		dataFiles[1] = new File(MOD_FILE_NAME_PATH);
		dataFiles[2] = new File(MOD_FILE_URL_PATH);
		
		if(!modFolder.exists()) {
			modFolder.mkdir();
		}
		
		for(File x : dataFiles) {
			if(!x.exists()) {
				x.getParentFile().mkdirs();
				try {
					x.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void update() {
		
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
			mods.add(current);
			panel.addModToList(current.getName());
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	public void setMessage(String m) {
		message = m;
	}
	
	
	
}

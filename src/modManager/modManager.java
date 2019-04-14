package modManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class modManager extends Thread{

	final static String CONFIG_FILE_PATH = "config\\modManager\\modManager.cfg";
	final static String MOD_FILE_NAME_PATH = "config\\modManager\\modNames.cfg";
	final static String MOD_FILE_URL_PATH = "config\\modManager\\modURLs.cfg";
	final static String MOD_FILE_FOLDER = "mods";
	boolean free = true;
	
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
	
	public void checkForUpdates(){
		for(mod x : mods) {
			x.checkForUpdate();
		}
		
	}
	
	
	public void run() {
		while(!message.equals("exit")) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!message.equals("")) {
				System.out.println(message);
				free = false;
			}
			
			if(message.equals("updateModList")) {
				updateModList();
				message = "";
				free = true;
			}
			
			if(message.equals("checkForUpdates")) {
				System.out.println("got here");
				checkForUpdates();
				message = "";
				free = true;
			}
		}
	}
	
	public boolean free() {
		return free;
	}
	
	
	public void updateModList() {
		
		
		File modsFile = new File(MOD_FILE_FOLDER);
		
		mods = new ArrayList<mod>();
		
		for(File x : modsFile.listFiles()) {
			
			if(x.getName().contains("+")) {
				x.renameTo(new File(x.getPath().replace("+", "")));
			}
			
			mod current = new mod(x.getName(), this);
			
			mods.add(current);
			panel.addModToList(current.getName());
			if(current.getFileName().contains(".disabled")) {
				panel.setModStatus(current.getName(), 4);
			}
		}
		
		
		
	}
	
	public modPanel getPanel() {
		return panel;
	}
	
	
	public void enableMods(ArrayList<String> x) {
		for(String y : x) {
			if(getMod(y).getFileName().contains(".disabled")) {
				panel.addToLog("Enabling " + y);
			}
			getMod(y).rename(getMod(y).getFileName().replace(".disabled", ""));
			panel.setModStatus(y, -1);
		}
	}
	
	public void disableMods(ArrayList<String> x) {
		for(String y : x) {
			if(getMod(y).getFileName().contains(".disabled")) {
				
			}else {
				panel.addToLog("Disabling " + y);
				getMod(y).rename(getMod(y).getFileName() + ".disabled");
				panel.setModStatus(y, 4);
			}
			
		}
	}
	
	public mod getMod(String name) {
		
		for(mod x : mods) {
			if(x.getName().equals(name)) {
				return x;
			}
		}
		
		return null;
	}
	
	public void setMessage(String m) {
		message = m;
	}
	
	
	
}

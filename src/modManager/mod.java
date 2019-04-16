package modManager;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class mod {
	
	//This is the name of the mod without the version attached
	String modName;
	//This is the URL that the mod files can be found at
	String modURL;
	//This is the raw name of the file, with all versions attached
	String modFileName;
	//This is the file name of the most current version of the mod that has been found
	String recievedFileName;
	//This will determine if the mod is up to date or not 
	String downloadLink;
	boolean isUpToDate;
	int enabledState;
	
	boolean URLTestingDone;
	
	URLTester[] t;
	
	modManager m;
	
	final static String MOD_FILE_NAME_PATH = "config\\modManager\\modNames.cfg";
	final static String MOD_FILE_URL_PATH = "config\\modManager\\modURLs.cfg";
	
	
	public mod(String mFName, modManager m) {
		this.m = m;
		URLTestingDone = false;
		enabledState = -1;
		this.modFileName = mFName;
		mFName = mFName.replace(".disabled", "");
		modName = "";
		modURL = "";
		
		File fileNames = new File(MOD_FILE_NAME_PATH);
		File URLs = new File(MOD_FILE_URL_PATH);
		//search modFileNames.cfg for mod name
		try {
			String wholeFile = "";
			Scanner fileIn = new Scanner(fileNames);
			String input;
			while(fileIn.hasNextLine()) {
				input = fileIn.nextLine();
				wholeFile = wholeFile + input + "\n";
				if(input.contains(mFName)) {
					modName = input.replaceAll(mFName + ":","");
				}
			}
			fileIn.close();
			
			if(modName.equals("")) {
				findModName();
				PrintWriter out = new PrintWriter(MOD_FILE_NAME_PATH);
				out.println(wholeFile + mFName + ":" + modName);
				out.flush();
				out.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	
		
	}
	
	public int getEnabledState() {
		return enabledState;
	}
	
	public void rename(String name) {
		File f = new File("mods\\" + modFileName);
		f.renameTo(new File("mods\\" + name));
		modFileName = name;
		
	}
	
	public void checkForUpdate() {
		File URLs = new File(MOD_FILE_URL_PATH);
		String mFName = modFileName.replace(".disabled", "");
		//search for URL
				try {
					Scanner fileIn = new Scanner(URLs);
					String input;
					while(fileIn.hasNextLine()) {
						input = fileIn.nextLine();
						if(input.contains(mFName)) {
							modURL = input.replaceAll(mFName + ":","");
						}
					}
					fileIn.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				if(modURL.equals("")) {
					findURL();
				}
				
				if(!modURL.equals("")) {
				
				System.setProperty("http.agent", "Chrome");
					URL url;
					try {
						url = new URL(modURL);
						URLConnection conn = url.openConnection();
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					
						String inputLine = br.readLine();
			
						while ((inputLine = br.readLine()) != null) {
							
						
			        	
			        	if(inputLine.contains("<a class=\"overflow-tip twitch-link\" href=\"")) {
			        		downloadLink = inputLine.replace("<a class=\"overflow-tip twitch-link\" href=\"", "").replace("\"", "");
			        		for(int i = 0; i < 4; i++) {
			        			inputLine = br.readLine();
			        		}
			        		for(int i = 0; i < inputLine.length(); i++) {
			        			if(inputLine.subSequence(i, i + 9).equals("data-name")) {
			        				int stop = 0;
			        				for(int j = i + 9; j < inputLine.length(); j++) {
			        					if(inputLine.substring(j, j + 2).equals("\">")) {
			        						stop = j;
			        						break;
			        					}
			        				}
			        				recievedFileName = inputLine.substring(i + 11, stop);
			        				System.out.println(recievedFileName);
			        				int limit = 0;
									if(recievedFileName.length() < modFileName.length()) {
										limit = recievedFileName.length();
									}else {
										limit = modFileName.length();
									}
									
									boolean update = false;
									
									int modScore = 0;
									int updateScore = 0;
									
									int place = 0;
									
									for(int x = 0; x < modFileName.length(); x++) {
										char toTest = modFileName.charAt(x);
										if(toTest >= 48 && toTest <= 57) {
											modScore = (modScore * (int)Math.pow(10, place)) + Character.getNumericValue(toTest); 
										}
									}
									
									
									
									
									if(update) {
										m.getPanel().setModStatus(modName, 2);
									}else {
										m.getPanel().setModStatus(modName, 1);
									}
			        				break;
			        			}
			        		}
			                
			                
			                
			                break;
			        	}
			        }
						
						
						
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}else {
					m.getPanel().setModStatus(modName, 0);
				}
				
				m.getPanel().addProgress();
	}
	
	/**
	 * 
	 */
	private void findURL() {
		ArrayList<String> names = getPossibleNames(modName);
		t = new URLTester[names.size()];
		
		for(int x = 0; x < t.length; x++) {
			t[x] = new URLTester("https://minecraft.curseforge.com/projects/" + names.get(x) + "/files?filter-game-version=1738749986%3A628");
			t[x].start();
		}
		
		boolean done = false;
		while(!done) {
			done = true;
			for(Thread x : t) {
				if(x.isAlive()) {
					done = false;
					break;
				}
			}
		}
		
		URLTestingDone = true;
	}
	
	public String getFileName() {
		return modFileName;
	}
	public String getName() {
		return modName;
	}
	
	public boolean isURLTestingDone() {
		return URLTestingDone;
	}
	
	public static ArrayList<String> getPossibleNames(String name) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		String ogName;
		
		name = name.replace("'", "");
		name = name.replace(" ", "");
		
		ogName = name;
		
		for(int i = 1; i < name.length(); i++) {
			if(name.charAt(i) == '-' || name.charAt(i) == '_') {
				indexes.add(i);
				name = name.substring(0, i) + "$" + name.substring(i + 1);
			}else if(name.substring(i, i + 1).equals(name.substring(i, i + 1).toUpperCase()) && name.charAt(i - 1) != '$') {
					if(name.length() - i > 2) {
						if(!name.substring(i + 1, i + 2).equals(name.substring(i + 1, i + 2).toUpperCase())) {
							name = name.substring(0, i) + "$" + name.substring(i);
							indexes.add(i);
							i++;
					}
				}
			}
		}
		
		
				
		int[] testing = new int[indexes.size()];
		for(int i = 0; i < testing.length; i++) {
			testing[i] = 0;
		}
		
		int begin = testing.length;
		
		boolean oneTime = true;
		
		while(testing.length == begin && (begin != 0 || oneTime)) {
			String man = name;
			int offset = 0;
			for(int i = 0; i < testing.length; i++) {
				if(testing[i] == 0) {
					man = man.substring(0,indexes.get(i) + offset) + man.substring(indexes.get(i) + offset + 1, man.length());
					offset--;
				}else if(testing[i] == 1) {
					man = man.substring(0,indexes.get(i) + offset) + "-" + man.substring(indexes.get(i) + offset + 1, man.length());
				}else if(testing[i] == 2) {
					man = man.substring(0,indexes.get(i) + offset) + "_" + man.substring(indexes.get(i) + offset + 1, man.length());
				}
			}
			names.add(man);
			oneTime = false;
			if(begin != 0) {
				testing = iterate(testing);
			}
		}
		
		names.add(ogName + "-mod");
		
		if(ogName.toLowerCase().contains("world")) {
			names.add(ogName.substring(0,ogName.toLowerCase().indexOf("world") + 5) + "_" + ogName.substring(ogName.toLowerCase().indexOf("world") + 5, ogName.length()));
			names.add(ogName.substring(0,ogName.toLowerCase().indexOf("world") + 5) + "-" + ogName.substring(ogName.toLowerCase().indexOf("world") + 5, ogName.length()));
		}
		
		if(ogName.toLowerCase().contains("io")) {
			names.add(ogName.substring(0,ogName.toLowerCase().indexOf("io") + 1) + "_" + ogName.substring(ogName.toLowerCase().indexOf("io") + 1, ogName.length()));
			names.add(ogName.substring(0,ogName.toLowerCase().indexOf("io") + 1) + "-" + ogName.substring(ogName.toLowerCase().indexOf("io") + 1, ogName.length()));
		}
		
		
		
		return names;
	}
	
	
	/**
	 * This method takes the file name, goes to the 1st occurrence of a number, and sets modName equal to that value.
	 */
	private void findModName() {
		String modName = modFileName;
		int nameEnd = 0;
		
		/*
		 * Finds the first instance of a number in a mod file name, this will cut it off
		 */
		for(int i = 0; i < modName.length(); i++) {
			if((int)modName.substring(i, i + 1).charAt(0) >= 48 && (int)modName.substring(i, i + 1).charAt(0) <= 57) {
				nameEnd = i;
				break;
			}
		}
		//Makes modName everything up to numbers
		modName = modName.substring(0,nameEnd);
		//removes any a - or _ if its at the very end
		if(modName.charAt(modName.length() - 1) == '_' ||
				modName.charAt(modName.length() - 1) == '-') {
			modName = modName.substring(0,modName.length() - 1);
		}
		
		
		//Array of things to remove from mod name
		String[] remove = {"-MC" , "-mc", "-Mc", "-forge"};
		//removes from mod name if any of the things above are contained
		for(String x : remove) {
			if(modName.contains(x)) {
				modName = modName.replace(x, "");
			}
		}
		
		this.modName = modName;
		
	}

	/**
	 * Class to handle if a URL is valid or not
	 * @author bshabowski
	 *
	 */
	private class URLTester extends Thread{
		
		String URL;
		
		private URLTester(String URL){
			this.URL = URL;
		}
		
		public void run() {
			System.setProperty("http.agent", "Chrome");
			try {
				URL url = new URL(URL);
				URLConnection conn = url.openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				modURL = URL;
		        
		        
		        br.close();
		        
		        PrintWriter out;
		        String wholeFile = "";
		        Scanner input = new Scanner(new File(MOD_FILE_URL_PATH));
		        while(input.hasNextLine()) {
		        	wholeFile = wholeFile + input.nextLine() + "\n";
		        }
				input.close();
				
		        try {
					out = new PrintWriter(MOD_FILE_URL_PATH);
					out.println(wholeFile + modFileName.replace(".disabled", "") + ":" + modURL);
					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}
	
	public static int[] iterate(int[] list) {
		int total = 0;
		for(int x : list) {
			total += x;
		}
		if(total >= list.length * 2) {
			return new int[0];
		}
		
		list[0]++;
		
		if(list.length == 1 && list[0] > 2) {
			list = new int[0];
		}else if(list[0] > 2) {
			list[0] = 0;
			int[] newList = new int[list.length - 1];
			for(int i = 0; i < newList.length; i++) {
				newList[i] = list[i + 1];
			}
			newList = iterate(newList);
			
			for(int i = 0; i < list.length - 1; i++) {
				list[i + 1] = newList[i];
			}
			
		}
		
		
		return list;
	}
	
	
}

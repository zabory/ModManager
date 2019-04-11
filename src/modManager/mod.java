package modManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

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
	boolean isUpToDate;
	
	
	public mod(String modFileName) {
		this.modFileName = modFileName;
		//checkUpdate();
	}
	
	public void newMod() {
		findModName();
		findURL();
	}
	
	public void existingMod(String modName, String modURL) {
		this.modName = modName;
		this.modURL = modURL;
	}
	
	/**
	 * 
	 */
	private void findURL() {
		ArrayList<String> names = getPossibleNames(modName);
		//"https://minecraft.curseforge.com/projects/" + possibleURL.get(i) + "/files"
		URLTester[] t = new URLTester[names.size()];
		
		for(int x = 0; x < t.length; x++) {
			t[x] = new URLTester("https://minecraft.curseforge.com/projects/" + names.get(x) + "/files");
			t[x].run();
		}
		
		
		
		
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
		if(modName.charAt(modName.length()) == '_' ||
				modName.charAt(modName.length()) == '-') {
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
				
				String inputLine;
		        while ((inputLine = br.readLine()) != null) {
		        	if(inputLine.contains("data-id") && !inputLine.contains("nate icon")) {
		        		int place = 0;
		        		for(int x = 49; x < inputLine.length(); x++) {
		        			if(inputLine.substring(x, x + 1).equals("\"")) {
		        				place = x;
		        				break;
		        			}
		        		}
		                recievedFileName = inputLine.substring(48, place);
		                break;
		        	}
		        }
		        
		        br.close();
				
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
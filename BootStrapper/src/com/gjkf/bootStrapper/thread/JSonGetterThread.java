/*
 * Copyright (c) 2014 Davide Cossu.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses>.
 */

package com.gjkf.bootStrapper.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.gjkf.bootStrapper.Main;

public class JSonGetterThread extends Thread{

	public String latest = Main.launcherUrl + "latest.json";

	public URL url;
	
	public static String version;

	public JSonGetterThread(){
		this.setName("Bootstrapper latest JSon getter");
		this.setDaemon(true);
	}

	@Override
	public void run(){
		try{

			/*
			 * Reads from the given URL, it writes to the console everything it finds.
			 */

			url = new URL(latest);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String updateUrl;
			String inputLine;
			String[] values;
			while((inputLine = in.readLine()) != null){
				//debug System.out.println(inputLine);
				values = inputLine.split(": ");
				for(int i = 0; i < values.length; i++){
				//debug System.err.println(values[i] + " " + i);
					/*
					 * Gets the version
					 */
					if(values[i].length() == 8){
						version = values[i].substring(1, 6);
						System.out.println(version);
 					}
					
					/*
					 * Gets the url
					 */
					
					if(values[i].length() > 8 && values[i].startsWith("http", 1)){
						updateUrl = values[i].substring(1, values[i].length()-1);
						System.out.println(updateUrl);
					}
				}
			}
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Checks if the first version is bigger then the 2nd
	 * @param the next version
	 * @param the actual version
	 * @return true if the 1st is older than the 2nd
	 */
	public boolean isUpdated(String currVersion, String nextVersion){
		String[] nextChar;
		String[] currChar;
		
		nextChar = nextVersion.split("\\.");
		
		currChar = currVersion.split("\\.");

		/*
		 * Checks if the Unicode value for the current version is less/equals/greater than the next one
		 */
		if(currChar[0].compareTo(nextChar[0]) >= 0){
			if(currChar[1].compareTo(nextChar[1]) >= 0){
				if(currChar[2].compareTo(nextChar[2]) >= 0){
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
}
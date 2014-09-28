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

public class JSonGetterThread extends Thread{

	public String latest = "http://update.skcraft.com/quark/launcher/latest.json";

	public URL url;

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

			String version;
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
	public boolean isUpdated(String nextVersion, String currVersion){
		System.out.println(nextVersion);
		System.out.println(currVersion);
		
		String[] nextChar;
		String[] currChar;
		
		nextChar = nextVersion.split(" ");
		
		currChar = currVersion.split("\\.");
		
		System.err.println(nextChar.length);
		System.err.println(currChar.length);
		
		for(int j = 0; j < nextChar.length; j++){
			System.out.println(nextChar[j]);
		}
		
		for(int j = 0; j < currChar.length; j++){
			System.out.println(currChar[j]);
		}
		
		if(Integer.getInteger(nextChar[0]) <= Integer.getInteger(currChar[0])){
			return true;
		}else if(Integer.getInteger(nextChar[1]) <= Integer.getInteger(currChar[1])){
			return true;
		}else if(Integer.getInteger(nextChar[2]) <= Integer.getInteger(currChar[2])){
			return true;
		}else{
			return false;
		}
	}
	
}
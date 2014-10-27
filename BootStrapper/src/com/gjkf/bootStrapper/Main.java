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

package com.gjkf.bootStrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import com.gjkf.bootStrapper.launcherUtils.Downloader;
import com.gjkf.bootStrapper.launcherUtils.FileHandler;
import com.gjkf.bootStrapper.thread.JSonGetterThread;

public class Main{

	private static File launcherFolder;
	private static File configFile;

	private static JSonGetterThread thread;

	private static boolean isUpdated = false;

	private static String nextVersion, currVersion, folderName;
	private static String folderPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

	//public static String launcherUrl = "http://update.skcraft.com/quark/launcher/versions/";
	//public static String updateUrl = "http://update.skcraft.com/quark/launcher/latest.json";

	private static String launcherUrl, updateUrl;
	public static String[] defaultValues, inputLines;

	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;

	@SuppressWarnings("static-access")
	public static void main(String[] args){

		defaultValues = new String[2];
		
		inputLines = new String[100];
		
		defaultValues[0] = "Update Url: used to update the launcher, use a .json as file, like so 'http://testUrl.com/launcher/latest.json' == ";
		defaultValues[1] = "Launcher Url: used to download the launcher == ";

		folderName = folderPath;

		System.out.println(folderPath);

		//launcherFolder = new File(folderPath.substring(0, folderPath.length()-1));

		//		folderName = folderPath;

		launcherFolder = new File(folderName + "launcher/");
		configFile = new File(folderPath + "/configFile.txt");

		/*
		 * Checks if the config file exists
		 */
		
		if(!configFile.exists()){

			FileHandler fileHandler = new FileHandler();

			for(int i = 0; i<defaultValues.length; i++)
			fileHandler.initFile(configFile, defaultValues[i]);
			
			reader = fileHandler.initReader(configFile);

		}
		
		/*
		 * Writes all the lines into an array
		 */
		
		try{
			while(reader.readLine() != null){
				int i = 0;
				
				inputLines[i] = reader.readLine();
				
				System.err.println(reader.readLine());
				
				i++;
			}
			
			for(int j = 0; j<inputLines.length; j++){
				System.out.println("Array: " + inputLines[j]);
			}

			updateUrl = inputLines[0].split("==")[0];
			launcherUrl = inputLines[1].split("==")[1];
			
		}catch(IOException e){
			System.err.format("IOException: %s%n", e);
		}
		
		System.err.println(updateUrl);
		System.err.println(launcherUrl);
		
		/*
		 * Checks if there's already the launcher folder. If not then it creates it.
		 */

		if(!launcherFolder.exists()){

			if(launcherFolder.mkdir()){
				System.out.println("Succesfully created folder");
			}else{
				System.out.println("Failed while creating the folder");
			}

		}
		
		thread = new JSonGetterThread(updateUrl);
		thread.run();

		/*
		 * Downloads the launcher in case it is not already there and checks if it's updated, if not it downloads the newest.
		 */

		if(launcherFolder.listFiles() == null){

			try{
				if(launcherUrl.endsWith("/")){
					Downloader.download(launcherUrl + thread.version + ".jar.pack", launcherFolder);
				}else{
					launcherUrl = launcherUrl + "/";
					Downloader.download(launcherUrl + thread.version + ".jar.pack", launcherFolder);
				}
			}catch(IOException e){

			}

		}else{

			/*
			 * If the folder's empty it checks everything
			 */

			if(launcherFolder.listFiles() == null){
				currVersion = launcherFolder.listFiles()[0].getName().substring(0, launcherFolder.listFiles()[0].getName().length() - 9);
				nextVersion = JSonGetterThread.version;

				launcherFolder.listFiles()[0].delete();

				System.out.println(launcherFolder.listFiles()[0].getName().substring(0, launcherFolder.listFiles()[0].getName().length() - 9));

				System.out.println("CurrVersion: " + currVersion + " " + "NextVersion: " + nextVersion);

				isUpdated = thread.isUpdated(currVersion, nextVersion);
				System.out.println("Is Updated: " + isUpdated);

			}

			if(!isUpdated){
				try{
					Downloader.download(launcherUrl + thread.version + ".jar.pack", launcherFolder);
				}catch(IOException e){}
			}

		}

	}

}
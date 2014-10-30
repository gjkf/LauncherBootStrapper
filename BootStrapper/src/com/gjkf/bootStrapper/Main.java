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

	private static File launcherFolder, configFile;

	private static JSonGetterThread thread;

	private static boolean isUpdated = false;

	private static String nextVersion, currVersion, folderName;
	private static String folderPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private static String configPath;
	//public static String launcherUrl = "http://update.skcraft.com/quark/launcher/versions/";
	//public static String updateUrl = "http://update.skcraft.com/quark/launcher/latest.json";

	private static String launcherUrl, updateUrl;

	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;

	@SuppressWarnings("static-access")
	public static void main(String[] args){

		//ClassLoader loader = Main.class.getClassLoader();
		//configPath = loader.getResource("").toString().substring(5);
		
		configPath = folderPath;
		
		String[] defaultValues;

		FileHandler fileHandler = new FileHandler();

		defaultValues = new String[2];

		defaultValues[0] = "Update Url (e.g. 'http://testUrl.com/launcher/latest.json') == ";
		defaultValues[1] = "Launcher Url == ";

		if(folderName == null)
			folderName = folderPath;

		System.out.println(configPath);

		launcherFolder = new File(folderName + "launcher/");
		configFile = new File(configPath + "/configFile.txt");

		/*
		 * Checks if the config file exists
		 */

		if(!configFile.exists()){

			try{

				writer = fileHandler.initWriter(configFile);
				//reader = fileHandler.initReader(configFile);

				writer.write("### Config File for Boostrapper: set the update URL (where it checks if the launcher is Updated) and the launcher URL (where the launcher is downloaded) \n");
				writer.write(defaultValues[0] + "\n");
				writer.write(defaultValues[1] + "\n");

			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					writer.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}

		}

		try{
			reader = fileHandler.initReader(configFile);

			while(reader.readLine() != null){

				try{
					reader = fileHandler.initReader(configFile);

					String readLine;

					while((readLine = reader.readLine()) != null){
						int index = 0;

						//String readLine = reader.readLine();

						System.out.println("ReadLine: " + readLine);

						//System.out.println("Ipotetic updateUrl: " + readLine.substring(readLine.split("==")[0].length() + 3));

						System.out.println("Index: " + index);

						if(readLine.startsWith("Launcher")){

							if(readLine.contains(defaultValues[1]) && !readLine.endsWith(".json")){
								launcherUrl = readLine.substring(readLine.split("==")[0].length() + 3);
								System.out.println("LauncherUrl: " + launcherUrl + " Length " + launcherUrl.length());
							}

						}else if(readLine.startsWith("Update Url")){

							if(readLine.contains(defaultValues[0]) && readLine.endsWith(".json")){
								updateUrl = readLine.substring(readLine.split("==")[0].length() + 3);
								System.out.println("UpdateUrl: " + updateUrl + " Length " + updateUrl.length());
							}

						}

						index ++;
						
					}

				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}catch(IOException e1){
			e1.printStackTrace();
		}


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

		if(updateUrl != null && launcherUrl != null){

			thread = new JSonGetterThread(updateUrl);
			thread.run();

		}

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
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.gjkf.bootStrapper.launcherUtils.Downloader;
import com.gjkf.bootStrapper.thread.JSonGetterThread;

public class Main{

	private static File launcherFolder;

	private static JSonGetterThread thread;

	private static boolean isUpdated = false;

	private static String nextVersion, currVersion;
	private static String folderPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

	//public static String updateUrl = "http://update.skcraft.com/quark/launcher/latest.json";
	//public static String launcherUrl = "http://update.skcraft.com/quark/launcher/versions/";

	private static String launcherUrl, updateUrl;

	@SuppressWarnings({ "static-access", "resource" })
	public static void main(String[] args){

		String[] defaultValues;

		defaultValues = new String[2];

		defaultValues[0] = "Update Url (e.g. 'http://testUrl.com/launcher/latest.json') == ";
		defaultValues[1] = "Launcher Url == ";

		System.out.println("Folder Path: " + folderPath);

	    InputStream is = Main.class.getResourceAsStream("/configurationFile/configFile.txt");
		
	    Scanner scanner = new Scanner(is);
	    
		launcherFolder = new File(folderPath + "launcher/");

		/*
		 * This reads the resource file looking for urls
		 */
		
		while(scanner.hasNextLine()){
			
			String readLine = scanner.nextLine();
			
			System.err.println("ReadLine: " + readLine);
			
			if(readLine.contains(".json")){

				if(readLine.contains(defaultValues[0])){
					updateUrl = readLine.substring(readLine.split("==")[0].length() + 3);
					System.out.println("UpdateUrl: " + updateUrl);
				}

			}
			
			if(readLine.startsWith("Launcher Url")){

				if(readLine.contains(defaultValues[1])){
					launcherUrl = readLine.substring(readLine.split("==")[0].length() + 3);
					System.out.println("LauncherUrl: " + launcherUrl);
				}

			}
			
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
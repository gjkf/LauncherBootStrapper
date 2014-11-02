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

	private static String nextVersion, currVersion, launcherUrl, updateUrl, launcherName, launcherFolderName;
	private static String folderPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

	/*
 		http://update.skcraft.com/quark/launcher/latest.json
		http://update.skcraft.com/quark/launcher/versions/4.2.2.jar.pack
	 */

	@SuppressWarnings("resource")
	public static void main(String[] args){

		folderPath = System.getProperty("user.home") + "/";

		/*
		if(os.indexOf("win") >= 0){

			folderPath = "%APPDATA%";

		}else if(os.indexOf("mac") >= 0){

			folderPath = "~/";

		}else if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0){

			folderPath = "~/";

		}else{
			throw new Exception("Your OS is not supported");
		}*/

		String[] defaultValues;

		defaultValues = new String[4];

		defaultValues[0] = "Update Url (Where the program checks if the launcher is updated: e.g. 'http://testUrl.com/launcher/latest.json') ==";
		defaultValues[1] = "Launcher Url (Where the program should download the launcher) ==";
		defaultValues[2] = "Launcher Name (The name of the downloaded launcher, with extension) ==";
		defaultValues[3] = "Launcher Folder Name (The name of the folder that it's created, where the launcher will be downloaded) ==";

		System.out.println("Folder Path: " + folderPath);

		InputStream is = Main.class.getResourceAsStream("/configurationFile/configFile.txt");

		Scanner scanner = new Scanner(is);

		if(launcherFolderName != null)
			launcherFolder = new File(folderPath + launcherFolderName + "/");

		/*
		 * This reads the resource file looking for stuff
		 */

		while(scanner.hasNextLine()){

			String readLine = scanner.nextLine();

			System.err.println("ReadLine: " + readLine);

			if(readLine.contains(".json") && readLine.startsWith("Update Url")){

				if(readLine.contains(defaultValues[0])){
					updateUrl = readLine.substring(readLine.split("==")[0].length()).substring(2);
					System.out.println("UpdateUrl: " + updateUrl);
				}

			}

			if(readLine.startsWith("Launcher Url")){

				if(readLine.contains(defaultValues[1])){
					launcherUrl = readLine.substring(readLine.split("==")[0].length()).substring(3);
					System.out.println("LauncherUrl: " + launcherUrl);
				}

			}

			if(readLine.startsWith("Launcher Name")){

				if(readLine.contains(defaultValues[2])){
					launcherName = readLine.substring(readLine.split("==")[0].length()).substring(3);
					System.out.println("LauncherName: " + launcherName);
				}

			}

			if(readLine.startsWith("Launcher Folder Name")){

				if(readLine.contains(defaultValues[3])){
					launcherFolderName = readLine.substring(readLine.split("==")[0].length()).substring(3);

					if(launcherFolderName != null || !launcherFolderName.equals(""))
						launcherFolder = new File(folderPath + launcherFolderName + "/");

					System.out.println("LauncherFolderName: " + launcherFolderName);
				}

			}

		}

		/*
		 * Checks if there's already the launcher folder. If not then it creates it.
		 */

		if(launcherFolder != null){
			if(!launcherFolder.exists()){

				try{
					launcherFolder.mkdir();
				}catch(Exception e){
					e.printStackTrace();
				}

			}
		}

		if(updateUrl != null && launcherUrl != null){

			thread = new JSonGetterThread(updateUrl, launcherUrl);
			thread.run();

		}

		/*
		 * Downloads the launcher in case it is not already there and checks if it's updated, if not it downloads the newest.
		 */

		if((launcherFolder.listFiles() == null)){

			try{
				if(launcherUrl.endsWith("/")){
					Downloader.download(launcherUrl, launcherFolder, launcherName);
				}else{
					launcherUrl = launcherUrl + "/";
					Downloader.download(launcherUrl, launcherFolder, launcherName);
				}
			}catch(IOException e){

			}

		}else{

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
					Downloader.download(launcherUrl, launcherFolder, launcherName);
				}catch(IOException e){}
			}

		}
	}


}
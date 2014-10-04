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

import com.gjkf.bootStrapper.launcherUtils.Downloader;
import com.gjkf.bootStrapper.thread.JSonGetterThread;

public class Main{

	public static File launcherFolder;

	public static JSonGetterThread thread;

	public static boolean isUpdated = false;

	public static String nextVersion, currVersion;
	public static String folderPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "launcher/";
	public static String launcherUrl = "http://update.skcraft.com/quark/launcher/versions/";

	public static String folderName;
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){

		System.out.println(folderPath);

		thread = new JSonGetterThread();
		thread.run();

		//launcherFolder = new File(folderPath.substring(0, folderPath.length()-1));

		folderName = folderPath.split("/launcher")[0];
		
		launcherFolder = new File(folderName);

		System.out.println(launcherFolder.getPath());

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

		/*
		 * Downloads the launcher in case it is not already there and checks if it's updated, if not it downloads the newest.
		 */

		if(launcherFolder.listFiles() == null){

			try{
				Downloader.download(launcherUrl + thread.version + ".jar.pack");
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
					Downloader.download(launcherUrl + thread.version + ".jar.pack");
				}catch(IOException e) {
				}
			}

		}

	}

}
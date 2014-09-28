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

import com.gjkf.bootStrapper.launcher.Downloader;
import com.gjkf.bootStrapper.thread.JSonGetterThread;

public class Main{

	public static File launcherFolder;
	public static JSonGetterThread thread;
	
	public static boolean isUpdated = false;
	
	public static String nextVersion, currVersion;
	public static String folderPath = "/Users/Davide/Desktop/launcher/";
	public static String launcherUrl = "http://update.skcraft.com/quark/launcher/versions/";
	
	public static void main(String[] args){
		
		nextVersion = "7.10.14";
		currVersion = "3.22.10";
		
		launcherFolder = new File(folderPath.substring(0, folderPath.length()-1));
		
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
		
		thread = new JSonGetterThread();
		thread.run();
		
		/*
		 * Downloads the launcher in case it is not already there
		 */
		
		if(launcherFolder.list() == null){
			System.out.println("Test");
		}else{
			try{
				Downloader.download(launcherUrl + "4.2.2.jar.pack");
			}catch(IOException e){
			}
		}
		
		isUpdated = thread.isUpdated(currVersion, nextVersion);
		System.out.println("Is Updated: " + isUpdated);
		
	}
	
}
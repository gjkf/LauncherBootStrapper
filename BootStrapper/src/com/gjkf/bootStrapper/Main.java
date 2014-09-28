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

import com.gjkf.bootStrapper.thread.JSonGetterThread;

public class Main{

	public static File launcherFolder;
	public static JSonGetterThread thread;
	
	public static boolean isUpdated;
	
	public static String nextVersion, currVersion;
	
	public static void main(String[] args){
		
		/*
		 * Checks if there's already the launcher folder. If not then it creates it.
		 */
		
		/*if(!launcherFolder.exists()){
			if(launcherFolder.mkdir()){
				System.out.println("Succesfully created folder");
			}else{
				System.out.println("Failed while creating the folder");
			}
		}*/
		
		nextVersion = "4 2 1";
		currVersion = "4.3.1";
		
		thread = new JSonGetterThread();
		thread.run();
		
		isUpdated = thread.isUpdated(nextVersion, currVersion);
		System.out.println(isUpdated);
		
	}
	
}
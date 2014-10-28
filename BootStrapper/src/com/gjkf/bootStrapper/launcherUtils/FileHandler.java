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

package com.gjkf.bootStrapper.launcherUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler{

	private BufferedWriter writer = null;
	private BufferedReader reader = null;

	public void writeToFile(File file, String string){

		try{
			initWriter(file);

			writer.write(string + "\n");
		}catch (IOException e){
			System.err.format("IOException: %s%n", e);
		}

	}

	public BufferedWriter initWriter(File file){
		
		try{
			writer = new BufferedWriter(new FileWriter(file));
		}catch (IOException e){
			System.err.format("IOException: %s%n", e);
		}
		return writer;
	}
	
	public BufferedReader initReader(File file){

		try{
			reader = new BufferedReader(new FileReader(file));
		}catch(IOException e){
			System.err.format("IOException: %s%n", e);
		}

		return reader;		
	}

}
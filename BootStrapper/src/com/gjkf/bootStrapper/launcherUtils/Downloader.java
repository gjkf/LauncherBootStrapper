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

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.gjkf.bootStrapper.Main;
import com.gjkf.bootStrapper.thread.JSonGetterThread;

public class Downloader{

	public static String name = JSonGetterThread.version + ".jar.pack";
	public static URL url;
	
	@SuppressWarnings("resource")
	public static void download(String link) throws IOException{
		url = new URL(link);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(Main.folderPath + name);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		
		System.out.println("Succesfully downloaded file");
	}
	
}
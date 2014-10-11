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

package com.gjkf.bootStrapper.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class View{

	public static Display display;
	private static Shell shell;

	private static Text pathField, nameField, updateUrlField, launcherUrlField;
	
	private static Button doneButton;
	
	private static CLabel nameLabel, pathLabel, updateUrlLabel, launcherUrlLabel;
	
	private static String path, name, updateUrl, launcherUrl;
	
	public static void init(){

		shell = new Shell(display);
		shell.setSize(500, 300);
		shell.setText("Path Selector");
		shell.setLayout(null);

		pathField = new Text(shell, SWT.SHADOW_IN);
		pathField.setBounds(20, 30, 250, 30);
		
		pathLabel = new CLabel(shell, SWT.CENTER);
		pathLabel.setBounds(300, 30, 200, 30);
		pathLabel.setText("Path of the Folder");
		
		nameField = new Text(shell, SWT.SHADOW_IN);
		nameField.setBounds(20, 70, 250, 30);

		nameLabel = new CLabel(shell, SWT.CENTER);
		nameLabel.setBounds(300, 70, 200, 30);
		nameLabel.setText("Name of the Folder");
		
		updateUrlField = new Text(shell, SWT.SHADOW_IN);
		updateUrlField.setBounds(20, 110, 250, 30);
		
		updateUrlLabel = new CLabel(shell, SWT.CENTER);
		updateUrlLabel.setBounds(300, 110, 200, 30);
		updateUrlLabel.setText("Update Url");
		
		launcherUrlField = new Text(shell, SWT.SHADOW_IN);
		launcherUrlField.setBounds(20, 150, 250, 30);
		
		launcherUrlLabel = new CLabel(shell, SWT.CENTER);
		launcherUrlLabel.setBounds(300, 150, 200, 30);
		launcherUrlLabel.setText("Launcher Download Url");
		
		doneButton = new Button(shell, SWT.PUSH);
		doneButton.setBounds(150, 200, 200, 30);
		doneButton.setText("Done");

		listeners();

		center();

		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static String getPath(){
		return path;
	}
	
	public static String getName(){
		return name;
	}
	
	public static String getUpdateUrl(){
		return updateUrl;
	}
	
	public static String getLauncherUrl(){
		return launcherUrl;
	}
	
	/*
	 * Listeners from SWT
	 */

	public static void listeners(){
		doneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e){
				path = pathField.getText();
				name = nameField.getText();
				updateUrl = updateUrlField.getText();
				launcherUrl = launcherUrlField.getText();
				
				shell.close();
				display.close();
			}
		});
	}
	
	/*
	 * Puts the shell at the center of the screen, just aestethical
	 */

	private static void center(){
		org.eclipse.swt.graphics.Rectangle bds = shell.getDisplay().getBounds();
		org.eclipse.swt.graphics.Point p = shell.getSize();
		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;
		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

}
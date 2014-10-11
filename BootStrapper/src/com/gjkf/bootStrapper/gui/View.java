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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class View{

	private static Display display;
	private static Shell shell;

	private static Text pathField;
	private static Text nameField;
	private static Button doneButton;
	
	private static String path, name;

	private static boolean isDone = false;
	
	public static void init(){
		
		display = new Display();

		shell = new Shell(display);
		shell.setSize(500, 300);
		shell.setText("Path Selector");
		shell.setLayout(null);

		pathField = new Text(shell, SWT.SHADOW_IN);
		pathField.setBounds(20, 30, 250, 30);
		
		nameField = new Text(shell, SWT.SHADOW_IN);
		nameField.setBounds(20, 70, 250, 30);

		doneButton = new Button(shell, SWT.PUSH);
		doneButton.setBounds(15, 110, 100, 30);
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
	
	public static boolean isDone(){
		return isDone;
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
				System.out.println(getPath());
				System.out.println(getName());
				isDone = true;
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
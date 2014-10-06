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

import com.gjkf.bootStrapper.Main;

public class View{

	public static Display display;
	public static Shell shell;

	public static Text textField;
	public static Button doneButton;

	public static void init(){
		
		display = new Display();

		shell = new Shell(display);
		shell.setSize(500, 300);
		shell.setText("Path Selector");
		shell.setLayout(null);

		textField = new Text(shell, SWT.SHADOW_ETCHED_IN);
		textField.setBounds(20, 30, 250, 30);

		doneButton = new Button(shell, SWT.PUSH);
		doneButton.setBounds(15, 60, 100, 30);
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
	
	/*
	 * Listeners from SWT
	 */

	public static void listeners(){
		doneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e){
				Main.folderPath = textField.getText();
				System.out.println(textField.getText());
				display.close();
				display.dispose();
			}
		});
	}

	private static void center(){
		org.eclipse.swt.graphics.Rectangle bds = shell.getDisplay().getBounds();
		org.eclipse.swt.graphics.Point p = shell.getSize();
		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;
		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

}
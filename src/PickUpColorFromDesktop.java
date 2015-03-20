/*
 * @(#) PickUpColorFromDesktop.java
 *
 * Copyright (C) 2002-2015 Satoshi Ogata <satosystems@gmail.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class PickUpColorFromDesktop implements MouseListener, ClipboardOwner {
	static BufferedImage image;
	static Clipboard clipboard;
	
	public static void main(String[] args) throws Exception {
		PickUpColorFromDesktop app = new PickUpColorFromDesktop();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		clipboard = toolkit.getSystemClipboard();
		
		Robot robo = new Robot();
		
		Dimension screen = toolkit.getScreenSize();
		Rectangle rect = new Rectangle(0, 0, screen.width, screen.height);
		image = robo.createScreenCapture(rect);
		
		ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel(icon);
		JWindow w = new JWindow();
		w.addMouseListener(app);
		w.getContentPane().add(label);
		w.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		w.pack();
		w.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void lostOwnership(Clipboard clipboard, Transferable contents) {}
	public void mousePressed(MouseEvent event) {
		Color c = new Color(image.getRGB(event.getX(), event.getY()));
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		String colorString = null;
		if ((event.getModifiers() & MouseEvent.BUTTON1_MASK) ==
				MouseEvent.BUTTON1_MASK) {
			colorString = "#";
			
			if (r < 16) {
				colorString += "0";
			}
			colorString += Integer.toHexString(r);
			if (g < 16) {
				colorString += "0";
			}
			colorString += Integer.toHexString(g);
			if (b < 16) {
				colorString += "0";
			}
			colorString += Integer.toHexString(b);
		} else {
			colorString = "r=" + r + ",g=" + g + ",b=" + b;
		}
		clipboard.setContents(new StringSelection(colorString), this);
		
		System.exit(0);
	}
}

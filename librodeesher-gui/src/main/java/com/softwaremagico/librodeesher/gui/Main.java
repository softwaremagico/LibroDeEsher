package com.softwaremagico.librodeesher.gui;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.log.EsherLog;
import com.softwaremagico.persistence.HibernateInitializator;

public class Main {

	/**
	 * Creates a new instance of Main
	 */
	public Main() {
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		String soName = System.getProperty("os.name");
		try {
			modifySplashString();
			if (soName.contains("windows") || soName.contains("Windows")) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
				} catch (UnsupportedLookAndFeelException ex) {
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException ex1) {
					}
				}
			} else if (soName.contains("Linux") || soName.contains("linux")) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}
			} else {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}
			}
		} catch (Exception ex) {
			EsherLog.errorMessage(Main.class.getName(), ex);
		}
		try {
			new Controller();
			//Check configurations.
			if(Config.isMagicDisabled()){
				MessageManager.warningMessage(Main.class.getName(), "Recuerda: la magia está desactivada.", "Opciones");
			}
		} catch (Exception ex) {
			MessageManager.showErrorInformation(Main.class.getName(), ex);
		}
	}

	private static void modifySplashString() {
		final SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash != null) {
			Graphics2D g = splash.createGraphics();
			if (g != null) {
				renderSplashFrame(g, "Database");
				splash.update();
				// Force database creation
				HibernateInitializator.getSessionFactory();
				renderSplashFrame(g, "GUI");
				splash.update();

				// End it
				splash.close();
			}
		}
	}

	private static void renderSplashFrame(Graphics2D g, String module) {
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(120, 140, 200, 40);
		g.setPaintMode();
		g.setColor(Color.BLACK);
		g.drawString("Loading " + module + "...", 120, 150);
	}
}

package com.softwaremagico.files;
/*
 * #%L
 * KendoTournamentGenerator
 * %%
 * Copyright (C) 2008 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.softwaremagico.log.EsherLog;

public class MessageManager {

	private static final int LINE = 50;

	public static void basicErrorMessage(String className, String text, String title) {
		EsherLog.severe(className, text);
		showGraphicMessage(text, title, JOptionPane.ERROR_MESSAGE);
	}

	public static void errorMessage(String className, Throwable throwable) {
		String error = getStackTrace(throwable);
		basicErrorMessage(className, error, "Error");
		throwable.printStackTrace();
	}

	public static void infoMessage(String className, String text, String title) {
		showGraphicMessage(text, title, JOptionPane.INFORMATION_MESSAGE);
		EsherLog.info(className, "(" + title + ") " + text);
	}

	public static void warningMessage(String className, String text, String title) {
		showGraphicMessage(text, title, JOptionPane.WARNING_MESSAGE);
		EsherLog.warning(className, "(" + title + ") " + text);
	}

	public static void customMessage(String className, String text, String title, int option) {
		showGraphicMessage(text, title, option);
		EsherLog.info(className, "(" + title + ") " + text);
	}

	public static void showGraphicMessage(String text, String title, int option) {
		int i = 0, caracteres = 0;
		try {
			String texto[] = text.split(" ");
			text = "";
			while (i < texto.length) {
				text += texto[i] + " ";
				caracteres += texto[i].length();
				if (caracteres > LINE) {
					text = text.trim() + "\n";
					caracteres = 0;
				}
				i++;
			}

			JFrame frame = null;

			text = text.replaceAll("\t", "  ");

			JOptionPane.showMessageDialog(frame, text, title, option);
		} catch (NullPointerException npe) {
		}
	}

	public static boolean questionMessage(String text, String title) {
		JFrame frame = null;
		int n = JOptionPane.showConfirmDialog(frame, text, title, JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			return true;
		} else if (n == JOptionPane.NO_OPTION) {
			return false;
		} else {
			return false;
		}
	}

	public static int questionMessage(String text, String title, Object[] options) {
		int n = JOptionPane.showOptionDialog(null, text, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
		return n;
	}

	private static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}

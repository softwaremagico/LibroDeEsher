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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.files.MyFile;
import com.softwaremagico.files.Version;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.log.EsherLog;

public class AboutWindow extends BaseFrame {
	private static final long serialVersionUID = -987975681639493971L;
	private final static String README_FILE = "Readme.txt";

	protected AboutWindow() {
		defineWindow(700, 400);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		String path = "./" + README_FILE;

		URL url = AboutWindow.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String jarPath = url.toExternalForm();
		jarPath = jarPath.substring(jarPath.indexOf("/"),
				jarPath.lastIndexOf("/"));

		File file = new File(path);
		if (!file.exists()) {
			file = new File(jarPath + File.separator + README_FILE);
			if (file.exists()) {
				path = jarPath + File.separator + README_FILE;
			} else {
				path = jarPath + File.separator + ".." + File.separator
						+ README_FILE;
			}
		}

		try {
			textArea.setText(MyFile.readTextFile(path, StandardCharsets.UTF_8,
					false));
		} catch (InvalidPathException ipe) {
			EsherLog.errorMessage(this.getClass().getName(), ipe);
		}
		textArea.setCaretPosition(0);

		JScrollPane textScrollPanel = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		add(textScrollPanel, gridBagConstraints);

		BaseLabel versionLabel = new BaseLabel("v" + Version.getVersion());
		versionLabel.setMinimumSize(new Dimension(10, textDefaultHeight));
		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		add(versionLabel, gridBagConstraints);

		BaseLabel authorLabel = new BaseLabel(
				"(Creado por Jorge Hortelano Otero)");
		authorLabel.setMinimumSize(new Dimension(250, textDefaultHeight));
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		add(authorLabel, gridBagConstraints);

		CloseButton closeButton = new CloseButton(this);
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.weighty = 0;
		add(closeButton, gridBagConstraints);

	}

	@Override
	public void updateFrame() {

	}

}

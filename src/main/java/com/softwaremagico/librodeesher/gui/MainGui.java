package com.softwaremagico.librodeesher.gui;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class MainGui extends BaseFrame {
	private static final long serialVersionUID = 2061009927740020075L;
	CharacterPanel characterPanel;
	List<CharacterPlayer> characters;

	/**
	 * Create the frame.
	 */
	public MainGui() {
		characters = new ArrayList<>();
		CharacterPlayer character = new CharacterPlayer();
		characters.add(character);
		windowsProperties();
		setElements();
		setEvents();
		setCharacter(character);
	}

	private void setCharacter(CharacterPlayer character) {
		characterPanel.setCharacter(character);
	}

	private void windowsProperties() {
		setSize(750, 400);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2
				- (int) (this.getWidth() / 2), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
				/ 2 - (int) (this.getHeight() / 2));
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		characterPanel = new CharacterPanel();
		characterPanel.setBounds(margin, margin, characterPanel.getWidth(), characterPanel.getHeight());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.ipadx = 5;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.insets = new Insets(1, 5, 1, 5);
		getContentPane().add(characterPanel, c);
	}

	private void setEvents() {
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent evt) {
				characterPanel.sizeChanged();
			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});
	}

}

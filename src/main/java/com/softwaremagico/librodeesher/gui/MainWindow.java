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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.characterBasics.CharacterPanel;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicSummaryPanel;
import com.softwaremagico.librodeesher.gui.resistance.ResistancePanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class MainWindow extends BaseFrame {
	private static final long serialVersionUID = 2061009927740020075L;
	private CharacterPanel characterPanel;
	private ResistancePanel resistancePanel;
	private CharacteristicSummaryPanel characteristicsPanel;
	private JScrollPane characteristicScrollPanel;
	private JScrollPane resistanceScrollPanel;
	private MainMenu mainMenu;
	private ResumeSkillPanel skillPanel;
	private CharacterPlayer character;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		defineWindow(750, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setElements();
		setEvents();
		addListeners();
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		characterPanel.setCharacter(character);
		characteristicsPanel.setCharacter(character, true);
		resistancePanel.setCharacter(character);
		mainMenu.setCharacter(character);
		skillPanel.update(character);
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// Add Main menu.
		mainMenu = new MainMenu();
		setJMenuBar(mainMenu.createMenu(this));

		characterPanel = new CharacterPanel(this);
		characterPanel.setBorder(getBorder());
		characterPanel.setBounds(margin, margin, characterPanel.getWidth(), characterPanel.getHeight());
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.ipadx = 5;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		getContentPane().add(characterPanel, gridBagConstraints);

		characteristicsPanel = new CharacteristicSummaryPanel();
		characteristicScrollPanel = new JScrollPane(characteristicsPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		characteristicScrollPanel.setBorder(getBorder());
		characteristicScrollPanel.setMinimumSize(new Dimension(80, 0));
		characteristicScrollPanel.setBounds(margin, margin, characteristicScrollPanel.getWidth(),
				characteristicScrollPanel.getHeight());
		// gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx = 0;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0.5;
		// gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		getContentPane().add(characteristicScrollPanel, gridBagConstraints);

		resistancePanel = new ResistancePanel();
		resistanceScrollPanel = new JScrollPane(resistancePanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		resistanceScrollPanel.setBorder(getBorder());
		resistanceScrollPanel.setMinimumSize(new Dimension(80, 0));
		resistanceScrollPanel.setBounds(margin, margin, resistanceScrollPanel.getWidth(),
				resistanceScrollPanel.getHeight());
		// gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx = 0;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0.5;
		// gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		getContentPane().add(resistanceScrollPanel, gridBagConstraints);

		// JPanel emptyPanel = new JPanel(); // emptyPanel.setBorder(border);
		// emptyPanel.setBounds(margin, margin, emptyPanel.getWidth(),
		// emptyPanel.getHeight());
		// gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		// gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
		// gridBagConstraints.gridwidth = 1;
		// gridBagConstraints.ipadx = 5;
		// gridBagConstraints.gridx = 0;
		// gridBagConstraints.gridy = 3;
		// gridBagConstraints.weightx = 0;
		// gridBagConstraints.weighty = 0;
		// gridBagConstraints.insets = new Insets(1, 5, 1, 5);
		// getContentPane().add(emptyPanel, gridBagConstraints);

		skillPanel = new ResumeSkillPanel();
		skillPanel.setBorder(getBorder());
		skillPanel.setMinimumSize(new Dimension(100, 100));
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 5;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		getContentPane().add(skillPanel, gridBagConstraints);

	}

	protected MainMenu getMainMenu() {
		return mainMenu;
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

	@Override
	public void update() {
		characterPanel.update();
		characteristicsPanel.update();
		resistancePanel.update();
		mainMenu.update();
	}

	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				update();
			}
		});
	}

	public void updateProfession() {
		skillPanel.update(character);
	}

}

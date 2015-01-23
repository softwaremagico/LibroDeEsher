package com.softwaremagico.librodeesher.gui.persistence;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;
import com.softwaremagico.utils.DateManager;

public class LoadCharacterPlayerWindow extends BaseFrame {
	private static final long serialVersionUID = -6558093656528513724L;
	private List<LoadCharacterListener> loadCharacterListeners;
	private JTable charactersTable;
	private List<CharacterPlayer> availableCharacterPlayers;

	public LoadCharacterPlayerWindow() {
		super();
		loadCharacterListeners = new ArrayList<>();
		defineWindow(700, 400);
		setResizable(false);
		availableCharacterPlayers = CharacterPlayerDao.getInstance().getAll();
		setElements();
	}

	private void setElements() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
		panel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.ipadx = xPadding;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;

		panel.add(createTablePanel(), constraints);

		constraints.anchor = GridBagConstraints.LAST_LINE_END;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = xPadding;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 0;
		panel.add(createButtonPanel(), constraints);
		add(panel);
	}

	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		String[] columnNames = { "Nombre", "Nivel", "Raza", "Profesión",
				"Adiestramientos", "Creado", "Modificado" };

		List<String[]> playersData = new ArrayList<>();
		for (CharacterPlayer characterPlayer : availableCharacterPlayers) {
			String[] playerData = new String[columnNames.length];
			playerData[0] = characterPlayer.getName();
			playerData[1] = characterPlayer.getCurrentLevelNumber().toString();
			playerData[2] = characterPlayer.getRace().getName();
			playerData[3] = characterPlayer.getProfession().getName();

			String trainingsText = "";
			List<String> trainings = characterPlayer.getSelectedTrainings();
			for (int i = 0; i < trainings.size(); i++) {
				trainingsText += trainings.get(i);
				if (i < trainings.size() - 1) {
					trainingsText += ", ";
				}
			}

			playerData[4] = trainingsText;
			playerData[5] = DateManager.convertDateToString(characterPlayer
					.getCreationTime());
			playerData[6] = DateManager.convertDateToString(characterPlayer
					.getCreationTime());
			playersData.add(playerData);
		}

		String[][] playerArray = new String[playersData.size()][columnNames.length];
		int i = 0;
		for (String[] playerData : playersData) {
			playerArray[i] = playerData;
			i++;
		}

		charactersTable = new JTable(playerArray, columnNames);
		charactersTable.setPreferredScrollableViewportSize(new Dimension(450,
				300));
		charactersTable.setFillsViewportHeight(true);
		charactersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(charactersTable);

		// Add the scroll pane to this panel.
		panel.add(scrollPane);

		return panel;
	}

	private JPanel createButtonPanel() {
		final BaseFrame thisWindow = this;
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.setMaximumSize(new Dimension(500, 40));
		buttonPanel.add(new JPanel());
		buttonPanel.add(new JPanel());

		JButton acceptButton = new BaseButton("Aceptar");
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Launch listeners.
				for (LoadCharacterListener listener : loadCharacterListeners) {
					if (charactersTable.getSelectedRow() >= 0) {
						CharacterPlayer selected = availableCharacterPlayers
								.get(charactersTable.getSelectedRow());
						selected.clearCache();
						listener.addCharacter(selected);
						thisWindow.dispose();
					}
				}
			}
		});
		buttonPanel.add(acceptButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		return buttonPanel;
	}

	@Override
	public void updateFrame() {

	}

	public void AddLoadCharacterListener(LoadCharacterListener listener) {
		loadCharacterListeners.add(listener);
	}

	public void removeLoadCharacterListener(LoadCharacterListener listener) {
		loadCharacterListeners.remove(listener);
	}

}

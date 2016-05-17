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
import javax.swing.table.AbstractTableModel;

import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.CharacterPlayerInfo;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerInfoDao;
import com.softwaremagico.utils.DateManager;

public class LoadCharacterPlayerWindow extends BaseFrame {
	private static final long serialVersionUID = -6558093656528513724L;
	private List<LoadCharacterListener> loadCharacterListeners;
	private List<RemoveCharacterListener> removeCharacterListeners;
	private JTable charactersTable;
	private List<CharacterPlayerInfo> availableCharacterPlayers;
	private JPanel characterTable;
	private JPanel rootPanel;

	public LoadCharacterPlayerWindow() {
		super();
		loadCharacterListeners = new ArrayList<>();
		removeCharacterListeners = new ArrayList<>();
		defineWindow(700, 400);
		availableCharacterPlayers = CharacterPlayerInfoDao.getInstance().getAll();
		setElements();
	}

	private void setElements() {
		if (rootPanel != null) {
			remove(rootPanel);
		}
		rootPanel = new JPanel();
		rootPanel.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
		rootPanel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.ipadx = xPadding;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		characterTable = createTablePanel();
		rootPanel.add(characterTable, constraints);

		constraints.anchor = GridBagConstraints.LAST_LINE_END;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = xPadding;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 0;
		rootPanel.add(createButtonPanel(), constraints);
		add(rootPanel);
		this.repaint();
		this.revalidate();
	}

	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		MyTableModel tableModel = new MyTableModel();

		charactersTable = new JTable(tableModel);
		charactersTable.setPreferredScrollableViewportSize(new Dimension(450, 300));
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
				if (charactersTable.getSelectedRow() >= 0) {
					CharacterPlayerInfo selected = availableCharacterPlayers.get(charactersTable.getSelectedRow());
					try {
						CharacterPlayer characterPlayer = CharacterPlayerDao.getInstance().read(selected.getId());
						characterPlayer.clearCache();
						for (LoadCharacterListener listener : loadCharacterListeners) {
							listener.addCharacter(characterPlayer);
							thisWindow.dispose();
						}
					} catch (Exception exception) {
						MessageManager
								.basicErrorMessage(
										this.getClass().getName(),
										"La base de datos está corrupta. Por favor, elimina el fichero '<home>/.librodeesher/database/esher' para reiniciarla. "
												+ "Si el fichero pertenece a una versión anterior del software, instala esa versión y exporta primero los personajes guardados a ficheros.",
										"Base de datos corrupta.");
					}
				}
			}
		});
		buttonPanel.add(acceptButton);

		JButton removeButton = new BaseButton("Borrar");
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CharacterPlayerInfo selected = availableCharacterPlayers.get(charactersTable.getSelectedRow());
				if (selected != null) {
					if (ShowMessage.showQuestionMessage(null, "El personaje seleccionado será elminado. ¿Quieres continuar con la acción?", "Borrado")) {
						CharacterPlayer characterPlayer = CharacterPlayerDao.getInstance().read(selected.getId());
						for (RemoveCharacterListener listener : removeCharacterListeners) {
							listener.removeCharacter(characterPlayer);
						}
						availableCharacterPlayers.remove(selected);
						setElements();
					}
				}
			}
		});
		buttonPanel.add(removeButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		return buttonPanel;
	}

	@Override
	public void updateFrame() {

	}

	public void addLoadCharacterListener(LoadCharacterListener listener) {
		loadCharacterListeners.add(listener);
	}

	public void removeLoadCharacterListener(LoadCharacterListener listener) {
		loadCharacterListeners.remove(listener);
	}

	public void addRemoveCharacterListener(RemoveCharacterListener listener) {
		removeCharacterListeners.add(listener);
	}

	public void removeRemoveCharacterListener(RemoveCharacterListener listener) {
		removeCharacterListeners.remove(listener);
	}

	class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -8023198297880106319L;

		private String[] columnNames = { "Nombre", "Nivel", "Raza", "Profesión", "Creado", "Modificado" };

		private Object[][] data = null;

		MyTableModel() {
			createData();
		}

		private void createData() {
			List<String[]> playersData = new ArrayList<>();
			for (CharacterPlayerInfo characterPlayer : availableCharacterPlayers) {
				String[] playerData = new String[columnNames.length];
				playerData[0] = characterPlayer.getName();
				playerData[1] = characterPlayer.getLevel() + "";
				playerData[2] = characterPlayer.getRaceName();
				playerData[3] = characterPlayer.getProfessionName();
				playerData[4] = DateManager.convertDateToString(characterPlayer.getCreationTime());
				playerData[5] = DateManager.convertDateToString(characterPlayer.getUpdateTime());
				playersData.add(playerData);
			}

			data = new String[playersData.size()][columnNames.length];
			int i = 0;
			for (String[] playerData : playersData) {
				data[i] = playerData;
				i++;
			}
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		@Override
		public Class<?> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

}

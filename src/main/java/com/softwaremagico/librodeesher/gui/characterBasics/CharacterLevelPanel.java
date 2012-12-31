package com.softwaremagico.librodeesher.gui.characterBasics;

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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;

public class CharacterLevelPanel extends BasePanel {

	private static final long serialVersionUID = -8063970435094018287L;
	private JLabel levelLabel;
	private JLabel developmentLabel;
	private JLabel magicLabel;
	private JTextField developmentTextField;
	private JTextField levelTextField;
	private JComboBox<RealmOfMagic> magicComboBox;
	private CharacterPlayer character;
	private boolean updatingMagic = false;

	protected CharacterLevelPanel() {
		setElements();
		setDefaultSize();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridBagLayout());

		levelLabel = new JLabel("Nivel:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		containerPanel.add(levelLabel, c);

		levelTextField = new JTextField();
		levelTextField.setEditable(false);
		levelTextField.setColumns(2);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		containerPanel.add(levelTextField, c);

		developmentLabel = new JLabel("Pts Desar.:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0;
		containerPanel.add(developmentLabel, c);

		developmentTextField = new JTextField();
		developmentTextField.setEditable(false);
		developmentTextField.setColumns(2);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.5;
		containerPanel.add(developmentTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.gridwidth = 2;
		add(containerPanel, c);

		magicLabel = new JLabel("Reino:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.gridwidth = 1;
		add(magicLabel, c);

		magicComboBox = new JComboBox<>();
		magicComboBox.addActionListener(new ChangeMagicListener());
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.gridwidth = 1;
		add(magicComboBox, c);
	}

	private void updateMagicComboBox() {
		updatingMagic = true;
		if (magicComboBox != null) {
			magicComboBox.removeAllItems();
			if (character != null && character.getProfession() != null) {
				List<RealmOfMagic> magicRealms = character.getProfession().getMagicRealmsAvailable();
				Collections.sort(magicRealms);
				for (RealmOfMagic magicRealm : magicRealms) {
					magicComboBox.addItem(magicRealm);
				}
				if (character.getRealmOfMagic() != null) {
					magicComboBox.setSelectedItem(character.getRealmOfMagic());
					if (getSelectedRealmOfMagic() != character.getRealmOfMagic()) {
						updateRealmOfMagic();
					}
				}
			}
		}
		updatingMagic = false;
	}

	public void sizeChanged() {
		if (this.getWidth() < 230) {
			levelLabel.setText("Nvl:");
			developmentLabel.setText("PD.:");
			magicLabel.setText("Re.:");
		} else if (this.getWidth() < 280) {
			levelLabel.setText("Nivel:");
			developmentLabel.setText("Pts. D.:");
			magicLabel.setText("Reino:");
		} else {
			levelLabel.setText("Nivel:");
			developmentLabel.setText("Pts. Des.:");
			magicLabel.setText("Reino:");
		}
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		levelTextField.setText(character.getCharacterLevel().toString());
		updateDevelopmentPoints();
		updateMagicComboBox();
		character.setRealmOfMagic(getSelectedRealmOfMagic());
	}

	private void updateDevelopmentPoints() {
		if (character.areCharacteristicsConfirmed()) {
			Integer points = character.getRemainingDevelopmentPoints();
			developmentTextField.setText(points.toString());
			if (points < 0) {
				developmentTextField.setForeground(Color.RED);
			} else {
				developmentTextField.setForeground(Color.DARK_GRAY);
			}
		} else {
			developmentTextField.setText("0");
		}
	}

	public void update() {
		updateMagicComboBox();
		levelTextField.setText(character.getCharacterLevel().toString());
		updateDevelopmentPoints();
		magicComboBox.setEnabled(!character.areCharacteristicsConfirmed());
	}

	public RealmOfMagic getSelectedRealmOfMagic() {
		if (magicComboBox != null) {
			return (RealmOfMagic) magicComboBox.getSelectedItem();
		}
		return null;
	}

	private void updateRealmOfMagic() {
		if (character != null) {
			character.setRealmOfMagic(getSelectedRealmOfMagic());
		}
	}

	class ChangeMagicListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!updatingMagic) {
				updateRealmOfMagic();
			}
		}
	}

}

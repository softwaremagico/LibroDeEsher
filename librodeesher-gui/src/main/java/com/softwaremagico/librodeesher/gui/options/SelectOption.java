package com.softwaremagico.librodeesher.gui.options;

/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class SelectOption extends BasePanel {
	CharacterPlayer character;
	private OptionsPanel optionsPanel;
	private BaseFrame parent;
	private List<String> options;
	private PointsCounterTextField optionsCount;
	private Integer numberOfOptions;
	private List<String> selectedOptions;

	public SelectOption(CharacterPlayer character, BaseFrame parent, List<String> options, Integer numberOfOptions) {
		this.parent = parent;
		this.character = character;
		this.options = options;
		this.numberOfOptions = numberOfOptions;
		optionsCount = new PointsCounterTextField();
		selectedOptions = new ArrayList<>();
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;

		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		JPanel countPanel = new JPanel();
		countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.X_AXIS));
		JLabel optionsLabel = new BaseLabel("    Opciones:  ");
		countPanel.add(optionsLabel);
		optionsCount.setColumns(3);
		optionsCount.setEditable(false);
		optionsCount.setMaximumSize(new Dimension(60, 25));
		setOptionsText();
		countPanel.add(optionsCount);
		add(countPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		optionsPanel = new OptionsPanel(this, options, numberOfOptions);
		add(optionsPanel, gridBagConstraints);
	}

	private void setOptionsText(List<String> selectedOptions) {
		optionsCount.setPoints(numberOfOptions - selectedOptions.size());
	}

	private void setOptionsText() {
		optionsCount.setPoints(numberOfOptions);
	}

	@Override
	public void update() {
		selectedOptions = optionsPanel.getSelectedOptions();
		setOptionsText(selectedOptions);
		parent.update();
	}

	public List<String> getSelectedOptions() {
		return selectedOptions;
	}
}

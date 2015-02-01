package com.softwaremagico.librodeesher.gui.options;

/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class SelectOption<T> extends BasePanel {
	private static final long serialVersionUID = 4299961102900809569L;
	private OptionsPanel optionsPanel;
	private BaseFrame parent;
	private ChooseGroup<T> options;
	private PointsCounterTextField optionsCount;
	private List<String> selectedOptions;
	private JLabel optionsLabel;

	public SelectOption(BaseFrame parent, ChooseGroup<T> options) {
		this.parent = parent;
		this.options = options;
		optionsCount = new PointsCounterTextField();
		selectedOptions = new ArrayList<>();
		setElements();
	}

	public void setPointCounterLabel(String text) {
		optionsLabel.setText(text);
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
		optionsLabel = new BaseLabel("    Opciones:  ");
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
		optionsPanel = new OptionsPanel(this, options.getOptionsAsString(),
				options.getNumberOfOptionsToChoose());
		add(optionsPanel, gridBagConstraints);
	}

	private void setOptionsText(List<String> selectedOptions) {
		optionsCount.setPoints(options.getNumberOfOptionsToChoose() - selectedOptions.size());
	}

	private void setOptionsText() {
		optionsCount.setPoints(options.getNumberOfOptionsToChoose());
	}

	@Override
	public void update() {
		selectedOptions = optionsPanel.getSelectedOptions();
		setOptionsText(selectedOptions);
		parent.updateFrame();
	}

	public List<String> getSelectedOptions() {
		return selectedOptions;
	}

	public ChooseGroup<T> getOptions() {
		return options;
	}
}

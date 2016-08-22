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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.elements.UpdatePanelListener;

public class OptionsPanel extends BaseScrollPanel {
	private static final long serialVersionUID = -5606065330811602828L;
	private OptionsTitle title;
	private OptionsListPanel optionsListPanel;
	private Set<UpdatePanelListener> updatePanelListeners;

	protected OptionsPanel() {
		title = new OptionsTitle();
		updatePanelListeners = new HashSet<>();
		addTitle(title);
	}

	protected OptionsPanel(List<String> options, Integer numberOfOptions, List<String> selectedOptions) {
		title = new OptionsTitle();
		updatePanelListeners = new HashSet<>();
		addTitle(title);
		optionsListPanel = new OptionsListPanel(options, numberOfOptions, this, selectedOptions);
		setBody(optionsListPanel);
	}

	public void setOptions(List<String> options, Integer numberOfOptions, List<String> selectedOptions) {
		optionsListPanel = new OptionsListPanel(options, numberOfOptions, this, selectedOptions);
		setBody(optionsListPanel);
	}

	public void removeOptions() {
		if (optionsListPanel != null) {
			optionsListPanel.removeAll();
		}
	}

	public List<String> getSelectedOptions() {
		return optionsListPanel.getSelectedOptions();
	}

	public void setSelectedOptions(List<String> selectedOptions) {
		if (selectedOptions != null) {
			optionsListPanel.setSelectedOptions(selectedOptions);
		}
	}

	@Override
	public void update() {
		for (UpdatePanelListener listener : updatePanelListeners) {
			listener.updatePanel();
		}
	}

	public void addUpdatePanelListener(UpdatePanelListener listener) {
		updatePanelListeners.add(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		optionsListPanel.setEnabled(enabled);
	}

}

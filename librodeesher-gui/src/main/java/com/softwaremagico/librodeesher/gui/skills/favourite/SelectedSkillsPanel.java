package com.softwaremagico.librodeesher.gui.skills.favourite;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2015 Softwaremagico
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

public class SelectedSkillsPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 1738271613358340305L;
	private SkillsTitle title;
	private SkillsListPanel optionsListPanel;
	private Set<UpdatePanelListener> updatePanelListeners;

	protected SelectedSkillsPanel() {
		title = new SkillsTitle();
		updatePanelListeners = new HashSet<>();
		addTitle(title);
	}

	protected SelectedSkillsPanel(List<String> options) {
		title = new SkillsTitle();
		updatePanelListeners = new HashSet<>();
		addTitle(title);
		optionsListPanel = new SkillsListPanel(options, this);
		setBody(optionsListPanel);
	}

	public void setOptions(List<String> options) {
		optionsListPanel = new SkillsListPanel(options, this);
		setBody(optionsListPanel);
	}

	public void removeOptions() {
		if (optionsListPanel != null) {
			optionsListPanel.removeAll();
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

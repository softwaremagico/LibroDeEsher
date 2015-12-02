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

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class SetSkillAsFavouritePanel extends BasePanel {
	private static final long serialVersionUID = -2805487044197535337L;
	private BaseCheckBox favouriteCheckBox;

	protected SetSkillAsFavouritePanel() {
		setElements();
	}

	private void setElements() {
		setLayout(new BorderLayout());
		favouriteCheckBox = new BaseCheckBox("Favorita");
		add(favouriteCheckBox, BorderLayout.LINE_END);
	}

	@Override
	public void update() {

	}

	public void addFavouriteCheckBoxActionListener(ActionListener al) {
		favouriteCheckBox.addActionListener(al);
	}

	public void setFavouriteCheckBoxSelected(boolean selected) {
		favouriteCheckBox.setSelected(selected);
	}

	public boolean isFavouriteCheckBoxSelected() {
		return favouriteCheckBox.isSelected();
	}
}

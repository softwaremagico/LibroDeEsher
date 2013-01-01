package com.softwaremagico.librodeesher.gui.culture;
/*
 * #%L
 * Libro de Esher
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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class WeaponCategoryLine extends BasicLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private Category weaponsCategory;
	private Integer ranks;

	public WeaponCategoryLine(Category weaponsCategory, Integer ranks, Color background) {
		this.weaponsCategory = weaponsCategory;
		this.ranks = ranks;
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;


		JLabel weaponCategoryLabel = new JLabel(weaponsCategory.getName());
		weaponCategoryLabel.setFont(new Font(font, Font.BOLD, fontSize));
		add(createLabelInsidePanel(weaponCategoryLabel, SwingConstants.LEFT, background, fontColor), gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.gridx = 1;
		JLabel rankLabel = new JLabel("("+ranks.toString()+")");
		weaponCategoryLabel.setFont(new Font(font, Font.BOLD, fontSize));
		add(createLabelInsidePanel(rankLabel, SwingConstants.CENTER, background, fontColor), gridBagConstraints);

	}
}

package com.softwaremagico.librodeesher.gui.elements;
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

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class BaseScrollPanel extends BasePanel {
	private static final long serialVersionUID = 2731483312448464339L;
	private JScrollPane scrollPanel;
	private JPanel titlePanel;

	public BaseScrollPanel() {
		setElements();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		titlePanel = new JPanel();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		add(titlePanel, gridBagConstraints);

		scrollPanel = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setBorder(getBorder());
		scrollPanel.setBounds(0, 0, scrollPanel.getWidth(), scrollPanel.getHeight());
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(scrollPanel, gridBagConstraints);
	}

	public void addTitle(Component component) {
		GridBagConstraints titleGridBagConstraints = new GridBagConstraints();
		titleGridBagConstraints.fill = GridBagConstraints.BOTH;
		titleGridBagConstraints.ipadx = xPadding;
		titleGridBagConstraints.gridx = 0;
		titleGridBagConstraints.gridy = 0;
		titleGridBagConstraints.gridheight = 1;
		titleGridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		titleGridBagConstraints.weightx = 1;
		titleGridBagConstraints.weighty = 0;
		titlePanel.setLayout(new GridBagLayout());
		titlePanel.add(component, titleGridBagConstraints);
	}

	public void addBody(Component component) {
		scrollPanel.setViewportView(component);
	}

}

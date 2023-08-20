package com.softwaremagico.librodeesher.gui.components;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2017 Softwaremagico
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SelectSkillWindow extends BasePanel {
	private static final long serialVersionUID = -7821247242913784930L;
	private CharacterPlayer characterPlayer;
	private List<Category> categories;
	private Set<WindowClosedListener> listeners;
	private JDialog parent;
	private SelectSkillPanel skillPanel;

	public interface WindowClosedListener {
		void setSelectedSkill(Skill skill);
	}

	public SelectSkillWindow(JDialog parent, CharacterPlayer characterPlayer, List<Category> categories) {
		listeners = new HashSet<>();
		this.parent = parent;
		this.characterPlayer = characterPlayer;
		this.categories = categories;
		setElements();
	}

	public void setTitle(String text) {
		skillPanel.setTitle(text);
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();


		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;

		skillPanel = new SelectSkillPanel(characterPlayer, categories);
		add(skillPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		CloseButton closeButton = new CloseButton(parent);
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (WindowClosedListener listener : listeners) {
					listener.setSelectedSkill(skillPanel.getSelectedSkill());
				}
			}
		});
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		add(buttonPanel, gridBagConstraints);

	}

	public void addWindowClosedListener(WindowClosedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}

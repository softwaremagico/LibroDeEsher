package com.softwaremagico.librodeesher.gui.files;
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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.Fonts;

public class ExploreWindowForPdfWithOptions extends BaseFrame {
	private static final long serialVersionUID = 3629660172289981315L;
	private static final Integer MARGIN = 10;
	private List<ExploreWindowsWithOptionsListener> listeners;

	public ExploreWindowForPdfWithOptions(String title, int mode, String file,
			javax.swing.filechooser.FileFilter filter, JComponent accesory,
			String defaultFileName) {
		listeners = new ArrayList<>();
		defineWindow(500, 400);
		setLayout(new BorderLayout());

		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

		JLabel titleLabel = new JLabel("Opciones:");
		titleLabel.setFont(Fonts.getInstance().getBoldFont());
		optionsPanel.add(titleLabel);

		final JCheckBox sortSkills = new JCheckBox(
				"Habilidades ordenadas alfabéticamente.");

		optionsPanel.setLayout(new GridLayout(0, 1));
		optionsPanel.add(sortSkills);
		add(optionsPanel, BorderLayout.NORTH);

		final JFileChooser chooser = ExploreWindow.createFileChooser(title,
				mode, file, filter, accesory, defaultFileName);
		chooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (ExploreWindowsWithOptionsListener listener : listeners) {
					listener.accept(
							chooser.getSelectedFile().getAbsolutePath(),
							sortSkills.isSelected());
				}
			}
		});
		add(chooser, BorderLayout.CENTER);

	}

	@Override
	public void updateFrame() {
	}

	public void addAcceptListener(ExploreWindowsWithOptionsListener listener) {
		listeners.add(listener);
	}

	public void removeAcceptListener(ExploreWindowsWithOptionsListener listener) {
		listeners.remove(listener);
	}

}

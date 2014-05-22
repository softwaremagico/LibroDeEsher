package com.softwaremagico.librodeesher.gui.style;/*
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


import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class BaseDialog extends JDialog {
	private static final String DIALOG_TITLE = "El Libro de Esher";
	private static final long serialVersionUID = -1726966977605568691L;

	public BaseDialog(BaseFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		setTitle(DIALOG_TITLE);
		setIconImage(new ImageIcon(this.getClass().getResource("/librodeesher.png")).getImage());
	}

	public void center() {
		setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2
				- (int) (this.getWidth() / 2), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
				/ 2 - (int) (this.getHeight() / 2));
	}

}

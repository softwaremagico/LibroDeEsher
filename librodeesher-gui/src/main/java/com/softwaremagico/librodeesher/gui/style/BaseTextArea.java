package com.softwaremagico.librodeesher.gui.style;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2016 Softwaremagico
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

import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class BaseTextArea extends JTextArea {
	private static final long serialVersionUID = -7088105553303837618L;
	private DefaultStyledDocument doc;

	public BaseTextArea() {
		super();
		setLineWrap(true);
		setDefaultDocument();
	}

	private void setDefaultDocument() {
		doc = new DefaultStyledDocument();
		doc.setDocumentFilter(new DocumentSizeFilter(CharacterPlayer.MAX_TEXT_LENGTH));
		setDocument(doc);
	}

	class DocumentSizeFilter extends DocumentFilter {
		int maxCharacters;

		public DocumentSizeFilter(int maxChars) {
			maxCharacters = maxChars;
		}

		@Override
		public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
				throws BadLocationException {

			// This rejects the entire insertion if it would make
			// the contents too long. Another option would be
			// to truncate the inserted string so the contents
			// would be exactly maxCharacters in length.
			if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
				super.insertString(fb, offs, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}

		@Override
		public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
				throws BadLocationException {
			// This rejects the entire replacement if it would make
			// the contents too long. Another option would be
			// to truncate the replacement string so the contents
			// would be exactly maxCharacters in length.
			if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
				super.replace(fb, offs, length, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}

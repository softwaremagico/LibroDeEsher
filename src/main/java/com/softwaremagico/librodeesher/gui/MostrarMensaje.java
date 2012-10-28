/*
 *
 This software is designed by Jorge Hortelano Otero.
 softwaremagico@gmail.com
 Copyright (C) 2007 Jorge Hortelano Otero.
 C/Botanico 12, 1. Valencia CP:46008 (Spain).
 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 Created on agost of 2008.
 */
package com.softwaremagico.librodeesher.gui;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2008 - 2012 Softwaremagico
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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jorge
 */
public class MostrarMensaje {

    private static final int LINE = 50;

    public MostrarMensaje(String text, String title) {
        errortextMessage(text, title);
        errorMessage(text, title, JOptionPane.ERROR_MESSAGE);
    }

    public MostrarMensaje(String text, String title, int option) {
        errortextMessage(text, title);
        errorMessage(text, title, option);
    }

    public static void showErrorMessage(String text, String title) {
        errortextMessage(text, title);
        errorMessage(text, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessage(String text, String title, int option) {
        errortextMessage(text, title);
        errorMessage(text, title, option);
    }

    private static void errorMessage(String text, String title, int option) {
        int i = 0, caracteres = 0;
        String texto[] = text.split(" ");
        text = "";
        while (i < texto.length) {
            text += texto[i] + " ";
            caracteres += texto[i].length();
            if (caracteres > LINE) {
                text = text.trim() + "\n";
                caracteres = 0;
            }
            i++;
        }

        JFrame frame = null;

        text = text.replaceAll("\t", "  ");

        JOptionPane.showMessageDialog(frame, text, title, option);
    }

    private static void errortextMessage(String text, String title) {
        System.out.println(title + ":");
        System.out.println(text);
    }
}
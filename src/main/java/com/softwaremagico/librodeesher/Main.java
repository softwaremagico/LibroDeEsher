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
 Created on october of 2007.
 */
package com.softwaremagico.librodeesher;
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

import com.softwaremagico.librodeesher.gui.AñadirAdiestramientoGUI;
import com.softwaremagico.librodeesher.gui.AleatorioGUI;
import com.softwaremagico.librodeesher.gui.AdiestramientoGUI;
import com.softwaremagico.librodeesher.gui.CaracteristicasGUI;
import com.softwaremagico.librodeesher.gui.CategoriasYHabilidadesGUI;
import com.softwaremagico.librodeesher.gui.SeleccionarHabilidadGUI;
import com.softwaremagico.librodeesher.gui.OpcionesGUI;
import com.softwaremagico.librodeesher.gui.ObjetoMagicoGUI;
import com.softwaremagico.librodeesher.gui.MainGUI;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    /**
     * Creates a new instance of Main
     */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Esher esher;
        String soName = System.getProperty("os.name");
        try {
            if (args.length > 0) {
                esher = new Esher(args[0]);
            } else {
                esher = new Esher("");
            }

            if (soName.contains("windows") || soName.contains("Windows")) {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                    try {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex1) {
                    }
                }
            } else {
                if (soName.contains("Linux") || soName.contains("linux")) {
                    try {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    }
                }
            }
            MainGUI gui = new MainGUI(esher);
            CaracteristicasGUI caracteristicasGui = new CaracteristicasGUI(esher);
            CategoriasYHabilidadesGUI catHabGui = new CategoriasYHabilidadesGUI(esher);
            AleatorioGUI aleatorio = new AleatorioGUI(esher);
            AdiestramientoGUI adiestramiento = new AdiestramientoGUI(esher);
            AñadirAdiestramientoGUI añadirAdiestramiento = new AñadirAdiestramientoGUI(esher);
            OpcionesGUI opciones = new OpcionesGUI(esher);
            SeleccionarHabilidadGUI habilidades = new SeleccionarHabilidadGUI(esher);
            ObjetoMagicoGUI objetosMagicos = new ObjetoMagicoGUI(esher);

            new Controller(esher, gui, caracteristicasGui, catHabGui, aleatorio,
                    adiestramiento, añadirAdiestramiento, opciones, habilidades, objetosMagicos);

            gui.setVisible(true);
            caracteristicasGui.setVisible(false);
            catHabGui.setVisible(false);
            adiestramiento.setVisible(false);
            añadirAdiestramiento.setVisible(false);
            opciones.setVisible(false);
            objetosMagicos.setVisible(false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

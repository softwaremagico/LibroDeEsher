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

import com.softwaremagico.files.DirectorioRolemaster;
import com.softwaremagico.librodeesher.gui.AboutBox;
import com.softwaremagico.librodeesher.gui.AdiestramientoGUI;
import com.softwaremagico.librodeesher.gui.AleatorioGUI;
import com.softwaremagico.librodeesher.gui.AñadirAdiestramientoGUI;
import com.softwaremagico.librodeesher.gui.CaracteristicasGUI;
import com.softwaremagico.librodeesher.gui.CategoriasYHabilidadesGUI;
import com.softwaremagico.librodeesher.gui.CulturaGUI;
import com.softwaremagico.librodeesher.gui.HistorialGUI;
import com.softwaremagico.librodeesher.gui.InsertarCategoriasGUI;
import com.softwaremagico.librodeesher.gui.InsertarPersonajeGUI;
import com.softwaremagico.librodeesher.gui.MainGUI;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import com.softwaremagico.librodeesher.gui.ObjetoMagicoGUI;
import com.softwaremagico.librodeesher.gui.OpcionesGUI;
import com.softwaremagico.librodeesher.gui.SeleccionarHabilidadGUI;
import com.softwaremagico.librodeesher.gui.TalentosGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller {

    private MainGUI gui;
    private CaracteristicasGUI caracteristicasGui;
    private CategoriasYHabilidadesGUI catHabGui;
    private AboutBox aboutGui;
    private JFileChooser fc;
    private CulturaGUI culturaGui;
    private AleatorioGUI aleatorioGui;
    private AdiestramientoGUI adiestramientoGui;
    private AñadirAdiestramientoGUI añadirAdiestramientoGui;
    private OpcionesGUI opciones;
    private HistorialGUI historial;
    private InsertarPersonajeGUI insertarPjGui;
    private InsertarCategoriasGUI insertarCatGui;
    private TalentosGUI talentos;
    private SeleccionarCaracteristica selecCar;
    private SeleccionarHabilidadGUI selecHab;
    private ObjetoMagicoGUI objetosMagicos;

    /**
     * Creates a new instance of Controller
     */
    public Controller(MainGUI tmp_gui, CaracteristicasGUI tmp_caracteristicasGui,
            CategoriasYHabilidadesGUI categoriasYHabilidades,
            AleatorioGUI tmp_aleatorio, AdiestramientoGUI tmp_adiestramiento,
            AñadirAdiestramientoGUI tmp_añadirAdiestramiento, OpcionesGUI tmp_opciones,
            SeleccionarHabilidadGUI tmp_selecHab, ObjetoMagicoGUI tmp_objetosMagicos) {
        gui = tmp_gui;
        caracteristicasGui = tmp_caracteristicasGui;
        catHabGui = categoriasYHabilidades;
        aleatorioGui = tmp_aleatorio;
        adiestramientoGui = tmp_adiestramiento;
        añadirAdiestramientoGui = tmp_añadirAdiestramiento;
        opciones = tmp_opciones;
        selecHab = tmp_selecHab;
        objetosMagicos = tmp_objetosMagicos;
        AddMenusPrincipalListeners();
        AddCaracteristicaListeners();
        AddCategoriasYHabilidadesListeners();
        AddAleatorioListeners();
        AddAdiestramientoListeners();
        AddAñadirAdiestramientoListeners();
        AddOpcionesListener();
        AddSeleccionarHabilidadesListeners();
        AddObjetoMagicoListener();
        //AddSeleccionarHabilidadesTalentoListeners();
    }

    /**
     * Generate a window to search in the file system.
     *
     * @param mode The kind of window.
     * @see setFileSelectionMode
     */
    String ExplorarVentanas(String title, int mode, String file) {
        JFrame frame = null;

        fc = new JFileChooser(new File(Esher.getInstance().ObtenerDirectorioPorDefecto() + File.separator));
        fc.setFileFilter(new RMFilter());
        fc.setFileSelectionMode(mode);
        if (file.length() == 0 && !title.equals("Load")) {
            fc.setSelectedFile(new File(Esher.getInstance().ArchivoDefectoGuardar()));
        } else {
            fc.setSelectedFile(new File(file));
        }
        int fcReturn = fc.showDialog(frame, title);
        if (fcReturn == JFileChooser.APPROVE_OPTION) {
            Esher.getInstance().CambiarDirectorioPorDefecto(fc.getSelectedFile().toString());
            if (fc.getSelectedFile().isDirectory()) {
                return fc.getSelectedFile().toString()
                        + File.pathSeparator + Esher.getInstance().ArchivoDefectoGuardar();
            }
            return fc.getSelectedFile().toString();
        }
        return "";
    }

    String ExplorarVentanasNivel(String title, int mode, String file) {
        JFrame frame = null;

        fc = new JFileChooser(new File(Esher.getInstance().ObtenerDirectorioPorDefecto() + File.separator));
        fc.setFileFilter(new NivelFilter());
        fc.setFileSelectionMode(mode);
        if (file.length() == 0 && !title.equals("Load")) {
            fc.setSelectedFile(new File(Esher.getInstance().ArchivoDefectoExportarNivel()));
        } else {
            fc.setSelectedFile(new File(file));
        }
        int fcReturn = fc.showDialog(frame, title);
        if (fcReturn == JFileChooser.APPROVE_OPTION) {
            Esher.getInstance().CambiarDirectorioPorDefecto(fc.getSelectedFile().toString());
            if (fc.getSelectedFile().isDirectory()) {
                return fc.getSelectedFile().toString()
                        + File.pathSeparator + Esher.getInstance().ArchivoDefectoExportarNivel();
            }
            return fc.getSelectedFile().toString();
        }
        return "";
    }

    String ExplorarVentanasPdf(String title, int mode, String file) {
        JFrame frame = null;

        fc = new JFileChooser(new File(Esher.getInstance().ObtenerDirectorioPorDefecto() + File.separator));
        fc.setFileFilter(new PdfFilter());
        fc.setFileSelectionMode(mode);
        if (file.length() == 0 && !title.equals("Load")) {
            fc.setSelectedFile(new File(Esher.getInstance().ArchivoDefectoGuardar()));
        } else {
            fc.setSelectedFile(new File(file));
        }
        int fcReturn = fc.showDialog(frame, title);
        if (fcReturn == JFileChooser.APPROVE_OPTION) {
            Esher.getInstance().CambiarDirectorioPorDefecto(fc.getSelectedFile().toString());
            if (fc.getSelectedFile().isDirectory()) {
                return fc.getSelectedFile().toString()
                        + File.pathSeparator + Esher.getInstance().ArchivoDefectoGuardar();
            }
            return fc.getSelectedFile().toString();
        }
        return "";
    }

    void ActualizarPjEnTodasVentanas() {
        gui.ActualizarPj();
        gui.Refrescar();
        caracteristicasGui.Refrescar();
        catHabGui.Iniciar();
        catHabGui.Refrescar();
    }

    boolean GuardarPersonaje() {
        if (Esher.CrearCarpeta(fc.getCurrentDirectory().toString())
                && GuardarPj(fc.getSelectedFile().toString(), true)) {
            return true;
        }
        return false;
    }

    public boolean GuardarPj(String file, boolean verbose) {
        if (file == null || file.equals("")) {
            file = Esher.ArchivoDefectoGuardar();
        }
        File directory = new File(file);
        if (directory.isDirectory()) {
            file = file + File.separator + Esher.ArchivoDefectoGuardar();
        }
        if (!file.endsWith(".rlm")) {
            file += ".rlm";
        }
        try {
            Personaje.getInstance().lock = true;
            if (verbose) {
                Personaje.getInstance().lastSavedLevel = Personaje.getInstance().nivel;
                Personaje.getInstance().vecesCargadoPersonaje = 0;
            }
            new SerialPjStream(file).save(Personaje.getInstance());
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        if (verbose) {
            MostrarMensaje.showMessage("El Personaje ha sido guardado con exito", "Exportar Personaje", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }

    void CargarPersonaje(String path) {
        try {
            Esher.Reset();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Load files.
        CargarPj(path);
        ActualizarPjEnTodasVentanas();
        gui.GUIdePersonajeYaInsertado();
        if (Personaje.getInstance().PuntosDesarrolloNoGastados() > 0) {
            gui.ActivarMenuGenerar(true);
        }
        //esher.CombinarIdiomasRazaYTodos();    //Borra los idiomas que se habían guardado!!
        Personaje.getInstance().FusionarCategoriasNuevasConUsables();
    }

    private void CargarPj(String path) {
        boolean error = false;
        List l;

        try {
            l = new SerialPjStream(path).load();
            Personaje.setInstance((Personaje) l.get(0));
            //new LeerProfesion(esher); //Borra las comunes y profesionales al leer de nuevo la profesion. Quitar esta linea.
            Personaje.getInstance().lock = true;
            Personaje.getInstance().loadedFrom = path;
        } catch (ClassNotFoundException | IOException | ClassCastException ex) {
            MostrarMensaje.showErrorMessage("Formato de archivo no válido.", "Load");
            error = true;
            ex.printStackTrace();
        }
        if (!error) {
            if (Personaje.getInstance().TieneRangosInsertados().length() > 0) {
                MostrarMensaje.showMessage("El Personaje tiene rangos insertados: no se ha generado siguiendo las normas de forma correcta.", "Cargar Personaje", JOptionPane.WARNING_MESSAGE);
            } else {
                MostrarMensaje.showMessage("El Personaje ha sido cargado con exito.", "Cargar Personaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    private boolean ExportarNivel() {
        if (Esher.CrearCarpeta(fc.getCurrentDirectory().toString())
                && ExportarNivel(fc.getSelectedFile().toString())) {
            return true;
        }
        return false;
    }

    private boolean ExportarNivel(String file) {
        if (file == null || file.equals("")) {
            file = Esher.ArchivoDefectoExportarNivel();
        }
        File directory = new File(file);
        if (directory.isDirectory()) {
            file = file + File.separator + Esher.ArchivoDefectoExportarNivel();
        }
        if (!file.endsWith(".nvl")) {
            file += ".nvl";
        }
        try {
            ExportarNivel en = new ExportarNivel();
            new SerialNivelStream(file).save(en);

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        MostrarMensaje.showMessage("El nivel ha sido exportado con exito", "Exportar nivel", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private void ImportarNivel(String path) {

        LoadNivel(path);
        ActualizarPjEnTodasVentanas();
        caracteristicasGui.Refrescar();

        catHabGui.Iniciar();
        catHabGui.Refrescar();
    }

    private void LoadNivel(String path) {
        boolean error = false;
        List l;

        try {
            l = new SerialNivelStream(path).load();
            ExportarNivel en = (ExportarNivel) l.get(0);
            if ((en.nivel == Personaje.getInstance().nivel + 1) && (Personaje.getInstance().DevolverNombreCompleto().equals(en.nombre))) {
                Personaje.getInstance().InicioSubirNivel();
                en.ImportarNivel();
                Personaje.getInstance().CalcularPuntosDesarrollo();
                Personaje.getInstance().caracteristicas.CalcularProximoAumento();
            } else {
                MostrarMensaje.showErrorMessage("El personaje o nivel no es el adecuado. ", "Importar nivel");
                error = true;
            }
        } catch (ClassNotFoundException ex) {
            error = true;
            ex.printStackTrace();
        } catch (IOException ex) {
            error = true;
            MostrarMensaje.showErrorMessage("Formato de archivo no válido.", "Load");
        } catch (ClassCastException ex) {
            error = true;
            MostrarMensaje.showErrorMessage("Formato de archivo no válido.", "Load");
        }
        if (!error) {
            MostrarMensaje.showMessage("El nivel ha sido cargado con exito.", "Cargar nivel", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * *******************************************************************
     *
     * MENU PRINCIPAL
     *
     ********************************************************************
     */
    /**
     * Añade los listeners a la ventana del menu principal.
     */
    private void AddMenusPrincipalListeners() {
        gui.addRazasComboBoxListener(new CambiarRazaListener());
        gui.addProfesionComboBoxListener(new CambiarProfesionListener());
        gui.addReinosComboBoxListener(new CambiarReinoMagiaListener());
        gui.addCaracteristicasListener(new AbrirVentanaCaracteristicas());
        gui.addCategoriasYHabilidadesListener(new AbrirVentanaCategoriasYHabilidades());
        gui.addAdiestramientoListener(new AbrirVentanaAdiestramiento());
        gui.addSubirNivelButtonListener(new SubirNivelListener());
        gui.addSubirNivelMenuItem(new SubirNivelListener());
        gui.addExportarNivelMenuItemListener(new ExportarNivelListener());
        gui.addImportarNivelMenuItemListener(new ImportarNivelListener());
        gui.addAleatorioMenuListener(new AbrirVentanaAleatorio());
        gui.addNewMenuItemListener(new NewListener());
        gui.addSaveMenuItemListener(new SaveListener());
        gui.addLoadMenuItemListener(new LoadListener());
        gui.addExitMenuItemListener(new ExitListener());
        gui.addAboutMenuItemListener(new AboutBoxListener());
        gui.addSexoListener(new CambiarSexoListener());
        gui.addCulturaMenuListener(new AbrirVentanaCultura());
        gui.addTxtMenuItemListener(new TxtListener());
        gui.addAbrevTxtMenuItemListener(new AbrevTxtListener());
        gui.addCulturaComboBoxListener(new CambiarCulturaListener());
        gui.addPdfMenuItemListener(new PdfListener());
        gui.addPdfCombinadoMenuItemListener(new PdfCombinadoListener());
        gui.addOpcionesMenuItemListener(new AbrirVentanaOpciones());
        gui.addHistorialMenuItemListener(new AbrirVentanaHistorial());
        gui.addTalentosMenuItemListener(new AbrirVentanaTalentos());
        gui.addHojaEnBlancoMenuItemListener(new HojaEnBlanco());
        gui.addInsertarPjMenuItemListener(new AbrirVentanaInsertarPersonaje());
        gui.addInsertarCategoriaMenuItemListener(new AbrirVentanaInsertarCategoria());
    }

    class CambiarRazaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.update) {
                String profesion = Personaje.getInstance().profesion;
                String cultura = Personaje.getInstance().cultura;
                try {
                    Esher.aleatorio = false;
                    Personaje.getInstance().raza = gui.DevolverRazaSeleccionada();
                    new LeerRaza();
                    gui.RellenarCulturas();
                    gui.SeleccionarCultura(cultura);
                    gui.RellenaProfesion();
                    gui.SeleccionarProfesion(profesion);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class CambiarProfesionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.update) {
                try {
                    Personaje.getInstance().profesion = gui.DevolverProfesionSeleccionada();
                    //gui.RellenarReinosDeMagia();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class CambiarReinoMagiaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.update) {
                Personaje.getInstance().reino = gui.DevolverReinoDeMagia();
                Personaje.getInstance().ObtenerMediaCostePuntosDePoder();
                Personaje.getInstance().ObtenerMagia();
            }
        }
    }

    class CambiarCulturaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.update) {
                Personaje.getInstance().cultura = gui.DevolverCulturaSeleccionada();
            }
        }
    }

    public CaracteristicasGUI getCaracteristicasGui() {
        return caracteristicasGui;
    }

    class AbrirVentanaCaracteristicas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (gui.ProfesionNoLeida()) {
                    Personaje.getInstance().profesion = gui.DevolverProfesionSeleccionada();
                    new LeerProfesion();
                    gui.RellenarReinosDeMagia();
                    Personaje.getInstance().ActualizaCaracteristicasReino();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (Personaje.getInstance().nivel > 1) {
                caracteristicasGui.DeshabilitarCambiosEnCaracteristicas();
            }
            caracteristicasGui.Refrescar();
            caracteristicasGui.setVisible(true);
        }
    }

    class AbrirVentanaCategoriasYHabilidades implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            catHabGui.setVisible(true);
            catHabGui.Refrescar();
        }
    }

    class AbrirVentanaAdiestramiento implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            añadirAdiestramientoGui.Refrescar();
            añadirAdiestramientoGui.setVisible(true);
        }
    }

    class SubirNivelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Personaje.getInstance().vecesCargadoPersonaje++;
            if (Personaje.getInstance().vecesCargadoPersonaje > Personaje.getInstance().nivel - Personaje.getInstance().lastSavedLevel + 1) {
                //MostrarError.showMessage("Atención: cada intento de subida de nivel quedará registrado.", "Importar Personaje", JOptionPane.WARNING_MESSAGE);
            }

            if (Personaje.getInstance().loadedFrom.length() > 0) {
                GuardarPj(Personaje.getInstance().loadedFrom, false);
            }
            Personaje.getInstance().SubirUnNivel();
            gui.SubirNiveles();
            caracteristicasGui.Refrescar();
            catHabGui.Refrescar();
        }
    }

    class ExportarNivelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(ExplorarVentanasNivel("Save",
                    JFileChooser.FILES_AND_DIRECTORIES, "")).equals("")) {
                if (!ExportarNivel()) {
                    MostrarMensaje.showErrorMessage("Fichero no creado. Comprueba los permisos de lectura/escritura.", "Error de guardado...");
                }
            }
        }
    }

    class ImportarNivelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String file;
            if (!(file = ExplorarVentanasNivel("Load",
                    JFileChooser.OPEN_DIALOG, "")).equals("")) {
                ImportarNivel(file);
            }
        }
    }

    class NewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                caracteristicasGui.Reset();
                Esher.Reset();
                gui.Reset();
                catHabGui.Reset();
                aleatorioGui.Reset();
                Habilidad.deleteSkills();
                try {
                    culturaGui.Reset();
                    culturaGui.setVisible(false);
                    insertarPjGui.dispose();
                } catch (NullPointerException npe) {
                }
                caracteristicasGui.setVisible(false);
                catHabGui.setVisible(false);
                aleatorioGui.setVisible(false);
                adiestramientoGui.setVisible(false);
                añadirAdiestramientoGui.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String file;
            if (!(file = ExplorarVentanas("Load",
                    JFileChooser.OPEN_DIALOG, "")).equals("")) {
                CargarPersonaje(file);
            }
        }
    }

    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(ExplorarVentanas("Save",
                    JFileChooser.FILES_AND_DIRECTORIES, "")).equals("")) {
                if (!GuardarPersonaje()) {
                    MostrarMensaje.showErrorMessage("Fichero no creado. Comprueba los permisos de lectura/escritura.", "Error de guardado...");
                }
            }
        }
    }

    class PdfListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String file;
                if (!(file = ExplorarVentanasPdf("Export to PDF",
                        JFileChooser.FILES_AND_DIRECTORIES, "")).equals("")) {
                    new FichaPDF(false, file);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class PdfCombinadoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String file;
                if (!(file = ExplorarVentanasPdf("Export to PDF",
                        JFileChooser.FILES_AND_DIRECTORIES, "")).equals("")) {
                    new FichaPDFCombinada(file);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class AbrevTxtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String file;
            if (!(file = ExplorarVentanas("Export",
                    JFileChooser.FILES_AND_DIRECTORIES, "")).equals("")) {
                FichaTxt ficha = new FichaTxt();
                if (!ficha.ExportarAbreviaturaPersonaje(file)) {
                    MostrarMensaje.showErrorMessage("Fichero no creado. Comprueba los permisos de lectura/escritura.", "Error al exportar...");
                }
            }
        }
    }

    class TxtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String file;
            if (!(file = ExplorarVentanas("Export",
                    JFileChooser.FILES_AND_DIRECTORIES, "")).equals("")) {
                FichaTxt ficha = new FichaTxt();
                if (!ficha.ExportarPersonaje(file)) {
                    MostrarMensaje.showErrorMessage("Fichero no creado. Comprueba los permisos de lectura/escritura.", "Error al exportar...");
                }
            }
        }
    }

    class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class AboutBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                aboutGui.dispose();
            } catch (NullPointerException npe) {
            }
            aboutGui = new AboutBox();
            aboutGui.UpdateText(DirectorioRolemaster.LeerFicheroComoTexto("Readme.txt"));
            aboutGui.setVisible(true);
        }
    }

    class CambiarSexoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Personaje.getInstance().AsignarSexoPersonaje(gui.DevuelveSexo());
        }
    }

    class AbrirVentanaCultura implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Personaje.getInstance().cultura = gui.DevolverCulturaSeleccionada();
                new LeerCultura();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                culturaGui.dispose();
            } catch (NullPointerException npe) {
            }
            culturaGui = new CulturaGUI();
            AddCulturaListeners();
            culturaGui.setVisible(true);
            culturaGui.Refrescar();
        }
    }

    class AbrirVentanaAleatorio implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            aleatorioGui.setVisible(true);
            aleatorioGui.Refrescar();
        }
    }

    class AbrirVentanaOpciones implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            opciones.AplicaConfiguracionGuardada();
            opciones.setVisible(true);
        }
    }

    class AbrirVentanaHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                historial.dispose();
            } catch (NullPointerException npe) {
            }
            historial = new HistorialGUI();
            AddHistorialListener();
            historial.Iniciar();
            historial.setVisible(true);
        }
    }

    class AbrirVentanaTalentos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                talentos.dispose();
            } catch (NullPointerException npe) {
            }
            talentos = new TalentosGUI();
            AddInsertaTalentosListeners();
            talentos.Iniciar();
            talentos.setVisible(true);
        }
    }

    class AbrirVentanaInsertarPersonaje implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                try {
                    insertarPjGui.dispose();
                } catch (NullPointerException npe) {
                }
                insertarPjGui = new InsertarPersonajeGUI();
                AddInsertaPersonajeListeners();
                insertarPjGui.Refrescar();
                Esher.CombinarIdiomasRazaYTodos();
                insertarPjGui.setVisible(true);
                //Si no he leido antes la cultura, la leo ahora.
                if (Personaje.getInstance().rangosAficiones == 0) {
                    insertarPjGui.ActualizarCultura();
                }
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            gui.setVisible(false);
        }
    }

    class AbrirVentanaInsertarCategoria implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                insertarCatGui.dispose();
            } catch (NullPointerException npe) {
            }
            insertarCatGui = new InsertarCategoriasGUI();
            AddInsertaCategoriaListeners();
            insertarCatGui.setVisible(true);
            insertarCatGui.IniciarVentana();
        }
    }

    class HojaEnBlanco implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String file;
                if (!(file = ExplorarVentanas("Export to PDF",
                        JFileChooser.FILES_AND_DIRECTORIES, "RMFEsher.pdf")).equals("")) {
                    new FichaPDF(true, file);
                }
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * *******************************************************************
     *
     * VENTANA DE CARACTERISTICAS
     *
     ********************************************************************
     */
    /**
     * Añade los listeners a la ventana de caracteristicas.
     */
    private void AddCaracteristicaListeners() {
        caracteristicasGui.addAgilidadTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(0));
        caracteristicasGui.addConstitucionTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(1));
        caracteristicasGui.addMemoriaTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(2));
        caracteristicasGui.addRazonTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(3));
        caracteristicasGui.addAutodisciplinaTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(4));
        caracteristicasGui.addEmpatiaTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(5));
        caracteristicasGui.addIntuicionTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(6));
        caracteristicasGui.addPresenciaTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(7));
        caracteristicasGui.addRapidezTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(8));
        caracteristicasGui.addFuerzaTemporalSpinnerListener(new ChangeCaracteristicaTemporalListener(9));
        caracteristicasGui.addAceptarButtonListener(new AceptarCaracteristicasListener());
        caracteristicasGui.addRandomButtonListener(new CaracteristicasAleatoriasListener());
    }

    class ChangeCaracteristicaTemporalListener implements ChangeListener {

        int index;

        ChangeCaracteristicaTemporalListener(int value) {
            index = value;
        }

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            caracteristicasGui.ActualizaCaracteristicaTemporal(index);
        }
    }

    class AceptarCaracteristicasListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PasoCaracteristicasPjAcabado();
        }
    }

    class CaracteristicasAleatoriasListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PersonajeAleatorio pjA = new PersonajeAleatorio();
            pjA.ObtenerCaracteristicasAleatorias();
            caracteristicasGui.Refrescar();
            caracteristicasGui.setVisible(false);
            PasoCaracteristicasPjAcabado();
        }
    }

    /**
     * *******************************************************************
     *
     * MENU CULTURA
     *
     ********************************************************************
     */
    /**
     * Añade los listeners a la ventana de caracteristicas.
     */
    private void AddCulturaListeners() {
        culturaGui.addSubirArmaListener(new SubirArmaListener());
        culturaGui.addBajarArmaListener(new BajarArmaListener());
        culturaGui.addAceptarButtonListener(new AceptarListener());
        culturaGui.addAleatorioButtonListener(new AleatorioCulturaListener());
        culturaGui.addEscribirSpinnerListener(new EscritoIdiomaListener());
        culturaGui.addHablarSpinnerListener(new HabladoIdiomaListener());
        culturaGui.addIdiomasComboBoxListener(new SeleccionarIdiomaListener());
        culturaGui.addCategoriaArmasAdolescenciaListener(new SeleccionarCategoriaArmaAdolescenciaListener());
        culturaGui.addArmasAdolescenciaListener(new SeleccionarArmaAdolescenciaListener());
        culturaGui.addAficionesSpinnerListener(new AficionesListener());
        culturaGui.addAficionesComboBoxListener(new SeleccionarAficionListener());
    }

    class SubirArmaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            culturaGui.SubirArma();
        }
    }

    class BajarArmaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            culturaGui.BajarArma();
        }
    }

    class AceptarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Personaje.getInstance().ActualizarOrdenCostesArmas();
            PasoCulturaPjAcabado();
            culturaGui.setVisible(false);
        }
    }

    class HabladoIdiomaListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            culturaGui.HabladoSpinnerInRange();
            culturaGui.AsignarPuntosIdiomas();
        }
    }

    class EscritoIdiomaListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            culturaGui.EscritoSpinnerInRange();
            culturaGui.AsignarPuntosIdiomas();
        }
    }

    class SeleccionarIdiomaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            culturaGui.ActualizarIdiomaSeleccionado();
        }
    }

    class SeleccionarAficionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            culturaGui.ActualizarAficionSeleccionada();
        }
    }

    class SeleccionarCategoriaArmaAdolescenciaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                culturaGui.RellenarArmasSegunCategoria();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            culturaGui.SeleccionarRangosArmasAdolescencia();
        }
    }

    class SeleccionarArmaAdolescenciaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                culturaGui.SeleccionarRangosArmasAdolescencia();
                Personaje.getInstance().armas.SeleccionarArmasConRangosCultura(culturaGui.DevolverArmaSeleccionada(), culturaGui.DevolverCategoriaArmasSeleccionada(),
                        Personaje.getInstance().armas.DevolverRangosCulturaTipoArma(culturaGui.DevolverCategoriaArmasSeleccionada()));
                culturaGui.ActualizarAficionSeleccionada();
            } catch (NullPointerException npe) {
            } catch (ArrayIndexOutOfBoundsException aiobe) {
            }
        }
    }

    class AficionesListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            culturaGui.AficionesSpinnerInRange();
            culturaGui.AsignarPuntosAficiones();
        }
    }

    class AleatorioCulturaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PersonajeAleatorio pjA = new PersonajeAleatorio();
            Personaje.getInstance().ActualizarOrdenCostesArmas();
            pjA.ObtenerCulturaAleatoria();
            culturaGui.AleatorioAceptado();
            PasoCulturaPjAcabado();
            culturaGui.setVisible(false);
        }
    }

    /**
     * *******************************************************************
     *
     * VENTANA DE CATEGORIAS Y HABILIDADES
     *
     ********************************************************************
     */
    /**
     * Añade los actionlisteners adecuados.
     */
    private void AddCategoriasYHabilidadesListeners() {
        catHabGui.addCategoriasComboBoxListener(new CambiarCategoriaListener());
        catHabGui.addHabilidadesComboBoxListener(new CambiarHabilidadesListener());
        catHabGui.addCategoriasYHabilidadesCheckBoxListener(new NuevoRangoListener());
        catHabGui.addGeneralCheckBoxListener(new HabilidadGeneralizadaListener());
        catHabGui.addEspecializadaCheckBoxListener(new HabilidadEspecializadaListener());
        catHabGui.addAceptarButtonListener(new CerrarVentanaListener());
        catHabGui.addAleatorioButtonListener(new AlatorioHabilidadesListener());
        catHabGui.addAddButtonListener(new OtrasHabilidadesListener());
        catHabGui.addPdSpinnerListener(new OtrasHabilidadesCosteInRangeListener());
    }

    class CambiarCategoriaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            catHabGui.ActualizarHabilidadesComboBox();
            catHabGui.ActualizarCategoriaSeleccionada();
            catHabGui.ActualizarCategoriasNuevosRangos();
        }
    }

    class CambiarHabilidadesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            catHabGui.ActualizarHabilidadSeleccionada();
            catHabGui.ActualizarHabilidadesNuevosRangos();
        }
    }

    class NuevoRangoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            catHabGui.ComprarCategoriasNuevosRangos();
            catHabGui.ComprarHabilidadesNuevosRangos();
            catHabGui.ActualizarCategoriasNuevosRangos();
            catHabGui.ActualizarHabilidadesNuevosRangos();
            catHabGui.ActualizarCategoriaSeleccionada();
            catHabGui.ActualizarHabilidadSeleccionada();
            catHabGui.ActualizarPuntosDesarrollo();
            AñadirHabilidadesVinculadas(catHabGui.DevolverHabilidadSeleccionada());
            catHabGui.ActualizarHabilidadesComboBox(catHabGui.DevolverHabilidadSeleccionada().DevolverNombre());
        }
    }

    class HabilidadGeneralizadaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            catHabGui.HacerGeneralHabilidad();
        }
    }

    class HabilidadEspecializadaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            catHabGui.HacerEspecializadaHabilidad();
        }
    }

    class CerrarVentanaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.Refrescar();
            catHabGui.setVisible(false);
        }
    }

    class AlatorioHabilidadesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PersonajeAleatorio pjA = new PersonajeAleatorio();
            pjA.GastarPuntosDesarrolloDeFormaAleatoria(false);
            catHabGui.Refrescar();
            gui.Refrescar();
            catHabGui.setVisible(false);
        }
    }

    class OtrasHabilidadesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = catHabGui.DevolverNombreOtrasHabilidades();
            int pd = catHabGui.DevolverCosteOtrasHabilidades();
            if (nombre.length() > 0) {
                Personaje.getInstance().AñadirHabilidadesNoContempladas(nombre, pd);
            }
            catHabGui.LimpiarOtrasHabilidades();
            catHabGui.ActualizarPuntosDesarrollo();
        }
    }

    class OtrasHabilidadesCosteInRangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            catHabGui.PdSpinnerInRange();
        }
    }

    /**
     * *******************************************************************
     *
     * ADIESTRAMIENTO
     *
     ********************************************************************
     */
    /**
     * Añade los actionlisteners adecuados.
     */
    private void AddAdiestramientoListeners() {
        adiestramientoGui.addCategoriasListener(new CambiarCategoriaAdiestramientoListener());
        adiestramientoGui.addHabilidadesListener(new CambiarHabilidadAdiestramientoListener());
        adiestramientoGui.addHabilidadRangosSpinnerListener(new RangosHabilidadListener());
        adiestramientoGui.AddAceptarButtonListener(new AceptarAdiestramientoListener());
        adiestramientoGui.AddCancelButtonListener(new CancelarAdiestramientoListener());
        adiestramientoGui.AddAleatorioButtonListener(new AleatorioAdiestramientoListener());
        adiestramientoGui.AddCaracteristicasButtonListener(new AbrirCaracteristicasAdiestramientoListener());
    }

    class CambiarCategoriaAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            adiestramientoGui.ActualizarCategoriaSeleccionada();
            adiestramientoGui.ActualizarMaximosRangosHabilidadesTextField();
            adiestramientoGui.ActualizarRangosCategoriasTextField();
        }
    }

    class CambiarHabilidadAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            adiestramientoGui.ActualizarHabilidadSeleccionada();
        }
    }

    class RangosHabilidadListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            adiestramientoGui.RangosHabilidadesInRange();
        }
    }

    class CancelarAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.Refrescar();
            adiestramientoGui.setVisible(false);
        }
    }

    class AceptarAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            adiestramientoGui.setVisible(false);
            Personaje.getInstance().ConfirmarAdiestramiento();
            gui.Refrescar();
        }
    }

    class AleatorioAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Personaje.getInstance().adiestramiento.RepartirRangosEnCategoriasDeFormaAleatoria();
            adiestramientoGui.setVisible(false);
            Personaje.getInstance().ConfirmarAdiestramiento();
            gui.Refrescar();
        }
    }

    class AbrirCaracteristicasAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SeleccionarCaracteristica[] selecciones = new SeleccionarCaracteristica[Personaje.getInstance().adiestramiento.DevolverListaCompletaAumentoCaracteristica().size()];
            for (int i = 0; i < Personaje.getInstance().adiestramiento.DevolverListaCompletaAumentoCaracteristica().size(); i++) {
                selecciones[i] = new SeleccionarCaracteristica("Adiestramiento");
                List<String> caracteristicas = Personaje.getInstance().adiestramiento.DevolverListaCompletaAumentoCaracteristica().get(i);
                selecciones[i].RellenaNombreCaracteristicas(caracteristicas);
                AddSeleccionarCaracteristicasListeners(selecciones[i]);
                selecciones[i].setVisible(true);
            }
        }
    }

    /**
     * *******************************************************************
     *
     * AÑADIR ADIESTRAMIENTO
     *
     ********************************************************************
     */
    /**
     * Añade los actionlisteners adecuados.
     */
    private void AddAñadirAdiestramientoListeners() {
        añadirAdiestramientoGui.AddAceptarButtonListener(new AceptarAñadirAdiestramientoListener());
        añadirAdiestramientoGui.AddCancelButtonListener(new CancelarAñadirAdiestramientoListener());
        añadirAdiestramientoGui.addElegirAdiestramientoListener(new SeleccionarAdiestramientoParaAñadirListener());
    }

    class CancelarAñadirAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.Refrescar();
            añadirAdiestramientoGui.setVisible(false);
        }
    }

    class AceptarAñadirAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            añadirAdiestramientoGui.setVisible(false);
            adiestramientoGui.setVisible(true);
            adiestramientoGui.Refrescar(añadirAdiestramientoGui.DevolverAdiestramientoSeleccionado());
        }
    }

    class SeleccionarAdiestramientoParaAñadirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            añadirAdiestramientoGui.ActualizarCosteAdiestramiento();
        }
    }

    /**
     * *******************************************************************
     *
     * GENERACIÓN ALEATORIO DE PJs
     *
     ********************************************************************
     */
    /**
     * Añade los actionlisteners adecuados.
     */
    private void AddAleatorioListeners() {
        aleatorioGui.addAceptarButtonListener(new GenerarPersonajeAleatorioListener());
        aleatorioGui.addCancelarButtonListener(new CerrarAleatorioListener());
        aleatorioGui.addSliderListener(new AleatorioSliderListener());
        aleatorioGui.addSubirNivelSpinnerListener(new IncrementarNivelListener());
        aleatorioGui.addAplicarInteligenciaCheckBox(new AplicarInteligencia());
        aleatorioGui.addRangosCategoriasSpinnerListener(new InsertaRangoCategoriaSugeridaListener());
        aleatorioGui.addRangosHabilidadSpinnerListener(new InsertaRangoHabilidadSugeridaListener());
    }

    class GenerarPersonajeAleatorioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Personaje.getInstance().profesion = gui.DevolverProfesionSeleccionada();
            Personaje.getInstance().raza = gui.DevolverRazaSeleccionada();
            Personaje.getInstance().cultura = gui.DevolverCulturaSeleccionada();
            PersonajeAleatorio pjA = new PersonajeAleatorio();
            pjA.ObtenerPersonajeAleatorio(aleatorioGui.DevolverNivelFinal());
            gui.SeleccionarReinoMagia();
            //Interfaz gráfica
            caracteristicasGui.DeshabilitarCambiosEnCaracteristicas();
            try {
                culturaGui.dispose();
                gui.DisableCultura();
            } catch (NullPointerException npe) {
            }
            gui.ActualizarPuntosDesarrollo();
            gui.PrimerPasoGeneracionPJ();
            gui.SegundoPasoGeneracionPJ();
            gui.TercerPasoGeneracionPJ();
            gui.MenuCambiosPJ(false);
            gui.ActivarMenuInsertar(true);
            catHabGui.Iniciar();
            aleatorioGui.setVisible(false);
        }
    }

    class CerrarAleatorioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            aleatorioGui.setVisible(false);
        }
    }

    class AleatorioSliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            aleatorioGui.ObtenerValorAleatorio();
        }
    }

    class IncrementarNivelListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            aleatorioGui.LevelInRange();
        }
    }

    class AplicarInteligencia implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Esher.inteligencia = !Esher.inteligencia;
        }
    }

    class InsertaRangoCategoriaSugeridaListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            aleatorioGui.ActualizarRangosCategoria();
        }
    }

    class InsertaRangoHabilidadSugeridaListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            aleatorioGui.ActualizarRangosHabilidad();
        }
    }

    /**
     * *******************************************************************
     *
     * VENTANA OPCIONES
     *
     ********************************************************************
     */
    /**
     * Configura las opciones de la generación de personajes para adaptarlo a
     * las necesidades de la partida.
     */
    private void AddOpcionesListener() {
        opciones.addCerrarButtonListener(new CerrarOpcionesListener());
        opciones.addOrdenHabilidadesListener(new CambiarOrdenHabilidadesListener());
    }

    class CerrarOpcionesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Esher.GuardarConfiguracion();
            opciones.setVisible(false);
            new NewListener();
        }
    }

    class CambiarOrdenHabilidadesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Esher.habilidadesOrdenadasEnPDF = opciones.DevolverOrdenLexicografico();
        }
    }

    /**
     * *******************************************************************
     *
     * VENTANA HISTORIAL
     *
     ********************************************************************
     */
    /**
     * Configura las opciones de la generación de personajes para adaptarlo a
     * las necesidades de la partida.
     */
    private void AddHistorialListener() {
        historial.addCategoriaCheckBoxListener(new CategoriaHistorial());
        historial.addHabilidadCheckBoxListener(new HabilidadHistorial());
        historial.addCategoriasComboBoxListener(new CambiarCategoriaHistorialListener());
        historial.addHabilidadesComboBoxListener(new CambiarHabilidadesHistorialListener());
        historial.addCerrarListener(new CerrarHistorialListener());
        historial.addAumentoCaracteristicasButtonListener(new AbrirAumentoCaracteristicas());
        historial.addObjetoButtonListener(new AbrirObjetosHistorial());
    }

    class CategoriaHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            historial.GastarPuntoHistorialEnCategoria();
        }
    }

    class HabilidadHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            historial.GastarPuntoHistorialEnHabilidad();
        }
    }

    class CambiarCategoriaHistorialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            historial.ActualizarHabilidadesComboBox();
            historial.ActualizarCategoriaCheckBox();
            historial.ActualizarHabilidadCheckBox();
        }
    }

    class CambiarHabilidadesHistorialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            historial.ActualizarHabilidadCheckBox();
        }
    }

    class CerrarHistorialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.Refrescar();
            historial.setVisible(false);
        }
    }

    class AbrirAumentoCaracteristicas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((Personaje.getInstance().DevolverPuntosHistoriaTotales() - Personaje.getInstance().DevolverPuntosHistorialGastados()) > 0) {
                try {
                    selecCar.dispose();
                } catch (NullPointerException npe) {
                }
                selecCar = new SeleccionarCaracteristica("Historial");
                selecCar.setVisible(true);
                selecCar.RellenaCaracteristicas();
                AddSeleccionarCaracteristicasListeners();
            }
        }
    }

    class AbrirObjetosHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((Personaje.getInstance().DevolverPuntosHistoriaTotales() - Personaje.getInstance().DevolverPuntosHistorialGastados()) > 0) {
                try {
                    objetosMagicos.dispose();
                } catch (NullPointerException npe) {
                }
                objetosMagicos.setVisible(true);
            }

        }
    }

    /**
     * *******************************************************************
     *
     * VENTANA OBJETOS MAGICOS
     *
     ********************************************************************
     */
    /**
     * Configura las opciones de la generación de personajes para adaptarlo a
     * las necesidades de la partida.
     */
    private void AddObjetoMagicoListener() {
        objetosMagicos.addCategoriasObjetoComboBoxListener(new CambiarCategoriaObjetolListener());
        objetosMagicos.addHabilidadesObjetosComboBoxListener(new CambiarHabilidadesObjetoListener());
        objetosMagicos.addBonusCategoriaObjetoSpinnerListener(new BonusObjetoHistorial());
        objetosMagicos.addBonusHabilidadObjetoSpinnerListener(new BonusObjetoHistorial());
        objetosMagicos.addBorrarButtonListener(new BorrarObjetoHistorial());
        objetosMagicos.addObjetosComboBoxListener(new CambiarObjetoHistorial());
        objetosMagicos.addCerrarButtonListener(new CerrarObjetoHistorial());
    }

    class CambiarCategoriaObjetolListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            objetosMagicos.ActualizarCategoriaBonusSpinner();
            objetosMagicos.ActualizarHabilidadesObjetosComboBox();
        }
    }

    class CambiarHabilidadesObjetoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (objetosMagicos.NumeroObjetosHabilidades() > 0) {
                objetosMagicos.ActualizarHabilidadBonusSpinner();
            }
        }
    }

    class BonusObjetoHistorial implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            objetosMagicos.BonusObjeto();
        }
    }

    class BorrarObjetoHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String obj = objetosMagicos.NombreObjetoSeleccionado();
            objetosMagicos.BorrarObjeto();
            Personaje.getInstance().BorrarObjeto(obj);
            objetosMagicos.ActualizarListadoObjetos();
        }
    }

    class CerrarObjetoHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            objetosMagicos.setVisible(false);
            historial.ActualizarPuntosHistorial();
            gui.Refrescar();
        }
    }

    class CambiarObjetoHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            objetosMagicos.SeleccionarObjetoParaEditar();
            objetosMagicos.ActualizarCategoriaBonusSpinner();
            objetosMagicos.ActualizarHabilidadBonusSpinner();
        }
    }

    /**
     * *******************************************************************
     *
     * INSERTA PERSONAJE
     *
     ********************************************************************
     */
    /**
     * Añade los listeners a la ventana de caracteristicas.
     */
    private void AddInsertaPersonajeListeners() {
        insertarPjGui.addAgilidadTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(0));
        insertarPjGui.addConstitucionTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(1));
        insertarPjGui.addMemoriaTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(2));
        insertarPjGui.addRazonTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(3));
        insertarPjGui.addAutodisciplinaTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(4));
        insertarPjGui.addEmpatiaTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(5));
        insertarPjGui.addIntuicionTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(6));
        insertarPjGui.addPresenciaTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(7));
        insertarPjGui.addRapidezTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(8));
        insertarPjGui.addFuerzaTemporalSpinnerListener(new InsertaCaracteristicaTemporalListener(9));
        insertarPjGui.addAgilidadPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(0));
        insertarPjGui.addConstitucionPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(1));
        insertarPjGui.addMemoriaPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(2));
        insertarPjGui.addRazonPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(3));
        insertarPjGui.addAutodisciplinaPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(4));
        insertarPjGui.addEmpatiaPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(5));
        insertarPjGui.addIntuicionPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(6));
        insertarPjGui.addPresenciaPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(7));
        insertarPjGui.addRapidezPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(8));
        insertarPjGui.addFuerzaPotencialSpinnerListener(new InsertaCaracteristicaPotencialListener(9));
        insertarPjGui.addAceptarButtonListener(new InsertaPersonajeListener());
        insertarPjGui.addCategoriasComboBoxListener(new CambiarCategoriaInsertaListener());
        insertarPjGui.addHabilidadesComboBoxListener(new CambiarHabilidadesInsertaListener());
        insertarPjGui.addRangosCategoriasSpinnerListener(new InsertaRangoCategoriaListener());
        insertarPjGui.addRangosHabilidadSpinnerListener(new InsertaRangoHabilidadListener());
        insertarPjGui.addRazasComboBoxListener(new InsertaRazaListener());
        insertarPjGui.addProfesionComboBoxListener(new InsertaProfesionListener());
        insertarPjGui.addAñadirAdiestramientoButtonListener(new InsertaAdiestramientoListener());
        insertarPjGui.addCulturaComboBoxListener(new InsertaCulturaListener());
        insertarPjGui.addReinosComboBoxListener(new InsertaReinoListener());
        insertarPjGui.addNivelSpinnerListener(new InsertarNivelListener());
        insertarPjGui.addBonusCategoriaSpinnerListener(new InsertarBonusCategoriaListener());
        insertarPjGui.addBonusHabilidadSpinnerListener(new InsertarBonusHabilidadListener());
        insertarPjGui.addGeneralCheckBoxListener(new InsertaHabilidadGeneralizadaListener());
        insertarPjGui.addEspecializadaCheckBoxListener(new InsertaHabilidadEspecializadaListener());
        insertarPjGui.addNombreTextFieldListener(new InsertaNombreListener());
        insertarPjGui.addSubirArmaListener(new InsertaSubirArmaListener());
        insertarPjGui.addBajarArmaListener(new InsertaBajarArmaListener());
        insertarPjGui.addSexoListener(new InsertarSexoListener());
        insertarPjGui.addTipoHabilidadListener(new InsertarTipoHabilidadListener());
        insertarPjGui.addCategoriaCheckBoxListener(new InsertaCategoriaHistorial());
        insertarPjGui.addHabilidadCheckBoxListener(new InsertaHabilidadHistorial());
        insertarPjGui.addCategoriasHistorialComboBoxListener(new InsertaCambiarCategoriaHistorialListener());
        insertarPjGui.addHabilidadesHistorialComboBoxListener(new InsertaCambiarHabilidadesHistorialListener());
        insertarPjGui.addCategoriasObjetoComboBoxListener(new InsertaCambiarCategoriaObjetolListener());
        insertarPjGui.addHabilidadesObjetosComboBoxListener(new InsertaCambiarHabilidadesObjetoListener());
        insertarPjGui.addBonusCategoriaObjetoSpinnerListener(new InsertaBonusObjeto());
        insertarPjGui.addBonusHabilidadObjetoSpinnerListener(new InsertaBonusObjeto());
        insertarPjGui.addBorrarButtonListener(new BorrarObjeto());
        insertarPjGui.addObjetosComboBoxListener(new CambiarObjeto());
        insertarPjGui.addSeleccionarTalentoCheckBoxListener(new InsertarTalentoListener());
        insertarPjGui.addInsertarTalentoComboBoxListener(new CambiarTalentoinsertarListener());
    }

    class InsertaCaracteristicaTemporalListener implements ChangeListener {

        int index;

        InsertaCaracteristicaTemporalListener(int value) {
            index = value;
        }

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.ActualizaCaracteristicaTemporal(index);
            insertarPjGui.ActualizarValorBasico();
            insertarPjGui.ActualizarTotal();
        }
    }

    class InsertaCaracteristicaPotencialListener implements ChangeListener {

        int index;

        InsertaCaracteristicaPotencialListener(int value) {
            index = value;
        }

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.ActualizaCaracteristicaPotencial(index);
        }
    }

    class InsertaPersonajeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.ObtenerNombre();
            insertarPjGui.setVisible(false);
            Personaje.getInstance().HacerFijoEspecialesRaza(); //<------------ Para que cambie las habilidades y se muestren antes de refrescar GUI.
            gui.Refrescar();
            //Personaje.getInstance().HacerFijoEspecialesRaza(); //<------------ Al refrescar GUI puede volver a leer la raza, por lo que hay que insertarlo otra vez.
            gui.ActivarMenuArchivo();
            gui.ActivarMenuAleatorio(false);
            gui.MenuCambiosPJ(false);
            gui.ActivarMenuGenerar(false);
            gui.setVisible(true);
        }
    }

    class CambiarCategoriaInsertaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.ActualizarHabilidadesComboBox();
            insertarPjGui.ActualizarCategoriaSeleccionada();
        }
    }

    class CambiarHabilidadesInsertaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.ActualizarHabilidadSeleccionada();
        }
    }

    class InsertaRangoCategoriaListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.ActualizarRangosCategoria();
        }
    }

    class InsertaRangoHabilidadListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.ActualizarRangosHabilidad();
        }
    }

    class InsertaRazaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Esher.aleatorio = false;
                if (!insertarPjGui.refrescando) {
                    Personaje.getInstance().raza = insertarPjGui.DevolverRazaSeleccionada();
                    new LeerRaza();
                    insertarPjGui.RellenarCulturas();
                    insertarPjGui.RellenaProfesion();
                    insertarPjGui.RefrescaCaracteristicas();
                    Esher.CombinarIdiomasRazaYTodos();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class InsertaProfesionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!insertarPjGui.refrescando) {
                    Personaje.getInstance().profesion = insertarPjGui.DevolverProfesionSeleccionada();
                    new LeerProfesion(true, false);
                    insertarPjGui.RellenarReinosDeMagia();
                    Personaje.getInstance().reino = insertarPjGui.DevolverReinoDeMagia();
                    insertarPjGui.RefrescaCaracteristicas();
                    insertarPjGui.RellenarAdiestramientos();
                    Personaje.getInstance().ObtenerMagia();
                    insertarPjGui.ActualizarTextoHabilidades();
                }
                Personaje.getInstance().ActualizarOrdenCostesArmas();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class InsertarNivelListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.NivelInRange();
        }
    }

    class InsertarBonusCategoriaListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.BonusCategoriaInRange();
        }
    }

    class InsertarBonusHabilidadListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.BonusHabilidadInRange();
        }
    }

    class InsertaReinoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!insertarPjGui.refrescando) {
                    Personaje.getInstance().reino = insertarPjGui.DevolverReinoDeMagia();
                    Personaje.getInstance().ObtenerMagia();
                    Personaje.getInstance().ObtenerMediaCostePuntosDePoder();
                    insertarPjGui.IniciaHabilidades();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class InsertaCulturaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Personaje.getInstance().cultura = insertarPjGui.DevolverCulturaSeleccionada();
            new LeerCultura();
            insertarPjGui.ActualizarTextoHabilidades();
        }
    }

    class InsertaAdiestramientoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!Personaje.getInstance().adiestramientosAntiguos.contains(insertarPjGui.DevolverAdiestramientoSeleccionado())) {
                    LeerAdiestramientos adiestramiento = new LeerAdiestramientos(insertarPjGui.DevolverAdiestramientoSeleccionado(), true);
                    Personaje.getInstance().ConfirmarAdiestramiento();
                    insertarPjGui.RellenaOpcionesAdiestramientoEscogidas();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class InsertaNombreListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.InsertaNombre();
        }
    }

    class InsertaSubirArmaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.SubirArma();
        }
    }

    class InsertaBajarArmaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.BajarArma();
        }
    }

    class InsertarSexoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Personaje.getInstance().AsignarSexoPersonaje(insertarPjGui.DevuelveSexo());
        }
    }

    class InsertarTipoHabilidadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.TipoHabilidad();
        }
    }

    class InsertaCategoriaHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.GastarPuntoHistorialEnCategoria();
        }
    }

    class InsertaHabilidadHistorial implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.GastarPuntoHistorialEnHabilidad();
        }
    }

    class InsertaCambiarCategoriaHistorialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.ActualizarHabilidadesHistorialComboBox();
            insertarPjGui.ActualizarCategoriaCheckBox();
            insertarPjGui.ActualizarHabilidadCheckBox();
        }
    }

    class InsertaCambiarHabilidadesHistorialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.ActualizarHabilidadCheckBox();
        }
    }

    class InsertaCambiarCategoriaObjetolListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.ActualizarCategoriaBonusSpinner();
            insertarPjGui.ActualizarHabilidadesObjetosComboBox();
        }
    }

    class InsertaCambiarHabilidadesObjetoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (insertarPjGui.NumeroObjetosHabilidades() > 0) {
                insertarPjGui.ActualizarHabilidadBonusSpinner();
            }
        }
    }

    class InsertaHabilidadGeneralizadaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.HacerGeneralHabilidad();
        }
    }

    class InsertaHabilidadEspecializadaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.HacerEspecializadaHabilidad();
        }
    }

    class InsertaBonusObjeto implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            insertarPjGui.BonusObjeto();
        }
    }

    class BorrarObjeto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String obj = insertarPjGui.NombreObjetoSeleccionado();
            insertarPjGui.BorrarObjeto();
            Personaje.getInstance().BorrarObjeto(obj);
            insertarPjGui.ActualizarListadoObjetos();
        }
    }

    class InsertarTalentoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.SeleccionaTalento();
        }
    }

    class CambiarTalentoinsertarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.ActualizarTalentoSeleccionado();
        }
    }

    class CambiarObjeto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarPjGui.SeleccionarObjetoParaEditar();
            insertarPjGui.ActualizarCategoriaBonusSpinner();
            insertarPjGui.ActualizarHabilidadBonusSpinner();
        }
    }

    /**
     * *******************************************************************
     *
     * INSERTA CATEGORIAS
     *
     ********************************************************************
     */
    /**
     * Inserta los listeners.
     */
    private void AddInsertaCategoriaListeners() {
        insertarCatGui.addAñadirCategoriaButtonListener(new AñadirCategoriaListener());
        insertarCatGui.addAñadirHabilidadButtonListener(new AñadirHabilidadListener());
        insertarCatGui.addCerrarButtonListener(new CerrarButtonListener());
    }

    class AñadirCategoriaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (insertarCatGui.CamposCategoriaCorrectos()) {
                    Categoria cat = Categoria.getCategory(insertarCatGui.DevolverNombreCategoria(),
                            insertarCatGui.DevolverAbreviatura(),
                            insertarCatGui.DevolverAbreviaturaCaracteristicas(),
                            insertarCatGui.DevolverTipo(), null);
                    cat.CambiarCosteRango(Personaje.getInstance().ConvertirStringCosteEnIntCoste(insertarCatGui.DevolverCosteCategoria()));
                    Personaje.getInstance().AñadirCategoria(cat);
                    Personaje.getInstance().OrdenarCategorias();
                    insertarCatGui.Refrescar();
                    try {
                        insertarPjGui.ActualizarCategoriasComboBox();
                        catHabGui.ActualizarCategoriasComboBox();
                    } catch (NullPointerException npe) {
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class AñadirHabilidadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre(insertarCatGui.DevolverCategoriaHabilidad());
            cat.AddHabilidad(insertarCatGui.DevolverNombreHabilidad());

            //Almacenar la nueva habilidad.
            if (Personaje.getInstance().DevolverCategoriaNuevaDeNombre(cat.DevolverNombre()) == null) {
                Personaje.getInstance().categoriasNuevas.add(cat);
            }
            cat = Personaje.getInstance().DevolverCategoriaNuevaDeNombre(insertarCatGui.DevolverCategoriaHabilidad());
            if (cat != null) {
                cat.AddHabilidad(insertarCatGui.DevolverNombreHabilidad());
            }
            MostrarMensaje.showMessage("Habilidad insertada con exito.", "Insertar Habilidades", JOptionPane.INFORMATION_MESSAGE);
            insertarCatGui.LimpiarHabilidad();
        }
    }

    class CerrarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            insertarCatGui.dispose();
            gui.Refrescar();
        }
    }

    /**
     * *******************************************************************
     *
     * TALENTOS
     *
     ********************************************************************
     */
    /**
     * Inserta los listeners
     */
    private void AddInsertaTalentosListeners() {
        talentos.addCategoriasComboBoxListener(new CambiarTalentoListener());
        talentos.addSeleccionarCheckBoxListener(new SeleccionarTalentoListener());
        talentos.addCerrarCheckBoxListener(new CerrarTalentoListener());
    }

    class CambiarTalentoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            talentos.ActualizarTalentoSeleccionado();
        }
    }

    class SeleccionarTalentoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            talentos.SeleccionaTalento();
        }
    }

    class CerrarTalentoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.Refrescar();
            talentos.setVisible(false);
        }
    }

    /**
     * *******************************************************************
     *
     * SELECCIONAR CARACTERISTICAS
     *
     ********************************************************************
     */
    /**
     * Inserta los listeners.
     */
    private void AddSeleccionarCaracteristicasListeners() {
        selecCar.addAceptarListener(new AceptarSubidaCaracteristicasListener(selecCar));
    }

    private void AddSeleccionarCaracteristicasListeners(SeleccionarCaracteristica tmp_selCar) {
        tmp_selCar.addAceptarListener(new AceptarSubidaCaracteristicasListener(tmp_selCar));
    }

    class AceptarSubidaCaracteristicasListener implements ActionListener {

        SeleccionarCaracteristica selCar;

        AceptarSubidaCaracteristicasListener(SeleccionarCaracteristica tmp_selCar) {
            selCar = tmp_selCar;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String modo = selCar.DevuelveModo();
            if (modo.equals("Historial")) {
                Caracteristica car = selCar.DevuelveCaracteristicaSeleccionada();
                if (car != null) {
                    car.SubirNivelCaracteristica();
                    //Personaje.getInstance().caracteristicas.ActualizarCaracteristica(car);
                    Personaje.getInstance().puntoshistorialCaracteristicas++;
                }
                selCar.dispose();
                historial.ActualizarPuntosHistorial();
            } else if (modo.equals("Adiestramiento")) {
                Caracteristica car = selCar.DevuelveCaracteristicaSeleccionada();
                if (car != null) {
                    Personaje.getInstance().adiestramiento.AñadirAumentoCaracteristicaSeleccionadaAdiestramiento(car.DevolverAbreviatura());
                }
                selCar.dispose();
            }
            gui.Refrescar();
        }
    }

    /**
     * *******************************************************************
     *
     * SELECCIONAR HABILIDADES
     *
     ********************************************************************
     */
    /**
     * Inserta los listeners.
     */
    private void AddSeleccionarHabilidadesListeners() {
        selecHab.addHabilidadesCheckBoxListener(new SeleccionarHabilidadListener());
    }

    class SeleccionarHabilidadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            selecHab.AñadirEliminarHabilidadSeleccionada();
            selecHab.ActualizaHabilidadesRestantes();
            Habilidad hab = catHabGui.DevolverHabilidadSeleccionada();
            catHabGui.ActualizarHabilidadesComboBox(hab.DevolverNombre());
        }
    }

    /**
     * *******************************************************************
     *
     * FUNCIONES GENERICAS
     *
     ********************************************************************
     */
    /**
     * Da por concluidas la selección de características.
     */
    private void PasoCaracteristicasPjAcabado() {
        try {
            new LeerRaza();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        gui.PrimerPasoGeneracionPJ();
        Personaje.getInstance().caracteristicas.ObtenerPotenciales();
        Personaje.getInstance().caracteristicas.ObtenerApariencia();
        caracteristicasGui.ActualizarPotenciales();
        caracteristicasGui.ActualizarApariencia();
        caracteristicasGui.DeshabilitarCambiosEnCaracteristicas();
        Personaje.getInstance().CalcularPuntosDesarrollo();
        gui.ActualizarPuntosDesarrollo();
        Personaje.getInstance().HacerFijoEspecialesRaza();
        Personaje.getInstance().ObtenerMagia();
    }

    private void PasoCulturaPjAcabado() {
        try {
            culturaGui.SegundoPasoPjAcabado();
        } catch (NullPointerException npe) {
        }
        gui.SegundoPasoGeneracionPJ();
        TercerPasoPjAcabado();
    }

    private void TercerPasoPjAcabado() {
        //Permitimos que se siga con el siguiente paso de la generación del personaje.
        gui.TercerPasoGeneracionPJ();
        Esher.CombinarIdiomasRazaYTodos();  //Siempre despues de gui.TercerPasoGeneracionPJ(); o pierde otra vez los idiomas.
        catHabGui.Iniciar();
    }

    private void AñadirHabilidadesVinculadas(Habilidad hab) {
        if (hab.rangos == 0 && hab.nuevosRangos > 0) {
            hab.categoriaPadre.AddHabilidades(hab.habilidadesNuevas);
            if (hab.habilidadesNuevasPosibles.size() > 0) {
                selecHab.Refrescar(hab, hab.habilidadesNuevasPosibles, 1);
                selecHab.setVisible(true);
            }
        }
        if (hab.rangos == 0 && hab.nuevosRangos == 0) {
            hab.categoriaPadre.BorrarHabilidades(hab.habilidadesNuevas);
            hab.categoriaPadre.BorrarHabilidades(hab.habilidadesNuevasPosibles);
        }
    }
}

class RMFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        String filename = file.getName();
        return file.isDirectory() || filename.endsWith(".rlm");
    }

    @Override
    public String getDescription() {
        return "Libro de Esher (PJ)";
    }
}

class NivelFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        String filename = file.getName();
        return file.isDirectory() || filename.endsWith(".nvl");
    }

    @Override
    public String getDescription() {
        return "Libro de Esher (Nivel)";
    }
}

class PdfFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        String filename = file.getName();
        return file.isDirectory() || filename.endsWith(".pdf");
    }

    @Override
    public String getDescription() {
        return "Portable Document Format";
    }
}

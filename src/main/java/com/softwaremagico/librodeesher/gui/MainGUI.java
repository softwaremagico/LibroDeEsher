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

import com.softwaremagico.librodeesher.Esher;
import com.softwaremagico.librodeesher.FichaTxt;
import com.softwaremagico.librodeesher.Magia;
import com.softwaremagico.librodeesher.Personaje;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author  jorge
 */
public final class MainGUI extends javax.swing.JFrame {

    public boolean update = true;

    /** Creates new form MainGUI */
    public MainGUI() throws Exception {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        setIconImage(new ImageIcon(this.getClass().getResource("/librodeesher.png")).getImage());
        RellenaRazas();
        Personaje.getInstance().raza = DevolverRazaSeleccionada();
        RellenaProfesion();
        RellenarCulturas();
        RellenarReinosDeMagia();
        //CambiaInicioPj();
    }

    public void ActualizarPj() {
        update = false;
        //Seleccionar Nombre, Profesion, Raza, etc...
        RazasComboBox.setSelectedItem(Personaje.getInstance().raza);
        CulturasComboBox.setSelectedItem(Personaje.getInstance().cultura);
        ProfesionesComboBox.setSelectedItem(Personaje.getInstance().profesion);
        ReinosComboBox.setSelectedItem(Personaje.getInstance().reino);
        Refrescar();
        update = true;
    }

    public void Refrescar() {
        ActualizarTextoCaracteristicas();
        ActualizarTextoHabilidades();
        ActualizarPuntosDesarrollo();
        ActualizarNombre();
        try {
            CambiarNivelSeleccionado(Personaje.getInstance().nivel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        RellenarOpcionesAdiestramiento();
        /*SeleccionarProfesion();
        SeleccionarReinoMagia();
        SeleccionarRaza();*/
        SeleccionarSexo();
    }

    public boolean ProfesionNoLeida() {
        return ProfesionesComboBox.isEnabled();
    }

    public void Reset() throws Exception {
        RellenaRazas();
        RellenaProfesion();
        RellenarCulturas();
        RellenarReinosDeMagia();
        SaveMenuItem.setEnabled(false);
        CulturaMenuItem.setEnabled(false);
        ExportMenu.setEnabled(false);
        RazasComboBox.setEnabled(true);
        CulturasComboBox.setEnabled(true);
        ProfesionesComboBox.setEnabled(true);
        ReinosComboBox.setEnabled(true);
        HabilidadesMenuItem.setEnabled(false);
        AdiestramientoMenuItem.setEnabled(false);
        AdiestramientoTextField.setText("");
        GenerarAleatorioMenuItem.setEnabled(true);
        VaronRadioButton.setEnabled(true);
        MujerRadioButton.setEnabled(true);
        SubirButton.setEnabled(false);
        CaracteristicasTextArea.setText("");
        HabilidadesTextArea.setText("");
        PuntosDesarrolloTextField.setText("");
        NivelTextField.setText("1");
        OpcionesMenuItem.setEnabled(true);
        HistorialMenuItem.setEnabled(false);
        TalentosMenuItem.setEnabled(false);
        NombreTextField.setText("");
        ActivarMenuGenerar(true);
        GenerarAleatorioMenuItem.setEnabled(true);
        AleatorioMenu.setEnabled(true);
        VaronRadioButton.setEnabled(true);
        MujerRadioButton.setEnabled(true);
        InsertarPjMenu.setEnabled(true);
        NivelMenu.setEnabled(false);
        ExportarNivelMenuItem.setEnabled(false);
        ExportarNivelMenuItem2.setEnabled(false);
    }

    public void SubirNiveles() {
        CaracteristicasMenuItem.setEnabled(true);
        AdiestramientoMenuItem.setEnabled(true);
        HabilidadesMenuItem.setEnabled(true);
        CulturaMenuItem.setEnabled(false);
        HistorialMenuItem.setEnabled(false);
        TalentosMenuItem.setEnabled(true);
        VaronRadioButton.setEnabled(false);
        MujerRadioButton.setEnabled(false);
        ActivarMenuGenerar(true);
        ExportarNivelMenuItem.setEnabled(true);
        ExportarNivelMenuItem2.setEnabled(true);
        //ImportarNivelMenuItem.setEnabled(true);
        Refrescar();
    }

    private void ObtenerNombre() {
        if (NombreTextField.getText().equals("")) {
            Personaje.getInstance().ObtenerNombrePersonaje();
        } else {
            Personaje.getInstance().AsignarNombreCompleto(NombreTextField.getText());
        }
        NombreTextField.setText(Personaje.getInstance().DevolverNombreCompleto());
    }

    public void MenuCambiosPJ(boolean value) {
        RazasComboBox.setEnabled(value);
        ProfesionesComboBox.setEnabled(value);
        AleatorioMenu.setEnabled(value);
        ReinosComboBox.setEnabled(value);
        CulturasComboBox.setEnabled(value);
        SubirButton.setEnabled(!value);
        VaronRadioButton.setEnabled(value);
        MujerRadioButton.setEnabled(value);
    }

    public void PrimerPasoGeneracionPJ() {
        ObtenerNombre();
        ActualizarTextoCaracteristicas();
        Personaje.getInstance().raza = RazasComboBox.getSelectedItem().toString();
        Personaje.getInstance().profesion = ProfesionesComboBox.getSelectedItem().toString();
        RazasComboBox.setEnabled(false);
        ProfesionesComboBox.setEnabled(false);
        AleatorioMenu.setEnabled(false);
        CulturaMenuItem.setEnabled(true);
        InsertarPjMenu.setEnabled(false);
    }

    public void DisableCultura() {
        CulturaMenuItem.setEnabled(false);
    }

    public void SegundoPasoGeneracionPJ() {
        ActivarMenuArchivo();
        try {
            Personaje.getInstance().cultura = CulturasComboBox.getSelectedItem().toString();
        } catch (NullPointerException npe) {
            MostrarError.showErrorMessage("No existe cultura asociada.", "Leer Raza");
        }
        CulturasComboBox.setEnabled(false);
        AdiestramientoMenuItem.setEnabled(true);
        VaronRadioButton.setEnabled(false);
        MujerRadioButton.setEnabled(false);
        ReinosComboBox.setEnabled(false);
        SubirButton.setEnabled(true);
        ActivarInsertarCategoria(true);
        Refrescar();
    }

    public void TercerPasoGeneracionPJ() {
        HabilidadesMenuItem.setEnabled(true);
        CulturaMenuItem.setEnabled(false);
        HistorialMenuItem.setEnabled(true);
        TalentosMenuItem.setEnabled(true);
        InsertarPjMenu.setEnabled(true);
        NivelMenu.setEnabled(true);
        ExportarNivelMenuItem.setEnabled(false);
        ExportarNivelMenuItem2.setEnabled(false);
        Refrescar();
    }

    public void GUIdePersonajeYaInsertado() {
        RazasComboBox.setEnabled(false);
        ProfesionesComboBox.setEnabled(false);
        AleatorioMenu.setEnabled(false);
        ActivarMenuArchivo();
        CulturasComboBox.setEnabled(false);
        AdiestramientoMenuItem.setEnabled(true);
        VaronRadioButton.setEnabled(false);
        MujerRadioButton.setEnabled(false);
        ReinosComboBox.setEnabled(false);
        SubirButton.setEnabled(true);
        ActivarInsertarCategoria(true);
        HabilidadesMenuItem.setEnabled(true);
        CulturaMenuItem.setEnabled(false);
        HistorialMenuItem.setEnabled(true);
        TalentosMenuItem.setEnabled(true);
        InsertarPjMenu.setEnabled(true);
        NivelMenu.setEnabled(true);
        ExportarNivelMenuItem.setEnabled(false);
        ExportarNivelMenuItem2.setEnabled(false);
        ActivarMenuInsertar(true);
        SeleccionarReinoMagia();
        Refrescar();
    }

    public  void ActivarMenuArchivo() {
        SaveMenuItem.setEnabled(true);
        ExportMenu.setEnabled(true);
    }

    public  void ActivarMenuGenerar(boolean value) {
        PersonajeMenu.setEnabled(value);
        if (Personaje.getInstance().nivel > 1) {
            HistorialMenuItem.setEnabled(false);
            TalentosMenuItem.setEnabled(false);
            CulturaMenuItem.setEnabled(false);
        }
    }

    public  void ActivarMenuAleatorio(boolean value) {
        AleatorioMenu.setEnabled(value);
    }

    public  void ActivarMenuInsertar(boolean value) {
        InsertarPjMenu.setEnabled(value);
    }

    void ActivarMenuCaracteristicas(boolean value) {
        CaracteristicasMenuItem.setEnabled(value);
    }

    private void RellenaRazas() throws Exception {
        List<String> razas = Esher.getInstance().RazasDisponibles();
        razas = Esher.getInstance().OrdenarLista(razas);
        RazasComboBox.removeAllItems();
        for (int i = 0; i < razas.size(); i++) {
            RazasComboBox.addItem(razas.get(i));
        }
        RazasComboBox.setSelectedItem(Personaje.getInstance().raza);
    }

    public void RellenaProfesion() throws Exception {
        List<String> profesiones = Esher.getInstance().ProfesionesDisponibles();
        profesiones = Esher.getInstance().OrdenarLista(profesiones);
        ProfesionesComboBox.removeAllItems();
        for (int i = 0; i < profesiones.size(); i++) {
            ProfesionesComboBox.addItem(profesiones.get(i));
        }
        ProfesionesComboBox.setSelectedItem(Personaje.getInstance().profesion);
    }

    public void RellenarCulturas() throws Exception {
        List<String> culturas = Esher.getInstance().CulturasDisponibles();
        culturas = Esher.getInstance().OrdenarLista(culturas);
        CulturasComboBox.removeAllItems();
        for (int i = 0; i < culturas.size(); i++) {
            CulturasComboBox.addItem(culturas.get(i));
        }
        CulturasComboBox.setSelectedItem(Personaje.getInstance().cultura);
    }

    public void RellenarReinosDeMagia() throws Exception {
        Magia magia = new Magia();
        List<String> reinos = magia.ObtenerReinoDisponible();
        ReinosComboBox.removeAllItems();
        for (int i = 0; i < reinos.size(); i++) {
            ReinosComboBox.addItem(reinos.get(i));
        }
        try {
            ReinosComboBox.setSelectedItem(Personaje.getInstance().reinos.get(0));
        } catch (IndexOutOfBoundsException iofe) {
        }
        try {
            Personaje.getInstance().reino = ReinosComboBox.getSelectedItem().toString();
        } catch (NullPointerException npe) {
            Personaje.getInstance().reino = "";
        }
    }

    public void SeleccionarReinoMagia() {
        try {
            String reino = Personaje.getInstance().reino;
            RellenarReinosDeMagia();
            ReinosComboBox.setSelectedItem(reino);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void RellenarOpcionesAdiestramiento() {
        String adiestramientos = "";
        for (int i = 0; i < Personaje.getInstance().adiestramientosAntiguos.size(); i++) {
            if (i > 0) {
                adiestramientos += ", ";
            }
            adiestramientos += Personaje.getInstance().adiestramientosAntiguos.get(i);
        }
        AdiestramientoTextField.setText(adiestramientos);
    }

    public void SeleccionarProfesion() {
        ProfesionesComboBox.setSelectedItem(Personaje.getInstance().profesion);
    }

    private void SeleccionarRaza() {
        RazasComboBox.setSelectedItem(Personaje.getInstance().raza);
    }

    private void SeleccionarSexo() {
        if (Personaje.getInstance().sexo.equals("Femenino")) {
            MujerRadioButton.setSelected(true);
        } else {
            VaronRadioButton.setSelected(true);
        }
    }

    public void SeleccionarProfesion(String tmp_profesion) {
        ProfesionesComboBox.setSelectedItem(tmp_profesion);
        Personaje.getInstance().profesion = tmp_profesion;
    }

    public void SeleccionarCultura(String tmp_cultura) {
        CulturasComboBox.setSelectedItem(tmp_cultura);
        Personaje.getInstance().cultura = tmp_cultura;
    }

    public  void ActivarInsertarCategoria(boolean value) {
        InsertarCategoriaMenuItem.setEnabled(value);
    }

    /******************************************************
     *
     *                      CONSULTAS
     *
     *****************************************************/
    /**
     * Devuelve el nivel marcado por el usuario en la interfaz.
     */
    public int DevolverNivelSeleccionado() {
        //return (Integer)NivelSpinner.getValue();
        return Integer.parseInt(NivelTextField.getText());
    }

    public String DevolverRazaSeleccionada() {
        if (RazasComboBox.getSelectedIndex() >= 0) {
            //return RazasComboBox.getItemAt(RazasComboBox.getSelectedIndex()).toString();
            return RazasComboBox.getSelectedItem().toString();
        } else {
            return Personaje.getInstance().raza;
        }
    }

    public String DevolverProfesionSeleccionada() {
        if (ProfesionesComboBox.getSelectedIndex() >= 0) {

            //return ProfesionesComboBox.getItemAt(ProfesionesComboBox.getSelectedIndex()).toString();
            return ProfesionesComboBox.getSelectedItem().toString();
        } else {
            return Personaje.getInstance().profesion;
        }
    }

    public String DevolverCulturaSeleccionada() {
        if (CulturasComboBox.getSelectedIndex() >= 0) {
            //return CulturasComboBox.getItemAt(CulturasComboBox.getSelectedIndex()).toString();
            return CulturasComboBox.getSelectedItem().toString();
        } else {
            return Personaje.getInstance().cultura;
        }
    }

    private void ActualizarTextoCaracteristicas() {
        String text = "";
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            text = text + Personaje.getInstance().caracteristicas.Get(i).DevolverAbreviatura() + "\t"
                    + Personaje.getInstance().caracteristicas.Get(i).Total() + "\n";
        }
        CaracteristicasTextArea.setTabSize(3);
        CaracteristicasTextArea.setText(text);
    }

    private void ActualizarTextoHabilidades() {
        FichaTxt fichaTxt = new FichaTxt();
        String text = fichaTxt.ExportarATextoHabilidades();
        HabilidadesTextArea.setTabSize(15);
        HabilidadesTextArea.setText(text);
    }

    public void ActualizarPuntosDesarrollo() {
        PuntosDesarrolloTextField.setText(Personaje.getInstance().PuntosDesarrolloNoGastados() + "");
    }

    public void ActualizarNombre() {
        NombreTextField.setText(Personaje.getInstance().DevolverNombreCompleto());
    }

    public void CambiarNivelSeleccionado(int valor) {
        NivelTextField.setText(valor + "");
    }

    public String DevuelveSexo() {
        if (MujerRadioButton.isSelected()) {
            return "Mujer";
        } else {
            return "Varón";
        }
    }

    public String DevolverReinoDeMagia() {
        try {
            return ReinosComboBox.getSelectedItem().toString();
        } catch (NullPointerException npe) {
            return "Esencia";
        }
    }

    /************************************************
     *
     *                    LISTENERS
     *
     ************************************************/
    /**
     * Añade un listener a un objeto.
     */
    public  void addRazasComboBoxListener(ActionListener al) {
        RazasComboBox.addActionListener(al);
    }

    public void addProfesionComboBoxListener(ActionListener al) {
        ProfesionesComboBox.addActionListener(al);
    }

    public void addCulturaComboBoxListener(ActionListener al) {
        CulturasComboBox.addActionListener(al);
    }

    public void addCaracteristicasListener(ActionListener al) {
        CaracteristicasMenuItem.addActionListener(al);
    }

    public void addCategoriasYHabilidadesListener(ActionListener al) {
        HabilidadesMenuItem.addActionListener(al);
    }

    public void addAdiestramientoListener(ActionListener al) {
        AdiestramientoMenuItem.addActionListener(al);
    }

    public void addSubirNivelMenuItem(ActionListener al) {
        SubirNivelMenuItem.addActionListener(al);
    }

    public void addSubirNivelButtonListener(ActionListener al) {
        SubirButton.addActionListener(al);
    }

    public void addNewMenuItemListener(ActionListener al) {
        NewMenuItem.addActionListener(al);
    }

    public void addOpcionesMenuItemListener(ActionListener al) {
        OpcionesMenuItem.addActionListener(al);
    }

    public void addHistorialMenuItemListener(ActionListener al) {
        HistorialMenuItem.addActionListener(al);
    }

    public void addTalentosMenuItemListener(ActionListener al) {
        TalentosMenuItem.addActionListener(al);
    }

    public void addSaveMenuItemListener(ActionListener al) {
        SaveMenuItem.addActionListener(al);
    }

    public void addAbrevTxtMenuItemListener(ActionListener al) {
        AbrevTxtMenuItem.addActionListener(al);
    }

    public void addTxtMenuItemListener(ActionListener al) {
        TxtMenuItem.addActionListener(al);
    }

    public void addPdfMenuItemListener(ActionListener al) {
        PdfMenuItem.addActionListener(al);
    }

    public void addPdfCombinadoMenuItemListener(ActionListener al) {
        PdfCombinadoMenuItem.addActionListener(al);
    }

    public void addLoadMenuItemListener(ActionListener al) {
        LoadMenuItem.addActionListener(al);
    }

    public void addExitMenuItemListener(ActionListener al) {
        ExitMenuItem.addActionListener(al);
    }

    public void addAboutMenuItemListener(ActionListener al) {
        AboutMenuItem.addActionListener(al);
    }

    public void addSexoListener(ActionListener al) {
        MujerRadioButton.addActionListener(al);
        VaronRadioButton.addActionListener(al);
    }

    public void addCulturaMenuListener(ActionListener al) {
        CulturaMenuItem.addActionListener(al);
    }

    public void addAleatorioMenuListener(ActionListener al) {
        GenerarAleatorioMenuItem.addActionListener(al);
    }

    public void addReinosComboBoxListener(ActionListener al) {
        ReinosComboBox.addActionListener(al);
    }

    public void addHojaEnBlancoMenuItemListener(ActionListener al) {
        HojaBlancoMenuItem.addActionListener(al);
    }

    public void addInsertarPjMenuItemListener(ActionListener al) {
        InsertarPjMenuItem.addActionListener(al);
    }

    public void addInsertarCategoriaMenuItemListener(ActionListener al) {
        InsertarCategoriaMenuItem.addActionListener(al);
    }

    public void addExportarNivelMenuItemListener(ActionListener al) {
        ExportarNivelMenuItem.addActionListener(al);
        ExportarNivelMenuItem2.addActionListener(al);
    }

    public void addImportarNivelMenuItemListener(ActionListener al) {
        ImportarNivelMenuItem.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SexoButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        MujerRadioButton = new javax.swing.JRadioButton();
        VaronRadioButton = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ReinosComboBox = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        PuntosDesarrolloTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        SubirButton = new javax.swing.JButton();
        NivelTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        CulturasComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        AdiestramientoTextField = new javax.swing.JTextField();
        HabilidadesScrollPane = new javax.swing.JScrollPane();
        CaracteristicasTextArea = new javax.swing.JTextArea();
        CaractScrollPane = new javax.swing.JScrollPane();
        HabilidadesTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ProfesionesComboBox = new javax.swing.JComboBox();
        RazasComboBox = new javax.swing.JComboBox();
        NombreTextField = new javax.swing.JTextField();
        MainMenuBar = new javax.swing.JMenuBar();
        ApplicationMenu = new javax.swing.JMenu();
        NewMenuItem = new javax.swing.JMenuItem();
        LoadMenuItem = new javax.swing.JMenuItem();
        SaveMenuItem = new javax.swing.JMenuItem();
        ExportMenu = new javax.swing.JMenu();
        TxtMenuItem = new javax.swing.JMenuItem();
        AbrevTxtMenuItem = new javax.swing.JMenuItem();
        PdfMenuItem = new javax.swing.JMenuItem();
        PdfCombinadoMenuItem = new javax.swing.JMenuItem();
        ExportarNivelMenuItem2 = new javax.swing.JMenuItem();
        ExitMenuItem = new javax.swing.JMenuItem();
        PersonajeMenu = new javax.swing.JMenu();
        CaracteristicasMenuItem = new javax.swing.JMenuItem();
        CulturaMenuItem = new javax.swing.JMenuItem();
        AdiestramientoMenuItem = new javax.swing.JMenuItem();
        HabilidadesMenuItem = new javax.swing.JMenuItem();
        HistorialMenuItem = new javax.swing.JMenuItem();
        TalentosMenuItem = new javax.swing.JMenuItem();
        NivelMenu = new javax.swing.JMenu();
        SubirNivelMenuItem = new javax.swing.JMenuItem();
        ExportarNivelMenuItem = new javax.swing.JMenuItem();
        ImportarNivelMenuItem = new javax.swing.JMenuItem();
        InsertarPjMenu = new javax.swing.JMenu();
        InsertarPjMenuItem = new javax.swing.JMenuItem();
        InsertarCategoriaMenuItem = new javax.swing.JMenuItem();
        AleatorioMenu = new javax.swing.JMenu();
        GenerarAleatorioMenuItem = new javax.swing.JMenuItem();
        ConfiguracionMenu = new javax.swing.JMenu();
        OpcionesMenuItem = new javax.swing.JMenuItem();
        OtrosMenu = new javax.swing.JMenu();
        HojaBlancoMenuItem = new javax.swing.JMenuItem();
        AyudaMenu = new javax.swing.JMenu();
        AboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("El Libro de Esher - Generador de PJs y PNJs para Rolemaster");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        SexoButtonGroup.add(MujerRadioButton);
        MujerRadioButton.setText("Femenino");
        MujerRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        SexoButtonGroup.add(VaronRadioButton);
        VaronRadioButton.setSelected(true);
        VaronRadioButton.setText("Masculino");
        VaronRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        jLabel9.setText("Sexo:");

        jLabel8.setText("Reino:");

        jLabel7.setText("Pts Des:");

        PuntosDesarrolloTextField.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PuntosDesarrolloTextField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(VaronRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(MujerRadioButton))
                    .addComponent(ReinosComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(MujerRadioButton)
                    .addComponent(VaronRadioButton)
                    .addComponent(jLabel9))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ReinosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(PuntosDesarrolloTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        SubirButton.setText("Subir");
        SubirButton.setEnabled(false);

        NivelTextField.setEditable(false);
        NivelTextField.setText("1");

        jLabel2.setText("Nivel: ");

        jLabel5.setText("Cultura:");

        jLabel6.setText("Adiestramientos:");

        AdiestramientoTextField.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CulturasComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(NivelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SubirButton, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                    .addComponent(AdiestramientoTextField))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(SubirButton)
                    .addComponent(jLabel2)
                    .addComponent(NivelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CulturasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(AdiestramientoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CaracteristicasTextArea.setEditable(false);
        CaracteristicasTextArea.setColumns(5);
        CaracteristicasTextArea.setRows(10);
        HabilidadesScrollPane.setViewportView(CaracteristicasTextArea);

        HabilidadesTextArea.setColumns(20);
        HabilidadesTextArea.setRows(5);
        CaractScrollPane.setViewportView(HabilidadesTextArea);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nombre:");

        jLabel3.setText("Raza:");

        jLabel4.setText("Profesion:");

        NombreTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NombreTextFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NombreTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RazasComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 179, Short.MAX_VALUE)
                    .addComponent(ProfesionesComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(NombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(RazasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ProfesionesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ApplicationMenu.setText("Archivo");

        NewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        NewMenuItem.setText("Nuevo");
        ApplicationMenu.add(NewMenuItem);

        LoadMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        LoadMenuItem.setText("Cargar");
        ApplicationMenu.add(LoadMenuItem);

        SaveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveMenuItem.setText("Salvar");
        SaveMenuItem.setEnabled(false);
        ApplicationMenu.add(SaveMenuItem);

        ExportMenu.setText("Exportar");
        ExportMenu.setEnabled(false);

        TxtMenuItem.setText("Txt");
        ExportMenu.add(TxtMenuItem);

        AbrevTxtMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        AbrevTxtMenuItem.setText("Txt (Abrev.)");
        AbrevTxtMenuItem.setToolTipText("Exporta a txt solamente la información más importante del personaje.");
        ExportMenu.add(AbrevTxtMenuItem);

        PdfMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        PdfMenuItem.setText("PDF");
        ExportMenu.add(PdfMenuItem);

        PdfCombinadoMenuItem.setText("PDF (Combinado)");
        ExportMenu.add(PdfCombinadoMenuItem);

        ExportarNivelMenuItem2.setText("Subida de Nivel");
        ExportMenu.add(ExportarNivelMenuItem2);

        ApplicationMenu.add(ExportMenu);

        ExitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        ExitMenuItem.setText("Salir");
        ExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuItemActionPerformed(evt);
            }
        });
        ApplicationMenu.add(ExitMenuItem);

        MainMenuBar.add(ApplicationMenu);

        PersonajeMenu.setText("Generar Personaje");
        PersonajeMenu.setToolTipText("Permite generar un personaje paso a paso siguiendo las reglas del Rolemaster.");

        CaracteristicasMenuItem.setText("Características");
        CaracteristicasMenuItem.setToolTipText("El primer paso es definir las caracteristicas del personaje");
        PersonajeMenu.add(CaracteristicasMenuItem);

        CulturaMenuItem.setText("Cultura");
        CulturaMenuItem.setEnabled(false);
        PersonajeMenu.add(CulturaMenuItem);

        AdiestramientoMenuItem.setText("Opciones de Adiestramiento");
        AdiestramientoMenuItem.setEnabled(false);
        PersonajeMenu.add(AdiestramientoMenuItem);

        HabilidadesMenuItem.setText("Categorias y Habilidades");
        HabilidadesMenuItem.setEnabled(false);
        PersonajeMenu.add(HabilidadesMenuItem);

        HistorialMenuItem.setText("Historial");
        HistorialMenuItem.setEnabled(false);
        PersonajeMenu.add(HistorialMenuItem);

        TalentosMenuItem.setText("Talentos");
        TalentosMenuItem.setToolTipText("Gestiona los talentos y defectos del personaje");
        TalentosMenuItem.setEnabled(false);
        PersonajeMenu.add(TalentosMenuItem);

        MainMenuBar.add(PersonajeMenu);

        NivelMenu.setText("Nivel");
        NivelMenu.setEnabled(false);

        SubirNivelMenuItem.setText("Subir nivel");
        NivelMenu.add(SubirNivelMenuItem);

        ExportarNivelMenuItem.setText("Exportar Subida de Nivel");
        ExportarNivelMenuItem.setToolTipText("Permite exportar de forma segura las acciones realizadas al subir un nivel para poder repetirlo en otro ordenador. Util para que los jugadores se suban el nivel en casa.");
        NivelMenu.add(ExportarNivelMenuItem);

        ImportarNivelMenuItem.setText("Importar Subida de Nivel");
        ImportarNivelMenuItem.setToolTipText("Permite importar a esta maquina el nivel subido por tus jugadores en sus casas.");
        NivelMenu.add(ImportarNivelMenuItem);

        MainMenuBar.add(NivelMenu);

        InsertarPjMenu.setText("Insertar");
        InsertarPjMenu.setToolTipText("Inserta un elemento externo al programa");

        InsertarPjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        InsertarPjMenuItem.setText("Personaje");
        InsertarPjMenuItem.setToolTipText("Permite crear un personaje sin utilizar las reglas del rolemaster");
        InsertarPjMenu.add(InsertarPjMenuItem);

        InsertarCategoriaMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        InsertarCategoriaMenuItem.setText("Categorías y Habilidades");
        InsertarCategoriaMenuItem.setToolTipText("Inserta una Categoría para el Personaje.");
        InsertarPjMenu.add(InsertarCategoriaMenuItem);

        MainMenuBar.add(InsertarPjMenu);

        AleatorioMenu.setText("Personaje Aleatorio");
        AleatorioMenu.setToolTipText("Genera un personaje de forma aleatoria.");

        GenerarAleatorioMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        GenerarAleatorioMenuItem.setText("Generar");
        GenerarAleatorioMenuItem.setToolTipText("Genera el personaje con los parámetros escogidos");
        AleatorioMenu.add(GenerarAleatorioMenuItem);

        MainMenuBar.add(AleatorioMenu);

        ConfiguracionMenu.setText("Configuración");
        ConfiguracionMenu.setToolTipText("Opciones de configuración");

        OpcionesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        OpcionesMenuItem.setText("Opciones");
        ConfiguracionMenu.add(OpcionesMenuItem);

        MainMenuBar.add(ConfiguracionMenu);

        OtrosMenu.setText("Otros");

        HojaBlancoMenuItem.setText("Ficha Vacia");
        HojaBlancoMenuItem.setToolTipText("Si ves demasiado complicado este programa y preferies el sistema de toda la vida, aqui puedes imprimir una hoja en blanco.");
        OtrosMenu.add(HojaBlancoMenuItem);

        MainMenuBar.add(OtrosMenu);

        AyudaMenu.setText("Ayuda");

        AboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        AboutMenuItem.setText("Acerca de...");
        AyudaMenu.add(AboutMenuItem);

        MainMenuBar.add(AyudaMenu);

        setJMenuBar(MainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(HabilidadesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CaractScrollPane)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CaractScrollPane)
                    .addComponent(HabilidadesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void NombreTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NombreTextFieldKeyTyped
        Personaje.getInstance().SetNombreCompleto(NombreTextField.getText());
    }//GEN-LAST:event_NombreTextFieldKeyTyped

    private void ExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_ExitMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutMenuItem;
    private javax.swing.JMenuItem AbrevTxtMenuItem;
    private javax.swing.JMenuItem AdiestramientoMenuItem;
    private javax.swing.JTextField AdiestramientoTextField;
    private javax.swing.JMenu AleatorioMenu;
    private javax.swing.JMenu ApplicationMenu;
    private javax.swing.JMenu AyudaMenu;
    private javax.swing.JScrollPane CaractScrollPane;
    private javax.swing.JMenuItem CaracteristicasMenuItem;
    private javax.swing.JTextArea CaracteristicasTextArea;
    private javax.swing.JMenu ConfiguracionMenu;
    private javax.swing.JMenuItem CulturaMenuItem;
    private javax.swing.JComboBox CulturasComboBox;
    private javax.swing.JMenuItem ExitMenuItem;
    private javax.swing.JMenu ExportMenu;
    private javax.swing.JMenuItem ExportarNivelMenuItem;
    private javax.swing.JMenuItem ExportarNivelMenuItem2;
    private javax.swing.JMenuItem GenerarAleatorioMenuItem;
    private javax.swing.JMenuItem HabilidadesMenuItem;
    private javax.swing.JScrollPane HabilidadesScrollPane;
    private javax.swing.JTextArea HabilidadesTextArea;
    private javax.swing.JMenuItem HistorialMenuItem;
    private javax.swing.JMenuItem HojaBlancoMenuItem;
    private javax.swing.JMenuItem ImportarNivelMenuItem;
    private javax.swing.JMenuItem InsertarCategoriaMenuItem;
    private javax.swing.JMenu InsertarPjMenu;
    private javax.swing.JMenuItem InsertarPjMenuItem;
    private javax.swing.JMenuItem LoadMenuItem;
    private javax.swing.JMenuBar MainMenuBar;
    private javax.swing.JRadioButton MujerRadioButton;
    private javax.swing.JMenuItem NewMenuItem;
    private javax.swing.JMenu NivelMenu;
    private javax.swing.JTextField NivelTextField;
    private javax.swing.JTextField NombreTextField;
    private javax.swing.JMenuItem OpcionesMenuItem;
    private javax.swing.JMenu OtrosMenu;
    private javax.swing.JMenuItem PdfCombinadoMenuItem;
    private javax.swing.JMenuItem PdfMenuItem;
    private javax.swing.JMenu PersonajeMenu;
    private javax.swing.JComboBox ProfesionesComboBox;
    private javax.swing.JTextField PuntosDesarrolloTextField;
    private javax.swing.JComboBox RazasComboBox;
    private javax.swing.JComboBox ReinosComboBox;
    private javax.swing.JMenuItem SaveMenuItem;
    private javax.swing.ButtonGroup SexoButtonGroup;
    private javax.swing.JButton SubirButton;
    private javax.swing.JMenuItem SubirNivelMenuItem;
    private javax.swing.JMenuItem TalentosMenuItem;
    private javax.swing.JMenuItem TxtMenuItem;
    private javax.swing.JRadioButton VaronRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}

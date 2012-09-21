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

import com.softwaremagico.librodeesher.Caracteristica;
import com.softwaremagico.librodeesher.Personaje;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

/**
 *
 * @author  jorge
 */
public class CaracteristicasGUI extends javax.swing.JFrame {

    private List<JSpinner> listaTemporales = new ArrayList<>();
    private List<JTextField> listaPotenciales = new ArrayList<>();
    private List<JTextField> listaBasica = new ArrayList<>();
    private List<JTextField> listaRaza = new ArrayList<>();
    private List<JTextField> listaTotal = new ArrayList<>();
    boolean activo = true;

    /** Creates new form CaracteristicasGUI */
    public CaracteristicasGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        //Reset();

        GeneraListadoTemporales();
        GeneraListadoPotenciales();
        GeneraListadoValorBasico();
        GeneraListadoRaza();
        GeneraListadoTotal();
    }

    public void Refrescar() {
        ActualizaTemporales();
        ActualizarPotenciales();
        ActualizarValorBasico();
        ActualizarBonificacionRaza();
        ActualizarTotal();
        ActualizarCuentaCaracteristicas();
        ActualizarApariencia();
    }

    public void Reset() {
        HabilitarCambiosEnCaracteristicas();
        GeneraListadoTemporales();
        GeneraListadoPotenciales();
        GeneraListadoValorBasico();
        GeneraListadoRaza();
        GeneraListadoTotal();
        InicialValoresDefecto();
        Refrescar();
        activo = true;
    }


    /**********************************************************************
     *
     *                                INICIALIZACION
     *
     **********************************************************************/
    /**
     * Guarda una lista de todos los marcadores de caracteristicas temporales.
     */
    private void GeneraListadoTemporales() {
        listaTemporales = new ArrayList<>();
        listaTemporales.add(AgilidadTemporalSpinner);
        listaTemporales.add(ConstitucionTemporalSpinner);
        listaTemporales.add(MemoriaTemporalSpinner);
        listaTemporales.add(RazonTemporalSpinner);
        listaTemporales.add(AutodisciplinaTemporalSpinner);
        listaTemporales.add(EmpatiaTemporalSpinner);
        listaTemporales.add(IntuicionTemporalSpinner);
        listaTemporales.add(PresenciaTemporalSpinner);
        listaTemporales.add(RapidezTemporalSpinner);
        listaTemporales.add(FuerzaTemporalSpinner);
    }

    /**
     * General un listado de los contenedores de las caracteristicas potenciales.
     */
    private void GeneraListadoPotenciales() {
        listaPotenciales = new ArrayList<>();
        listaPotenciales.add(AgilidadPotencialTextField);
        listaPotenciales.add(ConstitucionPotencialTextField);
        listaPotenciales.add(MemoriaPotencialTextField);
        listaPotenciales.add(RazonPotencialTextField);
        listaPotenciales.add(AutodisciplinaPotencialTextField);
        listaPotenciales.add(EmpatiaPotencialTextField);
        listaPotenciales.add(IntuicionPotencialTextField);
        listaPotenciales.add(PresenciaPotencialTextField);
        listaPotenciales.add(RapidezPotencialTextField);
        listaPotenciales.add(FuerzaPotencialTextField);
    }

    /**
     * General un listado de los contenedores del valor bÃ¡sico de caracteristicas.
     */
    private void GeneraListadoValorBasico() {
        listaBasica = new ArrayList<>();
        listaBasica.add(AgilidadBasicaTextField);
        listaBasica.add(ConstitucionBasicaTextField);
        listaBasica.add(MemoriaBasicaTextField);
        listaBasica.add(RazonBasicaTextField);
        listaBasica.add(AutodisciplinaBasicaTextField);
        listaBasica.add(EmpatiaBasicaTextField);
        listaBasica.add(IntuicionBasicaTextField);
        listaBasica.add(PresenciaBasicaTextField);
        listaBasica.add(RapidezBasicaTextField);
        listaBasica.add(FuerzaBasicaTextField);
    }

    /**
     * General un listado de los contenedores de las bonificaciones por raza.
     */
    private void GeneraListadoRaza() {
        listaRaza = new ArrayList<>();
        listaRaza.add(AgilidadRazaTextField);
        listaRaza.add(ConstitucionRazaTextField);
        listaRaza.add(MemoriaRazaTextField);
        listaRaza.add(RazonRazaTextField);
        listaRaza.add(AutodisciplinaRazaTextField);
        listaRaza.add(EmpatiaRazaTextField);
        listaRaza.add(IntuicionRazaTextField);
        listaRaza.add(PresenciaRazaTextField);
        listaRaza.add(RapidezRazaTextField);
        listaRaza.add(FuerzaRazaTextField);
    }

    /**
     * General un listado de los contenedores del total de caracteristicas.
     */
    private void GeneraListadoTotal() {
        listaTotal = new ArrayList<>();
        listaTotal.add(AgilidadTotalTextField);
        listaTotal.add(ConstitucionTotalTextField);
        listaTotal.add(MemoriaTotalTextField);
        listaTotal.add(RazonTotalTextField);
        listaTotal.add(AutodisciplinaTotalTextField);
        listaTotal.add(EmpatiaTotalTextField);
        listaTotal.add(IntuicionTotalTextField);
        listaTotal.add(PresenciaTotalTextField);
        listaTotal.add(RapidezTotalTextField);
        listaTotal.add(FuerzaTotalTextField);
    }

    /**********************************************************************
     *
     *                          CONTROL DE CAMPOS
     *
     **********************************************************************/
    /**
     * Muestra el valor potencial de las caracteristicas.
     */
    public void ActualizarPotenciales() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JTextField textFieldACambiar = listaPotenciales.get(i);
            textFieldACambiar.setText(car.ObtenerPuntosPotencial() + "");
        }
    }

    public void ActualizaTemporales() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JSpinner spin = listaTemporales.get(i);
            spin.setValue(car.ObtenerPuntosTemporal());
        }
    }

    public void ActualizarApariencia() {
        AparienciaTextField.setText(Personaje.getInstance().caracteristicas.DevolverTotalApariencia() + "");
    }

    /**
     * Muestra el total de las caracteristicas.
     */
    private void ActualizarValorBasico() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JTextField textFieldACambiar = listaBasica.get(i);
            textFieldACambiar.setText(car.ObtenerValorTemporal() + "");
        }
        ActualizarTotal();
    }

    /**
     * Muestra la bonificación por raza.
     */
    private void ActualizarBonificacionRaza() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JTextField textFieldACambiar = listaRaza.get(i);
            textFieldACambiar.setText(car.ObtenerValorRaza() + "");
        }
        ActualizarTotal();
    }

    /**
     * Muestra el total de las caracteristicas.
     */
    private void ActualizarTotal() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JTextField textFieldACambiar = listaTotal.get(i);
            textFieldACambiar.setText(car.Total() + "");
        }
    }

    private int ObtenerTotalTemporalSeleccionado() {
        double d;
        int totalTemporal = 0;
        for (int i = 0; i < listaTemporales.size(); i++) {
            JSpinner spin = listaTemporales.get(i);
            if ((Integer) spin.getValue() < 90) {
                totalTemporal += (Integer) spin.getValue();
            } else {
                d = Math.pow((Integer) spin.getValue() - 90, 2) + 90;
                totalTemporal += d;
            }
        }
        return totalTemporal;
    }

    public void ActualizaCaracteristicaTemporal(int index) {
        Caracteristica car = Personaje.getInstance().caracteristicas.Get(index);
        try {
            JSpinner spin = listaTemporales.get(index);

            if ((Integer) spin.getValue() < 90 &&
                    Personaje.getInstance().EsCaracteristicasPrincipal(car)) {
                spin.setValue(90);
            } else {
                if ((Integer) (spin.getValue()) < 1) {
                    spin.setValue(1);
                } else {
                    if ((Integer) spin.getValue() > 100) {
                        spin.setValue(100);
                    }
                    if (Personaje.getInstance().nivel == 1 && Personaje.getInstance().puntoshistorialCaracteristicas == 0 && activo) {
                        if (ObtenerTotalTemporalSeleccionado() > Personaje.getInstance().caracteristicas.totalCaracteristicas) {
                            spin.setValue((Integer) spin.getValue() - 1);
                        }
                    }
                }
            }
            //Actualizamos la caracteristica con un valor correcto.
            car.CrearPuntosTemporal((Integer) (spin.getValue()));
            JTextField texto = listaBasica.get(index);
            texto.setText(car.ObtenerValorTemporal() + "");
            PuntosCaracteristicasGastadosTextField.setText(ObtenerTotalTemporalSeleccionado() + "");
            JTextField totalCaracteristica = listaTotal.get(index);
            totalCaracteristica.setText(car.Total() + "");
        } catch (StackOverflowError sof) {
            car.CrearPuntosPotencial(car.ObtenerPuntosTemporal());
        }
    }

    private void InicialValoresDefecto() {
        for (int i = 0; i < listaTemporales.size(); i++) {
            JSpinner spin = listaTemporales.get(i);
            spin.setValue(45);
            ActualizaCaracteristicaTemporal(i);
        }
    }

    public void DeshabilitarCambiosEnCaracteristicas() {
        for (int i = 0; i < listaTemporales.size(); i++) {
            JSpinner spin = listaTemporales.get(i);
            spin.setEnabled(false);
        }
        AceptarButton.setEnabled(false);
        RandomButton.setEnabled(false);
        activo = false;
    }

    public void HabilitarCambiosEnCaracteristicas() {
        for (int i = 0; i < listaTemporales.size(); i++) {
            JSpinner spin = listaTemporales.get(i);
            spin.setEnabled(true);
        }
        AceptarButton.setEnabled(true);
        RandomButton.setEnabled(true);
    }

    private void ActualizarCuentaCaracteristicas() {
        if (Personaje.getInstance().nivel > 1 || Personaje.getInstance().puntoshistorialCaracteristicas > 1 || !activo) {
            PDLabel.setVisible(false);
            PuntosCaracteristicasGastadosTextField.setVisible(false);
            PuntosCaracteristicasTotalesTextField.setVisible(false);
            SeparadorLabel.setVisible(false);
        } else {
            PDLabel.setVisible(true);
            PuntosCaracteristicasGastadosTextField.setVisible(true);
            PuntosCaracteristicasTotalesTextField.setVisible(true);
            SeparadorLabel.setVisible(true);
        }
    }

    /**********************************************************************
     *
     *                                LISTENERS
     *
     **********************************************************************/
    /**
     * Añade el evento al spinner.
     */
    public void addAgilidadTemporalSpinnerListener(ChangeListener al) {
        AgilidadTemporalSpinner.addChangeListener(al);
    }

    public void addConstitucionTemporalSpinnerListener(ChangeListener al) {
        ConstitucionTemporalSpinner.addChangeListener(al);
    }

    public void addMemoriaTemporalSpinnerListener(ChangeListener al) {
        MemoriaTemporalSpinner.addChangeListener(al);
    }

    public void addRazonTemporalSpinnerListener(ChangeListener al) {
        RazonTemporalSpinner.addChangeListener(al);
    }

    public void addAutodisciplinaTemporalSpinnerListener(ChangeListener al) {
        AutodisciplinaTemporalSpinner.addChangeListener(al);
    }

    public void addEmpatiaTemporalSpinnerListener(ChangeListener al) {
        EmpatiaTemporalSpinner.addChangeListener(al);
    }

    public void addIntuicionTemporalSpinnerListener(ChangeListener al) {
        IntuicionTemporalSpinner.addChangeListener(al);
    }

    public void addPresenciaTemporalSpinnerListener(ChangeListener al) {
        PresenciaTemporalSpinner.addChangeListener(al);
    }

    public void addRapidezTemporalSpinnerListener(ChangeListener al) {
        RapidezTemporalSpinner.addChangeListener(al);
    }

    public void addFuerzaTemporalSpinnerListener(ChangeListener al) {
        FuerzaTemporalSpinner.addChangeListener(al);
    }

    public void addAceptarButtonListener(ActionListener al) {
        AceptarButton.addActionListener(al);
    }

    public void addRandomButtonListener(ActionListener al) {
        RandomButton.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        AgilidadTemporalSpinner = new javax.swing.JSpinner();
        ConstitucionTemporalSpinner = new javax.swing.JSpinner();
        MemoriaTemporalSpinner = new javax.swing.JSpinner();
        RazonTemporalSpinner = new javax.swing.JSpinner();
        AutodisciplinaTemporalSpinner = new javax.swing.JSpinner();
        EmpatiaTemporalSpinner = new javax.swing.JSpinner();
        IntuicionTemporalSpinner = new javax.swing.JSpinner();
        PresenciaTemporalSpinner = new javax.swing.JSpinner();
        RapidezTemporalSpinner = new javax.swing.JSpinner();
        FuerzaTemporalSpinner = new javax.swing.JSpinner();
        AparienciaTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        AgilidadPotencialTextField = new javax.swing.JTextField();
        ConstitucionPotencialTextField = new javax.swing.JTextField();
        MemoriaPotencialTextField = new javax.swing.JTextField();
        RazonPotencialTextField = new javax.swing.JTextField();
        AutodisciplinaPotencialTextField = new javax.swing.JTextField();
        EmpatiaPotencialTextField = new javax.swing.JTextField();
        IntuicionPotencialTextField = new javax.swing.JTextField();
        PresenciaPotencialTextField = new javax.swing.JTextField();
        RapidezPotencialTextField = new javax.swing.JTextField();
        FuerzaPotencialTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        AgilidadBasicaTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ConstitucionBasicaTextField = new javax.swing.JTextField();
        MemoriaBasicaTextField = new javax.swing.JTextField();
        RazonBasicaTextField = new javax.swing.JTextField();
        AutodisciplinaBasicaTextField = new javax.swing.JTextField();
        EmpatiaBasicaTextField = new javax.swing.JTextField();
        IntuicionBasicaTextField = new javax.swing.JTextField();
        PresenciaBasicaTextField = new javax.swing.JTextField();
        RapidezBasicaTextField = new javax.swing.JTextField();
        FuerzaBasicaTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        AgilidadRazaTextField = new javax.swing.JTextField();
        ConstitucionRazaTextField = new javax.swing.JTextField();
        MemoriaRazaTextField = new javax.swing.JTextField();
        RazonRazaTextField = new javax.swing.JTextField();
        AutodisciplinaRazaTextField = new javax.swing.JTextField();
        EmpatiaRazaTextField = new javax.swing.JTextField();
        IntuicionRazaTextField = new javax.swing.JTextField();
        PresenciaRazaTextField = new javax.swing.JTextField();
        RapidezRazaTextField = new javax.swing.JTextField();
        FuerzaRazaTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        AgilidadTotalTextField = new javax.swing.JTextField();
        ConstitucionTotalTextField = new javax.swing.JTextField();
        MemoriaTotalTextField = new javax.swing.JTextField();
        RazonTotalTextField = new javax.swing.JTextField();
        AutodisciplinaTotalTextField = new javax.swing.JTextField();
        EmpatiaTotalTextField = new javax.swing.JTextField();
        IntuicionTotalTextField = new javax.swing.JTextField();
        PresenciaTotalTextField = new javax.swing.JTextField();
        RapidezTotalTextField = new javax.swing.JTextField();
        FuerzaTotalTextField = new javax.swing.JTextField();
        AceptarButton = new javax.swing.JButton();
        RandomButton = new javax.swing.JButton();
        PDLabel = new javax.swing.JLabel();
        PuntosCaracteristicasGastadosTextField = new javax.swing.JTextField();
        SeparadorLabel = new javax.swing.JLabel();
        PuntosCaracteristicasTotalesTextField = new javax.swing.JFormattedTextField();

        setTitle("Características del Personaje");
        setResizable(false);

        jLabel1.setText("Agilidad");

        jLabel2.setText("Constitución");

        jLabel3.setText("Memoria");

        jLabel4.setText("Razón");

        jLabel5.setText("Autodisciplina");

        jLabel6.setText("Empatía");

        jLabel7.setText("Intuición");

        jLabel8.setText("Presencia");

        jLabel9.setText("Rapidez");

        jLabel10.setText("Fuerza");

        jLabel11.setText("Apariencia");

        AparienciaTextField.setEditable(false);
        AparienciaTextField.setText("0");
        AparienciaTextField.setToolTipText("Autocalculada");

        jLabel12.setText("Temporal");

        AgilidadPotencialTextField.setEditable(false);
        AgilidadPotencialTextField.setText("0");
        AgilidadPotencialTextField.setToolTipText("Autocalculada");

        ConstitucionPotencialTextField.setEditable(false);
        ConstitucionPotencialTextField.setText("0");
        ConstitucionPotencialTextField.setToolTipText("Autocalculada");

        MemoriaPotencialTextField.setEditable(false);
        MemoriaPotencialTextField.setText("0");
        MemoriaPotencialTextField.setToolTipText("Autocalculada");

        RazonPotencialTextField.setEditable(false);
        RazonPotencialTextField.setText("0");
        RazonPotencialTextField.setToolTipText("Autocalculada");

        AutodisciplinaPotencialTextField.setEditable(false);
        AutodisciplinaPotencialTextField.setText("0");
        AutodisciplinaPotencialTextField.setToolTipText("Autocalculada");

        EmpatiaPotencialTextField.setEditable(false);
        EmpatiaPotencialTextField.setText("0");
        EmpatiaPotencialTextField.setToolTipText("Autocalculada");

        IntuicionPotencialTextField.setEditable(false);
        IntuicionPotencialTextField.setText("0");
        IntuicionPotencialTextField.setToolTipText("Autocalculada");

        PresenciaPotencialTextField.setEditable(false);
        PresenciaPotencialTextField.setText("0");
        PresenciaPotencialTextField.setToolTipText("Autocalculada");

        RapidezPotencialTextField.setEditable(false);
        RapidezPotencialTextField.setText("0");
        RapidezPotencialTextField.setToolTipText("Autocalculada");

        FuerzaPotencialTextField.setEditable(false);
        FuerzaPotencialTextField.setText("0");
        FuerzaPotencialTextField.setToolTipText("Autocalculada");

        jLabel13.setText("Potencial");

        AgilidadBasicaTextField.setEditable(false);
        AgilidadBasicaTextField.setText("0");
        AgilidadBasicaTextField.setToolTipText("Autocalculadas");

        jLabel14.setText("B. Básica");

        ConstitucionBasicaTextField.setEditable(false);
        ConstitucionBasicaTextField.setText("0");
        ConstitucionBasicaTextField.setToolTipText("Autocalculadas");

        MemoriaBasicaTextField.setEditable(false);
        MemoriaBasicaTextField.setText("0");
        MemoriaBasicaTextField.setToolTipText("Autocalculadas");

        RazonBasicaTextField.setEditable(false);
        RazonBasicaTextField.setText("0");
        RazonBasicaTextField.setToolTipText("Autocalculadas");

        AutodisciplinaBasicaTextField.setEditable(false);
        AutodisciplinaBasicaTextField.setText("0");
        AutodisciplinaBasicaTextField.setToolTipText("Autocalculadas");

        EmpatiaBasicaTextField.setEditable(false);
        EmpatiaBasicaTextField.setText("0");
        EmpatiaBasicaTextField.setToolTipText("Autocalculadas");

        IntuicionBasicaTextField.setEditable(false);
        IntuicionBasicaTextField.setText("0");
        IntuicionBasicaTextField.setToolTipText("Autocalculadas");

        PresenciaBasicaTextField.setEditable(false);
        PresenciaBasicaTextField.setText("0");
        PresenciaBasicaTextField.setToolTipText("Autocalculadas");

        RapidezBasicaTextField.setEditable(false);
        RapidezBasicaTextField.setText("0");
        RapidezBasicaTextField.setToolTipText("Autocalculadas");

        FuerzaBasicaTextField.setEditable(false);
        FuerzaBasicaTextField.setText("0");
        FuerzaBasicaTextField.setToolTipText("Autocalculadas");

        jLabel15.setText("B. Raza");

        AgilidadRazaTextField.setEditable(false);
        AgilidadRazaTextField.setText("0");
        AgilidadRazaTextField.setToolTipText("Autocalculadas");

        ConstitucionRazaTextField.setEditable(false);
        ConstitucionRazaTextField.setText("0");
        ConstitucionRazaTextField.setToolTipText("Autocalculadas");

        MemoriaRazaTextField.setEditable(false);
        MemoriaRazaTextField.setText("0");
        MemoriaRazaTextField.setToolTipText("Autocalculadas");

        RazonRazaTextField.setEditable(false);
        RazonRazaTextField.setText("0");
        RazonRazaTextField.setToolTipText("Autocalculadas");

        AutodisciplinaRazaTextField.setEditable(false);
        AutodisciplinaRazaTextField.setText("0");
        AutodisciplinaRazaTextField.setToolTipText("Autocalculadas");

        EmpatiaRazaTextField.setEditable(false);
        EmpatiaRazaTextField.setText("0");
        EmpatiaRazaTextField.setToolTipText("Autocalculadas");

        IntuicionRazaTextField.setEditable(false);
        IntuicionRazaTextField.setText("0");
        IntuicionRazaTextField.setToolTipText("Autocalculadas");

        PresenciaRazaTextField.setEditable(false);
        PresenciaRazaTextField.setText("0");
        PresenciaRazaTextField.setToolTipText("Autocalculadas");

        RapidezRazaTextField.setEditable(false);
        RapidezRazaTextField.setText("0");
        RapidezRazaTextField.setToolTipText("Autocalculadas");

        FuerzaRazaTextField.setEditable(false);
        FuerzaRazaTextField.setText("0");
        FuerzaRazaTextField.setToolTipText("Autocalculadas");

        jLabel16.setText("Total");

        AgilidadTotalTextField.setEditable(false);
        AgilidadTotalTextField.setText("0");
        AgilidadTotalTextField.setToolTipText("Autocalculadas");

        ConstitucionTotalTextField.setEditable(false);
        ConstitucionTotalTextField.setText("0");
        ConstitucionTotalTextField.setToolTipText("Autocalculadas");

        MemoriaTotalTextField.setEditable(false);
        MemoriaTotalTextField.setText("0");
        MemoriaTotalTextField.setToolTipText("Autocalculadas");

        RazonTotalTextField.setEditable(false);
        RazonTotalTextField.setText("0");
        RazonTotalTextField.setToolTipText("Autocalculadas");

        AutodisciplinaTotalTextField.setEditable(false);
        AutodisciplinaTotalTextField.setText("0");
        AutodisciplinaTotalTextField.setToolTipText("Autocalculadas");

        EmpatiaTotalTextField.setEditable(false);
        EmpatiaTotalTextField.setText("0");
        EmpatiaTotalTextField.setToolTipText("Autocalculadas");

        IntuicionTotalTextField.setEditable(false);
        IntuicionTotalTextField.setText("0");
        IntuicionTotalTextField.setToolTipText("Autocalculadas");

        PresenciaTotalTextField.setEditable(false);
        PresenciaTotalTextField.setText("0");
        PresenciaTotalTextField.setToolTipText("Autocalculadas");

        RapidezTotalTextField.setEditable(false);
        RapidezTotalTextField.setText("0");
        RapidezTotalTextField.setToolTipText("Autocalculadas");

        FuerzaTotalTextField.setEditable(false);
        FuerzaTotalTextField.setText("0");
        FuerzaTotalTextField.setToolTipText("Autocalculadas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(IntuicionTemporalSpinner)
                            .addComponent(AparienciaTextField, 0, 1, Short.MAX_VALUE)
                            .addComponent(FuerzaTemporalSpinner)
                            .addComponent(RapidezTemporalSpinner)
                            .addComponent(PresenciaTemporalSpinner)
                            .addComponent(EmpatiaTemporalSpinner)
                            .addComponent(AutodisciplinaTemporalSpinner)
                            .addComponent(RazonTemporalSpinner)
                            .addComponent(MemoriaTemporalSpinner)
                            .addComponent(ConstitucionTemporalSpinner)
                            .addComponent(AgilidadTemporalSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ConstitucionPotencialTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(AgilidadPotencialTextField)
                    .addComponent(FuerzaPotencialTextField)
                    .addComponent(RapidezPotencialTextField)
                    .addComponent(MemoriaPotencialTextField)
                    .addComponent(RazonPotencialTextField)
                    .addComponent(AutodisciplinaPotencialTextField)
                    .addComponent(EmpatiaPotencialTextField)
                    .addComponent(IntuicionPotencialTextField)
                    .addComponent(PresenciaPotencialTextField)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FuerzaBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(RapidezBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(PresenciaBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(IntuicionBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(EmpatiaBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(AutodisciplinaBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(RazonBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(MemoriaBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(ConstitucionBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(AgilidadBasicaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addComponent(FuerzaRazaTextField, 0, 58, Short.MAX_VALUE)
                    .addComponent(RapidezRazaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(PresenciaRazaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(IntuicionRazaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(EmpatiaRazaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(AutodisciplinaRazaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(RazonRazaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(MemoriaRazaTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(AgilidadRazaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(ConstitucionRazaTextField, 0, 1, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RapidezTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addGap(25, 25, 25))
                        .addComponent(AgilidadTotalTextField)
                        .addComponent(ConstitucionTotalTextField, 0, 1, Short.MAX_VALUE))
                    .addComponent(FuerzaTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(AgilidadTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ConstitucionTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(MemoriaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(RazonTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(AutodisciplinaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(EmpatiaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(IntuicionTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(PresenciaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(RapidezTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(FuerzaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaPotencialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(AparienciaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        AceptarButton.setText("Confirmar");

        RandomButton.setText("Aleatorio");

        PDLabel.setText("Puntos de Características:");

        PuntosCaracteristicasGastadosTextField.setEditable(false);
        PuntosCaracteristicasGastadosTextField.setText("0");

        SeparadorLabel.setText("/");

        PuntosCaracteristicasTotalesTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        PuntosCaracteristicasTotalesTextField.setText(Personaje.getInstance().caracteristicas.totalCaracteristicas.toString());
        PuntosCaracteristicasTotalesTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PuntosCaracteristicasTotalesTextFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(PDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PuntosCaracteristicasGastadosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SeparadorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PuntosCaracteristicasTotalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RandomButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AceptarButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarButton)
                    .addComponent(RandomButton)
                    .addComponent(PDLabel)
                    .addComponent(PuntosCaracteristicasGastadosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SeparadorLabel)
                    .addComponent(PuntosCaracteristicasTotalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PuntosCaracteristicasTotalesTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PuntosCaracteristicasTotalesTextFieldKeyReleased
        Personaje.getInstance().caracteristicas.totalCaracteristicas = Integer.parseInt(PuntosCaracteristicasTotalesTextField.getText());
    }//GEN-LAST:event_PuntosCaracteristicasTotalesTextFieldKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarButton;
    private javax.swing.JTextField AgilidadBasicaTextField;
    private javax.swing.JTextField AgilidadPotencialTextField;
    private javax.swing.JTextField AgilidadRazaTextField;
    private javax.swing.JSpinner AgilidadTemporalSpinner;
    private javax.swing.JTextField AgilidadTotalTextField;
    private javax.swing.JTextField AparienciaTextField;
    private javax.swing.JTextField AutodisciplinaBasicaTextField;
    private javax.swing.JTextField AutodisciplinaPotencialTextField;
    private javax.swing.JTextField AutodisciplinaRazaTextField;
    private javax.swing.JSpinner AutodisciplinaTemporalSpinner;
    private javax.swing.JTextField AutodisciplinaTotalTextField;
    private javax.swing.JTextField ConstitucionBasicaTextField;
    private javax.swing.JTextField ConstitucionPotencialTextField;
    private javax.swing.JTextField ConstitucionRazaTextField;
    private javax.swing.JSpinner ConstitucionTemporalSpinner;
    private javax.swing.JTextField ConstitucionTotalTextField;
    private javax.swing.JTextField EmpatiaBasicaTextField;
    private javax.swing.JTextField EmpatiaPotencialTextField;
    private javax.swing.JTextField EmpatiaRazaTextField;
    private javax.swing.JSpinner EmpatiaTemporalSpinner;
    private javax.swing.JTextField EmpatiaTotalTextField;
    private javax.swing.JTextField FuerzaBasicaTextField;
    private javax.swing.JTextField FuerzaPotencialTextField;
    private javax.swing.JTextField FuerzaRazaTextField;
    private javax.swing.JSpinner FuerzaTemporalSpinner;
    private javax.swing.JTextField FuerzaTotalTextField;
    private javax.swing.JTextField IntuicionBasicaTextField;
    private javax.swing.JTextField IntuicionPotencialTextField;
    private javax.swing.JTextField IntuicionRazaTextField;
    private javax.swing.JSpinner IntuicionTemporalSpinner;
    private javax.swing.JTextField IntuicionTotalTextField;
    private javax.swing.JTextField MemoriaBasicaTextField;
    private javax.swing.JTextField MemoriaPotencialTextField;
    private javax.swing.JTextField MemoriaRazaTextField;
    private javax.swing.JSpinner MemoriaTemporalSpinner;
    private javax.swing.JTextField MemoriaTotalTextField;
    private javax.swing.JLabel PDLabel;
    private javax.swing.JTextField PresenciaBasicaTextField;
    private javax.swing.JTextField PresenciaPotencialTextField;
    private javax.swing.JTextField PresenciaRazaTextField;
    private javax.swing.JSpinner PresenciaTemporalSpinner;
    private javax.swing.JTextField PresenciaTotalTextField;
    private javax.swing.JTextField PuntosCaracteristicasGastadosTextField;
    private javax.swing.JFormattedTextField PuntosCaracteristicasTotalesTextField;
    private javax.swing.JButton RandomButton;
    private javax.swing.JTextField RapidezBasicaTextField;
    private javax.swing.JTextField RapidezPotencialTextField;
    private javax.swing.JTextField RapidezRazaTextField;
    private javax.swing.JSpinner RapidezTemporalSpinner;
    private javax.swing.JTextField RapidezTotalTextField;
    private javax.swing.JTextField RazonBasicaTextField;
    private javax.swing.JTextField RazonPotencialTextField;
    private javax.swing.JTextField RazonRazaTextField;
    private javax.swing.JSpinner RazonTemporalSpinner;
    private javax.swing.JTextField RazonTotalTextField;
    private javax.swing.JLabel SeparadorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

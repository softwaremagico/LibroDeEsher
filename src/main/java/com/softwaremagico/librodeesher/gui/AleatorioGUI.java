/*
 * AleatorioGUI.java
 *
 * Created on 27 de noviembre de 2007, 14:38
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

import com.softwaremagico.librodeesher.Categoria;
import com.softwaremagico.librodeesher.Esher;
import com.softwaremagico.librodeesher.Habilidad;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;

/**
 *
 * @author  jorge
 */
public class AleatorioGUI extends javax.swing.JFrame {

    private Esher esher;
    private List<String> adiestramientosDisponibles;

    /** Creates new form AleatorioGUI */
    public AleatorioGUI(Esher tmp_esher) {
        esher = tmp_esher;
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        IniciaHabilidades();
        adiestramientosDisponibles = esher.DevolverAdiestramientosDisponibles();
        RellenarAdiestramientos();
    }

    public void Iniciar() {
    }

    public void Refrescar() {
    }

    public void Reset() {
        EspecializacionSlider.setValue(1);
        NivelSpinner.setValue(1);
        IniciaHabilidades();
        esher.pj.adiestramientosSugeridos = new ArrayList<String>();
        ActualizaAdiestramientosSugeridos();
    }

    public void ObtenerValorAleatorio() {
        esher.especializacion = EspecializacionSlider.getValue();
    }

    public void LevelInRange() {
        if ((Integer) NivelSpinner.getValue() < 1) {
            NivelSpinner.setValue(1);
        }
        if ((Integer) NivelSpinner.getValue() > 250) {
            NivelSpinner.setValue(250);
        }
    }

    public Integer DevolverNivelFinal() {
        return (Integer) NivelSpinner.getValue();
    }

    /**********************************************************************
     *
     *                       LOGICA HABILIDADES
     *
     **********************************************************************/
    /**
     * Inicial la interfaz gráfica relacionada con las características.
     */
    public void IniciaHabilidades() {
        ActualizarCategoriasComboBox();
        ActualizarHabilidadesComboBox();
        ActualizarCategoriaSeleccionada();
        ActualizarHabilidadSeleccionada();
        RangosCategoriaSpinner.setValue(0);
    }

    private Categoria DevolverCategoriaSeleccionada() {
        try {
            return esher.pj.categorias.get(CategoriasComboBox.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    private Habilidad DevolverHabilidadSeleccionada() {
        Categoria cat = DevolverCategoriaSeleccionada();
        try {
            return cat.listaHabilidades.get(HabilidadesComboBox.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    public void ActualizarCategoriasComboBox() {
        CategoriasComboBox.removeAllItems();
        for (int i = 0; i < esher.pj.categorias.size(); i++) {
            Categoria cat = esher.pj.categorias.get(i);
            CategoriasComboBox.addItem(cat.DevolverNombre());
        }
    }

    public void ActualizarCategoriaSeleccionada() {
        try {
            Categoria cat = DevolverCategoriaSeleccionada();
            RangosCategoriaSpinner.setValue(cat.rangosSugeridos);
            ActualizarHabilidadesComboBox();

        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarHabilidadesComboBox() {
        HabilidadesComboBox.removeAllItems();
        Categoria cat = DevolverCategoriaSeleccionada();
        try {
            cat.OrdenarHabilidades();
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                //Al actualizar el ComboBox, a veces se vuelven a añadir elementos dos veces.
                HabilidadesComboBox.removeItem(hab.DevolverNombre());
                HabilidadesComboBox.addItem(hab.DevolverNombre());
            }
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarHabilidadesComboBox(String nombreSeleccionado) {
        ActualizarHabilidadesComboBox();
        try {
            HabilidadesComboBox.setSelectedItem(nombreSeleccionado);
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarHabilidadSeleccionada() {
        try {
            Habilidad hab = DevolverHabilidadSeleccionada();
            RangosHabilidadSpinner.setValue(hab.rangosSugeridos);
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarRangosCategoria() {
        if ((Integer) RangosCategoriaSpinner.getValue() < 0) {
            RangosCategoriaSpinner.setValue(0);
        }
        Categoria cat = DevolverCategoriaSeleccionada();
        cat.rangosSugeridos = (Integer) RangosCategoriaSpinner.getValue();
    }

    public void ActualizarRangosHabilidad() {
        if ((Integer) RangosHabilidadSpinner.getValue() < 0) {
            RangosHabilidadSpinner.setValue(0);
        }
        Habilidad hab = DevolverHabilidadSeleccionada();
        hab.rangosSugeridos = (Integer) RangosHabilidadSpinner.getValue();
    }

    /**********************************************************************
     *
     *                       LOGICA ADIESTRAMIENTOS
     *
     **********************************************************************/
    /**
     * Completa la lista.
     */
    private void RellenarAdiestramientos() {
        AdiestramientosComboBox.removeAllItems();
        for (int i = 0; i < adiestramientosDisponibles.size(); i++) {
            AdiestramientosComboBox.addItem(adiestramientosDisponibles.get(i));
        }
    }

    private void ActualizaAdiestramientosSugeridos() {
        String text = "";
        for (int i = 0; i < esher.pj.adiestramientosSugeridos.size(); i++) {
            text += esher.pj.adiestramientosSugeridos.get(i);
            if (i < esher.pj.adiestramientosSugeridos.size() - 1) {
                text += ", ";
            }
        }
        EscogidosTextField.setText(text);
    }

    /***********************************************
     *
     *                    LISTENERS
     *
     ************************************************/
    /**
     * Añade el listener al botón aceptar. 
     */
    public void addAceptarButtonListener(ActionListener al) {
        AceptarButton.addActionListener(al);
    }

    public void addCancelarButtonListener(ActionListener al) {
        CancelarButton.addActionListener(al);
    }

    public void addSliderListener(ChangeListener al) {
        EspecializacionSlider.addChangeListener(al);
    }

    public void addSubirNivelSpinnerListener(ChangeListener al) {
        NivelSpinner.addChangeListener(al);
    }

    public void addAplicarInteligenciaCheckBox(ActionListener al) {
        InteligenciaCheckBox.addActionListener(al);
    }

    public void addRangosCategoriasSpinnerListener(ChangeListener al) {
        RangosCategoriaSpinner.addChangeListener(al);
    }

    public void addRangosHabilidadSpinnerListener(ChangeListener al) {
        RangosHabilidadSpinner.addChangeListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        EspecializacionPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        EspecializacionSlider = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        AceptarButton = new javax.swing.JButton();
        CancelarButton = new javax.swing.JButton();
        NivelSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        InteligenciaCheckBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        CategoriasComboBox = new javax.swing.JComboBox();
        RangosCategoriaSpinner = new javax.swing.JSpinner();
        RangosHabilidadSpinner = new javax.swing.JSpinner();
        HabilidadesComboBox = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        AdiestramientosComboBox = new javax.swing.JComboBox();
        EscogidosTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        AñadirButton = new javax.swing.JButton();
        BorrarButton = new javax.swing.JButton();

        setTitle("Generar Personaje de Forma Aleatoria");
        setResizable(false);

        EspecializacionPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Especializado");

        EspecializacionSlider.setMajorTickSpacing(1);
        EspecializacionSlider.setMaximum(4);
        EspecializacionSlider.setMinimum(-2);
        EspecializacionSlider.setMinorTickSpacing(1);
        EspecializacionSlider.setPaintTicks(true);
        EspecializacionSlider.setSnapToTicks(true);
        EspecializacionSlider.setToolTipText("Cuanto más especializado sea el Pj, mas puntos tendrán las habilidades pero menos habilidades tendrá. ");
        EspecializacionSlider.setValue(1);

        jLabel2.setText("Generalizado");

        javax.swing.GroupLayout EspecializacionPanelLayout = new javax.swing.GroupLayout(EspecializacionPanel);
        EspecializacionPanel.setLayout(EspecializacionPanelLayout);
        EspecializacionPanelLayout.setHorizontalGroup(
            EspecializacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EspecializacionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EspecializacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EspecializacionSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                    .addGroup(EspecializacionPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        EspecializacionPanelLayout.setVerticalGroup(
            EspecializacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EspecializacionPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(EspecializacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EspecializacionSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        AceptarButton.setText("Aceptar");

        CancelarButton.setText("Cancelar");

        NivelSpinner.setValue(1);

        jLabel3.setText("Nivel:");

        InteligenciaCheckBox.setSelected(true);
        InteligenciaCheckBox.setText("Preferidas");
        InteligenciaCheckBox.setToolTipText("Da preferencia a algunas habilidades consideradas más adecuadas.");
        InteligenciaCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Habilidades deseadas:");

        jLabel17.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel17.setText("Categoría:");

        CategoriasComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CategoriasComboBoxActionPerformed(evt);
            }
        });

        HabilidadesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HabilidadesComboBoxActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel18.setText("Habilidad:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(HabilidadesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CategoriasComboBox, 0, 287, Short.MAX_VALUE))
                        .addGap(6, 6, 6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RangosHabilidadSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(RangosCategoriaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(CategoriasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(RangosCategoriaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RangosHabilidadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Adiestramientos deseados:");

        EscogidosTextField.setEditable(false);

        jLabel6.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel6.setText("Adiestramientos:");

        jLabel7.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel7.setText("Escogidos:");

        AñadirButton.setText("Añadir");
        AñadirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñadirButtonActionPerformed(evt);
            }
        });

        BorrarButton.setText("Borrar");
        BorrarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EscogidosTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(AdiestramientosComboBox, 0, 213, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BorrarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AñadirButton, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(AdiestramientosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(AñadirButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(EscogidosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(BorrarButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EspecializacionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NivelSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(InteligenciaCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                        .addComponent(CancelarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AceptarButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EspecializacionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarButton)
                    .addComponent(CancelarButton)
                    .addComponent(jLabel3)
                    .addComponent(NivelSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InteligenciaCheckBox))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CategoriasComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CategoriasComboBoxActionPerformed
        ActualizarCategoriaSeleccionada();
    }//GEN-LAST:event_CategoriasComboBoxActionPerformed

    private void HabilidadesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HabilidadesComboBoxActionPerformed
        ActualizarHabilidadSeleccionada();
    }//GEN-LAST:event_HabilidadesComboBoxActionPerformed

    private void BorrarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarButtonActionPerformed
        esher.pj.adiestramientosSugeridos = new ArrayList<String>();
        ActualizaAdiestramientosSugeridos();
    }//GEN-LAST:event_BorrarButtonActionPerformed

    private void AñadirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñadirButtonActionPerformed
        if (AdiestramientosComboBox.getItemCount() > 0) {
            String adiestramiento = AdiestramientosComboBox.getItemAt(AdiestramientosComboBox.getSelectedIndex()).toString();
            if (!esher.pj.adiestramientosSugeridos.contains(adiestramiento)) {
                esher.pj.adiestramientosSugeridos.add(adiestramiento);
            }
        }
        ActualizaAdiestramientosSugeridos();
    }//GEN-LAST:event_AñadirButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarButton;
    private javax.swing.JComboBox AdiestramientosComboBox;
    private javax.swing.JButton AñadirButton;
    private javax.swing.JButton BorrarButton;
    private javax.swing.JButton CancelarButton;
    private javax.swing.JComboBox CategoriasComboBox;
    private javax.swing.JTextField EscogidosTextField;
    private javax.swing.JPanel EspecializacionPanel;
    private javax.swing.JSlider EspecializacionSlider;
    private javax.swing.JComboBox HabilidadesComboBox;
    private javax.swing.JCheckBox InteligenciaCheckBox;
    private javax.swing.JSpinner NivelSpinner;
    private javax.swing.JSpinner RangosCategoriaSpinner;
    private javax.swing.JSpinner RangosHabilidadSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}

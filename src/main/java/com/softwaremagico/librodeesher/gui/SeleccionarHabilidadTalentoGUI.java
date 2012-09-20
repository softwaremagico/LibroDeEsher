/*
 * ElegirComunProfesional.java
 *
 * Created on 15 de enero de 2008, 22:03
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
import com.softwaremagico.librodeesher.Personaje;
import com.softwaremagico.librodeesher.Talento;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  jorge
 */
public class SeleccionarHabilidadTalentoGUI extends javax.swing.JFrame {

    Esher esher;
    Habilidad habilidad;
    Categoria cat;
    int maxElegir;
    Personaje pj;
    List<String> listadoHabilidades = new ArrayList<String>();
    Talento talento = null;
    int bonus = 0;
    boolean añadir = true;

    /** Creates new form ElegirComunProfesional */
    SeleccionarHabilidadTalentoGUI(Esher tmp_esher, Talento tmp_talento, int tmp_bonus,
            boolean tmp_añadir, List<String> tmp_habilidadesNuevas, int cuantas) {
        esher = tmp_esher;
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        Inicializar();
        talento = tmp_talento;
        añadir = tmp_añadir;
        bonus = tmp_bonus;
        maxElegir = Math.abs(cuantas);
        if (añadir) {
            this.setTitle("Selecciona la habilidad o categoría para escoger.");
        } else {
            this.setTitle("Selecciona a que habilidad NO se aplicará el talento.");
        }
        listadoHabilidades = tmp_habilidadesNuevas;
    }

    private void Inicializar() {
        TipoCheckBox.setText("Seleccionar");
    }

    void Refrescar() {
        ActualizarHabilidadesComboBox();
        ActualizaHabilidadesRestantes();
    }

    void ActualizaHabilidadesRestantes() {
        if (añadir) {
            NumeroTextField.setText(maxElegir - talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.size() + "");
            if (maxElegir - talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.size() > 0 ||
                    talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.contains(HabilidadesComboBox.getSelectedItem().toString())) {
                TipoCheckBox.setEnabled(true);
            } else {
                TipoCheckBox.setEnabled(false);
            }
        } else {
            NumeroTextField.setText(maxElegir + "");
            if (talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.size() > 0) {
                this.dispose();
            }
        }
    }

    void AñadirEliminarHabilidadSeleccionada() {
        String hab = HabilidadesComboBox.getSelectedItem().toString();

        //Añadimos la seleccionada.
        if (añadir) {
            if (TipoCheckBox.isSelected()) {
                if (esher.pj.DevolverHabilidadDeNombre(hab) != null) {
                    talento.AddHabilidadAfectada(hab, bonus);
                } else {
                    if (esher.pj.DevolverCategoriaDeNombre(hab) != null) {
                        talento.AddCategoriaAfectada(hab, bonus);
                        this.dispose();
                    }
                }
                } else  {
                talento.RemoveHabilidadCategoriaAfectada(hab);
            }
        //Añadimos todas menos la seleccionada.
        } else {
            if (TipoCheckBox.isSelected()) {
                for (int i = 0; i < HabilidadesComboBox.getItemCount(); i++) {
                    if (!HabilidadesComboBox.getItemAt(i).toString().equals(hab)) {
                        talento.AddCategoriaElegidaListadoCategoria(HabilidadesComboBox.getItemAt(i).toString());
                    }
                }
            } else {
                talento.LimpiarHabilidadesAfectadas();
            }
        }
        Refrescar();
    }

    void ActualizarHabilidadesComboBox() {
        HabilidadesComboBox.removeAllItems();
        for(int i=0; i<talento.bonusCategoriaHabilidadElegir.listadoCategoriasHabilidadesPosiblesAElegir.size();i++){
            HabilidadesComboBox.addItem(talento.bonusCategoriaHabilidadElegir.listadoCategoriasHabilidadesPosiblesAElegir.get(i));
        }
    }

    void addHabilidadesCheckBoxListener(ActionListener al) {
        TipoCheckBox.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HabilidadesComboBox = new javax.swing.JComboBox();
        TipoCheckBox = new javax.swing.JCheckBox();
        NumeroTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nueva Habilidad Disponible");
        setAlwaysOnTop(true);
        setResizable(false);

        HabilidadesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HabilidadesComboBoxActionPerformed(evt);
            }
        });

        TipoCheckBox.setText("Ok");
        TipoCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        TipoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoCheckBoxActionPerformed(evt);
            }
        });

        NumeroTextField.setEditable(false);
        NumeroTextField.setToolTipText("Numero de habilidades que pueden ser escogidas.");

        jLabel2.setText("Selecciona:");

        jLabel3.setText("Elegir:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TipoCheckBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(NumeroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TipoCheckBox)
                    .addComponent(NumeroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void HabilidadesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HabilidadesComboBoxActionPerformed
        try {
            Habilidad hab = pj.DevolverHabilidadDeNombre(HabilidadesComboBox.getSelectedItem().toString());
            if (habilidad.categoriaPadre.listaHabilidades.contains(hab)) {
                TipoCheckBox.setSelected(true);
            } else {
                TipoCheckBox.setSelected(false);
            }
        } catch (NullPointerException npe) {
        }
        ActualizaHabilidadesRestantes();
    }//GEN-LAST:event_HabilidadesComboBoxActionPerformed

    private void TipoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoCheckBoxActionPerformed
        AñadirEliminarHabilidadSeleccionada();
        ActualizaHabilidadesRestantes();
    }//GEN-LAST:event_TipoCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox HabilidadesComboBox;
    private javax.swing.JTextField NumeroTextField;
    private javax.swing.JCheckBox TipoCheckBox;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}

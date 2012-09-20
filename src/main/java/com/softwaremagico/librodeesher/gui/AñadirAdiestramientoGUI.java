/*
 * AñadirAdiestramientoGUI.java
 *
 * Created on 23 de diciembre de 2007, 11:31
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
import com.softwaremagico.librodeesher.LeerAdiestramientos;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author  jorge
 */
public class AñadirAdiestramientoGUI extends javax.swing.JFrame {

    private Esher esher;

    /**
     * Creates new form AñadirAdiestramientoGUI
     */
    public AñadirAdiestramientoGUI(Esher tmp_esher) {
        esher = tmp_esher;
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
    }

    public void Refrescar() {
        RellenarAdiestramientos();
        ActualizarPuntosDesarrollo();
        ActualizarCosteAdiestramiento();
    }

    private void ActualizarPuntosDesarrollo() {
        PDTextField.setText(esher.pj.PuntosDesarrolloNoGastados() + "");
    }

    public void ActualizarCosteAdiestramiento() {
        if (AdiestramientosComboBox.getSelectedIndex() >= 0) {
            LeerAdiestramientos adiestramiento = new LeerAdiestramientos(esher, DevolverAdiestramientoSeleccionado(), false);
            CosteTextField.setText(adiestramiento.DevolverCosteDeAdiestramiento() + "");
            if (esher.pj.PuntosDesarrolloNoGastados() >= adiestramiento.DevolverCosteDeAdiestramiento()) {
                AceptarButton.setEnabled(true);
            } else {
                AceptarButton.setEnabled(false);
            }
        } else {
            CosteTextField.setText("");
        }
    }

    private void RellenarAdiestramientos() {
        AdiestramientosComboBox.removeAllItems();
        List<String> adiestramientosPosibles = esher.DevolverAdiestramientosPosibles();
        adiestramientosPosibles = esher.OrdenarLista(adiestramientosPosibles);        
        
        for (int i = 0; i < adiestramientosPosibles.size(); i++) {
            if (esher.pj.costesAdiestramientos.ObtenerCosteAdiestramiento(adiestramientosPosibles.get(i)) < 299) {
                AdiestramientosComboBox.addItem(adiestramientosPosibles.get(i));
            }
        }
    }

    public String DevolverAdiestramientoSeleccionado() {
        if (AdiestramientosComboBox.getSelectedIndex() >= 0) {
            return AdiestramientosComboBox.getSelectedItem().toString();
        }
        return null;
    }

    /************************************************
     *
     *                    LISTENERS
     *
     ************************************************/
    public void AddCancelButtonListener(ActionListener al) {
        CancelarButton.addActionListener(al);
    }

    public void AddAceptarButtonListener(ActionListener al) {
        AceptarButton.addActionListener(al);
    }

    public void addElegirAdiestramientoListener(ActionListener al) {
        AdiestramientosComboBox.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        PDTextField = new javax.swing.JTextField();
        AdiestramientosComboBox = new javax.swing.JComboBox();
        CosteTextField = new javax.swing.JTextField();
        AceptarButton = new javax.swing.JButton();
        CancelarButton = new javax.swing.JButton();

        setTitle("Seleccionar Adiestramiento");
        setResizable(false);

        jLabel1.setText("Puntos Desarrollo:");

        PDTextField.setEditable(false);
        PDTextField.setText("0");

        CosteTextField.setEditable(false);
        CosteTextField.setText("0");

        AceptarButton.setText("Aceptar");

        CancelarButton.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AdiestramientosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PDTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(CancelarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(AceptarButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AceptarButton, CancelarButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdiestramientosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarButton)
                    .addComponent(CancelarButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarButton;
    private javax.swing.JComboBox AdiestramientosComboBox;
    private javax.swing.JButton CancelarButton;
    private javax.swing.JTextField CosteTextField;
    private javax.swing.JTextField PDTextField;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}

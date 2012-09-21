/*
 * TalentosGUI.java
 *
 * Created on 13 de junio de 2008, 17:55
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
import com.softwaremagico.librodeesher.Personaje;
import com.softwaremagico.librodeesher.Talento;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author  jorge
 */
public class TalentosGUI extends javax.swing.JFrame {

    private ElegirComunProfesionalGUI grupoHab = null;
    private SeleccionarHabilidadTalentoGUI selecHab = null;

    /** Creates new form TalentosGUI */
    public TalentosGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
    }

    public void Iniciar() {
        Refrescar();
    }

    public void Refrescar() {
        ActualizarTalentosComboBox();
        ActualizarTalentoSeleccionado();
        MostrarTalentosCogidos();
    }

    private void MostrarTalentosCogidos() {
        String texto = "";
        for (int i = 0; i < Personaje.getInstance().talentos.size(); i++) {
            Talento talento = Personaje.getInstance().talentos.get(i);
            texto += talento.nombre + ": " + talento.listadoCategorias + " (" +
                    talento.Descripcion() + ").\n\n";
        }
        TalentosCogidosTextArea.setText(texto);
    }

    public void ActualizarTalentoSeleccionado() {
        try{
        Talento talento = Esher.talentos.DevolverTalento(TalentosComboBox.getSelectedItem().toString());
        CosteTextField.setText(talento.coste + "");
        TipoTextField.setText(talento.clasificacion);
        DecripcionTextArea.setText(talento.listadoCategorias + "\n------\n" + talento.Descripcion());
        PuntosTextField.setText(Personaje.getInstance().DevolverPuntosTalentosRestantes() + "");
        if (Personaje.getInstance().DevolverTalento(talento.nombre) != null) {
            SeleccionarCheckBox.setSelected(true);
        } else {
            SeleccionarCheckBox.setSelected(false);
        }
        if (talento.coste > Personaje.getInstance().DevolverPuntosTalentosRestantes() && !SeleccionarCheckBox.isSelected()) {
            SeleccionarCheckBox.setEnabled(false);
        } else {
            SeleccionarCheckBox.setEnabled(true);
        }
        }catch(NullPointerException npe){}
    }

    private void ActualizarTalentosComboBox() {
        TalentosComboBox.removeAllItems();
        for (int i = 0; i < Esher.talentos.Size(); i++) {
            Talento tal = Esher.talentos.Get(i);
            if (tal.EsTalentoPermitido()) {
                TalentosComboBox.addItem(tal.nombre);
            }
        }
    }

    public void SeleccionaTalento() {
        Talento talento = Esher.talentos.DevolverTalento(TalentosComboBox.getSelectedItem().toString());
        //Borramos las elegidas por si hay alguna anterior.
        if (talento.bonusCategoriaHabilidadElegir != null) {
            talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas = new ArrayList<>();
        }
        if (SeleccionarCheckBox.isSelected()) {
            Personaje.getInstance().talentos.add(talento);
            //Si no se ha escogido habilidad común, es un buen momento.
            for (int i = 0; i < talento.bonusCategoria.size(); i++) {
                if (talento.bonusCategoria.get(i).habilidadComun) {
                    if (talento.bonusCategoria.get(i).habilidadEscogida == null) {
                        if (grupoHab != null) {
                            grupoHab.dispose();
                            grupoHab = null;
                        }
                        grupoHab = new ElegirComunProfesionalGUI("Común",
                                Personaje.getInstance().DevolverCategoriaDeNombre(talento.bonusCategoria.get(i).nombre),
                                1, talento);
                        grupoHab.setVisible(true);
                    }
                }
            }
            //Selecciona la habilidad o categoría si es necesario.
            if (talento.bonusCategoriaHabilidadElegir != null) {
                selecHab = new SeleccionarHabilidadTalentoGUI(talento,
                        talento.bonusCategoriaHabilidadElegir.bonus,
                        talento.bonusCategoriaHabilidadElegir.añadir,
                        talento.bonusCategoriaHabilidadElegir.listadoCategoriasHabilidadesPosiblesAElegir,
                        talento.bonusCategoriaHabilidadElegir.cuantas);
                selecHab.setVisible(true);
                selecHab.Refrescar();
            }
        } else {
            for (int j = 0; j < talento.bonusCategoria.size(); j++) {
                talento.bonusCategoria.get(j).habilidadEscogida = null;
            }
            Personaje.getInstance().EliminarTalento(TalentosComboBox.getSelectedItem().toString());
        }
        MostrarTalentosCogidos();
    }

    /************************************************
     *
     *                    LISTENERS
     *
     ************************************************/
    /**
     * Añade un listener a un objeto.
     */
    public void addCategoriasComboBoxListener(ActionListener al) {
        TalentosComboBox.addActionListener(al);
    }

    public void addSeleccionarCheckBoxListener(ActionListener al) {
        SeleccionarCheckBox.addActionListener(al);
    }

    public void addCerrarCheckBoxListener(ActionListener al) {
        CerrarButton.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        TalentosComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        CosteTextField = new javax.swing.JTextField();
        TipoTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DecripcionTextArea = new javax.swing.JTextArea();
        SeleccionarCheckBox = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        PuntosTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CerrarButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TalentosCogidosTextArea = new javax.swing.JTextArea();

        setTitle("Selección de Talentos");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Coste:");

        jLabel3.setText("Tipo:");

        CosteTextField.setEditable(false);
        CosteTextField.setToolTipText("Coste en puntos de talentos.");

        TipoTextField.setEditable(false);
        TipoTextField.setToolTipText("Clasificación según su potencia.");

        jLabel1.setText("Descripción:");

        DecripcionTextArea.setColumns(20);
        DecripcionTextArea.setEditable(false);
        DecripcionTextArea.setLineWrap(true);
        DecripcionTextArea.setRows(5);
        jScrollPane1.setViewportView(DecripcionTextArea);

        SeleccionarCheckBox.setText("Seleccionar");

        jLabel4.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel4.setText("Talento:");

        PuntosTextField.setEditable(false);
        PuntosTextField.setToolTipText("Puntos restantes para comprar talentos");

        jLabel5.setText("Restantes:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(TalentosComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 242, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(TipoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(SeleccionarCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PuntosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CosteTextField, TipoTextField});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TalentosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TipoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SeleccionarCheckBox)
                    .addComponent(PuntosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        CerrarButton.setText("Cerrar");
        CerrarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarButtonActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel6.setText("Talentos escogidos:");

        TalentosCogidosTextArea.setColumns(20);
        TalentosCogidosTextArea.setEditable(false);
        TalentosCogidosTextArea.setLineWrap(true);
        TalentosCogidosTextArea.setRows(5);
        jScrollPane2.setViewportView(TalentosCogidosTextArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CerrarButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CerrarButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void CerrarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarButtonActionPerformed
        this.setVisible(false);
}//GEN-LAST:event_CerrarButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CerrarButton;
    private javax.swing.JTextField CosteTextField;
    private javax.swing.JTextArea DecripcionTextArea;
    private javax.swing.JTextField PuntosTextField;
    private javax.swing.JCheckBox SeleccionarCheckBox;
    private javax.swing.JTextArea TalentosCogidosTextArea;
    private javax.swing.JComboBox TalentosComboBox;
    private javax.swing.JTextField TipoTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}

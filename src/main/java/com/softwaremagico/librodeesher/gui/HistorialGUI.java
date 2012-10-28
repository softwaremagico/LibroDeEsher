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
 Created on february of 2008.
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
import java.awt.Toolkit;
import java.awt.event.ActionListener;

/**
 *
 * @author jorge
 */
public class HistorialGUI extends javax.swing.JFrame {


    /**
     * Creates new form HistorialGUI
     */
    public HistorialGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
    }

    public void Iniciar() {
        ActualizarCategoriasComboBox();
        ActualizarHabilidadesComboBox();
        ActualizarCategoriaCheckBox();
        ActualizarHabilidadCheckBox();
        ActualizarPuntosHistorial();
        GastadosTodoHistorial();
    }

    public void GastadosTodoHistorial() {
        if ((Personaje.getInstance().DevolverPuntosHistoriaTotales() - Personaje.getInstance().DevolverPuntosHistorialGastados()) == 0) {
            AumentoCaracteristicaButton.setEnabled(false);
            ObjetoButton.setEnabled(false);
        }
    }

    public void ActualizarPuntosHistorial() {
        PuntosHistorialTextField.setText((Personaje.getInstance().DevolverPuntosHistoriaTotales()
                - Personaje.getInstance().DevolverPuntosHistorialGastados()) + "");
    }

    private void ActualizarCategoriasComboBox() {
        CategoriasComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            CategoriasComboBox.addItem(cat.DevolverNombre());
        }
    }

    public void ActualizarHabilidadesComboBox() {
        HabilidadesComboBox.removeAllItems();
        Categoria cat = DevolverCategoriaSeleccionada();
        try {
            cat.OrdenarHabilidades();
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                HabilidadesComboBox.addItem(hab.DevolverNombre());
            }
        } catch (NullPointerException npe) {
        }
    }

    public Categoria DevolverCategoriaSeleccionada() {
        try {
            return Personaje.getInstance().categorias.get(CategoriasComboBox.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    public Habilidad DevolverHabilidadSeleccionada() {
        Categoria cat = DevolverCategoriaSeleccionada();
        try {
            return cat.listaHabilidades.get(HabilidadesComboBox.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    public void ActualizarCategoriaCheckBox() {
        Categoria cat = DevolverCategoriaSeleccionada();
        if (cat.TipoCategoria().equals("DPP")
                || cat.TipoCategoria().equals("DF")) {
            CategoriaCheckBox.setEnabled(false);
        } else {
            CategoriaCheckBox.setEnabled(true);
        }
        CategoriaCheckBox.setSelected(DevolverCategoriaSeleccionada().historial);
    }

    public void ActualizarHabilidadCheckBox() {
        try {
            HabilidadCheckBox.setSelected(DevolverHabilidadSeleccionada().historial);
        } catch (NullPointerException npe) {
        }
    }

    public void GastarPuntoHistorialEnCategoria() {
        if (!CategoriaCheckBox.isSelected() || Personaje.getInstance().DevolverPuntosHistorialGastados() < Personaje.getInstance().DevolverPuntosHistoriaTotales()) {
            try {
                DevolverCategoriaSeleccionada().historial = CategoriaCheckBox.isSelected();
            } catch (NullPointerException npe) {
                MostrarMensaje.showErrorMessage("Punto de historial no recogido.", "Historial");
            }
        } else {
            CategoriaCheckBox.setSelected(false);
        }
        ActualizarPuntosHistorial();
    }

    public void GastarPuntoHistorialEnHabilidad() {
        if (!HabilidadCheckBox.isSelected() || Personaje.getInstance().DevolverPuntosHistorialGastados() < Personaje.getInstance().DevolverPuntosHistoriaTotales()) {
            try {
                DevolverHabilidadSeleccionada().historial = HabilidadCheckBox.isSelected();
            } catch (NullPointerException npe) {
                MostrarMensaje.showErrorMessage("Punto de historial no recogido.", "Historial");
            }
        } else {
            HabilidadCheckBox.setSelected(false);
        }
        ActualizarPuntosHistorial();
    }

    /**
     * **********************************************
     *
     * LISTENERS
     *
     ***********************************************
     */
    /**
     * Añade un listener a un objeto.
     */
    public void addCategoriaCheckBoxListener(ActionListener al) {
        CategoriaCheckBox.addActionListener(al);
    }

    public void addHabilidadCheckBoxListener(ActionListener al) {
        HabilidadCheckBox.addActionListener(al);
    }

    public void addCategoriasComboBoxListener(ActionListener al) {
        CategoriasComboBox.addActionListener(al);
    }

    public void addHabilidadesComboBoxListener(ActionListener al) {
        HabilidadesComboBox.addActionListener(al);
    }

    public void addAumentoCaracteristicasButtonListener(ActionListener al) {
        AumentoCaracteristicaButton.addActionListener(al);
    }

    public void addObjetoButtonListener(ActionListener al) {
        ObjetoButton.addActionListener(al);
    }

    public void addCerrarListener(ActionListener al) {
        CerrarButton.addActionListener(al);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HabilidadesPanel = new javax.swing.JPanel();
        CategoriasComboBox = new javax.swing.JComboBox();
        HabilidadesComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CategoriaCheckBox = new javax.swing.JCheckBox();
        HabilidadCheckBox = new javax.swing.JCheckBox();
        CerrarButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        PuntosHistorialTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        AumentoCaracteristicaButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        ObjetoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Puntos de Historial");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        HabilidadesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Habilidades:");

        jLabel2.setText("Categorias:");

        CategoriaCheckBox.setText("Historial");

        HabilidadCheckBox.setText("Historial");

        javax.swing.GroupLayout HabilidadesPanelLayout = new javax.swing.GroupLayout(HabilidadesPanel);
        HabilidadesPanel.setLayout(HabilidadesPanelLayout);
        HabilidadesPanelLayout.setHorizontalGroup(
            HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(HabilidadesComboBox, 0, 245, Short.MAX_VALUE)
                    .addComponent(CategoriasComboBox, 0, 245, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CategoriaCheckBox)
                    .addComponent(HabilidadCheckBox))
                .addContainerGap())
        );
        HabilidadesPanelLayout.setVerticalGroup(
            HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CategoriaCheckBox)
                    .addComponent(CategoriasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HabilidadCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CerrarButton.setText("Cerrar");

        jLabel3.setText("Puntos Restantes:");

        PuntosHistorialTextField.setEditable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Características:");

        AumentoCaracteristicaButton.setText("Aumento Características");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(AumentoCaracteristicaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(AumentoCaracteristicaButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Objetos:");

        ObjetoButton.setText("Añadir Objeto");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(ObjetoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(ObjetoButton))
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
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(PuntosHistorialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CerrarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(HabilidadesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HabilidadesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(PuntosHistorialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CerrarButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        GastadosTodoHistorial();
    }//GEN-LAST:event_formWindowGainedFocus
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AumentoCaracteristicaButton;
    private javax.swing.JCheckBox CategoriaCheckBox;
    private javax.swing.JComboBox CategoriasComboBox;
    private javax.swing.JButton CerrarButton;
    private javax.swing.JCheckBox HabilidadCheckBox;
    private javax.swing.JComboBox HabilidadesComboBox;
    private javax.swing.JPanel HabilidadesPanel;
    private javax.swing.JButton ObjetoButton;
    private javax.swing.JTextField PuntosHistorialTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}

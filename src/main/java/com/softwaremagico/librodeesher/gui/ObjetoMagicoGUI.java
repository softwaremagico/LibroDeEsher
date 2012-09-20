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
Created on november of 2008.
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
import com.softwaremagico.librodeesher.ObjetoMagico;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;

/**
 *
 * @author  jorge
 */
public class ObjetoMagicoGUI extends javax.swing.JFrame {

    Esher esher;

    /** Creates new form ObjetoMagicoGUI */
    public ObjetoMagicoGUI(Esher tmp_esher) {
        esher = tmp_esher;
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        IniciarObjetos();
    }

    /**********************************************************************
     *
     *                              OBJETOS
     *
     **********************************************************************/
    /**
     * Prepara la interfaz de objetos mágicos.
     */
    public void IniciarObjetos() {
        ActualizarCategoriasObjetosComboBox();
        ActivarObjetosMagicos();
        ActualizarListadoObjetos();
    }

    public void RefrescarObjetos() {
        ActivarObjetosMagicos();
        ActualizarListadoObjetos();
    }

    private void ActualizarCategoriasObjetosComboBox() {
        CategoriasObjetoComboBox.removeAllItems();
        for (int i = 0; i < esher.pj.categorias.size(); i++) {
            Categoria cat = esher.pj.categorias.get(i);
            CategoriasObjetoComboBox.addItem(cat.DevolverNombre());
        }
        ActualizarHabilidadesObjetosComboBox();
    }

   public  void ActualizarHabilidadesObjetosComboBox() {
        HabilidadesObjetoComboBox.removeAllItems();
        try {
            Categoria cat = DevolverCategoriaObjetoSeleccionada();
            cat.OrdenarHabilidades();
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                HabilidadesObjetoComboBox.addItem(hab.DevolverNombre());
            }
            ActualizarHabilidadBonusSpinner();
        } catch (NullPointerException npe) {
        }
    }

    private Categoria DevolverCategoriaObjetoSeleccionada() {
        try {
            return esher.pj.DevolverCategoriaDeNombre(CategoriasObjetoComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    private Habilidad DevolverHabilidadObjetoSeleccionada() {
        Categoria cat = DevolverCategoriaObjetoSeleccionada();
        try {
            return cat.DevolverHabilidadDeNombre(HabilidadesObjetoComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        } catch (IndexOutOfBoundsException iob) {
            return null;
        }
    }

    public void ActualizarCategoriaBonusSpinner() {
        ObjetoMagico objeto;
        if ((objeto = esher.pj.DevolverObjetoMagico(NombreObjetoTextField.getText())) != null) {
            BonusCategoriaSpinner.setValue(objeto.DevolverBonusCategoria(DevolverCategoriaObjetoSeleccionada()));
        } else {
            BonusCategoriaSpinner.setValue(0);
        }
    }

    public void ActualizarHabilidadBonusSpinner() {
        ObjetoMagico objeto;
        //if(HabilidadesObjetoComboBox.getItemCount()>0){
        try {
            if ((objeto = esher.pj.DevolverObjetoMagico(NombreObjetoTextField.getText())) != null) {
                BonusHabilidadSpinner.setValue(objeto.DevolverBonusHabilidad(DevolverHabilidadObjetoSeleccionada()));
            } else {
                BonusHabilidadSpinner.setValue(0);
            }
        } catch (NullPointerException npe) {
            BonusHabilidadSpinner.setValue(0);
        }
    //}
    }

    public void BonusObjeto() {
        if ((esher.pj.DevolverPuntosHistoriaTotales() - esher.pj.DevolverPuntosHistorialGastados()) > 0) {
            esher.pj.AñadirObjetoMagico(NombreObjetoTextField.getText(),
                    DevolverCategoriaObjetoSeleccionada(), Integer.parseInt(BonusCategoriaSpinner.getValue().toString()),
                    DevolverHabilidadObjetoSeleccionada(), Integer.parseInt(BonusHabilidadSpinner.getValue().toString()), true);
            String objeto = NombreObjeto();
            ActualizarListadoObjetos();
            SeleccionarObjetoEditado(objeto);
        }
    }

    public void ActivarObjetosMagicos() {
        boolean value;
        if (NombreObjetoTextField.getText().length() > 0) {
            value = true;
        } else {
            BonusCategoriaSpinner.setValue(0);
            BonusHabilidadSpinner.setValue(0);
            value = false;
        }
        BonusHabilidadSpinner.setEnabled(value);
        BonusCategoriaSpinner.setEnabled(value);
        CategoriasObjetoComboBox.setEnabled(value);
        HabilidadesObjetoComboBox.setEnabled(value);
        BorrarObjetoButton.setEnabled(ObjetosComboBox.getItemCount() > 0);
    }

    public void LimpiarObjetoAntiguo() {
        if (esher.pj.DevolverObjetoMagico(NombreObjetoTextField.getText()) == null) {
            BonusCategoriaSpinner.setValue(0);
            BonusHabilidadSpinner.setValue(0);
        }
    }

    public void ActualizarListadoObjetos() {
        ObjetosComboBox.removeAllItems();
        for (int i = 0; i < esher.pj.objetosMagicos.size(); i++) {
            ObjetoMagico objeto = esher.pj.objetosMagicos.get(i);
            ObjetosComboBox.addItem(objeto.nombre);
        }
        ActivarObjetosMagicos();
    }

    public String NombreObjeto() {
        return NombreObjetoTextField.getText();
    }

    public String NombreObjetoSeleccionado() {
        return ObjetosComboBox.getSelectedItem().toString();
    }

    private void SeleccionarObjetoEditado(String nombre) {
        for (int i = 0; i < ObjetosComboBox.getItemCount(); i++) {
            if (ObjetosComboBox.getItemAt(i).equals(nombre)) {
                ObjetosComboBox.setSelectedIndex(i);
            }
        }
    }

    public void BorrarObjeto() {
        try {
            esher.pj.BorrarObjeto(ObjetosComboBox.getSelectedItem().toString());
        } catch (NullPointerException npe) {
        }
        NombreObjetoTextField.setText("");
        BonusCategoriaSpinner.setValue(0);
        BonusHabilidadSpinner.setValue(0);
        RefrescarObjetos();
    //ActualizarListadoObjetos();
    }

    public int NumeroObjetosHabilidades() {
        return HabilidadesObjetoComboBox.getItemCount();
    }

    public void SeleccionarObjetoParaEditar() {
        if (ObjetosComboBox.getItemCount() > 0) {
            NombreObjetoTextField.setText(NombreObjetoSeleccionado());
        }
    }

    /**********************************************************************
     *
     *                             LISTENERS
     *
     **********************************************************************/
    /**
     * Listener
     * @param al
     */
    public void addCategoriasObjetoComboBoxListener(ActionListener al) {
        CategoriasObjetoComboBox.addActionListener(al);
    }

    public void addHabilidadesObjetosComboBoxListener(ActionListener al) {
        HabilidadesObjetoComboBox.addActionListener(al);
    }

    public void addBonusCategoriaObjetoSpinnerListener(ChangeListener al) {
        BonusCategoriaSpinner.addChangeListener(al);
    }

    public void addBonusHabilidadObjetoSpinnerListener(ChangeListener al) {
        BonusHabilidadSpinner.addChangeListener(al);
    }

    public void addBorrarButtonListener(ActionListener al) {
        BorrarObjetoButton.addActionListener(al);
    }

    public void addCerrarButtonListener(ActionListener al) {
        CerrarButton.addActionListener(al);
    }

    public void addObjetosComboBoxListener(ActionListener al) {
        ObjetosComboBox.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ObjetosPanel = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        NombreObjetoTextField = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        HabilidadesObjetoComboBox = new javax.swing.JComboBox();
        CategoriasObjetoComboBox = new javax.swing.JComboBox();
        BonusHabilidadSpinner = new javax.swing.JSpinner();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        BonusCategoriaSpinner = new javax.swing.JSpinner();
        jLabel35 = new javax.swing.JLabel();
        ObjetosComboBox = new javax.swing.JComboBox();
        BorrarObjetoButton = new javax.swing.JButton();
        CerrarButton = new javax.swing.JButton();

        setTitle("Objetos Mágicos por Historial");

        ObjetosPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel27.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel27.setText("Objetos:");

        NombreObjetoTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NombreObjetoTextFieldKeyReleased(evt);
            }
        });

        jLabel31.setText("Nuevo:");

        jLabel32.setText("Bonus:");

        jLabel33.setText("Habilidad:");

        jLabel34.setText("Categoria:");

        jLabel35.setText("Bonus:");

        javax.swing.GroupLayout ObjetosPanelLayout = new javax.swing.GroupLayout(ObjetosPanel);
        ObjetosPanel.setLayout(ObjetosPanelLayout);
        ObjetosPanelLayout.setHorizontalGroup(
            ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ObjetosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(NombreObjetoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel27)
                    .addComponent(ObjetosComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HabilidadesObjetoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(CategoriasObjetoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(BonusCategoriaSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(BonusHabilidadSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addContainerGap())
        );
        ObjetosPanelLayout.setVerticalGroup(
            ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ObjetosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel27)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CategoriasObjetoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ObjetosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BonusCategoriaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel31)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(HabilidadesObjetoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NombreObjetoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BonusHabilidadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BorrarObjetoButton.setText("Borrar");

        CerrarButton.setText("Cerrar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ObjetosPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(BorrarObjetoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CerrarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BorrarObjetoButton, CerrarButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ObjetosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CerrarButton)
                    .addComponent(BorrarObjetoButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BorrarObjetoButton, CerrarButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void NombreObjetoTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NombreObjetoTextFieldKeyReleased
        ActivarObjetosMagicos();
        LimpiarObjetoAntiguo();
    }//GEN-LAST:event_NombreObjetoTextFieldKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner BonusCategoriaSpinner;
    private javax.swing.JSpinner BonusHabilidadSpinner;
    private javax.swing.JButton BorrarObjetoButton;
    private javax.swing.JComboBox CategoriasObjetoComboBox;
    private javax.swing.JButton CerrarButton;
    private javax.swing.JComboBox HabilidadesObjetoComboBox;
    private javax.swing.JTextField NombreObjetoTextField;
    private javax.swing.JComboBox ObjetosComboBox;
    private javax.swing.JPanel ObjetosPanel;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    // End of variables declaration//GEN-END:variables
}

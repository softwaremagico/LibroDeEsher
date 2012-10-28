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
Created on december of 2007.
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
import com.softwaremagico.librodeesher.Personaje;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import java.util.List;

public class AdiestramientoGUI extends javax.swing.JFrame {

    /** Creates new form adiestramientoGUI */
    public AdiestramientoGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
    }

    void ShowErrorMessage(String text, String title) {
        JFrame frame = null;
        JOptionPane.showMessageDialog(frame, text, title, JOptionPane.ERROR_MESSAGE);
    }

    public void Refrescar(String adiestramiento) {
        new LeerAdiestramientos(adiestramiento, true, true);
        TituloLabel.setText(Personaje.getInstance().adiestramiento.nombre + ":");
        RellenarCategorias();
        RellenarHabilidades();
        ActualizarMaximosRangosHabilidadesTextField();
        ActualizarRangosCategoriasTextField();
        if(Personaje.getInstance().adiestramiento.DevolverListaCompletaAumentoCaracteristica().size()>0){
            CaracteristicasButton.setEnabled(true);
        }else{
            CaracteristicasButton.setEnabled(false);
        }
    }

    public void ActualizarMaximosRangosHabilidadesTextField() {
        try {
            MaxRangosHabilidadesTextField.setText((Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).rangosHabilidades -
                    Personaje.getInstance().adiestramiento.DevolverTotalRangosHabilidadesGastadosGrupoAdiestramiento(CategoriasComboBox.getSelectedItem().toString())) + "");
        } catch (ArrayIndexOutOfBoundsException aiofb) {
            MaxRangosHabilidadesTextField.setText("");
        }
    }

    public void ActualizarRangosCategoriasTextField() {
        try {
            if (Personaje.getInstance().adiestramiento.UnicoAdiestramientoGrupo(CategoriasComboBox.getSelectedItem().toString())) {
                CategoriasRangosTextField.setText(Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).rangos + "");
            } else if (Personaje.getInstance().adiestramiento.EsCategoriaGrupo(CategoriasComboBox.getSelectedItem().toString())) {
                CategoriasRangosTextField.setText(Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).rangosGrupo + "");
            } else {
                CategoriasRangosTextField.setText("0");
            }
        } catch (ArrayIndexOutOfBoundsException aiofb) {
            CategoriasRangosTextField.setText("0");
        } catch (NullPointerException npe) {
            CategoriasRangosTextField.setText("0");
        }
    }

    public void ActualizarCategoriaSeleccionada() {
        RellenarHabilidades();
        ActualizarMaximosRangosHabilidadesTextField();
        ActualizarRangosCategoriasTextField();
        ActualizarHabilidadSeleccionada();
        ActualizarEtiquetaCompartidos();
    }

    public void ActualizarHabilidadSeleccionada() {
        if (HabilidadesComboBox.getSelectedIndex() >= 0) {
            HabilidadRangosSpinner.setValue(Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverListaHabilidades().get(HabilidadesComboBox.getSelectedIndex()).Rangos());
            ActualizarTotalHabilidad();
        }
    }

    public void RellenarCategorias() {
        CategoriasComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().adiestramiento.DevolverListaCategorias().size(); i++) {
            CategoriasComboBox.addItem(Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(i).nombre);
        }
    }

    public void RellenarHabilidades() {
        HabilidadesComboBox.removeAllItems();
        try {
            for (int i = 0; i < Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverListaHabilidades().size(); i++) {
                HabilidadesComboBox.addItem(Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverListaHabilidades().get(i).nombre);
            }
        } catch (ArrayIndexOutOfBoundsException aiofb) {
        }
    }

    public void RangosHabilidadesInRange() {
        //Se actualiza los nuevos rangos en la habilidad.
        try{
        Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverListaHabilidades().get(HabilidadesComboBox.getSelectedIndex()).rangosAsignados =
                (Integer) HabilidadRangosSpinner.getValue() - Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverListaHabilidades().get(HabilidadesComboBox.getSelectedIndex()).rangosBasicos;
        }catch(ArrayIndexOutOfBoundsException aiofb){
            MostrarMensaje.showErrorMessage("No existe ninguna habilidad seleccionada.","Adiestramiento");
        }

        //Algunas categorias obligan a dividir los rangos al menos entre varias habilidades. Por tanto, si se ha alcanzado el maximo de rangos y no se
        //han puesto todas las habilidades te impide gastar el rango en esa habilidad.
        if (Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).rangosHabilidades == Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverRangosHabilidadesGastados() &&
                //Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverNumeroHabilidadesConRangos() < Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).minimoHabilidades) {
                Personaje.getInstance().adiestramiento.DevolverNumeroHabilidadesConRangosDeGrupo(CategoriasComboBox.getSelectedItem().toString()) < Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).minimoHabilidades) {
            ShowErrorMessage("Este adiestramiento para esta categoría de habilidades seleccionada te obliga a repartir los rangos en al menos " +
                    Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).minimoHabilidades + " habilidades.", "Error en la asignación de rangos");
            HabilidadRangosSpinner.setValue((Integer) HabilidadRangosSpinner.getValue() - 1);
        }

        //Algunas categorías tienen un máximo de habilidades mejorables.
        //if (Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverNumeroHabilidadesConRangos() > Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).maximoHabilidades) {
        if (Personaje.getInstance().adiestramiento.DevolverNumeroHabilidadesConRangosDeGrupo(CategoriasComboBox.getSelectedItem().toString()) > Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).maximoHabilidades) {
            HabilidadRangosSpinner.setValue((Integer) HabilidadRangosSpinner.getValue() - 1);
            if (Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).rangosHabilidades -
                    Personaje.getInstance().adiestramiento.DevolverTotalRangosHabilidadesGastadosGrupoAdiestramiento(CategoriasComboBox.getSelectedItem().toString()) > 0) {
                ShowErrorMessage("Este adiestramiento para esta categoría de habilidades seleccionada te impide mejorar más de " +
                        Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).maximoHabilidades + " habilidades.", "Error en la asignación de rangos");
            }
        }

        //No puedes gastar mas que los rangos totales designados.
        if (Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).rangosHabilidades < Personaje.getInstance().adiestramiento.DevolverTotalRangosHabilidadesGastadosGrupoAdiestramiento(CategoriasComboBox.getSelectedItem().toString())) {
            HabilidadRangosSpinner.setValue((Integer) HabilidadRangosSpinner.getValue() - 1);
        }

        //No puede ser negativo ni menor que los rangos ya designados por el adiestramiento.
        if (Integer.parseInt(HabilidadRangosSpinner.getValue().toString()) < 0 ||
                Integer.parseInt(HabilidadRangosSpinner.getValue().toString()) <
                Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverListaHabilidades().get(HabilidadesComboBox.getSelectedIndex()).rangosBasicos) {
            HabilidadRangosSpinner.setValue((Integer) HabilidadRangosSpinner.getValue() + 1);
        }

        //Si añades rangos a una habilidad de grupo, entonces su categoria es la que se tiene que llevar los rangos.
        ActualizarRangosCategoriasTextField();

        ActualizarMaximosRangosHabilidadesTextField();
        ActualizarTotalHabilidad();
    }

    private void ActualizarEtiquetaCompartidos() {
        try {
            if (Personaje.getInstance().adiestramiento.UnicoAdiestramientoGrupo(CategoriasComboBox.getSelectedItem().toString())) {
                CompartidosLabel.setVisible(false);
            } else {
                CompartidosLabel.setVisible(true);
            }
        } catch (NullPointerException npe) {
            CompartidosLabel.setVisible(false);
        }
    }

    private void ActualizarTotalHabilidad() {
        try {
            int rangos = Personaje.getInstance().adiestramiento.DevolverListaCategorias().get(CategoriasComboBox.getSelectedIndex()).DevolverListaHabilidades().get(HabilidadesComboBox.getSelectedIndex()).Rangos() +
                    Personaje.getInstance().DevolverHabilidadDeNombre(HabilidadesComboBox.getSelectedItem().toString()).DevolverRangos();
            RangosFinalesTextField.setText(rangos + "");
        } catch (NullPointerException npe) {
            RangosFinalesTextField.setText("0");
        }
    }

    /************************************************
     *
     *                    LISTENERS
     *
     ************************************************/
    /**
     * Coloca el listener.
     */
    public void addCategoriasListener(ActionListener al) {
        CategoriasComboBox.addActionListener(al);
    }

    public void addHabilidadesListener(ActionListener al) {
        HabilidadesComboBox.addActionListener(al);
    }

    public void addHabilidadRangosSpinnerListener(ChangeListener al) {
        HabilidadRangosSpinner.addChangeListener(al);
    }

    public void AddCancelButtonListener(ActionListener al) {
        CancelarButton.addActionListener(al);
    }

    public void AddAceptarButtonListener(ActionListener al) {
        AceptarButton.addActionListener(al);
    }

    public void AddAleatorioButtonListener(ActionListener al) {
        AleatorioButton.addActionListener(al);
    }
    
    public void AddCaracteristicasButtonListener(ActionListener al) {
        CaracteristicasButton.addActionListener(al);
    }
    

    /*void AddActivaCheckBoxListener(ActionListener al){
    ActivaCheckBox.addActionListener(al);
    }*/
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        CategoriasComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        HabilidadesComboBox = new javax.swing.JComboBox();
        CategoriasRangosTextField = new javax.swing.JTextField();
        HabilidadRangosSpinner = new javax.swing.JSpinner();
        MaxRangosHabilidadesTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        RangosFinalesTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        CompartidosLabel = new javax.swing.JLabel();
        AceptarButton = new javax.swing.JButton();
        TituloLabel = new javax.swing.JLabel();
        CancelarButton = new javax.swing.JButton();
        AleatorioButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        CaracteristicasButton = new javax.swing.JButton();

        setTitle("Adiestramiento");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Categoria:");

        jLabel2.setText("Habilidad");

        CategoriasRangosTextField.setEditable(false);
        CategoriasRangosTextField.setText("0");

        MaxRangosHabilidadesTextField.setEditable(false);
        MaxRangosHabilidadesTextField.setText("0");

        jLabel3.setText("Rangos:");

        jLabel4.setText("Rangos:");

        jLabel5.setText("Libres:");

        RangosFinalesTextField.setEditable(false);
        RangosFinalesTextField.setText("0");

        jLabel6.setText("Total:");

        CompartidosLabel.setText("Compartidos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(CategoriasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(CategoriasRangosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HabilidadRangosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CompartidosLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MaxRangosHabilidadesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RangosFinalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CategoriasRangosTextField, HabilidadRangosSpinner});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CategoriasComboBox, HabilidadesComboBox});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CategoriasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoriasRangosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CompartidosLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HabilidadRangosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MaxRangosHabilidadesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RangosFinalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AceptarButton.setText("Aceptar");

        TituloLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        TituloLabel.setText("Nombre Adiestramiento");

        CancelarButton.setText("Cancelar");

        AleatorioButton.setText("Aleatorio");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Aumento de característica:");

        CaracteristicasButton.setText("Aumentar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CaracteristicasButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(CaracteristicasButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TituloLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(AleatorioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CancelarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AceptarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AceptarButton, AleatorioButton, CancelarButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TituloLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelarButton)
                    .addComponent(AleatorioButton)
                    .addComponent(AceptarButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarButton;
    private javax.swing.JButton AleatorioButton;
    private javax.swing.JButton CancelarButton;
    private javax.swing.JButton CaracteristicasButton;
    private javax.swing.JComboBox CategoriasComboBox;
    private javax.swing.JTextField CategoriasRangosTextField;
    private javax.swing.JLabel CompartidosLabel;
    private javax.swing.JSpinner HabilidadRangosSpinner;
    private javax.swing.JComboBox HabilidadesComboBox;
    private javax.swing.JTextField MaxRangosHabilidadesTextField;
    private javax.swing.JTextField RangosFinalesTextField;
    private javax.swing.JLabel TituloLabel;
    private javax.swing.JLabel jLabel1;
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

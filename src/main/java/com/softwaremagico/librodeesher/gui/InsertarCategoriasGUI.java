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
Created on 9 de mayo de 2008, 12:58
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
import com.softwaremagico.librodeesher.Personaje;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author  jorge
 */
public class InsertarCategoriasGUI extends javax.swing.JFrame {

    /** Creates new form InsertarCategoriasGUI */
    public InsertarCategoriasGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        IniciarVentana();
    }

    public void IniciarVentana() {
        ActualizarCategoriasComboBox();
    }

    public void Refrescar() {
        IniciarVentana();
        LimpiarCategoria();
    }

    /**
     * Create a window to show an error message.
     */
  public   void ShowErrorMessage(String text, String title) {
        JFrame frame = null;
        JOptionPane.showMessageDialog(frame, text, title, JOptionPane.ERROR_MESSAGE);
    }

   public  String DevolverNombreCategoria() {
        return NombreCategoriaTextField.getText();
    }

   public  String DevolverCosteCategoria() {
        return CosteTextField.getText();
    }

   public  String DevolverAbreviaturaCaracteristicas() {
        return CaracteristicasTextField.getText();
    }

   public  String DevolverAbreviatura() {
        return AbreviaturaTextField.getText();
    }

   public  String DevolverTipo() {
        return TipoComboBox.getSelectedItem().toString();
    }

    public String DevolverNombreHabilidad() {
        return NombreHabilidadTextField.getText();
    }

    public String DevolverCategoriaHabilidad() {
        return CategoriasComboBox.getSelectedItem().toString();
    }

    public void LimpiarHabilidad() {
        NombreHabilidadTextField.setText("");
    }
    
    public void LimpiarCategoria(){
        NombreCategoriaTextField.setText("");
        AbreviaturaTextField.setText("");
    }

    public boolean CostesBienFormados() {
        int costeInt;
        String[] coste = DevolverCosteCategoria().split("/");
        try {
            costeInt = Integer.parseInt(coste[0]);
            if (costeInt < 1) {
                return false;
            }
            if (coste.length > 1) {
                costeInt = Integer.parseInt(coste[1]);
                if (costeInt < 1) {
                    return false;
                }
            } 
            if (coste.length > 2) {
                costeInt = Integer.parseInt(coste[2]);
                if (costeInt < 1) {
                    return false;
                }
            } 
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean CaracteristicasReales() {
        try{
        if (DevolverAbreviaturaCaracteristicas().equals("*") || DevolverAbreviaturaCaracteristicas().equals("Ninguna")) {
            return true;
        }
        String[] tmp_trioCaracteristicas = DevolverAbreviaturaCaracteristicas().split("/");
        for (int i = 0; i < tmp_trioCaracteristicas.length; i++) {
            if (Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(tmp_trioCaracteristicas[i]) == null) {
                return false;
            }
        }
        return true;
        }catch(ArrayIndexOutOfBoundsException aiofb){
            return false;
        }
    }

    public boolean CamposCategoriaCorrectos() {
        if (DevolverNombreCategoria().length() < 1) {
            ShowErrorMessage("El campo \"Nombre\" está incompleto", "Campo incompleto");
            return false;
        }
        if (DevolverAbreviatura().length() < 1) {
            ShowErrorMessage("El campo \"Abreviatura\" está incompleto", "Campo incompleto");
            return false;
        }
        if (DevolverCosteCategoria().length() < 1 || !CostesBienFormados()) {
            ShowErrorMessage("El campo \"Costes\" está incompleto o erróneo", "Campo incompleto");
            return false;
        }
        if (DevolverAbreviaturaCaracteristicas().length() < 1 || !CaracteristicasReales()) {
            ShowErrorMessage("El campo \"Caracteristicas\" está incompleto o erróneo", "Campo incompleto");
            return false;
        }
        return true;
    }

    private void ActualizarCategoriasComboBox() {
        CategoriasComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            CategoriasComboBox.addItem(cat.DevolverNombre());
        }
    }

    /**********************************************************************
     *
     *                                LISTENERS
     *
     **********************************************************************/
    /**
     * Inserta el listener del botón.
     * @param al
     */
    public void addAñadirCategoriaButtonListener(ActionListener al) {
        AñadirCategoriaButton.addActionListener(al);
    }

    public void addAñadirHabilidadButtonListener(ActionListener al) {
        AñadirHabilidadButton.addActionListener(al);
    }

    public void addCerrarButtonListener(ActionListener al){
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
        jLabel1 = new javax.swing.JLabel();
        NombreCategoriaTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        CosteTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        AñadirCategoriaButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        CaracteristicasTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        AbreviaturaTextField = new javax.swing.JTextField();
        TipoComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        CategoriasComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        NombreHabilidadTextField = new javax.swing.JTextField();
        AñadirHabilidadButton = new javax.swing.JButton();
        CerrarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Insertar Categorías y Habilidades");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel1.setText("Categoría:");

        jLabel2.setText("Nombre:");

        CosteTextField.setText("0/0/0");

        jLabel3.setText("Costes:");

        AñadirCategoriaButton.setText("Añadir");

        jLabel7.setText("Caract.:");

        CaracteristicasTextField.setText("Ca/Ca/Ca");

        jLabel8.setText("Abreviatura:");

        TipoComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Estándar", "Combinada", "Limitada" }));

        jLabel9.setText("Tipo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(NombreCategoriaTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(CaracteristicasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(TipoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addComponent(AñadirCategoriaButton))
                            .addComponent(AbreviaturaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CaracteristicasTextField, CosteTextField});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AbreviaturaTextField, TipoComboBox});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(NombreCategoriaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AbreviaturaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CaracteristicasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TipoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AñadirCategoriaButton))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel4.setText("Habilidades:");

        jLabel5.setText("Pertenece:");

        jLabel6.setText("Nombre:");

        AñadirHabilidadButton.setText("Añadir");
        AñadirHabilidadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñadirHabilidadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NombreHabilidadTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .addComponent(CategoriasComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AñadirHabilidadButton)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CategoriasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NombreHabilidadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AñadirHabilidadButton))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        CerrarButton.setText("Cerrar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CerrarButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CerrarButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void AñadirHabilidadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñadirHabilidadButtonActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_AñadirHabilidadButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AbreviaturaTextField;
    private javax.swing.JButton AñadirCategoriaButton;
    private javax.swing.JButton AñadirHabilidadButton;
    private javax.swing.JTextField CaracteristicasTextField;
    private javax.swing.JComboBox CategoriasComboBox;
    private javax.swing.JButton CerrarButton;
    private javax.swing.JTextField CosteTextField;
    private javax.swing.JTextField NombreCategoriaTextField;
    private javax.swing.JTextField NombreHabilidadTextField;
    private javax.swing.JComboBox TipoComboBox;
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
    // End of variables declaration//GEN-END:variables
}

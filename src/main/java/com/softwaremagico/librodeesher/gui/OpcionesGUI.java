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

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.Esher;
import com.softwaremagico.librodeesher.Personaje;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OpcionesGUI extends javax.swing.JFrame {

    /**
     * Creates new form OpcionesGUI
     */
    public OpcionesGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        AplicaConfiguracionGuardada();
        RellenaModulos();
        updateDisabledCheckBox();
    }

    public void AplicaConfiguracionGuardada() {
        for (int i = 0; i < Esher.opciones.size(); i++) {
            String opcion = Esher.opciones.get(i);
            if (opcion.equals(Esher.configArmasFuego)) {
                if (!ArmasFuegoCheckBox.isSelected()) {
                    ArmasFuegoCheckBox.setSelected(true);
                    AplicarArmasFuego();
                }
            } else if (opcion.equals(Esher.confighechizosAdiestramientoOtrosReinosPermitidos)) {
                if (!HechizosAdiestramientoCheckBox.isSelected()) {
                    HechizosAdiestramientoCheckBox.setSelected(true);
                    AplicarHechizosOtrosAdiestramientos();
                }
            } else if (opcion.equals(Esher.configHechizosMalignos)) {
                if (!HechizosMalignosCheckBox.isSelected()) {
                    HechizosMalignosCheckBox.setSelected(true);
                    Esher.hechizosMalignos = HechizosMalignosCheckBox.isSelected();
                }
            } else if (opcion.equals(Esher.configTalentosAleatorios)) {
                if (!TalentosCheckBox.isSelected()) {
                    TalentosCheckBox.setSelected(true);
                    Esher.talentosAleatorio = TalentosCheckBox.isSelected();
                }
            } else if (opcion.equals(Esher.configHabilidadesOrdenadas)) {
                if (!LexicograficoRadioButton.isSelected()) {
                    LexicograficoRadioButton.setSelected(true);
                }
            } else if (opcion.equals(Esher.configPoderesChi)) {
                if (!PoderesChiCheckBox.isSelected()) {
                    PoderesChiCheckBox.setSelected(true);
                    AplicarPoderesChi();
                }
            } else if (opcion.equals(Esher.configVariosGradosGolpes)) {
                if (!VariosGolpesCheckBox.isSelected()) {
                    VariosGolpesCheckBox.setSelected(true);
                    AplicarVariosGolpes();
                }
            }
        }
       updateDisabledCheckBox();
    }

    /**
     * Create a window to show an error message.
     */
    public void ShowErrorMessage(String text, String title) {
        JFrame frame = null;
        JOptionPane.showMessageDialog(frame, text, title, JOptionPane.ERROR_MESSAGE);
    }

    public void ShowAlertMessage(String text, String title) {
        JFrame frame = null;
        JOptionPane.showMessageDialog(frame, text, title, JOptionPane.WARNING_MESSAGE);
    }

    private void RellenaModulos() {
        ModulosComboBox.removeAllItems();
        for (int i = 0; i < RolemasterFolderStructure.modulosDisponibles().size(); i++) {
            ModulosComboBox.addItem(RolemasterFolderStructure.modulosDisponibles().get(i));
        }
    }

    public boolean DevolverOrdenLexicografico() {
        return LexicograficoRadioButton.isSelected();
    }

    private void AplicarArmasFuego() {
        try {
            Esher.armasFuegoPermitidas = ArmasFuegoCheckBox.isSelected();
            Personaje.getInstance().ActualizaArmas();
        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AplicarHechizosOtrosAdiestramientos() {
        try {
            Esher.hechizosAdiestramientoOtrosReinosPermitidos = HechizosAdiestramientoCheckBox.isSelected();
            Esher.LeerCategoriasDeArchivo();
        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AplicarPoderesChi() {
        try {
            Esher.poderesChi = PoderesChiCheckBox.isSelected();
        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AplicarVariosGolpes() {
        try {
            Esher.variosGradosGolpes = VariosGolpesCheckBox.isSelected();
            //Cambiamos la habilidad de golpes.
            Personaje.getInstance().CambiarGolpesArtesMarcialesGenericosADiversosGrados();

        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateDisabledCheckBox() {
        try{
        if (RolemasterFolderStructure.disabledModules.contains(ModulosComboBox.getSelectedItem().toString())) {
            DisableCheckBox.setSelected(true);
        } else {
            DisableCheckBox.setSelected(false);
        }
        }catch(NullPointerException npe){}
    }

    /**
     * **********************************************
     *
     * LISTENERS
     *
     ***********************************************
     */
    /**
     * Añade el listener
     *
     * @param al
     */
    public void addCerrarButtonListener(ActionListener al) {
        CerrarButton.addActionListener(al);
    }

    public void addOrdenHabilidadesListener(ActionListener al) {
        CategoriasRadioButton.addActionListener(al);
        LexicograficoRadioButton.addActionListener(al);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OrdenarHabilidadesbuttonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        ArmasFuegoCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        HechizosAdiestramientoCheckBox = new javax.swing.JCheckBox();
        HechizosMalignosCheckBox = new javax.swing.JCheckBox();
        PoderesChiCheckBox = new javax.swing.JCheckBox();
        VariosGolpesCheckBox = new javax.swing.JCheckBox();
        CerrarButton = new javax.swing.JButton();
        ModulosPanel = new javax.swing.JPanel();
        ModulosComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        DisableCheckBox = new javax.swing.JCheckBox();
        PersoanjeAleatorioPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        TalentosCheckBox = new javax.swing.JCheckBox();
        FichaPDFPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        CategoriasRadioButton = new javax.swing.JRadioButton();
        LexicograficoRadioButton = new javax.swing.JRadioButton();

        setTitle("Opciones");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ArmasFuegoCheckBox.setText("Armas Fuego");
        ArmasFuegoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArmasFuegoCheckBoxActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel1.setText("Categorias Y Habilidades");

        HechizosAdiestramientoCheckBox.setText("Listas Hechizos de Adiestramientos de Otro Reino");
        HechizosAdiestramientoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HechizosAdiestramientoCheckBoxActionPerformed(evt);
            }
        });

        HechizosMalignosCheckBox.setText("Listas de Hechizos Malignos como Básicas");
        HechizosMalignosCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HechizosMalignosCheckBoxActionPerformed(evt);
            }
        });

        PoderesChiCheckBox.setText("Poderes Chi");
        PoderesChiCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PoderesChiCheckBoxActionPerformed(evt);
            }
        });

        VariosGolpesCheckBox.setText("Artes Marciales·Golpes divididos en Grados");
        VariosGolpesCheckBox.setToolTipText("Al utilizar la Guia de Artes Marciales, desaparecen el concepto de Golpes Grado 1, Golpes Grado 2, etc...");
        VariosGolpesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VariosGolpesCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(VariosGolpesCheckBox)
                    .addComponent(PoderesChiCheckBox)
                    .addComponent(ArmasFuegoCheckBox)
                    .addComponent(jLabel1)
                    .addComponent(HechizosAdiestramientoCheckBox)
                    .addComponent(HechizosMalignosCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ArmasFuegoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HechizosAdiestramientoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HechizosMalignosCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PoderesChiCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VariosGolpesCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CerrarButton.setText("Cerrar");

        ModulosPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ModulosComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ModulosComboBoxItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel2.setText("Modulos Disponibles:");

        DisableCheckBox.setText("Deshabilitado");
        DisableCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisableCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ModulosPanelLayout = new javax.swing.GroupLayout(ModulosPanel);
        ModulosPanel.setLayout(ModulosPanelLayout);
        ModulosPanelLayout.setHorizontalGroup(
            ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ModulosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ModulosPanelLayout.createSequentialGroup()
                        .addComponent(ModulosComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DisableCheckBox))
                    .addGroup(ModulosPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ModulosPanelLayout.setVerticalGroup(
            ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ModulosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModulosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DisableCheckBox))
                .addContainerGap())
        );

        PersoanjeAleatorioPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel3.setText("Personaje Aleatorio");

        TalentosCheckBox.setText("Talentos");
        TalentosCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TalentosCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PersoanjeAleatorioPanelLayout = new javax.swing.GroupLayout(PersoanjeAleatorioPanel);
        PersoanjeAleatorioPanel.setLayout(PersoanjeAleatorioPanelLayout);
        PersoanjeAleatorioPanelLayout.setHorizontalGroup(
            PersoanjeAleatorioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersoanjeAleatorioPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PersoanjeAleatorioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(TalentosCheckBox))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        PersoanjeAleatorioPanelLayout.setVerticalGroup(
            PersoanjeAleatorioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersoanjeAleatorioPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TalentosCheckBox)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        FichaPDFPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel4.setText("Ficha PDF");

        OrdenarHabilidadesbuttonGroup.add(CategoriasRadioButton);
        CategoriasRadioButton.setSelected(true);
        CategoriasRadioButton.setText("Ordenar por Categorias");

        OrdenarHabilidadesbuttonGroup.add(LexicograficoRadioButton);
        LexicograficoRadioButton.setText("Orden lexicografico");

        javax.swing.GroupLayout FichaPDFPanelLayout = new javax.swing.GroupLayout(FichaPDFPanel);
        FichaPDFPanel.setLayout(FichaPDFPanelLayout);
        FichaPDFPanelLayout.setHorizontalGroup(
            FichaPDFPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FichaPDFPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FichaPDFPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(CategoriasRadioButton)
                    .addComponent(LexicograficoRadioButton))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        FichaPDFPanelLayout.setVerticalGroup(
            FichaPDFPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FichaPDFPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CategoriasRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LexicograficoRadioButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ModulosPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(PersoanjeAleatorioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FichaPDFPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(CerrarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PersoanjeAleatorioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FichaPDFPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ModulosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CerrarButton)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {FichaPDFPanel, PersoanjeAleatorioPanel});

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void ArmasFuegoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArmasFuegoCheckBoxActionPerformed
        AplicarArmasFuego();
    }//GEN-LAST:event_ArmasFuegoCheckBoxActionPerformed

    private void ModulosComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ModulosComboBoxItemStateChanged
        updateDisabledCheckBox();
    }//GEN-LAST:event_ModulosComboBoxItemStateChanged

    private void TalentosCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TalentosCheckBoxActionPerformed
        Esher.talentosAleatorio = TalentosCheckBox.isSelected();
    }//GEN-LAST:event_TalentosCheckBoxActionPerformed

    private void HechizosAdiestramientoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HechizosAdiestramientoCheckBoxActionPerformed
        AplicarHechizosOtrosAdiestramientos();
    }//GEN-LAST:event_HechizosAdiestramientoCheckBoxActionPerformed

    private void HechizosMalignosCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HechizosMalignosCheckBoxActionPerformed
        Esher.hechizosMalignos = HechizosMalignosCheckBox.isSelected();
    }//GEN-LAST:event_HechizosMalignosCheckBoxActionPerformed

    private void PoderesChiCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PoderesChiCheckBoxActionPerformed
        AplicarPoderesChi();
    }//GEN-LAST:event_PoderesChiCheckBoxActionPerformed

    private void VariosGolpesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VariosGolpesCheckBoxActionPerformed
        AplicarVariosGolpes();
}//GEN-LAST:event_VariosGolpesCheckBoxActionPerformed

    private void DisableCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisableCheckBoxActionPerformed
        if (DisableCheckBox.isSelected()) {
            if (!RolemasterFolderStructure.disabledModules.contains(ModulosComboBox.getSelectedItem().toString())) {
                RolemasterFolderStructure.disabledModules.add(ModulosComboBox.getSelectedItem().toString());
            }
        } else {
            RolemasterFolderStructure.disabledModules.remove(ModulosComboBox.getSelectedItem().toString());
        }
    }//GEN-LAST:event_DisableCheckBoxActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Esher.GuardarConfiguracion();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JCheckBox ArmasFuegoCheckBox;
    private static javax.swing.JRadioButton CategoriasRadioButton;
    private javax.swing.JButton CerrarButton;
    private javax.swing.JCheckBox DisableCheckBox;
    private javax.swing.JPanel FichaPDFPanel;
    private static javax.swing.JCheckBox HechizosAdiestramientoCheckBox;
    private static javax.swing.JCheckBox HechizosMalignosCheckBox;
    private static javax.swing.JRadioButton LexicograficoRadioButton;
    private javax.swing.JComboBox ModulosComboBox;
    private javax.swing.JPanel ModulosPanel;
    private javax.swing.ButtonGroup OrdenarHabilidadesbuttonGroup;
    private javax.swing.JPanel PersoanjeAleatorioPanel;
    private static javax.swing.JCheckBox PoderesChiCheckBox;
    private static javax.swing.JCheckBox TalentosCheckBox;
    private static javax.swing.JCheckBox VariosGolpesCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

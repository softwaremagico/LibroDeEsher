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

import com.softwaremagico.librodeesher.Esher;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OpcionesGUI extends javax.swing.JFrame {

    private Esher esher;

    /** Creates new form OpcionesGUI */
    public OpcionesGUI(Esher tmp_esher) {
        esher = tmp_esher;
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        AplicaConfiguracionGuardada();
        RellenaModulos();
    }

    public void AplicaConfiguracionGuardada() {
        for (int i = 0; i < esher.opciones.size(); i++) {
            String opcion = esher.opciones.get(i);
            if (opcion.equals(ArmasFuegoCheckBox.getText())) {
                if (!ArmasFuegoCheckBox.isSelected()) {
                    ArmasFuegoCheckBox.setSelected(true);
                    AplicarArmasFuego();
                }
            } else if (opcion.equals(HechizosAdiestramientoCheckBox.getText())) {
                if (!HechizosAdiestramientoCheckBox.isSelected()) {
                    HechizosAdiestramientoCheckBox.setSelected(true);
                    AplicarHechizosOtrosAdiestramientos();
                }
            } else if (opcion.equals(HechizosMalignosCheckBox.getText())) {
                if (!HechizosMalignosCheckBox.isSelected()) {
                    HechizosMalignosCheckBox.setSelected(true);
                    esher.hechizosMalignos = HechizosMalignosCheckBox.isSelected();
                }
            } else if (opcion.equals(TalentosCheckBox.getText())) {
                if (!TalentosCheckBox.isSelected()) {
                    TalentosCheckBox.setSelected(true);
                    esher.talentosAleatorio = TalentosCheckBox.isSelected();
                }
            } else if (opcion.equals(LexicograficoRadioButton.getText())) {
                if (!LexicograficoRadioButton.isSelected()) {
                    LexicograficoRadioButton.setSelected(true);
                }
            } else if (opcion.equals(PoderesChiCheckBox.getText())) {
                if (!PoderesChiCheckBox.isSelected()) {
                    PoderesChiCheckBox.setSelected(true);
                    AplicarPoderesChi();
                }
            } else if (opcion.equals(VariosGolpesCheckBox.getText())) {
                if (!VariosGolpesCheckBox.isSelected()) {
                    VariosGolpesCheckBox.setSelected(true);
                    AplicarVariosGolpes();
                }
            }
        }
    }

    public void GuardarConfiguracion() {
        esher.opciones = new ArrayList<String>();
        if (ArmasFuegoCheckBox.isSelected()) {
            esher.opciones.add(ArmasFuegoCheckBox.getText());
        }
        if (HechizosAdiestramientoCheckBox.isSelected()) {
            esher.opciones.add(HechizosAdiestramientoCheckBox.getText());
        }
        if (HechizosMalignosCheckBox.isSelected()) {
            esher.opciones.add(HechizosMalignosCheckBox.getText());
        }
        if (TalentosCheckBox.isSelected()) {
            esher.opciones.add(TalentosCheckBox.getText());
        }
        if (LexicograficoRadioButton.isSelected()) {
            esher.opciones.add(LexicograficoRadioButton.getText());
        }
        if (PoderesChiCheckBox.isSelected()) {
            esher.opciones.add(PoderesChiCheckBox.getText());
        }
        if (VariosGolpesCheckBox.isSelected()) {
            esher.opciones.add(VariosGolpesCheckBox.getText());
        }
        esher.directorioRolemaster.GuardarListaAFichero(esher.opciones, esher.directorioRolemaster.ObtenerPathConfiguracion(true));
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
        for (int i = 0; i < esher.modulosRolemaster.size(); i++) {
            ModulosComboBox.addItem(esher.modulosRolemaster.get(i));
        }
    }

    public void BorraModulo() {
        if (ModulosComboBox.getSelectedIndex() >= 0) {
            esher.modulosRolemaster.remove(ModulosComboBox.getSelectedIndex());
            ModulosTextField.setText("");
            GuardarModulos();
            RellenaModulos();
        }
    }

    public void GuardarModulos() {
        esher.directorioRolemaster.GuardarListaAFichero(esher.modulosRolemaster, esher.directorioRolemaster.ObtenerPathModulos(true));
    }

    public void AñadirModulo() {
        File file;
        if (!esher.modulosRolemaster.contains(ModulosTextField.getText())) {
            file = new File(ModulosTextField.getText());
            if (file.exists()) {
                esher.modulosRolemaster.add(ModulosTextField.getText());
                ModulosTextField.setText("");
                GuardarModulos();
                RellenaModulos();
                ShowAlertMessage("Debes reiniciar la aplicación para que los cambios tengan efecto.", "Reiniciar aplicación");
            } else {
                ShowErrorMessage("El directorio \"" + ModulosTextField.getText() + "\" no existe.", "Error de directorio");
            }
        } else {
            ShowErrorMessage("El directorio seleccionado ya existe.", "Error de directorio");
        }
    }

    public boolean DevolverOrdenLexicografico() {
        return LexicograficoRadioButton.isSelected();
    }

    private void AplicarArmasFuego() {
        try {
            esher.armasFuegoPermitidas = ArmasFuegoCheckBox.isSelected();
            esher.pj.ActualizaArmas();
        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AplicarHechizosOtrosAdiestramientos() {
        try {
            esher.hechizosAdiestramientoOtrosReinosPermitidos = HechizosAdiestramientoCheckBox.isSelected();
            esher.LeerCategoriasDeArchivo();
        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AplicarPoderesChi() {
        try {
            esher.poderesChi = PoderesChiCheckBox.isSelected();
        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AplicarVariosGolpes() {
        try {
            esher.variosGradosGolpes = VariosGolpesCheckBox.isSelected();
            //Cambiamos la habilidad de golpes.
            esher.pj.CambiarGolpesArtesMarcialesGenericosADiversosGrados();

        } catch (Exception ex) {
            Logger.getLogger(OpcionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /************************************************
     *
     *                    LISTENERS
     *
     ************************************************/
    /**
     * Añade el listener
     * @param al
     */
    public void addCerrarButtonListener(ActionListener al) {
        CerrarButton.addActionListener(al);
    }

    public void addOrdenHabilidadesListener(ActionListener al) {
        CategoriasRadioButton.addActionListener(al);
        LexicograficoRadioButton.addActionListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        ModulosTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        BorrarModuloButton = new javax.swing.JButton();
        AñadirButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        PersoanjeAleatorioPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        TalentosCheckBox = new javax.swing.JCheckBox();
        FichaPDFPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        CategoriasRadioButton = new javax.swing.JRadioButton();
        LexicograficoRadioButton = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();

        setTitle("Opciones");

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
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ArmasFuegoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HechizosAdiestramientoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HechizosMalignosCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PoderesChiCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(VariosGolpesCheckBox)
                .addContainerGap())
        );

        CerrarButton.setText("Cerrar");

        ModulosPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ModulosComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ModulosComboBoxItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel2.setText("Modulos");

        BorrarModuloButton.setText("Borrar");
        BorrarModuloButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarModuloButtonActionPerformed(evt);
            }
        });

        AñadirButton.setText("Añadir");
        AñadirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñadirButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Incluidos:");

        jLabel7.setText("Añadir:");

        javax.swing.GroupLayout ModulosPanelLayout = new javax.swing.GroupLayout(ModulosPanel);
        ModulosPanel.setLayout(ModulosPanelLayout);
        ModulosPanelLayout.setHorizontalGroup(
            ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ModulosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(ModulosComboBox, 0, 411, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ModulosPanelLayout.createSequentialGroup()
                        .addComponent(AñadirButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
                        .addComponent(BorrarModuloButton))
                    .addComponent(ModulosTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        ModulosPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AñadirButton, BorrarModuloButton});

        ModulosPanelLayout.setVerticalGroup(
            ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ModulosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ModulosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ModulosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ModulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BorrarModuloButton)
                    .addComponent(AñadirButton))
                .addGap(24, 24, 24))
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
                .addContainerGap(268, Short.MAX_VALUE))
        );
        PersoanjeAleatorioPanelLayout.setVerticalGroup(
            PersoanjeAleatorioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersoanjeAleatorioPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TalentosCheckBox)
                .addContainerGap())
        );

        FichaPDFPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel4.setText("Ficha PDF");

        OrdenarHabilidadesbuttonGroup.add(CategoriasRadioButton);
        CategoriasRadioButton.setSelected(true);
        CategoriasRadioButton.setText("Ordenar por Categorias");

        OrdenarHabilidadesbuttonGroup.add(LexicograficoRadioButton);
        LexicograficoRadioButton.setText("Orden lexicografico");

        jLabel5.setText("Habilidades:");

        javax.swing.GroupLayout FichaPDFPanelLayout = new javax.swing.GroupLayout(FichaPDFPanel);
        FichaPDFPanel.setLayout(FichaPDFPanelLayout);
        FichaPDFPanelLayout.setHorizontalGroup(
            FichaPDFPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FichaPDFPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FichaPDFPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(CategoriasRadioButton)
                    .addComponent(LexicograficoRadioButton)
                    .addComponent(jLabel5))
                .addContainerGap(262, Short.MAX_VALUE))
        );
        FichaPDFPanelLayout.setVerticalGroup(
            FichaPDFPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FichaPDFPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(PersoanjeAleatorioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FichaPDFPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ModulosPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CerrarButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PersoanjeAleatorioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FichaPDFPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ModulosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CerrarButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void ArmasFuegoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArmasFuegoCheckBoxActionPerformed
        AplicarArmasFuego();
    }//GEN-LAST:event_ArmasFuegoCheckBoxActionPerformed

    private void BorrarModuloButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarModuloButtonActionPerformed
        BorraModulo();
}//GEN-LAST:event_BorrarModuloButtonActionPerformed

    private void AñadirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñadirButtonActionPerformed
        AñadirModulo();
    }//GEN-LAST:event_AñadirButtonActionPerformed

    private void ModulosComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ModulosComboBoxItemStateChanged
        try {
            ModulosTextField.setText(ModulosComboBox.getSelectedItem().toString());
        } catch (NullPointerException npe) {
        }
    }//GEN-LAST:event_ModulosComboBoxItemStateChanged

    private void TalentosCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TalentosCheckBoxActionPerformed
        esher.talentosAleatorio = TalentosCheckBox.isSelected();
    }//GEN-LAST:event_TalentosCheckBoxActionPerformed

    private void HechizosAdiestramientoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HechizosAdiestramientoCheckBoxActionPerformed
        AplicarHechizosOtrosAdiestramientos();
    }//GEN-LAST:event_HechizosAdiestramientoCheckBoxActionPerformed

    private void HechizosMalignosCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HechizosMalignosCheckBoxActionPerformed
        esher.hechizosMalignos = HechizosMalignosCheckBox.isSelected();
    }//GEN-LAST:event_HechizosMalignosCheckBoxActionPerformed

    private void PoderesChiCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PoderesChiCheckBoxActionPerformed
        AplicarPoderesChi();
    }//GEN-LAST:event_PoderesChiCheckBoxActionPerformed

    private void VariosGolpesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VariosGolpesCheckBoxActionPerformed
        AplicarVariosGolpes();
}//GEN-LAST:event_VariosGolpesCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ArmasFuegoCheckBox;
    private javax.swing.JButton AñadirButton;
    private javax.swing.JButton BorrarModuloButton;
    private javax.swing.JRadioButton CategoriasRadioButton;
    private javax.swing.JButton CerrarButton;
    private javax.swing.JPanel FichaPDFPanel;
    private javax.swing.JCheckBox HechizosAdiestramientoCheckBox;
    private javax.swing.JCheckBox HechizosMalignosCheckBox;
    private javax.swing.JRadioButton LexicograficoRadioButton;
    private javax.swing.JComboBox ModulosComboBox;
    private javax.swing.JPanel ModulosPanel;
    private javax.swing.JTextField ModulosTextField;
    private javax.swing.ButtonGroup OrdenarHabilidadesbuttonGroup;
    private javax.swing.JPanel PersoanjeAleatorioPanel;
    private javax.swing.JCheckBox PoderesChiCheckBox;
    private javax.swing.JCheckBox TalentosCheckBox;
    private javax.swing.JCheckBox VariosGolpesCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

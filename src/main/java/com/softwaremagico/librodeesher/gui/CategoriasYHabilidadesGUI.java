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
Created on october of 2007.
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
import com.softwaremagico.librodeesher.FichaTxt;
import com.softwaremagico.librodeesher.Habilidad;
import com.softwaremagico.librodeesher.Personaje;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;

public class CategoriasYHabilidadesGUI extends javax.swing.JFrame {

    private String antiguaEspecializacion = "";

    /** Creates new form CategoriasYHabilidadesGUI */
    public CategoriasYHabilidadesGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
    }

    public void Iniciar() {
        ActualizarCategoriasComboBox();
        ActualizarHabilidadesComboBox();
        ActualizarCategoriaSeleccionada();
        ActualizarHabilidadSeleccionada();
        ActualizarPuntosDesarrollo();
        ActualizarCategoriasNuevosRangos();
        ActualizarHabilidadesNuevosRangos();
    }

    public void Refrescar() {
        ActualizarCategoriasComboBox();
        ActualizarPuntosDesarrollo();
        ActualizarTextoHabilidades();
        ActualizarHabilidadesNuevosRangos();
        ActualizarCategoriasNuevosRangos();
        ActualizarCategoriaSeleccionada();
        ActualizarHabilidadSeleccionada();
    }

    public void Reset() {
    }

    public void ActualizarCategoriasComboBox() {
        CategoriasComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            if (cat.MereceLaPenaListar()) {
                CategoriasComboBox.addItem(cat.DevolverNombre());
            }
        }
    }

    private void GenerarCosteCategoria() {
        Categoria cat = DevolverCategoriaSeleccionada();
        String coste = cat.GenerarCadenaCosteRangos();
        CosteTextField.setText(coste);
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
                if (hab.MereceLaPenaListar()) {
                    HabilidadesComboBox.addItem(hab.DevolverNombre());
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarHabilidadesComboBox(String nombreSeleccionado) {
        ActualizarHabilidadesComboBox();
        HabilidadesComboBox.setSelectedItem(nombreSeleccionado);
    }

    public void ActualizarCategoriaSeleccionada() {
        try {
            Categoria cat = DevolverCategoriaSeleccionada();
            //CategoriaSeleccionadaTextField.setText(cat.DevolverNombre());
            RangosCategoriaTextField.setText(cat.DevolverAntiguosRangos() + "");
            BonusCategoriaTextField.setText(cat.DevolverBonuses() + "");
            TotalCategoriaTextField.setText(cat.Total() + "");
            GenerarCosteCategoria();
            ActivarCheckBoxCategoria();
        } catch (NullPointerException npe) {
           // CategoriaSeleccionadaTextField.setText("");
            RangosCategoriaTextField.setText("");
            BonusCategoriaTextField.setText("");
            TotalCategoriaTextField.setText("");
        }
    }

    public void ActualizarListaEspecialidades(Habilidad hab) {
        String texto = "";
        for (int i = 0; i < hab.especializacion.size(); i++) {
            if (i > 0) {
                texto += ", ";
            }
            texto += hab.especializacion.get(i);
        }
        MotivoEspecializacionTextField.setText(texto);
    }

    public void ActualizarHabilidadSeleccionada() {
        try {
            Habilidad hab = DevolverHabilidadSeleccionada();
           // HabilidadSeleccionadaTextField.setText(hab.DevolverNombre());
            RangosHabilidadTextField.setText(hab.DevolverAntiguosRangos() + "");
            BonusHabilidadTextField.setText(hab.DevolverBonuses() + "");
            TotalHabilidadTextField.setText(hab.Total() + "");
            ActualizarCheckBoxHabilidad();
            GeneralCheckBox.setSelected(hab.EsGeneralizada());
            EspecializadaCheckBox.setSelected(hab.especializada);
            ActualizarListaEspecialidades(hab);
            Categoria cat = DevolverCategoriaSeleccionada();
            if (cat.TipoCategoria().equals("Limitada")
                    || cat.TipoCategoria().equals("DF")
                    || cat.TipoCategoria().equals("DPP")
                    || cat.TipoCategoria().equals("Especial")) {
                GeneralCheckBox.setEnabled(false);
                EspecializadaCheckBox.setEnabled(false);
            } else {
                if (cat.TipoCategoria().equals("Combinada")) {
                    GeneralCheckBox.setEnabled(false);
                    EspecializadaCheckBox.setEnabled(true);
                } else {
                    GeneralCheckBox.setEnabled(true);
                    EspecializadaCheckBox.setEnabled(true);
                }
            }
        } catch (NullPointerException npe) {
            //HabilidadSeleccionadaTextField.setText("");
            RangosHabilidadTextField.setText("");
            BonusHabilidadTextField.setText("");
            TotalHabilidadTextField.setText("");
        }
    }

    public void ActualizarPuntosDesarrollo() {
        PDTextField.setText(Personaje.getInstance().PuntosDesarrolloNoGastados() + "");
    }

    private Categoria DevolverCategoriaSeleccionada() {
        try {
            return Personaje.getInstance().DevolverCategoriaDeNombre(CategoriasComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        } catch (NullPointerException npe) {
            return null;
        }
    }

    public Habilidad DevolverHabilidadSeleccionada() {
        Categoria cat = DevolverCategoriaSeleccionada();
        try {
            return cat.DevolverHabilidadDeNombre(HabilidadesComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        } catch (IndexOutOfBoundsException iobe) {
            HabilidadesComboBox.setSelectedItem(0);
            return null;
        }
    }

    private void ActivarCheckBoxCategoria() {
        CategoriasCheckBox3.setEnabled(false);
        CategoriasCheckBox2.setEnabled(false);
        CategoriasCheckBox1.setEnabled(false);
        try {
            Categoria cat = DevolverCategoriaSeleccionada();

            if ((Personaje.getInstance().CosteCategoriaYHabilidad(cat, 0, null) <= Personaje.getInstance().PuntosDesarrolloNoGastados() || cat.nuevosRangos > 0) && !cat.TipoCategoria().equals("Limitada") && !cat.TipoCategoria().equals("Combinada") && !cat.TipoCategoria().equals("DF") && !cat.TipoCategoria().equals("DPP")) {
                CategoriasCheckBox1.setEnabled(true);
            }
            if ((Personaje.getInstance().CosteCategoriaYHabilidad(cat, 1, null) <= Personaje.getInstance().PuntosDesarrolloNoGastados() || cat.nuevosRangos > 1) && !cat.TipoCategoria().equals("Limitada") && !cat.TipoCategoria().equals("Combinada") && !cat.TipoCategoria().equals("DF") && !cat.TipoCategoria().equals("DPP")) {
                CategoriasCheckBox2.setEnabled(true);
            }
            if ((Personaje.getInstance().CosteCategoriaYHabilidad(cat, 2, null) <= Personaje.getInstance().PuntosDesarrolloNoGastados() || cat.nuevosRangos > 2) && !cat.TipoCategoria().equals("Limitada") && !cat.TipoCategoria().equals("Combinada") && !cat.TipoCategoria().equals("DF") && !cat.TipoCategoria().equals("DPP")) {
                CategoriasCheckBox3.setEnabled(true);
            }

        } catch (NullPointerException npe) {
        }
    }

    private void ActualizarCheckBoxHabilidad() {
        HabilidadesCheckBox3.setEnabled(false);
        HabilidadesCheckBox2.setEnabled(false);
        HabilidadesCheckBox1.setEnabled(false);
        try {
            Habilidad hab = DevolverHabilidadSeleccionada();
            if (hab != null) {
                Categoria cat = DevolverCategoriaSeleccionada();
                if (Personaje.getInstance().CosteCategoriaYHabilidad(cat, 0, hab) <= Personaje.getInstance().PuntosDesarrolloNoGastados() || hab.nuevosRangos > 0) {
                    HabilidadesCheckBox1.setEnabled(true);
                }
                if (Personaje.getInstance().CosteCategoriaYHabilidad(cat, 1, hab) <= Personaje.getInstance().PuntosDesarrolloNoGastados() || hab.nuevosRangos > 1) {
                    HabilidadesCheckBox2.setEnabled(true);
                }
                if (Personaje.getInstance().CosteCategoriaYHabilidad(cat, 2, hab) <= Personaje.getInstance().PuntosDesarrolloNoGastados() || hab.nuevosRangos > 2) {
                    HabilidadesCheckBox3.setEnabled(true);
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarCategoriasNuevosRangos() {
        CategoriasCheckBox3.setSelected(false);
        CategoriasCheckBox2.setSelected(false);
        CategoriasCheckBox1.setSelected(false);
        ActivarCheckBoxCategoria();
        try {
            Categoria cat = DevolverCategoriaSeleccionada();

            switch (cat.nuevosRangos) {
                case 3:
                    CategoriasCheckBox3.setSelected(true);
                case 2:
                    CategoriasCheckBox2.setSelected(true);
                case 1:
                    CategoriasCheckBox1.setSelected(true);
                    break;
                default:
                    break;
            }
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarHabilidadesNuevosRangos() {
        HabilidadesCheckBox3.setSelected(false);
        HabilidadesCheckBox2.setSelected(false);
        HabilidadesCheckBox1.setSelected(false);
        ActualizarCheckBoxHabilidad();
        try {
            Habilidad hab = DevolverHabilidadSeleccionada();

            switch (hab.nuevosRangos) {
                case 3:
                    HabilidadesCheckBox3.setSelected(true);
                case 2:
                    HabilidadesCheckBox2.setSelected(true);
                case 1:
                    HabilidadesCheckBox1.setSelected(true);
                    break;
                default:
                    break;
            }
        } catch (NullPointerException npe) {
        }
        try {
            ActualizarHabilidadesComboBox(DevolverHabilidadSeleccionada().DevolverNombre());
        } catch (NullPointerException npe) {
        }
    }

    public int ComprarCategoriasNuevosRangos() {
        int total = 0;
        try {
            if (CategoriasCheckBox3.isSelected()) {
                total++;
            }
            if (CategoriasCheckBox2.isSelected()) {
                total++;
            }
            if (CategoriasCheckBox1.isSelected()) {
                total++;
            }
            Categoria cat = DevolverCategoriaSeleccionada();
            cat.nuevosRangos = total;
        } catch (NullPointerException npe) {
        }
        ActualizarTextoHabilidades();
        return total;
    }

    public int ComprarHabilidadesNuevosRangos() {
        int total = 0;
        try {
            if (HabilidadesCheckBox3.isSelected()) {
                total++;
            }
            if (HabilidadesCheckBox2.isSelected()) {
                total++;
            }
            if (HabilidadesCheckBox1.isSelected()) {
                total++;
            }
            Habilidad hab = DevolverHabilidadSeleccionada();

            //Los hechizos tienen un tratamiento especial cuando se añaden rangos.
            Categoria cat = DevolverCategoriaSeleccionada();
            if (cat.DevolverNombre().equals("Listas Básicas de Hechizos")
                    || cat.DevolverNombre().equals("Listas Abiertas de Hechizos")
                    || cat.DevolverNombre().equals("Listas Cerradas de Hechizos")
                    || cat.DevolverNombre().equals("Listas Básicas de otras Profesiones")
                    || cat.DevolverNombre().equals("Listas Abiertas de otros Reinos")
                    || cat.DevolverNombre().equals("Listas Cerradas de otros Reinos")
                    || cat.DevolverNombre().equals("Listas Básicas de otros Reinos")
                    || cat.DevolverNombre().equals("Listas Abiertas Arcanas")
                    || cat.DevolverNombre().equals("Listas Hechizos de Adiestramiento")
                    || cat.DevolverNombre().equals("Listas Hechizos de Adiestramientos de Otro Reino")
                    || cat.DevolverNombre().equals("Listas Básicas de la Tríada")
                    || cat.DevolverNombre().equals("Listas Básicas Elementales Complementarias")) {
                //Si el el primer rango que añado a ese hechizo
                if (hab.nuevosRangos == 0 && total > 0) {
                    hab.multiplicadorCosteHechizos = Personaje.getInstance().DevolverMultiplicadoCosteHechizos();
                }
            }

            //Sumamos los rangos nuevos a la habilidad.
            hab.NuevosRangos(total);
            if (Personaje.getInstance().PuntosDesarrolloNoGastados() < 0 || (Personaje.getInstance().nivel == 1
                    && ((hab.DevolverRangos() > 10 && !hab.estiloDeVida)
                    || (hab.DevolverRangos() > 15 && hab.estiloDeVida)))) {
                hab.IncrementarNuevosRangos(-1);
            }
            if (hab.rangos + hab.nuevosRangos < hab.RangosGastadosEnEspecializacion()) {
                hab.IncrementarNuevosRangos(1);
            }

            if (hab.nuevosRangos + hab.rangos <= 0) {
                EspecializadaCheckBox.setSelected(false);
            }
        } catch (NullPointerException npe) {
        }
        ActualizarTextoHabilidades();

        return total;
    }

    public void ActualizarTotalCategoria() {
        Categoria cat = DevolverCategoriaSeleccionada();
        TotalCategoriaTextField.setText(cat.Total() + "");
    }

    private void ActualizarTextoHabilidades() {
        FichaTxt fichatxt = new FichaTxt();
        String texto = fichatxt.ExportarATextoHabilidades();

        if (Personaje.getInstance().otrasHabilidades.size() > 0) {
            texto += "\nOtros:\n";
            texto += "--------------------------\n";
            for (int k = 0; k < Personaje.getInstance().otrasHabilidades.size(); k++) {
                texto += Personaje.getInstance().otrasHabilidades.get(k).nombre + " ("
                        + Personaje.getInstance().otrasHabilidades.get(k).coste + ")\n";
            }
        }

        ResumenTextArea.setTabSize(15);
        ResumenTextArea.setText(texto);
    }

    public String DevolverNombreOtrasHabilidades() {
        return OtrosTextField.getText();
    }

    public int DevolverCosteOtrasHabilidades() {
        return (Integer) PdSpinner.getValue();
    }

    public void LimpiarOtrasHabilidades() {
        OtrosTextField.setText("");
        PdSpinner.setValue(0);
        ActualizarTextoHabilidades();
    }

    public void PdSpinnerInRange() {
        if ((Integer) PdSpinner.getValue() > Personaje.getInstance().PuntosDesarrolloNoGastados()) {
            PdSpinner.setValue((Integer) PdSpinner.getValue() - 1);
        }
        if ((Integer) PdSpinner.getValue() < 0) {
            PdSpinner.setValue(0);
        }
    }

    private void SeleccionarHabilidadYCategoria(String categoria, String habilidad) {
        CategoriasComboBox.setSelectedItem(categoria);
        HabilidadesComboBox.setSelectedItem(habilidad);
    }

    public void HacerGeneralHabilidad() {
        Habilidad hab = DevolverHabilidadSeleccionada();
        if (GeneralCheckBox.isSelected()) {
            if(hab.HacerGeneralizada()){
                EspecializadaCheckBox.setSelected(false);
                MotivoEspecializacionTextField.setText("");
            }
            GeneralCheckBox.setSelected(hab.EsGeneralizada());
            Refrescar();
        } else {
            hab.QuitarGeneralizada();
            Refrescar();
        }
        SeleccionarHabilidadYCategoria(hab.categoriaPadre.DevolverNombre(), hab.DevolverNombre());
    }

    public void HacerEspecializadaHabilidad() {
        Habilidad hab = DevolverHabilidadSeleccionada();
        if (hab.rangosEspecializacionAntiguosNiveles > 0) {
            EspecializadaCheckBox.setSelected(true);
        }
        if (!hab.EsGeneralizada()) {
            hab.AñadirEspecializacion(EspecializadaCheckBox.isSelected(), MotivoEspecializacionTextField.getText(), antiguaEspecializacion);
        } else {
            MostrarError.showErrorMessage("No puedes crear una especialización de una habilidad generalizada", "Habilidad",
                    JOptionPane.WARNING_MESSAGE);
            hab.especializada = false;
        }
        EspecializadaCheckBox.setSelected(hab.especializada);
        if (!hab.especializada) {
            MotivoEspecializacionTextField.setText("");
        }

        Refrescar();
    }

    /************************************************
     *
     *                    LISTENERS
     *
     ************************************************/
    /**
     * Añade el listener.
     */
    public void addCategoriasComboBoxListener(ActionListener al) {
        CategoriasComboBox.addActionListener(al);
    }

    public void addHabilidadesComboBoxListener(ActionListener al) {
        HabilidadesComboBox.addActionListener(al);
    }

    public void addCategoriasYHabilidadesCheckBoxListener(ActionListener al) {
        CategoriasCheckBox3.addActionListener(al);
        CategoriasCheckBox2.addActionListener(al);
        CategoriasCheckBox1.addActionListener(al);
        HabilidadesCheckBox3.addActionListener(al);
        HabilidadesCheckBox2.addActionListener(al);
        HabilidadesCheckBox1.addActionListener(al);
    }

    public void addGeneralCheckBoxListener(ActionListener al) {
        GeneralCheckBox.addActionListener(al);
    }

    public void addEspecializadaCheckBoxListener(ActionListener al) {
        EspecializadaCheckBox.addActionListener(al);
    }

    public void addAceptarButtonListener(ActionListener al) {
        AceptarButton.addActionListener(al);
    }

    public void addAleatorioButtonListener(ActionListener al) {
        AleatorioButton.addActionListener(al);
    }

    public void addAddButtonListener(ActionListener al) {
        AddButton.addActionListener(al);
    }

    public void addPdSpinnerListener(ChangeListener al) {
        PdSpinner.addChangeListener(al);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ResumenTextArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        PDTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        RangosCategoriaTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        CategoriasCheckBox1 = new javax.swing.JCheckBox();
        CategoriasCheckBox2 = new javax.swing.JCheckBox();
        CategoriasCheckBox3 = new javax.swing.JCheckBox();
        BonusCategoriaTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        RangosHabilidadTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        HabilidadesCheckBox1 = new javax.swing.JCheckBox();
        HabilidadesCheckBox2 = new javax.swing.JCheckBox();
        HabilidadesCheckBox3 = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        TotalCategoriaTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        BonusHabilidadTextField = new javax.swing.JTextField();
        TotalHabilidadTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        CosteTextField = new javax.swing.JTextField();
        GeneralCheckBox = new javax.swing.JCheckBox();
        EspecializadaCheckBox = new javax.swing.JCheckBox();
        MotivoEspecializacionTextField = new javax.swing.JTextField();
        CategoriasComboBox = new javax.swing.JComboBox();
        HabilidadesComboBox = new javax.swing.JComboBox();
        AceptarButton = new javax.swing.JButton();
        AleatorioButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        AddButton = new javax.swing.JButton();
        OtrosTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        PdSpinner = new javax.swing.JSpinner();

        setTitle("Categoría y Habilidades");

        ResumenTextArea.setColumns(20);
        ResumenTextArea.setRows(5);
        jScrollPane1.setViewportView(ResumenTextArea);

        jPanel1.setBorder(null);

        PDTextField.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel13.setText("PD:");

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel1.setText("Rangos en Categorías y Habilidades");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel1)
                .addComponent(jLabel13)
                .addComponent(PDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        RangosCategoriaTextField.setEditable(false);

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel3.setText("Categoría:");

        jLabel4.setText("Rangos:");

        jLabel5.setText("Nuevos:");

        jLabel6.setText("Bonus:");

        CategoriasCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        CategoriasCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        CategoriasCheckBox3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        BonusCategoriaTextField.setEditable(false);

        jLabel7.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel7.setText("Habilidad:");

        RangosHabilidadTextField.setEditable(false);

        jLabel8.setText("Rangos:");

        jLabel9.setText("Nuevos:");

        HabilidadesCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        HabilidadesCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        HabilidadesCheckBox3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        jLabel10.setText("Total:");

        TotalCategoriaTextField.setEditable(false);

        jLabel11.setText("Bonus:");

        jLabel12.setText("Total:");

        BonusHabilidadTextField.setEditable(false);

        TotalHabilidadTextField.setEditable(false);

        jLabel14.setText("Coste:");

        CosteTextField.setEditable(false);

        GeneralCheckBox.setText("Generalizada");

        EspecializadaCheckBox.setText("Especializada");

        MotivoEspecializacionTextField.setToolTipText("Indicar en qué está especializada en una lista separada por comas.");
        MotivoEspecializacionTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                MotivoEspecializacionTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                MotivoEspecializacionTextFieldFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CategoriasComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HabilidadesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(GeneralCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EspecializadaCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(RangosCategoriaTextField)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RangosHabilidadTextField))
                    .addComponent(jLabel8))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(CategoriasCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CategoriasCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CategoriasCheckBox3))
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(HabilidadesCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HabilidadesCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HabilidadesCheckBox3)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BonusHabilidadTextField)
                            .addComponent(jLabel6)
                            .addComponent(BonusCategoriaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                            .addComponent(jLabel11))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TotalCategoriaTextField)
                            .addComponent(TotalHabilidadTextField)))
                    .addComponent(MotivoEspecializacionTextField))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BonusCategoriaTextField, BonusHabilidadTextField});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CategoriasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel10)
                                .addComponent(jLabel14)))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(RangosHabilidadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HabilidadesCheckBox1)
                            .addComponent(HabilidadesCheckBox2)
                            .addComponent(HabilidadesCheckBox3)
                            .addComponent(BonusHabilidadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TotalHabilidadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(RangosCategoriaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(CategoriasCheckBox1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(CategoriasCheckBox2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(CategoriasCheckBox3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BonusCategoriaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TotalCategoriaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(MotivoEspecializacionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EspecializadaCheckBox)
                    .addComponent(GeneralCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AceptarButton.setText("Aceptar");

        AleatorioButton.setText("Aleatorio");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setText("Otros:");

        AddButton.setText("Añadir");

        OtrosTextField.setToolTipText("Permite añadir opciones no contempladas en el programa, como poderes Chi y demás");

        jLabel16.setText("PD:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OtrosTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(AddButton)
                    .addComponent(PdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(OtrosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AleatorioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AceptarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AceptarButton, AleatorioButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarButton)
                    .addComponent(AleatorioButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void MotivoEspecializacionTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MotivoEspecializacionTextFieldFocusLost
        if (EspecializadaCheckBox.isSelected()) {
            HacerEspecializadaHabilidad();
        }
    }//GEN-LAST:event_MotivoEspecializacionTextFieldFocusLost

    private void MotivoEspecializacionTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MotivoEspecializacionTextFieldFocusGained
        antiguaEspecializacion = MotivoEspecializacionTextField.getText();
    }//GEN-LAST:event_MotivoEspecializacionTextFieldFocusGained
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarButton;
    private javax.swing.JButton AddButton;
    private javax.swing.JButton AleatorioButton;
    private javax.swing.JTextField BonusCategoriaTextField;
    private javax.swing.JTextField BonusHabilidadTextField;
    private javax.swing.JCheckBox CategoriasCheckBox1;
    private javax.swing.JCheckBox CategoriasCheckBox2;
    private javax.swing.JCheckBox CategoriasCheckBox3;
    private javax.swing.JComboBox CategoriasComboBox;
    private javax.swing.JTextField CosteTextField;
    private javax.swing.JCheckBox EspecializadaCheckBox;
    private javax.swing.JCheckBox GeneralCheckBox;
    private javax.swing.JCheckBox HabilidadesCheckBox1;
    private javax.swing.JCheckBox HabilidadesCheckBox2;
    private javax.swing.JCheckBox HabilidadesCheckBox3;
    private javax.swing.JComboBox HabilidadesComboBox;
    private javax.swing.JTextField MotivoEspecializacionTextField;
    private javax.swing.JTextField OtrosTextField;
    private javax.swing.JTextField PDTextField;
    private javax.swing.JSpinner PdSpinner;
    private javax.swing.JTextField RangosCategoriaTextField;
    private javax.swing.JTextField RangosHabilidadTextField;
    private javax.swing.JTextArea ResumenTextArea;
    private javax.swing.JTextField TotalCategoriaTextField;
    private javax.swing.JTextField TotalHabilidadTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

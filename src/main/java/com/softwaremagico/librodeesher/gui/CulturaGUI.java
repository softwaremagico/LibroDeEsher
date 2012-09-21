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
import com.softwaremagico.librodeesher.Esher;
import com.softwaremagico.librodeesher.Habilidad;
import com.softwaremagico.librodeesher.IdiomaCultura;
import com.softwaremagico.librodeesher.Personaje;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jorge
 */
public class CulturaGUI extends javax.swing.JFrame {

    private boolean segundoPaso = false;
    DefaultListModel armasModel = new DefaultListModel();

    /**
     * Creates new form SelectGUI
     */
    public CulturaGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
    }

    public void Refrescar() {
        RellenarListaArmas();
        RellenarListaIdiomas();
        RellenarCategoriasArmas();
        RellenarListaAficiones();
        RellenarListaHechizosYPuntos();
        AsignarPuntosIdiomas();
        AsignarPuntosAficiones();

    }

    public void Reset() {
        AceptarButton.setEnabled(true);
        SubirArmaButton.setEnabled(true);
        BajarArmaButton.setEnabled(true);
    }

    public void Enabled(boolean actived) {
        AceptarButton.setEnabled(actived);
        //ArmaAdolescenciaComboBox.setEnabled(actived);
        ArmasAdolescenciaPanel.setEnabled(actived);
        //ArmasList.setEnabled(actived);
        //ArmasPanel.setEnabled(actived);
        ArmasScrollPane.setEnabled(actived);
        BajarArmaButton.setEnabled(actived);
        //CategoriaArmasAdolescenciaComboBox.setEnabled(actived);
        EscritoSpinner.setEnabled(actived);
        HabladoSpinner.setEnabled(actived);
        //IdiomaTextField.setEditable(actived);
        //IdiomasComboBox.setEnabled(actived);
        SubirArmaButton.setEnabled(actived);
    }

    public void SegundoPasoPjAcabado() {
        segundoPaso = true;
        AsignarRangosArmasSeleccionadas();
        AsignarRangosIdiomasSeleccionados();
        AsignarRangosHechizosSeleccionados();
        Enabled(false);
    }

    public void AleatorioAceptado() {
        Refrescar();
    }

    public int DevolverIndiceCategoriaArmaSeleccionada() {
        return CategoriaArmasAdolescenciaComboBox.getSelectedIndex();
    }

    public String DevolverArmaSeleccionada() {
        return ArmaAdolescenciaComboBox.getSelectedItem().toString();
    }

    public String DevolverCategoriaArmasSeleccionada() {
        return CategoriaArmasAdolescenciaComboBox.getSelectedItem().toString();
    }

    /**
     * **********************************************
     *
     * ARMAS
     *
     ***********************************************
     */
    /**
     * Genera una lista de tipos de arma y lo añade al ComboBox.
     */
    public void RellenarListaArmas() {
        armasModel.removeAllElements();
        for (int i = 0; i < Personaje.getInstance().armas.DevolverTotalTiposDeArmas(); i++) {
            armasModel.addElement(Personaje.getInstance().armas.DevolverTipoDeArma(i));
        }
    }

    public void SubirArma() {
        int index = ArmasList.getSelectedIndex();
        Personaje.getInstance().armas.SubirIndiceTipoArma(index);
        RellenarListaArmas();
        if (index > 0) {
            index--;
        }
        ArmasList.setSelectedIndex(index);
    }

    public void BajarArma() {
        int index = ArmasList.getSelectedIndex();
        Personaje.getInstance().armas.BajarIndiceTipoArma(index);
        RellenarListaArmas();
        if (index < Personaje.getInstance().armas.DevolverTotalTiposDeArmas() - 1) {
            index++;
        }
        ArmasList.setSelectedIndex(index);

    }

    public void RellenarCategoriasArmas() {
        CategoriaArmasAdolescenciaComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().armas.DevolverTotalTiposDeArmas(); i++) {
            CategoriaArmasAdolescenciaComboBox.addItem(Personaje.getInstance().armas.DevolverTipoDeArma(i));
        }
    }

    public void RellenarArmasSegunCategoria() throws Exception {
        List<String> listadoArmas;
        try {
            listadoArmas = Personaje.getInstance().armas.SeleccionarNombreArmasValidasPorCategoriaDeTipo(
                    CategoriaArmasAdolescenciaComboBox.getSelectedItem().toString());
        } catch (NullPointerException npe) {
            listadoArmas = Personaje.getInstance().armas.SeleccionarNombreArmasValidasPorCategoriaDeTipo("2manos");
        }
        ArmaAdolescenciaComboBox.removeAllItems();
        for (int i = 0; i < listadoArmas.size(); i++) {
            ArmaAdolescenciaComboBox.addItem(listadoArmas.get(i));
        }
    }

    public void SeleccionarRangosArmasAdolescencia() {
        if (!segundoPaso) {
            try {
                RangoCategoriaArmaTextField.setText(Personaje.getInstance().DevolverCategoriaDeNombre("Armas·"
                        + CategoriaArmasAdolescenciaComboBox.getSelectedItem().toString()).rangosCultura + "");
                RangoArmaTextField.setText(Personaje.getInstance().armas.DevolverRangosCulturaTipoArma(CategoriaArmasAdolescenciaComboBox.getSelectedItem().toString()) + "");
            } catch (NullPointerException npe) {
                RangoCategoriaArmaTextField.setText("0");
                RangoArmaTextField.setText("0");
            }
        } else {
            try {
                Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(ArmaAdolescenciaComboBox.getSelectedItem().toString());
                RangoArmaTextField.setText(hab.rangosCultura + "");
            } catch (NullPointerException npe) {
                RangoArmaTextField.setText("0");
            }
        }
    }

    private void AsignarRangosArmasSeleccionadas() {
        for (int i = 0; i < CategoriaArmasAdolescenciaComboBox.getItemCount(); i++) {
            Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre(("Armas·"
                    + CategoriaArmasAdolescenciaComboBox.getItemAt(i).toString()));
            try {
                cat.rangosCultura = Personaje.getInstance().armas.DevolverRangosCulturaTipoArma(CategoriaArmasAdolescenciaComboBox.getItemAt(i).toString());

                for (int j = 0; j < Personaje.getInstance().armas.armasCultura.GetTotalArmas(); j++) {
                    Habilidad hab = null;
                    String nombreArmaCultura = "";

                    nombreArmaCultura = Personaje.getInstance().armas.armasCultura.GetArmaCultura(j).nombreArma;
                    try {
                        hab = cat.DevolverHabilidadDeNombre(nombreArmaCultura);
                        hab.rangosCultura = Personaje.getInstance().armas.armasCultura.DevolverRangosCulturaArma(nombreArmaCultura) + hab.rangosCultura;
                    } catch (NullPointerException npe) {
                    }
                }
            } catch (NullPointerException npe) {
                cat = null;
                MostrarError.showErrorMessage("Imposible encontrar categoría " + CategoriaArmasAdolescenciaComboBox.getItemAt(i).toString(), "Cultura");
            }
        }
    }

    /**
     * **********************************************
     *
     * IDIOMAS
     *
     ***********************************************
     */
    /**
     * Genera una lista de idiomas seleccionables.
     */
    private void RellenarListaIdiomas() {
        IdiomasComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().idiomasCultura.Size(); i++) {
            IdiomaCultura id = Personaje.getInstance().idiomasCultura.Get(i);
            IdiomasComboBox.addItem(id.nombre);
        }
        for (int i = 0; i < Personaje.getInstance().idiomasRaza.Size(); i++) {
            IdiomaCultura id = Personaje.getInstance().idiomasRaza.Get(i);
            boolean exist = false;
            for (int j = 0; j < IdiomasComboBox.getItemCount(); j++) {
                if (IdiomasComboBox.getItemAt(j).toString().equals(id.nombre)) {
                    exist = true;
                }
            }
            if (!exist) {
                IdiomasComboBox.addItem(id.nombre);
            }
        }
    }

    public void ActualizarIdiomaSeleccionado() {
        try {
            IdiomaCultura id = DevolverIdiomaSeleccionado();
            IdiomaTextField.setText(id.nombre);
            HabladoSpinner.setValue(id.DevolverValorHablado());
            EscritoSpinner.setValue(id.DevolverValorEscrito());
        } catch (NullPointerException npe) {
            IdiomaTextField.setText("");
        }
    }

    private IdiomaCultura DevolverIdiomaSeleccionado() {
        try {
            return Personaje.getInstance().idiomasCultura.Get(IdiomasComboBox.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    public void AsignarPuntosIdiomas() {
        PuntosIdiomasTextField.setText(Personaje.getInstance().DevolverPuntosIdiomaCultura() + "");
    }

    public void HabladoSpinnerInRange() {
        IdiomaCultura id = DevolverIdiomaSeleccionado();
        id.rangosNuevosHablado = Integer.parseInt(HabladoSpinner.getValue().toString()) - id.hablado;
        if ((Integer) HabladoSpinner.getValue() < id.hablado) {
            HabladoSpinner.setValue((Integer) HabladoSpinner.getValue() + 1);
        }

        if ((Integer) HabladoSpinner.getValue() > id.maxHabladoCultura
                || Integer.parseInt(HabladoSpinner.getValue().toString()) > 10) {
            HabladoSpinner.setValue((Integer) HabladoSpinner.getValue() - 1);
        }
        if (Personaje.getInstance().DevolverPuntosIdiomaCultura() < 0) {
            HabladoSpinner.setValue((Integer) HabladoSpinner.getValue() - 1);
        }
    }

    public void EscritoSpinnerInRange() {
        IdiomaCultura id = DevolverIdiomaSeleccionado();
        id.rangosNuevosEscritos = Integer.parseInt(EscritoSpinner.getValue().toString()) - id.escrito;
        if ((Integer) EscritoSpinner.getValue() < id.escrito) {
            EscritoSpinner.setValue((Integer) EscritoSpinner.getValue() + 1);
        }
        if ((Integer) EscritoSpinner.getValue() > id.maxEscritoCultura
                || Integer.parseInt(EscritoSpinner.getValue().toString()) > 10) {
            EscritoSpinner.setValue((Integer) EscritoSpinner.getValue() - 1);
        }
        if (Personaje.getInstance().DevolverPuntosIdiomaCultura() < 0) {
            EscritoSpinner.setValue((Integer) EscritoSpinner.getValue() - 1);
        }
    }

    private void AsignarRangosIdiomasSeleccionados() {
        Habilidad hab;
        Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre("Comunicación");
        for (int i = 0; i < Personaje.getInstance().idiomasCultura.Size(); i++) {
            IdiomaCultura idi = Personaje.getInstance().idiomasCultura.Get(i);
            if (idi.DevolverValorHablado() > 0) {
                try {
                    hab = cat.DevolverHabilidadDeNombre("Hablar " + idi.nombre);
                    hab.rangos = idi.DevolverValorHablado();
                } catch (NullPointerException npe) {
                    hab = Habilidad.getSkill(cat, "Hablar " + idi.nombre);
                    hab.rangos = idi.DevolverValorHablado();
                    cat.AddHabilidad(hab);
                }
            }
            if (idi.DevolverValorEscrito() > 0) {
                try {
                    hab = cat.DevolverHabilidadDeNombre("Escribir " + idi.nombre);
                    hab.rangos = idi.DevolverValorEscrito();
                } catch (NullPointerException npe) {
                    hab = Habilidad.getSkill(cat, "Escribir " + idi.nombre);
                    hab.rangos = idi.DevolverValorHablado();
                    cat.AddHabilidad(hab);
                }
            }
        }
    }

    /**
     * **********************************************
     *
     * AFICIONES
     *
     ***********************************************
     */
    /**
     * Prepara la lista de aficiones para que el usuario pueda seleccionar una.
     */
    private void RellenarListaAficiones() {
        AficionesComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().listaAficiones.size(); i++) {
            String af = Personaje.getInstance().listaAficiones.get(i);
            AficionesComboBox.addItem(af);
        }
    }

    public void AsignarPuntosAficiones() {
        PuntosAficionesTextField.setText(Personaje.getInstance().DevolverPuntosAficiones() + "");
    }

    public void ActualizarAficionSeleccionada() {
        try {
            Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(AficionesComboBox.getSelectedItem().toString());
            AficionesSpinner.setValue(hab.rangosAficiones + hab.rangosCultura + Personaje.getInstance().armas.DevolverRangosCulturaArma(hab.DevolverNombre()));
        } catch (NullPointerException npe) {
            AficionesSpinner.setValue(0);
        }
    }

    public void AficionesSpinnerInRange() {

        try {
            Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(AficionesComboBox.getSelectedItem().toString());
            hab.rangosAficiones = (Integer) AficionesSpinner.getValue() - hab.rangosCultura - Personaje.getInstance().armas.DevolverRangosCulturaArma(hab.DevolverNombre());

            if ((Integer) AficionesSpinner.getValue() < hab.rangosCultura + Personaje.getInstance().armas.DevolverRangosCulturaArma(hab.DevolverNombre())) {
                AficionesSpinner.setValue(hab.rangosCultura + Personaje.getInstance().armas.DevolverRangosCulturaArma(hab.DevolverNombre()));
            }

            if ((Integer) AficionesSpinner.getValue() > 10) {
                AficionesSpinner.setValue(10);
            }

            if (Personaje.getInstance().DevolverPuntosAficiones() < 0) {
                AficionesSpinner.setValue((Integer) AficionesSpinner.getValue() - 1);
            }

            if ((Integer) AficionesSpinner.getValue() > hab.NumeroRangosIncrementables() + hab.rangosCultura + Personaje.getInstance().armas.DevolverRangosCulturaArma(hab.DevolverNombre())) {
                AficionesSpinner.setValue((Integer) AficionesSpinner.getValue() - 1);
            }


        } catch (NullPointerException npe) {
            AficionesSpinner.setValue(0);
        }
    }

    /**
     * **********************************************
     *
     * HECHIZOS
     *
     ***********************************************
     */
    /**
     * Genera un listado de hechizos en el combobox apropiado.
     */
    private void RellenarListaHechizosYPuntos() {
        HechizosComboBox.removeAllItems();
        int seleccionado = 0;
        if (Personaje.getInstance().rangosHechizosCultura > 0) {
            Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
            cat.OrdenarHabilidades();
            for (int h = 0; h < cat.listaHabilidades.size(); h++) {
                try {
                    if (cat.listaHabilidades.get(h).DevolverNombre().equals(Personaje.getInstance().hechizoCultura.nombre)) {
                        seleccionado = h;
                    }
                } catch (NullPointerException npe) {
                }
                HechizosComboBox.addItem(cat.listaHabilidades.get(h).DevolverNombre());
            }
        }
        try {
            HechizosComboBox.setSelectedIndex(seleccionado);
        } catch (IllegalArgumentException iae) {
        }
        HechizosRangosTextField.setText(Personaje.getInstance().rangosHechizosCultura + "");
    }

    private void AsignarRangosHechizosSeleccionados() {
        if (Personaje.getInstance().rangosHechizosCultura > 0) {
            try {
                Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(HechizosComboBox.getSelectedItem().toString());
                Personaje.getInstance().AsignarListaHechizosCultura(hab);
            } catch (NullPointerException npe) {
            }
        }
    }

    /**
     * **********************************************
     *
     * LISTENERS
     *
     ***********************************************
     */
    /**
     * Añade un lister adecuado.
     */
    public void addSubirArmaListener(ActionListener al) {
        SubirArmaButton.addActionListener(al);
    }

    public void addBajarArmaListener(ActionListener al) {
        BajarArmaButton.addActionListener(al);
    }

    public void addAceptarButtonListener(ActionListener al) {
        AceptarButton.addActionListener(al);
    }

    public void addAleatorioButtonListener(ActionListener al) {
        AleatorioButton.addActionListener(al);
    }

    public void addHablarSpinnerListener(ChangeListener al) {
        HabladoSpinner.addChangeListener(al);
    }

    public void addEscribirSpinnerListener(ChangeListener al) {
        EscritoSpinner.addChangeListener(al);
    }

    public void addAficionesSpinnerListener(ChangeListener al) {
        AficionesSpinner.addChangeListener(al);
    }

    public void addIdiomasComboBoxListener(ActionListener al) {
        IdiomasComboBox.addActionListener(al);
    }

    public void addAficionesComboBoxListener(ActionListener al) {
        AficionesComboBox.addActionListener(al);
    }

    public void addCategoriaArmasAdolescenciaListener(ActionListener al) {
        CategoriaArmasAdolescenciaComboBox.addActionListener(al);
    }

    public void addArmasAdolescenciaListener(ActionListener al) {
        ArmaAdolescenciaComboBox.addActionListener(al);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ArmasPanel = new javax.swing.JPanel();
        SubirArmaButton = new javax.swing.JButton();
        ArmasScrollPane = new javax.swing.JScrollPane();
        ArmasList = new javax.swing.JList();
        BajarArmaButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AceptarButton = new javax.swing.JButton();
        IdiomasPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        IdiomaTextField = new javax.swing.JTextField();
        IdiomasComboBox = new javax.swing.JComboBox();
        HabladoSpinner = new javax.swing.JSpinner();
        EscritoSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        PuntosIdiomasTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ArmasAdolescenciaPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        CategoriaArmasAdolescenciaComboBox = new javax.swing.JComboBox();
        RangoCategoriaArmaTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ArmaAdolescenciaComboBox = new javax.swing.JComboBox();
        RangoArmaTextField = new javax.swing.JTextField();
        AficionesPanel = new javax.swing.JPanel();
        AficionesComboBox = new javax.swing.JComboBox();
        AficionesSpinner = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        PuntosAficionesTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        AleatorioButton = new javax.swing.JButton();
        HechizosPanel = new javax.swing.JPanel();
        HechizosComboBox = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        HechizosRangosTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccionar Rasgos de la Cultura");
        setAlwaysOnTop(true);
        setResizable(false);

        ArmasPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        SubirArmaButton.setText("Subir");

        ArmasList.setModel(armasModel);
        ArmasList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ArmasScrollPane.setViewportView(ArmasList);

        BajarArmaButton.setText("Bajar");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel1.setText("Armas:");

        javax.swing.GroupLayout ArmasPanelLayout = new javax.swing.GroupLayout(ArmasPanel);
        ArmasPanel.setLayout(ArmasPanelLayout);
        ArmasPanelLayout.setHorizontalGroup(
            ArmasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ArmasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ArmasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ArmasScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(BajarArmaButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SubirArmaButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap())
        );
        ArmasPanelLayout.setVerticalGroup(
            ArmasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ArmasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubirArmaButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ArmasScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BajarArmaButton)
                .addContainerGap())
        );

        AceptarButton.setText("Aceptar");

        IdiomasPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel2.setText("Idiomas:");

        IdiomaTextField.setEditable(false);

        jLabel3.setText("Idioma:");

        jLabel4.setText("Hablado:");

        jLabel5.setText("Escrito:");

        PuntosIdiomasTextField.setEditable(false);

        jLabel6.setText("Puntos:");

        javax.swing.GroupLayout IdiomasPanelLayout = new javax.swing.GroupLayout(IdiomasPanel);
        IdiomasPanel.setLayout(IdiomasPanelLayout);
        IdiomasPanelLayout.setHorizontalGroup(
            IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IdiomasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IdiomasPanelLayout.createSequentialGroup()
                        .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IdiomasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(13, 13, 13)
                        .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(PuntosIdiomasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(IdiomasPanelLayout.createSequentialGroup()
                        .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(IdiomaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(HabladoSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(EscritoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        IdiomasPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {EscritoSpinner, HabladoSpinner, PuntosIdiomasTextField});

        IdiomasPanelLayout.setVerticalGroup(
            IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IdiomasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdiomasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PuntosIdiomasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(IdiomasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HabladoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EscritoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdiomaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ArmasAdolescenciaPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setText("Categoría Adolescencia:");

        jLabel7.setText("Rangos:");

        RangoCategoriaArmaTextField.setEditable(false);

        jLabel9.setText("Arma Adolescencia:");

        jLabel10.setText("Rangos:");

        RangoArmaTextField.setEditable(false);

        javax.swing.GroupLayout ArmasAdolescenciaPanelLayout = new javax.swing.GroupLayout(ArmasAdolescenciaPanel);
        ArmasAdolescenciaPanel.setLayout(ArmasAdolescenciaPanelLayout);
        ArmasAdolescenciaPanelLayout.setHorizontalGroup(
            ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ArmasAdolescenciaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ArmasAdolescenciaPanelLayout.createSequentialGroup()
                        .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CategoriaArmasAdolescenciaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RangoCategoriaArmaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(ArmasAdolescenciaPanelLayout.createSequentialGroup()
                        .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ArmasAdolescenciaPanelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(12, 12, 12))
                            .addGroup(ArmasAdolescenciaPanelLayout.createSequentialGroup()
                                .addComponent(ArmaAdolescenciaComboBox, 0, 144, Short.MAX_VALUE)
                                .addGap(16, 16, 16)))
                        .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(RangoArmaTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        ArmasAdolescenciaPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ArmaAdolescenciaComboBox, CategoriaArmasAdolescenciaComboBox});

        ArmasAdolescenciaPanelLayout.setVerticalGroup(
            ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ArmasAdolescenciaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RangoCategoriaArmaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoriaArmasAdolescenciaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ArmasAdolescenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ArmaAdolescenciaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RangoArmaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        AficionesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel11.setText("Aficiones:");

        jLabel12.setText("Rangos:");

        PuntosAficionesTextField.setEditable(false);
        PuntosAficionesTextField.setText("0");

        jLabel13.setText("Habilidad:");

        jLabel14.setText("Restantes:");

        javax.swing.GroupLayout AficionesPanelLayout = new javax.swing.GroupLayout(AficionesPanel);
        AficionesPanel.setLayout(AficionesPanelLayout);
        AficionesPanelLayout.setHorizontalGroup(
            AficionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AficionesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AficionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AficionesPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addComponent(AficionesComboBox, 0, 229, Short.MAX_VALUE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AficionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PuntosAficionesTextField, 0, 1, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AficionesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        AficionesPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AficionesSpinner, PuntosAficionesTextField});

        AficionesPanelLayout.setVerticalGroup(
            AficionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AficionesPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(AficionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(PuntosAficionesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGroup(AficionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AficionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AficionesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AficionesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        AficionesPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AficionesSpinner, PuntosAficionesTextField});

        AleatorioButton.setText("Aleatorio");

        HechizosPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setText("Lista de Hechizos:");

        HechizosRangosTextField.setEditable(false);
        HechizosRangosTextField.setText("0");

        javax.swing.GroupLayout HechizosPanelLayout = new javax.swing.GroupLayout(HechizosPanel);
        HechizosPanel.setLayout(HechizosPanelLayout);
        HechizosPanelLayout.setHorizontalGroup(
            HechizosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HechizosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HechizosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HechizosPanelLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(HechizosRangosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(HechizosComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 188, Short.MAX_VALUE))
                .addContainerGap())
        );
        HechizosPanelLayout.setVerticalGroup(
            HechizosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HechizosPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(HechizosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(HechizosRangosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(HechizosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ArmasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AficionesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(AleatorioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(AceptarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(HechizosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ArmasAdolescenciaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IdiomasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AceptarButton, AleatorioButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ArmasPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ArmasAdolescenciaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IdiomasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(HechizosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AficionesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AleatorioButton)
                    .addComponent(AceptarButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ArmasAdolescenciaPanel, IdiomasPanel});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AficionesPanel, HechizosPanel});

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarButton;
    private javax.swing.JComboBox AficionesComboBox;
    private javax.swing.JPanel AficionesPanel;
    private javax.swing.JSpinner AficionesSpinner;
    private javax.swing.JButton AleatorioButton;
    private javax.swing.JComboBox ArmaAdolescenciaComboBox;
    private javax.swing.JPanel ArmasAdolescenciaPanel;
    private javax.swing.JList ArmasList;
    private javax.swing.JPanel ArmasPanel;
    private javax.swing.JScrollPane ArmasScrollPane;
    private javax.swing.JButton BajarArmaButton;
    private javax.swing.JComboBox CategoriaArmasAdolescenciaComboBox;
    private javax.swing.JSpinner EscritoSpinner;
    private javax.swing.JSpinner HabladoSpinner;
    private javax.swing.JComboBox HechizosComboBox;
    private javax.swing.JPanel HechizosPanel;
    private javax.swing.JTextField HechizosRangosTextField;
    private javax.swing.JTextField IdiomaTextField;
    private javax.swing.JComboBox IdiomasComboBox;
    private javax.swing.JPanel IdiomasPanel;
    private javax.swing.JTextField PuntosAficionesTextField;
    private javax.swing.JTextField PuntosIdiomasTextField;
    private javax.swing.JTextField RangoArmaTextField;
    private javax.swing.JTextField RangoCategoriaArmaTextField;
    private javax.swing.JButton SubirArmaButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}

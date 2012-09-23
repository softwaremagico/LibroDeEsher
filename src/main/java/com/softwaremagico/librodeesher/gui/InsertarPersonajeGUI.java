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
 Created on march of 2008.
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

import com.softwaremagico.librodeesher.Caracteristica;
import com.softwaremagico.librodeesher.Categoria;
import com.softwaremagico.librodeesher.Esher;
import com.softwaremagico.librodeesher.FichaTxt;
import com.softwaremagico.librodeesher.Habilidad;
import com.softwaremagico.librodeesher.LeerCultura;
import com.softwaremagico.librodeesher.LeerProfesion;
import com.softwaremagico.librodeesher.Magia;
import com.softwaremagico.librodeesher.ObjetoMagico;
import com.softwaremagico.librodeesher.Personaje;
import com.softwaremagico.librodeesher.Talento;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jorge
 */
public class InsertarPersonajeGUI extends javax.swing.JFrame {

    private List<JSpinner> listaTemporales = new ArrayList<>();
    private List<JSpinner> listaPotenciales = new ArrayList<>();
    private List<JTextField> listaBasica = new ArrayList<>();
    private List<JTextField> listaRaza = new ArrayList<>();
    private List<JTextField> listaTotal = new ArrayList<>();
    public DefaultListModel armasModel = new DefaultListModel();
    private final String TAG_BONUS = "BONUS";
    public boolean refrescando = false;
    private String antiguaEspecializacion = "";
    private ElegirComunProfesionalGUI grupoHab = null;
    private SeleccionarHabilidadTalentoGUI selecHab = null;

    /**
     * Creates new form InsertarPersonajeGUI
     */
    public InsertarPersonajeGUI() {
        initComponents();
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int) (this.getWidth() / 2),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int) (this.getHeight() / 2));
        IniciarVentana();
    }

    public void IniciarVentana() {
        IniciaPersonaje();
        IniciaCaracteristicas();
        IniciaHabilidades();
        IniciarHistorial();
        IniciarObjetos();
    }

    public void Refrescar() {
        refrescando = true;
        NombreTextField.setText(Personaje.getInstance().DevolverNombreCompleto());
        NivelSpinner.setValue(Personaje.getInstance().nivel);
        RazasComboBox.setSelectedItem(Personaje.getInstance().raza);
        ProfesionesComboBox.setSelectedItem(Personaje.getInstance().profesion);
        CulturasComboBox.setSelectedItem(Personaje.getInstance().cultura);
        //RellenarReinosDeMagia();
        ReinosComboBox.setSelectedItem(Personaje.getInstance().reino);
        RellenaOpcionesAdiestramientoEscogidas();
        RellenarListaArmas();
        ActualizarTextoHabilidades();
        RefrescaCaracteristicas();
        ActualizarApariencia();
        ActualizarCategoriaCheckBox();
        ActualizarHabilidadCheckBox();
        ActualizarPuntosHistorial();
        RefrescarTalentos();
        refrescando = false;
        AparienciaTextField.setText(Personaje.getInstance().caracteristicas.DevolverApariencia() + "");
        BonusAparienciaTextField.setText(Personaje.getInstance().caracteristicas.DevolverTotalApariencia() + "");
    }

    public void ActualizarCultura() {
        new LeerCultura();
    }

    /**
     * ********************************************************************
     *
     * LOGICA PERSONAJE
     *
     *********************************************************************
     */
    /**
     * Inicia la intefaz grafica relativa a la definición del personaje.
     */
    private void IniciaPersonaje() {
        try {
            RellenaRazas();
            RellenaProfesion();
            /* Si no se ha leido aún la profesión, se lee. */
            if (!Personaje.getInstance().ProfesionAsignada()) {
                new LeerProfesion(false);
            }
            RellenarCulturas();
            RellenarReinosDeMagia();
            RellenarAdiestramientos();
        } catch (Exception ex) {
            Logger.getLogger(InsertarPersonajeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Rellena las razas disponibles en el sistema.
     *
     * @throws java.lang.Exception
     */
    private void RellenaRazas() throws Exception {
        List<String> razas = Esher.RazasDisponibles();
        razas = Esher.OrdenarLista(razas);
        RazasComboBox.removeAllItems();
        for (int i = 0; i < razas.size(); i++) {
            RazasComboBox.addItem(razas.get(i));
        }
        RazasComboBox.setSelectedItem(Personaje.getInstance().raza);
    }

    public void RellenaProfesion() throws Exception {
        List<String> profesiones = Esher.ProfesionesDisponibles();
        profesiones = Esher.OrdenarLista(profesiones);
        ProfesionesComboBox.removeAllItems();
        for (int i = 0; i < profesiones.size(); i++) {
            ProfesionesComboBox.addItem(profesiones.get(i));
        }
        ProfesionesComboBox.setSelectedItem(Personaje.getInstance().profesion);
    }

    public void RellenarCulturas() throws Exception {
        List<String> culturas = Esher.CulturasDisponibles();
        culturas = Esher.OrdenarLista(culturas);
        CulturasComboBox.removeAllItems();
        for (int i = 0; i < culturas.size(); i++) {
            CulturasComboBox.addItem(culturas.get(i));
        }
        CulturasComboBox.setSelectedItem(Personaje.getInstance().cultura);
    }

    public void RellenarReinosDeMagia() throws Exception {
        Magia magia = new Magia();
        List<String> reinos = magia.ObtenerReinoDisponible();
        ReinosComboBox.removeAllItems();
        for (int i = 0; i < reinos.size(); i++) {
            ReinosComboBox.addItem(reinos.get(i));
        }
        ReinosComboBox.setSelectedItem(Personaje.getInstance().reino);
    }

    public void RellenarAdiestramientos() {
        AdiestramientosComboBox.removeAllItems();
        List<String> adiestramientosPosibles = Esher.DevolverAdiestramientosPosibles();
        adiestramientosPosibles = Esher.OrdenarLista(adiestramientosPosibles);
        for (int i = 0; i < adiestramientosPosibles.size(); i++) {
            if (!Personaje.getInstance().adiestramientosAntiguos.contains(adiestramientosPosibles.get(i))) {
                AdiestramientosComboBox.addItem(adiestramientosPosibles.get(i));
            }
        }
    }

    public void RellenarOpcionesAdiestramiento() {
        String adiestramientos = "";
        for (int i = 0; i < Personaje.getInstance().adiestramientosAntiguos.size(); i++) {
            if (i > 0) {
                adiestramientos += ", ";
            }
            adiestramientos += Personaje.getInstance().adiestramientosAntiguos.get(i);
        }
        AdiestramientosTextField.setText(adiestramientos);
        RellenarAdiestramientos();
        RellenaOpcionesAdiestramientoEscogidas();
    }

    public void RellenaOpcionesAdiestramientoEscogidas() {
        String adiestramientos = "";
        for (int i = 0; i < Personaje.getInstance().adiestramientosAntiguos.size(); i++) {
            if (i > 0) {
                adiestramientos += ", ";
            }
            adiestramientos += Personaje.getInstance().adiestramientosAntiguos.get(i);
        }
        AdiestramientosTextField.setText(adiestramientos);
    }

    /**
     * Genera una lista de tipos de arma y lo añade al ComboBox.
     */
    public void RellenarListaArmas() {
        armasModel.removeAllElements();
        for (int i = 0; i < Personaje.getInstance().armas.DevolverTotalTiposDeArmas(); i++) {
            armasModel.addElement(Personaje.getInstance().armas.DevolverTipoDeArma(i));
        }
    }

    /**
     * Devuelve el nivel marcado por el usuario en la interfaz.
     */
    int DevolverNivelSeleccionado() {
        return (Integer) NivelSpinner.getValue();
    }

    public void NivelInRange() {
        if ((Integer) NivelSpinner.getValue() < 1) {
            NivelSpinner.setValue((Integer) 1);
        }
        Personaje.getInstance().nivel = (Integer) NivelSpinner.getValue();
    }

    public String DevolverRazaSeleccionada() {
        if (RazasComboBox.getSelectedIndex() >= 0) {
            //return RazasComboBox.getItemAt(RazasComboBox.getSelectedIndex()).toString();
            return RazasComboBox.getSelectedItem().toString();
        } else {
            return Personaje.getInstance().raza;
        }
    }

    public String DevolverProfesionSeleccionada() {
        if (ProfesionesComboBox.getSelectedIndex() >= 0) {

            //return ProfesionesComboBox.getItemAt(ProfesionesComboBox.getSelectedIndex()).toString();
            return ProfesionesComboBox.getSelectedItem().toString();
        } else {
            return Personaje.getInstance().profesion;
        }
    }

    public String DevolverCulturaSeleccionada() {
        if (CulturasComboBox.getSelectedIndex() >= 0) {
            //return CulturasComboBox.getItemAt(CulturasComboBox.getSelectedIndex()).toString();
            return CulturasComboBox.getSelectedItem().toString();
        } else {
            return Personaje.getInstance().cultura;
        }
    }

    public String DevolverReinoDeMagia() {
        try {
            return ReinosComboBox.getSelectedItem().toString();
        } catch (NullPointerException npe) {
            //npe.printStackTrace();
            return "Mentalismo";
        }
    }

    public String DevolverAdiestramientoSeleccionado() {
        if (AdiestramientosComboBox.getSelectedIndex() >= 0) {
            return AdiestramientosComboBox.getSelectedItem().toString();
        }
        return null;
    }

    public void InsertaNombre() {
        Personaje.getInstance().SetNombreCompleto(NombreTextField.getText());
    }

    public void SubirArma() {
        int index = ArmasList.getSelectedIndex();
        Personaje.getInstance().armas.SubirIndiceTipoArma(index);
        RellenarListaArmas();
        if (index > 0) {
            index--;
        }
        ArmasList.setSelectedIndex(index);
        Personaje.getInstance().ActualizarOrdenCostesArmas();
        ActualizarTextoHabilidades();
    }

    public void BajarArma() {
        int index = ArmasList.getSelectedIndex();
        Personaje.getInstance().armas.BajarIndiceTipoArma(index);
        RellenarListaArmas();
        if (index < Personaje.getInstance().armas.DevolverTotalTiposDeArmas() - 1) {
            index++;
        }
        ArmasList.setSelectedIndex(index);
        Personaje.getInstance().ActualizarOrdenCostesArmas();
        ActualizarTextoHabilidades();
    }

    public String DevuelveSexo() {
        if (MujerRadioButton.isSelected()) {
            return "Mujer";
        } else {
            return "Varón";
        }
    }

    public void ObtenerNombre() {
        if (NombreTextField.getText().equals("")) {
            Personaje.getInstance().ObtenerNombrePersonaje();
        } else {
            Personaje.getInstance().AsignarNombreCompleto(NombreTextField.getText());
        }
        NombreTextField.setText(Personaje.getInstance().DevolverNombreCompleto());
    }

    /**
     * ********************************************************************
     *
     * LOGICA CARACTERISTICAS
     *
     *********************************************************************
     */
    /**
     * Inicia la interfaz gráfica de las características.
     */
    private void IniciaCaracteristicas() {
        GeneraListadoTemporales();
        GeneraListadoPotenciales();
        GeneraListadoValorBasico();
        GeneraListadoRaza();
        GeneraListadoTotal();
        //ActualizaSeguridadCaracteristicas();
    }

    /**
     * Guarda una lista de todos los marcadores de caracteristicas temporales.
     */
    private void GeneraListadoTemporales() {
        listaTemporales = new ArrayList<JSpinner>();
        listaTemporales.add(AgilidadTemporalSpinner);
        listaTemporales.add(ConstitucionTemporalSpinner);
        listaTemporales.add(MemoriaTemporalSpinner);
        listaTemporales.add(RazonTemporalSpinner);
        listaTemporales.add(AutodisciplinaTemporalSpinner);
        listaTemporales.add(EmpatiaTemporalSpinner);
        listaTemporales.add(IntuicionTemporalSpinner);
        listaTemporales.add(PresenciaTemporalSpinner);
        listaTemporales.add(RapidezTemporalSpinner);
        listaTemporales.add(FuerzaTemporalSpinner);
    }

    private void ActualizaSeguridadCaracteristicas() {
        for (int i = 0; i < listaTemporales.size(); i++) {
            JSpinner js = listaTemporales.get(i);
            if (Personaje.getInstance().lock) {
                js.setEnabled(false);
            } else {
                js.setEnabled(true);
            }
        }

        for (int i = 0; i < listaPotenciales.size(); i++) {
            JSpinner js = listaPotenciales.get(i);
            if (Personaje.getInstance().lock) {
                js.setEnabled(false);
            } else {
                js.setEnabled(true);
            }
        }

        if (Personaje.getInstance().lock) {
            AparienciaTextField.setEnabled(false);
        } else {
            AparienciaTextField.setEnabled(true);
        }
    }

    /**
     * General un listado de los contenedores de las caracteristicas
     * potenciales.
     */
    private void GeneraListadoPotenciales() {
        listaPotenciales = new ArrayList<>();
        listaPotenciales.add(AgilidadPotencialSpinner);
        listaPotenciales.add(ConstitucionPotencialSpinner);
        listaPotenciales.add(MemoriaPotencialSpinner);
        listaPotenciales.add(RazonPotencialSpinner);
        listaPotenciales.add(AutodisciplinaPotencialSpinner);
        listaPotenciales.add(EmpatiaPotencialSpinner);
        listaPotenciales.add(IntuicionPotencialSpinner);
        listaPotenciales.add(PresenciaPotencialSpinner);
        listaPotenciales.add(RapidezPotencialSpinner);
        listaPotenciales.add(FuerzaPotencialSpinner);
    }

    /**
     * General un listado de los contenedores del valor bÃ¡sico de
     * caracteristicas.
     */
    private void GeneraListadoValorBasico() {
        listaBasica = new ArrayList<>();
        listaBasica.add(AgilidadBasicaTextField);
        listaBasica.add(ConstitucionBasicaTextField);
        listaBasica.add(MemoriaBasicaTextField);
        listaBasica.add(RazonBasicaTextField);
        listaBasica.add(AutodisciplinaBasicaTextField);
        listaBasica.add(EmpatiaBasicaTextField);
        listaBasica.add(IntuicionBasicaTextField);
        listaBasica.add(PresenciaBasicaTextField);
        listaBasica.add(RapidezBasicaTextField);
        listaBasica.add(FuerzaBasicaTextField);
    }

    /**
     * General un listado de los contenedores de las bonificaciones por raza.
     */
    private void GeneraListadoRaza() {
        listaRaza = new ArrayList<>();
        listaRaza.add(AgilidadRazaTextField);
        listaRaza.add(ConstitucionRazaTextField);
        listaRaza.add(MemoriaRazaTextField);
        listaRaza.add(RazonRazaTextField);
        listaRaza.add(AutodisciplinaRazaTextField);
        listaRaza.add(EmpatiaRazaTextField);
        listaRaza.add(IntuicionRazaTextField);
        listaRaza.add(PresenciaRazaTextField);
        listaRaza.add(RapidezRazaTextField);
        listaRaza.add(FuerzaRazaTextField);
    }

    /**
     * General un listado de los contenedores del total de caracteristicas.
     */
    private void GeneraListadoTotal() {
        listaTotal = new ArrayList<>();
        listaTotal.add(AgilidadTotalTextField);
        listaTotal.add(ConstitucionTotalTextField);
        listaTotal.add(MemoriaTotalTextField);
        listaTotal.add(RazonTotalTextField);
        listaTotal.add(AutodisciplinaTotalTextField);
        listaTotal.add(EmpatiaTotalTextField);
        listaTotal.add(IntuicionTotalTextField);
        listaTotal.add(PresenciaTotalTextField);
        listaTotal.add(RapidezTotalTextField);
        listaTotal.add(FuerzaTotalTextField);
    }

    /**
     * Actualiza el valor de la caracteristica temporal adecuada.
     *
     * @param index
     */
    public void ActualizaCaracteristicaTemporal(int index) {
        JSpinner spin = listaTemporales.get(index);
        Caracteristica car = Personaje.getInstance().caracteristicas.Get(index);

        if ((Integer) spin.getValue() < 90
                && Personaje.getInstance().EsCaracteristicasPrincipal(car)) {
            spin.setValue(90);
        } else {
            if ((Integer) spin.getValue() < 1) {
                spin.setValue(1);
            } else {
                if ((Integer) spin.getValue() > 102) {
                    spin.setValue(102);
                }
            }
        }

        //Actualizamos la caracteristica con un valor correcto.
        car.CambiarPuntosTemporal((Integer) (spin.getValue()));
        JTextField totalBasico = listaBasica.get(index);
        totalBasico.setText(car.ObtenerValorTemporal() + "");
        JTextField totalCaracteristica = listaTotal.get(index);
        totalCaracteristica.setText(car.Total() + "");

        //Obtenemos las sucesivas subidas de nivel.
        car.CambiarPuntosNextTemporal((Integer) (spin.getValue()));
        car.CalcularProximoAumentoCaracteristica();

        //Actualizamos las potenciales con un valor también correcto.
        JSpinner potencial = listaPotenciales.get(index);
        if ((Integer) potencial.getValue() < (Integer) spin.getValue()) {
            potencial.setValue(spin.getValue());
        }
    }

    /**
     * Actualiza el valor de la caracteristica potencial adecuada.
     *
     * @param index
     */
    public void ActualizaCaracteristicaPotencial(int index) {
        JSpinner spin = listaPotenciales.get(index);
        Caracteristica car = Personaje.getInstance().caracteristicas.Get(index);
        if ((Integer) spin.getValue() < 90
                && Personaje.getInstance().EsCaracteristicasPrincipal(car)) {
            spin.setValue(90);
        } else {
            if ((Integer) spin.getValue() < 20) {
                spin.setValue(20);
            } else {
                if ((Integer) spin.getValue() > 102) {
                    spin.setValue(102);
                }
            }
        }
        //Actualizamos la caracteristica con un valor correcto.
        car.CrearPuntosPotencial((Integer) (spin.getValue()));

        //Actualizamos las temporales con un valor también correcto.
        JSpinner temporal = listaTemporales.get(index);
        if ((Integer) temporal.getValue() > (Integer) spin.getValue()) {
            temporal.setValue(spin.getValue());
        }
        CambiarApariencia(true);
    }

    private void CambiarApariencia(boolean update) {
        int ap;
        try {
            ap = Integer.parseInt(AparienciaTextField.getText());
        } catch (NumberFormatException nfw) {
            ap = Personaje.getInstance().caracteristicas.DevolverApariencia();
        }

        Personaje.getInstance().caracteristicas.InsertarApariencia(ap);
        if (update) {
            AparienciaTextField.setText(Personaje.getInstance().caracteristicas.DevolverApariencia() + "");
            BonusAparienciaTextField.setText(Personaje.getInstance().caracteristicas.DevolverTotalApariencia() + "");
        }
    }

    /**
     * Refresca el tab de caracteristicas con nuevos valores.
     */
    public void RefrescaCaracteristicas() {
        int ap = Personaje.getInstance().caracteristicas.DevolverApariencia();
        ActualizarPotenciales();
        ActualizaTemporales();
        ActualizarValorBasico();
        ActualizarBonificacionRaza();
        ActualizarTotal();
        Personaje.getInstance().caracteristicas.InsertarApariencia(ap);
        ActualizarApariencia();
    }

    /**
     * Muestra el valor potencial de las caracteristicas.
     */
    public void ActualizarPotenciales() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JSpinner spin = listaPotenciales.get(i);
            spin.setValue(car.ObtenerPuntosPotencial());
        }
    }

    public void ActualizaTemporales() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JSpinner spin = listaTemporales.get(i);
            spin.setValue(car.ObtenerPuntosTemporal());
        }
    }

    public void ActualizarApariencia() {
        AparienciaTextField.setText(Personaje.getInstance().caracteristicas.DevolverTotalApariencia() + "");
    }

    public int DevolverApariencia() {
        return Integer.parseInt(AparienciaTextField.getText());
    }

    /**
     * Muestra el total de las caracteristicas.
     */
    public void ActualizarValorBasico() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JTextField textFieldACambiar = listaBasica.get(i);
            textFieldACambiar.setText(car.ObtenerValorTemporal() + "");
        }
        ActualizarTotal();
    }

    /**
     * Muestra la bonificación por raza.
     */
    private void ActualizarBonificacionRaza() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JTextField textFieldACambiar = listaRaza.get(i);
            textFieldACambiar.setText(car.ObtenerValorRaza() + "");
        }
        ActualizarTotal();
    }

    /**
     * Muestra el total de las caracteristicas.
     */
    public void ActualizarTotal() {
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            Caracteristica car = Personaje.getInstance().caracteristicas.Get(i);
            JTextField textFieldACambiar = listaTotal.get(i);
            textFieldACambiar.setText(car.Total() + "");
        }
    }

    /**
     * ********************************************************************
     *
     * LOGICA HABILIDADES
     *
     *********************************************************************
     */
    /**
     * Inicial la interfaz gráfica relacionada con las características.
     */
    public void IniciaHabilidades() {
        ActualizarCategoriasComboBox();
        ActualizarHabilidadesComboBox();
        ActualizarCategoriaSeleccionada();
        ActualizarHabilidadSeleccionada();
    }

    private Categoria DevolverCategoriaSeleccionada() {
        try {
            return Personaje.getInstance().DevolverCategoriaDeNombre(CategoriasComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException | NullPointerException aiobe) {
            return null;
        }
    }

    private Habilidad DevolverHabilidadSeleccionada() {
        Categoria cat = DevolverCategoriaSeleccionada();
        try {
            return cat.DevolverHabilidadDeNombre(HabilidadesComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
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

    public void ActualizarCategoriaSeleccionada() {
        try {
            Categoria cat = DevolverCategoriaSeleccionada();
            RangosCategoriaSpinner.setValue((Integer) cat.DevolverRangos());
            ActualizarHabilidadesComboBox();
            if (cat.EsIncrementable()) {
                RangosCategoriaSpinner.setValue(cat.DevolverRangos());
                RangosCategoriaSpinner.setEnabled(true);
            } else {
                RangosCategoriaSpinner.setValue(0);
                RangosCategoriaSpinner.setEnabled(false);
            }
        } catch (NullPointerException npe) {
        }
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
        try {
            HabilidadesComboBox.setSelectedItem(nombreSeleccionado);
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarHabilidadSeleccionada() {
        try {
            Habilidad hab = DevolverHabilidadSeleccionada();
            RangosHabilidadSpinner.setValue((Integer) hab.DevolverRangos());
            if (hab.EsComun()) {
                ComunRadioButton.setSelected(true);
            }
            if (hab.EsProfesional()) {
                ProfesionalRadioButton.setSelected(true);
            }
            if (hab.EsRestringida() && !hab.EsGeneralizada()) {
                RestringidaRadioButton.setSelected(true);
            }
            if (!hab.EsComun() && !hab.EsProfesional() && (!hab.EsRestringida() || hab.EsGeneralizada())) {
                NormalRadioButton.setSelected(true);
            }

            RangosHabilidadSpinner.setValue(hab.DevolverRangos());
            GeneralCheckBox.setSelected(hab.EsGeneralizada());
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarRangosCategoria() {
        Categoria cat = DevolverCategoriaSeleccionada();
        if ((Integer) RangosCategoriaSpinner.getValue() < cat.rangosAdiestramiento + cat.rangosCultura) {
            RangosCategoriaSpinner.setValue((Integer) cat.rangosAdiestramiento + cat.rangosCultura);
        }
        cat.rangosInsertados = (Integer) RangosCategoriaSpinner.getValue() - cat.rangosAdiestramiento - cat.rangosCultura;
        if (cat.rangosInsertados > 0) {
            cat.nuevosRangos = 0;
            ActualizarTextoHabilidades();
            GastarTodosPuntosDesarrollo();
        }
    }

    public void ActualizarRangosHabilidad() {
        Habilidad hab = DevolverHabilidadSeleccionada();
        if ((Integer) RangosHabilidadSpinner.getValue() < hab.rangosAdiestramiento + hab.rangosAficiones + hab.rangosCultura + hab.DevolverRangosCulturaHechizos()) {
            RangosHabilidadSpinner.setValue((Integer) hab.rangosAdiestramiento + hab.rangosAficiones + hab.rangosCultura + hab.DevolverRangosCulturaHechizos());
        }

        hab.rangosInsertados = (Integer) RangosHabilidadSpinner.getValue() - hab.rangosAdiestramiento - hab.rangosAficiones - hab.rangosCultura - hab.DevolverRangosCulturaHechizos();
        if (hab.rangosInsertados > 0) {
            hab.rangos = 0;
            hab.nuevosRangos = 0;
            ActualizarTextoHabilidades();
            GastarTodosPuntosDesarrollo();
            try {
                ActualizarHabilidadesComboBox(DevolverHabilidadSeleccionada().DevolverNombre());
            } catch (NullPointerException npe) {
            }
        }
    }

    public void ActualizarTextoHabilidades() {
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

    public void TipoHabilidad() {
        Habilidad hab = DevolverHabilidadSeleccionada();
        if (NormalRadioButton.isSelected()) {
            hab.HacerNormal();
        }
        if (ComunRadioButton.isSelected()) {
            hab.HacerComunCultura();
            GeneralCheckBox.setSelected(false);
            hab.QuitarGeneralizada();
        }
        if (ProfesionalRadioButton.isSelected()) {
            hab.HacerProfesional();
            GeneralCheckBox.setSelected(false);
            hab.QuitarGeneralizada();
        }
        if (RestringidaRadioButton.isSelected()) {
            hab.HacerRestringida();
            GeneralCheckBox.setSelected(false);
            hab.QuitarGeneralizada();
        }
        ActualizarRangosHabilidad();
        ActualizarTextoHabilidades();
    }

    public void GastarTodosPuntosDesarrollo() {
        Personaje.getInstance().puntosDesarrolloAnterioresNiveles = Personaje.getInstance().PuntosDesarrolloGastadosEnAdiestramientos() + Personaje.getInstance().PuntosDesarrolloGastadosEnOtros() + Personaje.getInstance().PuntosDesarrolloGastadosEnHabilidadesYCategorias();
        Personaje.getInstance().puntosDesarrolloGastadosAnterioresNiveles = Personaje.getInstance().CalcularPuntosDesarrollo();
        Personaje.getInstance().puntosDesarrolloNivel = Personaje.getInstance().CalcularPuntosDesarrollo();
    }

    public void HacerGeneralHabilidad() {
        Habilidad hab = DevolverHabilidadSeleccionada();
        if (GeneralCheckBox.isSelected()) {
            NormalRadioButton.setSelected(true);
            if (!hab.EsRestringida() && !hab.especializada) {
                hab.HacerGeneralizada();
                EspecializadaCheckBox.setSelected(false);
                MotivoEspecializacionTextField.setText("");
                hab.NoEsProfesional();
                hab.NoEsComunProfesion();
                hab.rangos = ((Integer) RangosHabilidadSpinner.getValue() - hab.nuevosRangos / 2 - hab.rangosAdiestramiento - hab.rangosAficiones - hab.rangosCultura - hab.DevolverRangosCulturaHechizos()) * 2;
            } else {
                MostrarError.showErrorMessage("No puedes generalizar una habilidad que contiene especializaciones o es restringida.", "Habilidad",
                        JOptionPane.WARNING_MESSAGE);
                hab.QuitarGeneralizada();
                RestringidaRadioButton.setSelected(true);
            }
            GeneralCheckBox.setSelected(hab.EsGeneralizada());
        } else {
            hab.QuitarGeneralizada();
        }
        Refrescar();
    }

    public void HacerEspecializadaHabilidad() {
        Habilidad hab = DevolverHabilidadSeleccionada();
        if (hab.rangosEspecializacionAntiguosNiveles > 0) {
            EspecializadaCheckBox.setSelected(true);
        }
        if (!hab.EsGeneralizada()) {
            if (hab.DevolverRangos() == 0) {
                RangosHabilidadSpinner.setValue(1);
            }
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

    /**
     * ********************************************************************
     *
     * TALENTOS
     *
     *********************************************************************
     */
    /**
     * Rellena los talentos con los valores adecuados.
     */
    public void RefrescarTalentos() {
        ActualizarTalentosComboBox();
        ActualizarTalentoSeleccionado();
        MostrarTalentosCogidos();
    }

    private void MostrarTalentosCogidos() {
        String texto = "";
        for (int i = 0; i < Personaje.getInstance().talentos.size(); i++) {
            Talento talento = Personaje.getInstance().talentos.get(i);
            texto += talento.nombre + ": " + talento.listadoCategorias + " ("
                    + talento.Descripcion() + ").\n\n";
        }
        TalentosCogidosTextArea.setText(texto);
    }

    public void ActualizarTalentoSeleccionado() {
        try {
            Talento talento = Esher.talentos.DevolverTalento(TalentosComboBox.getSelectedItem().toString());
            CosteTextField.setText(talento.coste + "");
            TipoTextField.setText(talento.clasificacion);
            DecripcionTextArea.setText(talento.listadoCategorias + "\n------\n" + talento.Descripcion());
            if (Personaje.getInstance().DevolverTalento(talento.nombre) != null) {
                SeleccionarCheckBox.setSelected(true);
            } else {
                SeleccionarCheckBox.setSelected(false);
            }
        } catch (NullPointerException npe) {
        }
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

    /**
     * ********************************************************************
     *
     * HISTORIAL
     *
     *********************************************************************
     */
    /**
     * Prepara la interfaz grafica
     */
    public void IniciarHistorial() {
        ActualizarCategoriasHistorialComboBox();
        ActualizarHabilidadesHistorialComboBox();
        ActualizarCategoriaCheckBox();
        ActualizarHabilidadCheckBox();
        ActualizarPuntosHistorial();
    }

    private void ActualizarPuntosHistorial() {
        PuntosHistorialTextField.setText((Personaje.getInstance().DevolverPuntosHistorialGastados()) + "");
    }

    private void ActualizarCategoriasHistorialComboBox() {
        CategoriasHistorialComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            if (cat.MereceLaPenaListar()) {
                CategoriasHistorialComboBox.addItem(cat.DevolverNombre());
            }
        }
    }

    public void ActualizarHabilidadesHistorialComboBox() {
        HabilidadesHistorialComboBox.removeAllItems();
        Categoria cat = DevolverCategoriaHistorialSeleccionada();
        try {
            cat.OrdenarHabilidades();
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                if (hab.MereceLaPenaListar()) {
                    HabilidadesHistorialComboBox.addItem(hab.DevolverNombre());
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    public Categoria DevolverCategoriaHistorialSeleccionada() {
        try {
            return Personaje.getInstance().DevolverCategoriaDeNombre(CategoriasHistorialComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    public Habilidad DevolverHabilidadHistorialSeleccionada() {
        Categoria cat = DevolverCategoriaHistorialSeleccionada();
        try {
            return cat.DevolverHabilidadDeNombre(HabilidadesHistorialComboBox.getSelectedItem().toString());
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return null;
        }
    }

    public void ActualizarCategoriaCheckBox() {
        try {
            CategoriaCheckBox.setSelected(DevolverCategoriaHistorialSeleccionada().historial);
        } catch (NullPointerException npe) {
        }
    }

    public void ActualizarHabilidadCheckBox() {
        try {
            HabilidadCheckBox.setSelected(DevolverHabilidadHistorialSeleccionada().historial);
        } catch (NullPointerException npe) {
        }
    }

    public void GastarPuntoHistorialEnCategoria() {
        try {
            DevolverCategoriaHistorialSeleccionada().historial = CategoriaCheckBox.isSelected();
        } catch (NullPointerException npe) {
            MostrarError.showErrorMessage("Punto de historial no recogido.", "Insertar personajes");
        }
        ActualizarPuntosHistorial();
    }

    public void GastarPuntoHistorialEnHabilidad() {
        try {
            DevolverHabilidadHistorialSeleccionada().historial = HabilidadCheckBox.isSelected();
        } catch (NullPointerException npe) {
            MostrarError.showErrorMessage("Punto de historial no recogido.", "Insertar Personajes");
        }

        ActualizarPuntosHistorial();
    }

    public void BonusCategoriaInRange() {
        Categoria cat = DevolverCategoriaHistorialSeleccionada();
        cat.ModificaBonusEspecial(TAG_BONUS, (Integer) CategoriaBonusOtroSpinner.getValue());
    }

    public void BonusHabilidadInRange() {
        Habilidad hab = DevolverHabilidadHistorialSeleccionada();
        hab.ModificaBonusEspecial(TAG_BONUS, (Integer) HabilidadBonusOtroSpinner.getValue());
    }

    /**
     * ********************************************************************
     *
     * OBJETOS
     *
     *********************************************************************
     */
    /**
     * Prepara la interfaz de objetos mágicos.
     */
    public void IniciarObjetos() {
        ActualizarCategoriasObjetosComboBox();
        ActivarObjetosMagicos();
        ActualizarListadoObjetos();
    }

    private void ActualizarCategoriasObjetosComboBox() {
        CategoriasObjetoComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            CategoriasObjetoComboBox.addItem(cat.DevolverNombre());
        }
        ActualizarHabilidadesObjetosComboBox();
    }

    public void ActualizarHabilidadesObjetosComboBox() {
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
            return Personaje.getInstance().DevolverCategoriaDeNombre(CategoriasObjetoComboBox.getSelectedItem().toString());
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
        if ((objeto = Personaje.getInstance().DevolverObjetoMagico(NombreObjetoTextField.getText())) != null) {
            BonusCategoriaSpinner.setValue(objeto.DevolverBonusCategoria(DevolverCategoriaObjetoSeleccionada()));
        } else {
            BonusCategoriaSpinner.setValue(0);
        }
    }

    public void ActualizarHabilidadBonusSpinner() {
        ObjetoMagico objeto;
        //if(HabilidadesObjetoComboBox.getItemCount()>0){
        try {
            if ((objeto = Personaje.getInstance().DevolverObjetoMagico(NombreObjetoTextField.getText())) != null) {
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
        Personaje.getInstance().AñadirObjetoMagico(NombreObjetoTextField.getText(),
                DevolverCategoriaObjetoSeleccionada(), Integer.parseInt(BonusCategoriaSpinner.getValue().toString()),
                DevolverHabilidadObjetoSeleccionada(), Integer.parseInt(BonusHabilidadSpinner.getValue().toString()), false);
        String objeto = NombreObjeto();
        ActualizarListadoObjetos();
        SeleccionarObjetoEditado(objeto);
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
        if (Personaje.getInstance().DevolverObjetoMagico(NombreObjetoTextField.getText()) == null) {
            BonusCategoriaSpinner.setValue(0);
            BonusHabilidadSpinner.setValue(0);
        }
    }

    public void ActualizarListadoObjetos() {
        ObjetosComboBox.removeAllItems();
        for (int i = 0; i < Personaje.getInstance().objetosMagicos.size(); i++) {
            ObjetoMagico objeto = Personaje.getInstance().objetosMagicos.get(i);
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
        NombreObjetoTextField.setText("");
        IniciarObjetos();
        BonusCategoriaSpinner.setValue(0);
        BonusHabilidadSpinner.setValue(0);
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

    /**
     * ********************************************************************
     *
     * LISTENERS
     *
     *********************************************************************
     */
    /**
     * Añade el evento al spinner.
     */
    public void addAgilidadTemporalSpinnerListener(ChangeListener al) {
        AgilidadTemporalSpinner.addChangeListener(al);
    }

    public void addConstitucionTemporalSpinnerListener(ChangeListener al) {
        ConstitucionTemporalSpinner.addChangeListener(al);
    }

    public void addMemoriaTemporalSpinnerListener(ChangeListener al) {
        MemoriaTemporalSpinner.addChangeListener(al);
    }

    public void addRazonTemporalSpinnerListener(ChangeListener al) {
        RazonTemporalSpinner.addChangeListener(al);
    }

    public void addAutodisciplinaTemporalSpinnerListener(ChangeListener al) {
        AutodisciplinaTemporalSpinner.addChangeListener(al);
    }

    public void addEmpatiaTemporalSpinnerListener(ChangeListener al) {
        EmpatiaTemporalSpinner.addChangeListener(al);
    }

    public void addIntuicionTemporalSpinnerListener(ChangeListener al) {
        IntuicionTemporalSpinner.addChangeListener(al);
    }

    public void addPresenciaTemporalSpinnerListener(ChangeListener al) {
        PresenciaTemporalSpinner.addChangeListener(al);
    }

    public void addRapidezTemporalSpinnerListener(ChangeListener al) {
        RapidezTemporalSpinner.addChangeListener(al);
    }

    public void addFuerzaTemporalSpinnerListener(ChangeListener al) {
        FuerzaTemporalSpinner.addChangeListener(al);
    }

    public void addAgilidadPotencialSpinnerListener(ChangeListener al) {
        AgilidadPotencialSpinner.addChangeListener(al);
    }

    public void addConstitucionPotencialSpinnerListener(ChangeListener al) {
        ConstitucionPotencialSpinner.addChangeListener(al);
    }

    public void addMemoriaPotencialSpinnerListener(ChangeListener al) {
        MemoriaPotencialSpinner.addChangeListener(al);
    }

    public void addRazonPotencialSpinnerListener(ChangeListener al) {
        RazonPotencialSpinner.addChangeListener(al);
    }

    public void addAutodisciplinaPotencialSpinnerListener(ChangeListener al) {
        AutodisciplinaPotencialSpinner.addChangeListener(al);
    }

    public void addEmpatiaPotencialSpinnerListener(ChangeListener al) {
        EmpatiaPotencialSpinner.addChangeListener(al);
    }

    public void addIntuicionPotencialSpinnerListener(ChangeListener al) {
        IntuicionPotencialSpinner.addChangeListener(al);
    }

    public void addPresenciaPotencialSpinnerListener(ChangeListener al) {
        PresenciaPotencialSpinner.addChangeListener(al);
    }

    public void addRapidezPotencialSpinnerListener(ChangeListener al) {
        RapidezPotencialSpinner.addChangeListener(al);
    }

    public void addFuerzaPotencialSpinnerListener(ChangeListener al) {
        FuerzaPotencialSpinner.addChangeListener(al);
    }

    public void addAceptarButtonListener(ActionListener al) {
        CerrarButton.addActionListener(al);
    }

    public void addCategoriasComboBoxListener(ActionListener al) {
        CategoriasComboBox.addActionListener(al);
    }

    public void addHabilidadesComboBoxListener(ActionListener al) {
        HabilidadesComboBox.addActionListener(al);
    }

    public void addRangosCategoriasSpinnerListener(ChangeListener al) {
        RangosCategoriaSpinner.addChangeListener(al);
    }

    public void addRangosHabilidadSpinnerListener(ChangeListener al) {
        RangosHabilidadSpinner.addChangeListener(al);
    }

    public void addRazasComboBoxListener(ActionListener al) {
        RazasComboBox.addActionListener(al);
    }

    public void addProfesionComboBoxListener(ActionListener al) {
        ProfesionesComboBox.addActionListener(al);
    }

    public void addCulturaComboBoxListener(ActionListener al) {
        CulturasComboBox.addActionListener(al);
    }

    public void addReinosComboBoxListener(ActionListener al) {
        ReinosComboBox.addActionListener(al);
    }

    public void addNivelSpinnerListener(ChangeListener al) {
        NivelSpinner.addChangeListener(al);
    }

    public void addBonusCategoriaSpinnerListener(ChangeListener al) {
        CategoriaBonusOtroSpinner.addChangeListener(al);
    }

    public void addBonusHabilidadSpinnerListener(ChangeListener al) {
        HabilidadBonusOtroSpinner.addChangeListener(al);
    }

    public void addAdiestramientosComboBoxListener(ActionListener al) {
        AdiestramientosComboBox.addActionListener(al);
    }

    public void addAñadirAdiestramientoButtonListener(ActionListener al) {
        AñadirButton.addActionListener(al);
    }

    public void addNombreTextFieldListener(ActionListener al) {
        NombreTextField.addActionListener(al);
    }

    public void addSubirArmaListener(ActionListener al) {
        SubirArmaButton.addActionListener(al);
    }

    public void addBajarArmaListener(ActionListener al) {
        BajarArmaButton.addActionListener(al);
    }

    public void addTipoHabilidadListener(ActionListener al) {
        NormalRadioButton.addActionListener(al);
        ComunRadioButton.addActionListener(al);
        ProfesionalRadioButton.addActionListener(al);
        RestringidaRadioButton.addActionListener(al);
    }

    public void addSexoListener(ActionListener al) {
        MujerRadioButton.addActionListener(al);
        VaronRadioButton.addActionListener(al);
    }

    public void addCategoriaCheckBoxListener(ActionListener al) {
        CategoriaCheckBox.addActionListener(al);
    }

    public void addHabilidadCheckBoxListener(ActionListener al) {
        HabilidadCheckBox.addActionListener(al);
    }

    public void addCategoriasHistorialComboBoxListener(ActionListener al) {
        CategoriasHistorialComboBox.addActionListener(al);
    }

    public void addHabilidadesHistorialComboBoxListener(ActionListener al) {
        HabilidadesHistorialComboBox.addActionListener(al);
    }

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

    public void addObjetosComboBoxListener(ActionListener al) {
        ObjetosComboBox.addActionListener(al);
    }

    public void addSeleccionarTalentoCheckBoxListener(ActionListener al) {
        SeleccionarCheckBox.addActionListener(al);
    }

    public void addInsertarTalentoComboBoxListener(ActionListener al) {
        TalentosComboBox.addActionListener(al);
    }

    public void addGeneralCheckBoxListener(ActionListener al) {
        GeneralCheckBox.addActionListener(al);
    }

    public void addEspecializadaCheckBoxListener(ActionListener al) {
        EspecializadaCheckBox.addActionListener(al);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TipoHabilidadGroup = new javax.swing.ButtonGroup();
        SexoButtonGroup = new javax.swing.ButtonGroup();
        CerrarButton = new javax.swing.JButton();
        PjTabbedPane = new javax.swing.JTabbedPane();
        PersonajePanel = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        NombreTextField = new javax.swing.JTextField();
        VaronRadioButton = new javax.swing.JRadioButton();
        MujerRadioButton = new javax.swing.JRadioButton();
        ProfesionesComboBox = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        AdiestramientosComboBox = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        AñadirButton = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        RazasComboBox = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        CulturasComboBox = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        NivelSpinner = new javax.swing.JSpinner();
        jLabel25 = new javax.swing.JLabel();
        ReinosComboBox = new javax.swing.JComboBox();
        AdiestramientosTextField = new javax.swing.JTextField();
        ArmasScrollPane = new javax.swing.JScrollPane();
        ArmasList = new javax.swing.JList();
        SubirArmaButton = new javax.swing.JButton();
        BajarArmaButton = new javax.swing.JButton();
        CaracteristicaPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        AgilidadTemporalSpinner = new javax.swing.JSpinner();
        ConstitucionTemporalSpinner = new javax.swing.JSpinner();
        MemoriaTemporalSpinner = new javax.swing.JSpinner();
        RazonTemporalSpinner = new javax.swing.JSpinner();
        AutodisciplinaTemporalSpinner = new javax.swing.JSpinner();
        EmpatiaTemporalSpinner = new javax.swing.JSpinner();
        IntuicionTemporalSpinner = new javax.swing.JSpinner();
        PresenciaTemporalSpinner = new javax.swing.JSpinner();
        RapidezTemporalSpinner = new javax.swing.JSpinner();
        FuerzaTemporalSpinner = new javax.swing.JSpinner();
        AparienciaTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        AgilidadBasicaTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ConstitucionBasicaTextField = new javax.swing.JTextField();
        MemoriaBasicaTextField = new javax.swing.JTextField();
        RazonBasicaTextField = new javax.swing.JTextField();
        AutodisciplinaBasicaTextField = new javax.swing.JTextField();
        EmpatiaBasicaTextField = new javax.swing.JTextField();
        IntuicionBasicaTextField = new javax.swing.JTextField();
        PresenciaBasicaTextField = new javax.swing.JTextField();
        RapidezBasicaTextField = new javax.swing.JTextField();
        FuerzaBasicaTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        AgilidadRazaTextField = new javax.swing.JTextField();
        ConstitucionRazaTextField = new javax.swing.JTextField();
        MemoriaRazaTextField = new javax.swing.JTextField();
        RazonRazaTextField = new javax.swing.JTextField();
        AutodisciplinaRazaTextField = new javax.swing.JTextField();
        EmpatiaRazaTextField = new javax.swing.JTextField();
        IntuicionRazaTextField = new javax.swing.JTextField();
        PresenciaRazaTextField = new javax.swing.JTextField();
        RapidezRazaTextField = new javax.swing.JTextField();
        FuerzaRazaTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        AgilidadTotalTextField = new javax.swing.JTextField();
        ConstitucionTotalTextField = new javax.swing.JTextField();
        MemoriaTotalTextField = new javax.swing.JTextField();
        RazonTotalTextField = new javax.swing.JTextField();
        AutodisciplinaTotalTextField = new javax.swing.JTextField();
        EmpatiaTotalTextField = new javax.swing.JTextField();
        IntuicionTotalTextField = new javax.swing.JTextField();
        PresenciaTotalTextField = new javax.swing.JTextField();
        RapidezTotalTextField = new javax.swing.JTextField();
        FuerzaTotalTextField = new javax.swing.JTextField();
        AgilidadPotencialSpinner = new javax.swing.JSpinner();
        ConstitucionPotencialSpinner = new javax.swing.JSpinner();
        MemoriaPotencialSpinner = new javax.swing.JSpinner();
        RazonPotencialSpinner = new javax.swing.JSpinner();
        AutodisciplinaPotencialSpinner = new javax.swing.JSpinner();
        EmpatiaPotencialSpinner = new javax.swing.JSpinner();
        IntuicionPotencialSpinner = new javax.swing.JSpinner();
        PresenciaPotencialSpinner = new javax.swing.JSpinner();
        RapidezPotencialSpinner = new javax.swing.JSpinner();
        FuerzaPotencialSpinner = new javax.swing.JSpinner();
        BonusAparienciaTextField = new javax.swing.JTextField();
        HabilidadesPanel = new javax.swing.JPanel();
        CategoriasComboBox = new javax.swing.JComboBox();
        HabilidadesComboBox = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        RangosCategoriaSpinner = new javax.swing.JSpinner();
        RangosHabilidadSpinner = new javax.swing.JSpinner();
        NormalRadioButton = new javax.swing.JRadioButton();
        ComunRadioButton = new javax.swing.JRadioButton();
        ProfesionalRadioButton = new javax.swing.JRadioButton();
        RestringidaRadioButton = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ResumenTextArea = new javax.swing.JTextArea();
        GeneralCheckBox = new javax.swing.JCheckBox();
        EspecializadaCheckBox = new javax.swing.JCheckBox();
        MotivoEspecializacionTextField = new javax.swing.JTextField();
        TalentosPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        TalentosComboBox = new javax.swing.JComboBox();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        CosteTextField = new javax.swing.JTextField();
        TipoTextField = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DecripcionTextArea = new javax.swing.JTextArea();
        SeleccionarCheckBox = new javax.swing.JCheckBox();
        jLabel39 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TalentosCogidosTextArea = new javax.swing.JTextArea();
        OtrosPanel = new javax.swing.JPanel();
        HistorialPanel = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        CategoriasHistorialComboBox = new javax.swing.JComboBox();
        CategoriaCheckBox = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        HabilidadesHistorialComboBox = new javax.swing.JComboBox();
        HabilidadCheckBox = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        PuntosHistorialTextField = new javax.swing.JTextField();
        CategoriaBonusOtroSpinner = new javax.swing.JSpinner();
        HabilidadBonusOtroSpinner = new javax.swing.JSpinner();
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
        BorrarObjetoButton = new javax.swing.JButton();
        ObjetosComboBox = new javax.swing.JComboBox();

        setTitle("Libro de Esher - Copiar Personaje Externo");

        CerrarButton.setText("Cerrar");

        PjTabbedPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel19.setText("Nombre:");

        NombreTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NombreTextFieldKeyReleased(evt);
            }
        });

        SexoButtonGroup.add(VaronRadioButton);
        VaronRadioButton.setSelected(true);
        VaronRadioButton.setText("Masculino");

        SexoButtonGroup.add(MujerRadioButton);
        MujerRadioButton.setText("Femenino");

        jLabel20.setText("Profesión:");

        jLabel21.setText("Adiestramiento:");

        AñadirButton.setText("Añadir");

        jLabel22.setText("Raza:");

        jLabel23.setText("Cultura:");

        jLabel24.setText("Nivel:");

        NivelSpinner.setValue(1);

        jLabel25.setText("Reino:");

        AdiestramientosTextField.setEditable(false);

        ArmasList.setModel(armasModel);
        ArmasList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ArmasScrollPane.setViewportView(ArmasList);

        SubirArmaButton.setText("Subir");

        BajarArmaButton.setText("Bajar");

        javax.swing.GroupLayout PersonajePanelLayout = new javax.swing.GroupLayout(PersonajePanel);
        PersonajePanel.setLayout(PersonajePanelLayout);
        PersonajePanelLayout.setHorizontalGroup(
            PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PersonajePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(jLabel19)
                    .addComponent(jLabel25)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PersonajePanelLayout.createSequentialGroup()
                        .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SubirArmaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BajarArmaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ArmasScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PersonajePanelLayout.createSequentialGroup()
                        .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RazasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NombreTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProfesionesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CulturasComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AdiestramientosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReinosComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PersonajePanelLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NivelSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(VaronRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MujerRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AñadirButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(AdiestramientosTextField, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(34, 34, 34))
        );

        PersonajePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AdiestramientosComboBox, CulturasComboBox, NombreTextField, ProfesionesComboBox, RazasComboBox, ReinosComboBox});

        PersonajePanelLayout.setVerticalGroup(
            PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonajePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(NombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(NivelSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel22)
                    .addComponent(RazasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VaronRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel23)
                    .addComponent(CulturasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MujerRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel20)
                    .addComponent(ProfesionesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel25)
                    .addComponent(ReinosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel21)
                    .addComponent(AdiestramientosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AñadirButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AdiestramientosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PersonajePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonajePanelLayout.createSequentialGroup()
                        .addComponent(SubirArmaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addComponent(BajarArmaButton))
                    .addComponent(ArmasScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addContainerGap())
        );

        PersonajePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AdiestramientosComboBox, CulturasComboBox, NombreTextField, ProfesionesComboBox, RazasComboBox, ReinosComboBox});

        PjTabbedPane.addTab("Personaje", PersonajePanel);

        CaracteristicaPanel.setToolTipText("Inserta las caracteristicas del personaje");

        jLabel1.setText("Agilidad");

        jLabel2.setText("Constitución");

        jLabel3.setText("Memoria");

        jLabel4.setText("Razón");

        jLabel5.setText("Autodisciplina");

        jLabel6.setText("Empatía");

        jLabel7.setText("Intuición");

        jLabel8.setText("Presencia");

        jLabel9.setText("Rapidez");

        jLabel10.setText("Fuerza");

        jLabel11.setText("Apariencia");

        AgilidadTemporalSpinner.setValue(20);

        ConstitucionTemporalSpinner.setValue(20);

        MemoriaTemporalSpinner.setValue(20);

        RazonTemporalSpinner.setValue(20);

        AutodisciplinaTemporalSpinner.setValue(20);

        EmpatiaTemporalSpinner.setValue(20);

        IntuicionTemporalSpinner.setValue(20);

        PresenciaTemporalSpinner.setValue(20);

        RapidezTemporalSpinner.setValue(20);

        FuerzaTemporalSpinner.setValue(20);

        AparienciaTextField.setText("0");
        AparienciaTextField.setToolTipText("Apariencia sin contar los bonus de raza.");
        AparienciaTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                AparienciaTextFieldFocusLost(evt);
            }
        });
        AparienciaTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                AparienciaTextFieldKeyReleased(evt);
            }
        });

        jLabel12.setText("Temporal");

        jLabel13.setText("Potencial");

        AgilidadBasicaTextField.setEditable(false);
        AgilidadBasicaTextField.setText("0");
        AgilidadBasicaTextField.setToolTipText("Autocalculadas");

        jLabel14.setText("B. Básica");

        ConstitucionBasicaTextField.setEditable(false);
        ConstitucionBasicaTextField.setText("0");
        ConstitucionBasicaTextField.setToolTipText("Autocalculadas");

        MemoriaBasicaTextField.setEditable(false);
        MemoriaBasicaTextField.setText("0");
        MemoriaBasicaTextField.setToolTipText("Autocalculadas");

        RazonBasicaTextField.setEditable(false);
        RazonBasicaTextField.setText("0");
        RazonBasicaTextField.setToolTipText("Autocalculadas");

        AutodisciplinaBasicaTextField.setEditable(false);
        AutodisciplinaBasicaTextField.setText("0");
        AutodisciplinaBasicaTextField.setToolTipText("Autocalculadas");

        EmpatiaBasicaTextField.setEditable(false);
        EmpatiaBasicaTextField.setText("0");
        EmpatiaBasicaTextField.setToolTipText("Autocalculadas");

        IntuicionBasicaTextField.setEditable(false);
        IntuicionBasicaTextField.setText("0");
        IntuicionBasicaTextField.setToolTipText("Autocalculadas");

        PresenciaBasicaTextField.setEditable(false);
        PresenciaBasicaTextField.setText("0");
        PresenciaBasicaTextField.setToolTipText("Autocalculadas");

        RapidezBasicaTextField.setEditable(false);
        RapidezBasicaTextField.setText("0");
        RapidezBasicaTextField.setToolTipText("Autocalculadas");

        FuerzaBasicaTextField.setEditable(false);
        FuerzaBasicaTextField.setText("0");
        FuerzaBasicaTextField.setToolTipText("Autocalculadas");

        jLabel15.setText("B. Raza");

        AgilidadRazaTextField.setEditable(false);
        AgilidadRazaTextField.setText("0");
        AgilidadRazaTextField.setToolTipText("Autocalculadas");

        ConstitucionRazaTextField.setEditable(false);
        ConstitucionRazaTextField.setText("0");
        ConstitucionRazaTextField.setToolTipText("Autocalculadas");

        MemoriaRazaTextField.setEditable(false);
        MemoriaRazaTextField.setText("0");
        MemoriaRazaTextField.setToolTipText("Autocalculadas");

        RazonRazaTextField.setEditable(false);
        RazonRazaTextField.setText("0");
        RazonRazaTextField.setToolTipText("Autocalculadas");

        AutodisciplinaRazaTextField.setEditable(false);
        AutodisciplinaRazaTextField.setText("0");
        AutodisciplinaRazaTextField.setToolTipText("Autocalculadas");

        EmpatiaRazaTextField.setEditable(false);
        EmpatiaRazaTextField.setText("0");
        EmpatiaRazaTextField.setToolTipText("Autocalculadas");

        IntuicionRazaTextField.setEditable(false);
        IntuicionRazaTextField.setText("0");
        IntuicionRazaTextField.setToolTipText("Autocalculadas");

        PresenciaRazaTextField.setEditable(false);
        PresenciaRazaTextField.setText("0");
        PresenciaRazaTextField.setToolTipText("Autocalculadas");

        RapidezRazaTextField.setEditable(false);
        RapidezRazaTextField.setText("0");
        RapidezRazaTextField.setToolTipText("Autocalculadas");

        FuerzaRazaTextField.setEditable(false);
        FuerzaRazaTextField.setText("0");
        FuerzaRazaTextField.setToolTipText("Autocalculadas");

        jLabel16.setText("Total");

        AgilidadTotalTextField.setEditable(false);
        AgilidadTotalTextField.setText("0");
        AgilidadTotalTextField.setToolTipText("Autocalculadas");

        ConstitucionTotalTextField.setEditable(false);
        ConstitucionTotalTextField.setText("0");
        ConstitucionTotalTextField.setToolTipText("Autocalculadas");

        MemoriaTotalTextField.setEditable(false);
        MemoriaTotalTextField.setText("0");
        MemoriaTotalTextField.setToolTipText("Autocalculadas");

        RazonTotalTextField.setEditable(false);
        RazonTotalTextField.setText("0");
        RazonTotalTextField.setToolTipText("Autocalculadas");

        AutodisciplinaTotalTextField.setEditable(false);
        AutodisciplinaTotalTextField.setText("0");
        AutodisciplinaTotalTextField.setToolTipText("Autocalculadas");

        EmpatiaTotalTextField.setEditable(false);
        EmpatiaTotalTextField.setText("0");
        EmpatiaTotalTextField.setToolTipText("Autocalculadas");

        IntuicionTotalTextField.setEditable(false);
        IntuicionTotalTextField.setText("0");
        IntuicionTotalTextField.setToolTipText("Autocalculadas");

        PresenciaTotalTextField.setEditable(false);
        PresenciaTotalTextField.setText("0");
        PresenciaTotalTextField.setToolTipText("Autocalculadas");

        RapidezTotalTextField.setEditable(false);
        RapidezTotalTextField.setText("0");
        RapidezTotalTextField.setToolTipText("Autocalculadas");

        FuerzaTotalTextField.setEditable(false);
        FuerzaTotalTextField.setText("0");
        FuerzaTotalTextField.setToolTipText("Autocalculadas");

        AgilidadPotencialSpinner.setValue(20);

        ConstitucionPotencialSpinner.setValue(20);

        MemoriaPotencialSpinner.setValue(20);

        RazonPotencialSpinner.setValue(20);

        AutodisciplinaPotencialSpinner.setValue(20);

        EmpatiaPotencialSpinner.setValue(20);

        IntuicionPotencialSpinner.setValue(20);

        PresenciaPotencialSpinner.setValue(20);

        RapidezPotencialSpinner.setValue(20);

        FuerzaPotencialSpinner.setValue(20);

        BonusAparienciaTextField.setEditable(false);
        BonusAparienciaTextField.setToolTipText("Contabilizando el bonus a apariencia por raza");

        javax.swing.GroupLayout CaracteristicaPanelLayout = new javax.swing.GroupLayout(CaracteristicaPanel);
        CaracteristicaPanel.setLayout(CaracteristicaPanelLayout);
        CaracteristicaPanelLayout.setHorizontalGroup(
            CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CaracteristicaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CaracteristicaPanelLayout.createSequentialGroup()
                        .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AparienciaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(IntuicionTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(FuerzaTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(RapidezTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(PresenciaTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EmpatiaTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AutodisciplinaTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(RazonTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(MemoriaTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ConstitucionTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AgilidadTemporalSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(CaracteristicaPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel13))
                    .addGroup(CaracteristicaPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ConstitucionPotencialSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(AgilidadPotencialSpinner)
                            .addComponent(MemoriaPotencialSpinner)
                            .addComponent(RazonPotencialSpinner)
                            .addComponent(AutodisciplinaPotencialSpinner)
                            .addComponent(EmpatiaPotencialSpinner)
                            .addComponent(IntuicionPotencialSpinner)
                            .addComponent(PresenciaPotencialSpinner)
                            .addComponent(RapidezPotencialSpinner)
                            .addComponent(FuerzaPotencialSpinner)
                            .addComponent(BonusAparienciaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CaracteristicaPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CaracteristicaPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(AgilidadBasicaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(ConstitucionBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(MemoriaBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(RazonBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(AutodisciplinaBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(EmpatiaBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(IntuicionBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(PresenciaBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(RapidezBasicaTextField, 0, 0, Short.MAX_VALUE)
                            .addComponent(FuerzaBasicaTextField, 0, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(AgilidadRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ConstitucionRazaTextField)
                            .addComponent(MemoriaRazaTextField)
                            .addComponent(RazonRazaTextField)
                            .addComponent(AutodisciplinaRazaTextField)
                            .addComponent(EmpatiaRazaTextField)
                            .addComponent(IntuicionRazaTextField)
                            .addComponent(PresenciaRazaTextField)
                            .addComponent(RapidezRazaTextField)
                            .addComponent(FuerzaRazaTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(AgilidadTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ConstitucionTotalTextField)
                            .addComponent(MemoriaTotalTextField)
                            .addComponent(RazonTotalTextField)
                            .addComponent(AutodisciplinaTotalTextField)
                            .addComponent(EmpatiaTotalTextField)
                            .addComponent(IntuicionTotalTextField)
                            .addComponent(PresenciaTotalTextField)
                            .addComponent(RapidezTotalTextField)
                            .addComponent(FuerzaTotalTextField))))
                .addContainerGap())
        );

        CaracteristicaPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AgilidadRazaTextField, AgilidadTotalTextField, AutodisciplinaBasicaTextField, AutodisciplinaPotencialSpinner, AutodisciplinaRazaTextField, AutodisciplinaTemporalSpinner, AutodisciplinaTotalTextField, ConstitucionBasicaTextField, ConstitucionPotencialSpinner, ConstitucionRazaTextField, ConstitucionTemporalSpinner, ConstitucionTotalTextField, EmpatiaBasicaTextField, EmpatiaPotencialSpinner, EmpatiaRazaTextField, EmpatiaTemporalSpinner, EmpatiaTotalTextField, FuerzaBasicaTextField, FuerzaPotencialSpinner, FuerzaRazaTextField, FuerzaTemporalSpinner, FuerzaTotalTextField, IntuicionBasicaTextField, IntuicionPotencialSpinner, IntuicionRazaTextField, IntuicionTemporalSpinner, IntuicionTotalTextField, MemoriaBasicaTextField, MemoriaPotencialSpinner, MemoriaRazaTextField, MemoriaTemporalSpinner, MemoriaTotalTextField, PresenciaBasicaTextField, PresenciaPotencialSpinner, PresenciaRazaTextField, PresenciaTemporalSpinner, PresenciaTotalTextField, RapidezBasicaTextField, RapidezPotencialSpinner, RapidezRazaTextField, RapidezTemporalSpinner, RapidezTotalTextField, RazonBasicaTextField, RazonPotencialSpinner, RazonRazaTextField, RazonTemporalSpinner, RazonTotalTextField});

        CaracteristicaPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {AgilidadBasicaTextField, AgilidadPotencialSpinner, AgilidadTemporalSpinner});

        CaracteristicaPanelLayout.setVerticalGroup(
            CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CaracteristicaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(AgilidadTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgilidadTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(ConstitucionTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConstitucionTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(MemoriaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemoriaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(RazonTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RazonTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(AutodisciplinaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AutodisciplinaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(EmpatiaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpatiaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(IntuicionTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntuicionTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(PresenciaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PresenciaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(RapidezTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RapidezTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(FuerzaTemporalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaPotencialSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaBasicaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FuerzaTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CaracteristicaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(AparienciaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BonusAparienciaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        PjTabbedPane.addTab("Caracteristicas", CaracteristicaPanel);

        jLabel17.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel17.setText("Categoría:");

        jLabel18.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        jLabel18.setText("Habilidad:");

        TipoHabilidadGroup.add(NormalRadioButton);
        NormalRadioButton.setSelected(true);
        NormalRadioButton.setText("Normal");

        TipoHabilidadGroup.add(ComunRadioButton);
        ComunRadioButton.setText("Común");

        TipoHabilidadGroup.add(ProfesionalRadioButton);
        ProfesionalRadioButton.setText("Profesional");

        TipoHabilidadGroup.add(RestringidaRadioButton);
        RestringidaRadioButton.setText("Restringida");

        ResumenTextArea.setColumns(20);
        ResumenTextArea.setRows(5);
        jScrollPane1.setViewportView(ResumenTextArea);

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

        javax.swing.GroupLayout HabilidadesPanelLayout = new javax.swing.GroupLayout(HabilidadesPanel);
        HabilidadesPanel.setLayout(HabilidadesPanelLayout);
        HabilidadesPanelLayout.setHorizontalGroup(
            HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                        .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addGap(25, 25, 25)
                                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HabilidadesPanelLayout.createSequentialGroup()
                                        .addComponent(NormalRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ComunRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ProfesionalRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RestringidaRadioButton))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HabilidadesPanelLayout.createSequentialGroup()
                                        .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(CategoriasComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(HabilidadesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(RangosCategoriaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(RangosHabilidadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                                .addComponent(GeneralCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EspecializadaCheckBox)
                                .addGap(18, 18, 18)
                                .addComponent(MotivoEspecializacionTextField)))
                        .addGap(15, 15, 15))))
        );

        HabilidadesPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RangosCategoriaSpinner, RangosHabilidadSpinner});

        HabilidadesPanelLayout.setVerticalGroup(
            HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HabilidadesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(RangosCategoriaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoriasComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(HabilidadesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RangosHabilidadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NormalRadioButton)
                    .addComponent(RestringidaRadioButton)
                    .addComponent(ProfesionalRadioButton)
                    .addComponent(ComunRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HabilidadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(GeneralCheckBox)
                    .addComponent(EspecializadaCheckBox)
                    .addComponent(MotivoEspecializacionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        PjTabbedPane.addTab("Habilidades", HabilidadesPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel36.setText("Coste:");

        jLabel37.setText("Tipo:");

        CosteTextField.setEditable(false);
        CosteTextField.setToolTipText("Coste en puntos de talentos.");

        TipoTextField.setEditable(false);
        TipoTextField.setToolTipText("Clasificación según su potencia.");

        jLabel38.setText("Descripción:");

        DecripcionTextArea.setColumns(20);
        DecripcionTextArea.setEditable(false);
        DecripcionTextArea.setLineWrap(true);
        DecripcionTextArea.setRows(5);
        jScrollPane2.setViewportView(DecripcionTextArea);

        SeleccionarCheckBox.setText("Seleccionar");

        jLabel39.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel39.setText("Talento:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(SeleccionarCheckBox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TalentosComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 225, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(CosteTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(TipoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                        .addGap(9, 9, 9))
                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TalentosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CosteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TipoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SeleccionarCheckBox)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel41.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel41.setText("Talentos escogidos:");

        TalentosCogidosTextArea.setColumns(20);
        TalentosCogidosTextArea.setEditable(false);
        TalentosCogidosTextArea.setLineWrap(true);
        TalentosCogidosTextArea.setRows(5);
        jScrollPane3.setViewportView(TalentosCogidosTextArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(jLabel41))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout TalentosPanelLayout = new javax.swing.GroupLayout(TalentosPanel);
        TalentosPanel.setLayout(TalentosPanelLayout);
        TalentosPanelLayout.setHorizontalGroup(
            TalentosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TalentosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        TalentosPanelLayout.setVerticalGroup(
            TalentosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TalentosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TalentosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        PjTabbedPane.addTab("Talentos", TalentosPanel);

        HistorialPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel26.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 14)); // NOI18N
        jLabel26.setText("Bonus:");

        jLabel28.setText("Categorias:");

        CategoriasHistorialComboBox.setMaximumSize(new java.awt.Dimension(10000, 32767));

        CategoriaCheckBox.setText("Historial");

        jLabel29.setText("Habilidades:");

        HabilidadesHistorialComboBox.setMaximumSize(new java.awt.Dimension(10000, 32767));

        HabilidadCheckBox.setText("Historial");

        jLabel30.setText("Puntos Asignados:");

        PuntosHistorialTextField.setEditable(false);

        javax.swing.GroupLayout HistorialPanelLayout = new javax.swing.GroupLayout(HistorialPanel);
        HistorialPanel.setLayout(HistorialPanelLayout);
        HistorialPanelLayout.setHorizontalGroup(
            HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistorialPanelLayout.createSequentialGroup()
                .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HistorialPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HistorialPanelLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                                .addComponent(jLabel30))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HistorialPanelLayout.createSequentialGroup()
                                .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(HabilidadesHistorialComboBox, 0, 265, Short.MAX_VALUE)
                                    .addComponent(CategoriasHistorialComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 265, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(HabilidadBonusOtroSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                    .addComponent(CategoriaBonusOtroSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                            .addComponent(PuntosHistorialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CategoriaCheckBox)
                            .addComponent(HabilidadCheckBox)))
                    .addGroup(HistorialPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel29))
                    .addGroup(HistorialPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel28)))
                .addContainerGap())
        );
        HistorialPanelLayout.setVerticalGroup(
            HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistorialPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addGroup(HistorialPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(PuntosHistorialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CategoriasHistorialComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoriaCheckBox)
                    .addComponent(CategoriaBonusOtroSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HistorialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(HabilidadesHistorialComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HabilidadCheckBox)
                    .addComponent(HabilidadBonusOtroSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

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

        BorrarObjetoButton.setText("Borrar");

        javax.swing.GroupLayout ObjetosPanelLayout = new javax.swing.GroupLayout(ObjetosPanel);
        ObjetosPanel.setLayout(ObjetosPanelLayout);
        ObjetosPanelLayout.setHorizontalGroup(
            ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ObjetosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BorrarObjetoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ObjetosPanelLayout.createSequentialGroup()
                        .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NombreObjetoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jLabel31)
                            .addComponent(jLabel27)
                            .addComponent(ObjetosComboBox, 0, 149, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(HabilidadesObjetoComboBox, 0, 219, Short.MAX_VALUE)
                            .addComponent(CategoriasObjetoComboBox, 0, 219, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ObjetosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BonusHabilidadSpinner)
                            .addComponent(BonusCategoriaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addGap(18, 18, 18)
                .addComponent(BorrarObjetoButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout OtrosPanelLayout = new javax.swing.GroupLayout(OtrosPanel);
        OtrosPanel.setLayout(OtrosPanelLayout);
        OtrosPanelLayout.setHorizontalGroup(
            OtrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtrosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OtrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ObjetosPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HistorialPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        OtrosPanelLayout.setVerticalGroup(
            OtrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtrosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HistorialPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ObjetosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        PjTabbedPane.addTab("Otros", OtrosPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CerrarButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PjTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PjTabbedPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CerrarButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void NombreObjetoTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NombreObjetoTextFieldKeyReleased
        ActivarObjetosMagicos();
        LimpiarObjetoAntiguo();
    }//GEN-LAST:event_NombreObjetoTextFieldKeyReleased

    private void NombreTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NombreTextFieldKeyReleased
        InsertaNombre();
    }//GEN-LAST:event_NombreTextFieldKeyReleased

    private void MotivoEspecializacionTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MotivoEspecializacionTextFieldFocusGained
        antiguaEspecializacion = MotivoEspecializacionTextField.getText();
    }//GEN-LAST:event_MotivoEspecializacionTextFieldFocusGained

    private void MotivoEspecializacionTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MotivoEspecializacionTextFieldFocusLost
        if (EspecializadaCheckBox.isSelected()) {
            HacerEspecializadaHabilidad();
        }
    }//GEN-LAST:event_MotivoEspecializacionTextFieldFocusLost

    private void AparienciaTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AparienciaTextFieldKeyReleased
        CambiarApariencia(false);
    }//GEN-LAST:event_AparienciaTextFieldKeyReleased

    private void AparienciaTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AparienciaTextFieldFocusLost
        CambiarApariencia(true);
    }//GEN-LAST:event_AparienciaTextFieldFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JComboBox AdiestramientosComboBox;
    private javax.swing.JTextField AdiestramientosTextField;
    private javax.swing.JTextField AgilidadBasicaTextField;
    private javax.swing.JSpinner AgilidadPotencialSpinner;
    private javax.swing.JTextField AgilidadRazaTextField;
    private javax.swing.JSpinner AgilidadTemporalSpinner;
    private javax.swing.JTextField AgilidadTotalTextField;
    private javax.swing.JTextField AparienciaTextField;
    private javax.swing.JList ArmasList;
    private javax.swing.JScrollPane ArmasScrollPane;
    private javax.swing.JTextField AutodisciplinaBasicaTextField;
    private javax.swing.JSpinner AutodisciplinaPotencialSpinner;
    private javax.swing.JTextField AutodisciplinaRazaTextField;
    private javax.swing.JSpinner AutodisciplinaTemporalSpinner;
    private javax.swing.JTextField AutodisciplinaTotalTextField;
    private javax.swing.JButton AñadirButton;
    private javax.swing.JButton BajarArmaButton;
    private javax.swing.JTextField BonusAparienciaTextField;
    private javax.swing.JSpinner BonusCategoriaSpinner;
    private javax.swing.JSpinner BonusHabilidadSpinner;
    private javax.swing.JButton BorrarObjetoButton;
    private javax.swing.JPanel CaracteristicaPanel;
    private javax.swing.JSpinner CategoriaBonusOtroSpinner;
    private javax.swing.JCheckBox CategoriaCheckBox;
    private javax.swing.JComboBox CategoriasComboBox;
    private javax.swing.JComboBox CategoriasHistorialComboBox;
    private javax.swing.JComboBox CategoriasObjetoComboBox;
    private javax.swing.JButton CerrarButton;
    private javax.swing.JRadioButton ComunRadioButton;
    private javax.swing.JTextField ConstitucionBasicaTextField;
    private javax.swing.JSpinner ConstitucionPotencialSpinner;
    private javax.swing.JTextField ConstitucionRazaTextField;
    private javax.swing.JSpinner ConstitucionTemporalSpinner;
    private javax.swing.JTextField ConstitucionTotalTextField;
    private javax.swing.JTextField CosteTextField;
    private javax.swing.JComboBox CulturasComboBox;
    private javax.swing.JTextArea DecripcionTextArea;
    private javax.swing.JTextField EmpatiaBasicaTextField;
    private javax.swing.JSpinner EmpatiaPotencialSpinner;
    private javax.swing.JTextField EmpatiaRazaTextField;
    private javax.swing.JSpinner EmpatiaTemporalSpinner;
    private javax.swing.JTextField EmpatiaTotalTextField;
    private javax.swing.JCheckBox EspecializadaCheckBox;
    private javax.swing.JTextField FuerzaBasicaTextField;
    private javax.swing.JSpinner FuerzaPotencialSpinner;
    private javax.swing.JTextField FuerzaRazaTextField;
    private javax.swing.JSpinner FuerzaTemporalSpinner;
    private javax.swing.JTextField FuerzaTotalTextField;
    private javax.swing.JCheckBox GeneralCheckBox;
    private javax.swing.JSpinner HabilidadBonusOtroSpinner;
    private javax.swing.JCheckBox HabilidadCheckBox;
    private javax.swing.JComboBox HabilidadesComboBox;
    private javax.swing.JComboBox HabilidadesHistorialComboBox;
    private javax.swing.JComboBox HabilidadesObjetoComboBox;
    private javax.swing.JPanel HabilidadesPanel;
    private javax.swing.JPanel HistorialPanel;
    private javax.swing.JTextField IntuicionBasicaTextField;
    private javax.swing.JSpinner IntuicionPotencialSpinner;
    private javax.swing.JTextField IntuicionRazaTextField;
    private javax.swing.JSpinner IntuicionTemporalSpinner;
    private javax.swing.JTextField IntuicionTotalTextField;
    private javax.swing.JTextField MemoriaBasicaTextField;
    private javax.swing.JSpinner MemoriaPotencialSpinner;
    private javax.swing.JTextField MemoriaRazaTextField;
    private javax.swing.JSpinner MemoriaTemporalSpinner;
    private javax.swing.JTextField MemoriaTotalTextField;
    private javax.swing.JTextField MotivoEspecializacionTextField;
    private javax.swing.JRadioButton MujerRadioButton;
    private javax.swing.JSpinner NivelSpinner;
    private javax.swing.JTextField NombreObjetoTextField;
    private javax.swing.JTextField NombreTextField;
    private javax.swing.JRadioButton NormalRadioButton;
    private javax.swing.JComboBox ObjetosComboBox;
    private javax.swing.JPanel ObjetosPanel;
    private javax.swing.JPanel OtrosPanel;
    private javax.swing.JPanel PersonajePanel;
    private javax.swing.JTabbedPane PjTabbedPane;
    private javax.swing.JTextField PresenciaBasicaTextField;
    private javax.swing.JSpinner PresenciaPotencialSpinner;
    private javax.swing.JTextField PresenciaRazaTextField;
    private javax.swing.JSpinner PresenciaTemporalSpinner;
    private javax.swing.JTextField PresenciaTotalTextField;
    private javax.swing.JRadioButton ProfesionalRadioButton;
    private javax.swing.JComboBox ProfesionesComboBox;
    private javax.swing.JTextField PuntosHistorialTextField;
    private javax.swing.JSpinner RangosCategoriaSpinner;
    private javax.swing.JSpinner RangosHabilidadSpinner;
    private javax.swing.JTextField RapidezBasicaTextField;
    private javax.swing.JSpinner RapidezPotencialSpinner;
    private javax.swing.JTextField RapidezRazaTextField;
    private javax.swing.JSpinner RapidezTemporalSpinner;
    private javax.swing.JTextField RapidezTotalTextField;
    private javax.swing.JComboBox RazasComboBox;
    private javax.swing.JTextField RazonBasicaTextField;
    private javax.swing.JSpinner RazonPotencialSpinner;
    private javax.swing.JTextField RazonRazaTextField;
    private javax.swing.JSpinner RazonTemporalSpinner;
    private javax.swing.JTextField RazonTotalTextField;
    private javax.swing.JComboBox ReinosComboBox;
    private javax.swing.JRadioButton RestringidaRadioButton;
    private javax.swing.JTextArea ResumenTextArea;
    private javax.swing.JCheckBox SeleccionarCheckBox;
    private javax.swing.ButtonGroup SexoButtonGroup;
    private javax.swing.JButton SubirArmaButton;
    private javax.swing.JTextArea TalentosCogidosTextArea;
    private javax.swing.JComboBox TalentosComboBox;
    private javax.swing.JPanel TalentosPanel;
    private javax.swing.ButtonGroup TipoHabilidadGroup;
    private javax.swing.JTextField TipoTextField;
    private javax.swing.JRadioButton VaronRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}

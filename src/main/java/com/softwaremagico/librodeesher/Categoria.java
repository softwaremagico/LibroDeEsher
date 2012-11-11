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
package com.softwaremagico.librodeesher;
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

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.softwaremagico.files.DirectorioRolemaster;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Genera una Categoria que el personaje puede desarrollar.
 */
public class Categoria implements Serializable {

    public Integer rangos;
    public int nuevosRangos;
    public int rangosCultura;
    public int rangosAdiestramiento;
    public int rangosSugeridos = 0;
    public int rangosInsertados = 0;
    public List<Habilidad> listaHabilidades = new ArrayList<>();
    private Caracteristica trioCaracteristicas[];
    public int costeRango[];
    private String nombre;
    private String abreviatura;
    private String stringTresCaracteristicas;
    public int bonusProfesion;
    public List<BonusEspecial> bonusEspecialesCategoria = new ArrayList<>();
    private TipoCategoria tipoCategoria;
    public boolean historial = false;
    private Random generator = new Random();
    public boolean restringida = false;
    public boolean noElegirAleatorio = false;
    private static HashMap<String, Categoria> categoriasDisponibles = new HashMap();

    /**
     * Creates a new instance of Categoria
     */
    private Categoria(String id, String tmp_abrev, String tresCaracteristicas, String tipo,
            String habilidades) throws Exception {
        nombre = id;
        rangos = 0;
        nuevosRangos = 0;
        rangosCultura = 0;
        abreviatura = tmp_abrev;
        rangosAdiestramiento = 0;
        costeRango = new int[3];
        trioCaracteristicas = new Caracteristica[3];
        stringTresCaracteristicas = tresCaracteristicas;
        tipoCategoria = new TipoCategoria(tipo);
        CaracteristicasDeCategoria();
        if (!Esher.armasFuegoPermitidas && habilidades != null) {
            habilidades.replace("Percepción del Entorno: Munición, ", "");
            habilidades.replace("Fuego de Supresión, ", "");
            habilidades.replace("Fuego Rápido", "");
        }
        if (id.contains("Desarrollo F")) {
            AñadirBonusEspecial(10, "DF");
        }
        GenerarHabilidades(habilidades);
    }

    public static Categoria getCategory(String id, String abrev, String tresCaracteristicas, String tipo,
            String habilidades) throws Exception {
        Categoria cat = categoriasDisponibles.get(id);
        if (cat == null) {
            cat = new Categoria(id, abrev, tresCaracteristicas, tipo, habilidades);
            categoriasDisponibles.put(id, cat);
        }
        return cat;
    }

    public void CambiarCosteRango(int[] nuevoCoste) {
        costeRango = nuevoCoste;
    }

    public void CaracteristicasDeCategoria() {
        //Los hechizos gastan solo una habilidad dependiente del reino.
        if (stringTresCaracteristicas.equals("*")) {
            int index = 0;
            for (int i = 0; i < 3; i++) {
                trioCaracteristicas[i] = null;
            }
            if (Personaje.getInstance().reinos.contains("Canalización")) {
                trioCaracteristicas[index] = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura("In");
                index++;
            }
            if (Personaje.getInstance().reinos.contains("Mentalismo")) {
                trioCaracteristicas[index] = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura("Pr");
                index++;
            }
            if (Personaje.getInstance().reinos.contains("Esencia")) {
                trioCaracteristicas[index] = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura("Em");
                index++;
            }
        } else {
            if (stringTresCaracteristicas.equals("Ninguna")) {
            } else {
                String[] tmp_trioCaracteristicas = stringTresCaracteristicas.split("/");
                for (int i = 0; i < tmp_trioCaracteristicas.length; i++) {
                    trioCaracteristicas[i] = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(tmp_trioCaracteristicas[i]);
                }
            }
        }
    }

    public String DevolverNombre() {
        return nombre;
    }

    public String DevolverAbreviatura() {
        return abreviatura;
    }

    public String FormatearNombre() {
        return nombre;
    }

    public String DevolverNombreTamañoDeterminado(int longitud) {
        String nuevoNombre = FormatearNombre();

        if (longitud > nuevoNombre.length()) {
            for (int i = nuevoNombre.length(); i < longitud; i++) {
                nuevoNombre += " ";
            }
        } else {
            return nombre.substring(0, longitud - 1);
        }
        return nuevoNombre;
    }

    public int DevolverTamañoMaximoNombre() {
        int tamañoMaximoHabilidad = DevolverTamañoMaximoNombreHabilidad();
        if (nombre.length() > tamañoMaximoHabilidad) {
            tamañoMaximoHabilidad = nombre.length();
        }
        return tamañoMaximoHabilidad;
    }

    private int DevolverTamañoMaximoNombreHabilidad() {
        int tamañoMaximoHabilidad = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            if (tamañoMaximoHabilidad < listaHabilidades.get(i).DevolverNombre().length()) {
                tamañoMaximoHabilidad = listaHabilidades.get(i).DevolverNombre().length();
            }
        }
        return tamañoMaximoHabilidad;
    }

    public String TipoCategoria() {
        return tipoCategoria.DevuelveTipoCategoria();
    }

    public void ConfirmarCategoria() {
        rangos += nuevosRangos;
        nuevosRangos = 0;
    }

    public String GenerarCadenaCaracteristicas() {
        String texto = "";
        for (int i = 0; i < trioCaracteristicas.length; i++) {
            if (trioCaracteristicas[0] != null) {
                try {
                    texto += trioCaracteristicas[i].DevolverAbreviatura();
                    if (i < 3 && trioCaracteristicas[i + 1] != null) {
                        texto += "/";
                    }
                } catch (ArrayIndexOutOfBoundsException | NullPointerException aiofb) {
                    break;
                }
            } else if (nombre.equals("Defensas Especiales")) {
                return "na";
            } else {
                return "_______";
            }
        }
        return texto;
    }

    public String GenerarCadenaCosteRangos() {
        String texto = "";
        for (int i = 0; i < 3; i++) {
            if (Personaje.getInstance().CosteCategoriaYHabilidad(this, i, null) < 1000) {
                texto += Personaje.getInstance().CosteCategoriaYHabilidad(this, i, null);
                try {
                    if (i < 3 && Personaje.getInstance().CosteCategoriaYHabilidad(this, i + 1, null) < 1000) {
                        texto += "/";
                    }
                } catch (ArrayIndexOutOfBoundsException aiofb) {
                    break;
                }
            }
        }
        return texto;
    }

    public boolean EsIncrementable() {
        if (tipoCategoria.DevuelveTipoCategoria().equals("Estándar")) {
            return true;
        }
        return false;
    }

    public int NumeroRangosIncrementables() {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            if (Personaje.getInstance().CosteCategoriaYHabilidad(this, i, null) < 1000) {
                total++;
            }
        }
        return total;
    }

    public boolean MereceLaPenaMostrar() {
        if (listaHabilidades.isEmpty()) {
            return false;
        }
        if (costeRango[0] == 0) {
            return false;
        }
        if (nombre.contains("Arma") && nombre.contains("Fuego") && !Esher.armasFuegoPermitidas) {
            return false;
        }
        if (!Esher.hechizosAdiestramientoOtrosReinosPermitidos && nombre.contains("Listas Hechizos de Adiestramientos de Otro Reino")) {
            return false;
        }
        if (((nombre.equals("Listas Básicas de la Tríada") || nombre.equals("Listas Básicas Elementales Complementarias")) && DevolverHabilidadesConRangos() == 0) && (!Personaje.getInstance().profesion.contains("Elementalista"))) {
            return false;
        }
        if ((nombre.equals("Listas Básicas de otras Profesiones") || nombre.equals("Listas Abiertas de otros Reinos")
                || nombre.equals("Listas Cerradas de otros Reinos") || nombre.equals("Listas Básicas de otros Reinos"))
                && DevolverHabilidadesConRangos() == 0) {
            return false;
        }
        if ((nombre.equals("Listas Abiertas Arcanas") || nombre.equals("Listas Hechizos de Adiestramiento")
                || nombre.equals("Listas Hechizos de Adiestramientos de Otro Reino"))
                && DevolverHabilidadesConRangos() == 0) {
            return false;
        }
        if (!Esher.armasFuegoPermitidas && nombre.contains("Armas·Fuego")) {
            return false;
        }
        return true;
    }

    public boolean MereceLaPenaListar() {
        if (listaHabilidades.isEmpty()) {
            return false;
        }
        if (costeRango[0] == 0) {
            return false;
        }
        if (nombre.contains("Arma") && nombre.contains("Fuego") && !Esher.armasFuegoPermitidas) {
            return false;
        }
        if (!Esher.hechizosAdiestramientoOtrosReinosPermitidos && nombre.contains("Listas Hechizos de Adiestramientos de Otro Reino")) {
            return false;
        }
        if (((nombre.equals("Listas Básicas de la Tríada") || nombre.equals("Listas Básicas Elementales Complementarias")) && DevolverHabilidadesConRangos() == 0) && (!Personaje.getInstance().profesion.contains("Elementalista"))) {
            return false;
        }
        if (!Esher.armasFuegoPermitidas && nombre.contains("Armas·Fuego")) {
            return false;
        }
        return true;
    }

    public boolean MereceLaPenaImprimir() {
        return MereceLaPenaMostrar();
    }

    public boolean EsRestringida() {
        if (restringida) {
            return true;
        }
        for (int i = 0; i < Personaje.getInstance().talentos.size(); i++) {
            for (int j = 0; j < Personaje.getInstance().talentos.get(i).bonusCategoria.size(); j++) {
                if (Personaje.getInstance().talentos.get(i).bonusCategoria.get(j).nombre.equals(nombre)) {
                    if (Personaje.getInstance().talentos.get(i).bonusCategoria.get(j).restringida) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Image DevolverCuadradosNuevosRangos(int scale) throws BadElementException, MalformedURLException, IOException {
        Image image;
        switch (nuevosRangos) {
            case 1:
                image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros1.png");
                break;
            case 2:
                image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros2.png");
                break;
            case 3:
                image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros3.png");
                break;
            default:
                image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros0.png");
        }
        image.scalePercent(28);

        return image;
    }

    /**
     * ********************************
     *
     * CATEGORIA ALEATORIA
     *
     *********************************
     */
    /**
     * Probabilidad que se incremente al generar un personaje aleatoriamente.
     */
    public int ProbabilidadSubida() {
        int probabilidad = 0;
        if (noElegirAleatorio) {
            return -100;
        }
        if (nuevosRangos <= 3) {
            if (Personaje.getInstance().PuntosDesarrolloNoGastados() >= Personaje.getInstance().CosteCategoriaYHabilidad(this, nuevosRangos + 1, null)
                    && TipoCategoria().equals("Estándar")) {
                probabilidad += DevolverValorCaracteristicas();
                probabilidad += CategoriaPreferida();
                probabilidad -= CaroCategoria();
                probabilidad += BonusTieneHabilidadComun();
                probabilidad += Esher.IntentosAsignarPD() * 3;
                if (Esher.inteligencia) {
                    probabilidad += AplicarInteligenciaALaAleatorizacion();
                }
                if (probabilidad > 90) {
                    probabilidad = 90;
                }
                if (probabilidad < 1) {
                    probabilidad = 1;
                }
                return probabilidad;
            }
        }
        return 0;
    }

    /**
     * Devuelve un modificador de acuerdo con algunos criterios.
     */
    private int AplicarInteligenciaALaAleatorizacion() {
        int bonus = 0;
        //No ponemos armas de fuego si no tienen nada. 
        if (!Esher.armasFuegoPermitidas && nombre.contains("Armas·Fuego")
                && DevolverRangos() == 0) {
            bonus = -10000;
        }
        if (nombre.equals("Armadura·Ligera") && Total() > 10) {
            return -1000;
        }
        if (nombre.equals("Armadura·Media") && Total() > 20) {
            return -1000;
        }
        if (nombre.equals("Armadura·Pesada") && Total() > 30) {
            return -1000;
        }
        //Si hay rangos en habilidades es muy probable que se suba la categoria.
        if (DevolverRangos() == 0) {
            bonus += (30 + DevolverHabilidadesConRangos() * 20);
            bonus += (30 + DevolverHabilidadesConRangosSugeridos() * 20);
        }
        //Se potencia la categoria con habilidades comunes o profesionales.
        if (ExisteHabilidadComunOProfesional()) {
            bonus += 50;
        }
        if (DevolverRangos() > 10) {
            bonus -= (8 - DevolverRangos()) * 10;
        }
        return bonus;
    }

    /**
     * Cuanto cuesta en puntos de desarrollo.
     */
    private int CaroCategoria() {
        return (Personaje.getInstance().CosteCategoriaYHabilidad(this, nuevosRangos, null) - 5) * 10;
    }

    /**
     * Las categorias preferidas son aquellas que tienen más rangos asignados.
     */
    int CategoriaPreferida() {
        int prob;
        prob = (rangos) * (Esher.especializacion + 4);
        if (prob > 30) {
            prob = 30;
        }
        return prob;
    }

    public void DeshabilitarAleatorio(boolean value) {
        noElegirAleatorio = value;
    }

    int BonusTieneHabilidadComun() {
        int bonus = 0;
        for (Habilidad habilidad : listaHabilidades) {
            if (habilidad.EsComun()) {
                bonus += 20;
            }
        }
        if (DevolverRangos() == 0) {
            return bonus;
        } else {
            return bonus / 10;
        }
    }

    /**
     * ********************************
     *
     * OBTENER TOTAL
     *
     *********************************
     */
    /**
     * Devuelve el valor de los rangos.
     */
    int DevolverValorRangoCategoria() {
        int total;
        if (TipoCategoria().equals("Estándar")) {
            if (DevolverRangos() == 0) {
                return -15;
            }
            if (DevolverRangos() <= 10) {
                return DevolverRangos() * 2;
            }
            if (DevolverRangos() > 10 && DevolverRangos() <= 20) {
                return (20 + (DevolverRangos() - 10));
            }
            if (DevolverRangos() > 20 && DevolverRangos() <= 30) {
                return 30 + (DevolverRangos() - 20) / 2;
            }
            if (DevolverRangos() > 30) {
                return 35;
            }
            return -15;
        } else {
            return 0;
        }
    }

    int DevolverValorCaracteristicas() {
        int total = 0;
        int numCaracteristicas = 0;
        try {
            total += trioCaracteristicas[0].Total();
            numCaracteristicas++;
        } catch (NullPointerException npe) {
        }
        try {
            total += trioCaracteristicas[1].Total();
            numCaracteristicas++;
        } catch (NullPointerException npe) {
        }
        try {
            total += trioCaracteristicas[2].Total();
            numCaracteristicas++;
        } catch (NullPointerException npe) {
        }
        //Los hechiceros híbridos tienen la media de las características mágicas.
        if (stringTresCaracteristicas.equals("*")) {
            if (numCaracteristicas < 1) {
                numCaracteristicas = 1;
            }
            return total / numCaracteristicas;
        }
        return total;
    }

    int DevolverBonusProfesion() {
        return bonusProfesion;
    }

    public int DevolverBonusEspecialesCategoria() {
        int total = 0;
        for (int i = 0; i < bonusEspecialesCategoria.size(); i++) {
            total += bonusEspecialesCategoria.get(i).bonus;
        }
        if (historial) {
            total += 5;
        }
        total += DevolverBonusTalentos();
        return total;
    }

    public int DevolverBonusTalentos() {
        return Personaje.getInstance().DevolverBonusTalentoCategoria(nombre);
    }

    public int DevolverBonusTalentosEspecial() {
        return Personaje.getInstance().DevolverBonusTalentoEspecialCategoria(nombre);
    }

    public BonusEspecial ExisteBonusEspecial(String tag) {
        for (int i = 0; i < bonusEspecialesCategoria.size(); i++) {
            if (bonusEspecialesCategoria.get(i).motivo.equals(tag)) {
                return bonusEspecialesCategoria.get(i);
            }
        }
        return null;
    }

    public void ModificaBonusEspecial(String tag, int bonus) {
        boolean existe = false;
        for (int i = 0; i < bonusEspecialesCategoria.size(); i++) {
            if (bonusEspecialesCategoria.get(i).motivo.equals(tag)) {
                if (bonus > 0) {
                    bonusEspecialesCategoria.get(i).bonus = bonus;
                    existe = true;
                } else {
                    bonusEspecialesCategoria.remove(i);
                }
            }
        }
        if (!existe) {
            BonusEspecial bonusEspecial = new BonusEspecial(tag, bonus);
            bonusEspecialesCategoria.add(bonusEspecial);
        }
    }

    public void AñadirBonusEspecial(int bon, String tag) {
        BonusEspecial bonusEspecial;
        bonusEspecial = new BonusEspecial(tag, bon);
        if (!bonusEspecialesCategoria.contains(bonusEspecial)) {
            bonusEspecialesCategoria.add(bonusEspecial);
        }
    }

    public int DevolverBonuses() {
        return DevolverBonusProfesion() + DevolverBonusEspecialesCategoria()
                + DevolverBonusObjetos();
    }

    public int Total() {
        return DevolverValorRangoCategoria() + DevolverValorCaracteristicas()
                + DevolverBonuses();
    }

    public int DevolverAntiguosRangos() {
        return rangos + rangosCultura + rangosAdiestramiento + rangosInsertados;
    }

    public int DevolverRangos() {
        return rangos + nuevosRangos + rangosCultura + rangosAdiestramiento + rangosInsertados;
    }

    public int DevolverBonusObjetos() {
        int total = 0;
        for (int i = 0; i < Personaje.getInstance().objetosMagicos.size(); i++) {
            total += Personaje.getInstance().objetosMagicos.get(i).DevolverBonusCategoria(this);
        }
        return total;
    }

    /**
     * Clasifica las categorias en Estandar, Combinadas, Limitadas, Especiales.
     */
    class TipoCategoria implements Serializable {

        private String tipo;

        public TipoCategoria(String tipoCat) {
            if (tipoCat.equals("Estándar") || tipoCat.equals("Combinada")
                    || tipoCat.equals("Limitada") || tipoCat.equals("Especial")
                    || tipoCat.equals("DPP") || tipoCat.equals("DF")) {
                tipo = tipoCat;
            } //Si no es nada, suponemos que es un error tipográfico.
            else {
                tipo = "Estándar";
                MostrarMensaje.showErrorMessage("Desconocida Categoría: " + tipoCat, "Categorías");
            }
        }

        public String DevuelveTipoCategoria() {
            return tipo;
        }
    }

    /**
     * ********************************
     *
     * HABILIDADES
     *
     *********************************
     */
    /**
     * Genera las habilidades según un nombre
     *
     * @param habilidades El nombre de la Habilidad
     * @throws java.lang.Exception
     */
    public void GenerarHabilidades(String habilidades) throws Exception {
        //Las Armas son especiales.
        if (nombre.startsWith("Armas·")) {
            String[] nombreFragmentado = nombre.split("\\·");
            String tipoArma = nombreFragmentado[1];
            if (!tipoArma.contains("Fuego") || Esher.armasFuegoPermitidas) {

                List<String> listadoArmas = Personaje.getInstance().armas.DevolverNombreArmasClase(tipoArma);
                try {
                    for (int i = 0; i < listadoArmas.size(); i++) {
                        String armaLeida = listadoArmas.get(i);
                        AddHabilidad(armaLeida);
                    }
                } catch (NullPointerException npe) {
                    MostrarMensaje.showErrorMessage("Error leyendo las armas de los ficheros. Comprueba el fichero de configuración de módulos.", "Categorías");
                    npe.printStackTrace();
                    System.exit(0);
                }
            }
            //Los hechizos son especiales.
        } else {
            try {
                String[] arrayHabilidades = habilidades.split(",");
                for (int i = 0; i < arrayHabilidades.length; i++) {
                    if (arrayHabilidades[i].equals("Hechizos")) {
                        //Se tratará cuando se seleccione la profesión.
                    } else {
                        if (arrayHabilidades[i].length() > 0) {
                            String name = arrayHabilidades[i].trim();
                            Habilidad hab = Habilidad.getSkill(this, name);
                            //if (esher.armasFuegoPermitidas || (!hab.DevolverNombre().equals("Percepción del Entorno: Munición")) && !hab.DevolverNombre().startsWith("Fuego ")) {
                            AddHabilidad(hab);
                            //}
                        }
                    }
                }
            } catch (NullPointerException npe) {
                MostrarMensaje.showErrorMessage("Categoria " + nombre + " sin habilidad alguna", "Categorías");
            }
        }
    }

    public void AddHabilidad(Habilidad hab) {
        if (!ExisteHabilidad(hab.DevolverNombre())) {
            listaHabilidades.add(hab);
        }
    }

    public void AddHabilidad(String nombreHab) {
        boolean rest = false;
        boolean noAleatoria = false;


        if (nombreHab.contains("(R)")) {
            rest = true;
            nombreHab = nombreHab.replace("(R)", "");
        }

        if (nombreHab.contains("*")) {
            noAleatoria = true;
            nombreHab = nombreHab.replace("*", "");
        }

        if (!ExisteHabilidad(nombreHab)) {
            Habilidad hab = Habilidad.getSkill(this, nombreHab.trim());
            hab.HacerHabilidadRestringidaPorTipo(rest);
            hab.DeshabilitarAleatorio(noAleatoria);
            listaHabilidades.add(hab);
        }
    }

    public void AddHabilidades(List<String> habilidades) {
        for (int i = 0; i < habilidades.size(); i++) {
            AddHabilidad(habilidades.get(i));
        }
    }

    public boolean ExisteHabilidad(String nom) {
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.DevolverNombre().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    public int NumeroHabilidadesExistes(List<String> habilidades) {
        int total = 0;
        for (int i = 0; i < habilidades.size(); i++) {
            if (ExisteHabilidad(habilidades.get(i).trim())) {
                total++;
            }
        }
        return total;
    }

    public Habilidad DevolverHabilidadDeNombre(String nom) {
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.DevolverNombre().equals(nom)) {
                return hab;
            }
        }
        return null;
    }

    public List<Habilidad> DevolverHabilidades() {
        return listaHabilidades;
    }

    public List<String> DevolverNombreHabilidades() {
        List<String> habilidades = new ArrayList<>();
        for (int i = 0; i < listaHabilidades.size(); i++) {
            habilidades.add(listaHabilidades.get(i).DevolverNombre());
        }
        return habilidades;
    }

    public int DevolverHabilidadesConRangos() {
        int habilidadesConRangos = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.DevolverRangos() > 0) {
                habilidadesConRangos++;
            }
        }
        return habilidadesConRangos;
    }

    private int DevolverHabilidadesConRangosSugeridos() {
        int habilidadesConRangos = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.rangosSugeridos > 0) {
                habilidadesConRangos++;
            }
        }
        return habilidadesConRangos;
    }

    public int DevolverHabilidadesConNuevosRangos() {
        int habilidadesConRangos = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.nuevosRangos > 0) {
                habilidadesConRangos++;
            }
        }
        return habilidadesConRangos;
    }

    public int DevolverNuevosRangosEnHabilidades() {
        int rangosHab = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            rangosHab += hab.nuevosRangos;


        }
        return rangosHab;
    }

    public int DevolverMaximoRangosEnHabilidad() {
        int maxRangos = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.DevolverRangos() > maxRangos) {
                maxRangos = hab.DevolverRangos();
            }
        }
        return maxRangos;
    }

    public int DevolverTotalRangosHabilidadesAdiestramiento() {
        int rangosAd = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            rangosAd += hab.rangosAdiestramiento;
        }
        return rangosAd;
    }

    public boolean ExisteHabilidadComunOProfesional() {
        return ExisteHabilidadProfesional() || ExisteHabilidadComun();
    }

    public boolean ExisteHabilidadProfesional() {
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.EsProfesional()) {
                return true;
            }
        }
        return false;
    }

    public boolean ExisteHabilidadRestringida() {
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.EsRestringida()) {
                return true;
            }
        }
        return false;
    }

    public boolean ExisteHabilidadComun() {
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            if (hab.EsComun()) {
                return true;
            }
        }
        return false;
    }

    public Habilidad DevolverHabilidadAlAzar() {
        return listaHabilidades.get(generator.nextInt(listaHabilidades.size()));
    }

    public void BorrarHabilidades() {
        listaHabilidades = new ArrayList<>();
    }

    public void BorrarHabilidad(String nombre) {
        for (int i = 0; i < listaHabilidades.size(); i++) {
            if (listaHabilidades.get(i).DevolverNombre().equals(nombre)) {
                listaHabilidades.remove(i);
            }
        }
    }

    public void BorrarHabilidades(List<String> habilidades) {
        for (int i = 0; i < habilidades.size(); i++) {
            BorrarHabilidad(habilidades.get(i).trim());
        }
    }

    public int DevolverRangosSugeridosHabilidades() {
        int rS = 0;
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            rS += hab.rangosSugeridos;
        }
        return rS;
    }

    public void OrdenarHabilidades() {
        String[] nombresHabilidades = new String[listaHabilidades.size()];
        for (int i = 0; i < listaHabilidades.size(); i++) {
            Habilidad hab = listaHabilidades.get(i);
            nombresHabilidades[i] = hab.DevolverNombre();
        }
        //Ordenamos las Habilidades.
        java.util.Arrays.sort(nombresHabilidades, java.text.Collator.getInstance(Locale.ITALIAN));

        List<Habilidad> listaHabilidadesOrdenadas = new ArrayList<>();
        for (int j = 0; j < nombresHabilidades.length; j++) {
            Habilidad habOrd = DevolverHabilidadDeNombre(nombresHabilidades[j]);
            listaHabilidadesOrdenadas.add(habOrd);
        }
        listaHabilidades = listaHabilidadesOrdenadas;
    }

    /**
     * Almacena un bonus especial a una categoría.
     */
    public class BonusEspecial implements Serializable {

        String motivo;
        int bonus;

        public BonusEspecial(String mot, int bon) {
            motivo = mot;
            bonus = bon;
        }
    }
}

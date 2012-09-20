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

import com.softwaremagico.librodeesher.gui.MostrarError;
import com.softwaremagico.librodeesher.gui.SeleccionarHabilidadGUI;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Crea una habilidad que el personaje puede desarrollar.
 */
public class Habilidad implements Serializable {

    public int rangos;
    public int nuevosRangos;
    public int rangosCultura;
    public int rangosAficiones;
    public int rangosAdiestramiento;
    public int rangosSugeridos = 0;
    public int rangosInsertados = 0;
    private boolean comunCultura = false;
    private boolean comunProfesion = false;
    private boolean comunRaza = false;
    private boolean comunAdiestramiento = false;
    private boolean profesional = false;
    private boolean profesionalAdiestramiento = false;
    private boolean restringida = false;
    private boolean siempreRestringida = false;
    private boolean restringidaAdiestramiento = false;
    public boolean estiloDeVida = false;
    public int multiplicadorCosteHechizos = 1;
    //boolean adiestramiento = false;     //Esta habilidad se está generando por motivo de leer un adiestramiento y debe ser exportada al exportar un nivel.
    public List<BonusEspecial> bonusEspecialesHabilidad = new ArrayList<BonusEspecial>();
    private String nombre;
    public Categoria categoriaPadre;
    public  boolean historial = false;
    public boolean especializada = false;
    public List<String> especializacion = new ArrayList<String>();   //Especializaciones seleccionadas por el PJ.
    public List<String> especializacionPosible = new ArrayList<String>();  //Especializaciones posibles para los aleatorios.
    public List<String> habilidadesNuevas = new ArrayList<String>();  //Habilidades que se ganan al adquirir esta habilidad.
    public List<String> habilidadesNuevasPosibles = new ArrayList<String>();  //Habilidades de las que se puede seleccionar al adquirir esta habilidad.
    private boolean generalizada = false;
    public int rangosEspecializacionAntiguosNiveles = 0;
    public transient SeleccionarHabilidadGUI grupoHab = null;
    public boolean noElegirAleatorio = false;

    /**
     * Creates a new instance of Habilidad
     */
    public Habilidad(Categoria cat, String nom) {
        rangos = 0;
        GenerarHabilidad(cat, nom);
    }

    public Habilidad(Categoria cat, String nom, int rang) {
        rangos = rang;
        GenerarHabilidad(cat, nom);
    }

    private void GenerarHabilidad(Categoria cat, String nom) {
        nuevosRangos = 0;
        rangosCultura = 0;
        rangosAdiestramiento = 0;
        rangosAficiones = 0;

        if (nom.contains("(R)")) {
            nom = nom.replace("(R)", "");
            siempreRestringida = true;
        }

        nom = nom.trim();

        if (nom.contains("{")) {
            String[] nombreFragmentado = nom.split("\\{");
            nom = nombreFragmentado[0].trim();
            //Separamos las nuevas habilidades;
            try {
                String lineaHabilidadesNuevas = nombreFragmentado[1].replace("}", "").trim();
                if (lineaHabilidadesNuevas.contains("&")) {
                    String[] habilidadesNuevasString = lineaHabilidadesNuevas.split(" & ");
                    for (int i = 0; i < habilidadesNuevasString.length; i++) {
                        habilidadesNuevas.add(habilidadesNuevasString[i]);
                    }
                } else if (lineaHabilidadesNuevas.contains("|")) {
                    String[] habilidadesNuevasPosiblesString = lineaHabilidadesNuevas.split("\\| ");
                    for (int i = 0; i < habilidadesNuevasPosiblesString.length; i++) {
                        habilidadesNuevasPosibles.add(habilidadesNuevasPosiblesString[i]);
                    }
                } else if (lineaHabilidadesNuevas.length() > 0) {
                    habilidadesNuevasPosibles.add(lineaHabilidadesNuevas);
                } else {
                    new MostrarError("No se puede leer la lista de habilidades vinculadas en " + nom, "Habilidad");
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                new MostrarError("Error en la lectura de las habilidades vinculadas a la habilidad " + nom, "Habilidad");
            }
        }
        if (!nom.contains("[")) {
            nombre = nom;
        } else {
            String[] nombreFragmentado = nom.split("\\[");
            nombre = nombreFragmentado[0].trim();
            //Separamos las especializaciones.
            try {
                String lineaEspecializaciones = nombreFragmentado[1].replace("]", "").trim();
                String[] especializaciones = lineaEspecializaciones.split("; ");
                for (int i = 0; i < especializaciones.length; i++) {
                    especializacionPosible.add(especializaciones[i]);
                }
            } catch (NullPointerException npe) {
                new MostrarError("Error en la lectura de las especializaciones predefinidas para la habilidad " + nom, "Habilidad");
            }
        }
        categoriaPadre = cat;
    }

    /**
     * Devuelve el nombre de la habilidad.
     *
     * @return
     */
    public String DevolverNombre() {
        return nombre;
    }

    /**
     * Devuelve el nombre de la habilidad. Si es generalizada, elimina algunos
     * acepciones del nombre.
     *
     * @return
     */
    public String FormatearNombre() {
        if (generalizada) {
            String[] vectorNombre = nombre.split(":");
            return vectorNombre[0];
        }
        return nombre;
    }

    /**
     * Añade la etiqueta común, restringida o profesional al nombre.
     *
     * @return
     */
    public String DevolverNombreYModif() {
        String nuevoNombre = "";
        if (EsRestringida()) {
            nuevoNombre += " (r)";
        } else {
            if (EsComun()) {
                nuevoNombre += " (c)";
            }
            if (EsProfesional()) {
                nuevoNombre += " (p)";
            }

            if (generalizada) {
                nuevoNombre += " (g)";
            }
        }
        return FormatearNombre() + nuevoNombre;
    }

    public String DevolverNombreTamañoDeterminado(int longitud) {
        String nuevoNombre = FormatearNombre();

        String sufix = "";
        int longSufix = 0;

        if (EsRestringida()) {
            sufix += " (r)";
            longSufix += 4;
        } else {
            if (EsComun()) {
                sufix += " (c)";
                longSufix += 4;
            }
            if (EsProfesional()) {
                sufix += " (p)";
                longSufix += 4;
            }
            if (generalizada) {
                sufix += " (g)";
                longSufix += 4;
            }
        }

        /* Mas largo */
        if (longitud < nuevoNombre.length() + longSufix) {
            nuevoNombre = nuevoNombre.substring(0, longitud - longSufix);
        }

        nuevoNombre += sufix;


        /* Mas corto */
        if (longitud > nuevoNombre.length()) {
            for (int i = nuevoNombre.length(); i < longitud; i++) {
                nuevoNombre += " ";
            }
        }

        return nuevoNombre;
    }

    public String DevolverEspecializacionTamañoDeterminado(int longitud, int indiceEspecializacion) {
        String nuevoNombre = nombre;

        nuevoNombre += " (" + especializacion.get(indiceEspecializacion) + ")";
        for (int i = nuevoNombre.length(); i < longitud; i++) {
            nuevoNombre += " ";
        }

        return nuevoNombre;
    }

    public void HacerComunRaza() {
        NoEsProfesional();
        NoEsRestringidaRaza();
        comunRaza = true;
    }

    public void NoEsComunRaza() {
        comunRaza = false;
    }

    public void HacerComunProfesion() {
        NoEsProfesional();
        NoEsRestringidaRaza();
        comunProfesion = true;
    }

    public void NoEsComunProfesion() {
        comunProfesion = false;
    }

    public void HacerComunCultura() {
        NoEsProfesional();
        NoEsRestringidaRaza();
        comunCultura = true;
    }

    public void HacerComunAdiestramiento() {
        NoEsProfesional();
        NoEsRestringidaRaza();
        comunAdiestramiento = true;
    }

    public void NoEsComunAdiestramiento() {
        comunAdiestramiento = false;
    }

    public void NoEsComunCultura() {
        comunCultura = false;
    }

    public void HacerProfesional() {
        NoEsComunCultura();
        NoEsComunProfesion();
        NoEsComunRaza();
        NoEsRestringidaRaza();
        profesional = true;
    }

    public void HacerProfesionalAdiestramiento() {
        NoEsComunCultura();
        NoEsComunProfesion();
        NoEsComunRaza();
        NoEsRestringidaRaza();
        profesionalAdiestramiento = true;
    }

    public void NoEsProfesional() {
        profesional = false;
    }

    public void NoEsProfesionalAdiestramiento() {
        profesionalAdiestramiento = false;
    }

    public void HacerRestringida() {
        NoEsProfesional();
        NoEsComunCultura();
        NoEsComunProfesion();
        NoEsComunRaza();
        restringida = true;
    }

    public void HacerRestringidaAdiestramiento() {
        NoEsProfesionalAdiestramiento();
        NoEsComunAdiestramiento();
        restringidaAdiestramiento = true;
    }

    public void NoEsRestringidaRaza() {
        restringida = false;
    }

    public void HacerHabilidadRestringidaPorTipo(boolean rest) {
        siempreRestringida = rest;
    }

    public void NoEsRestringidaAdiestramiento() {
        restringidaAdiestramiento = false;
    }

    public void HacerNormal() {
        restringida = false;
        profesional = false;
        comunCultura = false;
        comunProfesion = false;
        comunAdiestramiento = false;
        profesionalAdiestramiento = false;
        restringidaAdiestramiento = false;
        comunRaza = false;
    }

    public boolean HacerGeneralizada() {
        if (restringida || restringidaAdiestramiento) {
            new MostrarError("No se puede generalizar una habilidad restringida.",
                    "Habilidad", JOptionPane.WARNING_MESSAGE);
        } else if (especializada) {
            new MostrarError("No se puede generalizar una habilidad ya especializada",
                    "Habilidad", JOptionPane.WARNING_MESSAGE);
        } else {
            generalizada = true;
            especializada = false;
            especializacion = new ArrayList<String>();
        }
        return generalizada;
    }

    public void QuitarGeneralizada() {
        generalizada = false;
    }

    public boolean EsGeneralizada() {
        return generalizada;
    }

    /**
     * Añade un conjunto de especializaciones a la habilidad.
     *
     * @param especializar Indica si se desea especializar.
     * @param lista Lista separada por comas.
     */
    public void AñadirEspecializacion(boolean especializar, String lista, String antiguaLista) {
        if (especializar && !generalizada) {
            String[] arrayEspecializacion = lista.split(", ");
            if (lista.length() == 0) {
                if (rangosEspecializacionAntiguosNiveles > 0) {
                    String[] arrayAntiguaEspecializacion = antiguaLista.split(", ");
                    especializacion = new ArrayList<String>();
                    for (int i = 0; i < arrayAntiguaEspecializacion.length; i++) {
                        if (!especializacion.contains(arrayAntiguaEspecializacion[i])) {
                            especializacion.add(arrayAntiguaEspecializacion[i]);
                        }
                    }
                } else {
                    new MostrarError("Debes indicar en que campo/s esta especializada la habilidad.", "Habilidad",
                            JOptionPane.WARNING_MESSAGE);
                    especializada = false;
                    especializacion = new ArrayList<String>();
                }
            } else if (nuevosRangos + rangos >= arrayEspecializacion.length) {
                especializada = true;
                especializacion = new ArrayList<String>();
                for (int i = 0; i < arrayEspecializacion.length; i++) {
                    if (!especializacion.contains(arrayEspecializacion[i])) {
                        especializacion.add(arrayEspecializacion[i]);
                    }
                }
            } else {
                new MostrarError("No has comprado los rangos suficientes para "
                        + "especializar en todos los campos indicados.", "Habilidad",
                        JOptionPane.WARNING_MESSAGE);
                especializacion = new ArrayList<String>();
                for (int i = 0; i < arrayEspecializacion.length; i++) {
                    if (!especializacion.contains(arrayEspecializacion[i])) {
                        especializacion.add(arrayEspecializacion[i]);
                    }
                }
                do {
                    if (especializacion.size() > 0) {
                        especializacion.remove(especializacion.size() - 1);
                    }
                    if (especializacion.size() < 1) {
                        especializada = false;
                    } else {
                        especializada = true;
                    }
                } while (nuevosRangos + rangos < especializacion.size());

            }
        } else {
            especializada = false;
            especializacion = new ArrayList<String>();
        }
    }

    /**
     * Indica cuantos rangos deben ser restados del total para comprar las
     * epsecializaciones deseadas.
     *
     * @return
     */
    public int RangosNuevosGastadosEnEspecializacion() {
        return especializacion.size() - rangosEspecializacionAntiguosNiveles;
    }

    public int RangosGastadosEnEspecializacion() {
        return especializacion.size();
    }

    public Categoria DevolverCategoria() {
        return categoriaPadre;
    }

    /**
     * Hay habilidades que solamente se pueden subir hasta el valor de otra,
     * como golpes o barridos.
     */
    private int RangosQuePuedeSubirLaHabilidad(int rangos) {
        /////////////////// BARRIDOS //////////////////
        if (nombre.equals("Barridos Grado 1")) {
            // No puede ser menor que el de grado superior.
            Habilidad hab2 = categoriaPadre.DevolverHabilidadDeNombre("Barridos Grado 2");
            if ((DevolverAntiguosRangos() + rangos) < hab2.DevolverRangos()) {
                return hab2.DevolverRangos();
            } else {
                return rangos;
            }
        }
        if (nombre.equals("Barridos Grado 2")) {
            // No puede ser mayor que el de grado inferior.
            Habilidad hab1 = categoriaPadre.DevolverHabilidadDeNombre("Barridos Grado 1");
            if ((DevolverAntiguosRangos() + rangos) > hab1.DevolverRangos()) {
                return hab1.DevolverRangos();
            } else {
                // No puede ser menor que el de grado superior.
                Habilidad hab2 = categoriaPadre.DevolverHabilidadDeNombre("Barridos Grado 3");
                if ((DevolverAntiguosRangos() + rangos) < hab2.DevolverRangos()) {
                    return hab2.DevolverRangos();
                } else {
                    return rangos;
                }
            }
        }
        if (nombre.equals("Barridos Grado 3")) {
            // No puede ser mayor que el de grado inferior.
            Habilidad hab1 = categoriaPadre.DevolverHabilidadDeNombre("Barridos Grado 2");
            if ((DevolverAntiguosRangos() + rangos) > hab1.DevolverRangos()) {
                return hab1.DevolverRangos();
            } else {
                // No puede ser menor que el de grado superior.
                Habilidad hab2 = categoriaPadre.DevolverHabilidadDeNombre("Barridos Grado 4");
                if ((DevolverAntiguosRangos() + rangos) < hab2.DevolverRangos()) {
                    return hab2.DevolverRangos();
                } else {
                    return rangos;
                }
            }
        }
        if (nombre.equals("Barridos Grado 4")) {
            // No puede ser mayor que el de grado inferior.
            Habilidad hab1 = categoriaPadre.DevolverHabilidadDeNombre("Barridos Grado 3");
            if ((DevolverAntiguosRangos() + rangos) > hab1.DevolverRangos()) {
                return hab1.DevolverRangos();
            }
            return rangos;
        }

        /////////////////// GOLPES //////////////////
        if (nombre.equals("Golpes Grado 1")) {
            // No puede ser menor que el de grado superior.
            Habilidad hab2 = categoriaPadre.DevolverHabilidadDeNombre("Golpes Grado 2");
            if ((DevolverAntiguosRangos() + rangos) < hab2.DevolverRangos()) {
                return hab2.DevolverRangos();
            } else {
                return rangos;
            }
        }
        if (nombre.equals("Golpes Grado 2")) {
            // No puede ser mayor que el de grado inferior.
            Habilidad hab1 = categoriaPadre.DevolverHabilidadDeNombre("Golpes Grado 1");
            if ((DevolverAntiguosRangos() + rangos) > hab1.DevolverRangos()) {
                return hab1.DevolverRangos();
            } else {
                // No puede ser menor que el de grado superior.
                Habilidad hab2 = categoriaPadre.DevolverHabilidadDeNombre("Golpes Grado 3");
                if ((DevolverAntiguosRangos() + rangos) < hab2.DevolverRangos()) {
                    return hab2.DevolverRangos();
                } else {
                    return rangos;
                }
            }
        }
        if (nombre.equals("Golpes Grado 3")) {
            // No puede ser mayor que el de grado inferior.
            Habilidad hab1 = categoriaPadre.DevolverHabilidadDeNombre("Golpes Grado 2");
            if ((DevolverAntiguosRangos() + rangos) > hab1.DevolverRangos()) {
                return hab1.DevolverRangos();
            } else {
                // No puede ser menor que el de grado superior.
                Habilidad hab2 = categoriaPadre.DevolverHabilidadDeNombre("Golpes Grado 4");
                if ((DevolverAntiguosRangos() + rangos) < hab2.DevolverRangos()) {
                    return hab2.DevolverRangos();
                } else {
                    return rangos;
                }
            }
        }
        if (nombre.equals("Golpes Grado 4")) {
            // No puede ser mayor que el de grado inferior.
            Habilidad hab1 = categoriaPadre.DevolverHabilidadDeNombre("Golpes Grado 3");
            if ((DevolverAntiguosRangos() + rangos) > hab1.DevolverRangos()) {
                return hab1.DevolverRangos();
            }
            return rangos;
        }

        return rangos;
    }

    /**
     * Suma el valor determinado a los nuevos rangos.
     */
    public void IncrementarNuevosRangos(int valor) {
        nuevosRangos = RangosQuePuedeSubirLaHabilidad(nuevosRangos + valor);
        //AñadirHabilidadesVinculadas();
    }

    /**
     * Asigna los rangos nuevos al valor determinado.
     */
    public void NuevosRangos(int valor) {
        nuevosRangos = RangosQuePuedeSubirLaHabilidad(valor);
        //AñadirHabilidadesVinculadas();
    }

    public int NumeroRangosIncrementables() {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            if (categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, i, this) < 1000) {
                total++;
            }
        }
        return total;
    }

    public Image DevolverCuadradosNuevosRangos(int scale) throws BadElementException, MalformedURLException, IOException {
        Image image;
        if (!EsRestringida()) {
            switch (nuevosRangos) {
                case 1:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros1.png");
                    break;
                case 2:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros2.png");
                    break;
                case 3:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros3.png");
                    break;
                default:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros0.png");
            }
        } else {
            switch (nuevosRangos) {
                case 1:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros05.png");
                    break;
                case 2:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros1.png");
                    break;
                case 3:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros15.png");
                    break;
                default:
                    image = Image.getInstance("rolemaster/fichas/cuadros/cuadros0.png");
            }
        }
        image.scalePercent(25);

        return image;
    }

    /**
     * ********************************
     *
     * HABILIDAD ALEATORIA
     *
     *********************************
     */
    /**
     * Devuelve la probabilidad de que una habilidad suba un rango.
     */
    public int ProbabilidadSubida() {
        int probabilidad = 0;
        if (noElegirAleatorio && DevolverRangos() < 1) {
            return -100;
        }
        if (nuevosRangos <= 3) {
            if (categoriaPadre.esher.pj.PuntosDesarrolloNoGastados() >= categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this)) {
                probabilidad += categoriaPadre.CategoriaPreferida() / 3;
                probabilidad += HabilidadPreferida();
                probabilidad += FacilidadHabilidad();
                probabilidad -= CaroHabilidad();
                probabilidad += categoriaPadre.esher.IntentosAsignarPD() * 3;
                probabilidad += AplicarEspecializacionPersonaje();
                probabilidad += HabilidadAtrasada();
                if (categoriaPadre.esher.inteligencia) {
                    probabilidad += HabilidadesPreferidasHechiceros();
                    probabilidad += HabilidadesPreferidasLuchadores();
                    probabilidad += AplicarInteligenciaALaAleatorizacion();
                    probabilidad += HabilidadAbsurda();
                }
                //Ponemos una cota superior y una cota inferior.
                if (probabilidad > 90) {
                    probabilidad = 90;
                }
                if (probabilidad < 1 && categoriaPadre.DevolverRangos() > 0) {
                    probabilidad = 1;
                }
                probabilidad += MaximoRangos();
                return probabilidad;
            }
        }
        return 0;
    }

    /**
     * Habilidades que no han tenido ningún rango en muchos niveles y no son
     * demasiado caras.
     *
     * @return
     */
    private int HabilidadAtrasada() {
        int bonus = 0;
        bonus += Math.max(categoriaPadre.esher.pj.nivel - (categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, 0, this)) - DevolverRangos() * 2, 0);
        return bonus;
    }

    /**
     * Cuanto cuesta en puntos de desarrollo.
     */
    private int CaroHabilidad() {
        //Las que valen uno de coste son casi obligatorias cogerlas.
        if (categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this) == 1) {
            return -100;
        }
        if (categoriaPadre.DevolverNombre().equals("Listas Básicas de Hechizos")) {
            return (categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this) - 8) * 10;
        }
        return (categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this) - 5) * 10;
    }

    /**
     * Devuelve un modificador de acuerdo con algunos criterios.
     */
    private int AplicarInteligenciaALaAleatorizacion() {
        return AleatorizacionPorRaza() + AleatorizacionPorProfesion() + AleatorizacionPorRangos()
                + AleatorizacionPorHabilidad() + AleatorizacionPorOpciones();
    }

    private int AleatorizacionPorRaza() {
        //Los caballos para uno y los lobos para otros.
        if (DevolverNombre().equals("Montar: Caballos") && categoriaPadre.esher.pj.raza.contains("Orco")) {
            return -1000;
        }
        if (DevolverNombre().equals("Montar: Lobos") && !categoriaPadre.esher.pj.raza.contains("Orco")) {
            return -1000;
        }
        if (DevolverNombre().equals("Montar: Osos") && !categoriaPadre.esher.pj.raza.contains("Enanos")) {
            return -1000;
        }

        //Los ataques raciales com mordisco y demás, solo para aquellos a los que sea comunes.
        if (nombre.startsWith("Ataque Racial: ") && !EsComun()) {
            return -1000;
        }
        return 0;
    }

    private int AleatorizacionPorProfesion() {
        Habilidad hab;
        int bonus = 0;

        //Para los hechiceros.
        if (categoriaPadre.esher.pj.EsHechicero()) {
            //Potenciamos que existan las listas básicas (al menos 3)
            if (categoriaPadre.DevolverNombre().equals("Listas Básicas de Hechizos")) {
                if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < categoriaPadre.esher.especializacion + 2) {
                    if (DevolverRangos() < categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2) {
                        if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < categoriaPadre.esher.especializacion + 1) {
                            if (categoriaPadre.esher.pj.EsSemiHechicero()) {
                                bonus += 65;
                            } else {
                                bonus += 50;
                            }
                        } else {
                            if (categoriaPadre.esher.pj.EsSemiHechicero()) {
                                bonus += 35;
                            } else {
                                bonus += 25;
                            }
                        }
                    } else {
                        return -150;
                    }
                }
            }


            if (categoriaPadre.DevolverNombre().equals("Listas Abiertas de Hechizos")) {
                if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < categoriaPadre.esher.especializacion + 2) {
                    if (DevolverRangos() < categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2) {
                        if (categoriaPadre.DevolverHabilidadesConNuevosRangos() == 0) {
                            if (categoriaPadre.esher.pj.EsSemiHechicero()) {
                                bonus += 35;
                            } else {
                                bonus += 20;
                            }
                        } else {
                            bonus += 15;
                        }
                    } else {
                        return -100;
                    }
                }
            }
            if (categoriaPadre.DevolverNombre().equals("Listas Cerradas de Hechizos")) {
                if (categoriaPadre.DevolverHabilidadesConRangos() < 3) {
                    if (DevolverRangos() < categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2) {
                        if (categoriaPadre.DevolverHabilidadesConNuevosRangos() == 0) {
                            bonus += 20;
                        } else {
                            bonus += 10;
                        }
                    } else {
                        return -150;
                    }
                }
            }
            if (categoriaPadre.DevolverNombre().equals("Listas Básicas de otras Profesiones")) {
                if (DevolverRangos() < categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2 && categoriaPadre.DevolverHabilidadesConNuevosRangos() < 1) {
                    bonus += 5;
                } else {
                    bonus -= 150;
                }
            }
            if (categoriaPadre.DevolverNombre().equals("Listas Abiertas de otros Reinos")) {
                if (DevolverRangos() > categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2 || categoriaPadre.DevolverHabilidadesConNuevosRangos() > 1) {
                    bonus -= 150;
                }
            }
            if (categoriaPadre.DevolverNombre().equals("Listas Cerradas de otros Reinos")) {
                if (DevolverRangos() > categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2 || categoriaPadre.DevolverHabilidadesConNuevosRangos() > 1) {
                    bonus -= 150;
                }
            }
            if (categoriaPadre.DevolverNombre().equals("Listas Básicas de otros Reinos")) {
                if (DevolverRangos() > categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2 || categoriaPadre.DevolverHabilidadesConNuevosRangos() > 1) {
                    bonus -= 150;
                }
            }
            if (categoriaPadre.DevolverNombre().equals("Listas Abiertas Arcanas")) {
                if (DevolverRangos() > categoriaPadre.esher.pj.nivel + categoriaPadre.esher.especializacion + 2 || categoriaPadre.DevolverHabilidadesConNuevosRangos() > 2) {
                    bonus -= 150;
                }
            }
            if (nombre.startsWith("Hechizos Dirigidos de")) {
                String elemento = nombre.replaceAll("Hechizos Dirigidos de ", "");
                if (((hab = categoriaPadre.esher.pj.DevolverHabilidadDeNombre("Ley del ", elemento)) != null
                        || (hab = categoriaPadre.esher.pj.DevolverHabilidadDeNombre("Ley de ", elemento)) != null) && hab.DevolverRangos() > 0) {
                    bonus += 10 * (hab.DevolverRangos() - DevolverRangos());
                } else {
                    bonus -= 150;
                }
            }
            if (nombre.equals("Desarrollo de Puntos de Poder")) {
                if (nuevosRangos == 0) {
                    bonus += 50;
                } else {
                    if (rangos + nuevosRangos < categoriaPadre.esher.pj.nivel) {
                        bonus += 10 * (categoriaPadre.esher.pj.nivel - rangos + nuevosRangos);
                    }
                    //Al menos tener PPs suficientes para lanzar el mejor hechizo.
                    if (categoriaPadre.esher.pj.DevolverMaximoNivelHechizos() > Total()) {
                        bonus += 100;
                    }
                }
            }
        }

        //Dejamos los movimientos adrenales para monjes.
        if (!categoriaPadre.esher.pj.profesion.contains("Monje")) {
            if (nombre.contains("Adrenal")) {
                bonus -= 50;
            }
        }

        //Tampoco interesa demasiados artes marciales si no es monje.
        if (!categoriaPadre.esher.pj.profesion.contains("Monje") && (categoriaPadre.DevolverNombre().startsWith("Artes Marciales·"))
                && categoriaPadre.DevolverHabilidadesConNuevosRangos() > 0
                && (categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, 0, this) > 2)) {
            bonus -= 100;
        }

        //Para los guerreros.
        if (categoriaPadre.esher.pj.EsCombatiente()) {
            if ((categoriaPadre.DevolverNombre().startsWith("Armas·")) && nuevosRangos == 0
                    && categoriaPadre.costeRango[0] < 2) {
                if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < 2) {
                    bonus += 20;
                }
                if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < 3) {
                    bonus += 10;
                }
            }

            if (nombre.equals("Desarrollo Físico") && DevolverRangos() < 10 && nuevosRangos == 0) {
                bonus += 20;
            }
            //A veces lo no hechiceros tienen hechizos de opciones de adiestramiento o cultura.
            if (nombre.equals("Desarrollo de Puntos de Poder")) {
                Categoria cat1 = categoriaPadre.esher.pj.DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
                Categoria cat2 = categoriaPadre.esher.pj.DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");

                if (Total() < Math.max(cat1.DevolverMaximoRangosEnHabilidad(), cat2.DevolverMaximoRangosEnHabilidad())) {
                    bonus += Math.min(10 * categoriaPadre.esher.pj.nivel, 50);
                }
            }
            if (categoriaPadre.DevolverNombre().startsWith("Armadura·") && Total() < 30) {
                bonus += 20;
            }
        }

        return bonus;
    }

    private int AleatorizacionPorRangos() {
        int bonus = 0;

        /* Nunca más de 50 rangos en una habilidad */
        if (DevolverRangos() >= 50) {
            return -1000;
        }

        //Siempre va bien tener algunos puntos de vida.
        if (nombre.equals("Desarrollo Físico") && DevolverRangos() == 0) {
            bonus += 40;
        }

        //No tiene sentido subir un idioma más de 10 rangos;
        if (categoriaPadre.DevolverNombre().equals("Comunicación") && DevolverRangos() > 9) {
            return -1000;
        }

        //Las habilidades de comunicación tienen muchos rangos, por lo que tienen demasiadas probabilidades de subir.
        if (categoriaPadre.DevolverNombre().equals("Comunicación") && DevolverRangos() > 60) {
            bonus -= 40;
        }

        if (DevolverNombre().equals("Cuero Blando") && Total() > 30) {
            return -1000;
        }
        if (DevolverNombre().equals("Cuero Endurecido") && Total() > 90) {
            return -1000;
        }
        if (DevolverNombre().equals("Cota de Mallas") && Total() > 100) {
            return -1000;
        }
        if (DevolverNombre().equals("Coraza") && Total() > 120) {
            return -1000;
        }

        /* Cuando se tiene muchos rangos, es tonteria subir más de un rango en una habilidad por nivel */
        if (DevolverRangos() > 10 && nuevosRangos > 0) {
            bonus -= 50;
        }

        return bonus;
    }

    private int AleatorizacionPorHabilidad() {
        int bonus = 0;
        /* Es tontería incluir el Main Gauche si no tiene ningun arma mas.*/
        if (DevolverNombre().equals("Main Gauche") && categoriaPadre.DevolverHabilidadesConRangos() < 1) {
            return -10000;
        }

        //Evitemos que aleatoriamente se lancen rocas.
        if (nombre.contains("Rocas") && DevolverRangos() < 1) {
            return -10000;
        }

        //A veces se aprenden demasiados tipos de armas.
        if ((categoriaPadre.DevolverNombre().startsWith("Armas·"))
                && categoriaPadre.DevolverHabilidadesConNuevosRangos() > (3 - categoriaPadre.esher.pj.CosteCategoriaYHabilidad(categoriaPadre, 0, this))
                || categoriaPadre.esher.pj.DevolverArmasAprendidasEnEsteNivel() > 5) {
            bonus -= 50;
        }
        return bonus;
    }

    private int AleatorizacionPorOpciones() {
        if (!categoriaPadre.esher.armasFuegoPermitidas && (categoriaPadre.DevolverNombre().contains("Armas·Fuego"))) {
            return -10000;
        }
        return 0;
    }

    /**
     * Habilidades relacionadas con la profesion del personaje.
     *
     * @return
     */
    private int HabilidadesPreferidasHechiceros() {
        //Algunos hechizos son mejores.
        if (categoriaPadre.esher.inteligencia) {
            if (categoriaPadre.esher.pj.EsHechicero()) {
                if (categoriaPadre.esher.pj.reino.contains("Esencia")) {
                    if (nombre.equals("Maestría de los Escudos")) {
                        return Math.max(50 - categoriaPadre.esher.especializacion * 8, 10);
                    }
                    if (nombre.equals("Sendas de la Rapidez")) {
                        return Math.max(20 - categoriaPadre.esher.especializacion * 5, 0);
                    }
                }
                if (categoriaPadre.esher.pj.reino.contains("Mentalismo")) {
                    if (nombre.equals("Evasión de los Ataques")) {
                        return Math.max(50 - categoriaPadre.esher.especializacion * 8, 10);
                    }
                    if (nombre.equals("Autocuración")) {
                        return Math.max(30 - categoriaPadre.esher.especializacion * 5, 5);
                    }
                    if (nombre.equals("Velocidad")) {
                        return Math.max(20 - categoriaPadre.esher.especializacion * 5, 0);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Habilidades relacionadas con la profesion del personaje.
     *
     * @return
     */
    private int HabilidadesPreferidasLuchadores() {
        if (categoriaPadre.esher.inteligencia) {
            if (categoriaPadre.esher.pj.EsCombatiente()) {
                if (categoriaPadre.esher.pj.adiestramientosAntiguos.contains("Caballero")) {
                    if (nombre.equals("Montar: Caballos")) {
                        return 50;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Permite seleccionar si un personaje quiere muchas habilidades de pocos
     * rangos o pocas habilidades de muchos rangos.
     */
    private int AplicarEspecializacionPersonaje() {
        int bonus = 0;
        //Una pequeña penalización al añadir habilidades nuevas que no son típicas.
        if (DevolverRangos() == 0 && !(EsProfesional() || EsComun())) {
            bonus -= Math.pow(categoriaPadre.DevolverHabilidadesConRangos(), 2) * (categoriaPadre.esher.especializacion + 1) * 10;
        }
        //Evitar demasiada variedad de arma.
        if (categoriaPadre.DevolverNombre().startsWith("Armas·")) {
            if (DevolverRangos() - rangosCultura < 2) {
                bonus -= categoriaPadre.esher.pj.DevolverArmasAprendidas() * (categoriaPadre.esher.especializacion + 1) * 5;
            }
        }
        //No añadir demasiados rangos en habilidades de una misma categoria.
        bonus -= Math.max(Math.pow(categoriaPadre.DevolverHabilidadesConNuevosRangos(), 3) * (categoriaPadre.esher.especializacion + 1) * 3, 0);
        return bonus;
    }

    /**
     * Da preferencia a aquellas habilidades del personaje que ya han sido
     * subidas.
     */
    private int HabilidadPreferida() {
        int prob;
        prob = (DevolverRangos()) * 5 + (RangosGastadosEnEspecializacion() * 15);
        if (prob > 50 + RangosGastadosEnEspecializacion() * 15) {
            prob = 50 + RangosGastadosEnEspecializacion() * 15;
        }
        return prob;
    }

    /**
     * Las habilidades comunes y profesionales son más importantes para el
     * categoriaPadre.esher.pj.
     */
    private int FacilidadHabilidad() {
        if (generalizada) {
            return 50;
        }
        if (EsRestringida()) {
            return -75;
        }
        if (EsProfesional()) {
            return 75;
        }
        if (EsComun()) {
            return 35;
        }
        return 0;
    }

    /**
     * Existen ciertas habilidades que son absurdas tenerlas salvo ocasiones
     * especiales.
     *
     * @return
     */
    private int HabilidadAbsurda() {
        if (nombre.equals("Control de la Licantropía")
                || nombre.equals("Montar: Osos")) {
            return -1000;
        }
        return 0;
    }

    private int MaximoRangos() {
        if (categoriaPadre.esher.pj.nivel == 1
                && (DevolverRangos() > 10 && !estiloDeVida)
                || (DevolverRangos() > 15 && estiloDeVida)) {
            return -1000;
        }
        return 0;
    }

    public void DeshabilitarAleatorio(boolean value) {
        noElegirAleatorio = value;
    }

    /**
     * ********************************
     *
     * SUBIR HABILIDAD
     *
     *********************************
     */
    /**
     * Una habilidad merece la pena mostrarla si tiene algún modificador.
     */
    public boolean MereceLaPenaMostrar() {
        if (!categoriaPadre.esher.poderesChi && nombre.contains("Poderes Chi")) {
            return false;
        }
        if (DevolverRangos() > 0 || DevolverBonuses() > 0 || especializada || EsComun() || EsProfesional()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Una habilidad merece la pena imprimirla si tiene rangos.
     */
    public boolean MereceLaPenaImprimir() {
        if (!categoriaPadre.esher.poderesChi && nombre.contains("Poderes Chi")) {
            return false;
        }
        if (DevolverRangos() > 0 || DevolverBonuses() > 0 || especializada) {
            return true;
        } else {
            return false;
        }
    }

    public boolean MereceLaPenaListar() {
        if (!categoriaPadre.esher.poderesChi && nombre.contains("Poderes Chi")) {
            return false;
        }
        return true;
    }

    public void AñadirBonusEspecialRaza(int bon) {
        AñadirBonusEspecial(bon, "raza");
    }

    public void AñadirBonusEspecial(int bon, String tag) {
        boolean exist = false;
        BonusEspecial bonusEspecial;
        bonusEspecial = new BonusEspecial(tag, bon);
        for (int i = 0; i < bonusEspecialesHabilidad.size(); i++) {
            if (bonusEspecialesHabilidad.get(i).motivo.equals(tag)) {
                exist = true;
            }
        }
        if (!exist) {
            bonusEspecialesHabilidad.add(bonusEspecial);
        }
    }

    public boolean ExisteBonusEspecialRaza() {
        for (int i = 0; i < bonusEspecialesHabilidad.size(); i++) {
            if (bonusEspecialesHabilidad.get(i).motivo.equals("raza")) {
                return true;
            }
        }
        return false;
    }

    public void ConfirmarHabilidad() {
        rangos += nuevosRangos;
        rangosEspecializacionAntiguosNiveles = RangosGastadosEnEspecializacion();
        nuevosRangos = 0;
    }

    public boolean EsComun() {
        return comunRaza || comunProfesion || comunCultura || comunAdiestramiento
                || categoriaPadre.esher.pj.DevolverComunTalentoHabilidad(nombre);
    }

    public boolean EsComunCultura() {
        return comunCultura;
    }

    public boolean EsComunProfesion() {
        return comunProfesion;
    }

    public boolean EsComunRaza() {
        return comunRaza;
    }

    public boolean EsComunAdiestramiento() {
        return comunAdiestramiento;
    }

    public boolean EsProfesional() {
        return profesional || profesionalAdiestramiento;
    }

    public boolean EsNormal() {
        return !EsComun() && !EsProfesional() && !EsRestringida();
    }

    public boolean EsRestringida() {
        return restringida || restringidaAdiestramiento || siempreRestringida
                || categoriaPadre.EsRestringida() || DevolverRestringidaTalentos();
    }

    public BonusEspecial ExisteBonusEspecial(String tag) {
        for (int i = 0; i < bonusEspecialesHabilidad.size(); i++) {
            if (bonusEspecialesHabilidad.get(i).motivo.equals(tag)) {
                return bonusEspecialesHabilidad.get(i);
            }
        }
        return null;
    }

    public void LimpiarEspecialesRaza() {
        BonusEspecial bonusEspecial;
        for (int i = 0; i < bonusEspecialesHabilidad.size(); i++) {
            bonusEspecial = bonusEspecialesHabilidad.get(i);
            if (bonusEspecial.motivo.equals("raza")) {
                bonusEspecialesHabilidad.remove(i);
                i--;
            }
        }
    }

    public void ModificaBonusEspecial(String tag, int bonus) {
        boolean existe = false;
        for (int i = 0; i < bonusEspecialesHabilidad.size(); i++) {
            if (bonusEspecialesHabilidad.get(i).motivo.equals(tag)) {
                if (bonus > 0) {
                    bonusEspecialesHabilidad.get(i).bonus = bonus;
                    existe = true;
                } else {
                    bonusEspecialesHabilidad.remove(i);
                }
            }
        }
        if (!existe) {
            BonusEspecial bonusEspecial = new BonusEspecial(tag, bonus);
            bonusEspecialesHabilidad.add(bonusEspecial);
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
     * Devuelve el bonus total de una habilidad.
     */
    public int DevolverValorRangoHabilidad() {
        if (categoriaPadre.TipoCategoria().equals("Estándar")) {
            if (DevolverRangos() == 0) {
                return -15;
            }
            if (DevolverRangos() <= 10) {
                return DevolverRangos() * 3;
            }
            if (DevolverRangos() > 10 && DevolverRangos() <= 20) {
                return (30 + (DevolverRangos() - 10) * 2);
            }
            if (DevolverRangos() > 20 && DevolverRangos() <= 30) {
                return 50 + (DevolverRangos() - 20);
            }
            if (DevolverRangos() > 30) {
                return 60 + (DevolverRangos() - 30) / 2;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("Combinada")) {
            if (DevolverRangos() == 0) {
                return -30;
            }
            if (DevolverRangos() <= 10) {
                return DevolverRangos() * 5;
            }
            if (DevolverRangos() > 10 && DevolverRangos() <= 20) {
                return (50 + (DevolverRangos() - 10) * 3);
            }
            if (DevolverRangos() > 20 && DevolverRangos() <= 25) {
                return 80 + (DevolverRangos() - 20) * 2;
            }
            if (DevolverRangos() > 25 && DevolverRangos() <= 30) {
                return 80 + (int) ((DevolverRangos() - 20) * 1.5);
            }
            if (DevolverRangos() > 30) {
                return 95 + (DevolverRangos() - 30) / 2;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("Limitada")) {
            if (DevolverRangos() == 0) {
                return 0;
            }
            if (DevolverRangos() <= 20) {
                return DevolverRangos();
            }
            if (DevolverRangos() > 20 && DevolverRangos() < 30) {
                return 20 + (DevolverRangos() - 20) / 2;
            }
            if (DevolverRangos() >= 30) {
                return 25;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("Especial")) {
            if (DevolverRangos() == 0) {
                return 0;
            }
            if (DevolverRangos() <= 10) {
                return DevolverRangos() * 6;
            }
            if (DevolverRangos() > 10 && DevolverRangos() <= 20) {
                return (60 + (DevolverRangos() - 10) * 5);
            }
            if (DevolverRangos() > 20 && DevolverRangos() <= 30) {
                return 110 + (DevolverRangos() - 20) * 4;
            }
            if (DevolverRangos() > 30) {
                return 150 + (DevolverRangos() - 30) * 3;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("DF")) {
            String[] CosteRangosDF = categoriaPadre.esher.pj.progresionDesarrolloFisico.split("/");
            if (DevolverRangos() == 0) {
                return Integer.parseInt(CosteRangosDF[0]);
            }
            if (DevolverRangos() > 0 && DevolverRangos() <= 10) {
                return Integer.parseInt(CosteRangosDF[1]) * DevolverRangos();
            }
            if (DevolverRangos() > 10 && DevolverRangos() <= 20) {
                return Integer.parseInt(CosteRangosDF[1]) * 10
                        + Integer.parseInt(CosteRangosDF[2]) * (DevolverRangos() - 10);
            }
            if (DevolverRangos() > 20 && DevolverRangos() <= 30) {
                return Integer.parseInt(CosteRangosDF[1]) * 10
                        + Integer.parseInt(CosteRangosDF[2]) * 10
                        + Integer.parseInt(CosteRangosDF[3]) * (DevolverRangos() - 20);
            }
            if (DevolverRangos() > 30) {
                return Integer.parseInt(CosteRangosDF[1]) * 10
                        + Integer.parseInt(CosteRangosDF[2]) * 10
                        + Integer.parseInt(CosteRangosDF[3]) * 10
                        + Integer.parseInt(CosteRangosDF[4]) * (DevolverRangos() - 30);
            }
        }
        if (categoriaPadre.TipoCategoria().equals("DPP")) {
            String[] CosteRangosDPP = categoriaPadre.esher.pj.progresionPuntosPoder.split("/");
            if (DevolverRangos() == 0) {
                return Integer.parseInt(CosteRangosDPP[0]);
            }
            if (DevolverRangos() > 0 && DevolverRangos() <= 10) {
                return Integer.parseInt(CosteRangosDPP[1]) * DevolverRangos();
            }
            if (DevolverRangos() > 10 && DevolverRangos() <= 20) {
                return Integer.parseInt(CosteRangosDPP[1]) * 10
                        + Integer.parseInt(CosteRangosDPP[2]) * (DevolverRangos() - 10);
            }
            if (DevolverRangos() > 20 && DevolverRangos() <= 30) {
                return Integer.parseInt(CosteRangosDPP[1]) * 10
                        + Integer.parseInt(CosteRangosDPP[2]) * 10
                        + Integer.parseInt(CosteRangosDPP[3]) * (DevolverRangos() - 20);
            }
            if (DevolverRangos() > 30) {
                return Integer.parseInt(CosteRangosDPP[1]) * 10
                        + Integer.parseInt(CosteRangosDPP[2]) * 10
                        + Integer.parseInt(CosteRangosDPP[3]) * 10
                        + Integer.parseInt(CosteRangosDPP[4]) * (DevolverRangos() - 30);
            }
        }

        return -15;
    }

    public int DevolverValorRangoHabilidadEspecializacion() {
        if (categoriaPadre.TipoCategoria().equals("Estándar")) {
            if (DevolverRangosEspecializacion() == 0) {
                return -15;
            }
            if (DevolverRangosEspecializacion() <= 10) {
                return DevolverRangosEspecializacion() * 3;
            }
            if (DevolverRangosEspecializacion() > 10 && DevolverRangosEspecializacion() <= 20) {
                return (30 + (DevolverRangosEspecializacion() - 10) * 2);
            }
            if (DevolverRangosEspecializacion() > 20 && DevolverRangosEspecializacion() <= 30) {
                return 50 + (DevolverRangosEspecializacion() - 20);
            }
            if (DevolverRangosEspecializacion() > 30) {
                return 60 + (DevolverRangosEspecializacion() - 30) / 2;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("Combinada")) {
            if (DevolverRangosEspecializacion() == 0) {
                return -30;
            }
            if (DevolverRangosEspecializacion() <= 10) {
                return DevolverRangosEspecializacion() * 5;
            }
            if (DevolverRangosEspecializacion() > 10 && DevolverRangosEspecializacion() <= 20) {
                return (50 + (DevolverRangosEspecializacion() - 10) * 3);
            }
            if (DevolverRangosEspecializacion() > 20 && DevolverRangosEspecializacion() <= 25) {
                return 80 + (DevolverRangosEspecializacion() - 20) * 2;
            }
            if (DevolverRangosEspecializacion() > 25 && DevolverRangosEspecializacion() <= 30) {
                return 80 + (int) ((DevolverRangosEspecializacion() - 20) * 1.5);
            }
            if (DevolverRangosEspecializacion() > 30) {
                return 95 + (DevolverRangosEspecializacion() - 30) / 2;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("Limitada")) {
            if (DevolverRangosEspecializacion() == 0) {
                return 0;
            }
            if (DevolverRangosEspecializacion() <= 20) {
                return DevolverRangosEspecializacion();
            }
            if (DevolverRangosEspecializacion() > 20 && DevolverRangosEspecializacion() < 30) {
                return 20 + (DevolverRangosEspecializacion() - 20) / 2;
            }
            if (DevolverRangosEspecializacion() >= 30) {
                return 25;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("Especial")) {
            if (DevolverRangosEspecializacion() == 0) {
                return 0;
            }
            if (DevolverRangosEspecializacion() <= 10) {
                return DevolverRangosEspecializacion() * 6;
            }
            if (DevolverRangosEspecializacion() > 10 && DevolverRangosEspecializacion() <= 20) {
                return (60 + (DevolverRangosEspecializacion() - 10) * 5);
            }
            if (DevolverRangosEspecializacion() > 20 && DevolverRangosEspecializacion() <= 30) {
                return 110 + (DevolverRangosEspecializacion() - 20) * 4;
            }
            if (DevolverRangosEspecializacion() > 30) {
                return 150 + (DevolverRangosEspecializacion() - 30) * 3;
            }
        }
        if (categoriaPadre.TipoCategoria().equals("DF")) {
            String[] CosteRangosDF = categoriaPadre.esher.pj.progresionDesarrolloFisico.split("/");
            if (DevolverRangosEspecializacion() == 0) {
                return Integer.parseInt(CosteRangosDF[0]);
            }
            if (DevolverRangosEspecializacion() > 0 && DevolverRangosEspecializacion() <= 10) {
                return Integer.parseInt(CosteRangosDF[1]) * DevolverRangosEspecializacion();
            }
            if (DevolverRangosEspecializacion() > 10 && DevolverRangosEspecializacion() <= 20) {
                return Integer.parseInt(CosteRangosDF[1]) * 10
                        + Integer.parseInt(CosteRangosDF[2]) * (DevolverRangosEspecializacion() - 10);
            }
            if (DevolverRangosEspecializacion() > 20 && DevolverRangosEspecializacion() <= 30) {
                return Integer.parseInt(CosteRangosDF[1]) * 10
                        + Integer.parseInt(CosteRangosDF[2]) * 10
                        + Integer.parseInt(CosteRangosDF[3]) * (DevolverRangosEspecializacion() - 20);
            }
            if (DevolverRangosEspecializacion() > 30) {
                return Integer.parseInt(CosteRangosDF[1]) * 10
                        + Integer.parseInt(CosteRangosDF[2]) * 10
                        + Integer.parseInt(CosteRangosDF[3]) * 10
                        + Integer.parseInt(CosteRangosDF[4]) * (DevolverRangosEspecializacion() - 30);
            }
        }
        if (categoriaPadre.TipoCategoria().equals("DPP")) {
            String[] CosteRangosDPP = categoriaPadre.esher.pj.progresionPuntosPoder.split("/");
            if (DevolverRangosEspecializacion() == 0) {
                return Integer.parseInt(CosteRangosDPP[0]);
            }
            if (DevolverRangosEspecializacion() > 0 && DevolverRangosEspecializacion() <= 10) {
                return Integer.parseInt(CosteRangosDPP[1]) * DevolverRangosEspecializacion();
            }
            if (DevolverRangosEspecializacion() > 10 && DevolverRangosEspecializacion() <= 20) {
                return Integer.parseInt(CosteRangosDPP[1]) * 10
                        + Integer.parseInt(CosteRangosDPP[2]) * (DevolverRangosEspecializacion() - 10);
            }
            if (DevolverRangosEspecializacion() > 20 && DevolverRangosEspecializacion() <= 30) {
                return Integer.parseInt(CosteRangosDPP[1]) * 10
                        + Integer.parseInt(CosteRangosDPP[2]) * 10
                        + Integer.parseInt(CosteRangosDPP[3]) * (DevolverRangosEspecializacion() - 20);
            }
            if (DevolverRangosEspecializacion() > 30) {
                return Integer.parseInt(CosteRangosDPP[1]) * 10
                        + Integer.parseInt(CosteRangosDPP[2]) * 10
                        + Integer.parseInt(CosteRangosDPP[3]) * 10
                        + Integer.parseInt(CosteRangosDPP[4]) * (DevolverRangosEspecializacion() - 30);
            }
        }

        return -15;
    }

    public int DevolverRangos() {
        if (EsRestringida()) {
            return (rangos + nuevosRangos) / 2 + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
        }
        if (EsProfesional()) {
            return (rangos - RangosGastadosEnEspecializacion() + nuevosRangos) * 3 + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
        }
        if (EsComun()) {
            return (rangos - RangosGastadosEnEspecializacion() + nuevosRangos) * 2 + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
        }
        return rangos - RangosGastadosEnEspecializacion() + rangosAficiones + rangosCultura + nuevosRangos + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
    }

    public int DevolverRangosEspecializacion() {
        if (categoriaPadre.DevolverRangos() > 0) {
            return DevolverRangos() * 2;
        } else {
            return (int) (DevolverRangos() * 1.5);
        }
    }

    /**
     * Calcula aquellos rangos comprados sin tener en cuenta las modificaciones
     * por habilidades comunes o restringidas
     *
     * @return
     */
    public int DevolverRangosSubidos() {
        if (EsRestringida()) {
            return (rangos + nuevosRangos) / 2;
        }
        if (EsProfesional()) {
            return (rangos + nuevosRangos) * 3;
        }
        if (EsComun()) {
            return (rangos + nuevosRangos) * 2;
        }
        return rangos + nuevosRangos;

    }

    public int DevolverAntiguosRangos() {
        if (!generalizada) {
            if (EsRestringida()) {
                return (rangos) / 2 + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
            }
            if (EsProfesional()) {
                return (rangos - rangosEspecializacionAntiguosNiveles) * 3 + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
            }
            if (EsComun()) {
                return (rangos - rangosEspecializacionAntiguosNiveles) * 2 + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
            }
            return rangos - rangosEspecializacionAntiguosNiveles + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
        } else {
            if (EsProfesional() || EsComun()) {
                return rangos + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
            }
            return (rangos) / 2 + rangosAficiones + rangosCultura + rangosAdiestramiento + rangosInsertados + DevolverRangosCulturaHechizos();
        }
    }

    public int DevolverBonusEspeciales() {
        int total = 0;
        for (int i = 0; i < bonusEspecialesHabilidad.size(); i++) {
            total += bonusEspecialesHabilidad.get(i).bonus;
        }
        if (historial) {
            total += 10;
        }
        total += DevolverBonusTalentos();
        return total;
    }

    public int DevolverBonusTalentos() {
        return categoriaPadre.esher.pj.DevolverBonusTalentoHabilidad(nombre);
    }

    public int DevolverBonusTemporalTalentos() {
        return categoriaPadre.esher.pj.DevolverBonusTalentoEspecialHabilidad(nombre);
    }

    boolean DevolverRestringidaTalentos() {
        for (int i = 0; i < categoriaPadre.esher.pj.talentos.size(); i++) {
            for (int j = 0; j < categoriaPadre.esher.pj.talentos.get(i).bonusHabilidad.size(); j++) {
                if (categoriaPadre.esher.pj.talentos.get(i).bonusHabilidad.get(j).nombre.equals(nombre)) {
                    if (categoriaPadre.esher.pj.talentos.get(i).bonusHabilidad.get(j).restringida) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    int DevolverBonusObjetos() {
        int total = 0;
        for (int i = 0; i < categoriaPadre.esher.pj.objetosMagicos.size(); i++) {
            total += categoriaPadre.esher.pj.objetosMagicos.get(i).DevolverBonusHabilidad(this);
        }
        return total;
    }

    public int DevolverBonuses() {
        return DevolverBonusEspeciales() + DevolverBonusObjetos();
    }

    public int Total() {
        return DevolverValorRangoHabilidad() + categoriaPadre.Total()
                + DevolverBonuses();
    }

    public int TotalEspecializacion() {
        return DevolverValorRangoHabilidadEspecializacion() + categoriaPadre.Total()
                + DevolverBonuses();
    }

    public int DevolverRangosCulturaHechizos() {
        try {
            if (categoriaPadre.DevolverNombre().equals("Listas Abiertas de Hechizos")) {
                if (nombre.equals(categoriaPadre.esher.pj.hechizoCultura.nombre)) {
                    return categoriaPadre.esher.pj.hechizoCultura.rangos;
                }
            }
        } catch (NullPointerException npe) {
        }
        return 0;
    }

    /**
     * Almacena un bonus especial a una categoría.
     */
    class BonusEspecial implements Serializable {

        String motivo;
        int bonus;

        public BonusEspecial(String mot, int bon) {
            motivo = mot;
            bonus = bon;
        }
    }
}

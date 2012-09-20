/*
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class LeerCultura {

    private Esher esher;

    public LeerCultura(Esher tmp_esher) {
        try {
            esher = tmp_esher;
            LeerFicheroCultura();
        } catch (Exception ex) {
            Logger.getLogger(LeerRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LeerFicheroCultura() throws Exception {
        int lineaLeida = 0;
        BorrarCulturaAnterior();
        String ficheroCultura = esher.BuscarDirectorioModulo(esher.directorioRolemaster.DIRECTORIO_CULTURAS + File.separator + esher.pj.cultura + ".txt");
        List<String> lines = esher.directorioRolemaster.LeerLineasCultura(ficheroCultura);
        lineaLeida = AsignarArmasCultura(lines, lineaLeida);
        lineaLeida = AsignarArmadurasCultura(lines, lineaLeida);
        lineaLeida = AsignarRangosCulturaACategoríasYHabilidades(lines, lineaLeida);
        lineaLeida = AsignarRangosAficiones(lines, lineaLeida);
        lineaLeida = AsignarHabilidadesAficionesCultura(lines, lineaLeida);
        lineaLeida = AsignarListaIdiomasCultura(lines, lineaLeida);
    }

    /**
     * Asigna los rangos culturales a una habilidad.
     */
    private void AsignarRangosCulturaAHabilidades(Categoria categoriaCultura, String nombreHabilidad, int rangos) {
        if (nombreHabilidad.equals("Idiomas")) {
            esher.pj.puntosIdiomaCultura = rangos;
        } else {
            if (nombreHabilidad.equals("Arma")) {
                esher.pj.armas.AsignarRangosCulturaTipoArma(categoriaCultura.DevolverNombre().substring(6), rangos);
            } else {
                if (rangos > 0) {
                    Habilidad hab = categoriaCultura.DevolverHabilidadDeNombre(nombreHabilidad);
                    hab.rangosCultura = rangos;
                }
            }
        }
    }

    /**
     * Asigna los rangos culturales a una categoria.
     */
    private Categoria AsignarRangosCulturaACategorias(String nombreCategoria, int rangosCultura) {
        Categoria categoriaCultura = null;
        try {
            categoriaCultura = esher.pj.DevolverCategoriaDeNombre(nombreCategoria);
            categoriaCultura.rangosCultura = rangosCultura;
        } catch (NullPointerException npe) {
            new MostrarError("¡Atención! categoría " + nombreCategoria + " no encontrada.", "Leer Cultura " + esher.pj.cultura);
        }
        return categoriaCultura;
    }

    /**
     * Asigna los rangos de la cultura a las categorías y habilidades del personaje.
     */
    private int AsignarRangosCulturaACategoríasYHabilidades(List<String> lines, int index) throws Exception {
        Categoria categoriaCultura = null;
        index += 3;
        try {
            while (!lines.get(index).equals("")) {
                String lineaCultura = lines.get(index);
                String[] vectorCultura = lineaCultura.split("\t");
                String nombre = vectorCultura[0];
                int rangosCultura = Integer.parseInt(vectorCultura[1]);
                if (!nombre.startsWith("  *  ")) {
                    categoriaCultura = AsignarRangosCulturaACategorias(nombre, rangosCultura);
                } else {
                    if (nombre.contains("  *  Lista de Hechizos")) {
                        esher.pj.rangosHechizosCultura = rangosCultura;
                    } else {
                        try {
                            nombre = nombre.replace("  *  ", "");
                            AsignarRangosCulturaAHabilidades(categoriaCultura, nombre, rangosCultura);
                        } catch (NullPointerException npe) {
                            new MostrarError("¡Atención! habilidad cultural " + nombre + " no encontrada.", "Leer Cultura");
                        }
                    }
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iobe) {
        }
        esher.pj.armas.AsignarRangosArmaCulturaPorDefecto();
        return index;
    }

    private int AsignarRangosAficiones(List<String> lines, int index) {
        index += 3;
        try {
            while (!lines.get(index).equals("")) {
                String lineaAficiones = lines.get(index);
                esher.pj.rangosAficiones = Integer.parseInt(lineaAficiones);
                index++;
            }
        } catch (IndexOutOfBoundsException iof) {
        }
        return index;
    }

    /**
     * Genera un listado aleatorio de armas de una cultura.
     */
    private int AsignarArmasCultura(List<String> lines, int index) throws Exception {
        index += 2;
        try {
            List<String> armasLeidasCultura = new ArrayList<String>();
            if (!lines.get(index).equals("Todas")) {
                while (!lines.get(index).equals("")) {
                    String lineaArmasCultura = lines.get(index);
                    String[] arrayArmas = lineaArmasCultura.split(", ");
                    for (int i = 0; i < arrayArmas.length; i++) {
                        armasLeidasCultura.add(arrayArmas[i]);
                    }
                    index++;
                }
            } else {
                armasLeidasCultura = esher.pj.armas.ListarTodasArmas();
                index++;
            }
            esher.pj.armas.AsignarArmasCultura(armasLeidasCultura);
        } catch (IndexOutOfBoundsException iof) {
        }
        esher.pj.armas.armasCultura.BarajarArmasCultura();
        return index;
    }

    private int AsignarArmadurasCultura(List<String> lines, int index) {
        index += 3;
        esher.pj.armadurasCultura = new ArrayList<String>();
        try {
            while (!lines.get(index).equals("")) {
                String lineaArmadurasCultura = lines.get(index);
                String[] arrayArmaduras = lineaArmadurasCultura.split(", ");
                for (int i = 0; i < arrayArmaduras.length; i++) {
                    esher.pj.armadurasCultura.add(arrayArmaduras[i]);
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarListaIdiomasCultura(List<String> lines, int index) {
        index += 3;
        esher.pj.idiomasCultura = new IdiomasAdolescencia();
        while (!lines.get(index).equals("")) {
            try {
                String lineaIdiomas = lines.get(index);
                String[] datosIdioma = lineaIdiomas.split("\t");
                String[] culturaIdioma = datosIdioma[1].split("/");
                IdiomaCultura lengua = new IdiomaCultura(datosIdioma[0], 0, 0,
                        Integer.parseInt(culturaIdioma[0]), Integer.parseInt(culturaIdioma[1]));
                esher.pj.idiomasCultura.Add(lengua);
                index++;
            } catch (ArrayIndexOutOfBoundsException aiob) {
                aiob.printStackTrace();
            }
        }
        //Fusionamos los idiomas de Raza con los de Cultura.
        for (int i = 0; i < esher.pj.idiomasRaza.Size(); i++) {
            IdiomaCultura id = esher.pj.idiomasRaza.Get(i);
            boolean exist = false;
            for (int j = 0; j < esher.pj.idiomasCultura.Size(); j++) {
                if (esher.pj.idiomasCultura.Get(j).nombre.equals(id.nombre)) {
                    exist = true;
                }
            }
            if (!exist) {
                esher.pj.idiomasCultura.Add(id);
            }
        }
        return index;
    }

    public int DevolverPuntosIdiomaCultura() {
        int puntosIdiomasCulturaGastados = 0;
        for (int i = 0; i < esher.pj.idiomasCultura.Size(); i++) {
            IdiomaCultura idi = esher.pj.idiomasCultura.Get(i);
            puntosIdiomasCulturaGastados += idi.rangosNuevosEscritos;
            puntosIdiomasCulturaGastados += idi.rangosNuevosHablado;
        }
        return esher.pj.puntosIdiomaCultura + esher.pj.puntosIdiomaRaza - puntosIdiomasCulturaGastados;
    }

    private int AsignarHabilidadesAficionesCultura(List<String> lines, int index) {
        index += 3;
        esher.pj.listaAficiones = new ArrayList<String>();
        try {
            while (!lines.get(index).equals("")) {
                String lineaAficiones = lines.get(index);
                String[] vectorAficiones = lineaAficiones.split(", ");
                for (int i = 0; i < vectorAficiones.length; i++) {
                    if (vectorAficiones[i].length() > 0) {
                        Categoria cat;
                        if ((cat = esher.pj.DevolverCategoriaDeNombre(vectorAficiones[i].trim())) != null) {
                            esher.pj.listaAficiones.addAll(cat.DevolverNombreHabilidades());
                        } else if (esher.pj.DevolverHabilidadDeNombre(vectorAficiones[i].trim()) != null) {
                            esher.pj.listaAficiones.add(vectorAficiones[i].trim());
                        } else {
                            if (vectorAficiones[i].trim().equals("Arrojadizas")) {
                                esher.pj.listaAficiones.addAll(esher.pj.armas.LeerArmasFichero("Arrojadizas.txt"));
                            } else {
                                if (vectorAficiones[i].trim().equals("Arma")) {
                                    try {
                                        esher.pj.listaAficiones.addAll(esher.pj.armas.SeleccionarArmasValidasDeCultura());
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    if (vectorAficiones[i].trim().equals("Armadura")) {
                                        try {
                                            for (int k = 0; k < esher.pj.armadurasCultura.size(); k++) {
                                                esher.pj.listaAficiones.add(esher.pj.armadurasCultura.get(k));
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    } else {
                                        if (vectorAficiones[i].trim().equals("Lista de Hechizos")) {
                                            try {
                                                cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
                                                for (int h = 0; h < cat.listaHabilidades.size(); h++) {
                                                    esher.pj.listaAficiones.add(cat.listaHabilidades.get(h).DevolverNombre());
                                                }
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        } else {
                                            if (vectorAficiones[i].length() > 0) {
                                                new MostrarError("Afición " + vectorAficiones[i] + " no encontrada.", "Leer Cultura");

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    public int DevolverPuntosAficiones() {
        int puntosAficionesGastados = 0;
        for (int i = 0; i < esher.pj.categorias.size(); i++) {
            Categoria cat = esher.pj.categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                puntosAficionesGastados += hab.rangosAficiones;
            }
        }
        return esher.pj.rangosAficiones - puntosAficionesGastados;
    }

    public void BorrarCulturaAnterior() {
        esher.pj.idiomasCultura = new IdiomasAdolescencia();
        try {
            esher.pj.armas.BorrarArmasCultura();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < esher.pj.categorias.size(); i++) {
            Categoria cat = esher.pj.categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                hab.rangosAficiones = 0;
                hab.rangosCultura = 0;
                hab.NoEsComunCultura();
            }
        }
    }

    void ActualizarOrdenCostesArmas() {
        int[] coste = new int[3];
        for (int i = 0; i < esher.pj.armas.DevolverTotalTiposDeArmas(); i++) {
            String tarma = esher.pj.armas.DevolverTipoDeArma(i);
            Categoria cat = esher.pj.DevolverCategoriaDeNombre("Armas·" + tarma);
            try {
                coste = esher.pj.costearmas.DevolverCosteRango(i);
                //Hay mas armas que costes asignados, nos inventamos los nuevos.
            } catch (IndexOutOfBoundsException iobe) {
                coste[0] = 20;
                coste[1] = 1000;
                coste[2] = 1000;
            }
            cat.CambiarCosteRango(coste);
        }
    }
}

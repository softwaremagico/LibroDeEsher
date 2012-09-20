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
public class LeerProfesion {

    private Esher esher;
    private boolean creandoPJ = true;
    private boolean interactivo = true;

   public  LeerProfesion(Esher tmp_esher) {
        try {
            esher = tmp_esher;
            LeerFicheroProfesion();
        } catch (Exception ex) {
            Logger.getLogger(LeerProfesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public  LeerProfesion(Esher tmp_esher, boolean tmp_creandoPJ) {
        creandoPJ = tmp_creandoPJ;
        try {
            esher = tmp_esher;
            LeerFicheroProfesion();
        } catch (Exception ex) {
            Logger.getLogger(LeerProfesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public  LeerProfesion(Esher tmp_esher, boolean tmp_creandoPJ, boolean tmp_interactivo) {
        creandoPJ = tmp_creandoPJ;
        interactivo = tmp_interactivo;
        try {
            esher = tmp_esher;
            LeerFicheroProfesion();
        } catch (Exception ex) {
            Logger.getLogger(LeerProfesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LeerFicheroProfesion() throws Exception {
        int lineaLeida = 2;

        LimpiarAntiguaProfesion();
        String ficheroProfesion = esher.BuscarDirectorioModulo(esher.directorioRolemaster.DIRECTORIO_PROFESION + File.separator + esher.pj.profesion + ".txt");
        if (ficheroProfesion.length() > 0) {
            List<String> lines = esher.directorioRolemaster.LeerLineasProfesion(ficheroProfesion);
            lineaLeida = AsignarCaracteristicasBasicas(lines, lineaLeida);
            lineaLeida = AsignarReinosDisponiblesPorProfesión(lines, lineaLeida);
            lineaLeida = AsignarBonificacionPorProfesion(lines, lineaLeida);
            lineaLeida = AsignarCostesCategorias(lines, lineaLeida);
            lineaLeida = AsignarHabilidadesComunes(lines, lineaLeida);
            lineaLeida = AsignarHabilidadesProfesionales(lines, lineaLeida);
            lineaLeida = AsignarHabilidadesRestringidas(lines, lineaLeida);
            Magia magia = new Magia(esher);
            magia.PrepararCostesListas();
            lineaLeida = magia.AsignarCostesHechizos(lines, lineaLeida);
            lineaLeida = AsignarCostesAdiestramiento(lines, lineaLeida);
            magia.AsignarCostesListasAdiestramiento();
            OtrosProfesion();
        }
    }

    /**
     * Asigna todos los bonuses a las categorias típicas de la profesión escogida.
     */
    private int AsignarBonificacionPorProfesion(List<String> lines, int index) {
        index += 3;

        try {
            while (!lines.get(index).equals("")) {
                String lineaCategoriaProfesional = lines.get(index);
                String[] vectorCategoriaProfesional = lineaCategoriaProfesional.split("\t");
                String nombre = vectorCategoriaProfesional[0];
                int bonus = Integer.parseInt(vectorCategoriaProfesional[1]);

                try {
                    Categoria categoriaProfesional = esher.pj.DevolverCategoriaDeNombre(nombre);
                    categoriaProfesional.bonusProfesion = bonus;
                } catch (NullPointerException npe) {
                    new MostrarError("Bonus de " + nombre + " en " + esher.pj.profesion + ".txt mal definido.", "Leer Profesion");
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarReinosDisponiblesPorProfesión(List<String> lines, int index) {
        index += 4;
        esher.pj.reinosDeProfesion = new ArrayList<String>();
        try {
            while (!lines.get(index).equals("")) {
                String lineaReino = lines.get(index);
                esher.pj.reinosDeProfesion.add(lineaReino);
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarCaracteristicasBasicas(List<String> lines, int index) {
        if (esher.pj.nivel == 1 && creandoPJ) {
            String preferenciasCaracteristicas = lines.get(index);
            if (!lines.get(index).equals("Indiferente")) {
                esher.pj.arrayCaracteristicasProfesion = preferenciasCaracteristicas.split(" ");
                //No cambiamos los datos si los puntos han sido ya asignados y son validos para esa profesion.
                if (esher.pj.ObtenerPuntosCaracteristicasGastados() < esher.pj.caracteristicas.totalCaracteristicas
                        || !(esher.pj.caracteristicas.DevolverCaracteristicaDeAbreviatura(esher.pj.arrayCaracteristicasProfesion[0]).ObtenerPuntosTemporal() >= 90)
                        && !(esher.pj.caracteristicas.DevolverCaracteristicaDeAbreviatura(esher.pj.arrayCaracteristicasProfesion[1]).ObtenerPuntosTemporal() >= 90)) {
                    for (int i = 0; i < esher.pj.arrayCaracteristicasProfesion.length; i++) {
                        String car = esher.pj.arrayCaracteristicasProfesion[i];
                        try {
                            Caracteristica caract = esher.pj.caracteristicas.DevolverCaracteristicaDeAbreviatura(car);
                            caract.CrearPuntosTemporal(esher.baseCaracteristicas[i]);
                        } catch (NullPointerException npe) {
                            new MostrarError("Caracteristica " + car + " mostrada en el archivo " + esher.pj.profesion + ".txt no existente.", "Leer Profesion");
                        }
                    }
                }
            } else {
                if (esher.pj.ObtenerPuntosCaracteristicasGastados() < esher.pj.caracteristicas.totalCaracteristicas) {
                    List<Integer> listaEnteros = esher.ObtenerListaAleatoriaDeEnteros(10);
                    int i = 0;
                    Caracteristica car = null;
                    try {
                        for (int j = 0; j < esher.pj.caracteristicas.Size(); j++) {
                            car = esher.pj.caracteristicas.Get(j);
                            car.CrearPuntosTemporal(esher.baseCaracteristicas[listaEnteros.get(i)]);
                            i++;
                        }
                    } catch (NullPointerException npe) {
                        new MostrarError("Fallo al intentar asignar las caracteristicas de profesion de forma aleatoria.", "Leer Profesion");
                    }
                }
            }
        }
        return index++;
    }

    private int AsignarCostesCategorias(List<String> lines, int index) {
        index += 3;

        esher.pj.costearmas = new CosteArmas(esher);
        esher.pj.armas.ResetearCuentaArmas();
        while (!lines.get(index).equals("")) {
            String lineaCategoria = lines.get(index);
            String[] vectorCategoria = lineaCategoria.split("\t");
            String nombre = vectorCategoria[0];
            //Las armas tienen nombres diversos, se ordenan según las preferencias del personaje.
            if (nombre.startsWith("Armas·")) {
                esher.pj.costearmas.AñadirCosteRango(esher.pj.ConvertirStringCosteEnIntCoste(vectorCategoria[1]));
                nombre = "Armas·" + esher.pj.armas.ObtenerSiguienteArmaPreferida();
            } else {
                Categoria categoriaProfesional = esher.pj.DevolverCategoriaDeNombre(nombre);
                try {
                    categoriaProfesional.CambiarCosteRango(esher.pj.ConvertirStringCosteEnIntCoste(vectorCategoria[1]));
                } catch (NullPointerException npe) {
                    new MostrarError("Categoría desconocida: " + nombre, "Leer Profesion");

                } catch (ArrayIndexOutOfBoundsException aiob) {
                    new MostrarError("Categoría mal definida: " + nombre, "Leer Profesion");
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarHabilidadesEspeciales(List<String> lines, int index,
            String tipo) {
        index += 3;
        Habilidad hab;

        while (!lines.get(index).equals("")) {
            String lineaHabilidad = lines.get(index);

            String[] vectorHabilidades = lineaHabilidad.split(", ");
            for (int i = 0; i < vectorHabilidades.length; i++) {
                //Si es un grupo de caracteristicas para elegir, selecciona una.
                if (vectorHabilidades[i].startsWith("{") && creandoPJ) {
                    vectorHabilidades[i] = esher.pj.SeleccionarNombreHabilidadDeListado(vectorHabilidades[i].replace("}", "").replace("{", ""), tipo, "profesion");
                }
                if (!vectorHabilidades[i].equals("Ninguna")) {
                    try {
                        hab = esher.pj.DevolverHabilidadDeNombre(vectorHabilidades[i]);
                        if (tipo.equals("Común")) {
                            hab.HacerComunProfesion();
                        }
                        if (tipo.equals("Profesional")) {
                            hab.HacerProfesional();
                        }
                        if (tipo.equals("Restringida")) {
                            hab.HacerRestringida();
                        }
                    } catch (NullPointerException npe) {
                        //Puede ser una habilidad de un categoria.
                        if (creandoPJ && interactivo) {
                            if (!esher.pj.SeleccionarGrupoHabilidadesEspeciales(tipo, vectorHabilidades[i], "profesion")) {
                                new MostrarError("Habilidad desconocida: " + vectorHabilidades[i], "Leer Profesion");
                            }
                        }
                    }
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarHabilidadesComunes(List<String> lines, int index)
            throws Exception {
        return AsignarHabilidadesEspeciales(lines, index, "Común");
    }

    private int AsignarHabilidadesProfesionales(List<String> lines, int index)
            throws Exception {
        return AsignarHabilidadesEspeciales(lines, index, "Profesional");
    }

    private int AsignarHabilidadesRestringidas(List<String> lines, int index)
            throws Exception {
        return AsignarHabilidadesEspeciales(lines, index, "Restringida");
    }

    private int AsignarCostesAdiestramiento(List<String> lines, int index) {
        index += 3;
        esher.pj.BorraAntiguosCostesAdiestramiento();
        while (!lines.get(index).equals("")) {
            while (!lines.get(index).equals("")) {
                String lineaAdiestramiento = lines.get(index);
                String[] vectorAdiestramiento = lineaAdiestramiento.split("\t");
                if (vectorAdiestramiento[1].contains("+")) {
                    esher.pj.costesAdiestramientos.AñadirAdiestramientoPreferido(vectorAdiestramiento[0],
                            Integer.parseInt(vectorAdiestramiento[1].replace("+", "")));
                } else {
                    if (vectorAdiestramiento[1].contains("-")) {
                        esher.pj.costesAdiestramientos.AñadirAdiestramientoProhibido(vectorAdiestramiento[0],
                                Integer.parseInt(vectorAdiestramiento[1].replaceAll("-", "")));
                    } else {
                        esher.pj.costesAdiestramientos.AñadirAdiestramiento(vectorAdiestramiento[0],
                                Integer.parseInt(vectorAdiestramiento[1]));
                    }
                }
                index++;
            }
        }
        return index;
    }

    private void LimpiarAntiguaProfesion() {
        for (int i = 0; i < esher.pj.categorias.size(); i++) {
            Categoria cat = esher.pj.categorias.get(i);
            cat.bonusProfesion = 0;
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                hab.NoEsComunProfesion();
                hab.NoEsProfesional();
            }
        }
    }

    private void OtrosProfesion() {
        //Los elementalistas tienen categorías particulares de hechizos.
        if (esher.pj.profesion.contains("Elementalista")) {
            try {
                Categoria cat = new Categoria("Listas Básicas de la Tríada", "ListTri", "Em", "Limitada", "", esher);
                esher.pj.AñadirCategoria(cat);
                cat = new Categoria("Listas Básicas Elementales Complementarias", "LisElCom", "Em", "Limitada", "", esher);
                esher.pj.AñadirCategoria(cat);
            } catch (Exception ex) {
                Logger.getLogger(LeerProfesion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        esher.pj.OrdenarCategorias();
    }
}

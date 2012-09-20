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

import java.util.ArrayList;
import java.util.List;

public class Magia {

    Esher esher;

    public Magia(Esher tmp_esher) {
        esher = tmp_esher;
    }

    /**
     * Transforma un texto en objetos hechizos.
     */
    private void ObtenerListaHechizosDisponibles(List<String> lines, String reino) {
        try {
            for (int i = 0; i < lines.size(); i++) {
                String lineaHechizos = lines.get(i);
                if (!lineaHechizos.equals("")) {
                    String[] vectorHechizos = lineaHechizos.split("\t");
                    String nombreHechizo = vectorHechizos[0];
                    String claseHechizo = vectorHechizos[1];
                    esher.pj.AddHechizo(new ListaDeHechizos(esher.pj, nombreHechizo, claseHechizo, reino));
                }
            }
        } catch (IndexOutOfBoundsException iobe) {
            iobe.printStackTrace();
        }
    }

    /**
     * Obtiene el reino que puede seleccionar el PJ de acuerdo a su profesión
     * y a los disponibles en el programa.
     */
    public List<String> ObtenerReinoDisponible() throws Exception {
        List<String> reinosExistentes = ReinosDeMagiaDisponibles();
        List<String> reinosPosibles = new ArrayList<String>();
        for (int i = 0; i < reinosExistentes.size(); i++) {
            String reinoDisponible = reinosExistentes.get(i);
            //Si es híbrido de dos reinos.
            if (reinoDisponible.contains("/")) {
                String[] dosReinos = reinoDisponible.split("/");
                //El orden de los dos reinos no importa.
                if (esher.pj.reinosDeProfesion.contains(dosReinos[0] + "/" + dosReinos[1])
                        || esher.pj.reinosDeProfesion.contains(dosReinos[1] + "/" + dosReinos[0])) {
                    reinosPosibles.add(reinoDisponible);
                }
            } else {
                //Reinos simples.
                if (esher.pj.reinosDeProfesion.contains(reinoDisponible)) {
                    reinosPosibles.add(reinoDisponible);
                }
            }
        }
        return reinosPosibles;
    }

    private void ResetCategoriasHechizos() {
        Categoria cat = null;
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas de otros Reinos");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Cerradas de otros Reinos");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de otras Profesiones");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        if (esher.hechizosAdiestramientoOtrosReinosPermitidos) {
            cat = esher.pj.DevolverCategoriaDeNombre("Listas Hechizos de Adiestramientos de Otro Reino");
            cat.listaHabilidades = new ArrayList<Habilidad>();
        }
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de la Tríada");
        cat.listaHabilidades = new ArrayList<Habilidad>();
        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas Elementales Complementarias");
        cat.listaHabilidades = new ArrayList<Habilidad>();
    }

    private void AñadirHechizosPorProfesion() throws Exception {
        ResetCategoriasHechizos();
        for (int i = 0; i < esher.pj.listaHechizos.size(); i++) {
            Categoria cat = null;
            ListaDeHechizos hechizo = esher.pj.listaHechizos.get(i);

            //Hay listas compartidas por varias profesiones.
            String[] vectorClase = hechizo.clase.split("/");
            List<String> listaClase = new ArrayList<String>();
            for (int j = 0; j < vectorClase.length; j++) {
                listaClase.add(vectorClase[j]);
            }

            if (listaClase.contains("Abierta")) {
                if (esher.pj.reino.contains(hechizo.reino)) {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
                } else if ((hechizo.reino.equals("Arcana"))) {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
                } else {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas de otros Reinos");
                }
            } else if (listaClase.contains("Cerrada")) {
                if (esher.pj.reino.contains(hechizo.reino)) {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");
                } else {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Cerradas de otros Reinos");
                }
            } else if (listaClase.contains(esher.pj.profesion)) {
                //Listas basica de esta profesion.
                cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
            } else {
                //Listas básicas de otras profesiones.
                List<String> profesiones = esher.ProfesionesDisponibles();
                profesiones.addAll(esher.AdiestramientosDisponibles());
                //Consideramos las listas malignas como listas de otras profesiones si no está seleccionada la opción adecuada.. 
                if ((esher.hechizosMalignos) && (listaClase.contains("Esencia Maligna") || listaClase.contains("Canalización Maligna")
                        || listaClase.contains("Mentalismo Maligno"))) {
                    if (esher.pj.reino.contains(hechizo.reino)) {
                        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
                    } else {
                        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
                    }
                } else if (listaClase.contains("Esencia Maligna") || listaClase.contains("Canalización Maligna")
                        || listaClase.contains("Mentalismo Maligno")
                        || ElementoEnComun(profesiones, listaClase) && !listaClase.contains(esher.pj.profesion)) {
                    if (esher.pj.reino.contains(hechizo.reino)) {
                        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de otras Profesiones");
                    } else {
                        cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
                    }
                }
            }

            if (listaClase.contains(esher.pj.raza)) {
                if (esher.pj.EsHechicero()) {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
                } else {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
                }
            } else {
                //Las listas de otras razas
                List<String> razas = esher.RazasDisponibles();
                if (ElementoEnComun(razas, listaClase) && !listaClase.contains(esher.pj.raza)) {
                    cat = esher.pj.DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
                } else {
                    //Si la raza no existe, no admitimos sus hechizos.
                }
            }

            //Finalmente añadimos la lista a la categoría seleccionada previamente.
            try {
                if (!cat.ExisteHabilidad(hechizo.nombre)) {
                    Habilidad hab = new Habilidad(cat, hechizo.nombre);
                    cat.AddHabilidad(hab);
                }
            } catch (NullPointerException npe) {
            }
        }
    }

    void PrepararCostesListas() {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 5; i++) {
                esher.pj.listasBasicas[i][j] = 1000;
                esher.pj.listasAbiertas[i][j] = 1000;
                esher.pj.listasCerradas[i][j] = 1000;
                esher.pj.listasOtros[i][j] = 1000;
                esher.pj.listasAbiertasOtros[i][j] = 1000;
                esher.pj.listasCerradasOtros[i][j] = 1000;
                esher.pj.listasBasicasOtros[i][j] = 1000;
                esher.pj.listasAbiertasArcanas[i][j] = 1000;
                esher.pj.listasElementalesComplementarias[i][j] = 1000;
                esher.pj.listasTriada[i][j] = 1000;
                esher.pj.listasPropiasAdiestramientos[i][j] = 1000;
                esher.pj.listasOtrasAdiestramientos[i][j] = 1000;
            }
        }
    }

    private List<String> GenerarReinosMixtos(List<String> reinosDeMagia) {
        List<String> aux = new ArrayList<String>();
        for (int i = 0; i < reinosDeMagia.size(); i++) {
            String reinoSeleccionado = reinosDeMagia.get(i);
            for (int j = i + 1; j < reinosDeMagia.size(); j++) {
                aux.add(reinoSeleccionado + "/" + reinosDeMagia.get(j));
            }
        }
        reinosDeMagia.addAll(aux);
        return reinosDeMagia;
    }

    void SeleccionarReinoDeMagiaParaPersonaje(String reinoElegido) {
        String[] todosReinos = reinoElegido.split("/");
        esher.pj.reinos.clear();
        for (int i = 0; i < todosReinos.length; i++) {
            esher.pj.reinos.add(todosReinos[i]);
        }
    }

    void ObtenerMagiaPorProfesion(String reinoElegido) throws Exception {
        SeleccionarReinoDeMagiaParaPersonaje(reinoElegido);
        LeerHechizosDeArchivo();
        AñadirHechizosPorProfesion();
    }

    void AsignarCosteLista(int[][] lista, String[] coste, int nivel) {
        lista[nivel][0] = Integer.parseInt(coste[0]);
        if (coste.length > 1) {
            lista[nivel][1] = Integer.parseInt(coste[1]);
        } else {
            lista[nivel][1] = 1000;
        }
        if (coste.length > 2) {
            lista[nivel][2] = Integer.parseInt(coste[2]);
        } else {
            lista[nivel][2] = 1000;
        }
    }

    int AsignarCostesHechizos(List<String> lines, int index) {
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaHechizos = lines.get(index);
            String[] vectorHechizos = lineaHechizos.split("\t");
            String[] coste = vectorHechizos[1].split("/");
            if (vectorHechizos[0].equals("Lista Abierta (1-5)")) {
                AsignarCosteLista(esher.pj.listasAbiertas, coste, 0);
            }
            if (vectorHechizos[0].equals("Lista Abierta (6-10)")) {
                AsignarCosteLista(esher.pj.listasAbiertas, coste, 1);
            }
            if (vectorHechizos[0].equals("Lista Abierta (11-15)")) {
                AsignarCosteLista(esher.pj.listasAbiertas, coste, 2);
            }
            if (vectorHechizos[0].equals("Lista Abierta (16-20)")) {
                AsignarCosteLista(esher.pj.listasAbiertas, coste, 3);
            }
            if (vectorHechizos[0].equals("Lista Abierta (21+)")) {
                AsignarCosteLista(esher.pj.listasAbiertas, coste, 4);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (1-5)")) {
                AsignarCosteLista(esher.pj.listasCerradas, coste, 0);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (6-10)")) {
                AsignarCosteLista(esher.pj.listasCerradas, coste, 1);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (11-15)")) {
                AsignarCosteLista(esher.pj.listasCerradas, coste, 2);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (16-20)")) {
                AsignarCosteLista(esher.pj.listasCerradas, coste, 3);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (21+)")) {
                AsignarCosteLista(esher.pj.listasCerradas, coste, 4);
            }
            if (vectorHechizos[0].equals("Lista Básica (1-5)")) {
                AsignarCosteLista(esher.pj.listasBasicas, coste, 0);
            }
            if (vectorHechizos[0].equals("Lista Básica (6-10)")) {
                AsignarCosteLista(esher.pj.listasBasicas, coste, 1);
            }
            if (vectorHechizos[0].equals("Lista Básica (11-15)")) {
                AsignarCosteLista(esher.pj.listasBasicas, coste, 2);
            }
            if (vectorHechizos[0].equals("Lista Básica (16-20)")) {
                AsignarCosteLista(esher.pj.listasBasicas, coste, 3);
            }
            if (vectorHechizos[0].equals("Lista Básica (21+)")) {
                AsignarCosteLista(esher.pj.listasBasicas, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (1-5)")) {
                AsignarCosteLista(esher.pj.listasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (6-10)")) {
                AsignarCosteLista(esher.pj.listasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (11-15)")) {
                AsignarCosteLista(esher.pj.listasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (16-20)")) {
                AsignarCosteLista(esher.pj.listasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (21+)")) {
                AsignarCosteLista(esher.pj.listasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (1-5)")) {
                AsignarCosteLista(esher.pj.listasAbiertasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (6-10)")) {
                AsignarCosteLista(esher.pj.listasAbiertasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (11-15)")) {
                AsignarCosteLista(esher.pj.listasAbiertasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (16-20)")) {
                AsignarCosteLista(esher.pj.listasAbiertasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (21+)")) {
                AsignarCosteLista(esher.pj.listasAbiertasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (1-5)")) {
                AsignarCosteLista(esher.pj.listasCerradasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (6-10)")) {
                AsignarCosteLista(esher.pj.listasCerradasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (11-15)")) {
                AsignarCosteLista(esher.pj.listasCerradasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (16-20)")) {
                AsignarCosteLista(esher.pj.listasCerradasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (21+)")) {
                AsignarCosteLista(esher.pj.listasCerradasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (1-5)")) {
                AsignarCosteLista(esher.pj.listasBasicasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (6-10)")) {
                AsignarCosteLista(esher.pj.listasBasicasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (11-15)")) {
                AsignarCosteLista(esher.pj.listasBasicasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (16-20)")) {
                AsignarCosteLista(esher.pj.listasBasicasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (21+)")) {
                AsignarCosteLista(esher.pj.listasBasicasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (1-5)")) {
                AsignarCosteLista(esher.pj.listasAbiertasArcanas, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (6-10)")) {
                AsignarCosteLista(esher.pj.listasAbiertasArcanas, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (11-15)")) {
                AsignarCosteLista(esher.pj.listasAbiertasArcanas, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (16-20)")) {
                AsignarCosteLista(esher.pj.listasAbiertasArcanas, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (21+)")) {
                AsignarCosteLista(esher.pj.listasAbiertasArcanas, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (1-5)")) {
                AsignarCosteLista(esher.pj.listasTriada, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (6-10)")) {
                AsignarCosteLista(esher.pj.listasTriada, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (11-15)")) {
                AsignarCosteLista(esher.pj.listasTriada, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (16-20)")) {
                AsignarCosteLista(esher.pj.listasTriada, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (21+)")) {
                AsignarCosteLista(esher.pj.listasTriada, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (1-5)")) {
                AsignarCosteLista(esher.pj.listasElementalesComplementarias, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (6-10)")) {
                AsignarCosteLista(esher.pj.listasElementalesComplementarias, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (11-15)")) {
                AsignarCosteLista(esher.pj.listasElementalesComplementarias, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (16-20)")) {
                AsignarCosteLista(esher.pj.listasElementalesComplementarias, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (21+)")) {
                AsignarCosteLista(esher.pj.listasElementalesComplementarias, coste, 4);
            }

            index++;
        }
        return index;
    }

    void AsignarCostesListasAdiestramiento() {
        String[] coste;
        if (esher.pj.EsHechiceroPuro() || esher.pj.EsHechiceroHibrido()) {
            coste = "4/4/4".split("/");
        } else if (esher.pj.EsHechiceroHibrido()) {
            coste = "6/6/6".split("/");
        } else {
            coste = "8/8/8".split("/");
        }
        AsignarCosteLista(esher.pj.listasPropiasAdiestramientos, coste, 0);
        AsignarCosteLista(esher.pj.listasPropiasAdiestramientos, coste, 1);
        AsignarCosteLista(esher.pj.listasPropiasAdiestramientos, coste, 2);
        AsignarCosteLista(esher.pj.listasPropiasAdiestramientos, coste, 3);
        AsignarCosteLista(esher.pj.listasPropiasAdiestramientos, coste, 4);
        if (esher.pj.EsHechiceroPuro() || esher.pj.EsHechiceroHibrido()) {
            coste = "8/8".split("/");
        } else if (esher.pj.EsHechiceroHibrido()) {
            coste = "12/12".split("/");
        } else {
            coste = "16/16".split("/");
        }
        AsignarCosteLista(esher.pj.listasOtrasAdiestramientos, coste, 0);
        AsignarCosteLista(esher.pj.listasOtrasAdiestramientos, coste, 1);
        AsignarCosteLista(esher.pj.listasOtrasAdiestramientos, coste, 2);
        AsignarCosteLista(esher.pj.listasOtrasAdiestramientos, coste, 3);
        AsignarCosteLista(esher.pj.listasOtrasAdiestramientos, coste, 4);
    }

    private List<String> ReinosDeMagiaDisponibles() throws Exception {
        List<String> reinosFichero = esher.directorioRolemaster.ReinosDeMagiaDisponibles(esher.modulosRolemaster);
        return GenerarReinosMixtos(reinosFichero);
    }

    private void LeerHechizosDeArchivo() throws Exception {
        esher.pj.listaHechizos.clear();
        List<String> reinos = esher.directorioRolemaster.ReinosDeMagiaDisponibles(esher.modulosRolemaster);
        //Leemos los hechizos por reino.
        for (int i = 0; i < reinos.size(); i++) {
            String tmp_reino = reinos.get(i);
            List<String> lines = esher.directorioRolemaster.LeerLineasHechizos(tmp_reino + ".txt");
            ObtenerListaHechizosDisponibles(lines, tmp_reino);
        }
    }

    private boolean ElementoEnComun(List l1, List l2) {
        for (int i = 0; i < l1.size(); i++) {
            if (l2.contains(l1.get(i))) {
                return true;
            }
        }
        return false;
    }
}

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

import com.softwaremagico.files.DirectorioRolemaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Magia {


    public Magia() {
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
                    Personaje.getInstance().AddHechizo(new ListaDeHechizos(nombreHechizo, claseHechizo, reino));
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
        List<String> reinosPosibles = new ArrayList<>();
        for (int i = 0; i < reinosExistentes.size(); i++) {
            String reinoDisponible = reinosExistentes.get(i);
            //Si es híbrido de dos reinos.
            if (reinoDisponible.contains("/")) {
                String[] dosReinos = reinoDisponible.split("/");
                //El orden de los dos reinos no importa.
                if (Personaje.getInstance().reinosDeProfesion.contains(dosReinos[0] + "/" + dosReinos[1])
                        || Personaje.getInstance().reinosDeProfesion.contains(dosReinos[1] + "/" + dosReinos[0])) {
                    reinosPosibles.add(reinoDisponible);
                }
            } else {
                //Reinos simples.
                if (Personaje.getInstance().reinosDeProfesion.contains(reinoDisponible)) {
                    reinosPosibles.add(reinoDisponible);
                }
            }
        }
        return reinosPosibles;
    }

    private void ResetCategoriasHechizos() {
        Categoria cat = null;
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de otros Reinos");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Cerradas de otros Reinos");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de otras Profesiones");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento");
        cat.listaHabilidades = new ArrayList<>();
        if (Esher.hechizosAdiestramientoOtrosReinosPermitidos) {
            cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Hechizos de Adiestramientos de Otro Reino");
            cat.listaHabilidades = new ArrayList<>();
        }
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de la Tríada");
        cat.listaHabilidades = new ArrayList<>();
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas Elementales Complementarias");
        cat.listaHabilidades = new ArrayList<>();
    }

    private void AñadirHechizosPorProfesion() throws Exception {
        ResetCategoriasHechizos();
        for (int i = 0; i < Personaje.getInstance().listaHechizos.size(); i++) {
            Categoria cat = null;
            ListaDeHechizos hechizo = Personaje.getInstance().listaHechizos.get(i);

            //Hay listas compartidas por varias profesiones.
            String[] vectorClase = hechizo.clase.split("/");
            List<String> listaClase = new ArrayList<>();
            listaClase.addAll(Arrays.asList(vectorClase));
           
            if (listaClase.contains("Abierta")) {
                if (Personaje.getInstance().reino.contains(hechizo.reino)) {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
                } else if ((hechizo.reino.equals("Arcana"))) {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
                } else {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de otros Reinos");
                }
            } else if (listaClase.contains("Cerrada")) {
                if (Personaje.getInstance().reino.contains(hechizo.reino)) {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");
                } else {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Cerradas de otros Reinos");
                }
            } else if (listaClase.contains(Personaje.getInstance().profesion)) {
                //Listas basica de esta profesion.
                cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
            } else {
                //Listas básicas de otras profesiones.
                List<String> profesiones = Esher.ProfesionesDisponibles();
                profesiones.addAll(Esher.AdiestramientosDisponibles());
                //Consideramos las listas malignas como listas de otras profesiones si no está seleccionada la opción adecuada.. 
                if ((Esher.hechizosMalignos) && (listaClase.contains("Esencia Maligna") || listaClase.contains("Canalización Maligna")
                        || listaClase.contains("Mentalismo Maligno"))) {
                    if (Personaje.getInstance().reino.contains(hechizo.reino)) {
                        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
                    } else {
                        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
                    }
                } else if (listaClase.contains("Esencia Maligna") || listaClase.contains("Canalización Maligna")
                        || listaClase.contains("Mentalismo Maligno")
                        || ElementoEnComun(profesiones, listaClase) && !listaClase.contains(Personaje.getInstance().profesion)) {
                    if (Personaje.getInstance().reino.contains(hechizo.reino)) {
                        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de otras Profesiones");
                    } else {
                        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
                    }
                }
            }

            if (listaClase.contains(Personaje.getInstance().raza)) {
                if (Personaje.getInstance().EsHechicero()) {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
                } else {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
                }
            } else {
                //Las listas de otras razas
                List<String> razas = Esher.RazasDisponibles();
                if (ElementoEnComun(razas, listaClase) && !listaClase.contains(Personaje.getInstance().raza)) {
                    cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
                } else {
                    //Si la raza no existe, no admitimos sus hechizos.
                }
            }

            //Finalmente añadimos la lista a la categoría seleccionada previamente.
            try {
                if (!cat.ExisteHabilidad(hechizo.nombre)) {
                    Habilidad hab = Habilidad.getSkill(cat, hechizo.nombre);
                    cat.AddHabilidad(hab);
                }
            } catch (NullPointerException npe) {
            }
        }
    }

    void PrepararCostesListas() {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 5; i++) {
                Personaje.getInstance().listasBasicas[i][j] = 1000;
                Personaje.getInstance().listasAbiertas[i][j] = 1000;
                Personaje.getInstance().listasCerradas[i][j] = 1000;
                Personaje.getInstance().listasOtros[i][j] = 1000;
                Personaje.getInstance().listasAbiertasOtros[i][j] = 1000;
                Personaje.getInstance().listasCerradasOtros[i][j] = 1000;
                Personaje.getInstance().listasBasicasOtros[i][j] = 1000;
                Personaje.getInstance().listasAbiertasArcanas[i][j] = 1000;
                Personaje.getInstance().listasElementalesComplementarias[i][j] = 1000;
                Personaje.getInstance().listasTriada[i][j] = 1000;
                Personaje.getInstance().listasPropiasAdiestramientos[i][j] = 1000;
                Personaje.getInstance().listasOtrasAdiestramientos[i][j] = 1000;
            }
        }
    }

    private List<String> GenerarReinosMixtos(List<String> reinosDeMagia) {
        List<String> aux = new ArrayList<>();
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
        Personaje.getInstance().reinos.clear();
        Personaje.getInstance().reinos.addAll(Arrays.asList(todosReinos));
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
                AsignarCosteLista(Personaje.getInstance().listasAbiertas, coste, 0);
            }
            if (vectorHechizos[0].equals("Lista Abierta (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertas, coste, 1);
            }
            if (vectorHechizos[0].equals("Lista Abierta (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertas, coste, 2);
            }
            if (vectorHechizos[0].equals("Lista Abierta (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertas, coste, 3);
            }
            if (vectorHechizos[0].equals("Lista Abierta (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertas, coste, 4);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradas, coste, 0);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradas, coste, 1);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradas, coste, 2);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradas, coste, 3);
            }
            if (vectorHechizos[0].equals("Lista Cerrada (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradas, coste, 4);
            }
            if (vectorHechizos[0].equals("Lista Básica (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicas, coste, 0);
            }
            if (vectorHechizos[0].equals("Lista Básica (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicas, coste, 1);
            }
            if (vectorHechizos[0].equals("Lista Básica (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicas, coste, 2);
            }
            if (vectorHechizos[0].equals("Lista Básica (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicas, coste, 3);
            }
            if (vectorHechizos[0].equals("Lista Básica (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicas, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otras Profesiones (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Abiertas de otros Reinos (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Cerradas de otros Reinos (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasCerradasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicasOtros, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicasOtros, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicasOtros, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicasOtros, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas de otros Reinos (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasBasicasOtros, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasArcanas, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasArcanas, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasArcanas, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasArcanas, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Abiertas Arcanas (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasAbiertasArcanas, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasTriada, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasTriada, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasTriada, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasTriada, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas de la Tríada (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasTriada, coste, 4);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (1-5)")) {
                AsignarCosteLista(Personaje.getInstance().listasElementalesComplementarias, coste, 0);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (6-10)")) {
                AsignarCosteLista(Personaje.getInstance().listasElementalesComplementarias, coste, 1);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (11-15)")) {
                AsignarCosteLista(Personaje.getInstance().listasElementalesComplementarias, coste, 2);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (16-20)")) {
                AsignarCosteLista(Personaje.getInstance().listasElementalesComplementarias, coste, 3);
            }
            if (vectorHechizos[0].equals("Listas Básicas Elementales Complementarias (21+)")) {
                AsignarCosteLista(Personaje.getInstance().listasElementalesComplementarias, coste, 4);
            }

            index++;
        }
        return index;
    }

    void AsignarCostesListasAdiestramiento() {
        String[] coste;
        if (Personaje.getInstance().EsHechiceroPuro() || Personaje.getInstance().EsHechiceroHibrido()) {
            coste = "4/4/4".split("/");
        } else if (Personaje.getInstance().EsHechiceroHibrido()) {
            coste = "6/6/6".split("/");
        } else {
            coste = "8/8/8".split("/");
        }
        AsignarCosteLista(Personaje.getInstance().listasPropiasAdiestramientos, coste, 0);
        AsignarCosteLista(Personaje.getInstance().listasPropiasAdiestramientos, coste, 1);
        AsignarCosteLista(Personaje.getInstance().listasPropiasAdiestramientos, coste, 2);
        AsignarCosteLista(Personaje.getInstance().listasPropiasAdiestramientos, coste, 3);
        AsignarCosteLista(Personaje.getInstance().listasPropiasAdiestramientos, coste, 4);
        if (Personaje.getInstance().EsHechiceroPuro() || Personaje.getInstance().EsHechiceroHibrido()) {
            coste = "8/8".split("/");
        } else if (Personaje.getInstance().EsHechiceroHibrido()) {
            coste = "12/12".split("/");
        } else {
            coste = "16/16".split("/");
        }
        AsignarCosteLista(Personaje.getInstance().listasOtrasAdiestramientos, coste, 0);
        AsignarCosteLista(Personaje.getInstance().listasOtrasAdiestramientos, coste, 1);
        AsignarCosteLista(Personaje.getInstance().listasOtrasAdiestramientos, coste, 2);
        AsignarCosteLista(Personaje.getInstance().listasOtrasAdiestramientos, coste, 3);
        AsignarCosteLista(Personaje.getInstance().listasOtrasAdiestramientos, coste, 4);
    }

    private List<String> ReinosDeMagiaDisponibles() throws Exception {
        List<String> reinosFichero = DirectorioRolemaster.ReinosDeMagiaDisponibles();
        return GenerarReinosMixtos(reinosFichero);
    }

    private void LeerHechizosDeArchivo() throws Exception {
        Personaje.getInstance().listaHechizos.clear();
        List<String> reinos = DirectorioRolemaster.ReinosDeMagiaDisponibles();
        //Leemos los hechizos por reino.
        for (int i = 0; i < reinos.size(); i++) {
            String tmp_reino = reinos.get(i);
            List<String> lines = DirectorioRolemaster.LeerLineasHechizos(tmp_reino + ".txt");
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

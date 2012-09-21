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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jorge
 */
public class Adiestramiento implements Serializable {

    public String nombre;
    List<String> otro = new ArrayList<>();
    List<EspecialesAdiestramiento> especialesAdiestramiento = new ArrayList<>();
    private List<List<String>> listadoAumentosPosiblesCaracteristicas = new ArrayList<>();
    private List<String> caracteristicasParaAumentar = new ArrayList<>();
    private List<CategoriaAdiestramiento> categoriasAdiestramiento = new ArrayList<>();
    private int numeroCaracteristicasSeleccionadas = 0; //Evitamos que se seleccionen mas caracteristicas para subir que las permitidas.
    public int tiempo;
    public boolean preferido = false;
    private Random generator = new Random();
    List<String> limitadoRazas = new ArrayList<>();  //Adiestramiento solo valido para estas razas.
    String[] triada1 = {"Fuego", "Agua", "Hielo"};
    String[] triada2 = {"Tierra", "Aire", "Luz"};

    public Adiestramiento(String tmp_nombre) {
        nombre = tmp_nombre;
    }

    public void AñadirEspecialAdiestramiento(String tmp_nombre, int tmp_bonus, int tmp_probabilidad) {
        especialesAdiestramiento.add(new EspecialesAdiestramiento(tmp_nombre, tmp_bonus, tmp_probabilidad));
    }

    public void AñadirAumentoCaracteristicaAdiestramiento(String car) {
        if (car.equals("reino")) {
            car = Personaje.getInstance().CaracteristicasDeReino();
        }
        caracteristicasParaAumentar.add(car);
    }

    public void AñadirAumentoCaracteristicaSeleccionadaAdiestramiento(String car) {
        if (numeroCaracteristicasSeleccionadas < listadoAumentosPosiblesCaracteristicas.size()) {
            if (car.equals("reino")) {
                car = Personaje.getInstance().CaracteristicasDeReino();
            }
            if (!caracteristicasParaAumentar.contains(car)) {
                caracteristicasParaAumentar.add(car);
                numeroCaracteristicasSeleccionadas++;
            } else {
                MostrarError.showErrorMessage("Aumento de características " + car + "  ya seleccionado. Se ignora.", "Error de selección");
            }
        }
    }

    public void AñadirListaAumentoCaracteristicaAdiestramiento(String lineaComas) {
        List<String> listado = new ArrayList<>();
        String[] vectorCar;
        if (lineaComas.contains("; ")) {
            vectorCar = lineaComas.split("; ");
        } else {
            vectorCar = lineaComas.split(", ");
        }
        for (int i = 0; i < vectorCar.length; i++) {
            String car = vectorCar[i].trim();
            if (car.equals("reino")) {
                car = Personaje.getInstance().CaracteristicasDeReino();
            }
            if (Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(car) != null) {
                listado.add(car);
            } else {
                MostrarError.showErrorMessage("Caracteristica " + car + " desconocida", "Error en aumento de característica");

            }
        }
        listadoAumentosPosiblesCaracteristicas.add(listado);
    }

    public List<List<String>> DevolverListaCompletaAumentoCaracteristica() {
        return listadoAumentosPosiblesCaracteristicas;
    }

    public List<String> DevolverAumentoCaracteristica() {
        return caracteristicasParaAumentar;
    }

    public void AñadirCategoriaAdiestramiento(String tmp_nombre, int tmp_rangos, int min, int max, int rangosHabilidades) {
        categoriasAdiestramiento.add(new CategoriaAdiestramiento(tmp_nombre, tmp_rangos, min, max, rangosHabilidades, categoriasAdiestramiento.size()));
    }

    public void AñadirHabilidadUltimaCategoriaAdiestramiento(String tmp_nombre, int tmp_rangos) {
        CategoriaAdiestramiento cat = categoriasAdiestramiento.get(categoriasAdiestramiento.size() - 1);
        if (!cat.ExisteHabilidad(tmp_nombre)) {
            cat.AñadirHabilidad(tmp_nombre, tmp_rangos);
        }
    }

    public void AñadirOtrasHabilidadesUltimaCategoriaAdiestramiento() {

        CategoriaAdiestramiento catAd = categoriasAdiestramiento.get(categoriasAdiestramiento.size() - 1);
        try {
            Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre(catAd.nombre);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                AñadirHabilidadUltimaCategoriaAdiestramiento(cat.listaHabilidades.get(j).DevolverNombre(), 0);
            }
        } catch (ArrayIndexOutOfBoundsException aiofb) {
            MostrarError.showErrorMessage("Error al añadir Otras Habilidades a la ultima categoría de adiestramiento: " + catAd.nombre, "Adiestramiento");
        } catch (NullPointerException npe) {
            MostrarError.showErrorMessage("Error al añadir Otras Habilidades a la ultima categoría de adiestramiento: " + catAd.nombre + "\nExiste esa categoría?", "Adiestramiento");
            npe.printStackTrace();
        }
    }

    public void SeleccionarUnaCategoriaGrupo(String nombre, int rangos) {
        CategoriaAdiestramiento cat = ObtenerCategoria(nombre);
        cat.seleccionada = true;
        cat.rangos = rangos;
        //Deshabilitamos las categorias de este grupo.
        for (int i = 0; i < categoriasAdiestramiento.size(); i++) {
            CategoriaAdiestramiento otrasCat = categoriasAdiestramiento.get(i);
            if (otrasCat.grupo == cat.grupo && !otrasCat.nombre.equals(cat.nombre)) {
                otrasCat.seleccionada = false;
                cat.rangos = 0;
            }
            //Deshabilitamos las habilidades de esta categoria. 
            for (int j = 0; j < cat.habilidades.size(); j++) {
                HabilidadAdiestramiento hab = cat.habilidades.get(j);
                hab.rangosAsignados = 0;
            }
        }
    }

    public CategoriaAdiestramiento ObtenerCategoria(String nombre) {
        for (int i = 0; i < categoriasAdiestramiento.size(); i++) {
            CategoriaAdiestramiento cat = categoriasAdiestramiento.get(i);
            if (cat.nombre.equals(nombre)) {
                return cat;
            }
        }
        return null;
    }

    public boolean UnicoAdiestramientoGrupo(String nombre) {
        CategoriaAdiestramiento cat = ObtenerCategoria(nombre);
        for (int i = 0; i < categoriasAdiestramiento.size(); i++) {
            CategoriaAdiestramiento otrasCat = categoriasAdiestramiento.get(i);
            if (otrasCat.grupo == cat.grupo && !otrasCat.nombre.equals(cat.nombre)) {
                return false;
            }
        }
        return true;
    }

    public boolean TieneLimitacionesEsteAdiestramiento() {
        if (limitadoRazas.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean AdiestramientoValidoPersonaje() {
        if (limitadoRazas.contains(Personaje.getInstance().raza) || limitadoRazas.isEmpty()
                || limitadoRazas.contains(Personaje.getInstance().sexo)) {
            return true;
        } else {
            return false;
        }
    }

    public int DevolverTotalRangosHabilidadesGastadosGrupoAdiestramiento(String categoria) {
        int rangos = 0;
        CategoriaAdiestramiento cat = ObtenerCategoria(categoria);
        int grupo = cat.grupo;
        for (int i = 0; i < categoriasAdiestramiento.size(); i++) {
            CategoriaAdiestramiento catAd = categoriasAdiestramiento.get(i);
            if (catAd.grupo == grupo) {
                rangos += catAd.DevolverRangosHabilidadesGastados();
            }
        }
        return rangos;
    }

    /**
     * Indica que categoria de un grupo de categorias contiene la habilidad a la
     * que se han añadido los rangos.
     *
     * @return
     */
    public CategoriaAdiestramiento DevolverCategoriaGrupoConHabilidadConRangos(int grupo) {
        CategoriaAdiestramiento catAd = null;
        for (int i = categoriasAdiestramiento.size() - 1; i == 0; i--) {
            catAd = categoriasAdiestramiento.get(i);
            if (catAd.grupo == grupo) {
                if (catAd.DevolverRangosHabilidadesGastados() > 0) {
                    return catAd;
                }
            }
        }
        return catAd;
    }

    public boolean EsCategoriaGrupoSeleccionada(String categoria) {
        CategoriaAdiestramiento catAd = ObtenerCategoria(categoria);
        if (EsCategoriaGrupo(categoria) && catAd.DevolverRangosHabilidadesGastados() > 0) {
            return true;
        }
        return false;
    }

    public boolean EsCategoriaGrupo(String categoria) {
        CategoriaAdiestramiento catAd = ObtenerCategoria(categoria);
        if (catAd.rangosGrupo > 0) {
            return true;
        }
        return false;
    }

    public int DevolverNumeroHabilidadesConRangosDeGrupo(String categoria) {
        int habilidades = 0;
        CategoriaAdiestramiento cat = ObtenerCategoria(categoria);
        int grupo = cat.grupo;
        for (int i = 0; i < categoriasAdiestramiento.size(); i++) {
            CategoriaAdiestramiento catAd = categoriasAdiestramiento.get(i);
            if (catAd.grupo == grupo) {
                habilidades += catAd.DevolverNumeroHabilidadesConRangos();
            }
        }
        return habilidades;
    }

    /**
     * Añade un listado de habilidades para que sean seleccionables. Se añaden a
     * la última categoria seleccionada. /
     *
     * @param habilidadesValidas String habilidades separadas por ";".
     */
    void AñadirListadoHabilidadesUltimaCategoriaAdiestramiento(String habilidadesValidas) {
        String[] listadoHabilidades = habilidadesValidas.split(";");
        for (int i = 0; i < listadoHabilidades.length; i++) {
            AñadirHabilidadUltimaCategoriaAdiestramiento(listadoHabilidades[i].trim(), 0);
        }
    }

    /**
     * Añade un listado de categorias en un mismo equipo. Los rangos solo podrán
     * ser asignados a una unica categorias del equipo.
     *
     * @param categorias
     * @param tmp_rangos
     * @param min
     * @param max
     * @param rangosHabilidades
     */
    public void AñadirListadoCategorias(String categorias, int tmp_rangos, int min, int max, int rangosHabilidades) {
        String[] listadoCategorias;
        if (categorias.contains("; ")) {
            listadoCategorias = categorias.split("; ");
        } else {
            listadoCategorias = categorias.split(", ");
        }
        int grupo = categoriasAdiestramiento.size();
        for (int i = 0; i < listadoCategorias.length; i++) {
            if ((!listadoCategorias[i].equals("Armas·Fuego 1mano") && !listadoCategorias[i].equals("Armas·Fuego 2manos")) || Esher.armasFuegoPermitidas) {
                CategoriaAdiestramiento cat = new CategoriaAdiestramiento(listadoCategorias[i].trim(), 0, min, max, rangosHabilidades, grupo, tmp_rangos);
                categoriasAdiestramiento.add(cat);
                AñadirOtrasHabilidadesUltimaCategoriaAdiestramiento();
            }
        }
    }

    private void ResetHechizosAdiestramiento() {
        Categoria cat;
        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento");
        cat.BorrarHabilidades();
        if (Esher.hechizosAdiestramientoOtrosReinosPermitidos) {
            cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Hechizos de Adiestramientos de Otro Reino");
            cat.BorrarHabilidades();
        }
    }

    private boolean MismaTriada(ListaDeHechizos hechizo, String adiestramiento) {

        String[] nombreClase = hechizo.clase.split(" ");
        String[] nombreAdiest = adiestramiento.split(" ");

        boolean prof = false;
        boolean elem = false;

        for (int i = 0; i < triada1.length; i++) {
            if (triada1[i].equals(nombreClase[nombreClase.length - 1])) {
                elem = true;
            }
            if (triada1[i].equals(nombreAdiest[nombreAdiest.length - 1])) {
                prof = true;
            }
        }
        if (elem && prof) {
            return true;
        }

        prof = false;
        elem = false;

        for (int i = 0; i < triada2.length; i++) {
            if (triada2[i].equals(nombreClase[nombreClase.length - 1])) {
                elem = true;
            }
            if (triada2[i].equals(nombreAdiest[nombreAdiest.length - 1])) {
                prof = true;
            }
        }
        if (elem && prof) {
            return true;
        }
        return false;
    }

    private boolean HechizosElementales(List<String> clases) {
        for (int i = 0; i < clases.size(); i++) {
            String clase = clases.get(i);
            if (clase.contains("Mago de")) {
                return true;
            }
        }
        return false;
    }

    public void AñadirListasHechizosAdiestramiento() {
        Categoria cat;
        //ResetHechizosAdiestramiento();
        for (int i = 0; i < Personaje.getInstance().listaHechizos.size(); i++) {
            ListaDeHechizos hechizo = Personaje.getInstance().listaHechizos.get(i);

            //Hay listas compartidas por varias profesiones.
            String[] vectorClase = hechizo.clase.split("/");
            List<String> listaClase = new ArrayList<>();
            listaClase.addAll(Arrays.asList(vectorClase));
            //Es una lista de adiestramiento.
            if (ElementoEnComun(listaClase, Esher.DevolverAdiestramientosPosibles())) {
                //Es nuestro adiestramiento.
                if (listaClase.contains(nombre)) {
                    //de nuestro reino
                    if (Personaje.getInstance().reino.contains(hechizo.reino)) {
                        //Es un elementalista
                        if (Personaje.getInstance().profesion.equals("Elementalista")) {
                            cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
                        } else {
                            cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento");
                        }
                    } else {
                        //Es un adiestramiento de otro reino.
                        if (Esher.hechizosAdiestramientoOtrosReinosPermitidos) {
                            cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Hechizos de Adiestramientos de Otro Reino");
                        } else {
                            continue;
                        }
                    }
                    //Los elementalistas cogen listas de otros adiestramientos.
                } else if (HechizosElementales(listaClase) && Personaje.getInstance().profesion.equals("Elementalista")) {
                    if (MismaTriada(hechizo, nombre)) {
                        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas de la Tríada");
                    } else {
                        cat = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Básicas Elementales Complementarias");
                    }
                } else {
                    continue;
                }

            } else {
                continue;
            }

            //Finalmente añadimos la lista a la categoría seleccionada previamente.
            try {
                if (!cat.ExisteHabilidad(hechizo.nombre)) {
                    Habilidad hab = Habilidad.getSkill(cat, hechizo.nombre);
                    //hab.adiestramiento = true;
                    //Las listas de otros adiestramientos son restringidas.
                    if (!ElementoEnComun(Personaje.getInstance().adiestramientosAntiguos, listaClase) && !listaClase.contains(nombre)) {
                        hab.HacerRestringida();
                    }
                    cat.AddHabilidad(hab);
                }
            } catch (NullPointerException npe) {
                MostrarError.showErrorMessage("Error inesperado al leer la lista de hechizo " + hechizo.nombre + ".", "Adiestramiento");
            }
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

    public List<CategoriaAdiestramiento> DevolverListaCategorias() {
        return categoriasAdiestramiento;
    }

    public void RepartirRangosEnCategoriasDeFormaAleatoria() {
        int ret = 0;
        for (int i = 0; i < categoriasAdiestramiento.size(); i++) {
            CategoriaAdiestramiento cat = categoriasAdiestramiento.get(i);
            while (DevolverTotalRangosHabilidadesGastadosGrupoAdiestramiento(cat.nombre) < cat.rangosHabilidades && ret == 0) {
                if (cat.DevolverNumeroHabilidadesConRangos() < cat.minimoHabilidades) {
                    if (!cat.AñadirRangoNuevaHabilidad()) {
                        ret = cat.AñadirUnRangoAleatorio();
                    }
                } else {
                    if (DevolverNumeroHabilidadesConRangosDeGrupo(cat.nombre) < cat.maximoHabilidades) {
                        ret = cat.AñadirUnRangoAleatorio();
                    } else {
                        if (!cat.AñadirUnRangoHabilidadExistente()) {
                            ret = cat.AñadirUnRangoAleatorio();
                        }
                    }
                }
            }
        }
    }

    public void RepartirCaracterísticasParaSubirAleatoriamente() {
        String caractAnt = "";
        String caracterist;
        for (int i = 0; i < listadoAumentosPosiblesCaracteristicas.size(); i++) {
            List<String> listado = listadoAumentosPosiblesCaracteristicas.get(i);
            do {
                caracterist = listado.get(Esher.generator.nextInt(listado.size()));
            } while (caracterist.equals(caractAnt));
            AñadirAumentoCaracteristicaSeleccionadaAdiestramiento(caracterist);
            caractAnt = caracterist;
        }
    }

    public class CategoriaAdiestramiento implements Serializable {

        public String nombre;
        public int rangos;
        public int minimoHabilidades;
        public int maximoHabilidades;
        public int rangosHabilidades;
        public int rangosGrupo = 0;
        public boolean seleccionada; //Cuando hay un grupo de habilidades indica que esta es la seleccionada para subir.
        public int grupo; //Genera grupos de categorias. Permite implmententar el seleccionar una categoria de un grupo. Un grupo son todas aquellas categorias con el mismo identificador.
        public List<HabilidadAdiestramiento> habilidades = new ArrayList<>();

        public CategoriaAdiestramiento(String tmp_nombre, int tmp_rangos, int min, int max, int tmp_rangosHabilidades, int tmp_grupo) {
            nombre = tmp_nombre;
            rangos = tmp_rangos;
            minimoHabilidades = min;
            maximoHabilidades = max;
            rangosHabilidades = tmp_rangosHabilidades;
            grupo = tmp_grupo;
            rangosGrupo = 0;
        }

        public CategoriaAdiestramiento(String tmp_nombre, int tmp_rangos, int min, int max, int tmp_rangosHabilidades, int tmp_grupo, int tmp_rangosGrupo) {
            nombre = tmp_nombre;
            rangos = tmp_rangos;
            minimoHabilidades = min;
            maximoHabilidades = max;
            rangosHabilidades = tmp_rangosHabilidades;
            grupo = tmp_grupo;
            rangosGrupo = tmp_rangosGrupo;
        }

        public int DevolverRangosHabilidadesGastados() {
            int total = 0;
            for (int i = 0; i < habilidades.size(); i++) {
                total += habilidades.get(i).Rangos();
            }
            return total;
        }

        public void AñadirHabilidad(String tmp_nombre, int tmp_rangos) {
            habilidades.add(new HabilidadAdiestramiento(tmp_nombre, tmp_rangos));
        }

        public List<HabilidadAdiestramiento> DevolverListaHabilidades() {
            return habilidades;
        }

        public boolean ExisteHabilidad(String nombreHabilidad) {
            for (int i = 0; i < habilidades.size(); i++) {
                if (nombreHabilidad.equals(habilidades.get(i).nombre)) {
                    return true;
                }
            }
            return false;
        }

        public int DevolverNumeroHabilidadesConRangos() {
            int total = 0;
            for (int i = 0; i < habilidades.size(); i++) {
                if (habilidades.get(i).Rangos() > 0) {
                    total++;
                }
            }
            return total;
        }

        private List<HabilidadAdiestramiento> ObtenerHabilidadesSinRangos() {
            List<HabilidadAdiestramiento> habSinRangos = new ArrayList<>();
            for (int i = 0; i < habilidades.size(); i++) {
                HabilidadAdiestramiento hab = habilidades.get(i);
                if (hab.rangosAsignados + hab.rangosBasicos == 0) {
                    habSinRangos.add(hab);
                }
            }
            return habSinRangos;
        }

        private List<HabilidadAdiestramiento> ObtenerHabilidadesConRangos() {
            List<HabilidadAdiestramiento> habConRangos = new ArrayList<>();
            for (int i = 0; i < habilidades.size(); i++) {
                HabilidadAdiestramiento hab = habilidades.get(i);
                if (hab.rangosAsignados + hab.rangosBasicos > 0) {
                    habConRangos.add(hab);
                }
            }
            return habConRangos;
        }

        /**
         * Añade un rango a una habilidad que no tenga ninguno. Devuelve true si
         * se ha podido hacer false si no existe ninguna habilidad que lo
         * permita.
         */
        private boolean AñadirRangoNuevaHabilidad() {
            List<HabilidadAdiestramiento> habSinRangos = ObtenerHabilidadesSinRangos();
            if (habSinRangos.size() > 0) {
                int hab = generator.nextInt(habSinRangos.size());
                HabilidadAdiestramiento habAd = habSinRangos.get(hab);
                habAd.rangosAsignados++;
                return true;
            } else {
                return false;
            }
        }

        private int AñadirUnRangoAleatorio() {
            try {
                int hab = generator.nextInt(habilidades.size());
                HabilidadAdiestramiento habAd = habilidades.get(hab);
                if (generator.nextInt(100) < Esher.especializacion * Personaje.getInstance().DevolverHabilidadDeNombre(habAd.nombre).DevolverRangos() * 3 + 10) {
                    habAd.rangosAsignados++;
                }
                return 0;
            } catch (IllegalArgumentException iae) {
                MostrarError.showErrorMessage("La categoria \"" + nombre + "\" no contiene habilidades, no se puede asignar un rango.", "Error al añadir un rango");
                return -1;
            } catch (NullPointerException npe) {
                //Los Elementalistas dan un error al intentar añadir una Lista Básica de Hechizo, ya que estas pertenecen al adiestramiento. Lo ignoramos.               
                return -1;
            }
        }

        /**
         * Añade un rango a una habilidad que tenga al menos un rango de
         * adiestramiento. Devuelve true si se ha podido hacer o false si no
         * existe ninguna habilidad que lo permita.
         */
        private boolean AñadirUnRangoHabilidadExistente() {
            List<HabilidadAdiestramiento> habConRangos = ObtenerHabilidadesConRangos();
            if (habConRangos.size() > 0) {
                int hab = generator.nextInt(habConRangos.size());
                HabilidadAdiestramiento habAd = habConRangos.get(hab);
                habAd.rangosAsignados++;
                return true;
            } else {
                return false;
            }
        }
    }

    public class HabilidadAdiestramiento implements Serializable {

        public String nombre;
        public int rangosBasicos;
        public int rangosAsignados;

        public HabilidadAdiestramiento(String tmp_nombre, int tmp_rangos) {
            nombre = tmp_nombre;
            rangosBasicos = tmp_rangos;

        }

        public int Rangos() {
            return rangosBasicos + rangosAsignados;
        }
    }

    public class EspecialesAdiestramiento implements Serializable {

        public String nombre;
        public int bonus;
        public int probabilidad;

        public EspecialesAdiestramiento(String tmp_nombre, int tmp_bonus, int tmp_probabilidad) {
            nombre = tmp_nombre;
            probabilidad = tmp_probabilidad;
            bonus = tmp_bonus;
        }
    }
}

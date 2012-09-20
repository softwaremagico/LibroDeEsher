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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jorge
 */
public class Armas implements Serializable {

    private TiposArmas tiposArmas;
    private int ultimaArmaSeleccionadaParaCostes;
    public ArmasCultura armasCultura;
    private Esher esher;
    private Random generator = new Random();

    public Armas(Esher tmp_esher)
            throws Exception {
        esher = tmp_esher;
        LeerArmas();
        Inicializar();
        ultimaArmaSeleccionadaParaCostes = 0;
    }

    private void Inicializar() {
        armasCultura = new ArmasCultura();
    }

    public List<String> LeerArmasFichero(String fichero) {
        return esher.directorioRolemaster.LeerLineasArmas(fichero);
    }

    public void BorrarArmasCultura() {
        armasCultura = new ArmasCultura();
        ultimaArmaSeleccionadaParaCostes = 0;
    }

    public void ResetearCuentaArmas() {
        ultimaArmaSeleccionadaParaCostes = 0;
    }

    public void BarajarTiposArmas() {
        tiposArmas.Barajar();
    }

    public void BarajarInteligentementeTiposArmas() {
        tiposArmas.BarajarInteligentemente();
    }

    /**
     * Obtiene el siguiente grupo de armas al que se el asignará un coste de profesión.
     * Va por orden de preferencia.
     */
    public String ObtenerSiguienteArmaPreferida() {
        ultimaArmaSeleccionadaParaCostes++;
        return tiposArmas.Get(ultimaArmaSeleccionadaParaCostes - 1).tipo;
    }

    /**
     * Lee todas las armas indicadas en los ficheros de la carpeta armas.
     */
    public void LeerArmas() throws Exception {
        TiposArmas();
    }

    public ArmasCultura getArmasCultura() {
        return armasCultura;
    }

    /**
     * Genera una lista de armas de cultura a partir de una lista de nombres de armas.
     */
    public void AsignarArmasCultura(List<String> armasDeseadas) {
        for (int i = 0; i < armasDeseadas.size(); i++) {
            armasCultura.AddArmaCultura(armasDeseadas.get(i));
        }
    }

    public List<String> DevolverArmasCulturaDisponible() {
        List<String> totalArmas = new ArrayList<String>();
        for (int i = 0; i < armasCultura.DevolverTodasArmasCultura().size(); i++) {
            totalArmas.add(armasCultura.DevolverTodasArmasCultura().get(i).nombreArma);
        }
        return totalArmas;
    }

    private void TiposArmas() throws Exception {
        List<String> ficherosArmas = esher.directorioRolemaster.TiposArmasDisponibles(esher.modulosRolemaster);
        tiposArmas = new TiposArmas(esher.pj);
        for (int i = 0; i < ficherosArmas.size(); i++) {
            TipoArma tArma;
            tArma = new TipoArma(ficherosArmas.get(i));
            //if (esher.armasFuegoPermitidas || !tArma.tipo.contains("Fuego")) {
            tiposArmas.Add(tArma);
            //}
        }
    }

    public List<String> ListarTodasArmas() {
        List<String> todasArmas = new ArrayList<String>();
        for (int i = 0; i < tiposArmas.Size(); i++) {
            TipoArma ta = tiposArmas.Get(i);
            for (int j = 0; j < ta.armas.size(); j++) {
                todasArmas.add(ta.armas.get(j));
            }
        }
        return todasArmas;
    }

    public List<String> ListarTodasArmasNoRestringidas() {
        List<String> todasArmas = new ArrayList<String>();
        for (int i = 0; i < tiposArmas.Size(); i++) {
            TipoArma ta = tiposArmas.Get(i);
            for (int j = 0; j < ta.armas.size(); j++) {
                if (!ta.armas.get(j).contains("©")) {
                    todasArmas.add(ta.armas.get(j));
                }
            }
        }
        return todasArmas;
    }

    /**
     * Cuando se lee una cultura, existen rangos para un arma indeterminada. Esos rangos los
     * guardamos en el tipo de arma.
     */
    public void AsignarRangosCulturaTipoArma(String tipo, int rangos) {
        tiposArmas.AsignarRangosCulturaTipoArma(tipo, rangos);
    }

    /**
     * Para todos los tipos de armas, se ponen por defecto los rangos de arma de cultura a la primera de la lista.
     */
    public void AsignarRangosArmaCulturaPorDefecto() {
        ArmaCultura armaA;
        for (int i = 0; i < tiposArmas.Size(); i++) {
            TipoArma tipoA = tiposArmas.Get(i);
            try {
                armaA = armasCultura.SeleccionarArmaCategoria(tipoA.tipo);
                armaA.IndicaRangos(tipoA.DevolverRangosAdolescencia());
            } catch (Exception ex) {
            }
        }
    }

    public int DevolverRangosCulturaTipoArma(String tipo) {
        return tiposArmas.DevolverRangosCulturaTipoArma(tipo);
    }

    public int DevolverRangosCulturaArma(String arma) {
        return armasCultura.DevolverRangosCulturaArma(arma);
    }

    public void SeleccionarArmasConRangosCultura(String nombreArma, String categoriaArma, int rangosCultura) {

        armasCultura.SeleccionarArmasConRangosCultura(nombreArma, categoriaArma, rangosCultura, tiposArmas);
    }

    /**
     * Devuelve el listado de armas de un tipo concreto.
     */
    public List<String> DevolverNombreArmasClase(String tipo) {
        for (int i = 0; i < tiposArmas.Size(); i++) {
            if (tiposArmas.Get(i).tipo.equals(tipo)) {
                return tiposArmas.Get(i).armas;
            }
        }
        return null;
    }

    /**
     * Busca que armas son válidas para un cultura. Un arma es válida si está en el litado de armas.
     */
    List<String> SeleccionarArmasValidasDeCultura() throws Exception {
        //List<String> tiposArmasDisponibles = TiposArmasDisponibles();
        List<String> armasDisponibles = new ArrayList<String>();
        for (int j = 0; j < armasCultura.DevolverTodasArmasCultura().size(); j++) {
            String armaSeleccionada = armasCultura.DevolverTodasArmasCultura().get(j).nombreArma;

            for (int i = 0; i < tiposArmas.Size(); i++) {
                List<String> listadoArmas = tiposArmas.Get(i).armas;
                if (listadoArmas.contains(armaSeleccionada)) {
                    armasDisponibles.add(armaSeleccionada);
                    break;
                }
            }
        }
        return armasDisponibles;
    }

    /**
     * Crea un listado de nombre de armas según el tipo deseado. Las armas serán solo aquellas
     * típica de la cultura elegida.
     */
    public List<String> SeleccionarNombreArmasValidasPorCategoriaDeTipo(String clase) throws Exception {
        List<String> listadoArmas = DevolverNombreArmasClase(clase);
        List<String> listadoArmasCategoria = new ArrayList<String>();

        for (int i = 0; i < armasCultura.GetTotalArmas(); i++) {
            String armaSeleccionada = armasCultura.GetNombreArmaCultura(i);
            if (listadoArmas.contains(armaSeleccionada)) {
                listadoArmasCategoria.add(armaSeleccionada);
            }
        }
        return listadoArmasCategoria;
    }

    public List<String> DevolverListaNombreTiposDeArma() {
        List<String> nombreTipos = new ArrayList<String>();
        for (int i = 0; i < tiposArmas.Size(); i++) {
            nombreTipos.add(tiposArmas.Get(i).tipo);
        }
        return nombreTipos;
    }

    public String DevolverTipoDeArma(int index) {
        return tiposArmas.Get(index).tipo;
    }

    public Integer DevolverTotalTiposDeArmas() {
        return tiposArmas.Size();
    }

    /**
     * Reordena la lista de manera que el tipo seleccionado quede una posición por debajo.
     */
    public void BajarIndiceTipoArma(int indice) {
        tiposArmas.BajarIndiceTipoArma(indice);
    }

    /**
     * Reordena la lista de manera que el tipo seleccionado quede una posición por encima.
     */
    public void SubirIndiceTipoArma(int indice) {
        tiposArmas.SubirIndiceTipoArma(indice);
    }

    public class TiposArmas implements Serializable {

        private List<TipoArma> tiposArmas;
        private Personaje pj;

        TiposArmas(Personaje tmp_pj) {
            tiposArmas = new ArrayList<TipoArma>();
            pj = tmp_pj;
        }

        public void Add(TipoArma tipoArma) {
            tiposArmas.add(tipoArma);
        }

        public void Add(int index, TipoArma tipoArma) {
            tiposArmas.add(index, tipoArma);
        }

        public TipoArma Get(int index) {
            try {
                return tiposArmas.get(index);
            } catch (IndexOutOfBoundsException iobe) {
                return null;
            }
        }

        public int Size() {
            return tiposArmas.size();
        }

        public TipoArma BuscarTipoArma(String tipo) {
            for (int i = 0; i < tiposArmas.size(); i++) {
                if (tiposArmas.get(i).tipo.equals(tipo)) {
                    return tiposArmas.get(i);
                }
            }
            return null;
        }

        public boolean ContainsTipo(String tipo) {
            for (int i = 0; i < tiposArmas.size(); i++) {
                if (tiposArmas.get(i).tipo.equals(tipo)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Asigna los rangos de cultura a un tipo de arma para ser utilizados más tarde.
         */
        public void AsignarRangosCulturaTipoArma(String tipo, int rangos) {
            TipoArma tipoACambiar = BuscarTipoArma(tipo);
            tipoACambiar.AsignarRangosAdolescencia(rangos);
        }

        public int DevolverRangosCulturaTipoArma(String tipo) {
            TipoArma tipoACambiar = BuscarTipoArma(tipo);
            return tipoACambiar.DevolverRangosAdolescencia();
        }

        /**
         * Reordena la lista de manera que el tipo seleccionado quede una posición por debajo.
         */
        public void BajarIndiceTipoArma(int index) {
            if (index >= 0 && index < Size()) {
                TipoArma armaBajar = Get(index);
                tiposArmas.remove(index);
                if (index < Size()) {
                    index++;
                }
                Add(index, armaBajar);
            }
        }

        public void SubirIndiceTipoArma(int index) {
            if (index >= 0 && index < Size()) {
                TipoArma armaSubir = Get(index);
                tiposArmas.remove(index);
                if (index > 0) {
                    index--;
                }
                Add(index, armaSubir);
            }
        }

        public boolean EsDeTipo(String tipo, String nombreArma) {
            for (int i = 0; i < tiposArmas.size(); i++) {
                if (tiposArmas.get(i).tipo.equals(tipo)) {
                    TipoArma tipoA = tiposArmas.get(i);
                    for (int j = 0; j < tipoA.armas.size(); j++) {
                        if (tipoA.armas.get(j).equals(nombreArma)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Baraja una lista de armas a partir de un índice.
         */
        List<TipoArma> BarajarListaTipos(int index, List<TipoArma> lista) {
            List<TipoArma> listaBarajada = new ArrayList<TipoArma>();
            int pos = 0;
            int cont = 0;
            while (lista.size() > 0 && cont < index) {
                listaBarajada.add(lista.get(0));
                lista.remove(0);
                cont++;
            }
            while (lista.size() > 0) {
                int elemento = generator.nextInt(lista.size());
                listaBarajada.add(lista.get(elemento));
                lista.remove(elemento);
                pos++;
            }
            return listaBarajada;
        }

        public void Barajar() {
            tiposArmas = BarajarListaTipos(0, tiposArmas);
        }

        private void IncluirArma(List<TipoArma> lista, String tipo) {
            boolean added = false;
            if (ContainsTipo(tipo)) {
                //Arma a incluir.
                int comun = 0;
                int rC = esher.pj.DevolverCategoriaDeNombre("Armas·" + tipo).rangosCultura;
                if (esher.pj.DevolverCategoriaDeNombre("Armas·" + tipo).ExisteHabilidadComun()) {
                    comun = 3;
                }
                int rS = esher.pj.DevolverCategoriaDeNombre("Armas·" + tipo).rangosSugeridos + esher.pj.DevolverCategoriaDeNombre("Armas·" + tipo).DevolverRangosSugeridosHabilidades();

                for (int i = 0; i < lista.size(); i++) {
                    //Arma con la que se compara.
                    int comunA = 0;
                    int rCA = esher.pj.DevolverCategoriaDeNombre("Armas·" + lista.get(i).tipo).rangosCultura;
                    if (esher.pj.DevolverCategoriaDeNombre("Armas·" + lista.get(i).tipo).ExisteHabilidadComun()) {
                        comunA = 3;
                    }
                    int rSA = esher.pj.DevolverCategoriaDeNombre("Armas·" + lista.get(i).tipo).rangosSugeridos + esher.pj.DevolverCategoriaDeNombre("Armas·" + lista.get(i).tipo).DevolverRangosSugeridosHabilidades();
                    if (rC + rS + comun > rCA + rSA + comunA) {
                        lista.add(i, BuscarTipoArma(tipo));
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    lista.add(BuscarTipoArma(tipo));
                }
            }
        }

        public void BarajarInteligentemente() {
            List<TipoArma> tiposBarajados = new ArrayList<TipoArma>();
            List<TipoArma> cuerpoCuerpo = new ArrayList<TipoArma>();
            List<TipoArma> distancia = new ArrayList<TipoArma>();
            List<TipoArma> otrasArmas = new ArrayList<TipoArma>();
            List<TipoArma> armasFuego = new ArrayList<TipoArma>();
            //Se coloca como arma principal aquella de la cultura o que tenga alguna habilidad común por raza o sugerida. El resto se añaden por grupos.

            try {
                IncluirArma(cuerpoCuerpo, "Filo");
                IncluirArma(cuerpoCuerpo, "Contundentes");
                IncluirArma(cuerpoCuerpo, "Asta");
                IncluirArma(cuerpoCuerpo, "2manos");
                IncluirArma(distancia, "Proyectiles");
                IncluirArma(distancia, "Arrojadizas");
            } catch (NullPointerException npe) {
                new MostrarError("¡Problemas al barajar alguna categoría!", "Armas");
            }
            if (ContainsTipo("Artillería")) {
                otrasArmas.add(BuscarTipoArma("Artillería"));
            }
            if (ContainsTipo("Fuego 1mano")) {
                armasFuego.add(BuscarTipoArma("Fuego 1mano"));
            }
            if (ContainsTipo("Fuego 2manos")) {
                armasFuego.add(BuscarTipoArma("Fuego 2manos"));
            }
            //Seleccionamos un arma de ataque y otra de proyectiles.
            cuerpoCuerpo = BarajarListaTipos(1, cuerpoCuerpo);

            distancia = BarajarListaTipos(1, distancia);

            armasFuego = BarajarListaTipos(0, armasFuego);

            tiposBarajados.add(cuerpoCuerpo.get(0));
            if (!esher.armasFuegoPermitidas) {
                tiposBarajados.add(distancia.get(0));
            } else {
                tiposBarajados.add(armasFuego.get(0));
            }
            //Añadimos todas las demas armas sin usar aleatoriamente.
            for (int i = 1; i < cuerpoCuerpo.size(); i++) {
                otrasArmas.add(cuerpoCuerpo.get(i));
            }
            if (!esher.armasFuegoPermitidas) {
                for (int i = 1; i < distancia.size(); i++) {
                    otrasArmas.add(distancia.get(i));
                }
            } else {
                for (int i = 1; i < armasFuego.size(); i++) {
                    otrasArmas.add(armasFuego.get(i));
                }
            }
            otrasArmas = BarajarListaTipos(0, otrasArmas);
            if (!esher.armasFuegoPermitidas) {
                armasFuego = BarajarListaTipos(0, armasFuego);
                tiposBarajados.addAll(armasFuego);
            } else {
                distancia = BarajarListaTipos(0, distancia);
                tiposBarajados.addAll(distancia);
            }
            tiposBarajados.addAll(otrasArmas);

            tiposArmas = tiposBarajados;
        }
    }

    public class TipoArma implements Serializable {

        private String tipo = "";
        private List<String> armas;
        private int rangosAdolescencia = 0; //Guarda los rangos de cultura que no han sido asignados a un arma en concreto.
        boolean noElegirAleatorio = false;

        TipoArma(String tmp_tipo) throws Exception {
            tipo = tmp_tipo;
            LeerArmasDeArchivo(tipo);
        }

        TipoArma(String tmp_tipo, boolean tmp_noElegirAleatorio) throws Exception {
            tipo = tmp_tipo;
            LeerArmasDeArchivo(tipo);
            noElegirAleatorio = tmp_noElegirAleatorio;
        }

        private void LeerArmasDeArchivo(String file) throws Exception {
            List<String> lines = esher.directorioRolemaster.LeerLineasArmas(
                    esher.directorioRolemaster.DevolverDirectorio() + File.separator
                    + esher.directorioRolemaster.DIRECTORIO_ARMAS + File.separator + file + ".txt");
            armas = new ArrayList<String>();

            for (int i = 0; i < lines.size(); i++) {
                String armaLeida = (String) lines.get(i);
                if (!armaLeida.equals("") && !armaLeida.startsWith("#")) {
                    armas.add(armaLeida);
                }
            }
        }

        public int DevolverRangosAdolescencia() {
            return rangosAdolescencia;
        }

        public void AsignarRangosAdolescencia(int value) {
            rangosAdolescencia = value;
        }
    }

    public class ArmasCultura implements Serializable {

        private List<ArmaCultura> armasCultura;
        private Random generator = new Random();

        ArmasCultura() {
            Inicializa();
        }

        private void Inicializa() {
            armasCultura = new ArrayList<ArmaCultura>();
        }

        /**
         * Para un tipo de arma (filo, contundentes, ...) busca un arma de cultura de ese tipo.
         */
        private ArmaCultura SeleccionarArmaCategoria(String clase) throws Exception {
            List<ArmaCultura> listadoArmasCategoria = SeleccionarArmasCategoria(clase);
            if (listadoArmasCategoria.size() > 0) {
                return listadoArmasCategoria.get(0);
            } else {
                return null;
            }
        }

        /**
         * Genera todas las armas de una categoría de armas.
         */
        private List<ArmaCultura> SeleccionarArmasCategoria(String clase) throws Exception {
            List<String> listadoArmas = DevolverNombreArmasClase(clase);
            List<ArmaCultura> listadoArmasCategoria = new ArrayList<ArmaCultura>();

            for (int i = 0; i < GetTotalArmas(); i++) {
                ArmaCultura armaSeleccionada = armasCultura.get(i);
                if (listadoArmas.contains(armaSeleccionada.nombreArma)) {
                    listadoArmasCategoria.add(armaSeleccionada);
                }
            }
            return listadoArmasCategoria;
        }

        ArmaCultura BuscarArma(String nombre) {
            for (int i = 0; i < armasCultura.size(); i++) {
                if (armasCultura.get(i).nombreArma.equals(nombre)) {
                    return armasCultura.get(i);
                }
            }
            return null;
        }

        public int DevolverRangosCulturaArma(String nombreArma) {
            ArmaCultura armaC = BuscarArma(nombreArma);
            if (armaC != null) {
                return armaC.DevuelveRangosArmaCultura();
            }
            return 0;
        }

        /**
         * Cambia rangos de cultura a una arma.
         */
        public void IndicarRangosArmasCultura(String nombreArma, int rangosCultura) {
            ArmaCultura arma = BuscarArma(nombreArma);
            arma.IndicaRangos(rangosCultura);
        }

        public void SeleccionarArmasConRangosCultura(String nombreArma, String categoriaArma, int rangosCultura, TiposArmas tiposA) {
            for (int i = 0; i < armasCultura.size(); i++) {
                ArmaCultura arma = armasCultura.get(i);

                if (arma.nombreArma.equals(nombreArma)) {
                    arma.rangosArmasCultura = rangosCultura;
                } else {
                    if (tiposA.EsDeTipo(categoriaArma, arma.nombreArma)) {
                        arma.rangosArmasCultura = 0;
                    }
                }
            }
        }

        public void AddArmaCultura(String nombre) {
            ArmaCultura armaCultura = new ArmaCultura(nombre);
            armasCultura.add(armaCultura);
        }

        public int GetTotalArmas() {
            return armasCultura.size();
        }

        public ArmaCultura GetArmaCultura(int index) {
            return armasCultura.get(index);
        }

        public String GetNombreArmaCultura(int index) {
            return armasCultura.get(index).nombreArma;
        }

        public void BarajarArmasCultura() {
            List<ArmaCultura> listaBarajada = new ArrayList<ArmaCultura>();
            while (armasCultura.size() > 0) {
                int elemento = generator.nextInt(armasCultura.size());
                listaBarajada.add(armasCultura.get(elemento));
                armasCultura.remove(elemento);
            }
            armasCultura = listaBarajada;
        }

        public List<ArmaCultura> DevolverTodasArmasCultura() {
            return armasCultura;
        }
    }

    public class ArmaCultura implements Serializable {

        private int rangosArmasCultura;
        public String nombreArma;

       public  ArmaCultura(String nombre) {
            nombreArma = nombre;
            rangosArmasCultura = 0;
        }

       public  void IndicaRangos(int value) {
            rangosArmasCultura = value;
        }

       public  int DevuelveRangosArmaCultura() {
            return rangosArmasCultura;
        }
    }
}


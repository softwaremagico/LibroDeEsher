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
import com.softwaremagico.librodeesher.gui.ElegirComunProfesionalGUI;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Personaje implements Serializable {

    public Armas armas = new Armas();
    //Datos
    public String raza = "Hombre Alto";
    public String profesion = "Luchador";
    public String cultura = "Litoral";
    public String sexo = "Masculino";
    public Caracteristicas caracteristicas;
    public List<Categoria> categorias = new ArrayList<>();
    public int puntosDesarrolloNivel;
    public int puntosDesarrolloGastadosAnterioresNiveles = 0;
    public int puntosDesarrolloAnterioresNiveles = 0;
    public int nivel = 1;
    //Raza
    public int puntosIdiomaRaza = 0;
    public List<String> especialesRaza = new ArrayList<>();
    public List<String> profesionesRestringidas = new ArrayList<>();
    public String tamaño;
    //Profesion
    public String[] arrayCaracteristicasProfesion;
    public String progresionDesarrolloFisico = "0/0/0/0/0";
    public String progresionPuntosPoder = "0/0/0/0/0";
    public CosteArmas costearmas;
    //Cultura
    int puntosIdiomaCultura = 0;
    public IdiomasAdolescencia idiomasCultura = new IdiomasAdolescencia();
    public int rangosAficiones;
    public List<String> listaAficiones = new ArrayList<>();
    public IdiomasAdolescencia idiomasRaza = new IdiomasAdolescencia();
    public List<String> culturasPosiblesPorRaza = new ArrayList<>();
    public List<String> armadurasCultura = new ArrayList<>();
    //TRs
    private int trCanalizacion;
    private int trEsencia;
    private int trMentalismo;
    private int trPsionico;
    private int trVenenos;
    private int trEnfermedades;
    private int trFrio;
    private int trCalor;
    private int trMiedo;
    public int partidaAlma;
    public int tipoRaza;
    public float recuperacion;
    //Nombres
    private String nombreCompleto = "";
    public List<String> nombresMasculinos = new ArrayList<>();
    public List<String> nombresFemeninos = new ArrayList<>();
    public List<String> apellidos = new ArrayList<>();
    //Adiestramiento
    public CostesAdiestramientos costesAdiestramientos = new CostesAdiestramientos();
    public List<String> adiestramientosAntiguos = new ArrayList<>();
    public List<String> adiestramientosSugeridos = new ArrayList<>();
    public Adiestramiento adiestramiento = null;
    public transient ElegirComunProfesionalGUI grupoHab = null;
    //Conjuros Nivel (1-5), Nivel (6-10), Nivel (11-15), Nivel (16-20), Nivel (21+)
    public int[][] listasBasicas = new int[5][3];
    public int[][] listasAbiertas = new int[5][3];
    public int[][] listasCerradas = new int[5][3];
    public int[][] listasOtros = new int[5][3];
    public int[][] listasAbiertasOtros = new int[5][3];
    public int[][] listasCerradasOtros = new int[5][3];
    public int[][] listasBasicasOtros = new int[5][3];
    public int[][] listasAbiertasArcanas = new int[5][3];
    public int[][] listasTriada = new int[5][3];
    public int[][] listasElementalesComplementarias = new int[5][3];
    public int[][] listasPropiasAdiestramientos = new int[5][3];
    public int[][] listasOtrasAdiestramientos = new int[5][3];
    public int rangosHechizosCultura = 0;
    public HechizoCultura hechizoCultura = null;
    public List<String> reinosDeProfesion = new ArrayList<>();
    public List<ListaDeHechizos> listaHechizos = new ArrayList<>();
    public String[] progresionesPuntosPoder;
    public List<String> reinos = new ArrayList<>();
    public String reino = "";
    public final int ARCANO = 0;
    public final int ESENCIA = 1;
    public final int CANALIZACION = 2;
    public final int MENTALISMO = 3;
    public final int PSIONICO = 4;
    //Otros
    public List<String> equipo = new ArrayList<>();
    public List<OtraHabilidad> otrasHabilidades = new ArrayList<>();
    public int historial;
    public int puntoshistorialCaracteristicas = 0;
    //Objetos Magicos
    public List<ObjetoMagico> objetosMagicos = new ArrayList<>();
    //Talentos
    public List<Talento> talentos = new ArrayList<>();
    public int puntosTalentos;
    public List<Categoria> categoriasNuevas = new ArrayList<>();
    public int bonusDefensiva = 0;
    public int armaduraNatural = 1;
    //Seguridad
    public boolean lock = false; //Una vez se salva el personaje, se evita que el panel insertar pueda modificar las características del personaje y así evitar que los jugadores hagan trampas en el futuro. 
    public int vecesCargadoPersonaje = 0; //Las veces que el jugador a repetido las tiradas de características. 
    public String loadedFrom = ""; // El path del archivo desde donde se ha cargado.
    public int lastSavedLevel = 1;
    private static Personaje personaje = new Personaje();
    public String historia = "";

    Personaje() {
        resetCaracteristicas();
    }

    public static Personaje getInstance() {
        return personaje;
    }

    public static void setInstance(Personaje personaje) {
        Personaje.personaje = personaje;
    }
    
    public final void resetCaracteristicas(){
        caracteristicas = new Caracteristicas();
    }

    /**
     * *************************************************************
     *
     * CONSULTAS PERSONAJE
     *
     **************************************************************
     */
    /**
     * Devuelve la habilidad según el nombre indicado.
     *
     * @param nombre
     * @return Habilidad deseada.
     */
    public Habilidad DevolverHabilidadDeNombre(String nombre) {
        if (nombre.equals("Ninguno") || nombre.equals("Ninguna")) {
            return null;
        }

        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                if (hab.DevolverNombre().trim().equals(nombre.trim())) {
                    return hab;
                }
            }
        }
        return null;
    }

    public Habilidad DevolverHabilidadDeNombre(String prefijo, String sufijo) {
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                if (hab.DevolverNombre().startsWith(prefijo) && hab.DevolverNombre().endsWith(sufijo)) {
                    return hab;
                }
            }
        }
        return null;
    }

    public List<Habilidad> DevolverHabilidadesOrdenadasAlfabeticamente() {
        List<Habilidad> todasHabilidades = new ArrayList<Habilidad>();
        int total = 0;

        //Leemos todas las habilidades
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                todasHabilidades.add(hab);
                total++;
            }
        }
        String[] nombresHabilidades = new String[total];
        for (int i = 0; i < todasHabilidades.size(); i++) {
            Habilidad hab = todasHabilidades.get(i);
            nombresHabilidades[i] = hab.DevolverNombre();
        }
        //Ordenamos las Habilidades.
        java.util.Arrays.sort(nombresHabilidades, java.text.Collator.getInstance(Locale.ITALIAN));

        List<Habilidad> habilidadesOrdenadas = new ArrayList<Habilidad>();
        for (int j = 0; j < nombresHabilidades.length; j++) {
            Habilidad hab = DevolverHabilidadDeNombre(nombresHabilidades[j]);
            habilidadesOrdenadas.add(hab);
        }
        return habilidadesOrdenadas;
    }

    public int DevolverTamañoMaximoNombreCategoriasYHabilidades() {
        int tamañoMaximoHabilidad = 0;
        for (int i = 0; i < categorias.size(); i++) {
            int longitudNombreCategoria = categorias.get(i).DevolverTamañoMaximoNombre();
            if (tamañoMaximoHabilidad < longitudNombreCategoria) {
                tamañoMaximoHabilidad = longitudNombreCategoria;
            }
        }
        return tamañoMaximoHabilidad;
    }

    public String DevolverStringConjuntoAdiestramientos() {
        String adiest = "";
        for (int i = 0; i < adiestramientosAntiguos.size(); i++) {
            adiest += adiestramientosAntiguos.get(i);
            if (i < adiestramientosAntiguos.size() - 1) {
                adiest += ", ";
            }
        }
        return adiest;
    }

    public int DevolverPosicionCategoriaDeNombre(String nombre) {
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            if (cat.DevolverNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public int DevolverPosicionCategoriaDeNombre(List<Categoria> tmp_categorias, String nombre) {
        for (int i = 0; i < tmp_categorias.size(); i++) {
            Categoria cat = tmp_categorias.get(i);
            if (cat.DevolverNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public String DevolverNombreCompleto() {
        if (nombreCompleto == null || nombreCompleto.length() == 0) {
            return "";
        }
        String name = "";
        try {
            String[] names = nombreCompleto.split(" ");
            for (int i = 0; i < names.length; i++) {
                name += names[i].substring(0, 1).toUpperCase() + names[i].substring(1).toLowerCase();
                if (i < names.length - 1) {
                    name += " ";
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
        return name.replace(",", "");
    }

    public void SetNombreCompleto(String nombre) {
        nombreCompleto = nombre;
    }

    public String SexoPersonaje() {
        return sexo;
    }

    /**
     * Devuelve true si es luchador, bribón y otra profesión que no sea propensa
     * a la magia.
     */
    public boolean EsCombatiente() {
        if (reinosDeProfesion.size() < 3) {
            return false;
        }
        return true;
    }

    /**
     * Falta que distinga entre hechiceros puros y semi. /
     *
     * @return
     */
    public boolean EsHechiceroPuro() {
        return (CosteCategoriaYHabilidad(DevolverCategoriaDeNombre("Listas Básicas de Hechizos"), 0, null) == 3);
    }

    /**
     * Seme hechiceros.
     *
     * @return
     */
    public boolean EsHechiceroHibrido() {
        return (CosteCategoriaYHabilidad(DevolverCategoriaDeNombre("Listas Básicas de Hechizos"), 0, null) == 3 && reino.contains("/"));
    }

    /**
     * Hechiceros de dos reinos de magia.
     *
     * @return
     */
    public boolean EsSemiHechicero() {
        return (CosteCategoriaYHabilidad(DevolverCategoriaDeNombre("Listas Básicas de Hechizos"), 0, null) == 6);
    }

    /**
     * Devuelve el máximo nivel de entre las listas de hechizos del pj.
     *
     * @return
     */
    public int DevolverMaximoNivelHechizos() {
        int maxrangos = 0;
        int rangos = 0;
        Categoria cat = DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Hechizos de Adiestramientos de Otro Reino");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Básicas de la Tríada");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Básicas Elementales Complementarias");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Básicas de otras Profesiones");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Abiertas de otros Reinos");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Cerradas de otros Reinos");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        cat = DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
        rangos = cat.DevolverMaximoRangosEnHabilidad();
        if (rangos > maxrangos) {
            maxrangos = rangos;
        }
        return maxrangos;
    }

    public int DevolverArmasAprendidas() {
        int categor = 0;
        Categoria cat;
        cat = DevolverCategoriaDeNombre("Armas·2manos");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Armas·Arrojadizas");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Armas·Artillería");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Armas·Contundentes");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Armas·Asta");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Armas·Filo");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Artes Marciales·Barridos");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Artes Marciales·Golpes");
        categor += cat.DevolverHabilidadesConRangos();
        cat = DevolverCategoriaDeNombre("Artes Marciales·Maniobras de Combate");
        categor += cat.DevolverHabilidadesConRangos();
        try {
            cat = DevolverCategoriaDeNombre("Armas·Fuego 1mano");
            categor += cat.DevolverHabilidadesConRangos();
            cat = DevolverCategoriaDeNombre("Armas·Fuego 2manos");
            categor += cat.DevolverHabilidadesConRangos();
        } catch (NullPointerException npe) {
        }
        return categor;
    }

    public int DevolverArmasAprendidasEnEsteNivel() {
        int categor = 0;
        Categoria cat;
        cat = DevolverCategoriaDeNombre("Armas·2manos");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Armas·Arrojadizas");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Armas·Artillería");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Armas·Contundentes");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Armas·Asta");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Armas·Filo");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Artes Marciales·Barridos");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Artes Marciales·Golpes");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        cat = DevolverCategoriaDeNombre("Artes Marciales·Maniobras de Combate");
        categor += cat.DevolverHabilidadesConNuevosRangos();
        try {
            cat = DevolverCategoriaDeNombre("Armas·Fuego 1mano");
            categor += cat.DevolverHabilidadesConNuevosRangos();
            cat = DevolverCategoriaDeNombre("Armas·Fuego 2manos");
            categor += cat.DevolverHabilidadesConNuevosRangos();
        } catch (NullPointerException npe) {
        }
        return categor;
    }

    /**
     * Incluye un adiestramiento con hechizos.
     *
     * @return
     */
    public boolean EsHechiceroAdiestramiento() {
        return (DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento").listaHabilidades.size() > 0);
    }

    /**
     * Indica si puede lanzar algún tipo de hechizos. Comprende a los hechiceros
     * puros (y mixtos) o semihechiceros.
     *
     * @return
     */
    public boolean EsHechicero() {
        return EsHechiceroPuro() || EsSemiHechicero() || EsHechiceroHibrido() || EsHechiceroAdiestramiento();
    }

    /**
     * Subir más de cinco listas en un nivel multiplica el coste de las nuevas
     * listas.
     */
    public int DevolverMultiplicadoCosteHechizos() {
        int listasSubidas = 0;

        Categoria cat = DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Básicas de la Tríada");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Básicas Elementales Complementarias");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Básicas de otras Profesiones");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Abiertas de otros Reinos");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Cerradas de otros Reinos");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento");
        listasSubidas += cat.DevolverHabilidadesConNuevosRangos();

        cat = DevolverCategoriaDeNombre("Listas Hechizos de Adiestramientos de Otro Reino");
        if (cat != null) {
            listasSubidas += cat.DevolverHabilidadesConNuevosRangos();
        }

        if (listasSubidas < 5) {
            return 1;
        }
        if (listasSubidas < 10) {
            return 2;
        }
        return 4;
    }

    public int ObtenerExperineciaAPartirNivel() {
        if (nivel < 5) {
            return nivel * 10000;
        }
        if (nivel < 10) {
            return (nivel - 5) * 20000 + 50000;
        }
        if (nivel < 15) {
            return (nivel - 10) * 30000 + 150000;
        }
        if (nivel < 20) {
            return (nivel - 15) * 40000 + 300000;
        }
        return (nivel - 20) * 50000 + 500000;
    }

    /**
     * Devuelve true si la profesión ha sido ya asiganda.
     *
     * @return
     */
    public boolean ProfesionAsignada() {
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            if (cat.bonusProfesion > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean EsProfesionGuiaElemental() {
        if (profesion.equals("Elementalista")) {
            return true;
        }
        return false;
    }

    public int DevolverCapacidadMovimiento() {
        return (15 + caracteristicas.DevolverCaracteristicaDeAbreviatura("Rp").Total() + DevolverBonusTalentoMovimiento());
    }

    public int DevolverBD() {
        return caracteristicas.DevolverCaracteristicaDeAbreviatura("Rp").Total() * 3 + bonusDefensiva;
    }

    public int DevolverTA() {
        int max = 1;
        if (armaduraNatural > max) {
            max = armaduraNatural;
        }
        for (int i = 0; i < talentos.size(); i++) {
            if (talentos.get(i).tipoarmadura > max) {
                max = talentos.get(i).tipoarmadura;

            }
        }
        return max;
    }

    /**
     * *************************************************************
     *
     * CARACTERISTICAS
     *
     **************************************************************
     */
    /**
     * Obtiene los puntos de caracteristicas gastados si car es incrementado en
     * un numero.
     */
    public int ObtenerPuntosCaracteristicasGastados(Caracteristica caract, int incremento) {
        int total = 0;
        for (int i = 0; i < caracteristicas.Size(); i++) {
            Caracteristica car = caracteristicas.Get(i);
            if (caract.equals(car)) {
                total += car.ObtenerCosteTemporalCaracteristica(car.ObtenerPuntosTemporal() + incremento);
            } else {
                total += car.ObtenerCosteTemporalCaracteristica();
            }
        }
        return total;
    }

    public int ObtenerPuntosCaracteristicasGastados() {
        int total = 0;
        for (int i = 0; i < caracteristicas.Size(); i++) {
            Caracteristica car = caracteristicas.Get(i);
            total += car.ObtenerCosteTemporalCaracteristica();
        }
        return total;
    }

    public String CaracteristicasDeReino() {
        if (reinos.contains("Canalización")) {
            return "In";
        }
        if (reinos.contains("Mentalismo")) {
            return "Pr";
        }
        return "Em";
    }

    public int DevolverBonusCaracteristicaDeAbreviatura(String abrev) {
        Caracteristica car = caracteristicas.DevolverCaracteristicaDeAbreviatura(abrev);
        return car.Total();
    }

    public void ActualizaCaracteristicasReino() {
        Categoria cat;
        cat = DevolverCategoriaDeNombre("Desarrollo de Puntos de Poder");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Básicas de Hechizos");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Básicas de otras Profesiones");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Abiertas de otros Reinos");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Cerradas de otros Reinos");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Básicas de otros Reinos");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Abiertas Arcanas");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Hechizos de Adiestramiento");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Básicas de la Tríada");
        cat.CaracteristicasDeCategoria();
        cat = DevolverCategoriaDeNombre("Listas Básicas Elementales Complementarias");
        cat.CaracteristicasDeCategoria();
        if (Esher.hechizosAdiestramientoOtrosReinosPermitidos) {
            cat = DevolverCategoriaDeNombre("Listas Hechizos de Adiestramientos de Otro Reino");
            cat.CaracteristicasDeCategoria();
        }
    }

    /**
     * Indica si es la caracteristica principal de la profesion.
     */
    public boolean EsCaracteristicasPrincipal(Caracteristica car) {
        try {
            if (car.DevolverAbreviatura().equals(arrayCaracteristicasProfesion[0])
                    || car.DevolverAbreviatura().equals(arrayCaracteristicasProfesion[1])) {
                return true;
            }
        } catch (NullPointerException npe) {
            return false;
        } catch (StackOverflowError sof) {
            return false;
        }
        return false;
    }

    /**
     * *************************************************************
     *
     * TRs
     *
     **************************************************************
     */
    /**
     * Devuelve la TR básica (sin contar las características) del reino de
     * Canalización.
     *
     * @return
     */
    public int TrCanalizacion() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Canalización")
                        || (talento.bonusTrs.get(j).nombre.equals("TR Reino") && reino.equals("Canalización"))) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trCanalizacion + bonus;
    }

    public int TrEsencia() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Esencia")
                        || (talento.bonusTrs.get(j).nombre.equals("TR Reino") && reino.equals("Esencia"))) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trEsencia + bonus;
    }

    public int TrMentalismo() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Mentalismo")
                        || (talento.bonusTrs.get(j).nombre.equals("TR Reino") && reino.equals("Mentalismo"))) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trMentalismo + bonus;
    }

    public int TrPsionico() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Psiónico")
                        || (talento.bonusTrs.get(j).nombre.equals("TR Reino") && reino.equals("Psiónico"))) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trPsionico + bonus;
    }

    public int TrVenenos() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Venenos")) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trVenenos + bonus;
    }

    public int TrEnfermedades() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Enfermedades")) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trEnfermedades + bonus;
    }

    public int TrFrio() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Frío")) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trFrio + bonus;
    }

    public int TrCalor() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Calor")) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trCalor + bonus;
    }

    public int TrMiedo() {
        int bonus = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusTrs.size(); j++) {
                if (talento.bonusTrs.get(j).nombre.equals("TR Miedo")) {
                    bonus += talento.bonusTrs.get(j).bonus;
                }
            }
        }
        return trMiedo + bonus;
    }

    public void SetTrCanalizacion(int value) {
        trCanalizacion = value;
    }

    public void SetTrEsencia(int value) {
        trEsencia = value;
    }

    public void SetTrMentalismo(int value) {
        trMentalismo = value;
    }

    public void SetTrPsionico(int value) {
        trPsionico = value;
    }

    public void SetTrVenenos(int value) {
        trVenenos = value;
    }

    public void SetTrEnfermedades(int value) {
        trEnfermedades = value;
    }

    public void SetTrFrio(int value) {
        trFrio = value;
    }

    public void SetTrCalor(int value) {
        trCalor = value;
    }

    public void SetTrMiedo(int value) {
        trMiedo = value;
    }

    /**
     * *************************************************************
     *
     * CULTURA
     *
     **************************************************************
     */
    /**
     * Devuelve los puntos de idiomas libres.
     *
     * @return
     */
    public int DevolverPuntosIdiomaCultura() {
        int puntosIdiomasCulturaGastados = 0;
        for (int i = 0; i < idiomasCultura.Size(); i++) {
            IdiomaCultura idi = idiomasCultura.Get(i);
            puntosIdiomasCulturaGastados += idi.rangosNuevosEscritos;
            puntosIdiomasCulturaGastados += idi.rangosNuevosHablado;
        }
        return puntosIdiomaCultura + puntosIdiomaRaza - puntosIdiomasCulturaGastados;
    }

    public int DevolverPuntosAficiones() {
        int puntosAficionesGastados = 0;
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                puntosAficionesGastados += hab.rangosAficiones;
            }
        }
        return rangosAficiones - puntosAficionesGastados;
    }

    public void ActualizarOrdenCostesArmas() {
        int[] coste = new int[3];
        int costeUltimo = 1000;
        for (int i = 0; i < armas.DevolverTotalTiposDeArmas(); i++) {
            String tarma = armas.DevolverTipoDeArma(i);
            Categoria cat = DevolverCategoriaDeNombre("Armas·" + tarma);
            try {
                coste = costearmas.DevolverCosteRango(i);
                costeUltimo = coste[0];
            } catch (IndexOutOfBoundsException iobe) {
                coste[0] = costeUltimo;
                coste[1] = 1000;
                coste[2] = 1000;
            }
            cat.CambiarCosteRango(coste);
        }
    }

    public void AsignarListaHechizosCultura(Habilidad hab) {
        hechizoCultura = new HechizoCultura(hab.DevolverNombre(), rangosHechizosCultura);
    }

    /**
     * *************************************************************
     *
     * CATEGORIAS
     *
     **************************************************************
     */
    /**
     * Indica si una categoria ya ha sido insertada.
     *
     * @param nom
     * @return
     */
    public boolean ExisteCategoria(String nom) {
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            if (cat.DevolverNombre().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    public void AñadirCategoria(Categoria cat) {
        if (DevolverCategoriaDeNombre(cat.DevolverNombre()) == null) {
            categorias.add(cat);
            OrdenarCategorias();
        }
        if (DevolverCategoriaNuevaDeNombre(cat.DevolverNombre()) == null) {
            categoriasNuevas.add(cat);
        }
    }

    public boolean EliminarCategoria(Categoria cat) {
        return categorias.remove(cat);
    }

    public void AñadirHabilidades(String c, String habilidades) {
        String[] arrayHabilidades = habilidades.split(", ");
        Categoria cat = DevolverCategoriaDeNombre(c);
        for (int i = 0; i < arrayHabilidades.length; i++) {
            cat.AddHabilidad(arrayHabilidades[i]);
        }
    }

    public void FusionarCategoriasNuevasConUsables() {
        try {
            Categoria cat = null;

            String line;
            List<String> lines = DirectorioRolemaster.LeerLineasCategorias("categorías.txt");
            categorias = new ArrayList<Categoria>();
            for (int i = 2; i < lines.size(); i++) {
                line = (String) lines.get(i);
                String[] descomposed_line = line.split("\t");
                String[] nombreAbrev = descomposed_line[0].split("\\(");
                String nombreCat = nombreAbrev[0];
                try {
                    String abrevCat = nombreAbrev[1].replace(")", "");
                    if (!((!Esher.armasFuegoPermitidas && nombreCat.contains("Armas·Fuego"))
                            || (!Esher.hechizosAdiestramientoOtrosReinosPermitidos && nombreCat.contains("Listas Hechizos de Adiestramientos de Otro Reino")))) {
                        cat = Categoria.getCategory(nombreCat, abrevCat, descomposed_line[1],
                                descomposed_line[2], descomposed_line[3]);
                        categoriasNuevas.add(cat);
                    }
                } catch (ArrayIndexOutOfBoundsException aiofb) {
                    MostrarMensaje.showErrorMessage("Abreviatura de categoria mal definida en " + nombreCat, "Lectura de Categorías");
                }
            }
            /////////////////////////////////////////

            for (int i = 0; i < categoriasNuevas.size(); i++) {
                Categoria catN = categoriasNuevas.get(i);
                if ((cat = DevolverCategoriaDeNombre(catN.DevolverNombre())) == null) {
                    categorias.add(catN);
                } else {
                    for (int j = 0; j < catN.listaHabilidades.size(); j++) {
                        if (!cat.ExisteHabilidad(catN.listaHabilidades.get(j).DevolverNombre())) {
                            cat.AddHabilidad(catN.listaHabilidades.get(j));
                        }
                    }
                }
            }
            OrdenarCategorias();
        } catch (Exception ex) {
            Logger.getLogger(Personaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Categoria DevolverCategoriaDeNombre(String nombre) {
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            if (cat.DevolverNombre().equals(nombre)) {
                return cat;
            }
        }
        return null;
    }

    public Categoria DevolverCategoriaNuevaDeNombre(String nombre) {
        for (int i = 0; i < categoriasNuevas.size(); i++) {
            Categoria cat = categoriasNuevas.get(i);
            if (cat.DevolverNombre().equals(nombre)) {
                return cat;
            }
        }
        return null;
    }

    public void OrdenarCategorias() {
        String[] nombresCategorias = new String[categorias.size()];
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            nombresCategorias[i] = cat.DevolverNombre();
        }
        //Ordenamos las Categorías.
        java.util.Arrays.sort(nombresCategorias, java.text.Collator.getInstance(Locale.ITALIAN));

        List<Categoria> listaCategoriaOrdenadas = new ArrayList<Categoria>();
        for (int j = 0; j < nombresCategorias.length; j++) {
            Categoria catOrd = DevolverCategoriaDeNombre(nombresCategorias[j]);
            listaCategoriaOrdenadas.add(catOrd);
        }
        categorias = listaCategoriaOrdenadas;
    }

    /**
     * *************************************************************
     *
     * HISTORIAL
     *
     **************************************************************
     */
    /**
     * Cuantos puntos de historial han sido ya utilizados
     *
     * @return
     */
    public int DevolverPuntosHistorialGastados() {
        return ContarPuntosHistorialGastadosEnCategoriasYHabilidades()
                + ContarPuntosHistorialObjetos()
                + puntoshistorialCaracteristicas;
    }

    public int DevolverPuntosHistoriaTotales() {
        return historial;
    }

    private int ContarPuntosHistorialGastadosEnCategoriasYHabilidades() {
        int puntosHistorial = 0;
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            if (cat.historial) {
                puntosHistorial++;
            }
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                if (hab.historial) {
                    puntosHistorial++;
                }
            }
        }
        return puntosHistorial;
    }

    private int ContarPuntosHistorialObjetos() {
        int total = 0;
        for (int i = 0; i < objetosMagicos.size(); i++) {
            if (objetosMagicos.get(i).historial) {
                total++;
            }
        }
        return total;
    }

    /**
     * *************************************************************
     *
     * TALENTOS
     *
     **************************************************************
     */
    /**
     *
     * @return Total de puntos gastados.
     */
    public int DevolverPuntosTalentosGastados() {
        int total = 0;
        for (int i = 0; i < talentos.size(); i++) {
            total += talentos.get(i).coste;
        }
        return total;
    }

    public int DevolverPuntosTalentosRestantes() {
        return this.puntosTalentos - DevolverPuntosTalentosGastados();
    }

    public Talento DevolverTalento(String nombre) {
        for (int i = 0; i < talentos.size(); i++) {
            if (talentos.get(i).nombre.equals(nombre)) {
                return talentos.get(i);
            }
        }
        return null;
    }

    public void EliminarTalento(String nombre) {
        for (int i = 0; i < talentos.size(); i++) {
            if (talentos.get(i).nombre.equals(nombre)) {
                talentos.remove(i);
            }
        }
    }

    public int DevolverBonusTalentoCategoria(String cat) {
        int total = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusCategoria.size(); j++) {
                if (talento.bonusCategoria.get(j).nombre.equals(cat)) {
                    total += talento.bonusCategoria.get(j).bonus;
                    if (talento.bonusCategoria.get(j).bonusRango > 0) {
                        total += talento.bonusCategoria.get(j).bonusRango * DevolverCategoriaDeNombre(cat).DevolverRangos();
                    }
                }
            }
            try {
                for (int k = 0; k < talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.size(); k++) {
                    if (talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.get(k).equals(cat)) {
                        total += talento.bonusCategoriaHabilidadElegir.bonus;
                    }
                }
            } catch (NullPointerException npe) {
            }
        }
        return total;
    }

    public int DevolverBonusTalentoEspecialCategoria(String cat) {
        int bonusTemporal = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusCategoria.size(); j++) {
                if (talento.bonusCategoria.get(j).nombre.equals(cat)) {
                    if (talento.bonusCategoria.get(j).nombre.equals(cat)) {
                        if (talento.bonusCategoria.get(j).aVeces) {
                            bonusTemporal += talento.bonusCategoria.get(j).bonus;
                        }
                    }
                }
            }
        }
        return bonusTemporal;
    }

    public int DevolverBonusTalentoMovimiento() {
        int total = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            total += talento.bonusMovimiento;
        }
        return total;
    }

    public int DevolverBonusTalentoHabilidad(String hab) {
        int total = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusHabilidad.size(); j++) {
                if (talento.bonusHabilidad.get(j).nombre.equals(hab)) {
                    total += talento.bonusHabilidad.get(j).bonus;
                    if (talento.bonusHabilidad.get(j).bonusRango > 0) {
                        total += talento.bonusHabilidad.get(j).bonusRango * DevolverHabilidadDeNombre(hab).DevolverRangos();
                    }
                }
            }
        }
        return total;
    }

    public int DevolverBonusTalentoEspecialHabilidad(String hab) {
        int bonusTemporal = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusHabilidad.size(); j++) {
                if (talento.bonusHabilidad.get(j).nombre.equals(hab)) {
                    if (talento.bonusHabilidad.get(j).aVeces) {
                        bonusTemporal += talento.bonusHabilidad.get(j).bonus;
                    }
                }
            }
        }
        return bonusTemporal;
    }

    public int DevolverBonusTalentoCaracteristica(String Abrev) {
        int total = 0;
        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            for (int j = 0; j < talento.bonusCaracteristica.size(); j++) {
                if (talento.bonusCaracteristica.get(j).nombre.equals(Abrev)) {
                    total += talento.bonusCaracteristica.get(j).bonus;
                }
            }
        }
        return total;
    }

    public boolean DevolverComunTalentoHabilidad(String nombre) {
        Categoria cat = null;

        Habilidad hab = DevolverHabilidadDeNombre(nombre);
        cat = hab.categoriaPadre;

        for (int i = 0; i < talentos.size(); i++) {
            Talento talento = talentos.get(i);
            //Existen Talentos que permiten escoger una habilidad común dentro de una categoría.
            for (int j = 0; j < talento.bonusCategoria.size(); j++) {
                if (talento.bonusCategoria.get(j).nombre.equals(cat.DevolverNombre())) {
                    if (talento.bonusCategoria.get(j).habilidadComun) {
                        if (talento.bonusCategoria.get(j).habilidadEscogida != null) {
                            if (talento.bonusCategoria.get(j).habilidadEscogida.DevolverNombre().equals(nombre)) {
                                return true;
                            }
                        }
                    }
                }
            }
            //Existen talentos que hacen una habilidad común.
            for (int j = 0; j < talento.bonusHabilidad.size(); j++) {
                if (talento.bonusHabilidad.get(j).nombre.equals(nombre)) {
                    if (talento.bonusHabilidad.get(j).comun) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int DevolverBonusTalentoApariencia() {
        int bonusAparienciaTalentos = 0;
        //Apariencia modificada por los talentos!
        for (int i = 0; i < Personaje.getInstance().talentos.size(); i++) {
            Talento tal = Personaje.getInstance().talentos.get(i);
            for (int j = 0; j < Personaje.getInstance().talentos.get(i).bonusHabilidad.size(); j++) {
                if (Personaje.getInstance().talentos.get(i).bonusHabilidad.get(j).nombre.equals("Apariencia")) {
                    bonusAparienciaTalentos += Personaje.getInstance().talentos.get(i).bonusHabilidad.get(j).bonus;
                }
            }
        }
        return bonusAparienciaTalentos;
    }

    /**
     * Pondera un talento. Cuanto mayor es el número significa que mejor es el
     * talento para este Pj.
     *
     * @param talento
     * @return
     */
    public int EsTalentoAdecuado(Talento talento) {
        int total = 0;
        if (talento.coste > 0) {
            //Categorias y Habilidades determinadas
            for (int j = 0; j < talento.bonusCategoria.size(); j++) {
                Categoria cat;
                if ((cat = DevolverCategoriaDeNombre(talento.bonusCategoria.get(j).nombre)).DevolverRangos() > nivel / 2 + 1) {
                    //Al depender de los rangos en habilidades, en nivel uno casi no salen talentos.
                    if (nivel == 1) {
                        total += Math.max(cat.DevolverRangos(), 1) * talento.bonusCategoria.get(j).bonus / 3;
                    } else {
                        total += cat.DevolverRangos() * talento.bonusCategoria.get(j).bonus / 3;
                    }
                }
            }
            for (int j = 0; j < talento.bonusHabilidad.size(); j++) {
                Habilidad hab;
                if ((hab = DevolverHabilidadDeNombre(talento.bonusHabilidad.get(j).nombre)) != null) {
                    try {
                        if (hab.DevolverRangos() > nivel / 2 + 1) {
                            if (nivel == 1) {
                                total += Math.max(hab.DevolverRangos(), 1) * talento.bonusHabilidad.get(j).bonus / 3;
                            } else {
                                total += hab.DevolverRangos() * talento.bonusHabilidad.get(j).bonus / 3;
                            }
                        }
                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                    }
                }
            }
            //Categorias y Habilidades a elegir.
            try {
                for (int j = 0; j < talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.size(); j++) {
                    Categoria cat;
                    Habilidad hab;
                    if ((cat = DevolverCategoriaDeNombre(talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.get(j))) != null) {
                        if (nivel == 1) {
                            total += Math.max(cat.DevolverRangos(), 1) * talento.bonusCategoriaHabilidadElegir.bonus / 3;
                        } else {
                            total += cat.DevolverRangos() * talento.bonusCategoriaHabilidadElegir.bonus / 3;
                        }
                    } else if ((hab = DevolverHabilidadDeNombre(talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.get(j))) != null) {
                        if (nivel == 1) {
                            total += Math.max(hab.DevolverRangos(), 1) * talento.bonusCategoriaHabilidadElegir.bonus / 3;
                        } else {
                            total += hab.DevolverRangos() * talento.bonusCategoriaHabilidadElegir.bonus / 3;
                        }
                    }
                }
            } catch (NullPointerException npe) {
            }
        }
        //Al depender de los rangos en habilidades, en nivel uno casi no salen talentos.
        if (nivel == 1) {
            return Math.min(total, 1);
        }
        return total;
    }

    public boolean EsDefectoAdecuado(Talento talento) {
        if (talento.coste < 0) {
            for (int j = 0; j < talento.bonusCategoria.size(); j++) {
                if (DevolverCategoriaDeNombre(talento.bonusCategoria.get(j).nombre).DevolverRangos() > 0) {
                    return false;
                }
            }
            for (int j = 0; j < talento.bonusHabilidad.size(); j++) {
                if (DevolverHabilidadDeNombre(talento.bonusHabilidad.get(j).nombre).DevolverRangos() > 0) {
                    return false;
                }
            }
            //Categorias y Habilidades a elegir.
            try {
                for (int j = 0; j < talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.size(); j++) {
                    Categoria cat;
                    Habilidad hab;
                    if ((cat = DevolverCategoriaDeNombre(talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.get(j))) != null) {
                        if (cat.DevolverRangos() > 0) {
                            return false;
                        }
                    } else if ((hab = DevolverHabilidadDeNombre(talento.bonusCategoriaHabilidadElegir.listadoCategoriasYHabilidadesElegidas.get(j))) != null) {
                        if (hab.DevolverRangos() > 0) {
                            return false;
                        }
                    }
                }
            } catch (NullPointerException npe) {
            }
        }
        return true;
    }

    /**
     * No puede existir un talento similar pero de otro grado.
     *
     * @param talento
     * @return
     */
    public boolean ExisteTalentoSimilar(Talento talento) {
        String[] tal = talento.nombre.split(" \\(");
        for (int i = 0; i < talentos.size(); i++) {
            if (talentos.get(i).nombre.contains(tal[0])) {
                return true;
            }
        }
        return false;
    }

    /**
     * *************************************************************
     *
     * PD Y COSTES
     *
     **************************************************************
     */
    /**
     * Cuanto cuesta la habilidad perteneciente a la categoria.
     *
     * @param cat
     * @param hab
     * @return
     */
    private int ObtenerCosteRangosHabilidad(Categoria cat, Habilidad hab) {
        int total = 0;
        if (hab.nuevosRangos >= 3) {
            total += CosteCategoriaYHabilidad(cat, 2, hab);
        }
        if (hab.nuevosRangos >= 2) {
            total += CosteCategoriaYHabilidad(cat, 1, hab);
        }
        if (hab.nuevosRangos >= 1) {
            total += CosteCategoriaYHabilidad(cat, 0, hab);
        }
        return total;
    }

    private int ObtenerCosteTodosRangosCategoria(Categoria cat) {
        int total = 0;
        if (cat.nuevosRangos >= 3) {
            total += CosteCategoriaYHabilidad(cat, 2, null);
        }
        if (cat.nuevosRangos >= 2) {
            total += CosteCategoriaYHabilidad(cat, 1, null);
        }
        if (cat.nuevosRangos >= 1) {
            total += CosteCategoriaYHabilidad(cat, 0, null);
        }
        return total;
    }

    public int PuntosDesarrolloGastadosEnHabilidadesYCategorias() {
        int total = 0;
        for (int i = 0; i < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            total += ObtenerCosteTodosRangosCategoria(cat);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                total += ObtenerCosteRangosHabilidad(cat, hab);
            }
        }
        return total;
    }

    public int PuntosDesarrolloGastadosEnAdiestramientos() {
        int total = 0;
        for (int j = 0; j < adiestramientosAntiguos.size(); j++) {
            String adiest = adiestramientosAntiguos.get(j);
            total += new LeerAdiestramientos(adiest, false).DevolverCosteDeAdiestramiento();
        }
        return total;
    }

    public int PuntosDesarrolloNoGastados() {
        return puntosDesarrolloNivel + puntosDesarrolloAnterioresNiveles
                - puntosDesarrolloGastadosAnterioresNiveles
                - PuntosDesarrolloGastadosEnHabilidadesYCategorias()
                - PuntosDesarrolloGastadosEnAdiestramientos()
                - PuntosDesarrolloGastadosEnOtros();
    }

    public int PuntosDesarrolloGastadosEnOtros() {
        int total = 0;
        for (int i = 0; i < otrasHabilidades.size(); i++) {
            total += otrasHabilidades.get(i).coste;
        }
        return total;
    }

    public int CalcularPuntosDesarrollo() {
        puntosDesarrolloNivel = caracteristicas.CalcularPuntosDesarrollo();
        return puntosDesarrolloNivel;
    }

    /**
     * Devuelve el coste ponderado de manera que una categoria 2/6 sea más
     * barata a una 2/7
     *
     * @return
     */
    public float CosteCategoriaYHabilidadPonderadoPorDosRangos(Categoria cat) {
        int coste1 = CosteCategoriaYHabilidad(cat, 0, null);
        int coste2 = CosteCategoriaYHabilidad(cat, 1, null);
        return (float) (coste1 + coste2 * 0.001);
    }

    public int CosteCategoriaYHabilidad(Categoria cat, int rangoComprado, Habilidad hab) {
        //El coste de hechizos depende del nivel de estos.
        int costeHechizo;
        int mulHechizos = 1;
        if (hab != null) {
            costeHechizo = (hab.rangos / 5);
            mulHechizos = hab.multiplicadorCosteHechizos;
            if (costeHechizo > 4) {
                costeHechizo = 4;
            }
        } else {
            costeHechizo = 0;
        }
        try {
            if (cat.DevolverNombre().equals("Listas Básicas de Hechizos")) {
                return listasBasicas[costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Abiertas de Hechizos")) {
                return listasAbiertas[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Cerradas de Hechizos")) {
                return listasCerradas[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Básicas de otras Profesiones")) {
                return listasOtros[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Abiertas de otros Reinos")) {
                return listasAbiertasOtros[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Cerradas de otros Reinos")) {
                return listasCerradasOtros[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Básicas de otros Reinos")) {
                return listasBasicasOtros[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Abiertas Arcanas")) {
                return listasAbiertasArcanas[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Hechizos de Adiestramiento")) {
                return listasPropiasAdiestramientos[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Hechizos de Adiestramientos de Otro Reino")) {
                return listasOtrasAdiestramientos[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Básicas de la Tríada")) {
                return listasTriada[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else if (cat.DevolverNombre().equals("Listas Básicas Elementales Complementarias")) {
                return listasElementalesComplementarias[0 + costeHechizo][rangoComprado] * mulHechizos;
            } else {
                return cat.costeRango[rangoComprado];
            }
        } catch (ArrayIndexOutOfBoundsException auofb) {
            return 10000;
        }

    }

    public String TieneRangosInsertados() {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).rangosInsertados > 0) {
                return categorias.get(i).DevolverNombre();
            }
            for (int j = 0; j < categorias.get(i).listaHabilidades.size(); j++) {
                if (categorias.get(i).listaHabilidades.get(j).rangosInsertados > 0) {
                    return categorias.get(i).listaHabilidades.get(j).DevolverNombre();
                }
            }
        }
        return "";
    }

    /**
     * *************************************************************
     *
     * GENERAR PERSONAJE
     *
     **************************************************************
     */
    /**
     * Indica el genero del personaje.
     *
     * @param sexoPj
     */
    public void AsignarSexoPersonaje(String sexoPj) {
        if (sexoPj.equals("Femenino") || sexoPj.equals("Mujer")) {
            sexo = "Femenino";
        } else {
            sexo = "Masculino";
        }

    }

    public String ObtenerNombrePersonaje() {
        try {
            if (sexo.equals("Femenino")) {
                nombreCompleto = nombresFemeninos.get(0);
            } else {
                nombreCompleto = nombresMasculinos.get(0);
            }

            nombreCompleto += " " + apellidos.get(0);
        } catch (IndexOutOfBoundsException iobe) {
            MostrarMensaje.showErrorMessage("Debes de poner un nombre a tu personaje.", "Personaje.");
            nombreCompleto = "Nombre.";
        }

        return nombreCompleto;
    }

    public void AsignarNombreCompleto(String nom) {
        nombreCompleto = nom;
    }

    boolean SeleccionarGrupoHabilidadesEspeciales(String tipo, String grupo, String cuando) {
        Habilidad hab;
        Categoria cat;

        String[] supuestaCategoria = grupo.split("#");
        if (supuestaCategoria != null && supuestaCategoria.length > 0 && (cat = DevolverCategoriaDeNombre(supuestaCategoria[0])) != null) {
            //Se selecciona aleatoriamente algunas habilidades.
            if (Esher.aleatorio) {
                for (int j = 0; j
                        < Integer.parseInt(supuestaCategoria[1].replace("}", "")); j++) {
                    hab = SeleccionarHabilidadAleatoriaDeCategoria(cat.DevolverNombre());
                    if (tipo.equals("Común") && cuando.equals("adiestramiento")) {
                        hab.HacerComunAdiestramiento();
                    }
                    if (tipo.equals("Común") && cuando.equals("profesion")) {
                        hab.HacerComunProfesion();
                    }

                    if (tipo.equals("Profesional")) {
                        hab.HacerProfesional();
                    }

                    if (tipo.equals("Restringida")) {
                        hab.HacerRestringida();
                    }

                }
            } else {
                //Las selecciona el usuario.
                if (ContarHabilidadesEspeciales(cat, tipo) < Integer.parseInt(supuestaCategoria[1].replace("}", ""))
                        && (grupoHab == null || !grupoHab.isVisible())) {
                    grupoHab = new ElegirComunProfesionalGUI(tipo, cat,
                            Integer.parseInt(supuestaCategoria[1].replace("}", "")), cuando);
                    grupoHab.setVisible(true);
                }

            }
            return true;
        }

        return false;
    }

    public int ContarHabilidadesEspeciales(Categoria cat, String tipo) {
        int cuantas = 0;
        for (int i = 0; i
                < cat.listaHabilidades.size(); i++) {
            Habilidad hab = cat.listaHabilidades.get(i);
            if (tipo.equals("Común") && hab.EsComun()) {
                cuantas++;
            }

            if (tipo.equals("Profesional") && hab.EsProfesional()) {
                cuantas++;
            }

            if (tipo.equals("Restringida") && hab.EsRestringida()) {
                cuantas++;
            }

        }
        return cuantas;
    }

    /**
     * Añade una habilidad extra y su coste al personaje. Sirve para contemplar
     * las infinitas posibilidaddes tales como poderes chi y otras cosas que no
     * voy a programar.
     */
    public void AñadirHabilidadesNoContempladas(String nombre, int pd) {
        OtraHabilidad otraHabilidad = new OtraHabilidad(nombre, pd);
        otrasHabilidades.add(otraHabilidad);
    }

    public int[] ConvertirStringCosteEnIntCoste(String costeString) {
        int[] costeInt = new int[3];
        String[] coste = costeString.split("/");
        try {
            costeInt[0] = Integer.parseInt(coste[0]);
            if (coste.length > 1) {
                costeInt[1] = Integer.parseInt(coste[1]);
            } else {
                costeInt[1] = 1000;
            }

            if (coste.length > 2) {
                costeInt[2] = Integer.parseInt(coste[2]);
            } else {
                costeInt[2] = 1000;
            }

        } catch (NumberFormatException nfe) {
            MostrarMensaje.showErrorMessage("Coste mal formado.", "Personaje");
        }

        return costeInt;
    }

    public Habilidad SeleccionarHabilidadAleatoriaDeCategoria(String nombre) {
        Categoria cat = DevolverCategoriaDeNombre(nombre);
        int n = Esher.generator.nextInt(cat.listaHabilidades.size());
        return cat.listaHabilidades.get(n);
    }

    public String SeleccionarNombreHabilidadDeListado(String listadoPorPuntosYComas, String tipo, String cuando) {
        String[] listadoHabilidades = listadoPorPuntosYComas.split(";");
        if (Esher.aleatorio) {
            int n = Esher.generator.nextInt(listadoHabilidades.length);
            return listadoHabilidades[n];
        } else {
            //Las selecciona el usuario.
            try {
                Habilidad hab = DevolverHabilidadDeNombre(listadoHabilidades[0]);

                Categoria cat = hab.DevolverCategoria();
                if (grupoHab == null || !grupoHab.isVisible() || cuando.equals("profesion")) {  //Hay profesiones que sacan varias ventanas de habilidades comunes.
                    grupoHab = new ElegirComunProfesionalGUI(tipo, cat,
                            1, cuando, listadoHabilidades);
                    grupoHab.setVisible(true);
                }
            } catch (NullPointerException npe) {
                MostrarMensaje.showErrorMessage("Habilidad desconocida: " + listadoHabilidades[0], "Personaje");
            }

        }
        return "Ninguna";
    }

    public void BorraAntiguosCostesAdiestramiento() {
        costesAdiestramientos = new CostesAdiestramientos();
    }

    public void ActualizaArmas() throws Exception {
        armas = new Armas();
        Esher.LeerCategoriasDeArchivo();
    }

    /**
     * Existen algunos especiales de razas que se aplican a habilidades.
     * Entonces los contabilizamos.
     */
    public void HacerFijoEspecialesRaza() {
        String lineaEspecial;
        Habilidad hab;

        for (int i = 0; i
                < especialesRaza.size(); i++) {
            lineaEspecial = especialesRaza.get(i);
            if (!lineaEspecial.contains("Ninguno")) {
                String[] vectorEspecial = lineaEspecial.split("\t");
                if (vectorEspecial.length > 0) {
                    vectorEspecial[0] = vectorEspecial[0].replace("+", "");
                    try {
                        if ((hab = DevolverHabilidadDeNombre(vectorEspecial[1])) != null) {
                            hab.AñadirBonusEspecialRaza(Integer.parseInt(vectorEspecial[0]));
                        } else {
                            Categoria cat;
                            if ((cat = DevolverCategoriaDeNombre(vectorEspecial[1])) != null) {
                                for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                                    try {
                                        hab = cat.listaHabilidades.get(j);
                                        hab.AñadirBonusEspecialRaza(Integer.parseInt(vectorEspecial[0]));
                                    } catch (NullPointerException npe) {
                                        npe.printStackTrace();
                                    }
                                }

                            } else {
                                if (vectorEspecial[1].equals("Frio")) {
                                    trFrio += Integer.parseInt(vectorEspecial[0]);
                                }

                                if (vectorEspecial[1].equals("Calor")) {
                                    trCalor += Integer.parseInt(vectorEspecial[0]);
                                }

                                if (vectorEspecial[1].equals("Miedo")) {
                                    trMiedo += Integer.parseInt(vectorEspecial[0]);
                                }

                                if (vectorEspecial[1].equals("BD")) {
                                    bonusDefensiva += Integer.parseInt(vectorEspecial[0]);
                                }
                                if (vectorEspecial[1].equals("TA")) {
                                    armaduraNatural = Integer.parseInt(vectorEspecial[0]);
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException aiofb) {
                    }
                }
            }
        }
    }

    public void CambiarGolpesArtesMarcialesGenericosADiversosGrados() {
        if (Esher.variosGradosGolpes) {
            Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre("Artes Marciales·Golpes");
            cat.BorrarHabilidad("Golpes de Artes Marciales");
            Habilidad hab = Habilidad.getSkill(cat, "Golpes Grado 1");
            cat.AddHabilidad(hab);
            hab = Habilidad.getSkill(cat, "Golpes Grado 2");
            cat.AddHabilidad(hab);
            hab = Habilidad.getSkill(cat, "Golpes Grado 3");
            cat.AddHabilidad(hab);
            hab = Habilidad.getSkill(cat, "Golpes Grado 4");
            cat.AddHabilidad(hab);

            cat = Personaje.getInstance().DevolverCategoriaDeNombre("Artes Marciales·Barridos");
            cat.BorrarHabilidad("Barridos de Artes Marciales");
            hab = Habilidad.getSkill(cat, "Barridos Grado 1");
            cat.AddHabilidad(hab);
            hab = Habilidad.getSkill(cat, "Barridos Grado 2");
            cat.AddHabilidad(hab);
            hab = Habilidad.getSkill(cat, "Barridos Grado 3");
            cat.AddHabilidad(hab);
            hab = Habilidad.getSkill(cat, "Barridos Grado 4");
            cat.AddHabilidad(hab);
        } else {
            Categoria cat = Personaje.getInstance().DevolverCategoriaDeNombre("Artes Marciales·Golpes");
            cat.BorrarHabilidad("Golpes Grado 1");
            cat.BorrarHabilidad("Golpes Grado 2");
            cat.BorrarHabilidad("Golpes Grado 3");
            cat.BorrarHabilidad("Golpes Grado 4");
            Habilidad hab = Habilidad.getSkill(cat, "Golpes de Artes Marciales");
            cat.AddHabilidad(hab);

            cat = Personaje.getInstance().DevolverCategoriaDeNombre("Artes Marciales·Barridos");
            cat.BorrarHabilidad("Barridos Grado 1");
            cat.BorrarHabilidad("Barridos Grado 2");
            cat.BorrarHabilidad("Barridos Grado 3");
            cat.BorrarHabilidad("Barridos Grado 4");
            hab = Habilidad.getSkill(cat, "Barridos de Artes Marciales");
            cat.AddHabilidad(hab);
        }
    }

    /**
     * *************************************************************
     *
     * MAGIA
     *
     **************************************************************
     */
    /**
     * Añade un hechizo a las lista de hechizos seleccionables por la interfaz.
     *
     * @param lista
     */
    public void AddHechizo(ListaDeHechizos lista) {
        boolean existe = false;
        for (int i = 0; i < listaHechizos.size(); i++) {
            if (lista.nombre.equals(listaHechizos.get(i).nombre) && lista.clase.equals(listaHechizos.get(i).clase)) {
                existe = true;
                break;
            }

        }
        if (!existe) {
            listaHechizos.add(lista);
        }

    }

    public void ObtenerMagia() {
        try {
            Magia magia = new Magia();
            magia.ObtenerMagiaPorProfesion(reino);
            ActualizaCaracteristicasReino();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Para las profesiones mágicas híbridas, los puntos de poder son la media
     * de los reinos usados.
     */
    private String ObtenerMediaPuntosDePoder(List<Integer> añadir) {
        String progresionFinal = "";
        if (añadir.size() > 0) {
            for (int i = 0; i
                    < 5; i++) {
                int valor = 0;
                for (int j = 0; j
                        < añadir.size(); j++) {
                    String[] progresionPPAnt = progresionesPuntosPoder[añadir.get(j)].split("/");
                    valor +=
                            Integer.parseInt(progresionPPAnt[i]);
                }

                progresionFinal += (valor / añadir.size());
                if (i < 4) {
                    progresionFinal += "/";
                }

            }
            return progresionFinal;
        } else {
            return "0/0/0/0/0";
        }

    }

    /**
     * Para las profesiones mágicas híbridas, los puntos de poder son la media
     * de los reinos usados.
     */
    public void ObtenerMediaCostePuntosDePoder() {
        String[] reinosElegidos = reino.split("/");
        List<Integer> añadir = new ArrayList<Integer>();
        for (int i = 0; i
                < reinosElegidos.length; i++) {
            if (reinosElegidos[i].equals("Canalización")) {
                añadir.add(CANALIZACION);
            }

            if (reinosElegidos[i].equals("Esencia")) {
                añadir.add(ESENCIA);
            }

            if (reinosElegidos[i].equals("Mentalismo")) {
                añadir.add(MENTALISMO);
            }

            if (reinosElegidos[i].equals("Psiónico")) {
                añadir.add(PSIONICO);
            }

            if (reinosElegidos[i].equals("Arcano")) {
                añadir.add(ARCANO);
            }

        }

        progresionPuntosPoder = ObtenerMediaPuntosDePoder(añadir);

    }

    /**
     * *************************************************************
     *
     * ADIESTRAMIENTO
     *
     **************************************************************
     */
    /**
     * Traduce el adiestramiento escogido al personaje.
     */
    public void ConfirmarAdiestramiento() {
        PasarHabilidadesAdiestramientoAPersonaje();
        SubirCaracteristicasSeguras();
        ObtenerObjetosAdiestramiento();
        adiestramientosAntiguos.add(adiestramiento.nombre);
        adiestramiento = null;
    }

    /**
     * Incrementa las caracteristicas que debe de subir el adiestramiento. Si es
     * una lista a elegir, se suben en la ventana de adiestramiento en vez de
     * usar esta función
     */
    private void SubirCaracteristicasSeguras() {
        List<String> subirCaracteristicas = adiestramiento.DevolverAumentoCaracteristica();
        for (int i = 0; i < subirCaracteristicas.size(); i++) {
            Caracteristica car = caracteristicas.DevolverCaracteristicaDeAbreviatura(subirCaracteristicas.get(i));
            if (car != null) {
                car.SubirNivelCaracteristica();
                //Personaje.getInstance().caracteristicas.ActualizarCaracteristica(car);
            }

        }
    }

    public void PasarHabilidadesAdiestramientoAPersonaje() {
        adiestramiento.AñadirListasHechizosAdiestramiento();
        for (int i = 0; i < adiestramiento.DevolverListaCategorias().size(); i++) {
            String catAd = adiestramiento.DevolverListaCategorias().get(i).nombre;
            Categoria cat = DevolverCategoriaDeNombre(catAd);
            if (adiestramiento.UnicoAdiestramientoGrupo(cat.DevolverNombre())) {
                cat.rangosAdiestramiento += adiestramiento.DevolverListaCategorias().get(i).rangos;
            } else if (adiestramiento.EsCategoriaGrupoSeleccionada(cat.DevolverNombre())) {
                cat.rangosAdiestramiento += adiestramiento.DevolverListaCategorias().get(i).rangosGrupo;
            }

            for (int j = 0; j < adiestramiento.DevolverListaCategorias().get(i).DevolverListaHabilidades().size(); j++) {
                try {
                    Habilidad hab = cat.DevolverHabilidadDeNombre(adiestramiento.DevolverListaCategorias().get(i).DevolverListaHabilidades().get(j).nombre);
                    hab.rangosAdiestramiento += adiestramiento.DevolverListaCategorias().get(i).DevolverListaHabilidades().get(j).Rangos();
                } catch (NullPointerException nme) {
                    MostrarMensaje.showErrorMessage("Habilidad encontrada en el adiestramiento (" + adiestramiento.nombre + "): " + adiestramiento.DevolverListaCategorias().get(i).DevolverListaHabilidades().get(j).nombre + ", no existente.", "Personaje");
                }

            }
        }
    }

    public void ObtenerObjetosAdiestramiento() {
        int aceptados = 1;
        for (int i = 0; i < adiestramiento.especialesAdiestramiento.size(); i++) {
            if ((Esher.generator.nextInt(100)) < adiestramiento.especialesAdiestramiento.get(i).probabilidad / (aceptados)) {
                String eq = adiestramiento.especialesAdiestramiento.get(i).nombre;
                if (adiestramiento.especialesAdiestramiento.get(i).bonus > 0) {
                    eq += "\t" + adiestramiento.especialesAdiestramiento.get(i).bonus;
                }
                equipo.add(eq);
                aceptados++;
            }
        }
    }

    /**
     * *************************************************************
     *
     * SUBIR NIVEL
     *
     **************************************************************
     */
    /**
     * Sube un nivel el personaje.
     */
    public void SubirUnNivel() {
        InicioSubirNivel();
        CalcularSubidaCaracteristicas();
        Personaje.getInstance().lock = true;
        Personaje.getInstance().loadedFrom = "";
        CalcularPuntosDesarrollo();
    }

    public void InicioSubirNivel() {
        puntosDesarrolloAnterioresNiveles += puntosDesarrolloNivel;
        puntosDesarrolloGastadosAnterioresNiveles +=
                PuntosDesarrolloGastadosEnHabilidadesYCategorias();
        nivel++;
        Esher.bucleHabilidades = 0;
        LimpiarRangos();
    }

    private void CalcularSubidaCaracteristicas() {
        caracteristicas.SubirNivelCaracteristicas();
    }

    /**
     * Limpia los rangos añadidos en el nivel anterior para que en el proximo
     * nivel los costes de rangos no contemeplen los comprados anteriormente.
     */
    private void LimpiarRangos() {
        for (int i = 0; i
                < categorias.size(); i++) {
            Categoria cat = categorias.get(i);
            cat.ConfirmarCategoria();
            for (int j = 0; j
                    < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                hab.ConfirmarHabilidad();
                hab.multiplicadorCosteHechizos = 1;
            }

        }
    }

    /**
     * *************************************************************
     *
     * OBJETOS MAGICOS
     *
     **************************************************************
     */
    /**
     * Añade un objeto mágico
     */
    public void AñadirObjetoMagico(String nombre, Categoria cat, Integer bonusCat, Habilidad hab, Integer bonusHab, boolean historial) {
        ObjetoMagico objeto;
        if (nombre.length() > 0) {
            if ((objeto = DesapilarObjetoMagico(nombre)) == null) {
                objeto = new ObjetoMagico(nombre, historial);
            }

            if (cat != null) {
                objeto.AñadirBonusCategoria(cat, bonusCat);
            }

            if (hab != null) {
                objeto.AñadirBonusHabilidad(hab, bonusHab);
            }

            objetosMagicos.add(objeto);
        }

    }

    private ObjetoMagico DesapilarObjetoMagico(String nombre) {
        ObjetoMagico objeto;
        for (int i = 0; i
                < objetosMagicos.size(); i++) {
            if (objetosMagicos.get(i).nombre.equals(nombre)) {
                objeto = objetosMagicos.get(i);
                objetosMagicos.remove(objeto);
                return objeto;
            }

        }
        return null;
    }

    public ObjetoMagico DevolverObjetoMagico(String nombre) {
        for (int i = 0; i
                < objetosMagicos.size(); i++) {
            if (objetosMagicos.get(i).nombre.equals(nombre)) {
                return objetosMagicos.get(i);
            }

        }
        return null;
    }

    public void BorrarObjeto(String nombre) {
        DesapilarObjetoMagico(nombre);
    }

    public boolean ExisteObjetoModificaCategoria(Categoria cat) {
        for (int i = 0; i < objetosMagicos.size(); i++) {
            if (objetosMagicos.get(i).DevolverBonusCategoria(cat) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean ExisteObjetoModificaHabilidad(Habilidad hab) {
        for (int i = 0; i < objetosMagicos.size(); i++) {
            if (objetosMagicos.get(i).DevolverBonusHabilidad(hab) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * *************************************************************
     *
     * CLASES SECUNDARIAS
     *
     **************************************************************
     */
    /**
     * *************************************************************
     *
     * CLASES SECUNDARIAS
     *
     **************************************************************
     */
    public class CostesAdiestramientos implements Serializable {

        private List<String> adiestramientos;
        private List<Integer> costes;
        private List<Boolean> preferido;
        private List<Boolean> prohibido;

        public CostesAdiestramientos() {
            costes = new ArrayList<Integer>();
            adiestramientos = new ArrayList<String>();
            preferido = new ArrayList<Boolean>();
            prohibido = new ArrayList<Boolean>();
        }

        public void AñadirAdiestramiento(String nombre, int coste) {
            if (!ExisteAdiestramiento(nombre)) {
                adiestramientos.add(nombre);
                costes.add(coste);
                preferido.add(false);
                prohibido.add(false);
            }
        }

        public boolean ExisteAdiestramiento(String nombre) {
            for (int i = 0; i < adiestramientos.size(); i++) {
                if (adiestramientos.get(i).equals(nombre)) {
                    return true;
                }
            }
            return false;
        }

        public void AñadirAdiestramientoPreferido(String nombre, int coste) {
            if (!ExisteAdiestramiento(nombre)) {
                adiestramientos.add(nombre);
                costes.add(coste);
                preferido.add(true);
                prohibido.add(false);
            }
        }

        public void AñadirAdiestramientoProhibido(String nombre, int coste) {
            if (!ExisteAdiestramiento(nombre)) {
                adiestramientos.add(nombre);
                costes.add(coste);
                preferido.add(false);
                prohibido.add(true);
            }
        }

        public int ObtenerCosteAdiestramiento(String nombre) {
            for (int i = 0; i < adiestramientos.size(); i++) {
                if (adiestramientos.get(i).equals(nombre)) {
                    return costes.get(i);
                }
            }
            return 1000;
        }

        public boolean EsAdiestramientoPreferido(String nombre) {
            for (int i = 0; i < adiestramientos.size(); i++) {
                if (adiestramientos.get(i).equals(nombre)) {
                    return preferido.get(i);
                }
            }
            return false;
        }

        public boolean EsAdiestramientoProhibido(String nombre) {
            for (int i = 0; i < adiestramientos.size(); i++) {
                if (adiestramientos.get(i).equals(nombre)) {
                    return prohibido.get(i);
                }
            }
            return false;
        }
    }

    public class OtraHabilidad implements Serializable {

        public String nombre;
        public int coste;

        public OtraHabilidad(String tmp_nombre, int tmp_coste) {
            nombre = tmp_nombre;
            coste = tmp_coste;
        }
    }

    public class HechizoCultura implements Serializable {

        public String nombre;
        public int rangos;

        public HechizoCultura(String tmp_nombre, int tmp_rangos) {
            nombre = tmp_nombre;
            rangos = tmp_rangos;
        }
    }
}

/*
 * DirectorioRolemaster.java
 *
 *
 * This software is designed by Jorge Hortelano Otero.
 * SoftwareMagico@gmail.com
 * Copyright (C) 2007 Jorge Hortelano Otero.
 * C/Botanico 12, 1. Valencia CP:46008 (Spain).
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 *
 * Created on 22 de noviembre de 2007, 15:14
 *
 *
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

import com.softwaremagico.explorarDirectorios.Directorio;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Hortelano
 */
public class DirectorioRolemaster implements Serializable {

    private final String DIRECTORIO_CATEGORIAS = "";
    final String DIRECTORIO_PROFESION = "profesiones";
    final String DIRECTORIO_TALENTOS = "talentos";
    final String DIRECTORIO_RAZAS = "razas";
    final String DIRECTORIO_CULTURAS = "culturas";
    final String DIRECTORIO_ARMAS = "armas";
    private final String ARCHIVO_CATEGORIAS = "categorías.txt";
    private final String DIRECTORIO_HECHIZOS = "hechizos";
    final String DIRECTORIO_ADIESTRAMIENTOS = "adiestramientos";
    private final String DIRECTORIO_CONFIGURACION = "configuracion";
    private final String DIRECTORIO_STORE_USER_DATA = "librodeesher";
    private final String MODULOS = "modulos.txt";
    private final String CONFIG = "configuracion.txt";
    private Directorio directorio;
    private List<String> ficherosOcultos;
    private Esher esher;
    final String DIRECTORIO_ROLEMASTER = "rolemaster";
    final String DIRECTORIO;

    /** Creates a new instance of DirectorioRolemaster */
    public DirectorioRolemaster(String tmp_directorio, Esher tmp_esher) throws Exception {
        DIRECTORIO = tmp_directorio;
        esher = tmp_esher;
        GenerarListadoFicherosOcultos();
        if (DIRECTORIO.endsWith("/")) {
            directorio = new Directorio(DIRECTORIO + DIRECTORIO_ROLEMASTER);
        } else {
            directorio = new Directorio(DIRECTORIO + File.separator + DIRECTORIO_ROLEMASTER);
        }
    }

    public String DevolverDirectorio() {
        return directorio.DevolverDirectorio();
    }

    /*public String DevolverDirectorioRazas() {
    return DIRECTORIO + File.separator + DIRECTORIO_ROLEMASTER + File.separator + DIRECTORIO_RAZAS;
    }
     */
    private void GenerarListadoFicherosOcultos() {
        ficherosOcultos = new ArrayList<String>();
        ficherosOcultos.add("plantilla");
        ficherosOcultos.add("costes");
        ficherosOcultos.add("Raciales");
        ficherosOcultos.add(".txt~");
    }

    public List<String> ReinosDeMagiaDisponibles(List<String> directorios) throws Exception {
        List<String> ficherosReinos = new ArrayList<String>();
        for (int i = 0; i < directorios.size(); i++) {
            ficherosReinos.addAll(directorio.ObtenerFicherosSubdirectorio(directorios.get(i) +
                    File.separator + DIRECTORIO_HECHIZOS));
        }
        List<String> reinos = new ArrayList<String>();
        for (int i = 0; i < ficherosReinos.size(); i++) {
            String reino = ficherosReinos.get(i);
            for (int j = 0; j < ficherosOcultos.size(); j++) {
                if (!reino.contains(ficherosOcultos.get(j)) && !reinos.contains(ficherosReinos.get(i))) {
                    reinos.add(ficherosReinos.get(i));
                }
            }
        }
        return reinos;
    }

    public List<String> RazasDisponibles(List<String> directorios) throws Exception {
        List<String> razasTodos = new ArrayList<String>();
        for (int i = 0; i < directorios.size(); i++) {
            razasTodos.addAll(directorio.ObtenerFicherosSubdirectorio(directorios.get(i) + File.separator +
                    DIRECTORIO_RAZAS));
        }
        List<String> razas = new ArrayList<String>();
        for (int i = 0; i < razasTodos.size(); i++) {
            if (!ficherosOcultos.contains(razasTodos.get(i))) {
                razas.add(razasTodos.get(i));
            }
        }
        return razas;
    }

    public List<String> TiposArmasDisponibles(List<String> directorios) throws Exception {
        List<String> armasTodos = new ArrayList<String>();
        for (int i = 0; i < directorios.size(); i++) {
            armasTodos.addAll(directorio.ObtenerFicherosSubdirectorio(directorios.get(i) +
                    File.separator + DIRECTORIO_ARMAS));
        }
        List<String> armas = new ArrayList<String>();
        for (int i = 0; i < armasTodos.size(); i++) {
            if (!ficherosOcultos.contains(armasTodos.get(i))) {
                armas.add(armasTodos.get(i));
            }
        }
        return armas;
    }

    public List<String> CulturasDisponibles(List<String> directorios) throws Exception {
        List<String> culturasTodos = new ArrayList<String>();
        for (int i = 0; i < directorios.size(); i++) {
            culturasTodos.addAll(directorio.ObtenerFicherosSubdirectorio(directorios.get(i) +
                    File.separator + DIRECTORIO_CULTURAS));
        }
        List<String> culturas = new ArrayList<String>();
        for (int i = 0; i < culturasTodos.size(); i++) {
            if (!ficherosOcultos.contains(culturasTodos.get(i))) {
                culturas.add(culturasTodos.get(i));
            }
        }
        return culturas;
    }

    public List<String> CulturasDisponiblesSubString(List<String> directorios, String substring) throws Exception {
        List<String> culturas = new ArrayList<String>();
        List<String> culturasTodos = CulturasDisponibles(directorios);
        for (int i = 0; i < culturasTodos.size(); i++) {
            if (culturasTodos.get(i).contains(substring)) {
                culturas.add(culturasTodos.get(i));
            }
        }
        return culturas;
    }

    public List<String> ProfesionesDisponibles(List<String> directorios) throws Exception {
        List<String> profesionesTodos = new ArrayList<String>();
        for (int i = 0; i < directorios.size(); i++) {
            profesionesTodos.addAll(directorio.ObtenerFicherosSubdirectorio(directorios.get(i) +
                    File.separator + DIRECTORIO_PROFESION));
        }
        List<String> profesiones = new ArrayList<String>();
        for (int i = 0; i < profesionesTodos.size(); i++) {
            if (!ficherosOcultos.contains(profesionesTodos.get(i))) {
                profesiones.add(profesionesTodos.get(i));
            }
        }
        return profesiones;
    }

    public List<String> CategoriasDisponibles(List<String> directorios) throws Exception {
        List<String> categoriasTodos = new ArrayList<String>();
        for (int i = 0; i < directorios.size(); i++) {
            if (directorio.ObtenerFicherosSubdirectorio(directorios.get(i) +
                    File.separator + DIRECTORIO_CATEGORIAS).size() > 0) {
                if (new File(directorios.get(i) + File.separator + DIRECTORIO_CATEGORIAS + ARCHIVO_CATEGORIAS).exists()) {
                    categoriasTodos.add(directorios.get(i) +
                            File.separator + DIRECTORIO_CATEGORIAS);
                }
            }
        }

        return categoriasTodos;
    }

    public List<String> AdiestramientosDisponibles(List<String> directorios) throws Exception {
        List<String> adiestramientosTodos = new ArrayList<String>();
        for (int i = 0; i < directorios.size(); i++) {
            adiestramientosTodos.addAll(directorio.ObtenerFicherosSubdirectorio(directorios.get(i) +
                    File.separator + DIRECTORIO_ADIESTRAMIENTOS));
        }
        List<String> adiestramientos = new ArrayList<String>();
        for (int i = 0; i < adiestramientosTodos.size(); i++) {
            if (!ficherosOcultos.contains(adiestramientosTodos.get(i))) {
                adiestramientos.add(adiestramientosTodos.get(i));
            }
        }
        return adiestramientos;
    }

    public List<String> LeerLineasHechizos(String file) {
        List<String> lineasFicheroHechizos = new ArrayList<String>();
        List<String> ficherosHechizos = DevolverPathsFichero(DIRECTORIO_HECHIZOS + File.separator + file);
        for (int i = 0; i < ficherosHechizos.size(); i++) {
            lineasFicheroHechizos.addAll(directorio.LeerLineasFichero(ficherosHechizos.get(i)));
        }
        return lineasFicheroHechizos;
    }

    public List<String> LeerLineasTalentos(String file) {
        List<String> lineasFicheroTalentos = new ArrayList<String>();
        List<String> ficherosTalentos = DevolverPathsFichero(DIRECTORIO_TALENTOS + File.separator + file);
        for (int i = 0; i < ficherosTalentos.size(); i++) {
            lineasFicheroTalentos.addAll(directorio.LeerLineasFichero(ficherosTalentos.get(i)));
        }
        return lineasFicheroTalentos;
    }

    public List<String> LeerLineasCultura(String file) {
        return directorio.LeerLineasFichero(file);
    }

    public List<String> LeerLineasAdiestramiento(String file) {
        return directorio.LeerLineasFichero(file);
    }

    public List<String> LeerLineasCosteAdiestramiento(String file) {
        return directorio.LeerLineasFichero(DevolverDirectorio() + File.separator +
                DIRECTORIO_ADIESTRAMIENTOS + File.separator + file);
    }

    public List<String> LeerLineasRaza(String file) {
        return directorio.LeerLineasFichero(file);
    }

    public List<String> LeerLineasProfesion(String file) {
        return directorio.LeerLineasFichero(file);
    }

    public List<String> LeerLineasArmas(String file) {
        return directorio.LeerLineasFichero(file);
    }

    public List<String> LeerLineasCategorias(String file) {
        return directorio.LeerLineasFichero(file);
    }

    public String LeerFicheroComoTexto(String folder) {
        return directorio.LeerFicheroComoTexto(folder);
    }

    public Object[][] LeerCostesAdiestramiento() {
        List<String> filas;
        String[] columna;
        Object[][] costesAdiestramiento;

        filas = directorio.LeerLineasFichero(DevolverDirectorio() + File.separator +
                DIRECTORIO_ADIESTRAMIENTOS + File.separator + "costes.txt");

        columna = filas.get(0).split("\t");

        costesAdiestramiento = new Object[filas.size() - 1][columna.length];

        for (int i = 1; i < filas.size(); i++) {
            String[] linea = filas.get(i).split("\t");
            for (int j = 1; j < linea.length; j++) {
                costesAdiestramiento[i][j] = linea[j];
            }

        }
        return costesAdiestramiento;
    }

    public void GuardarEnFichero(String texto, String file) {
        directorio.SaveTextInFile(texto, file);
    }

    public List<String> ObtenerModulosRolemasterGuardados() {
        String file = ObtenerPathModulos(false);
        List<String> pathModulos = directorio.LeerLineasFichero(file);

        for (int i = 0; i < pathModulos.size(); i++) {
            if (!pathModulos.get(i).startsWith(DIRECTORIO)) {
                String direct = DIRECTORIO;
                if (!direct.endsWith(File.separator)) {
                    direct += File.separator;
                }
                pathModulos.set(i, direct + pathModulos.get(i));
            }
        }

        return pathModulos;
    }

    public List<String> ObtieneConfiguracionGuardada() {
        String file = ObtenerPathConfiguracion(false);
        return directorio.LeerLineasFichero(file);
    }

    public String ObtenerPathConfigEnHome() {
        String config = System.getProperty("user.home");
        String soName = System.getProperty("os.name");
        if (soName.contains("Linux") || soName.contains("linux")) {
            directorio.GenerarDirectorioSiNoExiste(config + File.separator + "." + DIRECTORIO_STORE_USER_DATA + File.separator);
            directorio.GenerarDirectorioSiNoExiste(config + File.separator + "." + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator);
            return config + File.separator + "." + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator;
        } else if (soName.contains("Windows") || soName.contains("windows") || soName.contains("vista") || soName.contains("Vista")) {
            directorio.GenerarDirectorioSiNoExiste(config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator);
            directorio.GenerarDirectorioSiNoExiste(config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator);
            return config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator;
        }
        return config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator;

    }

    /**
     * Return the path of the modules list file.
     * @param write True if is for writting a new configuration file. False for reading.
     * @return
     */
    public String ObtenerPathModulos(boolean write) {
        String pathModulos = ObtenerPathConfigEnHome() + MODULOS;

        File test = new File(pathModulos);
        if (write || test.exists()) {
            return pathModulos;
        }
        return DIRECTORIO + File.separator + DIRECTORIO_CONFIGURACION + File.separator + MODULOS;
    }

    /**
     * Return the path of the configuration file.
     * @param write True if is for writting a new configuration file. False for reading.
     * @return
     */
    public String ObtenerPathConfiguracion(boolean write) {
        String pathConfig = ObtenerPathConfigEnHome() + CONFIG;
        File test = new File(pathConfig);
        if (write || test.exists()) {
            return pathConfig;
        }
        return DIRECTORIO + File.separator + DIRECTORIO_CONFIGURACION + File.separator + CONFIG;
    }

    public void GuardarListaAFichero(List<String> modulos, String file) {
        directorio.SaveListInFile(modulos, file);
    }

    private List<String> DevolverPathsFichero(String fichero) {
        List<String> ficheros = new ArrayList<String>();
        for (int i = 0; i < esher.modulosRolemaster.size(); i++) {
            File file = new File(esher.modulosRolemaster.get(i) + File.separator + fichero);
            if (file.exists()) {
                ficheros.add(file.toString());
            }
        }
        return ficheros;
    }
}

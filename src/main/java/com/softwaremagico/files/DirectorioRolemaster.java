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
package com.softwaremagico.files;
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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Hortelano
 */
public class DirectorioRolemaster implements Serializable {

    public static final String DIRECTORIO_CATEGORIAS = "";
    private static final String APPLICATION_FOLDER = getApplicationInstallationDirectory();
    public static final String ROLEMASTER_FOLDER = APPLICATION_FOLDER +  File.separator + "rolemaster";
    public static final String DIRECTORIO_PROFESION = "profesiones";
    public final static String DIRECTORIO_TALENTOS = "talentos";
    public final static String DIRECTORIO_RAZAS = "razas";
    public final static String DIRECTORIO_CULTURAS = "culturas";
    public final static String DIRECTORIO_ARMAS = ROLEMASTER_FOLDER + File.separator + "armas";
    public static final String DIRECTORIO_HECHIZOS = "hechizos";
    public final static String DIRECTORIO_ADIESTRAMIENTOS = "adiestramientos";
    public final static String DIRECTORIO_MODULOS = ROLEMASTER_FOLDER + File.separator + "modulos";
    public static final String DIRECTORIO_CONFIGURACION = "configuracion";
    public static final String DIRECTORIO_STORE_USER_DATA = "librodeesher";
    public static final String ARCHIVO_CATEGORIAS = "categorias.txt";
    private static final String MODULOS = "modulos.txt";
    private static final String CONFIG = "configuracion.txt";
    //private Folder directorio;
    private static List<String> ficherosOcultos = ignoredFiles();
    // Modulos configurados para obtener los ficheros adecuados */
    public static List<String> modulosRolemaster = ObtenerModulosRolemaster();
    public static final boolean verbose = false;

    /**
     * Creates a new instance of DirectorioRolemaster
     */
    public DirectorioRolemaster() throws Exception {
    }

    private static String getApplicationInstallationDirectory() {
        File directory = new File(DirectorioRolemaster.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        //Carga local mediante netbeans
        if (directory.getParentFile().getAbsolutePath().contains("target")) {
            return directory.getParentFile().getParentFile().getAbsolutePath();
        }
        //Carga desde el instalador. 
        return directory.getParentFile().getAbsolutePath();
    }

    private static List<String> ignoredFiles() {
        List<String> ignoredFiles = new ArrayList<>();
        ignoredFiles = new ArrayList<>();
        ignoredFiles.add("plantilla");
        ignoredFiles.add("costes");
        ignoredFiles.add("Raciales");
        ignoredFiles.add(".txt~");
        return ignoredFiles;
    }

    private static List<String> filesAvailable(String folder) throws Exception {
        List<String> files = new ArrayList<>();
        for (int i = 0; i < modulosRolemaster.size(); i++) {
            files.addAll(Folder.ObtainfilesSubdirectory(DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i)
                    + File.separator + folder));
        }
        List<String> fileList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            for (int j = 0; j < ficherosOcultos.size(); j++) {
                if (!files.get(i).contains(ficherosOcultos.get(j)) && !fileList.contains(files.get(i))) {
                    fileList.add(files.get(i));
                }
            }
        }
        return fileList;
    }

    public static List<String> ReinosDeMagiaDisponibles() throws Exception {
        return filesAvailable(DIRECTORIO_HECHIZOS);
    }

    public static List<String> RazasDisponibles() throws Exception {
        return filesAvailable(DIRECTORIO_RAZAS);
    }

    public static List<String> TiposArmasDisponibles() throws Exception {
        List<String> armasTodos = new ArrayList<>();
        armasTodos.addAll(Folder.ObtainfilesSubdirectory(DIRECTORIO_ARMAS));
        List<String> armas = new ArrayList<>();
        for (int i = 0; i < armasTodos.size(); i++) {
            if (!ficherosOcultos.contains(armasTodos.get(i))) {
                armas.add(armasTodos.get(i));
            }
        }
        return armas;
    }

    public static List<String> CulturasDisponibles() throws Exception {
        return filesAvailable(DIRECTORIO_CULTURAS);
    }

    public static List<String> CulturasDisponiblesSubString(String substring) throws Exception {
        List<String> culturas = new ArrayList<>();
        List<String> culturasTodos = CulturasDisponibles();
        for (int i = 0; i < culturasTodos.size(); i++) {
            if (culturasTodos.get(i).contains(substring)) {
                culturas.add(culturasTodos.get(i));
            }
        }
        return culturas;
    }

    public static List<String> ProfesionesDisponibles() throws Exception {
        return filesAvailable(DIRECTORIO_PROFESION);
    }

    public static List<String> CategoriasDisponibles() throws Exception {
        List<String> categoriasTodos = new ArrayList<>();
        if (new File(ROLEMASTER_FOLDER + File.separator + ARCHIVO_CATEGORIAS).exists()) {
            categoriasTodos.add(ROLEMASTER_FOLDER + File.separator + ARCHIVO_CATEGORIAS);
        }
        for (int i = 0; i < modulosRolemaster.size(); i++) {
            if (Folder.ObtainfilesSubdirectory(DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i)).size() > 0) {
                if (new File(DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i) + File.separator + ARCHIVO_CATEGORIAS).exists()) {
                    categoriasTodos.add(DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i) + File.separator + ARCHIVO_CATEGORIAS);
                }
            }
        }
        return categoriasTodos;
    }

    public static List<String> AdiestramientosDisponibles() throws Exception {
        return filesAvailable(DIRECTORIO_ADIESTRAMIENTOS);
    }

    public static List<String> LeerLineasHechizos(String file) {
        List<String> lineasFicheroHechizos = new ArrayList<>();
        List<String> ficherosHechizos = DevolverPathsFichero(DIRECTORIO_HECHIZOS + File.separator + file);
        for (int i = 0; i < ficherosHechizos.size(); i++) {
            try {
                lineasFicheroHechizos.addAll(Folder.readFileLines(ficherosHechizos.get(i), verbose));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return lineasFicheroHechizos;
    }

    public static List<String> LeerLineasTalentos(String file) {
        List<String> lineasFicheroTalentos = new ArrayList<>();
        List<String> ficherosTalentos = DevolverPathsFichero(DIRECTORIO_TALENTOS + File.separator + file);
        for (int i = 0; i < ficherosTalentos.size(); i++) {
            try {
                lineasFicheroTalentos.addAll(Folder.readFileLines(ficherosTalentos.get(i), verbose));
            } catch (IOException ex) {
            }
        }
        return lineasFicheroTalentos;
    }

    public static List<String> LeerLineasCultura(String file) {
        try {
            return Folder.readFileLines(file, verbose);
        } catch (IOException ex) {
        }
        return new ArrayList<>();
    }

    public static List<String> LeerLineasAdiestramiento(String file) {
        try {
            return Folder.readFileLines(file, verbose);
        } catch (IOException ex) {
        }
        return new ArrayList<>();
    }

    public static List<String> LeerLineasRaza(String file) {
        try {
            return Folder.readFileLines(file, verbose);
        } catch (IOException ex) {
        }
        return new ArrayList<>();
    }

    public static List<String> LeerLineasProfesion(String file) {
        try {
            return Folder.readFileLines(file, verbose);
        } catch (IOException ex) {
        }
        return new ArrayList<>();
    }

    public static List<String> LeerLineasArmas(String file) {
        try {
            return Folder.readFileLines(DIRECTORIO_ARMAS + File.separator + file, verbose);
        } catch (IOException ex) {
        }
        return new ArrayList<>();
    }

    public static List<String> LeerLineasCategorias(String file) {
        try {
            return Folder.readFileLines(file, verbose);
        } catch (IOException ex) {
            Logger.getLogger(DirectorioRolemaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public static String LeerFicheroComoTexto(String folder) {
        try {
            return Folder.readFileAsText(folder, verbose);
        } catch (IOException ex) {
        }
        return new String();
    }

    public static void GuardarEnFichero(String texto, String file) {
        Folder.saveTextInFile(texto, file);
    }

    public static List<String> ObtenerModulosRolemaster() {
        List<String> pathModulos;
        try {
            pathModulos = Folder.obtainFolders(DIRECTORIO_MODULOS + File.separator);
            return pathModulos;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static String BuscarDirectorioModulo(String fichero) {
        File file;
        for (int i = 0; i < modulosRolemaster.size(); i++) {
            file = new File(DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i) + File.separator + fichero);
            if (file.exists()) {
                return DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i) + File.separator + fichero;
            }
        }
        return "";
    }

    public static List<String> ObtieneConfiguracionGuardada() {
        String file = ObtenerPathConfiguracion(false);
        try {
            return Folder.readFileLines(file, verbose);
        } catch (IOException ex) {
        }
        return new ArrayList<>();
    }

    public static String ObtenerPathConfigEnHome() {
        String config = System.getProperty("user.home");
        String soName = System.getProperty("os.name");
        if (soName.contains("Linux") || soName.contains("linux")) {
            Folder.generateFolder(config + File.separator + "." + DIRECTORIO_STORE_USER_DATA + File.separator);
            Folder.generateFolder(config + File.separator + "." + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator);
            return config + File.separator + "." + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator;
        } else if (soName.contains("Windows") || soName.contains("windows") || soName.contains("vista") || soName.contains("Vista")) {
            Folder.generateFolder(config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator);
            Folder.generateFolder(config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator);
            return config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator;
        }
        return config + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator + DIRECTORIO_CONFIGURACION + File.separator;

    }

    /**
     * Return the path of the configuration file.
     *
     * @param write True if is for writting a new configuration file. False for
     * reading.
     * @return
     */
    public static String ObtenerPathConfiguracion(boolean write) {
        return ObtenerPathConfigEnHome() + CONFIG;
    }

    public static void saveListInFile(List<String> modulos, String file) {
        Folder.saveListInFile(modulos, file);
    }

    private static List<String> DevolverPathsFichero(String fichero) {
        List<String> ficheros = new ArrayList<>();
        for (int i = 0; i < modulosRolemaster.size(); i++) {
            File file = new File(DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i) + File.separator + fichero);
            if (file.exists()) {
                ficheros.add(file.toString());
            }
        }
        return ficheros;
    }
}

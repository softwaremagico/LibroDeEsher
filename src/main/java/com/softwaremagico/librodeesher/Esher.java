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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 *
 * @author Jorge Hortelano
 */
public class Esher implements Serializable {

    public final double version = 0.991;
    public Personaje pj;
    //Directorios
    private final String ARCHIVO_CATEGORIAS = "categorías.txt";
    private String DIRECTORIO_DEFECTO = System.getProperty("user.home");
    public String BASIC_PATH = "";
    public DirectorioRolemaster directorioRolemaster;
    //Control
    public int bucleHabilidades;
    public boolean GeneraSiguiendoNormas = true;
    public boolean inteligencia = true;
    public int especializacion = 0; // Indica si se quiere un personaje con muchas o pocas habilidades.
    public boolean armasFuegoPermitidas = false;
    public boolean hechizosAdiestramientoOtrosReinosPermitidos = false;
    //Aleatoriedad.
    public Random generator = new Random();
    public boolean aleatorio = false;
    //Caracteristicas base.
    //final int PUNTOS_CARACTERISTICAS = 660;
    public final int[] baseCaracteristicas = {90, 90, 75, 75, 70, 70, 31, 31, 31, 31}; //sobran 22
    //Idiomas en Categoria.
    public List<String> idiomasRolemaster;
    // Modulos configurados para obtener los ficheros adecuados */
    public List<String> modulosRolemaster;
    public List<String> opciones;
    public Talentos talentos;
    public boolean talentosAleatorio = false;
    public boolean habilidadesOrdenadasEnPDF = true;
    public boolean hechizosMalignos = false;
    public boolean poderesChi = false;
    public boolean variosGradosGolpes = false;

    /** Creates a new instance of Personaje */
    public Esher(String directorioFichas) throws Exception {
        BASIC_PATH = directorioFichas;
        if (BASIC_PATH.endsWith("/")) {
            directorioRolemaster = new DirectorioRolemaster(BASIC_PATH, this);
        } else {
            directorioRolemaster = new DirectorioRolemaster(BASIC_PATH + File.separator, this);
        }
        modulosRolemaster = directorioRolemaster.ObtenerModulosRolemasterGuardados();
        opciones = directorioRolemaster.ObtieneConfiguracionGuardada();
        pj = new Personaje(this);
        LeerCategoriasDeArchivo();
        InicializarVariables();
        new LeerRaza(this);
        new LeerCultura(this);
    }

    public void InicializarVariables() {
        aleatorio = false;
        bucleHabilidades = 0;
        talentos = new Talentos(this);
        habilidadesOrdenadasEnPDF = false;
    }

    public void InsertaOGenera(boolean estado) {
        GeneraSiguiendoNormas = estado;
    }

    int IntentosAsignarPD() {
        return bucleHabilidades;
    }

    /***************************************************************
     *
     *                EXPLORAR DIRECTORIOS Y FICHEROS
     *
     ***************************************************************/
    /**
     * Lee el fichero de categorías.
     */
    public void LeerCategoriasDeArchivo() throws Exception {
        String line;
        pj.categorias = new ArrayList<Categoria>();
        List<String> ficherosCategorias = directorioRolemaster.CategoriasDisponibles(modulosRolemaster);
        for (int j = 0; j < ficherosCategorias.size(); j++) {
            List<String> lines = directorioRolemaster.LeerLineasCategorias(ficherosCategorias.get(j) + ARCHIVO_CATEGORIAS);

            for (int i = 2; i < lines.size(); i++) {
                line = (String) lines.get(i);
                String[] descomposed_line = line.split("\t");
                String[] nombreAbrev = descomposed_line[0].split("\\(");
                String nombreCat = nombreAbrev[0];
                try {
                    String abrevCat = nombreAbrev[1].replace(")", "");
                    if (!pj.ExisteCategoria(nombreCat)) {
                        Categoria cat = new Categoria(nombreCat, abrevCat, descomposed_line[1],
                                descomposed_line[2], descomposed_line[3], this);
                        pj.AñadirCategoria(cat);
                    } else {
                        pj.AñadirHabilidades(nombreCat, descomposed_line[3]);
                    }
                } catch (ArrayIndexOutOfBoundsException aiofb) {
                    new MostrarError("Abreviatura de categoria mal definida en " + nombreCat, "Lectura de Categorías");
                }
            }
        }
        OrdenarCategoriasYHabilidades();
        idiomasRolemaster = DevolverListaTodosIdiomas();

    }

    private List<String> DevolverListaTodosIdiomas() {
        List<String> idiomas = new ArrayList<String>();
        Categoria comunicacion = pj.DevolverCategoriaDeNombre("Comunicación");
        try {
            for (int i = comunicacion.listaHabilidades.size() - 1; i >= 0; i--) {
                Habilidad hab = comunicacion.listaHabilidades.get(i);
                if (hab.DevolverNombre().startsWith("Hablar") || hab.DevolverNombre().startsWith("Escribir")) {
                    idiomas.add(hab.DevolverNombre());
                }
            }
        } catch (NullPointerException npe) {
        }
        return idiomas;
    }

    void CombinarIdiomasRazaYTodos() {
        Categoria comunicacion = pj.DevolverCategoriaDeNombre("Comunicación");
        for (int i = 0; i < idiomasRolemaster.size(); i++) {
            comunicacion.AddHabilidad(idiomasRolemaster.get(i));
        }
    }

    public List<String> ProfesionesDisponibles() throws Exception {
        List<String> profesionesDisponibles = new ArrayList<String>();
        List<String> profesionesImplementadas = directorioRolemaster.ProfesionesDisponibles(modulosRolemaster);

        for (int i = 0; i < profesionesImplementadas.size(); i++) {
            if (!pj.profesionesRestringidas.contains(profesionesImplementadas.get(i))) {
                profesionesDisponibles.add(profesionesImplementadas.get(i));
            }
        }
        return profesionesDisponibles;
    }

    public List<String> AdiestramientosDisponibles() throws Exception {
        List<String> adiestramientosDisponibles = new ArrayList<String>();
        List<String> adiestramientosImplementados = directorioRolemaster.AdiestramientosDisponibles(modulosRolemaster);

        for (int i = 0; i < adiestramientosImplementados.size(); i++) {
            if (!pj.profesionesRestringidas.contains(adiestramientosImplementados.get(i))) {
                adiestramientosDisponibles.add(adiestramientosImplementados.get(i));
            }
        }
        return adiestramientosDisponibles;
    }

    public List<String> RazasDisponibles() throws Exception {
        return directorioRolemaster.RazasDisponibles(modulosRolemaster);
    }

    public List<String> CulturasDisponibles() throws Exception {
        List<String> culturasDisponibles = new ArrayList<String>();
        List<String> culturasProgramadas = directorioRolemaster.CulturasDisponibles(modulosRolemaster);
        for (int i = 0; i < pj.culturasPosiblesPorRaza.size(); i++) {
            if (culturasProgramadas.contains(pj.culturasPosiblesPorRaza.get(i))) {
                culturasDisponibles.add(pj.culturasPosiblesPorRaza.get(i));
            }
        }
        return culturasDisponibles;
    }

    public boolean CrearCarpeta(String folder) {
        //Save the objects of Castadiva.
        File dirFileSave = new java.io.File(folder);
        if (!dirFileSave.exists()) {
            try {
                dirFileSave.mkdirs();
            } catch (SecurityException se) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return a string with the path to a folder in the system where the user
     * can save, load, import from ns.
     */
    public String ObtenerDirectorioPorDefecto() {
        return DIRECTORIO_DEFECTO;
    }

    /**
     * Change the default folder where the user can save, load or import from ns.
     */
    public void CambiarDirectorioPorDefecto(String path) {
        DIRECTORIO_DEFECTO = path;
    }

    public String ArchivoDefectoGuardar() {
        return pj.DevolverNombreCompleto() + "_N"
                + pj.nivel + "_" + pj.raza + "_" + pj.profesion;
    }

    public String ArchivoDefectoExportarNivel() {
        return "Subida_Nivel_" + pj.nivel + "_" + pj.DevolverNombreCompleto();
    }

    public String BuscarDirectorioModulo(String fichero) {
        File file;
        for (int i = 0; i < modulosRolemaster.size(); i++) {
            file = new File(modulosRolemaster.get(i) + File.separator + fichero);
            if (file.exists()) {
                return modulosRolemaster.get(i) + File.separator + fichero;
            }
        }
        return "";
    }

    /***************************************************************
     *
     *                    MANIPULAR DATOS
     *
     ***************************************************************/
    /**
     * Baraja una lista de strings aleatoriamente.
     * @param lista
     * @return
     */
    List<String> BarajarLista(List<String> lista) {
        List<String> listaBarajada = new ArrayList<String>();
        int pos = 0;
        while (lista.size() > 0) {
            int elemento = generator.nextInt(lista.size());
            listaBarajada.add(lista.get(elemento));
            lista.remove(elemento);
            pos++;
        }
        return listaBarajada;
    }

    public List<String> OrdenarLista(List<String> lista) {
        String[] vectorElementos = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            vectorElementos[i] = lista.get(i);
        }
        //Ordenamos las Categorías.
        java.util.Arrays.sort(vectorElementos, java.text.Collator.getInstance(Locale.ITALIAN));

        List<String> listaOrdenada = new ArrayList<String>();
        for (int j = 0; j < vectorElementos.length; j++) {
            listaOrdenada.add(vectorElementos[j]);
        }
        return listaOrdenada;
    }

    List<Categoria> BarajarCategorias() {
        List<Categoria> copiaCategorias = new ArrayList<Categoria>();
        int posicion;

        //Normalmente se miran primero los ataques y los hechizos en los PJs.
        String[] vectorPrimerasCategorias = {"Armas·Filo", "Armas·Asta", "Armas·Contundentes",
            "Armas·Artillería", "Armas·Arrojadizas", "Armas·2manos", "Armas·Proyectiles",
            "Maniobras de Combate", "Ataques Especiales",
            "Artes Marciales·Barridos", "Artes Marciales·Golpes", "Artes Marciales·Maniobras de Combate",
            "Listas Básicas de Hechizos", "Listas Abiertas de Hechizos", "Listas Cerradas de Hechizos",
            "Desarrollo Físico", "Desarrollo de Puntos de Poder"
        };

        //Reemplazamos los proyectiles y armas de proyectiles si están las armas de fuego.
        if (armasFuegoPermitidas) {
            vectorPrimerasCategorias[4] = "Armas·Fuego 1mano";
            vectorPrimerasCategorias[6] = "Armas·Fuego 2manos";
        }

        //Copiamos el listado de categorias
        copiaCategorias.addAll(pj.categorias);

        //Mezclamos las categorias barajadas con las primeras. 
        List<Categoria> listaCategoriasBarajada = new ArrayList<Categoria>();
        if (inteligencia) {
            //Primero las preferidas
            for (int i = 0; i < vectorPrimerasCategorias.length; i++) {
                //Evitamos dar preferenci a todas, después de varios niveles, ya que sino se gasta todos los PD en estas siempre.
                if (generator.nextInt(100) > pj.nivel * 15) {
                    posicion = pj.DevolverPosicionCategoriaDeNombre(copiaCategorias, vectorPrimerasCategorias[i]);
                    if (posicion < copiaCategorias.size() && posicion >= 0) {
                        listaCategoriasBarajada.add(copiaCategorias.get(posicion));
                        copiaCategorias.remove(posicion);
                    }
                }
            }
            //Luego se ordena por precios o por habilidades comunes.
            //Algoritmo de la burbuja.
            for (int i = 0; i < copiaCategorias.size(); i++) {
                for (int j = 0; j < copiaCategorias.size() - i - 1; j++) {
                    Categoria cat1 = copiaCategorias.get(j);
                    Categoria cat2 = copiaCategorias.get(j + 1);
                    if (pj.CosteCategoriaYHabilidadPonderadoPorDosRangos(cat1) > pj.CosteCategoriaYHabilidadPonderadoPorDosRangos(cat2)) {
                        copiaCategorias.set(j, cat2);
                        copiaCategorias.set(j + 1, cat1);
                    }
                }
            }
            while (copiaCategorias.size() > 0) {
                listaCategoriasBarajada.add(copiaCategorias.get(0));
                copiaCategorias.remove(0);
            }
        } else {
            //Totalmente aleatorio.
            while (copiaCategorias.size() > 0) {
                int elemento = generator.nextInt(copiaCategorias.size());
                listaCategoriasBarajada.add(copiaCategorias.get(elemento));
                copiaCategorias.remove(elemento);
            }
        }
        return listaCategoriasBarajada;
    }

    private void OrdenarCategoriasYHabilidades() {
        String[] nombresCategorias = new String[pj.categorias.size()];
        for (int i = 0; i < pj.categorias.size(); i++) {
            Categoria cat = pj.categorias.get(i);
            nombresCategorias[i] = cat.DevolverNombre();
        }
        //Ordenamos las Categorías.
        java.util.Arrays.sort(nombresCategorias, java.text.Collator.getInstance(Locale.ITALIAN));

        List<Categoria> CategoriasOrdenadas = new ArrayList<Categoria>();
        for (int j = 0; j < nombresCategorias.length; j++) {
            Categoria catOrd = pj.DevolverCategoriaDeNombre(nombresCategorias[j]);
            catOrd.OrdenarHabilidades();
            CategoriasOrdenadas.add(catOrd);
        }
        pj.categorias = CategoriasOrdenadas;
    }

    /***************************************************************
     *
     *                           OTROS
     *
     ***************************************************************/
    /**
     * Obtiene una lista de orden aleatorio de 1 a cuantos enteros.
     */
    public List<Integer> ObtenerListaAleatoriaDeEnteros(int cuantos) {
        List<Integer> originalList = new ArrayList<Integer>();
        List<Integer> suffledList = new ArrayList<Integer>();
        int aux;

        for (int i = 0; i < cuantos; i++) {
            originalList.add(i);
        }

        do {
            aux = (generator.nextInt(originalList.size()));
            suffledList.add(originalList.get(aux));
            originalList.remove(aux);
        } while (originalList.size() > 0);

        return suffledList;
    }

    private boolean AdiestramientoValido(String nombre) {
        Adiestramiento ad = pj.adiestramiento;
        new LeerAdiestramientos(this, nombre, false, false);
        if (pj.adiestramiento.AdiestramientoValidoPersonaje()) {
            pj.adiestramiento = ad;
            return true;
        }
        pj.adiestramiento = ad;
        return false;
    }

    /***************************************************************
     *
     *                              OTROS
     *
     ***************************************************************/
    public List<String> DevolverAdiestramientosPosibles() {
        List<String> adiestramientosPosibles = new ArrayList<String>();
        List<String> adiestramientosDisponibles = new ArrayList<String>();
        String tmp_nombreAdiestramiento;
        try {
            adiestramientosDisponibles = directorioRolemaster.AdiestramientosDisponibles(modulosRolemaster);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < adiestramientosDisponibles.size(); i++) {
            tmp_nombreAdiestramiento = adiestramientosDisponibles.get(i);
            //Si no ha sido ya elegido anteriormente este adiestramiento y no está prohibido.
            if (!pj.adiestramientosAntiguos.contains(tmp_nombreAdiestramiento)
                    && !pj.costesAdiestramientos.EsAdiestramientoProhibido(tmp_nombreAdiestramiento)
                    && AdiestramientoValido(tmp_nombreAdiestramiento)) {
                adiestramientosPosibles.add(tmp_nombreAdiestramiento);
            }

        }
        return adiestramientosPosibles;
    }

    public List<String> DevolverAdiestramientosDisponibles() {
        List<String> adiestramientosDisponibles = new ArrayList<String>();
        try {
            adiestramientosDisponibles = directorioRolemaster.AdiestramientosDisponibles(modulosRolemaster);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        adiestramientosDisponibles = OrdenarLista(adiestramientosDisponibles);
        return adiestramientosDisponibles;
    }

    /**
     * Tira "cuantos" dados de "dado" caras.
     */
    public int TiradaDados(int cuantos, int dado) {
        int total = 0;
        for (int i = 0; i < cuantos; i++) {
            total += TiradaDado(dado);
        }
        return total;
    }

    /**
     * Realiza una tirada de un dado de "dado" caras.
     */
    public int TiradaDado(int dado) {
        return generator.nextInt(dado) + 1;
    }

    public void Reset() throws Exception {
        pj = new Personaje(this);
        LeerCategoriasDeArchivo();
        InicializarVariables();
        new LeerRaza(this);
        new LeerCultura(this);
    }
}

/***************************************************************
 *
 *                      GUARDAR Y CARGAR
 *
 ***************************************************************/
/**
 * This is the abstract class where all Serializable objects inherit the Load
 * Write functions. Allow to save the simulation in PJ format.
 * @author Jorge Hortelano Otero.
 * @version %I%, %G%
 * @since 1.4
 */
class SerialParent {

    protected String FILENAME;

    public void write(Object objectToSave) throws IOException {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(FILENAME)));
            os.writeObject(objectToSave);
            os.close();
        } catch (FileNotFoundException fnoe) {
        }
    }

    public void save(Object o) throws IOException {
        List<Object> l = new ArrayList<Object>();
        l.add(o);
        write(l);
    }

    public List load() throws IOException, ClassNotFoundException,
            FileNotFoundException {
        List l;
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILENAME));
        l = (List) is.readObject();
        is.close();
        return l;
    }

    public void dump() throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILENAME));
        System.out.println(is.readObject());
        is.close();
    }
}

/**
 * Inherit from SerialParent this class allow to save the Pj structure in a
 * file.
 * @see SerialParent
 * @see SerialAPStream
 * @see SerialScenarioData
 */
class SerialPjStream extends SerialParent {

    public SerialPjStream(String file) {
        FILENAME = file;
    }
}

/**
 * Inherit from SerialParent this class allow to save the ExportarNivel structure in a
 * file.
 * @see SerialParent
 * @see SerialAPStream
 * @see SerialScenarioData
 */
class SerialNivelStream extends SerialParent {

    public SerialNivelStream(String file) {
        FILENAME = file;
    }
}





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

import com.softwaremagico.files.DirectorioRolemaster;
import com.softwaremagico.files.MyFile;
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

    public static final String version = "0.9.9.1";
    //Directorios
    private String DIRECTORIO_DEFECTO = System.getProperty("user.home");
    public String BASIC_PATH = "";
    //Control
    public static int bucleHabilidades;
    public static boolean GeneraSiguiendoNormas = true;
    public static boolean inteligencia = true;
    public static int especializacion = 0; // Indica si se quiere un personaje con muchas o pocas habilidades.
    public static String configArmasFuego = "ArmasFuegoPeritidas";
    public static String confighechizosAdiestramientoOtrosReinosPermitidos = "HechizosAdiestramientosOtrosreinos";
    public static String configHechizosMalignos = "HechizosMalignos";
    public static boolean armasFuegoPermitidas = false;
    public static boolean hechizosAdiestramientoOtrosReinosPermitidos = false;
    //Aleatoriedad.
    public static Random generator = new Random();
    public static boolean aleatorio = false;
    //Caracteristicas base.
    //final int PUNTOS_CARACTERISTICAS = 660;
    public final int[] baseCaracteristicas = {90, 90, 75, 75, 70, 70, 31, 31, 31, 31}; //sobran 22
    //Idiomas en Categoria.
    public static List<String> idiomasRolemaster;
    public static List<String> opciones;
    public static Talentos talentos;
    public static boolean talentosAleatorio = false;
    public static String configTalentosAleatorios = "TalentosAleatorios";
    public static boolean habilidadesOrdenadasEnPDF = true;
    public static String configHabilidadesOrdenadas = "HabilidadesOrdenadas";
    public static boolean hechizosMalignos = false;
    public static boolean poderesChi = false;
    public static String configPoderesChi = "PoderesChi";
    public static boolean variosGradosGolpes = false;
    public static String configVariosGradosGolpes = "VariosGradosGolpes";
    private static Esher esher = new Esher();
    private final static String modulosTag = "DisabledModules: ";

    /**
     * Creates a new instance of Personaje
     */
    private Esher() {
        try {
            AplicarConfiguracion();
            LeerCategoriasDeArchivo();
            InicializarVariables();
            new LeerRaza();
            new LeerCultura();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Esher getInstance() {
        return esher;
    }

    public static void InicializarVariables() {
        aleatorio = false;
        bucleHabilidades = 0;
        talentos = new Talentos();
        habilidadesOrdenadasEnPDF = false;
    }

    public void InsertaOGenera(boolean estado) {
        GeneraSiguiendoNormas = estado;
    }

    public static int IntentosAsignarPD() {
        return bucleHabilidades;
    }

    /**
     * *************************************************************
     *
     * EXPLORAR DIRECTORIOS Y FICHEROS
     *
     **************************************************************
     */
    /**
     * Lee el fichero de categorías.
     */
    public static void LeerCategoriasDeArchivo() throws Exception {
        String line;
        Personaje.getInstance().categorias = new ArrayList<>();
        List<String> ficherosCategorias = DirectorioRolemaster.CategoriasDisponibles();
        for (int j = 0; j < ficherosCategorias.size(); j++) {
            List<String> lines = DirectorioRolemaster.LeerLineasCategorias(ficherosCategorias.get(j));

            for (int i = 2; i < lines.size(); i++) {
                line = (String) lines.get(i);
                String[] descomposed_line = line.split("\t");
                String[] nombreAbrev = descomposed_line[0].split("\\(");
                String nombreCat = nombreAbrev[0];
                try {
                    String abrevCat = nombreAbrev[1].replace(")", "");
                    if (!Personaje.getInstance().ExisteCategoria(nombreCat)) {
                        Categoria cat = Categoria.getCategory(nombreCat, abrevCat, descomposed_line[1],
                                descomposed_line[2], descomposed_line[3]);
                        Personaje.getInstance().AñadirCategoria(cat);
                    } else {
                        Personaje.getInstance().AñadirHabilidades(nombreCat, descomposed_line[3]);
                    }
                } catch (ArrayIndexOutOfBoundsException aiofb) {
                    MostrarError.showErrorMessage("Abreviatura de categoria mal definida en " + nombreCat, "Lectura de Categorías");
                }
            }
        }
        OrdenarCategoriasYHabilidades();
        idiomasRolemaster = DevolverListaTodosIdiomas();

    }

    private static List<String> DevolverListaTodosIdiomas() {
        List<String> idiomas = new ArrayList<>();
        Categoria comunicacion = Personaje.getInstance().DevolverCategoriaDeNombre("Comunicación");
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

    public static void CombinarIdiomasRazaYTodos() {
        Categoria comunicacion = Personaje.getInstance().DevolverCategoriaDeNombre("Comunicación");
        for (int i = 0; i < idiomasRolemaster.size(); i++) {
            comunicacion.AddHabilidad(idiomasRolemaster.get(i));
        }
    }

    public static List<String> ProfesionesDisponibles() throws Exception {
        List<String> profesionesDisponibles = new ArrayList<>();
        List<String> profesionesImplementadas = DirectorioRolemaster.ProfesionesDisponibles();

        for (int i = 0; i < profesionesImplementadas.size(); i++) {
            if (!Personaje.getInstance().profesionesRestringidas.contains(profesionesImplementadas.get(i))) {
                profesionesDisponibles.add(profesionesImplementadas.get(i));
            }
        }
        return profesionesDisponibles;
    }

    public static List<String> AdiestramientosDisponibles() throws Exception {
        List<String> adiestramientosDisponibles = new ArrayList<>();
        List<String> adiestramientosImplementados = DirectorioRolemaster.AdiestramientosDisponibles();

        for (int i = 0; i < adiestramientosImplementados.size(); i++) {
            if (!Personaje.getInstance().profesionesRestringidas.contains(adiestramientosImplementados.get(i))) {
                adiestramientosDisponibles.add(adiestramientosImplementados.get(i));
            }
        }
        return adiestramientosDisponibles;
    }

    public static List<String> RazasDisponibles() throws Exception {
        return DirectorioRolemaster.RazasDisponibles();
    }

    public static List<String> CulturasDisponibles() throws Exception {
        List<String> culturasDisponibles = new ArrayList<String>();
        List<String> culturasProgramadas = DirectorioRolemaster.CulturasDisponibles();
        for (int i = 0; i < Personaje.getInstance().culturasPosiblesPorRaza.size(); i++) {
            if (culturasProgramadas.contains(Personaje.getInstance().culturasPosiblesPorRaza.get(i))) {
                culturasDisponibles.add(Personaje.getInstance().culturasPosiblesPorRaza.get(i));
            }
        }
        return culturasDisponibles;
    }

    public static boolean CrearCarpeta(String folder) {
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
     * Change the default folder where the user can save, load or import from
     * ns.
     */
    public void CambiarDirectorioPorDefecto(String path) {
        DIRECTORIO_DEFECTO = path;
    }

    public static String ArchivoDefectoGuardar() {
        return Personaje.getInstance().DevolverNombreCompleto() + "_N"
                + Personaje.getInstance().nivel + "_" + Personaje.getInstance().raza + "_" + Personaje.getInstance().profesion;
    }

    public static String ArchivoDefectoExportarNivel() {
        return "Subida_Nivel_" + Personaje.getInstance().nivel + "_" + Personaje.getInstance().DevolverNombreCompleto();
    }

    /**
     * *************************************************************
     *
     * MANIPULAR DATOS
     *
     **************************************************************
     */
    /**
     * Baraja una lista de strings aleatoriamente.
     *
     * @param lista
     * @return
     */
    public static List<String> BarajarLista(List<String> lista) {
        List<String> listaBarajada = new ArrayList<>();
        int pos = 0;
        while (lista.size() > 0) {
            int elemento = generator.nextInt(lista.size());
            listaBarajada.add(lista.get(elemento));
            lista.remove(elemento);
            pos++;
        }
        return listaBarajada;
    }

    public static List<String> OrdenarLista(List<String> lista) {
        String[] vectorElementos = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            vectorElementos[i] = lista.get(i);
        }
        //Ordenamos las Categorías.
        java.util.Arrays.sort(vectorElementos, java.text.Collator.getInstance(Locale.ITALIAN));

        List<String> listaOrdenada = new ArrayList<>();
        for (int j = 0; j < vectorElementos.length; j++) {
            listaOrdenada.add(vectorElementos[j]);
        }
        return listaOrdenada;
    }

    public static List<Categoria> BarajarCategorias() {
        List<Categoria> copiaCategorias = new ArrayList<>();
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
        copiaCategorias.addAll(Personaje.getInstance().categorias);

        //Mezclamos las categorias barajadas con las primeras. 
        List<Categoria> listaCategoriasBarajada = new ArrayList<>();
        if (inteligencia) {
            //Primero las preferidas
            for (int i = 0; i < vectorPrimerasCategorias.length; i++) {
                //Evitamos dar preferenci a todas, después de varios niveles, ya que sino se gasta todos los PD en estas siempre.
                if (generator.nextInt(100) > Personaje.getInstance().nivel * 15) {
                    posicion = Personaje.getInstance().DevolverPosicionCategoriaDeNombre(copiaCategorias, vectorPrimerasCategorias[i]);
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
                    if (Personaje.getInstance().CosteCategoriaYHabilidadPonderadoPorDosRangos(cat1) > Personaje.getInstance().CosteCategoriaYHabilidadPonderadoPorDosRangos(cat2)) {
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

    private static void OrdenarCategoriasYHabilidades() {
        String[] nombresCategorias = new String[Personaje.getInstance().categorias.size()];
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            nombresCategorias[i] = cat.DevolverNombre();
        }
        //Ordenamos las Categorías.
        java.util.Arrays.sort(nombresCategorias, java.text.Collator.getInstance(Locale.ITALIAN));

        List<Categoria> CategoriasOrdenadas = new ArrayList<>();
        for (int j = 0; j < nombresCategorias.length; j++) {
            Categoria catOrd = Personaje.getInstance().DevolverCategoriaDeNombre(nombresCategorias[j]);
            catOrd.OrdenarHabilidades();
            CategoriasOrdenadas.add(catOrd);
        }
        Personaje.getInstance().categorias = CategoriasOrdenadas;
    }

    /**
     * *************************************************************
     *
     * OTROS
     *
     **************************************************************
     */
    /**
     * Obtiene una lista de orden aleatorio de 1 a cuantos enteros.
     */
    public static List<Integer> ObtenerListaAleatoriaDeEnteros(int cuantos) {
        List<Integer> originalList = new ArrayList<>();
        List<Integer> suffledList = new ArrayList<>();
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

    private static boolean AdiestramientoValido(String nombre) {
        Adiestramiento ad = Personaje.getInstance().adiestramiento;
        new LeerAdiestramientos(nombre, false, false);
        if (Personaje.getInstance().adiestramiento.AdiestramientoValidoPersonaje()) {
            Personaje.getInstance().adiestramiento = ad;
            return true;
        }
        Personaje.getInstance().adiestramiento = ad;
        return false;
    }

    /**
     * *************************************************************
     *
     * OTROS
     *
     **************************************************************
     */
    public static List<String> DevolverAdiestramientosPosibles() {
        List<String> adiestramientosPosibles = new ArrayList<>();
        List<String> adiestramientosDisponibles = new ArrayList<>();
        String tmp_nombreAdiestramiento;
        try {
            adiestramientosDisponibles = DirectorioRolemaster.AdiestramientosDisponibles();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < adiestramientosDisponibles.size(); i++) {
            tmp_nombreAdiestramiento = adiestramientosDisponibles.get(i);
            //Si no ha sido ya elegido anteriormente este adiestramiento y no está prohibido.
            if (!Personaje.getInstance().adiestramientosAntiguos.contains(tmp_nombreAdiestramiento)
                    && !Personaje.getInstance().costesAdiestramientos.EsAdiestramientoProhibido(tmp_nombreAdiestramiento)
                    && AdiestramientoValido(tmp_nombreAdiestramiento)) {
                adiestramientosPosibles.add(tmp_nombreAdiestramiento);
            }

        }
        return adiestramientosPosibles;
    }

    public static List<String> DevolverAdiestramientosDisponibles() {
        List<String> adiestramientosDisponibles = new ArrayList<String>();
        try {
            adiestramientosDisponibles = DirectorioRolemaster.AdiestramientosDisponibles();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        adiestramientosDisponibles = OrdenarLista(adiestramientosDisponibles);
        return adiestramientosDisponibles;
    }

    /**
     * Tira "cuantos" dados de "dado" caras.
     */
    public static int TiradaDados(int cuantos, int dado) {
        int total = 0;
        for (int i = 0; i < cuantos; i++) {
            total += TiradaDado(dado);
        }
        return total;
    }

    /**
     * Realiza una tirada de un dado de "dado" caras.
     */
    public static int TiradaDado(int dado) {
        return generator.nextInt(dado) + 1;
    }

    public static void Reset() throws Exception {
        LeerCategoriasDeArchivo();
        InicializarVariables();
        new LeerRaza();
        new LeerCultura();
    }

    public String getVersion() {
        return MyFile.readTextFile(this.getClass().getResource("/version.txt").getPath(), false);
        //return MyFile.readTextFile("/version.txt",false);
    }

    private static void AplicarConfiguracion() {
        opciones = DirectorioRolemaster.ObtieneConfiguracionGuardada();
        if (opciones.contains(configArmasFuego)) {
            Esher.armasFuegoPermitidas = true;
            try {
                Personaje.getInstance().ActualizaArmas();
            } catch (Exception ex) {
            }
        } else {
            Esher.armasFuegoPermitidas = false;
        }

        if (opciones.contains(configHechizosMalignos)) {
            Esher.hechizosMalignos = true;
        } else {
            Esher.hechizosMalignos = false;
        }

        if (opciones.contains(confighechizosAdiestramientoOtrosReinosPermitidos)) {
            try {
                Esher.hechizosAdiestramientoOtrosReinosPermitidos = true;
                Esher.LeerCategoriasDeArchivo();
            } catch (Exception ex) {
            }
        } else {
            Esher.hechizosAdiestramientoOtrosReinosPermitidos = false;
        }

        if (opciones.contains(configPoderesChi)) {
            Esher.poderesChi = true;
        } else {
            Esher.poderesChi = false;
        }

        if (opciones.contains(configVariosGradosGolpes)) {
            Esher.variosGradosGolpes = true;
            Personaje.getInstance().CambiarGolpesArtesMarcialesGenericosADiversosGrados();
        } else {
            Esher.variosGradosGolpes = false;
        }

        if (opciones.contains(configTalentosAleatorios)) {
            Esher.talentosAleatorio = true;
        } else {
            Esher.talentosAleatorio = false;
        }

        if (opciones.contains(configHabilidadesOrdenadas)) {
            Esher.habilidadesOrdenadasEnPDF = true;
        } else {
            Esher.habilidadesOrdenadasEnPDF = false;
        }
        
        DirectorioRolemaster.disabledModules = new ArrayList<>();

        for (String line : opciones) {
            try{
            if (line.contains(Esher.modulosTag)) {
                String[] disabledModules = line.split(" ");
                for (int i = 1; i < disabledModules.length; i++) {
                    DirectorioRolemaster.disabledModules.add(disabledModules[i]);
                }
            }
            }catch(NullPointerException npe){}
        }
    }

    public static void GuardarConfiguracion() {
        opciones = new ArrayList<>();
        if (armasFuegoPermitidas) {
            opciones.add(configArmasFuego);
        }

        if (hechizosAdiestramientoOtrosReinosPermitidos) {
            opciones.add(confighechizosAdiestramientoOtrosReinosPermitidos);
        }

        if (hechizosMalignos) {
            opciones.add(configHechizosMalignos);
        }

        if (poderesChi) {
            opciones.add(configPoderesChi);
        }

        if (variosGradosGolpes) {
            opciones.add(configVariosGradosGolpes);
        }

        if (talentosAleatorio) {
            opciones.add(configTalentosAleatorios);
        }

        if (habilidadesOrdenadasEnPDF) {
            opciones.add(configHabilidadesOrdenadas);
        }

        String disabledModules = Esher.modulosTag;
        for (String disabledModule : DirectorioRolemaster.disabledModules) {
            disabledModules += disabledModule + " ";
        }

        Esher.opciones.add(disabledModules);
        DirectorioRolemaster.saveListInFile(Esher.opciones, DirectorioRolemaster.ObtenerPathConfiguracion());
    }
}

/**
 * *************************************************************
 *
 * GUARDAR Y CARGAR
 *
 **************************************************************
 */
/**
 * This is the abstract class where all Serializable objects inherit the Load
 * Write functions. Allow to save the simulation in PJ format.
 *
 * @author Jorge Hortelano Otero.
 * @version %I%, %G%
 * @since 1.4
 */
class SerialParent {

    protected String FILENAME;

    public void write(Object objectToSave) throws IOException {
        try {
            try (ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(
                            new FileOutputStream(FILENAME)))) {
                os.writeObject(objectToSave);
            }
        } catch (FileNotFoundException fnoe) {
        }
    }

    public void save(Object o) throws IOException {
        List<Object> l = new ArrayList<>();
        l.add(o);
        write(l);
    }

    public List load() throws IOException, ClassNotFoundException,
            FileNotFoundException {
        List l;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILENAME))) {
            l = (List) is.readObject();
        }
        return l;
    }

    public void dump() throws IOException, ClassNotFoundException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILENAME))) {
            System.out.println(is.readObject());
        }
    }
}

/**
 * Inherit from SerialParent this class allow to save the Pj structure in a
 * file.
 *
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
 * Inherit from SerialParent this class allow to save the ExportarNivel
 * structure in a file.
 *
 * @see SerialParent
 * @see SerialAPStream
 * @see SerialScenarioData
 */
class SerialNivelStream extends SerialParent {

    public SerialNivelStream(String file) {
        FILENAME = file;
    }
}

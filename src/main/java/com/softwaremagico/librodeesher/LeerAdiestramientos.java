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
import com.softwaremagico.librodeesher.gui.MostrarError;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeerAdiestramientos {

    public String nombreAdiestramiento;
    private int modificadorCoste = 0;             //Si se cumplen requisitos profesionales, sale mas barato el adiestramiento.
    private boolean message = true;
    boolean reino = true;
    private boolean creandoPJ = false;

   public  LeerAdiestramientos(String tmp_nombreAdiestramiento, boolean tmp_message) {
        message = tmp_message;
        try {
            nombreAdiestramiento = tmp_nombreAdiestramiento;
            LeerFicheroAdiestramiento();
        } catch (Exception ex) {
            Logger.getLogger(LeerRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LeerAdiestramientos(String tmp_nombreAdiestramiento, boolean tmp_message, boolean tmp_generandoPJ) {
        creandoPJ = tmp_generandoPJ;
        message = tmp_message;
        try {
            nombreAdiestramiento = tmp_nombreAdiestramiento;
            LeerFicheroAdiestramiento();
        } catch (Exception ex) {
            Logger.getLogger(LeerRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LeerFicheroAdiestramiento() throws Exception {
        LimpiarAntiguoAdiestramiento();
        int lineaLeida = 0;
        Personaje.getInstance().adiestramiento = new Adiestramiento(nombreAdiestramiento);
        String ficheroAdiestramiento = DirectorioRolemaster.BuscarDirectorioModulo(DirectorioRolemaster.DIRECTORIO_ADIESTRAMIENTOS + File.separator + nombreAdiestramiento + ".txt");
        List<String> lines = DirectorioRolemaster.LeerLineasAdiestramiento(ficheroAdiestramiento);
        lineaLeida = LeerTiempoAdiestramiento(lines, lineaLeida);
        lineaLeida = LeerLimitacionesAdiestramiento(lines, lineaLeida);
        lineaLeida = LeerEspecialAdiestramiento(lines, lineaLeida);
        lineaLeida = LeerHabilidadesAdiestramiento(lines, lineaLeida);
        lineaLeida = LeerAumentosCaracteristicasAdiestramiento(lines, lineaLeida);
        lineaLeida = LeerRequisitosProfesionales(lines, lineaLeida);
        lineaLeida = LeerHabilidadesEstiloDeVida(lines, lineaLeida);
        lineaLeida = AsignarHabilidadesComunes(lines, lineaLeida);
        lineaLeida = AsignarHabilidadesProfesionales(lines, lineaLeida);
        lineaLeida = AsignarHabilidadesRestringidas(lines, lineaLeida);
    }

    /**
     * Devuelve el coste de un adiestramiento para el personaje.
     */
    public int DevolverCosteDeAdiestramiento() {
        return Personaje.getInstance().costesAdiestramientos.ObtenerCosteAdiestramiento(nombreAdiestramiento) - modificadorCoste;
    }

    public boolean EsAdiestramientoPreferido() {
        return Personaje.getInstance().costesAdiestramientos.EsAdiestramientoPreferido(nombreAdiestramiento);
    }

    /**
     * Devuelve si se puede escoger un adiestramiento para ese personaje. Un adiestramiento
     * es valido si existe el fichero que la define y además no esta prohibido.
     */
    public boolean EsAdiestramientoValido(String tmp_adiestramiento) {
        List<String> adiestramientosDisponibles;
        try {
            adiestramientosDisponibles = DirectorioRolemaster.AdiestramientosDisponibles();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        if (!adiestramientosDisponibles.contains(tmp_adiestramiento)) {
            return false;
        }
        return true;
    }

    public int LeerTiempoAdiestramiento(List<String> lines, int index) {
        index += 2;
        try {
            Personaje.getInstance().adiestramiento.tiempo = Integer.parseInt(lines.get(index));
        } catch (ArrayIndexOutOfBoundsException aiofb) {
            MostrarError.showErrorMessage("Problema con la linea: \"" + lines.get(index) + "\" del adiestramiento " + Personaje.getInstance().adiestramiento.nombre, "Leer adiestramientos");
        }
        return 3;
    }

    public int LeerLimitacionesAdiestramiento(List<String> lines, int index) {
        index += 3;
        Personaje.getInstance().adiestramiento.limitadoRazas = new ArrayList<>();
        while (!lines.get(index).equals("")) {
            String lineaAdiestramiento = lines.get(index);
            try {
                String[] vectorRazasLimitadas = lineaAdiestramiento.split(", ");
                for (int i = 0; i < vectorRazasLimitadas.length; i++) {
                    if (!vectorRazasLimitadas[i].contains("Ningun")) {
                        Personaje.getInstance().adiestramiento.limitadoRazas.add(vectorRazasLimitadas[i]);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException aiofb) {
                MostrarError.showErrorMessage("Problema con la linea: \"" + lineaAdiestramiento + "\" del adiestramiento " + Personaje.getInstance().adiestramiento.nombre, "Leer adiestramientos");
            }
            index++;
        }
        return index;
    }

    public int LeerEspecialAdiestramiento(List<String> lines, int index) {
        index += 3;
        int bonusAdiestramiento;
        int probabilidadAdiestramiento = 0;
        Personaje.getInstance().listaAficiones = new ArrayList<>();
        while (!lines.get(index).equals("")) {
            if (creandoPJ) {
                String lineaAdiestramiento = lines.get(index);
                try {
                    String[] vectorAdiestramiento = lineaAdiestramiento.split("\t");
                    String tmp_nombreAdiestramiento = vectorAdiestramiento[0];
                    try {
                        probabilidadAdiestramiento = Integer.parseInt(vectorAdiestramiento[1]);
                    } catch (NumberFormatException nfe) {
                        MostrarError.showErrorMessage("Formato de porcentaje de especial \"" + tmp_nombreAdiestramiento + "\" en adiestramiento " + nombreAdiestramiento, "Leer adiestramientos");
                    }
                    if (vectorAdiestramiento.length > 2) {
                        bonusAdiestramiento = Integer.parseInt(vectorAdiestramiento[2]);
                    } else {
                        bonusAdiestramiento = 0;
                    }
                    Personaje.getInstance().adiestramiento.AñadirEspecialAdiestramiento(tmp_nombreAdiestramiento, bonusAdiestramiento, probabilidadAdiestramiento);
                } catch (ArrayIndexOutOfBoundsException aiofb) {
                    MostrarError.showErrorMessage("Problema con la linea: \"" + lineaAdiestramiento + "\" del adiestramiento " + Personaje.getInstance().adiestramiento.nombre, "Leer adiestramientos");
                }
            }
            index++;
        }
        return index;
    }

    public int LeerHabilidadesAdiestramiento(List<String> lines, int index) {
        String tmp_nombre;
        int tmp_rangos;
        boolean otras = true;
        boolean ignorarHabilidades = false;
        index += 3;
        Personaje.getInstance().listaAficiones = new ArrayList<>();
        while (!lines.get(index).equals("")) {
            if (creandoPJ) {
                String lineaAdiestramiento = lines.get(index);
                String[] vectorCategoria = lineaAdiestramiento.split("\t");
                //Es una categoria.
                if (!lineaAdiestramiento.startsWith("  *  ")) {
                    otras = true;
                    ignorarHabilidades = false;
                    //Es una lista propia del adiestramiento:
                    if (lineaAdiestramiento.contains("Listas Hechizos de Adiestramiento")) {
                        String categorias;
                        if (lineaAdiestramiento.contains("Mentalismo")) {
                            if (Personaje.getInstance().reino.contains("Mentalismo")) {
                                categorias = "Listas Hechizos de Adiestramiento";
                            } else {
                                reino = false;
                                if (Esher.hechizosAdiestramientoOtrosReinosPermitidos) {
                                    categorias = "Listas Hechizos de Adiestramientos de Otro Reino";
                                } else {
                                    if (message) {
                                        MostrarError.showErrorMessage("El reino del personaje no coincide con el del adiestramiento, se ignoran las listas de hechizos", "Leer adiestramientos");
                                    }
                                    index++;
                                    ignorarHabilidades = true;
                                    continue;
                                }
                            }
                        } else if (lineaAdiestramiento.contains("Esencia")) {
                            if (Personaje.getInstance().reino.contains("Esencia")) {
                                categorias = "Listas Hechizos de Adiestramiento";
                            } else {
                                reino = false;
                                if (Esher.hechizosAdiestramientoOtrosReinosPermitidos) {
                                    categorias = "Listas Hechizos de Adiestramientos de Otro Reino";
                                } else {
                                    if (message) {
                                        MostrarError.showErrorMessage("El reino del personaje no coincide con el del adiestramiento, se ignoran las listas de hechizos", "Leer adiestramientos");
                                    }
                                    index++;
                                    ignorarHabilidades = true;
                                    continue;
                                }
                            }
                        } else if (lineaAdiestramiento.contains("Canalizaci")) {
                            if (Personaje.getInstance().reino.contains("Canlizaci")) {
                                categorias = "Listas Hechizos de Adiestramiento";
                            } else {
                                reino = false;
                                if (Esher.hechizosAdiestramientoOtrosReinosPermitidos) {
                                    categorias = "Listas Hechizos de Adiestramientos de Otro Reino";
                                } else {
                                    if (message) {
                                        MostrarError.showErrorMessage("El reino del personaje no coincide con el del adiestramiento, se ignoran las listas de hechizos", "Leer adiestramientos");
                                    }
                                    index++;
                                    ignorarHabilidades = true;
                                    continue;
                                }
                            }
                        } else {
                            MostrarError.showErrorMessage("Por favor, indica en el adiestramiento " + nombreAdiestramiento + " el reino en la categoría Listas Hechizos de Adiestramiento (Reino)", "Leer Adiestramiento");
                            index++;
                            continue;
                        }

                        //Añadimos la categoria que toque.
                        try {
                            tmp_rangos = Integer.parseInt(vectorCategoria[1]);
                            int tmp_min = Integer.parseInt(vectorCategoria[2]);
                            int tmp_max = Integer.parseInt(vectorCategoria[3]);
                            int tmp_rngHab = Integer.parseInt(vectorCategoria[4]);
                            Personaje.getInstance().adiestramiento.AñadirCategoriaAdiestramiento(categorias, tmp_rangos,
                                    tmp_min, tmp_max, tmp_rngHab);
                        } catch (NullPointerException npe) {
                            MostrarError.showErrorMessage("Categoria " + vectorCategoria[0] + " mal formada en adiestramiento " + nombreAdiestramiento, "Leer Adiestramiento");
                        }
                    //Son hechizos
                    } else if (lineaAdiestramiento.contains("Listas") && lineaAdiestramiento.contains("Hechizos")) {
                        String categorias = null;
                        if (Personaje.getInstance().EsCombatiente()) {
                            categorias = "Listas Abiertas de Hechizos";
                        } else {
                            categorias = "Listas Básicas de Hechizos";
                        }
                        tmp_rangos = Integer.parseInt(vectorCategoria[1]);
                        int tmp_min = Integer.parseInt(vectorCategoria[2]);
                        int tmp_max = Integer.parseInt(vectorCategoria[3]);
                        int tmp_rngHab = Integer.parseInt(vectorCategoria[4]);
                        Personaje.getInstance().adiestramiento.AñadirListadoCategorias(categorias, tmp_rangos,
                                tmp_min, tmp_max, tmp_rngHab);
                    } else if (lineaAdiestramiento.contains("{")) {
                        //Leer categorias del mismo grupo.
                        String categorias = vectorCategoria[0].replace("}", "").replace("{", "");
                        tmp_rangos = Integer.parseInt(vectorCategoria[1]);
                        int tmp_min = Integer.parseInt(vectorCategoria[2]);
                        int tmp_max = Integer.parseInt(vectorCategoria[3]);
                        int tmp_rngHab = Integer.parseInt(vectorCategoria[4]);
                        Personaje.getInstance().adiestramiento.AñadirListadoCategorias(categorias, tmp_rangos,
                                tmp_min, tmp_max, tmp_rngHab);
                    } else if (Personaje.getInstance().DevolverCategoriaDeNombre(vectorCategoria[0]) != null) {
                        //Categorias normales
                        tmp_nombre = vectorCategoria[0];
                        try {
                            tmp_rangos = Integer.parseInt(vectorCategoria[1]);
                            int tmp_min = Integer.parseInt(vectorCategoria[2]);
                            int tmp_max = Integer.parseInt(vectorCategoria[3]);
                            int tmp_rngHab = Integer.parseInt(vectorCategoria[4]);
                            Personaje.getInstance().adiestramiento.AñadirCategoriaAdiestramiento(tmp_nombre, tmp_rangos,
                                    tmp_min, tmp_max, tmp_rngHab);
                        } catch (NullPointerException npe) {
                            MostrarError.showErrorMessage("Categoria " + vectorCategoria[0] + " mal formada en adiestramiento " + nombreAdiestramiento, "Leer Adiestramiento");
                        }
                    } else {
                        MostrarError.showErrorMessage("Categoria " + vectorCategoria[0] + " no encontrada en adiestramiento " + nombreAdiestramiento, "Leer Adiestramiento");
                    }
                } else {
                    //Es un listado de habilidades
                    if (!ignorarHabilidades) {
                        if (vectorCategoria[0].contains("{")) {
                            otras = false;
                            String habilidades = vectorCategoria[0].replace("}", "").replace("{", "").replace("  *  ", "");
                            Personaje.getInstance().adiestramiento.AñadirListadoHabilidadesUltimaCategoriaAdiestramiento(habilidades);
                        } else {
                            //Es una habilidad.
                            try {
                                tmp_nombre = vectorCategoria[0];
                                tmp_nombre = tmp_nombre.substring(5);
                                tmp_rangos = Integer.parseInt(vectorCategoria[1]);
                                Personaje.getInstance().adiestramiento.AñadirHabilidadUltimaCategoriaAdiestramiento(tmp_nombre, tmp_rangos);
                            } catch (Exception e) {
                                MostrarError.showErrorMessage("Habilidad " + vectorCategoria[0] + " mal formada en adiestramiento " + nombreAdiestramiento, "Leer Adiestramiento");
                            }
                        }
                    }
                }
                //Cuando hemos añadido la ultima habilidad con rangos, añadimos el resto para que el usuario
                //pueda seleccionarlas después.
                String siguienteLineaAdiestramiento = lines.get(index + 1);
                if (!siguienteLineaAdiestramiento.startsWith("  *  ") && otras) {
                    try {
                        Personaje.getInstance().adiestramiento.AñadirOtrasHabilidadesUltimaCategoriaAdiestramiento();
                    } catch (NullPointerException npe) {
                        MostrarError.showErrorMessage("Linea: " + lineaAdiestramiento + " mal formada en adiestramiento " + nombreAdiestramiento, "Leer Adiestramiento");
                    }
                }
            }
            index++;
        }
        return index;
    }

    public int LeerAumentosCaracteristicasAdiestramiento(List<String> lines, int index) {
        index += 3;
        Personaje.getInstance().listaAficiones = new ArrayList<String>();
        while (!lines.get(index).equals("")) {
            if (creandoPJ) {
                String lineaAdiestramiento = lines.get(index);
                try {
                    if (lineaAdiestramiento.contains("{")) {
                        //Lista para elegir caracteristica.
                        lineaAdiestramiento = lineaAdiestramiento.replace("}", "").replace("{", "");
                        Personaje.getInstance().adiestramiento.AñadirListaAumentoCaracteristicaAdiestramiento(lineaAdiestramiento);
                    } else {
                        Personaje.getInstance().adiestramiento.AñadirAumentoCaracteristicaAdiestramiento(lineaAdiestramiento);
                    }
                } catch (ArrayIndexOutOfBoundsException aiofb) {
                    MostrarError.showErrorMessage("Problema con la linea: \"" + lineaAdiestramiento + "\" del adiestramiento " + Personaje.getInstance().adiestramiento.nombre, "Leer Adiestramiento");
                }
            }
            index++;
        }
        return index;
    }

    private int LeerRequisitosProfesionales(List<String> lines, int index) {
        Habilidad hab;
        Caracteristica car;
        boolean seguirComprobando = true;

        index += 3;
        Personaje.getInstance().listaAficiones = new ArrayList<>();
        while (!lines.get(index).equals("")) {
            if (creandoPJ) {
                String lineaAdiestramiento = lines.get(index);
                if (!lineaAdiestramiento.equals("Ninguna") && !lineaAdiestramiento.equals("Ninguno")) {
                    String[] requisitos = lineaAdiestramiento.split(", ");
                    for (int i = 0; i < requisitos.length; i++) {
                        //Religion (10) (-3)
                        String[] requisito = requisitos[i].split("\\(");
                        String nombre = requisito[0];
                        int valor = Integer.parseInt(requisito[1].replace(")", ""));
                        int modificaciones = Integer.parseInt(requisito[2].replace(")", ""));
                        //Si es una habilidad, es tener más de X rangos.
                        if ((hab = Personaje.getInstance().DevolverHabilidadDeNombre(nombre)) != null && seguirComprobando) {
                            if (hab.DevolverRangos() >= valor) {
                                modificadorCoste = modificaciones;
                            }
                        } else if ((car = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(nombre)) != null &&
                                seguirComprobando) {
                            //Si es una caracteristica, es superar un valor.
                            if (car.Total() >= valor) {
                                modificadorCoste = modificaciones;
                            }
                        } else {
                            modificadorCoste = 0;
                            seguirComprobando = false;
                        }
                    }
                }
            }
            index++;
        }
        return index;
    }

    private int LeerHabilidadesEstiloDeVida(List<String> lines, int index) {
        index += 3;
        Personaje.getInstance().listaAficiones = new ArrayList<>();
        while (!lines.get(index).equals("")) {
            if (creandoPJ) {
                String lineaAdiestramiento = lines.get(index);
                if (!lineaAdiestramiento.equals("Ninguna") || !lineaAdiestramiento.equals("Ninguno")) {
                    String[] habilidades = lineaAdiestramiento.split(", ");
                    for (int i = 0; i < habilidades.length; i++) {
                        Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(habilidades[i]);
                        if (hab != null) {
                            hab.estiloDeVida = true;
                        }
                    }
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
        try {
            while (!lines.get(index).equals("")) {
                if (creandoPJ) {
                    String lineaHabilidad = lines.get(index);

                    String[] vectorHabilidades = lineaHabilidad.split(", ");
                    for (int i = 0; i < vectorHabilidades.length; i++) {
                        //Si es un grupo de caracteristicas para elegir, selecciona una.
                        if (vectorHabilidades[i].startsWith("{")) {
                            vectorHabilidades[i] = Personaje.getInstance().SeleccionarNombreHabilidadDeListado(vectorHabilidades[i].replace("}", "").replace("{", ""), tipo, "adiestramiento");
                        }
                        if (!vectorHabilidades[i].equals("Ninguna")) {
                            try {
                                hab = Personaje.getInstance().DevolverHabilidadDeNombre(vectorHabilidades[i]);
                                if (tipo.equals("Común")) {
                                    hab.HacerComunAdiestramiento();
                                }
                                if (tipo.equals("Profesional")) {
                                    hab.HacerProfesionalAdiestramiento();
                                }
                                if (tipo.equals("Restringida")) {
                                    hab.HacerRestringidaAdiestramiento();
                                }
                            } catch (NullPointerException npe) {
                                //Puede ser una habilidad de un categoria.

                                if (!Personaje.getInstance().SeleccionarGrupoHabilidadesEspeciales(tipo, vectorHabilidades[i], "adiestramiento")) {
                                    MostrarError.showErrorMessage("Habilidad desconocida: " + vectorHabilidades[i], "Leer adiestramiento");
                                }
                            }
                        }
                    }
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
            MostrarError.showErrorMessage("Adiestramiento " + nombreAdiestramiento + " tiene un error en la definición de los rangos.", "Leer Adiestramientos");
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

    private void LimpiarAntiguoAdiestramiento() {
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                hab.NoEsProfesionalAdiestramiento();
                hab.NoEsRestringidaAdiestramiento();
                hab.NoEsComunAdiestramiento();
            }
        }
    }
}

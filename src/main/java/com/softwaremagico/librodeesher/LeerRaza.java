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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class LeerRaza {


    LeerRaza() {
        try {
            LeeFicheroRaza();
        } catch (Exception ex) {
            Logger.getLogger(LeerRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LeeFicheroRaza() throws Exception {
        int lineaLeida = 2;

        LimpiarAntiguaRaza();
        String ficheroRaza = DirectorioRolemaster.BuscarDirectorioModulo(DirectorioRolemaster.DIRECTORIO_RAZAS + File.separator + Personaje.getInstance().raza + ".txt");
        if (ficheroRaza.length() > 0) {
            List<String> lines = DirectorioRolemaster.LeerLineasRaza(ficheroRaza);
            lineaLeida = AsignarCaracteristicasRaza(lines, lineaLeida);
            lineaLeida = AsignarTiradasResistencia(lines, lineaLeida);
            lineaLeida = AsignarProgresiones(lines, lineaLeida);
            lineaLeida = AsignarProfesionesRestringidas(lines, lineaLeida);
            lineaLeida = AsignarOtrosDatos(lines, lineaLeida);
            lineaLeida = AsignarListaIdiomasRaza(lines, lineaLeida);
            lineaLeida = AsignarHabilidadesComunesRaza(lines, lineaLeida);
            lineaLeida = AsignarHabilidadesRestringidaRaza(lines, lineaLeida);
            lineaLeida = AsignarCulturas(lines, lineaLeida);
            lineaLeida = AsignarEspeciales(lines, lineaLeida);
            lineaLeida = AsignarPuntosTalentos(lines, lineaLeida);
            lineaLeida = AsignarNombres(lines, lineaLeida);
        }
    }

    /**
     * Asigna los bonus por raza a cada una de las características.
     */
    private int AsignarCaracteristicasRaza(List<String> lines, int index) {
        try {
            while (!lines.get(index).equals("")) {
                String lineaCaracteristica = lines.get(index);
                String[] CaracteristicaValor = lineaCaracteristica.split("\t");
                if (CaracteristicaValor[0].equals("Ap")) {
                    Personaje.getInstance().caracteristicas.bonusAparienciaRaza = Integer.parseInt(CaracteristicaValor[1]);
                } else {
                    try {
                        Caracteristica car = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(CaracteristicaValor[0]);
                        car.CambiarPuntosRaza(Integer.parseInt(CaracteristicaValor[1]));
                    } catch (Exception e) {
                        MostrarError.showErrorMessage("Caracteristicas desconocida: " + CaracteristicaValor[0], "Leer Raza");
                    }
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
            MostrarError.showErrorMessage("Problema al leer las características de la raza.", "Leer Raza");
            System.exit(-1);
        }
        return index;
    }

    private int AsignarTiradasResistencia(List<String> lines, int index) {
        index += 3;
        try {
            while (!lines.get(index).equals("")) {
                String lineaTR = lines.get(index);
                String[] trValor = lineaTR.split("\t");
                try {
                    if (trValor[0].equals("Canalización")) {
                        Personaje.getInstance().SetTrCanalizacion(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrCanalizacion(0);
                }
                try {
                    if (trValor[0].equals("Esencia")) {
                        Personaje.getInstance().SetTrEsencia(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrEsencia(0);
                }
                try {
                    if (trValor[0].equals("Mentalismo")) {
                        Personaje.getInstance().SetTrMentalismo(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrMentalismo(0);
                }
                try {
                    if (trValor[0].equals("Psiónico")) {
                        Personaje.getInstance().SetTrPsionico(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrPsionico(0);
                }
                try {
                    if (trValor[0].contains("Veneno")) {
                        Personaje.getInstance().SetTrVenenos(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrVenenos(0);
                }
                try {
                    if (trValor[0].contains("Enfermedad")) {
                        Personaje.getInstance().SetTrEnfermedades(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrEnfermedades(0);
                }
                try {
                    if (trValor[0].equals("Frío")) {
                        Personaje.getInstance().SetTrFrio(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrFrio(0);
                }
                try {
                    if (trValor[0].equals("Calor")) {
                        Personaje.getInstance().SetTrCalor(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrCalor(0);
                }
                try {
                    if (trValor[0].equals("Miedo")) {
                        Personaje.getInstance().SetTrMiedo(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    Personaje.getInstance().SetTrMiedo(0);
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarProgresiones(List<String> lines, int index) {
        index += 3;
        Personaje.getInstance().progresionesPuntosPoder = new String[5];
        try {
            while (!lines.get(index).equals("")) {
                String lineaProgresiones = lines.get(index);
                String[] progresiones = lineaProgresiones.split("\t");
                if (progresiones[0].equals("Desarrollo Físico")) {
                    Personaje.getInstance().progresionDesarrolloFisico = progresiones[1];
                }

                if (progresiones[0].equals("PP Arcano")) {
                    Personaje.getInstance().progresionesPuntosPoder[Personaje.getInstance().ARCANO] = progresiones[1];
                }
                if (progresiones[0].equals("PP Canalización")) {
                    Personaje.getInstance().progresionesPuntosPoder[Personaje.getInstance().CANALIZACION] = progresiones[1];
                }
                if (progresiones[0].equals("PP Esencia")) {
                    Personaje.getInstance().progresionesPuntosPoder[Personaje.getInstance().ESENCIA] = progresiones[1];
                }
                if (progresiones[0].equals("PP Mentalismo")) {
                    Personaje.getInstance().progresionesPuntosPoder[Personaje.getInstance().MENTALISMO] = progresiones[1];
                }
                if (progresiones[0].equals("PP Psiónico")) {
                    Personaje.getInstance().progresionesPuntosPoder[Personaje.getInstance().PSIONICO] = progresiones[1];
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarProfesionesRestringidas(List<String> lines, int index) {
        index += 3;
        Personaje.getInstance().profesionesRestringidas = new ArrayList<>();
        try {
            while (!lines.get(index).equals("")) {
                String lineaProfesionesRestringidas = lines.get(index);
                String[] pRestringidas = lineaProfesionesRestringidas.split(", ");
                Personaje.getInstance().profesionesRestringidas.addAll(Arrays.asList(pRestringidas));
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarOtrosDatos(List<String> lines, int index) {
        index += 3;
        Personaje.getInstance().partidaAlma = Integer.parseInt(lines.get(index));
        index += 4;
        Personaje.getInstance().tipoRaza = Integer.parseInt(lines.get(index));
        index += 4;
        Personaje.getInstance().tamaño = lines.get(index);
        index += 4;
        try {
            Personaje.getInstance().recuperacion = Float.parseFloat(lines.get(index));
        } catch (NumberFormatException nfe) {
            Personaje.getInstance().recuperacion = Float.parseFloat(lines.get(index).replaceAll(",", "."));
        }
        index += 4;
        Personaje.getInstance().puntosIdiomaRaza = Integer.parseInt(lines.get(index));
        index += 4;
        Personaje.getInstance().historial = Integer.parseInt(lines.get(index));
        index++;
        return index;
    }

    private void BorrarIdiomas() {
        Categoria comunicacion = Personaje.getInstance().DevolverCategoriaDeNombre("Comunicación");
        try {
            for (int i = comunicacion.listaHabilidades.size() - 1; i >= 0; i--) {
                Habilidad hab = comunicacion.listaHabilidades.get(i);
                if (hab.DevolverNombre().startsWith("Hablar") || hab.DevolverNombre().startsWith("Escribir")) {
                    comunicacion.listaHabilidades.remove(i);
                }
            }
        } catch (NullPointerException npe) {
        }

    }

    private int AsignarListaIdiomasRaza(List<String> lines, int index) {
        index += 3;
        BorrarIdiomas();
        Categoria comunicacion = Personaje.getInstance().DevolverCategoriaDeNombre("Comunicación");
        Personaje.getInstance().idiomasRaza = new IdiomasAdolescencia();
        while (!lines.get(index).equals("")) {
            try {
                String lineaIdiomas = lines.get(index);
                String[] datosIdioma = lineaIdiomas.split("\t");
                String[] rangosIdioma = datosIdioma[1].split("/");
                String[] culturaIdioma = datosIdioma[2].split("/");
                IdiomaCultura lengua = new IdiomaCultura(datosIdioma[0],
                        Integer.parseInt(rangosIdioma[0]), Integer.parseInt(rangosIdioma[1]),
                        Integer.parseInt(culturaIdioma[0]), Integer.parseInt(culturaIdioma[1]));
                Personaje.getInstance().idiomasRaza.Add(lengua);
                Habilidad habH = Habilidad.getSkill(comunicacion, "Hablar " + datosIdioma[0],
                        Integer.parseInt(rangosIdioma[0]));
                Habilidad habE = Habilidad.getSkill(comunicacion, "Escribir " + datosIdioma[0],
                        Integer.parseInt(rangosIdioma[1]));
                comunicacion.AddHabilidad(habE);
            } catch (ArrayIndexOutOfBoundsException aiofb) {
                MostrarError.showErrorMessage("Error leyendo la linea de idiomas: " + lines.get(index), "Leer Raza");
            }
            index++;
        }
        return index;
    }

    private int AsignarPuntosTalentos(List<String> lines, int index) {
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaTalentos = lines.get(index);
            try {
                Personaje.getInstance().puntosTalentos = Integer.parseInt(lineaTalentos);
            } catch (NumberFormatException nfe) {
                MostrarError.showErrorMessage("Numero de puntos de talento irreconocible.", "Leer Raza");
                Personaje.getInstance().puntosTalentos = 0;
            }
            index++;
        }
        return index;
    }

    private int AsignarHabilidadesComunesRaza(List<String> lines, int index) {
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaHabilidad = lines.get(index);
            String[] vectorHabilidad = lineaHabilidad.split(", ");
            for (int i = 0; i < vectorHabilidad.length; i++) {
                try {
                    Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(vectorHabilidad[i]);
                    hab.HacerComunRaza();
                } catch (NullPointerException npe) {
                    Categoria cat;
                    if ((cat = Personaje.getInstance().DevolverCategoriaDeNombre(vectorHabilidad[i])) != null) {
                        for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                            Habilidad hab = cat.listaHabilidades.get(j);
                            hab.HacerComunRaza();
                        }
                    } else {
                        if (!vectorHabilidad[i].equals("Ninguna")) //Puede ser una habilidad de un categoria.
                        {
                            if (!Personaje.getInstance().SeleccionarGrupoHabilidadesEspeciales("Común", vectorHabilidad[i], "raza")) {
                                MostrarError.showErrorMessage("Habilidad de raza " + vectorHabilidad[i] + " no reconocida.", "Leer Raza");
                            }
                        }
                    }
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarHabilidadesRestringidaRaza(List<String> lines, int index) {
        index += 3;
        Categoria cat;
        while (!lines.get(index).equals("")) {
            String lineaHabilidad = lines.get(index);
            String[] vectorHabilidad = lineaHabilidad.split(", ");
            for (int i = 0; i < vectorHabilidad.length; i++) {
                if ((cat = Personaje.getInstance().DevolverCategoriaDeNombre(vectorHabilidad[i])) != null) {
                    for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                        Habilidad hab = cat.listaHabilidades.get(j);
                        hab.HacerRestringida();
                    }
                } else {
                    try {
                        if (!vectorHabilidad[i].equals("Ninguna")) {
                            Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(vectorHabilidad[i]);
                            hab.HacerRestringida();
                        }
                    } catch (NullPointerException npe) {
                        MostrarError.showErrorMessage("Habilidad racial restringida " + vectorHabilidad[i] + " no existente.", "Leer Raza");
                    }
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarCulturas(List<String> lines, int index) throws Exception {
        index += 3;
        Personaje.getInstance().culturasPosiblesPorRaza = new ArrayList<>();
        while (!lines.get(index).equals("")) {
            String lineaCultura = lines.get(index);
            if (lineaCultura.contains("Todas")) {
                Personaje.getInstance().culturasPosiblesPorRaza = DirectorioRolemaster.CulturasDisponibles();
                index++;
                break;
            }
            String[] vectorCulturas = lineaCultura.split(", ");
            for (int i = 0; i < vectorCulturas.length; i++) {
                //Grupos de culturas. Si empiezan por "*".
                if (vectorCulturas[i].contains("{")) {
                    String cult = vectorCulturas[i].replace("{", "").replace("}", "");
                    Personaje.getInstance().culturasPosiblesPorRaza.addAll(DirectorioRolemaster.CulturasDisponiblesSubString(cult));
                } else {
                    Personaje.getInstance().culturasPosiblesPorRaza.add(vectorCulturas[i]);
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarEspeciales(List<String> lines, int index) {
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaEspeciales = lines.get(index);
            if (!lineaEspeciales.equals("Ninguno")) {
                if (!Personaje.getInstance().especialesRaza.contains(lineaEspeciales)) {
                    Personaje.getInstance().especialesRaza.add(lineaEspeciales);
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarNombres(List<String> lines, int index) {
        index += 3;
        Personaje.getInstance().nombresMasculinos = new ArrayList<>();
        Personaje.getInstance().nombresFemeninos = new ArrayList<>();
        Personaje.getInstance().apellidos = new ArrayList<>();
        //Nombres Masculinos.
        while (!lines.get(index).equals("")) {
            String lineaNombres = lines.get(index);
            String[] nombres = lineaNombres.split(", ");
            Personaje.getInstance().nombresMasculinos.addAll(Arrays.asList(nombres));
            index++;
        }
        //Nombres Femeninos.
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaNombres = lines.get(index);
            String[] nombres = lineaNombres.split(", ");
            Personaje.getInstance().nombresFemeninos.addAll(Arrays.asList(nombres));
            index++;
        }
        //Apellidos.
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaNombres = lines.get(index);
            String[] nombres = lineaNombres.split(", ");
            Personaje.getInstance().apellidos.addAll(Arrays.asList(nombres));
            index++;
        }

        //Barajamos los nombres aleatoriamente.
        Personaje.getInstance().nombresMasculinos = Esher.BarajarLista(Personaje.getInstance().nombresMasculinos);
        Personaje.getInstance().nombresFemeninos = Esher.BarajarLista(Personaje.getInstance().nombresFemeninos);
        Personaje.getInstance().apellidos = Esher.BarajarLista(Personaje.getInstance().apellidos);

        return index;
    }

    private void LimpiarAntiguaRaza() {
        Personaje.getInstance().especialesRaza = new ArrayList<>();
        Personaje.getInstance().puntosTalentos = 0;
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                hab.NoEsComunRaza();
                hab.NoEsRestringidaRaza();
                hab.LimpiarEspecialesRaza();
            }
        }
    }
}

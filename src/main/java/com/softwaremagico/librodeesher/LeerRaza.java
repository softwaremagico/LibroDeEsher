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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class LeerRaza {

    Esher esher;

    LeerRaza(Esher tmp_esher) {
        try {
            esher = tmp_esher;
            LeeFicheroRaza();
        } catch (Exception ex) {
            Logger.getLogger(LeerRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LeeFicheroRaza() throws Exception {
        int lineaLeida = 2;

        LimpiarAntiguaRaza();
        String ficheroRaza = esher.BuscarDirectorioModulo(esher.directorioRolemaster.DIRECTORIO_RAZAS + File.separator + esher.pj.raza + ".txt");
        if (ficheroRaza.length() > 0) {
            List<String> lines = esher.directorioRolemaster.LeerLineasRaza(ficheroRaza);
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
                    esher.pj.caracteristicas.bonusAparienciaRaza = Integer.parseInt(CaracteristicaValor[1]);
                } else {
                    try {
                        Caracteristica car = esher.pj.caracteristicas.DevolverCaracteristicaDeAbreviatura(CaracteristicaValor[0]);
                        car.CambiarPuntosRaza(Integer.parseInt(CaracteristicaValor[1]));
                    } catch (Exception e) {
                        new MostrarError("Caracteristicas desconocida: " + CaracteristicaValor[0], "Leer Raza");
                    }
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
            new MostrarError("Problema al leer las características de la raza.", "Leer Raza");
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
                        esher.pj.SetTrCanalizacion(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrCanalizacion(0);
                }
                try {
                    if (trValor[0].equals("Esencia")) {
                        esher.pj.SetTrEsencia(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrEsencia(0);
                }
                try {
                    if (trValor[0].equals("Mentalismo")) {
                        esher.pj.SetTrMentalismo(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrMentalismo(0);
                }
                try {
                    if (trValor[0].equals("Psiónico")) {
                        esher.pj.SetTrPsionico(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrPsionico(0);
                }
                try {
                    if (trValor[0].contains("Veneno")) {
                        esher.pj.SetTrVenenos(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrVenenos(0);
                }
                try {
                    if (trValor[0].contains("Enfermedad")) {
                        esher.pj.SetTrEnfermedades(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrEnfermedades(0);
                }
                try {
                    if (trValor[0].equals("Frío")) {
                        esher.pj.SetTrFrio(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrFrio(0);
                }
                try {
                    if (trValor[0].equals("Calor")) {
                        esher.pj.SetTrCalor(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrCalor(0);
                }
                try {
                    if (trValor[0].equals("Miedo")) {
                        esher.pj.SetTrMiedo(Integer.parseInt(trValor[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    esher.pj.SetTrMiedo(0);
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarProgresiones(List<String> lines, int index) {
        index += 3;
        esher.pj.progresionesPuntosPoder = new String[5];
        try {
            while (!lines.get(index).equals("")) {
                String lineaProgresiones = lines.get(index);
                String[] progresiones = lineaProgresiones.split("\t");
                if (progresiones[0].equals("Desarrollo Físico")) {
                    esher.pj.progresionDesarrolloFisico = progresiones[1];
                }

                if (progresiones[0].equals("PP Arcano")) {
                    esher.pj.progresionesPuntosPoder[esher.pj.ARCANO] = progresiones[1];
                }
                if (progresiones[0].equals("PP Canalización")) {
                    esher.pj.progresionesPuntosPoder[esher.pj.CANALIZACION] = progresiones[1];
                }
                if (progresiones[0].equals("PP Esencia")) {
                    esher.pj.progresionesPuntosPoder[esher.pj.ESENCIA] = progresiones[1];
                }
                if (progresiones[0].equals("PP Mentalismo")) {
                    esher.pj.progresionesPuntosPoder[esher.pj.MENTALISMO] = progresiones[1];
                }
                if (progresiones[0].equals("PP Psiónico")) {
                    esher.pj.progresionesPuntosPoder[esher.pj.PSIONICO] = progresiones[1];
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarProfesionesRestringidas(List<String> lines, int index) {
        index += 3;
        esher.pj.profesionesRestringidas = new ArrayList<String>();
        try {
            while (!lines.get(index).equals("")) {
                String lineaProfesionesRestringidas = lines.get(index);
                String[] pRestringidas = lineaProfesionesRestringidas.split(", ");
                for (int i = 0; i < pRestringidas.length; i++) {
                    esher.pj.profesionesRestringidas.add(pRestringidas[i]);
                }
                index++;
            }
        } catch (IndexOutOfBoundsException iob) {
        }
        return index;
    }

    private int AsignarOtrosDatos(List<String> lines, int index) {
        index += 3;
        esher.pj.partidaAlma = Integer.parseInt(lines.get(index));
        index += 4;
        esher.pj.tipoRaza = Integer.parseInt(lines.get(index));
        index += 4;
        esher.pj.tamaño = lines.get(index);
        index += 4;
        try {
            esher.pj.recuperacion = Float.parseFloat(lines.get(index));
        } catch (NumberFormatException nfe) {
            esher.pj.recuperacion = Float.parseFloat(lines.get(index).replaceAll(",", "."));
        }
        index += 4;
        esher.pj.puntosIdiomaRaza = Integer.parseInt(lines.get(index));
        index += 4;
        esher.pj.historial = Integer.parseInt(lines.get(index));
        index++;
        return index;
    }

    private void BorrarIdiomas() {
        Categoria comunicacion = esher.pj.DevolverCategoriaDeNombre("Comunicación");
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
        Categoria comunicacion = esher.pj.DevolverCategoriaDeNombre("Comunicación");
        esher.pj.idiomasRaza = new IdiomasAdolescencia();
        while (!lines.get(index).equals("")) {
            try {
                String lineaIdiomas = lines.get(index);
                String[] datosIdioma = lineaIdiomas.split("\t");
                String[] rangosIdioma = datosIdioma[1].split("/");
                String[] culturaIdioma = datosIdioma[2].split("/");
                IdiomaCultura lengua = new IdiomaCultura(datosIdioma[0],
                        Integer.parseInt(rangosIdioma[0]), Integer.parseInt(rangosIdioma[1]),
                        Integer.parseInt(culturaIdioma[0]), Integer.parseInt(culturaIdioma[1]));
                esher.pj.idiomasRaza.Add(lengua);
                Habilidad habH = new Habilidad(comunicacion, "Hablar " + datosIdioma[0],
                        Integer.parseInt(rangosIdioma[0]));
                comunicacion.AddHabilidad(habH);
                Habilidad habE = new Habilidad(comunicacion, "Escribir " + datosIdioma[0],
                        Integer.parseInt(rangosIdioma[1]));
                comunicacion.AddHabilidad(habE);
            } catch (ArrayIndexOutOfBoundsException aiofb) {
                new MostrarError("Error leyendo la linea de idiomas: " + lines.get(index), "Leer Raza");
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
                esher.pj.puntosTalentos = Integer.parseInt(lineaTalentos);
            } catch (NumberFormatException nfe) {
                new MostrarError("Numero de puntos de talento irreconocible.", "Leer Raza");
                esher.pj.puntosTalentos = 0;
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
                    Habilidad hab = esher.pj.DevolverHabilidadDeNombre(vectorHabilidad[i]);
                    hab.HacerComunRaza();
                } catch (NullPointerException npe) {
                    Categoria cat;
                    if ((cat = esher.pj.DevolverCategoriaDeNombre(vectorHabilidad[i])) != null) {
                        for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                            Habilidad hab = cat.listaHabilidades.get(j);
                            hab.HacerComunRaza();
                        }
                    } else {
                        if (!vectorHabilidad[i].equals("Ninguna")) //Puede ser una habilidad de un categoria.
                        {
                            if (!esher.pj.SeleccionarGrupoHabilidadesEspeciales("Común", vectorHabilidad[i], "raza")) {
                                new MostrarError("Habilidad de raza " + vectorHabilidad[i] + " no reconocida.", "Leer Raza");
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
                if ((cat = esher.pj.DevolverCategoriaDeNombre(vectorHabilidad[i])) != null) {
                    for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                        Habilidad hab = cat.listaHabilidades.get(j);
                        hab.HacerRestringida();
                    }
                } else {
                    try {
                        if (!vectorHabilidad[i].equals("Ninguna")) {
                            Habilidad hab = esher.pj.DevolverHabilidadDeNombre(vectorHabilidad[i]);
                            hab.HacerRestringida();
                        }
                    } catch (NullPointerException npe) {
                        new MostrarError("Habilidad racial restringida " + vectorHabilidad[i] + " no existente.", "Leer Raza");
                    }
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarCulturas(List<String> lines, int index) throws Exception {
        index += 3;
        esher.pj.culturasPosiblesPorRaza = new ArrayList<String>();
        while (!lines.get(index).equals("")) {
            String lineaCultura = lines.get(index);
            if (lineaCultura.contains("Todas")) {
                esher.pj.culturasPosiblesPorRaza = esher.directorioRolemaster.CulturasDisponibles(esher.modulosRolemaster);
                index++;
                break;
            }
            String[] vectorCulturas = lineaCultura.split(", ");
            for (int i = 0; i < vectorCulturas.length; i++) {
                //Grupos de culturas. Si empiezan por "*".
                if (vectorCulturas[i].contains("{")) {
                    String cult = vectorCulturas[i].replace("{", "").replace("}", "");
                    esher.pj.culturasPosiblesPorRaza.addAll(esher.directorioRolemaster.CulturasDisponiblesSubString(esher.modulosRolemaster, cult));
                } else {
                    esher.pj.culturasPosiblesPorRaza.add(vectorCulturas[i]);
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
                if (!esher.pj.especialesRaza.contains(lineaEspeciales)) {
                    esher.pj.especialesRaza.add(lineaEspeciales);
                }
            }
            index++;
        }
        return index;
    }

    private int AsignarNombres(List<String> lines, int index) {
        index += 3;
        esher.pj.nombresMasculinos = new ArrayList<String>();
        esher.pj.nombresFemeninos = new ArrayList<String>();
        esher.pj.apellidos = new ArrayList<String>();
        //Nombres Masculinos.
        while (!lines.get(index).equals("")) {
            String lineaNombres = lines.get(index);
            String[] nombres = lineaNombres.split(", ");
            for (int i = 0; i < nombres.length; i++) {
                esher.pj.nombresMasculinos.add(nombres[i]);
            }
            index++;
        }
        //Nombres Femeninos.
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaNombres = lines.get(index);
            String[] nombres = lineaNombres.split(", ");
            for (int i = 0; i < nombres.length; i++) {
                esher.pj.nombresFemeninos.add(nombres[i]);

            }
            index++;
        }
        //Apellidos.
        index += 3;
        while (!lines.get(index).equals("")) {
            String lineaNombres = lines.get(index);
            String[] nombres = lineaNombres.split(", ");
            for (int i = 0; i < nombres.length; i++) {
                esher.pj.apellidos.add(nombres[i]);
            }
            index++;
        }

        //Barajamos los nombres aleatoriamente.
        esher.pj.nombresMasculinos = esher.BarajarLista(esher.pj.nombresMasculinos);
        esher.pj.nombresFemeninos = esher.BarajarLista(esher.pj.nombresFemeninos);
        esher.pj.apellidos = esher.BarajarLista(esher.pj.apellidos);

        return index;
    }

    private void LimpiarAntiguaRaza() {
        esher.pj.especialesRaza = new ArrayList<String>();
        esher.pj.puntosTalentos = 0;
        for (int i = 0; i < esher.pj.categorias.size(); i++) {
            Categoria cat = esher.pj.categorias.get(i);
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                hab.NoEsComunRaza();
                hab.NoEsRestringidaRaza();
                hab.LimpiarEspecialesRaza();
            }
        }
    }
}

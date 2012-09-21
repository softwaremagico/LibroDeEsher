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
Created on july of 2009.
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
import java.util.List;

/**
 *
 * @author jorge
 */
public class ExportarNivel implements Serializable {

    int nivel;
    String nombre;
    List<CaracteristicaSubida> caracteristicas;
    List<CategoriaSubida> categorias;
    List<HabilidadSubida> habilidades;
    List<String> adiestramientos;
    List<String> equipo;

    public ExportarNivel() {
        nivel = Personaje.getInstance().nivel;
        nombre = Personaje.getInstance().DevolverNombreCompleto();
        ExportarCaracteristicas();
        ExportarCategoria();
        adiestramientos = Personaje.getInstance().adiestramientosAntiguos;
        equipo = Personaje.getInstance().equipo;

    }

    public void ImportarNivel() {
        //El personaje ya debe estar iniciado al nivel adecuado. 
        if (Personaje.getInstance().DevolverNombreCompleto().equals(nombre)) {
            if (Personaje.getInstance().nivel == nivel) {
                if (Personaje.getInstance().lock == false) {
                    MostrarError.showErrorMessage("El personaje ha sido alterado. No se procederá a la subida de nivel.", "Importar Personaje");
                } else {
                    if (Personaje.getInstance().vecesCargadoPersonaje > 1) {
                    //MostrarError.showErrorMessage("Atención: El jugador ha cargado " + Personaje.getInstance().vecesCargadoPersonaje + " veces el personaje", "Importar Personaje");
                    }
                    ImportarCaracteristicas();
                    ImportarCategoria();
                    ImportarHabilidad();
                    ImportarAdiestramiento();
                }
            }
        } else {
            MostrarError.showErrorMessage("El nivel importado no pertenece a este personaje.", "Importar Personaje");
        }
    }

    private void ExportarCaracteristicas() {
        caracteristicas = new ArrayList<CaracteristicaSubida>();
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            CaracteristicaSubida cs = new CaracteristicaSubida(Personaje.getInstance().caracteristicas.Get(i).DevolverAbreviatura(), Personaje.getInstance().caracteristicas.Get(i).ObtenerPuntosTemporal(), Personaje.getInstance().caracteristicas.Get(i).ObtenerPuntosNextTemporal());
            caracteristicas.add(cs);
        }
    }

    private void ExportarCategoria() {
        categorias = new ArrayList<CategoriaSubida>();
        habilidades = new ArrayList<HabilidadSubida>();
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            if ((Personaje.getInstance().categorias.get(i).nuevosRangos > 0) || (Personaje.getInstance().categorias.get(i).rangosAdiestramiento > 0)) {
                CategoriaSubida cs = new CategoriaSubida(Personaje.getInstance().categorias.get(i).DevolverNombre(), Personaje.getInstance().categorias.get(i).nuevosRangos, Personaje.getInstance().categorias.get(i).rangosAdiestramiento);
                categorias.add(cs);
            }
            ExportarHabilidad(i);
        }
    }

    private void ExportarHabilidad(int categoria) {
        for (int j = 0; j < Personaje.getInstance().categorias.get(categoria).listaHabilidades.size(); j++) {
            if ((Personaje.getInstance().categorias.get(categoria).listaHabilidades.get(j).nuevosRangos > 0) || (Personaje.getInstance().categorias.get(categoria).listaHabilidades.get(j).rangosAdiestramiento > 0)) {
                HabilidadSubida hs = new HabilidadSubida(Personaje.getInstance().categorias.get(categoria).listaHabilidades.get(j).DevolverNombre(), Personaje.getInstance().categorias.get(categoria).DevolverNombre(), Personaje.getInstance().categorias.get(categoria).listaHabilidades.get(j).nuevosRangos, Personaje.getInstance().categorias.get(categoria).listaHabilidades.get(j).rangosAdiestramiento);
                habilidades.add(hs);
            }
        }
    }

    private void ImportarCaracteristicas() {
        for (int i = 0; i < caracteristicas.size(); i++) {
            Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(caracteristicas.get(i).abreviatura).CambiarPuntosTemporal(caracteristicas.get(i).valor);
            Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(caracteristicas.get(i).abreviatura).CambiarPuntosNextTemporal(caracteristicas.get(i).nextValor);
        }
    }

    private void ImportarCategoria() {
        for (int i = 0; i < categorias.size(); i++) {
            Personaje.getInstance().DevolverCategoriaDeNombre(categorias.get(i).nombre).nuevosRangos = categorias.get(i).rangos;
            Personaje.getInstance().DevolverCategoriaDeNombre(categorias.get(i).nombre).rangosAdiestramiento = categorias.get(i).rangosAdiestramiento;
        }
    }

    private void ImportarHabilidad() {
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidad hab = Personaje.getInstance().DevolverHabilidadDeNombre(habilidades.get(i).nombre);
            if (hab == null) {
                //Se han generado nuevas habilidades debido probablemente a un adiestramiento adquirido en ese nivel.
                Personaje.getInstance().DevolverCategoriaDeNombre(habilidades.get(i).categoria).AddHabilidad(habilidades.get(i).nombre);
                hab = Personaje.getInstance().DevolverHabilidadDeNombre(habilidades.get(i).nombre);
            }
            hab.nuevosRangos = habilidades.get(i).rangos;
            hab.rangosAdiestramiento = habilidades.get(i).rangosAdiestramiento;
        }
    }

    private void ImportarAdiestramiento() {
        //Finalmente
        Personaje.getInstance().adiestramientosAntiguos = adiestramientos;
        Personaje.getInstance().equipo = equipo;
    }

    private class CaracteristicaSubida implements Serializable {

        String abreviatura;
        int valor;
        int nextValor;

        CaracteristicaSubida(String tmp_abrev, int tmp_valor, int tmp_nextValor) {
            abreviatura = tmp_abrev;
            valor = tmp_valor;
            nextValor = tmp_nextValor;
        }
    }

    private class CategoriaSubida implements Serializable {

        String nombre;
        int rangos;
        int rangosAdiestramiento;

        CategoriaSubida(String tmp_nombre, int tmp_rangos, int rangAd) {
            nombre = tmp_nombre;
            rangos = tmp_rangos;
            rangosAdiestramiento = rangAd;
        }
    }

    private class HabilidadSubida implements Serializable {

        String nombre;
        String categoria;
        int rangos;
        int rangosAdiestramiento;

        HabilidadSubida(String tmp_nombre, String cat, int tmp_rangos, int rangAd) {
            nombre = tmp_nombre;
            rangos = tmp_rangos;
            rangosAdiestramiento = rangAd;
            categoria = cat;
        }
    }
}

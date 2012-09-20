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

    private transient Personaje pj;
    int nivel;
    String nombre;
    List<CaracteristicaSubida> caracteristicas;
    List<CategoriaSubida> categorias;
    List<HabilidadSubida> habilidades;
    List<String> adiestramientos;
    List<String> equipo;

    public ExportarNivel(Personaje tmp_pj) {
        pj = tmp_pj;
        nivel = pj.nivel;
        nombre = pj.DevolverNombreCompleto();
        ExportarCaracteristicas();
        ExportarCategoria();
        adiestramientos = pj.adiestramientosAntiguos;
        equipo = pj.equipo;

    }

    public void ImportarNivel(Personaje tmp_pj) {
        pj = tmp_pj;
        //El personaje ya debe estar iniciado al nivel adecuado. 
        if (pj.DevolverNombreCompleto().equals(nombre)) {
            if (pj.nivel == nivel) {
                if (pj.lock == false) {
                    new MostrarError("El personaje ha sido alterado. No se procederá a la subida de nivel.", "Importar Personaje");
                } else {
                    if (pj.vecesCargadoPersonaje > 1) {
                    //new MostrarError("Atención: El jugador ha cargado " + pj.vecesCargadoPersonaje + " veces el personaje", "Importar Personaje");
                    }
                    ImportarCaracteristicas();
                    ImportarCategoria();
                    ImportarHabilidad();
                    ImportarAdiestramiento();
                }
            }
        } else {
            new MostrarError("El nivel importado no pertenece a este personaje.", "Importar Personaje");
        }
    }

    private void ExportarCaracteristicas() {
        caracteristicas = new ArrayList<CaracteristicaSubida>();
        for (int i = 0; i < pj.caracteristicas.Size(); i++) {
            CaracteristicaSubida cs = new CaracteristicaSubida(pj.caracteristicas.Get(i).DevolverAbreviatura(), pj.caracteristicas.Get(i).ObtenerPuntosTemporal(), pj.caracteristicas.Get(i).ObtenerPuntosNextTemporal());
            caracteristicas.add(cs);
        }
    }

    private void ExportarCategoria() {
        categorias = new ArrayList<CategoriaSubida>();
        habilidades = new ArrayList<HabilidadSubida>();
        for (int i = 0; i < pj.categorias.size(); i++) {
            if ((pj.categorias.get(i).nuevosRangos > 0) || (pj.categorias.get(i).rangosAdiestramiento > 0)) {
                CategoriaSubida cs = new CategoriaSubida(pj.categorias.get(i).DevolverNombre(), pj.categorias.get(i).nuevosRangos, pj.categorias.get(i).rangosAdiestramiento);
                categorias.add(cs);
            }
            ExportarHabilidad(i);
        }
    }

    private void ExportarHabilidad(int categoria) {
        for (int j = 0; j < pj.categorias.get(categoria).listaHabilidades.size(); j++) {
            if ((pj.categorias.get(categoria).listaHabilidades.get(j).nuevosRangos > 0) || (pj.categorias.get(categoria).listaHabilidades.get(j).rangosAdiestramiento > 0)) {
                HabilidadSubida hs = new HabilidadSubida(pj.categorias.get(categoria).listaHabilidades.get(j).DevolverNombre(), pj.categorias.get(categoria).DevolverNombre(), pj.categorias.get(categoria).listaHabilidades.get(j).nuevosRangos, pj.categorias.get(categoria).listaHabilidades.get(j).rangosAdiestramiento);
                habilidades.add(hs);
            }
        }
    }

    private void ImportarCaracteristicas() {
        for (int i = 0; i < caracteristicas.size(); i++) {
            pj.caracteristicas.DevolverCaracteristicaDeAbreviatura(caracteristicas.get(i).abreviatura).CambiarPuntosTemporal(caracteristicas.get(i).valor);
            pj.caracteristicas.DevolverCaracteristicaDeAbreviatura(caracteristicas.get(i).abreviatura).CambiarPuntosNextTemporal(caracteristicas.get(i).nextValor);
        }
    }

    private void ImportarCategoria() {
        for (int i = 0; i < categorias.size(); i++) {
            pj.DevolverCategoriaDeNombre(categorias.get(i).nombre).nuevosRangos = categorias.get(i).rangos;
            pj.DevolverCategoriaDeNombre(categorias.get(i).nombre).rangosAdiestramiento = categorias.get(i).rangosAdiestramiento;
        }
    }

    private void ImportarHabilidad() {
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidad hab = pj.DevolverHabilidadDeNombre(habilidades.get(i).nombre);
            if (hab == null) {
                //Se han generado nuevas habilidades debido probablemente a un adiestramiento adquirido en ese nivel.
                pj.DevolverCategoriaDeNombre(habilidades.get(i).categoria).AddHabilidad(habilidades.get(i).nombre);
                hab = pj.DevolverHabilidadDeNombre(habilidades.get(i).nombre);
            }
            hab.nuevosRangos = habilidades.get(i).rangos;
            hab.rangosAdiestramiento = habilidades.get(i).rangosAdiestramiento;
        }
    }

    private void ImportarAdiestramiento() {
        //Finalmente
        pj.adiestramientosAntiguos = adiestramientos;
        pj.equipo = equipo;
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

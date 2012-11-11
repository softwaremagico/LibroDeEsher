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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Hortelano
 */
public class Caracteristicas implements Serializable {

    List<Caracteristica> caracteristicas = new ArrayList<>();
    int bonusAparienciaRaza;
    int apariencia;
    public Integer totalCaracteristicas = 660;

    /**
     * Creates a new instance of Caracteristicas
     */
    public Caracteristicas() {
        CrearCaracteristicas();
    }

    /**
     * Genera las 10 características del PJ y sus abreviaturas.
     */
    private void CrearCaracteristicas() {
        caracteristicas.add(new Caracteristica("Ag"));
        caracteristicas.add(new Caracteristica("Co"));
        caracteristicas.add(new Caracteristica("Me"));
        caracteristicas.add(new Caracteristica("Ra"));
        caracteristicas.add(new Caracteristica("Ad"));
        caracteristicas.add(new Caracteristica("Em"));
        caracteristicas.add(new Caracteristica("In"));
        caracteristicas.add(new Caracteristica("Pr"));
        caracteristicas.add(new Caracteristica("Rp"));
        caracteristicas.add(new Caracteristica("Fu"));
    }

    public Caracteristica DevolverCaracteristicaDeAbreviatura(String abrev) {
        for (int i = 0; i < caracteristicas.size(); i++) {
            Caracteristica car = caracteristicas.get(i);
            if (car.DevolverAbreviatura().equals(abrev)) {
                return car;
            }
        }
        return null;
    }

    /**
     * Obtener lista aleatoria de caracteristicas.
     */
    public List<Caracteristica> ObtenerListaAleatoriaDeCaracteristicas() {
        List<Integer> listaEnteros = Esher.ObtenerListaAleatoriaDeEnteros(10);
        List<Caracteristica> listaAleatoriaCaracteristicas = new ArrayList<>();
        for (int i = 0; i < listaEnteros.size(); i++) {
            listaAleatoriaCaracteristicas.add(caracteristicas.get(listaEnteros.get(i)));
        }
        return listaAleatoriaCaracteristicas;
    }

    public void ObtenerPotenciales() {
        if (Personaje.getInstance().nivel == 1) {
            for (int i = 0; i < caracteristicas.size(); i++) {
                Caracteristica car = caracteristicas.get(i);
                car.GuardarPontencial();
            }
        }
    }

    public void ObtenerApariencia() {
        Caracteristica pres = DevolverCaracteristicaDeAbreviatura("Pr");
        apariencia = pres.ObtenerPuntosPotencial() - 25 + Esher.TiradaDados(5, 10);
    }

    /**
     * Devuelve el valor básico de apariencia.
     *
     * @return
     */
    public int DevolverApariencia() {
        return apariencia;
    }

    /**
     * Devuelve el valor de apariencia con los modificadores de raza.
     *
     * @return
     */
    public int DevolverTotalApariencia() {
        return apariencia + bonusAparienciaRaza + Personaje.getInstance().DevolverBonusTalentoApariencia();
    }

    public void InsertarApariencia(int value) {
        Caracteristica pres = DevolverCaracteristicaDeAbreviatura("Pr");
        if (value > pres.ObtenerPuntosPotencial() + 25) {
            value = pres.ObtenerPuntosPotencial() + 25;
        }
        if (value < pres.ObtenerPuntosPotencial() - 25) {
            value = pres.ObtenerPuntosPotencial() - 25;
        }
        apariencia = value;
    }

    public Caracteristica Get(int index) {
        return caracteristicas.get(index);
    }

    public int Size() {
        return caracteristicas.size();
    }

    public int CalcularPuntosDesarrollo() {
        int sumaCaracteristicas = 0;
        for (int i = 0; i < 5; i++) {
            sumaCaracteristicas += Get(i).ObtenerPuntosTemporal();
        }
        return sumaCaracteristicas / 5;
    }

    public void SubirNivelCaracteristicas() {
        for (int i = 0; i < caracteristicas.size(); i++) {
            Caracteristica car = caracteristicas.get(i);
            car.SubirNivelCaracteristica();
        }
    }

    public void CalcularProximoAumento() {
        for (int i = 0; i < caracteristicas.size(); i++) {
            Caracteristica car = caracteristicas.get(i);
            car.CalcularProximoAumentoCaracteristica();
        }
    }

    public void ActualizarCaracteristica(Caracteristica car) {
        for (int i = 0; i < caracteristicas.size(); i++) {
            Caracteristica carAnt = caracteristicas.get(i);
            if (carAnt.DevolverAbreviatura().equals(car.DevolverAbreviatura())) {
                caracteristicas.add(i, car);
                caracteristicas.remove(i + 1);
                break;
            }
        }
    }
}

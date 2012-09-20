/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author jorge
 */
public class IdiomaCultura implements Serializable {
    public String nombre;
    public int hablado;
    public int escrito;
    public int rangosNuevosHablado;
    public int rangosNuevosEscritos;
    public int maxHabladoCultura;
    public int maxEscritoCultura;

    public IdiomaCultura(String nom, int h, int e, int hc, int ec){
        nombre = nom;
        hablado = h;
        escrito = e;
        maxHabladoCultura = hc;
        maxEscritoCultura = ec; 
        rangosNuevosHablado = 0;
        rangosNuevosEscritos = 0;
    }
     
    public int DevolverValorHablado(){
         return hablado + rangosNuevosHablado;
     }
     
    public int DevolverValorEscrito(){
         return escrito + rangosNuevosEscritos;
     }
}

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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
/**
 * Almacena los idiomas posibles que se pueden coger con la cultaura.
 */
public class IdiomasAdolescencia implements Serializable {
    private List<IdiomaCultura> idiomasAdolescencia;
    public IdiomasAdolescencia(){
        idiomasAdolescencia = new ArrayList<IdiomaCultura>();
    }
    
    public void Add(IdiomaCultura nuevoIdioma){
        if(!ExisteIdioma(nuevoIdioma)){
            idiomasAdolescencia.add(nuevoIdioma);
        }else{
            IdiomaCultura idi = ObtenerIdioma(nuevoIdioma.nombre);
            idi.maxEscritoCultura = Math.max(nuevoIdioma.maxEscritoCultura, idi.maxEscritoCultura);
            idi.maxHabladoCultura = Math.max(nuevoIdioma.maxHabladoCultura, idi.maxHabladoCultura);
        }
    }
    
    public IdiomaCultura ObtenerIdioma(String nombreIdioma){
        for(int i=0; i<idiomasAdolescencia.size(); i++){
            IdiomaCultura idi = idiomasAdolescencia.get(i);
            if(idi.nombre.equals(nombreIdioma)){
                return idi;
            }
        }
        return null;
    }
    
    public IdiomaCultura Get(int index){
        return idiomasAdolescencia.get(index);
    }
    
    public boolean ExisteIdioma(IdiomaCultura nuevoIdioma){
        for(int i=0; i<idiomasAdolescencia.size(); i++){
            IdiomaCultura idi = idiomasAdolescencia.get(i);
            if(idi.nombre.equals(nuevoIdioma.nombre)){
                return true;
            }
        }
        return false;
    }
    
    public int Size(){
        return idiomasAdolescencia.size();
    }
}

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
public class ObjetoMagico implements Serializable {

   public  String nombre;
    private List<BonusCategoria> modificacionCategoria = new ArrayList<BonusCategoria>();
    private List<BonusHabilidad> modificacionHabilidad = new ArrayList<BonusHabilidad>();
    boolean historial = false;

    public ObjetoMagico(String obj) {
        nombre = obj;
    }
    
    public ObjetoMagico(String obj, boolean tmp_historial) {
        nombre = obj;
        historial = tmp_historial;
    }

   public  void AñadirBonusCategoria(Categoria cat, Integer bonus) {
        int categoria;
        //Si ya existe se incrementa.
        if ((categoria = DevolverCategoria(cat)) >= 0) {
            modificacionCategoria.get(categoria).bonus = bonus;
        } else {
            //Sino se añade.
            AñadirNuevaCategoria(cat, bonus);
        }
    }

   public  void AñadirNuevaCategoria(Categoria cat, Integer bonus) {
        modificacionCategoria.add(new BonusCategoria(bonus, cat));
    }

   public  void AñadirBonusHabilidad(Habilidad hab, Integer bonus) {
        int habilidad;
        //Si ya existe se incrementa.
        if ((habilidad = DevolverHabilidad(hab)) >= 0) {
            modificacionHabilidad.get(habilidad).bonus = bonus;
        } else {
            //Sino se añade.
            AñadirNuevaHabilidad(hab, bonus);
        }
    }

    public void AñadirNuevaHabilidad(Habilidad hab, Integer bonus) {
        modificacionHabilidad.add(new BonusHabilidad(bonus, hab));
    }

    private int DevolverCategoria(Categoria cat) {
        for (int i = 0; i < modificacionCategoria.size(); i++) {
            if (cat.DevolverNombre().equals(modificacionCategoria.get(i).categoria.DevolverNombre())) {
                return i;
            }
        }
        return -1;
    }

    private int DevolverHabilidad(Habilidad hab) {
        for (int i = 0; i < modificacionHabilidad.size(); i++) {
            if (hab.DevolverNombre().equals(modificacionHabilidad.get(i).habilidad.DevolverNombre())) {
                return i;
            }
        }
        return -1;
    }

    public int DevolverBonusCategoria(Categoria cat) {
        int total = 0;
        for (int i = 0; i < modificacionCategoria.size(); i++) {
            if (cat.DevolverNombre().equals(modificacionCategoria.get(i).categoria.DevolverNombre())) {
                total += modificacionCategoria.get(i).bonus;
            }
        }
        return total;
    }

    public int DevolverBonusHabilidad(Habilidad hab) {
        int total = 0;
        for (int i = 0; i < modificacionHabilidad.size(); i++) {
            if (hab.DevolverNombre().equals(modificacionHabilidad.get(i).habilidad.DevolverNombre())) {
                total += modificacionHabilidad.get(i).bonus;
            }
        }
        return total;
    }

    public String DevolverPropiedades() {
        String texto = "";
        boolean existeCat = false;
        int insertado = 0;
        for (int i = 0; i < modificacionCategoria.size(); i++) {
            BonusCategoria bc = modificacionCategoria.get(i);
            if (bc.bonus != 0) {
                if (insertado > 0) {
                    texto += ", ";
                }
                texto += bc.categoria.DevolverNombre() + " (" + bc.bonus + ")";
                existeCat = true;
                insertado ++;
            }
        }
        insertado = 0;
        for (int j = 0; j < modificacionHabilidad.size(); j++) {
            BonusHabilidad bh = modificacionHabilidad.get(j);
            if (bh.bonus != 0) {
                if (insertado > 0 || existeCat) {
                    texto += ", ";
                }
                texto += bh.habilidad.DevolverNombre() + " (" + bh.bonus + ")";
                insertado ++;
            }
        }
        return texto;
    }

    public class BonusCategoria implements Serializable {

        public Integer bonus;
        public Categoria categoria;

        public BonusCategoria(Integer bon, Categoria cat) {
            bonus = bon;
            categoria = cat;
        }
        }

    public class BonusHabilidad implements Serializable {

        public Integer bonus;
        public Habilidad habilidad;

        public BonusHabilidad(Integer bon, Habilidad hab) {
            bonus = bon;
            habilidad = hab;
        }
        }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwaremagico.librodeesher.pj.characteristic;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author jorge
 */
public class Characteristics {
	private final static String allowedCharacteristics="AgCoMeRaAdEmInPrRpFu";
    private final static int TOTAL_CHARACTERISTICS_POINTS = 660;

    private List<Characteristic> characteristics;
    private Appearance appearance;

    public Characteristics() {
        createCharacteristics();
        appearance = new Appearance();
    }

    private void createCharacteristics() {
        characteristics = new ArrayList<>();
        characteristics.add(new Characteristic("Ag"));
        characteristics.add(new Characteristic("Co"));
        characteristics.add(new Characteristic("Me"));
        characteristics.add(new Characteristic("Ra"));
        characteristics.add(new Characteristic("Ad"));
        characteristics.add(new Characteristic("Em"));
        characteristics.add(new Characteristic("In"));
        characteristics.add(new Characteristic("Pr"));
        characteristics.add(new Characteristic("Rp"));
        characteristics.add(new Characteristic("Fu"));
    }
    
    public List<Characteristic> getCharacteristicsList(){
    	return characteristics;
    }
    
    public static boolean isCharacteristicValid(String abbrev){
    	return allowedCharacteristics.contains(abbrev);
    }

    public Characteristic getCharacteristicFromAbbreviature(String abrev) {
        for (int i = 0; i < characteristics.size(); i++) {
            Characteristic characteristic = characteristics.get(i);
            if (characteristic.getAbbreviation().equals(abrev)) {
                return characteristic;
            }
        }
        return null;
    }
    
    public int getDevelopmentPoints() {
        int total = 0;
        for (int i = 0; i < 5; i++) {
            total += characteristics.get(i).getTemporalValue();
        }
        return total / 5;
    }
}

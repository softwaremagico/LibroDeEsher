/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwaremagico.librodeesher.pj.characteristic;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author jorge
 */
public class Characteristics {
	private final static String allowedCharacteristics="AgCoMeRaAdEmInPrRpFu";
    private final static int TOTAL_CHARACTERISTICS_POINTS = 660;

    List<Characteristic> characteristics;
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
    
    public static boolean isCharacteristicValid(String abbrev){
    	return allowedCharacteristics.contains(abbrev);
    }

    public Characteristic obtainCharacteristicFromAbbreviature(String abrev) {
        for (int i = 0; i < characteristics.size(); i++) {
            Characteristic characteristic = characteristics.get(i);
            if (characteristic.getAbbreviation().equals(abrev)) {
                return characteristic;
            }
        }
        return null;
    }
    
    public int obtainDevelopmentPoints() {
        int total = 0;
        for (int i = 0; i < 5; i++) {
            total += characteristics.get(i).getTemporalValue();
        }
        return total / 5;
    }
}

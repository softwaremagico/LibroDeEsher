package com.softwaremagico.librodeesher.pj.characteristic;
import com.softwaremagico.librodeesher.Rolemaster;

/**
 *
 * @author jorge
 */
public class Characteristic {

    private int temporalValue;
    private int temporalUpdate;
    private int potentialValue;
    private int raceBonus = 0;
    private int specialBonus = 0;
    private String abbreviation;

    public Characteristic(String ab) {
        abbreviation = ab;
        temporalValue = 21;
        temporalUpdate = 21;
        potentialValue = 21;
    }

    public int getTemporalValue(){
        return temporalValue;
    }
    
    private int obtainPotencial() {
        if (temporalValue >= 100) {
            return 99 + Rolemaster.rollDices(1, 2);
        }
        if (temporalValue >= 99) {
            return 98 + Rolemaster.rollDices(1, 2);
        }
        if (temporalValue >= 98) {
            return 97 + Rolemaster.rollDices(1, 3);
        }
        if (temporalValue >= 97) {
            return 96 + Rolemaster.rollDices(1, 4);
        }
        if (temporalValue >= 96) {
            return 95 + Rolemaster.rollDices(1, 5);
        }
        if (temporalValue >= 95) {
            return 94 + Rolemaster.rollDices(1, 6);
        }
        if (temporalValue >= 94) {
            return 93 + Rolemaster.rollDices(1, 7);
        }
        if (temporalValue >= 93) {
            return 92 + Rolemaster.rollDices(1, 8);
        }
        if (temporalValue >= 92) {
            return 91 + Rolemaster.rollDices(1, 9);
        }
        if (temporalValue >= 85) {
            return 90 + Rolemaster.rollDices(1, 10);
        }
        if (temporalValue >= 75) {
            return 80 + Rolemaster.rollDices(2, 10);
        }
        if (temporalValue >= 65) {
            return 70 + Rolemaster.rollDices(3, 10);
        }
        if (temporalValue >= 55) {
            return 60 + Rolemaster.rollDices(4, 10);
        }
        if (temporalValue >= 45) {
            return 50 + Rolemaster.rollDices(5, 10);
        }
        if (temporalValue >= 35) {
            return 40 + Rolemaster.rollDices(6, 10);
        }
        if (temporalValue >= 25) {
            return 30 + Rolemaster.rollDices(7, 10);
        }
        if (temporalValue >= 20) {
            return 20 + Rolemaster.rollDices(8, 10);
        }
        return potentialValue;
    }

    public int obtainCharacteristicUpgrade() {
        int dice1 = Rolemaster.rollDice(10);
        int dice2 = Rolemaster.rollDice(10);

        if (dice1 == dice2) {
            if (dice1 < 6) {
                return -dice1;
            } else {
                return dice1 * 2;
            }
        }else{
             if (potentialValue - temporalValue <= 10) {
                 return Math.min(dice1, dice2);
             }else if (potentialValue - temporalValue <= 20) {
                 return Math.max(dice1, dice2);
             } else {
                 return dice1 + dice2;
             }
        }
    }
    
    public String getAbbreviation(){
        return abbreviation;
    }
}

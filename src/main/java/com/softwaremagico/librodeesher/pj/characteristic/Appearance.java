package com.softwaremagico.librodeesher.pj.characteristic;

import com.softwaremagico.librodeesher.Rolemaster;

public class Appearance {
    private int raceBonus;
    private int dicesResult;
    
    public Appearance(){
         dicesResult = Rolemaster.rollDices(5, 10);
    }
    
    public int getTotal(int presencePotentialValue){
        return presencePotentialValue - 25 + dicesResult + raceBonus;
    }
}

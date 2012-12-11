/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwaremagico.librodeesher;

import java.util.Random;

/**
 *
 * @author jorge
 */
public class Rolemaster {

    public static Random random = new Random();

    /**
     * Tira "cuantos" dados de "dado" caras.
     */
    public static int rollDices(int number, int diceNumber) {
        int total = 0;
        for (int i = 0; i < number; i++) {
            total += rollDice(diceNumber);
        }
        return total;
    }

    /**
     * Realiza una tirada de un dado de "dado" caras.
     */
    public static int rollDice(int diceNumber) {
        return random.nextInt(diceNumber) + 1;
    }
}

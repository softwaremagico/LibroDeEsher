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
public  class Caracteristica implements Serializable {

    private int temporal = 31;
    private int nextTemporal = 31;
    private int potencial = 0;
    private int raza = 0;
    private int especial = 0;
    private String abreviatura;
    private Esher esher;

    /** Creates a new instance of Caracteristica */
    public Caracteristica(String ab, Esher tmp_esher) {
        abreviatura = ab;
        esher = tmp_esher;
    }

    public void GuardarPontencial() {
        if (esher.pj.nivel == 1) {
            potencial = CalcularPotencial();
            nextTemporal = temporal;
            CalcularProximoAumentoCaracteristica();
        }
    }

    private int CalcularPotencial() {
        if (temporal >= 100) {
            return 99 + esher.TiradaDados(1, 2);
        }
        if (temporal >= 99) {
            return 98 + esher.TiradaDados(1, 2);
        }
        if (temporal >= 98) {
            return 97 + esher.TiradaDados(1, 3);
        }
        if (temporal >= 97) {
            return 96 + esher.TiradaDados(1, 4);
        }
        if (temporal >= 96) {
            return 95 + esher.TiradaDados(1, 5);
        }
        if (temporal >= 95) {
            return 94 + esher.TiradaDados(1, 6);
        }
        if (temporal >= 94) {
            return 93 + esher.TiradaDados(1, 7);
        }
        if (temporal >= 93) {
            return 92 + esher.TiradaDados(1, 8);
        }
        if (temporal >= 92) {
            return 91 + esher.TiradaDados(1, 9);
        }
        if (temporal >= 85) {
            return 90 + esher.TiradaDados(1, 10);
        }
        if (temporal >= 75) {
            return 80 + esher.TiradaDados(2, 10);
        }
        if (temporal >= 65) {
            return 70 + esher.TiradaDados(3, 10);
        }
        if (temporal >= 55) {
            return 60 + esher.TiradaDados(4, 10);
        }
        if (temporal >= 45) {
            return 50 + esher.TiradaDados(5, 10);
        }
        if (temporal >= 35) {
            return 40 + esher.TiradaDados(6, 10);
        }
        if (temporal >= 25) {
            return 30 + esher.TiradaDados(7, 10);
        }
        if (temporal >= 20) {
            return 20 + esher.TiradaDados(8, 10);
        }
        return potencial;
    }

    public void CrearPuntosTemporal(int valor) {
        if (esher.pj.nivel == 1) {
            temporal = valor;
        }
    }

    public void CrearPuntosPotencial(int valor) {
        potencial = valor;
        nextTemporal = temporal;
        CalcularProximoAumentoCaracteristica();
    }

    public void IncrementarPuntosTemporalSinContemplarPotencial(int valor) {
        if (esher.pj.nivel == 1) {
            temporal += valor;
            if (temporal < 20) {
                temporal = 20;
            }
        }
    }

   public  void SubirNivelCaracteristica() {
        temporal = nextTemporal;
        CalcularProximoAumentoCaracteristica();
    }

    /**
     * Devuelve el valor temporal de una caracteristica.
     */
    public int ObtenerPuntosTemporal() {
        return temporal;
    }

    public int ObtenerPuntosNextTemporal() {
        return nextTemporal;
    }

    /**
     * Devuelve el coste sobre 660 de que la caracteristicas obtenga ese valor.
     */
    public int ObtenerValorTemporal() {
        if (temporal >= 102) {
            return 14;
        }
        if (temporal >= 101) {
            return 12;
        }
        if (temporal >= 100) {
            return 10;
        }
        if (temporal >= 98) {
            return 9;
        }
        if (temporal >= 96) {
            return 8;
        }
        if (temporal >= 94) {
            return 7;
        }
        if (temporal >= 92) {
            return 6;
        }
        if (temporal >= 90) {
            return 5;
        }
        if (temporal >= 85) {
            return 4;
        }
        if (temporal >= 80) {
            return 3;
        }
        if (temporal >= 75) {
            return 2;
        }
        if (temporal >= 70) {
            return 1;
        }
        if (temporal >= 31) {
            return 0;
        }
        if (temporal >= 26) {
            return -1;
        }
        if (temporal >= 21) {
            return -2;
        }
        if (temporal >= 16) {
            return -3;
        }
        if (temporal >= 11) {
            return -4;
        }
        if (temporal >= 10) {
            return -5;
        }
        if (temporal >= 8) {
            return -6;
        }
        if (temporal >= 6) {
            return -7;
        }
        if (temporal >= 4) {
            return -8;
        }
        if (temporal >= 2) {
            return -9;
        }
        return -10;
    }

   public int ObtenerValorRaza() {
        return raza;
    }

    public void CambiarPuntosRaza(int valor) {
        raza = valor;
    }

    public int ObtenerPuntosEspecial() {
        return especial + esher.pj.DevolverBonusTalentoCaracteristica(abreviatura);
    }

    /**
     * Devuelve los puntos obtenidos al calcular el valor potencial de una caracteristica.
     */
    public int ObtenerPuntosPotencial() {
        return potencial;
    }

    public int Total() {
        return ObtenerValorTemporal() + ObtenerValorRaza() + ObtenerPuntosEspecial();
    }

    public String DevolverAbreviatura() {
        return abreviatura;
    }

    public int ObtenerCosteTemporalCaracteristica(int value) {
        Double d;
        if (value < 91) {
            return value;
        } else //Double d = Math.pow(ObtenerPuntosTemporal()-90,2) + 90);
        {
            d = Math.pow(value - 90, 2) + 90;
        }
        return d.intValue();
    }

    public int ObtenerCosteTemporalCaracteristica() {
        Double d;
        if (ObtenerPuntosTemporal() < 91) {
            return ObtenerPuntosTemporal();
        } else //Double d = Math.pow(ObtenerPuntosTemporal()-90,2) + 90);
        {
            d = Math.pow(ObtenerPuntosTemporal() - 90, 2) + 90;
        }
        return d.intValue();
    }

    private void IncrementarPuntosNextTemporal(int valor) {
        nextTemporal += valor;
        if (nextTemporal > potencial) {
            nextTemporal = potencial;
        }
        if (nextTemporal < 20) {
            nextTemporal = 20;
        }
    }

    public void CalcularProximoAumentoCaracteristica() {
        int subir = 0;
        int dado1 = esher.TiradaDado(10);
        int dado2 = esher.TiradaDado(10);
        if (potencial - nextTemporal <= 10) {
            if (dado1 != dado2) {
                subir = Math.min(dado1, dado2);
            } else {
                if (dado1 < 6) {
                    subir = -dado1;
                } else {
                    subir = dado1 * 2;
                }
            }
        } else {
            if (potencial - nextTemporal <= 20) {
                if (dado1 != dado2) {
                    subir = Math.max(dado1, dado2);
                } else {
                    if (dado1 < 6) {
                        subir = -dado1;
                    } else {
                        subir = dado1 * 2;
                    }
                }
            } else {
                if (dado1 != dado2) {
                    subir = dado1 + dado2;
                } else {
                    if (dado1 < 6) {
                        subir = -dado1;
                    } else {
                        subir = dado1 * 2;
                    }
                }
            }
        }
        IncrementarPuntosNextTemporal(subir);
    }

    public void CambiarPuntosTemporal(int valor) {
        temporal = valor;
    }

    /**
     * Util al importar subida de nivel.
     * @param valor
     */
    public void CambiarPuntosNextTemporal(int valor) {
        nextTemporal = valor;
    }
}

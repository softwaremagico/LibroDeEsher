/*
 * Fichero.java
 *
 *
 * This software is designed by Jorge Hortelano Otero.
 * SoftwareMagico@gmail.com
 * Copyright (C) 2007 Jorge Hortelano Otero.
 * C/Botanico 12, 1. Valencia CP:46008 (Spain).
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 *
 * Created on 22 de noviembre de 2007, 15:18
 *
 *
 */
package com.softwaremagico.files;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Hortelano
 */
class Fichero {

    String nombreFichero;

    /** Creates a new instance of LecturaFicheros */
    public Fichero(String tmp_file) {
        nombreFichero = tmp_file;
    }

    /**
     * Devuelve las lineas de un fichero leido anteriormente.
     */
    public List<String> InLines() {
        String OS = System.getProperty("os.name");
        if(OS.contains("Windows Vista")){
            return ReadTextFileInLines(nombreFichero, "ISO8859_1");
        }else if(OS.contains("Windows")){
            return ReadTextFileInLines(nombreFichero, "Cp1252");
        }
        return ReadTextFileInLines(nombreFichero, "UTF8");
    }

    public String InString() {
        return ReadTextFile(nombreFichero);
    }

    //Cambia el nombre del fichero por si no se aclara con el juego de caracteres
    private String DepurarNombreFichero(String file) {
        /*String tmp_file =  nombreFichero.replaceAll("�", "í");
        tmp_file =  tmp_file.replaceAll("�", "á");
        tmp_file =  tmp_file.replaceAll("�", "ú");
        tmp_file =  tmp_file.replaceAll("�", "é");
        tmp_file =  tmp_file.replaceAll("�", "ó");
        return tmp_file;*/
        return file;
    }

    /**
     * Devuelve el fichero leido como una lista de l�neas.
     */
    private List<String> ReadTextFileInLines(String file, String mode) {
        List<String> contents = new ArrayList<String>();
        String tmp_file;

        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file)), mode));
            //input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
            String line = null;
            while ((line = input.readLine()) != null) {
                contents.add(line);
            }
        } catch (FileNotFoundException ex) {
            //Problemas con el juego de caracteres?
            tmp_file = DepurarNombreFichero(file);
            if (!file.equals(tmp_file)) {
                return ReadTextFileInLines(tmp_file, mode);
            }
        } catch (IOException ex) {
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return contents;
    }


    /**
     * Devuelve el fichero leido como un �nico string.
     */
    private String ReadTextFile(String file) {
        File licence = new File(file);
        String text = "Error opening the file.";
        try {
            FileInputStream inputData = new FileInputStream(licence);
            byte bt[] = new byte[(int) licence.length()];
            int numBytes = inputData.read(bt);
            text = new String(bt);
            inputData.close();
        } catch (IOException ex) {
        }
        return text;
    }
}

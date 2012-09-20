/*
 * Directorio.java
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
 * Created on 22 de noviembre de 2007, 14:54
 *
 *
 */

package com.softwaremagico.explorarDirectorios;
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

import com.softwaremagico.librodeesher.gui.MostrarError;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Hortelano
 */
public class Directorio implements Serializable{
    public String nombreDirectorio;
    //List<Fichero> ficheros = new ArrayList<Fichero>();
    private List<String> ficheros = new ArrayList<String>();
    
    /** Creates a new instance of Directorio */
    public Directorio(String tmp_directorio) throws Exception {
        nombreDirectorio = tmp_directorio;
        //ficheros = BuscaFicherosExistentes();
    }
    
    public String DevolverDirectorio(){
        return nombreDirectorio;
    }
    
    public List<String> ObtenerFicheros(String src) throws Exception{
        //Creamos el Objeto File con la URL que queremos desplegar
        File dir = new File(src);
        List<String> lista = new ArrayList<String>();
        if (dir.isDirectory()) {
            if (!dir.exists()) {
                throw new Exception("Error: El directorio no existe");
            }
            
            //tomamos los ficheros contenidos en la URL dada
            String[] archivos = dir.list();
            //agregamos cada fichero en una lista
            for(int i=0;i<archivos.length;i++)
                    lista.add(archivos[i]);
        }
        return lista;
    }
        
    private List<String> BuscaFicherosExistentes(String directorio) throws Exception {
        List<String> ficherosDisponibles = ObtenerFicheros(directorio);
        List<String> listaDatos = new ArrayList<String>();
        for(int i=0; i<ficherosDisponibles.size();i++){
            String dato = ficherosDisponibles.get(i);
            dato = dato.replaceAll(".txt", "");
            if(!dato.contains("svn"))
                if(!dato.contains("plantilla"))
                    listaDatos.add(dato);
        }
        return listaDatos;
    }
      
    public List<String> ObtenerFicherosSubdirectorio(String subdirectorio) throws Exception{
        return BuscaFicherosExistentes(subdirectorio);
    }
    
    public List<String> FicherosDisponibles(){
        return ficheros;
    }
    
    public List<String> LeerLineasFichero(String fichero){
        Fichero ficheroALeer = new Fichero(fichero);
        return ficheroALeer.InLines();
        
    }
    
    public String LeerFicheroComoTexto(String fichero){
        Fichero ficheroALeer = new Fichero(fichero);
        return ficheroALeer.InString();
    }
    
    /**
     * Store text in a file. The text must be written in a String list.
     * @param dataList The text to be written
     * @file the path to the file.
     */
    public void SaveListInFile(List dataList, String file){
        File outputFile;
        byte b[];
        //Se guarda en el fichero
        outputFile = new File(file);
        try {
            FileOutputStream outputChannel = new FileOutputStream(outputFile);
            for (int i=0; i<dataList.size();i++){
                b = (dataList.get(i).toString()+"\n").getBytes();
                try {
                    outputChannel.write(b);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                outputChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            String text = "Imposible crear el fichero:\n\t"+ file +
                    "\n¿Existe el directorio?\n";
            new MostrarError(text, "Directorios");
        }
    }
    
    /**
     * Store text in a file. The text must be written in a String list.
     * @param dataList The text to be written
     * @file the path to the file.
     */
    public void SaveTextInFile(String text, String file){
        File outputFile;
        byte b[];
        //Se guarda en el fichero
        outputFile = new File(file);
        try {
            FileOutputStream outputChannel = new FileOutputStream(outputFile);
            
            b = text.getBytes();
            try {
                outputChannel.write(b);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            try {
                outputChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            String msg = "Impossible to generate file:\n\t"+ file +
                    ". \nIs the working directory created properly?\n"+
                    "Check into \"Configuration -> Configurate the Computer\"";
            new MostrarError(msg, "Directorios");
        }
    }
    
    public void GenerarDirectorioSiNoExiste(String file){
        File f = new File(file);
        f.mkdir();
    }
    
}

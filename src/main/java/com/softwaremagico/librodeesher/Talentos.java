/*
This software is designed by Jorge Hortelano Otero.
softwaremagico@gmail.com
Copyright (C) 2007 Jorge Hortelano Otero.
C/Botanico 12, 1. Valencia CP:46008 (Spain).
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
Created on june of 2008.
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

import com.softwaremagico.librodeesher.gui.MostrarError;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class Talentos implements Serializable {

    private final String ARCHIVO_TALENTOS = "talentos.txt";
    private Esher esher;
    private List<Talento> talentos;

    Talentos(Esher tmp_esher) {
        try {
            esher = tmp_esher;
            talentos = new ArrayList<Talento>();
            LeerTalentosDeArchivo();
        } catch (Exception ex) {
            Logger.getLogger(Talentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LeerTalentosDeArchivo() throws Exception {
        String line;
        List<String> lines = esher.directorioRolemaster.LeerLineasTalentos(ARCHIVO_TALENTOS);
        for (int i = 2; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (!line.startsWith("#")) {
                try {
                    String[] descomposed_line = line.split("\t");
                    String nombreTalento = descomposed_line[0];
                    int coste = Integer.parseInt(descomposed_line[1]);
                    String clasificacion = descomposed_line[2];
                    String bonuses = descomposed_line[3];
                    String permitido = descomposed_line[4];
                    String descripcion = descomposed_line[5];
                    Talento talento = new Talento(esher, nombreTalento, coste, clasificacion, descripcion, permitido);
                    talento.AddBonificacion(bonuses);
                    talentos.add(talento);
                } catch (NumberFormatException npe) {
                    npe.printStackTrace();
                    new MostrarError("Error en el coste del talento:\n\"" + line + "\"", "Talentos");
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    new MostrarError("Error en la formación del talento:\n\"" + line + "\"", "Talentos");
                }
            }
        }
        OrdenarTalentos();
    }

    public Talento DevolverTalento(String nombre) {
        for (int i = 0; i < talentos.size(); i++) {
            if (talentos.get(i).nombre.equals(nombre)) {
                return talentos.get(i);
            }
        }
        return null;
    }

    public int Size() {
        return talentos.size();
    }

    public Talento Get(int i) {
        return talentos.get(i);
    }

    public void OrdenarTalentos() {
        String[] nombresTalentos = new String[talentos.size()];
        for (int i = 0; i < talentos.size(); i++) {
            Talento tal = talentos.get(i);
            nombresTalentos[i] = tal.nombre;
        }
        //Ordenamos las Categorías.
        java.util.Arrays.sort(nombresTalentos, java.text.Collator.getInstance(Locale.ITALIAN));

        List<Talento> listaTalentosOrdenados = new ArrayList<Talento>();
        for (int j = 0; j < nombresTalentos.length; j++) {
            Talento talOrd = DevolverTalento(nombresTalentos[j]);
            listaTalentosOrdenados.add(talOrd);
        }
        talentos = listaTalentosOrdenados;
    }

    public List<Talento> BarajarTalentos() {
        List<Talento> copiaTalentos = new ArrayList<Talento>();
        List<Talento> listaTalentosBarajada = new ArrayList<Talento>();
        copiaTalentos.addAll(talentos);
        if (esher.inteligencia) {
            int valor = 1;
            while (copiaTalentos.size() > 0 && valor < 20) {
                for (int j = 0; j < copiaTalentos.size(); j++) {
                    Talento tal = copiaTalentos.get(j);
                    try {
                        if (esher.pj.EsTalentoAdecuado(tal) > 0) {
                            listaTalentosBarajada.add(copiaTalentos.get(j));
                            copiaTalentos.remove(j);
                            j--;
                        }
                    } catch (ArrayIndexOutOfBoundsException aiofb) {
                    }
                }
                valor++;
            }
        }
        //Totalmente aleatorio.
        while (copiaTalentos.size() > 0) {
            int elemento = esher.generator.nextInt(copiaTalentos.size());
            listaTalentosBarajada.add(copiaTalentos.get(elemento));
            copiaTalentos.remove(elemento);
        }
        return listaTalentosBarajada;
    }
}

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

import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jorge
 */
public class Talento implements Serializable {

    public String nombre;
    public int coste;
    public String clasificacion;
    public String permitido;
    public List<BonusCategoria> bonusCategoria = new ArrayList<>();
    public ListadoCategoriaYHabilidadesElegir bonusCategoriaHabilidadElegir = null;
    public List<BonusHabilidad> bonusHabilidad = new ArrayList<>();
    public List<BonusTR> bonusTrs = new ArrayList<>();
    public List<BonusCaracteristica> bonusCaracteristica = new ArrayList<>();
    private String descripcion;
    public String listadoCategorias;
    public int bonusMovimiento = 0;
    public int tipoarmadura = 0;
    public List<String> listadoCategoriasYHabilidadesElegidas = new ArrayList<>();

    public Talento(String tmp_nombre, int tmp_coste, String tmp_clasificacion,
            String tmp_descripcion, String tmp_permitido) {
        nombre = tmp_nombre;
        coste = tmp_coste;
        clasificacion = tmp_clasificacion;
        descripcion = tmp_descripcion;
        permitido = tmp_permitido;
    }

    void AddBonificacion(String tmp_listadoCategorias) {
        listadoCategorias = tmp_listadoCategorias;
        BonusTR bonusTr;
        String[] descomposed_line = listadoCategorias.split(", ");
        for (int i = 0; i < descomposed_line.length; i++) {
            String[] linea_categoria = descomposed_line[i].split(" \\(");
            //Elige una o varias categorias de una lista.
            if (linea_categoria[0].contains("{")) {
                linea_categoria[0] = linea_categoria[0].replace("{", "").replace("}", "");
                if (linea_categoria[0].contains("|")) {
                    List<String> opciones = new ArrayList<>();
                    String[] lineaOpciones = linea_categoria[0].split(" \\| ");
                    opciones.addAll(Arrays.asList(lineaOpciones));
                    int cuantas = Integer.parseInt(linea_categoria[1].split("\\[")[1].replace("]", ""));
                    int bonus = Integer.parseInt(linea_categoria[1].split("\\[")[0].replace(")", "").replace("(", ""));

                    boolean añadir = false;
                    bonusCategoriaHabilidadElegir = new ListadoCategoriaYHabilidadesElegir(this, opciones, bonus, cuantas, añadir);
                } else if (linea_categoria[0].contains("Cualquier Categoría")) {
                    int cuantas = Integer.parseInt(linea_categoria[1].split("\\[")[1].replace("]", ""));
                    int bonus = Integer.parseInt(linea_categoria[1].split("\\[")[0].replace(")", "").replace("(", ""));
                    bonusCategoriaHabilidadElegir = new ListadoCategoriaYHabilidadesElegir(this, bonus, cuantas, true, null, false);
                } else if (linea_categoria[0].contains("Cualquier Habilidad de ")) {
                    int cuantas = Integer.parseInt(linea_categoria[1].split("\\[")[1].replace("]", ""));
                    int bonus = Integer.parseInt(linea_categoria[1].split("\\[")[0].replace(")", "").replace("(", ""));
                    Categoria catDonde = Personaje.getInstance().DevolverCategoriaDeNombre(linea_categoria[0].split("Cualquier Habilidad de ")[1]);
                    bonusCategoriaHabilidadElegir = new ListadoCategoriaYHabilidadesElegir(this, bonus, cuantas, false, catDonde, true);
                } else if (linea_categoria[0].contains("Cualquier Habilidad")) {
                    int cuantas = Integer.parseInt(linea_categoria[1].split("\\[")[1].replace("]", ""));
                    int bonus = Integer.parseInt(linea_categoria[1].split("\\[")[0].replace(")", "").replace("(", ""));
                    bonusCategoriaHabilidadElegir = new ListadoCategoriaYHabilidadesElegir(this, bonus, cuantas, false, null, true);
                }
            } else if ((linea_categoria[0].equals("TR Esencia"))
                    || (linea_categoria[0].equals("TR Canalización"))
                    || (linea_categoria[0].equals("TR Mentalismo"))
                    || (linea_categoria[0].equals("TR Psiónico"))
                    || (linea_categoria[0].equals("TR Enfermedades"))
                    || (linea_categoria[0].equals("TR Venenos"))
                    || (linea_categoria[0].equals("TR Frío"))
                    || (linea_categoria[0].equals("TR Calor"))
                    || (linea_categoria[0].equals("TR Miedo"))
                    || (linea_categoria[0].equals("TR Reino"))) {
                int bonus = Integer.parseInt(linea_categoria[1].replace(")", ""));
                bonusTr = new BonusTR(linea_categoria[0], bonus);
                bonusTrs.add(bonusTr);
            } else if (linea_categoria[0].equals("Movimiento")) {
                int bonus = Integer.parseInt(linea_categoria[1].replace(")", ""));
                bonusMovimiento = bonus;
            } else if (linea_categoria[0].equals("TA")) {
                int bonus = Integer.parseInt(linea_categoria[1].replace(")", ""));
                tipoarmadura = bonus;
            } else if (Personaje.getInstance().DevolverCategoriaDeNombre(linea_categoria[0]) != null
                    && !linea_categoria[0].equals("Desarrollo de Puntos de Poder")) {
                if (linea_categoria[1].contains("Común")) {
                    BonusCategoria categoria = new BonusCategoria(linea_categoria[0], true);
                    bonusCategoria.add(categoria);
                } else {
                    //Bonus por rango.
                    if (linea_categoria[1].contains("r")) {
                        int bonus = Integer.parseInt(linea_categoria[1].replace("r)", ""));
                        BonusCategoria categoria = new BonusCategoria(linea_categoria[0], 0, 0, bonus);
                        bonusCategoria.add(categoria);
                    } else if (linea_categoria[1].contains("R")) {
                        //Habilidad restringida.
                        BonusCategoria categoria = new BonusCategoria(linea_categoria[0], 0, 0, 0);
                        categoria.restringida = true;
                        bonusCategoria.add(categoria);
                    } else {
                        //Bonus total.
                        boolean aVeces = false;
                        if (linea_categoria[1].contains("*")) {
                            aVeces = true;
                            linea_categoria[1] = linea_categoria[1].replace("*", "");
                        }
                        int bonus = Integer.parseInt(linea_categoria[1].replace(")", ""));
                        BonusCategoria categoria = new BonusCategoria(linea_categoria[0], bonus, aVeces);
                        bonusCategoria.add(categoria);
                    }
                }
            } else if ((Personaje.getInstance().DevolverHabilidadDeNombre(linea_categoria[0]) != null) || (linea_categoria[0].equals("Apariencia"))) {
                BonusHabilidad habilidad;
                if (linea_categoria[1].contains("Común")) {
                    habilidad = new BonusHabilidad(linea_categoria[0], true);
                } else {
                    //Bonus por rango.
                    if (linea_categoria[1].contains("r")) {
                        int bonus = Integer.parseInt(linea_categoria[1].replace("r)", ""));
                        habilidad = new BonusHabilidad(linea_categoria[0], 0, 0, bonus);
                    } else if (linea_categoria[1].contains("R")) {
                        //Habilidad restringida.
                        habilidad = new BonusHabilidad(linea_categoria[0], 0, 0, 0);
                        habilidad.restringida = true;
                    } else {
                        boolean aVeces = false;
                        if (linea_categoria[1].contains("*")) {
                            aVeces = true;
                            linea_categoria[1] = linea_categoria[1].replace("*", "");
                        }
                        int bonus = Integer.parseInt(linea_categoria[1].replace(")", ""));
                        habilidad = new BonusHabilidad(linea_categoria[0], bonus, aVeces);
                    }
                }
                bonusHabilidad.add(habilidad);
                //Modifica Apariencia
            } else if (Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(linea_categoria[0]) != null) {
                int bonus = Integer.parseInt(linea_categoria[1].replace(")", ""));
                BonusCaracteristica caracteristica = new BonusCaracteristica(linea_categoria[0], bonus);
                bonusCaracteristica.add(caracteristica);
            } else if (!linea_categoria[0].equals("Ninguno") && !linea_categoria[0].equals("Nada") && !linea_categoria[0].equals("Ninguna")) {
                MostrarMensaje.showErrorMessage("Categoria \"" + linea_categoria[0] + "\" del talento " + nombre + " desconocida.", "Talento");
            }
        }
    }

    public String Descripcion() {
        String elegido = "";
        if (listadoCategoriasYHabilidadesElegidas.size() > 0) {
            elegido += " (Elegido: ";
            for (int i = 0; i < listadoCategoriasYHabilidadesElegidas.size(); i++) {
                elegido += listadoCategoriasYHabilidadesElegidas.get(i);
                if (i < listadoCategoriasYHabilidadesElegidas.size() - 1) {
                    elegido += ", ";
                }
            }
        }
        return descripcion + elegido;
    }

    public boolean EsTalentoPermitido() {
        if (permitido.contains("Todos")) {
            return true;
        }
        if (permitido.contains(Personaje.getInstance().raza)) {
            return true;
        }
        if (permitido.contains(Personaje.getInstance().profesion)) {
            return true;
        }
        return false;
    }

    public void AddHabilidadAfectada(String hab, int bonus) {
        BonusHabilidad habilidad = new BonusHabilidad(hab, bonus, false);
        bonusHabilidad.add(habilidad);
        if (!listadoCategoriasYHabilidadesElegidas.contains(hab)) {
            listadoCategoriasYHabilidadesElegidas.add(hab);
        }
    }

    public void RemoveHabilidadAfectada(String hab) {
        for (int i = 0; i < bonusHabilidad.size(); i++) {
            if (bonusHabilidad.get(i).nombre.equals(hab)) {
                bonusHabilidad.remove(i);
                break;
            }
        }
        listadoCategoriasYHabilidadesElegidas.remove(hab);
    }

    public void LimpiarHabilidadesAfectadas() {
        bonusHabilidad = new ArrayList<>();
    }

    public void AddCategoriaAfectada(String cat, int bonus) {
        BonusCategoria categoria = new BonusCategoria(cat, bonus, false);
        bonusCategoria.add(categoria);
        if (!listadoCategoriasYHabilidadesElegidas.contains(cat)) {
            listadoCategoriasYHabilidadesElegidas.add(cat);
        }
    }

    public void RemoveCategoriaAfectada(String cat) {
        for (int i = 0; i < bonusCategoria.size(); i++) {
            if (bonusCategoria.get(i).nombre.equals(cat)) {
                bonusCategoria.remove(i);
                break;
            }
        }
        listadoCategoriasYHabilidadesElegidas.remove(cat);
    }

    public void RemoveHabilidadCategoriaAfectada(String nombre) {
        if (Personaje.getInstance().DevolverCategoriaDeNombre(nombre) != null) {
            RemoveCategoriaAfectada(nombre);
        } else if (Personaje.getInstance().DevolverHabilidadDeNombre(nombre) != null) {
            RemoveHabilidadAfectada(nombre);
        }
    }

    public void AddCategoriaElegidaListadoCategoria(String categoria) {
        bonusCategoriaHabilidadElegir.AddHabilidadElegida(categoria);
    }

    public void BorrarCategoriasElegidasDeListado() {
        bonusCategoriaHabilidadElegir = null;
    }

    public class BonusCategoria implements Serializable {

        public int bonus;
        public String nombre;
        public int rangos = 0;
        public boolean habilidadComun = false; //Debe elegir una habilidad que se haga común.
        public Habilidad habilidadEscogida = null;
        public int bonusRango = 0;
        public boolean aVeces = false; //Algunos Talentos solo afectan a habilidades en cierto momento.
        public boolean restringida = false;

        BonusCategoria(String tmp_nombre, int tmp_bonus, boolean noSiempre) {
            aVeces = noSiempre;
            bonus = tmp_bonus;
            nombre = tmp_nombre;
        }

        BonusCategoria(String tmp_nombre, boolean comun) {
            habilidadComun = comun;
            nombre = tmp_nombre;
        }

        BonusCategoria(String tmp_nombre, int tmp_bonus, int tmp_rangos, int tmp_bonusRango) {
            bonus = tmp_bonus;
            nombre = tmp_nombre;
            rangos = tmp_rangos;
            bonusRango = tmp_bonusRango;
        }
    }

    public class ListadoCategoriaYHabilidadesElegir implements Serializable {

        public List<String> listadoCategoriasHabilidadesPosiblesAElegir = new ArrayList<>();
        public int bonus;
        public int cuantas;
        public boolean añadir;
        public List<String> listadoCategoriasYHabilidadesElegidas = new ArrayList<>();
        public boolean seleccionaCualquierCategoria;
        public Categoria categoriaDondeSeleccionarHabilidades;
        public boolean seleccionaCualquierHabilidad;
        public Talento talento;

        ListadoCategoriaYHabilidadesElegir(Talento t, List<String> tmp_listadoCategorias, int tmp_bonus, int tmp_cuantas, boolean tmp_añadir) {
            talento = t;
            bonus = tmp_bonus;
            listadoCategoriasHabilidadesPosiblesAElegir = tmp_listadoCategorias;
            cuantas = tmp_cuantas;
            añadir = tmp_añadir;
        }

        ListadoCategoriaYHabilidadesElegir(Talento t, int tmp_bonus, int tmp_cuantas, boolean tmp_cualquierCategoria, Categoria tmp_cat, boolean tmp_cualquierHabilidad) {
            talento = t;
            bonus = tmp_bonus;
            añadir = true;
            cuantas = tmp_cuantas;
            seleccionaCualquierCategoria = tmp_cualquierCategoria;
            categoriaDondeSeleccionarHabilidades = tmp_cat;
            seleccionaCualquierHabilidad = tmp_cualquierHabilidad;
            //Si es cualquier categoria.
            if (tmp_cualquierCategoria) {
                for (int j = 0; j < Personaje.getInstance().categorias.size(); j++) {
                    listadoCategoriasHabilidadesPosiblesAElegir.add(Personaje.getInstance().categorias.get(j).DevolverNombre());
                }
                //Si es cualquier habilidad de categoría.
            } else if (categoriaDondeSeleccionarHabilidades != null && seleccionaCualquierHabilidad) {
                for (int j = 0; j < bonusCategoriaHabilidadElegir.categoriaDondeSeleccionarHabilidades.listaHabilidades.size(); j++) {
                    Habilidad hab = bonusCategoriaHabilidadElegir.categoriaDondeSeleccionarHabilidades.listaHabilidades.get(j);
                    listadoCategoriasHabilidadesPosiblesAElegir.add(hab.DevolverNombre());
                }
                //Si es cualquier habilidad.
            } else if (categoriaDondeSeleccionarHabilidades == null && seleccionaCualquierHabilidad) {
                List<String> habilidades = new ArrayList<>();
                for (int c = 0; c < Personaje.getInstance().categorias.size(); c++) {
                    Categoria catSeleccionada = Personaje.getInstance().categorias.get(c);
                    for (int j = 0; j < catSeleccionada.listaHabilidades.size(); j++) {
                        Habilidad hab = catSeleccionada.listaHabilidades.get(j);
                        habilidades.add(hab.DevolverNombre());
                    }
                }
                habilidades = Esher.OrdenarLista(habilidades);
                for (int s = 0; s < habilidades.size(); s++) {
                    listadoCategoriasHabilidadesPosiblesAElegir.add(habilidades.get(s));
                }
            }
        }

        void AddHabilidadElegida(String nombre) {
            Categoria cat;
            if (!listadoCategoriasYHabilidadesElegidas.contains(nombre)) {
                //Solamente progresiones estándar o combinadas.
                try {
                    if ((cat = Personaje.getInstance().DevolverCategoriaDeNombre(nombre)) == null) {
                        cat = Personaje.getInstance().DevolverHabilidadDeNombre(nombre).categoriaPadre;
                    }
                    if (cat.TipoCategoria().equals("Estándar") || cat.TipoCategoria().equals("Combinada")) {
                        if (seleccionaCualquierCategoria) {
                            listadoCategoriasYHabilidadesElegidas.add(nombre);
                        } else if (listadoCategoriasHabilidadesPosiblesAElegir.contains(nombre)) {
                            listadoCategoriasYHabilidadesElegidas.add(nombre);
                        }
                    }
                } catch (NullPointerException npe) {
                }
            }
            if (!talento.listadoCategoriasYHabilidadesElegidas.contains(nombre)) {
                talento.listadoCategoriasYHabilidadesElegidas.add(nombre);
            }
        }

        /**
         * Selecciona al azar una de las habilidades, siempre que esta tenga
         * algún rango.
         */
        void SeleccionarAlAzar() {
            listadoCategoriasHabilidadesPosiblesAElegir = Esher.BarajarLista(listadoCategoriasHabilidadesPosiblesAElegir);
            for (int i = 0; i < cuantas; i++) {
                Categoria cat;
                Habilidad hab;
                if ((cat = Personaje.getInstance().DevolverCategoriaDeNombre(listadoCategoriasHabilidadesPosiblesAElegir.get(i))) != null) {
                    if (cat.DevolverRangos() > 0) {
                        listadoCategoriasYHabilidadesElegidas.add(listadoCategoriasHabilidadesPosiblesAElegir.get(i));
                        talento.listadoCategoriasYHabilidadesElegidas.add(listadoCategoriasHabilidadesPosiblesAElegir.get(i));
                    }
                } else if ((hab = Personaje.getInstance().DevolverHabilidadDeNombre(listadoCategoriasHabilidadesPosiblesAElegir.get(i))) != null) {
                    if (hab.DevolverRangos() > 0) {
                        listadoCategoriasYHabilidadesElegidas.add(listadoCategoriasHabilidadesPosiblesAElegir.get(i));
                        talento.listadoCategoriasYHabilidadesElegidas.add(listadoCategoriasHabilidadesPosiblesAElegir.get(i));
                    }
                }
            }
            //Si no se ha añadido ninguna, añade una cualquiera
            if (listadoCategoriasYHabilidadesElegidas.isEmpty()) {
                listadoCategoriasYHabilidadesElegidas.add(listadoCategoriasHabilidadesPosiblesAElegir.get(0));
                talento.listadoCategoriasYHabilidadesElegidas.add(listadoCategoriasHabilidadesPosiblesAElegir.get(0));
            }
        }
    }

    public class BonusHabilidad implements Serializable {

        public int bonus;
        public String nombre;
        public int rangos = 0;
        public boolean comun = false;
        public int bonusRango = 0;
        public boolean aVeces = false; //Algunos Talentos solo afectan a habilidades en cierto momento.
        public boolean restringida = false;

        public BonusHabilidad(String tmp_nombre, int tmp_bonus, boolean noSiempre) {
            aVeces = noSiempre;
            bonus = tmp_bonus;
            nombre = tmp_nombre;
        }

        public BonusHabilidad(String tmp_nombre, boolean tmp_comun) {
            nombre = tmp_nombre;
            comun = tmp_comun;
        }

        public BonusHabilidad(String tmp_nombre, int tmp_bonus, int tmp_rangos, int tmp_bonusRango) {
            bonus = tmp_bonus;
            nombre = tmp_nombre;
            rangos = tmp_rangos;
            bonusRango = tmp_bonusRango;
        }
    }

    public class BonusCaracteristica implements Serializable {

        public int bonus;
        public String nombre;

        public BonusCaracteristica(String tmp_nombre, int tmp_bonus) {
            bonus = tmp_bonus;
            nombre = tmp_nombre;
        }
    }

    public class BonusTR implements Serializable {

        public int bonus;
        public String nombre;

        public BonusTR(String tmp_nombre, int tmp_bonus) {
            bonus = tmp_bonus;
            nombre = tmp_nombre;
        }
    }
}

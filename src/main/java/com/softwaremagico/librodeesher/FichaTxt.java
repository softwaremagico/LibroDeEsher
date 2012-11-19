/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwaremagico.librodeesher;

import com.softwaremagico.files.DirectorioRolemaster;

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
/**
 *
 * @author jorge
 */
public class FichaTxt {

    public FichaTxt() {
    }

    /**
     * Genera un texto con el nombre, raza, profesion y otros detalles del
     * Personaje.getInstance().
     */
    public String ExportarATextDetalles() {
        return Personaje.getInstance().DevolverNombreCompleto() + "\tNº " + Personaje.getInstance().nivel + "\n"
                + Personaje.getInstance().raza + "\n" + Personaje.getInstance().profesion + "\n";
    }

    private String ExportarATextoCaracteristicas() {
        String text = "Caract\tTemp\tPot\tTot\tRaza\tEsp\tTotal\n";
        text += "---------------------------------------------------------------------------------\n";
        for (int i = 0; i < Personaje.getInstance().caracteristicas.Size(); i++) {
            text = text + Personaje.getInstance().caracteristicas.Get(i).DevolverAbreviatura() + "\t"
                    + Personaje.getInstance().caracteristicas.Get(i).ObtenerPuntosTemporal() + "\t"
                    + Personaje.getInstance().caracteristicas.Get(i).ObtenerPuntosPotencial() + "\t"
                    + Personaje.getInstance().caracteristicas.Get(i).ObtenerValorTemporal() + "\t"
                    + Personaje.getInstance().caracteristicas.Get(i).ObtenerValorRaza() + "\t"
                    + Personaje.getInstance().caracteristicas.Get(i).ObtenerPuntosEspecial() + "\t"
                    + Personaje.getInstance().caracteristicas.Get(i).Total() + "\n";
        }
        return text;
    }

    public String ExportarATextoHabilidades() {
        String text = "Nombre:                                        "
                + "\tCoste\tRang\tBon\tCar\tOtros\tTotal\n";
        text += "------------------------------------------------------------------"
                + "------------------------------------------------------------------"
                + "-------------------------------------------------------------\n";
        int incrementoTamaño = 10;

        int tamañoMaximoNombre = Personaje.getInstance().DevolverTamañoMaximoNombreCategoriasYHabilidades();

        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            if (cat.MereceLaPenaMostrar()) {
                text = text + cat.DevolverNombreTamañoDeterminado(
                        tamañoMaximoNombre + incrementoTamaño)
                        + "\t" + cat.GenerarCadenaCosteRangos() + "\t"
                        + cat.DevolverRangos() + "\t" + cat.DevolverValorRangoCategoria() + "\t"
                        + cat.DevolverValorCaracteristicas() + "\t" + cat.DevolverBonuses();
                String letra = "";
                if (cat.historial) {
                    letra += "H";
                }
                if (cat.DevolverBonusTalentos() != 0) {
                    letra += "T";
                }
                if (Personaje.getInstance().ExisteObjetoModificaCategoria(cat)) {
                    letra += "O";
                }
                if (!letra.equals("")) {
                    text += "(" + letra + ")";
                }
                text += "\t" + cat.Total() + "\n";
                for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                    Habilidad hab = cat.listaHabilidades.get(j);
                    if (hab.MereceLaPenaImprimir()) {
                        text = text + "  *  " + hab.DevolverNombreTamañoDeterminado(
                                tamañoMaximoNombre + incrementoTamaño - 5);
                        text = text + "\t" + "\t"
                                + hab.DevolverRangos() + "\t" + hab.DevolverValorRangoHabilidad()
                                + "\t" + cat.Total() + "\t"
                                + hab.DevolverBonuses();
                        letra = "";
                        if (hab.historial) {
                            letra += "H";
                        }
                        int bonusTalentos = hab.DevolverBonusTalentos();
                        int siempreTalento = hab.DevolverBonusTemporalTalentos();
                        if (bonusTalentos != 0) {
                            letra += "T";
                            if (siempreTalento > 0) {
                                letra += "*";
                            }
                        }
                        if (Personaje.getInstance().ExisteObjetoModificaHabilidad(hab)) {
                            letra += "O";
                        }
                        if (!letra.equals("")) {
                            text += "(" + letra + ")";
                        }
                        if (hab.DevolverBonusObjetos() > 0 || siempreTalento > 0) {
                            text += "\t" + (hab.Total() - hab.DevolverBonusObjetos() - siempreTalento) + "/" + hab.Total() + "";
                        } else {
                            text += "\t" + hab.Total();
                        }
                        text += "\n";

                        //Mostramos las habilidades especializadas.
                        for (int m = 0; m < hab.RangosGastadosEnEspecializacion(); m++) {
                            text = text + "  *  " + hab.DevolverEspecializacionTamañoDeterminado(
                                    tamañoMaximoNombre + incrementoTamaño, m);
                            text = text + "\t" + "\t"
                                    + hab.DevolverRangosEspecializacion() + "\t" + hab.DevolverValorRangoHabilidadEspecializacion()
                                    + "\t" + cat.Total() + "\t"
                                    + hab.DevolverBonuses();
                            letra = "";
                            if (hab.historial) {
                                letra += "H";
                            }
                            bonusTalentos = hab.DevolverBonusTalentos();
                            siempreTalento = hab.DevolverBonusTemporalTalentos();
                            if (bonusTalentos != 0) {
                                letra += "T";
                                if (siempreTalento > 0) {
                                    letra += "*";
                                }
                            }
                            if (!letra.equals("")) {
                                text += "(" + letra + ")";
                            }
                            if (hab.DevolverBonusObjetos() > 0 || siempreTalento > 0) {
                                text += "\t" + (hab.TotalEspecializacion() - hab.DevolverBonusObjetos() - siempreTalento) + "/" + hab.TotalEspecializacion() + "";
                            } else {
                                text += "\t" + hab.TotalEspecializacion();
                            }
                            text += "\n";
                        }
                    } else {
                        text = text + "";
                    }
                }
            }
        }
        return text;
    }

    public String ExportarATextoEquipo() {
        String text = "";
        text = text.replaceAll("\t", "  ");
        if (Personaje.getInstance().equipo.size() > 0) {
            text = "Equipo:\n";
            text += "--------------------------------------------------\n";
            for (int i = 0; i < Personaje.getInstance().equipo.size(); i++) {
                text += Personaje.getInstance().equipo.get(i) + "\n\n";
            }
            text += "\n";
        }

        if (Personaje.getInstance().objetosMagicos.size() > 0) {
            text = "Objetos:\n";
            text += "--------------------------------------------------\n";
            for (int i = 0; i < Personaje.getInstance().objetosMagicos.size(); i++) {
                ObjetoMagico objeto = Personaje.getInstance().objetosMagicos.get(i);
                if (objeto.DevolverPropiedades().length() > 0) {
                    text += objeto.nombre + ": " + objeto.DevolverPropiedades();
                    text += "\n\n";
                }
            }
            text += "\n";
        }
        return text;
    }

    public String ExportarTRs() {
        String texto = "Modificación a las TR\n";
        texto += "--------------------------------------------------\n";
        texto += "Canalización \t" + (Personaje.getInstance().TrCanalizacion() + 3 * Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("In")) + "\n";
        texto += "Esencia      \t" + (Personaje.getInstance().TrEsencia() + 3 * Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Em")) + "\n";
        texto += "Mentalismo   \t" + (Personaje.getInstance().TrMentalismo() + 3 * Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Pr")) + "\n";
        texto += "Psiónico     \t" + Personaje.getInstance().TrPsionico() + "\n";
        texto += "Veneno       \t" + (Personaje.getInstance().TrVenenos() + 3 * Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Co")) + "\n";
        texto += "Enfermedad   \t" + (Personaje.getInstance().TrEnfermedades() + 3 * Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Co")) + "\n";
        texto += "Miedo        \t" + (Personaje.getInstance().TrMiedo() + 3 * Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Ad")) + "\n";
        texto += "Frío         \t" + Personaje.getInstance().TrFrio() + "\n";
        texto += "Calor        \t" + Personaje.getInstance().TrCalor() + "\n";
        return texto;
    }

    public String ExportarEspeciales() {
        String texto = "Especiales:\n";
        texto += "--------------------------------------------------\n";
        for (int i = 0; i < Personaje.getInstance().especialesRaza.size(); i++) {
            texto += Personaje.getInstance().especialesRaza.get(i) + "\n\n";
        }
        texto = texto.replaceAll("\t", "  ");
        return texto;
    }

    public String ExportarTalentos() {
        String texto = "Talentos:\n";
        texto += "--------------------------------------------------\n";
        for (int i = 0; i < Personaje.getInstance().talentos.size(); i++) {
            Talento talento = Personaje.getInstance().talentos.get(i);
            texto += talento.nombre + ":\t " + talento.listadoCategorias + " ("
                    + talento.Descripcion() + ").\n\n";
        }
        return texto;
    }

    /**
     * Exporta un personaje a txt.
     */
    public boolean ExportarPersonaje(String file) {
        if (file == null || file.equals("")) {
            file = Personaje.getInstance().DevolverNombreCompleto() + "_N"
                    + Personaje.getInstance().nivel + "_" + Personaje.getInstance().raza + "_" + Personaje.getInstance().profesion + ".txt";
        }
        if (!file.endsWith(".txt")) {
            file += ".txt";
        }
        String texto = ExportarATextDetalles() + "\n\n" + ExportarATextoCaracteristicas() + "\n\n"
                + ExportarTRs() + "\n\n"
                + ExportarATextoHabilidades() + "\n\n"
                + ExportarTalentos() + "\n\n"
                + ExportarEspeciales() + "\n\n"
                + ExportarATextoEquipo();
        DirectorioRolemaster.GuardarEnFichero(texto, file);
        return true;
    }

    private String DevolverCodigoTamaño() {
        if (Personaje.getInstance().tamaño.equals("Muy Pequeño")) {
            return "MP";
        }
        if (Personaje.getInstance().tamaño.equals("Pequeño")) {
            return "P";
        }
        if (Personaje.getInstance().tamaño.equals("Mediano") || Personaje.getInstance().tamaño.equals("Normal") || Personaje.getInstance().tamaño.equals("Medio")) {
            return "M";
        }
        if (Personaje.getInstance().tamaño.equals("Grande")) {
            return "G";
        }
        if (Personaje.getInstance().tamaño.equals("Enorme") || Personaje.getInstance().tamaño.equals("Muy Grande")) {
            return "EN";
        }
        return "";
    }

    private Habilidad BuscarMejorArma() {
        int maximo = 0;
        Habilidad maximoAtaque = null;
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            if (cat.DevolverNombre().equals("Armas·2manos")
                    || cat.DevolverNombre().equals("Armas·Contundentes")
                    || cat.DevolverNombre().equals("Armas·Asta")
                    || cat.DevolverNombre().equals("Armas·Filo")) {
                for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                    Habilidad hab = cat.listaHabilidades.get(j);
                    if (maximoAtaque == null) {
                        maximoAtaque = hab;
                    } else {
                        if (hab.Total() > maximoAtaque.Total()) {
                            maximoAtaque = hab;
                        }
                    }
                }
            }
        }
        return maximoAtaque;
    }

    private String DevolverCodigoAtaque(Habilidad hab) {
        if (hab.DevolverNombre().equals("Arma de Asta")) {
            return "aa";
        }
        if (hab.DevolverNombre().equals("Jabalina")) {
            return "la";
        }
        if (hab.DevolverNombre().equals("Lanza")) {
            return "ja";
        }
        if (hab.DevolverNombre().equals("Lanza de Caballería")) {
            return "lc";
        }
        if (hab.DevolverNombre().equals("Cayado")) {
            return "ca";
        }
        if (hab.DevolverNombre().equals("Espadón")) {
            return "e2";
        }
        if (hab.DevolverNombre().equals("Hacha de Batalla")) {
            return "hb";
        }
        if (hab.DevolverNombre().equals("Mangual")) {
            return "mg";
        }
        if (hab.DevolverNombre().equals("Pica de Guerra")) {
            return "pc";
        }
        if (hab.DevolverNombre().equals("Boleadoras")) {
            return "bo";
        }
        if (hab.DevolverNombre().equals("Daga Arrojadiza")) {
            return "daA";
        }
        if (hab.DevolverNombre().equals("Estrella de la Mañana")) {
            return "em";
        }
        if (hab.DevolverNombre().equals("Garrote")) {
            return "ga";
        }
        if (hab.DevolverNombre().equals("Látigo")) {
            return "lg";
        }
        if (hab.DevolverNombre().equals("Martillo de Guerra")) {
            return "mr";
        }
        if (hab.DevolverNombre().equals("Maza")) {
            return "ma";
        }
        if (hab.DevolverNombre().equals("Alfanje")) {
            return "af";
        }
        if (hab.DevolverNombre().equals("Cimitarra")) {
            return "ci";
        }
        if (hab.DevolverNombre().equals("Daga")) {
            return "da";
        }
        if (hab.DevolverNombre().equals("Espada")) {
            return "ea";
        }
        if (hab.DevolverNombre().equals("Espada Corta")) {
            return "ec";
        }
        if (hab.DevolverNombre().equals("Estoque")) {
            return "et";
        }
        if (hab.DevolverNombre().equals("Hacha de Mano")) {
            return "hm";
        }
        if (hab.DevolverNombre().equals("Main Gauche")) {
            return "mg";
        }
        if (hab.DevolverNombre().equals("Sable")) {
            return "sb";
        }
        if (hab.DevolverNombre().equals("Arco Compuesto")) {
            return "am";
        }
        if (hab.DevolverNombre().equals("Arco Corto")) {
            return "ac";
        }
        if (hab.DevolverNombre().equals("Arco Largo")) {
            return "al";
        }
        if (hab.DevolverNombre().equals("Ballesta Ligera")) {
            return "ba";
        }
        if (hab.DevolverNombre().equals("Ballesta Pesada")) {
            return "bp";
        }
        if (hab.DevolverNombre().equals("Honda")) {
            return "ho";
        }
        if (hab.DevolverNombre().equals("Barridos Grado 1")) {
            return "bar1";
        }
        if (hab.DevolverNombre().equals("Barridos Grado 2")) {
            return "bar2";
        }
        if (hab.DevolverNombre().equals("Barridos Grado 3")) {
            return "bar3";
        }
        if (hab.DevolverNombre().equals("Barridos Grado 4")) {
            return "bar4";
        }
        if (hab.DevolverNombre().equals("Golpes Grado 1")) {
            return "go1";
        }
        if (hab.DevolverNombre().equals("Golpes Grado 2")) {
            return "go2";
        }
        if (hab.DevolverNombre().equals("Golpes Grado 3")) {
            return "go3";
        }
        if (hab.DevolverNombre().equals("Golpes Grado 4")) {
            return "go4";
        }
        if (hab.DevolverNombre().equals("Boxeo")) {
            return "bx";
        }
        if (hab.DevolverNombre().equals("Placaje")) {
            return "pj";
        }
        if (hab.DevolverNombre().equals("Ataque Racial: Extremidades")) {
            return DevolverCodigoTamaño() + "Ga";
        }
        if (hab.DevolverNombre().equals("Ataque Racial: Boca")) {
            return DevolverCodigoTamaño() + "Mo";
        }
        if (hab.DevolverNombre().equals("Ataque Racial: Otros")) {
            return "??";
        }
        return "";
    }

    private Habilidad BuscarMejorDisparo() {
        int maximo = 0;
        Habilidad maximoAtaque = null;
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            if (cat.DevolverNombre().equals("Armas·Proyectiles")
                    || cat.DevolverNombre().equals("Armas·Arrojadizas")
                    || cat.DevolverNombre().equals("Armas·Fuego")) {
                for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                    Habilidad hab = cat.listaHabilidades.get(j);
                    if (maximoAtaque == null) {
                        maximoAtaque = hab;
                    } else {
                        if (hab.Total() > maximoAtaque.Total()) {
                            maximoAtaque = hab;
                        }
                    }
                }
            }
        }
        return maximoAtaque;
    }

    private Habilidad BuscarMejorAtaque() {
        int maximo = 0;
        Habilidad maximoAtaque = null;
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            if (cat.DevolverNombre().equals("Artes Marciales·Golpes")
                    || cat.DevolverNombre().equals("Artes Marciales·Barridos")
                    || cat.DevolverNombre().equals("Ataques Especiales")) {
                for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                    Habilidad hab = cat.listaHabilidades.get(j);
                    if (maximoAtaque == null) {
                        maximoAtaque = hab;
                    } else {
                        if (hab.Total() > maximoAtaque.Total()) {
                            maximoAtaque = hab;
                        }
                    }
                }
            }
        }
        return maximoAtaque;
    }

    private String ObtenerCodigoVelocidad() {
        int rapidez = Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Rp");
        if (rapidez <= -14) {
            return "IM";
        }
        if (rapidez <= -10) {
            return "AR";
        }
        if (rapidez <= -6) {
            return "ML";
        }
        if (rapidez <= -2) {
            return "L";
        }
        if (rapidez <= 2) {
            return "N";
        }
        if (rapidez <= 6) {
            return "MdR";
        }
        if (rapidez <= 10) {
            return "R";
        }
        if (rapidez <= 14) {
            return "MR";
        }
        return "RS";
    }

    /**
     * Genera una version del personaje del mismo método que en el criaturas y
     * tesoros.
     */
    private String GenerarCaracteristicasTipoMonstruo() {
        Habilidad habCC = BuscarMejorArma();
        Habilidad habProy = BuscarMejorDisparo();
        Habilidad habAtaq = BuscarMejorAtaque();
        String vel = ObtenerCodigoVelocidad();
        int PV = Personaje.getInstance().DevolverHabilidadDeNombre("Desarrollo Físico").Total();
        String texto = "Raza\tNivel\tMov.\tMM\tVM/VA\tTam\tPV\tTA(BD)\tAtaques\n";
        texto += Personaje.getInstance().raza + "\t" + Personaje.getInstance().nivel + "\t" + Personaje.getInstance().DevolverCapacidadMovimiento() + "\t"
                + Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Ag") * 3 + "\t"
                + vel + "\t" + DevolverCodigoTamaño() + "\t" + PV + "\t "
                + Personaje.getInstance().DevolverTA() + " (" + Personaje.getInstance().DevolverBD() + ")\t"
                + habCC.Total() + DevolverCodigoAtaque(habCC);
        if (habProy.Total() > 0) {
            texto += "/" + habProy.Total() + DevolverCodigoAtaque(habProy);
        }
        if (habAtaq.Total() > 0) {
            texto += "/" + habAtaq.Total() + DevolverCodigoAtaque(habAtaq) + " \n";
        }
        return texto;
    }

    private String GenerarAbreviaturaHabilidades() {
        String texto = "";
        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            Categoria cat = Personaje.getInstance().categorias.get(i);
            texto += cat.DevolverAbreviatura() + " " + cat.Total();
            int añadidas = 0;
            for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                Habilidad hab = cat.listaHabilidades.get(j);
                if (hab.DevolverRangos() > 0) {
                    if (añadidas == 0) {
                        texto += " (";
                    }
                    if (añadidas > 0) {
                        texto += ", ";
                    }
                    texto += hab.DevolverNombre() + " " + hab.Total();
                    añadidas++;
                }
            }
            if (añadidas > 0) {
                texto += ")";
            }
            if (i < Personaje.getInstance().categorias.size() - 1) {
                texto += ", ";
            }
            if (i == Personaje.getInstance().categorias.size() - 1) {
                texto += ".";
            }

        }
        return texto;
    }

    public boolean ExportarAbreviaturaPersonaje(String file) {
        if (file == null || file.equals("")) {
            file = Personaje.getInstance().DevolverNombreCompleto() + "_N"
                    + Personaje.getInstance().nivel + "_" + Personaje.getInstance().raza + "_" + Personaje.getInstance().profesion + ".txt";
        }
        if (!file.endsWith(".txt")) {
            file += ".txt";
        }
        String texto = Personaje.getInstance().DevolverNombreCompleto() + "\n"
                + GenerarCaracteristicasTipoMonstruo() + "\n"
                + "HABILIDADES: \n"
                + GenerarAbreviaturaHabilidades();
        DirectorioRolemaster.GuardarEnFichero(texto, file);
        return true;
    }
}

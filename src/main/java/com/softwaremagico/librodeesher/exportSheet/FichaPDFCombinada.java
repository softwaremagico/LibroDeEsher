/*
 *
 This software is designed by Jorge Hortelano Otero.
 softwaremagico@gmail.com
 Copyright (C) 2010 Jorge Hortelano Otero.
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
 Created on december of 2010.
 */
package com.softwaremagico.librodeesher.exportSheet;
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

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.Categoria;
import com.softwaremagico.librodeesher.Habilidad;
import com.softwaremagico.librodeesher.Personaje;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class FichaPDFCombinada extends FichaPDF {

    private int lines = 0;
    private int listada = 0;
    private int maxLines = 71;
    int column = 0;
    static int fontSize = 6;
    private int page = 1;
    private PdfPTable tableColumn = new PdfPTable(1);
    float[] mainWidths = {0.45f, 0.45f};
    PdfPTable table = new PdfPTable(mainWidths);
    private int categoriasAMostrar = 0;
    private int habilidadesAMostrar = 0;
    private int categoriasSinHabilidades = 0;
    private int lineasFaltan = 0;
    private int paginas = 0;
    private int paginasEscritas = 0;

    FichaPDFCombinada(String path) throws Exception {
        completar = true;
        PersonajePDFCom(path);
    }

    /**
     * Crea una hoja de personajes en formato PDF.
     */
    private void PersonajePDFCom(String path) throws Exception {
        Document document = new Document(PageSize.A4);

        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setTotalWidth(document.getPageSize().getWidth() - 60);

        if (!path.contains("_Comb")) {
            path += "_Comb";
        }

        if (!path.endsWith(".pdf")) {
            path += ".pdf";
        }
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            GeneratePDF(document, writer);
            MessageManager.infoMessage("Ficha creada correctamente.", "Exportar a PDF");
        } catch (FileNotFoundException fnfe) {
            MessageManager.basicErrorMessage("Creación de PDF fallida.", "Exportar a PDF");
            MessageManager.errorMessage(fnfe);
        }
    }

    private void GeneratePDF(Document document, PdfWriter writer) throws Exception {
        String font = FontFactory.HELVETICA;

        doscaras = (Personaje.getInstance().talentos.size() > 0 || Personaje.getInstance().especialesRaza.size() > 0
                || Personaje.getInstance().objetosMagicos.size() > 0 || Personaje.getInstance().equipo.size() > 0);

        ContarLineas();

        DocumentData(document, writer);
        document.open();
        PersonajePagina1PDF(document, writer, font);
        if (doscaras) {
            PersonajePagina1bPDF(document, writer, font);
        }
        PersonajePagina2PDF(document, writer, font);
        if (doscaras) {
            //PersonajePaginaVacia(document, writer, font);
        }
        document.close();
    }

    private void PersonajePagina2PDF(Document document, PdfWriter writer, String font) throws Exception {
        document.newPage();
        AddImagenFondo(document, RolemasterFolderStructure.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "RMHPComb.png");
        /*AddTablaNombreCat(document, writer, font);
         AddTablaValoresCategorias(document, writer, font);*/
        AddHabilidadesCombinadasPage(document, writer, font);
    }

    private PdfPCell CategoriaCell(PdfWriter writer, Document document, Categoria cat, String font)
            throws MalformedURLException, BadElementException, IOException, DocumentException {
        Paragraph p;
        PdfPCell cell;
        float[] widths = {0.23f, 0.23f, 0.14f, 0.11f, 0.13f, 0.16f};
        PdfPTable tableCat = new PdfPTable(widths);
        tableCat.flushContent();

        p = new Paragraph(cat.DevolverNombreTamañoDeterminado(35).toUpperCase(), FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        p = new Paragraph(cat.GenerarCadenaCaracteristicas(), FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        p = new Paragraph(cat.GenerarCadenaCosteRangos(), FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        p = new Paragraph("Rang: " + cat.DevolverAntiguosRangos() + "", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);


        if (cat.TipoCategoria().equals("Estándar")) {
            cell = new PdfPCell(cat.DevolverCuadradosNuevosRangos(25));
        } else {
            String texto;
            if (cat.TipoCategoria().equals("Combinada")) {
                texto = "*";
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
            }
            if (cat.TipoCategoria().equals("Limitada") || cat.TipoCategoria().equals("Especial")
                    || cat.TipoCategoria().equals("DPP") || cat.TipoCategoria().equals("DF")) {
                texto = "+";
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
            }
        }

        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderWidth(0);
        tableCat.addCell(cell);


        /**
         * SEGUNDA LINEA *
         */
        p = new Paragraph("Bonif. Rango: " + cat.DevolverValorRangoCategoria(), FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        p = new Paragraph("Caract: " + cat.DevolverValorCaracteristicas(), FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        String texto = cat.DevolverBonusEspecialesCategoria() + "";
        String letra = "";
        if (cat.historial) {
            letra += "H";
        }
        int bonusTalentos = cat.DevolverBonusTalentos();
        int siempreTalento = cat.DevolverBonusTalentosEspecial();
        if (bonusTalentos != 0) {
            letra += "T";
            if (siempreTalento > 0) {
                letra += "*";
            }
        }

        if (!letra.equals("")) {
            texto += "(" + letra + ")";
        }

        p = new Paragraph("Esp: " + texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        p = new Paragraph("Prof: " + cat.DevolverBonusProfesion(), FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        p = new Paragraph("Obj: " + cat.DevolverBonusObjetos(), FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        p = new Paragraph("Total: " + cat.Total(), FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableCat.addCell(cell);

        lines += 3;

        cell = new PdfPCell(tableCat);
        cell.setBorderWidth(1);
        cell.setColspan(1);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableColumn.addCell(cell);

        if (NecesitaNuevaColumna(document, writer, font)) {
            tableColumn = new PdfPTable(1);
            tableColumn.flushContent();
        }

        categoriasAMostrar--;
        return cell;
    }

    private PdfPTable TituloHabilidades(String font) throws BadElementException, MalformedURLException, IOException, DocumentException {
        Paragraph p;
        PdfPCell cell;
        float[] widths = {0.32f, 0.05f, 0.15f, 0.08f, 0.10f, 0.10f, 0.10f, 0.10f};
        PdfPTable tableHab = new PdfPTable(widths);
        tableHab.flushContent();


        p = new Paragraph("Habilidad:", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);


        p = new Paragraph("#", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("Rng:", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("Cat:", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("Obj:", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("Esp:", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("Total:", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        return tableHab;
    }

    private void ListadoHabilidades(Document document, PdfWriter writer, Categoria cat, String font, boolean ultima)
            throws BadElementException, MalformedURLException, IOException, DocumentException {
        PdfPCell cell;
        Habilidad hab;
        PdfPTable tableHab;
        int añadidas = 0;
        listada = 0;
        boolean ultimaCompleta = false;

        /* TITULO */
        tableHab = TituloHabilidades(font);
        cell = new PdfPCell(tableHab);
        cell.setBorderWidth(0);
        tableColumn.addCell(cell);
        lines++;

        if (NecesitaNuevaColumna(document, writer, font)) {
            tableColumn = new PdfPTable(1);
            tableColumn.flushContent();
        }

        /* LISTA HABILIDADES */
        for (int i = 0; i < cat.listaHabilidades.size(); i++) {
            hab = cat.listaHabilidades.get(i);
            if (hab.MereceLaPenaImprimir()) {
                tableHab = LineaHabilidad(hab, font);
                cell = new PdfPCell(tableHab);
                cell.setBorderWidth(0);
                tableColumn.addCell(cell);
                lines++;
                añadidas++;
                habilidadesAMostrar--;

                if (NecesitaNuevaColumna(document, writer, font)) {
                    tableColumn = new PdfPTable(1);
                    tableColumn.flushContent();
                }
            }
        }

        /* ULTIMAS LINEAS */
        if (lines > maxLines - 5) {
            for (int i = lines; i <= maxLines; i++) {
                tableHab = ListadoHabilidadVacia(cat, font);
                cell = new PdfPCell(tableHab);
                cell.setBorderWidth(0);
                tableColumn.addCell(cell);
                lines++;
                añadidas++;
                lineasFaltan--;
            }
            if (NecesitaNuevaColumna(document, writer, font)) {
                tableColumn = new PdfPTable(1);
                tableColumn.flushContent();
            }
            if (ultima) {
                ultimaCompleta = true;
            }
        }

        /* AL MENOS UNA HABILIDAD POR CATEGORIA */
        if (añadidas == 0) {
            tableHab = ListadoHabilidadVacia(cat, font);
            cell = new PdfPCell(tableHab);
            cell.setBorderWidth(0);
            tableColumn.addCell(cell);
            lines++;
            añadidas++;
            if (NecesitaNuevaColumna(document, writer, font)) {
                tableColumn = new PdfPTable(1);
                tableColumn.flushContent();
            }
            categoriasSinHabilidades--;
        }

        /* REPARTIMOS LAS LINEAS QUE SOBRAN POR LAS DISTINTAS CATEGORIAS*/
        if (lineasFaltan > 0 && categoriasAMostrar > 0) {
            int añadir = lineasFaltan / categoriasAMostrar;
            for (int i = 0; i < añadir; i++) {
                tableHab = ListadoHabilidadVacia(cat, font);
                cell = new PdfPCell(tableHab);
                cell.setBorderWidth(0);
                tableColumn.addCell(cell);
                lines++;
                añadidas++;
                lineasFaltan--;
                if (NecesitaNuevaColumna(document, writer, font)) {
                    tableColumn = new PdfPTable(1);
                    tableColumn.flushContent();
                }
            }
        }

        /* ULTIMA CATEGORIA */
        if (lines < maxLines && ultima && !ultimaCompleta) {
            for (int j = column; j < 2; j++) {
                for (int i = lines; i <= maxLines; i++) {
                    tableHab = ListadoHabilidadVacia(cat, font);
                    cell = new PdfPCell(tableHab);
                    cell.setBorderWidth(0);
                    tableColumn.addCell(cell);
                    lines++;
                    añadidas++;
                    lineasFaltan--;
                }
                if (NecesitaNuevaColumna(document, writer, font)) {
                    tableColumn = new PdfPTable(1);
                    tableColumn.flushContent();
                }
            }
        }
        habilidadesAMostrar--;
    }

    private PdfPTable LineaHabilidad(Habilidad hab, String font)
            throws BadElementException, MalformedURLException, IOException, DocumentException {
        Paragraph p;
        PdfPCell cell;
        BaseColor background;


        if (listada % 2 == 1) {
            background = BaseColor.WHITE;
        } else {
            background = BaseColor.LIGHT_GRAY;
        }
        listada++;


        float[] widths = {0.32f, 0.05f, 0.15f, 0.08f, 0.10f, 0.10f, 0.10f, 0.10f};
        PdfPTable tableHab = new PdfPTable(widths);
        tableHab.flushContent();

        p = new Paragraph(hab.DevolverNombreTamañoDeterminado(32), FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        //cell.setBorderWidth(0);
        if (column == 0) {
            cell.setBorderWidthLeft(1);
        } else {
            cell.setBorderWidthLeft(0);
        }
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph(hab.DevolverAntiguosRangos() + "", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);


        cell = new PdfPCell(hab.DevolverCuadradosNuevosRangos(25));
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHab.addCell(cell);


        p = new Paragraph(hab.DevolverValorRangoHabilidad() + "", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph(hab.categoriaPadre.Total() + "", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph(hab.DevolverBonusObjetos() + "", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);


        String texto = hab.DevolverBonusEspeciales() + "";

        String letra = "";
        if (hab.historial) {
            letra += "H";
        }
        if (hab.ExisteBonusEspecialRaza()) {
            letra += "R";
        }

        int bonusTalentos = hab.DevolverBonusTalentos();
        int siempreTalento = hab.DevolverBonusTemporalTalentos();
        if (bonusTalentos != 0) {
            letra += "T";
            if (siempreTalento > 0) {
                letra += "*";
            }
        }
        if (!letra.equals("")) {
            texto += "(" + letra + ")";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHab.addCell(cell);

        if (hab.DevolverBonusObjetos() > 0 || siempreTalento > 0) {
            texto = hab.Total() - hab.DevolverBonusObjetos() - siempreTalento + "/" + hab.Total() + "";
        } else {
            texto = hab.Total() + "";
        }

        p = new Paragraph(texto + "", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        if (column == 1) {
            cell.setBorderWidthRight(1);
        } else {
            cell.setBorderWidthRight(0);
        }
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        return tableHab;
    }

    private PdfPTable ListadoHabilidadVacia(Categoria cat, String font)
            throws BadElementException, MalformedURLException, IOException, DocumentException {
        Paragraph p;
        PdfPCell cell;
        BaseColor background;

        float[] widths = {0.31f, 0.06f, 0.15f, 0.08f, 0.10f, 0.10f, 0.10f, 0.10f};

        if (listada % 2 == 1) {
            background = BaseColor.WHITE;
        } else {
            background = BaseColor.LIGHT_GRAY;
        }
        listada++;

        PdfPTable tableHab = new PdfPTable(widths);
        tableHab.flushContent();

        p = new Paragraph("________________________", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        if (column == 0) {
            cell.setBorderWidthLeft(1);
        } else {
            cell.setBorderWidthLeft(0);
        }
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("___", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        Image image = Image.getInstance(RolemasterFolderStructure.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros0.png");
        image.scalePercent(25);

        cell = new PdfPCell(image);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHab.addCell(cell);


        p = new Paragraph("__", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph(cat.Total() + "", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("___", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        p = new Paragraph("___", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        tableHab.addCell(cell);


        p = new Paragraph("___", FontFactory.getFont(font, fontSize - 1));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(10);
        if (column == 1) {
            cell.setBorderWidthRight(1);
        } else {
            cell.setBorderWidthRight(0);
        }
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingLeft(5f);
        tableHab.addCell(cell);

        return tableHab;
    }

    private PdfPCell CabeceraIzquierda() {
        PdfPCell cell;
        Font f = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, fontSize + 9);
        f.setColor(BaseColor.WHITE);

        Paragraph p = new Paragraph("Hoja Combinada de Habilidades (" + page + " de " + paginas + ")", f);
        //Paragraph p = new Paragraph("Hoja Combinada de Habilidades (" + page + ")", f);
        cell = new PdfPCell(p);
        cell.setMinimumHeight(30);
        cell.setBackgroundColor(BaseColor.BLACK);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingLeft(5f);


        return cell;
    }

    private PdfPCell CabeceraDerecha() {
        PdfPTable tableC = new PdfPTable(1);
        tableC.flushContent();
        PdfPCell cell;

        Paragraph p = new Paragraph("Personaje: " + Personaje.getInstance().DevolverNombreCompleto(), FontFactory.getFont(FontFactory.HELVETICA, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingLeft(5f);
        tableC.addCell(cell);

        p = new Paragraph("Jugador: ___________________________________", FontFactory.getFont(FontFactory.HELVETICA, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(15);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5f);
        tableC.addCell(cell);

        cell = new PdfPCell(tableC);
        return cell;
    }

    private PdfPCell Cabecera() {
        float[] widths = {0.50f, 0.50f};
        PdfPTable tableC = new PdfPTable(widths);
        tableC.flushContent();
        PdfPCell cell;

        cell = new PdfPCell(CabeceraIzquierda());
        cell.setBorderWidth(1);
        cell.setColspan(1);
        tableC.addCell(cell);

        cell = new PdfPCell(CabeceraDerecha());
        cell.setBorderWidth(1);
        cell.setColspan(1);
        tableC.addCell(cell);

        cell = new PdfPCell(tableC);
        return cell;
    }

    private void AddHabilidadesCombinadasPage(Document document, PdfWriter writer, String font)
            throws MalformedURLException, BadElementException, IOException, DocumentException {
        PdfPCell cell;
        Categoria cat = null;

        cell = new PdfPCell(Cabecera());
        cell.setColspan(2);
        table.addCell(cell);

        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            cat = Personaje.getInstance().categorias.get(i);
            if (cat.MereceLaPenaImprimir()) {
                CategoriaCell(writer, document, cat, font);
                ListadoHabilidades(document, writer, cat, font, i == Personaje.getInstance().categorias.size() - 1);
            }
        }

        /* IMPRIMIR LA ULTIMA COLUMNA DE CATEGORIAS */
        /* cell = new PdfPCell(tableColumn);
         cell.setBorderWidth(1);
         cell.setColspan(1);
         //cell.setMinimumHeight(document.getPageSize().getWidth() - 60);
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cell);
         tableColumn = new PdfPTable(1);
         tableColumn.flushContent();*/

        if (column == 0) {
            /* AÑADIR COLUMNA DERECHA VACIA SI ES NECESARIO */
            cell = new PdfPCell();
            cell.setBorderWidth(1);
            cell.setColspan(1);
            cell.setMinimumHeight(document.getPageSize().getWidth() - 60);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        CerrarPagina(document, writer, font);
    }

    private void AñadirNuevaPaginaHabilidades(Document document, PdfWriter writer, String font)
            throws BadElementException, MalformedURLException, IOException, DocumentException {
        PdfPCell cell;

        //Cerramos pagina anterior.
        CerrarPagina(document, writer, font);
        paginasEscritas++;

        //Generamos una nueva.
        float[] widths = {0.45f, 0.45f};
        table = new PdfPTable(widths);
        table.flushContent();
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setTotalWidth(document.getPageSize().getWidth() - 60);

        document.newPage();
        page++;
        try {
            AddImagenFondo(document, RolemasterFolderStructure.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "RMHPComb.png");
        } catch (DocumentException ex) {
            Logger.getLogger(FichaPDFCombinada.class.getName()).log(Level.SEVERE, null, ex);
        }

        cell = new PdfPCell(Cabecera());
        cell.setColspan(2);
        table.addCell(cell);

        column = 0;
    }

    private boolean NecesitaNuevaColumna(Document document, PdfWriter writer, String font)
            throws BadElementException, MalformedURLException, IOException, DocumentException {
        PdfPCell cell;
        if (lines > maxLines) {
            cell = new PdfPCell(tableColumn);
            cell.setBorderWidth(1);
            cell.setColspan(1);
            cell.setMinimumHeight(document.getPageSize().getWidth() - 60);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            lines = 0;
            column++;

            tableColumn = new PdfPTable(1);
            tableColumn.flushContent();

            if (column > 1) {
                if (paginasEscritas < paginas - 1) {
                    AñadirNuevaPaginaHabilidades(document, writer, font);
                }
            }
            return true;
        }
        return false;
    }

    private void CerrarPagina(Document document, PdfWriter writer, String font) {
        PdfPCell cell = new PdfPCell(CrearFirma(font, fontSize));
        cell.setBorderWidth(0);
        cell.setColspan(2);
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(cell);
        table.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37, writer.getDirectContent());
    }

    private void ContarLineas() {
        Categoria cat;
        Habilidad hab;
        int añadidas;
        int lineasPredecidas = 0;


        for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
            cat = Personaje.getInstance().categorias.get(i);
            if (cat.MereceLaPenaImprimir()) {
                categoriasAMostrar++;

                añadidas = 0;
                for (int j = 0; j < cat.listaHabilidades.size(); j++) {
                    hab = cat.listaHabilidades.get(j);
                    if (hab.MereceLaPenaImprimir()) {
                        habilidadesAMostrar++;
                        añadidas++;
                    }
                }

                if (añadidas == 0) {
                    categoriasSinHabilidades++;
                }

            }
        }
        lineasPredecidas = categoriasAMostrar * 4 + habilidadesAMostrar + categoriasSinHabilidades;
        lineasFaltan = (maxLines * 2) - (lineasPredecidas % (maxLines * 2));
        paginas = (int) Math.ceil((double) lineasPredecidas / (double) (maxLines * 2));
    }
}

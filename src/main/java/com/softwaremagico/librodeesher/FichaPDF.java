/*
 *
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
 Created on february of 2008.
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

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwaremagico.files.DirectorioRolemaster;
import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 *
 * @author jorge
 */
public class FichaPDF {

    boolean completar;
    boolean doscaras;

    FichaPDF() {
    }

    FichaPDF(boolean enblanco) throws Exception {
        if (enblanco) {
            completar = false;
            PersonajeHojaBlanco();
        } else {
            completar = true;
            PersonajePDF();
        }
    }

    FichaPDF(boolean enblanco, String path) throws Exception {
        if (enblanco) {
            PersonajeHojaBlanco(path);
            completar = false;
        } else {
            completar = true;
            PersonajePDF(path);
        }
    }

    /**
     * Crea una hoja de personajes en formato PDF.
     */
    public void PersonajePDF() throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Esher.ArchivoDefectoGuardar() + ".pdf"));
        GeneratePDF(document, writer);
    }

    /**
     * Crea una hoja de personajes en formato PDF.
     */
    public void PersonajePDF(String path) throws Exception {
        Document document = new Document(PageSize.A4);
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

        DocumentData(document, writer);
        document.open();
        PersonajePagina1PDF(document, writer, font);
        if (doscaras) {
            PersonajePagina1bPDF(document, writer, font);
        }
        PersonajePagina2PDF(document, writer, font);
        if (doscaras) {
            PersonajePaginaVacia(document, writer, font);
        }
        PersonajePagina3PDF(document, writer, font);
        if (doscaras) {
            PersonajePaginaVacia(document, writer, font);
        }
        document.close();
    }

    private void PersonajeHojaBlanco() throws Exception {
        String font = FontFactory.HELVETICA;

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RMFesher.pdf"));
        DocumentData(document, writer);
        document.open();
        PersonajePagina1PDF(document, writer, font);
        PersonajePagina2PDF(document, writer, font);
        PersonajePagina3PDF(document, writer, font);
        document.close();
    }

    private void PersonajeHojaBlanco(String path) throws Exception {
        String font = FontFactory.HELVETICA;

        if (!path.endsWith(".pdf")) {
            if ((new File(path)).exists()) {
                path += File.pathSeparator + "RMFesher.pdf";
            } else {
                path = "RMFesher.pdf";
            }
        }
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        DocumentData(document, writer);
        document.open();
        PersonajePagina1PDF(document, writer, font);
        PersonajePagina2PDF(document, writer, font);
        PersonajePagina3PDF(document, writer, font);
        document.close();
    }

    Document DocumentData(Document document, PdfWriter writer) {
        document.addTitle("Ficha Personaje Rolemaster");
        document.addAuthor("Software Magico");
        document.addCreator("Libro de Esher - Generador de PJs y PNJs para Rolemaster");
        document.addSubject("Pagina de PJ para Rolemaster");
        document.addKeywords("Rolemaster, PJ, PNJ, Libro de Esher");
        document.addCreationDate();

        return document;
    }

    void AddImagenFondo(Document document, String imagen) throws BadElementException,
            DocumentException, MalformedURLException, IOException {
        Image png;

        png = Image.getInstance(imagen);
        png.setAlignment(Image.MIDDLE | Image.UNDERLYING);
        png.scaleToFit((float) 760, (float) 760);
        document.add(png);
    }

    private void AddTablaNombreCat(Document document, PdfWriter writer, String font) {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorderWidth(0);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        int fontSize = 8;
        table.setTotalWidth(document.getPageSize().getWidth() - 500);

        Paragraph p;
        if (completar) {
            p = new Paragraph(Personaje.getInstance().DevolverNombreCompleto(), FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize + 2));
        }
        PdfPCell cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        table.addCell(cell);

        table.writeSelectedRows(0, -1, 3 * document.getPageSize().getWidth() / 4 + 20,
                document.getPageSize().getHeight() - 45, writer.getDirectContent());
        table.flushContent();
    }

    private void AddTablaValoresCategorias(Document document, PdfWriter writer, String font)
            throws DocumentException, MalformedURLException, IOException {
        float[] widths = {0.23f, 0.09f, 0.07f, 0.07f, 0.085f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f};
        PdfPTable table = new PdfPTable(widths);
        table.getDefaultCell().setBorderWidth(0);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setTotalWidth(document.getPageSize().getWidth() - 65);
        int fontSize = 6;
        String texto;
        PdfPCell cell;
        Categoria cat = null;
        int omitidas = 0;

        //Añadimos las categorias * añadimos espacios para nuevas..
        for (int i = 0; i < 60 + omitidas; i++) {
            if (i < Personaje.getInstance().categorias.size()) {
                cat = Personaje.getInstance().categorias.get(i);
            }

            if (cat.MereceLaPenaImprimir() || i >= Personaje.getInstance().categorias.size()) {

                //Generamos una fila de categoria.
                if (i < Personaje.getInstance().categorias.size()) {
                    texto = cat.DevolverNombre();
                } else {
                    texto = "_______________________";
                }
                Paragraph p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setMinimumHeight(11 + (i - omitidas) % 2);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPaddingLeft(5f);
                table.addCell(cell);

                if (i < Personaje.getInstance().categorias.size()) {
                    texto = cat.GenerarCadenaCaracteristicas();
                } else {
                    texto = "_______";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                if (completar && i < Personaje.getInstance().categorias.size()) {
                    texto = cat.GenerarCadenaCosteRangos();
                } else {
                    texto = "_________";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                if (i < Personaje.getInstance().categorias.size()) {
                    if (cat.TipoCategoria().equals("Estándar")) {
                        if (completar) {
                            texto = cat.DevolverAntiguosRangos() + "";
                        } else {
                            texto = "_____";
                        }
                    } else {
                        texto = "na";
                    }
                } else {
                    texto = "_____";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);


                if (i < Personaje.getInstance().categorias.size()) {
                    if (cat.TipoCategoria().equals("Estándar")) {
                        Image image;
                        if (!completar) {
                            image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros0.png");
                            image.scalePercent(28);
                        } else {
                            image = cat.DevolverCuadradosNuevosRangos(28);
                        }
                        cell = new PdfPCell(image);
                    }

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
                } else {
                    Image image;
                    image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros0.png");
                    image.scalePercent(28);
                    cell = new PdfPCell(image);
                }
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                if (completar && i < Personaje.getInstance().categorias.size()) {
                    texto = cat.DevolverValorRangoCategoria() + "";
                } else {
                    texto = "_____";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                if (completar && i < Personaje.getInstance().categorias.size()) {
                    texto = cat.DevolverValorCaracteristicas() + "";
                } else {
                    texto = "_____";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                if (completar && i < Personaje.getInstance().categorias.size()) {
                    texto = cat.DevolverBonuses() - cat.DevolverBonusEspecialesCategoria()
                            - cat.DevolverBonusObjetos() + "";
                } else {
                    texto = "_____";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                if (completar && i < Personaje.getInstance().categorias.size()) {
                    texto = cat.DevolverBonusEspecialesCategoria() + "";
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

                    if (cat.DevolverBonusTalentos() > 0) {
                        letra += "T";
                    }

                    if (!letra.equals("")) {
                        texto += "(" + letra + ")";
                    }
                } else {
                    texto = "_____";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                // Objetos mágicos.
                if (completar && i < Personaje.getInstance().categorias.size()) {
                    texto = cat.DevolverBonusObjetos() + "";
                } else {
                    texto = "_____";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                if (completar && i < Personaje.getInstance().categorias.size()) {
                    texto = cat.Total() + "";
                } else {
                    texto = "_____";
                }
                p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            } else {
                omitidas++;
            }
        }

        cell = new PdfPCell(CrearFirma(font, fontSize + 1));
        cell.setBorderWidth(0);
        cell.setColspan(11);
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(cell);


        table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 97, writer.getDirectContent());
        table.flushContent();
    }

    private void AddTablaNombreHabilidades(Document document, PdfWriter writer, String font) {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorderWidth(0);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        int fontSize = 8;
        table.setTotalWidth(document.getPageSize().getWidth() - 500);

        Paragraph p;
        if (completar) {
            p = new Paragraph(Personaje.getInstance().DevolverNombreCompleto(), FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize));
        }
        PdfPCell cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        table.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().nivel + "", FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        table.addCell(cell);

        table.writeSelectedRows(0, -1, 3 * document.getPageSize().getWidth() / 4 - 80,
                document.getPageSize().getHeight() - 65, writer.getDirectContent());
    }

    private void AddLineaHabilidad(Habilidad hab, String font, int fontSize, PdfPTable table, int line)
            throws BadElementException, MalformedURLException, IOException {
        String texto;
        PdfPCell cell;

        if (completar) {
            texto = hab.DevolverNombreYModif();
        } else {
            texto = "___________________________________________";
        }
        Paragraph p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(11 + line % 2);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5f);
        table.addCell(cell);

        if (completar) {
            texto = hab.DevolverAntiguosRangos() + "";
        } else {
            texto = "__";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(hab.DevolverCuadradosNuevosRangos(28));
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = "  " + hab.DevolverValorRangoHabilidad() + "";
        } else {
            texto = "   __";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = hab.categoriaPadre.Total() + "";
        } else {
            texto = "__";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = hab.DevolverBonusObjetos() + "";
        } else {
            texto = "__";
        }
        if (texto.equals("0")) {
            texto = "__";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = hab.DevolverBonusEspeciales() + "";
        } else {
            texto = "__";
        }
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
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            if (hab.DevolverBonusObjetos() > 0 || siempreTalento > 0) {
                texto = hab.Total() - hab.DevolverBonusObjetos() - siempreTalento + "/" + hab.Total() + "";
            } else {
                texto = hab.Total() + "";
            }
        } else {
            texto = "__";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void AddLineaHabilidadEspecializacion(Habilidad hab, String font, int fontSize, PdfPTable table, int i, int indiceEspecializacion)
            throws BadElementException, MalformedURLException, IOException {
        String texto;
        PdfPCell cell;

        if (completar) {
            texto = hab.DevolverNombre() + " (" + hab.especializacion.get(indiceEspecializacion) + ")";
        } else {
            texto = "___________________________________________";
        }
        Paragraph p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(11 + i % 2);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5f);
        table.addCell(cell);

        if (completar) {
            texto = hab.DevolverRangosEspecializacion() + "";
        } else {
            texto = "__";
        }

        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        Image image;
        image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros0.png");


        image.scalePercent(28);
        cell = new PdfPCell(image);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = "  " + hab.DevolverValorRangoHabilidadEspecializacion() + "";
        } else {
            texto = "   __";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = hab.categoriaPadre.Total() + "";
        } else {
            texto = "__";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = hab.DevolverBonusObjetos() + "";
        } else {
            texto = "__";
        }
        if (texto.equals("0")) {
            texto = "__";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            texto = hab.DevolverBonusEspeciales() + "";
        } else {
            texto = "__";
        }

        String letra = "";
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
        if (!letra.equals("")) {
            texto += "(" + letra + ")";
        }

        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (completar) {
            if (hab.DevolverBonusObjetos() > 0 || siempreTalento > 0) {
                texto = hab.Total() - hab.DevolverBonusObjetos() - siempreTalento + "/" + hab.Total() + "";
            } else {
                texto = hab.Total() + "";
            }
        } else {
            texto = "__";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void AddLineaHabilidadVacia(String font, int fontSize, PdfPTable table, int i)
            throws BadElementException, MalformedURLException, IOException {
        PdfPCell cell;
        Paragraph p = new Paragraph("___________________________________________",
                FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight(11 + i % 2);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5f);
        table.addCell(cell);

        p = new Paragraph("_____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        Image image;
        image = Image.getInstance(DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "cuadros" + File.separator + "cuadros0.png");
        image.scalePercent(28);
        cell = new PdfPCell(image);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("   _____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("  _____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("  _____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("_____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("_____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p = new Paragraph("_____", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void AñadirNuevaPaginaHabilidades(PdfPTable table, Document document, PdfWriter writer, String font, int fontSize)
            throws BadElementException, DocumentException, MalformedURLException, IOException, Exception {
        PdfPCell cell;
        //Cerramos pagina anterior.
        cell = new PdfPCell(CrearFirma(font, fontSize));
        cell.setBorderWidth(0);
        cell.setColspan(9);
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(cell);
        table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 129, writer.getDirectContent());
        table.flushContent();
        //Generamos el reverso en blanco.
        if (doscaras) {
            PersonajePaginaVacia(document, writer, font);
        }
        //Generamos una nueva.
        document.newPage();
        AddImagenFondo(document, DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "RMHP3.png");
    }

    private int NuevaHabilidad(PdfPTable table, Document document, PdfWriter writer,
            String font, int fontsize, float[] widths, List<Habilidad> habilidades, int habilidadesYaInsertadas)
            throws DocumentException, MalformedURLException, IOException, Exception {

        for (int j = 0; j < habilidades.size(); j++) {
            Habilidad hab = habilidades.get(j);
            if (habilidadesYaInsertadas > 56) {
                AñadirNuevaPaginaHabilidades(table, document, writer, font, fontsize);
                table.flushContent();
                table.getDefaultCell().setBorderWidth(0);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.setTotalWidth(document.getPageSize().getWidth() - 65);
                habilidadesYaInsertadas = 0;
            }

            if (hab.MereceLaPenaImprimir()) {
                habilidadesYaInsertadas++;
                AddLineaHabilidad(hab, font, fontsize, table, habilidadesYaInsertadas);
                for (int m = 0; m < hab.RangosGastadosEnEspecializacion(); m++) {
                    AddLineaHabilidadEspecializacion(hab, font, fontsize, table, habilidadesYaInsertadas, m);
                    if (habilidadesYaInsertadas > 56) {
                        AñadirNuevaPaginaHabilidades(table, document, writer, font, fontsize);
                        table.flushContent();
                        table.getDefaultCell().setBorderWidth(0);
                        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.setTotalWidth(document.getPageSize().getWidth() - 65);
                        habilidadesYaInsertadas = 0;
                    }
                    habilidadesYaInsertadas++;
                }
            }
        }
        return habilidadesYaInsertadas;
    }

    private void AddTablaValoresHabilidades(Document document, PdfWriter writer, String font)
            throws DocumentException, MalformedURLException, IOException, Exception {
        float[] widths = {0.36f, 0.07f, 0.085f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f};
        PdfPTable table = new PdfPTable(widths);
        table.getDefaultCell().setBorderWidth(0);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        //table.setWidthPercentage((float)102);
        table.setTotalWidth(document.getPageSize().getWidth() - 65);
        PdfPCell cell;
        int fontsize = 6;

        int habilidades = 0;

        if (completar) {
            //Añadimos las habilidades y añadimos espacios para nuevas.
            if (!Esher.habilidadesOrdenadasEnPDF) {
                for (int i = 0; i < Personaje.getInstance().categorias.size(); i++) {
                    Categoria cat = Personaje.getInstance().categorias.get(i);
                    habilidades = NuevaHabilidad(table, document, writer, font, fontsize, widths, cat.listaHabilidades, habilidades);
                }
            } else {
                List<Habilidad> habilidadesOrdenadas = Personaje.getInstance().DevolverHabilidadesOrdenadasAlfabeticamente();
                habilidades = NuevaHabilidad(table, document, writer, font, fontsize, widths, habilidadesOrdenadas, habilidades);
            }
        }
        while (habilidades < 57) {
            habilidades++;
            AddLineaHabilidadVacia(font, fontsize, table, habilidades);
        }

        cell = new PdfPCell(CrearFirma(font, fontsize + 1));
        cell.setBorderWidth(0);
        cell.setColspan(9);
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(cell);

        table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 129, writer.getDirectContent());
        table.flushContent();
    }

    private void PersonajePagina3PDF(Document document, PdfWriter writer, String font) throws Exception {
        document.newPage();
        AddImagenFondo(document, DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "RMHP3.png");
        AddTablaNombreHabilidades(document, writer, font);
        AddTablaValoresHabilidades(document, writer, font);
    }

    private void PersonajePagina2PDF(Document document, PdfWriter writer, String font) throws Exception {
        document.newPage();
        AddImagenFondo(document, DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "RMHP2.png");
        AddTablaNombreCat(document, writer, font);
        AddTablaValoresCategorias(document, writer, font);
        //El reverso en blanco para no desentonar.
        //if(doscaras) PersonajePaginaVacia(document, writer, font);
    }

    private PdfPTable CrearTablaCabeceraPaginaPrincipal(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        float[] widths = {0.26f, 0.23f, 0.51f};
        PdfPTable table = new PdfPTable(widths);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().ObtenerExperineciaAPartirNivel() + "", FontFactory.getFont(font, fontSize + 2));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize + 2));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingBottom(10f);
        table.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().nivel + "", FontFactory.getFont(font, fontSize + 2));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize + 2));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingBottom(10f);
        table.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().DevolverNombreCompleto(), FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize + 2));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingRight(15f);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setPaddingTop(5f);
        table.addCell(cell);


        return table;
    }

    private PdfPTable CrearTablaResistencia(String font, int fontSize, int tr, String caracteristica) {
        PdfPCell cell;
        Paragraph p;
        float[] widths = {0.37f, 0.15f, 0.15f, 0.165f, 0.15f};
        PdfPTable tablaResistencia = new PdfPTable(widths);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencia.addCell(cell);

        if (completar) {
            p = new Paragraph(tr + "", FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph(" _____", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencia.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura(caracteristica) * 3 + "",
                    FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph(" _____", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaResistencia.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencia.addCell(cell);

        if (completar) {
            p = new Paragraph((Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura(caracteristica) * 3 + tr) + "    ",
                    FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("______", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencia.addCell(cell);

        return tablaResistencia;
    }

    private PdfPTable CrearTablaResistencias(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tablaResistencias = new PdfPTable(1);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencias.addCell(cell);

        if (completar) {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, Personaje.getInstance().TrCanalizacion(), "In"));
        } else {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, 0, ""));
        }
        cell.setBorderWidth(0);
        cell.setMinimumHeight(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencias.addCell(cell);

        if (completar) {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, Personaje.getInstance().TrEsencia(), "Em"));
        } else {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, 0, ""));
        }
        cell.setBorderWidth(0);
        cell.setMinimumHeight(11);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencias.addCell(cell);

        if (completar) {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, Personaje.getInstance().TrMentalismo(), "Pr"));
        } else {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, 0, ""));
        }
        cell.setBorderWidth(0);
        cell.setMinimumHeight(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencias.addCell(cell);

        if (completar) {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, Personaje.getInstance().TrEnfermedades(), "Co"));
        } else {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, 0, ""));
        }
        cell.setBorderWidth(0);
        cell.setMinimumHeight(11);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencias.addCell(cell);

        if (completar) {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, Personaje.getInstance().TrVenenos(), "Co"));
        } else {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, 0, ""));
        }
        cell.setBorderWidth(0);
        cell.setMinimumHeight(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencias.addCell(cell);

        if (completar) {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, 0, "Ad"));
        } else {
            cell = new PdfPCell(CrearTablaResistencia(font, fontSize, 0, ""));
        }
        cell.setBorderWidth(0);
        cell.setMinimumHeight(11);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResistencias.addCell(cell);

        return tablaResistencias;
    }

    private PdfPTable CrearTablaBonificacionDefensiva(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tablaResistencias = new PdfPTable(1);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().DevolverBD() + "    ", FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("_________", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setColspan(4);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(30);
        cell.setPaddingRight(5);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaResistencias.addCell(cell);

        return tablaResistencias;
    }

    private PdfPTable CrearTablaRaza(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tablaRaza = new PdfPTable(1);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(14);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaRaza.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().partidaAlma + "", FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("____", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(14);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        tablaRaza.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().recuperacion + "   ", FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("____             ", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setColspan(2);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(14);
        cell.setPaddingRight(10f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaRaza.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().progresionDesarrolloFisico, FontFactory.getFont(font, fontSize + 2));
        } else {
            p = new Paragraph("   ", FontFactory.getFont(font, fontSize + 2));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(23);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingBottom(5);
        tablaRaza.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().progresionPuntosPoder, FontFactory.getFont(font, fontSize + 2));
        } else {
            p = new Paragraph("   ", FontFactory.getFont(font, fontSize + 2));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(23);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPaddingBottom(5);
        tablaRaza.addCell(cell);

        return tablaRaza;
    }

    private PdfPTable CrearTablaInterpretacion(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tabla = new PdfPTable(1);
        String texto;
        String linea;

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(13);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        if (completar) {
            if (Personaje.getInstance().caracteristicas.DevolverTotalApariencia() < 10) {
                linea = "  ________________________";
            } else if (Personaje.getInstance().caracteristicas.DevolverTotalApariencia() < 100) {
                linea = "  _______________________";
            } else {
                linea = "  ______________________";
            }
            texto = "(" + Personaje.getInstance().caracteristicas.DevolverTotalApariencia() + ")" + linea;
        } else {
            texto = "____________________________";
        }

        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(11);
        cell.setPaddingRight(6f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(11);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(11);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        if (completar) {
            texto = "   " + Personaje.getInstance().sexo;
        } else {
            texto = "_____________";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingLeft(26f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(cell);

        return tabla;
    }

    private PdfPTable CrearTablaHistorial(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tabla = new PdfPTable(1);
        String texto;

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(16);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        for (int i = 0; i < 8; i++) {
            p = new Paragraph("", FontFactory.getFont(font, fontSize));
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setMinimumHeight(11);
            cell.setPaddingRight(5f);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla.addCell(cell);
        }

        String linea = "______________________________";
        if (completar) {
            texto = Personaje.getInstance().cultura + "  " + linea.substring(Personaje.getInstance().cultura.length());
        } else {
            texto = linea;
        }

        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(11);
        cell.setPaddingRight(6f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        return tabla;
    }

    private PdfPTable CrearTablaDatosPersonaje(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tabla = new PdfPTable(1);
        String texto;

        if (completar) {
            texto = Personaje.getInstance().raza;
        } else {
            texto = "";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        if (completar) {
            texto = Personaje.getInstance().profesion;
        } else {
            texto = "";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);

        if (completar) {
            texto = Personaje.getInstance().DevolverStringConjuntoAdiestramientos();
        } else {
            texto = "";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        if (completar) {
            texto = Personaje.getInstance().reino;
        } else {
            texto = "";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        return tabla;
    }

    private PdfPTable CrearTablaDatosArmadura(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tabla = new PdfPTable(1);
        String texto;

        texto = "";
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        texto = "";
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);

        if (completar) {
            texto = Personaje.getInstance().DevolverCapacidadMovimiento() + "";
        } else {
            texto = "";
        }
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize + 1));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(12);
        cell.setPaddingRight(5f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cell);



        return tabla;
    }

    private PdfPTable CrearTableMarcoIzquierdoPaginaPrincipal(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tablaIzquierda = new PdfPTable(1);

        cell = new PdfPCell(CrearTablaDatosPersonaje(font, fontSize));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(60);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaIzquierda.addCell(cell);

        cell = new PdfPCell(CrearTablaDatosArmadura(font, fontSize));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(65);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaIzquierda.addCell(cell);

        cell = new PdfPCell(CrearTablaBonificacionDefensiva(font, fontSize + 1));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(75);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaIzquierda.addCell(cell);

        cell = new PdfPCell(CrearTablaResistencias(font, fontSize - 1));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(130);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaIzquierda.addCell(cell);

        cell = new PdfPCell(CrearTablaRaza(font, fontSize + 1));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(86);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaIzquierda.addCell(cell);

        cell = new PdfPCell(CrearTablaInterpretacion(font, fontSize + 1));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(138);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaIzquierda.addCell(cell);

        cell = new PdfPCell(CrearTablaHistorial(font, fontSize + 1));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(100);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaIzquierda.addCell(cell);

        return tablaIzquierda;
    }

    private PdfPTable CrearTablaMarcoCaracteristicasRunas(String font, int fontSize) {
        float[] widths = {0.79f, 0.21f};
        PdfPTable tablaMarco = new PdfPTable(widths);
        Paragraph p;
        PdfPCell cell;

        cell = new PdfPCell(CrearTablaCaracteristicas(font, fontSize));
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaMarco.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaMarco.addCell(cell);

        return tablaMarco;
    }

    private PdfPTable CrearTablaCaracteristicas(String font, int fontSize) {
        String[] caract = {"Ag", "Co", "Me", "Ra", "Ad", "Em", "In", "Pr", "Rp", "Fu"};
        float[] widths = {0.30f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.20f};
        PdfPTable tablaCaracteristicas = new PdfPTable(widths);
        Paragraph p;
        PdfPCell cell;

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setMinimumHeight((float) 20);
        cell.setColspan(10);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaCaracteristicas.addCell(cell);

        for (int i = 0; i < caract.length; i++) {

            Caracteristica car = null;
            if (completar) {
                car = Personaje.getInstance().caracteristicas.DevolverCaracteristicaDeAbreviatura(caract[i]);
            }
            p = new Paragraph("", FontFactory.getFont(font, fontSize));
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setMinimumHeight((float) 13.5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaCaracteristicas.addCell(cell);

            if (completar) {
                p = new Paragraph(car.ObtenerPuntosTemporal() + "", FontFactory.getFont(font, fontSize));
            } else {
                p = new Paragraph("  ____", FontFactory.getFont(font, fontSize));
            }
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaCaracteristicas.addCell(cell);

            if (completar) {
                p = new Paragraph(car.ObtenerPuntosPotencial() + "", FontFactory.getFont(font, fontSize));
            } else {
                p = new Paragraph("  ____", FontFactory.getFont(font, fontSize));
            }
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaCaracteristicas.addCell(cell);

            if (completar) {
                p = new Paragraph(car.ObtenerValorTemporal() + "", FontFactory.getFont(font, fontSize));
            } else {
                p = new Paragraph("  ____", FontFactory.getFont(font, fontSize));
            }
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaCaracteristicas.addCell(cell);

            if (completar) {
                p = new Paragraph(car.ObtenerValorRaza() + "", FontFactory.getFont(font, fontSize));
            } else {
                p = new Paragraph("  ____", FontFactory.getFont(font, fontSize));
            }
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaCaracteristicas.addCell(cell);

            if (completar) {
                p = new Paragraph("    " + car.ObtenerPuntosEspecial(), FontFactory.getFont(font, fontSize));
            } else {
                p = new Paragraph("    ____", FontFactory.getFont(font, fontSize));
            }
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaCaracteristicas.addCell(cell);

            if (completar) {
                p = new Paragraph(car.Total() + "", FontFactory.getFont(font, fontSize));
            } else {
                p = new Paragraph("", FontFactory.getFont(font, fontSize));
            }
            cell = new PdfPCell(p);
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaCaracteristicas.addCell(cell);

            //El separador de caracteristicas
            if (i == 4) {
                p = new Paragraph("", FontFactory.getFont(font, fontSize));
                cell = new PdfPCell(p);
                cell.setBorderWidth(0);
                cell.setMinimumHeight((float) 6);
                cell.setColspan(10);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaCaracteristicas.addCell(cell);
            }

        }
        return tablaCaracteristicas;
    }

    private PdfPTable CrearTablaContabilizacionPuntos(String font, int fontSize) {
        float[] widths = {0.32f, 0.32f, 0.35f};
        PdfPTable tabla = new PdfPTable(widths);
        Paragraph p;
        PdfPCell cell;

        p = new Paragraph(" ", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(30);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);

        if (completar) {
            p = new Paragraph(Personaje.getInstance().DevolverHabilidadDeNombre("Desarrollo Físico").Total() + "",
                    FontFactory.getFont(font, fontSize + 3));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize + 3));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);
        if (completar) {
            p = new Paragraph(Personaje.getInstance().DevolverHabilidadDeNombre("Desarrollo de Puntos de Poder").Total() + "",
                    FontFactory.getFont(font, fontSize + 3));
        } else {
            p = new Paragraph("", FontFactory.getFont(font, fontSize + 3));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);

        p = new Paragraph(" ", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(10);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);

        if (completar) {
            int puntos = Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Co") / 2;
            if (puntos < 0) {
                puntos = 0;
            }
            p = new Paragraph(puntos + "",
                    FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("__", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setMinimumHeight(25);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(30f);
        tabla.addCell(cell);


        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingRight(30f);
        tabla.addCell(cell);

        if (completar) {
            int puntos = Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura(Personaje.getInstance().CaracteristicasDeReino()) / 2;
            if (puntos < 1) {
                puntos = 1;
            }
            p = new Paragraph(puntos + "",
                    FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("__", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(30f);
        tabla.addCell(cell);

        if (completar) {
            int puntos = Math.min(Personaje.getInstance().DevolverBonusCaracteristicaDeAbreviatura("Co") * 2,
                    Personaje.getInstance().DevolverHabilidadDeNombre("Desarrollo Físico").Total());
            if (puntos < 1) {
                puntos = 1;
            }
            p = new Paragraph(puntos + "",
                    FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("__", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setMinimumHeight(20);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(30f);
        tabla.addCell(cell);


        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingRight(30f);
        tabla.addCell(cell);

        if (completar) {
            int puntos = Personaje.getInstance().DevolverHabilidadDeNombre("Desarrollo de Puntos de Poder").Total() / 2;
            if (puntos < 1) {
                puntos = 1;
            }
            p = new Paragraph(puntos + "",
                    FontFactory.getFont(font, fontSize));
        } else {
            p = new Paragraph("__", FontFactory.getFont(font, fontSize));
        }
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(30f);
        tabla.addCell(cell);


        return tabla;
    }

    PdfPTable CrearFirma(String font, int fontSize) {
        PdfPTable tabla = new PdfPTable(1);
        Paragraph p;
        PdfPCell cell;

        p = new Paragraph("Generado con El Libro de Esher, herramienta para Rolemaster V" + Esher.getVersion() + "",
                FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingLeft(30f);
        tabla.addCell(cell);

        return tabla;
    }

    private PdfPTable CrearTableMarcoDerechoPaginaPrincipal(String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        PdfPTable tablaDerecha = new PdfPTable(1);

        cell = new PdfPCell(CrearTablaMarcoCaracteristicasRunas(font, fontSize));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(165);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaDerecha.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(208);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaDerecha.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(100);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaDerecha.addCell(cell);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));
        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setMinimumHeight(100);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaDerecha.addCell(cell);

        cell = new PdfPCell(CrearTablaContabilizacionPuntos(font, fontSize + 2));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(125);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaDerecha.addCell(cell);

        return tablaDerecha;
    }

    private void AddTablaPrincipal(Document document, PdfWriter writer, String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;
        float[] widths = {0.295f, 0.605f};
        PdfPTable tablaPagina = new PdfPTable(widths);
        tablaPagina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.setTotalWidth(document.getPageSize().getWidth() - 60);


        cell = new PdfPCell(CrearTablaCabeceraPaginaPrincipal(font, fontSize));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(58);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaPagina.addCell(cell);

        cell = new PdfPCell(CrearTableMarcoIzquierdoPaginaPrincipal(font, fontSize));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(document.getPageSize().getHeight() - 145);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.addCell(cell);

        cell = new PdfPCell(CrearTableMarcoDerechoPaginaPrincipal(font, fontSize));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(document.getPageSize().getHeight() - 145);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.addCell(cell);

        cell = new PdfPCell(CrearFirma(font, fontSize));
        cell.setBorderWidth(0);
        cell.setColspan(2);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.addCell(cell);

        //PdfPCell celda  = new PdfPCell();
        tablaPagina.writeSelectedRows(0, -1, 30,
                document.getPageSize().getHeight() - 37, writer.getDirectContent());
        tablaPagina.flushContent();
    }

    void PersonajePagina1PDF(Document document, PdfWriter writer, String font) throws Exception {
        int fontSize = 7;
        AddImagenFondo(document, DirectorioRolemaster.ROLEMASTER_FOLDER + File.separator + "fichas" + File.separator + "RMHP1.png");
        AddTablaPrincipal(document, writer, font, fontSize);
    }

    public String ExportarEspeciales() {
        FichaTxt textoPJ = new FichaTxt();
        return textoPJ.ExportarEspeciales();
    }

    public String ExportarTalentos() {
        FichaTxt textoPJ = new FichaTxt();
        return textoPJ.ExportarTalentos();
    }

    public String ExportarATextoEquipos() {
        FichaTxt textoPJ = new FichaTxt();
        return textoPJ.ExportarATextoEquipo();
    }

    private void AddTextoEspeciales(Document document, PdfWriter writer, String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;

        float[] widths = {1};
        PdfPTable tablaPagina = new PdfPTable(widths);
        tablaPagina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.setTotalWidth(document.getPageSize().getWidth() - 60);

        String texto = ExportarTalentos() + "\n\n" + ExportarEspeciales()
                + "\n" + ExportarATextoEquipos();
        p = new Paragraph(texto, FontFactory.getFont(font, fontSize - 1));

        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(30f);
        cell.setMinimumHeight(document.getPageSize().getHeight() - 90);
        tablaPagina.addCell(cell);

        cell = new PdfPCell(CrearFirma(font, fontSize - 2));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.addCell(cell);

        tablaPagina.writeSelectedRows(0, -1, 30,
                document.getPageSize().getHeight() - 37, writer.getDirectContent());
        tablaPagina.flushContent();
    }

    private void AddTextoVacio(Document document, PdfWriter writer, String font, int fontSize) {
        PdfPCell cell;
        Paragraph p;

        float[] widths = {1};
        PdfPTable tablaPagina = new PdfPTable(widths);
        tablaPagina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.setTotalWidth(document.getPageSize().getWidth() - 60);

        p = new Paragraph("", FontFactory.getFont(font, fontSize));

        cell = new PdfPCell(p);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(30f);
        cell.setMinimumHeight(document.getPageSize().getHeight() - 90);
        tablaPagina.addCell(cell);

        cell = new PdfPCell(CrearFirma(font, fontSize - 2));
        cell.setBorderWidth(0);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaPagina.addCell(cell);

        tablaPagina.writeSelectedRows(0, -1, 30,
                document.getPageSize().getHeight() - 37, writer.getDirectContent());
        tablaPagina.flushContent();
    }

    void PersonajePagina1bPDF(Document document, PdfWriter writer, String font) throws Exception {
        int fontSize = 9;
        document.newPage();
        AddTextoEspeciales(document, writer, font, fontSize);
    }

    private void PersonajePaginaVacia(Document document, PdfWriter writer, String font) throws Exception {
        int fontSize = 9;
        document.newPage();
        AddTextoVacio(document, writer, font, fontSize);
    }
}

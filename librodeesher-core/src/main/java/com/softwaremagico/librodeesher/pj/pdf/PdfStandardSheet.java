package com.softwaremagico.librodeesher.pj.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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
import com.softwaremagico.files.Path;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.Experience;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.ProgressionCostType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.txt.ExportToText;
import com.softwaremagico.log.Log;

public class PdfStandardSheet {
	private final static String EMPTY_VALUE = "_____";
	private CharacterPlayer characterPlayer;
	protected boolean twoFaced;
	private boolean sortedSkills;

	public PdfStandardSheet(CharacterPlayer characterPlayer, String path, boolean sortedSkills)
			throws MalformedURLException, DocumentException, IOException {
		this.characterPlayer = characterPlayer;
		this.sortedSkills = sortedSkills;
		if (characterPlayer == null) {
			whitePage();
		} else {
			characterPDF(path);
		}
	}

	/**
	 * Creates a character sheet in a pdf format.
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public void characterPDF(String path) throws DocumentException, MalformedURLException, IOException {
		Document document = new Document(PageSize.A4);
		if (path == null) {
			path = Path.getDefaultPdfPath() + File.separator + "RMFesher.pdf";
		} else if (!path.endsWith(".pdf")) {
			path += ".pdf";
		}
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			createPdf(document, writer);
		} catch (FileNotFoundException fnfe) {
			Log.errorMessage(PdfStandardSheet.class.getName(), fnfe);
			throw fnfe;
		}
	}

	private void createPdf(Document document, PdfWriter writer) throws BadElementException, MalformedURLException,
			DocumentException, IOException {
		String font = FontFactory.HELVETICA;

		twoFaced = (characterPlayer.getPerks().size() > 0 || characterPlayer.getRace().getSpecials().size() > 0 || characterPlayer
				.getEquipment().size() > 0);

		DocumentData(document, writer);
		document.open();
		characteristicsPage(document, writer, font);
		if (twoFaced) {
			equipmentPage(document, writer, font);
		}
		categoriesPage(document, writer, font);
		if (twoFaced) {
			whitePage(document, writer, font);
		}
		skillPage(document, writer, font);
		if (twoFaced) {
			whitePage(document, writer, font);
		}
		document.close();
	}

	private void whitePage() throws DocumentException, MalformedURLException, IOException {
		String font = FontFactory.HELVETICA;

		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RMFesher.pdf"));
		DocumentData(document, writer);
		document.open();
		characteristicsPage(document, writer, font);
		categoriesPage(document, writer, font);
		skillPage(document, writer, font);
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

	void createBackgroundImage(Document document, String imagen) throws BadElementException, DocumentException,
			MalformedURLException, IOException {
		Image png;

		png = Image.getInstance(imagen);
		png.setAlignment(Image.MIDDLE | Image.UNDERLYING);
		png.scaleToFit((float) 760, (float) 760);
		document.add(png);
	}

	private void addCategoryTable(Document document, PdfWriter writer, String font) {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorderWidth(0);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		int fontSize = 8;
		table.setTotalWidth(document.getPageSize().getWidth() - 500);

		Paragraph p;
		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getName(), FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph("", FontFactory.getFont(font, fontSize + 2));
		}
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 3 * document.getPageSize().getWidth() / 4 + 20, document.getPageSize()
				.getHeight() - 45, writer.getDirectContent());
		table.flushContent();
	}

	private void addCategoryValuesTable(Document document, PdfWriter writer, String font) throws DocumentException,
			MalformedURLException, IOException {
		float[] widths = { 0.23f, 0.09f, 0.07f, 0.07f, 0.085f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f };
		PdfPTable table = new PdfPTable(widths);
		table.getDefaultCell().setBorderWidth(0);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setTotalWidth(document.getPageSize().getWidth() - 65);
		int fontSize = 6;
		String text;
		PdfPCell cell;
		Category category = null;
		int omitidas = 0;

		// Add categories and spaces for a new ones.
		for (int i = 0; i < 60 + omitidas; i++) {
			if (i < CategoryFactory.getAvailableCategories().size()) {
				category = CategoryFactory.getCategory(CategoryFactory.getAvailableCategories().get(i));
			}

			if (characterPlayer.isCategoryUseful(category) || i >= CategoryFactory.getAvailableCategories().size()) {

				// Generamos una fila de Category.
				if (i < CategoryFactory.getAvailableCategories().size()) {
					text = category.getName();
				} else {
					text = "_______________________";
				}
				Paragraph p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setMinimumHeight(11 + (i - omitidas) % 2);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingLeft(5f);
				table.addCell(cell);

				if (i < CategoryFactory.getAvailableCategories().size()) {
					text = category.getCharacterisitcsTags();
				} else {
					text = "_______";
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
					text = characterPlayer.getCategoryCost(category, 0).getCostTag();
				} else {
					text = "_________";
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (i < CategoryFactory.getAvailableCategories().size()) {
					if (category.getCategoryType().equals(CategoryType.STANDARD)) {
						if (characterPlayer != null) {
							text = characterPlayer.getPreviousRanks(category) + "";
						} else {
							text = EMPTY_VALUE;
						}
					} else {
						text = "na";
					}
				} else {
					text = EMPTY_VALUE;
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (i < CategoryFactory.getAvailableCategories().size()) {
					if (category.getCategoryType().equals(CategoryType.STANDARD)) {
						Image image;
						if (characterPlayer == null) {
							image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator
									+ "cuadros" + File.separator + "cuadros0.png");
							image.scalePercent(28);
						} else {
							image = getNewRanksImage(characterPlayer.getCurrentLevelRanks(category));
						}
						cell = new PdfPCell(image);
					}

					if (category.getCategoryType().equals(CategoryType.COMBINED)) {
						text = "*";
						p = new Paragraph(text, FontFactory.getFont(font, fontSize));
						cell = new PdfPCell(p);
					}
					if (category.getCategoryType().equals(CategoryType.LIMITED)
							|| category.getCategoryType().equals(CategoryType.SPECIAL)
							|| category.getCategoryType().equals(CategoryType.PPD)
							|| category.getCategoryType().equals(CategoryType.PD)) {
						text = "+";
						p = new Paragraph(text, FontFactory.getFont(font, fontSize));
						cell = new PdfPCell(p);
					}
				} else {
					Image image;
					image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
							+ File.separator + "cuadros0.png");
					image.scalePercent(28);
					cell = new PdfPCell(image);
				}
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
					text = characterPlayer.getRanksValue(category) + "";
				} else {
					text = EMPTY_VALUE;
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
					text = characterPlayer.getCharacteristicsBonus(category) + "";
				} else {
					text = EMPTY_VALUE;
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
					text = category.getBonus() + characterPlayer.getProfession().getCategoryBonus(category.getName())
							+ "";
				} else {
					text = EMPTY_VALUE;
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
					text = (characterPlayer.getHistorial().getBonus(category) + characterPlayer.getPerkBonus(category))
							+ "";
					String letra = "";

					if (characterPlayer.getHistorial().getBonus(category) > 0) {
						letra += "H";
					}

					if (characterPlayer.getPerkBonus(category) != 0) {
						letra += "T";
						if (characterPlayer.getConditionalPerkBonus(category) != 0) {
							letra += "*";
						}
					}

					if (!letra.equals("")) {
						text += "(" + letra + ")";
					}
				} else {
					text = EMPTY_VALUE;
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				// Magic Items
				if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
					text = characterPlayer.getItemBonus(category) + "";
				} else {
					text = EMPTY_VALUE;
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
					text = characterPlayer.getTotalValue(category) + "";
				} else {
					text = EMPTY_VALUE;
				}
				p = new Paragraph(text, FontFactory.getFont(font, fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			} else {
				omitidas++;
			}
		}

		cell = new PdfPCell(createSignature(font, fontSize + 1));
		cell.setBorderWidth(0);
		cell.setColspan(11);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 97, writer.getDirectContent());
		table.flushContent();
	}

	private void addSkillNameTable(Document document, PdfWriter writer, String font) {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorderWidth(0);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		int fontSize = 8;
		table.setTotalWidth(document.getPageSize().getWidth() - 500);

		Paragraph p;
		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getName(), FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph("", FontFactory.getFont(font, fontSize));
		}
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		table.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getCurrentLevelNumber() + "", FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph("", FontFactory.getFont(font, fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 3 * document.getPageSize().getWidth() / 4 - 80, document.getPageSize()
				.getHeight() - 65, writer.getDirectContent());
	}

	private void addSkillLine(Skill skill, String font, int fontSize, PdfPTable table, int line)
			throws BadElementException, MalformedURLException, IOException {
		String text;
		PdfPCell cell;

		if (characterPlayer != null) {
			text = skill.getName() + " " + skill.getSkillType().getTag();
			text.trim();
		} else {
			text = "___________________________________________";
		}
		Paragraph p = new Paragraph(text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(11 + line % 2);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(5f);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getPreviousRanks(skill) + "";
		} else {
			text = "__";
		}
		p = new Paragraph(text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(getNewRanksImage(characterPlayer.getCurrentLevelRanks(skill)));
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = "  " + characterPlayer.getRanksValue(skill) + "";
		} else {
			text = "   __";
		}
		p = new Paragraph(text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getTotalValue(skill.getCategory()) + "";
		} else {
			text = "__";
		}
		p = new Paragraph(text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getItemBonus(skill) + "";
		} else {
			text = EMPTY_VALUE;
		}
		if (text.equals("0")) {
			text = EMPTY_VALUE;
		}
		p = new Paragraph(text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getProfession().getSkillBonus(skill.getName())
					+ characterPlayer.getHistorial().getBonus(skill) + characterPlayer.getPerkBonus(skill) + "";
		} else {
			text = "__";
		}
		String letter = "";
		if (characterPlayer.getHistorial().getBonus(skill) > 0) {
			letter += "H";
		}

		if (characterPlayer.getPerkBonus(skill) != 0) {
			letter += "T";
			if (characterPlayer.getConditionalPerkBonus(skill) != 0) {
				letter += "*";
			}
		}
		if (!letter.equals("")) {
			text += "(" + letter + ")";
		}
		p = new Paragraph(text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			if (characterPlayer.getItemBonus(skill) > 0 || characterPlayer.getConditionalPerkBonus(skill) > 0) {
				text = characterPlayer.getTotalValue(skill) - characterPlayer.getItemBonus(skill)
						- characterPlayer.getConditionalPerkBonus(skill) + "/" + characterPlayer.getTotalValue(skill)
						+ "";
			} else {
				text = characterPlayer.getTotalValue(skill) + "";
			}
		} else {
			text = EMPTY_VALUE;
		}
		p = new Paragraph(text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	private void addSpecializedSkillLine(Skill hab, String font, int fontSize, PdfPTable table, int i,
			int specializedIndex) throws BadElementException, MalformedURLException, IOException {
		String texto;
		PdfPCell cell;

		if (characterPlayer != null) {
			texto = hab.getName() + " (" + characterPlayer.getSkillSpecializations(hab).get(specializedIndex) + ")";
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

		texto = "__";

		p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		Image image;
		image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
				+ File.separator + "cuadros0.png");

		image.scalePercent(28);
		cell = new PdfPCell(image);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			texto = "  " + characterPlayer.getSpecializedRanks(hab) + "";
		} else {
			texto = "   __";
		}
		p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			texto = characterPlayer.getTotalValue(hab.getCategory()) + "";
		} else {
			texto = "__";
		}
		p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			texto = characterPlayer.getItemBonus(hab) + "";
		} else {
			texto = EMPTY_VALUE;
		}
		if (texto.equals("0")) {
			texto = EMPTY_VALUE;
		}
		p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			texto = characterPlayer.getProfession().getSkillBonus(hab.getName())
					+ characterPlayer.getHistorial().getBonus(hab) + characterPlayer.getPerkBonus(hab) + "";
		} else {
			texto = "__";
		}

		String letra = "";
		if (characterPlayer.getHistorial().getBonus(hab) > 0) {
			letra += "H";
		}

		if (characterPlayer.getPerkBonus(hab) != 0) {
			letra += "T";
			if (characterPlayer.getConditionalPerkBonus(hab) != 0) {
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

		p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			if (characterPlayer.getItemBonus(hab) > 0 || characterPlayer.getConditionalPerkBonus(hab) > 0) {
				texto = characterPlayer.getSpecializedTotalValue(hab) - characterPlayer.getItemBonus(hab)
						- characterPlayer.getConditionalPerkBonus(hab) + "/"
						+ characterPlayer.getSpecializedTotalValue(hab) + "";
			} else {
				texto = characterPlayer.getSpecializedTotalValue(hab) + "";
			}
		} else {
			texto = EMPTY_VALUE;
		}
		p = new Paragraph(texto, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	private void addEmptySkillLine(String font, int fontSize, PdfPTable table, int i) throws BadElementException,
			MalformedURLException, IOException {
		PdfPCell cell;
		Paragraph p = new Paragraph("___________________________________________", FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(11 + i % 2);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(5f);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		Image image;
		image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
				+ File.separator + "cuadros0.png");
		image.scalePercent(28);
		cell = new PdfPCell(image);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph("   " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph("  " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph("  " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	protected void addNewSkillPage(PdfPTable table, Document document, PdfWriter writer, String font, int fontSize)
			throws BadElementException, DocumentException, MalformedURLException, IOException {
		PdfPCell cell;
		// Cerramos pagina anterior.
		cell = new PdfPCell(createSignature(font, fontSize));
		cell.setBorderWidth(0);
		cell.setColspan(9);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);
		table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 129, writer.getDirectContent());
		table.flushContent();
		// Generamos el reverso en blanco.
		if (twoFaced) {
			whitePage(document, writer, font);
		}
		// Generamos una nueva.
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator + "RMHP3.png");
	}

	private int newSkill(PdfPTable table, Document document, PdfWriter writer, String font, int fontsize,
			float[] widths, List<Skill> skills, int alreadyAddedSkills) throws DocumentException,
			MalformedURLException, IOException {

		for (int j = 0; j < skills.size(); j++) {
			Skill skill = skills.get(j);
			if (alreadyAddedSkills > 56) {
				addNewSkillPage(table, document, writer, font, fontsize);
				table.flushContent();
				table.getDefaultCell().setBorderWidth(0);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setTotalWidth(document.getPageSize().getWidth() - 65);
				alreadyAddedSkills = 0;
			}

			if (characterPlayer.isSkillInteresting(skill)) {
				alreadyAddedSkills++;
				addSkillLine(skill, font, fontsize, table, alreadyAddedSkills);
				for (int m = 0; m < characterPlayer.getSkillSpecializations(skill).size(); m++) {
					addSpecializedSkillLine(skill, font, fontsize, table, alreadyAddedSkills, m);
					if (alreadyAddedSkills > 56) {
						addNewSkillPage(table, document, writer, font, fontsize);
						table.flushContent();
						table.getDefaultCell().setBorderWidth(0);
						table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						table.setTotalWidth(document.getPageSize().getWidth() - 65);
						alreadyAddedSkills = 0;
					}
					alreadyAddedSkills++;
				}
			}
		}
		return alreadyAddedSkills;
	}

	private void addSkillTable(Document document, PdfWriter writer, String font) throws DocumentException,
			MalformedURLException, IOException {
		float[] widths = { 0.36f, 0.07f, 0.085f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f };
		PdfPTable table = new PdfPTable(widths);
		table.getDefaultCell().setBorderWidth(0);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		// table.setWidthPercentage((float)102);
		table.setTotalWidth(document.getPageSize().getWidth() - 65);
		PdfPCell cell;
		int fontsize = 6;
		int skillLines = 0;

		if (characterPlayer != null) {
			// Add skills and add lines for new ones.
			if (!sortedSkills) {
				for (int i = 0; i < CategoryFactory.getAvailableCategories().size(); i++) {
					Category category = CategoryFactory.getCategory(CategoryFactory.getAvailableCategories().get(i));
					skillLines = newSkill(table, document, writer, font, fontsize, widths, category.getSkills(),
							skillLines);
				}
				// Add skills sorted
			} else {
				List<Skill> sortedSkills = SkillFactory.getSkills();
				skillLines = newSkill(table, document, writer, font, fontsize, widths, sortedSkills, skillLines);
			}
		}
		while (skillLines < 57) {
			skillLines++;
			addEmptySkillLine(font, fontsize, table, skillLines);
		}

		cell = new PdfPCell(createSignature(font, fontsize + 1));
		cell.setBorderWidth(0);
		cell.setColspan(9);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 129, writer.getDirectContent());
		table.flushContent();
	}

	private void skillPage(Document document, PdfWriter writer, String font) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator + "RMHP3.png");
		addSkillNameTable(document, writer, font);
		addSkillTable(document, writer, font);
	}

	private void categoriesPage(Document document, PdfWriter writer, String font) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator + "RMHP2.png");
		addCategoryTable(document, writer, font);
		addCategoryValuesTable(document, writer, font);
		// El reverso en blanco para no desentonar.
		// if(twoFaced) PersonajePaginaVacia(document, writer, font);
	}

	private PdfPTable createMainHeader(String font, int fontSize) {
		PdfPCell cell;
		Paragraph p;
		float[] widths = { 0.26f, 0.23f, 0.51f };
		PdfPTable table = new PdfPTable(widths);

		if (characterPlayer != null) {
			p = new Paragraph(Experience.getMinExperienceForLevel(characterPlayer.getCurrentLevelNumber()) + "",
					FontFactory.getFont(font, fontSize + 2));
		} else {
			p = new Paragraph("", FontFactory.getFont(font, fontSize + 2));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingBottom(10f);
		table.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getCurrentLevelNumber() + "", FontFactory.getFont(font, fontSize + 2));
		} else {
			p = new Paragraph("", FontFactory.getFont(font, fontSize + 2));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingBottom(10f);
		table.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getName(), FontFactory.getFont(font, fontSize));
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

	private PdfPTable createResistenceTable(String font, int fontSize, int tr,
			CharacteristicsAbbreviature characteristicAbbreviature) {
		PdfPCell cell;
		Paragraph p;
		float[] widths = { 0.37f, 0.15f, 0.15f, 0.165f, 0.15f };
		PdfPTable tablaResistencia = new PdfPTable(widths);

		p = new Paragraph("", FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencia.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(tr + "", FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph(" " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencia.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getCharacteristicTotalBonus(characteristicAbbreviature) * 3 + "",
					FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph(" " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
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

		if (characterPlayer != null) {
			p = new Paragraph((characterPlayer.getCharacteristicTotalBonus(characteristicAbbreviature) * 3 + tr)
					+ "    ", FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencia.addCell(cell);

		return tablaResistencia;
	}

	private PdfPTable createResistenceTable(String font, int fontSize) {
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

		if (characterPlayer != null) {
			cell = new PdfPCell(createResistenceTable(font, fontSize,
					characterPlayer.getResistanceBonus(ResistanceType.CANALIZATION),
					CharacteristicsAbbreviature.INTUITION));
		} else {
			cell = new PdfPCell(createResistenceTable(font, fontSize, 0, CharacteristicsAbbreviature.NULL));
		}
		cell.setBorderWidth(0);
		cell.setMinimumHeight(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		if (characterPlayer != null) {
			cell = new PdfPCell(createResistenceTable(font, fontSize,
					characterPlayer.getResistanceBonus(ResistanceType.ESSENCE), CharacteristicsAbbreviature.EMPATHY));
		} else {
			cell = new PdfPCell(createResistenceTable(font, fontSize, 0, CharacteristicsAbbreviature.NULL));
		}
		cell.setBorderWidth(0);
		cell.setMinimumHeight(11);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		if (characterPlayer != null) {
			cell = new PdfPCell(createResistenceTable(font, fontSize,
					characterPlayer.getResistanceBonus(ResistanceType.MENTALISM), CharacteristicsAbbreviature.PRESENCE));
		} else {
			cell = new PdfPCell(createResistenceTable(font, fontSize, 0, CharacteristicsAbbreviature.NULL));
		}
		cell.setBorderWidth(0);
		cell.setMinimumHeight(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		if (characterPlayer != null) {
			cell = new PdfPCell(createResistenceTable(font, fontSize,
					characterPlayer.getResistanceBonus(ResistanceType.DISEASE),
					CharacteristicsAbbreviature.CONSTITUTION));
		} else {
			cell = new PdfPCell(createResistenceTable(font, fontSize, 0, CharacteristicsAbbreviature.NULL));
		}
		cell.setBorderWidth(0);
		cell.setMinimumHeight(11);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		if (characterPlayer != null) {
			cell = new PdfPCell(
					createResistenceTable(font, fontSize, characterPlayer.getResistanceBonus(ResistanceType.POISON),
							CharacteristicsAbbreviature.CONSTITUTION));
		} else {
			cell = new PdfPCell(createResistenceTable(font, fontSize, 0, CharacteristicsAbbreviature.NULL));
		}
		cell.setBorderWidth(0);
		cell.setMinimumHeight(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		if (characterPlayer != null) {
			cell = new PdfPCell(
					createResistenceTable(font, fontSize, characterPlayer.getResistanceBonus(ResistanceType.FEAR),
							CharacteristicsAbbreviature.SELFDISCIPLINE));
		} else {
			cell = new PdfPCell(createResistenceTable(font, fontSize, 0, CharacteristicsAbbreviature.NULL));
		}
		cell.setBorderWidth(0);
		cell.setMinimumHeight(11);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		return tablaResistencias;
	}

	private PdfPTable createBDTable(String font, int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tablaResistencias = new PdfPTable(1);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getDefensiveBonus() + "    ", FontFactory.getFont(font, fontSize));
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

	private PdfPTable createTableRace(String font, int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tablaRaza = new PdfPTable(1);

		p = new Paragraph("", FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setMinimumHeight(14);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaRaza.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getRace().getSoulDepartTime() + "", FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setMinimumHeight(14);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		tablaRaza.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getRace().getRestorationTime() + "   ", FontFactory.getFont(font,
					fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE + "             ", FontFactory.getFont(font, fontSize));
		}
		cell = new PdfPCell(p);
		cell.setColspan(2);
		cell.setBorderWidth(0);
		cell.setMinimumHeight(14);
		cell.setPaddingRight(10f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tablaRaza.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getRace().getProgressionRankValuesAsString(
					ProgressionCostType.PHYSICAL_DEVELOPMENT), FontFactory.getFont(font, fontSize + 2));
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

		if (characterPlayer != null) {
			p = new Paragraph(Race.getProgressionRankValuesAsString(characterPlayer.getPowerPointsDevelopmentCost()),
					FontFactory.getFont(font, fontSize + 2));
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

	private PdfPTable createTableInterpretation(String font, int fontSize) {
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

		if (characterPlayer != null) {
			// Apparence line length depending on digits.
			if (characterPlayer.getAppearance() < 10) {
				linea = "  ________________________";
			} else if (characterPlayer.getAppearance() < 100) {
				linea = "  _______________________";
			} else {
				linea = "  ______________________";
			}
			texto = "(" + characterPlayer.getAppearance() + ")" + linea;
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

		if (characterPlayer != null) {
			texto = "   " + characterPlayer.getSex().getTag();
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

	private PdfPTable createHistoryTable(String font, int fontSize) {
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
		if (characterPlayer != null) {
			texto = characterPlayer.getCulture().getName() + "  "
					+ linea.substring(characterPlayer.getCulture().getName().length());
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

	private PdfPTable createCharacterDataTable(String font, int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tabla = new PdfPTable(1);
		String texto;

		if (characterPlayer != null) {
			texto = characterPlayer.getRace().getName();
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

		if (characterPlayer != null) {
			texto = characterPlayer.getProfession().getName();
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

		if (characterPlayer != null) {
			List<String> trainings = characterPlayer.getSelectedTrainings();
			for (int i = 0; i < trainings.size(); i++) {
				texto += trainings.get(i);
				if (i < trainings.size() - 1) {
					texto += ", ";
				}
			}
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

		if (characterPlayer != null) {
			texto = "";
			for (RealmOfMagic realm : characterPlayer.getRealmOfMagic().getRealmsOfMagic()) {
				if (texto.length() > 0) {
					texto += "/";
				}
				texto += realm.getName();
			}
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

	private PdfPTable createArmourTable(String font, int fontSize) {
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

		if (characterPlayer != null) {
			texto = characterPlayer.getMovementCapacity() + "";
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

	private PdfPTable createMainPageLeftFrame(String font, int fontSize) {
		PdfPCell cell;
		PdfPTable tablaIzquierda = new PdfPTable(1);

		cell = new PdfPCell(createCharacterDataTable(font, fontSize));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(60);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createArmourTable(font, fontSize));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(65);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createBDTable(font, fontSize + 1));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(75);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createResistenceTable(font, fontSize - 1));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(130);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createTableRace(font, fontSize + 1));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(86);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createTableInterpretation(font, fontSize + 1));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(138);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createHistoryTable(font, fontSize + 1));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(100);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		return tablaIzquierda;
	}

	private PdfPTable createRuneTable(String font, int fontSize) {
		float[] widths = { 0.79f, 0.21f };
		PdfPTable tablaMarco = new PdfPTable(widths);
		Paragraph p;
		PdfPCell cell;

		cell = new PdfPCell(createCharacteristicsTable(font, fontSize));
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

	private PdfPTable createCharacteristicsTable(String font, int fontSize) {
		List<Characteristic> characteristics = Characteristics.getCharacteristics();
		float[] widths = { 0.30f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.20f };
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

		for (int i = 0; i < characteristics.size(); i++) {
			p = new Paragraph("", FontFactory.getFont(font, fontSize));
			cell = new PdfPCell(p);
			cell.setBorderWidth(0);
			cell.setMinimumHeight((float) 13.5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicTemporalValue(characteristics.get(i)
						.getAbbreviature()) + "", FontFactory.getFont(font, fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicPotencialValue().get(
						characteristics.get(i).getAbbreviature())
						+ "", FontFactory.getFont(font, fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicTemporalBonus(characteristics.get(i)
						.getAbbreviature()) + "", FontFactory.getFont(font, fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicRaceBonus(characteristics.get(i).getAbbreviature())
						+ "", FontFactory.getFont(font, fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph("    "
						+ characterPlayer.getCharacteristicSpecialBonus(characteristics.get(i).getAbbreviature()),
						FontFactory.getFont(font, fontSize));
			} else {
				p = new Paragraph("    " + EMPTY_VALUE, FontFactory.getFont(font, fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicTotalBonus(characteristics.get(i).getAbbreviature())
						+ "", FontFactory.getFont(font, fontSize));
			} else {
				p = new Paragraph("", FontFactory.getFont(font, fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			// El separador de caracteristicas
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

	private PdfPTable createPointsTable(String font, int fontSize) {
		float[] widths = { 0.32f, 0.32f, 0.35f };
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

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getTotalValue(SkillFactory.getSkill(Spanish.PHISICAL_DEVELOPMENT_SKILL))
					+ "", FontFactory.getFont(font, fontSize + 3));
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
		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getPowerPoints() + "", FontFactory.getFont(font, fontSize + 3));
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

		if (characterPlayer != null) {
			int value = characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.CONSTITUTION) / 2;
			if (value < 0) {
				value = 0;
			}
			p = new Paragraph(value + "", FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
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

		if (characterPlayer != null) {
			int value = characterPlayer.getBonusCharacteristicOfRealmOfMagic() / 2;
			if (value < 1) {
				value = 1;
			}
			p = new Paragraph(value + "", FontFactory.getFont(font, fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, FontFactory.getFont(font, fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			int puntos = Math.min(
					characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.CONSTITUTION) * 2,
					characterPlayer.getTotalValue(SkillFactory.getSkill(Spanish.PHISICAL_DEVELOPMENT_SKILL)));
			if (puntos < 1) {
				puntos = 1;
			}
			p = new Paragraph(puntos + "", FontFactory.getFont(font, fontSize));
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

		if (characterPlayer != null) {
			int puntos = characterPlayer.getPowerPoints() / 2;
			if (puntos < 1) {
				puntos = 1;
			}
			p = new Paragraph(puntos + "", FontFactory.getFont(font, fontSize));
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

	PdfPTable createSignature(String font, int fontSize) {
		PdfPTable tabla = new PdfPTable(1);
		Paragraph p;
		PdfPCell cell;

		p = new Paragraph("Generado con El Libro de Esher, herramienta para Rolemaster V"
				+ characterPlayer.getVersion() + "", FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(30f);
		tabla.addCell(cell);

		return tabla;
	}

	private PdfPTable createMainPageRightFrame(String font, int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tablaDerecha = new PdfPTable(1);

		cell = new PdfPCell(createRuneTable(font, fontSize));
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

		cell = new PdfPCell(createPointsTable(font, fontSize + 2));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(125);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaDerecha.addCell(cell);

		return tablaDerecha;
	}

	private void addMainTable(Document document, PdfWriter writer, String font, int fontSize) {
		PdfPCell cell;
		float[] widths = { 0.295f, 0.605f };
		PdfPTable table = new PdfPTable(widths);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setTotalWidth(document.getPageSize().getWidth() - 60);

		cell = new PdfPCell(createMainHeader(font, fontSize));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(58);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(createMainPageLeftFrame(font, fontSize));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(document.getPageSize().getHeight() - 145);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(createMainPageRightFrame(font, fontSize));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(document.getPageSize().getHeight() - 145);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(createSignature(font, fontSize));
		cell.setBorderWidth(0);
		cell.setColspan(2);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		// PdfPCell celda = new PdfPCell();
		table.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37, writer.getDirectContent());
		table.flushContent();
	}

	void characteristicsPage(Document document, PdfWriter writer, String font) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		int fontSize = 7;
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator + "RMHP1.png");
		addMainTable(document, writer, font, fontSize);
	}

	public String exportSpecials() {
		return ExportToText.exportSpecials(characterPlayer);
	}

	public String exportPerks() {
		return ExportToText.exportPerks(characterPlayer);
	}

	public String exportItems() {
		return ExportToText.exportItems(characterPlayer);
	}

	private void addSpecialText(Document document, PdfWriter writer, String font, int fontSize) {
		PdfPCell cell;
		Paragraph p;

		float[] widths = { 1 };
		PdfPTable tablaPagina = new PdfPTable(widths);
		tablaPagina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaPagina.setTotalWidth(document.getPageSize().getWidth() - 60);

		String texto = exportPerks() + "\n\n" + exportSpecials() + "\n" + exportItems();
		p = new Paragraph(texto, FontFactory.getFont(font, fontSize - 1));

		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		cell.setMinimumHeight(document.getPageSize().getHeight() - 90);
		tablaPagina.addCell(cell);

		cell = new PdfPCell(createSignature(font, fontSize - 2));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaPagina.addCell(cell);

		tablaPagina.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37, writer.getDirectContent());
		tablaPagina.flushContent();
	}

	private void addEmptyText(Document document, PdfWriter writer, String font, int fontSize) {
		PdfPCell cell;
		Paragraph p;

		float[] widths = { 1 };
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

		cell = new PdfPCell(createSignature(font, fontSize - 2));
		cell.setBorderWidth(0);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaPagina.addCell(cell);

		tablaPagina.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37, writer.getDirectContent());
		tablaPagina.flushContent();
	}

	void equipmentPage(Document document, PdfWriter writer, String font) {
		int fontSize = 9;
		document.newPage();
		addSpecialText(document, writer, font, fontSize);
	}

	private void whitePage(Document document, PdfWriter writer, String font) {
		int fontSize = 9;
		document.newPage();
		addEmptyText(document, writer, font, fontSize);
	}

	protected Image getNewRanksImage(int ranks) throws BadElementException, MalformedURLException, IOException {
		Image image;
		switch (ranks) {
		case 1:
			image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
					+ File.separator + "cuadros1.png");
			break;
		case 2:
			image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
					+ File.separator + "cuadros2.png");
			break;
		case 3:
			image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
					+ File.separator + "cuadros3.png");
			break;
		default:
			image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
					+ File.separator + "cuadros0.png");
		}
		image.scalePercent(28);

		return image;
	}

	public CharacterPlayer getCharacterPlayer() {
		return characterPlayer;
	}
}

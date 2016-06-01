package com.softwaremagico.librodeesher.pj.export.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwaremagico.files.Path;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.files.Version;
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
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.log.EsherLog;

public class PdfStandardSheet {
	private final static String EMPTY_VALUE = "_____";
	private final static String FONT_NAME = "ArchitectsDaughter.ttf";
	private final static int BORDER = 0;
	public final static int MOST_USED_SKILLS_LINES = 16;
	public final static int MOST_USED_ATTACKS_LINES = 6;
	private CharacterPlayer characterPlayer;
	protected boolean twoFaced;
	private boolean sortedSkills;
	private BaseFont handWrittingFont;
	private BaseFont baseFont;

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

	protected BaseFont getHandWrittingFont() {
		if (handWrittingFont == null) {
			Font font = FontFactory.getFont("/" + FONT_NAME, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f,
					Font.NORMAL, BaseColor.BLACK);
			handWrittingFont = font.getBaseFont();
		}
		return handWrittingFont;
	}

	protected BaseFont getDefaultFont() {
		if (baseFont == null) {
			String categoriesfont = FontFactory.HELVETICA;
			baseFont = FontFactory.getFont(categoriesfont).getBaseFont();
		}
		return baseFont;
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
			EsherLog.errorMessage(PdfStandardSheet.class.getName(), fnfe);
			throw fnfe;
		}
	}

	private void createPdf(Document document, PdfWriter writer) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		twoFaced = (characterPlayer.getPerks().size() > 0
				|| characterPlayer.getRace().getSpecials().size() > 0 || characterPlayer.getEquipment()
				.size() > 0);

		DocumentData(document, writer);
		document.open();
		characteristicsPage(document, writer);
		if (twoFaced) {
			equipmentPage(document, writer);
		}

		categoriesPage(document, writer);
		if (twoFaced) {
			whitePage(document, writer);
		}
		skillPage(document, writer);
		if (twoFaced) {
			whitePage(document, writer);
		}
		document.close();
	}

	private void whitePage() throws DocumentException, MalformedURLException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RMFesher.pdf"));
		DocumentData(document, writer);
		document.open();
		characteristicsPage(document, writer);
		categoriesPage(document, writer);
		skillPage(document, writer);
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

	void createBackgroundImage(Document document, String imagen) throws BadElementException,
			DocumentException, MalformedURLException, IOException {
		Image png;

		png = Image.getInstance(imagen);
		png.setAlignment(Image.MIDDLE | Image.UNDERLYING);
		png.scaleToFit((float) 760, (float) 760);
		document.add(png);
	}

	private void addCategoryTable(Document document, PdfWriter writer) {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorderWidth(BORDER);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		int fontSize = 8;
		table.setTotalWidth(document.getPageSize().getWidth() - 500);

		Paragraph p;
		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getName().substring(0,
					Math.max(characterPlayer.getName().length(), 20)), new Font(getHandWrittingFont(),
					fontSize));
		} else {
			p = new Paragraph("", new Font(getDefaultFont(), fontSize + 2));
		}
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 3 * document.getPageSize().getWidth() / 4 + 20, document.getPageSize()
				.getHeight() - 45, writer.getDirectContent());
		table.flushContent();
	}

	private void addCategoryValuesTable(Document document, PdfWriter writer) throws DocumentException,
			MalformedURLException, IOException {
		float[] widths = { 0.23f, 0.09f, 0.07f, 0.07f, 0.085f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f };
		PdfPTable table = new PdfPTable(widths);
		table.getDefaultCell().setBorderWidth(BORDER);
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

			if (characterPlayer != null) {
				if (characterPlayer.isCategoryUseful(category)
						|| i >= CategoryFactory.getAvailableCategories().size()) {

					// Add a category row
					Paragraph p;
					if (i < CategoryFactory.getAvailableCategories().size()) {
						text = category.getName();
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					} else {
						text = "_______________________";
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setMinimumHeight(11 + (i - omitidas) % 2);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingLeft(5f);
					table.addCell(cell);

					if (i < CategoryFactory.getAvailableCategories().size()) {
						text = category.getCharacterisitcsTags();
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					} else {
						text = "_______";
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()
							&& characterPlayer.getCategoryCost(category, 0) != null) {
						text = characterPlayer.getCategoryCost(category, 0).getCostTag();
						p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
					} else {
						text = "_________";
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}

					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (i < CategoryFactory.getAvailableCategories().size()) {
						if (category.getCategoryType().equals(CategoryType.STANDARD)) {
							if (characterPlayer != null) {
								text = characterPlayer.getPreviousRanks(category) + "";
								p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
							} else {
								text = EMPTY_VALUE;
								p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
							}
						} else {
							text = "na";
							p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
						}
					} else {
						text = EMPTY_VALUE;
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (i < CategoryFactory.getAvailableCategories().size()) {
						if (category.getCategoryType().equals(CategoryType.STANDARD)) {
							Image image;
							if (characterPlayer == null) {
								image = Image.getInstance(RolemasterFolderStructure.getSheetFolder()
										+ File.separator + "cuadros" + File.separator + "cuadros0.png");
								image.scalePercent(28);
							} else {
								image = getNewRanksImage(characterPlayer.getCurrentLevelRanks(category));
							}
							cell = new PdfPCell(image);
						}

						if (category.getCategoryType().equals(CategoryType.COMBINED)) {
							text = "*";
							p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
							cell = new PdfPCell(p);
						}
						if (category.getCategoryType().equals(CategoryType.LIMITED)
								|| category.getCategoryType().equals(CategoryType.SPECIAL)
								|| category.getCategoryType().equals(CategoryType.PPD)
								|| category.getCategoryType().equals(CategoryType.PD)) {
							text = "+";
							p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
							cell = new PdfPCell(p);
						}
					} else {
						Image image;
						image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator
								+ "cuadros" + File.separator + "cuadros0.png");
						image.scalePercent(28);
						cell = new PdfPCell(image);
					}
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
						text = characterPlayer.getRanksValue(category) + "";
						p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
					} else {
						text = EMPTY_VALUE;
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
						text = characterPlayer.getCharacteristicsBonus(category) + "";
						p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
					} else {
						text = EMPTY_VALUE;
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
						text = category.getBonus()
								+ characterPlayer.getProfession().getCategoryBonus(category.getName())
								+ characterPlayer.getRace().getBonus(category) + "";
						p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
					} else {
						text = EMPTY_VALUE;
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
						text = (characterPlayer.getHistorial().getBonus(category) + characterPlayer
								.getPerkBonus(category)) + "";
						String letter = "";

						if (characterPlayer.getHistorial().getBonus(category) > 0) {
							letter += "H";
						}

						if (characterPlayer.getPerkBonus(category) != 0) {
							letter += "T";
							if (characterPlayer.getConditionalPerkBonus(category) != 0) {
								letter += "*";
							}
						}

						if (!letter.equals("")) {
							text += " (" + letter + ")";
						}
						p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
					} else {
						text = EMPTY_VALUE;
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}

					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					// Magic Items
					if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
						text = characterPlayer.getItemBonus(category) + "";
						p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
					} else {
						text = EMPTY_VALUE;
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					if (characterPlayer != null && i < CategoryFactory.getAvailableCategories().size()) {
						text = characterPlayer.getTotalValue(category) + "";
						p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
					} else {
						text = EMPTY_VALUE;
						p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
					}
					cell = new PdfPCell(p);
					cell.setBorderWidth(BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					omitidas++;
				}
			}
		}

		cell = new PdfPCell(createFooter(fontSize + 1));
		cell.setBorderWidth(BORDER);
		cell.setColspan(11);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 97, writer.getDirectContent());
		table.flushContent();
	}

	private void addSkillNameTable(Document document, PdfWriter writer) {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorderWidth(BORDER);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		int fontSize = 8;
		table.setTotalWidth(document.getPageSize().getWidth() - 500);

		Paragraph p;
		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getName(), new Font(getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph("", new Font(getDefaultFont(), fontSize));
		}
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		table.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getCurrentLevelNumber() + "", new Font(getHandWrittingFont(),
					fontSize));
		} else {
			p = new Paragraph("", new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 3 * document.getPageSize().getWidth() / 4 - 80, document.getPageSize()
				.getHeight() - 65, writer.getDirectContent());
	}

	private void addSkillLine(Skill skill, int fontSize, PdfPTable table, int line)
			throws BadElementException, MalformedURLException, IOException {
		String text;
		PdfPCell cell;

		Paragraph p;
		if (characterPlayer != null) {
			text = (characterPlayer.getSkillNameWithSufix(skill)).trim();
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "___________________________________________";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setMinimumHeight(11 + line % 2);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(5f);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getPreviousRanks(skill) + "";
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "__";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			cell = new PdfPCell(getNewRanksImage(characterPlayer.getCurrentLevelRanks(skill)));
		} else {
			cell = new PdfPCell(getNewRanksImage(0));
		}
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = "  " + characterPlayer.getRanksValue(skill) + "";
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "   __";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getTotalValue(skill.getCategory()) + "";
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "__";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null && characterPlayer.getItemBonus(skill) != 0) {
			text = characterPlayer.getItemBonus(skill) + "";
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = EMPTY_VALUE;
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getSimpleBonus(skill) + "";
			String letter = "";
			if (characterPlayer != null && characterPlayer.getHistorial().getBonus(skill) > 0) {
				letter += "H";
			}

			if (characterPlayer != null
					&& (characterPlayer.getPerkBonus(skill) != 0 || characterPlayer
							.getConditionalPerkBonus(skill) != 0)) {
				letter += "T";
				if (characterPlayer.getConditionalPerkBonus(skill) != 0) {
					letter += "*";
				}
			}
			if (!letter.equals("")) {
				text += " (" + letter + ")";
			}
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "__";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}

		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			if (characterPlayer.getItemBonus(skill) > 0 || characterPlayer.getConditionalPerkBonus(skill) > 0) {
				text = characterPlayer.getTotalValue(skill) - characterPlayer.getItemBonus(skill)
						- characterPlayer.getConditionalPerkBonus(skill) + "/"
						+ characterPlayer.getTotalValue(skill) + "";
			} else {
				text = characterPlayer.getTotalValue(skill) + "";
			}
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = EMPTY_VALUE;
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	private void addSpecializedSkillLine(Skill skill, int fontSize, PdfPTable table, int i,
			int specializedIndex) throws BadElementException, MalformedURLException, IOException {
		String text;
		PdfPCell cell;
		Paragraph p;

		if (characterPlayer != null) {
			text = "  " + characterPlayer.getSkillSpecializations(skill).get(specializedIndex);
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "___________________________________________";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setMinimumHeight(11 + i % 2);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(5f);
		table.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell();
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = "  " + characterPlayer.getSpecializedRanksValue(skill) + "";
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "   __";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getTotalValue(skill.getCategory()) + "";
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "__";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getItemBonus(skill) + "";
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = EMPTY_VALUE;
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		if (text.equals("0")) {
			text = EMPTY_VALUE;
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getProfession().getSkillBonus(skill.getName())
					+ characterPlayer.getHistorial().getBonus(skill) + characterPlayer.getPerkBonus(skill)
					+ "";
			String letra = "";
			if (characterPlayer != null && characterPlayer.getHistorial().getBonus(skill) > 0) {
				letra += "H";
			}

			if (characterPlayer != null && characterPlayer.getPerkBonus(skill) != 0) {
				letra += "T";
				if (characterPlayer.getConditionalPerkBonus(skill) != 0) {
					letra += "*";
				}
			}
			if (!letra.equals("")) {
				text += "(" + letra + ")";
			}
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "__";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		if (characterPlayer != null) {
			if (characterPlayer.getItemBonus(skill) > 0 || characterPlayer.getConditionalPerkBonus(skill) > 0) {
				text = characterPlayer.getSpecializedTotalValue(skill) - characterPlayer.getItemBonus(skill)
						- characterPlayer.getConditionalPerkBonus(skill) + "/"
						+ characterPlayer.getSpecializedTotalValue(skill) + "";
			} else {
				text = characterPlayer.getSpecializedTotalValue(skill) + "";
			}
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = EMPTY_VALUE;
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	private void addEmptySkillLine(int fontSize, PdfPTable table, int i) throws BadElementException,
			MalformedURLException, IOException {
		PdfPCell cell;
		Paragraph p = new Paragraph("___________________________________________", new Font(getDefaultFont(),
				fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(11 + i % 2);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(5f);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(getNewRanksImage(0));
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph("   " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph("  " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph("  " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	protected void addNewSkillPage(PdfPTable table, Document document, PdfWriter writer, int fontSize)
			throws BadElementException, DocumentException, MalformedURLException, IOException {
		PdfPCell cell;
		// Cerramos pagina anterior.
		cell = new PdfPCell(createFooter(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setColspan(9);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);
		table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 129,
				writer.getDirectContent());
		table.flushContent();
		// Generamos el reverso en blanco.
		if (twoFaced) {
			whitePage(document, writer);
		}
		// Generamos una nueva.
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator
				+ "RMHP3.png");
	}

	private int newSkill(PdfPTable table, Document document, PdfWriter writer, int fontsize, float[] widths,
			List<Skill> skills, int alreadyAddedSkills) throws DocumentException, MalformedURLException,
			IOException {

		for (int j = 0; j < skills.size(); j++) {
			Skill skill = skills.get(j);
			if (alreadyAddedSkills > 56) {
				addNewSkillPage(table, document, writer, fontsize);
				table.flushContent();
				table.getDefaultCell().setBorderWidth(BORDER);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setTotalWidth(document.getPageSize().getWidth() - 65);
				alreadyAddedSkills = 0;
			}

			if (characterPlayer.isSkillInteresting(skill)) {
				alreadyAddedSkills++;
				addSkillLine(skill, fontsize, table, alreadyAddedSkills);
				for (int m = 0; m < characterPlayer.getSkillSpecializations(skill).size(); m++) {
					addSpecializedSkillLine(skill, fontsize, table, alreadyAddedSkills, m);
					if (alreadyAddedSkills > 56) {
						addNewSkillPage(table, document, writer, fontsize);
						table.flushContent();
						table.getDefaultCell().setBorderWidth(BORDER);
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

	private void addSkillTable(Document document, PdfWriter writer) throws DocumentException,
			MalformedURLException, IOException {
		float[] widths = { 0.36f, 0.07f, 0.085f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f };
		PdfPTable table = new PdfPTable(widths);
		table.getDefaultCell().setBorderWidth(BORDER);
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
					Category category = CategoryFactory.getCategory(CategoryFactory.getAvailableCategories()
							.get(i));
					category = characterPlayer.getCategory(category);
					skillLines = newSkill(table, document, writer, fontsize, widths, category.getSkills(),
							skillLines);
				}
				// Add skills sorted
			} else {
				List<Skill> sortedSkills = SkillFactory.getSkills();
				skillLines = newSkill(table, document, writer, fontsize, widths, sortedSkills, skillLines);
			}
		}
		while (skillLines < 57) {
			skillLines++;
			addEmptySkillLine(fontsize, table, skillLines);
		}

		cell = new PdfPCell(createFooter(fontsize + 1));
		cell.setBorderWidth(BORDER);
		cell.setColspan(9);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);

		table.writeSelectedRows(0, -1, 34, document.getPageSize().getHeight() - 129,
				writer.getDirectContent());
		table.flushContent();
	}

	private void skillPage(Document document, PdfWriter writer) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator
				+ "RMHP3.png");
		addSkillNameTable(document, writer);
		addSkillTable(document, writer);
	}

	private void categoriesPage(Document document, PdfWriter writer) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator
				+ "RMHP2.png");
		addCategoryTable(document, writer);
		addCategoryValuesTable(document, writer);
		// El reverso en blanco para no desentonar.
		// if(twoFaced) PersonajePaginaVacia(document, writer, font);
	}

	private PdfPTable createMainHeader(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		float[] widths = { 0.26f, 0.23f, 0.51f };
		PdfPTable table = new PdfPTable(widths);

		if (characterPlayer != null) {
			p = new Paragraph(Experience.getMinExperienceForLevel(characterPlayer.getCurrentLevelNumber())
					+ "", new Font(getHandWrittingFont(), fontSize + 2));
		} else {
			p = new Paragraph("", new Font(getHandWrittingFont(), fontSize + 2));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingBottom(10f);
		table.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getCurrentLevelNumber() + "", new Font(getHandWrittingFont(),
					fontSize + 2));
		} else {
			p = new Paragraph("", new Font(getHandWrittingFont(), fontSize + 2));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingBottom(10f);
		table.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getName(), new Font(getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph("", new Font(getHandWrittingFont(), fontSize + 2));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingRight(15f);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setPaddingTop(5f);
		table.addCell(cell);

		return table;
	}

	private PdfPTable createResistenceTable(int fontSize, ResistanceType resistence,
			CharacteristicsAbbreviature characteristicAbbreviature) {
		PdfPCell cell;
		Paragraph p;
		float[] widths = { 0.37f, 0.15f, 0.15f, 0.165f, 0.15f };
		PdfPTable tablaResistencia = new PdfPTable(widths);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencia.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getRace().getResistancesBonus(resistence) + "", new Font(
					getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph(" " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencia.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getCharacteristicTotalBonus(characteristicAbbreviature) * 3
					+ "", new Font(getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph(" " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tablaResistencia.addCell(cell);

		p = new Paragraph("", new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencia.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getResistanceBonus(resistence) + "    ", new Font(
					getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencia.addCell(cell);

		return tablaResistencia;
	}

	private PdfPTable createResistenceTable(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tablaResistencias = new PdfPTable(1);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setColspan(4);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		cell = new PdfPCell(createResistenceTable(fontSize, ResistanceType.CANALIZATION,
				CharacteristicsAbbreviature.INTUITION));

		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		cell = new PdfPCell(createResistenceTable(fontSize, ResistanceType.ESSENCE,
				CharacteristicsAbbreviature.EMPATHY));

		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		cell = new PdfPCell(createResistenceTable(fontSize, ResistanceType.MENTALISM,
				CharacteristicsAbbreviature.PRESENCE));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		cell = new PdfPCell(createResistenceTable(fontSize, ResistanceType.DISEASE,
				CharacteristicsAbbreviature.CONSTITUTION));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		cell = new PdfPCell(createResistenceTable(fontSize, ResistanceType.POISON,
				CharacteristicsAbbreviature.CONSTITUTION));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		cell = new PdfPCell(createResistenceTable(fontSize, ResistanceType.FEAR,
				CharacteristicsAbbreviature.SELFDISCIPLINE));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaResistencias.addCell(cell);

		return tablaResistencias;
	}

	private PdfPTable createBDTable(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tableDefenseive = new PdfPTable(1);

		if (characterPlayer != null) {
			p = new Paragraph(
					(characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.SPEED) * 3)
							+ "    ", new Font(getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph("_________", new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setColspan(4);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(30);
		cell.setPaddingRight(5);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableDefenseive.addCell(cell);

		return tableDefenseive;
	}

	private PdfPTable createTableRace(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tablaRaza = new PdfPTable(1);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(14);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaRaza.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getRace().getSoulDepartTime() + "", new Font(
					getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(14);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		tablaRaza.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getRace().getRestorationTime() + "   ", new Font(
					getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE + "             ", new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setColspan(2);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(14);
		cell.setPaddingRight(10f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tablaRaza.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getRace().getProgressionRankValuesAsString(
					ProgressionCostType.PHYSICAL_DEVELOPMENT), new Font(getHandWrittingFont(), fontSize + 2));
		} else {
			p = new Paragraph("   ", new Font(getHandWrittingFont(), fontSize + 2));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(23);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingBottom(5);
		tablaRaza.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(Race.getProgressionRankValuesAsString(characterPlayer
					.getPowerPointsDevelopmentCost()), new Font(getHandWrittingFont(), fontSize + 2));
		} else {
			p = new Paragraph("   ", new Font(getHandWrittingFont(), fontSize + 2));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(23);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingBottom(5);
		tablaRaza.addCell(cell);

		return tablaRaza;
	}

	private PdfPTable createTableInterpretation(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tabla = new PdfPTable(1);
		String text;
		String line;

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(13);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			// Apparence line length depending on digits.
			if (characterPlayer.getAppearance() < 10) {
				line = "  ________________________";
			} else if (characterPlayer.getAppearance() < 100) {
				line = "  _______________________";
			} else {
				line = "  ______________________";
			}
			text = "(" + characterPlayer.getAppearance() + ")";
			Paragraph p1 = new Paragraph(line, new Font(getDefaultFont(), fontSize));
			Paragraph p2 = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
			p = new Paragraph();
			p.add(p1);
			p.add(p2);
		} else {
			text = "____________________________";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setPaddingRight(6f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		p = new Paragraph("/" + characterPlayer.getRace().getExpectedLifeYears(), new Font(
				getHandWrittingFont(), fontSize - 1));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			text = "   " + characterPlayer.getSex().getTag();
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = "_____________";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(12);
		cell.setPaddingLeft(26f);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabla.addCell(cell);

		return tabla;
	}

	private PdfPTable createHistoryTable(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tabla = new PdfPTable(1);
		String text;

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(16);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		for (int i = 0; i < 8; i++) {
			p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setMinimumHeight(11);
			cell.setPaddingRight(5f);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tabla.addCell(cell);
		}

		String line = "______________________________";
		if (characterPlayer != null) {
			text = characterPlayer.getCulture().getName();
			p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
		} else {
			text = line;
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setPaddingRight(6f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		return tabla;
	}

	private PdfPTable createCharacterDataTable(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tabla = new PdfPTable(1);
		String texto;

		if (characterPlayer != null) {
			texto = characterPlayer.getRace().getName();
		} else {
			texto = "";
		}
		p = new Paragraph(texto, new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(12);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			texto = characterPlayer.getProfession().getName();
		} else {
			texto = "";
		}
		p = new Paragraph(texto, new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(12);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(12);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			texto = "";
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
		p = new Paragraph(texto, new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
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
		p = new Paragraph(texto, new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(12);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		return tabla;
	}

	private PdfPTable createArmourTable(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tabla = new PdfPTable(1);
		String text;

		text = "";
		if (characterPlayer.getRace().getNaturalArmourType() != 1) {
			text = "(" + characterPlayer.getRace().getNaturalArmourType() + ")";
			String line = " ______________________";
			Paragraph p1 = new Paragraph(text, new Font(getHandWrittingFont(), fontSize));
			Paragraph p2 = new Paragraph(line, new Font(getDefaultFont(), fontSize));
			p = new Paragraph();
			p.add(p1);
			p.add(p2);
		} else {
			text = "_________________________";
			p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(13);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		text = "";
		p = new Paragraph(text, new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(11);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			text = characterPlayer.getMovementCapacity() + "";
		} else {
			text = "";
		}
		p = new Paragraph(text, new Font(getHandWrittingFont(), fontSize + 1));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(12);
		cell.setPaddingRight(5f);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tabla.addCell(cell);

		return tabla;
	}

	private PdfPTable createMainPageLeftFrame(int fontSize) {
		PdfPCell cell;
		PdfPTable tablaIzquierda = new PdfPTable(1);

		cell = new PdfPCell(createCharacterDataTable(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(60);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createArmourTable(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(65);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createBDTable(fontSize + 1));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(75);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createResistenceTable(fontSize - 1));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(130);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createTableRace(fontSize + 1));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(86);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createTableInterpretation(fontSize + 1));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(138);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		cell = new PdfPCell(createHistoryTable(fontSize + 1));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(100);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaIzquierda.addCell(cell);

		return tablaIzquierda;
	}

	private PdfPTable createRuneTable(int fontSize) {
		float[] widths = { 0.79f, 0.21f };
		PdfPTable tableFrame = new PdfPTable(widths);
		Paragraph p;
		PdfPCell cell;

		cell = new PdfPCell(createCharacteristicsTable(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		return tableFrame;
	}

	private PdfPTable createMostUsedAttacksTable(List<Skill> favouriteAttacks, int fontSize) {
		PdfPTable tableFrame = new PdfPTable(1);
		Paragraph p;
		PdfPCell cell;

		// Header
		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(25);
		cell.setColspan(2);
		tableFrame.addCell(cell);

		int favouriteSkillsNumber = 0;
		for (int i = 0; i < (favouriteAttacks.size() < MOST_USED_SKILLS_LINES ? favouriteAttacks.size()
				: MOST_USED_SKILLS_LINES); i++) {
			cell = new PdfPCell(createMostUsedAttackLine(
					" " + TxtSheet.getNameSpecificLength(favouriteAttacks.get(i).getName(), 25),
					characterPlayer.getTotalRanks(favouriteAttacks.get(i)) + "",
					characterPlayer.getTotalValue(favouriteAttacks.get(i)) + "", getHandWrittingFont(),
					fontSize));
			cell.setBorderWidth(BORDER);
			cell.setMinimumHeight((float) 8);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFrame.addCell(cell);
			favouriteSkillsNumber++;
		}

		for (int i = favouriteSkillsNumber; i < MOST_USED_ATTACKS_LINES; i++) {
			cell = new PdfPCell(createMostUsedAttackLine("_______________________", "______", "_______",
					getDefaultFont(), fontSize));
			cell.setBorderWidth(BORDER);
			cell.setMinimumHeight((float) 9);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFrame.addCell(cell);
		}

		return tableFrame;
	}

	private PdfPTable createMostUsedAttackLine(String skillName, String skillRanks, String skillTotal,
			BaseFont font, int fontSize) {
		float[] widths = { 3.1f, 1f, 1f, 1f, 5f };
		PdfPTable tableFrame = new PdfPTable(widths);

		// Name
		Paragraph p = new Paragraph(skillName, new Font(font, fontSize));
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 9);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableFrame.addCell(cell);

		// Ranks
		p = new Paragraph(skillRanks, new Font(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 9);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		// Total
		p = new Paragraph(skillTotal, new Font(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 9);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		// CriticalFailure
		p = new Paragraph("______", new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 9);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		// Range
		p = new Paragraph("_______________________________________", new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 9);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableFrame.addCell(cell);

		return tableFrame;
	}

	private PdfPTable createMostUsedSkillsTable(int fontSize) {
		float[] widths = { 0.49f, 0.51f };
		PdfPTable tableFrame = new PdfPTable(widths);
		Paragraph p;
		PdfPCell cell;

		// Header
		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(25);
		cell.setColspan(2);
		tableFrame.addCell(cell);

		List<Skill> skillsToAdd = new ArrayList<>();
		skillsToAdd.addAll(characterPlayer.getFavouriteNoOffensiveSkills());
		cell = new PdfPCell(createMostUsedSkillsColumn(skillsToAdd, fontSize));
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		cell = new PdfPCell(createMostUsedSkillsColumn(skillsToAdd, fontSize));
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		return tableFrame;
	}

	private PdfPTable createMostUsedSkillsColumn(List<Skill> favouriteSkills, int fontSize) {
		PdfPTable tableFrame = new PdfPTable(1);

		int skillsShowed = 0;
		List<Skill> skillsToAdd = new ArrayList<>(favouriteSkills);
		for (int i = 0; i < (skillsToAdd.size() < MOST_USED_SKILLS_LINES ? skillsToAdd.size()
				: MOST_USED_SKILLS_LINES); i++) {
			PdfPCell cell = new PdfPCell(createMostUsedSkillLine(
					" " + TxtSheet.getNameSpecificLength(skillsToAdd.get(i).getName(), 31),
					characterPlayer.getTotalRanks(skillsToAdd.get(i)) + "",
					characterPlayer.getTotalValue(skillsToAdd.get(i)) + "", getHandWrittingFont(), fontSize));
			cell.setBorderWidth(BORDER);
			cell.setMinimumHeight((float) 8);
			tableFrame.addCell(cell);
			favouriteSkills.remove(skillsToAdd.get(i));
			skillsShowed++;
		}

		for (int i = skillsShowed; i < MOST_USED_SKILLS_LINES; i++) {
			PdfPCell cell = new PdfPCell(createMostUsedSkillLine(" ____________________________", "_____",
					"_____", getDefaultFont(), fontSize));
			cell.setBorderWidth(BORDER);
			cell.setMinimumHeight((float) 8);
			tableFrame.addCell(cell);
		}

		return tableFrame;
	}

	private PdfPTable createMostUsedSkillLine(String skillName, String skillRanks, String skillTotal,
			BaseFont font, int fontSize) {
		float[] widths = { 4f, 1f, 1f };
		PdfPTable tableFrame = new PdfPTable(widths);

		// Name
		Paragraph p = new Paragraph(skillName, new Font(font, fontSize));
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 8);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableFrame.addCell(cell);

		// Ranks
		p = new Paragraph(skillRanks, new Font(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 8);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		// Total
		p = new Paragraph(skillTotal, new Font(font, fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight((float) 8);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableFrame.addCell(cell);

		return tableFrame;
	}

	private PdfPTable createCharacteristicsTable(int fontSize) {
		float[] widths = { 0.30f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.20f };
		PdfPTable tablaCaracteristicas = new PdfPTable(widths);
		Paragraph p;
		PdfPCell cell;

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight((float) 20);
		cell.setColspan(10);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCaracteristicas.addCell(cell);

		int i = 0;
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setMinimumHeight((float) 13.5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicTemporalValue(characteristic
						.getAbbreviature()) + "", new Font(getHandWrittingFont(), fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicPotentialValue(characteristic
						.getAbbreviature()) + "", new Font(getHandWrittingFont(), fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicTemporalBonus(characteristic
						.getAbbreviature()) + "", new Font(getHandWrittingFont(), fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(
						characterPlayer.getCharacteristicRaceBonus(characteristic.getAbbreviature()) + "",
						new Font(getHandWrittingFont(), fontSize));
			} else {
				p = new Paragraph("  " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph("    "
						+ characterPlayer.getCharacteristicSpecialBonus(characteristic.getAbbreviature()),
						new Font(getHandWrittingFont(), fontSize));
			} else {
				p = new Paragraph("    " + EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			if (characterPlayer != null) {
				p = new Paragraph(characterPlayer.getCharacteristicTotalBonus(characteristic
						.getAbbreviature()) + "", new Font(getHandWrittingFont(), fontSize));
			} else {
				p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
			}
			cell = new PdfPCell(p);
			cell.setBorderWidth(BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCaracteristicas.addCell(cell);

			// El separador de caracteristicas
			if (i == 4) {
				p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
				cell = new PdfPCell(p);
				cell.setBorderWidth(BORDER);
				cell.setMinimumHeight((float) 6);
				cell.setColspan(10);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tablaCaracteristicas.addCell(cell);
			}
			i++;
		}
		return tablaCaracteristicas;
	}

	private PdfPTable createPointsTable(int fontSize) {
		float[] widths = { 0.32f, 0.32f, 0.35f };
		PdfPTable tabla = new PdfPTable(widths);
		Paragraph p;
		PdfPCell cell;

		p = new Paragraph(" ", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(30);
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(characterPlayer.getTotalValue(SkillFactory
					.getSkill(Spanish.PHISICAL_DEVELOPMENT_SKILL)) + "", new Font(getHandWrittingFont(),
					fontSize + 3));
		} else {
			p = new Paragraph("", new Font(getHandWrittingFont(), fontSize + 3));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(cell);
		if (characterPlayer != null) {
			p = new Paragraph(Math.max(characterPlayer.getPowerPoints(), 0) + "", new Font(
					getHandWrittingFont(), fontSize + 3));
		} else {
			p = new Paragraph("", new Font(getHandWrittingFont(), fontSize + 3));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(cell);

		p = new Paragraph(" ", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(10);
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			int value = characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.CONSTITUTION) / 2;
			if (value < 0) {
				value = 0;
			}
			p = new Paragraph(value + "", new Font(getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setMinimumHeight(25);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		tabla.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingRight(30f);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(Math.max(characterPlayer.getBonusCharacteristicOfRealmOfMagic() / 2, 1) + "",
					new Font(getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph(EMPTY_VALUE, new Font(getDefaultFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			int puntos = Math
					.min(characterPlayer
							.getCharacteristicTotalBonus(CharacteristicsAbbreviature.CONSTITUTION) * 2,
							characterPlayer.getTotalValue(SkillFactory
									.getSkill(Spanish.PHISICAL_DEVELOPMENT_SKILL)));
			if (puntos < 1) {
				puntos = 1;
			}
			p = new Paragraph(puntos + "", new Font(getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph("__", new Font(getHandWrittingFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setMinimumHeight(20);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		tabla.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingRight(30f);
		tabla.addCell(cell);

		if (characterPlayer != null) {
			p = new Paragraph(Math.max(characterPlayer.getPowerPoints() / 2, 1) + "", new Font(
					getHandWrittingFont(), fontSize));
		} else {
			p = new Paragraph("__", new Font(getHandWrittingFont(), fontSize));
		}
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		tabla.addCell(cell);

		return tabla;
	}

	PdfPTable createFooter(int fontSize) {
		PdfPTable tabla = new PdfPTable(1);
		Paragraph p;
		PdfPCell cell;

		p = new Paragraph("Generado con El Libro de Esher, herramienta para Rolemaster V"
				+ Version.getVersion() + "", new Font(getDefaultFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(30f);
		tabla.addCell(cell);

		return tabla;
	}

	private PdfPTable createMainPageRightFrame(int fontSize) {
		PdfPCell cell;
		Paragraph p;
		PdfPTable tablaDerecha = new PdfPTable(1);

		cell = new PdfPCell(createRuneTable(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(165);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaDerecha.addCell(cell);

		cell = new PdfPCell(createMostUsedSkillsTable(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(208);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaDerecha.addCell(cell);

		cell = new PdfPCell(createMostUsedAttacksTable(characterPlayer.getFavouriteOffensiveSkills(),
				fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(100);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaDerecha.addCell(cell);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));
		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(100);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaDerecha.addCell(cell);

		cell = new PdfPCell(createPointsTable(fontSize + 2));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(125);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaDerecha.addCell(cell);

		return tablaDerecha;
	}

	private void addMainTable(Document document, PdfWriter writer, int fontSize) {
		PdfPCell cell;
		float[] widths = { 0.295f, 0.605f };
		PdfPTable table = new PdfPTable(widths);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setTotalWidth(document.getPageSize().getWidth() - 60);

		cell = new PdfPCell(createMainHeader(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(58);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(createMainPageLeftFrame(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(document.getPageSize().getHeight() - 145);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(createMainPageRightFrame(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(document.getPageSize().getHeight() - 145);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(createFooter(fontSize));
		cell.setBorderWidth(BORDER);
		cell.setColspan(2);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		// PdfPCell celda = new PdfPCell();
		table.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37, writer.getDirectContent());
		table.flushContent();
	}

	void characteristicsPage(Document document, PdfWriter writer) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		int fontSize = 7;
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator
				+ "RMHP1.png");
		addMainTable(document, writer, fontSize);
	}

	public String exportSpecials() {
		return TxtSheet.exportSpecials(characterPlayer);
	}

	public String exportPerks() {
		return TxtSheet.exportPerks(characterPlayer);
	}

	public String exportItems() {
		return TxtSheet.exportItems(characterPlayer);
	}

	private void addSpecialText(Document document, PdfWriter writer, int fontSize) {
		PdfPCell cell;
		Paragraph p;

		float[] widths = { 1 };
		PdfPTable tablaPagina = new PdfPTable(widths);
		tablaPagina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaPagina.setTotalWidth(document.getPageSize().getWidth() - 60);

		String texto = exportPerks() + "\n\n" + exportSpecials() + "\n" + exportItems();
		p = new Paragraph(texto, new Font(getHandWrittingFont(), fontSize - 1));

		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		cell.setMinimumHeight(document.getPageSize().getHeight() - 90);
		tablaPagina.addCell(cell);

		cell = new PdfPCell(createFooter(fontSize - 2));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaPagina.addCell(cell);

		tablaPagina.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37,
				writer.getDirectContent());
		tablaPagina.flushContent();
	}

	private void addEmptyText(Document document, PdfWriter writer, int fontSize) {
		PdfPCell cell;
		Paragraph p;

		float[] widths = { 1 };
		PdfPTable tablaPagina = new PdfPTable(widths);
		tablaPagina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaPagina.setTotalWidth(document.getPageSize().getWidth() - 60);

		p = new Paragraph("", new Font(getHandWrittingFont(), fontSize));

		cell = new PdfPCell(p);
		cell.setBorderWidth(BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(30f);
		cell.setMinimumHeight(document.getPageSize().getHeight() - 90);
		tablaPagina.addCell(cell);

		cell = new PdfPCell(createFooter(fontSize - 2));
		cell.setBorderWidth(BORDER);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaPagina.addCell(cell);

		tablaPagina.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37,
				writer.getDirectContent());
		tablaPagina.flushContent();
	}

	void equipmentPage(Document document, PdfWriter writer) {
		int fontSize = 9;
		document.newPage();
		addSpecialText(document, writer, fontSize);
	}

	private void whitePage(Document document, PdfWriter writer) {
		int fontSize = 9;
		document.newPage();
		addEmptyText(document, writer, fontSize);
	}

	protected Image getNewRanksImage(int ranks) throws BadElementException, MalformedURLException,
			IOException {
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

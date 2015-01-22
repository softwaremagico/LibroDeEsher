package com.softwaremagico.librodeesher.pj.export.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

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
import com.softwaremagico.files.Path;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.log.EsherLog;

public class PdfCombinedSheet extends PdfStandardSheet {
	private int lines = 0;
	private int showed = 0;
	private final static int MAX_LINES = 71;
	private int column = 0;
	private static int fontSize = 6;
	private int page = 1;
	private PdfPTable tableColumn;
	private PdfPTable table;
	private int categoriesToShown = 0;
	private int remainingLines = 0;
	private int pages = 0;
	private int writtenPages = 0;

	public PdfCombinedSheet(CharacterPlayer characterPlayer, String path) throws MalformedURLException,
			DocumentException, IOException {
		super(characterPlayer, path, false);
	}

	@Override
	public void characterPDF(String path) throws DocumentException, MalformedURLException, IOException {
		Document document = new Document(PageSize.A4);
		float[] mainWidths = { 0.45f, 0.45f };
		table = new PdfPTable(mainWidths);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setTotalWidth(document.getPageSize().getWidth() - 60);

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

	private void createPdf(Document document, PdfWriter writer) throws BadElementException, MalformedURLException,
			DocumentException, IOException {
		String font = FontFactory.HELVETICA;

		twoFaced = (getCharacterPlayer().getPerks().size() > 0
				|| getCharacterPlayer().getRace().getSpecials().size() > 0 || getCharacterPlayer().getEquipment()
				.size() > 0);

		countLines();

		DocumentData(document, writer);
		document.open();
		characteristicsPage(document, writer, font);
		if (twoFaced) {
			equipmentPage(document, writer, font);
		}
		combinedPage(document, writer, font);
		document.close();
	}

	private void combinedPage(Document document, PdfWriter writer, String font) throws BadElementException,
			MalformedURLException, DocumentException, IOException {
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator + "RMHPComb.png");
		combinedSkillsPage(document, writer, font);
	}

	private void combinedSkillsPage(Document document, PdfWriter writer, String font) throws MalformedURLException,
			BadElementException, IOException, DocumentException {
		PdfPCell cell;
		Category category = null;

		cell = new PdfPCell(createHeader());
		cell.setColspan(2);
		table.addCell(cell);

		tableColumn = new PdfPTable(1);

		for (int i = 0; i < CategoryFactory.getAvailableCategories().size(); i++) {
			category = CategoryFactory.getCategory(CategoryFactory.getAvailableCategories().get(i));
			category =  getCharacterPlayer().getCategory(category);
			if (getCharacterPlayer().isCategoryUseful(category)) {
				categoryCell(writer, document, category, font);
				skillsList(document, writer, category, font, i == CategoryFactory.getAvailableCategories().size() - 1);
			}
		}

		if (column == 0) {
			// Add column if necesary.
			cell = new PdfPCell();
			cell.setBorderWidth(1);
			cell.setColspan(1);
			cell.setMinimumHeight(document.getPageSize().getWidth() - 60);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}

		closePage(document, writer, font);
	}

	private PdfPTable skillsTitle(String font) throws BadElementException, MalformedURLException, IOException,
			DocumentException {
		Paragraph p;
		PdfPCell cell;
		float[] widths = { 0.32f, 0.05f, 0.15f, 0.08f, 0.10f, 0.10f, 0.10f, 0.10f };
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

	private void skillsList(Document document, PdfWriter writer, Category cat, String font, boolean last)
			throws BadElementException, MalformedURLException, IOException, DocumentException {
		PdfPCell cell;
		Skill hab;
		PdfPTable tableHab;
		int added = 0;
		showed = 0;
		boolean lastFilled = false;

		/* TITULO */
		tableHab = skillsTitle(font);
		cell = new PdfPCell(tableHab);
		cell.setBorderWidth(0);
		tableColumn.addCell(cell);
		lines++;

		newColumnRequired(document, writer, font);

		// Skills list.
		for (int i = 0; i < cat.getSkills().size(); i++) {
			hab = cat.getSkills().get(i);
			if (getCharacterPlayer().isSkillInteresting(hab)) {
				tableHab = skillLine(hab, font);
				cell = new PdfPCell(tableHab);
				cell.setBorderWidth(0);
				tableColumn.addCell(cell);
				lines++;
				added++;

				newColumnRequired(document, writer, font);
			}
		}

		// Last lines
		if (lines > MAX_LINES - 5) {
			for (int i = lines; i <= MAX_LINES; i++) {
				tableHab = emptySkillList(cat, font);
				cell = new PdfPCell(tableHab);
				cell.setBorderWidth(0);
				tableColumn.addCell(cell);
				lines++;
				added++;
				remainingLines--;
			}
			newColumnRequired(document, writer, font);
			if (last) {
				lastFilled = true;
			}
		}

		// At least one skill per category
		if (added == 0) {
			tableHab = emptySkillList(cat, font);
			cell = new PdfPCell(tableHab);
			cell.setBorderWidth(0);
			tableColumn.addCell(cell);
			lines++;
			added++;
			newColumnRequired(document, writer, font);
		}

		// remaining space shared among categories
		if (remainingLines > 0 && categoriesToShown > 0) {
			int add = remainingLines / categoriesToShown;
			for (int i = 0; i < add; i++) {
				tableHab = emptySkillList(cat, font);
				cell = new PdfPCell(tableHab);
				cell.setBorderWidth(0);
				tableColumn.addCell(cell);
				lines++;
				added++;
				remainingLines--;
				newColumnRequired(document, writer, font);
			}
		}

		// Last category
		if (lines < MAX_LINES && last && !lastFilled) {
			for (int j = column; j < 2; j++) {
				for (int i = lines; i <= MAX_LINES; i++) {
					tableHab = emptySkillList(cat, font);
					cell = new PdfPCell(tableHab);
					cell.setBorderWidth(0);
					tableColumn.addCell(cell);
					lines++;
					added++;
					remainingLines--;
				}
				newColumnRequired(document, writer, font);
			}
		}
	}

	private PdfPTable skillLine(Skill skill, String font) throws BadElementException, MalformedURLException,
			IOException, DocumentException {
		Paragraph p;
		PdfPCell cell;
		BaseColor background;

		if (showed % 2 == 1) {
			background = BaseColor.WHITE;
		} else {
			background = BaseColor.LIGHT_GRAY;
		}
		showed++;

		float[] widths = { 0.32f, 0.05f, 0.15f, 0.08f, 0.10f, 0.10f, 0.10f, 0.10f };
		PdfPTable tableHab = new PdfPTable(widths);
		tableHab.flushContent();

		p = new Paragraph(skill.getName(30) + " " + skill.getSkillType().getTag(), FontFactory.getFont(font,
				fontSize - 1));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(10);
		// cell.setBorderWidth(0);
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

		p = new Paragraph(getCharacterPlayer().getPreviousRanks(skill) + "", FontFactory.getFont(font, fontSize - 1));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(10);
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		tableHab.addCell(cell);

		cell = new PdfPCell(getNewRanksImage(getCharacterPlayer().getCurrentLevelRanks(skill)));
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		tableHab.addCell(cell);

		p = new Paragraph(getCharacterPlayer().getRanksValue(skill) + "", FontFactory.getFont(font, fontSize - 1));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(10);
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		tableHab.addCell(cell);

		p = new Paragraph(getCharacterPlayer().getTotalValue(skill.getCategory()) + "", FontFactory.getFont(font,
				fontSize - 1));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(10);
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		tableHab.addCell(cell);

		p = new Paragraph(getCharacterPlayer().getItemBonus(skill) + "", FontFactory.getFont(font, fontSize - 1));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(10);
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		tableHab.addCell(cell);

		String text = getCharacterPlayer().getProfession().getSkillBonus(skill.getName())
				+ getCharacterPlayer().getHistorial().getBonus(skill) + getCharacterPlayer().getPerkBonus(skill) + "";

		String letter = "";
		if (getCharacterPlayer().getHistorial().getBonus(skill) > 0) {
			letter += "H";
		}

		if (getCharacterPlayer().getPerkBonus(skill) != 0) {
			letter += "T";
			if (getCharacterPlayer().getConditionalPerkBonus(skill) != 0) {
				letter += "*";
			}
		}
		if (!letter.equals("")) {
			text += "(" + letter + ")";
		}
		p = new Paragraph(text, FontFactory.getFont(font, fontSize - 1));
		cell = new PdfPCell(p);
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableHab.addCell(cell);

		if (getCharacterPlayer().getItemBonus(skill) > 0 || getCharacterPlayer().getConditionalPerkBonus(skill) > 0) {
			text = getCharacterPlayer().getTotalValue(skill) - getCharacterPlayer().getItemBonus(skill)
					- getCharacterPlayer().getConditionalPerkBonus(skill) + "/"
					+ getCharacterPlayer().getTotalValue(skill) + "";
		} else {
			text = getCharacterPlayer().getTotalValue(skill) + "";
		}

		p = new Paragraph(text + "", FontFactory.getFont(font, fontSize - 1));
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

	private PdfPTable emptySkillList(Category cat, String font) throws BadElementException, MalformedURLException,
			IOException, DocumentException {
		Paragraph p;
		PdfPCell cell;
		BaseColor background;

		float[] widths = { 0.31f, 0.06f, 0.15f, 0.08f, 0.10f, 0.10f, 0.10f, 0.10f };

		if (showed % 2 == 1) {
			background = BaseColor.WHITE;
		} else {
			background = BaseColor.LIGHT_GRAY;
		}
		showed++;

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

		Image image = Image.getInstance(RolemasterFolderStructure.getSheetFolder() + File.separator + "cuadros"
				+ File.separator + "cuadros0.png");
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

		p = new Paragraph(getCharacterPlayer().getTotalValue(cat) + "", FontFactory.getFont(font, fontSize - 1));
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

	private PdfPCell categoryCell(PdfWriter writer, Document document, Category category, String font)
			throws MalformedURLException, BadElementException, IOException, DocumentException {
		Paragraph p;
		PdfPCell cell;
		float[] widths = { 0.23f, 0.23f, 0.14f, 0.11f, 0.13f, 0.16f };
		PdfPTable categoryTable = new PdfPTable(widths);
		categoryTable.flushContent();

		p = new Paragraph(category.getName(35).toUpperCase(), FontFactory.getFont(font, fontSize - 1));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		p = new Paragraph(category.getCharacterisitcsTags(), FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		p = new Paragraph(getCharacterPlayer().getCategoryCost(category, 0).getCostTag(), FontFactory.getFont(font,
				fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		p = new Paragraph("Rang: " + getCharacterPlayer().getPreviousRanks(category) + "", FontFactory.getFont(font,
				fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		if (category.getCategoryType().equals(CategoryType.STANDARD)) {
			cell = new PdfPCell(getNewRanksImage(getCharacterPlayer().getCurrentLevelRanks(category)));
		} else {
			String text;
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
		}

		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderWidth(0);
		categoryTable.addCell(cell);

		// Second line
		p = new Paragraph("Bonif. Rango: " + getCharacterPlayer().getRanksValue(category), FontFactory.getFont(font,
				fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		p = new Paragraph("Caract: " + getCharacterPlayer().getCharacteristicsBonus(category), FontFactory.getFont(
				font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		String text = (getCharacterPlayer().getHistorial().getBonus(category) + getCharacterPlayer().getPerkBonus(
				category))
				+ "";
		String letter = "";

		if (getCharacterPlayer().getHistorial().getBonus(category) > 0) {
			letter += "H";
		}

		if (getCharacterPlayer().getPerkBonus(category) != 0) {
			letter += "T";
			if (getCharacterPlayer().getConditionalPerkBonus(category) != 0) {
				letter += "*";
			}
		}

		if (!letter.equals("")) {
			text += "(" + letter + ")";
		}

		p = new Paragraph("Esp: " + text, FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		p = new Paragraph("Prof: "
				+ (category.getBonus() + getCharacterPlayer().getProfession().getCategoryBonus(category.getName())),
				FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		p = new Paragraph("Obj: " + getCharacterPlayer().getItemBonus(category), FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		p = new Paragraph("Total: " + getCharacterPlayer().getTotalValue(category), FontFactory.getFont(font, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);
		categoryTable.addCell(cell);

		lines += 3;

		cell = new PdfPCell(categoryTable);
		cell.setBorderWidth(1);
		cell.setColspan(1);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableColumn.addCell(cell);

		newColumnRequired(document, writer, font);

		categoriesToShown--;
		return cell;
	}

	private PdfPCell createHeader() {
		float[] widths = { 0.50f, 0.50f };
		PdfPTable tableC = new PdfPTable(widths);
		tableC.flushContent();
		PdfPCell cell;

		cell = new PdfPCell(leftHeader());
		cell.setBorderWidth(1);
		cell.setColspan(1);
		tableC.addCell(cell);

		cell = new PdfPCell(rightHeader());
		cell.setBorderWidth(1);
		cell.setColspan(1);
		tableC.addCell(cell);

		cell = new PdfPCell(tableC);
		return cell;
	}

	private PdfPCell leftHeader() {
		PdfPCell cell;
		Font f = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, fontSize + 9);
		f.setColor(BaseColor.WHITE);

		Paragraph p = new Paragraph("Hoja Combinada de Habilidades (" + (page + 1) + " de " + pages + ")", f);
		// Paragraph p = new Paragraph("Hoja Combinada de Habilidades (" + page
		// + ")", f);
		cell = new PdfPCell(p);
		cell.setMinimumHeight(30);
		cell.setBackgroundColor(BaseColor.BLACK);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);

		return cell;
	}

	private PdfPCell rightHeader() {
		PdfPTable tableC = new PdfPTable(1);
		tableC.flushContent();
		PdfPCell cell;

		Paragraph p = new Paragraph("Personaje: " + getCharacterPlayer().getName(), FontFactory.getFont(
				FontFactory.HELVETICA, fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);
		tableC.addCell(cell);

		p = new Paragraph("Jugador: ___________________________________", FontFactory.getFont(FontFactory.HELVETICA,
				fontSize));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(5f);
		tableC.addCell(cell);

		cell = new PdfPCell(tableC);
		return cell;
	}

	private void closePage(Document document, PdfWriter writer, String font) {
		PdfPCell cell = new PdfPCell(createFooter(font, fontSize));
		cell.setBorderWidth(0);
		cell.setColspan(2);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);
		table.writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37, writer.getDirectContent());
	}

	private boolean newColumnRequired(Document document, PdfWriter writer, String font) throws BadElementException,
			MalformedURLException, IOException, DocumentException {
		PdfPCell cell;
		if (lines > MAX_LINES) {
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
				if (writtenPages < pages - 1) {
					addNewSkillPage(document, writer, font);
				}
			}
			return true;
		}
		return false;
	}

	protected void addNewSkillPage(Document document, PdfWriter writer, String font) throws BadElementException,
			MalformedURLException, IOException, DocumentException {
		PdfPCell cell;

		// Cerramos pagina anterior.
		closePage(document, writer, font);
		writtenPages++;

		// Create a new one
		float[] widths = { 0.45f, 0.45f };
		table = new PdfPTable(widths);
		table.flushContent();
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setTotalWidth(document.getPageSize().getWidth() - 60);

		document.newPage();
		page++;
		try {
			createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator
					+ "RMHPComb.png");
		} catch (DocumentException ex) {
			EsherLog.errorMessage(PdfCombinedSheet.class.getName(), ex);
		}

		cell = new PdfPCell(createHeader());
		cell.setColspan(2);
		table.addCell(cell);

		column = 0;
	}

	private void countLines() {
		int predictedLines = 0;
		int categoriesToShow = 0;
		int skillsToShow = 0;
		int categoriesWithoutSkills = 0;

		for (int i = 0; i < CategoryFactory.getAvailableCategories().size(); i++) {
			Category category = getCharacterPlayer().getCategory(CategoryFactory.getAvailableCategories().get(i));
			if (getCharacterPlayer().isCategoryUseful(category)) {
				categoriesToShow++;

				int added = 0;
				for (int j = 0; j < category.getSkills().size(); j++) {
					Skill skill = category.getSkills().get(j);
					if (getCharacterPlayer().isSkillInteresting(skill)) {
						skillsToShow++;
						added++;
					}
				}

				if (added == 0) {
					categoriesWithoutSkills++;
				}

			}
		}

		predictedLines = categoriesToShow * 4 + skillsToShow + categoriesWithoutSkills;
		remainingLines = (MAX_LINES * 2) - (predictedLines % (MAX_LINES * 2));
		pages = (int) Math.ceil((double) predictedLines / (double) (MAX_LINES * 2));
	}
}

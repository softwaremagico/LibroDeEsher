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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwaremagico.files.Path;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.log.EsherLog;

public class PdfCombinedSheet2Columns extends PdfStandardSheet {
	private final static int MAX_LINES_PER_COLUMN = 71;
	private final static int CATEGORY_SIZE = 4;
	private final static int SKILL_SIZE = 1;
	private final static int COLUMNS_PER_PAGE = 2;
	private final static int BORDER = 0;
	private final static int SKILL_NAME_LENGTH = 25;
	private final static int CATEGORY_NAME_LENGTH = 33;
	private final static int MIN_EMPTY_SKILLS_PER_CATEGORY = 1;
	private static final BaseColor VERY_LIGHT_GRAY = new BaseColor(215, 215, 215);

	private static int FONT_SIZE = 6;
	private int page = 1;
	private PdfPTable tableColumn;
	private PdfPTable table;

	private List<List<Category>> columns;

	public PdfCombinedSheet2Columns(CharacterPlayer characterPlayer, String path) throws MalformedURLException, DocumentException,
			IOException {
		super(characterPlayer, path, false);
	}

	@Override
	public void characterPDF(String path) throws DocumentException, MalformedURLException, IOException {
		Document document = new Document(PageSize.A4);
		setMainTable(new PdfPTable(getMainTableWidths()));
		getMainTable().getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		getMainTable().setTotalWidth(document.getPageSize().getWidth() - 60);

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

	private void createPdf(Document document, PdfWriter writer) throws BadElementException, MalformedURLException, DocumentException,
			IOException {
		twoFaced = (getCharacterPlayer().getPerks().size() > 0 || getCharacterPlayer().getRace().getSpecials().size() > 0 || getCharacterPlayer()
				.getEquipment().size() > 0);

		DocumentData(document, writer);
		document.open();
		characteristicsPage(document, writer);
		if (twoFaced) {
			equipmentPage(document, writer);
		}
		combinedPage(document, writer);
		document.close();
	}

	private void combinedPage(Document document, PdfWriter writer) throws BadElementException, MalformedURLException, DocumentException,
			IOException {
		document.newPage();
		createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator + "RMHPComb.png");
		columns = getCategoriesPerColumn(getMinEmptySkillsPerCategory());
		combinedSkillsPage(document, writer);
	}

	protected void combinedSkillsPage(Document document, PdfWriter writer) throws MalformedURLException, BadElementException, IOException,
			DocumentException {
		PdfPCell cell;

		cell = new PdfPCell(createHeader());
		cell.setColspan(2);
		getMainTable().addCell(cell);

		setColumnTable(new PdfPTable(1));

		int columnIndex = 1;
		for (List<Category> column : getColumns()) {
			int linesByCategory = getMinNeededColumnLines(column);
			int emptyLinesToAdd = getMaxLinesPerColumn() - linesByCategory;
			for (Category category : column) {
				// Add The Category title
				PdfPCell categoryCell = categoryCell(writer, document, category);
				getColumnTable().addCell(categoryCell);

				// Add the title for skill list
				PdfPCell skillTitleCell = skillLine("Habilidad:", "#", null, "Rng:", "Cat", "Obj:", "Esp:", "Total:", new Font(
						getDefaultFont(), getFontSize() - 1, Font.BOLD), new Font(getDefaultFont(), getFontSize() - 1, Font.BOLD),
						getSkillLineWidhts());
				getColumnTable().addCell(skillTitleCell);

				// Skills list.
				for (Skill skill : category.getSkills()) {
					if (getCharacterPlayer().isSkillInteresting(skill)) {
						PdfPCell skillLineCell = skillLine(
								TxtSheet.getNameSpecificLength(getCharacterPlayer(), skill, getSkillNameLength()), getCharacterPlayer()
										.getPreviousRanks(skill) + "", getNewRanksImage(getCharacterPlayer().getCurrentLevelRanks(skill)),
								getCharacterPlayer().getRanksValue(skill) + "", getCharacterPlayer().getTotalValue(skill.getCategory())
										+ "", getCharacterPlayer().getItemBonus(skill) + "", getBonusValue(skill), getTotalValue(skill),
								new Font(getHandWrittingFont(), getFontSize() - 1), new Font(getHandWrittingFont(), getFontSize() - 1),
								getSkillLineWidhts());
						getColumnTable().addCell(skillLineCell);

						// Add specialization
						for (String specialization : getCharacterPlayer().getSkillSpecializations(skill)) {
							PdfPCell skillSpecializedLineCell = skillLine("  " + specialization, "", null, getCharacterPlayer()
									.getSpecializedRanksValue(skill) + "", getCharacterPlayer().getTotalValue(skill.getCategory()) + "",
									getCharacterPlayer().getItemBonus(skill) + "", getBonusValue(skill), getTotalSpecializedValue(skill),
									new Font(getHandWrittingFont(), getFontSize() - 1), new Font(getHandWrittingFont(), getFontSize() - 1),
									getSkillLineWidhts());
							getColumnTable().addCell(skillSpecializedLineCell);
						}
					}
				}

				// Empty skill lines to fill gaps. Divide lines in remaining
				// categories of column.
				int addEmptyLines = (Math.max(0, emptyLinesToAdd) / (column.size() - column.indexOf(category)))
						+ getMinEmptySkillsPerCategory();

				for (int i = 0; i < addEmptyLines; i++) {
					PdfPCell emptySkillLines = skillLine("________________________", "__", getNewRanksImage(0), "___", getCharacterPlayer()
							.getTotalValue(category) + "", "___", "___", "___", new Font(getDefaultFont(), getFontSize() - 1), new Font(
							getHandWrittingFont(), getFontSize() - 1), getSkillLineWidhts());
					getColumnTable().addCell(emptySkillLines);
					emptyLinesToAdd--;
				}
				//Already calculated. Remove it.
				emptyLinesToAdd += getMinEmptySkillsPerCategory();
			}

			addColumn(document);

			if (columnIndex % 2 == 0 && columnIndex < getColumns().size()) {
				addNewSkillPage(document, writer);
			}
			columnIndex++;
		}

		closePage(document, writer);
	}

	protected String getBonusValue(Skill skill) {
		String bonusText = getCharacterPlayer().getProfession().getSkillBonus(skill.getName())
				+ getCharacterPlayer().getHistorial().getBonus(skill) + getCharacterPlayer().getPerkBonus(skill)
				+ getCharacterPlayer().getConditionalPerkBonus(skill) + "";

		String letter = "";
		if (getCharacterPlayer().getHistorial().getBonus(skill) > 0) {
			letter += "H";
		}
		if (getCharacterPlayer() != null
				&& (getCharacterPlayer().getPerkBonus(skill) != 0 || getCharacterPlayer().getConditionalPerkBonus(skill) != 0)) {
			letter += "T";
			if (getCharacterPlayer().getConditionalPerkBonus(skill) != 0) {
				letter += "*";
			}
		}
		if (!letter.equals("")) {
			bonusText += " (" + letter + ")";
		}
		return bonusText;
	}

	protected String getTotalValue(Skill skill) {
		if (getCharacterPlayer().getItemBonus(skill) > 0 || getCharacterPlayer().getConditionalPerkBonus(skill) > 0) {
			return getCharacterPlayer().getTotalValue(skill) - getCharacterPlayer().getItemBonus(skill)
					- getCharacterPlayer().getConditionalPerkBonus(skill) + "/" + getCharacterPlayer().getTotalValue(skill) + "";
		} else {
			return getCharacterPlayer().getTotalValue(skill) + "";
		}
	}

	protected String getTotalSpecializedValue(Skill skill) {
		if (getCharacterPlayer().getItemBonus(skill) > 0 || getCharacterPlayer().getConditionalPerkBonus(skill) > 0) {
			return getCharacterPlayer().getSpecializedTotalValue(skill) - getCharacterPlayer().getItemBonus(skill)
					- getCharacterPlayer().getConditionalPerkBonus(skill) + "/" + getCharacterPlayer().getSpecializedTotalValue(skill) + "";
		} else {
			return getCharacterPlayer().getSpecializedTotalValue(skill) + "";
		}
	}

	protected void addColumn(Document document) {
		PdfPCell cell = new PdfPCell(getColumnTable());
		cell.setBorderWidth(1);
		cell.setColspan(1);
		cell.setMinimumHeight(document.getPageSize().getWidth() - 60);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		getMainTable().addCell(cell);

		setColumnTable(new PdfPTable(1));
		getColumnTable().flushContent();
	}

	protected void closePage(Document document, PdfWriter writer) {
		PdfPCell cell = new PdfPCell(createFooter(getFontSize()));
		cell.setBorderWidth(0);
		cell.setColspan(2);
		cell.setMinimumHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		getMainTable().addCell(cell);
		getMainTable().writeSelectedRows(0, -1, 30, document.getPageSize().getHeight() - 37, writer.getDirectContent());
	}

	private List<List<Category>> getCategoriesPerColumn(int emptyLinesPerCategory) {
		List<List<Category>> columns = new ArrayList<>();

		List<Category> categoriesPerColumn = new ArrayList<>();
		int currentColumnLines = 0;

		for (String categoryName : CategoryFactory.getAvailableCategories()) {
			int linesByCategory = 0;
			Category category = CategoryFactory.getCategory(categoryName);
			category = getCharacterPlayer().getCategory(category);
			if (getCharacterPlayer().isCategoryUseful(category)
			// Avoid firearms if not set.
					&& getCharacterPlayer().getCategoryCost(category, 0) != null) {
				linesByCategory += getCategoryRowSize();
				for (Skill skill : category.getSkills()) {
					if (getCharacterPlayer().isSkillInteresting(skill)) {
						linesByCategory += getSkillRowSize();
						// Count specializations
						linesByCategory += getCharacterPlayer().getSkillSpecializations(skill).size() * getSkillRowSize();
					}
				}

				// Add some empty lines each category.
				linesByCategory += emptyLinesPerCategory * getSkillRowSize();

				// If category does not fits in current column, change column.
				if (currentColumnLines + linesByCategory > getMaxLinesPerColumn()) {
					columns.add(categoriesPerColumn);
					categoriesPerColumn = new ArrayList<>();
					currentColumnLines = 0;
				}

				categoriesPerColumn.add(category);
				currentColumnLines += linesByCategory;
			}
		}
		// Add last column.
		columns.add(categoriesPerColumn);

		// If we have an empty column, move categories to this column.
		if (columns.size() % getColumnsPerPage() == 1) {
			columns = getCategoriesPerColumn(emptyLinesPerCategory + 1);
		}

		return columns;
	}

	protected int getMinNeededColumnLines(List<Category> column) {
		int linesByCategory = 0;
		for (Category category : column) {
			linesByCategory += getCategoryRowSize();
			for (Skill skill : category.getSkills()) {
				if (getCharacterPlayer().isSkillInteresting(skill)) {
					linesByCategory += getSkillRowSize();
					// Count specializations
					linesByCategory += getCharacterPlayer().getSkillSpecializations(skill).size() * getSkillRowSize();
				}
			}
			linesByCategory += getMinEmptySkillsPerCategory() * getSkillRowSize();
		}
		return linesByCategory;
	}

	protected static PdfPCell skillLine(String skillName, String previousSkillRanks, Image currentLevelRanks, String rankValue,
			String totalCategoryValue, String itemBonus, String totalBonus, String total, Font font, Font categoryFont, float[] widths)
			throws BadElementException, MalformedURLException, IOException, DocumentException {
		Paragraph p;
		PdfPCell cell;
		BaseColor background = BaseColor.WHITE;

		PdfPTable tableHab = new PdfPTable(widths);
		tableHab.flushContent();

		// Skill name
		p = new Paragraph(skillName, font);
		cell = getSkillCell(p, background);
		cell.setBorderWidthRight(0);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		tableHab.addCell(cell);

		// Previous ranks
		p = new Paragraph(previousSkillRanks, font);
		tableHab.addCell(getSkillCell(p, background));

		// Current ranks
		if (currentLevelRanks != null) {
			tableHab.addCell(getSkillCell(currentLevelRanks, background));
		} else {
			p = new Paragraph("", font);
			tableHab.addCell(getSkillCell(p, background));
		}

		// Rank value
		p = new Paragraph(rankValue, font);
		tableHab.addCell(getSkillCell(p, background));

		// Categoy value
		p = new Paragraph(totalCategoryValue, categoryFont);
		tableHab.addCell(getSkillCell(p, background));

		// Magic items.
		p = new Paragraph(itemBonus, font);
		tableHab.addCell(getSkillCell(p, background));

		// Bonus
		p = new Paragraph(totalBonus, font);
		tableHab.addCell(getSkillCell(p, background));

		// Total
		p = new Paragraph(total, font);
		cell = getSkillCell(p, background);
		cell.setBorderWidthLeft(0);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		tableHab.addCell(cell);

		PdfPCell skillcell = new PdfPCell(tableHab);
		skillcell.setBorderWidth(0);

		return skillcell;
	}

	private static PdfPCell getSkillCell(Paragraph content, BaseColor background) {
		PdfPCell cell = new PdfPCell(content);
		cell.setMinimumHeight(10);
		cell.setBorderWidth(BORDER);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);

		return cell;
	}

	private static PdfPCell getSkillCell(Image content, BaseColor background) {
		PdfPCell cell = new PdfPCell(content);
		cell.setMinimumHeight(10);
		cell.setBorderWidth(BORDER);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingLeft(5f);

		return cell;
	}

	protected PdfPCell categoryCell(PdfWriter writer, Document document, Category category) throws MalformedURLException,
			BadElementException, IOException, DocumentException {
		Paragraph p;
		float[] widths = { 0.23f, 0.18f, 0.19f, 0.11f, 0.13f, 0.16f };
		PdfPTable categoryTable = new PdfPTable(widths);
		categoryTable.flushContent();
		BaseColor background = getCategoryHeaderColor();

		p = new Paragraph(category.getName(getCategoryNameLength()).toUpperCase(), new Font(getDefaultFont(), getFontSize() - 1, Font.BOLD));
		PdfPCell cell = getCategoryCell(p, background);
		cell.setColspan(2);
		categoryTable.addCell(cell);

		p = new Paragraph(category.getCharacterisitcsTags(), new Font(getDefaultFont(), getFontSize()));
		categoryTable.addCell(getCategoryCell(p, background));

		p = new Paragraph(getCharacterPlayer().getCategoryCost(category, 0).getCostTag(), new Font(getHandWrittingFont(), getFontSize()));
		categoryTable.addCell(getCategoryCell(p, background));

		Paragraph p1 = new Paragraph("Rang: ", new Font(getDefaultFont(), getFontSize()));
		Paragraph p2 = new Paragraph(getCharacterPlayer().getPreviousRanks(category) + "", new Font(getHandWrittingFont(), getFontSize()));
		p = new Paragraph();
		p.add(p1);
		p.add(p2);
		categoryTable.addCell(getCategoryCell(p, background));

		if (category.getCategoryType().equals(CategoryType.STANDARD)) {
			categoryTable.addCell(getCategoryCell(getNewRanksImage(getCharacterPlayer().getCurrentLevelRanks(category)), background));
		} else {
			String text;
			if (category.getCategoryType().equals(CategoryType.COMBINED)) {
				text = "*";
				p = new Paragraph(text, new Font(getDefaultFont(), getFontSize()));
				categoryTable.addCell(getCategoryCell(p, background));
			}
			if (category.getCategoryType().equals(CategoryType.LIMITED) || category.getCategoryType().equals(CategoryType.SPECIAL)
					|| category.getCategoryType().equals(CategoryType.PPD) || category.getCategoryType().equals(CategoryType.PD)) {
				text = "+";
				p = new Paragraph(text, new Font(getDefaultFont(), getFontSize()));
				categoryTable.addCell(getCategoryCell(p, background));
			}
		}

		// Second line
		p1 = new Paragraph("Bonif. Rango: ", new Font(getDefaultFont(), getFontSize()));
		p2 = new Paragraph(getCharacterPlayer().getRanksValue(category) + "", new Font(getHandWrittingFont(), getFontSize()));
		p = new Paragraph();
		p.add(p1);
		p.add(p2);
		categoryTable.addCell(getCategoryCell(p, background));

		p1 = new Paragraph("Caract: ", new Font(getDefaultFont(), getFontSize()));
		p2 = new Paragraph(getCharacterPlayer().getCharacteristicsBonus(category) + "", new Font(getHandWrittingFont(), getFontSize()));
		p = new Paragraph();
		p.add(p1);
		p.add(p2);
		categoryTable.addCell(getCategoryCell(p, background));

		String text = (getCharacterPlayer().getHistorial().getBonus(category) + getCharacterPlayer().getPerkBonus(category)) + "";
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
			text += " (" + letter + ")";
		}

		p1 = new Paragraph("Esp: ", new Font(getDefaultFont(), getFontSize()));
		p2 = new Paragraph(text, new Font(getHandWrittingFont(), getFontSize()));
		p = new Paragraph();
		p.add(p1);
		p.add(p2);
		categoryTable.addCell(getCategoryCell(p, background));

		p1 = new Paragraph("Prof: ", new Font(getDefaultFont(), getFontSize()));
		p2 = new Paragraph((category.getBonus() + getCharacterPlayer().getProfession().getCategoryBonus(category.getName())) + "",
				new Font(getHandWrittingFont(), getFontSize()));
		p = new Paragraph();
		p.add(p1);
		p.add(p2);
		categoryTable.addCell(getCategoryCell(p, background));

		p1 = new Paragraph("Obj: ", new Font(getDefaultFont(), getFontSize()));
		p2 = new Paragraph(getCharacterPlayer().getItemBonus(category) + "", new Font(getHandWrittingFont(), getFontSize()));
		p = new Paragraph();
		p.add(p1);
		p.add(p2);
		categoryTable.addCell(getCategoryCell(p, background));

		p1 = new Paragraph("Total: ", new Font(getDefaultFont(), getFontSize()));
		p2 = new Paragraph(getCharacterPlayer().getTotalValue(category) + "", new Font(getHandWrittingFont(), getFontSize()));
		p = new Paragraph();
		p.add(p1);
		p.add(p2);
		categoryTable.addCell(getCategoryCell(p, background));

		cell = new PdfPCell(categoryTable);
		cell.setBorderWidth(1);
		cell.setBorderWidthTop(3);
		cell.setColspan(1);
		cell.setMinimumHeight(30);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(background);

		return cell;
	}

	private PdfPCell getCategoryCell(Paragraph content, BaseColor background) {
		PdfPCell cell = new PdfPCell(content);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);

		return cell;
	}

	private PdfPCell getCategoryCell(Image content, BaseColor background) {
		PdfPCell cell = new PdfPCell(content);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setBackgroundColor(background);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);

		return cell;
	}

	protected PdfPCell createHeader() {
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
		Font f = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, getFontSize() + 9);
		f.setColor(BaseColor.WHITE);

		Paragraph p = new Paragraph(
				"Hoja de Habilidades (" + (page + 1) + " de " + ((getColumns().size() + 1) / getColumnsPerPage()) + ")", f);
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

		Paragraph p = new Paragraph("Personaje: " + getCharacterPlayer().getName(), FontFactory.getFont(FontFactory.HELVETICA,
				getFontSize()));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(5f);
		tableC.addCell(cell);

		p = new Paragraph("Jugador: ___________________________________", new Font(getDefaultFont(), getFontSize()));
		cell = new PdfPCell(p);
		cell.setMinimumHeight(15);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(5f);
		tableC.addCell(cell);

		cell = new PdfPCell(tableC);
		return cell;
	}

	protected void addNewSkillPage(Document document, PdfWriter writer) throws BadElementException, MalformedURLException, IOException,
			DocumentException {
		PdfPCell cell;

		// Close previous page
		closePage(document, writer);

		// Create a new one
		setMainTable(new PdfPTable(getMainTableWidths()));
		getMainTable().flushContent();
		getMainTable().getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		getMainTable().setTotalWidth(document.getPageSize().getWidth() - 60);

		document.newPage();
		page++;
		try {
			createBackgroundImage(document, RolemasterFolderStructure.getSheetFolder() + File.separator + "RMHPComb.png");
		} catch (DocumentException ex) {
			EsherLog.errorMessage(PdfCombinedSheet2Columns.class.getName(), ex);
		}

		cell = new PdfPCell(createHeader());
		cell.setColspan(2);
		getMainTable().addCell(cell);
	}

	protected int getFontSize() {
		return FONT_SIZE;
	}

	protected int getColumnsPerPage() {
		return COLUMNS_PER_PAGE;
	}

	protected PdfPTable getMainTable() {
		return table;
	}

	protected float[] getSkillLineWidhts() {
		float[] widths = { 0.32f, 0.05f, 0.15f, 0.08f, 0.10f, 0.10f, 0.10f, 0.10f };
		return widths;
	}

	protected float[] getMainTableWidths() {
		float[] mainWidths = { 0.45f, 0.45f };
		return mainWidths;
	}

	protected void setMainTable(PdfPTable table) {
		this.table = table;
	}

	protected PdfPTable getColumnTable() {
		return tableColumn;
	}

	protected void setColumnTable(PdfPTable table) {
		tableColumn = table;
	}

	protected List<List<Category>> getColumns() {
		return columns;
	}

	protected BaseColor getCategoryHeaderColor() {
		return VERY_LIGHT_GRAY;
	}

	protected int getCategoryNameLength() {
		return CATEGORY_NAME_LENGTH;
	}

	protected int getSkillNameLength() {
		return SKILL_NAME_LENGTH;
	}

	protected int getCategoryRowSize() {
		return CATEGORY_SIZE;
	}

	protected int getSkillRowSize() {
		return SKILL_SIZE;
	}

	protected int getMinEmptySkillsPerCategory() {
		return MIN_EMPTY_SKILLS_PER_CATEGORY;
	}

	protected int getMaxLinesPerColumn() {
		return MAX_LINES_PER_COLUMN;
	}
}

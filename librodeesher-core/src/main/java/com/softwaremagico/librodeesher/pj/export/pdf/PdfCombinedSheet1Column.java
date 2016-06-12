package com.softwaremagico.librodeesher.pj.export.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class PdfCombinedSheet1Column extends PdfCombinedSheet2Columns {
	private static final BaseColor WHITE = new BaseColor(255, 255, 255);
	private final static int MAX_LINES_PER_COLUMN = 69;
	private final static int COLUMNS_PER_PAGE = 1;
	private final static int SKILL_NAME_LENGTH = 33;
	private final static int CATEGORY_NAME_LENGTH = 33;
	private final static int MIN_EMPTY_SKILLS_PER_CATEGORY = 1;
	private final static int CATEGORY_SIZE = 4;
	private final static int SKILL_SIZE = 1;

	private static int FONT_SIZE = 8;

	public PdfCombinedSheet1Column(CharacterPlayer characterPlayer, String path) throws MalformedURLException, DocumentException,
			IOException {
		super(characterPlayer, path);
	}

	@Override
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
				PdfPCell skillTitleCell = skillLine("Habilidad:", "Rangos", null, "B. Rangos:", "Categoría", "Objeto:", "Especial:",
						"Total:", new Font(getDefaultFont(), getFontSize() - 1, Font.BOLD), new Font(getDefaultFont(), getFontSize() - 1,
								Font.BOLD), getSkillLineWidhts());
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
				int addEmptyLines = (int) (Math.ceil((Math.max(0, emptyLinesToAdd) / (double) (column.size() - column.indexOf(category)))) + getMinEmptySkillsPerCategory());

				for (int i = 0; i < addEmptyLines; i++) {
					PdfPCell emptySkillLines = skillLine("_________________________________", "______", getNewRanksImage(0), "______",
							getCharacterPlayer().getTotalValue(category) + "", "______", "______", "______", new Font(getDefaultFont(),
									getFontSize() - 1), new Font(getHandWrittingFont(), getFontSize() - 1), getSkillLineWidhts());
					getColumnTable().addCell(emptySkillLines);
					emptyLinesToAdd--;
				}
				//Already calculated. Remove it.
				emptyLinesToAdd += getMinEmptySkillsPerCategory();
			}
			addColumn(document);
			if (columnIndex < getColumns().size()) {
				addNewSkillPage(document, writer);
			}
			columnIndex++;
		}

		closePage(document, writer);
	}

	@Override
	protected int getFontSize() {
		return FONT_SIZE;
	}

	@Override
	protected int getColumnsPerPage() {
		return COLUMNS_PER_PAGE;
	}

	@Override
	protected float[] getMainTableWidths() {
		float[] mainWidths = { 0.90f };
		return mainWidths;
	}

	@Override
	protected BaseColor getCategoryHeaderColor() {
		return WHITE;
	}

	@Override
	protected int getCategoryNameLength() {
		return CATEGORY_NAME_LENGTH;
	}

	@Override
	protected int getSkillNameLength() {
		return SKILL_NAME_LENGTH;
	}

	protected float[] getSkillLineWidhts() {
		float[] widths = { 0.32f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f };
		return widths;
	}

	@Override
	protected int getCategoryRowSize() {
		return CATEGORY_SIZE;
	}

	@Override
	protected int getSkillRowSize() {
		return SKILL_SIZE;
	}

	@Override
	protected int getMinEmptySkillsPerCategory() {
		return MIN_EMPTY_SKILLS_PER_CATEGORY;
	}

	@Override
	protected int getMaxLinesPerColumn() {
		return MAX_LINES_PER_COLUMN;
	}
}

package com.softwaremagico.librodeesher.pj;

import java.io.PrintWriter;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.LevelJsonManager;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfCombinedSheet;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;

@Test
public class CharacterCreationTest {
	private final static String PDF_PATH_STANDARD = System.getProperty("java.io.tmpdir") + "/testStnd.pdf";
	private final static String PDF_PATH_COMBINED = System.getProperty("java.io.tmpdir") + "/testCmb.pdf";
	private final static String TXT_PATH = System.getProperty("java.io.tmpdir") + "/testStandard.txt";
	private final static String TXT_ABBREVIATED_PATH = System.getProperty("java.io.tmpdir") + "/testAbbreviated.txt";
	private final static String JSON_CHARACTER_PATH = System.getProperty("java.io.tmpdir") + "/testCharacterJson.txt";
	private final static String JSON_LEVEL_PATH = System.getProperty("java.io.tmpdir") + "/testLevelJson.txt";
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();
	private CharacterPlayer characterPlayer;

	@Test(groups = { "characterCreation" })
	public void createCharacter() throws MagicDefinitionException, InvalidProfessionException {
		characterPlayer = new RandomCharacterPlayer(null, null, null, null, 1).getCharacterPlayer();
		Assert.assertTrue(characterPlayer.getRemainingDevelopmentPoints() >= 0);
	}

	@Test(groups = { "characterStorage" }, dependsOnMethods = { "createCharacter" })
	public void storeCharacter() {
		characterPlayerDao.makePersistent(characterPlayer);
		Assert.assertNotNull(characterPlayer.getId());
		Assert.assertNotNull(characterPlayer.getTotalValue(SkillFactory.getAvailableSkill("Trepar")));
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "createCharacter" })
	public void standardPdf() throws Exception {
		new PdfStandardSheet(characterPlayer, PDF_PATH_STANDARD, false);
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "createCharacter" })
	public void combinedPdf() throws Exception {
		new PdfCombinedSheet(characterPlayer, PDF_PATH_COMBINED);
	}

	@Test(groups = { "characterTxt" }, dependsOnMethods = { "createCharacter" })
	public void exportStandardTxt() throws Exception {
		new TxtSheet(characterPlayer).exportSheet(TXT_PATH);
	}

	@Test(groups = { "characterTxt" }, dependsOnMethods = { "createCharacter" })
	public void exportAbbreviatedTxt() throws Exception {
		TxtSheet.exportCharacterAbbreviature(characterPlayer, TXT_ABBREVIATED_PATH);
	}

	@Test(groups = { "characterJson" }, dependsOnMethods = { "createCharacter" })
	public void exportCharacterJson() throws Exception {
		String jsonText = CharacterJsonManager.toJson(characterPlayer);
		// store in a file.
		PrintWriter out = new PrintWriter(JSON_CHARACTER_PATH);
		out.println(jsonText);
		out.close();
		// get json to object.
		CharacterPlayer importedCharacter = CharacterJsonManager.fromJson(jsonText);
		// Compared generated sheet to be sure that has the same information.
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet = TxtSheet.getCharacterStandardSheetAsText(importedCharacter);
		Assert.assertEquals(importedSheet, orginalSheet);
	}

	@Test(groups = { "characterJson" }, dependsOnMethods = { "exportCharacterJson" })
	public void exportLevelJson() throws Exception {
		String jsonText = CharacterJsonManager.toJson(characterPlayer);
		CharacterPlayer duplicatedCharacter = CharacterJsonManager.fromJson(jsonText);

		// Increase level of one character.
		characterPlayer.increaseLevel();
		new RandomCharacterPlayer(characterPlayer);
		
		// Export last level
		String levelJsonText = LevelJsonManager.toJson(characterPlayer);

		// store in a file.
		PrintWriter out = new PrintWriter(JSON_LEVEL_PATH);
		out.println(levelJsonText);
		out.close();
		// get json to object.
		LevelJsonManager.fromJson(duplicatedCharacter, levelJsonText);
		// Compared generated sheet to be sure that has the same information.
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet = TxtSheet.getCharacterStandardSheetAsText(duplicatedCharacter);
		Assert.assertEquals(importedSheet, orginalSheet);
	}

}

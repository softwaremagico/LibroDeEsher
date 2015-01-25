package com.softwaremagico.librodeesher.pj;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.junit.After;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.LevelJsonManager;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfCombinedSheet;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.export.txt.TxtSheet;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.random.RandomFeedbackListener;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;

@Test
public class CharacterCreationTest {
	private final static String PDF_PATH_STANDARD = System.getProperty("java.io.tmpdir") + "/testStnd.pdf";
	private final static String PDF_PATH_COMBINED = System.getProperty("java.io.tmpdir") + "/testCmb.pdf";
	private final static String TXT_PATH = System.getProperty("java.io.tmpdir") + "/testStandard.txt";
	private final static String TXT_ABBREVIATED_PATH = System.getProperty("java.io.tmpdir")
			+ "/testAbbreviated.txt";
	private final static String JSON_LEVEL_PATH = System.getProperty("java.io.tmpdir") + "/testLevelJson.txt";
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();
	private CharacterPlayer characterPlayer;

	@Test(groups = { "characterCreation" })
	public void createCharacter() throws MagicDefinitionException, InvalidProfessionException {
		RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(null, null, null, null, 1);
		randomCharacter.addFeedbackListener(new RandomFeedbackListener() {
			@Override
			public void feedBackMessage(String message) {
				System.out.println(message);
			}
		});
		randomCharacter.createRandomValues();
		characterPlayer = randomCharacter.getCharacterPlayer();
		Assert.assertTrue(characterPlayer.getRemainingDevelopmentPoints() >= 0);
	}

	@Test(groups = { "characterStorage" }, dependsOnMethods = { "createCharacter" })
	public void storeCharacter() throws Exception {
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
		// get json to object.
		CharacterPlayer importedCharacter = CharacterJsonManager.fromJson(jsonText);
		PrintWriter out3 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "characterAsJson.txt");
		out3.println(jsonText);
		out3.close();

		// Compared generated sheet to be sure that has the same information.
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet = TxtSheet.getCharacterStandardSheetAsText(importedCharacter);

		PrintWriter out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "originalCharacterSheet.txt");
		out1.println(orginalSheet);
		out1.close();

		PrintWriter out2 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "importedCharacterSheet.txt");
		out2.println(importedSheet);
		out2.close();

		Assert.assertEquals(importedSheet, orginalSheet);
	}

	@Test(groups = { "characterJson" }, dependsOnMethods = { "exportCharacterJson" })
	public void exportLevelJson() throws Exception {
		String jsonText = CharacterJsonManager.toJson(characterPlayer);
		CharacterPlayer duplicatedCharacter = CharacterJsonManager.fromJson(jsonText);

		// Increase level of one character.
		characterPlayer.increaseLevel();
		RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(characterPlayer);
		randomCharacter.setDevelopmentPoints();

		// Export last level
		String levelJsonText = LevelJsonManager.toJson(characterPlayer);

		// store in a file.
		PrintWriter out = new PrintWriter(JSON_LEVEL_PATH);
		out.println(levelJsonText);
		out.close();
		// get json to object.
		LevelUp newLevel = LevelJsonManager.fromJson(duplicatedCharacter, levelJsonText);
		duplicatedCharacter.importLevel(newLevel);
		// Compared generated sheet to be sure that has the same information.
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet = TxtSheet.getCharacterStandardSheetAsText(duplicatedCharacter);

		PrintWriter out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "originalSheet.txt");
		out1.println(orginalSheet);
		out1.close();

		PrintWriter out2 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "importedLevelSheet.txt");
		out2.println(importedSheet);
		out2.close();

		Assert.assertEquals(importedSheet, orginalSheet);
	}

	@Test(groups = { "characterCreation" }, dependsOnMethods = { "createCharacter" })
	private void magicObjectTest() throws Exception {
		Skill broadSword = SkillFactory.getSkill("Espada");
		Assert.assertNotNull(broadSword);
		int previousAttackBonus = characterPlayer.getTotalValue(broadSword);

		MagicObject magicSword = new MagicObject();
		magicSword.setName(broadSword.getName());
		magicSword.setSkillBonus(broadSword.getName(), 20);
		Assert.assertEquals(magicSword.getBonus().size(), 1);

		characterPlayer.addMagicItem(magicSword);

		Assert.assertEquals(characterPlayer.getTotalValue(broadSword), new Integer(previousAttackBonus + 20));

		characterPlayerDao.makePersistent(characterPlayer);
	}

	@Test
	@After
	public void removeAll() throws Exception {
		List<CharacterPlayer> characterPlayers = characterPlayerDao.getAll();
		for (CharacterPlayer character : characterPlayers) {
			characterPlayerDao.makeTransient(character);
		}
		Assert.assertEquals(0, characterPlayerDao.getRowCount());
	}

}

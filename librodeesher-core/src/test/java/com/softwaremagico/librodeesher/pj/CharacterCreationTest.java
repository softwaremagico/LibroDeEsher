package com.softwaremagico.librodeesher.pj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.After;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.LevelJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidCharacterException;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidLevelException;
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
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerInfoDao;
import com.softwaremagico.persistence.dao.hibernate.DatabaseException;

@Test
public class CharacterCreationTest {
	private final static String PDF_PATH_STANDARD = System.getProperty("java.io.tmpdir") + "/testStnd.pdf";
	private final static String PDF_PATH_COMBINED = System.getProperty("java.io.tmpdir") + "/testCmb.pdf";
	private final static String TXT_PATH = System.getProperty("java.io.tmpdir") + "/testStandard.txt";
	private final static String TXT_ABBREVIATED_PATH = System.getProperty("java.io.tmpdir")
			+ "/testAbbreviated.txt";
	private final static String JSON_LEVEL_PATH = System.getProperty("java.io.tmpdir") + "/testLevelJson.txt";
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();
	private CharacterPlayerInfoDao characterPlayerInfoDao = CharacterPlayerInfoDao.getInstance();
	private CharacterPlayer characterPlayer;

	@Test(groups = { "characterCreation" })
	public void createCharacter() throws MagicDefinitionException, InvalidProfessionException {
		RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(null, null, null, null, 1);
		// randomCharacter.setSuggestedTrainings(new
		// ArrayList<String>(Arrays.asList("Soldado", "Guardia")));
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
	public void storeCharacter() throws DatabaseException {
		characterPlayerDao.makePersistent(characterPlayer);
		Assert.assertNotNull(characterPlayer.getId());
		Assert.assertNotNull(characterPlayer.getTotalValue(SkillFactory.getAvailableSkill("Trepar")));
	}

	@Test(groups = { "characterStorage" }, dependsOnMethods = { "storeCharacter" })
	public void characterInfo() throws DatabaseException {
		Assert.assertEquals(characterPlayerInfoDao.getRowCount(), 1);
		Assert.assertEquals(CharacterPlayerInfo.getCharacterPlayerInfo(characterPlayer),
				characterPlayerInfoDao.getAll().get(0));
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "createCharacter" })
	public void standardPdf() throws MalformedURLException, DocumentException, IOException {
		new PdfStandardSheet(characterPlayer, PDF_PATH_STANDARD, false);
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "createCharacter" })
	public void combinedPdf() throws MalformedURLException, DocumentException, IOException {
		new PdfCombinedSheet(characterPlayer, PDF_PATH_COMBINED);
	}

	@Test(groups = { "characterTxt" }, dependsOnMethods = { "createCharacter" })
	public void exportStandardTxt() {
		new TxtSheet(characterPlayer).exportSheet(TXT_PATH);
	}

	@Test(groups = { "characterTxt" }, dependsOnMethods = { "createCharacter" })
	public void exportAbbreviatedTxt() {
		TxtSheet.exportCharacterAbbreviature(characterPlayer, TXT_ABBREVIATED_PATH);
	}

	@Test(groups = { "characterJson" }, dependsOnMethods = { "createCharacter" })
	public void exportCharacterJson() throws FileNotFoundException {
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
	public void exportLevelJson() throws MagicDefinitionException, InvalidProfessionException,
			FileNotFoundException, InvalidLevelException, InvalidCharacterException {
		String jsonText = CharacterJsonManager.toJson(characterPlayer);
		CharacterPlayer duplicatedCharacter = CharacterJsonManager.fromJson(jsonText);
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet = TxtSheet.getCharacterStandardSheetAsText(duplicatedCharacter);
		Assert.assertTrue(importedSheet.equals(orginalSheet));

		// Increase level of one character.
		int prevCharacterLevel = characterPlayer.getCurrentLevelNumber();
		characterPlayer.increaseLevel();
		Assert.assertEquals((int) characterPlayer.getCurrentLevelNumber(), prevCharacterLevel + 1);
		RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(characterPlayer);
		randomCharacter.setDevelopmentPoints();

		// Export last level
		String levelJsonText = LevelJsonManager.toJson(characterPlayer);

		// store in a file.
		PrintWriter out = new PrintWriter(JSON_LEVEL_PATH);
		out.println(levelJsonText);
		out.close();

		// get json to object. Add to duplicatedCharacter.
		LevelUp newLevel = LevelJsonManager.fromJson(duplicatedCharacter, levelJsonText);
		Assert.assertNotNull(newLevel);
		int prevLevel = duplicatedCharacter.getCurrentLevelNumber();
		duplicatedCharacter.importLevel(newLevel);
		Assert.assertEquals((int) duplicatedCharacter.getCurrentLevelNumber(), prevLevel + 1);

		// Compared generated sheet to be sure that has the same information.
		orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		importedSheet = TxtSheet.getCharacterStandardSheetAsText(duplicatedCharacter);

		PrintWriter out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "originalSheet.txt");
		out1.println(orginalSheet);
		out1.close();

		PrintWriter out2 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator
				+ "importedLevelSheet.txt");
		out2.println(importedSheet);
		out2.close();

		// Use an equals to avoid to show all TXT in console if wrong.
		Assert.assertTrue(importedSheet.equals(orginalSheet));
	}

	@Test(groups = { "characterCreation" }, dependsOnMethods = { "createCharacter" })
	private void magicObjectTest() throws DatabaseException {
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
	public void removeAll() {
		List<CharacterPlayer> characterPlayers = characterPlayerDao.getAll();
		for (CharacterPlayer character : characterPlayers) {
			characterPlayerDao.makeTransient(character);
		}
		Assert.assertEquals(characterPlayerDao.getRowCount(), 0);
		Assert.assertEquals(characterPlayerInfoDao.getRowCount(), 0);
	}

}

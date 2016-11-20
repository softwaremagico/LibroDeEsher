package com.softwaremagico.librodeesher.pj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.After;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.exceptions.CategoryNotEqualsException;
import com.softwaremagico.librodeesher.pj.exceptions.CharacteristicNotEqualsException;
import com.softwaremagico.librodeesher.pj.exceptions.SkillNotEqualsException;
import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.LevelJsonManager;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidCharacterException;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidLevelException;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfCombinedSheet2Columns;
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
	private final static String TXT_ABBREVIATED_PATH = System.getProperty("java.io.tmpdir") + "/testAbbreviated.txt";
	private final static String JSON_LEVEL_PATH = System.getProperty("java.io.tmpdir") + "/testLevelJson.json";
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();
	private CharacterPlayerInfoDao characterPlayerInfoDao = CharacterPlayerInfoDao.getInstance();
	private CharacterPlayer characterPlayer;

	@Test(groups = { "characterCreation" })
	public void createCharacter() throws MagicDefinitionException, InvalidProfessionException {
		RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(null, null, null, null, true, 1);
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
		int charactersStored = characterPlayerDao.getRowCount();
		characterPlayerDao.makePersistent(characterPlayer);
		Assert.assertEquals(characterPlayerDao.getRowCount(), charactersStored + 1);
		Assert.assertNotNull(characterPlayer.getId());
		Assert.assertNotNull(characterPlayer.getTotalValue(SkillFactory.getAvailableSkill("Trepar")));
	}

	@Test(groups = { "characterStorage" }, dependsOnMethods = { "storeCharacter" })
	public void characterInfo() throws DatabaseException {
		Assert.assertEquals(characterPlayerInfoDao.getRowCount(), 1);
		CharacterPlayerInfo original = CharacterPlayerInfo.getCharacterPlayerInfo(characterPlayer);
		CharacterPlayerInfo obtained = characterPlayerInfoDao.getAll().get(0);
		Assert.assertEquals(original.getId(), obtained.getId());
		Assert.assertEquals(original.getName(), obtained.getName());
		Assert.assertEquals(original.getCreatedLevel(), obtained.getCreatedLevel());
		Assert.assertEquals(original.getInsertedLevel(), obtained.getInsertedLevel());
		Assert.assertEquals(original.getSex(), obtained.getSex());
		Assert.assertEquals(original.getRaceName(), obtained.getRaceName());
		Assert.assertEquals(original.getCultureName(), obtained.getCultureName());
		Assert.assertEquals(original.getProfessionName(), obtained.getProfessionName());
		Assert.assertEquals(original, obtained);
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "createCharacter" })
	public void standardPdf() throws MalformedURLException, DocumentException, IOException {
		new PdfStandardSheet(characterPlayer, PDF_PATH_STANDARD, false);
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "createCharacter" })
	public void combinedPdf() throws MalformedURLException, DocumentException, IOException {
		new PdfCombinedSheet2Columns(characterPlayer, PDF_PATH_COMBINED);
	}

	@Test(groups = { "characterTxt" }, dependsOnMethods = { "createCharacter" })
	public void exportStandardTxt() {
		new TxtSheet(characterPlayer).exportSheet(TXT_PATH);
	}

	@Test(groups = { "characterTxt" }, dependsOnMethods = { "createCharacter" })
	public void exportAbbreviatedTxt() {
		TxtSheet.exportCharacterAbbreviature(characterPlayer, TXT_ABBREVIATED_PATH);
	}

	@Test(groups = { "characterJson" }, dependsOnMethods = { "createCharacter", "characterInfo" })
	public void exportCharacterJson() throws FileNotFoundException, CharacteristicNotEqualsException, CategoryNotEqualsException, SkillNotEqualsException {
		String jsonText = CharacterJsonManager.toJson(characterPlayer);
		// get json to object.
		CharacterPlayer importedCharacter = CharacterJsonManager.fromJson(jsonText);
		PrintWriter out3 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "character_l1.json");
		out3.println(jsonText);
		out3.close();

		// Compared characters.
		CharacterComparator.compare(characterPlayer, importedCharacter);

		// Compared generated sheet to be sure that has the same information.
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet = TxtSheet.getCharacterStandardSheetAsText(importedCharacter);

		PrintWriter out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "originalCharacterSheet.txt");
		out1.println(orginalSheet);
		out1.close();

		PrintWriter out2 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "importedCharacterSheet.txt");
		out2.println(importedSheet);
		out2.close();

		Assert.assertEquals(importedSheet, importedSheet);
	}

	@Test(groups = { "characterJson" }, dependsOnMethods = { "exportCharacterJson", "storeCharacter" })
	public void importCharacterFromJson() throws DatabaseException, IOException {
		BufferedReader bufferReader = null;
		String jsonText = "";
		// Read file.
		bufferReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(System.getProperty("java.io.tmpdir") + File.separator + "character_l1.json"), "UTF8"));
		String str;

		while ((str = bufferReader.readLine()) != null) {
			jsonText += str;
		}
		bufferReader.close();

		CharacterPlayer importedCharacter = CharacterJsonManager.fromJson(jsonText);
		int charactersStored = characterPlayerDao.getRowCount();
		// Only one character allowed. Must be overridden.
		characterPlayerDao.makePersistent(importedCharacter);
		// No new characters on database.
		Assert.assertEquals(characterPlayerDao.getRowCount(), charactersStored);
	}

	@Test(groups = { "characterJson" }, dependsOnMethods = { "importCharacterFromJson" })
	public void exportLevelJson() throws MagicDefinitionException, InvalidProfessionException, FileNotFoundException, InvalidLevelException,
			InvalidCharacterException, CharacteristicNotEqualsException, CategoryNotEqualsException, SkillNotEqualsException {
		String jsonText = CharacterJsonManager.toJson(characterPlayer);
		CharacterPlayer duplicatedCharacter = CharacterJsonManager.fromJson(jsonText);
		String orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet = TxtSheet.getCharacterStandardSheetAsText(duplicatedCharacter);
		
		PrintWriter out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "originalCharacterSheet2.txt");
		out1.println(orginalSheet);
		out1.close();
		
		PrintWriter out2 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "importedSheet2.txt");
		out2.println(importedSheet);
		out2.close();
		
		
		
		Assert.assertTrue(importedSheet.equals(orginalSheet));

		// Increase level of one character.
		int prevCharacterLevel = characterPlayer.getCurrentLevelNumber();
		characterPlayer.increaseLevel();
		Assert.assertEquals((int) characterPlayer.getCurrentLevelNumber(), prevCharacterLevel + 1);
		RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(characterPlayer);
		randomCharacter.addFeedbackListener(new RandomFeedbackListener() {
			@Override
			public void feedBackMessage(String message) {
				System.out.println(message);
			}
		});
		randomCharacter.setDevelopmentPoints();

		// Export character
		String characterLevel2 = CharacterJsonManager.toJson(characterPlayer);
		PrintWriter out3 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "character_l2.json");
		out3.println(characterLevel2);
		out3.close();

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
		String orginalSheet2 = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		String importedSheet2 = TxtSheet.getCharacterStandardSheetAsText(duplicatedCharacter);

		PrintWriter out4 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "originalCharacterSheetLvl2.txt");
		out4.println(orginalSheet2);
		out4.close();

		PrintWriter out5 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "importedCharacterSheetLvl2.txt");
		out5.println(importedSheet2);
		out5.close();

		String characterLevel2duplicated = CharacterJsonManager.toJson(duplicatedCharacter);
		PrintWriter out6 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "character_l2dup.json");
		out6.println(characterLevel2duplicated);
		out6.close();

		CharacterComparator.compare(characterPlayer, duplicatedCharacter);

		// Compared generated sheet to be sure that has the same information.
		orginalSheet = TxtSheet.getCharacterStandardSheetAsText(characterPlayer);
		importedSheet = TxtSheet.getCharacterStandardSheetAsText(duplicatedCharacter);

		out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "originalSheet.txt");
		out1.println(orginalSheet);
		out1.close();

		out2 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "importedLevelSheet.txt");
		out2.println(importedSheet);
		out2.close();

		// Use an equals to avoid to show all TXT in console if wrong.
		Assert.assertTrue(importedSheet.equals(orginalSheet));
	}

	@Test(groups = { "characterCreation" }, dependsOnMethods = { "createCharacter", "storeCharacter" })
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

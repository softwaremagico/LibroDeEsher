package com.softwaremagico.librodeesher.pj;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.export.json.CharacterToJson;
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
	public void exportJson() throws Exception {
		CharacterToJson.toJson(characterPlayer);
	}

}

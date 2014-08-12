package com.softwaremagico.librodeesher.pj;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.pdf.PdfCombinedSheet;
import com.softwaremagico.librodeesher.pj.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;

@Test
public class CharacterCreationTest {
	private final static String PDF_PATH = System.getProperty("java.io.tmpdir") + "/test.pdf";
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();
	private CharacterPlayer characterPlayer;

	@Test(groups = { "characterCreation" })
	public void createCharacter() throws MagicDefinitionException, InvalidProfessionException {
		characterPlayer = new RandomCharacterPlayer(null, null, null, null, 1).getCharacterPlayer();

	}

	@Test(groups = { "characterStorage" }, dependsOnMethods = { "createCharacter" })
	public void storeCharacter() {
		characterPlayerDao.makePersistent(characterPlayer);
		Assert.assertNotNull(characterPlayer.getId());
		Assert.assertNotNull(characterPlayer.getTotalValue(SkillFactory.getAvailableSkill("Trepar")));
		Assert.assertTrue(characterPlayer.getRemainingDevelopmentPoints() >= 0);
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "storeCharacter" })
	public void standardPdf() throws Exception {
		new PdfStandardSheet(characterPlayer, PDF_PATH, false);
	}

	@Test(groups = { "characterPdf" }, dependsOnMethods = { "storeCharacter" })
	public void combinedPdf() throws Exception {
		new PdfCombinedSheet(characterPlayer, PDF_PATH);
	}

}

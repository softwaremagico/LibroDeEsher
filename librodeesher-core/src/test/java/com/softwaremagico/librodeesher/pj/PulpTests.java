package com.softwaremagico.librodeesher.pj;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;

@Test(groups = "pulpTests")
public class PulpTests {
	private final static String MODERN_MAN_RACE = "Hombre Moderno";
	private final static String INDUSTRY_CULTURE = "Industrializado (Urbano Clase Alta)";

	@Test
	public void checkCultureFiles() {
		CharacterPlayer characterPlayer = new CharacterPlayer();
		characterPlayer.setRace(MODERN_MAN_RACE);
		Assert.assertTrue(characterPlayer.getRace().getAvailableCultures().size() > 0);
	}

	@Test
	public void checkCultureRanks() throws MagicDefinitionException, InvalidProfessionException {
		CharacterPlayer characterPlayer = new CharacterPlayer();
		characterPlayer.setRace(MODERN_MAN_RACE);
		characterPlayer.setCulture(INDUSTRY_CULTURE);
		Assert.assertEquals(
				(int) characterPlayer.getTotalRanks(CategoryFactory.getCategory("Percepción·Búsqueda")), 1);
	}
}

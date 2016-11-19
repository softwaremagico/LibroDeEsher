package com.softwaremagico.librodeesher.pj;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.random.RandomFeedbackListener;

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

	@Test
	public void checkRandomCharacterIndustryCulture() throws MagicDefinitionException, InvalidProfessionException {
		RandomCharacterPlayer randomCharacter = new RandomCharacterPlayer(null, MODERN_MAN_RACE,
				INDUSTRY_CULTURE, null, true, 1);
		randomCharacter.addFeedbackListener(new RandomFeedbackListener() {
			@Override
			public void feedBackMessage(String message) {
				System.out.println(message);
			}
		});
		randomCharacter.createRandomValues();
		CharacterPlayer characterPlayer = randomCharacter.getCharacterPlayer();
		Assert.assertEquals((int) characterPlayer.getRemainingBackgroundPoints(), 0);
		Assert.assertEquals((int) characterPlayer.getCultureTotalHobbyRanks(), (int) characterPlayer
				.getCulture().getHobbyRanks());
		Assert.assertEquals(characterPlayer.getCultureDecisions().getAdolescenceCategoriesSelected().size(), 3);
		Assert.assertTrue(randomCharacter.getCharacterPlayer().getRemainingDevelopmentPoints() >= 0);
	}
}

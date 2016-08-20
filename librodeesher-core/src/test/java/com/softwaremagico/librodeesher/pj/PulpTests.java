package com.softwaremagico.librodeesher.pj;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;

@Test(groups = "pulpTests")
public class PulpTests {
	private final static String MODERN_MAN_RACE = "Hombre Moderno";
	private final static String INDUSTRY_CULTURE = "Industrializado (Urbano Clase Alta)";
	private final static List<String> PULP_CULTURES = Arrays.asList("Industrializado (Urbano Clase Alta)",
			"Industrializado (Rural Clase Media)", "Industrializado (Rural Clase Alta)",
			"Industrializado (Rural Clase Baja)", "Industrializado (Urbano Clase Media)",
			"Industrializado (Urbano Clase Baja)", "Tercer mundo (Urbano Clase Alta)",
			"Tercer mundo (Rural Clase Media)", "Tercer mundo (Rural Clase Alta)", "Tercer mundo (Rural Clase Baja)",
			"Tercer mundo (Urbano Clase Media)", "Tercer mundo (Urbano Clase Baja)");

	static {
		Collections.sort(PULP_CULTURES);
	}

	@Test
	public void checkCultureFiles() {
		CharacterPlayer characterPlayer = new CharacterPlayer();
		characterPlayer.setRace(MODERN_MAN_RACE);
		Assert.assertEquals(characterPlayer.getRace().getAvailableCultures().size(), PULP_CULTURES.size());

	}

	@Test
	public void checkCultureRanks() throws MagicDefinitionException, InvalidProfessionException {
		CharacterPlayer characterPlayer = new CharacterPlayer();
		characterPlayer.setRace(MODERN_MAN_RACE);
		characterPlayer.setCulture(INDUSTRY_CULTURE);
		Assert.assertEquals((int) characterPlayer.getTotalRanks(CategoryFactory.getCategory("Armas·Fuego 1mano")), 1);
	}

	@Test
	public void checkLanguages() {
		CharacterPlayer characterPlayer = new CharacterPlayer();
		characterPlayer.setRace(MODERN_MAN_RACE);
		characterPlayer.setCulture(INDUSTRY_CULTURE);

		Assert.assertEquals(characterPlayer.getRace().getOptionalLanguages().size(), 6);
		Assert.assertEquals(characterPlayer.getCulture().getOptionalLanguages().size(), 3);

		RandomCharacterPlayer.setRandomCultureAndRaceLanguages(characterPlayer, 0);
		// 6 languages written, 6 spoken.
		Assert.assertEquals(characterPlayer.getCultureDecisions().getOptionalRaceLanguages().size(), 12);
		Assert.assertEquals(characterPlayer.getCultureDecisions().getOptionalCulturalLanguages().size(), 4);

		// Check new languages has ranges.
		int culturalRanksAssigned = 0;
		for (String language : characterPlayer.getCultureDecisions().getLanguageRanks().keySet()) {
			culturalRanksAssigned += characterPlayer.getCultureDecisions().getLanguageRanks().get(language);
		}
		Assert.assertEquals(culturalRanksAssigned, (int) characterPlayer.getCulture().getLanguageRanksToChoose()
				+ characterPlayer.getRace().getLanguagePoints());
	}

}

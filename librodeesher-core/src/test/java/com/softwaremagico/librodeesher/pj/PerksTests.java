package com.softwaremagico.librodeesher.pj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.exceptions.CategoryNotEqualsException;
import com.softwaremagico.librodeesher.pj.exceptions.CharacteristicNotEqualsException;
import com.softwaremagico.librodeesher.pj.exceptions.SkillNotEqualsException;
import com.softwaremagico.librodeesher.pj.export.json.CharacterJsonManager;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkDecision;
import com.softwaremagico.librodeesher.pj.perk.PerkFactory;
import com.softwaremagico.librodeesher.pj.perk.PerkPointsCalculator;
import com.softwaremagico.librodeesher.pj.perk.SelectedPerk;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;
import com.softwaremagico.librodeesher.pj.random.PerkProbability;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;

@Test(groups = "perkTests")
public class PerksTests {
	private final static String PERK_WITH_CATEGORY_TO_CHOSE = "Experimentado (Cat) (Máximo)";

	@Test
	public void laanPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(45, new PerkPointsCalculator(RaceFactory.getRace("Laan")).getPerkPoints());
	}

	@Test
	public void centauroCaballoPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(55, new PerkPointsCalculator(RaceFactory.getRace("Centauro Caballo")).getPerkPoints());
	}

	@Test
	public void centauroLeonPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(50, new PerkPointsCalculator(RaceFactory.getRace("Centauro León")).getPerkPoints());
	}

	@Test
	public void dyariPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(30, new PerkPointsCalculator(RaceFactory.getRace("Dyari")).getPerkPoints());
	}

	@Test
	public void dragonetPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(0, new PerkPointsCalculator(RaceFactory.getRace("Dragonet")).getPerkPoints());
	}

	@Test
	public void trollPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(0, new PerkPointsCalculator(RaceFactory.getRace("Troll de Guerra")).getPerkPoints());
	}

	@Test
	public void funguidosPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(15, new PerkPointsCalculator(RaceFactory.getRace("Funguidos")).getPerkPoints());
	}

	@Test
	public void ilourianosPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(50, new PerkPointsCalculator(RaceFactory.getRace("Ilourianos")).getPerkPoints());
	}

	@Test
	public void jhordiPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(55, new PerkPointsCalculator(RaceFactory.getRace("Jhordi")).getPerkPoints());
	}

	@Test
	public void checkTrollRacePerks() {
		CharacterPlayer character = new CharacterPlayer();
		character.setRace("Troll de Guerra");
		Assert.assertEquals(9, character.getRace().getPerks().size());
	}

	@Test
	public void checkRandomPerkCategorySelection() {
		CharacterPlayer character = new CharacterPlayer();
		Perk perk = PerkFactory.getPerk("Experimentado (Cat) (Máximo)");
		Assert.assertNotNull(perk);
		PerkProbability perkProbability = new PerkProbability(character, perk, 0, null);
		perkProbability.selectOptions();
		PerkDecision perkDecision = character.getPerkDecisions().get(perk.getName());
		Assert.assertNotNull(perkDecision);
		Assert.assertEquals(1, perkDecision.getCategoriesBonusChosen().size());
	}

	@Test
	public void checkRandomPerkCategoryWeaponSelection() {
		CharacterPlayer character = new CharacterPlayer();
		Perk perk = PerkFactory.getPerk("Maldición de las Armas");
		Assert.assertNotNull(perk);
		PerkProbability perkProbability = new PerkProbability(character, perk, 0, null);
		perkProbability.selectOptions();
		PerkDecision perkDecision = character.getPerkDecisions().get(perk.getName());
		Assert.assertNotNull(perkDecision);
		Assert.assertEquals(8, perkDecision.getCategoriesBonusChosen().size());
	}

	@Test
	public void checkRandomPerkSkillSelection() {
		CharacterPlayer character = new CharacterPlayer();
		Perk perk = PerkFactory.getPerk("Experimentado (Hab) (Máximo)");
		Assert.assertNotNull(perk);
		PerkProbability perkProbability = new PerkProbability(character, perk, 0, null);
		perkProbability.selectOptions();
		PerkDecision perkDecision = character.getPerkDecisions().get(perk.getName());
		Assert.assertNotNull(perkDecision);
		Assert.assertEquals(1, perkDecision.getSkillsBonusChosen().size());
	}

	@Test
	public void checkRandomPerkWeaponSkillSelection() {
		CharacterPlayer character = new CharacterPlayer();
		Perk perk = PerkFactory.getPerk("Experto en Armas Especializado (Menor)");
		Assert.assertNotNull(perk);
		PerkProbability perkProbability = new PerkProbability(character, perk, 0, null);
		perkProbability.selectOptions();
		PerkDecision perkDecision = character.getPerkDecisions().get(perk.getName());
		Assert.assertNotNull(perkDecision);
		Assert.assertEquals(1, perkDecision.getCommonSkillsChosen().size());
	}

	@Test
	public void checkRandomPerkSkillRankSelection() throws MagicDefinitionException, InvalidProfessionException {
		CharacterPlayer character = new CharacterPlayer();
		character.setRace("Hombres Altos");
		character.setProfession("Mago");
		character.setCulture("Litoral");
		RandomCharacterPlayer randomCharacterPlayer = new RandomCharacterPlayer(character);
		randomCharacterPlayer.createRandomValues();
		Perk perk = PerkFactory.getPerk("Poder (Mínimo)");
		Assert.assertNotNull(perk);
		PerkProbability perkProbability = new PerkProbability(character, perk, 0, null);
		perkProbability.selectOptions();
		PerkDecision perkDecision = character.getPerkDecisions().get(perk.getName());
		Assert.assertNotNull(perkDecision);
		Assert.assertEquals(3, perkDecision.getSkillsRanksChosen().size());
	}

	@Test
	public void checkRandomPerkCategoryOptionSelection() throws MagicDefinitionException, InvalidProfessionException, FileNotFoundException,
			CharacteristicNotEqualsException, CategoryNotEqualsException, SkillNotEqualsException {
		CharacterPlayer character = new CharacterPlayer();
		character.setRace("Hombres Altos");
		character.setProfession("Mago");
		character.setCulture("Litoral");

		List<String> suggestedPerks = Arrays.asList(PERK_WITH_CATEGORY_TO_CHOSE);

		RandomCharacterPlayer randomCharacterPlayer = new RandomCharacterPlayer(character);
		randomCharacterPlayer.setSuggestedPerks(suggestedPerks);
		randomCharacterPlayer.createRandomValues();

		// Check perk is selected.
		System.out.println("###############################3 " + character.getSelectedPerks());
		boolean perkSelected = false;
		for (SelectedPerk selectedPerk : character.getSelectedPerks()) {
			if (selectedPerk.getName().equals(PERK_WITH_CATEGORY_TO_CHOSE)) {
				perkSelected = true;
			}
		}
		Assert.assertTrue(perkSelected);

		// Check can be exported to json and back.
		String jsonText = CharacterJsonManager.toJson(character);
		// get json to object.
		CharacterPlayer importedCharacter = CharacterJsonManager.fromJson(jsonText);
		PrintWriter out1 = new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "character_with_perk.json");
		out1.println(jsonText);
		out1.close();

		// Compared characters.
		CharacterComparator.compare(character, importedCharacter);
	}

}

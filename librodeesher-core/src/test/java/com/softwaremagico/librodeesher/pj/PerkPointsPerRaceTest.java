package com.softwaremagico.librodeesher.pj;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.perk.PerkPointsCalculator;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;

@Test(groups = "perkPoints")
public class PerkPointsPerRaceTest {

	@Test
	public void laanPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(45, new PerkPointsCalculator(RaceFactory.getRace("Laan")).getPerkPoints());
	}

	@Test
	public void centauroCaballoPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(50,
				new PerkPointsCalculator(RaceFactory.getRace("Centauro Caballo")).getPerkPoints());
	}

	@Test
	public void centauroLeonPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(50,
				new PerkPointsCalculator(RaceFactory.getRace("Centauro León")).getPerkPoints());
	}

	@Test
	public void dyariPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(30, new PerkPointsCalculator(RaceFactory.getRace("Dyari")).getPerkPoints());
	}

	@Test
	public void dragonetPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(5, new PerkPointsCalculator(RaceFactory.getRace("Dragonet")).getPerkPoints());
	}

	@Test
	public void trollPerks() throws InvalidRaceDefinition, InvalidRaceException {
		Assert.assertEquals(10,
				new PerkPointsCalculator(RaceFactory.getRace("Troll de Guerra")).getPerkPoints());
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

}

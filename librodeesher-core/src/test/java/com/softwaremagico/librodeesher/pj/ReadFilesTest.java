package com.softwaremagico.librodeesher.pj;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.culture.InvalidCultureException;
import com.softwaremagico.librodeesher.pj.perk.PerkPointsCalculator;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;
import com.softwaremagico.librodeesher.pj.training.InvalidTrainingException;
import com.softwaremagico.librodeesher.pj.training.Training;

@Test(groups = "readFiles")
public class ReadFilesTest extends BasicTest {
	public static final String RACE_FOLDER = "razas";
	public static final String PROFESSION_FOLDER = "profesiones";
	public static final String TRAINING_FOLDER = "adiestramientos";
	public static final String CULTURE_FOLDER = "culturas";

	@Test
	private void readAllRaceFiles() throws InvalidRaceException, InvalidRaceDefinition {
		for (String raceName : RolemasterFolderStructure.getFilesAvailable(RACE_FOLDER)) {
			Race race = new Race(raceName);
			new PerkPointsCalculator(race).getPerkPoints();
		}
	}

	@Test
	private void readAllProfessionFiles() throws InvalidProfessionException {
		for (String professionName : RolemasterFolderStructure.getFilesAvailable(PROFESSION_FOLDER)) {
			new Profession(professionName);
		}
	}

	@Test
	private void readAllTrainingFiles() throws InvalidTrainingException {
		for (String trainingName : RolemasterFolderStructure.getFilesAvailable(TRAINING_FOLDER)) {
			new Training(trainingName);
		}
	}

	@Test
	private void readAllCultureFiles() throws InvalidCultureException {
		for (String cultureName : RolemasterFolderStructure.getFilesAvailable(CULTURE_FOLDER)) {
			new Culture(cultureName);
		}
	}

	@Test
	private void readRaceOptionalLanguages() throws InvalidRaceException {
		Race dopplenganger = RaceFactory.getRace("Doppleganger");
		Assert.assertEquals(dopplenganger.getOptionalRaceLanguages().size(), 1);
		Assert.assertEquals(dopplenganger.getOptionalRaceLanguages().iterator().next()
				.getInitialSpeakingRanks(), 0);
		Assert.assertEquals(dopplenganger.getOptionalRaceLanguages().iterator().next()
				.getInitialWrittingRanks(), 0);
		Assert.assertEquals(dopplenganger.getOptionalRaceLanguages().iterator().next().getMaxSpeakingRanks(),
				10);
		Assert.assertEquals(dopplenganger.getOptionalRaceLanguages().iterator().next().getMaxWritingRanks(),
				10);

		Race laan = RaceFactory.getRace("Laan");
		Assert.assertEquals(laan.getOptionalRaceLanguages().size(), 2);

		Race punkari = RaceFactory.getRace("Punkari");
		Assert.assertEquals(punkari.getOptionalRaceLanguages().size(), 1);
	}

	@Test
	private void readCulturalOptionalLanguages() throws InvalidCultureException {
		Culture subterraneaUrbana = CultureFactory.getCulture("Subterránea Urbana (Calse Alta)");
		Assert.assertEquals(subterraneaUrbana.getOptionalLanguages().size(), 1);
	}
}

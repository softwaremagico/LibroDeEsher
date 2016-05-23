package com.softwaremagico.librodeesher.pj;

import org.testng.annotations.Test;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.culture.InvalidCultureException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;
import com.softwaremagico.librodeesher.pj.training.InvalidTrainingException;
import com.softwaremagico.librodeesher.pj.training.Training;

@Test(groups = "readFiles")
public class ReadFilesTest {
	public static final String RACE_FOLDER = "razas";
	public static final String PROFESSION_FOLDER = "profesiones";
	public static final String TRAINING_FOLDER = "adiestramientos";
	public static final String CULTURE_FOLDER = "culturas";

	@Test
	private void readAllRaceFiles() throws InvalidRaceException {
		for (String raceName : RolemasterFolderStructure
				.getFilesAvailable(RACE_FOLDER)) {
			new Race(raceName);
		}
	}

	@Test
	private void readAllProfessionFiles() throws InvalidProfessionException {
		for (String professionName : RolemasterFolderStructure
				.getFilesAvailable(PROFESSION_FOLDER)) {
			new Profession(professionName);
		}
	}

	@Test
	private void readAllTrainingFiles() throws InvalidTrainingException {
		for (String trainingName : RolemasterFolderStructure
				.getFilesAvailable(TRAINING_FOLDER)) {
			new Training(trainingName);
		}
	}

	@Test
	private void readAllCultureFiles() throws InvalidCultureException {
		for (String cultureName : RolemasterFolderStructure
				.getFilesAvailable(CULTURE_FOLDER)) {
			new Culture(cultureName);
		}
	}
}
